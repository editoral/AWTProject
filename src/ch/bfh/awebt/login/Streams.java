package ch.bfh.awebt.login;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Utility class to use with streams.
 *
 */
public final class Streams {

	private Streams() {
		//utility class
	}

	/**
	 * Creates a {@link Stream} from an {@link Iterator}.
	 *
	 * @param <T>      type of the elements
	 * @param iterator iterator containing the elements to return
	 *
	 * @return a stream containing the elements from the iterator
	 */
	public static <T> Stream<T> iteratorStream(Iterator<T> iterator) {

		return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), false);
	}

	/**
	 * Creates a {@link List} from an {@link Iterator}.
	 *
	 * @param <T>      type of the elements
	 * @param iterator iterator containing the elements to return
	 *
	 * @return a list containing the elements from the iterator
	 */
	public static <T> List<T> iteratorList(Iterator<T> iterator) {

		return iteratorStream(iterator).collect(Collectors.toList());
	}

	/**
	 * Creates a {@link Collector} returning only one element.
	 *
	 * @param <T> type of the element
	 *
	 * @return an {@link Optional} containing either the single element or nothing if there is no element in the {@link Stream}
	 *
	 * @throws TooManyElementsException if there are multiple elements in the {@link Stream}
	 */
	public static <T> Collector<T, ?, Optional<T>> optionalSingleCollector() {

		return Collectors.reducing((a, b) -> {
			throw new TooManyElementsException();
		});
	}

	/**
	 * Creates a {@link Collector} returning only one element.
	 *
	 * @param <T> type of the element
	 *
	 * @return either the single element or {@code null} if there is no element in the {@link Stream}
	 *
	 * @throws TooManyElementsException if there are multiple elements in the {@link Stream}
	 */
	public static <T> Collector<T, ?, T> nullableSingleCollector() {

		return Collector.of(() -> new OptionalBox<>(true), OptionalBox<T>::set, OptionalBox<T>::set, OptionalBox<T>::get);
	}

	/**
	 * Creates a {@link Collector} returning exactly one element.
	 *
	 * @param <T> type of the element
	 *
	 * @return the single element in the {@link Stream}
	 *
	 * @throws NoSuchElementException   if there are no elements in the {@link Stream}
	 * @throws TooManyElementsException if there are multiple elements in the {@link Stream}
	 */
	public static <T> Collector<T, ?, T> singleCollector() {

		return Collector.of(() -> new OptionalBox<>(false), OptionalBox<T>::set, OptionalBox<T>::set, OptionalBox<T>::get);
	}

	private static class OptionalBox<T> {

		private final boolean optional;

		private T value = null;
		private boolean present = false;

		private OptionalBox(boolean optional) {

			this.optional = optional;
		}

		private OptionalBox<T> set(OptionalBox<T> optional) {

			return optional.present ? this.set(optional.value) : this;
		}

		private OptionalBox<T> set(T value) {

			if (present)
				throw new TooManyElementsException();

			present = true;
			this.value = value;
			return this;
		}

		private T get() {

			if (!present && !optional)
				throw new NoSuchElementException();

			return value;
		}
	}
}
