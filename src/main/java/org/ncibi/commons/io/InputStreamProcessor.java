package org.ncibi.commons.io;

import java.io.IOException;
import java.io.InputStream;

public abstract class InputStreamProcessor
{
    public abstract void doProcess(InputStream input) throws IOException;
    
    public void process(InputStream stream)
    {
        try
        {
            doProcess(stream);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                stream.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
