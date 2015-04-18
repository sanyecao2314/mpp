
<script src="js/jquery.bgiframe-2.1.2.js"></script>
<script src="js/jquery.ui.core.js"></script>
<script src="js/jquery.ui.widget.js"></script>
<script src="js/jquery.ui.mouse.js"></script>
<script src="js/jquery.ui.button.js"></script>
<script src="js/jquery.ui.draggable.js"></script>
<script src="js/jquery.ui.position.js"></script>
<script src="js/jquery.ui.dialog.js"></script>


<script type="text/javascript" >
	function FUNC_OK(){
		var selectedlist = $("#clause-select-form tr.checked");
		for(var i=0;i<selectedlist.length;i++){
			$li=$("<li/>");
			$("#policy_list").append($li);

			$li.attr("id",$("#"+selectedlist.eq(i).attr("id")+" td").eq(0).html());
			$li.append($("#"+selectedlist.eq(i).attr("id")+" td").eq(0).html());
			$li.append("<br/>");
			$li.append($("#"+selectedlist.eq(i).attr("id")+" td").eq(1).html());
			
			$hidden=$("<input type='hidden' name='customer.clauses'/>");
			$("#policy_list").append($hidden);
			$hidden.val($("#"+selectedlist.eq(i).attr("id")+" td").eq(0).html()); 			
		}
	}
	
	$(function() {
		$( "#clause-select-form" ).dialog({
			resizable: false,
			width: 800,
			height: 600,
			modal: true,
			buttons: {
				"OK": function() {
					FUNC_OK();
					$( this ).dialog( "close" );
				},
				Cancel: function() {
					$( this ).dialog( "close" );
				}
			}
		});
		$("#clause-select-form").dialog( "close" );
	});

</script>

<div id="clause-select-form" title="Form - Please Select Clauses" style="display:none">
		<div id="clause-select-form-message">
		
			<table id="clause-table" cellpadding="0" cellspacing="0">
				
			</table>

		</div>
</div>




