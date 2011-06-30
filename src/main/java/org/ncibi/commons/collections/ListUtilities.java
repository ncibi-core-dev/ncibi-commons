package org.ncibi.commons.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.math.NumberUtils;
import org.ncibi.commons.exception.ConstructorCalledError;
import org.ncibi.commons.lang.StrUtils;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;

/**
 * Class containing utilities for manipulating lists.
 * 
 * @author gtarcea
 * 
 */
public final class ListUtilities
{
    /**
     * Constructor - Not callable.
     */
    private ListUtilities()
    {
        throw new ConstructorCalledError(this.getClass());
    }

    /**
     * Split a list into equal size chunks, with the last chunk containing an
     * amount smaller than the chunk size if the list doesn't divide evenly.
     * 
     * @param <T>
     *            The type of items in the list.
     * @param list
     *            The list to chunk.
     * @param chunkSize
     *            The chunk size.
     * @return A list of lists of chunk size, with the last containing the
     *         remainder.
     * 
     * @throws IllegalArgumentException
     *             When chunckSize <= 0.
     */
    public static <T> List<List<T>> splitIntoChunks(final List<T> list, final int chunkSize)
    {
        if (chunkSize <= 0)
        {
            throw new IllegalArgumentException("chunkSize must be greater than 0.");
        }

        final List<List<T>> chunksList = new ArrayList<List<T>>();
        int index = 0;
        while (index < list.size())
        {
            final int count = list.size() - index > chunkSize ? chunkSize : list.size() - index;
            chunksList.add(list.subList(index, count + index));
            index += chunkSize;
        }

        return chunksList;
    }
    
    public static String createQuotedCommaJoinedString(final Collection<String> items)
    {
        return StrUtils.COMMA_JOINER.join(quoteStringsToQuotedStrings(items));
    }

    public static Iterable<String> quoteStringsToQuotedStrings(final Collection<String> items)
    {
        return Iterables.transform(items, new Function<String, String>()
        {
            public String apply(final String item)
            {
                return "'" + item + "'";
            }
        });
    }

    public static String createCommaJoinedStringFromIntegers(final Collection<Integer> items)
    {
        return StrUtils.COMMA_JOINER.join(transformIntegersToStrings(items));
    }

    public static Iterable<String> transformIntegersToStrings(final Collection<Integer> items)
    {
        return Iterables.transform(items, new Function<Integer, String>()
        {
            public String apply(final Integer item)
            {
                return item.toString();
            }
        });
    }

    public static List<String> csv2StringsListUpperCase(final String csv)
    {
        final List<String> values = new LinkedList<String>();
        for (final String value : StrUtils.COMMA_SPLITTER.omitEmptyStrings().trimResults().split(
                    csv))
        {
            values.add(value.toUpperCase());
        }

        return values;
    }
    
    public static List<String> csv2StringsList(final String csv)
    {
        final List<String> values = new LinkedList<String>();
        for (final String value : StrUtils.COMMA_SPLITTER.omitEmptyStrings().trimResults().split(csv))
        {
            values.add(value);
        }
        
        return values;
    }

    public static List<Integer> csv2IntegersList(final String csv)
    {
        final List<Integer> values = new LinkedList<Integer>();
        for (final String value : StrUtils.COMMA_SPLITTER.omitEmptyStrings().trimResults().split(csv))
        {
            if (NumberUtils.isNumber(value))
            {
                values.add(NumberUtils.toInt(value));
            }
        }

        return values;
    }

}
