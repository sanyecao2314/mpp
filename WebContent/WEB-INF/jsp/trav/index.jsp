<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false" import=" com.citsamex.ws.*" %>
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
	<script src='js/common.js'></script>
	<script src="js/jquery.ui.datepicker.js"></script>
	<script type="text/javascript" src="js/jquery.timers.js"></script>
	
	<script type='text/javascript'
		src='${pageContext.request.contextPath}/dwr/interface/services.js'></script>
	<script type='text/javascript'
		src='${pageContext.request.contextPath}/dwr/engine.js'></script>
	
	<jsp:include page="/WEB-INF/jsp/common/head.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/form/par_load_panel.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/form/syncInfo.jsp"></jsp:include>
	
	<script type="text/javascript">
	
		<%Employee employee=((Employee)session.getAttribute("passport"));%>
		var cur_user="<%=employee.getId() + "/" + employee.getUsernameCn()%>";
	
		$(document).ready(function(){
		  $("#tab3").addClass("tab_act").removeClass("tab_out");
		  load(1);
		});
		
		var LOAD_ACTION="traveler.loadPar";
		
		var UPDATE_STATUS_REMARK="traveler.updateStatusRemark";
		var objid="";
		
		var currentPage = 1;
		var load = function(pageNo) {
			reset();
			$("#statusshower").show();
			currentPage = pageNo;
			var x=$("#quickSearch_ebill")[0].checked?1:0;
			var axo=$("#quickSearch_fromAxo")[0].checked?"axo":"";
			
			services.call("traveler.countTraveler", ["${param.cpname }", $("#quickSearch").val(),$("#quickSearch_name").val(),$("#quickSearch_createDate").val(),$("#quickSearch_modifyDate").val(),x,axo], afterCount);
		};
		
		var afterCount = function(obj) {
			if (!obj.success) {
				alert("some errors occur while processing request.");
				return;
			}
			var x=$("#quickSearch_ebill")[0].checked?1:0;
			var axo=$("#quickSearch_fromAxo")[0].checked?"axo":"";
			services.call("traveler.listTraveler", [ currentPage, "${param.cpname }", $("#quickSearch").val(),$("#quickSearch_name").val(),$("#quickSearch_createDate").val(),$("#quickSearch_modifyDate").val(),x,axo], afterLoad);
			$("#pageCtrl").append(pageCtrl(obj.data.count, currentPage));
		};
		
		var afterLoad = function(obj) {
			if(!obj.success) {
				alert("some errors occur while processing request.");
				return;
			}
			for(var i=0;i<obj.data.length;i++) {
				
				
				var $btn = $("<img src='img/MinusRed.png' class='act' width='26' height='26' style='cursor:hand'/>");
				if(obj.data[i].status=='ACTIVE'){
					$btn.attr("src","img/Blue Ball.png");
				} else {
					$btn.attr("src","img/Grey Ball.png");
				}
				$btn.click(function(){
					
					$("#confirm_dialog_message").html("This action will change the status of selected item.");
					var id = $(this).parent().parent().attr("id");
					
					$( "#dialog-confirm" ).dialog({
						resizable: false,
						height:200,
						modal: true,
						buttons: {
							"Active/InActive": function() {
								services.call("traveler.activeTraveler", [ id ], afterActive);
								$( this ).dialog( "close" );
								$("#confirm_dialog_message").html("THis action will remove the selected item, are you sure?");
							},
							Cancel: function() {
								$( this ).dialog( "close" );
								$("#confirm_dialog_message").html("THis action will remove the selected item, are you sure?");
							}
						}
					});
					
					$( "#dialog-confirm" ).dialog( "open" );
				});

				var $lockdiv=$("<div>");
				var $btn1 = $("<img src='img/sync.png' class='btn_sync' width='26' height='26' style='cursor:hand'/>");
				$btn1.unbind('click');
				
				if(obj.data[i].lock == 0) {
					$btn1.attr("src","img/blank.gif");
					$lockdiv.append($btn1);
				}else if(obj.data[i].lock == 1) {
					if(cur_user==obj.data[i].lockuser){
						if(obj.data[i].dirty == -1){
							$btn1.attr("src","img/loading.gif");
						}else{
							$btn1.attr("src","img/sync.png");
							$btn1.click(function() {
								var id=$(this).attr("id");
								travelerNo=id.substring(4);
								$("#confirm_dialog_message").html("This action will synchronize PowerSuite.");
								$( "#dialog-confirm" ).dialog({
									resizable: false,
									width:350,
									height:200,
									modal: true,
									buttons: {
										"Synchronize": function() {										
											//$btn1.unbind('click');
											services.call("traveler.setDirty", [ travelerNo, -1], afDirty);
											$( this ).dialog( "close" );
											$("#"+id).attr("src","img/loading.gif");
											$("#confirm_dialog_message").html("This action will remove the selected item, are you sure?");
										},
										Cancel: function() {
											$( this ).dialog( "close" );
											$("#confirm_dialog_message").html("This action will remove the selected item, are you sure?");
										}
									}
								});
								
								$( "#dialog-confirm" ).dialog( "open" );
							});
						}
						$lockdiv.append($btn1);
					}else{
						$btn1.attr("src","img/User.png");
						$lockdiv.append($btn1);	
						$lockdiv.append(obj.data[i].lockuser);
					}
				}
				
				insertRow($("#selectable"), obj.data[i].id, [ $btn, i + 1, a("cname", obj.data[i].travelerNo, "displayTraveler.action?id="+obj.data[i].id), 
				                                              obj.data[i].travelerName, 
				                                              obj.data[i].companyName 
				                                              ,$lockdiv, "" ]);
				
				if(obj.data[i].lock == 1){
					$("#"+obj.data[i].id).addClass("obj_lock");
				}else{
					$("#"+obj.data[i].id).removeClass("obj_lock");
				}
				
				$("#"+obj.data[i].id).dblclick(function(){
					var id=$(this).attr("id");
					var travelerNo=$("#"+id+" td A").html();
					
					if($(this).hasClass("obj_lock")){
						services.call("traveler.unlock", [ travelerNo ], afUnlock);
					}else{
						services.call("traveler.lock", [ travelerNo ], afLock);
					}
				});
								
				var id = $btn1.parent().parent().attr("id");
				var travelerNo=$("#"+id+" td A").html();
				$btn1.attr("id", "btn_" + obj.data[i].travelerNo);
				
				
				for(j=0; j<citsamexSystems.length; j++) {
					$status_div=$("#sync_status_" + obj.data[i].travelerNo + citsamexSystems[j]);
					if($status_div.length==0){
						$status_div=$("<div/>",{id:"sync_status_" + obj.data[i].travelerNo + citsamexSystems[j]});
						$status_div.html(citsamexSystemsName[j]);
						$btn1.parent().append($status_div);
					}
					$status_div.addClass("sync_status_block");
					if((obj.data[i].dirty & citsamexSystems[j]) == citsamexSystems[j]) {
						$status_div.css("backgroundColor", citsamexSystemsColor[j]);
					} else {
						$status_div.css("backgroundColor", "#DDD");
					}
				}
				
				
			}
			
			$("#statusshower").hide(1000);
			$("#quickSearch").attr("readonly",false);
			$("#quickSearch").css("backgroundColor","#fff");
			
			$("#quickSearch_name").attr("readonly",false);
			$("#quickSearch_name").css("backgroundColor","#fff");
			
		};

		function afDirty(obj){
			if(obj==null){
				alert("Traveler is not found in MPP.");
				return;
			}
			var travelerNo=obj.travelerNo;
			services.call("traveler.synchronize", [ travelerNo, 0, 1, "PS,BS,AXO"], afSync);
		}
		
		function afSync(obj){
			if(obj == null){
				alert("Traveler is not found in MPP.");
				return;
			}
			
			var $tr = $("#"+obj.id);
			var $btn = $("#"+obj.id+" td .btn_sync");
			// $btn.unbind("click");
			$btn.unbind('click');
			$btn.attr("src","img/blank.gif");
					
			for(i=0;i<citsamexSystems.length;i++) {
				$status_div=$("#sync_status_"+obj.travelerNo + citsamexSystems[i]);
				if($status_div.length==0){
					$status_div=$("<div/>",{id:"sync_status_" + obj.travelerNo + citsamexSystems[i]});
					$status_div.html(citsamexSystemsName[i]);
					$btn.parent().append($status_div);
				}
				$status_div.addClass("sync_status_block");
				if((obj.dirty & citsamexSystems[i]) == citsamexSystems[i]) {
					$status_div.css("backgroundColor",citsamexSystemsColor[i]);
				} else {
					$status_div.css("backgroundColor","#DDD");
				}
			}
		}
		
		function afLock(obj) {
			if(obj == null){
				alert("Traveler is not found in MPP.");
				return;
			}
			
			var $tr = $("#"+obj.id);
			var $btn = $("#"+obj.id+" td .btn_sync");
			
			if(obj.lock==1 && cur_user==obj.lockuser) {
				if(!$tr.hasClass("obj_lock")){
					$tr.addClass("obj_lock");
					$btn.attr("src","img/sync.png");
					$btn.parent().html($btn);
					$btn.click(function() {
						var id=$(this).attr("id");
						travelerNo=id.substring(4);
						$("#confirm_dialog_message").html("This action will synchronize PowerSuite.");
						$( "#dialog-confirm" ).dialog({
							resizable: false,
							width:350,
							height:200,
							modal: true,
							buttons: {
								"Synchronize": function() {
									//$btn.unbind('click');
									services.call("traveler.setDirty", [ travelerNo, -1], afDirty);
									$( this ).dialog( "close" );
									$("#"+id).attr("src","img/loading.gif");
									$("#confirm_dialog_message").html("This action will remove the selected item, are you sure?");
								},
								Cancel: function() {
									$( this ).dialog( "close" );
									$("#confirm_dialog_message").html("This action will remove the selected item, are you sure?");
								}
							}
						});
						
						$( "#dialog-confirm" ).dialog( "open" );
					});
				}
			}else {
				$tr.removeClass("obj_lock");
				$btn.attr("src","img/User.png");
				$btn.parent().append(obj.lockuser);
			}
		}
		
		function afUnlock(obj) {
			if(obj == null){
				alert("Traveler is not found in MPP.");
				return;
			}
			
			var $tr = $("#"+obj.id);
			var $btn = $("#"+obj.id+" td .btn_sync");
			
			if(obj.lock==1) {
				if(!$tr.hasClass("obj_lock")){
					$tr.addClass("obj_lock");
					$btn.attr("src","img/User.png");
					$btn.parent().html($btn);
					if(cur_user!=obj.lockuser){
						$btn.parent().append(obj.lockuser);
					}
				}
			}else {
				$tr.removeClass("obj_lock");
				$btn.attr("src","img/blank.gif");
				$btn.parent().html($btn);
			}
		}
		
		
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
			
			$("#quickSearch_modifyDate").datepicker({
				dateFormat : 'yy-mm-dd'
			});

			$("#quickSearch_createDate").datepicker({
				dateFormat : 'yy-mm-dd'
			});
			
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

			$("#btn_loadPar").click(function(){
				$("#parload-no").attr("maxlength",10);
				$("#parload-form").dialog("open");
			});
		
			$("#btn_search").click(function(){
				$("#quickSearch").val($("#quickSearch").val().toUpperCase());
				load(1);
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
						
						$btn=$("<img src='img/MinusRed.png' width='26' height='26' style='cursor:hand'/>");
						$btn.click(function(){
							var id = $(this).parent().parent().attr("id");
							
							$( "#dialog-confirm" ).dialog({
								resizable: false,
								height:200,
								modal: true,
								buttons: {
									"Delete": function() {
										services.call("traveler.deleteTraveler", [ id ], afterDelete);
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
			
			var afterActive = function(obj){
				if (!obj.success) {
					alert("some errors occur while processing request.");
					return;
				}
				
				
				objid = obj.data.id;
				$("#status-remark").val(obj.data.statusRemark);
				$("#statusgroup-"+obj.data.status)[0].checked=true;
				$("#status-form").dialog("open");
			};
			
			var afterDelete = function(obj){
				if (!obj.success) {
					alert("some errors occur while processing request.");
					return;
				}
				
				$("#" + obj.data).remove();
			};
			
			$(document).everyTime(10000, function(i) {
				var btns = $(".btn_sync");
				var travNos="";
				for(i=0; i<btns.length; i++) {
					var travNo = btns.eq(i).attr("id").substring(4);
					travNos+=travNo;
					if( i < btns.length - 1 ) {
						travNos += ",";	
					}
				}
				services.call("traveler.getTravelerSyncStatus", [ travNos ], afTravSync);
				
			}, 10000);
			
			function afTravSync(list) {
				for(i=0; i<list.length; i++) {
					
					var travelerNo = list[i].travelerNo;
					var dirty = list[i].dirty;
					var $t_btn=$("#btn_"+travelerNo);
										
					if( list[i].lock==1 && list[i].lockuser==cur_user && dirty != -1 ) {
						if($t_btn.attr("src")=='img/sync.png'){
							continue;
						}
						$t_btn.unbind('click');
						$t_btn.attr("src","img/blank.gif");
						for(j=0; j<citsamexSystems.length; j++) {
							$status_div=$("#sync_status_" + list[i].travelerNo + citsamexSystems[j]);
							if($status_div.length==0){
								$status_div=$("<div/>",{id:"sync_status_" + list[i].travelerNo + citsamexSystems[j]});
								$status_div.html(citsamexSystemsName[j]);
								$t_btn.parent().append($status_div);
							}
							$status_div.addClass("sync_status_block");
							if((dirty & citsamexSystems[j]) == citsamexSystems[j]) {
								$status_div.css("backgroundColor", citsamexSystemsColor[j]);
							} else {
								$status_div.css("backgroundColor", "#DDD");
							}
						}
					}
					
				}
			}
			
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
						To create new traveler, please click <a href="createTraveler.action">new</a>.
					</div>
					<div id="btn_new2" class="info_text">
						To load from PowerSuite, please click <a id="btn_loadPar" href="#">load</a>.
					</div>

					<table cellpadding="0" cellspacing="0" width="100%">
						<tr>
							<td valign="top">
								<table id="header" width="100%" cellpadding="0" cellspacing="0">
									<tr>
										<td>
											No: <input type="text" id="quickSearch" value="" />
											Name: <input type="text" id="quickSearch_name" value="" />
											Create: <input type="text" id="quickSearch_createDate"/>
											Update: <input type="text" id="quickSearch_modifyDate"/>
											<input type="checkbox" id="quickSearch_fromAxo" value="1"/>aXo
											<input type="checkbox" id="quickSearch_ebill" value="1" /> E-Billing
											
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
										<td width="30">#</td>
										<td width="30">Seq.</td>
										
										<td width="150">Traveler No</td>
										<td width="200">Traveler Name</td>
										<td width="600">Company Name</td>
										
										<td width="150">#</td>
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



