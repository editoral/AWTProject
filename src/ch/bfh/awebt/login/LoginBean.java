package ch.bfh.awebt.login;

import java.io.IOException;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import ch.bfh.awebt.login.model.User;

@ManagedBean(name="loginBean", eager=true)
@SessionScoped
public class LoginBean implements Serializable {

	public static enum ErrorType {

		DATABASE("ErrorDatabase"),
		MISSING_INFORMATION("LoginErrorMissingInformation"),
		INCORRECT_INFORMATION("LoginErrorIncorrectInformation"),
		USER_EXISTS("UserExists");

		private String message;

		private ErrorType(String message) {

			this.message = message;
		}

		public String getMessage() {

			return this.message;
		}
	}

	private User user;

	private String _userLogin;
	private String _userPassword;

	private ErrorType error;

	public boolean isLoggedIn() {
		return this.user != null;
	}

	public User getUser() {
		return this.user;
	}

	public String getLogin() {
		return this.user != null ? this.user.getUsername() : this._userLogin;
	}

	public void setLogin(String login) {
		this._userLogin = login;
	}

	@Deprecated
	public String getPassword() {
		return null;
	}

	public void setPassword(String password) {
		this._userPassword = password;
	}

	public ErrorType getError() {
		return this.error;
	}

	public String register() {

		this.error = null;
		if (this._userLogin != null && this._userLogin.length() > 0
				&& this._userPassword != null && this._userPassword.length() > 0) {
			this.user = new User();
			this.user.setUsername(this._userLogin);
			this.user.setPassword(this._userPassword);
			try {
				if(!this.user.save()) {
					this._userLogin = null;
					this.user = null;
					this.error = ErrorType.USER_EXISTS;
				}
				return "accountList?faces-redirect=true";
			} catch (Exception e) {
				this.error = ErrorType.DATABASE;
			};
			this._userPassword = null;
		} else {
			this.error = ErrorType.MISSING_INFORMATION;
		}

		return "";
	}

	public String login() {

		this.error = null;

		User user = new User();
		boolean success = false;
		try {
			success = user.loadUser(this._userLogin);
		} catch (Exception e) {
			this.error = ErrorType.DATABASE;
		}
		if (success && user != null && user.validatePassword(this._userPassword)) {
			this.user = user;
			return "accountList?faces-redirect=true";

		} else
			this.error = ErrorType.INCORRECT_INFORMATION;


		return "accountList?faces-redirect=true";
	}

	public String logout() {

		this.error = null;
		this.user = null;
		this._userLogin = null;
		this._userPassword = null;

		return "accountList?faces-redirect=true";
	}
	
	public void redirectIfNotAuthenticated() throws IOException {
		FacesContext fc = FacesContext.getCurrentInstance();
		if(!isLoggedIn()) {
			fc.getExternalContext().redirect("login.xhtml");
		}	
	}
}
