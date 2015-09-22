package jad.patterns.common;

import jad.patterns.log.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by jdonofrio on 9/21/15.
 */
public abstract class ConnectionManager {
    private static final Log l = Log.getLogger(ConnectionManager.class.getName());
    protected List<Connection> connectionList;

    private static final String DEFAULT_CLASS = "jad.patterns.common.DerbyConnectionManager";

    protected ConnectionManager(){connectionList = new ArrayList<>();}

    public static ConnectionManager createInstance() {
        ConnectionManager cm = null;
        try{
            cm = (ConnectionManager) Class.forName(DEFAULT_CLASS).newInstance();
        } catch (Exception e){
            l.error(e);
        }
        return cm;
    }

    protected void cleanupConnections() throws SQLException{
        Iterator<Connection> i = connectionList.iterator();
        while(i.hasNext()){
            Connection c = i.next();
            if(!c.isClosed()){
                l.warning("Closing leaked connection");
                c.close();
                i.remove();
            }
        }
    }

    @Override
    protected void finalize() throws Throwable {
        cleanupConnections();
        super.finalize();
    }

    public Connection createConnection() throws SQLException{
        return createConnection(getJDBCURL());
    }
    public Connection createConnection(String url) throws SQLException{
        Connection c = null;
        l.info("Creating connection: " + url);
        c = DriverManager.getConnection(url);
        connectionList.add(c);
        return c;
    }

    public abstract String getDriver();
    public abstract String getJDBCURL();
    public abstract void init();
    public abstract void shutdown();
}
