
<script src="js/jquery.bgiframe-2.1.2.js"></script>
<script src="js/jquery.ui.core.js"></script>
<script src="js/jquery.ui.widget.js"></script>
<script src="js/jquery.ui.mouse.js"></script>
<script src="js/jquery.ui.button.js"></script>
<script src="js/jquery.ui.draggable.js"></script>
<script src="js/jquery.ui.position.js"></script>
<script src="js/jquery.ui.dialog.js"></script>

<script type="text/javascript" >
	//par load panel: plp
	var init_product_dialog_flag_plp = false;

	$(function() {
		$("#parload-form").hide();
		
		if(!init_product_dialog_flag_plp){
			$("#parload-form").dialog({
				resizable : true,
				width : 400,
				height : 200,
				modal : true,
				buttons : {
					"OK" : function(){
						OK_FUNC_PLP();
					},
					Cancel : function() {
						CANCEL_FUNC_PLP();
					}
				}
			});
			init_product_dialog_flag_plp = true;
			$("#parload-form").dialog("close");
		}
		
	});
	
	var OK_FUNC_PLP = function() {
		if($("#parload-groupname").val()==''){
			$("#parload-message").html("<h4 style='color:red'>Group name could not be empty.</h4>");
			return ;
		}
		
		if($("#parload-no").val().length!=$("#parload-no").attr("maxlength")){
			$("#parload-message").html("<h4 style='color:red'>Please input correct customer no.</h4>");
			return ;
		}
		
		$("#statusshower").show();
		if(LOAD_ACTION=='company.loadBar'){
			services.call("common.addTask", [], afAddTask);
		}else{
			services.call(LOAD_ACTION, [$("#parload-groupname").val(),$("#parload-no").val()], afLoad);
		}

		$("#parload-form").dialog("close");
		
	};
	
	var CANCEL_FUNC_PLP = function() {
		$("#parload-form").dialog("close");
		RESET_PLP();
	};
	
	var RESET_PLP = function() {
		$("#parload-message").html("");
		$("#parload-groupname").val("");
		$("#parload-no").val("");
	};
	
	function afLoad(obj) {
		if(obj!='success'){
			alert(obj);	
		}
		if(LOAD_ACTION=='company.loadBar'){
			$(document).stopTime();
			$(document).everyTime(10000, function(i) {
				var btns = $(".btn_sync");
				var companyNos="";
				for(i=0; i<btns.length; i++) {
					var companyNo = btns.eq(i).attr("id").substring(4);
					companyNos+=companyNo;
					if( i < btns.length - 1 ) {
						companyNos += ",";	
					}
				}
				services.call("traveler.getCompanySyncStatus", [ companyNos ], afCompSync);
				
			}, 10000);
		}
		RESET_PLP();
		
		$("#statusshower").html("Loading...");
		$("#statusshower").hide(1000);
		window.location.reload();
	}
	
	function afAddTask(obj){
		$(document).stopTime();
		services.call(LOAD_ACTION, [obj, $("#parload-groupname").val(), $("#parload-no").val()], afLoad);
		$(document).everyTime(1000, function(i) {
			services.call("common.getTaskStatus", [obj], afGetStatus);
			}, 10000);
	}
	
	function afGetStatus(obj){
		$("#statusshower").html("Loading..."+obj+"%");
	}
</script>

<div id="parload-form" title="Please input customer no in PowerSuite" style="display:none">
		<div id="parload-form_message"> 
			
			<input type="hidden" name="parload.id">
			<table cellpadding="5" cellspacing="0">
				
				<tr>
					<td colspan="2"><div id="parload-message"></div></td>					
				</tr>
				<tr>
					<td width="120">Group Name:</td>
					<td><input type="text" id="parload-groupname"/></td>
				</tr>
				<tr>
					<td width="120">Customer No:</td>
					<td><input type="text" id="parload-no" maxlength="10"/></td>
				</tr>

			</table>
			 
		</div>
</div>




