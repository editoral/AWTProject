package ch.bfh.awebt.login;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import ch.bfh.awebt.login.persistence.data.User;
import ch.bfh.awebt.login.persistence.UserDAO;

@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

	public static enum ErrorType {

		DATABASE("ErrorDatabase"),
		MISSING_INFORMATION("LoginErrorMissingInformation"),
		INCORRECT_INFORMATION("LoginErrorIncorrectInformation");

		private String message;

		private ErrorType(String message) {

			this.message = message;
		}

		public String getMessage() {

			return this.message;
		}
	}

	private UserDAO userDAO;
	private User user;

	private String _userLogin;
	private char[] _userPassword;

	private ErrorType error;

	private UserDAO getUserDAO() {

		if (this.userDAO == null)
			this.userDAO = new UserDAO();

		return this.userDAO;
	}

	public boolean isLoggedIn() {
		return this.user != null;
	}

	public User getUser() {
		return this.user;
	}

	public String getLogin() {
		return this.user != null ? this.user.getLogin() : this._userLogin;
	}

	public void setLogin(String login) {
		this._userLogin = login;
	}

	@Deprecated
	public String getPassword() {
		return null;
	}

	public void setPassword(String password) {
		this._userPassword = password.toCharArray();
	}

	public ErrorType getError() {
		return this.error;
	}

	public String register() {

		this.error = null;

		if (this._userLogin != null && this._userLogin.length() > 0
				&& this._userPassword != null && this._userPassword.length > 0)
			try {
				this.user = new User(this._userLogin, this._userPassword);
				this.getUserDAO().persist(this.user);
				this._userPassword = null;

			} catch (NoSuchAlgorithmException ex) {
				this.error = ErrorType.DATABASE;
			}

		else
			this.error = ErrorType.MISSING_INFORMATION;

		return "accountList?faces-redirect=true";
	}

	public String login() {

		this.error = null;

		try {
			User user = this.getUserDAO().findByLogin(this._userLogin);
			if (user != null && user.validatePassword(this._userPassword)) {
				this.user = user;
				return "accountList?faces-redirect=true";

			} else
				this.error = ErrorType.INCORRECT_INFORMATION;

		} catch (NoSuchAlgorithmException ex) {
			this.error = ErrorType.DATABASE;
		}

		return "accountList?faces-redirect=true";
	}

	public String logout() {

		this.error = null;

		this.user = null;
		this._userLogin = null;
		this._userPassword = null;

		return "accountList?faces-redirect=true";
	}
}
