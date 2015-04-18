package com.citsamex.service.db;

import java.util.Calendar;

import com.citsamex.annotation.UploadMapping;

@SuppressWarnings("serial")
public class VisaPo extends MppBasePo {

	private long id;
	@UploadMapping("VISA国家")
	private String country;
	@UploadMapping("VISA类型")
	private String visaType;
	@UploadMapping("VISAEntry")
	private String visaEntry;
	@UploadMapping("VISA号")
	private String visaNo;
	@UploadMapping("VISA发行日")
	private Calendar visaidate;
	@UploadMapping("VISA有效期")
	private Calendar visaedate;
	@UploadMapping("VISA备注")
	private String visarem;
	@UploadMapping("Purpose")
	private String purpose;
	@UploadMapping("NoOfEntry")
	private String noOfEntry;
	private String relationId;
	private String createuserno;
	private Calendar createdatetime;
	private String updateuserno;
	private Calendar updatedatetime;
	private String custno;

	public String getCustno() {
		return custno;
	}

	public void setCustno(String custno) {
		this.custno = custno;
	}
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNoOfEntry() {
		return noOfEntry;
	}

	public void setNoOfEntry(String noOfEntry) {
		this.noOfEntry = noOfEntry;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getVisaType() {
		return visaType;
	}

	public void setVisaType(String visaType) {
		this.visaType = visaType;
	}

	public String getVisaEntry() {
		return visaEntry;
	}

	public void setVisaEntry(String visaEntry) {
		this.visaEntry = visaEntry;
	}

	public String getVisaNo() {
		return visaNo;
	}

	public void setVisaNo(String visaNo) {
		this.visaNo = visaNo;
	}

	public Calendar getVisaidate() {
		return visaidate;
	}

	public String getVisaidateStr() {
		return uti.formatDate(visaidate);
	}

	public void setVisaidate(Calendar visaidate) {
		this.visaidate = visaidate;
	}

	public Calendar getVisaedate() {
		return visaedate;
	}

	public String getVisaedateStr() {
		return uti.formatDate(visaedate);
	}

	public void setVisaedate(Calendar visaedate) {
		this.visaedate = visaedate;
	}

	public String getVisarem() {
		return visarem;
	}

	public void setVisarem(String visarem) {
		this.visarem = visarem;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
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