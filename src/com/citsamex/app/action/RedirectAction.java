package com.citsamex.app.action;

public class RedirectAction extends BaseAction {

	private static final long serialVersionUID = 5327918505035800791L;

	String url = "home";
	String form = "";

	public String getForm() {
		return form;
	}

	public void setForm(String form) {
		this.form = form;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String redirect() {
		return SUCCESS;
	}

	public String form() {
		return SUCCESS;
	}

}
