package org.ncibi.commons.lang;

import org.ncibi.commons.exception.ConstructorCalledError;

/**
 * Utilities methods on Classes.
 * 
 * @author gtarcea
 * 
 */
public final class ClassUtils
{
    /**
     * Utility class, make constructor private.
     */
    private ClassUtils()
    {
        throw new ConstructorCalledError(this.getClass());
    }
    
    /**
     * Emulates the C# as operator. Casts a class to a different class, if the
     * cast is invalid then returns null rather than throwing an exception.
     * 
     * @param <T>
     *            Generic type parameter for class.
     * @param cls
     *            The class type to cast to.
     * @param o
     *            The object to cast.
     * @return Object casted as T, or null if the cast is invalid.
     */
    public static <T> T as(final Class<T> cls, final Object o)
    {
        if (cls.isInstance(o))
        {
            return cls.cast(o);
        }
        return null;
    }
}