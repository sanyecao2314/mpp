package com.citsamex.app.tag;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class ControllerTag extends BodyTagSupport {
	private static final long serialVersionUID = -1547209754589851124L;
	protected String id;
	protected String name;
	protected String onclick;
	protected String onmousedown;
	protected String onmouseup;
	protected String ondblclick;
	protected String css;
	protected String label;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getOnclick() {
		return onclick;
	}

	public String getCss() {
		return css;
	}

	public void setCss(String css) {
		this.css = css;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	public String getOnmousedown() {
		return onmousedown;
	}

	public void setOnmousedown(String onmousedown) {
		this.onmousedown = onmousedown;
	}

	public String getOnmouseup() {
		return onmouseup;
	}

	public void setOnmouseup(String onmouseup) {
		this.onmouseup = onmouseup;
	}

	public String getOndblclick() {
		return ondblclick;
	}

	public void setOndblclick(String ondblclick) {
		this.ondblclick = ondblclick;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	protected void output(Object output) {
		JspWriter out = pageContext.getOut();
		try {
			out.println(output);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
