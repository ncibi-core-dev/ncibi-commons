package org.ncibi.commons.io;

import java.io.IOException;

import org.ncibi.commons.exception.ExceptionHandler;

/**
 * Template class that processes a file a line at a time counting the line being
 * processed as it goes. If headers are skipped then line counting starts after
 * the headers.
 * 
 * @author gtarcea
 * 
 */
public abstract class CountedFileInputLineProcessor extends FileInputLineProcessor
{
    /**
     * lineNumber counter.
     */
    private int lineNumber = 1;

    /**
     * Processes a single line passing in the line and the line count.
     * 
     * @param line
     *            Line to process.
     * @param lineCount
     *            line number.
     */
    public abstract void processLineNumber(String line, int lineNumber);

    /**
     * Constructor.
     */
    public CountedFileInputLineProcessor()
    {
        super();
    }

    /**
     * Constructor with extended exception handler.
     * 
     * @param exceptionHandler
     *            The extended exception handler.
     */
    public CountedFileInputLineProcessor(final ExceptionHandler exceptionHandler)
    {
        super(exceptionHandler);
    }

    /**
     * Overrides the InputLineStreamProcessor abstract function to provide for
     * counted line processing.
     */
    @Override
    public void processLine(final String line) throws IOException
    {
        lineNumber++;
        processLineNumber(line, lineNumber);
    }
}
