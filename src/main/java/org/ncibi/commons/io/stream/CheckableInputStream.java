package org.ncibi.commons.io.stream;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.input.AutoCloseInputStream;
import org.apache.commons.io.input.ProxyInputStream;

/**
 * Implements a checkable input stream. This class allows InputStreams to be
 * checked if they are open. Internally the class tracks whether a stream is
 * open by associating a flag (isOpen) with a stream. The isOpen flag is set to
 * true in the constructor and assumes that the stream is open at that time. The
 * flag is set to false when close() is called. Regardless of whether the
 * close() method succeeds or fails, the flag will be set to false.
 * 
 * The class makes a protected member setIsOpen() available for subclasses to
 * access to explicitly change the state of the isOpen flag.
 * 
 * @author gtarcea
 * 
 */
public class CheckableInputStream extends ProxyInputStream implements Checkable
{
    /**
     * Tracks the open status of the stream.
     */
    private boolean isOpen;

    /**
     * Constructor. Sets the stream open status to true. The class won't work as
     * expected if the inputStream parameter is an Apache Commons
     * AutoCloseInputStream. If that is the case, then the static factory method
     * newAutoCloseInputStream should be used.
     * 
     * @param inputStream
     */
    public CheckableInputStream(final InputStream inputStream)
    {
        super(inputStream);
        isOpen = true;
    }

    /**
     * This class is used for proxying the Apache AutoCloseInputStream. This
     * stream calls close indirectly when a read() method returns -1 indicating
     * that end of file has been reached. Because of the inheritance structure,
     * the close() is not directly called by AutoCloseInputStream in
     * CheckableInputStream. So, we have to proxy it by extending the Apache
     * class, and then passing it to a CheckableInputStream. If the user passes
     * an AutoCloseInputStream directly to the CheckableInputStream constructor
     * it won't work as expected.
     * 
     * @author gtarcea
     * 
     */
    private static class CheckableAutoCloseInputStream extends AutoCloseInputStream
    {
        /**
         * is the stream open?
         */
        private boolean isOpen;

        /**
         * Constructor. Sets the isOpen flag to true.
         * 
         * @param is
         *            The stream to auto close.
         */
        CheckableAutoCloseInputStream(final InputStream is)
        {
            super(is);
            isOpen = true;
        }

        /**
         * Closes the stream and sets the isOpen flag to false.
         */
        @Override
        public void close() throws IOException
        {
            isOpen = false;
            super.close();
        }
    }

    /**
     * Class used to proxy Apache commons AutoCloseInputStream, works in
     * conjunction with the CheckableAutoCloseInputStream. This is a proxy for
     * the inheritance hierarchy so that we can pass back a class that is a
     * CheckableInputStream class.
     * 
     * @author gtarcea
     * 
     */
    private static class CheckableProxyClass extends CheckableInputStream
    {
        /**
         * The AutoClose class we are proxying to.
         */
        private final CheckableAutoCloseInputStream is;

        /**
         * Constructor. Sets isOpen flag to true.
         * 
         * @param inputStream
         *            The auto close stream.
         */
        public CheckableProxyClass(final CheckableAutoCloseInputStream inputStream)
        {
            super(inputStream);
            is = inputStream;
            super.setIsOpen(is.isOpen);
        }

        /**
         * Retrieve the isOpen flag from the CheckableAutoCloseInputStream and
         * takes care of setting the super state..
         */
        @Override
        public boolean isOpen()
        {
            super.setIsOpen(is.isOpen);
            return super.isOpen();
        }

        // TODO: Do we need to override setIsOpen()?
        // TODO: Do we need to override close()?
    }

    /**
     * Static factory method that should be used when you want the stream to
     * auto close using commons io AutoCloseInputStream. The inputStream passed
     * in cannot be an instance of AutoCloseInputStream.
     * 
     * TODO: Should we throw an exception if InputStream is an instanceof
     * AutoCloseInputStream?
     * 
     * @param is
     *            The inputstream to auto close.
     * @return A CheckableInputStream that auto closes.
     */
    public static CheckableInputStream newAutoCloseInputStream(final InputStream is)
    {
        final CheckableAutoCloseInputStream cis = new CheckableAutoCloseInputStream(is);
        return new CheckableProxyClass(cis);
    }

    /**
     * Closes the stream and sets its open status to false. Even if an exception
     * is thrown the open status of the stream will be set to false.
     * 
     * @throws IOException
     *             When an exception is thrown by the underlying stream.
     */
    @Override
    public void close() throws IOException
    {
        try
        {
            super.close();
        }
        finally
        {
            isOpen = false;
        }
    }

    /**
     * Returns the streams open status.
     * 
     * @return True if stream is open, false otherwise.
     */
    public boolean isOpen()
    {
        return isOpen;
    }

    /**
     * Allows a class that extends CheckableInputStream to have direct control
     * over the isOpen status. Sets isOpen to the value specified.
     * 
     * @param value
     *            The new setting for isOpen
     */
    protected final void setIsOpen(final boolean value)
    {
        isOpen = value;
    }
}
