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
	
	var init_product_dialog_flag_upload = false;

	$(function() {
		$("#upload-form").hide();
		
		if(!init_product_dialog_flag_upload){
			$("#upload-form").dialog({
				resizable : true,
				width : 470,
				height : 150,
				modal : true,
				buttons : {
					"OK" : function() {
						OK_FUNC_CF();
					},
					Cancel : function() {
						CANCEL_FUNC_CF();
					}
				}
			});
			init_product_dialog_flag_upload = true;
			$("#upload-form").dialog("close");
		}
		
	});
	
	var OK_FUNC_CF = function() {
		$("#statusshower").show();
		
		$("#uploadform").submit();
		$("#upload-form").dialog("close");
	};
	
	var CANCEL_FUNC_CF = function(){
		$("#upload-form").dialog("close");
		RESET_CF();
	};
	
	var RESET_CF = function(){
		$("#upload-no").val("");
	};

</script>

<div id="upload-form" title="Form - Upload" style="display:none">
		<div id="upload-form_message"> 			
			<input type="hidden" name="upload.id">
			
			<s:form id="uploadform" action="upload.action" method="POST" enctype="multipart/form-data">
				<input type="hidden" name="companyId" value="${company.id }" />
				<s:file name="files" label="" theme="simple" cssStyle="width:450px" />
			</s:form>
			
		</div>
</div>




