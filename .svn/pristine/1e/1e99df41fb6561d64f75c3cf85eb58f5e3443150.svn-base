<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<jsp:include page="/WEB-INF/jsp/common/head.jsp"></jsp:include>
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
		  $("#tab5").addClass("tab_act").removeClass("tab_out");
		});
	</script>
	<body>
		<jsp:include page="/WEB-INF/jsp/common/top.jsp"></jsp:include>
		
		<table width="100%" cellpadding="0" cellspacing="0">
			<tr>
			
				<td class="loginform" valign="top">
					<div class="info_text">Please fill in the information of clause.</div>
					<div>${clauseno_message }</div>
					<form action="createClause.action" method="post">
						<input type="hidden" name="clause.status" value="ACTIVE">
						<table cellpadding="5" cellspacing="0">
							<tr>
								<td class="label_bold">
									Clause No:<input type="text" name="clause.catalog" size="4" maxlength="5">-<input type="text" name="clause.no" size="4" maxlength="5">
								</td>
							</tr>

							<tr>
								<td class="label_bold">
									Content:
								</td>
							</tr>
							
							<tr>
								<td>
									<textarea name="clause.content" cols="70" rows="7"></textarea>
								</td>
							</tr>
							
							<tr>
								<td>
									<input type="submit" name="btn_next" value="Save" class="btn" />
								</td>
							</tr>
						</table>
					</form>
				</td>
			</tr>
		</table>
	</body>
</html>



