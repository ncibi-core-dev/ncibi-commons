package org.ncibi.commons.io;

import java.io.IOException;

public abstract class FileStreamProcessor<S>
{
    public abstract S openFileStream(String filename) throws IOException;
    public abstract void processFileStream(S stream);
    public abstract void closeFileStream(S stream) throws IOException;
    
    public void process(final String filename)
    {
        S stream = null;
        try
        {
            stream = openFileStream(filename);
            processFileStream(stream);
        }
        catch (IOException e)
        {
            // Ignore
        }
        finally
        {
            if (stream != null)
            {
                try
                {
                    closeFileStream(stream);
                }
                catch (IOException e)
                {
                    // Ignore.
                }
            }
        }
    }
}
