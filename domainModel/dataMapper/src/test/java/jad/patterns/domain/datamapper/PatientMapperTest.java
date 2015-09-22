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

import static org.junit.Assert.*;

/**
 * Created by jdonofrio on 9/18/15.
 */
public class PatientMapperTest extends AbstractDBTestCase {
    private static final Log l = Log.getLogger(PatientMapperTest.class.getName());
    private static final String TEST_SQL_FILE = "/testdata.sql";
    private static DerbyConnectionManager cm;
//    private static Connection c;
//    private boolean flag = false;
//    private static String url;

    public PatientMapperTest() throws Exception{
//        cleanup();
//        ConnectionHelper.init();
//        url = getJDBCURL();

    }

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
    }

    @AfterClass
    public static void destroy() throws Exception{
        l.info("Cleaning up test class");
        cm.shutdown();
    }

    @Before
    public void setUp() throws Exception {
//        c = ConnectionHelper.createConnection(getJDBCURL());


    }

    @After
    public void tearDown() throws Exception {
//        c.close();
//        cm.shutdown();
    }

    @Test
    public void testFindStatement() throws Exception {
//        String stmnt = new PatientMapper().findStatement();
//        assertNotNull(stmnt);
//        assertTrue(stmnt.toUpperCase().contains("SELECT"));
    }

    @Test
    public void testDoLoad() throws Exception {
        Connection c = cm.createConnection();
        loadTestData(c);
        c.close();
    }

    @Test
    public void testFind() throws Exception {
//        PreparedStatement s = c.prepareStatement("SELECT * FROM PATIENT WHERE ID = ?");
//        PatientMapper m = new PatientMapper();
//        Patient p = m.find(2L);
//        assertNotNull(p);
//        l.info(p.toString());
//        assertSame(new Long(2), p.getId());
    }

    @Override
    protected String getTestDataFile() {
        return TEST_SQL_FILE;
    }
}