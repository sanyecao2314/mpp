package com.citsamex.service.db;

import java.util.Calendar;

@SuppressWarnings("serial")
public class GmaxNoPo extends MppBasePo {
	private long id;
	private String tmrId;
	private String parentName;
	private String gmaxNo;
	private String clientName;
	private String pricingMode;
	private String remark;
	private String relationId;
	private String createuserno;
	private String updateuserno;
	private Calendar createdatetime;
	private Calendar updatedatetime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTmrId() {
		return tmrId;
	}

	public void setTmrId(String tmrId) {
		this.tmrId = tmrId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getGmaxNo() {
		return gmaxNo;
	}

	public void setGmaxNo(String gmaxNo) {
		this.gmaxNo = gmaxNo;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getPricingMode() {
		return pricingMode;
	}

	public void setPricingMode(String pricingMode) {
		this.pricingMode = pricingMode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getUpdateuserno() {
		return updateuserno;
	}

	public void setUpdateuserno(String updateuserno) {
		this.updateuserno = updateuserno;
	}

	public Calendar getCreatedatetime() {
		return createdatetime;
	}

	public void setCreatedatetime(Calendar createdatetime) {
		this.createdatetime = createdatetime;
	}

	public Calendar getUpdatedatetime() {
		return updatedatetime;
	}

	public void setUpdatedatetime(Calendar updatedatetime) {
		this.updatedatetime = updatedatetime;
	}

}
