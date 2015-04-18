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


	var init_product_dialog_flag3 = false;
	var OK_FUNC3 = function(){
		
		$("#statusshower").show();
		var ebill = getEBill();
		//call_ebill_service_create(ebill);
		validate2("create",ebill);
	};
	
	var call_ebill_service_create= function(ebill){
		services.call(EBILL_CREATE, [ebill.fieldName, ebill.defaultValue,
		                      			ebill.mandatory, compId], afCreateEBill);
	};
		
	var call_ebill_service_update= function(ebill){
		services.call(EBILL_UPDATE, [ebill.id,
                                     $("#fieldName"+ebill.id).val(),
                                     $("#defaultValue"+ebill.id).val(),
                                     $("#mandatory" + ebill.id+":checked").length
                                     ], afUpdate);
	};
	
	var initEBill = function(){
		$("#statusshower").show();
		services.call(EBILL_LIST, [compId], afListEBill);
	};
	
	var CANCEL_FUNC3 = function(){
		$("#ebill-form").dialog("close");
		$("#statusshower").hide();
	};
	
	var afCreateEBill = function(obj) {
		if(!obj.success) {
			alert("some errors occur while processing request, ebill");
			return;
		}
		addEBill(obj.data);
		$("#statusshower").hide();
		if($("#company_ebilling").length==1){
			$("#company_ebilling")[0].checked=true;
		}
	};
	
	$(function() {
		if(!init_product_dialog_flag3){
			$("#ebill-form").dialog({
				resizable : true,
				width : 350,
				height : 280,
				modal : true,
				buttons : {
					"OK" : function(){
						OK_FUNC3();
						RESET_EB();
					},
					Cancel : function() {
						CANCEL_FUNC3();
						RESET_EB();
					}
				}
			});
			init_product_dialog_flag3 = true;
			$("#ebill-form").dialog("close");
		}
	});
	var RESET_EB=function(){
		
		$("#eb-fieldName").val("");
		$("#eb-default").val("");
		$("#eb-mandatory")[0].checked=false;
	};
	
	var getEBill = function(){
		return {
				fieldName : $("#eb-fieldName").val(),
				defaultValue : $("#eb-default").val(),
				mandatory : $("#eb-mandatory:checked").length
			};
	};
	function afDeleteEBill(obj){
		if(!obj.success) {
			alert("some errors occur while processing request, ebill");
			return;
		}
		$("#ec_"+obj.data.id).remove();
	}
	
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
			services.call(EBILL_DEL, [ebill.id], afDeleteEBill);
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
			//call_ebill_service_update(ebill);
			validate2("update",ebill);
		});
		$div.append($btn);
		$div.addClass("c_div");
	    $div.hide();
	    
				
		$("#ebill-form").dialog("close");		
	};
	var afListEBill = function(obj){
		if(!obj.success) {
			alert("some errors occur while processing request, ebill");
			return;
		}
		var list = obj.data;
		for(var i=0;i<list.length;i++){
			addEBill( list[i] );
		}
		$("#statusshower").hide();
	};
</script>
<div id="ebill-form" title="Form - E-Billing" style="display:none">
	<div id='trav4'><div id="comp5">
		<div id="product-form_message"> 
			<input type="hidden" id="ebill-id" name="ebill.id">
			<table>
				<tr>
					<td>Field Name</td> 
				</tr>
				<tr>
					<td>
						<input type="text" id="eb-fieldName" class="validator" />
					</td>
				</tr>
				<tr>
					<td>Default Value</td>
				</tr>
				<tr>
					<td>
						<input type="text" id="eb-default"  class="validator"/>
					</td>
				</tr>
				<tr>
					<td><input type="checkbox" value="1" name="eb-mandatory" class="validator" id="eb-mandatory" /> Mandatory</td>
				</tr>
				
			</table>
		
		</div>
	</div></div>
</div>




