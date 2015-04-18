package com.citsamex.service.action;

import java.io.Serializable;

import com.citsamex.service.db.ClausePo;

public class Clause implements Serializable {

	private static final long serialVersionUID = -914005453603609758L;

	public final static String STATUS_ACTIVE = "ACTIVE";
	public final static String STATUS_EXPIRED = "EXPIRED";

	private long id;
	private String no;
	private String content;
	private String catalog;
	private String status;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCatalogNo() {
		return this.catalog + "-" + this.no;
	}

	public ClausePo buildClausePo() {
		ClausePo po = new ClausePo();
		po.setId(id);
		po.setNo(no);
		po.setContent(content);
		po.setCatalog(catalog);
		po.setStatus(status);
		return po;
	}

	public void setPo(ClausePo po) {
		this.id = po.getId();
		this.no = po.getNo();
		this.content = po.getContent();
		this.catalog = po.getCatalog();
		this.status = po.getStatus();
	}
}
