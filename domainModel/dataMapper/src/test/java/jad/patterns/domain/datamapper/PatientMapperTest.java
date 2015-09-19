package jad.patterns.domain.datamapper;

import jad.patterns.common.AbstractDBTestCase;
import jad.patterns.data.model.helper.ConnectionHelper;
import jad.patterns.log.Log;
import org.apache.derby.iapi.services.classfile.CONSTANT_Index_info;
import org.junit.*;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.Statement;

import static org.junit.Assert.*;

/**
 * Created by jdonofrio on 9/18/15.
 */
public class PatientMapperTest extends AbstractDBTestCase{
    private static final Log l = Log.getLogger(PatientMapperTest.class.getName());
    private static final String TEST_SQL_FILE = "/testdata.sql";
    private static Connection c;
    private boolean flag = false;

    @BeforeClass
    public static void init() throws Exception{
        ConnectionHelper.init();
    }

    @AfterClass
    public static void destroy() throws Exception{
        ConnectionHelper.shutdown();
    }

    @Before
    public void setUp() throws Exception {
        cleanup();
        if(c == null){
            c = ConnectionHelper.createConnection(getJDBCURL());
        }
        if(c.isClosed()) {
            c = ConnectionHelper.createConnection(getJDBCURL());
        }
        if(!flag){
            loadTestData(c);
            flag = true;
        }
    }

    @After
    public void tearDown() throws Exception {
        c.close();
    }

    @Test
    public void testFindStatement() throws Exception {
        String stmnt = new PatientMapper().findStatement();
        assertNotNull(stmnt);
        assertTrue(stmnt.toUpperCase().contains("SELECT"));
    }

    @Test
    public void testDoLoad() throws Exception {

    }

    @Test
    public void testFind() throws Exception {

    }

    @Override
    protected String getTestDataFile() {
        return TEST_SQL_FILE;
    }
}