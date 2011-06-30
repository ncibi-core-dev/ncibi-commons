package org.ncibi.commons.exception;

/**
 * An exception to be used when checking for post conditions. This is useful to maintain
 * invariants.
 * @author gtarcea
 *
 */
public class PostConditionCheckFailedException extends RuntimeException
{
    /**
     * Required for serialization.
     */
    private static final long serialVersionUID = 2685507954556588637L;

    /**
     * Default Constructor.
     */
    public PostConditionCheckFailedException()
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
    public PostConditionCheckFailedException(final String message, final Exception e)
    {
        super(message, e);
    }

    /**
     * Constructor that takes message as input.
     * 
     * @param message
     *            The message for the exception.
     */
    public PostConditionCheckFailedException(final String message)
    {
        super(message);
    }
}
