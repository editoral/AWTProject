package ch.bfh.awebt.login.persistence.data;

/**
 * Represents the base for all persistent objects.
 *
 * @param <K> type of the persistent object's unique identifier
 *
 */
public abstract class PersistentObject<K> {

	/**
	 * Gets the unique identifier of the persistent object.
	 *
	 * @return unique identifier of the persistent object
	 */
	protected abstract K getId();

	/**
	 * Gets a hash code for the persistent object.
	 *
	 * @return hash code for the persistent object
	 */
	@Override
	public int hashCode() {
		return (17 + getId().hashCode()) * 31;
	}

	/**
	 * Compares this persistent object with another object.
	 *
	 * @param obj object to compare to the persistent object
	 *
	 * @return whether or not the two objects are equal
	 */
	@Override
	public boolean equals(Object obj) {
		return obj != null && getClass() == obj.getClass() && getId().equals(((PersistentObject)obj).getId());
	}

	/**
	 * Gets a string representation for the persistent object.
	 *
	 * @return string representation for the persistent object
	 */
	@Override
	public String toString() {
		return String.format("%s[id=%s]", getClass().getSimpleName(), getId());
	}
}
