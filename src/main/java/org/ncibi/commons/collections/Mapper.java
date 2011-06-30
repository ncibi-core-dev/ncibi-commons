package org.ncibi.commons.collections;

/**
 * An interface for mapping objects. Allows for the conversion of a set of
 * objects into a map.
 * 
 * @author gtarcea
 * @param <K>
 *            The key type.
 * @param <V>
 *            The value type.
 * @param <T>
 *            The type we are extracting keys and values from.
 */
public interface Mapper<K, V, T>
{
    /**
     * Extracts a value for a map from an item.
     * 
     * @param item
     *            The item to get a value from.
     * @return The value to store in the map.
     */
    public V getValue(final T item);

    /**
     * Extracts a key for a map from an item.
     * 
     * @param item
     *            The item to get a key from.
     * @return The key to use in the map for the value (see above) that was
     *         extracted.
     */
    public K getKey(final T item);
}
