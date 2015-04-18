
<script src="js/jquery.bgiframe-2.1.2.js"></script>
<script src="js/jquery.ui.core.js"></script>
<script src="js/jquery.ui.widget.js"></script>
<script src="js/jquery.ui.mouse.js"></script>
<script src="js/jquery.ui.button.js"></script>
<script src="js/jquery.ui.draggable.js"></script>
<script src="js/jquery.ui.position.js"></script>
<script src="js/jquery.ui.dialog.js"></script>

<script type="text/javascript" >
	
	var init_product_dialog_flag_CF = false;

	$(function() {
		$("#copyfrom-form").hide();
		
		if(!init_product_dialog_flag_CF){
			$("#copyfrom-form").dialog({
				resizable : true,
				width : 470,
				height : 150,
				modal : true,
				buttons : {
					"OK" : function(){
						OK_FUNC_CF();
					},
					Cancel : function() {
						CANCEL_FUNC_CF();
						
					}
				}
			});
			init_product_dialog_flag_CF = true;
			$("#copyfrom-form").dialog("close");
		}
		
	});
	
	var OK_FUNC_CF = function() {
		$("#statusshower").show();
		services.call(COPY_ACTION, [$("#copyfrom-no").val(), destId], afCopy);
		$("#copyfrom-form").dialog("close");
		RESET_CF();
	};
	
	var CANCEL_FUNC_CF = function(){
		$("#copyfrom-form").dialog("close");
		RESET_CF();
	};
	
	var RESET_CF = function(){
		$("#copyfrom-no").val("");
	};
	
	function afCopy(obj){
		if(!obj.success) {
			alert("can't copy from that entity, please check if it is valid.");
			return;
		}else{
			isOk = confirm("Completed successfully! Refresh this page now?");
			if(isOk){
				location.reload();
			}
		}
		$("#statusshower").hide(1000);
	}
	
</script>

<div id="copyfrom-form" title="Form - Copy From" style="display:none">
		<div id="copyfrom-form_message"> 
			
			<input type="hidden" name="copyfrom.id">
			<table cellpadding="5" cellspacing="0">
				
				<tr>
					<td colspan="2"><div id="copyfrom-message"></div></td>					
				</tr>
				
				<tr>
					<td width="120">Copy From:</td>
					<td><input type="text" id="copyfrom-no" /></td>
				</tr>

			</table>
			 
		</div>
</div>




