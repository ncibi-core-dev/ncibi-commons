package org.ncibi.commons.closure;

/**
 * An interface for classes that take a field and transform it's value without
 * changing its type. For example take a field containing an integer and add one
 * to it's value.
 * 
 * @author V. Glenn Tarcea
 * 
 * @param <FieldType>
 *            The type of field to change.
 */
public interface FieldChanger<FieldType>
{
    /**
     * Changes the value of a field.
     * 
     * @param field
     *            The current value of the field.
     * @return The new value of the field.
     */
    public FieldType changeField(FieldType field);
}
