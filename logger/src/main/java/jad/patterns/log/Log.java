package jad.patterns.log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;

public class Log {
    private Logger l;

    private Log(){};
    private Log(String name){
        this.l = Logger.getLogger(name);
    }
    public static Log getLogger(String name){
        return new Log(name);
    }

    public void debug(String msg){l.fine(msg);}
    public void debug(Throwable t){l.fine(convertException(t));}
    public void info(String msg){l.info(msg);}
    public void info(Throwable t){l.info(convertException(t));}
    public void warning(String msg){l.warning(msg);}
    public void warning(Throwable t){l.warning(convertException(t));}
    public void error(String msg){l.severe(msg);}
    public void error(Throwable t){l.severe(convertException(t));}

    private String convertException(Throwable t){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        return sw.toString();
    }
}