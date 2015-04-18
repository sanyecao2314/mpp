<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<jsp:include page="/WEB-INF/jsp/common/head.jsp"></jsp:include>
	<script type="text/javascript" src="js/jquery.js"></script>
	<script src="js/jquery.ui.core.js"></script>
	<script src="js/jquery.ui.widget.js"></script>
	<script src="js/jquery.ui.mouse.js"></script>
	<script src="js/jquery.ui.selectable.js"></script>
	
	<script type='text/javascript'
		src='${pageContext.request.contextPath}/dwr/interface/services.js'></script>
	<script type='text/javascript'
		src='${pageContext.request.contextPath}/dwr/engine.js'></script>

	<script src='js/common.js'></script>
	
	<script type="text/javascript">
		
		$(document).ready(function(){
		  $("#tab1").addClass("tab_act").removeClass("tab_out");
		  load(1);
		  
		  
		$("#btn_search").click(function(){
			load(1);
		});
		  
		});
		
		
		var UPDATE_STATUS_REMARK="customer.updateStatusRemark";
		var objid ="";
		var currentPage = 1;
		var load = function(pageNo) {
			reset();
			$("#statusshower").show();
			currentPage = pageNo;
			services.call("customer.countCustomer", [$("#quickSearch").val()], afterCount);
		};
		
		var afterLoad = function(obj) {
			if(!obj.success) {
				alert("some errors occur while processing request.");
				return;
			}
			for(var i=0;i<obj.data.length;i++){
				/*
				$btn = $("<img src='img/MinusRed.png' class='act' width='26' height='26' style='cursor:hand'/>");
				
				if(obj.data[i].status=='ACTIVE'){
					$btn.attr("src","img/Blue Ball.png");
				} else {
					$btn.attr("src","img/Grey Ball.png");
				}
				
				
				$btn.click(function(){
					var id = $(this).parent().parent().attr("id");
					$( "#dialog-confirm" ).dialog({
						resizable: false,
						height:200,
						modal: true,
						buttons: {
							"Active/Inactive": function() {
								services.call("customer.activeCustomer", [ id ], afterActive);
								$( this ).dialog( "close" );
							},
							Cancel: function() {
								$( this ).dialog( "close" );
							}
						}
					});
					
					$( "#dialog-confirm" ).dialog( "open" );
				});
				insertRow($("#selectable"), obj.data[i].id, [ i + 1, a("cname", 
						obj.data[i].name, "displayCust.action?id="+obj.data[i].id),
						$btn, "" ]);
				*/
				insertRow($("#selectable"), obj.data[i].id, [ i + 1, a("cname", 
						obj.data[i].name, "displayCust.action?id="+obj.data[i].id),
						"", "" ]);
			}
			$("#statusshower").hide();
		};

		var afterCount = function(obj) {
			if (!obj.success) {
				alert("some errors occur while processing request.");
				return;
			}

			services.call("customer.listCustomer", [ currentPage , $("#quickSearch").val()], afterLoad);
			$("#pageCtrl").append(pageCtrl(obj.data.count, currentPage));
		};

		var reset = function() {
			$("#del_panel").html("");
			$("#selectable tr:gt(0)").remove();
			$("#pageCtrl").html("");
			_uncheck();
		};

		pageCtrl_func = function(pageNo) {
			load(pageNo);
		};

		$(function() {
			
			$("#chk1").removeClass("checked");
			$("#chk2").removeClass("checked");
			$("#chk2_txt").html("OFF");
			$("#chk1_txt").html("&nbsp;");
			$(this).addClass("unchecked");
			$(this).removeClass("checked");
			
			$("#checkbox").click(function(){
				if($(this).hasClass("unchecked")){
					_check();
				}else{
					_uncheck();
				}
			oncheck();
			});

		});
			var _check = function(){
				$("#chk1").addClass("checked");
				$("#chk2").addClass("checked");
				$("#chk2_txt").html("&nbsp;");
				$("#chk1_txt").html("ON");
				$("#checkbox").removeClass("unchecked");
				$("#checkbox").addClass("checked");
			};
			var _uncheck = function(){
				$("#chk1").removeClass("checked");
				$("#chk2").removeClass("checked");
				$("#chk2_txt").html("OFF");
				$("#chk1_txt").html("&nbsp;");
				$("#checkbox").addClass("unchecked");
				$("#checkbox").removeClass("checked");
			};
			var oncheck = function(){
				
				var list=$(".table_ctrl");
				for(var i=0;i<list.length;i++){
					if($("#checkbox").hasClass("unchecked")){
						list.eq(i).html("");	
					} else {
						$btn = $("<img src='img/MinusRed.png' width='26' height='26' style='cursor:hand'/>");
												
						$btn.click(function(){
							var id = $(this).parent().parent().attr("id");
							$( "#dialog-confirm" ).dialog({
								resizable: false,
								height:200,
								modal: true,
								buttons: {
									"Delete": function() {
										services.call("customer.deleteCustomer", [ id ], afterDelete);
										$( this ).dialog( "close" );
									},
									Cancel: function() {
										$( this ).dialog( "close" );
									}
								}
							});
							
							$( "#dialog-confirm" ).dialog( "open" );
						});
						list.eq(i).append($btn);
					}	
				}
			};
			
			var afterActive=function(obj){
				if (!obj.success) {
					alert("some errors occur while processing request.");
					return;
				}
				var $btn = $("#"+obj.data.id + " td img[class='act']");
				if(obj.data.status == "ACTIVE") {
					$btn.attr("src","img/Blue Ball.png");
				} else {
					$btn.attr("src","img/Grey Ball.png");
				}
				objid = obj.data.id;
				$("#status-remark").val(obj.data.statusRemark);
				$("#status-form").dialog("open");
			};
			
			var afterDelete = function(obj){
				if (!obj.success) {
					alert("some errors occur while processing request.");
					return;
				}
				$("#" + obj.data).remove();
			};
	</script>
	
	<body>
		<jsp:include page="/WEB-INF/jsp/common/top.jsp"></jsp:include>
		<jsp:include page="/WEB-INF/jsp/common/modalconfirmdialog.jsp"></jsp:include>
		<jsp:include page="/WEB-INF/jsp/form/statusRemarktrav.jsp"></jsp:include>
		<div id="statusshower">Loading...</div>
		<table width="100%" cellpadding="0" cellspacing="0">
			<tr>
				<td class="loginform" valign="top">
					<div id="btn_new" class="info_text">
						To create new customer, please click <a href="createCust.action">new</a>.
					</div>

					<table cellpadding="0" cellspacing="0" width="100%">
						<tr>
							<td valign="top">
								<table id="header" width="100%" cellpadding="0" cellspacing="0">
									<tr>
										<td>
											Name: <input type="text" id="quickSearch" value="" />
											<input type="button" id="btn_search" value="Search" />
										</td>
										<td valign="top" align="right">
											<div id="checkbox" class="unchecked">
												<div id="chk1" class="sChkB_l"><label id="chk1_txt">ON</label></div><div id="chk2" class="sChkB"><label id="chk2_txt">OFF</label></div>
											</div>
										</td>
									</tr>
								</table>
								<table id="selectable" width="100%" cellpadding="0" cellspacing="0">
									<tr class="table_header">
										<td></td>
										<td width="30">Seq.</td>
										<td width="200">Customer Name</td>
										<td width="80"></td>
										<td></td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
					<div id="pageCtrl"></div>
					<div id="del_panel"></div>
				</td>
			</tr>
		</table>
	</body>
</html>



