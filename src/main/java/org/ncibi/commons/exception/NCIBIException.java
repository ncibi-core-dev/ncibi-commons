package org.ncibi.commons.exception;

/**
 * This is the default exception for NCIBI. It should be used very sparingly.
 * You should instead create and use existing exceptions or create new ones that
 * better describe the issue.
 * 
 * @author V. Glenn Tarcea
 * 
 */
@SuppressWarnings("serial")
public class NCIBIException extends Exception
{
    /**
     * Default Constructor.
     */
    public NCIBIException()
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
    public NCIBIException(final String message, final Exception e)
    {
        super(message, e);
    }

    /**
     * Constructor that takes message as input.
     * 
     * @param message
     *            The message for the exception.
     */
    public NCIBIException(final String message)
    {
        super(message);
    }

    /**
     * Returns the known, i.e., not-null, root cause of this exception.
     * 
     * @return The root cause.
     */
    public Throwable getKnownRootCause()
    {
        Throwable root = this.getCause();

        if (root == null)
        {
            return this;
        }

        while ((root != null) && (root.getCause() != null))
        {
            root = root.getCause();
        }

        return root;
    }
}
