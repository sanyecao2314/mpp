var CONTACT_DEL = "company.deleteContact";
var CONTACT_CREATE = "company.createContact";
var CONTACT_LIST = "company.listContact";
var CONTACT_UPDATE = "company.updateContact";
var ADDR_CREATE = "company.createAddress";
var ADDR_DELETE = "company.deleteAddress";
var ADDR_UPDATE = "company.updateAddress";
var ADDR_LIST = "company.listAddress";
var CCARD_CREATE = "company.createCreditCard";
var CCARD_LIST = "company.listCreditCard";
var CCARD_DEL = "company.deleteCreditCard";
var CCARD_UPDATE = "company.updateCreditCard";
var EBILL_CREATE = "company.createEBill";
var EBILL_LIST = "company.listEBill";
var EBILL_DEL = "company.deleteEBill";
var EBILL_UPDATE = "company.updateEBill";
var EXTRA_CREATE = "company.createExtra";
var EXTRA_LIST = "company.listExtra";
var EXTRA_DEL = "company.deleteExtra";
var EXTRA_UPDATE = "company.updateExtra";
var MEMBER_CREATE = "company.createMember";
var MEMBER_DEL = "company.deleteMember";
var MEMBER_UPDATE = "company.updateMember";
var MEMBER_LIST = "company.listMember";
var PREFER_CREATE = "company.createPrefer";
var PREFER_LIST = "company.listPrefer";
var PREFER_DEL = "company.deletePrefer";
var PREFER_UPDATE = "company.updatePrefer";

var FEECODE_CREATE = "company.createFeeCode";
var FEECODE_LIST = "company.listFeeCode";
var FEECODE_DEL = "company.deleteFeeCode";
var FEECODE_UPDATE = "company.updateFeeCode";
var FEECODEDETAIL_LIST = "company.listFeeCodeDetail";
var FEECODEDETAIL_CREATE = "company.createFeeCodeDetail";
var FEECODEDETAIL_UPDATE = "company.updateFeeCodeDetail";
var FEECODEDETAIL_DEL = "company.deleteFeeCodeDetail";
var DISCOUNT_CREATE = "company.createDiscount";
var DISCOUNT_LIST = "company.listDiscount";
var DISCOUNT_DEL = "company.deleteDiscount";
var DISCOUNT_UPDATE = "company.updateDiscount";

var REASON_CREATE = "company.createReasonCode";
var REASON_UPDATE = "company.updateReasonCode";
var REASON_DELETE = "company.deleteReasonCode";
var REASON_LIST = "company.listReasonCode";

var PURPOSE_CREATE = "company.createPurposeCode";
var PURPOSE_UPDATE = "company.updatePrposeCode";
var PURPOSE_DELETE = "company.deletePurposeCode";
var PURPOSE_LIST = "company.listPurposeCode";

var feeCodeId = "";

$(function(){
	initAddress();
	initContact();
	initDiscount();
	initEBill();
	initCCard();
	initFeeCode();
	initReasonCode();
	initPurposeCode();
});

$(function(){
	$("#tab2").addClass("tab_act").removeClass("tab_out");
	
	$("#btnAddress").click(function() {
		$("#address-form").dialog("open");
	});
	
	$("#btnContact").click(function() {
		$("#contact-form").dialog("open");
	});
	
	$("#btnTerm").click(function() {
		$("#fc-form").dialog("open");
	});
	
	$("#btnCCard").click(function() {
		$("#holderName").val($("#traveler-travelerName").val());
		$("#ccard-form").dialog("open");
	});
	
	$("#btnDiscount").click(function() {
		$("#discount-form").dialog("open");
	});

	$("#btnCopyDiscount").click(function() {
		COPY_ACTION = "traveler.copyDiscount";
		$("#copyfrom-form").dialog("open");
	});
	
	$("#btnCopy").click(function() {
		COPY_ACTION = "traveler.copyTerms";
		$("#copyfrom-form").dialog("open");
	});
	
	$("#btnEBill").click(function() {
		$("#ebill-form").dialog("open");
	});
	
	$("#btnReasonCode").click(function() {
		$("#reason-form").dialog("open");
	});
	
	$("#btnPurposeCode").click(function() {
		$("#purpose-form").dialog("open");
	});
	
	$( "#policy_list" ).sortable({
		placeholder: "ui-state-highlight"
	});
	$( "#policy_list" ).disableSelection();
		
	$(".flag_chk").click(function() {
		if($(this)[0].checked)
			$(this).val(1);
		else
			$(this).val(0);
	});
	
	$(".flag_chk").each(function() {
		if($(this).val()==1) {
			$(this)[0].checked = true;
		} else {
			$(this)[0].checked = false;
		}
	});

});

var afCreateEBill = function(obj) {
	if(!obj.success) {
		alert("some errors occur while processing request, ebill");
		return;
	}
	addEBill(obj.data);
	$("#statusshower").hide();
};


var afCreateContact = function(obj){
	if(!obj.success) {
		alert("some errors occur while processing request, contact");
		return;
	}
	addContact(obj.data);
	$("#statusshower").hide();
};

$(function() {
	$("input:text").css("width", "300px");
	$("select").css("width", "300px");

	$("#datepicker1").datepicker({
		dateFormat : 'yy-mm-dd'
	});
	$("#datepicker2").datepicker({
		dateFormat : 'yy-mm-dd'
	});
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
		validate1();
	});

	$("#returnComp").click(function() {
		location.href = 'redirect.action?url=comp';
	});
});

var initAddress = function(){
	$("#statusshower").show();
	services.call("company.listAddress", [compId], afListAddr);
};

var initContact = function(){
	$("#statusshower").show();
	services.call("company.listContact", [compId], afListContact);
};

var initEBill = function(){
	$("#statusshower").show();
	services.call("company.listEBill", [compId], afListEBill);
};

var afListEBill = function(obj){
	if(!obj.success) {
		alert("some errors occur while processing request,ebill");
		return;
	}
	var list = obj.data;
	for(var i=0;i<list.length;i++){
		addEBill( list[i] );
	}
	$("#statusshower").hide();
};

var afListContact = function(obj){
	if(!obj.success) {
		alert("some errors occur while processing request,contact");
		return;
	}
	var list = obj.data;
	for(var i=0;i<list.length;i++){
		addContact( list[i] );
	}
	$("#statusshower").hide();
};


function afDeleteContact(obj){
	if(!obj.success) {
		alert("some errors occur while processing request,contact");
		return;
	}
	$("#cc_"+obj.data.id).remove();
}

function afDeleteEBill(obj){
	if(!obj.success) {
		alert("some errors occur while processing request,ebill");
		return;
	}
	$("#ec_"+obj.data.id).remove();
}

function afDeleteAddr(obj){
	if(!obj.success) {
		alert("some errors occur while processing request,address");
		return;
	}
	$("#ac_"+obj.data.id).remove();
}



function afUpdate(obj){
	if(!obj.success) {
		alert("some errors occur while processing request,");
		return;
	}
	$("#statusshower").hide(1000);
}


$(function() {

	services.call("company.listClauseByString", [compId, clauses], afterlistClause);
});

var afterlistClause = function(obj) {
	if(!obj.success) {
		alert("some errors occur while processing request,clause");
		return;
	}
	
	if(clauseStr==""){
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

$(function(){
	$("#clauseSelector").click(function(){
		$( "#clause-select-form" ).dialog( "open" );
		$( "#clause-table" ).html("");
		services.call("customer.listClause", [''], afterlistClause2);
	});
});

var afterlistClause2 = function(obj){
	if(!obj.success) {
		alert("some errors occur while processing request,clause2");
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
			if($(this).attr("class")=='unchecked'){
				$(this).addClass("checked");
				$(this).removeClass("unchecked");
			}else{
				$(this).addClass("unchecked");
				$(this).removeClass("checked");
			}
		});

	}
};

var addEBill = function(ebill){
	var $cdiv=$("<div/>");
	$cdiv.attr("id","ec_" + ebill.id);
	$("#eBillPool").append($cdiv);
	var $label = $("<div/>");
	$label.addClass("ebill");
	$label.attr("id", ebill.id);
	$cdiv.append($label);
	$label.html("E-Billing " + $(".ebill").length);
	var $btn=$("<input/>",{type:"button",value:"delete"});
	$cdiv.append($btn);
	$btn.click(function(){
		services.call("company.deleteEBill", [ebill.id], afDeleteEBill);
	});
	$label.toggle(
			function() {
				$("#ei_"+ebill.id).show(1000);
			},
			function() {
				$("#ei_"+ebill.id).hide(1000);
			}
	);

	var $div=$("<div/>");
	$div.attr("id","ei_"+ebill.id);
	$cdiv.append($div);
	
	var $table=$("<table/>");
	
	$table.append(createRow("Field Name",{type:"text",id:"fieldName" + ebill.id, value:ebill.fieldName}));
	$table.append(createRow("Default Value",{type:"text",id:"defaultValue" + ebill.id, value:ebill.defaultValue}));
	$table.append(createRow("Mandatory",{type:"checkbox",id:"mandatory" + ebill.id}));
	
	$div.append($table);
	if(ebill.mandatory=="1")
		$("#mandatory" + ebill.id)[0].checked = true;
		
	var $btn=$("<input/>",{type:"button",value:"Save"});
	$btn.addClass("btn");
	$btn.click(function(){
		$("#statusshower").show(1000);
		services.call("company.updateEBill", [ebill.id,
		                                        $("#fieldName"+ebill.id).val(),
		                                        $("#defaultValue"+ebill.id).val(),
		                                        $("#mandatory" + ebill.id+":checked").length
		                                        ], afUpdate);
	});
	$div.append($btn);
	$div.addClass("c_div");
    $div.hide();
	$("#ebill-form").dialog("close");		
};

var addContact = function(contact){
	
	var $cdiv=$("<div/>");
	$cdiv.attr("id","cc_" + contact.id);
	$("#contactPool").append($cdiv);
			
	var $label = $("<div/>");
	$label.addClass("contact");
	$label.attr("id", contact.id);
	$cdiv.append($label);
	$label.html("Contact " + $(".contact").length);
	var $btn=$("<input/>",{type:"button",value:"delete"});
	$cdiv.append($btn);
	$btn.click(function(){
		services.call("company.deleteContact", [contact.id], afDeleteContact);
	});
	$label.toggle(
			
			function() {
				$("#ci_"+contact.id).show(1000);
			},
			function() {
				$("#ci_"+contact.id).hide(1000);
			}

	);

	var $div=$("<div/>");
	$div.attr("id","ci_"+contact.id);
	$cdiv.append($div);
	
	var $table=$("<table/>");
	
	$table.append(createRow("First Name",{type:"text",id:"firstname"+contact.id,value:contact.firstname}));
	$table.append(createRow("Last Name",{type:"text",id:"lastname"+contact.id,value:contact.lastname}));
	$table.append(createRow("First Name(EN)",{type:"text",id:"firstname_en"+contact.id,value:contact.firstname_en}));
	$table.append(createRow("Last Name(EN)",{type:"text",id:"lastname_en"+contact.id,value:contact.lastname_en}));
	$table.append(createRow("Title",{type:"text",id:"title"+contact.id,value:contact.title}));
	$table.append(createRow("Home Phone",{type:"text",id:"homephone"+contact.id,value:contact.homephone}));
	$table.append(createRow("Office Phone",{type:"text",id:"officephone"+contact.id,value:contact.officephone}));
	$table.append(createRow("Fax No",{type:"text",id:"faxno"+contact.id,value:contact.faxno}));
	$table.append(createRow("Email",{type:"text",id:"email"+contact.id,value:contact.email}));
			
	$div.append($table);
	var $btn=$("<input/>",{type:"button",value:"Save"});
	$btn.addClass("btn");
	$btn.click(function(){
		$("#statusshower").show(1000);
		services.call("company.updateContact", [contact.id,
		                                        $("#firstname"+contact.id).val(),
		                                        $("#lastname"+contact.id).val(),
		                                        $("#firstname_en"+contact.id).val(),
		                                        $("#lastname_en"+contact.id).val(),
		                                        $("#homephone"+contact.id).val(),
		                                        $("#officephone"+contact.id).val(),
		                                        $("#title"+contact.id).val(),
		                                        $("#faxno"+contact.id).val(),
		                                        $("#email"+contact.id).val()
		                                        ], afUpdate);
	});
	$div.append($btn);
	$div.addClass("c_div");
    $div.hide();

	$("#contact-form").dialog("close");		
};
