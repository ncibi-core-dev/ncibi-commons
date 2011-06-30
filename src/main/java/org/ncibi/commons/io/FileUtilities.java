package org.ncibi.commons.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import org.ncibi.commons.config.ProjectConfiguration;
import org.ncibi.commons.exception.ConstructorCalledError;
import org.ncibi.commons.lang.ClassLoaderUtils;

/**
 * Contains file utilities for easier manipulation of java files.
 * 
 * @author gtarcea
 * 
 */
public final class FileUtilities
{
    /**
     * Constructor. Private because this is a utilities class with all static
     * methods.
     */
    private FileUtilities()
    {
        throw new ConstructorCalledError(this.getClass());
    }

    /**
     * Checks if a file exists.
     * 
     * @param filepath
     *            The path to check.
     * @return True if file exists, false otherwise.
     */
    public static boolean fileExists(final String filepath)
    {
        return (new File(filepath)).exists();
    }

    public static boolean deleteFile(final String filepath)
    {
        File f = new File(filepath);

        if (!f.exists())
        {
            return true;
        }

        if (!f.canWrite() || f.isDirectory())
        {
            return false;
        }

        return f.delete();
    }

    /**
     * Check if a file exists.
     * 
     * @param directory
     *            The directory to check in.
     * @param filename
     *            The filename to check.
     * @return True if file exists, false otherwise.
     */
    public static boolean fileExists(final String directory, final String filename)
    {
        return (new File(directory, filename)).exists();
    }

    /**
     * Returns the full path to the file as a system resource.
     * 
     * @param filename
     *            The filename to create a path to.
     * @return The full path for the file (including the file) appending the
     *         SystemResource path.
     * @throws IllegalArgumentException
     *             When the filename cannot be found in the class path.
     */
    public static String getSystemPath(final String filename)
    {
        final String path = ClassLoaderUtils.getSystemResourceAsFilePath(filename);
        if (path == null)
        {
            throw new IllegalArgumentException("File could not be found: " + filename);
        }
        return path;
    }
    
    public static InputStream openAsInputStream(String filePath) throws FileNotFoundException
    {
        return new FileInputStream(filePath);
    }

    public static String pathSeparator()
    {
        return System.getProperty("file.separator");
    }

    public static String convertStreamToString(InputStream is) throws IOException
    {
        /*
         * From: http://www.kodejava.org/examples/266.html
         * 
         * To convert the InputStream to String we use the Reader.read(char[]
         * buffer) method. We iterate until the Reader return -1 which means
         * there's no more data to read. We use the StringWriter class to
         * produce the string.
         */
        if (is != null)
        {
            Writer writer = new StringWriter();

            char[] buffer = new char[1024];
            try
            {
                Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1)
                {
                    writer.write(buffer, 0, n);
                }
            }
            finally
            {
                is.close();
            }
            return writer.toString();
        }
        else
        {
            return "";
        }
    }
    
    public static String tmpDir()
    {
        String tmpdir = ProjectConfiguration.getProjectProperty("tmp.dir");
        if (tmpdir == null)
        {
            tmpdir = System.getProperty("java.io.tmpdir");
        }
        return tmpdir;
    }
}
