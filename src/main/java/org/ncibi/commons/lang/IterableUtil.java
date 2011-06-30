package org.ncibi.commons.lang;

import java.util.LinkedList;
import java.util.List;

import org.ncibi.commons.exception.ConstructorCalledError;

import com.google.common.collect.Iterables;

public final class IterableUtil
{
    private IterableUtil()
    {
        throw new ConstructorCalledError(this.getClass());
    }
    
    public static <T> T getAt(Iterable<T> iterable, int position, T defaultValue)
    {
        T value = defaultValue;
        
        try
        {
            value = Iterables.get(iterable, position);
        }
        catch (IndexOutOfBoundsException e)
        {
            value = defaultValue;
        }
        
        return value;
    }
    
    public static <T> List<T> asList(Iterable<T> iterable)
    {
        List<T> listOfItems = new LinkedList<T>();
        Iterables.addAll(listOfItems, iterable);
        return listOfItems;
    }
}
