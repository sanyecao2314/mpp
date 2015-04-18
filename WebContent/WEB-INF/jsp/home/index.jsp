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
	
	<style>
		#tonyhead {
			font-weight: bold;
			font-size: 12px;
			color: #fff;
			background-color: #000;
		}
	
	</style>
	
	<script type="text/javascript">
		$(document).ready(function(){
		  $("#tab0").addClass("tab_act").removeClass("tab_out");
		  
		  $("#btnOk").click(function(){
			  var companyName = $("#companyName").val();
			  
			  if(companyName == '') {
				  alert("Please input the company's name");
				  return;
			  }
			  
			  $("#form1").submit();
		  });
		});
		
		var customers = [];
		
		$(function() {
			$("#statusshower").show();
			//services.call("customer.listCustomer", [], afterLoad);
		});

		var afterLoad = function(obj) {
			if(!obj.success) {
				alert("some errors occur while processing request.");
				return;
			}
			for(var i=0; i<obj.data.length; i++) {
				customers[i] = obj.data[i].name;
			}
			$("#statusshower").hide();
			
			$( "#companyName" ).autocomplete({
				source: customers,
				autoFocus: true 
			});
		};
		
		$(function() {
			$("#companyName").keyup(function(){
				if($(this).val()=='iwannatony'){
					$("#div2").show();
					$("#div1").hide();
					$("#searchbox").focus();
					$("#searchbox").val("");
				}
			}); 
			
			$("#btnSearch").click(function(){
				services.queryTony($("#searchbox").val(),afSearch);
			});
		});
		
		function afSearch(list) {
			
			$("#tonylist tr[id!=tonyhead]").remove();
			
			for(var i=0; i<list.length; i++) {
				var obj = list[i];
				var $tr=$("<tr>");
				if(i%2==0){
					$tr.css("color",'#000');
					$tr.css("backgroundColor",'#eee');
				}else{
					$tr.css("color",'#000');
					$tr.css("backgroundColor",'#fff');
				}
				$("#tonylist").append($tr);
				var $td=$("<td>");
				var $input=$("<input>",{val:obj.USER_ID,readonly:"readonly"});
				$input.css("width","50px");
				$input.css("border","1px solid #eee");
				$td.append($input);
				$tr.append($td);
				
				var $td=$("<td>");
				$td.append(obj.USER_CP_ID);
				$tr.append($td);
				
				var $td=$("<td>");
				$td.append(obj.USER_STATUS);
				$tr.append($td);
				
				var $td=$("<td>");
				$td.append(obj.USER_CUSTNO);
				$tr.append($td);
				
				var $td=$("<td>");
				$td.append(obj.USER_LOGNAME);
				$tr.append($td);
				
				var $td=$("<td>");
				$td.append(obj.USER_PASSWORD);
				$tr.append($td);
				
				var $td=$("<td>");
				$td.append(obj.USER_NAME_CN);
				$tr.append($td);
				
				var $td=$("<td>");
				$td.append(obj.USER_NAME_EN);
				$tr.append($td);
				
				var $td=$("<td>");
				$td.append(obj.USER_EMAIL);
				$tr.append($td);
				
				var $td=$("<td>");
				$td.append(obj.USER_EMPNO);
				$tr.append($td);

				var $td=$("<td>");
				$td.append(obj.USER_TR);
				$tr.append($td);

				var $td=$("<td>");
				$td.append(obj.USER_MOBILE);
				$tr.append($td);
				
				var $td=$("<td>");
				$td.append(obj.USER_PHONENO);
				$tr.append($td);
				

			}
		}
		
	</script>
	
	<body>
		<jsp:include page="/WEB-INF/jsp/common/top.jsp"></jsp:include>
		<form id="form1" action="cfgCust.action" method="post">
			<table id="searchBar" width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td class="loginform" valign="top">
						
						<div id="div1">
							公司名称：
							<input id="companyName" name="companyName" type="text" value="" style="width:250px;border: 1px solid #ccc" />
							<input id="btnOk" type="button" value="Go" />
						</div>
						<div id="div2" style="display:none;">
							<input id="command" type="hidden" value="" />
							<input id="searchbox" type="text" value="" style="width:500px;border: 1px solid #ccc" />
							<input id="btnSearch" type="button" value="Search" />
							
							<table id="tonylist">
								<tr id="tonyhead">
									<td>
										USER_ID
									</td>
									
									<td>
										USER_CP_ID
									</td>
									<td>
										USER_STATUS
									</td>
									<td>
										USER_CUSTNO
									</td>
									<td>
										USER_LOGNAME
									</td>
									<td>
										USER_PASSWORD
									</td>
									<td>
										USER_NAME_CN
									</td>
									<td>
										USER_NAME_EN
									</td>
									<td>
										USER_EMAIL
									</td>
									<td>
										USER_EMPNO
									</td>
									<td>
										USER_TR
									</td>
									<td>
										USER_MOBILE
									</td>
									
									<td>
										USER_PHONENO
									</td>
									

								</tr>
							</table>
							
						</div>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>



