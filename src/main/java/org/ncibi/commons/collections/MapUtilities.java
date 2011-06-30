package org.ncibi.commons.collections;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.ncibi.commons.exception.ConstructorCalledError;

import com.google.common.collect.ImmutableMap;

/**
 * Utility functions on maps.
 * 
 * @author gtarcea
 */
public final class MapUtilities
{
    /**
     * Utility class - can't be instantiated.
     */
    private MapUtilities()
    {
        throw new ConstructorCalledError(this.getClass());
    }

    /**
     * Creates a new mutable map from a collection.
     * 
     * @param <K>
     *            The key type for the map.
     * @param <V>
     *            The value type for the map.
     * @param <T>
     *            The type of entry in the collection.
     * @param items
     *            A collection of items to create a map from.
     * @param mapper
     *            A mapper for extracting key and value from each item in the
     *            collection.
     * @return A mutable map containing keys and values created from the
     *         collection and identified using the mapper.
     */
    public static <K, V, T> Map<K, V> toHashMap(
            final Collection<? extends T> items, final Mapper<K, V, T> mapper)
    {
        final Map<K, V> map = new HashMap<K, V>();

        for (final T item : items)
        {
            final K key = mapper.getKey(item);
            final V value = mapper.getValue(item);
            map.put(key, value);
        }

        return map;
    }

    /**
     * Creates a new immutable map from a collection.
     * 
     * @param <K>
     *            The key type for the map.
     * @param <V>
     *            The value type for the map.
     * @param <T>
     *            The type of entry in the collection.
     * @param items
     *            A collection of items to create a map from.
     * @param mapper
     *            A mapper for extracting key and value from each item in the
     *            collection.
     * @return A immutable map containing keys and values created from the
     *         collection and identified using the mapper.
     */
    public static <K, V, T> Map<K, V> toImmutableMap(
            final Collection<? extends T> items, final Mapper<K, V, T> mapper)
    {
        final ImmutableMap.Builder<K, V> builder = new ImmutableMap.Builder<K, V>();

        for (final T item : items)
        {
            final K key = mapper.getKey(item);
            final V value = mapper.getValue(item);
            builder.put(key, value);
        }

        return builder.build();
    }
}
