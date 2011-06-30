package org.ncibi.commons.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.ncibi.commons.exception.ExceptionHandler;

/**
 * Template class that processes a file a line at a time. This class hides the
 * details of open/closing the stream, correctly handling exceptions, etc...
 * 
 * @author gtarcea
 * 
 */
public abstract class FileInputLineProcessor
{
    /**
     * Abstract method that is given each line of the file to process.
     * 
     * @param line
     *            The line from the file to process.
     * @throws IOException
     *             When an unrecoverable error occurs during line processing.
     */
    public abstract void processLine(String line) throws IOException;

    /**
     * The template InputStreamProcessor class that does the actual IO and
     * exception handling.
     */
    private final FileInputProcessor inputStreamProcessor;

    /**
     * Should header lines be skipped?
     */
    private boolean skipHeader = false;

    /**
     * How many lines are there in the header?
     */
    private int headerLineCount = 1;

    /**
     * Constructor that sets up processing without an exception handler.
     */
    public FileInputLineProcessor()
    {
        inputStreamProcessor = new FileInputProcessor()
        {
            public void doProcess(final InputStream input) throws IOException
            {
                processInputStream(input);
            }
        };
    }

    /**
     * Constructor that accepts an exception handler to handle exceptions during
     * processing.
     * 
     * @param exceptionHandler
     *            The exception handler to call when an exception is raised.
     */
    public FileInputLineProcessor(final ExceptionHandler exceptionHandler)
    {
        inputStreamProcessor = new FileInputProcessor(exceptionHandler)
        {
            public void doProcess(final InputStream input) throws IOException
            {
                processInputStream(input);
            }
        };
    }

    /**
     * Processes a file a line at a time.
     * 
     * @param filename
     *            The file to process.
     */
    public void process(final String filename)
    {
        inputStreamProcessor.process(filename);
    }

    /**
     * Sets the skipHeader property. If the property is true then the input file
     * has headers that should be skipped over.
     * 
     * @param skip
     *            If true then skip the header lines.
     */
    public void setSkipHeader(final boolean skip)
    {
        skipHeader = skip;
    }

    /**
     * Sets the count of header lines. The default is 1.
     * 
     * @param count
     *            The number of headers lines in the file.
     */
    public void setHeaderLineCount(final int count)
    {
        this.headerLineCount = count;
    }

    /**
     * Private method that is used in doProcess() abstract method for
     * InputLineProcessor that handles reading each line from the file and
     * calling the processLine() abstract method in this class.
     * 
     * @param input
     *            The input stream to process.
     * @throws IOException
     *             When an unrecoverable error occurs.
     */
    private void processInputStream(final InputStream input) throws IOException
    {
        final BufferedReader in = new BufferedReader(new InputStreamReader(input));
        String line;

        if (skipHeader)
        {
            for (int i = 0; i < headerLineCount; i++)
            {
                in.readLine(); // throw away header lines.
            }
        }

        while ((line = in.readLine()) != null)
        {
            processLine(line);
        }
    }
}
