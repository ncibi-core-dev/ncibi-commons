package org.ncibi.commons.io.stream;

/**
 * A Checkable is a source or destination of data that can be queried to see if
 * it is open. The isOpen() method is invoked to test if the source or
 * destination is open.
 * 
 * @author gtarcea
 * 
 */
public interface Checkable
{
    /**
     * Checks if the stream is open or closed.
     * 
     * @return True if stream is open, otherwise it returns false.
     */
    public abstract boolean isOpen();
}
