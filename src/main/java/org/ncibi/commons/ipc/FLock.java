package org.ncibi.commons.ipc;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

import org.ncibi.commons.io.FileUtilities;

public final class FLock implements IpcLock
{
    private static enum LockState
    {
        UNINITIALIZED, LOCKED_NOT_OBTAINED, LOCKED_OBTAINED
    };

    private final String lockNameWithPath;
    private FileLock lock;
    private LockState lockState = LockState.UNINITIALIZED;

    public FLock(String lockName)
    {
        this.lockNameWithPath = System.getProperty("java.io.tmpdir")
                    + FileUtilities.pathSeparator() + lockName;
    }

    public FLock(String path, String lockName)
    {
        this.lockNameWithPath = path + FileUtilities.pathSeparator() + lockName;
    }

    public boolean lock()
    {
        tryToObtainLockAndSetState();
        return this.lockState == LockState.LOCKED_OBTAINED;
    }

    private void tryToObtainLockAndSetState()
    {
        try
        {
            getLockAndSetState();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new IllegalStateException("Unable to obtain lock for: " + this.lockNameWithPath);
        }
    }

    private void getLockAndSetState() throws IOException
    {
        FileChannel channel = createChannel();
        lock = channel.tryLock();
        if (lock != null)
        {
            this.lockState = LockState.LOCKED_OBTAINED;
        }
        else
        {
            this.lockState = LockState.LOCKED_NOT_OBTAINED;
        }
    }

    private FileChannel createChannel() throws IOException
    {
        File file = new File(lockNameWithPath);
        file.createNewFile();
        FileChannel channel = new RandomAccessFile(file, "rw").getChannel();
        return channel;
    }

    public void unlock()
    {
        if (lockState == LockState.LOCKED_OBTAINED)
        {
            try
            {
                lock.release();
            }
            catch (IOException e)
            {
            }
        }
    }

    public boolean isLocked()
    {
        return lockState != LockState.UNINITIALIZED;
    }

    public boolean possesLock()
    {
        return lockState == LockState.LOCKED_OBTAINED;
    }

    public boolean waitAndTryToObtainLock()
    {
        waitUntilLockReleased();
        return lock();
    }

    public void waitUntilLockReleased()
    {
        try
        {
            blockOnLock();
        }
        catch (IOException e)
        {

        }

        unlock();
    }

    private void blockOnLock() throws IOException
    {
        FileChannel channel = createChannel();
        lock = channel.lock();
        this.lockState = LockState.LOCKED_OBTAINED;
    }
}
