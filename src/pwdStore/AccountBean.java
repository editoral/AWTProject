package pwdStore;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import pwdStore.LoginBean;
import model.Account;


@ManagedBean(name="accountBean", eager=true)
@SessionScoped
public class AccountBean implements Serializable {
	
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
	
    @ManagedProperty(value="#{loginBean}")
    private LoginBean loginBean;

	private ErrorType error;
	
	private Integer id = null;
	private String title;
	private String website;
	private String username;
	private String password;
	private String iv;
	private String salt;
	private ArrayList<Account> accList;
	
	public String createForm() {
		id = null;
		return "account?faces-redirect=true";
	}
	
	//handles save operation, after save button on account.xhtml was clicked
	//Expects member values to be properly set by post request from client
	//IV and Salt are stored in database, reason is the used client side algorithm
	public String save() {
		Account acc = new Account();
		acc.setTitle(title);
		acc.setWebsite(website);
		acc.setUsername(username);
		acc.setPassword(password);
		acc.setIv(iv);
		acc.setSalt(salt);
		System.out.println(iv);
		acc.setUser(loginBean.getLogin());
		try {
			if(id != null) {
				acc.setId(id);
				acc.update();
			} else {
				acc.save();
			}
			this.title = null;
			this.website = null;
			this.username = null;
			this.password = null;
			this.iv = null;
			this.salt = null;
			this.id = 0;
			return "accountList?faces-redirect=true";
		} catch (Exception e) {
			this.error = ErrorType.DATABASE;
		}
		return "";
	}
	
	//Edits an existing account, works similar to the save operation, only with preset id
	public String edit() {
		//pattern to fetch context params, so id can be retrieved
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
		int id = Integer.parseInt(params.get("action"));
		try {
			Account acc = Account.getAccount(id);
			this.id = acc.getId();
			this.title = acc.getTitle();
			this.website = acc.getWebsite();
			this.username = acc.getUsername();
			this.password = acc.getPassword();
			this.iv = acc.getIv();
			this.salt = acc.getSalt();
		} catch (Exception e) {
			this.error = ErrorType.DATABASE;
		}
		return "account?faces-redirect=true";
	}
	
	//Very self explanatory: deletes a given account based on id
	public void delete() {
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
		int id = Integer.parseInt(params.get("action"));
		try {
			Account acc = Account.getAccount(id);
			acc.delete();
		} catch (Exception e) {
			this.error = ErrorType.DATABASE;
		}
	}
	
	//List all accounts for accountList.xhtml
	public List<Account> getAccountList() {
		accList = new ArrayList<Account>();
		try {
			accList = Account.getAccounts(loginBean.getLogin());
		} catch (Exception e) {
			this.error = ErrorType.DATABASE;
		}
		return accList;
	}

	
	// Getter and Setter

	public ErrorType getError() {
		return error;
	}
	public void setError(ErrorType error) {
		this.error = error;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIv() {
		return iv;
	}
	public void setIv(String iv) {
		this.iv = iv;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}

}