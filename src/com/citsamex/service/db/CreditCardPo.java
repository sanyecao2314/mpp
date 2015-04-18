package com.citsamex.service.db;

import java.util.Calendar;

import com.citsamex.annotation.UploadMapping;
import com.citsamex.annotation.Validate;
import com.citsamex.annotation.valid.Indicator;
import com.citsamex.annotation.valid.MaxLength;

@SuppressWarnings("serial")
public class CreditCardPo extends MppBasePo {

	private long id;
	@UploadMapping("信用卡供应商")
	@Validate("cardVendor")
	private String vendor;
	@UploadMapping("信用卡卡号")
	@Validate("cardNo")
	private String cardNo;
	@UploadMapping("信用卡发卡机构")
	@Validate("cardInstitution")
	private String institution;
	@Validate("expiry")
	@Indicator("ccdDate")
	@UploadMapping("信用卡有效期")
	private String expiry;
	@UploadMapping("信用卡持卡人")
	private String holderName;
	@UploadMapping("信用卡类型")
	private String type;
	@UploadMapping("MerchantNo")
	private String merchantNo;
	@UploadMapping("信用卡账单类型")
	@Validate("cardBillType")
	private String billType;
	@UploadMapping("信用卡是否偏好")
	@Validate("cardPreference")
	private String preferenceCard;
	@MaxLength(min = 0, max = 50)
	@UploadMapping("信用卡备注")
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
	
	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getExpiry() {
		return expiry;
	}

	public void setExpiry(String expiry) {
		this.expiry = expiry;
	}

	public String getHolderName() {
		return holderName;
	}

	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public String getPreferenceCard() {
		return preferenceCard;
	}

	public void setPreferenceCard(String preferenceCard) {
		this.preferenceCard = preferenceCard;
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

	public String getExpiryStr() {
		return expiry;
	}

}
