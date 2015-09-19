package jad.patterns.data.model.helper;

import jad.patterns.common.AbstractDBTestCase;
import jad.patterns.log.Log;
import org.apache.commons.io.FileUtils;
import org.junit.*;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by jdonofrio on 9/17/15.
 */
public class ConnectionHelperTest extends AbstractDBTestCase {
    private static final Log l = Log.getLogger(ConnectionHelperTest.class.getName());


    @BeforeClass
    public static void setup() throws IOException{
        ConnectionHelper.init();
    }

    @AfterClass
    public static void tearDown() throws IOException{
        ConnectionHelper.shutdown();
    }

    @Test
    public void createConnectionTest() throws Exception{
        Connection c = ConnectionHelper.createConnection();
        assertNotNull(c);
        c.close();
    }

    @Override
    protected String getTestDataFile() {
        return null;
    }
}
