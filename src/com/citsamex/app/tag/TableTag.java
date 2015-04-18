package com.citsamex.app.tag;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;

public class TableTag extends ControllerTag {

	private static final long serialVersionUID = 686949878784936271L;
	private Object input;
	private Iterator<Object> iterator;
	private Object element;
	private String width;
	private String height;
	private String titleCss;
	private String css1;
	private String css2;
	private String needAuth;
	private String username;
	private List<String> authedFields;
	private String pageCount;
	private String currentPage;
	private String ctrlCount;

	public String getCtrlCount() {
		return ctrlCount;
	}

	public void setCtrlCount(String ctrlCount) {
		this.ctrlCount = ctrlCount;
	}

	public String getPageCount() {
		return pageCount;
	}

	public void setPageCount(String pageCount) {
		this.pageCount = pageCount;
	}

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

	private int n;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNeedAuth() {
		return needAuth;
	}

	public void setNeedAuth(String needAuth) {
		this.needAuth = needAuth;
	}

	public String getTitleCss() {
		return titleCss;
	}

	public void setTitleCss(String titleCss) {
		this.titleCss = titleCss;
	}

	public String getCss1() {
		return css1;
	}

	public void setCss1(String css1) {
		this.css1 = css1;
	}

	public String getCss2() {
		return css2;
	}

	public void setCss2(String css2) {
		this.css2 = css2;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public Object getElement() {
		return element;
	}

	public void setElement(Object element) {
		this.element = element;
	}

	public Object getInput() {
		return input;
	}

	public void setInput(Object input) throws JspException {
		this.input = input;
	}

	public List<String> getAuthedFields() {
		return authedFields;
	}

	public void setAuthedFields(List<String> authedFields) {
		this.authedFields = authedFields;
	}

	@Override
	@SuppressWarnings("unchecked")
	public int doStartTag() throws JspException {
		if (input == null)
			return EVAL_PAGE;
		@SuppressWarnings("rawtypes")
		Collection c = (Collection) input;
		iterator = c.iterator();
		if ("TRUE".equalsIgnoreCase(needAuth)) {

		}
		output("<table cellspacing='1' width='" + width + "' class='" + css + "' height='" + height + "' >");
		output("<tr  class=\"" + titleCss + "\">");
		return EVAL_BODY_INCLUDE;
	}

	@Override
	public int doAfterBody() throws JspException {
		if (input == null)
			return EVAL_PAGE;
		output("<td></td></tr>");

		if (iterator.hasNext()) {
			element = iterator.next();
			if (n % 2 == 0)
				output("<tr class='" + css1 + "'>");
			else
				output("<tr class='" + css2 + "'>");
			n++;
			return EVAL_BODY_AGAIN;
		}
		output("</table>");
		element = null;

		int iCurrentPage = 0, iPageCount = 0;
		int nextPage = 0, prevPage = 0;
		try {
			iCurrentPage = Integer.parseInt(currentPage);
			iPageCount = Integer.parseInt(pageCount);
		} catch (Exception ex) {
		}
		if (iCurrentPage <= 1) {
			iCurrentPage = 1;
			prevPage = 1;
		} else {
			prevPage = iCurrentPage - 1;

		}
		if (iCurrentPage >= iPageCount) {
			iCurrentPage = iPageCount;
			nextPage = iPageCount;
		} else {
			nextPage = iCurrentPage + 1;
		}

		int iCtrlCount = 5;
		if (ctrlCount != null) {
			try {
				iCtrlCount = Integer.parseInt(ctrlCount);
			} catch (Exception ex) {
			}
		}
		if (iCtrlCount > 0) {
			if (iCtrlCount > iPageCount) {
				iCtrlCount = iPageCount;
			}
			String pageCtrl = "<table width='100%'><tr><td align='right'>"
					+ "<span class='pageCtrl'><a href='javascript:gotoPageNo(1)'>&lt;&lt;</a></span>&nbsp;"
					+ "<span class='pageCtrl'><a href='javascript:gotoPageNo(" + prevPage + ")'>&lt;</a></span>&nbsp;";// <
																														// previous;

			int start = 0;
			if (iCurrentPage > iCtrlCount / 2) {
				if ((iPageCount - iCurrentPage) > (iCtrlCount / 2)) {
					start = iCurrentPage - iCtrlCount / 2;
				} else {
					start = iCurrentPage - (iCtrlCount - (iPageCount - iCurrentPage)) + 1;
				}
			} else {
				start = 1;
			}
			for (int i = 0; i < iCtrlCount; i++) {
				if (start + i == iCurrentPage) {
					pageCtrl += "<span class='pageCtrl_bold'><a href='javascript:gotoPageNo(" + (iCurrentPage) + ")'>"
							+ (iCurrentPage) + "</a></span>&nbsp;";

				} else
					pageCtrl += "<span class='pageCtrl'><a href='javascript:gotoPageNo(" + (start + i) + ")'>"
							+ (start + i) + "</a></span>&nbsp;";
			}

			pageCtrl += "<span class='pageCtrl'><a href='javascript:gotoPageNo("
					+ nextPage
					+ ")'>&gt;</a></span>&nbsp;"// > next
					+ "<span class='pageCtrl'><a href='javascript:gotoPageNo(" + pageCount + ")'>&gt;&gt;</a></span>"
					+ "</td></tr></table>";

			output(pageCtrl);

			String script = "<script language='javascript'/> function gotoPageNo(pageNo) {"
					+ " document.forms[0].currentPage.value=pageNo;" + " document.forms[0].submit();} </script>";

			output(script);
		}
		return EVAL_PAGE;
	}
}
