package org.ncibi.commons.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * This class wraps input stream processing shielding users from the need to
 * deal with all the details of opening and closing streams and getting the
 * exception handling right.
 * 
 * This template is based off of code written by Jakob Jenkov and can be seen in
 * his tutorial at:
 * http://tutorials.jenkov.com/java-exception-handling/exception
 * -handling-templates.html
 * 
 * @author gtarcea
 * 
 */
public abstract class FileInputProcessorWIP
{

    public abstract void processInput(InputStream input) throws IOException;
    
    private final InputStreamProcessor inputStreamProcessor = new InputStreamProcessor()
    {
        @Override
        public void doProcess(InputStream input) throws IOException
        {
            processInput(input);
        }       
    };

    /**
     * Opens a file, handles exceptions and calls the abstract doProcess()
     * method to process the stream.
     * 
     * @param filename
     *            The file to open.
     */
    public void process(final String filename)
    {
        try
        {
            InputStream input = new FileInputStream(filename);
            this.inputStreamProcessor.process(input);
        }
        catch (FileNotFoundException e)
        {
            // Do nothing
        }
    }
}
