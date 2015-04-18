package com.citsamex.service.action;

import java.io.Serializable;
import java.util.Calendar;

import com.citsamex.service.db.CustomerPo;

public class Customer implements Serializable {

	private static final long serialVersionUID = 3251465679914321515L;
	private long id;
	private String name;
	private String[] gmaxes;
	private String[] clauses;
	private String[] departments;
	private String[] costcenters;
	private String clauseString;
	private String remarks;
	private String status;
	private String statusRemark;
	private String createuserno;
	private String updateuserno;
	private Calendar createdatetime;
	private Calendar updatedatetime;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getGmaxes() {
		return gmaxes;
	}

	public void setGmaxes(String[] gmaxes) {
		this.gmaxes = gmaxes;
	}

	public String[] getClauses() {
		return clauses;
	}

	public void setClauses(String[] clauses) {
		this.clauses = clauses;
	}

	public String[] getDepartments() {
		return departments;
	}

	public String[] getTmrParNo() {
		String div[] = new String[departments.length];
		for (int i = 0; i < departments.length; i++) {
			div[i] = departments[i].indexOf(";") == -1 ? "" : departments[i].substring(0, departments[i].indexOf(";"));
		}
		return div;
	}

	public void setDepartments(String[] departments) {
		this.departments = departments;
	}

	public String[] getCostcenters() {
		return costcenters;
	}

	public void setCostcenters(String[] costcenters) {
		this.costcenters = costcenters;
	}

	public String getClauseString() {
		return clauseString;
	}

	public void setClauseString(String clauseString) {
		this.clauseString = clauseString;
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

	public CustomerPo buildCustomerPo() {
		CustomerPo po = new CustomerPo();
		po.setId(id);
		po.setName(name);
		po.setStatus(status);
		// set policy combined with clause ids, separated by comma.
		String policy = "";
		if (clauses != null) {
			for (int i = 0; i < clauses.length; i++) {

				policy += clauses[i];
				if (i < clauses.length - 1)
					policy += ",";
			}
		}
		po.setPolicy(policy);

		String costcenter = "";
		if (costcenters != null) {
			for (int i = 0; i < costcenters.length; i++) {

				costcenter += costcenters[i];
				if (i < costcenters.length - 1)
					costcenter += ",";
			}
		}
		po.setCostcenter(costcenter);

		// set GMax no.
		String gmaxgroup = "";
		if (gmaxes != null) {
			for (int i = 0; i < gmaxes.length; i++) {

				gmaxgroup += gmaxes[i];
				if (i < gmaxes.length - 1)
					gmaxgroup += ",";
			}
		}
		po.setGmax(gmaxgroup);

		// set department.
		String departmentgroup = "";
		if (departments != null) {
			for (int i = 0; i < departments.length; i++) {

				departmentgroup += departments[i];
				if (i < departments.length - 1)
					departmentgroup += ",";
			}
		}
		po.setDepartment(departmentgroup);
		po.setRemarks(remarks);
		return po;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setPo(CustomerPo po) {

		this.id = po.getId();
		this.name = po.getName();
		this.status = po.getStatus();
		String department = po.getDepartment();

		if (department != null && !department.isEmpty()) {
			this.departments = department.split(",");
		} else {
			this.departments = new String[0];
		}

		String gmaxNo = po.getGmax();
		if (gmaxNo != null && !gmaxNo.isEmpty()) {
			this.gmaxes = gmaxNo.split(",");
		} else {
			this.gmaxes = new String[0];
		}

		String costcenter = po.getCostcenter();
		if (costcenter != null && !costcenter.isEmpty()) {
			this.costcenters = costcenter.split(",");
		} else {
			this.costcenters = new String[0];
		}

		remarks = po.getRemarks();

		String policy = po.getPolicy();

		clauseString = policy;
		if (policy != null && !policy.isEmpty()) {
			this.clauses = policy.split(",");

		} else {
			this.clauses = new String[0];
		}
	}
}
