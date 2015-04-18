package com.citsamex.service.db;

import java.util.Calendar;

import com.citsamex.annotation.valid.Indicator;
import com.citsamex.annotation.valid.MaxLength;

@SuppressWarnings("serial")
public class CustomerPo extends MppBasePo {
	private Long id;

	private String name;
	@MaxLength(min = 11, max = 11)
	@Indicator("numeric")
	private String gmax;

	private String policy;
	@Indicator("tmr")
	private String department;
	private String costcenter;
	@MaxLength(min = 0, max = 4000)
	private String remarks;
	private String createuserno;
	private String updateuserno;
	private Calendar createdatetime;
	private Calendar updatedatetime;
	private String status;
	private String statusRemark;

	public String getStatusRemark() {
		return statusRemark;
	}

	public void setStatusRemark(String statusRemark) {
		this.statusRemark = statusRemark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCostcenter() {
		return costcenter;
	}

	public void setCostcenter(String costcenter) {
		this.costcenter = costcenter;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGmax() {
		return gmax;
	}

	public void setGmax(String gmax) {
		this.gmax = gmax;
	}

	public String getPolicy() {
		return policy;
	}

	public void setPolicy(String policy) {
		this.policy = policy;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
