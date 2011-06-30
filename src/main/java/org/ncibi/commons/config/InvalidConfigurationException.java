package org.ncibi.commons.config;

/**
 * Invalid configurations throw this exception.
 * 
 * @author gtarcea
 * 
 *         TODO: Consider changing this to an Unchecked Exception. This would
 *         clean up the code and the use of classes that make use of this class.
 */
@SuppressWarnings("serial")
public class InvalidConfigurationException extends RuntimeException
{
    /**
     * Default constructor.
     */
    public InvalidConfigurationException()
    {
        super();
    }

    /**
     * Constructor accepting a message and exception.
     * 
     * @param message
     *            The message for the exception.
     * @param e
     *            The exception to include.
     */
    public InvalidConfigurationException(final String message, final Exception e)
    {
        super(message, e);
    }

    /**
     * Constructor accepting a exception message.
     * 
     * @param message
     *            The message for the exception.
     */
    public InvalidConfigurationException(final String message)
    {
        super(message);
    }
}
