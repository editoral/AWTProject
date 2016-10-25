package ch.bfh.awebt.login;

import java.util.HashMap;
import java.util.Map;

/**
 * Helper class to build a map with chained method calls.
 *
 * @param <K> type of the keys
 * @param <V> type of the values
 *
 */
public class MapBuilder<K, V> {

	private final Map<K, V> map = new HashMap<>();

	private MapBuilder() {
		//use factory method
	}

	/**
	 * Creates a builder instance and adds the first key-value pair.
	 *
	 * @param <K>   type of the keys
	 * @param <V>   type of the values
	 * @param key   key to add to the underlying map
	 * @param value value to set the key to
	 *
	 * @return a builder with the first pair added
	 */
	public static <K, V> MapBuilder<K, V> first(K key, V value) {
		return new MapBuilder<K, V>().add(key, value);
	}

	/**
	 * Creates a map instance and adds the single key-value pair.
	 *
	 * @param key   key to add to the underlying map
	 * @param value value to set the key to
	 *
	 * @param <K>   type of the keys
	 * @param <V>   type of the values
	 *
	 * @return a map with the only pair added
	 */
	public static <K, V> Map<K, V> single(K key, V value) {
		return first(key, value).map;
	}

	/**
	 * Adds an additional key-value pair to the map.
	 *
	 * @param key   key to add to the underlying map
	 * @param value value to set the key to
	 *
	 * @return a builder to add additional elements to
	 */
	public MapBuilder<K, V> add(K key, V value) {

		map.put(key, value);
		return this;
	}

	/**
	 * Adds a last key-value pair and returns the map.
	 *
	 * @param key   key to add to the underlying map
	 * @param value value to set the key to
	 *
	 * @return the underlying map with the added pairs
	 */
	public Map<K, V> last(K key, V value) {
		return this.add(key, value).map;
	}
}
