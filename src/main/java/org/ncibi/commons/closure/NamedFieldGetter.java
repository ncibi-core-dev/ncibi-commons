package org.ncibi.commons.closure;

import java.lang.reflect.Field;

/**
 * A class that uses reflection to discover the value of a field. The field
 * names are passed in as strings (named) and the value for that field is
 * returned.
 * 
 * @author V. Glenn Tarcea
 * 
 * @param <ReturnType>
 *            The type of the field in question.
 * @param <ObjectType>
 *            The type of object to get the field from.
 */
public class NamedFieldGetter<ReturnType, ObjectType> implements
        FieldGetter<ReturnType, ObjectType>
{
    /**
     * The field we are interested in extracting.
     */
    private final String fieldName;

    /**
     * Constructor that takes the name of the field to extract.
     * 
     * @param field
     *            The name of the field to extract from the object.
     */
    public NamedFieldGetter(final String field)
    {
        fieldName = field;
    }

    /**
     * Given an object returns the value of the named field. If the field can't
     * be found or their is some other issue on access the method currently
     * returns null. TODO: Should we throw an exception instead?
     * 
     * @param object
     *            The object to extract the field from.
     * @return The value of the field or null.
     */
    public ReturnType getField(final ObjectType object)
    {
        final Class<?> cls = object.getClass();
        try
        {
            final Field field = cls.getDeclaredField(fieldName);
            field.setAccessible(true);

            @SuppressWarnings("unchecked")
            final ReturnType value = (ReturnType) field.get(object);

            return value;
        }
        catch (SecurityException e)
        {
            // e.printStackTrace();
        }
        catch (NoSuchFieldException e)
        {
            // e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            // e.printStackTrace();
        }
        throw new IllegalArgumentException("No such field:" + fieldName); 
    }
}
