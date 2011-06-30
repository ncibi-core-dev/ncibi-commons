package org.ncibi.commons.config;

import static org.junit.Assert.*;

import org.junit.Test;
import org.ncibi.commons.config.Configuration;
import org.ncibi.commons.config.InvalidConfigurationException;
import org.ncibi.commons.config.SearchConfig;

import java.io.File;
import java.util.HashMap;

public class SearchConfigTest
{
    /*
     * Tests rely on the path being /usr/search/Lucene
     */
    private boolean testPath(String path)
    {
        if (File.separator.equals("/"))
        {
            return path.equals("/usr/search/Lucene");
        }
        else
        {
            return path.equals("\\usr\\search\\Lucene");
        }
    }

    /**
     * Test using catalina.home.
     * 
     * @throws InvalidConfigurationException
     */
    @Test
    public final void testGetSearchIndexDirUsingCatalinaHome() throws InvalidConfigurationException
    {
        HashMap<String, String> searchProperties = new HashMap<String, String>();
        searchProperties.put("search.use.catalina.home", "true");
        searchProperties.put("catalina.home", "/usr/search");
        searchProperties.put("search.dir", "Lucene");
        Configuration config = new Configuration(searchProperties);
        //SearchConfig searchConfig = new SearchConfig(config);
        //String path = searchConfig.getSearchIndexDirectory();
        //assertTrue(testPath(path));
    }

    /**
     * Test using search.base first as a Resource, then as a directory.
     * 
     * @throws InvalidConfigurationException
     */
    @Test
    public final void testGetSearchIndexDirUsingSearchBase() throws InvalidConfigurationException
    {
        HashMap<String, String> searchProperties = new HashMap<String, String>();
        
        /*
         * Test by looking in the class path.
         */
        searchProperties.put("search.base", "RESOURCE");
        searchProperties.put("search.dir", "Lucene");
        Configuration config = new Configuration(searchProperties);
        SearchConfig searchConfig = new SearchConfig(config);
        
        String path = searchConfig.getSearchIndexDirectory();
        assertNotNull(path);

        /*
         * Test looking at a real path.
         */
        searchProperties.put("search.base", "/usr/search");
        config = new Configuration(searchProperties);
        searchConfig = new SearchConfig(config);
        path = searchConfig.getSearchIndexDirectory();
        assertTrue(testPath(path));

        /*
         * Test that InvalidConfigurationException is thrown on a bad search
         * path in the configuration.
         */
        boolean caughtException = false;
        searchProperties.put("search.base", "RESOURCE");
        searchProperties.put("search.dir", "does-not-exist");
        config = new Configuration(searchProperties);
        searchConfig = new SearchConfig(config);
        try
        {
            path = searchConfig.getSearchIndexDirectory();
        }
        catch (InvalidConfigurationException e)
        {
            caughtException = true;
        }
        
        assertTrue(caughtException);
    }
    
    /**
     * 
     * @throws InvalidConfigurationException
     */
    @Test
    public final void testUsingProjectSearchConfig() throws InvalidConfigurationException
    {
        SearchConfig searchConfig = SearchConfig.projectSearchConfig();
        String path = searchConfig.getSearchIndexDirectory();
        assertTrue(testPath(path));
    }

}
