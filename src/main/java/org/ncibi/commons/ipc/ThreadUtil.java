package org.ncibi.commons.ipc;

public class ThreadUtil
{
    private ThreadUtil()
    {
    }

    public static void waitSeconds(int seconds)
    {
        try
        {
            Thread.sleep(seconds * 1000);
        }
        catch (InterruptedException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
