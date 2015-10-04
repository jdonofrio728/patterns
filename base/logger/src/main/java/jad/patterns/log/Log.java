package jad.patterns.log;

import java.io.PrintWriter;
import java.io.StringWriter;

public abstract class Log {
    // Future use for config file
    private static final String SECTION = "Log";
    private static final String LOGGER_CLASS_NAME = "log.classname";

    // Temp Use
    private static final String LOGGER_CLASS = "jad.patterns.log.JavaLog";

    protected Log(){};
    protected Log(String name){}

    public static Log getLogger(String name) {
        Object log = null;
        try {
            log = Class.forName(LOGGER_CLASS).getConstructor(String.class).newInstance(name);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
        return (Log) log;
    }

    public abstract void debug(String msg);
    public abstract void debug(Throwable t);
    public abstract void info(String msg);
    public abstract void info(Throwable t);
    public abstract void warning(String msg);
    public abstract void warning(Throwable t);
    public abstract void error(String msg);
    public abstract void error(Throwable t);

    protected String convertException(Throwable t){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        return sw.toString();
    }
}