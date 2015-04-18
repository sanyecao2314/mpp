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
	<script src="js/jquery.ui.draggable.js"></script>
	<script src="js/jquery.ui.datepicker.js"></script>
	<script src="js/jQuery.Hz2Py-min.js"></script>
	<script src="js/traveler_validate.js"></script>

	<script type='text/javascript'
		src='${pageContext.request.contextPath}/dwr/interface/services.js'></script>
	<script type='text/javascript'
		src='${pageContext.request.contextPath}/dwr/engine.js'></script>
	<script src="js/traveler_define.js"></script>

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

	var compId = "${traveler.id}";
	
	var destId = "T${traveler.id}";
	var ADDR_SEL = "${traveler.officeaddress}";
	var COPY_ACTION = "";
	$(function(){
		initAddress();
		initContact();
		initCCard();
		initEBill();
		initMember();
		initExtra();
		initPrefer();
		initFeeCode();
		initvisa();
	});
	
	$(function() {
		var idx = 0;

		$("#tabs").tabs({"selected": ${tabSelected}});
		$("#tab3").addClass("tab_act").removeClass("tab_out");

		$("input:text").css("width", "300px");
		$("select").css("width", "300px");
		
		$("#datepicker1").datepicker({
			dateFormat : 'yy-mm-dd'
		});
		$("#datepicker2").datepicker({
			dateFormat : 'yy-mm-dd'
		});
		
		$("#btnAddress").click(function() {
			$("#address-form").dialog("open");
		});

		$("#btnPrefer").click(function() {
			$("#pr-form").dialog("open");
		});
		$("#btnVisa").click(function() {
			$("#visa-form").dialog("open");
		});
		
		$("#btnContact").click(function() {
			$("#contact-form").dialog("open");
		});

		$("#btnCCard").click(function() {
			var holderName = $("#traveler-lastnameEn").val() + ' ' + $("#traveler-firstnameEn").val();
			$("#holderName").val(holderName);
			$("#ccard-form").dialog("open");
		});
		
		$("#btnextra").click(function() {
			$("#ex-form").dialog("open");
		});
		
		$("#btnEBill").click(function() {
			$("#ebill-form").dialog("open");
		});

		$("#btnmember").click(function(){
			$("#MB-form").dialog("open");
		});
		
		$( "#policy_list" ).sortable({
			placeholder: "ui-state-highlight"
		});
		$( "#policy_list" ).disableSelection();
		
		$("#btnTerm").click(function() {
			$("#fc-form").dialog("open");
		});
		
		$("#btnCopy").click(function() {
			COPY_ACTION = "traveler.copyTerms";
			$("#copyfrom-form").dialog("open");
		});
		
		$("#newBar").css("width", "60px");

		$("#traveler_otherTitle").css("width", "50px");
		
	});

	$(function() {

		$("#dialog-confirm").dialog({
			resizable : false,
			height : 200,
			modal : true,
			buttons : {
				"Delete" : function() {
					deleteItem();
					$(this).dialog("close");
				},
				Cancel : function() {
					$(this).dialog("close");
				}
			}
		});

		$("#dialog-confirm").dialog("close");

	});

	$(function() {
		$("#policy_list").dblclick(function(event){
			if(event.target.tagName=="LI"){					
				$( "#dialog-confirm" ).dialog( "open" );
				deleteItem=function(){
					$("#policy_list")[0].removeChild(event.target);
				};
			}
		});
		
		$("#updateComp").click(function() {
			var $hidden3=$("#policy_list input:hidden");
			var $li3=$("#policy_list li");
			$("#company-policy").val("");
			
			var policy="";
			for(var i=0;i<$li3.length;i++) {
				policy += $li3.eq(i).attr("id");
				if(i<$li3.length-1)
					policy+=",";
			}
			
			$("#company-policy").val(policy);
			$("#tabSelected").val($("#tabs").tabs("option", "selected"));
			
			$("#traveler-firstnameEn").val($("#traveler-firstnameEn").val().toUpperCase());
			$("#traveler-lastnameEn").val($("#traveler-lastnameEn").val().toUpperCase());
			$("#traveler-firstname").val($("#traveler-firstname").val().toUpperCase());
			$("#traveler-lastname").val($("#traveler-lastname").val().toUpperCase());
			$("#traveler-travelerName").val($("#traveler-travelerName").val().toUpperCase());
			
			validate1();
		});

		$("#returnComp").click(function() {
			location.href = 'redirect.action?url=trav';
		});

	});


	function validate1() {
		
		if(!$("#traveler_gender_male")[0].checked && !$("#traveler_gender_female")[0].checked){
			alert("Please choose gender.");
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
			var systems=$(".syncsystems");
			var value="";
			for(var i=0;i<systems.length;i++) {
				value+=systems.eq(i).html();
				if(i<systems.length-1) {
					value+=",";
				}
			}
			
			if(value!='') {
				$("#systems").val(value);
			}
				
			$("#form1").submit();
		}
		services.call("traveler.resetValidator", []);
		
	}
	
	var act = "";
	var params;
	
	function validate2(action,parameters) {
		act=action;
		params=parameters;
		var tabIndex = $("#tabs").tabs("option", "selected");
		
		var tabId="#trav"+(tabIndex);
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
		var tabId="#trav"+(tabIndex);
		
		if(obj.length!=0) {
			for(i=0;i<obj.length;i++) {
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
						call_ebill_service_create(params);
						break;
					case 5:
						
						break;
					case 6:
						call_member_service_create(params);
						break;
					case 7:
						call_extra_service_create(params);
						break;
					case 8:
						call_pref_service_create(params);
						break;
					case 9:
						call_visa_service_create(params);
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
	
	function validate3(action, parameters) {
		act = action;
		params = parameters;
		var tabIndex = $("#tabs").tabs("option", "selected");
		
		var tabId="#tabs-"+(tabIndex + 1);
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
						call_ebill_service_update(params);
						break;
					case 5:
						break;
					case 6:
						call_member_service_update(params);
						break;
					case 7:
						call_extra_service_update(params);
						break;
					case 8:
						call_pref_service_update(params);
						break;
					case 9:
						call_visa_service_update(params);
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
	
	
	function createRow(label,textprops){
		var $tr=$("<tr/>");
		var $td=$("<td/>");
		$td.addClass("contactlabel");
		$tr.append($td);
		var $label=$("<label/>");
		$label.html(label);
		$td.append($label);
		$td=$("<td/>");
		$tr.append($td);
		var $text=$("<input/>", textprops);
		$text.attr("size",60);
		$td.append($text);
		return $tr;
	}
		
	function afUpdate(obj){
		if(!obj.success) {
			alert("some errors occur while processing request.");
			return;
		}
		$("#statusshower").hide(1000);
	}
	
	
	$(function() {
		$("#statementAddress").remove();
	});
	
	var afterlistClause = function(obj) {
		if(!obj.success) {
			alert("some errors occur while processing request.");
			return;
		}
	
		var clauseStr="${company.policy}";
		
		if(clauseStr=="") {
			return;
		}
		
		var dict = new Array();
		var list = obj.data;
		
		for(var i = 0;i < list.length; i++) {
			dict[list[i].catalog+"-"+list[i].no] = list[i].content;
		}
		
		var clauses = clauseStr.split(",");
		for(var i = 0;i < clauses.length; i++) {
			$li=$("<li/>");
			$("#policy_list").append($li);
			$li.attr("id",clauses[i]);
			$li.append(clauses[i]);
			$li.append("<br/>");
			$li.append(dict[clauses[i]]);
			
			$hidden=$("<input type='hidden' name='customer.clauses'/>");
			$hidden.val(clauses[i]);
			$("#policy_list").append($hidden);
		}
	};
	
	$(function() {
		$("#clauseSelector").click(function() {
			$( "#clause-select-form" ).dialog( "open" );
			$( "#clause-table" ).html("");
			services.call("customer.listClause", [''], afterlistClause2);
		});
	});
	
	var afterlistClause2 = function(obj){
		if(!obj.success) {
			alert("some errors occur while processing request.");
			return;
		}

		var list = obj.data;			
		for(var i = 0;i < list.length; i++) {
			$tr=$("<tr/>");
			$( "#clause-table" ).append($tr);
			$td=$("<td/>");
			$td.addClass("td1");
			$td.append(list[i].catalog + "-" + list[i].no);
			$tr.append($td);
			$td=$("<td/>");
			$td.append(list[i].content);
			$tr.append($td);
			$tr.attr("id", list[i].catalog + "-" + list[i].no);
			$tr.addClass("unchecked");
			$tr.click(function(){
				if($(this).attr("class")=='unchecked') {
					$(this).addClass("checked");
					$(this).removeClass("unchecked");
				} else {
					$(this).addClass("unchecked");
					$(this).removeClass("checked");
				}
			});
		}
	};
	
	// initialize the costcenter & tmr.
	$(function() {
		
		var costcenter=new Array();
		
		<c:forEach var="cc" varStatus="status" items="${customer.costcenters }">
			costcenter[${status.index}]= "${cc }";
		</c:forEach>
		
		$( "#traveler_costcenter" ).autocomplete({
			source: costcenter,
			autoFocus: true
		});
		
		var tmr = new Array();
		<c:forEach var="tmr" varStatus="status" items="${customer.tmrParNo }">
			tmr[${status.index}]="${tmr}";
		</c:forEach>

		tmr.sort();
		$( "#traveler-department2" ).autocomplete({
			source: tmr,
			autoFocus: true
		});
		
	});
	
	// initialize the traveler country 
	$(function(){
		services.call("common.listCountry", [], afterListCountry);
	});
	
	function afterListCountry(codes){
		
		var array = new Array();
		
		for(i = 0; i < codes.length; i++) {
			array[i] = codes[i].key + "/" + codes[i].value;
		}
		
		$("#traveler-country").autocomplete({
			source: array,
			autoFocus: true
		});
		
		$("#traveler-country").blur(function(){
			$("#traveler-country").blur(function(){
				if($("#traveler-country").val().indexOf(" ")!=-1){
					//$("#traveler-country").val($("#traveler-country").val().substring(0,$("#traveler-country").val().indexOf(" ")));	
				}
			});
		});
		
		$("#traveler-nationality1").autocomplete({
			source: array,
			autoFocus: true
		});
		
		$("#traveler-nationality1").blur(function(){
			$("#traveler-nationality1").blur(function(){
				if($("#traveler-nationality1").val().indexOf(" ")!=-1){
					//$("#traveler-nationality1").val($("#traveler-nationality1").val().substring(0,$("#traveler-nationality1").val().indexOf(" ")));	
				}
			});
		});
		
		$("#traveler-nationality2").autocomplete({
			source: array,
			autoFocus: true
		});
		
		$("#traveler-nationality2").blur(function(){
			if($("#traveler-nationality2").val().indexOf(" ")!=-1){
				//$("#traveler-nationality2").val($("#traveler-nationality2").val().substring(0,$("#traveler-nationality2").val().indexOf(" ")));	
			}
		});
		
		$("#visa_country").autocomplete({
			source: array,
			autoFocus: true
		});
		
		$("#visa_country").blur(function(){
			if($("#visa_country").val().indexOf(" ")!=-1){
				//$("#visa_country").val($("#visa_country").val().substring(0,$("#visa_country").val().indexOf(" ")));	
			}
			
		});
		
		$("#visaIssueCountry").autocomplete({
			source: array,
			autoFocus: true
		});
		
		$("#visaIssueCountry").blur(function(){
			if($("#visaIssueCountry").val().indexOf(" ")!=-1){
				//$("#visaIssueCountry").val($("#visaIssueCountry").val().substring(0,$("#visaIssueCountry").val().indexOf(" ")));	
			}
			
		});

		$("#btnNewBar").click(function(){
			$("#changeBar-form").dialog("open");
			var travelerno=$("#traveler-travelerNo").val();
			$("#changeBar-from").val(travelerno.substring(0, 6));
			$("#changeBar-travelerNo").val(travelerno);
		});
				
	}
	
	$(function(){
		var dirty = "${traveler.dirty}";
		
		for(j = 0; j < citsamexSystems.length; j++) {
			
			$status_div=$("#sync_status_" + citsamexSystems[j]);
			if($status_div.length == 0){
				$status_div=$("<div/>",{id:"sync_status_" + citsamexSystems[j]});
				$status_div.html(citsamexSystemsName[j]);
				$("#colorBar").append($status_div);
				$status_div.click(function(){
					if($(this).hasClass("syncsystems")){
						$(this).removeClass("syncsystems");
						$(this).css("backgroundColor", "#DDD");
					}else{
						$(this).addClass("syncsystems");
						$(this).css("backgroundColor", "#581");
					}
				});
			}
			$status_div.addClass("sync_status_block2");
			if((dirty & citsamexSystems[j]) == citsamexSystems[j]) {
				$status_div.css("backgroundColor", citsamexSystemsColor[j]);
			} else {
				$status_div.css("backgroundColor", "#DDD");
			}
		}
		
		$("#changeBar-status-remark").val("${traveler.statusRemark}");
		
	});
	
	$(function(){
		var $fields=$("[name='fieldnames']");
	
		for(i = 0; i < $fields.length; i++) {
			id=$fields.eq(i).parent().attr("id");
			$("#" + id + "P").click(function(){
				if($(this).hasClass("field_check")){
					$(this).removeClass("field_check");
					$(this).addClass("field_uncheck");
				} else {
					$(this).removeClass("field_uncheck");
					$(this).addClass("field_check");
				}
			});
			
			$("#" + id + "B").click(function(){
				if($(this).hasClass("field_check")){
					$(this).removeClass("field_check");
					$(this).addClass("field_uncheck");
				} else {
					$(this).removeClass("field_uncheck");
					$(this).addClass("field_check");
				}
			});
			
			$("#" + id + "G").click(function(){
				if($(this).hasClass("field_check")){
					$(this).removeClass("field_check");
					$(this).addClass("field_uncheck");
				} else {
					$(this).removeClass("field_uncheck");
					$(this).addClass("field_check");
				}
			});
			
			$fields.eq(i).dblclick(function(){
				$(this).attr("readonly", false);
				$(this).addClass("fielddesc_act");
				$(this).removeClass("fielddesc_inact");
			});
			
			$fields.eq(i).blur(function(){
				id = $(this).parent().attr("id");
				var lv = "";
				if($("#" + id + "P").hasClass("field_check")) {
					lv+="P";
				}
				if($("#" + id + "B").hasClass("field_check")) {
					lv+="B";
				}
				if($("#" + id + "G").hasClass("field_check")) {
					lv+="G";
				}
				
				var field = $(this).attr("id");

				services.call("common.updateFieldDesc", ["${traveler.travelerNo}",field,$(this).val(),lv]);
								
				$(this).attr("readonly", true);
				$(this).removeClass("fielddesc_act");
				$(this).addClass("fielddesc_inact");

			});
		}
		
		$(".fielddesc_inact").css("width","150px");
		services.call("common.queryFieldsDesc", ["${traveler.travelerNo}"] ,afQueryF);		
	});
	
	function afQueryF(obj) {
		for(i = 0; i < obj.length; i++ ) {
			var key = obj[i].key;
			var val = obj[i].value;
			$("#"+key).val(val);
		}
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
	<jsp:include page="/WEB-INF/jsp/form/creditcard.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/form/ebill.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/form/member.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/form/extra.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/form/prefer.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/form/feecode.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/form/feecodeDetail.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/form/copyfrom.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/form/changeBar.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/form/visa.jsp"></jsp:include>
	
	<script src='js/common.js'></script>
	<div id="error">${message_message }</div>

	<table width="100%" cellpadding="0" cellspacing="0">
		<tr>
			<td class="loginform" valign="top">

				<form id="form1" action="updateTraveler.action" method="post">
					
					<input type="hidden" name="traveler.id" id="traveler-id" value="${traveler.id }">
					<input type="hidden" name="company.id" id="company-id" value="${company.id }">
					<input type="hidden" name="customer.name" value="${customer.name }">
					<input type="hidden" name="company.custId" id="company-customer-id" value="${company.custId }">
					<input type="hidden" name="company.companyName" value="${company.companyName }">
					<input type="hidden" name="company.policy" id="company-policy" value="">
					<input type="hidden" name="traveler.companyId" id="traveler-companyId" value="${traveler.companyId }">
					<input type="hidden" name="traveler.companyName" id="traveler-companyName" value="${traveler.companyName }">
					<input type="hidden" name="traveler.officeaddress" id="officeaddress">
					<input type="hidden" id="systems" name="systems" value="PS,BS,AXO">
					<input type="hidden" name="traveler.ebilling" id="traveler_ebilling" value="${company.ebilling }">
					
					<input type="hidden" name="tabSelected" id="tabSelected">
					
					<table width="100%" cellpadding="4" cellspacing="0">
						<tr>
							<td><span class="label_bold">Customer name:</span>
								${customer.name } <span class="label_bold">Company name:</span>
								${company.companyName }</td>
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
												<a href="#tabs-5">E-Billing</a>
											</li>
											<li>
												<a href="#tabs-6">Terms</a>
											</li>
											<li>
												<a href="#tabs-7">Member</a>
											</li>
											<li>
												<a href="#tabs-8">Required Data</a>
											</li>
											<li>
												<a href="#tabs-9">Preference</a>
											</li>
											<li>
												<a href="#tabs-10">Visa</a>
											</li>
										</ul>
										
										<div id="colorBar"></div>
										<div id="tabs-1">
											<!-- company base info -->
											<div>
											<select id="traveler_status" name="traveler.status" class="unique">
												<option value="ACTIVE" ${traveler.status=='ACTIVE'?'selected':'' }>Active</option>
												<option value="INACTIVE" ${traveler.status=='INACTIVE'?'selected':'' }>Inactive</option>
												<option value="BLACKLIST" ${traveler.status=='BLACKLIST'?'selected':'' }>Black List</option>
												<option value="SUSPEND" ${traveler.status=='SUSPEND'?'selected':'' }>Suspend</option>
											</select>
											</div>
											
											<c:if test="${traveler.travelerType=='traveler' }">
												<a href="${pageContext.request.contextPath}/createSTraveler.action?company.companyNo=${company.companyNo}&traveler.travelerType=family&traveler.travelerNo=${traveler.travelerNo}">Create Family</a>
											</c:if>
											<c:if test="false">
											<a href="${pageContext.request.contextPath}/createSTraveler.action?company.companyNo=${company.companyNo}&traveler.travelerType=work-in&traveler.travelerNo=${traveler.travelerNo}">Create Walk-in</a>
											</c:if>
											
											<jsp:include page="/WEB-INF/jsp/form/travelerinfo.jsp"></jsp:include>
											
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
												to add new E-Billing, click <a href="#" id="btnEBill">add</a>
												<ul id="eBillPool">
													
												</ul>
											</div>
										</div>
										
										<div id="tabs-6">
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
										
										<div id="tabs-7">
											<div style="background-color: white;">
												to add new Member, click <a href="#" id="btnmember">add</a>
												<ul id="memberPool">
													
												</ul>
											</div>
										</div>
										
										<div id="tabs-8">
											<div style="background-color: white;">
												to add new Extra, click <a href="#" id="btnextra">add</a>
												<ul id="extraPool">
													
												</ul>
											</div>
										</div>
										
										<div id="tabs-9">
											<div style="background-color: white;">
												to add new Preference, click <a href="#" id="btnPrefer">add</a>
												<ul id="preferPool">
													
												</ul>
											</div>
										</div>
										
										<div id="tabs-10">
											<div style="background-color: white;">
												to add new Visa, click <a href="#" id="btnVisa">add</a>
												<ul id="visaPool">
													
												</ul>
											</div>
										</div>
									</div>
								</div></td>
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



