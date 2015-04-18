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
	$(function(){
		services.call("common.listDBIKey", [], afterListDBIKey);
	});
	

	function afterListDBIKey(codes){
		
		var $table=$("<table id='dbi'/>");
		$("#dbi_div").append($table);
		for(i = 0; i < codes.length; i++) {
			
			$div=$("<div/>");
			$input = $("<input/>",{type:"hidden",name:"dbiId",value:0});
			$div.append($input);
			
			$input01 = $("<input/>",{type:"hidden",name:"dbiKeyValue",value:0});
			$div.append($input01);
			
			$input02 = $("<input/>",{type:"hidden",name:"dbiOptional",value:0});
			$div.append($input02);
			
			$input = $("<input/>",{type:"hidden",name:"dbiKey",value:codes[i].key});
			$div.append($input);
			$inputc1 = $("<input/>",{type:"checkbox",name:"dbiKeyValuec",value:0});
	
			$div.append($inputc1);
			$div.append(codes[i].key);
			
			
			$select1=$("<input/>",{name: "dbiValue1", type:"text"});
			$select2=$("<input/>",{name: "dbiValue2", type:"text"});
			
			/*
			$select1=$("<select/>",{name: "dbiValue1"});
			$select1.append($("<option/>",{value:"Invoice Header Info",html:"Invoice Header Info"}));
			$select1.append($("<option/>",{value:"Invoice",html:"Invoice"}));
			
			$select2=$("<select/>",{name: "dbiValue2"});
			$select2.append($("<option/>",{value:"TR",html:"TR"}));
			$select2.append($("<option/>",{value:"CR",html:"CR"}));
			*/
			
			$div2=$("<div/>");
			$inputc2 = $("<input/>",{type:"checkbox",name:"dbiOptionalc",value:0});
			
			$div2.append($inputc2);
			$div2.append("Optional");
			
			$table.append(insertTableRow2([$div,$select1,$select2,$div2]));
			$div2.parent().parent().attr("id",i);

		}
		
		services.call("company.listDBI", [compId], afterListDBI);
		
	}

	function afterListDBI(obj){
		
		if(!obj.success) {
			alert("some errors occur while processing request, dbi");
			return;
		}
		
		for(i = 0; i < obj.data.length; i++) {
			
			$("input[name='dbiId']").eq(i).val(obj.data[i].id);
			$("input[name='dbiKeyValue']").eq(i).val(obj.data[i].dbiKeyValue);
			$("input[name='dbiOptional']").eq(i).val(obj.data[i].optional);
			$("input[name='dbiKey']").eq(i).val(obj.data[i].dbiKey);
			
			if(obj.data[i].dbiKeyValue == 1)
				$("input[name='dbiKeyValuec']").eq(i)[0].checked = true;
			else
				$("input[name='dbiKeyValuec']").eq(i)[0].checked = false;
			
			if(obj.data[i].optional == 1)
				$("input[name='dbiOptionalc']").eq(i)[0].checked = true;
			else
				$("input[name='dbiOptionalc']").eq(i)[0].checked = false;
		
			$("input[name='dbiValue1']").eq(i).val(obj.data[i].value1);
			$("input[name='dbiValue2']").eq(i).val(obj.data[i].value2);
			/*
			//select 1
			var v1=$("#"+i+" select[name='dbiValue1'] option[value='"+obj.data[i].value1+"']");
			v1[0].selected=true;
			
			//select 2
			var v2=$("#"+i+" select[name='dbiValue2'] option[value='"+obj.data[i].value2+"']");
			v2[0].selected=true;
			*/
			
		}
		
		$("input[name='dbiKeyValuec']").each(function(){
			$(this).click(function() {
				var idx=$(this).parent().parent().parent().attr("id");
				if($(this)[0].checked) {
					$("input[name='dbiKeyValue']").eq(idx).val(1);
				} else {
					$("input[name='dbiKeyValue']").eq(idx).val(0);
				}
			});
		});
		
		$("input[name='dbiOptionalc']").each(function(){
			$(this).click(function(){
				var idx=$(this).parent().parent().parent().attr("id");
				if($(this)[0].checked) {
					$("input[name='dbiOptional']").eq(idx).val(1);
				} else {
					$("input[name='dbiOptional']").eq(idx).val(0);
				}
			});
		});
	}
	
</script>

<div id="dbi_div"></div>