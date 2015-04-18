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
<script type="text/javascript" src="js/jquery.timers.js"></script>
<script type='text/javascript'
	src='${pageContext.request.contextPath}/dwr/interface/services.js'></script>
<script type='text/javascript'
	src='${pageContext.request.contextPath}/dwr/engine.js'></script>

<style>
#sortable1 {
	list-style-type: none;
	margin: 0;
	padding: 0;
	width: 400px;
}

#sortable1 li {
	margin: 0 5px 5px 5px;
	padding: 5px;
	font-size: 1.0em;
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
	font-size: 1.0em;
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

#sortable3 {
	list-style-type: none;
	margin: 0;
	padding: 0;
	width: 500px;
}

#sortable3 li {
	margin: 0 5px 5px 5px;
	padding: 5px;
	font-size: 1.0em;
	height: 1.0em;
}

html>body #sortable3 li {
	height: 1.0em;
	line-height: 1.0em;
}

.ui-state-highlight {
	height: 1.0em;
	line-height: 1.0em;
}
</style>
<script src="js/customer_define.js"></script>
<script type="text/javascript">
		var compId = "${customer.id}";
		var destId = "S${customer.id}";
		var feeCodeId = "";
		var COPY_ACTION = "";
		
		$(document).ready(function(){
			$("#tab1").addClass("tab_act").removeClass("tab_out");
			
			$("#tabs").tabs({"selected":${tabSelected}});		
			$( "#sortable1" ).sortable({
				placeholder: "ui-state-highlight"
			});
			$( "#sortable1" ).disableSelection();
						
			$( "#sortable2" ).sortable({
				placeholder: "ui-state-highlight"
			});
			$( "#sortable2" ).disableSelection();
			
			$( "#sortable3" ).sortable({
				placeholder: "ui-state-highlight"
			});
			
			$( "#sortable3" ).disableSelection();
			
			$( "#policy_list" ).sortable({
				placeholder: "ui-state-highlight"
			});
			$( "#policy_list" ).disableSelection();
					
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
			
			$("#btnCCard").click(function() {
				$("#holderName").val($("#traveler-travelerName").val());
				$("#ccard-form").dialog("open");
			});
			
			initFeeCode();
			initDiscount();
			initReasonCode();
			initCCard();
			
			$("input:text").css("width", "300px");
			
			$(".gmaxNoLink").each(function(){
				$(this).click(function(){
					$("#gmax-form").dialog("open");
					$("#gmax-no").val($(this).html());
					initGmax();
				});
			});
		});
		
		var afCreateDiscount = function(obj) {
			if(!obj.success) {
				alert("some errors occur while processing request.");
				return;
			}
			addDiscount(obj.data);
			$("#statusshower").hide();
		};
		
		var addDiscount = function(discount){
			
			var $cdiv=$("<div/>");
			$cdiv.attr("id","dc_" + discount.id);
			$("#discountPool").append($cdiv);
					
			var $label = $("<div/>");
			$label.addClass("discount");
			$label.attr("id", discount.id);
			$cdiv.append($label);
			$label.html("Discount " + $(".discount").length);
			var $btn=$("<input/>",{type:"button",value:"delete"});
			$cdiv.append($btn);
			$btn.click(function() {
				services.call("customer.deleteDiscount", [discount.id], afDeleteDiscount);
			});
			
			$label.toggle(
					
					function() {
						$("#di_"+discount.id).show(1000);
					},
					function() {
						$("#di_"+discount.id).hide(1000);
					}

			);

			var $div=$("<div/>");
			$div.attr("id","di_"+discount.id);
			$cdiv.append($div);
			
			var $table=$("<table/>");
					
			var $tr=$("<tr/>");
			$table.append($tr);
			var $td=$("<td/>");
			$td.addClass("contactlabel");
			$tr.append($td);
			var $label=$("<label/>");
			$label.html("Product");
			$td.append($label);
			$td=$("<td/>");
			$tr.append($td);
			var $text=$("<select id='product"+discount.id+"'/>");
			
			<c:forEach var="product" items="${productlist }">
				var $option=$("<option/>");
				$option.attr("value","${product.id }");
				$option.html("${product.productNo }-${product.productName }");
				$text.append($option);
			</c:forEach>
			$td.append($text);
			
			
			
			$table.append(createRow("Discount Rate",{type:"text",id:"rate" + discount.id,value:discount.discountRate}));
			$table.append(createRow2("Remark",{type:"text",id:"remark" + discount.id,html:discount.remark,cols:70,rows:8}));
					
			$div.append($table);
			
			var $options=$("#product"+discount.id+" option");
			for(var i=0;i<$options.length;i++){
				
				if(discount.prodId == $options.eq(i).val()){
					$options.eq(i)[0].selected = true;
				} else {
					$options.eq(i)[0].selected = false;
				}
			}
			
			var $btn=$("<input/>",{type:"button",value:"Save"});
			$btn.addClass("btn");
			$btn.click(function(){
				$("#statusshower").show(1000);
				
				services.call("company.updateDiscount", [discount.id,
				                                        $("#product"+discount.id).val(),
				                                        $("#remark"+discount.id).val(),
				                                        $("#rate" + discount.id).val()
				                                        ], afUpdate);
			});
			$div.append($btn);
			$div.addClass("c_div");
		    $div.hide();

			$("#discount-form").dialog("close");		
		};
	
	var deleteItem = function(){
		
	};
		var createGmaxNo = function(){
			
			var p = $("#sortable1");
			var gmaxno = $("#txtGMaxNo").val();
			if($("a[id='"+gmaxno+"']").length>0){
				alert("duplicated Gmax No");
				return;
			}
		   	var item = $("<li/>");
		   	var link = $("<a href='#'/>");
		   	link.append(gmaxno);
		   	link.addClass("gmaxNoLink");
		   	link.attr("id", gmaxno);
		   	link.click(function(){
				$("#gmax-form").dialog("open");
				$("#gmax-no").val($(this).html());
				initGmax();
			});
		   	item.append(link);
		   	
		    item.addClass("ui-state-default");
		    p.append(item);
		    $("#txtGMaxNo").val("");
		    p.append($("<input>",{type:"hidden",name:"customer.gmaxes",value:gmaxno}));
		};
		
		var createCC = function(){
			var p = $("#sortable3");
			var cc=$("#txtCC").val();
		   	var item = $("<li>"+cc+"</li>");
		    item.addClass("ui-state-default");
		    p.append(item);
		    $("#txtCC").val("");
		    $("#sortable3").append($("<input>",{type:"hidden",name:"customer.costcenters",value:cc}));
		};
		
		var createDepartment = function() {
			var p = $("#sortable2");
			var departments = $("[name=txtdepartment]");
			var tmrParNo = $("[name=txtTmrPar]").val();
			var tmrParName = $("[name=txtTmrParName]").val();
			var tmrParGmax = $("[name=txtTmrParGmax]").val();

			var department = "";
			for(var i=0;i<departments.length;i++){
				if(departments[i].value=="")
					department +="/";
				else
					department += departments[i].value;
				if(i != departments.length - 1)
					department += "-";
				departments[i].value="";
			}
			
		   	var item = $("<li>"+""+tmrParNo+";"+department+";"+tmrParGmax+";"+tmrParName+"</li>");
		    item.addClass("ui-state-default");
		    p.append(item);
		    $("#txtdepartment").val("");
		    $("#txtTmrPar").val("");
		    $("[name=txtTmrParName]").val("");
		    $("[name=txtTmrParGmax]").val("");
		    $("[name=txtTmrPar]").val("");
		    $("#sortable2").append($("<input>",{type:"hidden",name:"customer.departments",value:tmrParNo + ":" + department}));
		};

		$(function(){
			
			$( "#dialog-confirm" ).dialog({
				resizable: false,
				height:200,
				modal: true,
				buttons: {
					"Delete": function() {
						deleteItem();
						$( this ).dialog( "close" );
					},
					Cancel: function() {
						$( this ).dialog( "close" );
					}
				}
			});

			$( "#dialog-confirm" ).dialog( "close" );
			
			$("#sortable1").dblclick(function(event){
				if(event.target.tagName=="LI"){
					$( "#dialog-confirm" ).dialog( "open" );
					deleteItem=function(){
						$("#sortable1")[0].removeChild(event.target);
					};
				}
			});
			
			$("#sortable2").dblclick(function(event){
				if(event.target.tagName=="LI"){					
					$( "#dialog-confirm" ).dialog( "open" );
					deleteItem=function(){
						$("#sortable2")[0].removeChild(event.target);
					};
				}
			});
			
			$("#policy_list").dblclick(function(event){
				if(event.target.tagName=="LI"){					
					$( "#dialog-confirm" ).dialog( "open" );
					deleteItem=function(){
						$("#policy_list")[0].removeChild(event.target);
					};
				}
			});
			
			$("#sortable3").dblclick(function(event){
				if(event.target.tagName=="LI"){					
					$( "#dialog-confirm" ).dialog( "open" );
					deleteItem = function(){
						$("#sortable3")[0].removeChild(event.target);
					};
				}
			});
			
		});	

		$(function() {
			$("#btnGMaxNo").click(createGmaxNo);
			$("#btndepartment").click(createDepartment);
			$("#btnCC").click(createCC);
			$("#btnTerm").click(function(){
				$("#fc-form").dialog("open");
			});

			$("#updateCust").click(function() {
				var $hidden=$("#sortable1 input:hidden");
				var $li=$("#sortable1 li a");
				for(var i=0;i<$hidden.length;i++) {
					$hidden.eq(i).val($li.eq(i).html());
				}
				
				var $hidden2=$("#sortable2 input:hidden");
				var $li2=$("#sortable2 li");
				for(var i=0;i<$hidden2.length;i++) {
					$hidden2.eq(i).val($li2.eq(i).html());
				}
				
				var $hidden3=$("#sortable3 input:hidden");
				var $li3=$("#sortable3 li");
				for(var i=0;i<$hidden3.length;i++) {
					$hidden3.eq(i).val($li3.eq(i).html());
				}
				
				var $hidden3=$("#policy_list input:hidden");
				var $li3=$("#policy_list li");
				for(var i=0;i<$hidden3.length;i++) {
					if(i>=$li3.length){
						$hidden3.eq(i).remove();
						continue;
					}
					$hidden3.eq(i).val($li3.eq(i).attr("id"));
				}
				$("#tabSelected").val($("#tabs").tabs("option", "selected"));
				$("#form1").submit();
			});
			
			$("#returnCust").click(function(){
				location.href='redirect.action?url=cust';
			});
			
		});
		
		$(function() {
			var clauses = "${customer.clauseString}";
			services.call("customer.listClauseByString", [compId,clauses], afterlistClause);
		});
		
		var afterlistClause = function(obj) {
			if(!obj.success) {
				alert("some errors occur while processing request.");
				return;
			}
		
			var clauseStr="${customer.clauseString}";
			
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
			
			$("#btnUpload").click(function(){
				$("#upload-form").dialog( "open" );
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

		var initDiscount = function(){
			$("#statusshower").show();
			services.call("customer.listDiscount", [compId], afListDiscount);
		};
		
		var afListDiscount = function(obj){
			if(!obj.success) {
				alert("some errors occur while processing request.");
				return;
			}
			var list = obj.data;
			for(var i=0;i<list.length;i++){
				addDiscount( list[i] );
			}
			$("#statusshower").hide();
		};
		
		

		function afDeleteDiscount(obj){
			if(!obj.success) {
				alert("some errors occur while processing request.");
				return;
			}
			$("#dc_"+obj.data.id).remove();
		}

		function afUpdate(obj){
			if(!obj.success) {
				alert("some errors occur while processing request.");
				return;
			}
			$("#statusshower").hide(1000);
		}
		
		
		$(function(){
			
			
			$("#btnReasonCode").click(function() {
				$("#reason-form").dialog("open");
			});
			
			$("input[name='txtdepartment']").css("width","60px");
			$("input[name='txtTmrPar']").css("width","120px");
			$("input[name='txtTmrParName']").css("width","250px");
			
		});
			
		function validate1() {
			
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
			$("#statusshower").show(1000);
		}
		
		var act = "";
		var params;
		
		function validate2(action,parameters) {
			act=action;
			params=parameters;
			var tabIndex = $("#tabs").tabs("option", "selected");
			
			var tabId="#cust"+(tabIndex);
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
			var tabId="#cust"+(tabIndex);
			
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
			$("#updateCust").removeAttr("disabled");
			
		});
		
		var serverFile="";
		var fields = "";
		
		function startUpload() {
			serverFile="";
			fields = "";
			
			$("#btnStartUpload").attr("disabled",true);
			serverFile = $("#serverFile").val();
			if(serverFile=='') {
				alert("Please choose a excel(.xls) file and upload");
				return;
			}
			
			var $options = $(".fieldsMapping option:selected");
			
			for(var i=0; i<$options.length; i++) {
				fields += $options.eq(i).val();
				if(i < $options.length - 1) {
					fields += ",";
				}
			}
			$("#statusshower").show();
			services.call("traveler.checkUpload", [serverFile, fields], afcheckUpload);
		}
		
		function afcheckUpload(obj){
			if(obj == "") {
				services.call("common.addTask", [], afAddTask);
			}else{
				$("#uploadMessage").html(obj);
				$("#statusshower").hide(1000);
			}
		}
		
		function afprocUpload(obj){
			
			if(obj == ""){
				$("#uploadMessage").html("<h4><strong>Completed.</strong></h4>");				
			} else {
				$("#uploadMessage").html("<h4><strong>"+obj+"</strong></h4>");
			}
			
			$(document).stopTime();			
			$("#statusshower").html("Loading...");
			$("#statusshower").hide(1000);
		}
				
		function afAddTask(obj){
			$(document).stopTime();
			services.call("traveler.processUpload", [obj, serverFile, fields], afprocUpload);
			//services.call(LOAD_ACTION, [obj, $("#parload-groupname").val(), $("#parload-no").val()], afLoad);
			$(document).everyTime(500, function(i) {
				services.call("common.getTaskStatus", [obj], afGetStatus);
				}, 500);
		}
		
		function afGetStatus(obj){
			$("#statusshower").html("Loading..."+obj+"%");
		}
	</script>

<body>
	<jsp:include page="/WEB-INF/jsp/common/top.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/common/modalconfirmdialog.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/form/clauseselect.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/form/feecode.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/form/feecodeDetail.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/form/discount.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/form/copyfrom.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/form/gmax.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/form/reasonCode.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/form/creditcard.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/form/upload.jsp"></jsp:include>
	<div id="error">${message_message }</div>
	<table width="100%" cellpadding="0" cellspacing="0">
		<tr>
			<td class="loginform" valign="top">
				<form id="form1" action="updateCust.action" method="post">
					<input type="hidden" name="customer.id" value="${customer.id }">
					<input type="hidden" name="customer.name" value="${customer.name }">
					<input type="hidden" name="tabSelected" id="tabSelected"> <input
						type="hidden" name="customer.status" id="customer-status"
						value="ACTIVE">
					<table width="100%" cellpadding="4" cellspacing="0">
						<tr>
							<td><span class="label_bold">Customer name:</span>
							<!-- cpname=customer.id --> <a
								href="${pageContext.request.contextPath}/redirect.action?url=comp&cpname=${customer.id }">${customer.name
									}</a> - <c:if test="${customer.status=='ACTIVE' }">
									<a
										href="${pageContext.request.contextPath}/createComp.action?customer.name=${customer.name }">create
										company</a>
								</c:if></td>
						</tr>
						<tr>
							<td>
								<div class="main">
									<div id="tabs">
										<ul>
											<li><a href="#tabs-1">GMax No</a>
											</li>
											<li><a href="#tabs-2">CostCenter</a>
											</li>
											<li><a href="#tabs-3">TMR</a>
											</li>
											<li><a href="#tabs-4">Credit Card</a></li>
											<li><a href="#tabs-5">Discount</a>
											</li>
											<li><a href="#tabs-6">E-Billing</a>
											</li>
											<li><a href="#tabs-7">Terms</a>
											</li>
											<li><a href="#tabs-8">Policy</a>
											</li>
											<li><a href="#tabs-9">Upload</a>
											</li>
										</ul>
										<div id="tabs-1">
											<div>
												<input type="text" id="txtGMaxNo" size="40"> <input
													type="button" id="btnGMaxNo" value="Add">
											</div>
											<p></p>
											<ul id="sortable1">
												<c:forEach var="gmaxNo" items="${customer.gmaxes}">
													<li class="ui-state-default"><a id="${gmaxNo }"
														class="gmaxNoLink" href="#">${gmaxNo }</a>
													</li>
													<input type="hidden" name="customer.gmaxes"
														value="${gmaxNo }" />
												</c:forEach>
											</ul>
										</div>

										<div id="tabs-2">
											<div>
												<input type="text" id="txtCC" size="40"> <input
													type="button" id="btnCC" value="Add">
											</div>
											<p></p>
											<ul id="sortable3">
												<c:forEach var="costcenter" items="${customer.costcenters}">
													<li class="ui-state-default">${costcenter }</li>
													<input type="hidden" name="customer.costcenters"
														value="${costcenter }" />
												</c:forEach>
											</ul>
										</div>

										<div id="tabs-3">
											<div>
												<table>
													<tr>
														<td>TMR PAR NO:</td>
														<td><input type="text" name="txtTmrPar" size="10">
														</td>
													</tr>

													<tr>
														<td>GMax NO:</td>
														<td><input type="text" name="txtTmrParGmax" size="10">
														</td>
													</tr>

													<tr>
														<td>TMR PAR Name:</td>
														<td><input type="text" name="txtTmrParName" size="50">
														</td>
													</tr>

													<tr>
														<td>TMR L1~L5:</td>
														<td><input type="text" name="txtdepartment" size="10">-
															<input type="text" name="txtdepartment" size="10">-
															<input type="text" name="txtdepartment" size="10">-
															<input type="text" name="txtdepartment" size="10">-
															<input type="text" name="txtdepartment" size="10">
														</td>
													</tr>
												</table>

												<input type="button" id="btndepartment" value="Add">
											</div>
											<p></p>
											<ul id="sortable2">
												<c:forEach var="department" items="${customer.departments}">
													<li class="ui-state-default">${department }</li>
													<input type="hidden" name="customer.departments"
														value="${department }" />
												</c:forEach>
											</ul>
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
													<li>to add new Discount, click <a href="#"
														id="btnDiscount">add</a></li>
													<li>to copy from another, click <a href="#"
														id="btnCopyDiscount">copy</a></li>
												</ul>

												<ul id="discountPool">

												</ul>
											</div>

										</div>

										<div id="tabs-6">
											<div style="background-color: white;">
												<li>to add new Reason Code, click <a href="#"
													id="btnReasonCode">add</a>
												</li>
												<table id="reasonPool">
													<tr>
														<td width="200" class="reason_code_title">Reason Code</td>
														<td class="reason_code_title">Product Code</td>
														<td class="reason_code_title">Description</td>
														<td>&nbsp;</td>
													</tr>
												</table>
											</div>
										</div>

										<div id="tabs-7">
											<div style="background-color: white;">
												<ul>
													<li>to add new Term, click <a href="#" id="btnTerm">add</a>
													</li>
													<li>to copy from another, click <a href="#"
														id="btnCopy">copy</a>
													</li>
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
												<textarea rows="10" cols="100" name="customer.remarks">${customer.remarks }</textarea>
											</div>
										</div>

										<div id="tabs-9">
											<div id="uploadMessage"></div>

											<div id="uploadTable">
												<iframe src="preUpload.action" width="100%" frameborder="0"
													height="100"> </iframe>
											</div>

											<div>
												<input type="hidden" id="serverFile" value="" /> <input
													type="button" id="btnStartUpload" value=" Start Uploading "
													onclick="startUpload()" />
											</div>
										</div>

									</div>
								</div></td>
						</tr>
					</table>
					<input type="button" id="updateCust" value="Save" class="btn"
						disabled="false" /> <input type="button" id="returnCust"
						value="Back" class="btn" />
				</form>
				<div id="statusshower">Processing...</div></td>
		</tr>
	</table>
</body>
</html>



