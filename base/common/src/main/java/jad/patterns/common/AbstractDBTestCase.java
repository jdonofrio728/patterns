package jad.patterns.common;

import jad.patterns.log.Log;
import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.Statement;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by jdonofrio on 9/18/15.
 */
public abstract class AbstractDBTestCase extends AbstractTestCase{
    private static final Log l = Log.getLogger(AbstractDBTestCase.class.getName());
    private static final String DEFAULT_LOCATION = "/tmp";
    private static final String DEFAULT_NAME = "/testDB";
    private static final String DEFAULT_LOG_FILE = "log.txt";
    private static final String CREATE_DB = ";create=true";

    protected String getJDBCURL(){
        String url =  "jdbc:derby:" + getDBLocation() + ":" + getDBName() + getDBProps();
        l.debug("JDBC URL: " + url);
        return url;
    }

    protected String getDBLocation(){
        return DEFAULT_LOCATION + getDBName();
    }

    protected String getDBProps(){
        return CREATE_DB + ";logDevice=" + getDBLocation();
    }

    protected String getDBName(){
        return DEFAULT_NAME;
    }

    protected String getLogFile(){
        return DEFAULT_LOCATION + "/LOG_FILE";
    }

    protected String getLogFileName(){
        return DEFAULT_LOG_FILE;
    }

    protected void loadTestData(Connection c) throws Exception{
        assertNotNull(c);
        URL url = this.getClass().getResource(getTestDataFile());
        File f = new File(url.getFile());
        assertTrue(f.exists());
        String sqlFile = readFileToString(f);
        for(String sql : sqlFile.split(";")){
            l.debug("Executing SQL: " + sql);
            Statement s = c.createStatement();
            s.execute(sql);
            s.close();
        }
        c.close();
    }

    protected void cleanup() throws Exception{
        l.info("Cleaning up test data");
        File data = new File(getDBLocation());
        if(data.exists()) {
            FileUtils.deleteDirectory(data);
        }
    }

    protected abstract String getTestDataFile();
}
