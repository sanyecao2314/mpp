
<script src="js/jquery.bgiframe-2.1.2.js"></script>
<script src="js/jquery.ui.core.js"></script>
<script src="js/jquery.ui.widget.js"></script>
<script src="js/jquery.ui.mouse.js"></script>
<script src="js/jquery.ui.button.js"></script>
<script src="js/jquery.ui.draggable.js"></script>
<script src="js/jquery.ui.position.js"></script>
<script src="js/jquery.ui.dialog.js"></script>
<script type="text/javascript" >
	var init_product_dialog_flag = false;
	var OK_FUNC = function(){
		
	};
	var CANCEL_FUNC = function(){
		
	};
	
	$(function() {
		if(!init_product_dialog_flag){
			$("#product-form").dialog({
				resizable : true,
				width : 600,
				height : 550,
				modal : true,
				buttons : {
					"OK" : function(){
						OK_FUNC();
					},
					Cancel : function() {
						CANCEL_FUNC();
					}
				}
			});
			init_product_dialog_flag = true;
			$("#product-form").dialog("close");
		}
	});
</script>
<div id="product-form" title="Form - Product" style="display:none">
		<div id="product-form_message"> 
			<input type="hidden" id="product-id" name="product.id">
			<table cellpadding="5" cellspacing="0">
				<tr>
					<td><div id="product-form-message"></div></td>
				</tr>
				<tr>
					<td width="30" class="info_text">Product No:</td>
				</tr>
				<tr>
					<td>
						<input type="text" id="product-no" />
					</td>
				</tr>
				<tr>
					<td class="info_text">Product Name:</td>
				</tr>
				<tr>
					<td>
						<input type="text" id="product-name" size="70" />
					</td>
				</tr>
				<tr>
					<td class="info_text">Vendor Name:</td>
				</tr>
				<tr>
					<td>
						<input type="text" id="vendor-name" size="70" />
					</td>
				</tr>
				<tr>
					<td class="info_text">Description:</td>
				</tr>
				<tr>
					<td>
						<textarea id="product-desc" cols="70" rows="7"></textarea>
					</td>
				</tr>
			</table> </div>
	
</div>




