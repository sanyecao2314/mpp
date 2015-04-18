package com.citsamex.app.validate.db;

import com.citsamex.service.db.MppBasePo;

@SuppressWarnings("serial")
public class ValidatorPo extends MppBasePo {

	private long id;
	private String name;
	private String classname;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

}
