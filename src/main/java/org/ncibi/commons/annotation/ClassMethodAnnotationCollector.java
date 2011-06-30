package org.ncibi.commons.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.lang.ObjectUtils.Null;

/**
 * An annotation collector for class methods.
 * 
 * @author gtarcea
 * 
 * @param <T>
 *            The data type returned on collection.
 */
public abstract class ClassMethodAnnotationCollector<T>
{
    /**
     * Called on each annotation for a method.
     * 
     * @param method
     *            The method the annotation is on.
     * @param annotation
     *            The annotation.
     * @return Information collected on the method/annotation.
     */
    public abstract T onEachMethodAnnotation(Method method, Annotation annotation);

    /**
     * Allows for processing when a new method is encountered.
     * 
     * @param method
     *            The method currently encountered.
     */
    public abstract void onEachMethod(Method method);

    /**
     * The class annotation collector used to collect annotations for methods.
     * The field portion is never called, the methods below represent reasonable
     * default ignorable implmentations for these field methods.
     */
    private final ClassAnnotationCollector<Null, T> annotationCollector = new ClassAnnotationCollector<Null, T>()
    {
        @Override
        public void onEachField(final Field field)
        {
            // Nothing to do.
        }

        @Override
        public Null onEachFieldAnnotation(final Field field, final Annotation annotation)
        {
            // Nothing to do.
            return null;
        }

        @Override
        public void onEachMethod(final Method method)
        {
            this.onEachMethod(method);
        }

        @Override
        public T onEachMethodAnnotation(final Method method, final Annotation annotation)
        {
            return this.onEachMethodAnnotation(method, annotation);
        }
    };

    /**
     * Collects the information on method annotations.
     * 
     * @param <C>
     *            The class type.
     * @param cls
     *            The class to collect information on.
     */
    public <C> void collect(final Class<C> cls)
    {
        /*
         * Not interested in the fields so don't collect.
         */
        annotationCollector.setFinishedProcessingFields(true);
        annotationCollector.collect(cls);
    }

    /**
     * Returns the list of collected information on methods/annotations.
     * 
     * @return The list of data collected.
     */
    public List<T> getMethodResults()
    {
        return annotationCollector.getMethodResults();
    }

    /**
     * Used to control processing.
     * 
     * @param setTo
     *            If true stops processing, otherwise processing continues.
     */
    public void setFinished(final boolean setTo)
    {
        annotationCollector.setFinishedProcessingMethods(setTo);
    }
}
