package ch.bfh.awebt.login.persistence.data;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;


/**
 * Represents an application user.
 *
 */
@Entity
@Table(name = "users")
@NamedQueries({
	@NamedQuery(name = User.FIND_BY_LOGIN_QUERY, query = "select u from User u where lower(u.login) = lower(:login)"),
	@NamedQuery(name = User.FIND_ALL_ORDERED_BY_LOGIN_QUERY, query = "select u from User u order by u.login"),
	@NamedQuery(name = User.FIND_MANAGERS_ORDERED_BY_LOGIN_QUERY, query = "select u from User u where u.isManager=1 order by u.login")})
public class User extends PersistentObject<Integer> implements Serializable {

	private static final long serialVersionUID = -7147878463002225404L;

	/**
	 * Name of the query to find a user by their login.
	 */
	public static final String FIND_BY_LOGIN_QUERY = "User.FIND_BY_LOGIN_QUERY";

	/**
	 * Name of the query to get all users ordered by their login.
	 */
	public static final String FIND_ALL_ORDERED_BY_LOGIN_QUERY = "User.FIND_ALL_ORDERED_BY_LOGIN_QUERY";

	/**
	 * Name of the query to find all managers ordered by their login.
	 */
	public static final String FIND_MANAGERS_ORDERED_BY_LOGIN_QUERY = "User.FIND_MANAGERS_ORDERED_BY_LOGIN_QUERY";

	/**
	 * Name of the algorithm to use to hash the passwords.
	 */
	public static final String HASH_ALGORITHM = "MD5";


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private Integer id;


	@Column(name = "login", nullable = false, length = 25, unique = true)
	private String login;
	

	@Column(name = "password", nullable = false)
	private byte[] passwordHash;

	@Column(name = "manager", nullable = false)
	private boolean isManager = false;


	/**
	 * Constructs an empty user.
	 */
	protected User() {

	}

	private User(String login) {
		this();

		this.login = login;

	}

	/**
	 * Constructs a user with a specified login, name and password.
	 *
	 * @param login        unique login of the user
	 * @param passwordHash hashed password of the user
	 */
	public User(String login,  byte[] passwordHash) {
		this(login);

		this.passwordHash = passwordHash;
	}

	/**
	 * Constructs a user with a specified login, name and password.
	 *
	 * @param login    unique login of the user
	 * @param timeZone time zone the user wants to view times in
	 * @param password clear-text password of the user
	 *
	 * @throws NoSuchAlgorithmException if the password could not be hashed
	 */
	public User(String login,  char[] password) throws NoSuchAlgorithmException {
		this(login,  hashPassword(login, password));
	}

	@Override
	public Integer getId() {
		return id;
	}

	/**
	 * Sets the unique identifier of the user.
	 *
	 * @param id unique identifier of the user
	 */
	protected void setId(int id) {
		this.id = id;
	}



	/**
	 * Gets the unique login of the user.
	 *
	 * @return unique login of the user
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * Gets the hashed password of the user.
	 *
	 * @return hashed password of the user
	 */
	byte[] getPasswordHash() {

		return Arrays.copyOf(passwordHash, passwordHash.length);
	}

	/**
	 * Sets the clear-text password of the user.
	 *
	 * @param password clear-text password of the user
	 *
	 * @throws NoSuchAlgorithmException if the password could not be hashed
	 */
	public void setPassword(char[] password) throws NoSuchAlgorithmException {

		passwordHash = hashPassword(login, password);
	}

	/**
	 * Checks if the given password matches the user's one.
	 *
	 * @param password clear-text password to match against the user's one
	 *
	 * @return whether or not the given password matches the user's one
	 *
	 * @throws NoSuchAlgorithmException if the given password could not be hashed
	 */
	public boolean validatePassword(char[] password) throws NoSuchAlgorithmException {

		return validatePassword(hashPassword(login, password));
	}

	/**
	 * Checks if the given password matches the user's one.
	 *
	 * @param passwordHash hashed password to match against the user's one
	 *
	 * @return whether or not the given password matches the user's one
	 */
	public boolean validatePassword(byte[] passwordHash) {

		return MessageDigest.isEqual(this.passwordHash, passwordHash);
	}

	/**
	 * Gets whether or not the user is a manager.
	 *
	 * @return whether or not the user is a manager
	 */
	public boolean isManager() {
		return isManager;
	}

	/**
	 * Sets whether or not the user is a manager.
	 *
	 * @param isManager whether or not the user is a manager
	 */
	public void setIsManager(boolean isManager) {
		this.isManager = isManager;
	}



	/**
	 * Generate a hash for a given password.
	 *
	 * @param login    login of the user to generate password for
	 * @param password password to generate hash for
	 *
	 * @return hash for the specified password
	 *
	 * @throws NoSuchAlgorithmException if the password could not be hashed
	 */
	public static byte[] hashPassword(String login, char[] password) throws NoSuchAlgorithmException {

		try {
			byte[] loginBytes = login.getBytes();

			byte[] passwordBytes = new byte[password.length * 2];
			ByteBuffer.wrap(passwordBytes).asCharBuffer().put(password);

			MessageDigest md = MessageDigest.getInstance(User.HASH_ALGORITHM);
			md.update(loginBytes);
			md.update(passwordBytes);
			return md.digest();

		} catch (NullPointerException | NoSuchAlgorithmException ex) {
			throw new NoSuchAlgorithmException("Could not hash password.", ex);
		}
	}
}
