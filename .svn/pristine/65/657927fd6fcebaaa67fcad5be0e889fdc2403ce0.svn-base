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
	var init_product_dialog_flag_VIS = false;
	var OK_FUNC_VIS = function(){
		$("#statusshower").show();
		var visa = getVisa();
		validate2("create",visa);
	};
	
	var call_visa_service_create= function(visa){
		services.call("traveler.createVisa", [visa.country, visa.visaType, visa.visaIssueCountry,
             visa.visaNo, visa.visaIssue, visa.visaExpiry,
             visa.visaRemark , visa.visaPurpose, visa.noOfEntry, compId], afCreateVisa);
	};
		
	var call_visa_service_update= function(visa){
		services.call("traveler.updateVisa", [visa.id,
             $("#_visa_country_" + visa.id).val(),
             $("#_visaType_" + visa.id).val(),
             $("#_visaIssueCountry_" + visa.id).val(),
             $("#_visaNo_" + visa.id).val(),
             $("#_visaIssue_" + visa.id).val(),
             $("#_visaExpiry_" + visa.id).val(),
             $("#_visaRemark_" + visa.id).val(),
             $("#_visaPurpose_" + visa.id).val(),
             $("#_noOfEntry_" + visa.id).val()
             ], afUpdate);	
	};
	
	var CANCEL_FUNC_VIS = function(){
		$("#visa-form").dialog("close");
		$("#statusshower").hide();
	};
	var initvisa = function(){
		$("#statusshower").show();
		services.call("traveler.listVisa", [compId], afListVisa);
	};
	// called by OK_FUNC_VIS()
	var afCreateVisa = function(obj) {
		if(!obj.success) {
			alert("some errors occur while processing request, visa");
			return;
		}
		addVisa(obj.data);
		$("#statusshower").hide();
		
	};

	$(function() {
		$("#visaExpiry").datepicker({dateFormat: 'yy-mm-dd'} );
		$("#visaIssue").datepicker({dateFormat: 'yy-mm-dd'} );
		$("#visaIssue").blur(dateTransform);
		$("#visaExpiry").blur(dateTransform);
		
		if(!init_product_dialog_flag_VIS){
			$("#visa-form").dialog({
				resizable : true,
				width : 430,
				height : 680,
				modal : true,
				buttons : {
					"OK" : function(){
						OK_FUNC_VIS();
						RESET_VIS();
					},
					Cancel : function() {
						CANCEL_FUNC_VIS();
						RESET_VIS();
					}
				}
			});
			init_product_dialog_flag_VIS = true;
			$("#visa-form").dialog("close");
		}
	});
	
	var RESET_VIS = function(){
		$("#visa_country").val("");
		$("#visaType").val("");
		$("#visaPurpose").val("");
		$("#noOfEntry").val("");
		$("#visaIssue").val("");
		$("#visaIssueCountry").val("");
		$("#visaExpiry").val("");
		$("#visaRemark").val("");
		$("#visaNo").val("");
	};
	
	var addVisa = function(visa){	
		
		var $cdiv=$("<div/>");
		$cdiv.attr("id","vc_" + visa.id);
		$("#visaPool").append($cdiv);

		var $label = $("<div/>");
		$label.addClass("visa");
		$label.attr("id", visa.id);
		$cdiv.append($label);
		$label.html(visa.country + " " + visa.visaNo);
		
		$label.toggle(
				
				function() {
					$("#vi_" + visa.id).show(1000);
				},
				function() {
					$("#vi_" + visa.id).hide(1000);
				}

		);

		var $div=$("<div/>");
		$div.attr("id","vi_" + visa.id);
		$cdiv.append($div);
		
		var $table=$("<table/>");
				
		$table.append(createRow("Country:",{type:"text",id:"_visa_country_" + visa.id, value:visa.country,readonly:true}));
		$table.append(createRow("Visa Type:",{type:"text",id:"_visaType_" + visa.id, value:visa.visaType,readonly:true}));
		$table.append(createRow("Issue Country:",{type:"text",id:"_visaIssueCountry_" + visa.id, value:visa.visaEntry,readonly:true}));
		$table.append(createRow("Visa No:",{type:"text",id:"_visaNo_" + visa.id, value:visa.visaNo,readonly:true}));
		$table.append(createRow("Visa Issue:",{type:"text",id:"_visaIssue_" + visa.id, value:visa.visaidateStr}));
		$table.append(createRow("Visa Expiry:",{type:"text",id:"_visaExpiry_" + visa.id, value:visa.visaedateStr}));
		$table.append(createRow("Visa Purpose:",{type:"text",id:"_visaPurpose_" + visa.id, value:visa.purpose}));
		
		$select = $("<select/>",{id:"_noOfEntry_" + visa.id});
		$opt= $("<option/>",{val:"Single",html:"Single"});
		$select.append($opt);
		$opt= $("<option/>",{val:"Double",html:"Double"});
		$select.append($opt);
		$opt= $("<option/>",{val:"Mutiple",html:"Mutiple"});
		$select.append($opt);
		$table.append(insertTableRow(["No Of Entry:"],[$select]));
		
		$table.append(insertTableRow(["Visa Remark:"],[$("<textarea/>",{id:"_visaRemark_" + visa.id, html: visa.visarem, cols:50, rows:6})]));
		$div.append($table);
		
		$("#_visa_country_" + visa.id).addClass("validator_sub");
		$("#_visaType_" + visa.id).addClass("validator_sub");
		$("#_visaIssueCountry_" + visa.id).addClass("validator_sub");
		$("#_visaNo_" + visa.id).addClass("validator_sub");
		$("#_visaIssue_" + visa.id).addClass("validator_sub");
		$("#_visaExpiry_" + visa.id).addClass("validator_sub");
		$("#_visaPurpose_" + visa.id).addClass("validator_sub");
		$("#_noOfEntry_" + visa.id).addClass("validator_sub");
		$("#_visaRemark_" + visa.id).addClass("validator_sub");
		
		var $btn=$("<input/>",{type:"button",value:"Save"});
		$btn.addClass("btn");
		$btn.click(function(){
			$("#statusshower").show(1000);
			
			//call_visa_service_update(visa);
			validate3("update",visa);
		});
		$div.append($btn);
		
		$btn=$("<input/>",{type:"button",value:"delete"});
		$btn.addClass("btn");
		$div.append($btn);
		$btn.click(function() {
			services.call("traveler.deleteVisa", [visa.id], afDeleteVisa);
		});
		$div.addClass("c_div");
	    $div.hide();

	    
	    defaultValue($select,visa.noOfEntry);
	    $("#_visaType_" + visa.id).css("backgroundColor","#eee");
	    $("#_visaIssueCountry_" + visa.id).css("backgroundColor","#eee");
	    $("#_visaNo_" + visa.id).css("backgroundColor","#eee");
	    $("#_visa_country_" + visa.id).css("backgroundColor","#eee");
	    
	    $("#_visaExpiry_" + visa.id).datepicker({dateFormat: 'yy-mm-dd'} );
		$("#_visaIssue_" + visa.id).datepicker({dateFormat: 'yy-mm-dd'} );
		$("#_visaExpiry_" + visa.id).blur(dateTransform);
		$("#_visaIssue_" + visa.id).blur(dateTransform);

	    $("#visa-form").dialog("close");	
	};
	
	function afDeleteVisa(obj){
		if(!obj.success) {
			alert("some errors occur while processing request, visa");
			return;
		}
		$("#vc_"+obj.data.id).remove();
	}
	
	var afListVisa = function(obj){
		if(!obj.success) {
			alert("some errors occur while processing request, visa");
			return;
		}
		var list = obj.data;
		for(var i=0;i<list.length;i++){
			addVisa( list[i] );
		}
		$("#statusshower").hide();
	};
	
	var getVisa = function() {
		return {
			country: $("#visa_country").val(),
			visaType: $("#visaType").val(),
			visaPurpose: $("#visaPurpose").val(),
			noOfEntry: $("#noOfEntry").val(),
			visaIssue: $("#visaIssue").val(),
			visaIssueCountry: $("#visaIssueCountry").val(),
			visaExpiry: $("#visaExpiry").val(),
			visaRemark: $("#visaRemark").val(),
			visaNo: $("#visaNo").val()
		};
	};
</script>
	
	<div id="visa-form" title="Form - Visa" style="display:none">
	<div id="trav9"><div id="comp9">
		<div id="product-form_message"> 
			<input type="hidden" id="visa-id" name="visa.id">

			<table>
				<tr>
					<td>Country:</td>
				</tr>
				<tr>
					<td>						
						<input type="text" id="visa_country" class="validator" />
					</td>
				</tr>
				<tr>
					<td>Visa Type:</td>
				</tr>
				<tr>
					<td>
						<input type="text" id="visaType" class="validator" />
					</td>
				</tr>
				
				
				<tr>
					<td>Visa Purpose:</td>
				</tr>
				<tr>
					<td>
						<input type="text" id="visaPurpose" class="validator" />
					</td>
				</tr>
				<tr>
					<td>No of Entry</td>
				</tr>
				<tr>
					<td>
						<select id="noOfEntry" class="validator">
							<option value="Single">Single</option>
							<option value="Double">Double</option>
							<option value="Multiple">Multiple</option>
						</select>
						
					</td>
				</tr>
				<tr>
					<td>visa Issue</td>
				</tr>
				<tr>
					<td>
						<input type="text" id="visaIssue" class="validator" />
					</td>
				</tr>
				<tr>
					<td>visa Issue Country</td>
				</tr>
				<tr>
					<td>
						<input type="text" id="visaIssueCountry" class="validator"/>
					</td>
				</tr>
				<tr>
					<td>visa expire</td>
				</tr>
				<tr>
					<td>
						<input type="text" id="visaExpiry" class="validator"/>
					</td>
				</tr>
				<tr>
					<td>Visa No</td>
				</tr>
				<tr>
					<td>
						<input type="text" id="visaNo" class="validator"/>
					</td>
				</tr>
				<tr>
					<td>Remark</td>
				</tr>
				<tr>
					<td>
						<textarea cols="50" rows="7" id="visaRemark" class="validator"></textarea>
					</td>
				</tr>
				
			</table>
		</div>
		</div>
		</div>
</div>




