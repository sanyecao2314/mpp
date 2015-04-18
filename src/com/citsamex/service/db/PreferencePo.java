package com.citsamex.service.db;

import java.util.Calendar;

import com.citsamex.annotation.UploadMapping;
import com.citsamex.annotation.Validate;

@SuppressWarnings("serial")
public class PreferencePo extends MppBasePo {

	private long id;
	@Validate("preCatalog")
	@UploadMapping("偏好目录")
	private String catalog;
	@UploadMapping("偏好类型")
	@Validate("preType")
	private String type;
	@UploadMapping("偏好值A")
	@Validate("preValue1")
	private String value1;
	@UploadMapping("偏好值B")
	private String value2;
	@UploadMapping("偏好备注")
	private String remark;
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

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
