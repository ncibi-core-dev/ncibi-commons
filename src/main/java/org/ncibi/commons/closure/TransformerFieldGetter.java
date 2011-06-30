package org.ncibi.commons.closure;

/**
 * The FieldTransformer class can be used to transform one object or field into
 * another type. This is useful when you have a list of objects and you want to
 * convert each one of them. Since the class implements the FieldGetter
 * interface it can be used on any method that accepts a FieldGetter.
 * <p>
 * This class implements the FieldTransformer and FieldGetter interfaces.
 * 
 * @author gtarcea
 * 
 * @param <FieldType>
 *            The original field type.
 * @param <NewFieldType>
 *            The new field to convert to.
 * @param <ObjectType>
 *            The object containing the field, may be the same type as field
 *            type.
 */
public abstract class TransformerFieldGetter<FieldType, NewFieldType, ObjectType> implements
        FieldGetter<NewFieldType, ObjectType>, FieldTransformer<FieldType, NewFieldType>
{
    /**
     * Used to extract the field from an object.
     */
    private final FieldGetter<FieldType, ObjectType> fieldGetter;

    /**
     * The abstract function the does the actual conversion from one field type
     * to another type.
     * 
     * @param field
     *            The field to convert.
     * @return The new field type.
     */
    public abstract NewFieldType transformField(FieldType field);

    /**
     * Constructor that accepts any of the different FieldGetter types.
     * 
     * @param fieldGetter
     *            The fieldGetter for extracting a field from an object.
     */
    public TransformerFieldGetter(final FieldGetter<FieldType, ObjectType> fieldGetter)
    {
        this.fieldGetter = fieldGetter;
    }

    /**
     * Constructor that takes the name of the field. This constructor then
     * creates a NamedFieldGetter to extract the object field.
     * 
     * @param fieldName
     *            The name of the field to extract.
     */
    public TransformerFieldGetter(final String fieldName)
    {
        this.fieldGetter = new NamedFieldGetter<FieldType, ObjectType>(fieldName);
    }

    /**
     * Converts a field from one type to another by extracting the field from
     * the object and then passing that field to the abstract method
     * transformField. Follows the FieldGetter protocol.
     * 
     * @param object
     *            The object to extract the field from.
     * @return The field converted to the new type.
     */
    public NewFieldType getField(final ObjectType object)
    {
        final FieldType field = fieldGetter.getField(object);

        return transformField(field);
    }
}
