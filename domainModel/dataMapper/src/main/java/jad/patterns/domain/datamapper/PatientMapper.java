package jad.patterns.domain.datamapper;

import jad.patterns.common.Finder;
import jad.patterns.data.model.DomainModel;
import jad.patterns.data.model.Patient;
import jad.patterns.log.Log;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by jdonofrio on 9/18/15.
 */
public class PatientMapper extends AbstractMapper implements Finder<Patient>{
    private static final Log l = Log.getLogger(PatientMapper.class.getName());
    private static final String FIND = "SELECT * FROM PATIENT WHERE ID = ?";
    @Override
    protected String findStatement() {
        return FIND;
    }

    @Override
    protected DomainModel doLoad(Long id, ResultSet rs) throws SQLException{
        String firstName = rs.getString(2);
        String middleName = rs.getString(3);
        String lastName = rs.getString(4);
        Patient p = new Patient();
        p.setFirstName(firstName);
        p.setMiddleName(middleName);
        p.setLastName(lastName);
        return p;
    }

    @Override
    public Patient find(Long id) {
        return (Patient) abstractFind(id);
    }
}
