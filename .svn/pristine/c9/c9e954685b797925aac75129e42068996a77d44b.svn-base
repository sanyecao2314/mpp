
<script src="js/jquery.bgiframe-2.1.2.js"></script>
<script src="js/jquery.ui.core.js"></script>
<script src="js/jquery.ui.widget.js"></script>
<script src="js/jquery.ui.mouse.js"></script>
<script src="js/jquery.ui.button.js"></script>
<script src="js/jquery.ui.draggable.js"></script>
<script src="js/jquery.ui.position.js"></script>
<script src="js/jquery.ui.dialog.js"></script>

<script type="text/javascript" >
	
	var init_dialog_flag_CHANGEBAR = false;

	$(function() {
		$("#changeBar-form").hide();
		
		if(!init_dialog_flag_CHANGEBAR){
			$("#changeBar-form").dialog({
				resizable : true,
				width : 340,
				height : 370,
				modal : true,
				buttons : {
					"OK" : function(){
						OK_FUNC_CHANGEBAR();
					},
					Cancel : function() {
						CANCEL_FUNC_CHANGEBAR();
						
					}
				}
			});
			init_dialog_flag_CHANGEBAR = true;
			$("#changeBar-form").dialog("close");
		}
		$("#changeBar-from").css("width","80px");
		$("#changeBar-to").css("width","80px");
		$("#changeBar-from").css("backgroundColor","#ccc");
		$("#changeBar-from").attr("readonly", true);
		
		$("#changeBar-status").click(function(){
			if((this).checked) {
				$("#changeBar-status-remark").css("backgroundColor","#fff");
				$("#changeBar-status-remark").attr("readonly", false);
			} else {
				$("#changeBar-status-remark").css("backgroundColor","#ccc");
				$("#changeBar-status-remark").attr("readonly", true);
				$("#changeBar-status-remark").val("");
			}
		});
		
	});
	
	var OK_FUNC_CHANGEBAR = function() {
		$("#statusshower").show();
		$("#updateComp").attr("disabled",true);
		
		var inactive = $("#changeBar-status")[0].checked;
		services.call("traveler.changeBar", [$("#changeBar-travelerNo").val(), "", $("#changeBar-to").val(), inactive?1:0 , $("#changeBar-status-remark").val()], afchange);
		$("#changeBar-form").dialog("close");
		RESET_CHANGEBAR();
	};
	
	var CANCEL_FUNC_CHANGEBAR = function(){
		$("#changeBar-form").dialog("close");
		RESET_CHANGEBAR();
	};
	
	var RESET_CHANGEBAR = function(){
		$("#changeBar-from").val("");
		$("#changeBar-to").val("");
		$("#changeBar-travelerNo").val("");
		$("#changeBar-status").attr("checked", true);
	};
	
	function afchange(obj){
		$("#updateComp").attr("disabled",false);
		$("#statusshower").hide();

		if(obj=='err-changebar-001'){
			alert("Bar does not exist.");
		}else if(obj=='err-changebar-002'){
			alert("Traveler does not exist.");
		}else{
			var isOk = confirm("Traveler has been assigned to new Bar, if you want to redirect to the new traveler info page, click 'OK'.");
			if(isOk){
				location.href = 'displayTraveler.action?id=' + obj;
			}
		}	
	}
	
</script>

	<div id="changeBar-form" title="Form - Change Bar From" style="display:none">
		<div id="changeBar-form_message"> 
			<input type="hidden" name="changeBar.id">
			
			<table cellpadding="5" cellspacing="0">
				<tr>
					<td colspan="2"><div id="changeBar-message"></div></td>					
				</tr>
				<tr>
					<td width="60">From:</td>
					<td>
						<input type="text" id="changeBar-from" maxlength="6" />
						<input type="hidden" id="changeBar-travelerNo"/>
					</td>
				</tr>
				<tr>
					<td>To:</td>
					<td><input type="text" id="changeBar-to" maxlength="6" /></td>
				</tr>
			</table>
			
			 <div style="font-size: 12px">
			 	<input type="checkbox" id="changeBar-status" checked="checked" />&nbsp;Inactive current traveler.
			 </div>
			 
			 <div style="font-size: 12px">
			 	<textarea id="changeBar-status-remark" cols="60" rows="7"></textarea>
			 </div>
			 
		</div>
	</div>




