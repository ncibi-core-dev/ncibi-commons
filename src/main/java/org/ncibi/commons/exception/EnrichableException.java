package org.ncibi.commons.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the EnrichableException class described by Jakob Jenkov
 * at http://tutorials.jenkov.com/java-exception-handling/exception-enrichment.
 * html
 * 
 * The class allows a user to enrich exceptions.
 * 
 * @author (Original) Jakob Jenkov
 * @author (Extended) V. Glenn Tarcea
 * 
 */
public class EnrichableException extends RuntimeException
{
    /**
     * Because the classes are serializable.
     */
    public static final long serialVersionUID = -1;

    /**
     * The enriched data.
     */
    protected List<InfoItem> infoItems = new ArrayList<InfoItem>();

    /**
     * Inner class to track exception data.
     * 
     * @author Jakob Jenkov and V. Glenn Tarcea
     * 
     */
    protected static class InfoItem
    {
        public String errorContext = null;
        public String errorCode = null;
        public String errorText = null;

        public InfoItem(final String contextCode, final String errorCode, final String errorText)
        {

            this.errorContext = contextCode;
            this.errorCode = errorCode;
            this.errorText = errorText;
        }
    }

    /**
     * Constructor.
     * 
     * @param errorContext
     *            The context for the exception.
     * @param errorCode
     *            A unique error code to help with identification in trace
     *            backs.
     * @param errorMessage
     *            The error message to associate.
     */
    public EnrichableException(final String errorContext, final String errorCode,
            final String errorMessage)
    {
        super(errorMessage) ;
        addInfo(errorContext, errorCode, errorMessage);
    }

    /**
     * Constructor.
     * 
     * @param errorContext
     *            The context for the exception.
     * @param errorCode
     *            A unique error code to help with identification in trace
     *            backs.
     * @param errorMessage
     *            The error message to associate.
     * @param cause
     *            The exception to wrap.
     */
    public EnrichableException(final String errorContext, final String errorCode,
            final String errorMessage, final Throwable cause)
    {
        super(cause);
        addInfo(errorContext, errorCode, errorMessage);
    }

    /**
     * Adds information about an exception.
     * 
     * @param errorContext
     *            The context for the exception.
     * @param errorCode
     *            A unique error code to help with identification in trace
     *            backs.
     * @param errorText
     *            The error message to associate.
     * @return this.
     */
    public final EnrichableException addInfo(final String errorContext, final String errorCode,
            final String errorText)
    {

        this.infoItems.add(new InfoItem(errorContext, errorCode, errorText));
        return this;
    }

    /**
     * Turns the enriched information into a human readable formatted string.
     * 
     * @return The string containing the enriched information.
     */
    public String getCode()
    {
        final StringBuilder builder = new StringBuilder();

        for (int i = this.infoItems.size() - 1; i >= 0; i--)
        {
            final InfoItem info = this.infoItems.get(i);
            builder.append('[');
            builder.append(info.errorContext);
            builder.append(':');
            builder.append(info.errorCode);
            builder.append(']');
        }

        return builder.toString();
    }

    /**
     * Turns the enriched information into a semi-formatted string.
     * 
     * @return Semi-formatted string of extension information.
     */
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();

        builder.append(getCode());
        builder.append('\n');

        // append additional context information.
        for (int i = this.infoItems.size() - 1; i >= 0; i--)
        {
            final InfoItem info = this.infoItems.get(i);
            builder.append('[');
            builder.append(info.errorContext);
            builder.append(':');
            builder.append(info.errorCode);
            builder.append(']');
            builder.append(info.errorText);
            if (i > 0)
            {
                builder.append('\n');
            }
        }

        // append root causes and text from this exception first.
        if (getMessage() != null)
        {
            builder.append('\n');
            if (getCause() == null)
            {
                builder.append(getMessage());
            }
            else if (!getMessage().equals(getCause().toString()))
            {
                builder.append(getMessage());
            }
        }
        appendException(builder, getCause());

        return builder.toString();
    }

    /**
     * Appends exception information.
     * 
     * @param builder
     *            The string builder to use.
     * @param throwable
     *            The exception being appended.
     */
    private void appendException(final StringBuilder builder, final Throwable throwable)
    {
        if (throwable == null)
        {
            return;
        }
        appendException(builder, throwable.getCause());
        builder.append(throwable.toString());
        builder.append('\n');
    }
}