package org.ncibi.commons.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Define a field or method as the default field for search. This allows class
 * search fields to be annotated as the default field to use in a search. The
 * default field is used in some Lucene searches to tell Lucene which field to
 * use in the search when no field is explicitly identified.
 * 
 * @author gtarcea
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(
    { ElementType.METHOD, ElementType.FIELD })
@Documented
public @interface DefaultSearchField
{
}
