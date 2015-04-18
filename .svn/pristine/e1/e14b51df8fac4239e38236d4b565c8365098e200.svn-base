<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://www.citsamex.com/taglib/app" prefix="mss"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<jsp:include page="/WEB-INF/jsp/common/head.jsp"></jsp:include>

<style>
#feedback {
	font-size: 1.4em;
}

#selectable .ui-selecting {
	background: #FECA40;
}

#selectable .ui-selected {
	background: #966668;
	color: white;
}

#selectable {
	list-style-type: none;
	margin: 0;
	padding: 0;
	width: 100%;
}

#selectable li {
	margin: 3px;
	padding: 0.4em;
	font-size: 1.4em;
	height: 18px;
}

#selectable .td1 {
	text-align: center;
	width: 30px;
}

#selectable .td2 {
	text-align: center;
	width: 150px;
}

#selectable .td3 {
	text-align: left;
}
</style>
<script type="text/javascript" src="js/jquery.js"></script>
<script src="js/jquery.ui.core.js"></script>
<script src="js/jquery.ui.widget.js"></script>
<script src="js/jquery.ui.mouse.js"></script>
<script src="js/jquery.ui.selectable.js"></script>
<script src="js/common.dialog.js"></script>
<script src='js/common.js'></script>
<script type='text/javascript'
	src='${pageContext.request.contextPath}/dwr/interface/services.js'></script>
<script type='text/javascript'
	src='${pageContext.request.contextPath}/dwr/engine.js'></script>


<script type="text/javascript">

	$(document).ready(function() {
		$("#tab4").addClass("tab_act").removeClass("tab_out");
	});

	
	$(function() {

		$("#selectable").selectable({
			stop : function() {
				var result = $("#select-result").empty();
				$(".ui-selected", this).each(function() {
					var index = $("#selectable li").index(this);
					result.append(" #" + (index + 1));
				});
				
				var all_tds=$("#selectable .td2");
				for(var i=0;i<all_tds.length;i++){
					all_tds.eq(i).html(all_tds.eq(i).attr("id"));
				}
				var sel_trs=$("#selectable .ui-widget-content.ui-selectee.ui-selected .td2");
				for(var i=0;i<sel_trs.length;i++){
					var btn1=$("<input type='button' name='btn' value='Delete' />");
					
					btn1.click(function(){
						var id = $(this).parent().parent().attr("id");
						
						OK_FUNC =function(){
							services.call("product.deleteProduct", [id], afterDelete);
						};
									
						$( "#dialog-confirm" ).dialog( "open" );


					});
					var btn2=$("<input type='button' name='btn' value='Update' />");
					btn2.click(function(){
						var id = $(this).parent().parent().attr("id");
						services.call("product.findProduct", [id], afterLoad);
					});
					//sel_trs.eq(i).html("");
					//sel_trs.eq(i).append(btn1);
					//sel_trs.eq(i).append(btn2);
				}
			}
		});
	});
	
	function afterLoad(obj){
		if(!obj.success){
			return;
		}
		$("#product-no").attr("readonly","");
		
		OK_FUNC = function(){
			services.call("product.updateProduct", [$("#product-id").val(),
			    $("#product-no").val(), $("#product-name").val(),
			    $("#vendor-name").val(),
			    $("#product-desc").val()], afterUpdate);
		};
		
		CANCEL_FUNC = function(){
			$("#product-form").dialog("close");	
			reset();
		};
		
		$("#product-id").val(obj.data.id);
		$("#product-no").val(obj.data.productNo);
		$("#product-name").val(obj.data.productName);
        $("#vendor-name").val(obj.data.venderName);
        $("#product-desc").val(obj.data.productDesc);
        
		$("#product-form").dialog("open");	
	}
	
	function afterUpdate(obj) {
		if(!obj.success){
			$("#clause-form-message").html(obj.serviceMessage);
			return;
		}
		var tds=$("#"+obj.data.id+" td");
		tds.eq(2).html(obj.data.productName);
		
		$("#product-form").dialog("close");
		reset();
	}
	
	function afterDelete(obj){
		if(!obj.success){
			return;
		}
		deleteRow(obj.data);
		$( "#dialog-confirm" ).dialog( "close" );
	}
	
	function deleteRow(id){
		$("#"+id).remove();
	}
	
	// click the new link to launch.
	function create() {
		OK_FUNC = function(){
			services.call("product.createProduct", [
			    $("#product-no").val(), $("#product-name").val(),
			    $("#vendor-name").val(),
			    $("#product-desc").val()], afterCreate);
		};
		
		CANCEL_FUNC = function(){
			$("#product-form").dialog("close");
			reset();
		};
		
		$("#product-form").dialog("open");
	}
	
	function afterCreate(obj) {
		if(!obj.success){
			$("#product-form-message").html(obj.serviceMessage);
			return;
		}
		insertRow($("#selectable"), obj.data.id, "ui-widget-content ui-selectee", [$("#selectable tr").length+1,obj.data.productNo,obj.data.productName], ["td1","td2","td3"]);
		$("#product-form").dialog("close");
		reset();
	}

	//insert a new row in table.
	function insertRow(jqtable, id, rowClass, values, classes) {
		var row = $("<tr></tr>");
		row.attr("id",id);
		row.addClass(rowClass);
		
		for(var i=0;i<values.length;i++){
			td = $("<td></td>");
			td.append(values[i]);
			td.addClass(classes[i]);
			row.append(td);
		}
		jqtable.append(row);
		
	}
	
	function reset(){
		$("#product-no").removeAttr("readonly");
		
		$("#product-no").val("");
		$("#product-name").val("");
	    $("#vendor-name").val("");
	    $("#product-desc").val("");
	}
</script>

<body>
	<jsp:include page="/WEB-INF/jsp/common/top.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/form/product.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/common/confirm_dialog.jsp"></jsp:include>
	
	<table width="100%" cellpadding="0" cellspacing="0">
		<tr>
			<td class="loginform" valign="top">
				<!-- 
				<div id="btn_new" class="info_text">
					To add new product, please click <a href="javascript:create()">new</a>.
				</div>
 				-->
 
				<table width="100%">
					<thead>
						<tr>
							<td width="30">Seq</td>
							<td width="100">Product Code</td>
							<td width="200">Product Name</td>
							<td width="200">Product Name SH</td>
							<td width="200">Product Name GZ</td>
							<td width="200">Product Name BJ</td>
							<td></td>
						</tr>
					</thead>
				</table>
				<table id="selectable" width="100%">
					<c:forEach var="product" items="${list }" varStatus="status">
						<tr class="ui-widget-content" id="${product.id }">
							<td width="30" id="${product.productNo }" class="td1">${status.index +1 }.</td>
							<td width="100" id="${product.productNo }" class="td2">${product.productNo }</td>
							<td width="200" id="${product.productNo }" class="td3">${product.productName }</td>
							<td width="200" id="${product.productNo }" class="td3">${product.productNameSH }</td>
							<td width="200" id="${product.productNo }" class="td3">${product.productNameGZ }</td>
							<td width="200" id="${product.productNo }" class="td3">${product.productNameBJ }</td>
							<td></td>
						</tr>
					</c:forEach>
				</table></td>
		</tr>
	</table>
</body>
</html>



