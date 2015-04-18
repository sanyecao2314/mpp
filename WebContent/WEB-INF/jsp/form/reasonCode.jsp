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

	var init_product_dialog_flag_RC = false;
	//init dialog of reason code.
	$(function() {
		if(!init_product_dialog_flag_RC){
			$("#reason-form").dialog({
				resizable : true,
				width : 350,
				height : 280,
				modal : true,
				buttons : {
					"OK" : function(){
						OK_FUNC_RC();
						RESET_RC();
					},
					Cancel : function() {
						CANCEL_FUNC_RC();
						RESET_RC();
					}
				}
			});
			init_product_dialog_flag_RC = true;
			$("#reason-form").dialog("close");
		}
	});
	
	var OK_FUNC_RC = function() {
		$("#statusshower").show();
		var code = getCode();
		services.call(REASON_CREATE, [code.code, code.productCode, code.description, compId], afCreateCode);
	};
	
	var afCreateCode = function(obj) {
		if(!obj.success) {
			alert("some errors occur while processing request, reason");
			return;
		}
		addCode(obj.data);
		$("#statusshower").hide();
	};
	
	var initReasonCode = function(){
		$("#statusshower").show();
		services.call(REASON_LIST, [compId], afListCode);
	};
	
	var CANCEL_FUNC_RC = function(){
		$("#reason-form").dialog("close");
	};
	
	var RESET_RC=function() {
		$("#reason-code").val("");
		$("#reason-product").val("");
		$("#reason-description").val("");
	};
	
	var getCode = function() {
		return {
			code : $("#reason-code").val(),
			productCode : $("#reason-product").val(),
			description : $("#reason-description").val()
		};
	};

	var addCode = function(code){
		
		$table = $("#reasonPool");
		$tr = $("<tr>");
		$tr.attr("id", "reasonCode_"+code.id);
		$table.append($tr);
		
		$td = $("<td>");
		$td.append($("<input/>",{type:"text",id:"reasonCode" + code.id, value:code.reasonCode}));
		$tr.append($td);
		$("#reasonCode" + code.id).addClass("reason_code");
		$("#reasonCode" + code.id).keyup(function(){
			$("#action_update_" + code.id).show();
		});
		
		$td = $("<td>");
		
		$select=$("<select/>",{id:"productCode" + code.id});
		$select.html($productselect.html());
		$select.change(function(){	
			$("#action_update_" + code.id).show();
		});
		$td.append($select);
		
		$tr.append($td);
		$("#description" + code.id).addClass("reason_description");
		$("#description" + code.id).keyup(function(){
			$("#action_update_" + code.id).show();
		});
		
		$td = $("<td>");		
		$td.append($("<input/>",{type:"text",id:"description" + code.id, value:code.description}));
		$tr.append($td);
		$("#description" + code.id).addClass("reason_description");
		$("#description" + code.id).keyup(function(){
			$("#action_update_" + code.id).show();
		});
		
		var $options=$("#productCode"+code.id+" option");
		for(var i=0;i<$options.length;i++){
			
			if(code.productCode == $options.eq(i).val()){
				$options.eq(i)[0].selected = true;
			} else {
				$options.eq(i)[0].selected = false;
			}
		}
		
		//action column.
		$td = $("<td>");
		$btn=$("<img src='img/tick.png' width='26' height='26' style='cursor:hand' id='action_update_" + code.id + "'/>");	
		$btn.click(function() {
			$("#statusshower").show();
			services.call(REASON_UPDATE, [code.id,
	                                        $("#reasonCode" + code.id).val(),
	                                        $("#productCode" + code.id).val(),
	                                        $("#description"+code.id).val()
	                                        ], afUpdate);
			$(this).hide();
		});
		$btn.hide();
		$td.append($btn);
		$btn=$("<img src='img/MinusRed.png' width='26' height='26' style='cursor:hand'/>");
		$btn.click(function() {
			$("#statusshower").show();
			services.call(REASON_DELETE, [code.id], afDeleteCode);
		});
		$td.append($btn);
		$tr.append($td);
		$("#reason-form").dialog("close");
	};
	
	function afDeleteCode(obj){
		if(!obj.success) {
			alert("some errors occur while processing request, reason");
			return;
		}
		$("#reasonCode_"+obj.data.id).remove();
		$("#statusshower").hide(100);
	}
	
	var afListCode = function(obj) {
		
		if(!obj.success) {
			alert("some errors occur while processing request, reason");
			return;
		}
		
		var list = obj.data;
		for(var i=0;i<list.length;i++) {
			addCode( list[i] );
		}
		
		$("#statusshower").hide();
	};
	
	$(function(){
		$("#reason-product").html($productselect.html());	
	});
	
</script>

<div id="reason-form" title="Form - Reason Code" style="display:none">
	<div id="product-form_message"> 
		<input type="hidden" id="reason-id" name="reason.id">
		<table>
			<tr>
				<td>Reason Code</td> 
			</tr>
			<tr>
				<td>
					<input type="text" id="reason-code" />
				</td>
			</tr>
			
			<tr>
				<td>Product Code</td>
			</tr>
			<tr>
				<td>
					<select id="reason-product">
					</select>
				</td>
			</tr>
			
			<tr>
				<td>Description</td>
			</tr>
			<tr>
				<td>
					<input type="text" id="reason-description" />
				</td>
			</tr>
		</table>
	</div>
</div>