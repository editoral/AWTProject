package ch.bfh.awebt.login;

import java.util.NoSuchElementException;

/**
 * Represents a status where there are too many elements in an {@link Iterable}. <br>
 * This exception represents the opposite status of {@link NoSuchElementException}.
 *
 */
public class TooManyElementsException extends RuntimeException {

	private static final long serialVersionUID = 3801154629261088655L;
}
