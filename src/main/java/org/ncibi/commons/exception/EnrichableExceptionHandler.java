package org.ncibi.commons.exception;

/**
 * This interface is based off the tutorial by Jakob Jenkov where he discusses
 * pluggable exception handlers.
 * 
 * @author gtarcea
 * 
 */
public interface EnrichableExceptionHandler
{
    /**
     * 
     * @param contextCode
     * @param errorCode
     * @param errorText
     * @param t
     */
    public void handle(String contextCode, String errorCode, String errorText, Throwable t);

    /**
     * 
     * @param contextCode
     * @param errorCode
     * @param errorText
     */
    public void raise(String contextCode, String errorCode, String errorText);

}
