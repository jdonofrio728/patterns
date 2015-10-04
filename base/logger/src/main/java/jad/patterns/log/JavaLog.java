package jad.patterns.log;

import java.util.logging.Logger;

/**
 * Created by jdonofrio on 9/20/15.
 */
public class JavaLog extends Log {
    private static final String LOG_FORMATTER_PROP = "java.util.logging.SimpleFormatter.format";
    protected Logger l;
    protected JavaLog(){}
    public JavaLog(String name){
        this.l = Logger.getLogger(name);
        if(System.getProperty(LOG_FORMATTER_PROP) == null){
            System.setProperty(LOG_FORMATTER_PROP, "<%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS> <%4$s> <%3$s> %5$s %6$s%n");
//            System.setProperty(LOG_FORMATTER_PROP, "%1$s %2$s %3$s %4$s %5$s %6$s%n");
        }
    }

    public void debug(String msg){l.fine(msg);}
    public void debug(Throwable t){l.fine(convertException(t));}
    public void info(String msg){l.info(msg);}
    public void info(Throwable t){l.info(convertException(t));}
    public void warning(String msg){l.warning(msg);}
    public void warning(Throwable t){l.warning(convertException(t));}
    public void error(String msg){l.severe(msg);}
    public void error(Throwable t){l.severe(convertException(t));}
}
