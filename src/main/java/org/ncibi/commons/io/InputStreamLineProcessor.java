package org.ncibi.commons.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public abstract class InputStreamLineProcessor
{
    public abstract void processLine(String line) throws IOException;
    
    private final InputStreamProcessor inputStreamProcessor;
    private boolean skipHeader = false;
    private int headerLineCount = 1;
    
    public InputStreamLineProcessor()
    {
        inputStreamProcessor = new InputStreamProcessor()
        {
            public void doProcess(final InputStream input) throws IOException
            {
                processInputStream(input);
            }
        };
    }
    
    public void setSkipHeader(final boolean skip)
    {
        skipHeader = skip;
    }
    
    public void setHeaderLineCount(final int count)
    {
        this.headerLineCount = count;
    }
    
    private void processInputStream(final InputStream input) throws IOException
    {
        BufferedReader in = new BufferedReader(new InputStreamReader(input));
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
    
    public void process(final InputStream input)
    {
        inputStreamProcessor.process(input);
    }
}
