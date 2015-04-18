	
	var citsamexSystems = [1,2,4];
	var citsamexSystemsColor = ["#FF0000","#00FF00","#0000FF"];
	var citsamexSystemsName = ["PS","AXO","BS"];

	
	//insert a new row in table.
	function insertRow(jqtable, id, values) {
		var row = $("<tr></tr>");
		row.attr("id",id);
		if($("#"+jqtable.attr("id")+" tr").length % 2==0)
			row.addClass("T_"+jqtable.attr("id")+"_TR1");
		else
			row.addClass("T_"+jqtable.attr("id")+"_TR2");
		
		td = $("<td></td>");
		td.addClass("table_ctrl");
		td.attr("width","20");
		row.append(td);
		
		for(var i=0;i<values.length;i++){
			td = $("<td></td>");
			td.append(values[i]);
			td.addClass("T_"+jqtable.attr("id")+"_TD"+(i+1));
			row.append(td);
			
		}
		jqtable.append(row);
		
	}

	function select(id,array) {
		var sel=$("<select></select>");
		sel.attr("id",id);
		for(var i=0;i<array.length;i++) {
			var opt=$("<option></option>");
			opt.val(array[i]);
			opt.html(array[i]);
			sel.append(opt);
		}
		return sel;
	}
	
	function img(id) {
		var img=$("<div/>");
		img.attr("id",id);
		return img;
		
	}

	function label(id,value) {
		var label=$("<label/>");
		label.attr("id",id);
		label.append(value);
		return label;
		
	}
	
	function a(id,value,href) {
		var a=$("<a></a>");
		a.attr("id",id);
		a.append(value);
		a.attr("href", href);
		return a;
	}
	
	var pageCtrl_func = function(pageNo) {
		alert(pageNo);
		alert("please overwrite this function!");
	};

	function pageCtrl(count, current) {
		var $table = $("<table></table>");
		var $tr = $("<tr></tr>");
		$table.append($tr);
		
		for(var i = 1; i <= count; i++) {
			
			if(current <= 20){
				if(i > 40){
					if(i!=count && i!=1)
						continue;
				}
			} else {
				if((i > (parseInt(current) + 20)) || (i < parseInt(current) - 20)) {
					if(i!=count && i!=1)
						continue;
				}
			}
			
			
			var $td=$("<td></td>");
			if(i==current){
				$td.attr("id","pageCtrlCur");
			}else{
				$td.attr("id","pageCtrlSpan");
			}
			var $a=$("<a href='#'></a>");
			$a.html(i);
			$a.click(function(){
				pageCtrl_func($(this).html());
			});
			$td.append($a);
			$tr.append($td);
		}
		return $table;
	}

	function insertTableRow(labels, objs){
		var $tr=$("<tr/>");
		
		for(var i=0; i<labels.length; i++){
			var $td=$("<td/>");
			$tr.append($td);
			$td.addClass("contactlabel");
			var $label=$("<label/>");
			$td.append($label);
			$label.html(labels[i]);
			
			$td=$("<td/>");
			$tr.append($td);
			$td.append(objs[i]);
		}
		return $tr;
	}
	
	function insertTableRow2(objs){
		$tr=$("<tr/>");
		
		for(var i=0; i<objs.length; i++){
			$td=$("<td/>");
			$tr.append($td);
			$td.append(objs[i]);
		}
		return $tr;
	}
	
	function createRow(label,textprops){
		var $tr=$("<tr/>");
		var $td=$("<td/>");
		$td.addClass("contactlabel");
		$tr.append($td);
		var $label=$("<label/>");
		$label.html(label);
		$td.append($label);
		$td=$("<td/>");
		$tr.append($td);
		var $text=$("<input/>", textprops);
		$text.attr("size",60);
		$td.append($text);
		return $tr;
	}
	
	function createRow2(label, textprops) {
		var $tr=$("<tr/>");
		var $td=$("<td/>");
		$td.addClass("contactlabel");
		$tr.append($td);
		var $label=$("<label/>");
		$label.html(label);
		$td.append($label);
		$td=$("<td/>");
		$tr.append($td);
		var $text=$("<textarea/>", textprops);
		$td.append($text);
		return $tr;
	}

	function defaultValue($select, defaultValue) {
		options = $select[0].options;
		var i = 0;
		for (i=0;i<options.length;i++) {
			options[0].selected= true;
			if(options[i].value == defaultValue){
				options[i].selected = true;
				break;
			}
		}
		
	}
	
	var dateTransform = function(){
		if($(this).val().indexOf("-")!=-1){
				return;
		}
		
		if($(this).val().length>8){
			$(this).val($(this).val().substring(0,8));
		}
		
		if($(this).val().length==8){
			$(this).val($(this).val().substring(0,4)+"-"+$(this).val().substring(4,6)+"-"+$(this).val().substring(6,8));	
		}
		
	};
	
	var dateTransformByMonth = function(){
		if($(this).val().indexOf("/")!=-1){
			return;
		}
		if($(this).val().length>6){
			$(this).val($(this).val().substring(0,6));
		}
		if($(this).val().length==6){
			$(this).val($(this).val().substring(0,4)+"/"+$(this).val().substring(4,6));
			return;
		}
		if($(this).val().length>4){
			$(this).val($(this).val().substring(0,4));
		}
		if($(this).val().length==4){
			$(this).val($(this).val().substring(0,2)+"/"+$(this).val().substring(2,4));
		}
	};
	
	function createBlock(obj) {
		var $div = $("<div/>");
		$div.addClass("cfgObj");
		$div.attr("id",obj.id);
		$div.attr("title",obj.name);
		$div.attr("name",obj.seq);
		//$div.css("color",obj.color);
		//$div.css("backgroundColor",obj.bgColor);
		//$div.css("left",obj.left);
		//$div.css("top",obj.top);
		
		if(obj.name.substring(0, 5) != "FIELD"){
			var $a=$("<a>",{html:"+"});
			$a.css("cursor","default");
			$a.css("fontSize","16pt");
			$div.append($a);
			$a.click(function(){
				if (obj.path2 !='0000') {
				//if (true) {
					if ($a.hasClass("inited")) {
						
					} else {
						var cust = $("#customerName").val();
						services.call("customer.listBarCfgSub",[cust,obj.id],afListSub);
						$a.addClass("inited");
					}
				}				
				$("#sub"+obj.id).toggle();
				if($a.html()=="+"){
					$a.html("-");
				} else {
					$a.html("+");
				}
			});
		}
		$div.append(obj.name+":");
		var $input = $("<input>",{"type":"text", val: obj.text});
		$input.addClass("disable");
		$input.attr("readonly",true);
		$div.append($input);
		
		if(obj.path2!='0000'){// not company level
			$input.dblclick(function(){
				$(this).removeClass("disable");
				$input.addClass("enable");
				$input.attr("readonly",false);
			});
			
			$input.blur(function(){
				$(this).removeClass("enable");
				$input.addClass("disable");
				$input.attr("readonly",true);
				services.call("customer.updateBarCfg",[obj.id,$(this).val()],afUpd);
				
			});
		}
		
		if(obj.name.substring(0, 5) != "FIELD"){
			//btn +
			var $a1=$("<a>",{html:"+"});
			$a1.css("cursor","default");
			$a1.css("fontSize","16pt");
			$a1.click(createObject);
			$a1.attr("title", obj.id);
	
			$div.append($a1);
		}
		if(obj.path2!='0000'){// not company level
			// btn-
			var $a2=$("<a>",{html:"-"});
			$a2.css("cursor","default");
			$a2.css("fontSize","16pt");
			$a2.attr("title", obj.id);
			$a2.click(function(){
				var isOk=confirm("Delete?");
				if(isOk){
					var id=$(this).attr("title");
					services.call("customer.deleteBarCfg",[id],afDel);
				}
			});
			$div.append($a2);
		}
		
		var p = obj.path;
		if($("#sub"+p).length==0){
			$("#sub1").append($div);	
		}else{
			$("#sub"+p).append($div);
		}		

		var $div2 = $("<div/>");
		$div2.attr("id","sub"+obj.id);
		$div2.attr("title",obj.seq + 1);
		$div2.attr("name",obj.path2+"-"+obj.id);
		$div2.css("paddingLeft","40px");
		$div.append($div2);
		$div2.hide();
	
		return $div;
	}
	
	function afListSub(list) {
		var i = 0;
		for(i=0; i<list.length; i++) {
			//alert(i);
			afCreateCfg(list[i]);			
		}
	}
	
	function afUpd(obj){
		
	}
	var createObject = function() {
	/*
		if($(".selected").length == 0 || $(".mouseover").length == 1) {
			return;
		}
		
		var tagName = $(".selected").attr("title");
		if(tagName.substring(0, 5) == "FIELD"){
			return;
		}
	*/
	
		var  id = $(this).attr("title");
		var seq = $(this).parent().attr("name");
		var path2 = $("#sub"+id).attr("name");
		
		var tagName=$("#"+id).attr("title");
		var $div = $("<div/>",{id:"newCfgdialog"});
		$("BODY").append($div);
		$div.addClass("newObjDialog");
		$div.css("left",window.event.x);
		$div.css("top",window.event.y);
		
		$div.append($("<label/>",{html:"Name:"}));
		$div.append($("<br/>"));
		
		if(tagName.substring(0, 5) == "BAR"){
			var $name = $("<select/>",{id:"name"});
			services.call("common.listBarConfigField", ['FIELD'], aflsfield2);
			
			$name.addClass("objTxt");
			$div.append($name);
			$div.append($("<br/>"));
		
			$div.append($("<label/>",{html:"Display:"}));
			$div.append($("<br/>"));
			var $name = $("<input/>",{id:"display",size:20,maxLength:35});
			$name.addClass("objTxt");
			$div.append($name);
			$div.append($("<br/>"));
		} else {
			var $name = $("<select/>",{id:"name"});
			services.call("common.listBarConfigField", ['BAR'], aflsfield1);
			//$name.append($("<option/>",{val:"LOCATION",html:"LOCATION"}));
			//$name.append($("<option/>",{val:"BAR",html:"BAR"}));
			
			$name.addClass("objTxt");
			$div.append($name);
			$div.append($("<br/>"));
		
			var $name = $("<input/>",{id:"display",type:"hidden",size:20,maxLength:35});
			$name.addClass("objTxt");
			$div.append($name);
		}
		
		$div.append($("<label/>",{html:"Value:"}));
		$div.append($("<br/>"));
		$name = $("<textarea/>",{id:"value", rows:5,cols:30});
		$name.addClass("objTxt");
		$div.append($name);
		$div.append($("<br/>"));
		

		/*
		$div.append($("<label/>",{html:"Color:"}));
		var $sel = $("<select/>",{id:"color"});
		$sel.addClass("objSel");
		
		var $opt=$("<option/>",{value:"black"});
		$opt.css("backgroundColor","black");
		$sel.append($opt);
		$opt=$("<option/>",{value:"blue"});
		$opt.css("backgroundColor","blue");
		$sel.append($opt);
		$opt=$("<option/>",{value:"green"});
		$opt.css("backgroundColor","green");
		$sel.append($opt);
		$opt=$("<option/>",{value:"white"});
		$opt.css("backgroundColor","white");
		$sel.append($opt);
		
		$div.append($sel);
		
		*/
	
		$div.append($("<br/>"));
		var $btn = $("<input/>",{ type:"button", value:" OK "});
		$div.append($btn);
		
		$btn.click(function() {
			var $sdiv=$("<div/>");
			var parent = id;
			var company = $("#company").val();
			
			var name = $("#name option[selected=true]").val();
			var tagName = $("#"+id).attr("title");
			
			if(tagName.toUpperCase()=="BAR") {
				name = "FIELD-" + name;
			}
			
			services.call("customer.createBarCfg",[name, $("#value").val(), 
				$( "#newCfgdialog" ).css("left"),$( "#newCfgdialog" ).css("top"),
				parent, path2, seq, company,$("#display").val()]
					,afCreateCfg);
			//$( document ).click(docOnclick);
			$( "#newCfgdialog" ).remove();
		});
		
		var $btn2 = $("<input/>",{ type:"button", value:"Cancel"});
		$div.append($btn2);
		$btn2.click(function() {
			$( "#newCfgdialog" ).remove();
		});
		
	};
	
	function afCreateCfg(obj) {
		parentid = obj.path;
		obj = createBlock(obj);
		obj.addClass("unselected");
		obj.click(function(){
			if($(this).hasClass("selected")){
				if($(".newObjDialog").length > 0)
					return;
				$(".selected").addClass("unselected");
				$(".selected").removeClass("selected");
			}else {
				if($(".newObjDialog").length > 0)
					return;
				$(".selected").addClass("unselected");
				$(".selected").removeClass("selected");
				$(this).removeClass("unselected");
				$(this).addClass("selected");
			}
		});
		obj.mouseover(function(){
			$(this).addClass("mouseover");
		});
		
		obj.mouseout(function(){
			$(this).removeClass("mouseover");
		});
				
		var pleft = $("#"+parentid).css("left");
		pleft=pleft.substring(0, pleft.length - 2);
		var ptop = $("#"+parentid).css("top");
		ptop=ptop.substring(0, ptop.length - 2);
		var pwidth = $("#"+parentid).css("width");
		pwidth=pwidth.substring(0, pwidth.length - 2);
		var pheight = $("#"+parentid).css("height");
		pheight=pheight.substring(0, pheight.length - 2);
		var tleft = obj.css("left");
		tleft=tleft.substring(0, tleft.length - 2);
		var ttop = obj.css("top");
		ttop=ttop.substring(0, ttop.length - 2);
		var twidth = obj.css("width");
		twidth=twidth.substring(0, twidth.length - 2);
		var theight = obj.css("height");
		theight=theight.substring(0, theight.length - 2);
		rx=tleft<pleft?tleft:pleft;
		ry=ttop<ptop?ttop:ptop;
		createLine(pleft, ptop, tleft, ttop, rx, ry);
	}
	
	function afDel(obj) {
		$("#"+obj.id).remove();
	}
	
	function createLine(x1,y1,x2,y2,rx,ry) {
		var p1="<v:stroke dashstyle='Dot' EndArrow='Classic'/>";
		var props=document.createElement(p1);
		var l1="<v:line from=\"" + x1 + "," + y1 + "\" to=\"" + x2 + "," + y2 + "\" style=\"position:absolute;left:" + rx + "px;top:" + ry + "px\"></v:line>";
		var line = document.createElement(l1);
		line.insertBefore(props);
		document.body.insertBefore(line);
	}
	
	function lsCfg(companyName) {
		services.call("customer.listBarCfg",[companyName],afLsCfg);
	}
	
	function lsCfgMain(companyName,id) {
		services.call("customer.listBarCfgMain",[companyName,id],afLsCfg);
	}
	
	function afLsCfg(list) {
		var dis = list.length;
		if (list.length > 500) {
			//dis = 500;
		}
		var i = 0;
		for(i=0; i<dis; i++) {
			afCreateCfg(list[i]);			
		}
	}
	
	function transport(companyName) {
		services.call("customer.transportBarCfg",[companyName],afTs);
	}
	
	function afTs(){
		alert("Configuration has been transported, it will take a few minutes to take effect.");
	}
	
	function aflsfield1(list) {
		for(var i=0; i<list.length;i++){
			$("#name").append($("<option/>",{val:list[i].key,html:list[i].value}));
		}
	}
	
	function aflsfield2(list) {
		for(var i=0; i<list.length;i++){
			$("#name").append($("<option/>",{val:list[i].key,html:list[i].value}));
		}
	}
	