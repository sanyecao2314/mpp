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

	var call_contact_service_create = function(contact){
		services.call(CONTACT_CREATE, [contact.firstname,
                                       contact.lastname,
                                       contact.firstname_en,
                                       contact.lastname_en,
                                       contact.homephone,
                                       contact.officephone,
                                       contact.title,
                                       contact.faxno,
                                       contact.email,
                                       contact.mobile,
                                       compId], afCreateContact);
	};
	var call_contact_service_update = function(contact){
		services.call(CONTACT_UPDATE, [contact.id,
                                   $("#_firstname_"+contact.id).val(),
                                   $("#_lastname_"+contact.id).val(),
                                   $("#_firstname_en_"+contact.id).val(),
                                   $("#_lastname_en_"+contact.id).val(),
                                   $("#_homephone_"+contact.id).val(),
                                   $("#_officephone_"+contact.id).val(),
                                   $("#_title_"+contact.id).val(),
                                   $("#_faxno_"+contact.id).val(),
                                   $("#_email_"+contact.id).val(),
                                   $("#_moible_"+contact.id).val()
                                   ], afUpdate);
	
	};
	var init_product_dialog_flag1 = false;
	
	var OK_FUNC1 = function(){
		$("#statusshower").show();
		var contact = getContact();
		validate2("create",contact);
	};
	
	var CANCEL_FUNC1 = function(){
		$("#contact-form").dialog("close");
		$("#statusshower").hide();
	};
	
	var initContact = function(){
		$("#statusshower").show();
		services.call(CONTACT_LIST, [compId], afListContact);
	};
	
	var afCreateContact = function(obj){
		if(!obj.success) {
			alert("some errors occur while processing request, contact.");
			return;
		}
		addContact(obj.data);
		$("#statusshower").hide();
	};
	
	$(function() {
		if(!init_product_dialog_flag1){
			$("#contact-form").dialog({
				resizable : true,
				width : 500,
				height : 600,
				modal : true,
				buttons : {
					"OK" : function(){
						OK_FUNC1();
						RESET_CONTACT();
					},
					Cancel : function() {
						CANCEL_FUNC1();
						RESET_CONTACT();
					}
				}
			});
			init_product_dialog_flag1 = true;
			$("#contact-form").dialog("close");
		}
	});
	var RESET_CONTACT = function(){
		$("#firstname").val("");
		$("#lastname").val("");
		$("#firstname_en").val("");
		$("#lastname_en").val("");
		$("#title").val("");
		$("#homephone").val("");
		$("#officephone").val("");
		$("#faxno").val("");
		$("#email").val("");
		$("#mobile").val("");
	};
	function afDeleteContact(obj){
		if(!obj.success) {
			alert("some errors occur while processing request, contact");
			return;
		}
		$("#cc_"+obj.data.id).remove();
	}
	var afListContact = function(obj){
		if(!obj.success) {
			alert("some errors occur while processing request, contact");
			return;
		}
		var list = obj.data;
		for(var i=0;i<list.length;i++){
			addContact( list[i] );
		}
		$("#statusshower").hide();
	};
	var getContact = function() {
		var contact = {
				firstname : $("#firstname").val(),
				lastname : $("#lastname").val(),
				firstname_en : $("#firstname_en").val(),
				lastname_en : $("#lastname_en").val(),
				title:$("#title").val(),
				homephone:$("#homephone").val(),
				officephone:$("#officephone").val(),
				faxno:$("#faxno").val(),
				email:$("#email").val(),
				mobile:$("#mobile").val()
			};
		return contact;
	};
	
	var addContact = function(contact){
		
		var $cdiv=$("<div/>");
		$cdiv.attr("id","cc_" + contact.id);
		$("#contactPool").append($cdiv);
				
		var $label = $("<div/>");
		$label.addClass("contact");
		$label.attr("id", contact.id);
		$cdiv.append($label);
		$label.html("Contact " + $(".contact").length);
		$label.append($("<br/>"));
		$label.append(contact.lastname + " " + contact.firstname);
		
		$label.toggle(
				
				function() {
					$("#ci_"+contact.id).show(1000);
				},
				function() {
					$("#ci_"+contact.id).hide(1000);
				}

		);

		var $div=$("<div/>");
		$div.attr("id","ci_"+contact.id);
		$cdiv.append($div);
		
		var $table=$("<table/>");
		
		$table.append(createRow("Last Name",{type:"text",id:"_lastname_"+contact.id,value:contact.lastname}));
		$table.append(createRow("First Name",{type:"text",id:"_firstname_"+contact.id,value:contact.firstname}));
		$table.append(createRow("Last Name(EN)",{type:"text",id:"_lastname_en_"+contact.id,value:contact.lastname_en}));
		$table.append(createRow("First Name(EN)",{type:"text",id:"_firstname_en_"+contact.id,value:contact.firstname_en}));
		$table.append(createRow("Title",{type:"text",id:"_title_"+contact.id,value:contact.title}));
		$table.append(createRow("Home Phone",{type:"text",id:"_homephone_"+contact.id,value:contact.homephone}));
		$table.append(createRow("Office Phone",{type:"text",id:"_officephone_"+contact.id,value:contact.officephone}));
		$table.append(createRow("Fax No",{type:"text",id:"_faxno_"+contact.id,value:contact.faxno}));
		$table.append(createRow("Email",{type:"text",id:"_email_"+contact.id,value:contact.email}));
		$table.append(createRow("Mobile",{type:"text",id:"_moible_"+contact.id,value:contact.mobile}));
		
		$div.append($table);
		
		$("#_lastname_"+contact.id).addClass('validator_sub');
		$("#_firstname_"+contact.id).addClass('validator_sub');
		$("#_lastname_en_"+contact.id).addClass('validator_sub');
		$("#_firstname_en_"+contact.id).addClass('validator_sub');
		$("#_title_"+contact.id).addClass('validator_sub');
		$("#_homephone_"+contact.id).addClass('validator_sub');
		$("#_officephone_"+contact.id).addClass('validator_sub');
		$("#_faxno_"+contact.id).addClass('validator_sub');
		$("#_email_"+contact.id).addClass('validator_sub');
		$("#_moible_"+contact.id).addClass('validator_sub');
		
		var $btn=$("<input/>",{type:"button",value:"Save"});
		$btn.addClass("btn");
		$btn.click(function(){
			$("#statusshower").show(1000);
			validate3("update",contact);
		});
		$div.append($btn);
		
		var $btn=$("<input/>",{type:"button",value:"delete"});
		$btn.addClass("btn");
		$div.append($btn);
		$btn.click(function(){
			services.call(CONTACT_DEL, [contact.id], afDeleteContact);
		});
		
		$div.addClass("c_div");
	    $div.hide();

		$("#contact-form").dialog("close");		
	};
</script>
<div id="contact-form" title="Form - Contact" style="display:none">
	<div id="trav2"><div id="comp2">
		<div id="product-form_message"> 
			<input type="hidden" id="contact-id" name="contact.id">
			<table>
				
				<tr>
					<td>Last Name</td>
				</tr>
				<tr>
					<td>
						<input type="text" id="lastname" class="validator" />
					</td>
				</tr>
				<tr>
					<td>First Name</td>
				</tr>
				<tr>
					<td>
						<input type="text" id="firstname" class="validator" />
					</td>
				</tr>
				
				<tr>
					<td>Last Name(EN)</td>
				</tr>
				<tr>
					<td>
						<input type="text" id="lastname_en" class="validator" />
					</td>
				</tr>
				<tr>
					<td>First Name(EN)</td>
				</tr>
				<tr>
					<td>
						<input type="text" id="firstname_en" class="validator" />
					</td>
				</tr>
				<tr>
					<td>Title</td>
				</tr>
				<tr>
					<td>
						<input type="text" id="title"  class="validator"/>
					</td>
				</tr>
				
				<tr>
					<td>Mobile</td>
				</tr>
				<tr>
					<td>
						<input type="text" id="mobile"  class="validator"/>
					</td>
				</tr>
				
				
				<tr>
					<td>Home Phone</td>
				</tr>
				<tr>
					<td>
						<input type="text" id="homephone"  class="validator"/>
					</td>
				</tr>
				
				
				<tr>
					<td>Office Phone</td>
				</tr>
				<tr>
					<td>
						<input type="text" id="officephone"  class="validator"/>
					</td>
				</tr>
				
				<tr>
					<td>Fax No</td>
				</tr>
				<tr>
					<td>
						<input type="text" id="faxno"  class="validator"/>
					</td>
				</tr>
				
				<tr>
					<td>Email</td>
				</tr>
				<tr>
					<td>
						<input type="text" id="email"  class="validator"/>
					</td>
				</tr>
			</table>
		
		</div>
	</div></div>
</div>




