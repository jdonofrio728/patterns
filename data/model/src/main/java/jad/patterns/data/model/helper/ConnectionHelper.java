package jad.patterns.data.model.helper;

import jad.patterns.log.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by jdonofrio on 9/17/15.
 */

public class ConnectionHelper {
    private static final Log l = Log.getLogger(ConnectionHelper.class.getName());
    private static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    private static boolean flag = false;

    private ConnectionHelper(){}

    public static void init(){
        if(!flag){
            // Database is not initialized yet
            try {
                l.info("Initializing embedded database");
                System.setProperty("derby.system.home", "/tmp");
//                Class.forName(DRIVER).newInstance();
                flag = true;
            } catch (Exception e){
                l.error(e);
            }
        }
    }
    public static Connection createConnection(){
        String jdbcUrl = "jdbc:derby:derbyDB;create=true";
        return createConnection(jdbcUrl);
    }
    public static Connection createConnection(String url){
        if(!flag){
            init();
        }
        Connection c = null;
        try {
            l.info("Creating connection");
            c = DriverManager.getConnection(url);
        }catch (SQLException e){
            l.error(e);
        }
        return c;
    }
    public static void shutdown(){
        String url = "jdbc:derby:derbyDB;shutdown=true";
        shutdown(url);
    }
    public static void shutdown(String url){
        if(flag){
            l.info("Shutting down database engine");
            try{
                DriverManager.getConnection(url + ";shutdown=true");
                flag = false;
            } catch(SQLException e){
                // Expected error: ERROR XJ015 for derby based system
                // Expected error: ERROR 08006 for derby shutdown
                if (!(e.getCause().toString().contains("XJ015") || e.getCause().toString().contains("08006"))) {
                    l.error(e);
                }
            }
        }

    }
}
