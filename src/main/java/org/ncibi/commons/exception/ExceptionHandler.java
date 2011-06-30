package org.ncibi.commons.exception;

public interface ExceptionHandler
{
    public void handle(Throwable t, String message) ;
    
    public void raise(String message) ;
}
