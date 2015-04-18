package com.citsamex.vo;

import java.util.Calendar;

import com.citsamex.annotation.OneMany;
import com.citsamex.annotation.PS;
import com.citsamex.annotation.TagName;
import com.citsamex.annotation.XPath;

@SuppressWarnings("serial")
@TagName("Preference")
@XPath("")
@PS("ARCUSPRF")
@OneMany(false)
public class Preference extends Base {

	private long id;
	private String catalog;
	private String type;
	private String value1;
	private String value2;
	private String remark;

	// seat preference code
	private String seatcode;
	// seat preference(fee format)
	private String seatpref;
	private String seatremark;
	private String seatdesc;

	private String mealcode;
	private String mealpref;
	private String mealremark;
	private String mealdesc;

	private String airpref;
	private String carpref;
	private String hotelpref;
	private String ffppref;

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

	public String getSeatcode() {
		return seatcode;
	}

	public void setSeatcode(String seatcode) {
		this.seatcode = seatcode;
	}

	public String getSeatpref() {
		return seatpref;
	}

	public void setSeatpref(String seatpref) {
		this.seatpref = seatpref;
	}

	public String getSeatremark() {
		return seatremark;
	}

	public void setSeatremark(String seatremark) {
		this.seatremark = seatremark;
	}

	public String getSeatdesc() {
		return seatdesc;
	}

	public void setSeatdesc(String seatdesc) {
		this.seatdesc = seatdesc;
	}

	public String getMealcode() {
		return mealcode;
	}

	public void setMealcode(String mealcode) {
		this.mealcode = mealcode;
	}

	public String getMealpref() {
		return mealpref;
	}

	public void setMealpref(String mealpref) {
		this.mealpref = mealpref;
	}

	public String getMealremark() {
		return mealremark;
	}

	public void setMealremark(String mealremark) {
		this.mealremark = mealremark;
	}

	public String getMealdesc() {
		return mealdesc;
	}

	public void setMealdesc(String mealdesc) {
		this.mealdesc = mealdesc;
	}

	public String getAirpref() {
		return airpref;
	}

	public void setAirpref(String airpref) {
		this.airpref = airpref;
	}

	public String getCarpref() {
		return carpref;
	}

	public void setCarpref(String carpref) {
		this.carpref = carpref;
	}

	public String getHotelpref() {
		return hotelpref;
	}

	public void setHotelpref(String hotelpref) {
		this.hotelpref = hotelpref;
	}

	public String getFfppref() {
		return ffppref;
	}

	public void setFfppref(String ffppref) {
		this.ffppref = ffppref;
	}

}
