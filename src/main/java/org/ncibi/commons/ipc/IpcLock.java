package org.ncibi.commons.ipc;

public interface IpcLock
{
    public boolean lock();
    public void unlock();
    public boolean isLocked();
    public boolean possesLock();
    public boolean waitAndTryToObtainLock();
    public void waitUntilLockReleased();
}
