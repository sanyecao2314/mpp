<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<table width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td>
			<div class="logotext">Master Profile Platform</div></td>
		<td align="right">
			<a href="${pageContext.request.contextPath}/logout.action">Logout</a>
		</td>
	</tr>
	<tr>
		<td height="30"></td>
	</tr>

</table>
<table width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td style="padding-left: 100px">
			<table cellpadding="0" cellspacing="0">
				<tr>
					<td valign="middle" width="100">
						<div id="tab0" class="tab_out">
							<a id="t0_link"
								href="${pageContext.request.contextPath}/redirect.action?url=home"
								class="tab_a_out">HOME</a>
						</div></td>
					<td valign="middle" width="3"></td>
					<td valign="middle" width="100">
						<div id="tab1" class="tab_out">
							<a id="t1_link"
								href="${pageContext.request.contextPath}/redirect.action?url=cust"
								class="tab_a_out">Group</a>
						</div></td>
					<td valign="middle" width="3"></td>

					<td valign="middle" width="100">
						<div id="tab2" class="tab_out">
							<a id="t2_link"
								href="${pageContext.request.contextPath}/redirect.action?url=comp"
								class="tab_a_out">Comp(Bar)</a>
						</div></td>
					<td valign="middle" width="3"></td>

					<td valign="middle" width="100">
						<div id="tab3" class="tab_out">
							<a id="t3_link"
								href="${pageContext.request.contextPath}/redirect.action?url=trav"
								class="tab_a_out">Trav(Par)</a>
						</div></td>
					
					<td valign="middle" width="3"></td>
					
					<td valign="middle" width="100">
						<div id="tab4" class="tab_out">
							<a id="t4_link"
								href="${pageContext.request.contextPath}/redirect.action?url=prod"
								class="tab_a_out">Product</a>
						</div></td>
					
					<td valign="middle" width="3"></td>
					<td valign="middle" width="100">
						<div id="tab5" class="tab_out">
							<a id="t5_link"
								href="${pageContext.request.contextPath}/redirect.action?url=clause"
								class="tab_a_out">Clause</a>
						</div></td>
				</tr>
			</table></td>
	</tr>
	<tr>
		<td height="5" bgcolor="#266DBB" align="left"
			style="padding-left: 20px">
		</td>
	</tr>

</table>