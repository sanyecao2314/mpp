package com.citsamex.vo;

import java.util.Calendar;

import com.citsamex.annotation.OneMany;
import com.citsamex.annotation.PS;
import com.citsamex.annotation.TagName;
import com.citsamex.annotation.XPath;

@SuppressWarnings("serial")
@TagName("Preference")
@XPath("")
@PS("ARCUSPRF_DTL")
@OneMany(true)
public class Preference2 extends Base {

	private long id;
	private String type;
	private String pref_avoid;
	private String prefcode;
	private String prefname;
	private String remark;
	private String relationId;
	private String createuserno;
	private Calendar createdatetime;
	private String updateuserno;
	private Calendar updatedatetime;
	private String catalog;
	private String value1;
	private String value2;

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPref_avoid() {
		return pref_avoid;
	}

	public void setPref_avoid(String pref_avoid) {
		this.pref_avoid = pref_avoid;
	}

	public String getPrefcode() {
		return prefcode;
	}

	public void setPrefcode(String prefcode) {
		this.prefcode = prefcode;
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

	public String getPrefname() {
		return prefname;
	}

	public void setPrefname(String prefname) {
		this.prefname = prefname;
	}

}
