package org.ncibi.commons.closure;

/**
 * Interface for classes that return fields from an object.
 * 
 * @author V. Glenn Tarcea
 * 
 * @param <ReturnType>
 *            The field type of the field to return from the object.
 * @param <ObjectType>
 *            The type of object containing the field.
 */
public interface FieldGetter<ReturnType, ObjectType>
{
    /**
     * Given an object returns the value of a specific field from that object.
     * 
     * @param object
     *            The object containing the field of interest.
     * @return The value of the field.
     */
    public ReturnType getField(ObjectType object);
}
