<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<jsp:include page="/WEB-INF/jsp/common/head.jsp"></jsp:include>
<script type="text/javascript" src="js/jquery.js"></script>
<script src="js/jquery.ui.core.js"></script>
<script src="js/jquery.ui.widget.js"></script>
<script src="js/jquery.ui.tabs.js"></script>
<script src="js/jquery.ui.mouse.js"></script>
<script src="js/jquery.ui.sortable.js"></script>
<script src="js/jquery.ui.position.js"></script>
<script src="js/jquery.ui.autocomplete.js"></script>
<script src="js/jquery.ui.datepicker.js"></script>
<script src="js/common.js"></script>
<script src="js/company_define.js"></script>

<script type='text/javascript'
	src='${pageContext.request.contextPath}/dwr/interface/services.js'></script>
<script type='text/javascript'
	src='${pageContext.request.contextPath}/dwr/engine.js'></script>
<style>

#sortable1 {
	list-style-type: none;
	margin: 0;
	padding: 0;
	width: 300px;
}

#sortable1 li {
	margin: 0 5px 5px 5px;
	padding: 5px;
	font-size: 12px;
	height: 1.0em;
}

html>body #sortable1 li {
	height: 1.0em;
	line-height: 1.0em;
}

.ui-state-highlight {
	height: 1.0em;
	line-height: 1.0em;
}

#sortable2 {
	list-style-type: none;
	margin: 0;
	padding: 0;
	width: 500px;
}

#sortable2 li {
	margin: 0 5px 5px 5px;
	padding: 5px;
	font-size: 12px;
	height: 1.0em;
}

html>body #sortable2 li {
	height: 1.0em;
	line-height: 1.0em;
}

.ui-state-highlight {
	height: 1.0em;
	line-height: 1.0em;
}
</style>

<script type="text/javascript">
	var compId = "${company.id}";
	var destId = "C${company.id}";
	var ADDR_SEL = "${company.officeaddress}";
	var clauses = "${company.policy}";
	var clauseStr="${company.policy}";
	var COPY_ACTION = "";
	
	$(document).ready(function() {
		var idx = 0;
		$("#tabs").tabs({"selected":${tabSelected}});
		
		$("#homeAddress").remove();
		
		//$("#btnUpload").click(function(){
		//	$("#upload-form").dialog( "open" );
		//});
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
	});
	
	$(function(){
		var dirty = "${company.dirty}";
		
		for(j = 0; j < citsamexSystems.length; j++) {
			
			$status_div=$("#sync_status_" + citsamexSystems[j]);
			if($status_div.length == 0){
				$status_div=$("<div/>",{id:"sync_status_" + citsamexSystems[j]});
				$status_div.html(citsamexSystemsName[j]);
				$("#colorBar").append($status_div);
			}
			
			$status_div.addClass("sync_status_block2");
			
			if((dirty & citsamexSystems[j]) == citsamexSystems[j]) {
				$status_div.css("backgroundColor", citsamexSystemsColor[j]);
			} else {
				$status_div.css("backgroundColor", "#DDD");
			}
		}
	});
	
	function validate1() {
		if($("#company_remarks").val().length >= 4000){
			alert("Length of remark should be less than 4000.");
			return;
		}
		
		$v=$("#tabs-1 .invalid");
		for(i = 0; i < $v.length; i++) {
			$v.removeClass("invalid");
		}
		$v=$("#tabs-1 .invalid_message");
		for(i = 0; i < $v.length; i++) {
			$v.remove();
		}
		
		$validObject=$("#tabs-1 .validator");
		
		var objs = "";
		for(i = 0; i < $validObject.length; i++) {
			objs += $validObject.eq(i).attr("id") + "=" + $validObject.eq(i).val() + "<sp/>";
		}
		
		services.call("traveler.validate", [objs], afValid1);
	}
	
	function afValid1(obj1) {
		//SPLIT WITH (", OR ;")
		if(obj1 ==null || obj1.data==null) {
			return;
		}
		
		obj=obj1.data;
		
		if(obj.length!=0) {
			for(i=0;i<obj.length;i++){
				var objId = obj[i].substring(0,obj[i].indexOf("="));
				var message = obj[i].substring(obj[i].indexOf("=")+1);
				
				$("#" + objId).addClass("invalid");
				$div=$("<div/>");
				$div.addClass("invalid_message");
				$div.html(message);
				$("#tabs-1").append($div);
				$div.position({
					of: $("#"+objId).parent(),
					my: "center bottom",
					at: "center top",
					offset: "",
					collision: "flip flip"
				});
			}
		} else {
			$("#statusshower").show();
			$("#form1").submit();
		}
		services.call("traveler.resetValidator", []);
		//$("#statusshower").show(1000);
	}
	
	var act = "";
	var params;
	
	function validate2(action,parameters) {
		act=action;
		params=parameters;
		var tabIndex = $("#tabs").tabs("option", "selected");
		
		var tabId="#comp"+(tabIndex);
		var invalidClass=tabId + " .invalid";
		var invalidMessageClass=tabId + " .invalid_message";
		var validatorClass=tabId + " .validator";
		
		$v=$(invalidClass);
		for(i = 0; i < $v.length; i++) {
			$v.removeClass("invalid");
		}
		$v=$(invalidMessageClass);
		for(i = 0; i < $v.length; i++) {
			$v.remove();
		}
		
		$validObject=$(validatorClass);
		var objs = "";
		for(i = 0; i < $validObject.length; i++) {
			objs += $validObject.eq(i).attr("id") + "=" + $validObject.eq(i).val() + "<sp/>";
		}
		
		services.call("traveler.validate", [objs], afValid2);
		
	}
	
	function afValid2(obj1) {
		//SPLIT WITH (", OR ;")
		if(obj1 ==null || obj1.data==null) {
			return;
		}
		
		obj=obj1.data;

		var tabIndex = $("#tabs").tabs("option", "selected");	
		var tabId="#comp"+(tabIndex);
		
		if(obj.length!=0) {
			for(i=0;i<obj.length;i++){
				var objId = obj[i].substring(0, obj[i].indexOf("="));
				var message = obj[i].substring(obj[i].indexOf("=")+1);
				
				$("#" + objId).addClass("invalid");
				$div=$("<div/>");
				$div.addClass("invalid_message");
				$div.html(message);
				$("#"+objId).parent().append($div);
				$div.position({
					of: $("#"+objId).parent(),
					my: "center bottom",
					at: "center top",
					offset: "",
					collision: "flip flip"
				});
			}
		} else {

			if(act == 'create'){
				switch(tabIndex){
					case 0:
						break;
					case 1:
						call_address_service_create(params);
						break;
					case 2:
						call_contact_service_create(params);
						break;
					case 3:
						call_credit_service_create(params);
						break;
					case 4:
						call_disc_service_create(params);
						break;
					case 5:
						call_ebill_service_create(params);
						break;
					case 6:
						break;
					case 7:
						break;
					case 8:
						break;
					case 9:
						break;
					case 10:
						break;
				}
			}
			act="";
		}
		services.call("traveler.resetValidator", []);
		$("#statusshower").hide(1000);
	}
	
	function validate3(action, parameters) {
		act = action;
		params = parameters;
		var tabIndex = $("#tabs").tabs("option", "selected");
		
		var tabId="#tabs-" + (tabIndex + 1);
		var invalidClass = tabId + " .invalid_sub";
		var invalidMessageClass = tabId + " .invalid_message_sub";
		var validatorClass=tabId + " .validator_sub";
		
		$v=$(invalidClass);
		for(i = 0; i < $v.length; i++) {
			$v.removeClass("invalid_sub");
		}
		$v=$(invalidMessageClass);
		for(i = 0; i < $v.length; i++) {
			$v.remove();
		}
		
		$validObject=$(validatorClass);
		var objs = "";
		for(i = 0; i < $validObject.length; i++) {
			objs += $validObject.eq(i).attr("id") + "=" + $validObject.eq(i).val() + "<sp/>";
		}
		services.call("traveler.validate", [objs], afValid3);
	}
	
	function afValid3(obj1) {
		//SPLIT WITH (", OR ;")
		if(obj1 ==null || obj1.data==null) {
			return;
		}
		
		obj=obj1.data;
		
		var tabIndex = $("#tabs").tabs("option", "selected");	
		var tabId="#tabs-"+(tabIndex);
		
		if(obj.length!=0) {
			for(i=0;i<obj.length;i++) {
				var objId = obj[i].substring(0, obj[i].indexOf("="));
				var message = obj[i].substring(obj[i].indexOf("=")+1);
				
				$("#" + objId).addClass("invalid_sub");
				$div=$("<div/>");
				$div.addClass("invalid_message_sub");
				$div.html(message);
				$("#"+objId).parent().append($div);
				$div.position({
					of: $("#"+objId).parent(),
					my: "center bottom",
					at: "center top",
					offset: "",
					collision: "flip flip"
				});
			}
		} else {
			if(act=='update'){
				switch(tabIndex){
					case 0:
						break;
					case 1:
						call_address_service_update(params);
						break;
					case 2:
						call_contact_service_update(params);
						break;
					case 3:
						call_credit_service_update(params);
						break;
					case 4:
						call_disc_service_update(params);
						break;
					case 5:
						call_ebill_service_update(params);
						break;
					case 6:
						break;
					case 7:
						break;
					case 8:
						break;
					case 9:
						break;
					case 10:
						break;
				}
			}
			act = "";
		}
		services.call("traveler.resetValidator", []);
		$("#statusshower").hide(1000);
	}
	
	$(function(){
		$("#updateComp").removeAttr("disabled");
		
	});
</script>

<body>
	<jsp:include page="/WEB-INF/jsp/common/top.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/common/modalconfirmdialog.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/form/clauseselect.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/form/address.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/form/contact.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/form/discount.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/form/ebill.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/form/feecode.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/form/feecodeDetail.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/form/creditcard.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/form/reasonCode.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/form/purposeCode.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/form/copyfrom.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/form/upload.jsp"></jsp:include>
	<script src='js/common.js'></script>
	
	<div id="error">${message_message }
		<c:if test="${param.errorMessage!=null && param.errorMessage!='' }">
			${ param.errorMessage}
		</c:if>
	</div>
	
	<table width="100%" cellpadding="0" cellspacing="0">
		<tr>
			<td class="loginform" valign="top">

				<form id="form1" action="updateComp.action" method="post">
				
					<input type="hidden" name="customer.id" id="customer-id" value="${customer.id }">
					<input type="hidden" name="company.id" id="company-id" value="${company.id }">
					<input type="hidden" name="customer.name" value="${customer.name }">
					<input type="hidden" name="company.custId" id="company-customer-id" value="${company.custId }">
					
					<input type="hidden" name="company.policy" id="company-policy">
					<input type="hidden" name="company.officeaddress" id="officeaddress">
					<input type="hidden" name="tabSelected" id="tabSelected">
					
					<table width="100%" cellpadding="4" cellspacing="0">
						<tr>
							<td>
								<span class="label_bold">Customer name:</span>
								<a href="${pageContext.request.contextPath}/redirect.action?url=trav&cpname=${customer.name }">${customer.name }</a>
								-
								<c:if test="${company.status=='ACTIVE' }">
									<a href="${pageContext.request.contextPath}/createTraveler.action?company.companyNo=${company.companyNo }">create traveler</a>
								</c:if>
								<div>
								<!-- 
								<input type="button" value="Download" style="width:80px" onclick='location.href="download.action?companyNo=${company.companyNo }"'/>
								-
								<input type="button" value="Upload" id="btnUpload" style="width:80px"/>
								 -->
								</div>
								
							</td>
						</tr>
						<tr>
							<td>
								<div class="main">
									<div id="tabs">
										<ul>
											<li>
												<a href="#tabs-1">Base Info</a>
											</li>
											<li>
												<a href="#tabs-2">Address</a>
											</li>
											<li>
												<a href="#tabs-3">Contact</a>
											</li>
											<li>
												<a href="#tabs-4">Credit Card</a>
											</li>
											<li>
												<a href="#tabs-5">Discount</a>
											</li>
											<li>
												<a href="#tabs-6">E-Billing</a>
											</li>
											<li>
												<a href="#tabs-7">Terms</a>
											</li>
											<li>
												<a href="#tabs-8">Policy</a>
											</li>
											<li>
												<a href="#tabs-9">DBI</a>
											</li>
										</ul>
										
										<div id="colorBar"></div>
																
										<div id="tabs-1">
											<!-- company base info -->

											<div>
											<select name="company.status">
												<option value="ACTIVE" ${company.status=='ACTIVE'?'selected':'' }>Active</option>
												<option value="INACTIVE" ${company.status=='INACTIVE'?'selected':'' }>Inactive</option>
												<option value="BLACKLIST" ${company.status=='BLACKLIST'?'selected':'' }>Black List</option>
												<option value="SUSPEND" ${company.status=='SUSPEND'?'selected':'' }>Suspend</option>
											</select>
											</div>
											
											<jsp:include page="/WEB-INF/jsp/form/companyinfo.jsp"></jsp:include>

											<!-- company base info -->
										</div>

										<div id="tabs-2">
											<div style="background-color: white;">
												to add new address, click <a href="#" id="btnAddress">add</a>
												<p>&nbsp;</p>
												<div id="addressPool">
												
												</div>
											</div>
										</div>
										
										<div id="tabs-3">
											<div style="background-color: white;">
												to add new contact, click <a href="#" id="btnContact">add</a>
												<p>&nbsp;</p>
												<div id="contactPool"></div>
											</div>
										</div>
										
										<div id="tabs-4">
											<div style="background-color: white;">
												to add new Credit Card, click <a href="#" id="btnCCard">add</a>
												<ul id="ccardPool">
													
												</ul>
											</div>
										</div>
										
										<div id="tabs-5">
											<div style="background-color: white;">
											<ul>
												<li>
													to add new Discount, click <a href="#" id="btnDiscount">add</a>
												</li>
												<li>
													to copy from another, click <a href="#" id="btnCopyDiscount">copy</a>
												</li>
											</ul>
												<ul id="discountPool">
													
												</ul>
											</div>

										</div>
										
										<div id="tabs-6">

											<div style="background-color: white;">
												<li>to add new E-Billing, click <a href="#" id="btnEBill">add</a></li>
													<ul id="eBillPool">
													
													</ul>
												
												<li>to add new Reason Code, click <a href="#" id="btnReasonCode">add</a></li>
												<table id="reasonPool">
													<tr>
														<td width="200" class="reason_code_title">Reason Code</td>
														<td class="reason_code_title">Product Code</td>
														<td class="reason_code_title">Description</td>
														<td>&nbsp;</td>
													</tr>
												</table>
												
												<li>to add new Purpose Code, click <a href="#" id="btnPurposeCode">add</a></li>
												<table id="purposePool">
													<tr>
														<td width="200" class="reason_code_title">Purpose Code</td>
														<td class="reason_code_title">Description</td>
														<td>&nbsp;</td>
													</tr>
												</table>
												
											</div>
										</div>
										<div id="tabs-7">
											<div style="background-color: white;">
												<ul>
													<li>to add new Term, click <a href="#" id="btnTerm">add</a></li>
													<li>to copy from another, click <a href="#" id="btnCopy">copy</a></li>
												</ul>
												
												<div id="termsPool">
													<table>
														<tr>
															<td class="feeCode">Variable</td>
															<td class="feeCode">Fee Type</td>
															<td class="feeCode">Fare Code</td>
															<td class="feeCode">Group ID</td>
														</tr>
													</table>
												</div>
											</div>
										</div>
										
										<div id="tabs-8">
											<div>
												<a id="clauseSelector" href="#">add clauses to list.</a>
												<ul id="policy_list">

												</ul>
												
												<p>Remarks</p>
												<textarea rows="10" cols="100" id="company_remarks" name="company.remarks">${company.remarks }</textarea>
											</div>
										</div>
										
										<div id="tabs-9">
											<jsp:include page="/WEB-INF/jsp/form/dbi.jsp"></jsp:include>
										</div>
										
									</div>
								</div>
							</td>
						</tr>
					</table>
					<input type="button" id="updateComp" value="Save" class="btn" disabled="false" /> 
					<input type="button" id="returnComp" value="Back" class="btn" />
				</form>
				<div id="statusshower">Processing...</div>
				</td>
		</tr>
	</table>
</body>
</html>



