package org.ncibi.commons.exception;

public class LoadException extends RuntimeException
{

    /**
     * Required for serialization.
     */
    private static final long serialVersionUID = -2633205537970163636L;

    /**
     * Default Constructor.
     */
    public LoadException()
    {
        super();
    }

    /**
     * Constructor that takes message and the exception as inputs.
     * 
     * @param message
     *            The message for the exception.
     * @param e
     *            The previous exception to include in this exception.
     */
    public LoadException(final String message, final Exception e)
    {
        super(message, e);
    }

    /**
     * Constructor that takes message as input.
     * 
     * @param message
     *            The message for the exception.
     */
    public LoadException(final String message)
    {
        super(message);
    }
}
