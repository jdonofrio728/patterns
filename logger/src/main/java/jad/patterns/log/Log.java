package jad.patterns.log;

import java.util.logging.Logger;

public class Log {
    private java.util.logging.Logger l;

    private Log(){};
    private Log(String name){
        this.l = Logger.getLogger(name);
    }
    public static Log getLogger(String name){
        return new Log(name);
    }

    public void debug(String msg){l.fine(msg);}
    public void info(String msg){l.info(msg);}
    public void warning(String msg){l.warning(msg);}
    public void error(String msg){l.severe(msg);}
}