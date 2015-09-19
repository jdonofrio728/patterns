package jad.patterns.common;

import jad.patterns.log.Log;

import java.io.File;
import java.nio.file.Files;

/**
 * Created by jdonofrio on 9/18/15.
 */
public abstract class AbstractTestCase {
    private static final Log l = Log.getLogger(AbstractTestCase.class.getName());
    protected String readFileToString(File f) throws Exception{
        byte[] bytes = Files.readAllBytes(f.toPath());
        return new String(bytes);
    }

}
