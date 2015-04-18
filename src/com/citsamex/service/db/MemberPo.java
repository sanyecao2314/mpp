package com.citsamex.service.db;

import java.util.Calendar;

import com.citsamex.annotation.UploadMapping;
import com.citsamex.annotation.Validate;

@SuppressWarnings("serial")
public class MemberPo extends MppBasePo {

	private long id;
	@UploadMapping("里程卡类型")
	@Validate("memberProdType")
	private String prodType;
	@UploadMapping("里程卡卡号")	
	@Validate("memberNo")
	private String memberNo;
	@UploadMapping("里程卡名称")
	private String memberName;
	@UploadMapping("里程卡代码")
	@Validate("memberCode")
	private String memberCode;
	@UploadMapping("里程卡发行日")
	private Calendar memberIssue;
	@UploadMapping("里程卡有效期")
	private Calendar memberExpire;
	@UploadMapping("里程卡备注")
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

	public String getProdType() {
		return prodType;
	}

	public void setProdType(String prodType) {
		this.prodType = prodType;
	}

	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public Calendar getMemberIssue() {
		return memberIssue;
	}

	public void setMemberIssue(Calendar memberIssue) {
		this.memberIssue = memberIssue;
	}

	public Calendar getMemberExpire() {
		return memberExpire;
	}

	public String getMemberExpireStr() {
		return uti.formatDate(memberExpire);
	}

	public String getMemberIssueStr() {
		return uti.formatDate(memberIssue);
	}

	public void setMemberExpire(Calendar memberExpire) {
		this.memberExpire = memberExpire;
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
