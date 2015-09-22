package jad.patterns.common;


import jad.patterns.log.Log;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by jdonofrio on 9/21/15.
 */
public class DerbyConnectionManager extends ConnectionManager {
    private static final Log l = Log.getLogger(DerbyConnectionManager.class.getName());
    private static final String DEFAULT_DERBY_HOME = "/tmp";
    private static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";

    private static String databaseName = "testDB";
    private static boolean flag = false;

    static{
        if(System.getProperty("derby.system.home") == null) {
            l.debug("Setting derby home property to " + DEFAULT_DERBY_HOME);
            System.setProperty("derby.system.home", DEFAULT_DERBY_HOME);
        }
    }

    public DerbyConnectionManager(){

    }
    public DerbyConnectionManager(String name){
        databaseName = name;
    }

    @Override
    public String getDriver() {
        return DRIVER;
    }

    @Override
    public String getJDBCURL() {
        String url = "jdbc:derby:" + databaseName;
//        if(!flag){
//            url = url + ";create=true";
//        }
        return url;
    }

//    @Override
//    public Connection createConnection() throws SQLException{
//        return super.createConnection();
//    }
//
//    @Override
//    public Connection createConnection(String url) throws SQLException{
//        if(!flag){
//            init();
//        }
//        return super.createConnection(url);
//    }

    @Override
    public void init(){
        l.info("Initializing database system");

        try {
            Class.forName(DRIVER).newInstance();
            createConnection(getJDBCURL() + ";create=true").close();
            flag = true;
        } catch(Exception e){
            l.error(e);
        }
    }

    @Override
    public void shutdown() {
        l.info("Shutting down database system");
        try {
            createConnection(getJDBCURL() + ";shutdown=true");

        } catch(SQLException e){
            // Expected error: ERROR XJ015 for derby based system
            // Expected error: ERROR 08006 for derby shutdown
            if (!(e.getCause().toString().contains("XJ015") || e.getCause().toString().contains("08006"))) {
                l.error(e);
            }
        }
        flag = false;
    }

    public File getDBDirectory(){
        return new File(System.getProperty("derby.system.home") + "/" + databaseName);
    }
}
