package jad.patterns.common;

/**
 * Created by jdonofrio on 10/3/15.
 */
public interface StatementSource {
    public String sql();
    public Object[] parameters();
}
