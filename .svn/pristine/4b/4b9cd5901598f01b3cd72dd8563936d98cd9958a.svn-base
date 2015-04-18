package com.citsamex.app.cfg;

import java.io.Serializable;

public class ElementMap implements Serializable {

	private long id;
	private String field;
	private String psfield;
	private String cpid;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getPsfield() {
		return psfield;
	}

	public void setPsfield(String psfield) {
		this.psfield = psfield;
	}

	public String getCpid() {
		return cpid;
	}

	public void setCpid(String cpid) {
		this.cpid = cpid;
	}

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof ElementMap) {
            ElementMap map = (ElementMap) obj;
            if (map.getCpid().equals(this.cpid) && map.getPsfield().equals(psfield)) {
                return true;
            }
            return false;
        } else {
            return false;
        }
    }
	
	

}
