package org.ncibi.commons.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Define a field or a method to be used as a part of a multifield search. This
 * allows class search fields to be annotated as being included in searches that
 * use multiple fields. Some Lucene searches can search across multiple fields.
 * 
 * @author gtarcea
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(
    { ElementType.METHOD, ElementType.FIELD })
@Documented
public @interface MultiFieldSearchField
{
}
