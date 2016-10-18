package ch.bfh.awebt.login;

import java.io.Serializable;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class LocaleBean implements Serializable {

	private Locale locale;

	@PostConstruct
	public void init() {

		this.locale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
	}

	public Locale getLocale() {
		return this.locale;
	}

	public String getLanguage() {
		return this.locale.getLanguage();
	}

	public String setLanguage(String language, String action) {

		this.locale = new Locale(language);

		FacesContext.getCurrentInstance().getViewRoot().setLocale(this.locale);
		return String.format("%s?faces-redirect=true", action);
	}
}
