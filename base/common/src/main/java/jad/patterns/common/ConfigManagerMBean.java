package jad.patterns.common;

/**
 * Created by jdonofrio on 9/28/15.
 */
public interface ConfigManagerMBean {
    public int countSections();
    public int countEntriesInSection(String sectionName);
    public String[] listSections();
    public String[] listEntriesInSection(String sectionName);
}
