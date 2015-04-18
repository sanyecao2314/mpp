package com.citsamex.app.tag;

import javax.servlet.jsp.JspException;

import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

public class SelectTag extends ControllerTag {

	private static final long serialVersionUID = 8029957210299176604L;
	private String table;
	private String key;
	private String value;
	private String orderby;
	private String where;
	private Object selected;
	private String mustHave = "TRUE";
	private String css;
	private String onchange;
	private String id;
	private String style;

	@Override
	public int doStartTag() throws JspException {

		// String sql = "select ("
		// + key
		// + ") as key_, ("
		// + value
		// + ") as value_ from "
		// + table
		// + (where == null || where.trim().equals("") ? ""
		// : (" where " + where)) + " "
		// + (orderby == null ? "" : (" order by " + orderby));
		//
		// StringBuffer sb = new StringBuffer();
		// sb.append("<select id=\"" + id + "\" name=\"" + name + "\" css=\""
		// + css + "\" style=\"" + style + "\" onchange=\"" + onchange
		// + "\">");
		//
		// if (!"TRUE".equalsIgnoreCase(mustHave)) {
		// sb.append("<option value=\"\">");
		// //
		// sb.append(Application.getInstance().getText("COMMON.OPTION.NAME"));
		// sb.append("</option>");
		// }
		// ApplicationContext ac = WebApplicationContextUtils
		// .getWebApplicationContext(pageContext.getServletContext());
		// // IJdbcDao dao = (IJdbcDao) ac.getBean("jdbcdao");
		// List list = null;// dao.executeQuery(sql);
		// for (int i = 0; i < list.size(); i++) {
		// Map map = (Map) list.get(i);
		// sb.append("<option value=\"");
		// sb.append(map.get("key_"));
		// sb.append("\" ");
		// if (selected != null && !selected.toString().trim().equals("")) {
		// if (selected.toString().trim()
		// .equals(map.get("key_").toString().trim())) {
		// sb.append(" selected ");
		// }
		// }
		//
		// sb.append(" >");
		// sb.append(map.get("value_"));
		// sb.append("</option>");
		// }
		// sb.append("</select>");
		// output(sb.toString());
		return super.doStartTag();
	}

	public String getOnchange() {
		return onchange;
	}

	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}

	@Override
	public String getCss() {
		return css;
	}

	@Override
	public void setCss(String css) {
		this.css = css;
	}

	public String getMustHave() {
		return mustHave;
	}

	public void setMustHave(String mustHave) {
		this.mustHave = mustHave;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public Object getSelected() {
		return selected;
	}

	public void setSelected(Object selected) throws JspException {
		if (selected != null)
			this.selected = ExpressionEvaluatorManager.evaluate("selected", selected.toString(), Object.class,
					pageContext);
		else
			this.selected = "";
	}

	public String getWhere() {
		return where;
	}

	public void setWhere(String where) {
		this.where = where;
	}

	public String getOrderby() {
		return orderby;
	}

	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

}
