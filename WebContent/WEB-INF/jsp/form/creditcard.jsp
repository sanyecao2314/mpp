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
    $(document).ready(function(){
    	$("#cardNo").change(function(){
    		var cardbin = $("#cardNo").val();
    		if (cardbin.length < 10) {
    		    return false;
        	}
    		services.call(CCARD_GETTYPE, [cardbin],afGetType);
    	});
    });
    
    function afGetType(obj) {
        if (obj == 'error') {
            return false;
        }
        var l = obj.split(",");
        console.info(obj);
        console.info(l);
        console.info(l.length);
        if(l.length === 2){
        	$("#cardType").prepend("<option value='" + l[0] + "'>"+ l[0] +"</option>");
 	        $("#cardType")[0].options[0].selected = true; 
 	        $("#institution").val(l[1]);
        }else{
	        $("#cardType").prepend("<option value='" + obj + "'>"+ obj +"</option>");
	        $("#cardType")[0].options[0].selected = true;   	
        }
    }
    
	var list = null;
	var init_product_dialog_flag_ccd = false;
	var OK_FUNC_CC = function(){
		$("#statusshower").show();
		var card = getCCardXML();
		//call_contact_service_create(card);
		validate2("create",card);
		
	};
	
	var call_credit_service_create= function(card){
		services.call(CCARD_CREATE, [card], afCreateCreditCard);
	};
	
	var call_credit_service_update= function(ccard){
		var card = getCCardXML2(ccard.id);
		services.call(CCARD_UPDATE, [ccard.id, card], afUpdate);
	};
	
	var CANCEL_FUNC_CC = function(){
		$("#ccard-form").dialog("close");
		$("#statusshower").hide();
	};
	var initCCard = function(){
		$("#statusshower").show();
	};
	var afCreateCreditCard = function(obj) {
		if(!obj.success) {
			alert("some errors occur while processing request, credit card");
			return;
		}
		addCCard(obj.data);
		$("#statusshower").hide();
		
	};

	$(function() {
		
		$("#expiry").blur(dateTransformByMonth);
		
		if(!init_product_dialog_flag_ccd){
			$("#ccard-form").dialog({
				resizable : true,
				width : 480,
				height : 750,
				modal : true,
				buttons : {
					"OK" : function(){
						OK_FUNC_CC();
						RESET_CC();
					},
					Cancel : function() {
						CANCEL_FUNC_CC();
						RESET_CC();
					}
				}
			});
			init_product_dialog_flag_ccd = true;
			$("#ccard-form").dialog("close");
		}
	});
	
	var RESET_CC = function(){
		defaultValue($("#vendor"),"");
		$("#cardNo").val("");
		$("#expiry").val("");
		$("#holderName").val("");
		defaultValue($("#cardType"),"");
		defaultValue($("#merchantNo"),"");
		defaultValue($("#billType"),"");
		defaultValue($("#preferenceCard"),"");
		$("#remark").val("");
	};

	function getCCardXML(){		
		var xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?><java class=\"java.beans.XMLDecoder\">"
				+ "<object class=\"com.citsamex.service.db.CreditCardPo\">"
				+ getXMLProperty("vendor",$("#vendor").val(),"string")
				+ getXMLProperty("institution",$("#institution").val(),"string")
				+ getXMLProperty("cardNo",$("#cardNo").val(),"string") 
				+ getXMLProperty("expiry",$("#expiry").val(),"string") 
				+ getXMLProperty("holderName",$("#holderName").val(),"string") 
				+ getXMLProperty("type",$("#cardType").val(),"string") 
				+ getXMLProperty("merchantNo",$("#merchantNo").val(),"string") 
				+ getXMLProperty("billType",$("#billType").val(),"string")
				+ getXMLProperty("preferenceCard",$("#preferenceCard").val(),"string") 
				+ getXMLProperty("remark",$("#remark").val(),"string") 
				+ getXMLProperty("relationId",compId,"string")
				+ "</object></java>";
		return xml;
	}
	
	function getCCardXML2(suffix){
		
		var xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?><java class=\"java.beans.XMLDecoder\">"
				+ "<object class=\"com.citsamex.service.db.CreditCardPo\">"
				+ getXMLProperty("vendor",$("#_vendor_"+suffix).val(),"string")
				+ getXMLProperty("institution",$("#_institution_"+suffix).val(),"string")
				+ getXMLProperty("cardNo",$("#_cardNo_"+suffix).val(),"string") 
				+ getXMLProperty("expiry",$("#_expiryDate_"+suffix).val(),"string") 
				+ getXMLProperty("holderName",$("#_holder_"+suffix).val(),"string") 
				+ getXMLProperty("type",$("#_cardType_"+suffix).val(),"string") 
				+ getXMLProperty("merchantNo",$("#_merchantNo_"+suffix).val(),"string") 
				+ getXMLProperty("billType",$("#_billType_"+suffix).val(),"string")
				+ getXMLProperty("preferenceCard",$("#_preferenceCard_"+suffix).val(),"string") 
				+ getXMLProperty("remark",$("#_remark_"+suffix).val(),"string") 
				+ getXMLProperty("relationId",compId,"string")
				+ "</object></java>";
		return xml;
	}
	
	 function getCCard(){
		return {
			vendor: $("#vendor").val(),
			institution: $("institution").val(),
			cardno: $("#cardNo").val(),
			expiry: $("#expiry").val(),
			holder: $("#holderName").val(),
			cdtype: $("#cardType").val(),
			merchantNo: $("#merchantNo").val(),
			billType: $("#billType").val(),
			preferenceCard: $("#preferenceCard").val(),
			remark: $("#remark").val()
			};
	}
	
	function getXMLProperty(name, value, type){
		return "<void property=\"" + name + "\"><"+type+">"+value+"</"+type+"></void>";	
	}
	
	var addCCard = function(ccard){
		
		var $cdiv=$("<div/>");
		$cdiv.attr("id","ccc_" + ccard.id);
		$("#ccardPool").append($cdiv);
				
		var $label = $("<div/>");
		$label.addClass("ccard");
		$label.attr("id", ccard.id);
		$cdiv.append($label);
		$label.html(ccard.institution + " " + ccard.cardNo);
		
		$label.toggle(
			function() {
				$("#cci_"+ccard.id).show(1000);
			},
			function() {
				$("#cci_"+ccard.id).hide(1000);
			}
		);

		var $div=$("<div/>");
		$div.attr("id","cci_"+ccard.id);
		$cdiv.append($div);
		
		var $table=$("<table/>");
		
		$select=$("<select/>",{id: "_vendor_" + ccard.id});
		$select.addClass("cardvendor");
		$select.html($("#vendor").html());
		defaultValue($select,ccard.vendor);
		
		$table.append(insertTableRow([$("<label/>",{html:"Vendor:"})],[$select]));
		
		$select=$("<select/>",{id: "_institution_" + ccard.id});
		$select.addClass("cardvendor");
		$select.html($("#institution").html());
		defaultValue($select,ccard.institution);
		$table.append(insertTableRow([$("<label/>",{html:"institution:"})],[$select]));
		
		$table.append(insertTableRow([$("<label/>",{html:"Card No:"})],[$("<input/>",{type:"text",id:"_cardNo_" + ccard.id,value:ccard.cardNo})]));
		$table.append(insertTableRow([$("<label/>",{html:"Expiry Date(YYYY/MM):"})],[$("<input/>",{type:"text",id:"_expiryDate_" + ccard.id, value:ccard.expiryStr})]));
		$table.append(insertTableRow([$("<label/>",{html:"Holder Name:"})],[$("<input/>",{type:"text",id:"_holder_" + ccard.id, value:ccard.holderName})]));
	    
		$table.append(insertTableRow([$("<label/>",{html:"Card Type:"})],[$("<input/>",{type:"text",id:"_cardType_" + ccard.id, value:ccard.type})]));
//		$select=$("<select/>",{id: "_cardType_" + ccard.id});
//		$select.addClass("cardType");
//		$select.html($("#cardType").html());
//		defaultValue($select,ccard.type);
		
//		$table.append(insertTableRow([$("<label/>",{html:"Card Type:"})],[$select]));
		
		$select=$("<select/>",{id: "_merchantNo_" + ccard.id});
		$select.addClass("merchantNo");
		$select.html($("#merchantNo").html());
		defaultValue($select,ccard.merchantNo);
		
		$table.append(insertTableRow([$("<label/>",{html:"Merchant No:"})],[$select]));
		
		$select=$("<select/>",{id: "_billType_" + ccard.id});
		$select.addClass("billType");
		$select.html($("#billType").html());
		defaultValue($select,ccard.billType);
		
		$table.append(insertTableRow([$("<label/>",{html:"Bill Type:"})],[$select]));
		
		$select=$("<select/>",{id: "_preferenceCard_" + ccard.id});
		$select.addClass("preferenceCard");
		$select.html($("#preferenceCard").html());
		defaultValue($select,ccard.preferenceCard);

		$table.append(insertTableRow([$("<label/>",{html:"Preference Card:"})],[$select]));
		
		
		$table.append(insertTableRow([$("<label/>",{html:"Remark:"})],[$("<textarea/>",{id:"_remark_" + ccard.id, cols:50, rows:6, html:ccard.remark})]));
		
		$div.append($table);
		
		$("#_vendor_" + ccard.id).addClass("validator_sub");
		$("#_institution_" + ccard.id).addClass("validator_sub");
		$("#_cardNo_" + ccard.id).addClass("validator_sub");
		$("#_expiryDate_" + ccard.id).addClass("validator_sub");
		$("#_holder_" + ccard.id).addClass("validator_sub");
		$("#_cardType_" + ccard.id).addClass("validator_sub");
		$("#_merchantNo_" + ccard.id).addClass("validator_sub");
		$("#_billType_" + ccard.id).addClass("validator_sub");
		$("#_preferenceCard_" + ccard.id).addClass("validator_sub");
		$("#_remark_" + ccard.id).addClass("validator_sub");
		
		
		var $btn=$("<input/>",{type:"button",value:"Save"});
		$btn.addClass("btn");
		$btn.click(function(){
			$("#statusshower").show(1000);
			//call_credit_service_update();
			validate3("update",ccard);
		});
		$div.append($btn);
		
		var $btn=$("<input/>",{type:"button",value:"delete"});
		$btn.addClass("btn");
		$div.append($btn);
		$btn.click(function(){
			services.call(CCARD_DEL, [ccard.id], afDeleteCCard);
		});
		
		$div.addClass("c_div");
	    $div.hide();
		$("#_expiryDate_" + ccard.id).blur(dateTransformByMonth);
				
	    $("#ccard-form").dialog("close");	
	};
	
	function afDeleteCCard(obj){
		if(!obj.success) {
			alert("some errors occur while processing request, credit card");
			return;
		}
		$("#ccc_"+obj.data.id).remove();
	}
	
	var afListCCard = function(obj){
		if(!obj.success) {
			alert("some errors occur while processing request, credit card");
			return;
		}
		var list = obj.data;
		for(var i=0;i<list.length;i++){
			addCCard( list[i] );
		}
		$("#statusshower").hide();
	};
	
	
	$(function(){
		services.call("common.listCardVendor", [], fillVendor);
	});
	
	function fillVendor(codes) {			
		for(i = 0; i < codes.length; i++) {
			$option=$("<option/>",{value:codes[i].key, html:codes[i].value});
			$("#vendor").append($option);
		}
		services.call("common.listCardType", [], fillCardType);
	}

	function fillInstitute(codes) {			
		for(i = 0; i < codes.length; i++) {
			$option=$("<option/>",{value:codes[i].key, html:codes[i].value});
			$("#institution").append($option);
		}
		services.call(CCARD_LIST, [compId], afListCCard);
	}
	
	function fillCardType(codes) {			
		for(i = 0; i < codes.length; i++) {
			$option=$("<option/>",{value:codes[i].key, html:codes[i].value});
			$("#cardType").append($option);
		}
		services.call("common.listMerchantNo", [], fillMerchantNo);
	}
	
	function fillMerchantNo(codes) {			
		for(i = 0; i < codes.length; i++) {
			$option=$("<option/>",{value:codes[i].key, html:codes[i].value});
			$("#merchantNo").append($option);
		}
		services.call("common.listInstituteKey", [], fillInstitute);
		
	}
	
</script>
	
	<div id="ccard-form" title="Form - Credit Card" style="display:none">
	<div id="cust3">
		<div id="trav3"><div id="comp3">
	
		<div id="product-form_message"> 
			<input type="hidden" id="ccard-id" name="ccard.id">

			<table>
				<tr>
					<td>Vendor</td>
				</tr>
				<tr>
					<td>
						<select id="vendor" class="validator">
						
						</select>
					</td>
				</tr>
				
				<tr>
					<td>Institution</td>
				</tr>
				<tr>
					<td>
						<select id="institution" class="validator">
						
						</select>
					</td>
				</tr>
				
				<tr>
					<td>Card No</td>
				</tr>
				<tr>
					<td>
						<input type="text" id="cardNo" class="validator"/>
					</td>
				</tr>
				
				
				<tr>
					<td>Expiry Date(YYYY/MM)</td>
				</tr>
				<tr>
					<td>
						<input type="text" id="expiry" class="validator" />
					</td>
				</tr>
				<tr>
					<td>Holder Name</td>
				</tr>
				<tr>
					<td>
						<input type="text" id="holderName" class="validator" />
					</td>
				</tr>
				<tr>
					<td>Card Type</td>
				</tr>
				<tr>
					<td>
						<select id="cardType" class="validator">
							
						</select>
					</td>
				</tr>
				<tr>
					<td>Merchant No</td>
				</tr>
				<tr>
					<td>
						<select id="merchantNo" class="validator">
							
						</select>
					</td>
				</tr>
				<tr>
					<td>Bill Type</td>
				</tr>
				<tr>
					<td>
						<select id="billType" class="validator">
							<option value="Personal">Personal</option>
							<option value="Corporate">Corporate</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>Preference Card</td>
				</tr>
				<tr>
					<td>
						<select id="preferenceCard" class="validator">
							<option value="No">No</option>
							<option value="Yes">Yes</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>Remark</td>
				</tr>
				<tr>
					<td>
						<textarea id="remark" rows="6" cols="60"></textarea>
					</td>
				</tr>
			</table>
		</div></div>
		</div></div>
</div>




