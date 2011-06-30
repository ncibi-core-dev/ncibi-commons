package org.ncibi.commons.exception;

/**
 * An exception to be used when checking for pre conditions. This is useful to ensure
 * that certain conditions apply in a method.
 * @author gtarcea
 *
 */
public class PreConditionCheckFailedException extends RuntimeException
{  
    /**
     * Required for serialization.
     */
    private static final long serialVersionUID = 5397703662441483679L;

    /**
     * Default Constructor.
     */
    public PreConditionCheckFailedException()
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
    public PreConditionCheckFailedException(final String message, final Exception e)
    {
        super(message, e);
    }

    /**
     * Constructor that takes message as input.
     * 
     * @param message
     *            The message for the exception.
     */
    public PreConditionCheckFailedException(final String message)
    {
        super(message);
    }
}
