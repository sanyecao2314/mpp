package com.citsamex.service.db;

import java.util.Calendar;

public class DBIHisPo extends MppBasePo {
	private long seq;
	private long id;
	private String dbiKey;
	private int dbiKeyValue;
	private String value1;
	private String value2;
	private String optional;
	private String relationId;
	private String createuserno;
	private Calendar createdatetime;
	private String updateuserno;
	private Calendar updatedatetime;
	private Calendar logdatetime;

	public long getSeq() {
		return seq;
	}

	public void setSeq(long seq) {
		this.seq = seq;
	}

	public int getDbiKeyValue() {
		return dbiKeyValue;
	}

	public void setDbiKeyValue(int dbiKeyValue) {
		this.dbiKeyValue = dbiKeyValue;
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

	public String getDbiKey() {
		return dbiKey;
	}

	public void setDbiKey(String dbiKey) {
		this.dbiKey = dbiKey;
	}

	public String getValue1() {
		return value1;
	}

	public void setValue1(String value1) {
		this.value1 = value1;
	}

	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}

	public String getOptional() {
		return optional;
	}

	public void setOptional(String optional) {
		this.optional = optional;
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
