<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript" src="js/jquery.js"></script>
<script src="js/jquery.ui.core.js"></script>
<script src="js/jquery.ui.widget.js"></script>
<script src="js/jquery.ui.tabs.js"></script>
<script src="js/jquery.ui.mouse.js"></script>	
<script src="js/jquery.ui.sortable.js"></script>
<script src="js/jquery.ui.position.js"></script>
<script src="js/jquery.ui.autocomplete.js"></script>
<script src="js/jquery.ui.datepicker.js"></script>
<script src="js/common.js"></script>
<script type='text/javascript'
		src='${pageContext.request.contextPath}/dwr/interface/services.js'></script>
<script type='text/javascript'
		src='${pageContext.request.contextPath}/dwr/engine.js'></script>
<script type="text/javascript">

	$(function() {	
		services.call("traveler.initUploadColumnMapp",[],afInit);
	});
	
	function afInit(list) {
		
		var $tmp_select = $("<select>");
		var $option = $("<option/>",{html:"", val:""});
		$option.attr("selected", "selected");
		$tmp_select.append($option);
		
		for( var i = 0; i < list.length; i++ ) {
			var $option = $("<option value='"+list[i].key+"'/>");
			$option.append(list[i].value);
			$tmp_select.append($option);
		}

		var $table = $("<table>");
		$(window.parent.document).find("#tabs-9").append($table);
			
		<c:forEach var="column" items="${columns }">
		
			var $tr = $("<tr>");
			$table.append($tr);
			
			//column 1
			var $td = $("<td>");
			$tr.append($td);
			
			var $input = $("<input>",{type:"text"});
			$input.addClass("columnField");
			$input.val("${column}");
			$input.attr("readonly", true);
			$td.append($input);
		
			//column 2
			var $td = $("<td>");
			$tr.append($td);
			
			var $select = $("<select>");
			$select.addClass("fieldsMapping");
			var $option = $("<option/>",{html:"", val:""});
			$select.append($option);
			$option.attr("selected", "selected");
			
			var hasOpts = false;
			for( var i = 0; i < list.length; i++ ) {
				var $option = $("<option value='"+list[i].key+"'/>");
				$option.append(list[i].value);
				if("${column}"==list[i].value) {
					$option.attr("selected", "selected");
					$select.append($option);
					hasOpts=!hasOpts;
					break;
				}
			}
			
			if(!hasOpts) {
				$select.html($tmp_select.html());
			}
			
			$select.css("width","250px");
			$td.append($select);
			
		</c:forEach>
		
	
		$(window.parent.document).find("#serverFile").val("${serverFilename }");
		
		$(window.parent.document).find("#uploadTable").remove();
	}
	
	
</script>

<body>
	<h4>Please waiting...</h4>
</body>