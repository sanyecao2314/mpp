package com.citsamex.service.db;

import java.io.Serializable;

import com.citsamex.service.Base;

@SuppressWarnings("serial")
public class DataMapping extends Base implements Serializable {
	private long id;
	private String custno;
	private String action;
	private String catalog;
	private String mpp;
	private String ps;
	private String axo;
	private String bs;
	private String sys1;
	private String sys2;
	private String sys3;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCustno() {
		return custno;
	}

	public void setCustno(String custno) {
		this.custno = custno;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public String getMpp() {
		return mpp;
	}

	public void setMpp(String mpp) {
		this.mpp = mpp;
	}

	public String getPs() {
		return ps;
	}

	public void setPs(String ps) {
		this.ps = ps;
	}

	public String getAxo() {
		return axo;
	}

	public void setAxo(String axo) {
		this.axo = axo;
	}

	public String getBs() {
		return bs;
	}

	public void setBs(String bs) {
		this.bs = bs;
	}

	public String getSys1() {
		return sys1;
	}

	public void setSys1(String sys1) {
		this.sys1 = sys1;
	}

	public String getSys2() {
		return sys2;
	}

	public void setSys2(String sys2) {
		this.sys2 = sys2;
	}

	public String getSys3() {
		return sys3;
	}

	public void setSys3(String sys3) {
		this.sys3 = sys3;
	}

}
