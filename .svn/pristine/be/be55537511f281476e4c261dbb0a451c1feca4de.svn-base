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
	var init_product_dialog_flag_FCD = false;
	var rowid=1;
	
	var OK_FUNC_FCD = function() {
		$("#statusshower").show();
		var obj = getFeeCodeDetail();
		
		var groupId = $("#fcdGroupId").val();
		var validateSel = $("#validateSel").val();
		
		if(groupId == '' || groupId == null){
			alert("Group ID can't be empty.");
			return;
		}
		
		if(validateSel=='' || validateSel == null){
			alert("Please select a validate period.");
			return;
		}
		
		//visit all rows in the data table.
		//if its id start with tmp,create
		//else, update.
		
		var $table_rows=$("#fcd_table tr");
		for(i = 1; i<$table_rows.length; i++) {
			var $cols = $("#"+$table_rows.eq(i).attr("id")+" td");
			
			var fcdProd = $("#" + $cols.eq(0).attr("id") +" select" ).val();
			
			var fcdRangeFrom = $("#" + $cols.eq(1).attr("id")+" input").eq(0).val();
			
			//check if it's a numeric data. --jquery.
			if(fcdRangeFrom!='') {
				
			}
			
			var fcdRangeTo = $("#" + $cols.eq(1).attr("id")+" input").eq(1).val();
			if(fcdRangeTo!='') {
				
			}
			
			var fcdByAmt = $("#" + $cols.eq(2).attr("id")+" input").val();
			var fcdByPercent = $("#" + $cols.eq(3).attr("id")+" input").val();
			var fcdMethod = $("#" + $cols.eq(4).attr("id")+" select").val();
			var fcdType = $("#" + $cols.eq(5).attr("id")+" select").val();
			
			if($table_rows.eq(i).attr("id").substring(0,3)=="tmp"){
				services.call(FEECODEDETAIL_CREATE, [groupId, validateSel, fcdProd, 
				                                     fcdRangeFrom, fcdRangeTo,
				                                     fcdByAmt, fcdByPercent,
				                                     fcdMethod, fcdType, compId, feeCodeId], 
				                                     afCreateFeeCodeDetail);
			} else {
				services.call(FEECODEDETAIL_UPDATE, [$table_rows.eq(i).attr("id"),
				                                     groupId, validateSel, fcdProd, 
				                                     fcdRangeFrom, fcdRangeTo,
				                                     fcdByAmt, fcdByPercent,
				                                     fcdMethod, fcdType], 
				                                     afUpdateFeeCodeDetail);
			}
			
		}
		
		$("#statusshower").hide();
		RESET_FCD();
		
		$("#fcd-form").dialog("close");
	};
	
	var initFeeCodeDetail = function() {
		$("#statusshower").show();
		services.call(FEECODEDETAIL_LIST, [compId, feeCodeId, groupid], afLoadOption);
	};
	
	var afLoadOption = function(obj) {
		if(!obj.success) {
			alert("some errors occur while processing request, feecodeDetail");
			return;
		}
		
		var $options = $("#validateSel option");
		for(var i=1; i<$options.length; i++) {
			$options.eq(i).remove();
		}
		
		var list = obj.data;
		var temp = "";
		for(var i=0; i<list.length; i++) {
			if(temp == list[i].validPeriod){
				continue;
			}
			temp = list[i].validPeriod;
			var $option = $("<option/>", {html:temp});
			$("#validateSel").append($option);	
			
		}
		var rows = $("#fcd_table tr");
		for(i = 1; i < rows.length; i++) {
			rows.eq(i).remove();
		}
		
		$("#statusshower").hide();
	};
	
	var afCreateFeeCodeDetail = function(obj) {
		if(!obj.success) {
			alert("some errors occur while processing request, feecodeDetail");
			return;
		}		
	};
	
	
	var addFeeCodeDetail = function(obj) {
		if(temp==obj.validPeriod){
			return;
		}
		temp = obj.validPeriod;
		var $option = $("<option/>", {html:temp});
		$("#validateSel").append($option);		
	};
	
	function updateFeeCodeDetail(id){
		
	}
	
	function afUpdateFeeCodeDetail(obj){
		if(!obj.success) {
			alert("some errors occur while processing request, feecodeDetail");
			return;
		}
		
	}
	
	function afListFeeCodeDetail(obj){
		if(!obj.success) {
			alert("some errors occur while processing request, feecodeDetail");
			return;
		}
		
		for(i=0; i<obj.data.length; i++){
			var colid = 0;
			$tr=$("<tr/>");
			$tr.attr("id", obj.data[i].id);
			
			$td=$("<td/>");
			$td.attr("id",$tr.attr("id")+"_"+colid++);
			
			$tr.append($td);
			
			$select = $("<select/>",{id:"fcdProd"});
			$select.css("width","100px");
			<c:forEach var="product" items="${productlist }">
				$option=$("<option/>");
				$option.html("${product.productNo }");
				$select.append($option);
							
				if("${product.productNo }"==obj.data[i].productCode){
					$option[0].selected=true;
				}
				
			</c:forEach>
			$td.append($select);
			
			$td=$("<td/>");
			$td.attr("id",$tr.attr("id")+"_"+colid++);
			
			$tr.append($td);
			$text=$("<input/>",{id:"fcdRangeFrom",value:obj.data[i].moneyRangeFrom});
			$text.css("width","80px");
			$td.append($text);
			
			$td.append(" - ");
			
			$text=$("<input/>",{id:"fcdRangeTo",value:obj.data[i].moneyRangeTo});
			$text.css("width","80px");
			$td.append($text);			
			
			$td=$("<td/>");
			$td.attr("id",$tr.attr("id")+"_"+colid++);
			
			$tr.append($td);
			$text=$("<input/>",{id:"fcdByAmt",value:obj.data[i].feeAmt});
			$text.css("width","80px");
			$td.append($text);
			
			$td=$("<td/>");
			$td.attr("id",$tr.attr("id")+"_"+colid++);
			
			$tr.append($td);
			$text=$("<input/>",{id:"fcdByPercent",value:obj.data[i].feePercent});
			$text.css("width","80px");
			$td.append($text);
			
			$td=$("<td/>");
			$td.attr("id",$tr.attr("id")+"_"+colid++);
			
			$tr.append($td);
			$select = $("<select/>",{id:"fcdMethod"});
			$select.css("width","100px");
			$option=$("<option/>");
			$option.html("By Amt");
			if(obj.data[i].feeMethod=="By Amt"){
				$option[0].selected=true;
			}
			$select.append($option);
			
			
			$option=$("<option/>");
			$option.html("By Percent");
			if(obj.data[i].feeMethod=="By Percent"){
				$option[0].selected=true;
			}
			$select.append($option);
			
			$option=$("<option/>");
			$option.html("Large Value");
			if(obj.data[i].feeMethod=="Large Value"){
				$option[0].selected=true;
			}
			$select.append($option);
			
			$option=$("<option/>");
			$option.html("Small Value");
			if(obj.data[i].feeMethod=="Small Value"){
				$option[0].selected=true;
			}
			$select.append($option);
			
			$td.append($select);
			
			$td=$("<td/>");
			$td.attr("id",$tr.attr("id")+"_"+colid++);
			
			$tr.append($td);
			$select = $("<select/>",{id:"fcdType"});
			$select.css("width","100px");
			$option=$("<option/>");
			$option.html("By Unit");
			if(obj.data[i].feeType=="By Unit"){
				$option[0].selected=true;
			}
			$select.append($option);
			
			$option=$("<option/>");
			$option.html("By Transaction");
			if(obj.data[i].feeType=="By Transaction"){
				$option[0].selected=true;
			}
			$select.append($option);
			$td.append($select);
		
			$td=$("<td/>");
			$td.attr("id",$tr.attr("id")+"_"+colid++);
			
			$tr.append($td);
			
			$btn=$("<input/>",{type:"button",id:"btnRemoveEntry",value:"-"});
			$btn.click(function(){
				var id=$(this).parent().parent().attr("id");
				if(id.substring(0,3)!="tmp"){
					services.call(FEECODEDETAIL_DEL, [id], 
							afDeleteFeeCodeDetail);
				}
				else
					$(this).parent().parent().remove();
			});
			$td.append($btn);

			$("#fcd_table").append($tr);
		}
		

	}
	function afDeleteFeeCodeDetail(obj) {
		if(!obj.success) {
			alert("some errors occur while processing request, feecodeDetail");
			return;
		}
		$("#"+obj.data.id).remove();
	}
	
	var CANCEL_FUNC_FCD = function() {
		$("#fcd-form").dialog("close");
	};

	$(function() {		
		if (!init_product_dialog_flag_FCD) {
			$("#fcd-form").dialog({
				resizable : true,
				width : 750,
				height : 600,
				modal : true,
				buttons : {
					"OK" : function() {
						OK_FUNC_FCD();
						
					},
					Cancel : function() {
						CANCEL_FUNC_FCD();
						RESET_FCD();
					}
				}
			});
			init_product_dialog_flag_FCD = true;
			$("#fcd-form").dialog("close");
		}
		
		$("#btn_add").click(function(){
			var from = $("#fcdvalidFrom").val();
			var to = $("#fcdvalidTo").val();
			
			if(from == '' && to == '') {
				$("#add_panel2").css("display","block");
				$("#add_panel").css("display","none");
				return;
			}
			
			if(from == '' || to == '') {
				alert("Please input the validate date.");
				return;
			}
			
			if(from > to){
				alert("please check the validate date.");
				return;
			}
			
			var $option = $("<option/>",{html:from + " to " + to});
			$option[0].selected = true;
			$("#validateSel").append($option);
			
			$("#add_panel2").css("display","block");
			$("#add_panel").css("display","none");
			
		});
		
		$("#btn_add1").click(function(){
			$("#add_panel").css("display","block");
			$("#add_panel2").css("display","none");
		});
		
		$("#btnRemoveEntry").click(function(){
			$(this).parent().parent().remove();
		});
		
		$("#btnInsertEntry").click(function(){
			var colid=0;
			$tr=$("<tr/>");
			$tr.attr("id", "tmp_" + rowid++);
			
			$td=$("<td/>");
			$td.attr("id",$tr.attr("id")+"_"+colid++);
			
			$tr.append($td);
			
			$select = $("<select/>",{id:"fcdProd"});
			$select.css("width","100px");
			<c:forEach var="product" items="${productlist }">
				$option=$("<option/>");
				$option.html("${product.productNo }");
				$select.append($option);
			</c:forEach>
			$td.append($select);
			
			$td=$("<td/>");
			$td.attr("id",$tr.attr("id")+"_"+colid++);
			
			$tr.append($td);
			$text=$("<input/>",{id:"fcdRangeFrom"});
			$text.css("width","80px");
			$td.append($text);
			
			$td.append(" - ");
			
			$text=$("<input/>",{id:"fcdRangeTo"});
			$text.css("width","80px");
			$td.append($text);			
			
			$td=$("<td/>");
			$td.attr("id",$tr.attr("id")+"_"+colid++);
			
			$tr.append($td);
			$text=$("<input/>",{id:"fcdByAmt"});
			$text.css("width","80px");
			$td.append($text);
			
			$td=$("<td/>");
			$td.attr("id",$tr.attr("id")+"_"+colid++);
			
			$tr.append($td);
			$text=$("<input/>",{id:"fcdByPercent"});
			$text.css("width","80px");
			$td.append($text);
			
			$td=$("<td/>");
			$td.attr("id",$tr.attr("id")+"_"+colid++);
			
			$tr.append($td);
			$select = $("<select/>",{id:"fcdMethod"});
			$select.css("width","100px");
			$option=$("<option/>");
			$option.html("By Amt");
			$select.append($option);
			
			$option=$("<option/>");
			$option.html("By Percent");
			$select.append($option);
			
			$option=$("<option/>");
			$option.html("Large Value");
			$select.append($option);
			
			$option=$("<option/>");
			$option.html("Small Value");
			$select.append($option);
			$td.append($select);
			
			$td=$("<td/>");
			$td.attr("id",$tr.attr("id")+"_"+colid++);
			
			$tr.append($td);
			$select = $("<select/>",{id:"fcdType"});
			$select.css("width","100px");
			$option=$("<option/>");
			$option.html("By Unit");
			$select.append($option);
			
			$option=$("<option/>");
			$option.html("By Transaction");
			$select.append($option);
			$td.append($select);
		
			$td=$("<td/>");
			$td.attr("id",$tr.attr("id")+"_"+colid++);
			
			$tr.append($td);
			
			$btn=$("<input/>",{type:"button",id:"btnRemoveEntry",value:"-"});
			$btn.click(function(){
				$(this).parent().parent().remove();
			});
			$td.append($btn);

			$("#fcd_table").append($tr);
		});
		
		$("#validateSel").change(function(){
			var rows = $("#fcd_table tr");
			for(i=1; i<rows.length; i++){
				rows.eq(i).remove();
			}
			services.call(FEECODEDETAIL_LIST, [compId, feeCodeId, $(this).val(),$("#fcdGroupId").val()], 
			                                     afListFeeCodeDetail);
		});
		
	});

	var RESET_FCD = function() {
		defaultValue($("#fcdGroupId"));
		$("#fcdvalidFrom").val("");
		$("#fcdvalidTo").val("");
		$("#validateSel").val("");
		$("#fcdProd").val("");
		$("#fcdRangeFrom").val("");
		$("#fcdRangeTo").val("");
		$("#fcdByAmt").val("");
		$("#fcdByPercent").val("");
		$("#fcdMethod").val("");
		$("#fcdType").val("");
	};

	var getFeeCodeDetail = function() {
		return {
			
		};
	};

	$(function(){
		$("#fcdGroupId").css("width","150px");
		$("#fcdvalidFrom").css("width","150px");
		$("#fcdvalidTo").css("width","150px");
		
		$("#fcdGroupId").change(function(){
			groupid=$(this).val();
			initFeeCodeDetail();
		});
		
	});
</script>



<div id="fcd-form" title="Form - Fee Code Detail" style="display:none">
	<div id="product-form_message">
		<input type="hidden" id="fc-id" name="fc.id">

		<table>
			<tr>
				<td width="100">Group ID:</td>
				<td>
					<select id="fcdGroupId">
					
					</select>
				</td>
			</tr>
			<tr>
				<td>Valid Period:</td>
				<td>
					<div id="add_panel" style="display:none">
						<input type="text" id="fcdvalidFrom" />
						-
						<input type="text" id="fcdvalidTo" />
						<input type="button" id="btn_add" class="btn" value="O  K" />
					</div>
					<div id="add_panel2">
						<select id="validateSel" style="width:250px">
						<option></option>
						</select>
						<input type="button" id="btn_add1" value="Add Validate Period" />
					</div>
				</td>
			</tr>

			<tr>
				<td colspan="2">
					<table id="fcd_table" width="700">
						<tr>
							<td class="fcd_table">
								Product Code
							</td>
							<td class="fcd_table">
								Money Range
							</td>
							<td class="fcd_table">
								By Amt
							</td>
							<td class="fcd_table">
								By Percent(%)
							</td>
							<td class="fcd_table">
								Method
							</td>
							<td class="fcd_table">
								Type
							</td>
							<td>
								<input type="button" id="btnInsertEntry" value="+" />
							</td>
						</tr>
						
					</table>	
				</td>
			</tr>
		</table>
	</div>
</div>




