<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://www.citsamex.com/taglib/app" prefix="mss"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<jsp:include page="/WEB-INF/jsp/common/head.jsp"></jsp:include>

<style>

</style>
<script type="text/javascript" src="js/jquery.js"></script>
<script src="js/jquery.ui.core.js"></script>
<script src="js/jquery.ui.widget.js"></script>
<script src="js/jquery.ui.mouse.js"></script>
<script src="js/jquery.ui.selectable.js"></script>
<script type='text/javascript'
	src='${pageContext.request.contextPath}/dwr/interface/services.js'></script>
<script type='text/javascript'
	src='${pageContext.request.contextPath}/dwr/engine.js'></script>

<script type="text/javascript">

	var init_dialog_flag = false;
	$(document).ready(function() {
		$("#tab5").addClass("tab_act").removeClass("tab_out");
	});

	var a_func = function(){
		alert(1);
	};
	
	$(function() {

		$( "#dialog-confirm" ).hide();
		var deleteItem = function(){
			
		};
		$("#selectable").selectable({
			stop : function() {			
				var all_tds=$("#selectable .td2");
				for(var i=0;i<all_tds.length;i++){
					var $a = $("<a href='#'></a>");
					$a.html(all_tds.eq(i).attr("id"));
					$a.click(function(){
						alert(1);
					});
					all_tds.eq(i).html($a);
				}
				var sel_trs=$("#selectable .ui-widget-content.ui-selectee.ui-selected .td2");
				for(var i=0;i<sel_trs.length;i++){
					var btn1=$("<input type='button' name='btn' value='Delete' />");
					
					btn1.click(function(){
						var ids = $(this).parent().attr("id").split('-');
						
						$( "#dialog-confirm" ).dialog({
							resizable: false,
							height:200,
							modal: true,
							buttons: {
								"Delete": function() {
									deleteItem();
									$( this ).dialog( "close" );
								},
								Cancel: function() {
									$( this ).dialog( "close" );
								}
							}
						});
						
						$( "#dialog-confirm" ).dialog( "open" );
						deleteItem=function(){
							services.call("customer.deleteClause", [ids[0],ids[1]], deleteClause);
						};

					});
					var btn2=$("<input type='button' name='btn' value='Update' />");
					btn2.click(function(){
						var ids = $(this).parent().attr("id").split('-');
						services.call("customer.findClause", [ids[0],ids[1]], setClause);
					});
					sel_trs.eq(i).html("");
					sel_trs.eq(i).append(btn1);
					sel_trs.eq(i).append(btn2);
				}
			}
		});
	});
	
	function deleteClause(obj){
		if(!obj.success){
			return;
		}
		deleteRow(obj.data);
	}
	
	function deleteRow(id){
		$("#"+id).remove();
	}
	// service.createClause call back.
	function addClause(obj) {
		if(!obj.success){
			$("#clause-form-message").html(obj.serviceMessage);
			return;
		}
		insertRow($("#selectable tr").length+1, obj.data.catalog + '-'
				+ obj.data.no, obj.data.content, obj.data.status,obj.data.id);
		closeClauseDialog();
	}
	
	function updateClause(obj) {
		if(!obj.success){
			$("#clause-form-message").html(obj.serviceMessage);
			return;
		}
		
		var tds=$("#"+obj.data.id+" td");
		tds.eq(2).html(obj.data.content);
		closeClauseDialog();
	}
	
	//insert a new row to table in dom.
	function insertRow(val1, val2, val3, val4, id) {
		var row = $("<tr></tr>");
		row.attr("id",id);
		row.addClass("ui-widget-content ui-selectee");

		var td1 = $("<td></td>");
		td1.html(val1);
		td1.addClass("td1");
		td1.attr("id",val2);
		row.append(td1);
		var td2 = $("<td></td>");
		
		td2.html(val2);
		td2.addClass("td2");
		td2.attr("id",val2);
		row.append(td2);
		var td3 = $("<td></td>");
		td3.html(val3);
		td3.addClass("td3");
		td3.attr("id",val2);
		row.append(td3);
		$("#selectable").append(row);
	}
	
	// click the new link to launch.
	function createClause() {
	
		execDialogfunc = function(){
			services.call("customer.createClause", [
			    $("#clause-catalog").val(), $("#clause-no").val(),
			    $("#clause-content").val(),
			    $("[name='clause-status']:checked").val() ], addClause);
		};
		openDialog();        
	}
	
	function setClause(obj){
		if(!obj.success){
			return;
		}
		$("#clause-catalog").attr("readonly","");
		$("#clause-no").attr("readonly","");
		execDialogfunc=function(){
			services.call("customer.updateClause", [
   				$("#clause-catalog").val(), $("#clause-no").val(),
   			    $("#clause-content").val(),
   			    $("[name='clause-status']:checked").val() ], updateClause);
		};
		openDialog();
		
		$("#clause-catalog").val(obj.data.catalog);
		$("#clause-no").val(obj.data.no);
        $("#clause-content").val(obj.data.content);
        
        if($("[name='clause-status']").eq(0).val()==obj.data.status)
        	$("[name='clause-status']").eq(0).attr("checked",true);
        else
        	$("[name='clause-status']").eq(1).attr("checked",true);
		
	}
	
	function openDialog() {
		if(!init_dialog_flag){
			$("#clause-form").dialog({
				resizable : true,
				width : 600,
				height : 500,
				modal : true,
				buttons : {
					"Save" : function(){
						execDialogfunc();
					},
					Cancel : function() {
						closeClauseDialog();
					}
				}
			});
			init_dialog_flag=true;
		}
		$("#clause-form").dialog("open");
	}
	
	function execDialogfunc(){
		
	}
	
	function closeClauseDialog(){
		$("#clause-catalog").val("");
		$("#clause-no").val("");
        $("#clause-content").val("");
        $("[name='clause-status']").eq(0).attr("checked",true);
		$("#clause-catalog").removeAttr("readonly");
		$("#clause-no").removeAttr("readonly");
		$("#clause-form").dialog("close");
	}
	
	
</script>

<body>
	<jsp:include page="/WEB-INF/jsp/common/top.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/form/clause.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/common/modalconfirmdialog.jsp"></jsp:include>

	<table width="100%" cellpadding="0" cellspacing="0">
		<tr>
			<td class="loginform" valign="top">
				<div id="btn_new" class="info_text">
					To add new clause, please click <a href="javascript:createClause()">new</a>.
				</div>

				<table width="100%">
					<thead>
						<tr class="table_header">
							<td width="30">Seq</td>
							<td width="150">Clause No</td>
							<td>Content</td>
						</tr>
					</thead>
				</table>
				<table id="selectable">
					<c:forEach var="clause" items="${list }" varStatus="status">
						<tr class="ui-widget-content" id="${clause.id }">
							<td id="${clause.catalog }-${clause.no }" class="td1">${status.index +1 }.</td>
							<td id="${clause.catalog }-${clause.no }" class="td2">${clause.catalog }-${clause.no }</td>
							<td id="${clause.catalog }-${clause.no }" class="td3">${clause.content }</td>
						</tr>
					</c:forEach>
				</table></td>
		</tr>
	</table>
</body>
</html>



