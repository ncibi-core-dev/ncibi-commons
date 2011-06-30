package org.ncibi.commons.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Allows for the collection of data associated with field and method
 * annotations on a class. Abstract methods are defined that are called at
 * points in the collection process to gather data on the annotations for fields
 * and methods. The data collected is placed into separate list collections that
 * can be accessed afterwards to retrieve the list of collected information. If
 * a collection routine returns a null value then nothing is added on that
 * iteration.
 * 
 * @author gtarcea
 * 
 * @param <FT>
 *            The field type information to place into the field collection.
 * @param <MT>
 *            The method type information to place into the method collection.
 */
public abstract class ClassAnnotationCollector<FT, MT>
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
    public abstract FT onEachFieldAnnotation(Field field, Annotation annotation);

    /**
     * Called on every field. This method allows a user of the class to reset
     * state information or to do other field specific processing.
     * 
     * @param field
     *            The current field.
     */
    public abstract void onEachField(Field field);

    /**
     * Called on every annotation for a method. If a non-null value is returned
     * from this class then the value is collected into a list. This list is
     * available through the classes getMethodResults() method.
     * 
     * @param method
     *            The method currently being traversed.
     * @param annotation
     *            The current annotation for the method.
     * @return The data to collect on the method/annotation pair, or null if
     *         nothing should be collected.
     */
    public abstract MT onEachMethodAnnotation(Method method, Annotation annotation);

    /**
     * Called on every class method. This method allows a user of the class to
     * reset state information or to do other method specific processing.
     * 
     * @param method
     *            The current method.
     */
    public abstract void onEachMethod(Method method);

    /**
     * The list of data collected when onEachFieldAnnotation returns a non-null
     * value.
     */
    private final List<FT> fieldList = new ArrayList<FT>();

    /**
     * The list of data collected when onEachMethodAnnotation returns a non-null
     * value.
     */
    private final List<MT> methodList = new ArrayList<MT>();

    /**
     * Allows the user to control when processing stops on fields and methods.
     */
    private boolean finished = false;

    /**
     * Allows the user to control when processing stops on fields.
     */
    private boolean finishedProcessingFields = false;

    /**
     * Allows the user to control when processing stops on methods.
     */
    private boolean finishedProcessingMethods = false;

    /**
     * Performs the collection of data on field annotations. This method checks
     * to see if its finished on each new field. After the finish check it calls
     * onEachField for each field, and onEachFieldAnnotation for each annotation
     * for that field. It also checks the finished flag on each annotation, so
     * setting the finished flag while iterating over annotations will also
     * cause iteration to immediately finish. Non-null values returned by
     * onEachFieldAnnotation are collected and made available through the
     * getFieldResults() method.
     * 
     * @param <C>
     *            The class type.
     * @param cls
     *            The class to collection field annotation information on.
     */
    private <C> void collectFields(final Class<C> cls)
    {
        final Field[] fields = cls.getDeclaredFields();

        outerloop: for (final Field field : fields)
        {
            if (finished || finishedProcessingFields)
            {
                break;
            }

            onEachField(field);

            final Annotation[] annotations = field.getAnnotations();
            for (final Annotation annotation : annotations)
            {
                if (finished || finishedProcessingFields)
                {
                    // Exit out from both loops.
                    break outerloop;
                }
                final FT dataItem = onEachFieldAnnotation(field, annotation);
                if (dataItem != null)
                {
                    fieldList.add(dataItem);
                }
            }
        }
    }

    /**
     * Performs the collection of data on method annotations. This method checks
     * to see if its finished on each new method. After the finish check it
     * calls onEachMethod for each method, and onEachMethodAnnotation for each
     * annotation for that method. It also checks the finished flag on each
     * annotation, so setting the finished flag while iterating over annotations
     * will also cause iteration to immediately finish. Non-null values returned
     * by onEachMethodAnnotation are collected and made available through the
     * getMethodResults() method.
     * 
     * @param <C>
     *            The class type.
     * @param cls
     *            The class to collection method annotation information on.
     */
    private <C> void collectMethods(final Class<C> cls)
    {
        final Method[] methods = cls.getDeclaredMethods();

        outerloop: for (final Method method : methods)
        {
            if (finished || finishedProcessingMethods)
            {
                break;
            }

            onEachMethod(method);

            final Annotation[] annotations = method.getAnnotations();
            for (final Annotation annotation : annotations)
            {
                if (finished || finishedProcessingMethods)
                {
                    // Exit out from both loops.
                    break outerloop;
                }
                final MT dataItem = onEachMethodAnnotation(method, annotation);
                if (dataItem != null)
                {
                    methodList.add(dataItem);
                }
            }
        }
    }

    /**
     * Performs collection of data on field and method annotations in the class.
     * 
     * @param <C>
     *            The class type.
     * @param cls
     *            The class to collect field and method annotation information
     *            on.
     */
    public <C> void collect(final Class<C> cls)
    {
        if (!finished || !finishedProcessingFields)
        {
            collectFields(cls);
        }

        if (!finished || !finishedProcessingMethods)
        {
            collectMethods(cls);
        }
    }

    /**
     * Returns the list of data collected on field annotations.
     * 
     * @return The list of field annotation data that was collected.
     */
    public List<FT> getFieldResults()
    {
        return Collections.unmodifiableList(fieldList);
    }

    /**
     * Returns the list of data collected on method annotations.
     * 
     * @return The list of method annotation data that was collected.
     */
    public List<MT> getMethodResults()
    {
        return Collections.unmodifiableList(methodList);
    }

    /**
     * Allows users to control when processing stops.
     * 
     * @param setTo
     *            The value to set the finished flag to. True means stop
     *            immediately, false means continue iterating.
     */
    public void setFinished(final boolean setTo)
    {
        finished = setTo;
    }

    /**
     * Allows users to control when field processing stops.
     * 
     * @param setTo
     *            The value to set the finishedProcessingFields flag to. True
     *            means stop immediately, false means continue iterating.
     */
    public void setFinishedProcessingFields(final boolean setTo)
    {
        finishedProcessingFields = setTo;
    }

    /**
     * Allows users to control when method processing stops.
     * 
     * @param setTo
     *            The value to set the finishedProcessingMethods flag to. True
     *            means stop immediately, false means continue iterating.
     */
    public void setFinishedProcessingMethods(final boolean setTo)
    {
        finishedProcessingMethods = setTo;
    }
}