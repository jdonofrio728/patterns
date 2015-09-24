package jad.patterns.common;

/**
 * Created by jdonofrio on 9/24/15.
 */
public class PropertyNotFoundException extends Exception {
    public PropertyNotFoundException(String msg){super(msg);}
    public PropertyNotFoundException(Throwable t){super(t);}
}
