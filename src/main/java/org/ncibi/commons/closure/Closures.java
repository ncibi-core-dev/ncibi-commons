package org.ncibi.commons.closure;

import org.ncibi.commons.exception.ConstructorCalledError;

public class Closures
{
    private Closures()
    {
        throw new ConstructorCalledError(this.getClass());
    }

    private static F1<Object, Object> IDENTITY_FUNCTION = new F1<Object, Object>()
                                                        {
                                                            public Object apply(Object arg)
                                                            {
                                                                return arg;
                                                            }
                                                        };

    @SuppressWarnings("unchecked")
    public static <T> F1<T, T> identityFunction()
    {
        return (F1<T, T>) IDENTITY_FUNCTION;
    }
}
