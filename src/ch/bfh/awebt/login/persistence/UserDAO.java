package ch.bfh.awebt.login.persistence;

import java.io.Serializable;
import java.util.List;
import ch.bfh.awebt.login.MapBuilder;
import ch.bfh.awebt.login.Streams;
import ch.bfh.awebt.login.persistence.data.User;

/**
 * Represents a data access object for user
 *
 *
 */
public class UserDAO extends GenericDAO<User, Integer> implements Serializable {

	private static final long serialVersionUID = 7855160381047946895L;

	@Override
	protected Class<User> getEntityClass() {
		return User.class;
	}

	/**
	 * Gets all users ordered by their login.
	 *
	 * @return a list of all users
	 */
	public List<User> findAllOrderedByLogin() {

		return findByQuery(User.FIND_ALL_ORDERED_BY_LOGIN_QUERY);
	}

	/**
	 * Finds all managers ordered by their login.
	 *
	 * @return a list of all managers
	 */
	public List<User> findManagersOrderedByLogin() {

		return findByQuery(User.FIND_MANAGERS_ORDERED_BY_LOGIN_QUERY);
	}

	/**
	 * Finds a user by its unique login.
	 *
	 * @param login unique login of the user
	 *
	 * @return user with the specified login or  null 
	 */
	public User findByLogin(String login) {

		return findByQuery(User.FIND_BY_LOGIN_QUERY, MapBuilder.single("login", login))
			.stream().collect(Streams.nullableSingleCollector());
	}
}
