package com.citsamex.service.action;

import java.io.Serializable;

import com.citsamex.service.db.ProductPo;

public class Product implements Serializable {

	private static final long serialVersionUID = -4036418722000104045L;
	private long id;
	private String productNo;
	private String productName;
	private String productNameSH;
	private String productNameGZ;
	private String productNameBJ;
	private String productType;
	private String productDesc;
	private String venderName;

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

	public String getVenderName() {
		return venderName;
	}

	public void setVenderName(String venderName) {
		this.venderName = venderName;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public ProductPo buildPo() {
		ProductPo po = new ProductPo();
		po.setId(id);
		po.setProductNo(productNo);
		po.setProductName(productName);
		po.setProductDesc(productDesc);
		po.setVenderName(venderName);
		po.setProductType(productType);
		return po;
	}

	public void setPo(ProductPo po) {

		this.id = po.getId();
		this.productName = po.getProductName();
		this.productNameSH = po.getProductNameSH();
		this.productNameGZ = po.getProductNameGZ();
		this.productNameBJ = po.getProductNameBJ();
		this.productDesc = po.getProductDesc();
		this.productNo = po.getProductNo();
		this.venderName = po.getVenderName();
		this.productType = po.getProductType();

	}
}
