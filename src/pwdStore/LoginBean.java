package pwdStore;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import model.Account;
import model.User;
import pwdStore.AccountBean.ErrorType;

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

	private ArrayList<Account> accList;
	
	private User user;

	private String _userLogin;
	private String _userPassword;

	private ErrorType error;

	public boolean isLoggedIn() {
		return this.user != null;
	}

	public String register() {

		this.error = null;
		if (this._userLogin != null && this._userLogin.length() > 0
				&& this._userPassword != null && this._userPassword.length() > 0) {
			this.user = new User();
			this.user.setUsername(this._userLogin);
			SecureRandom random = new SecureRandom();
			this.user.setSalt(new BigInteger(130, random).toString(32));
			try {
				System.out.println("User PWD: " + this._userPassword);
				this.user.setPassword(this._userPassword);
				if(!this.user.save()) {
					this.error = ErrorType.USER_EXISTS;
				}
			} catch (Exception e) {
				this.error = ErrorType.DATABASE;
			};
			this._userPassword = null;
		} else {
			this.error = ErrorType.MISSING_INFORMATION;
		}
		this._userLogin = null;
		this._userPassword = null;
		this.user = null;
		return "login?faces-redirect=true";
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
		try {
			if (success && user != null && user.validatePassword(this._userPassword)) {
				this.user = user;
				return "accountList?faces-redirect=true";

			} else
				this.error = ErrorType.INCORRECT_INFORMATION;
		} catch (Exception e) {
			this.error = ErrorType.INCORRECT_INFORMATION;
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
	
	public String changePassword() {

		this.error = null;
		if (this._userLogin != null && this._userLogin.length() > 0
				&& this._userPassword != null && this._userPassword.length() > 0 && this.user != null) {
			
			SecureRandom random = new SecureRandom();
			this.user.setSalt(new BigInteger(130, random).toString(32));
			try {
				System.out.println("User PWD: " + this._userPassword);
				this.user.setPassword(this._userPassword);
				if(!this.user.update()) {
					this.error = ErrorType.USER_EXISTS;
				}
				for(Account acc : accList) {
					System.out.println("new Acc Password: " + acc.getPassword());
					acc.update();
				}
			} catch (Exception e) {
				this.error = ErrorType.DATABASE;
			};
			this._userPassword = null;
		} else {
			this.error = ErrorType.MISSING_INFORMATION;
		}
		this._userLogin = null;
		this._userPassword = null;
		this.user = null;
		return "login?faces-redirect=true";

	}
	
	public String changePasswordForm() {
		return "changePassword?faces-redirect=true";
	}
	
	public String registerRedirect() {
		return "register?faces-redirect=true";
	}
	
	//Dublicate Method from Account Bean to prevent "Detected cyclic reference to managedBean" Error
	public List<Account> getAccountList() {
		accList = new ArrayList<Account>();
		try {
			accList = Account.getAccounts(this.getLogin());
		} catch (Exception e) {
			this.error = ErrorType.DATABASE;
		}
		return accList;
	}
	
	public void redirectIfNotAuthenticated() throws IOException {
		FacesContext fc = FacesContext.getCurrentInstance();
		if(!isLoggedIn()) {
			fc.getExternalContext().redirect("login.xhtml");
		}	
	}

	// Getter and Setter
	
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
	
}
