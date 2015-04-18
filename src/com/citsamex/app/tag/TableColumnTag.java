package com.citsamex.app.tag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;

import org.apache.commons.beanutils.PropertyUtils;

import com.citsamex.app.Server;

public class TableColumnTag extends ControllerTag {

	private static final long serialVersionUID = 310720475592323207L;

	private String type;
	private String value;
	private String width;
	private String height;
	private String titleCss;
	private String label;
	private String params;
	private String maxLength;

	public String getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(String maxLength) {
		this.maxLength = maxLength;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	private TableTag table;

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public void setLabel(String label) {
		this.label = label;
	}

	public String getTitleCss() {
		return titleCss;
	}

	public void setTitleCss(String titleCss) {
		this.titleCss = titleCss;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) throws JspException {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public TableTag getTable() {
		return table;
	}

	public void setTable(TableTag table) {
		this.table = table;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	@Override
	public int doStartTag() throws JspException {

		table = (TableTag) getParent();
		if (table == null) {
			throw new JspException("table tag is null");
		}

		Object element = table.getElement();
		List<String> authedFields = table.getAuthedFields();

		if ("TRUE".equalsIgnoreCase(table.getNeedAuth())) {
			if (authedFields == null || !authedFields.contains(name)) {
				return EVAL_PAGE;
			}
		}
		WebControlWriter webControl = Server.controller.get(type);

		Map<String, Object> props = new HashMap<String, Object>();
		props.put("name", name);
		props.put("label", label);
		props.put("onclick", onclick);
		props.put("params", params);
		props.put("width", width);
		props.put("height", height);
		props.put("element", element);
		props.put("maxLength", maxLength);

		if (element == null) { // column header
			output("<td " + params + " class='" + titleCss + "' width='" + width + "' ><span>");
			output(webControl.createHeaderObject(props));
		} else { // data list
			Object propertyValue = null;
			try {
				propertyValue = PropertyUtils.getProperty(element, value);
			} catch (Exception e) {
				e.printStackTrace();
			}
			output("<td " + params + " class='" + css + "' ><span>");
			props.put("value", propertyValue == null ? "" : propertyValue.toString());
			output(webControl.createObject(props));
		}
		output("</span></td>");
		return EVAL_PAGE;
	}

}
