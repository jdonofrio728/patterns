package jad.patterns.domain.datamapper;


import jad.patterns.common.ConnectionManager;
import jad.patterns.common.DerbyConnectionManager;
import jad.patterns.common.test.AbstractDBTestCase;
import jad.patterns.data.model.Patient;
import jad.patterns.data.model.helper.ConnectionHelper;
import jad.patterns.log.Log;
import org.apache.commons.io.FileUtils;
import org.junit.*;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import static org.junit.Assert.*;

/**
 * Created by jdonofrio on 9/18/15.
 */
public class PatientMapperTest extends AbstractDBTestCase {
    private static final Log l = Log.getLogger(PatientMapperTest.class.getName());
    private static final String TEST_SQL_FILE = "/testdata.sql";
    private static DerbyConnectionManager cm;

    public PatientMapperTest() throws Exception{}

    @BeforeClass
    public static void init() throws Exception{
        l.info("Initializing test class");
        cm = (DerbyConnectionManager) ConnectionManager.createInstance();
        File dir = cm.getDBDirectory();
        l.info("DB: " + dir);
        if(dir.exists()){
            l.info("Deleting old database");
            FileUtils.deleteDirectory(dir);
        }
        cm.init();
        Connection c = cm.createConnection();
        new PatientMapperTest().loadTestData(c);
        c.close();
    }

    @AfterClass
    public static void destroy() throws Exception{
        l.info("Cleaning up test class");
        cm.shutdown();
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testFindStatement() throws Exception {
        assertNotNull(new PatientMapper().findStatement());
    }

    @Test
    public void testFind() throws Exception {
        Connection c = cm.createConnection();
        c.close();

        PatientMapper m = new PatientMapper();
        Patient p = m.find(1L);
        assertNotNull(p);
        l.info(p.toString());
    }

    @Test
    public void testInsertStatement() throws Exception{
        assertNotNull(new PatientMapper().insertStatement());
    }

    @Test
    public void testInsert() throws Exception{
        l.info("Starting test: testInsert()");
        PatientMapper m = new PatientMapper();
        Patient p = new Patient();
        p.setFirstName("Test");
        p.setMiddleName("Testicular");
        p.setLastName("Testy");
        Long id = m.insert(p);
        l.info("New Patient id: " + id);
        p = m.find(id);
        assertNotNull(p);
        l.info(p.toString());
        assertEquals(id, p.getId());
    }

    @Test
    public void testNextId() throws Exception{
        PatientMapper m = new PatientMapper();
        Long id = m.findNextId();
        l.info("Next available ID: " + id);
        assertNotNull(id);
    }

    @Test
    public void testFindAll() throws Exception{
        l.info("Starting test: findAll()");
        PatientMapper m = new PatientMapper();
        List<Patient> list = m.findAll();
        for(Patient p : list){
            l.info(p.toString());
        }
    }
    @Test
    public void testUpdate() throws Exception{
        l.info("Starting test: update()");
        PatientMapper m = new PatientMapper();
        Patient p = m.find(1L);
        assertNotNull(p);
        p.setFirstName("John");
        p.setMiddleName("Peaches");
        p.setLastName("Doe");
        m.update(p);
        p = m.find(1L);
        l.info(p.toString());
        assertEquals("John", p.getFirstName());
        assertEquals("Peaches", p.getMiddleName());
        assertEquals("Doe", p.getLastName());
    }

    @Override
    protected String getTestDataFile() {
        return TEST_SQL_FILE;
    }
}