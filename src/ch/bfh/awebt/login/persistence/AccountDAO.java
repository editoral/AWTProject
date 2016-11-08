package ch.bfh.awebt.login.persistence;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import ch.bfh.awebt.login.MapBuilder;
import ch.bfh.awebt.login.Streams;
import ch.bfh.awebt.login.persistence.data.Account;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

/**
 * Represents a data access object for user
 *
 *
 */
public class AccountDAO extends GenericDAO<Account, Integer> implements Serializable {

	private static final long serialVersionUID = 7855160381047946895L;

	@Override
	protected Class<Account> getEntityClass() {
		return Account.class;
	}

//	/**
//	 * Gets all users ordered by their login.
//	 *
//	 * @return a list of all users
//	 */
//	public List<Account> findAllOrderedByLogin() {
//
//		return findByQuery(Account.FIND_ALL_ORDERED_BY_LOGIN_QUERY);
//	}
//
//	/**
//	 * Finds all managers ordered by their login.
//	 *
//	 * @return a list of all managers
//	 */
//	public List<Account> findManagersOrderedByLogin() {
//
//		return findByQuery(Account.FIND_MANAGERS_ORDERED_BY_LOGIN_QUERY);
//	}
//
//	/**
//	 * Finds a user by its unique login.
//	 *
//	 * @param login unique login of the user
//	 *
//	 * @return user with the specified login or  null 
//	 */
//	public Account findByLogin(String login) {
//
//		return findByQuery(Account.FIND_BY_LOGIN_QUERY, MapBuilder.single("login", login))
//			.stream().collect(Streams.nullableSingleCollector());
//	}
}
