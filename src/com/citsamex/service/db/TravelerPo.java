package com.citsamex.service.db;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.citsamex.annotation.UploadMapping;
import com.citsamex.annotation.Validate;
import com.citsamex.annotation.valid.Indicator;
import com.citsamex.annotation.valid.MaxLength;

@SuppressWarnings("serial")
public class TravelerPo extends MppBasePo {

	private long id;
	private long companyId;
	@Validate("traveler-companyName")
	private String companyName;
	@MaxLength(min = 10, max = 10)
	@Validate("traveler-travelerNo")
	@UploadMapping("ParNo")
	private String travelerNo;
	@MaxLength(min = 0, max = 28)
	@UploadMapping("旅客姓名")
	@Validate("traveler-travelerName")
	private String travelerName;
	@UploadMapping("旅客名")
	private String firstname;
	@UploadMapping("旅客姓")
	private String lastname;
	@UploadMapping("旅客英文名")
	private String firstnameEn;
	@UploadMapping("旅客英文姓")
	private String lastnameEn;
	@UploadMapping("旅客性别")
	private String gender;
	private String otherTitle;

	private String travelerType;
	private String maritalStatus;
	private String alias;
	@UploadMapping("旅客身份证")
	@Validate("traveler-icidNo")
	private String icidNo;
	private String insuranceNo;
	private String internet;
	@UploadMapping("旅客国家")
	private String country;
	@UploadMapping("旅客国籍1")
	@Validate("traveler-nationality")
	private String nationality1;
	@UploadMapping("旅客国籍2")
	private String nationality2;
	@UploadMapping("旅客生日")
	private Calendar birthday;
	private String security;
	@UploadMapping("旅客职位")
	@Validate("traveler-jobTitle")
	private String jobTitle;
	@UploadMapping("旅客部门")
	@Validate("traveler-department1")
	private String department1;
	@UploadMapping("TMRPar")
	private String department2;
	@Validate("traveler-employeeNo")
	@UploadMapping("旅客员工号")
	private String employeeNo;
	@Validate("traveler_costcenter")
	@UploadMapping("旅客成本中心")
	private String costcenter;
	@UploadMapping("旅客TR")
	@Validate("traveler-tr")
	private String tr;
	@UploadMapping("旅客ProjectNo")
	@Validate("traveler-projectNumber")
	private String projectNumber;
	@UploadMapping("旅客AccountNo")
	@Validate("traveler-accountNumber")
	private String accountNumber;
	
	private String refno;
	
	private String refno2;
	
	private String pppcmid;

	@Validate("traveler-unique")
	private String unique;

	private int reqStatement;
	private int feeBased;
	private String securityGroup;
	private String custCategory;
	private String custType;
	private int tcProfile;
	@UploadMapping("旅客港澳通行证号")
	private String exitPermitNo;
	@UploadMapping("旅客港澳通行证有效期")
	private Calendar exitPermitExpDate;

	@Indicator("passport")
	@UploadMapping("旅客通行证1号码")
	private String passport1;
	@UploadMapping("旅客通行证1类型")
	private String passport1Type;
	@UploadMapping("旅客通行证1发行日")
	private Calendar issueDate1;
	@UploadMapping("旅客通行证1有效期")
	private Calendar expiryDate1;
	@Indicator("passport")
	@UploadMapping("旅客通行证2号码")
	private String passport2;
	@UploadMapping("旅客通行证2类型")
	private String passport2Type;
	@UploadMapping("旅客通行证2发行日")
	private Calendar issueDate2;
	@UploadMapping("旅客通行证2有效期")
	private Calendar expiryDate2;
	private int ebilling;
	@UploadMapping("旅客状态")
	private String status;
	private String statusRemark;
	private String createuserno;
	private Calendar createdatetime;
	private String updateuserno;
	private Calendar updatedatetime;
	private String officeaddress;
	@Validate("traveler-title")
	@UploadMapping("旅客抬头")
	private String title;
	@Indicator("mobile")
	@UploadMapping("旅客手机")
	private String mobile;
	@Indicator("phone")
	@UploadMapping("旅客电话")
	private String officePhone;
	@UploadMapping("旅客传真")
	private String faxNo;
	@UploadMapping("旅客邮件")
	@Indicator("email")
	@Validate("traveler-email")
	private String email;
	@UploadMapping("旅客其他证件号")
	private String otherCard;
	@UploadMapping("旅客其他证件类型")
	private String otherCardType;
	@UploadMapping("旅客其他证件发行日")
	private Calendar otherCardIssueDate;
	@UploadMapping("旅客其他证件有效期")
	private Calendar otherCardExpiryDate;

	private String syncMessage;

	private String lockuser;
	private int lock;
	private int dirty;

	private String homephone;
	
	private boolean ignoreMasterUpdate;
	

	public String getRefno2() {
		return refno2;
	}

	public void setRefno2(String refno2) {
		this.refno2 = refno2;
	}

	public boolean isIgnoreMasterUpdate() {
		return ignoreMasterUpdate;
	}

	public void setIgnoreMasterUpdate(boolean ignoreMasterUpdate) {
		this.ignoreMasterUpdate = ignoreMasterUpdate;
	}

	public String getOtherTitle() {
		return otherTitle;
	}

	public void setOtherTitle(String otherTitle) {
		this.otherTitle = otherTitle;
	}

	public String getHomephone() {
		return homephone;
	}

	public void setHomephone(String homephone) {
		this.homephone = homephone;
	}

	private String divisionCode;

	public String getDivisionCode() {
		return divisionCode;
	}

	public void setDivisionCode(String divisionCode) {
		this.divisionCode = divisionCode;
	}

	private String travelerRelation;

	public String getTravelerRelation() {
		return travelerRelation;
	}

	public void setTravelerRelation(String travelerRelation) {
		this.travelerRelation = travelerRelation;
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

	public String getOtherCard() {
		return otherCard;
	}

	public void setOtherCard(String otherCard) {
		this.otherCard = otherCard;
	}

	public String getStatusRemark() {
		return statusRemark;
	}

	public void setStatusRemark(String statusRemark) {
		this.statusRemark = statusRemark;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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
		this.travelerNo = travelerNo == null ? null : travelerNo.toUpperCase();
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

	public void setBirthday(Calendar birthday) {
		this.birthday = birthday;
	}

	public void setBirthday(String birthday) {
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(dateformat.parse(birthday));
			this.birthday = c;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
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

	public String getDepartment2() {
		return department2;
	}

	public void setDepartment2(String department2) {
		this.department2 = department2;
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

	public void setExitPermitExpDate(Calendar exitPermitExpDate) {
		this.exitPermitExpDate = exitPermitExpDate;
	}

	public void setExitPermitExpDate(String exitPermitExpDate) {
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(dateformat.parse(exitPermitExpDate));
			this.exitPermitExpDate = c;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
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

	public void setIssueDate1(Calendar issueDate1) {
		this.issueDate1 = issueDate1;
	}

	public void setIssueDate1(String issueDate1) {
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(dateformat.parse(issueDate1));
			this.issueDate1 = c;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public Calendar getExpiryDate1() {
		return expiryDate1;
	}

	public void setExpiryDate1(Calendar expiryDate1) {
		this.expiryDate1 = expiryDate1;
	}

	public void setExpiryDate1(String expiryDate1) {
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(dateformat.parse(expiryDate1));
			this.expiryDate1 = c;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
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

	public void setIssueDate2(Calendar issueDate2) {
		this.issueDate2 = issueDate2;
	}

	public void setIssueDate2(String issueDate2) {
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(dateformat.parse(issueDate2));
			this.issueDate2 = c;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public Calendar getExpiryDate2() {
		return expiryDate2;
	}

	public void setExpiryDate2(Calendar expiryDate2) {
		this.expiryDate2 = expiryDate2;
	}

	public void setExpiryDate2(String expiryDate2) {
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(dateformat.parse(expiryDate2));
			this.expiryDate2 = c;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
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

	public String getLockuser() {
		return lockuser;
	}

	public void setLockuser(String lockuser) {
		this.lockuser = lockuser;
	}

	public int getLock() {
		return lock;
	}

	public void setLock(int lock) {
		this.lock = lock;
	}

	public String getUnique() {
		return "id=" + id + ";travelerName=" + noNull(travelerName) + ";email="
				+ noNull(email) + ";icidNo=" + noNull(icidNo) + ";passport1="
				+ noNull(passport1) + ";passport2=" + noNull(passport2)
				+ ";status=" + noNull(status) + ";travelerNo=" + travelerNo + ";";
	}

	public void setUnique(String unique) {
		this.unique = unique;
	}

	public String getRefno() {
		return refno;
	}

	public void setRefno(String refno) {
		this.refno = refno;
	}

	public String getPppcmid() {
		return pppcmid;
	}

	public void setPppcmid(String pppcmid) {
		this.pppcmid = pppcmid;
	}

	
}
