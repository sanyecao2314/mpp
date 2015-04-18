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


	var init_product_dialog_flag_status_remark = false;
	var OK_FUNC_status_remark = function(){
		$("#statusshower").show(500);
		services.call(UPDATE_STATUS_REMARK, [ objid, $("#status-remark").val(),$(":checked").val(),$("#status-travelerList").val()], afUpdate);
	};
	
	function afUpdate(obj) {
		if (!obj.success) {
			alert("some errors occur while processing request.");
			return;
		}
		
		var $btn = $("#"+obj.data.id + " td img[class='act']");
		if(obj.data.status == "ACTIVE") {
			$btn.attr("src","img/Blue Ball.png");
		} else {
			$btn.attr("src","img/Grey Ball.png");
		}
		
		$("#statusshower").hide(500);
	}
	
	var CANCEL_FUNC_status_remark = function(){
		
	};
	
	$(function() {
		if(!init_product_dialog_flag_status_remark){
			$("#status-form").dialog({
				resizable : true,
				width : 470,
				height : 500,
				modal : true,
				buttons : {
					"OK" : function(){
						OK_FUNC_status_remark();
						RESET_status_remark();
					},
					Cancel : function() {
						CANCEL_FUNC_status_remark();
						RESET_status_remark();
					}
				}
			});
			init_product_dialog_flag_status_remark = true;
			$("#status-form").dialog("close");
		}
	});

	function RESET_status_remark(){
		$("#status-remark").val("");
		$("#status-form").dialog("close");
	}

</script>
<div id="status-form" title="Form - Status Change Remark" style="display:none">
		<div id="product-form_message"> 
			<input type="hidden" id="status-id" name="status.id">
			<table>
				<tr>
					<td>Traveler List</td> 
				</tr>
				
				<tr>
					<td>
						<textarea id="status-travelerList" cols="60" rows="7"></textarea>
					</td>
				</tr>
				<tr>
					<td>Status Change Remark</td> 
				</tr>
				<tr>
					<td>
						<textarea id="status-remark" cols="60" rows="7"></textarea>
					</td>
				</tr>
				<tr>
					<td>Status</td> 
				</tr>
				<tr>
					<td>
						<input type="radio" name="statusgroup" id="statusgroup-ACTIVE" value="ACTIVE" />ACTIVE 
						<input type="radio" name="statusgroup" id="statusgroup-INACTIVE" value="INACTIVE"/>INACTIVE 
						<input type="radio" name="statusgroup" id="statusgroup-BLACKLIST" value="BLACKLIST" />BLACKLIST 
						<input type="radio" name="statusgroup" id="statusgroup-SUSPEND" value="SUSPEND" />SUSPEND 
					</td>
				</tr>
				
			</table>
		</div>
</div>




