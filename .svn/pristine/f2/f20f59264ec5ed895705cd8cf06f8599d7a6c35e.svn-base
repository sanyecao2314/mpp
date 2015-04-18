<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<jsp:include page="/WEB-INF/jsp/common/head.jsp"></jsp:include>

	<script type="text/javascript" src="js/jquery.js"></script>
	<script src="js/jquery.ui.datepicker.js"></script>
	<script src="js/jquery.ui.widget.js"></script>
	<script src="js/jquery.ui.datepicker.js"></script>
	<script src="js/jquery.ui.core.js"></script>
	<script src="js/jquery.ui.position.js"></script>
	<script src="js/jquery.ui.autocomplete.js"></script>
	<script src='js/common.js'></script>
	
	<script type='text/javascript'
		src='${pageContext.request.contextPath}/dwr/interface/services.js'></script>
	<script type='text/javascript'
		src='${pageContext.request.contextPath}/dwr/engine.js'></script>

	<script type="text/javascript">
		$(document).ready(function(){
		  $("#tab3").addClass("tab_act").removeClass("tab_out");
		  
			$("#btn_back").click(function(){
				location.href='redirect.action?url=trav';
			});
		});
		
		$(function(){
			$("input:text").css("width","300px");
		});

	</script>
		
	<body>
		<jsp:include page="/WEB-INF/jsp/common/top.jsp"></jsp:include>
		<div id="statusshower">Loading...</div>
		<form action="createTraveler.action" method="post">
		<div id="error">${message_message }</div>
		<table width="100%" cellpadding="0" cellspacing="0">
			<tr>
				<td class="loginform" valign="top">
					<div class="info_text">Please fill in the traveler information.</div>
					<div>${custname_message }</div>
					
						<table cellpadding="4" cellspacing="4">

							<tr>
								<td class="label_bold">
									Company No:
								</td>
								<td>
									<input type="text" name="company.companyNo" id="companyNo">
								</td>
							</tr>

							<tr>
								<td colspan="4" align="right">
									<input type="button" id="btn_back" value="Back" class="btn" />
									<input type="submit" name="btn_next" value="Next" class="btn" />
								</td>
							</tr>
						</table>

				</td>
			</tr>
		</table>
		</form>	
	</body>
</html>



