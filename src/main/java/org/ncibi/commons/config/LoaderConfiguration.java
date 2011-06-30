package org.ncibi.commons.config;

public class LoaderConfiguration
{
    private Configuration loaderConfiguration = null;
    private final Configuration projectConfiguration;
    private final String loader;
    
    public LoaderConfiguration(String loader)
    {
        try
        {
            final String loaderPropertiesFilename = "loaders/" + loader + "/loader.properties";
            if (Configuration.configFileExists(loaderPropertiesFilename))
            {
                loaderConfiguration = new Configuration(loaderPropertiesFilename);
            }
            projectConfiguration = ProjectConfiguration.getProject().getConfiguration();
            this.loader = loader;
        }
        catch (InvalidConfigurationException e)
        {
            throw new IllegalStateException("No such loader: " + loader);
        }
    }
    
    public String getProperty(String property)
    {
        /*
         * The project configuration holds default values. If these values
         * are specified in a loader configuration then they override the
         * project configuration.
         */
        String projectValue = projectConfiguration.getProperty(property);
        String loaderValue = null;
        
        if (loaderConfiguration != null)
        {
            loaderValue = loaderConfiguration.getProperty(property);
        }
        
        if (loaderValue != null)
        {
            return loaderValue;
        }
        else
        {
            return projectValue;
        }
    }
    
    public String getLoader()
    {
        return loader;
    }
}
