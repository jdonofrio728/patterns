package jad.patterns.common;

import java.util.Collection;
import java.util.List;

/**
 * Created by jdonofrio on 9/18/15.
 */
public interface Finder<T> {
    public T find(Long id);
    public List<T> findAll();
}
