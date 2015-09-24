package jad.patterns.common;

import jad.patterns.log.Log;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jdonofrio on 9/24/15.
 */
public class ConfigManagerTest {
    private static final Log l = Log.getLogger(ConfigManagerTest.class.getName());
    @Test
    public void testGetInstance() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        assertNotNull(cm);
    }

    @Test
    public void testGetValue() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        String value = cm.getValue("Section1", "value1");
        assertNotNull(value);
        l.info("Loaded value: " + value);
        assertEquals("SecretMessage", value);
        try {
            cm.getValue("Missing Section", "MissingValue");
        } catch(PropertyNotFoundException e){
            l.info(e);
            l.info("Excepted exception");
        }
        try {
            cm.getValue("Section1", "MissingValue");
        } catch(PropertyNotFoundException e){
            l.info(e);
            l.info("Excepted exception-");
        }
    }
}