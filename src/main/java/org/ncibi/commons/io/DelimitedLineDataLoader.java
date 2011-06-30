package org.ncibi.commons.io;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Loads a file containing delimited lines that can be parsed and put into a
 * list of data items.
 * 
 * @author V. Glenn Tarcea
 * 
 * @param <T>
 *            The type of data items to load.
 */
public abstract class DelimitedLineDataLoader<T>
{
    /**
     * The list of (converted) data items loaded from the data file.
     */
    private final List<T> dataItems = new ArrayList<T>();

    /**
     * The filepath to load.
     */
    private final String filepath;

    /**
     * Should headers be skipped?
     */
    private boolean skipHeader = true;

    /**
     * The number of lines that are header lines in the file.
     */
    private int headerLineCount = 1;

    /**
     * Abstract function that parses a line turning into a object of type T. It
     * should return null for bad lines.
     * 
     * @param line
     *            The line to parse.
     * @return The item to add to the list of data items, or null on error or to
     *         ignore that line.
     */
    protected abstract T parseLine(String line);

    /**
     * Constructor that accepts the path to the file to load.
     * 
     * @param filepath
     */
    public DelimitedLineDataLoader(final String filepath)
    {
        this.filepath = filepath;
    }

    /**
     * Loads the data from the file passed into the constructor.
     */
    public final void loadData()
    {
        final FileInputLineProcessor dataItemLineProcessor = new FileInputLineProcessor()
        {
            @Override
            public void processLine(final String line) throws IOException
            {
                T item = parseLine(line);
                if (item != null)
                {
                    dataItems.add(item);
                }
            }
        };

        dataItemLineProcessor.setSkipHeader(skipHeader);
        dataItemLineProcessor.setHeaderLineCount(headerLineCount);

        dataItemLineProcessor.process(filepath);
    }

    /**
     * Returns the list of type T data items loaded.
     * 
     * @return The list of data items.
     */
    public final List<T> getDataItems()
    {
        return dataItems;
    }

    /**
     * Sets whether or not header lines should be skipped.
     * 
     * @param skipHeader
     *            True if headers should be skipped.
     * @return this
     */
    public final DelimitedLineDataLoader<T> setSkipHeader(final boolean skipHeader)
    {
        this.skipHeader = skipHeader;
        return this;
    }

    /**
     * Sets the number of header lines (to skip).
     * 
     * @param lineCount
     *            The number of headers lines in the data file.
     * @return this
     */
    public final DelimitedLineDataLoader<T> setHeaderLineCount(final int lineCount)
    {
        this.headerLineCount = lineCount;
        return this;
    }
}
