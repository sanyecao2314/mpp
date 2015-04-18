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
	var init_product_dialog_flag_PR = false;
	
	var OK_FUNC_PR = function() {
		$("#statusshower").show();
		var obj = getPrefer();
		//call_pref_service_create(obj);
		validate2("create",obj);
	};
	
	var call_pref_service_create= function(obj){
		services.call(PREFER_CREATE, [obj.catalog, obj.type, obj.value1,
                                      obj.value2, obj.remark, 
                                      compId], afCreatePrefer);
	};
		
	var call_pref_service_update= function(obj){
		services.call(PREFER_UPDATE, [obj.id,
                                      $("#_Pref_Catalog_" + obj.id).val(),
                                      $("#_Pref_Type_" + obj.id).val(),
                                      $("#_Pref_Value1_" + obj.id).val(),
                                      $("#_Pref_Value2_" + obj.id).val(),
                                      $("#_Pref_Remark_" + obj.id).val()
                                      ], afUpdate);
	};
	
	var initPrefer = function(){
		$("#statusshower").show();
		services.call(PREFER_LIST, [compId], afListPrefer);
	};
	
	var afListPrefer = function(obj){
		if(!obj.success) {
			alert("some errors occur while processing request, prefer");
			return;
		}
		var list = obj.data;
		for(var i=0;i<list.length;i++){
			addPrefer( list[i] );
		}
		$("#statusshower").hide();
	};
	
	var afCreatePrefer = function(obj) {
		if(!obj.success) {
			alert("some errors occur while processing request, prefer");
			return;
		}
		addPrefer(obj.data);
		$("#statusshower").hide();
		
	};
	
	var addPrefer = function(obj){
		
		var $cdiv=$("<div/>");
		$cdiv.attr("id","prc_" + obj.id);
		$("#preferPool").append($cdiv);

		var $label = $("<div/>");
		$label.addClass("prefer");
		$label.attr("id", obj.id);
		$cdiv.append($label);
		$label.html(obj.catalog);
		$label.append(" ");
		$label.append(obj.type);
		$label.append(" ");
		$label.append(obj.value1);
		
		$label.toggle(
				
				function() {
					$("#pri_" + obj.id).show(1000);
				},
				function() {
					$("#pri_" + obj.id).hide(1000);
				}

		);

		var $div=$("<div/>");
		$div.attr("id","pri_" + obj.id);
		$cdiv.append($div);
		
		var $table=$("<table/>");
		
		$table.append(insertTableRow(["Catalog:"],[$("<input/>",{type:"text",id:"_Pref_Catalog_" + obj.id, value:obj.catalog,readonly:true})]));
		$table.append(insertTableRow(["Type:"],[$("<input/>",{type:"text",id:"_Pref_Type_" + obj.id, value:obj.type,readonly:true})]));
		$table.append(insertTableRow(["Value1:"],[$("<input/>",{type:"text",id:"_Pref_Value1_" + obj.id, value:obj.value1,readonly:true})]));
		$table.append(insertTableRow(["Value2:"],[$("<input/>",{type:"text",id:"_Pref_Value2_" + obj.id, value:obj.value2,readonly:true})]));
		$table.append(insertTableRow(["Remark:"],[$("<textarea/>",{id:"_Pref_Remark_" + obj.id, html:obj.remark,cols:60,rows:6})]));

		$div.append($table);
		
		$("#_Pref_Catalog_" + obj.id).addClass("validator_sub");
		$("#_Pref_Type_" + obj.id).addClass("validator_sub");
		$("#_Pref_Value1_" + obj.id).addClass("validator_sub");
		$("#_Pref_Value2_" + obj.id).addClass("validator_sub");
		$("#_Pref_Remark_" + obj.id).addClass("validator_sub");
		
		var $btn=$("<input/>",{type:"button",value:"Save"});
		$btn.addClass("btn");
		$btn.click(function(){
			$("#statusshower").show(1000);
			
			//call_pref_service_update(obj);
			validate3("update",obj);
		});
		$div.append($btn);
		
		var $btn=$("<input/>",{type:"button",value:"delete"});
		$btn.addClass("btn");
		$div.append($btn);
		$btn.click(function(){
			services.call(PREFER_DEL, [obj.id], afDeletePrefer);
		});
		
		$div.addClass("c_div");
	    $div.hide();
	    $("#_Pref_Catalog_" + obj.id).css("backgroundColor","#ccc");
	    $("#_Pref_Type_" + obj.id).css("backgroundColor","#ccc");
	    $("#_Pref_Value1_" + obj.id).css("backgroundColor","#ccc");
	    $("#_Pref_Value2_" + obj.id).css("backgroundColor","#ccc");
	    $("#pr-form").dialog("close");	
	};
	
	function afDeletePrefer(obj){
		if(!obj.success) {
			alert("some errors occur while processing request, prefer");
			return;
		}
		$("#prc_"+obj.data.id).remove();
	}
	
	var CANCEL_FUNC_PR = function() {
		$("#pr-form").dialog("close");
		$("#statusshower").hide();
	};

	$(function() {

		if (!init_product_dialog_flag_PR) {
			$("#pr-form").dialog({
				resizable : true,
				width : 390,
				height : 500,
				modal : true,
				buttons : {
					"OK" : function() {
						OK_FUNC_PR();
						RESET_PR();
					},
					Cancel : function() {
						CANCEL_FUNC_PR();
						RESET_PR();
					}
				}
			});
			init_product_dialog_flag_PR = true;
			$("#pr-form").dialog("close");
		}
		
		$("#Pref_catalog").change(function(){
			$("#Pref_value1 option").remove();
			//$("#value2 option").remove();
			
			if($(this).val()!='') {				
				services.call("common.listPreferKey", [$(this).val()], afLsPrefer);
			}
		});
		
		function afLsPrefer(list){
			$option=$("<option/>",{val:"",text:"Please select..."});
			$("#Pref_value1").append($option);
			if(list!=null) {
				for(i=0;i<list.length;i++) {
					var code = list[i];
					$option=$("<option/>",{val:code.value,text:code.value});
					$("#Pref_value1").append($option);
				}
			}
		}
		
		function afLsPreferV1(list){
			$option=$("<option/>",{val:"",text:"Please select..."});
			$("#Pref_value2").append($option);
			if(list!=null) {
				for(i=0;i<list.length;i++) {
					var code = list[i];
					$option=$("<option/>",{val:code.value,text:code.value});
					$("#Pref_value2").append($option);
				}
			}
		}
		
	});

	var RESET_PR = function() {
		$("#Pref_catalog").val("");
		$("#Pref_type").val("");
		$("#Pref_remark").val("");
		$("#Pref_value1 option").remove();
		$("#Pref_value2").val();
	};

	var getPrefer = function() {
		return {		
			catalog : $("#Pref_catalog").val(),
			type : $("#Pref_type").val(),
			value1 : $("#Pref_value1").val(),
			value2 : $("#Pref_value2").val(),
			remark : $("#Pref_remark").val()
		};
	};
</script>

<div id="pr-form" title="Form - Preference" style="display:none">
<div id="trav8"><div id="comp8">
	<div id="product-form_message">
		<input type="hidden" id="ex-id" name="prefer.id">

		<table>
			<tr>
				<td>Catalog:</td>
			</tr>
			<tr>
				<td>
					<select id='Pref_catalog' class="validator">
						<option value="">Please select... </option>
						<option value="Car">Car</option>
						<option value="Air">Air</option>
						<option value="Hotel">Hotel</option>
						<option value="MEAL">Meal</option>
						<option value="Seat">Seat</option>
						
					</select>
				
			</tr>
			<tr>
				<td>Type:</td>
			</tr>
			<tr>
				<td>
					<select id="Pref_type" class="validator">
						<option>Prefer</option>
						<option>Avoid</option>
					</select>
				</td>
			</tr>


			<tr>
				<td>Value1:</td>
			</tr>
			<tr>
				<td>
					<select id="Pref_value1" class="validator"></select>
				</td>
			</tr>
			<tr>
				<td>Value2</td>
			</tr>
			<tr>
				<td>
					<input type="text" id="Pref_value2" class="validator" />
				</td>
			</tr>
			<tr>
				<td>Remark</td>
			</tr>
			<tr>
				<td><textarea id="Pref_remark" cols="50" rows="6" class="validator"></textarea></td>
			</tr>

		</table>
	</div>
	</div>
	</div>
</div>




