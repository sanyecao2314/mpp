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
		  	$("#tab2").addClass("tab_act").removeClass("tab_out");
			$("#btn_back").click(function(){
				location.href='redirect.action?url=comp';
			});
			
			$("input:text").css("width","300px");
			$("select").css("width","300px");
			
			$( "#datepicker1" ).datepicker({dateFormat: 'yy-mm-dd'} );
			$( "#datepicker2" ).datepicker({dateFormat: 'yy-mm-dd'} );
			
		});
		
		//initialize function.
		$(function(){
			$("#gmaxes").change(function(){
				if($(this).val() == -1){
					$("#companyNo").val("");
					return;
				}
				var val = $("option:selected").val();
				val = "";
				$("#companyNo").val(val);
			});

		});
		
		$(function() {
			
			var costcenter=new Array;
			
			<c:forEach var="cc" varStatus="status" items="${customer.costcenters }">
				costcenter[${status.index}]= "${cc }";
			</c:forEach>
			
			$( "#company_costcenter" ).autocomplete({
				source: costcenter,
				autoFocus: true 
			});
			
			services.call("company.listCompanyOnline",[],afListCp);
			
		});

		function afListCp(cpnames){
			$( "#companyName" ).autocomplete({
				source: cpnames,
				autoFocus: true 
			});
			
		}
	</script>
		
	<body>
		<jsp:include page="/WEB-INF/jsp/common/top.jsp"></jsp:include>
		<form action="createComp.action" method="post">
		<input type="hidden" name="customer.name" id="customer-name" value="${customer.name }">
		<input type="hidden" name="company.custId" id="company-custId" value="${customer.id }">
		<input type="hidden" name="company.companyName" id="company-name" value="${customer.name }">
		<input type="hidden" name="company.policy" id="company-policy" value="${customer.clauseString }">
		<input type="hidden" name="company.remarks" id="company-remarks" value="${customer.remarks }">
		<input type="hidden" name="company.status" id="company-status" value="ACTIVE">
		
		<div id="error">${message_message }</div>
		
		<table width="100%" cellpadding="0" cellspacing="0">
			<tr>
				<td class="loginform" valign="top">
					<div class="info_text">Please fill in the company information.</div>
					<div>${custname_message }</div>
					
						<table cellpadding="4" cellspacing="4">
							<tr>
								<td class="label_bold">
									Gmax No:
								</td>
								<td>
									<select name="company.gmaxNo" id="gmaxes">
										<option value="-1">Please select ...</option>
										<c:forEach var="gmax" items="${customer.gmaxes }">
											<option value="${gmax }" ${company.gmaxNo==gmax?"selected":"" }>${gmax }</option>
										</c:forEach>
									</select>
								</td>
								<td class="label_bold">
									Company no:
								</td>
								<td>
									<input type="text" name="company.companyNo" id="companyNo" value="${company.companyNo }">
								</td>
							</tr>
							
							<tr>
								
								<td class="label_bold">
									Company name:
								</td>
								<td>
									<input type="text" name="company.companyName" id="companyName" value="${company.companyName }">
								</td>
								<td class="label_bold">
									Company alias:
								</td>
								<td>
									<input type="text" name="company.alias"
										value="${customer.name }" id="company-alias">
								</td>
								
							</tr>
							
							<tr>
								<td class="label_bold">
									Sales:
								</td>
								<td>
									<input type="text" name="company.sales" value="${company.sales }">
								</td>
								<td class="label_bold">
									Cost Center:
								</td>
								<td>
									<input type="text" id="company_costcenter" value="" name="company.costcenter" />
								</td>
								
							</tr>
							
							<tr>
								<td class="label_bold">
									Security Group:
								</td>
								<td>
									<select name="company.securityGroup" id="sg">
										<option value="-1">Please select ...</option>
									</select>
								</td>
								<td class="label_bold">
									Customer Type:
								</td>
								<td>
									<select name="company.custType" id="ccc">
										<option value="-1">Please select ...</option>
										<option value="Agent" ${company.custType=='Agent'?"selected":"" }>Agent</option>
										<option value="FIT" ${company.custType=='FIT'?"selected":"" }>FIT</option>
										<option value="Commercial" ${company.custType=='Commercial'?"selected":"" }>Commercial</option>
									</select>
								</td>
							</tr>
							
							<tr>
								<td colspan="4" height="2" style="background-color:#837492">
								</td>
							</tr>
							<tr>
								
								<td class="label_bold">
									Business Request No:
								</td>
								<td>
									<input type="text" name="company.businessReqNo" value="${company.businessReqNo }">
								</td>
								<td class="label_bold">
									Internet Address:
								</td>
								<td>
									<input type="text" name="company.internetAddress" value="${company.internetAddress }">
								</td>
							</tr>
							
							<tr>
								
								<td class="label_bold">
									Insurance No:
								</td>
								<td>
									<input type="text" name="company.insuranceNo" value="${company.insuranceNo }">
								</td>
								<td class="label_bold">
									License No:
								</td>
								<td>
									<input type="text" name="company.licenseNo" value="${company.licenseNo }">
								</td>
							</tr>
							
							<tr>
								<td class="label_bold">
									AC Open Date:
								</td>
								<td>
									<input type="text" id="datepicker1" name="company.acOpenDate" value="${company.acOpenDateStr }">
								</td>
								<td class="label_bold">
									AC Close Date:
								</td>
								<td>
									<input type="text" id="datepicker2" name="company.acCloseDate" value="${company.acCloseDateStr }">
								</td>
							</tr>
							
							<tr>
								<td class="label_bold">
									IATA Number:
								</td>
								<td>
									<input type="text" name="company.iataNumber" value="${company.iataNumber }">
								</td>
								<td class="label_bold">
									Security:
								</td>
								<td>
									<input type="text" name="company.security" value="${company.security }">
								</td>
							</tr>
							<tr>
								<td colspan="4" height="2" style="background-color:#837492">
								</td>
							</tr>
							<tr>
								<td colspan="4" align="left">
									<input type="checkbox" name="company.reqStmt" value="1" ${company.reqStmt==1?"checked":"" } />
									Request Statement.
								</td>
							</tr>
							
							<tr>
								<td colspan="4" align="left">
									<input type="checkbox" name="company.feeBased" value="1" ${company.feeBased==1?"checked":"" } />
									FEE BASED.
								</td>
							</tr>
							
							<tr>
								<td colspan="4" align="left">
									<input type="checkbox" name="company.intAirplus" value="1" ${company.intAirplus==1?"checked":"" } />
									INT AIRPLUS.
								</td>
							</tr>
							
							<tr>
								<td colspan="4" align="left">
									<input type="checkbox" name="company.tcProfile" value="1" ${company.tcProfile==1?"checked":"" } />
									T/C Profile
								</td>
							</tr>

							<tr>
								<td colspan="4" align="right">
									<input type="button" id="btn_back" value="Back" class="btn" />
									<input type="submit" name="btn_next" value="Save" class="btn" />
								</td>
							</tr>
						</table>

				</td>
			</tr>
		</table>
		</form>	
	</body>
</html>



