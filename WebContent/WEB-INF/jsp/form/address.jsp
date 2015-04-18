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
	var init_product_dialog_flag = false;
	
	var OK_FUNC = function() {
		$("#statusshower").show();
		var obj = getAddress();
		validate2("create",obj);
	};
	
	var call_address_service_create= function(obj){
		services.call(ADDR_CREATE, [obj.type, obj.address, obj.city, obj.zipCode, compId], afCreateAddr);
	};
	
	var call_address_service_update= function(id){
		services.call(ADDR_UPDATE, [id, $("#_txt_"+id).val(), $("#_city_"+id).val(), $("#_zipCode_"+id).val()], afUpdate);
	};
	
	var CANCEL_FUNC = function(){
		$("#address-form").dialog("close");
		$("#statusshower").hide();
	};
	var afListAddr = function(obj){
		if(!obj.success) {
			alert("some errors occur while processing request, address");
			return;
		}
		var list = obj.data;
		for(var i=0;i<list.length;i++){
			addAddr(list[i].id, list[i].catalog, list[i].address,list[i].city,list[i].zipCode);
		}
		var $radios = $(":radio[name='officeaddr']");
		for(var i=0;i<$radios.length;i++){
			if($radios.eq(i).attr("value")==ADDR_SEL)
				$radios.eq(i)[0].checked=true;
		}
		
		$("#statusshower").hide();
	};
	$(function() {
		if(!init_product_dialog_flag){
			$("#address-form").dialog({
				resizable : true,
				width : 600,
				height : 550,
				modal : true,
				buttons : {
					"OK" : function(){
						OK_FUNC();
						RESET_ADDR();
					},
					Cancel : function() {
						CANCEL_FUNC();
						RESET_ADDR();
					}
				}
			});
			init_product_dialog_flag = true;
			$("#address-form").dialog("close");
		}
	});
	var RESET_ADDR=function(){
		$("#addressType").val("");
		$("#address_content").val("");
		$("#address_zipCode").val("");
		$("#address_city").val("");
	};
	
	var getAddress = function(){
		return {
			type: $("#addressType").val(),
			address: $("#address_content").val(),
			zipCode: $("#address_zipCode").val(),
			city: $("#address_city").val()
		};
	};
	
	var afCreateAddr = function(obj){
		if(!obj.success) {
			alert("some errors occur while processing request, address");
			return;
		}
		addAddr(obj.data.id, obj.data.catalog, obj.data.address, obj.data.city, obj.data.zipCode);
		$("#statusshower").hide();
	};
	
	var initAddress = function(){
		$("#statusshower").show();
		services.call(ADDR_LIST, [compId], afListAddr);
	};

	var addAddr = function(id, catalog, address, city, zipCode){
		
		var $cdiv=$("<div/>");
		$cdiv.attr("id","ac_"+id);
		$("#addressPool").append($cdiv);
		
		catalogtmp=catalog;
		if(catalogtmp == null || catalogtmp == '') {
			catalogtmp='M';
		}
		var $radio = $("<input/>", {
			type : 'radio',
			name : 'officeaddr',
			value: catalogtmp.substring(0, 1)
		});
		$radio.click(function(){
			$("#officeaddress").val($(this).val());
		});
		$cdiv.append($radio);
		
		var $label = $("<div/>");
		$label.addClass("addr");
		$label.attr("id", id);

		$cdiv.append($label);
		
		
		$label.html(catalog);
		
		$label.append($("<br/>"));
		$label.append(address.substring(0,50));
		$label.append(" ... ...");
		
		$label.toggle(
				function() {
					$("#ai_"+id).show(1000);
				},
				function() {
					$("#ai_"+id).hide(1000);
				}
			);

		var $div=$("<div/>");
		$div.attr("id","ai_"+id);
		$cdiv.append($div);

		var $table=$("<table/>");
		$div.append($table);
		
		$input = $("<input/>", {type:"text",id:"_city_"+id});
		$input.val(city);
		
		$table.append(insertTableRow([$("<label/>",{html:"City:"})],[$input]));
		
		$input = $("<input/>", {type:"text",id:"_zipCode_"+id});
		$input.val(zipCode);
		
		$table.append(insertTableRow([$("<label/>",{html:"zipCode:"})],[$input]));
		
		var $textarea=$("<textarea/>",{cols:70, rows:8, id:"_txt_"+id} );
		$textarea.val(address);	
		$textarea.addClass("validator_sub");
		$table.append(insertTableRow([$("<label/>",{html:"Address:"})],[$textarea]));
		
		var $btn=$("<input/>",{type:"button", value:"Save"});
		$btn.addClass("btn");
		$btn.click(function() {
			$("#statusshower").show(1000);
			validate3("update", id);
		});
		
		$div.append($btn);
		
		$btn=$("<input/>",{type:"button",value:"delete"});
		$btn.addClass("btn");
		$btn.click(function(){
			services.call(ADDR_DELETE, [id], afDeleteAddr);
		});
		$div.append($btn);
		
		$div.hide();
		
		$("#address-form").dialog("close");		
	};

	function afDeleteAddr(obj) {
		if(!obj.success) {
			alert("some errors occur while processing request, address");
			return;
		}
		$("#ac_"+obj.data.id).remove();
	}
		
</script>
	<div id="address-form" title="Form - Address" style="display:none">
		<div id="trav1">
			<div id="comp1">
			<div id="product-form_message"> 
				<input type="hidden" id="address-id" name="address.id">
				<table>
					<tr>
						<td>Catalog</td>
					</tr>
					<tr>
						<td>
							<select id="addressType">
								<option value="Mailing">Mailing</option>
								<option value="Delivery">Delivery</option>
								<option value="Billing">Billing</option>
								<option id="homeAddress" value="Home">Home</option>
								<option id="statementAddress" value="Statement">Statement</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>City</td>
					</tr>
					<tr>
						<td>
							<input type="text" id="address_city"  class="validator"/>
						</td>
					</tr>
					<tr>
						<td>ZipCode</td>
					</tr>
					<tr>
						<td>
							<input type="text" id="address_zipCode"  class="validator"/>
						</td>
					</tr>
					<tr>
						<td>Address</td>
					</tr>
					<tr>
						<td>
							<textarea cols="80" rows="7" id="address_content" class="validator"></textarea>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</div>




