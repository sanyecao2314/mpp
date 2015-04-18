package com.citsamex.service.action;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Calendar;

import com.citsamex.service.Base;
import com.citsamex.service.db.TravelerPo;
import com.citsamex.vo.Contact;

public class Traveler extends Base implements Serializable {

	private static final long serialVersionUID = 5751124484458294638L;
	private long id;
	private long companyId;
	private String companyName;
	private String travelerNo;
	private String travelerName;
	private String firstname;
	private String lastname;
	private String firstnameEn;
	private String lastnameEn;
	private String gender;
	private String otherTitle;// is doctor.
	private String travelerType;
	private String maritalStatus;
	private String alias;
	private String icidNo;
	private String insuranceNo;
	private String internet;
	private String country;
	private String nationality1;
	private String nationality2;
	private Calendar birthday;
	private String security;
	private String jobTitle;
	private String department1;
	private String department2;
	private String department2bak;
	private String employeeNo;
	private String costcenter;
	private String tr;
	private String projectNumber;
	private String accountNumber;
	private int reqStatement;
	private int feeBased;
	private String securityGroup;
	private String custCategory;
	private String custType;
	private int tcProfile;
	private String exitPermitNo;
	private Calendar exitPermitExpDate;
	private String passport1;
	private String passport1Type;
	private Calendar issueDate1;
	private Calendar expiryDate1;

	private String otherCard;
	private String otherCardType;
	private Calendar otherCardIssueDate;
	private Calendar otherCardExpiryDate;

	private String passport2;
	private String passport2Type;
	private Calendar issueDate2;
	private Calendar expiryDate2;
	private int ebilling;

	private String status;
	private String statusRemark;
	private String createuserno;
	private Calendar createdatetime;
	private String updateuserno;
	private Calendar updatedatetime;
	private String officeaddress;

	private String title;
	private String mobile;
	private String officePhone;
	private String faxNo;
	private String email;

	private int dirty;
	private String syncMessage;
	private int lock;
	private String lockuser;

	private String homephone;
	private String tmr1;
	private String tmr2;
	private String tmr3;
	private String tmr4;
	private String tmr5;

	private String travelerNameEn;
	private String travelerNameCh;

	private String divisionCode;

	public String getDivisionCode() {
		return divisionCode;
	}

	public void setDivisionCode(String divisionCode) {
		this.divisionCode = divisionCode;
	}

	public String getTravelerNameEn() {
		return travelerNameEn;
	}

	public void setTravelerNameEn(String travelerNameEn) {
		this.travelerNameEn = travelerNameEn;
	}

	public String getOtherTitle() {
		return otherTitle;
	}

	public void setOtherTitle(String otherTitle) {
		this.otherTitle = otherTitle;
	}

	public String getTravelerNameCh() {
		return travelerNameCh;
	}

	public void setTravelerNameCh(String travelerNameCh) {
		this.travelerNameCh = travelerNameCh;
	}

	public String getHomephone() {
		return homephone;
	}

	public void setHomephone(String homephone) {
		this.homephone = homephone;
	}

	public String getTmr1() {
		return tmr1;
	}

	public void setTmr1(String tmr1) {
		this.tmr1 = tmr1;
	}

	public String getTmr2() {
		return tmr2;
	}

	public void setTmr2(String tmr2) {
		this.tmr2 = tmr2;
	}

	public String getTmr3() {
		return tmr3;
	}

	public void setTmr3(String tmr3) {
		this.tmr3 = tmr3;
	}

	public String getTmr4() {
		return tmr4;
	}

	public void setTmr4(String tmr4) {
		this.tmr4 = tmr4;
	}

	public String getTmr5() {
		return tmr5;
	}

	public void setTmr5(String tmr5) {
		this.tmr5 = tmr5;
	}

	private String travelerRelation;

	public String getTravelerRelation() {
		return travelerRelation;
	}

	public void setTravelerRelation(String travelerRelation) {
		this.travelerRelation = travelerRelation;
	}

	public int getLock() {
		return lock;
	}

	public void setLock(int lock) {
		this.lock = lock;
	}

	public String getLockuser() {
		return lockuser;
	}

	public void setLockuser(String lockuser) {
		this.lockuser = lockuser;
	}

	public String getSyncMessage() {
		return syncMessage;
	}

	public void setSyncMessage(String syncMessage) {
		this.syncMessage = syncMessage;
	}

	public int getDirty() {
		return dirty;
	}

	public void setDirty(int dirty) {
		this.dirty = dirty;
	}

	public String getStatusRemark() {
		if (statusRemark != null)
			statusRemark = statusRemark.replaceAll("\n", "");
		return statusRemark;
	}

	public void setStatusRemark(String statusRemark) {
		this.statusRemark = statusRemark;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOtherCard() {
		return otherCard;
	}

	public void setOtherCard(String otherCard) {
		this.otherCard = otherCard;
	}

	public String getOtherCardType() {
		return otherCardType;
	}

	public void setOtherCardType(String otherCardType) {
		this.otherCardType = otherCardType;
	}

	public Calendar getOtherCardIssueDate() {
		return otherCardIssueDate;
	}

	public void setOtherCardIssueDate(Calendar otherCardIssueDate) {
		this.otherCardIssueDate = otherCardIssueDate;
	}

	public Calendar getOtherCardExpiryDate() {
		return otherCardExpiryDate;
	}

	public void setOtherCardExpiryDate(Calendar otherCardExpiryDate) {
		this.otherCardExpiryDate = otherCardExpiryDate;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getOfficePhone() {
		return officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	public String getFaxNo() {
		return faxNo;
	}

	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOfficeaddress() {
		return officeaddress;
	}

	public void setOfficeaddress(String officeaddress) {
		this.officeaddress = officeaddress;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getTravelerNo() {
		return travelerNo;
	}

	public void setTravelerNo(String travelerNo) {
		this.travelerNo = travelerNo;
	}

	public String getTravelerName() {
		return travelerName;
	}

	public void setTravelerName(String travelerName) {
		this.travelerName = travelerName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getTravelerType() {
		return travelerType;
	}

	public void setTravelerType(String travelerType) {
		this.travelerType = travelerType;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getIcidNo() {
		return icidNo;
	}

	public void setIcidNo(String icidNo) {
		this.icidNo = icidNo;
	}

	public String getInsuranceNo() {
		return insuranceNo;
	}

	public void setInsuranceNo(String insuranceNo) {
		this.insuranceNo = insuranceNo;
	}

	public String getInternet() {
		return internet;
	}

	public void setInternet(String internet) {
		this.internet = internet;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getNationality1() {
		return nationality1;
	}

	public void setNationality1(String nationality1) {
		this.nationality1 = nationality1;
	}

	public String getNationality2() {
		return nationality2;
	}

	public void setNationality2(String nationality2) {
		this.nationality2 = nationality2;
	}

	public Calendar getBirthday() {
		return birthday;
	}

	public String getBirthdayStr() {
		return uti.formatDate(birthday);
	}

	public String getOtherCardExpiryDateStr() {
		return uti.formatDate(otherCardExpiryDate);
	}

	public String getOtherCardIssueDateStr() {
		return uti.formatDate(otherCardIssueDate);
	}

	public void setBirthday(Calendar birthday) {
		this.birthday = birthday;
	}

	public String getSecurity() {
		return security;
	}

	public void setSecurity(String security) {
		this.security = security;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getDepartment1() {
		return department1;
	}

	public void setDepartment1(String department1) {
		this.department1 = department1;
	}
	
	/**
	 * in powersuit department2 represent bar number in mpp
	 * @return
	 */
	public String getDepartment2() {
		return department2;
	}

	public void setDepartment2(String department2) {
		this.department2 = department2;
	}

	public String getDepartment2bak() {
		return department2bak;
	}

	public void setDepartment2bak(String department2bak) {
		this.department2bak = department2bak;
	}

	public String getEmployeeNo() {
		return employeeNo;
	}

	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}

	public String getCostcenter() {
		return costcenter;
	}

	public void setCostcenter(String costcenter) {
		this.costcenter = costcenter;
	}

	public String getTr() {
		return tr;
	}

	public void setTr(String tr) {
		this.tr = tr;
	}

	public String getProjectNumber() {
		return projectNumber;
	}

	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public int getReqStatement() {
		return reqStatement;
	}

	public void setReqStatement(int reqStatement) {
		this.reqStatement = reqStatement;
	}

	public int getFeeBased() {
		return feeBased;
	}

	public void setFeeBased(int feeBased) {
		this.feeBased = feeBased;
	}

	public String getSecurityGroup() {
		return securityGroup;
	}

	public void setSecurityGroup(String securityGroup) {
		this.securityGroup = securityGroup;
	}

	public String getCustCategory() {
		return custCategory;
	}

	public void setCustCategory(String custCategory) {
		this.custCategory = custCategory;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public int getTcProfile() {
		return tcProfile;
	}

	public void setTcProfile(int tcProfile) {
		this.tcProfile = tcProfile;
	}

	public String getExitPermitNo() {
		return exitPermitNo;
	}

	public void setExitPermitNo(String exitPermitNo) {
		this.exitPermitNo = exitPermitNo;
	}

	public Calendar getExitPermitExpDate() {
		return exitPermitExpDate;
	}

	public String getExitPermitExpDateStr() {
		return uti.formatDate(exitPermitExpDate);
	}

	public void setExitPermitExpDate(Calendar exitPermitExpDate) {
		this.exitPermitExpDate = exitPermitExpDate;
	}

	public String getPassport1() {
		return passport1;
	}

	public void setPassport1(String passport1) {
		this.passport1 = passport1;
	}

	public Calendar getIssueDate1() {
		return issueDate1;
	}

	public String getIssueDate1Str() {
		return uti.formatDate(issueDate1);
	}

	public void setIssueDate1(Calendar issueDate1) {
		this.issueDate1 = issueDate1;
	}

	public Calendar getExpiryDate1() {
		return expiryDate1;
	}

	public String getExpiryDate1Str() {
		return uti.formatDate(expiryDate1);
	}

	public void setExpiryDate1(Calendar expiryDate1) {
		this.expiryDate1 = expiryDate1;
	}

	public String getPassport2() {
		return passport2;
	}

	public void setPassport2(String passport2) {
		this.passport2 = passport2;
	}

	public Calendar getIssueDate2() {
		return issueDate2;
	}

	public String getIssueDate2Str() {
		return uti.formatDate(issueDate2);
	}

	public void setIssueDate2(Calendar issueDate2) {
		this.issueDate2 = issueDate2;
	}

	public Calendar getExpiryDate2() {
		return expiryDate2;
	}

	public String getExpiryDate2Str() {
		return uti.formatDate(expiryDate2);
	}

	public void setExpiryDate2(Calendar expiryDate2) {
		this.expiryDate2 = expiryDate2;
	}

	public String getPassport1Type() {
		return passport1Type;
	}

	public void setPassport1Type(String passport1Type) {
		this.passport1Type = passport1Type;
	}

	public String getPassport2Type() {
		return passport2Type;
	}

	public void setPassport2Type(String passport2Type) {
		this.passport2Type = passport2Type;
	}

	public int getEbilling() {
		return ebilling;
	}

	public void setEbilling(int ebilling) {
		this.ebilling = ebilling;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getFirstnameEn() {
		return firstnameEn;
	}

	public void setFirstnameEn(String firstnameEn) {
		this.firstnameEn = firstnameEn;
	}

	public String getLastnameEn() {
		return lastnameEn;
	}

	public void setLastnameEn(String lastnameEn) {
		this.lastnameEn = lastnameEn;
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

	public TravelerPo buildPo() {
		TravelerPo po = new TravelerPo();
		uti.copyProperties(po, this);
		return po;
	}

	public void setPo(TravelerPo po) {
		uti.copyProperties(this, po);
	}

	public static void main(String[] args) {
		Field[] fields = Contact.class.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			System.out.print(fields[i].getName() + "=\"\" ");
		}
	}

	public String getUnique() {
		return "id=" + id + ";travelerName=" + noNull(travelerName) + ";email="
				+ noNull(email) + ";icidNo=" + noNull(icidNo) + ";passport1="
				+ noNull(passport1) + ";passport2=" + noNull(passport2)
				+ ";status=" + noNull(status) + ";travelerNo=" + travelerNo + ";";
	}

}
