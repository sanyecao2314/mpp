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
	<script src="js/jQuery.Hz2Py-min.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
		  	$("#tab3").addClass("tab_act").removeClass("tab_out");
			$("#btn_back").click(function(){
				location.href='redirect.action?url=comp';
			});
			
			$("input:text").css("width","300px");
			$("select").css("width","300px");
			
			$( "#traveler-birthday" ).datepicker({dateFormat: 'yy-mm-dd'} );
			$( "#traveler-exitPermitExpDate" ).datepicker({dateFormat: 'yy-mm-dd'} );
			$( "#traveler-issueDate1" ).datepicker({dateFormat: 'yy-mm-dd'} );
			$( "#traveler-expiryDate1" ).datepicker({dateFormat: 'yy-mm-dd'} );
			$( "#traveler-issueDate2" ).datepicker({dateFormat: 'yy-mm-dd'} );
			$( "#traveler-expiryDate2" ).datepicker({dateFormat: 'yy-mm-dd'} );
		});
		
		//initialize function.
		$(function(){
			$("#gmaxes").change(function(){
				$("#travelerNo").val($("option:selected").val());
			});

		});
		
		$(function() {
			
			$("#traveler-firstname").blur(function(){
				$(this).val($(this).val().toUpperCase());
				var reg=/[\u4E00-\u9FA5]/g;
				if (reg.test($(this).val())) {//chinese
					//$("#traveler-travelerName").val($("#traveler-firstname").val()+$("#traveler-lastname").val());
				
					var firstname = $('#traveler-firstname').val();
					var pyfirstname = "";
					
					for(i = 0; i < firstname.length; i++){

						$('#traveler-firstnameEn').val(firstname.charAt(i));
						pyfirstname += $('#traveler-firstnameEn').toPinyin();

						if(i < firstname.length - 1){
							pyfirstname += " ";
						}
					}
					
					$('#traveler-firstnameEn').val(pyfirstname.toUpperCase());
				} else {
					$('#traveler-firstnameEn').val($('#traveler-firstname').val().toUpperCase());
				}
				$('#traveler-travelerName').val($("#traveler-lastname").val()+" "+$("#traveler-firstname").val());
			});
			
			$("#traveler-firstnameEn").blur(function(){
				$(this).val($(this).val().toUpperCase());
				$('#traveler-travelerName').val($("#traveler-lastname").val()+" "+$("#traveler-firstname").val());
			});
			
			$("#traveler-lastnameEn").blur(function(){
				$(this).val($(this).val().toUpperCase());
				$('#traveler-travelerName').val($("#traveler-lastname").val()+" "+$("#traveler-firstname").val());
			});
			
			$("#traveler-lastname").blur(function(){
				$(this).val($(this).val().toUpperCase());
				var reg=/[\u4E00-\u9FA5]/g;
				if (reg.test($(this).val())) {//chinese
				//$("#traveler-travelerName").val($("#traveler-firstname").val()+$("#traveler-lastname").val());
				
					var lastname = $('#traveler-lastname').val();
					var pyLastname = "";
					for(i = 0;i<lastname.length;i++){
						$('#traveler-lastnameEn').val(lastname.charAt(i));
						pyLastname += $('#traveler-lastnameEn').toPinyin();
						if(i < lastname.length-1){
							pyLastname += " ";
						}
					}
					$('#traveler-lastnameEn').val(pyLastname.toUpperCase());
				} else {
					$('#traveler-lastnameEn').val($('#traveler-lastname').val().toUpperCase());
				}
				$('#traveler-travelerName').val($("#traveler-lastname").val()+" "+$("#traveler-firstname").val());
			});
			
		});
		
		
		
		$(function() {
			//$('#btn_next').attr("type", "button");
			$('#btn_next').click(function(){
				<c:if test="${traveler.travelerType!='traveler' }">		
					if($("#traveler_travelerRelation").val()=='') {
						alert("Please input relationship.");
						return;
					}
				</c:if>		
				$("#form1").submit();
			});
		});
		
		
	</script>
		
	<body>
		<jsp:include page="/WEB-INF/jsp/common/top.jsp"></jsp:include>
		<form id="form1" action="createTraveler.action" method="post">
		<input type="hidden" name="customer.name" id="customer-name" value="${customer.name }">
		<input type="hidden" name="company.custId" id="company-custId" value="${customer.id }">
		<input type="hidden" name="company.companyName" id="company-name" value="${customer.name }">
		<input type="hidden" name="company.policy" id="company-policy" value="${customer.clauseString }">
		<input type="hidden" name="company.companyNo" id="company-companyNo" value="${company.companyNo }">
		<input type="hidden" name="traveler.companyId" id="traveler-companyId" value="${company.id }">
		<input type="hidden" name="traveler.companyName" id="traveler-companyName" value="${company.companyName }">
		<input type="hidden" name="traveler.travelerType" id="traveler_travelerType" value="${traveler.travelerType }">
		<input type="hidden" name="traveler.status" id="traveler-status" value="ACTIVE">
		<div id="error">${message_message }</div>
		<table width="100%" cellpadding="0" cellspacing="0">
			<tr>
				<td class="loginform" valign="top">
					<div class="info_text">Please fill in the traveler information.</div>
					<div>${traveler_message }</div>
					
						<table cellpadding="4" cellspacing="4">
							<tr>
								<td class="label_bold">
									Traveler no:
								</td>
								<td>
									<input type="text" name="traveler.travelerNo" id="traveler-travelerNo" value="${traveler.travelerNo }">
								</td>
								
								<td class="label_bold">
									Traveler Name:
								</td>
								<td>
									<input type="text" style="background-color: #EEE" name="traveler.travelerName" id="traveler-travelerName" readonly="readonly" value="${traveler.travelerName }">
								</td>
							</tr>
							
							<tr>
								<td class="label_bold">
									Last Name:
								</td>
								<td>
									<input type="text" name="traveler.lastname" id="traveler-lastname" value="${traveler.lastname }">
								</td>
								<td class="label_bold">
									First Name:
								</td>
								<td>
									<input type="text" name="traveler.firstname" id="traveler-firstname" value="${traveler.firstname }">
								</td>			
							</tr>
							
							<tr>
								<td class="label_bold">
									Last Name(E):
								</td>
								<td>
									<input type="text" name="traveler.lastnameEn" id="traveler-lastnameEn" value="${traveler.lastnameEn }">
								</td>
								<td class="label_bold">
									First Name(E):
								</td>
								<td>
									<input type="text" name="traveler.firstnameEn" id="traveler-firstnameEn" value="${traveler.firstnameEn }">
								</td>
							</tr>
							<c:if test="${traveler.travelerType!='traveler' }">
							<tr>
								<td class="label_bold">
									Relation
								</td>
								<td>
									<input type="text" id="traveler_travelerRelation" name="traveler.travelerRelation"/>									
								</td>
								<td colspan="2">
									
								</td>
							</tr>
							</c:if>
							<tr>
								<td colspan="4" align="right">
									<input type="button" id="btn_back" value="Back" class="btn" />
									<input type="button" id="btn_next" name="btn_next" value="Save" class="btn" />
								</td>
							</tr>
							
						</table>

				</td>
			</tr>
		</table>
		</form>	
	</body>
</html>



