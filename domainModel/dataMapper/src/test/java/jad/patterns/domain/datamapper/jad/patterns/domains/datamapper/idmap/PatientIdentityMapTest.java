package jad.patterns.domain.datamapper.jad.patterns.domains.datamapper.idmap;

import jad.patterns.data.model.Patient;
import jad.patterns.log.Log;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jdonofrio on 10/6/15.
 */
public class PatientIdentityMapTest {
    private static final Log l = Log.getLogger(PatientIdentityMapTest.class.getName());

    @Test
    public void testGetObject() throws Exception {
        PatientIdentityMap identityMap = new PatientIdentityMap();
        assertNull(identityMap.getObject(1l));
        Patient p = new Patient();
        p.setId(1L);
        identityMap.putObject(1L, p);
        assertNotNull(identityMap.getObject(1l));
    }

    @Test
    public void testPutObject() throws Exception {
        // Tested with getObject()
    }
}