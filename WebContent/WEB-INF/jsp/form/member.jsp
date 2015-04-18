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
	var init_product_dialog_flag_MB = false;
	var OK_FUNC_MB = function(){
		$("#statusshower").show();
		var member = getMB();
		validate2("create",member);
	};
	
	var call_member_service_create= function(member){
		services.call(MEMBER_CREATE, [member.prodType, member.memberNo, member.memberName,
                                      member.memberCode, member.memberIssue, member.memberExpiry,
                                      member.memberRemark , compId], afCreateMb);
	};
		
	var call_member_service_update= function(member){
		services.call(MEMBER_UPDATE, [member.id,
                                      $("#_prodType_" + member.id).val(),
                                      $("#_memberNo_" + member.id).val(),
                                      $("#_memberName_" + member.id).val(),
                                      $("#_memberCode_" + member.id).val(),
                                      $("#_memberIssue_" + member.id).val(),
                                      $("#_memberExpiry_" + member.id).val(),
                                      $("#_memberRemark_" + member.id).val()
                                      ], afUpdate);
	};
	
	var CANCEL_FUNC_MB = function(){
		$("#MB-form").dialog("close");
		$("#statusshower").hide();
	};
	var initMember = function(){
		$("#statusshower").show();
		services.call(MEMBER_LIST, [compId], afListMember);
	};
	// called by OK_FUNC_MB()
	var afCreateMb = function(obj) {
		if(!obj.success) {
			alert("some errors occur while processing request, member");
			return;
		}
		addMember(obj.data);
		$("#statusshower").hide();
		
	};
	

	$(function() {
		
		$("#memberExpiry").datepicker({dateFormat: 'yy-mm-dd'} );
		$("#memberIssue").datepicker({dateFormat: 'yy-mm-dd'} );
		$("#memberIssue").blur(dateTransform);
		$("#memberExpiry").blur(dateTransform);
		$("#memberCode").blur(function(){
			$(this).val($(this).val().toUpperCase());
		});
		if(!init_product_dialog_flag_MB){
			$("#MB-form").dialog({
				resizable : true,
				width : 430,
				height : 580,
				modal : true,
				buttons : {
					"OK" : function(){
						OK_FUNC_MB();
						RESET_MB();
					},
					Cancel : function() {
						CANCEL_FUNC_MB();
						RESET_MB();
					}
				}
			});
			init_product_dialog_flag_MB = true;
			$("#MB-form").dialog("close");
		}
	});
	
	var RESET_MB = function(){
		$("#prodType").val("");
		$("#memberNo").val("");
		$("#memberName").val("");
		$("#memberCode").val("");
		$("#memberIssue").val("");
		$("#memberExpiry").val("");
		$("#memberRemark").val("");
		
	};
	
	var getMB = function(){
		return {
			prodType: $("#prodType").val(),
			memberNo: $("#memberNo").val(),
			memberName: $("#memberName").val(),
			memberCode: $("#memberCode").val(),
			memberIssue: $("#memberIssue").val(),
			memberExpiry: $("#memberExpiry").val(),
			memberRemark: $("#memberRemark").val()
			};
	};
	var addMember = function(member){
		
		var $cdiv=$("<div/>");
		$cdiv.attr("id","mc_" + member.id);
		$("#memberPool").append($cdiv);

		var $label = $("<div/>");
		$label.addClass("member");
		$label.attr("id", member.id);
		$cdiv.append($label);
		$label.html(member.prodType +" "+ member.memberCode + " " + member.memberNo);

		$label.toggle(
				
				function() {
					$("#mi_" + member.id).show(1000);
				},
				function() {
					$("#mi_" + member.id).hide(1000);
				}

		);

		var $div=$("<div/>");
		$div.attr("id","mi_" + member.id);
		$cdiv.append($div);
		
		var $table=$("<table/>");
				
		$table.append(createRow("Product Type:",{type:"text",id:"_prodType_" + member.id, value:member.prodType,readonly:true}));
		$table.append(createRow("Member No",{type:"text",id:"_memberNo_" + member.id, value:member.memberNo,readonly:true}));
		$table.append(createRow("Member Name:",{type:"text",id:"_memberName_" + member.id, value:member.memberName}));
		$table.append(createRow("Member Code:",{type:"text",id:"_memberCode_" + member.id, value:member.memberCode,readonly:true}));
		$table.append(createRow("Member Issue:",{type:"text",id:"_memberIssue_" + member.id, value:member.memberIssueStr}));
		$table.append(createRow("Member Expiry:",{type:"text",id:"_memberExpiry_" + member.id, value:member.memberExpireStr}));
		$table.append(insertTableRow(["MemberRemark:"],[$("<textarea/>",{id:"_memberRemark_" + member.id, html:member.remark, cols:50, rows:6})]));
		$div.append($table);
		
		$("#_prodType_" + member.id).addClass("validator_sub");
		$("#_memberNo_" + member.id).addClass("validator_sub");
		$("#_memberName_" + member.id).addClass("validator_sub");
		$("#_memberCode_" + member.id).addClass("validator_sub");
		$("#_memberIssue_" + member.id).addClass("validator_sub");
		$("#_memberExpiry_" + member.id).addClass("validator_sub");
		$("#_memberRemark_" + member.id).addClass("validator_sub");
		
		var $btn=$("<input/>",{type:"button",value:"Save"});
		$btn.addClass("btn");
		$btn.click(function(){
			$("#statusshower").show(1000);
			//call_member_service_update(member);
			validate3("update",member);			
		});
		$div.append($btn);
		
		$btn=$("<input/>",{type:"button",value:"delete"});
		$btn.addClass("btn");
		$div.append($btn);
		$btn.click(function(){
			services.call(MEMBER_DEL, [member.id], afDeleteMember);
		});
		
		$div.addClass("c_div");
	    $div.hide();
		$("#_memberExpiry_" + member.id).datepicker({dateFormat: 'yy-mm-dd'} );
		$("#_memberIssue_" + member.id).datepicker({dateFormat: 'yy-mm-dd'} );
		$("#_memberExpiry_" + member.id).blur(dateTransform);
		$("#_memberIssue_" + member.id).blur(dateTransform);
		$("#_prodType_" + member.id).css("backgroundColor","#eee");
		$("#_memberNo_" + member.id).css("backgroundColor","#eee");
		$("#_memberCode_" + member.id).css("backgroundColor","#eee");
		
		$("#_memberCode_" + member.id).blur(function(){
			$(this).val($(this).val().toUpperCase());
		});
	    $("#MB-form").dialog("close");	
	};
	
	function afDeleteMember(obj){
		if(!obj.success) {
			alert("some errors occur while processing request, member");
			return;
		}
		$("#mc_"+obj.data.id).remove();
	}
	var afListMember = function(obj){
		if(!obj.success) {
			alert("some errors occur while processing request, member");
			return;
		}
		var list = obj.data;
		for(var i=0;i<list.length;i++){
			addMember( list[i] );
		}
		$("#statusshower").hide();
	};
		
</script>
	
	<div id="MB-form" title="Form - Member" style="display:none">
		<div id="trav6"><div id="comp6">
		<div id="product-form_message"> 
			<input type="hidden" id="MB-id" name="member.id">

			<table>
				<tr>
					<td>Product Type:</td>
				</tr>
				<tr>
					<td>
						<select id="prodType">
							<option value="AIR">AIR</option>
							<option value="CAR">CAR</option>
							<option value="HOTEL">HOTEL</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>Member No:</td>
				</tr>
				<tr>
					<td>
						<input type="text" id="memberNo" class="validator"/>
					</td>
				</tr>
				
				
				<tr>
					<td>Member Name:</td>
				</tr>
				<tr>
					<td>
						<input type="text" id="memberName" class="validator" />
					</td>
				</tr>
				<tr>
					<td>Member Code</td>
				</tr>
				<tr>
					<td>
						<input type="text" id="memberCode"  class="validator"/>
					</td>
				</tr>
				<tr>
					<td>Member Issue</td>
				</tr>
				<tr>
					<td>
						<input type="text" id="memberIssue"  class="validator"/>
					</td>
				</tr>
				<tr>
					<td>Member expire</td>
				</tr>
				<tr>
					<td>
						<input type="text" id="memberExpiry"  class="validator"/>
					</td>
				</tr>
				<tr>
					<td>Remark</td>
				</tr>
				<tr>
					<td>
						<textarea cols="50" rows="7" id="memberRemark" class="validator"></textarea>
					</td>
				</tr>
				
			</table>
		</div>
		</div>
		</div>
</div>




