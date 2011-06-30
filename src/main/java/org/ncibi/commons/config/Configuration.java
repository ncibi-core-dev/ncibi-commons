package org.ncibi.commons.config;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.ncibi.commons.io.FileUtilities;

/**
 * This class is a cover over the Properties class that hides details of loading
 * the properties file. It also extends the configuration to allow for setting
 * the configuration from a HashMap (useful for temporary or non persistent
 * property settings).
 * 
 * @author V. Glenn Tarcea
 * 
 */
public class Configuration
{
    /**
     * The Properties object to query against.
     */
    private final Properties configurationProperties;

    /**
     * Constructor that accepts a properties file name and then attempts to load
     * it. It uses the class loader to find the properties file.
     * 
     * @param propertiesFilename
     *            The name of the properties file, for example
     *            "myapp.properties"
     * @throws InvalidConfigurationException
     *             When the properties file could not be found or loaded.
     */
    public Configuration(final String propertiesFilename) throws InvalidConfigurationException
    {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        final InputStream in = classLoader.getResourceAsStream(propertiesFilename);
        configurationProperties = new Properties();
        try
        {
            configurationProperties.load(in);
            in.close();
        }
        catch (IOException e)
        {
            //e.printStackTrace();
            throw new InvalidConfigurationException("Unable to load configuration file:"
                    + propertiesFilename);
        }
        catch (Throwable e)
        {
            //e.printStackTrace();
            throw new InvalidConfigurationException("Error reading configuration file:"
                    + propertiesFilename);
        }
    }
    
    public Configuration(final InputStream in) throws InvalidConfigurationException
    {
        configurationProperties = new Properties();
        try
        {
            configurationProperties.load(in);
        }
        catch (Throwable e)
        {
            //e.printStackTrace();
            throw new InvalidConfigurationException("Error reading configuration stream.");
        }   
    }
    
    public static Configuration newConfigurationUsingPath(final String fullPath) throws InvalidConfigurationException
    {
        try
        {
            final InputStream in = FileUtilities.openAsInputStream(fullPath);
            return new Configuration(in);
        }
        catch (IOException e)
        {
            throw new InvalidConfigurationException("Unable to load configuration file: " + fullPath);
        }
    }
    
    /**
     * Checks to see if a particular configuration file exists.
     * @param propertiesFilename The name of the configuration file.
     * @return True if it exists. False otherwise.
     */
    public static boolean configFileExists(final String propertiesFilename)
    {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL resource = classLoader.getResource(propertiesFilename);
        if (resource == null)
        {
            return false;
        }
        
        return true;
    }

    /**
     * Constructor that accepts a HashMap containing of strings. Initializes the
     * properties from the hashmap.
     * 
     * @param propertiesList
     *            HashMap contain property settings as key value pairs.
     */
    public Configuration(final Map<String, String> propertiesList)
    {
        configurationProperties = new Properties();
        configurationProperties.putAll(propertiesList);
    }

    /**
     * Gets the value for a property.
     * 
     * @param property
     *            The name of the property.
     * @return The value of the property if found, or null.
     */
    public String getProperty(final String property)
    {
        return configurationProperties.getProperty(property);
    }
    
    public String getDefaultedProperty(final String property, final String defaultValue)
    {
        String propertyValue = getProperty(property);
        if (propertyValue == null)
        {
            return defaultValue;
        }
        return propertyValue;
    }
    
    /**
     * Create a map of all the properties that match a given prefix with the prefix removed.
     * For example for the prefix �test� if there is a property �test.yellow.leaves� with a value �oak�
     * then the resulting map will contain a the value �oak� for the key �yellow.leaves�. A null or
     * blank prefix will result in a mapping of all of the properties of this Configuration.
     * 
     * @param prefix the prefix String
     * @return the map of the matching properties 
     */
    public Map<String,String> mapPrefix(String prefix){
    	Map<String,String> map = new HashMap<String,String>();
    	String prefixDot = "";
    	if ((prefix != null) && (prefix.length() > 0)) prefixDot = prefix + ".";
    	for (Object key: configurationProperties.keySet()) {
    		if (key instanceof String) {
    			String sKey = (String)key;
    			if (sKey.startsWith(prefixDot)) {
    				String newKey = sKey.substring(prefixDot.length());
    				map.put(newKey,getProperty(sKey));
    			}
    		}
    	}
    	return map;
    }

}
