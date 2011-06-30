package org.ncibi.commons.lang;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrTokenizer;
import org.ncibi.commons.bean.BeanUtils;
import org.ncibi.commons.closure.ChangerFieldGetter;
import org.ncibi.commons.closure.FieldGetter;
import org.ncibi.commons.exception.ConstructorCalledError;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

/**
 * This is a container static class of useful string utilities. Where possible you should look for
 * utilities in org.apache.commons.lang.StringUtils. Where there isn't something in apache commons
 * then add the functionality here.
 * 
 * @author gtarcea
 * 
 */
public final class StrUtils
{
    /****************************************
     * Provide common Splitters and Joiners
     ****************************************/

    /**
     * Split on comma separated string.
     */
    public static final Splitter COMMA_SPLITTER = Splitter.on(',');

    /**
     * Split on semi-colon separated string.
     */
    public static final Splitter SEMI_COLON_SPLITTER = Splitter.on(';');

    /**
     * Join a list using a comma as a separator.
     */
    public static final Joiner COMMA_JOINER = Joiner.on(',');
    
    public static final Joiner SEMI_COLON_JOINER = Joiner.on(';');

    /**
     * Utility class, make constructor private.
     */
    private StrUtils()
    {
        throw new ConstructorCalledError(this.getClass());
    }

    /**
     * The type of quote.
     */
    public static enum Quote
    {
        SINGLE_QUOTE, DOUBLE_QUOTE
    }

    /**
     * This function takes a collection of objects (beans), pulls a field from the collection,
     * quotes the fields, and joins the resulting list of quoted fields. See StringUtils.join() for
     * details on joining a set of strings.
     * 
     * @param <T>
     *            The type of object in the collection.
     * @param collection
     *            A collection of objects of type T.
     * @param quoteType
     *            The type of quote to surround the field with.
     * @param separator
     *            The field separator for joining.
     * @param fieldGetter
     *            A FieldGetter for pulling a field from the collection of objects.
     * 
     * @see FieldGetter
     * 
     * @return A joined quoted string, eg: "'a','b','c'"
     */
    public static <T> String quoteFieldJoin(final Collection<T> collection, final Quote quoteType,
                final String separator, final FieldGetter<String, T> fieldGetter)
    {
        // Declare as final so changeField can see it.
        final String quote = quoteType == Quote.SINGLE_QUOTE ? "'" : "\"";

        final List<String> strList = BeanUtils.fieldToList(collection,
                    new ChangerFieldGetter<String, T>(fieldGetter)
                    {
                        @Override
                        public String changeField(final String field)
                        {
                            return quote + field + quote;
                        }
                    });

        return StringUtils.join(strList, separator);
    }

    /**
     * This function takes an array of objects (beans), pulls a field from the collection, quotes
     * the fields, and joins the resulting list of quoted fields. See StringUtils.join() for details
     * on joining a set of strings.
     * 
     * @param <T>
     *            The type of object in the array.
     * @param array
     *            An array of objects of type T.
     * @param quoteType
     *            The type of quote to surround the field with.
     * @param separator
     *            The field separator for joining.
     * @param fieldGetter
     *            A FieldGetter for pulling a field from the collection of objects.
     * 
     * @see FieldGetter
     * 
     * @return A joined quoted string, eg: "'a','b','c'"
     */
    public static <T> String quoteFieldJoin(final T[] array, final Quote quoteType,
                final String separator, final FieldGetter<String, T> fieldGetter)
    {
        final List<T> collection = Arrays.asList(array);
        return quoteFieldJoin(collection, quoteType, separator, fieldGetter);
    }

    /**
     * Takes a list of objects (beans), pulls a field from each object in the list and joins the
     * resulting list of fields.
     * 
     * @param <T>
     *            The type of object in the list.
     * @param collection
     *            A collection objects of type T.
     * @param separator
     *            The field separator for joining.
     * @param fieldGetter
     *            A FieldGetter for pulling a field from the collection of objects.
     * @return A joined string, eg: "a,b,c"
     */
    public static <T> String fieldJoin(final Collection<T> collection, final String separator,
                final FieldGetter<String, T> fieldGetter)
    {
        final List<String> strList = BeanUtils.fieldToList(collection, fieldGetter);
        return StringUtils.join(strList, separator);
    }

    /**
     * Takes an array of objects (beans), pulls a field from each object in the array and joins the
     * resulting list of fields.
     * 
     * @param <T>
     *            The type of object in the array.
     * @param array
     *            An array objects of type T
     * @param separator
     *            The field separator for joining.
     * @param fieldGetter
     *            A FieldGetter for pulling a field from the collection of objects.
     * @return A joined string, eg: "a,b,c"
     */
    public static <T> String fieldJoin(final T[] array, final String separator,
                final FieldGetter<String, T> fieldGetter)
    {
        final List<T> collection = Arrays.asList(array);
        return fieldJoin(collection, separator, fieldGetter);
    }

    /**
     * Splits a line up into separate entries separated by the specified delimiter.
     * 
     * @param line
     *            The line to split up.
     * @param delimiter
     *            The delimiter separating each entry in the line.
     * @return A list of tokens.
     */
    public static List<String> split(final String line, final String delimiter)
    {
        final List<String> tokenList = new ArrayList<String>();
        final StrTokenizer tokenizer = new StrTokenizer(line, delimiter);
        tokenizer.setEmptyTokenAsNull(false);
        tokenizer.setIgnoreEmptyTokens(false);

        while (tokenizer.hasNext())
        {
            tokenList.add(tokenizer.nextToken());
        }

        return tokenList;
    }

    /**
     * Splits a line up into separate entries separated by the specified delimiter.
     * 
     * @param line
     *            The line to split up.
     * @param delimiter
     *            The delimiter (character) separating each entry in the line.
     * @return A list of tokens.
     */
    public static List<String> split(final String line, final char delimiter)
    {
        return split(line, Character.toString(delimiter));
    }

    /**
     * Splits a line up into separate entries separated by the specified delimiter.
     * 
     * @param line
     *            The line to split up.
     * @param delimiter
     *            The delimiter separating each entry in the line.
     * @return An array of tokens.
     */
    public static String[] splitAsArray(final String line, final String delimiter)
    {
        final List<String> tokenList = split(line, delimiter);
        final String[] tokenArray = new String[tokenList.size()];

        return tokenList.toArray(tokenArray);
    }

    /**
     * Splits a line up into separate entries separated by the specified delimiter.
     * 
     * @param line
     *            The line to split up.
     * @param delimiter
     *            The delimiter (character) separating each entry in the line.
     * @return An array of tokens.
     */
    public static String[] splitAsArray(final String line, final char delimiter)
    {
        return splitAsArray(line, Character.toString(delimiter));
    }

    /**
     * Convert an object to a string. Return the defaultValue if an exception is raised.
     * 
     * @param <T>
     *            The type to convert to a string.
     * @param item
     *            The item to convert.
     * @param defaultValue
     *            The default value to return if an error occurs.
     * @return The item in string form.
     */
    public static <T> String toString(final T item, final String defaultValue)
    {
        try
        {
            return item.toString();
        }
        catch (final Throwable t)
        {
            return defaultValue;
        }
    }
}
