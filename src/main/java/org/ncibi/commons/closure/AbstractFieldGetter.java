package org.ncibi.commons.closure;

/**
 * Abstract class for pulling out fields from a specific object.
 * 
 * @author V. Glenn Tarcea
 * 
 * @param <ReturnType>
 *            The type of the field to being pulled out.
 * @param <ObjectType>
 *            The object type containing the field.
 */
public abstract class AbstractFieldGetter<ReturnType, ObjectType> implements
        FieldGetter<ReturnType, ObjectType>
{
    /**
     * An abstract method that takes in an object and returns the value of a
     * field from that object.
     * 
     * @param object
     *            The object containing the field
     * @return A field
     */
    public abstract ReturnType getField(ObjectType object);
}
