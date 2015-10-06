package jad.patterns.domain.datamapper;

import jad.patterns.common.ApplicationException;
import jad.patterns.common.ConnectionManager;
import jad.patterns.common.StatementSource;
import jad.patterns.data.model.DomainModel;
import jad.patterns.domain.datamapper.jad.patterns.domains.datamapper.idmap.AbstractIdentityMap;
import jad.patterns.log.Log;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMapper {
    private static final Log l = Log.getLogger(AbstractMapper.class.getName());
    private ConnectionManager cm;
    public AbstractMapper(){
        cm = ConnectionManager.createInstance();
    }

    @Override
    protected void finalize() throws Throwable {
        cm.shutdown();
        super.finalize();
    }

    protected Connection getConnection(){
        Connection c = null;
        try {
            c = cm.createConnection();
        } catch (SQLException e){
            l.error(e);
        }
        return c;
    }

    protected DomainModel abstractFind(Long id){
        DomainModel result = null;
        l.info("Finding entity with id " + id);
        Connection c = getConnection();
        try {
            AbstractIdentityMap identityMap = getIdentityMap();
            if(identityMap != null){
                result = identityMap.getObject(id);
                if(result != null){
                    l.info("Found cached copy, returning");
                    return result;
                }
            }
            PreparedStatement s = c.prepareStatement(findStatement());
            s.setLong(1, id.longValue());
            l.debug("Executing SQL: " + findStatement());
            ResultSet rs = s.executeQuery();
            if(rs.next()){
                result = load(rs);
            }
            l.debug("Entity not found");
        } catch (SQLException e) {
            throw new ApplicationException(e);
        } finally {
            try {
                c.close();
            }catch (SQLException e2){
                throw new ApplicationException(e2);
            }
        }
        return result;
    }

    protected DomainModel load(ResultSet rs) throws SQLException{
        Long id = new Long(rs.getLong(1));
        AbstractIdentityMap identityMap = getIdentityMap();
        if(identityMap.getObject(id) != null){
            l.info("Found cached copy, returning");
            return identityMap.getObject(id);
        }
        DomainModel m = doLoad(id, rs);
        identityMap.putObject(id, m);
        return m;
    }

    public Long insert(DomainModel model){
        Connection c = getConnection();
        Long nextId = null;
        try {
            PreparedStatement s = c.prepareStatement(insertStatement());
            nextId = findNextId();
            model.setId(nextId);
            s.setLong(1, model.getId());
            doInsert(model, s);
            s.execute();
            getIdentityMap().putObject(model.getId(), model);
        } catch(SQLException e){
            l.error("Error occurred");
            throw new ApplicationException(e);
        } finally {
            try {
                c.close();
            }
            catch (Exception e){
                l.error("Error while closing connection");
                throw new ApplicationException(e);
            }
        }
        return nextId;
    }

    protected List findMany(StatementSource s){
        List result = null;
        Connection c = getConnection();
        try{
            PreparedStatement stmt = c.prepareStatement(s.sql());
            int i = 0;
            for(Object o : s.parameters()){
                stmt.setObject(i, o);
                i++;
            }
            ResultSet rs = stmt.executeQuery();
            result = loadAll(rs);

        } catch (SQLException e) {
            throw new ApplicationException(e);
        }
        return result;
    }

    protected List loadAll(ResultSet rs) throws SQLException{
        List result = new ArrayList();
        while(rs.next()){
            DomainModel d = load(rs);
            result.add(d);
        }
        return result;
    }

    protected abstract Long findNextId();
    protected abstract DomainModel doLoad(Long id, ResultSet rs) throws SQLException;
    protected abstract String findStatement();
    protected abstract String insertStatement();
    protected abstract void doInsert(DomainModel model, PreparedStatement s);
    protected abstract AbstractIdentityMap getIdentityMap();
}