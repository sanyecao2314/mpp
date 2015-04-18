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
	var init_product_dialog_flag_EX = false;
	var OK_FUNC_EX = function() {
		$("#statusshower").show();
		var obj = getExtra();
		//call_extra_service_create(obj);
		validate2("create",obj);
	};
	
	var call_extra_service_create= function(obj){
		services.call(EXTRA_CREATE, [obj.level, obj.fieldName, obj.mandatory,
                                     obj.action, obj.displayOrder, obj.defaultValue,
                                     compId], afCreateExtra);
	};
	
	var call_extra_service_update= function(obj){
		services.call(EXTRA_UPDATE, [obj.id,
                                     $("#level" + obj.id).val(),
                                     $("#fieldName" + obj.id).val(),
                                     $("#mandatory" + obj.id).val(),
                                     $("#action" + obj.id).val(),
                                     $("#displayOrder" + obj.id).val(),
                                     $("#defaultValue" + obj.id).val()
                                     ], afUpdate);
	};
	
	var initExtra = function(){
		$("#statusshower").show();
		services.call(EXTRA_LIST, [compId], afListExtra);
	};
	
	var afListExtra = function(obj){
		if(!obj.success) {
			alert("some errors occur while processing request, extra");
			return;
		}
		var list = obj.data;
		for(var i=0;i<list.length;i++){
			addExtra( list[i] );
		}
		$("#statusshower").hide();
	};
	var afCreateExtra = function(obj) {
		if(!obj.success) {
			alert("some errors occur while processing request, extra");
			return;
		}
		addExtra(obj.data);
		$("#statusshower").hide();
		
	};
	
	var addExtra = function(obj){
		
		var $cdiv=$("<div/>");
		$cdiv.attr("id","exc_" + obj.id);
		$("#extraPool").append($cdiv);

		var $label = $("<div/>");
		$label.addClass("extra");
		$label.attr("id", obj.id);
		$cdiv.append($label);
		$label.html("Extra Data " + $(".extra").length);
		var $btn=$("<input/>",{type:"button",value:"delete"});
		$cdiv.append($btn);
		$btn.click(function(){
			services.call(EXTRA_DEL, [obj.id], afDeleteExtra);
		});
		$label.toggle(
				
				function() {
					$("#exi_" + obj.id).show(1000);
				},
				function() {
					$("#exi_" + obj.id).hide(1000);
				}

		);

		var $div=$("<div/>");
		$div.attr("id","exi_" + obj.id);
		$cdiv.append($div);
		
		var $table=$("<table/>");
				
		$table.append(insertTableRow(["Level:"],[$("<input/>",{type:"text",id:"level" + obj.id, value:obj.level,readonly:true})]));
		
		$table.append(insertTableRow(["Field Name:"],[$("<input/>",{type:"text",id:"fieldName" + obj.id, value:obj.fieldName,readonly:true})]));
		$table.append(insertTableRow(["Mandatory:"],[$("<input/>",{type:"checkbox",id:"mandatory" + obj.id,value:0})]));
		$table.append(insertTableRow(["Active:"],[$("<input/>",{type:"checkbox",id:"action" + obj.id,value:0})]));
		$table.append(insertTableRow(["Display Order:"],[$("<input/>",{type:"text",id:"displayOrder" + obj.id, value:obj.displayOrder})]));
		$table.append(insertTableRow(["defaultValue:"],[$("<input/>",{type:"text",id:"defaultValue" + obj.id, value:obj.defaultValue})]));
		$div.append($table);
		
		var $btn=$("<input/>",{type:"button",value:"Save"});
		$btn.addClass("btn");
		$btn.click(function(){
			$("#statusshower").show(1000);
			//call_extra_service_update(obj);
			validate2("update",obj);
		});
		$div.append($btn);
		$div.addClass("c_div");
	    $div.hide();
	    
	    if(obj.mandatory==1){
	    	$("#mandatory" + obj.id)[0].checked=true;
	    }else{
	    	$("#mandatory" + obj.id)[0].checked=false;
	    }
	    
	    if(obj.action==1){
	    	$("#action" + obj.id)[0].checked=true;
	    }else{
	    	$("#action" + obj.id)[0].checked=false;
	    }
	    
		$("#mandatory" + obj.id).click(function(){
			if($(this)[0].checked){
				$(this).val("1");
			}else{
				$(this).val("0");
			}
		});
		$("#action" + obj.id).click(function(){
			if($(this)[0].checked){
				$(this).val("1");
			}else{
				$(this).val("0");
			}
		});
	     
	    $("#level" + obj.id).css("backgroundColor","#ccc");
	    $("#fieldName" + obj.id).css("backgroundColor","#ccc");
	    $("#ex-form").dialog("close");	
	};
	
	function afDeleteExtra(obj){
		if(!obj.success) {
			alert("some errors occur while processing request, extra");
			return;
		}
		$("#exc_"+obj.data.id).remove();
	}
	
	var CANCEL_FUNC_EX = function() {
		$("#ex-form").dialog("close");
		$("#statusshower").hide();
	};

	$(function() {

		if (!init_product_dialog_flag_EX) {
			$("#ex-form").dialog({
				resizable : true,
				width : 330,
				height : 420,
				modal : true,
				buttons : {
					"OK" : function() {
						OK_FUNC_EX();
						RESET_EX();
					},
					Cancel : function() {
						CANCEL_FUNC_EX();
						RESET_EX();
					}
				}
			});
			init_product_dialog_flag_EX = true;
			$("#ex-form").dialog("close");
		}
		
		$("#extmandatory").click(function(){
			if($(this)[0].checked){
				$(this).val("1");
			}else{
				$(this).val("0");
			}
		});
		$("#extaction").click(function(){
			if($(this)[0].checked){
				$(this).val("1");
			}else{
				$(this).val("0");
			}
		});
	});

	var RESET_EX = function() {
		 $("#extlevel").val("");
		 $("#extfieldName").val("");
		 $("#extmandatory")[0].checked = false;
		 $("#extaction")[0].checked = false;
		 $("#extdisplayOrder").val("");
		 $("#extdefaultValue").val("");
	};

	var getExtra = function() {
		return {
			level : $("#extlevel").val(),
			fieldName : $("#extfieldName").val(),
			mandatory : $("#extmandatory").val(),
			action : $("#extaction").val(),
			displayOrder : $("#extdisplayOrder").val(),
			defaultValue : $("#extdefaultValue").val()
		};
	};
</script>

<div id="ex-form" title="Form - Required Data" style="display:none">
<div id='trav7'><div id='comp7'>
	<div id="product-form_message">
		<input type="hidden" id="ex-id" name="extra.id">

		<table>
			<tr>
				<td>Level:</td>
			</tr>
			<tr>
				<td>
					<select id="extlevel" class="validator">
						<option>Booking_Level</option>
						<option>Item_Level</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>FieldName:</td>
			</tr>
			<tr>
				<td>
					<select id="extfieldName" class="validator">
						<option>10000</option>
						
					</select>
				</td>
			</tr>


			<tr>
				<td>Mandatory:</td>
			</tr>
			<tr>
				<td><input type="checkbox" id="extmandatory" class="validator" value="0"/></td>
			</tr>
			<tr>
				<td>Active</td>
			</tr>
			<tr>
				<td><input type="checkbox" id="extaction" class="validator" value="0"/></td>
			</tr>
			<tr>
				<td>Display Order</td>
			</tr>
			<tr>
				<td><input type="text" id="extdisplayOrder" class="validator" /></td>
			</tr>
			<tr>
				<td>Default Value</td>
			</tr>
			<tr>
				<td><input type="text" id="extdefaultValue" class="validator" /></td>
			</tr>

		</table>
	</div>
	</div>
	</div>
</div>




