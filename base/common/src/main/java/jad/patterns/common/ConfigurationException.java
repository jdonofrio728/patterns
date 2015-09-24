package jad.patterns.common;

/**
 * Created by jdonofrio on 9/24/15.
 */
public class ConfigurationException extends Exception{
    public ConfigurationException(String msg){super(msg);}
    public ConfigurationException(Throwable t){super(t);}
    public ConfigurationException(String msg, Throwable t){super(msg, t);}
}
