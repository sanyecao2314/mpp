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
	var init_product_dialog_flag2 = false;
	var OK_FUNC2 = function() {
		$("#statusshower").show();
		var disc = getDiscount();
		validate2("create",disc);
	};

	var call_disc_service_create= function(disc){
		services.call(DISCOUNT_CREATE, [disc.variable, disc.product, disc.remark,
	                         			disc.rate, compId], afCreateDiscount);
	};
	
	var call_disc_service_update= function(discount){
		services.call(DISCOUNT_UPDATE, [discount.id,
                                		$("#_variable_"+discount.id).val(),
                                        $("#_product_"+discount.id).val(),
                                        $("#_remark_"+discount.id).val(),
                                        $("#_rate_" + discount.id).val()
                                        ], afUpdate);
	};
	var CANCEL_FUNC2 = function(){
		$("#discount-form").dialog("close");
	};
	
	$(function() {
		if(!init_product_dialog_flag2){
			$("#discount-form").dialog({
				resizable : true,
				width : 600,
				height : 550,
				modal : true,
				buttons : {
					"OK" : function(){
						OK_FUNC2();
						RESET_DISC();
					},
					Cancel : function() {
						CANCEL_FUNC2();
						RESET_DISC();
					}
				}
			});
			init_product_dialog_flag2 = true;
			$("#discount-form").dialog("close");
		}
	});
	var RESET_DISC=function(){
		$("#product").val("");
		$("#disvariable").val("");
		$("#discountRate").val("");
		$("#discRemark").val("");
	};
	
	var getDiscount = function(){
		return {
				variable : $("#disvariable").val(),
				product : $("#product").val(),
				rate : $("#discountRate").val(),
				remark : $("#discRemark").val()
			};
	};
	
	var $productselect=$("<select/>",{id:"product"});
	<c:forEach var="product" items="${productlist }">
		var $option=$("<option/>");
		$option.attr("value","${product.productNo }");
		$option.html("${product.productType }-${product.productName }");
		$productselect.append($option);
	</c:forEach>
	
	var afCreateDiscount = function(obj) {
		if(!obj.success) {
			alert("some errors occur while processing request, discount");
			return;
		}
		addDiscount(obj.data);
		$("#statusshower").hide();
	};
	
	var initDiscount = function(){
		$("#statusshower").show();
		services.call(DISCOUNT_LIST, [compId], afListDiscount);
	};
	
	var afListDiscount = function(obj){
		if(!obj.success) {
			alert("some errors occur while processing request, discount");
			return;
		}
		var list = obj.data;
		for(var i=0;i<list.length;i++){
			addDiscount( list[i] );
		}
		$("#statusshower").hide();
	};
	
	var addDiscount = function(discount){
		
		var $cdiv=$("<div/>");
		$cdiv.attr("id","dc_" + discount.id);
		$("#discountPool").append($cdiv);
				
		var $label = $("<div/>");
		$label.addClass("discount");
		$label.attr("id", discount.id);
		$cdiv.append($label);
		$label.html("Discount " + $(".discount").length);
		var $btn=$("<input/>",{type:"button",value:"delete"});
		$cdiv.append($btn);
		$btn.click(function(){
			services.call(DISCOUNT_DEL, [discount.id], afDeleteDiscount);
		});
		$label.toggle(
				
				function() {
					$("#di_"+discount.id).show(1000);
				},
				function() {
					$("#di_"+discount.id).hide(1000);
				}

		);

		var $div=$("<div/>");
		$div.attr("id","di_"+discount.id);
		$cdiv.append($div);
		
		var $table=$("<table/>");
		
		$table.append(createRow("Variable",{type:"text",id:"_variable_" + discount.id, value:discount.varname}));
		
		var $tr=$("<tr/>");
		$table.append($tr);
		var $td=$("<td/>");
		$td.addClass("contactlabel");
		$tr.append($td);
		var $label=$("<label/>");
		$label.html("Product");
		$td.append($label);
		$td=$("<td/>");
		$tr.append($td);
		var $text=$("<select id='_product_" + discount.id+"'/>");
		
		<c:forEach var="product" items="${productlist }">
			var $option=$("<option/>");
			$option.attr("value","${product.productNo }");
			$option.html("${product.productType }-${product.productName }");
			$text.append($option);
		</c:forEach>
		$td.append($text);
		$table.append(createRow("Rate(%)",{type:"text",id:"_rate_" + discount.id,value:discount.discountRate}));
		$table.append(createRow2("Remark",{id:"_remark_" + discount.id,html:discount.remark,cols:70,rows:8}));
				
		$div.append($table);
		
		$("#_rate_" + discount.id).addClass("validator_sub");
		$("#_variable_" + discount.id).addClass("validator_sub");
		$("#_remark_" + discount.id).addClass("validator_sub");
		$("#_product_" + discount.id).addClass("validator_sub");
		
		var $options=$("#product"+discount.id+" option");
		for(var i=0;i<$options.length;i++){
			
			if(discount.prodId == $options.eq(i).val()){
				$options.eq(i)[0].selected = true;
			} else {
				$options.eq(i)[0].selected = false;
			}
		}
		
		var $btn=$("<input/>",{type:"button",value:"Save"});
		$btn.addClass("btn");
		$btn.click(function(){
			$("#statusshower").show(1000);
			//call_disc_service_update(discount);
			validate3("update",discount);
		});
		$div.append($btn);
		$div.addClass("c_div");
	    $div.hide();

		$("#discount-form").dialog("close");		
	};
	
	function afDeleteDiscount(obj){
		if(!obj.success) {
			alert("some errors occur while processing request, discount");
			return;
		}
		$("#dc_"+obj.data.id).remove();
	}
	
	$(function(){
		$("#product").html($productselect.html());	
	});
	
	function afUpdate(obj){
		if(!obj.success) {
			alert("some errors occur while processing request, discount");
			return;
		}
		$("#statusshower").hide(1000);
	}
</script>
<div id="discount-form" title="Form - discount" style="display:none">
	<div id="trav"><div id="comp4">
		<div id="product-form_message"> 
			<input type="hidden" id="discount-id" name="discount.id">
			<table>
				<tr>
					<td>Variable</td>
				</tr>
				<tr>
					<td>
						<input type="text" id="disvariable"  class="validator"/>
					</td>
				</tr>
				<tr>
					<td>Product</td>
				</tr>
				<tr>
					<td>
						<select id="product" class="validator">
						</select>
					</td>
				</tr>
				<tr>
					<td>Rate(%)</td>
				</tr>
				<tr>
					<td>
						<input type="text" id="discountRate" class="validator" />
					</td>
				</tr>
				<tr>
					<td>Remark</td>
				</tr>
				<tr>
					<td>
						<textarea cols="80" rows="7" id="discRemark" class="validator"></textarea>
					</td>
				</tr>
				
			</table>
		
		</div>
	</div></div>
</div>




