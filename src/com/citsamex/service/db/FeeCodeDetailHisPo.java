package com.citsamex.service.db;

import java.util.Calendar;

@SuppressWarnings("serial")
public class FeeCodeDetailHisPo extends MppBasePo {
	private long seq;
	private long id;
	private String groupId;
	private String validPeriod;
	private String productCode;
	private double moneyRangeFrom;
	private double moneyRangeTo;
	private double feeAmt;
	private double feePercent;
	// private Long feeCodeId;
	private String feeMethod;
	private String feeType;
	private String relationId;
	private String createuserno;
	private Calendar createdatetime;
	private String updateuserno;
	private Calendar updatedatetime;
	private Calendar logdatetime;
	private String custno;

	public String getCustno() {
		return custno;
	}

	public void setCustno(String custno) {
		this.custno = custno;
	}
	public long getSeq() {
		return seq;
	}

	public void setSeq(long seq) {
		this.seq = seq;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getValidPeriod() {
		return validPeriod;
	}

	public void setValidPeriod(String validPeriod) {
		this.validPeriod = validPeriod;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public double getMoneyRangeFrom() {
		return moneyRangeFrom;
	}

	public void setMoneyRangeFrom(double moneyRangeFrom) {
		this.moneyRangeFrom = moneyRangeFrom;
	}

	public double getMoneyRangeTo() {
		return moneyRangeTo;
	}

	public void setMoneyRangeTo(double moneyRangeTo) {
		this.moneyRangeTo = moneyRangeTo;
	}

	public double getFeeAmt() {
		return feeAmt;
	}

	public void setFeeAmt(double feeAmt) {
		this.feeAmt = feeAmt;
	}

	public double getFeePercent() {
		return feePercent;
	}

	public void setFeePercent(double feePercent) {
		this.feePercent = feePercent;
	}

	public String getFeeMethod() {
		return feeMethod;
	}

	public void setFeeMethod(String feeMethod) {
		this.feeMethod = feeMethod;
	}

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
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

	public Calendar getLogdatetime() {
		return logdatetime;
	}

	public void setLogdatetime(Calendar logdatetime) {
		this.logdatetime = logdatetime;
	}

}
