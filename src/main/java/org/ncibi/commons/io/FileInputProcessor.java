package org.ncibi.commons.io;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.ncibi.commons.exception.ExceptionHandler;

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
public abstract class FileInputProcessor
{

    /**
     * Abstract method that handles processing the stream.
     * 
     * @param input
     *            The input stream to process.
     * @throws IOException
     *             When an unrecoverable error occurs.
     */
    public abstract void doProcess(InputStream input) throws IOException;

    /**
     * The exception handler to call on exception. If null this is ignored.
     */
    private final ExceptionHandler exceptionHandler;

    /**
     * Constructor accepting an exception handler that will be called when an
     * exception is raised during processing or when open or closing the stream.
     * 
     * @param exceptionHandler
     */
    public FileInputProcessor(final ExceptionHandler exceptionHandler)
    {
        this.exceptionHandler = exceptionHandler;
    }

    /**
     * Default constructor.
     */
    public FileInputProcessor()
    {
        exceptionHandler = null;
    }

    /**
     * Private method that centralizes checking for the exception handler. Will
     * slightly mess up stack traces.
     * 
     * @param t
     *            The exception that was raised.
     * @param message
     *            The message to associate with the exception.
     */
    private void handleException(final Throwable t, final String message)
    {
        if (exceptionHandler != null)
        {
            exceptionHandler.handle(t, message);
        }
        else
        {
            throw new RuntimeException(message, t);
        }
    }

    /**
     * Opens a file, handles exceptions and calls the abstract doProcess()
     * method to process the stream.
     * 
     * @param filename
     *            The file to open.
     */
    public void process(final String filename)
    {
        IOException processException = null;
        InputStream input = null;
        try
        {
            input = new FileInputStream(filename);
            doProcess(input);
        }
        catch (IOException e)
        {
            processException = e;
        }
        finally
        {
            if (input != null)
            {
                try
                {
                    input.close();
                }
                catch (IOException e)
                {
                    if (processException != null)
                    {
                        handleException(processException, "Error closing stream for file: "
                                + filename + " when doProcess() threw an exception");
                    }
                    else
                    {
                        handleException(e, "Error closing stream. File: " + filename);
                    }
                }
            }
            if (processException != null)
            {
                handleException(processException, "Error processing stream. File:" + filename);
            }
        }
    }
}
