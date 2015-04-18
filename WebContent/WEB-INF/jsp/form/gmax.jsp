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

	var update_flag = false;
	var init_product_dialog_flag_gmax= false;
	var OK_FUNC_GX = function() {
		$("#statusshower").show();
		var obj = getGmax();

		services.call("customer.updateGmaxNo", [obj.id, obj.tmrid, obj.gmaxNo, obj.clientName, obj.parentName,
		                                		obj.pricingMode, obj.remark], afUpdateGmax);
		$("#gmax-form").dialog("close");
		$("#statusshower").hide();
	};
	
	
	var afUpdateGmax = function(obj) {
		if(!obj.success) {
			alert("some errors occur while processing request, gmax");
			return;
		}
		$("#statusshower").hide();
		
	};

	var CANCEL_FUNC_GX = function() {
		$("#gmax-form").dialog("close");
	};

	$(function() {

		if (!init_product_dialog_flag_gmax) {
			$("#gmax-form").dialog({
				resizable : true,
				width : 330,
				height : 420,
				modal : true,
				buttons : {
					"OK" : function() {
						OK_FUNC_GX();
						RESET_GX();
					},
					Cancel : function() {
						CANCEL_FUNC_GX();
						RESET_GX();
					}
				}
			});
			init_product_dialog_flag_gmax = true;
			$("#gmax-form").dialog("close");
		}
		
	});

	var RESET_GX = function() {
		$("#gmax-id").val("");
		$("#gmax-tmrid").val("");
		$("#gmax-parentName").val("");
		$("#gmax-no").val("");
		$("#gmax-clientName").val("");
		$("#gmax-pricingMode").val("");
		$("#gmax-remark").val("");
	};

	var getGmax = function() {
		return {
			id: $("#gmax-id").val(),
			tmrid : $("#gmax-tmrid").val(),
			parentName : $("#gmax-parentName").val(),
			gmaxNo : $("#gmax-no").val(),
			clientName : $("#gmax-clientName").val(),
			pricingMode : $("#gmax-pricingMode").val(),
			remark : $("#gmax-remark").val()
		};
	};
	
	var initGmax = function(){
		services.call("customer.findGmaxNo", [$("#gmax-no").val()], afInitGmax);
	};
	
	function afInitGmax(obj){
		if(!obj.success) {
			alert("some errors occur while processing request, gmax");
			return;
		}
		
		$("#gmax-id").val(obj.data.id);
		$("#gmax-tmrid").val(obj.data.tmrId);
		$("#gmax-parentName").val(obj.data.parentName);
		$("#gmax-no").val(obj.data.gmaxNo);
		$("#gmax-clientName").val(obj.data.clientName);
		$("#gmax-pricingMode").val(obj.data.pricingMode);
		$("#gmax-remark").val(obj.data.remark);
		
	}
</script>

<div id="gmax-form" title="Form - Gmax No" style="display:none">
	<div id="product-form_message">
		<input type="hidden" id="gx-id" name="gmax.id">
		<input type="hidden" id="gmax-id"/>
		<table>
			<tr>
				<td>TMR ID:</td>
			</tr>
			<tr>
				<td>
					<input type="text" id="gmax-tmrid"/>
				</td>
			</tr>
			<tr>
				<td>Parent Name:</td>
			</tr>
			<tr>
				<td>
					<input type="text" id="gmax-parentName"/>
				</td>
			</tr>

			<tr>
				<td>Gmax No:</td>
			</tr>
			<tr>
				<td>
					<input type="text" id="gmax-no" readonly="readonly" style="background-color:#eee" />
				</td>
			</tr>
			<tr>
				<td>Client Name</td>
			</tr>
			<tr>
				<td>
					<input type="text" id="gmax-clientName"/>
				</td>
			</tr>
			<tr>
				<td>Pricing Mode</td>
			</tr>
			<tr>
				<td>
					<select id="gmax-pricingMode">
						<option>Mode</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>Remark</td>
			</tr>
			<tr>
				<td><input type="text" id="gmax-remark" /></td>
			</tr>
		</table>
	</div>
</div>




