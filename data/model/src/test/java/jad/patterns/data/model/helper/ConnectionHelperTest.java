package jad.patterns.data.model.helper;

import jad.patterns.log.Log;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.*;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by jdonofrio on 9/17/15.
 */
public class ConnectionHelperTest {
    private static final Log l = Log.getLogger(ConnectionHelperTest.class.getName());
    private static final String TEST_SQL_FILE = "/testdata.sql";
    private static final String DATABASE = "/tmp/testDB";
    private static final String JDBC_URL = "jdbc:derby:" + DATABASE + ";create=true";

    @BeforeClass
    public static void init() throws IOException{
        ConnectionHelper.init();
        File data = new File(DATABASE);
        if (data.exists()){
            l.info("Removing old data");
            FileUtils.deleteDirectory(data);
        }
    }

    @AfterClass
    public static void cleanup() throws IOException{
        ConnectionHelper.shutdown();
        l.info("Cleaning up test data");
        File data = new File(DATABASE);
        FileUtils.deleteDirectory(data);
    }

    @Test
    public void loadtestData() throws IOException{
        URL url = this.getClass().getResource(TEST_SQL_FILE);
        File f = new File(url.getFile());
        assertTrue(f.exists());
        byte[] bytes = Files.readAllBytes(f.toPath());
        String sqlFile = new String(bytes);
        Connection c = ConnectionHelper.createConnection(JDBC_URL);
        assertNotNull(c);
        try {
            for(String sql : sqlFile.split(";")){
                l.info("Executing SQL: " + sql);
                Statement s = c.createStatement();
                s.execute(sql);
                s.close();
            }
        } catch(SQLException e) {
            l.error(e);
        }finally {
            try {
                c.close();
            }catch (SQLException e){
                l.error(e);
            }
        }
    }
    @Test
    public void createConnectionTest(){
        Connection c = ConnectionHelper.createConnection();
        assertNotNull(c);
        try{
            c.close();
        } catch(SQLException e){
            l.error(e);
        }
    }
}
