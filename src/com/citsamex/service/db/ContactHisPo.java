package com.citsamex.service.db;

import java.util.Calendar;

@SuppressWarnings("serial")
public class ContactHisPo extends MppBasePo {
	private long seq;
	private long id;
	private String firstname;
	private String lastname;
	private String firstname_en;
	private String lastname_en;
	private String title;
	private String homephone;
	private String officephone;
	private String faxno;
	private String email;
	private String relationId;
	private String createuserno;
	private Calendar createdatetime;
	private String updateuserno;
	private Calendar updatedatetime;
	private Calendar logdatetime;
	private String mobile;

	private String custno;

	public String getCustno() {
		return custno;
	}

	public void setCustno(String custno) {
		this.custno = custno;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	private Integer secretary;

	public Integer getSecretary() {
		return secretary;
	}

	public void setSecretary(Integer secretary) {
		this.secretary = secretary;
	}

	public long getSeq() {
		return seq;
	}

	public void setSeq(long seq) {
		this.seq = seq;
	}

	public Calendar getLogdatetime() {
		return logdatetime;
	}

	public void setLogdatetime(Calendar logdatetime) {
		this.logdatetime = logdatetime;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname_en() {
		return firstname_en;
	}

	public void setFirstname_en(String firstname_en) {
		this.firstname_en = firstname_en;
	}

	public String getLastname_en() {
		return lastname_en;
	}

	public void setLastname_en(String lastname_en) {
		this.lastname_en = lastname_en;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHomephone() {
		return homephone;
	}

	public void setHomephone(String homephone) {
		this.homephone = homephone;
	}

	public String getOfficephone() {
		return officephone;
	}

	public void setOfficephone(String officephone) {
		this.officephone = officephone;
	}

	public String getFaxno() {
		return faxno;
	}

	public void setFaxno(String faxno) {
		this.faxno = faxno;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRelationId() {
		return relationId;
	}

	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}

	public String getCreateuserno() {
		return createuserno;
	}

	public void setCreateuserno(String createuserno) {
		this.createuserno = createuserno;
	}

	public Calendar getCreatedatetime() {
		return createdatetime;
	}

	public void setCreatedatetime(Calendar createdatetime) {
		this.createdatetime = createdatetime;
	}

	public String getUpdateuserno() {
		return updateuserno;
	}

	public void setUpdateuserno(String updateuserno) {
		this.updateuserno = updateuserno;
	}

	public Calendar getUpdatedatetime() {
		return updatedatetime;
	}

	public void setUpdatedatetime(Calendar updatedatetime) {
		this.updatedatetime = updatedatetime;
	}

}
