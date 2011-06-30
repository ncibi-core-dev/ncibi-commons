package org.ncibi.commons.reflection;

import org.ncibi.commons.exception.ConstructorCalledError;

import com.google.common.collect.ImmutableMap;

public final class ReflectionUtils
{
    /**
     * Utility class - no constructor.
     */
    private ReflectionUtils()
    {
        throw new ConstructorCalledError(this.getClass());
    }
    
    public static final ImmutableMap<Class<?>, Class<?>> PRIMITIVE_TYPES_MAP = new ImmutableMap.Builder<Class<?>, Class<?>>()
                                                                                     .put(
                                                                                             int.class,
                                                                                             Integer.class)
                                                                                     .put(
                                                                                             long.class,
                                                                                             Long.class)
                                                                                     .put(
                                                                                             double.class,
                                                                                             Double.class)
                                                                                     .put(
                                                                                             float.class,
                                                                                             Float.class)
                                                                                     .put(
                                                                                             boolean.class,
                                                                                             Boolean.class)
                                                                                     .put(
                                                                                             char.class,
                                                                                             Character.class)
                                                                                     .put(
                                                                                             byte.class,
                                                                                             Byte.class)
                                                                                     .put(
                                                                                             void.class,
                                                                                             Void.class)
                                                                                     .put(
                                                                                             short.class,
                                                                                             Short.class)
                                                                                     .build();

    public static final Class<?> mapClass(Class<?> cls)
    {
        if (cls.isPrimitive())
        {
            System.out.println("isPrimitive " + cls);
            System.out.println("  maps to " + PRIMITIVE_TYPES_MAP.get(cls));
            System.out.println(" Integer.class = " + Integer.class);
            return PRIMITIVE_TYPES_MAP.get(cls);
        }
        
        return cls;
    }
}
