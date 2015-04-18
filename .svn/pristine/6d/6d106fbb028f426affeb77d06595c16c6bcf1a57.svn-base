package com.citsamex.service.db;

import java.util.Calendar;

import com.citsamex.annotation.UploadMapping;
import com.citsamex.annotation.valid.Indicator;
import com.citsamex.annotation.valid.MaxLength;

@SuppressWarnings("serial")
public class ContactPo extends MppBasePo {
	private long id;
	@UploadMapping("联系人名")
	private String firstname;
	@UploadMapping("联系人姓")
	private String lastname;
	@UploadMapping("联系人英文名")
	private String firstname_en;
	@UploadMapping("联系人英文姓")
	private String lastname_en;
	@MaxLength(min = 0, max = 28)
	private String fullname;

	@UploadMapping("联系人职位")
	private String title;
	@Indicator("phone")
	@UploadMapping("联系人家庭电话")
	private String homephone;
	@Indicator("phone")
	@UploadMapping("联系人办公电话")
	private String officephone;
	@UploadMapping("联系人传真")
	private String faxno;
	@Indicator("email")
	@UploadMapping("联系人邮件")
	private String email;
	private String relationId;
	private String createuserno;
	private Calendar createdatetime;
	private String updateuserno;
	private Calendar updatedatetime;
	@UploadMapping("联系人手机")
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFullname() {
		fullname = firstname + lastname;
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
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
