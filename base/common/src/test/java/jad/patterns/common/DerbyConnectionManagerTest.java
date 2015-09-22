package jad.patterns.common;

import jad.patterns.log.Log;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.sql.Connection;

import static org.junit.Assert.*;

/**
 * Created by jdonofrio on 9/21/15.
 */
public class DerbyConnectionManagerTest {
    private static final Log l = Log.getLogger(DerbyConnectionManagerTest.class.getName());
    private DerbyConnectionManager db;

    @Before
    public void setUp() throws Exception {
        db = new DerbyConnectionManager();
        File dir = db.getDBDirectory();
        if(dir.exists()){
            l.info("Deleting old database");
            FileUtils.deleteDirectory(dir);
        }
        db.init();
    }

    @After
    public void tearDown() throws Exception {
        db.cleanupConnections();
        db.shutdown();
    }

    @Test
    public void testGetDriver() throws Exception {
        assertNotNull(db.getDriver());
    }

    @Test
    public void testGetJDBCURL() throws Exception {
        assertNotNull(db.getJDBCURL());
    }

    @Test
    public void testCreateConnection() throws Exception {
        Connection c = db.createConnection();
        assertNotNull(c);
        assertFalse(c.isClosed());
    }

    @Test
    public void testInit() throws Exception {
        // NA
    }

    @Test
    public void testShutdown() throws Exception {
        //NA
    }
}