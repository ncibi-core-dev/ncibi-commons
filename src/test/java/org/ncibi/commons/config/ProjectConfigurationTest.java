package org.ncibi.commons.config;

import static org.junit.Assert.*;

import org.junit.Test;

import org.ncibi.commons.config.Configuration;
import org.ncibi.commons.config.ProjectConfiguration;

public class ProjectConfigurationTest
{

    @Test
    public void testProjectConfiguration()
    {
        Configuration config = ProjectConfiguration.getProject().getConfiguration();
        assertTrue(config != null);
        assertTrue(ProjectConfiguration.getProject().getName().equals("test"));
        assertTrue(ProjectConfiguration.getProject().getVersion().equals("0.1"));
    }
    
    @Test
    public void showSystemProperties()
    {
        System.out.println(System.getProperty("os.name"));
        String os = "Windows XP";
        
        System.out.println(os.toLowerCase().contains("windows"));
    }

}
