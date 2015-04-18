<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html xmlns:v="urn:schemas-microsoft-com:vml">
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

	<script type='text/javascript'>
		var oriColor = "";
		var oriBgColor = "";

		$(function() {
			var width = $(document).width();
			var height = $(document).height();
			var obj={id:"${companyId}", name:"COMPANY", text:"${companyName}", path2:"0000", seq:1, color:"#000", bgColor:"#ccc", left:width/2, top:height/2};
			
			obj = createBlock(obj);
			obj.addClass("unselected");
				
			var $customerName = $("#customerName");
			$customerName.val("${companyName}");
			
			//lsCfg("${companyName}");
			lsCfgMain("${companyName}",obj.attr("id"));
			
			$(document).keydown(function(e){
				if(e.ctrlKey && e.which==13){
					transport("${companyName}");
				}
			});
		
			$("#fieldMapping").hide();
			$("#btnmap").css("fontSize","14pt");
			$("#btnmap").click(function(){
				$("#fieldMapping").toggle();
				
			});
			
			$(".fm").each(function(){
				$(this).blur(function(){
					services.call("customer.createElementMap",[$(this).val(),$(this).attr("id"),'${companyName}'],afUpd);
				});
			});
			
			services.call("customer.listElementMap",['${companyName}'], aflsElementMap);	
		});
		
		function aflsElementMap(list){
			for(var i=0;i<list.length;i++){
				$("#"+list[i].psfield).val(list[i].field);
			}
		}

	</script>
	
	<body>
		<input type="hidden" id="company" value="${companyName }" />
		<div class="logotext_small">
			<a style="color:#fff" href="${pageContext.request.contextPath}/redirect.action?url=home">Master Profile Platform</a>		
		</div>
		
		<a id="btnmap">+ Fields Mapping</a>
		<div id="fieldMapping">
			<table>
				<tr>
				<td>
					tr:
				</td>
				<td>
					<input type="text" id="USER_TR" class="fm">
				</td>
				</tr>
				
				<tr>
				<td>	
					empno:
				</td>
				<td>
					<input type="text" id="USER_EMPNO" class="fm">
				</td>
				</tr>
				
				<tr>
				<td>	
					jobtitle:
				</td>
				<td>
					<input type="text" id="UESR_JOBTITLE" class="fm">
				</td>
				</tr>
				
				<tr>
				<td>	
					department:
				</td>
				<td>
					<input type="text" id="USER_DEPARTMENT" class="fm">
				</td>
				</tr>
				
				<tr>
				<td>	
					costcenter:
				</td>
				<td>
					<input type="text" id="USER_COSTCENTER" class="fm">
				</td>
				</tr>
				
				<tr>
				<td>	
					tmrpar:
				</td>
				<td>
					<input type="text" id="USER_BAR_PATH" class="fm">
				</td>
				</tr>
				
				<tr>
				<td>	
					tmr1:
				</td>
				<td>
					<input type="text" id="USER_TMRLA" class="fm">
				</td>
				</tr>
			
				<tr>
				<td>	
					tmr2:
				</td>
				<td>
					<input type="text" id="USER_TMRLB" class="fm">
				</td>
				</tr>
			
				<tr>
				<td>	
					tmr3:
				</td>
				<td>
					<input type="text" id="USER_TMRLC" class="fm">
				</td>
				</tr>
			
				<tr>
				<td>	
					tmr4:
				</td>
				<td>
					<input type="text" id="USER_TMRLD" class="fm">
				</td>
				</tr>
			
				<tr>
				<td>	
					tmr5:
				</td>
				<td>
					<input type="text" id="USER_TMRLE" class="fm">
				</td>
				</tr>

			</table>
			
		</div>
		
		<div id="sub1">
		  <input type="hidden" value="" id="customerName"/> 
		</div>
		
		
	</body>
</html>