package org.ncibi.commons.collections;

/**
 * An implementation of the Mapper interface that returns the item
 * as the value to store in the map.
 * 
 * @author gtarcea
 *
 * @param <K> The key type.
 * @param <T> The item type (and value type to store in the map).
 */
public abstract class IdentityMapper<K, T> implements Mapper<K, T, T>
{
    /**
     * The user must create an implementation of the key mapping.
     */
    public abstract K getKey(final T item);
    
    /**
     * An identity function that returns the item passed in.
     */
    public T getValue(final T item)
    {
        return item;
    }
}
