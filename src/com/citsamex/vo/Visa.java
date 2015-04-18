package com.citsamex.vo;

import java.util.Calendar;

import com.citsamex.annotation.OneMany;
import com.citsamex.annotation.PS;
import com.citsamex.annotation.TagName;
import com.citsamex.annotation.XPath;

@SuppressWarnings("serial")
@TagName("Visa")
@XPath("")
@PS("ARCUSVIS")
@OneMany(true)
public class Visa extends Base {

	private long id;
	private String country;
	private String visaType;
	private String visaEntry;
	private String visaNo;
	private Calendar visaidate;
	private Calendar visaedate;
	private String visarem;
	private String purpose;
	private String noOfEntry;
	private String relationId;
	private String createuserno;
	private Calendar createdatetime;
	private String updateuserno;
	private Calendar updatedatetime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public void setVisaidate(Calendar visaidate) {
		this.visaidate = visaidate;
	}

	public Calendar getVisaedate() {
		return visaedate;
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

	public String getNoOfEntry() {
		return noOfEntry;
	}

	public void setNoOfEntry(String noOfEntry) {
		this.noOfEntry = noOfEntry;
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
