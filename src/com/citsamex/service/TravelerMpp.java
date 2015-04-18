package com.citsamex.service;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.citsamex.annotation.Validate;
import com.citsamex.annotation.valid.Indicator;
import com.citsamex.vo.Address;
import com.citsamex.vo.Contact;
import com.citsamex.vo.CreditCard;
import com.citsamex.vo.Discount;
import com.citsamex.vo.EBilling;
import com.citsamex.vo.Extra;
import com.citsamex.vo.FeeCode;
import com.citsamex.vo.FeeCodeDetail;
import com.citsamex.vo.Member;
import com.citsamex.vo.Preference;
import com.citsamex.vo.PurposeCode;
import com.citsamex.vo.ReasonCode;
import com.citsamex.vo.Traveler;

@SuppressWarnings("serial")
@XmlRootElement
public class TravelerMpp extends Traveler {

	private String action;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	private long id;
	private long companyId;
	@Validate("traveler-companyName")
	private String companyName;
	@Validate("traveler-travelerNo")
	private String travelerNo;
	@Validate("traveler-travelerName")
	private String travelerName;
	private String travelerNameEn;
	private String travelerNameCh;
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
	@Validate("traveler-jobTitle")
	private String jobTitle;
	@Validate("traveler-department1")
	private String department1;
	private String department2;
	@Validate("traveler-employeeNo")
	private String employeeNo;
	@Validate("traveler_costcenter")
	private String costcenter;
	@Validate("traveler-tr")
	private String tr;
	@Validate("traveler-projectNumber")
	private String projectNumber;
	@Validate("traveler-accountNumber")
	private String accountNumber;
	private int reqStatement;
	private int feeBased;
	private String securityGroup;
	private String custCategory;
	private String custType;
	private int tcProfile;
	private String exitPermitNo;
	private Calendar exitPermitExpDate;
	@Indicator("passport")
	private String passport1;
	private String passport1Type;
	private Calendar issueDate1;
	private Calendar expiryDate1;
	@Indicator("passport")
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
	@Validate("traveler-title")
	private String title;
	@Indicator("mobile")
	private String mobile;
	@Indicator("phone")
	private String officePhone;
	private String faxNo;
	@Indicator("email")
	private String email;
	private String otherCard;
	private String otherCardType;
	private Calendar otherCardIssueDate;
	private Calendar otherCardExpiryDate;
	private String syncMessage;
	private String lockuser;
	private int lock;
	private int dirty;
	private String homephone;
	private String newCompanyNo;
	private boolean updateContact;
	private boolean updateAddress;
	private boolean updateCreditCard;
	private boolean updateMember;
	private boolean updatePref;
	private String refno;
	private String refno2;
	

	public String getRefno2() {
		return refno2;
	}

	public void setRefno2(String refno2) {
		this.refno2 = refno2;
	}

	public boolean isUpdateContact() {
		return updateContact;
	}

	public void setUpdateContact(boolean updateContact) {
		this.updateContact = updateContact;
	}

	public boolean isUpdatePref() {
		return updatePref;
	}

	public void setUpdatePref(boolean updatePref) {
		this.updatePref = updatePref;
	}

	public boolean isUpdateAddress() {
		return updateAddress;
	}

	public void setUpdateAddress(boolean updateAddress) {
		this.updateAddress = updateAddress;
	}

	public boolean isUpdateCreditCard() {
		return updateCreditCard;
	}

	public void setUpdateCreditCard(boolean updateCreditCard) {
		this.updateCreditCard = updateCreditCard;
	}

	public boolean isUpdateMember() {
		return updateMember;
	}

	public void setUpdateMember(boolean updateMember) {
		this.updateMember = updateMember;
	}

	public List<Member> getMembers() {
		return members;
	}

	public void setMembers(List<Member> members) {
		this.members = members;
	}

	public String getNewCompanyNo() {
		return newCompanyNo;
	}

	public void setNewCompanyNo(String newCompanyNo) {
		this.newCompanyNo = newCompanyNo;
	}

	public String getTravelerNameEn() {
		return travelerNameEn;
	}

	public void setTravelerNameEn(String travelerNameEn) {
		this.travelerNameEn = travelerNameEn;
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

	public String getOtherTitle() {
		return otherTitle;
	}

	public void setOtherTitle(String otherTitle) {
		this.otherTitle = otherTitle;
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

	public Calendar getExpiryDate1() {
		return expiryDate1;
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

	public void setIssueDate2(Calendar issueDate2) {
		this.issueDate2 = issueDate2;
	}

	public Calendar getExpiryDate2() {
		return expiryDate2;
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

	private List<Contact> contacts = new ArrayList<Contact>();
	private List<Preference> pref = new ArrayList<Preference>();
	private List<Address> addresses = new ArrayList<Address>();
	private List<CreditCard> creditCards = new ArrayList<CreditCard>();
	private List<Discount> discounts = new ArrayList<Discount>();
	private List<EBilling> eBillings = new ArrayList<EBilling>();
	private List<Extra> extras = new ArrayList<Extra>();
	private List<FeeCode> feeCodes = new ArrayList<FeeCode>();
	private List<FeeCodeDetail> feeCodeDetails = new ArrayList<FeeCodeDetail>();
	private List<Member> members = new ArrayList<Member>();
	private List<PurposeCode> purposeCodes = new ArrayList<PurposeCode>();
	private List<ReasonCode> reasonCodes = new ArrayList<ReasonCode>();

	@XmlElement
	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	@XmlElement
	public List<Preference> getPref() {
		return pref;
	}

	public void setPref(List<Preference> pref) {
		this.pref = pref;
	}

	@XmlElement
	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	@XmlElement
	public List<CreditCard> getCreditCards() {
		return creditCards;
	}

	public void setCreditCards(List<CreditCard> creditCards) {
		this.creditCards = creditCards;
	}

	@XmlElement
	public List<Discount> getDiscounts() {
		return discounts;
	}

	public void setDiscounts(List<Discount> discounts) {
		this.discounts = discounts;
	}

	@XmlElement
	public List<EBilling> geteBillings() {
		return eBillings;
	}

	public void seteBillings(List<EBilling> eBillings) {
		this.eBillings = eBillings;
	}

	@XmlElement
	public List<Extra> getExtras() {
		return extras;
	}

	public void setExtras(List<Extra> extras) {
		this.extras = extras;
	}
	
	@XmlElement
	public List<FeeCode> getFeeCodes() {
		return feeCodes;
	}

	public void setFeeCodes(List<FeeCode> feeCodes) {
		this.feeCodes = feeCodes;
	}

	@XmlElement
	public List<FeeCodeDetail> getFeeCodeDetails() {
		return feeCodeDetails;
	}

	public void setFeeCodeDetails(List<FeeCodeDetail> feeCodeDetails) {
		this.feeCodeDetails = feeCodeDetails;
	}

	@XmlElement
	public List<Member> getMember() {
		return members;
	}

	public void setMember(List<Member> members) {
		this.members = members;
	}

	@XmlElement
	public List<PurposeCode> getPurposeCodes() {
		return purposeCodes;
	}

	public void setPurposeCodes(List<PurposeCode> purposeCodes) {
		this.purposeCodes = purposeCodes;
	}

	@XmlElement
	public List<ReasonCode> getReasonCodes() {
		return reasonCodes;
	}

	public void setReasonCodes(List<ReasonCode> reasonCodes) {
		this.reasonCodes = reasonCodes;
	}

	public String toXML() {
		StringBuffer buffer = new StringBuffer();
		ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
		XMLEncoder encoder = new XMLEncoder(byteArray);
		encoder.writeObject(this);
		encoder.flush();
		encoder.close();
		byte[] b = byteArray.toByteArray();

		try {
			String xml = new String(b, "UTF-8");
			buffer.append(xml);
			byteArray.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		return buffer.toString();
	}

	public static TravelerMpp parse(String xml) {
		TravelerMpp trav = null;
		try {
			JAXBContext context = JAXBContext.newInstance(TravelerMpp.class);
			Unmarshaller unmarshller = context.createUnmarshaller();
			trav = (TravelerMpp)unmarshller.unmarshal(new ByteArrayInputStream(xml.getBytes("UTF-8")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return trav;
	}

	public String getRefno() {
		return refno;
	}

	public void setRefno(String refno) {
		this.refno = refno;
	}

	
	

}
