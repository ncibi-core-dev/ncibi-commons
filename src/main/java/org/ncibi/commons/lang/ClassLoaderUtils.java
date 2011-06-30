package org.ncibi.commons.lang;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.ncibi.commons.exception.ConstructorCalledError;

public class ClassLoaderUtils
{
    /**
     * Utility class, make constructor private.
     */
    private ClassLoaderUtils()
    {
        throw new ConstructorCalledError(this.getClass());
    }
    
    /**
     * This routine works around a bug in the java getSystemResource().getFile() set of calls
     * where if the path for getSystemResource() contains spaces in it, then the file path
     * returned is invalid because the spaces are replaced with %20. This results in a no
     * such file being found error. Sun has refused to address this issue since at least Java 1.3.
     * Because it is impossible to predict whether or not there will be a path with a space in
     * it this routine was created to hide these details.
     *  
     * @param resource The name of the resource to find in the system resource paths.
     * @return The file path to the resource or null if it doesn't exist.
     * @throws IllegalArgumentException if the resource name is badly formed.
     */
    public static String getSystemResourceAsFilePath(final String resource)
    {
        URI uri = getSystemResourceAsUri(resource);
        
        return uri == null ? null : uri.getPath();
    }
    
    /**
     * See comment above for getSystemResourceAsFilePath(). Rather than URL we return a
     * URI to handle the issue with spaces in name.
     * 
     * @param resource The name of the resource to find in the system resource paths.
     * @return The file path to the resource or null if it doesn't exist.
     * @throws IllegalArgumentException if the resource name is badly formed.
     */
    public static URI getSystemResourceAsUri(final String resource)
    {
        URI uri = null;
        try
        {
            final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            URL url = classLoader.getResource(resource);
            if (url == null)
            {
                return null;
            }
            uri = new URI(url.getFile());
        }
        catch (URISyntaxException e)
        {
            throw new IllegalArgumentException("Resource name badly formed: '" + resource + "'", e);
        }
        
        return uri;
    }
}
