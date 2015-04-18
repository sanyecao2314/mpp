package com.citsamex.service.db;

import java.util.Calendar;

@SuppressWarnings("serial")
public class ProductPo extends MppBasePo {

	private long id;
	private String productNo;
	private String productName;

	private String productNameSH;
	private String productNameGZ;
	private String productNameBJ;
	private String productType;
	private String productDesc;
	private String venderName;
	private String createuserno;
	private String updateuserno;
	private Calendar createdatetime;
	private Calendar updatedatetime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public String getVenderName() {
		return venderName;
	}

	public void setVenderName(String venderName) {
		this.venderName = venderName;
	}

	public String getProductNameSH() {
		return productNameSH;
	}

	public void setProductNameSH(String productNameSH) {
		this.productNameSH = productNameSH;
	}

	public String getProductNameGZ() {
		return productNameGZ;
	}

	public void setProductNameGZ(String productNameGZ) {
		this.productNameGZ = productNameGZ;
	}

	public String getProductNameBJ() {
		return productNameBJ;
	}

	public void setProductNameBJ(String productNameBJ) {
		this.productNameBJ = productNameBJ;
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
