package jad.patterns.common;

import jad.patterns.log.Log;

import javax.management.*;
import java.lang.management.ManagementFactory;

/**
 * Created by jdonofrio on 9/28/15.
 */
public abstract class AbstractMBean {
    private static final Log l = Log.getLogger(AbstractMBean.class.getName());
    public static final String DOMAIN = "jad.patterns";
    public abstract String getName();
    public abstract Object getObject();
    public String getDomain(){
        return DOMAIN;
    }
    public void register(){
        MBeanServer mbs = getMBeanServer();
        try {
            l.info("Registering MBean: " + buildName());
            ObjectName obj = new ObjectName(buildName());
            mbs.registerMBean(this, obj);
        } catch (Exception e) {
            l.error("Failed to register mbean: " + this.toString());
            l.error(e);
        }
    }
    protected MBeanServer getMBeanServer() {
        return ManagementFactory.getPlatformMBeanServer();
    }
    private String buildName(){
        return new StringBuilder()
                .append(getDomain())
                .append(":")
                .append(getName())
                .toString();
    }

    @Override
    public String toString() {
        return buildName();
    }
}
