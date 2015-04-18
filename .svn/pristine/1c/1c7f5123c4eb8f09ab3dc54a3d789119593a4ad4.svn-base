<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script src="js/jquery.bgiframe-2.1.2.js"></script>
<script src="js/jquery.ui.core.js"></script>
<script src="js/jquery.ui.widget.js"></script>
<script src="js/jquery.ui.mouse.js"></script>
<script src="js/jquery.ui.button.js"></script>
<script src="js/jquery.ui.draggable.js"></script>
<script src="js/jquery.ui.position.js"></script>
<script src="js/jquery.ui.dialog.js"></script>
<script type="text/javascript" >

	var init_product_dialog_flag_PC = false;
	//init dialog of purpose code.
	$(function() {
		if(!init_product_dialog_flag_PC){
			$("#purpose-form").dialog({
				resizable : true,
				width : 350,
				height : 280,
				modal : true,
				buttons : {
					"OK" : function(){
						OK_FUNC_PC();
						RESET_PC();
					},
					Cancel : function() {
						CANCEL_FUNC_PC();
						RESET_PC();
					}
				}
			});
			init_product_dialog_flag_PC = true;
			$("#purpose-form").dialog("close");
		}
	});
	
	var OK_FUNC_PC = function() {
		$("#statusshower").show();
		var code = getPCode();
		services.call(PURPOSE_CREATE, [code.code, code.description, compId], afCreatePCode);
	};
	
	var afCreatePCode = function(obj) {
		if(!obj.success) {
			alert("some errors occur while processing request, purpose");
			return;
		}
		addPCode(obj.data);
		$("#statusshower").hide();
	};
	
	var initPurposeCode = function(){
		$("#statusshower").show();
		services.call(PURPOSE_LIST, [compId], afListPCode);
	};
	
	var CANCEL_FUNC_PC = function(){
		$("#purpose-form").dialog("close");
	};
	
	var RESET_PC=function() {
		$("#purpose-code").val("");
		$("#purpose-description").val("");
	};
	
	var getPCode = function() {
		return {
			code : $("#purpose-code").val(),
			description : $("#purpose-description").val()
		};
	};

	var addPCode = function(code){
		
		$table = $("#purposePool");
		$tr = $("<tr>");
		$tr.attr("id", "purposeCode_"+code.id);
		$table.append($tr);
		
		$td = $("<td>");
		$td.append($("<input/>",{type:"text",id:"purposeCode" + code.id, value:code.purposeCode}));
		$tr.append($td);
		$("#purposeCode" + code.id).addClass("purpose_code");
		$("#purposeCode" + code.id).keyup(function(){
			$("#action_update_pc_" + code.id).show();
		});
		
		$td = $("<td>");		
		$td.append($("<input/>",{type:"text",id:"description" + code.id, value:code.description}));
		$tr.append($td);
		$("#description" + code.id).addClass("purpose_description");
		$("#description" + code.id).keyup(function(){
			$("#action_update_pc_" + code.id).show();
		});
		
		//action column.
		$td = $("<td>");
		$btn=$("<img src='img/tick.png' width='26' height='26' style='cursor:hand' id='action_update_pc_" + code.id + "'/>");	
		$btn.click(function() {
			$("#statusshower").show();
			services.call(PURPOSE_UPDATE, [code.id,
	                                        $("#purposeCode" + code.id).val(),
	                                        $("#description"+code.id).val()
	                                        ], afUpdate);
			$(this).hide();
		});
		$td.append($btn);
		$btn=$("<img src='img/MinusRed.png' width='26' height='26' style='cursor:hand'/>");
		$btn.click(function() {
			$("#statusshower").show();
			services.call(PURPOSE_DELETE, [code.id], afDeletePCode);
		});
		$td.append($btn);
		$tr.append($td);
		$("#purpose-form").dialog("close");		
	};
	
	function afDeletePCode(obj){
		if(!obj.success) {
			alert("some errors occur while processing request, purpose");
			return;
		}
		$("#purposeCode_"+obj.data.id).remove();
		$("#statusshower").hide(100);
	}
	
	var afListPCode = function(obj) {
		
		if(!obj.success) {
			alert("some errors occur while processing request, purpose");
			return;
		}
		
		var list = obj.data;
		for(var i=0;i<list.length;i++) {
			addPCode( list[i] );
		}
		
		$("#statusshower").hide();
	};
	
</script>

<div id="purpose-form" title="Form - Purpose Code" style="display:none">
	<div id="purpose-form_message"> 
		<input type="hidden" id="purpose-id" name="purpose.id">
		<table>
			<tr>
				<td>Purpose Code</td> 
			</tr>
			<tr>
				<td>
					<input type="text" id="purpose-code" />
				</td>
			</tr>
			<tr>
				<td>Description</td>
			</tr>
			<tr>
				<td>
					<input type="text" id="purpose-description" />
				</td>
			</tr>
		</table>
	</div>
</div>