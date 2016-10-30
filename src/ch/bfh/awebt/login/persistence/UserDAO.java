package ch.bfh.awebt.login.persistence;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import ch.bfh.awebt.login.MapBuilder;
import ch.bfh.awebt.login.Streams;
import ch.bfh.awebt.login.persistence.data.User;
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
public class UserDAO extends GenericDAO<User, Integer> implements Serializable {

	private static final long serialVersionUID = 7855160381047946895L;

	@Override
	protected Class<User> getEntityClass() {
		return User.class;
	}
	/*private Connection getConnection() throws SQLException {

		try {
			Class.forName("mysql server");
		} catch (ClassNotFoundException ex) {
			throw new SQLException("Could not load JDBC driver.", ex);
		}

		return DriverManager.getConnection("jdbc:mysql://localhost:3306", "JSFlogin", "JSFLogin");
	}
	public User create(User entity) throws SQLException {

		try (Connection con = this.getConnection();
				 PreparedStatement stmIns = con.prepareStatement("insert Users(login, password) values(?, ?, ?)");
				 PreparedStatement stmSel = con.prepareStatement("select id from Users where login=?")) {

			stmIns.setString(1, entity.getLogin());
			stmIns.setBytes(2, entity.getPasswordHash());
			stmIns.executeUpdate();

			stmSel.setString(1, entity.getLogin());
			try (ResultSet res = stmSel.executeQuery()) {
				if (res.next())
					entity.setId(res.getInt("id"));
			}
		}

		return entity;
	}*/
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
