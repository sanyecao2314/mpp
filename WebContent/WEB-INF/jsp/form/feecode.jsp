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
<script type="text/javascript">
	var init_product_dialog_flag_FC = false;
	var OK_FUNC_FC = function() {
		$("#statusshower").show();
		var obj = getFeeCode();
		services.call(FEECODE_CREATE, [obj.variable, obj.feeType, obj.fareCode, obj.groupId, compId], afCreateFeeCode);
	};
	
	var initFeeCode = function() {
		$("#statusshower").show();
		services.call(FEECODE_LIST, [compId], afListFeeCode);
	};
	
	var afListFeeCode = function(obj) {
		if(!obj.success) {
			alert("some errors occur while processing request.");
			return;
		}
		var list = obj.data;
		for(var i=0; i<list.length; i++) {
			addFeeCode( list[i] );
		}
		$("#statusshower").hide();
	};
	var afCreateFeeCode = function(obj) {
		if(!obj.success) {
			alert("some errors occur while processing request, feecode");
			return;
		}
		addFeeCode(obj.data);
		$("#statusshower").hide();
		
	};
	
	var addFeeCode = function(obj) {
		
		var $div=$("<div/>");
		$div.attr("id",obj.id);
	
		var $table = $("<table/>");
		$div.append($table);
		var $tr = $("<tr/>");
		$table.append($tr);
		
		$btn = $("<input/>",{type:"button",value:"-"});
		$btn.click(function(){
			$( "#dialog-confirm" ).dialog( "open" );
			deleteItem = function(){
				services.call(FEECODE_DEL, [obj.id], afDeleteFeeCode);
			};
		});
				
		$td = $("<td/>");
		$td.addClass("feeCodeItem");
		$td.append($btn);
		$td.append(obj.varname);
		$tr.append($td);
		
		$td = $("<td/>");
		$td.addClass("feeCodeItem");
		$td.append(obj.feeType);
		$tr.append($td);
		
		$td = $("<td/>");
		$td.addClass("feeCodeItem");
		$td.append(obj.fareCode);
		$tr.append($td);
		
		$td = $("<td/>");
		$td.addClass("feeCodeItem");
		$select = $("<select/>",{id:"sel"+obj.id});
		$select.addClass("fc_i_sel");
		
		$td.append($select);
		$text = $("<input/>",{type:"text",id:"text"+obj.id});
		$text.addClass("fc_i_sel");
		$td.append($text);
		$text.css("display","none");
		
		var groups=obj.groupId;
		if(groups!=null && groups!=''){
			groups=groups.split(";");
		}else{
			groups=[];
		}
		
		var defaultGroup = obj.defaultGroup;
		for(i=0;i<groups.length;i++) {
			var $option = $("<option/>",{html:groups[i], val:groups[i]});
			if(groups[i] == defaultGroup){
				$option[0].selected = true;
			}
			$select.append($option);
		}
		
		$select.change(function(){
			updateFeeCode(obj.id);
		});
		
		$text.blur(function(){
			$("#text" + obj.id).css("display","none");
			$("#sel" + obj.id).css("display","block");
			
			var textValue=$("#text" + obj.id).val();
			var selValue=$("#sel" + obj.id).val();			
			if(textValue!=selValue){
				var $option = $("<option/>",{html: textValue, val: textValue});
				$option[0].selected = true;
				$("#sel" + obj.id).append($option);	

				updateFeeCode(obj.id);
			}
			$("#sel" + obj.id).focus();		
		});

		$tr.append($td);
		
		$td = $("<td/>");
		$btn1=$("<input/>",{type:'button',name:'add',value:'+'});
		$btn1.click(function(){
			$("#sel" + obj.id).css("display","none");
			$("#text" + obj.id).css("display","block");
			$("#text" + obj.id).val($("#sel" + obj.id).val());
			$("#text" + obj.id).focus();
			updateFeeCode(obj.id);
		});
		$td.append($btn1);
		
		$btn2=$("<input/>",{type:'button',name:'rmv',value:'-'});
		$btn2.click(function(){
			var value = $("#sel" + obj.id).val();
			$("#sel" + obj.id+" option:selected").remove();
			updateFeeCode(obj.id);
		});
		$td.append($btn2);
		$tr.append($td);
		
		$tr.dblclick(function(){
			var value = $("#sel" + obj.id).val();
			if(value == "" || value == null){
				alert("Please select the group ID");
				return;
			}
			
			$("#fcd-form").dialog("open");
			$("#fcdvalidFrom").datepicker({
				dateFormat : 'yy-mm-dd'
			});
			
			$("#fcdvalidTo").datepicker({
				dateFormat : 'yy-mm-dd'
			});
			feeCodeId = obj.id;
			groupid = $("#sel" + obj.id).val();
			
			$("#fcdGroupId").html($("#sel" + obj.id).html());
			defaultValue($("#fcdGroupId"),groupid);
			initFeeCodeDetail();
			
		});
		$("#termsPool").append($div);
		$("#fc-form").dialog("close");	
	};
	
	function updateFeeCode(id){
		var $select = $("#sel" + id);
		var $options = $("#sel" + id +" option");
		
		var group = "";
		for(i=0;i<$options.length;i++) {
			var $option=$options.eq(i);
			group += $option.val();
			if(i<$options.length-1) {
				group += ";";
			}
		}
		var defaultGroup = $select.val();

		services.call(FEECODE_UPDATE, [id, group, defaultGroup], afUpdateFeeCode);
	}
	
	function afUpdateFeeCode(obj){
		if(!obj.success) {
			alert("some errors occur while processing request, feecode");
			return;
		}
	}
	
	function afDeleteFeeCode(obj) {
		if(!obj.success) {
			alert("some errors occur while processing request, feecode");
			return;
		}
		$("#"+obj.data.id).remove();
	}
	
	var CANCEL_FUNC_FC = function() {
		$("#fc-form").dialog("close");
	};

	$(function() {
		if (!init_product_dialog_flag_FC) {
			$("#fc-form").dialog({
				resizable : true,
				width : 330,
				height : 300,
				modal : true,
				buttons : {
					"OK" : function() {
						OK_FUNC_FC();
						RESET_FC();
					},
					Cancel : function() {
						CANCEL_FUNC_FC();
						RESET_FC();
					}
				}
			});
			init_product_dialog_flag_FC = true;
			$("#fc-form").dialog("close");
		}
	});

	var RESET_FC = function() {
		$("#feevariable").val("");
		$("#feeType").val("");
		$("#fareCode").val("");
	};

	var getFeeCode = function() {
		return {
			variable : $("#feevariable").val(),
			feeType : $("#feeType").val(),
			fareCode : $("#fareCode").val(),
			groupId: ""
		};
	};
</script>

<div id="fc-form" title="Form - Fee Code" style="display:none">
	<div id="product-form_message">
		<input type="hidden" id="fc-id" name="fc.id">

		<table>
			<tr>
				<td>Variable:</td>
			</tr>
			<tr>
				<td><input type="text" id="feevariable" class="validator" /></td>
			</tr>
			
			<tr>
				<td>Fee Type:</td>
			</tr>
			<tr>
				<td><input type="text" id="feeType"  class="validator"/></td>
			</tr>

			<tr>
				<td>Fare Code:</td>
			</tr>
			<tr>
				<td><input type="text" id="fareCode"  class="validator"/></td>
			</tr>

		</table>
	</div>
</div>




