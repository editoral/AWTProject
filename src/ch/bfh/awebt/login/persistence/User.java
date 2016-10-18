package ch.bfh.awebt.login.persistence;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;

public class User {

	public static final String HASH_ALGORITHM = "MD5";

	private int id;
	private String login;
	private byte[] passwordHash;
	private LocalDate dateOfBirth;

	protected User(int id, String login, LocalDate dateOfBirth) {

		this.id = id;
		this.login = login;
		this.dateOfBirth = dateOfBirth;
	}

	public User(int id, String login, char[] password, LocalDate dateOfBirth) throws NoSuchAlgorithmException {
		this(id, login, dateOfBirth);

		this.passwordHash = this.hashPassword(password);
	}

	public User(int id, String login, byte[] passwordHash, LocalDate dateOfBirth) {
		this(id, login, dateOfBirth);

		this.passwordHash = passwordHash;
	}

	public User(int id, String login, char[] password) throws NoSuchAlgorithmException {
		this(id, login, password, null);
	}

	public User(int id, String login, byte[] passwordHash) {
		this(id, login, passwordHash, null);
	}

	public User(String login, char[] password, LocalDate dateOfBirth) throws NoSuchAlgorithmException {
		this(-1, login, password, dateOfBirth);
	}

	public User(String login, byte[] passwordHash, LocalDate dateOfBirth) {
		this(-1, login, passwordHash, dateOfBirth);
	}

	public User(String login, char[] password) throws NoSuchAlgorithmException {
		this(-1, login, password, null);
	}

	public User(String login, byte[] passwordHash) {
		this(-1, login, passwordHash, null);
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	byte[] getPasswordHash() {
		return this.passwordHash;
	}

	public void setPassword(char[] password) throws NoSuchAlgorithmException {

		this.passwordHash = this.hashPassword(password);
	}

	public boolean validatePassword(char[] password) throws NoSuchAlgorithmException {

		return MessageDigest.isEqual(this.passwordHash, this.hashPassword(password));
	}

	public LocalDate getDateOfBirth() {
		return this.dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	private byte[] hashPassword(char[] password) throws NoSuchAlgorithmException {

		try {
			byte[] passwordBytes = new byte[password.length * 2];
			ByteBuffer.wrap(passwordBytes).asCharBuffer().put(password);

			return MessageDigest.getInstance(User.HASH_ALGORITHM).digest(passwordBytes);

		} catch (NullPointerException | NoSuchAlgorithmException ex) {
			throw new NoSuchAlgorithmException("Could not hash password.", ex);
		}
	}

	@Override
	public String toString() {
		return String.format("[User] login=%s", login);
	}
}
