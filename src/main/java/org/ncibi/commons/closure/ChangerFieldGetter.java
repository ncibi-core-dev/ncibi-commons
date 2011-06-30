package org.ncibi.commons.closure;

/**
 * An implementation of the FieldChanger and FieldGetter interfaces. This
 * creates a field getter that also changes the value of the extracted field.
 * 
 * @author V. Glenn Tarcea
 * 
 * @param <FieldType>
 *            The type of field to get and change.
 * @param <ObjectType>
 *            The type of object containing the field.
 */
public abstract class ChangerFieldGetter<FieldType, ObjectType> implements
        FieldGetter<FieldType, ObjectType>, FieldChanger<FieldType>
{
    /**
     * The getter for extracting the value of the field.
     */
    private final FieldGetter<FieldType, ObjectType> fieldGetter;

    /**
     * Abstract method that takes the value of a field in and returns a new
     * value.
     * 
     * @param field
     *            The original value of the field.
     * @return The new value of the field
     */
    public abstract FieldType changeField(FieldType field);

    /**
     * Constructor that takes a FieldGetter to extract the field from an object.
     * 
     * @param fieldGetter
     */
    public ChangerFieldGetter(final FieldGetter<FieldType, ObjectType> fieldGetter)
    {
        this.fieldGetter = fieldGetter;
    }

    /**
     * Constructor that just takes the name of the field to extract.
     * 
     * @param fieldName
     *            The name of the field to extract from the object.
     */
    public ChangerFieldGetter(final String fieldName)
    {
        this.fieldGetter = new NamedFieldGetter<FieldType, ObjectType>(fieldName);
    }

    /**
     * Uses the FieldGetter protocol to get a field value and change it.
     * 
     * @param object
     *            The object to extract the value of a field from.
     * @return The new value for the extracted field.
     */
    public FieldType getField(final ObjectType object)
    {
        final FieldType field = fieldGetter.getField(object);

        return changeField(field);
    }
}
