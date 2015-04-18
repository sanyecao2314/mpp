package com.citsamex.app.validate;

import com.citsamex.app.util.CommonUtil;

public abstract class AbstractValidator implements IValidator {
	protected String message;
	protected String attachMessage = "";
	
	protected CommonUtil uti = new CommonUtil();

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public void setMessage(String message) {
		this.message = message;
	}

    @Override
    public String getAttachMessage() {
        return attachMessage;
    }

    @Override
    public void setAttachMessage(String message) {
       this.attachMessage = message;
    }
	
	
}
