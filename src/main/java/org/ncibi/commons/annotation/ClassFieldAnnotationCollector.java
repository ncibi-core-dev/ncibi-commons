package org.ncibi.commons.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class provides for collection of field annotations on a class. The class
 * allows for iteration over the field annotations and provides abstract methods
 * that can be used to collect data on these. The two abstract methods provided
 * are onEachField() and onEachFieldAnnotation. The onEachFieldAnnotation()
 * method allows the user to inspect a field and an annotation on that field and
 * return any data they deem pertinent. This information is then collected by
 * the class and made available through the getResults() method.
 * 
 * @author gtarcea
 * 
 * @param <T>
 *            The type of information being collected on a Field Annotation.
 */
public abstract class ClassFieldAnnotationCollector<T>
{
    /**
     * Called on every annotation for a field. If a non-null value is returned
     * from this class then the value is collected into a list. This list is
     * available through the classes getResults() method.
     * 
     * @param field
     *            The field currently being traversed.
     * @param annotation
     *            The current annotation for the field.
     * @return The data to collect on the field/annotation pair, or null if
     *         nothing should be collected.
     */
    public abstract T onEachFieldAnnotation(Field field, Annotation annotation);

    /**
     * Called on every field. This method allows a user of the class to reset
     * state information or to do other field specific processing.
     * 
     * @param field
     *            The current field.
     */
    public abstract void onEachField(Field field);

    /**
     * The list of data collected when onEachFieldAnnotation returns a non-null
     * value.
     */
    private final List<T> dataList = new ArrayList<T>();

    /**
     * Should collection stop? The user can control the collection process by
     * setting this field to true to cause the iteration to stop.
     */
    private boolean finished = false;

    /**
     * Performs the collection of data on field annotations. This method checks
     * to see if its finished on each new field. After the finish check it calls
     * onEachField for each field, and onEachFieldAnnotation for each annotation
     * for that field. It also checks the finished flag on each annotation, so
     * setting the finished flag while iterating over annotations will also
     * cause iteration to immediately finish. Non-null values returned by
     * onEachFieldAnnotation are collected and made available through the
     * getResults() method.
     * 
     * @param <C>
     *            The class type.
     * @param cls
     *            The class to collect field annotation information on.
     */
    public <C> void collect(final Class<C> cls)
    {
        final Field[] fields = cls.getDeclaredFields();

        for (final Field field : fields)
        {
            if (finished)
            {
                break;
            }

            onEachField(field);

            final Annotation[] annotations = field.getAnnotations();
            for (final Annotation annotation : annotations)
            {
                if (finished)
                {
                    // Exit out from both loops.
                    return;
                }
                final T dataItem = onEachFieldAnnotation(field, annotation);
                if (dataItem != null)
                {
                    dataList.add(dataItem);
                }
            }
        }
    }

    /**
     * Returns the list of data collected on the field annotations. The returned
     * list is not modifiable.
     * 
     * @return The list of field annotation data that was collected.
     */
    public List<T> getResults()
    {
        return Collections.unmodifiableList(dataList);
    }

    /**
     * Allows users to control whether or not iteration over fields and their
     * annotations continues. If true is passed in then iteration will
     * immediately stop.
     * 
     * @param setTo
     *            The value to set the finished flag to. True means stop
     *            immediately, false means continue iterating.
     */
    public void setFinished(final boolean setTo)
    {
        finished = setTo;
    }
}
