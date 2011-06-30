package org.ncibi.commons.config;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.ncibi.commons.io.FileUtilities;

/**
 * This class allows for the static configuration of project properties. The
 * name of the project is contained in the project.config file in the
 * project.name property. The project.name property is used to load a
 * Configuration property file with the project.name string + .properties. For
 * example, if project.name=myproj, then myproj.properties is loaded for the
 * project specific configuration.
 * 
 * @author V. Glenn Tarcea
 */
public final class ProjectConfiguration
{
    /**
     * The name of the project.
     */
    private String projectName;

    /**
     * The version of the project.
     */
    private String projectVersion;

    /**
     * The Configuration object for the project.
     */
    private Configuration projectConfig;

    private Configuration overrideProjectConfig;
    
    /**
     * Create a thread safe singleton by putting the instance creation into a
     * separate inner class.
     * 
     * @author gtarcea
     */
    private static class ProjectConfigurationLoader
    {
        /**
         * The singleton project configuration instance.
         */
        private static final ProjectConfiguration INSTANCE = new ProjectConfiguration();
    }
    
    /**
     * Hides the constructor as it is should never be called outside of this
     * class.
     * 
     * @throws IllegalStateException
     *             If the configuration cannot be loaded.
     * @throws AssertionError
     *             If the constructor is called more than once.
     */
    
    private ProjectConfiguration()
    {       
        String projectConfigFile = projectConfigFileNameFromProject();

        try
        {
            loadConfiguration(projectConfigFile);
        }
        catch (InvalidConfigurationException e)
        {
            String message = invalidConfigurationMessage(projectConfigFile);
            throw new IllegalStateException(message, e);
        }
    }
    
    private String projectConfigFileNameFromProject()
    {
        InputStream in = openDotProjectFileInputStream();
        String projectFile = "project.config";
        
        if (in != null)
        {
            String projectName = readStreamIntoProjectName(in);
            projectFile += projectName;
        }
        
        IOUtils.closeQuietly(in);
        
        return projectFile;
    }
    
    private String readStreamIntoProjectName(InputStream in)
    {
        try
        {
            String projectName = FileUtilities.convertStreamToString(in);
            return "." + projectName.trim();
        }
        catch (IOException e)
        {
            return "";
        }
    }
    
    private InputStream openDotProjectFileInputStream()
    {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        final InputStream in = classLoader.getResourceAsStream(".project");
        
        return in;
    }
    
    private void loadConfiguration(String projectConfigFile) throws InvalidConfigurationException
    {
        final Configuration cfg = new Configuration(projectConfigFile);
        projectName = cfg.getProperty("project.name");
        if (projectName == null)
        {
            throw new IllegalStateException(
                    "The project configuration is incorrect. The project.name property was not set.");
        }
        projectVersion = cfg.getProperty("project.version");
        projectConfig = new Configuration(projectName + ".properties");
        openOSSpecificOverride(projectName + ".properties");
    }
    
    private void openOSSpecificOverride(String propertiesFileName)
    {
        String os = System.getProperty("os.name");
        String propertyFilePath = null;
        if (os.toLowerCase().contains("windows"))
        {
            propertyFilePath = "c:/" + propertiesFileName; 
        }
        else
        {
            propertyFilePath = "/etc/" + propertiesFileName;
        }
        
        try
        {
            overrideProjectConfig = Configuration.newConfigurationUsingPath(propertyFilePath);
        }
        catch (InvalidConfigurationException e)
        {
            overrideProjectConfig = null;
        }
    }
    
    private String invalidConfigurationMessage(String projectConfigFile)
    {
        final StringBuilder message = new StringBuilder("Problem loading project configuration file " + projectConfigFile + ".");
        if (projectName != null)
        {
            message.append(", configuration file = " + projectName + ".properties");
        }
        return message.toString();
    } 

    /**
     * Gets the single instance of ProjectInstance().
     * 
     * @return The singleton ProjectInstance
     */
    public static ProjectConfiguration getProject()
    {
        return ProjectConfigurationLoader.INSTANCE;
    }
    
    public static ProjectConfiguration getProject(String project)
    {
        return null;
    }

    /**
     * Returns a property value configured for the project.
     * 
     * @param property
     *            The property to get the value for.
     * @return The property value.
     */
    public static String getProjectProperty(String property)
    {
        String propertyValue = getOverrideProjectProperty(property);
        if (propertyValue != null)
        {
            return propertyValue;
        }
        else
        {
            return getProject().getConfiguration().getProperty(property);
        }
    }
    
    private static String getOverrideProjectProperty(String property)
    {
        Configuration c = getProject().getOverrideConfiguration();
        String propertyValue = null;
        if (c != null)
        {
            propertyValue = c.getProperty(property);
        }
        
        return propertyValue;
    }

    /**
     * Returns a property value, or if no value the defaultValue.
     * 
     * @param property
     *            The property to get the value for.
     * @param defaultValue
     *            The default value to use if the property is not configured.
     * @return The property value or the defaultValue given.
     */
    public static String getDefaultedProjectProperty(String property, String defaultValue)
    {
        String propertyValue = getOverrideProjectProperty(property);
        if (propertyValue != null)
        {
            return propertyValue;
        }
        else
        {
            return getProject().getConfiguration().getDefaultedProperty(property, defaultValue);
        }
    }

    /**
     * Retrieves the Configuration object for a project.
     * 
     * @return The Configuration object for the project.
     */
    public Configuration getConfiguration()
    {
        return projectConfig;
    }
    
    public Configuration getOverrideConfiguration()
    {
        return overrideProjectConfig;
    }

    /**
     * Retrieves the name for a project.
     * 
     * @return The project name (as configured).
     */
    public String getName()
    {
        return projectName;
    }

    /**
     * Retrieves the version for a project.
     * 
     * @return The project version (as configured).
     */
    public String getVersion()
    {
        return projectVersion;
    }
}
