package com.citsamex.service.action;

import java.io.Serializable;
import java.util.Calendar;

import com.citsamex.service.Base;
import com.citsamex.service.db.CompanyPo;

public class Company extends Base implements Serializable {

	private static final long serialVersionUID = 7281068846243280196L;
	private long id;
	private long custId;
	private String gmaxNo;
	private String companyNo;
	private String companyName;
	private String alias;
	private int reqStmt;
	private int feeBased;
	private int intAirplus;
	private String securityGroup;
	private String custType;
	private int tcProfile;
	private String policy;
	private String costcenter;
	private String sales;
	private String officeaddress;
	private int ebilling;
	private int lowestFareReasonCode;
	private int purposeCode;
	private int tripDate;
	private int dateOfBooking;
	private int customerRefernce;
	private int tripRequistition;
	private int jobNumber;
	private int projectNumber;
	private int accountNumber;
	private int jobTitle;
	private int fareType;
	private int shortLongHaul;
	private int destination;
	private int iataFare;
	private String businessReqNo;
	private String internetAddress;
	private String insuranceNo;
	private String licenseNo;
	private String iataNumber;
	private String security;
	private Calendar acOpenDate;
	private Calendar acCloseDate;
	private String remarks;
	private String status;
	private String statusRemark;
	private String createuserno;
	private Calendar createdatetime;
	private String updateuserno;
	private Calendar updatedatetime;
	private int dirty;
	private String syncMessage;

	private String tmrRemark;
	
	private String ebillingPrevious;

	public String getTmrRemark() {
		return tmrRemark;
	}

	public void setTmrRemark(String tmrRemark) {
		this.tmrRemark = tmrRemark;
	}

	private int lock;
	private String lockuser;

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
		return statusRemark;
	}

	public void setStatusRemark(String statusRemark) {
		this.statusRemark = statusRemark;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAcOpenDateStr() {
		return uti.formatDate(acOpenDate);
	}

	public String getAcCloseDateStr() {
		return uti.formatDate(acCloseDate);
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public long getCustId() {
		return custId;
	}

	public void setCustId(long custId) {
		this.custId = custId;
	}

	public String getGmaxNo() {
		return gmaxNo;
	}

	public void setGmaxNo(String gmaxNo) {
		this.gmaxNo = gmaxNo;
	}

	public String getCompanyNo() {
		return companyNo;
	}

	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
	}

	public int getReqStmt() {
		return reqStmt;
	}

	public void setReqStmt(int reqStmt) {
		this.reqStmt = reqStmt;
	}

	public int getFeeBased() {
		return feeBased;
	}

	public void setFeeBased(int feeBased) {
		this.feeBased = feeBased;
	}

	public int getIntAirplus() {
		return intAirplus;
	}

	public void setIntAirplus(int intAirplus) {
		this.intAirplus = intAirplus;
	}

	public String getSecurityGroup() {
		return securityGroup;
	}

	public void setSecurityGroup(String securityGroup) {
		this.securityGroup = securityGroup;
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

	public String getPolicy() {
		return policy;
	}

	public void setPolicy(String policy) {
		this.policy = policy;
	}

	public String getCostcenter() {
		return costcenter;
	}

	public void setCostcenter(String costcenter) {
		this.costcenter = costcenter;
	}

	public String getSales() {
		return sales;
	}

	public void setSales(String sales) {
		this.sales = sales;
	}

	public String getOfficeaddress() {
		return officeaddress;
	}

	public void setOfficeaddress(String officeaddress) {
		this.officeaddress = officeaddress;
	}

	public int getEbilling() {
		return ebilling;
	}

	public void setEbilling(int ebilling) {
		this.ebilling = ebilling;
	}

	public int getTripDate() {
		return tripDate;
	}

	public void setTripDate(int tripDate) {
		this.tripDate = tripDate;
	}

	public int getDateOfBooking() {
		return dateOfBooking;
	}

	public void setDateOfBooking(int dateOfBooking) {
		this.dateOfBooking = dateOfBooking;
	}

	public int getCustomerRefernce() {
		return customerRefernce;
	}

	public void setCustomerRefernce(int customerRefernce) {
		this.customerRefernce = customerRefernce;
	}

	public int getTripRequistition() {
		return tripRequistition;
	}

	public void setTripRequistition(int tripRequistition) {
		this.tripRequistition = tripRequistition;
	}

	public int getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(int jobNumber) {
		this.jobNumber = jobNumber;
	}

	public int getProjectNumber() {
		return projectNumber;
	}

	public void setProjectNumber(int projectNumber) {
		this.projectNumber = projectNumber;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public int getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(int jobTitle) {
		this.jobTitle = jobTitle;
	}

	public int getFareType() {
		return fareType;
	}

	public void setFareType(int fareType) {
		this.fareType = fareType;
	}

	public int getShortLongHaul() {
		return shortLongHaul;
	}

	public void setShortLongHaul(int shortLongHaul) {
		this.shortLongHaul = shortLongHaul;
	}

	public int getDestination() {
		return destination;
	}

	public void setDestination(int destination) {
		this.destination = destination;
	}

	public int getIataFare() {
		return iataFare;
	}

	public void setIataFare(int iataFare) {
		this.iataFare = iataFare;
	}

	public String getBusinessReqNo() {
		return businessReqNo;
	}

	public void setBusinessReqNo(String businessReqNo) {
		this.businessReqNo = businessReqNo;
	}

	public String getInternetAddress() {
		return internetAddress;
	}

	public void setInternetAddress(String internetAddress) {
		this.internetAddress = internetAddress;
	}

	public String getInsuranceNo() {
		return insuranceNo;
	}

	public void setInsuranceNo(String insuranceNo) {
		this.insuranceNo = insuranceNo;
	}

	public String getLicenseNo() {
		return licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	public String getIataNumber() {
		return iataNumber;
	}

	public void setIataNumber(String iataNumber) {
		this.iataNumber = iataNumber;
	}

	public String getSecurity() {
		return security;
	}

	public void setSecurity(String security) {
		this.security = security;
	}

	public Calendar getAcOpenDate() {
		return acOpenDate;
	}

	public void setAcOpenDate(Calendar acOpenDate) {
		this.acOpenDate = acOpenDate;
	}

	public Calendar getAcCloseDate() {
		return acCloseDate;
	}

	public void setAcCloseDate(Calendar acCloseDate) {
		this.acCloseDate = acCloseDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public int getLowestFareReasonCode() {
		return lowestFareReasonCode;
	}

	public void setLowestFareReasonCode(int lowestFareReasonCode) {
		this.lowestFareReasonCode = lowestFareReasonCode;
	}

	public int getPurposeCode() {
		return purposeCode;
	}

	public void setPurposeCode(int purposeCode) {
		this.purposeCode = purposeCode;
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

	public CompanyPo buildPo() {
		CompanyPo po = new CompanyPo();
		uti.copyProperties(po, this);
		return po;
	}

	public void setPo(CompanyPo po) {
		uti.copyProperties(this, po);
	}

	public String getEbillingPrevious() {
		return ebillingPrevious;
	}

	public void setEbillingPrevious(String ebillingPrevious) {
		this.ebillingPrevious = ebillingPrevious;
	}


}
