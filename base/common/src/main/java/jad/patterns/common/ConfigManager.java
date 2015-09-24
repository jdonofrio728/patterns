package jad.patterns.common;

import jad.patterns.log.Log;
import org.ini4j.Ini;
import org.ini4j.Profile;


import java.io.FileReader;


/**
 * Created by jdonofrio on 9/20/15.
 */
public class ConfigManager {
    private static final Log l = Log.getLogger(ConfigManager.class.getName());
    private static final String PROP_CONFIG_NAME = "jad.patterns.configfile";
    private static ConfigManager instance;
    private Ini configFile;

    private ConfigManager() {
        configFile = new Ini();
        String fileName = System.getProperty(PROP_CONFIG_NAME);
        if(fileName == null){
            l.error("Configuration file parameter not set, only defaults will be used");
            return;
        }

        try {
            l.debug("Loading file " + fileName);
            configFile.load(new FileReader(fileName));
            l.info("Config file loaded successfully");
        }catch(Exception e){
            String msg = "Failed to load configuration file " + fileName;
            l.error(msg);
            l.error(e);
        }
    }
    public static ConfigManager getInstance() {
        if(instance == null){
            l.info("Initializing ConfigManager");
            instance = new ConfigManager();
        }
        return instance;
    }

    public String getValue(String section, String property) throws PropertyNotFoundException{
        Profile.Section s = configFile.get(section);
        if(s == null){
            throw new PropertyNotFoundException("Section: " + section + " is missing");
        }
        String value = s.get(property);
        if(value == null){
            throw new PropertyNotFoundException("Section: " + section + ", property: " + property + " is missing");
        }
        return value;
    }
}
