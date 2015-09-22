package jad.patterns.domain.datamapper;

import jad.patterns.common.ApplicationException;
import jad.patterns.common.ConnectionManager;
import jad.patterns.data.model.DomainModel;
import jad.patterns.data.model.helper.ConnectionHelper;
import jad.patterns.log.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractMapper {
    private static final Log l = Log.getLogger(AbstractMapper.class.getName());
    private ConnectionManager cm;
//    protected Connection getConnection(){
//        return ConnectionHelper.createConnection();
//    }
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
            PreparedStatement s = c.prepareStatement(findStatement());
            s.setLong(1, id.longValue());
            l.debug("Executing SQL: " + s.toString());
            ResultSet rs = s.executeQuery();
            rs.next();

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
        return doLoad(id, rs);
    }

    protected abstract DomainModel doLoad(Long id, ResultSet rs) throws SQLException;
    protected abstract String findStatement();
}