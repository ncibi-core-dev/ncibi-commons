package org.ncibi.commons.config;

import java.io.File;

import org.ncibi.commons.lang.ClassLoaderUtils;

/**
 * This class contains routine(s) for search configuration such as configuring
 * the directory where the search index can be found. The following properties
 * are used by this class:
 * <p>
 * search.dir : This is the directory to use for the search index directory. It
 * can be a full directory path, or alternatively the full path to this
 * directory can be controlled by using the property settings documented below.
 * <p>
 * search.use.catalina.home : If this property is set then it overrides
 * sesarch.base. The index directory is searched for using the catalina.home
 * variable as the base.
 * <p>
 * search.base : If this property exists then it is the base directory to append
 * to search.dir (if it doesn't exist then "/" is appended). This property is
 * only used if search.use.catalina.home is not set. One useful setting for this
 * property is RESOURCE. If search.base is set to RESOURCE then the class path
 * is searched for the search.dir entry.
 * <p>
 * catalina.home : This is an optional property. If it is set, then if the
 * System.getProperty for catalina.home fails, the class will use the
 * catalina.home setting found in the configuration file. This allows the class
 * to be used outside of a tomcat container.
 * 
 * @author V. Glenn Tarcea
 * 
 */
public final class SearchConfig
{
    private final Configuration config;

    /**
     * Singleton class for projectConfigSearch factory.
     * 
     * @author gtarcea
     * 
     */
    private static class SearchConfigLoaderForProject
    {
        /**
         * Singleton instance of SearchConfig when configured using the project
         * configuration.
         */
        private static final SearchConfig INSTANCE = new SearchConfig(ProjectConfiguration
                .getProject().getConfiguration());
    }

    /**
     * Constructor.
     */
    public SearchConfig(final Configuration config)
    {
        this.config = config;
    }

    /**
     * Static factory method to retrieve the search configuration for the
     * project.
     * 
     * @return The project search config.
     */
    @Deprecated
    public static SearchConfig projectSearchConfig()
    {
        return SearchConfigLoaderForProject.INSTANCE;
    }
    
    /**
     * Get singleton instance.
     * @return
     */
    public static SearchConfig getInstance()
    {
        return SearchConfigLoaderForProject.INSTANCE;
    }

    /**
     * Checks to see if we should use catalina.home as the base for constructing
     * the directory path to the search directory.
     * 
     * @return True if catalina.home should be used, otherwise returns false.
     */
    private boolean useCatalinaHome()
    {
        final String value = config.getProperty("search.use.catalina.home");

        if (value == null)
        {
            return false;
        }
        else if (value.compareToIgnoreCase("yes") == 0 || value.compareToIgnoreCase("true") == 0)
        {
            return true;
        }

        return false;
    }

    /**
     * Builds a full directory path.
     * 
     * @param path
     *            The path base.
     * @param luceneDir
     *            The Lucene directory.
     * @return The directory path with the two paths appended together.
     */
    private static String buildDir(final String path, final String luceneDir)
    {
        return new File(path, luceneDir).toString();
    }

    /**
     * Retrieves the configuration parameter search.base. If the parameter
     * doesn't exist then it returns "/".
     * 
     * @return The property value for search.base or "/" if it doesn't exist.
     */
    private String getSearchBase()
    {
        final String searchBase = config.getProperty("search.base");
        return searchBase == null ? "/" : searchBase;
    }

    /**
     * Builds the search index directory path. Uses catalina.home to build the
     * path if the configuration property search.use.catalina.home is set to yes
     * or true.
     * <p>
     * If search.use.catalina.home is set, and the system property for
     * catalina.home does not exist then we look up catalina.home in our
     * Configuration object and use the setting if it exists.
     * <p>
     * If search.use.catalina.home is not set, then search.base can be set as
     * the base directory to use to look for the index directory. While this
     * path could be encoded into search.dir, search.base is useful when you
     * want to search the class path for the directory. If search.base is set to
     * RESOURCE then the class path will be searched for this resources.
     * 
     * @return The directory path to the search indices, or null if the
     *         configuration is invalid. (Should we throw an exception instead?)
     * 
     * @throws InvalidConfigurationException
     *             Throws exception when no search directory is configured.
     */
    public String getSearchIndexDirectory() throws InvalidConfigurationException
    {
        final String searchIndexDir;
        final String searchDir = config.getProperty("search.dir");

        if (searchDir == null)
        {
            throw new InvalidConfigurationException("No search.dir configured.");
        }

        if (useCatalinaHome())
        {
            /*
             * If catalina.home does not exist in the System properties then
             * look for it in our local properties.
             */
            String catalinaHome = System.getProperty("catalina.home");
            if (catalinaHome == null)
            {
                catalinaHome = config.getProperty("catalina.home");
            }

            searchIndexDir = buildDir(catalinaHome, searchDir);
        }
        else
        {
            final String searchBase = getSearchBase();
            if ("RESOURCE".compareToIgnoreCase(searchBase) == 0)
            {
                searchIndexDir = ClassLoaderUtils.getSystemResourceAsFilePath(searchDir);
                if (searchIndexDir == null)
                {
                    throw new InvalidConfigurationException(
                            "Could not find search.dir in Resource path");
                }
            }
            else
            {
                searchIndexDir = buildDir(searchBase, searchDir);
            }
        }

        return searchIndexDir;
    }
}
