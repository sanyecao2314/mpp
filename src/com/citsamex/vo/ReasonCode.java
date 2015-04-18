package com.citsamex.vo;

import java.util.Calendar;

import com.citsamex.annotation.TagName;
import com.citsamex.annotation.XPath;

@SuppressWarnings("serial")
@TagName("ReasonCode")
@XPath("")
public class ReasonCode extends Base {
	private long id;
	private String reasonCode;

	private String description;
	private String relationId;
	private String createuserno;
	private String updateuserno;
	private Calendar createdatetime;
	private Calendar updatedatetime;
	private String productCode;

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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