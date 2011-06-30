package org.ncibi.commons.closure;

/**
 * An interface for classes that transform a field from one type into another.
 * For example this could be used to transform a field with an Integer into a
 * String.
 * 
 * @author V. Glenn Tarcea
 * 
 * @param <FieldType>
 *            The original type of the field.
 * @param <NewFieldType>
 *            The new type for the transformed field.
 */
public interface FieldTransformer<FieldType, NewFieldType>
{
    /**
     * Transforms a field changing its type.
     * 
     * @param field
     *            The field to transform.
     * @return The value of the field after transformation.
     */
    public NewFieldType transformField(FieldType field);
}
