package jad.patterns.data.model.helper;

import jad.patterns.common.test.AbstractDBTestCase;
import jad.patterns.log.Log;
import org.junit.*;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.Connection;

/**
 * Created by jdonofrio on 9/17/15.
 */
public class ConnectionHelperTest extends AbstractDBTestCase {
    private static final Log l = Log.getLogger(ConnectionHelperTest.class.getName());
    private static final String TEST_SQL_FILE = "/testdata.sql";
    private static String url = null;
    public ConnectionHelperTest() throws Exception{
        // Initialize DB
        ConnectionHelper.init();
        url = getJDBCURL();
        cleanup();
    }

    @BeforeClass
    public static void setup() throws IOException{
//        ConnectionHelper.init();
    }

    @AfterClass
    public static void tearDown() throws IOException{
        if(url != null){
            ConnectionHelper.shutdown(url);
        }
    }

    @Test
    public void createConnectionTest() throws Exception{
        Connection c = ConnectionHelper.createConnection(getJDBCURL());
        assertNotNull(c);
        loadTestData(c);
        c.close();
    }

    @Override
    protected String getTestDataFile() {
        return TEST_SQL_FILE;
    }
}
