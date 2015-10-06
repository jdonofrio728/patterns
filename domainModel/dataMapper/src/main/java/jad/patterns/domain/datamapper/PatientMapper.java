package jad.patterns.domain.datamapper;

import jad.patterns.common.ApplicationException;
import jad.patterns.common.Finder;
import jad.patterns.common.StatementSource;
import jad.patterns.data.model.DomainModel;
import jad.patterns.data.model.Patient;
import jad.patterns.domain.datamapper.jad.patterns.domains.datamapper.idmap.AbstractIdentityMap;
import jad.patterns.domain.datamapper.jad.patterns.domains.datamapper.idmap.PatientIdentityMap;
import jad.patterns.log.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

/**
 * Created by jdonofrio on 9/18/15.
 */
public class PatientMapper extends AbstractMapper implements Finder<Patient>{
    private static final Log l = Log.getLogger(PatientMapper.class.getName());
    private static final String FIND = "SELECT * FROM PATIENT WHERE ID = ?";
    private static final String FIND_ALL = "SELECT * FROM PATIENT";
    private static final String INSERT = "INSERT INTO PATIENT VALUES(?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE PATIENT SET FIRST_NAME=?, MIDDLE_NAME=?, LAST_NAME=? WHERE ID = ?";
    private static final String NEXT = "SELECT ID FROM PATIENT ORDER BY ID DESC";
    private static final String DELETE = "DELETE FROM PATIENT WHERE ID = ?";

    @Override
    protected String findStatement() {
        return FIND;
    }

    @Override
    protected String insertStatement() {
        return INSERT;
    }

    @Override
    protected void doInsert(DomainModel model, PreparedStatement s) {
        Patient p = (Patient) model;
        try {
            s.setString(2, p.getFirstName());
            s.setString(3, p.getMiddleName());
            s.setString(4, p.getLastName());
        } catch(SQLException e){
            throw new ApplicationException(e);
        }
    }

    @Override
    protected AbstractIdentityMap getIdentityMap() {
        return new PatientIdentityMap();
    }

    @Override
    protected Long findNextId() {
        Connection c = getConnection();
        Long next = null;
        try {
            PreparedStatement s = c.prepareStatement(NEXT);
            s.setMaxRows(1);
            ResultSet rs = s.executeQuery();
            rs.next();
            Long currentId = rs.getLong(1);
            next = currentId + 1;
        } catch (SQLException e){
            throw new ApplicationException(e);
        } finally {
            try {
                c.close();
            } catch (SQLException e) {
                throw new ApplicationException(e);
            }
        }
        return next;
    }

    @Override
    protected DomainModel doLoad(Long id, ResultSet rs) throws SQLException{
        String firstName = rs.getString(2);
        String middleName = rs.getString(3);
        String lastName = rs.getString(4);
        Patient p = new Patient();
        p.setId(id);
        p.setFirstName(firstName);
        p.setMiddleName(middleName);
        p.setLastName(lastName);
//        l.info(p.toString());
        return p;
    }

    public void update(Patient p){
        l.info("Updating patinet with id " + p.getId());
        Connection c = getConnection();
        try{
            PreparedStatement s = c.prepareStatement(UPDATE);
            s.setString(1, p.getFirstName());
            s.setString(2, p.getMiddleName());
            s.setString(3, p.getLastName());
            s.setLong(4, p.getId());
            s.execute();
            getIdentityMap().putObject(p.getId(), p);
        } catch (SQLException e) {
            throw new ApplicationException(e);
        } finally {
            try {
                c.close();
            } catch (SQLException e) {
                throw new ApplicationException(e);
            }
        }
    }

    public void delete(Long id){
        l.info("Deleting patient with id " + id);
        Connection c = getConnection();
        try{
            PreparedStatement s = c.prepareStatement(DELETE);
            s.setLong(1, id);
            s.execute();
            getIdentityMap().putObject(id, null);
        }catch (SQLException e){
            throw new ApplicationException(e);
        } finally {
            try{
                c.close();
            }catch (SQLException e){
                throw new ApplicationException(e);
            }
        }
    }

    @Override
    public Patient find(Long id) {
        return (Patient) abstractFind(id);
    }

    @Override
    public List<Patient> findAll() {
        return findMany(new FindAllPatients());
    }

    private static class FindAllPatients implements StatementSource{

        @Override
        public String sql() {
            return FIND_ALL;
        }

        @Override
        public Object[] parameters() {
            return new Object[0];
        }
    }
}
