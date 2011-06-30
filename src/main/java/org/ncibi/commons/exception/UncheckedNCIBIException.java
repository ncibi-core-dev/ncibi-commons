package org.ncibi.commons.exception;

/**
 * Default Unchecked Exception for NCIBI.
 * 
 * @author gtarcea
 * 
 */
public class UncheckedNCIBIException extends RuntimeException
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Default Constructor.
     */
    public UncheckedNCIBIException()
    {
        super() ;
    }

    /**
     * Constructor that takes message and the exception as inputs.
     * 
     * @param message
     *            The message for the exception.
     * @param e
     *            The previous exception to include in this exception.
     */
    public UncheckedNCIBIException(final String message, final Exception e)
    {
        super(message, e);
    }

    /**
     * Constructor that takes message as input.
     * 
     * @param message
     *            The message for the exception.
     */
    public UncheckedNCIBIException(final String message)
    {
        super(message);
    }

}
