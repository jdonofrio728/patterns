package jad.patterns.common;

/**
 * Created by jdonofrio on 9/18/15.
 */
public class ApplicationException extends RuntimeException {
    public ApplicationException(String msg){
        super(msg);
    }
    public ApplicationException(Throwable t){
        super(t);
    }
}
