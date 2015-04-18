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
		  $("#tab1").addClass("tab_act").removeClass("tab_out");
		  
			$("#returnCust").click(function(){
				location.href='redirect.action?url=cust';
			});
		});
		
	</script>
	<body>
		<jsp:include page="/WEB-INF/jsp/common/top.jsp"></jsp:include>
		
		<table width="100%" cellpadding="0" cellspacing="0">
			<tr>
			
				<td class="loginform" valign="top">
					<div class="info_text">Please fill in the customer information.</div>
					<div id="error">${message_message }</div>
					<form action="createCust.action" method="post">
					<input type="hidden" name="customer.status" id="customer-status" value="ACTIVE">
						<table cellpadding="4" cellspacing="0">
							<tr>
								<td class="label_bold">
									Customer name:
								</td>
								<td>
									<input type="text" name="customer.name">
								</td>
							</tr>
							<tr>
								<td colspan="2" align="right">
									<input type="button" id="returnCust" value="Back" class="btn" />
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



