
<script src="js/jquery.bgiframe-2.1.2.js"></script>
<script src="js/jquery.ui.core.js"></script>
<script src="js/jquery.ui.widget.js"></script>
<script src="js/jquery.ui.mouse.js"></script>
<script src="js/jquery.ui.button.js"></script>
<script src="js/jquery.ui.draggable.js"></script>
<script src="js/jquery.ui.position.js"></script>
<script src="js/jquery.ui.dialog.js"></script>

<script type="text/javascript" >
	
	var init_product_dialog_flag_sync = false;

	$(function() {
		$("#syncInfo").hide();
		
		if(!init_product_dialog_flag_sync){
			$("#syncInfo").dialog({
				resizable : true,
				width : 550,
				height : 300,
				modal : true,
				buttons : {
					"OK" : function(){
						OK_FUNC_sync();
					},
					Cancel : function() {
						CANCEL_FUNC_sync();
						
					}
				}
			});
			init_product_dialog_flag_sync = true;
			$("#syncInfo").dialog("close");
		}
		
	});
	
	var OK_FUNC_sync = function() {
		$("#syncInfo").dialog("close");
		RESET_sync();
	};
	
	var CANCEL_FUNC_sync = function(){
		$("#syncInfo").dialog("close");
		RESET_sync();
	};
	
	var RESET_sync = function(){
		$("#syncInfo_remark").val("");
	};
	
</script>

<div id="syncInfo" title="Form - Synchronizing Information" style="display:none">
		<div id="syncInfo-form_message"> 
			<table cellpadding="5" cellspacing="0">
				<tr>
					<td colspan="2">
					<textarea id="syncInfo_remark" rows="8" cols="70"></textarea>
					</td>					
				</tr>
			</table>
			 
		</div>
</div>




