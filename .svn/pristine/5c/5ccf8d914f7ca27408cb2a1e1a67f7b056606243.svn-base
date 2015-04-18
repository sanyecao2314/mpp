<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

	<script type="text/javascript">
	
	var oriFname=$('#traveler-firstname').val();
	var oriLname=$('#traveler-lastname').val();
	
	$(function(){
		
		$("#traveler-icidNo").blur(function(){
			var x = -1;
			if($(this).val().length == 15){
				x = $(this).val().substring(14,15);
			}
			
			if($(this).val().length == 18){
				x = $(this).val().substring(16,17);
			}

			if(x!=-1) {
				if(x%2==0) {
					$("#traveler_gender_male")[0].checked=false;
					$("#traveler_gender_female")[0].checked=true;
				} else {
					$("#traveler_gender_male")[0].checked=true;
					$("#traveler_gender_female")[0].checked=false;
				}
			}
			
		});
		
		$( "#traveler-birthday" ).datepicker({dateFormat: 'yy-mm-dd'} );
		$( "#traveler-exitPermitExpDate" ).datepicker({dateFormat: 'yy-mm-dd'} );
		$( "#traveler-issueDate1" ).datepicker({dateFormat: 'yy-mm-dd'} );
		$( "#traveler-expiryDate1" ).datepicker({dateFormat: 'yy-mm-dd'} );
		$( "#traveler-issueDate2" ).datepicker({dateFormat: 'yy-mm-dd'} );
		$( "#traveler-expiryDate2" ).datepicker({dateFormat: 'yy-mm-dd'} );
		$( "#traveler-otherCardIssueDate" ).datepicker({dateFormat: 'yy-mm-dd'} );
		$( "#traveler-otherCardExpiryDate" ).datepicker({dateFormat: 'yy-mm-dd'} );
		
		$( "#traveler-birthday" ).blur(dateTransform);
		$( "#traveler-exitPermitExpDate" ).blur(dateTransform);
		$( "#traveler-issueDate1" ).blur(dateTransform);
		$( "#traveler-expiryDate1" ).blur(dateTransform);
		$( "#traveler-issueDate2" ).blur(dateTransform);
		$( "#traveler-expiryDate2" ).blur(dateTransform);
		$( "#traveler-otherCardIssueDate" ).blur(dateTransform);
		$( "#traveler-otherCardExpiryDate" ).blur(dateTransform);
		
		$("#traveler-firstname").blur(function(){
			if($(this).val()==oriFname) return;
			$(this).val($(this).val().toUpperCase());
			var reg=/[\u4E00-\u9FA5]/g;
			if (reg.test($(this).val())) {//chinese
				//$("#traveler-travelerName").val($("#traveler-firstname").val()+$("#traveler-lastname").val());
			
				var firstname = $('#traveler-firstname').val();
				var nationality1 = $("#traveler-nationality1").val();
				var pyfirstname = "";
				
				for(i = 0; i < firstname.length; i++) {

					$('#traveler-firstnameEn').val(firstname.charAt(i));
					pyfirstname += $('#traveler-firstnameEn').toPinyin();

					if(i < firstname.length - 1){
						pyfirstname += ";";
					}
				}
				services.call("common.findTravelerEnname", [nationality1, pyfirstname.toUpperCase()], afFindPp2);
				
				// to upper case the pinyin string
				//$("#traveler-firstnameEn").val(pyfirstname.toUpperCase());
				//alert(pyfirstname);
				//services.call("common.findPolyphone2", [firstname, pyfirstname.toUpperCase()], afFindPp2);
				
				
			} else {
				$('#traveler-firstnameEn').val($('#traveler-firstname').val().toUpperCase());
			}
			$('#traveler-travelerName').val($("#traveler-lastname").val()+" "+$("#traveler-firstname").val());
			if($('#traveler-travelerName').val()==" "){
				$('#traveler-travelerName').val("");
			}
			oriFname=$(this).val();
		});
		
		$("#traveler-firstnameEn").blur(function(){
			$(this).val($(this).val().toUpperCase());
			$('#traveler-travelerName').val($("#traveler-lastname").val()+" "+$("#traveler-firstname").val());
			if($('#traveler-travelerName').val()==" "){
				$('#traveler-travelerName').val("");
			}
			if($(this).val().indexOf("/")!=-1 || $(this).val()=="" || $(this).val()==$("#traveler-firstname").val()){
				return;
			}
			
			var a = $("#traveler-firstname").val();
			var b = $(this).val();
			var bs = b.split(" ");
			var firstname="";
			if(bs.length==a.length){
				for(i = 0; i < bs.length; i++){
					firstname+=bs[i];
					if(i < bs.length - 1){
						firstname += ";";
					}
				}
				services.call("common.findPolyphone2", [$("#traveler-firstname").val(), firstname]);	
			}
			
			
		});
		
		$("#traveler-lastnameEn").blur(function(){
			$(this).val($(this).val().toUpperCase());
			$('#traveler-travelerName').val($("#traveler-lastname").val()+" "+$("#traveler-firstname").val());
			if($('#traveler-travelerName').val()==" "){
				$('#traveler-travelerName').val("");
			}
			if($(this).val().indexOf("/")!=-1 || $(this).val()=="" || $(this).val()==$("#traveler-lastname").val()){
				return;                   
			}
			services.call("common.findPolyphone1", [$("#traveler-lastname").val(), $(this).val()]);
		});
		
		$("#traveler-lastname").blur(function(){
			if($(this).val()==oriLname) return;
			$(this).val($(this).val().toUpperCase());
			var reg=/[\u4E00-\u9FA5]/g;
			if (reg.test($(this).val())) {//chinese
			
				var lastname = $('#traveler-lastname').val();
				var pyLastname = "";
				for(i = 0;i<lastname.length;i++){
					$('#traveler-lastnameEn').val(lastname.charAt(i));
					pyLastname += $('#traveler-lastnameEn').toPinyin();
					if(i < lastname.length-1) {
						pyLastname += "";
					}
				}
				
				services.call("common.findPolyphone1", [lastname, pyLastname.toUpperCase()], afFindPp);
				
			} else {
				$('#traveler-lastnameEn').val($('#traveler-lastname').val().toUpperCase());
			}
			
			$('#traveler-travelerName').val($("#traveler-lastname").val()+" "+$("#traveler-firstname").val());
			if($('#traveler-travelerName').val()==" "){
				$('#traveler-travelerName').val("");
			}
			oriLname = $(this).val();
		});
		
		function afFindPp(obj){
			$('#traveler-lastnameEn').val(obj);
		}
		
		function afFindPp2(obj){
			$('#traveler-firstnameEn').val(obj);
		}
		
		/*
		$("#traveler-travelerName").blur(function(){
			var reg=/[\u4E00-\u9FA5]/g;
			if (reg.test($(this).val())) {//chinese
				if($(this).val().length >= 4) {
					$("#traveler-firstname").val($(this).val().substring(2,$(this).val().length));
					$("#traveler-lastname").val($(this).val().substring(0,2));
				} else {
					$("#traveler-firstname").val($(this).val().substring(1,$(this).val().length));
					$("#traveler-lastname").val($(this).val().substring(0,1));						
				}
				$('#traveler-firstnameEn').val($('#traveler-firstname').toPinyin());
				$('#traveler-lastnameEn').val($('#traveler-lastname').toPinyin());
			}else {
				$("#traveler-firstname").val($(this).val().substring($(this).val().lastIndexOf(" ") + 1));
				$("#traveler-lastname").val($(this).val().substring(0,$(this).val().lastIndexOf(" ")));
				
				$('#traveler-firstnameEn').val($('#traveler-firstname').val());
				$('#traveler-lastnameEn').val($('#traveler-lastname').val());
			}
		});
*/
		defaultValue($("#traveler-custType"),"${traveler.custType}");
		defaultValue($("#traveler-maritalStatus"),"${traveler.maritalStatus}");
		//defaultValue($("#traveler-department2"),"${traveler.department2}");
	});
	
	$(function(){
		services.call("common.listPassportType", [], fillPassport);
	});
	
	function fillPassport(codes) {
	
		$option=$("<option/>",{value:'', html:'Please select...'});
		$("#traveler-otherCardType").append($option);
		
		for(i = 0; i < codes.length; i++) {
			$option=$("<option/>",{value:codes[i].key, html:codes[i].value});
			$("#traveler-otherCardType").append($option);
/* 			$option=$("<option/>",{value:codes[i].key, html:codes[i].value});
			$("#traveler-passport1Type").append($option);
			$option=$("<option/>",{value:codes[i].key, html:codes[i].value});
			$("#traveler-passport2Type").append($option); */
		}
		
		defaultValue($("#traveler-otherCardType"),"${traveler.otherCardType}");
		//defaultValue($("#traveler-passport2Type"),"${traveler.passport2Type}");
	}
	
	$(function(){
		unique();
		validate();
		$("#btnGuide").toggle(function() {
			$("#company_tmrRemark").show();
		},function() {
			$("#company_tmrRemark").hide();
		});
	
		$(".unique").change(function() {
			unique();
		});
	});
	
	function unique(){
		var id='${ traveler.id }';
		var travelerName=$("#traveler-travelerName").val();
		var email=$("#traveler-email").val();
		var icid=$("#traveler-icidNo").val();
		var passport1=$("#traveler-passport1").val();
		var passport2=$("#traveler-passport2").val();
		var status=$("#traveler_status").val();
		var travelerNo=$("#traveler-travelerNo").val();
		
		$("#traveler-unique").val("id=" + id + ";travelerName=" + travelerName + ";email="
				+ email + ";icidNo=" + icid + ";passport1="
				+ passport1 + ";passport2=" + passport2 + ";status=" + status + ";travelerNo=" + travelerNo +";");
	}
	
	</script>


<div id="company_tmrRemark" style="display:none;left: 530px;top:380px;position: absolute;">
	<textarea style="border: 1px #ccc solid;background-color: #eee" cols="70" rows="6" readonly="readonly">${company.tmrRemark}</textarea>
</div>
								
<table width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td class="loginform" valign="top">
			<div>${custname_message }</div>
			
			<input type="hidden" value="${traveler.travelerType }" name="traveler.travelerType" id="traveler_travelerType"/>
			
			<input type="hidden" id="traveler_travelerRelation" name="traveler.travelerRelation" value="${traveler.travelerRelation }"/>
			
			<c:if test="${traveler.travelerType!='traveler' }">
				<div class="label_bold">Relation: ${traveler.travelerRelation }</div>
			</c:if>
					
			<table cellpadding="4" cellspacing="4">
				<tr>
					<td class="label_bold">
						Traveler no:
						<c:if test="${traveler.travelerType=='traveler' }">
							<input type="button" id="btnNewBar" value="Change Bar" />
						</c:if>
					</td>
					<td>
						<input type="text" name="traveler.travelerNo" class="validator" readonly='true' style="background-color: #eee" id="traveler-travelerNo" value="${traveler.travelerNo }">
					</td>
					<td class="label_bold">
						Traveler Name:
						<select id="traveler_otherTitle" name="traveler.otherTitle">
							<option value="" ${traveler.otherTitle==''?"selected":"" }></option>
							<option value="DR" ${traveler.otherTitle=='DR'?"selected":"" }>DR</option>
						</select>						
					</td>
					<td>
						<input type="text" style="background-color: #EEE" readonly="true" name="traveler.travelerName" class="travelerName validator unique" id="traveler-travelerName" value="${traveler.travelerName }">
					</td>
				</tr>
							<tr>
								
								<td class="label_bold">
									Last Name:
								</td>
								<td>
									<input type="text" name="traveler.lastname" ${traveler.travelerType!='traveler'?"readonly='true' style='background-color: #EEE'":"" } class="travelerName" id="traveler-lastname" value="${traveler.lastname }">
								</td>
								<td class="label_bold">
									First Name:
								</td>
								<td>
									<input type="text" name="traveler.firstname" class="travelerName" ${traveler.travelerType!='traveler'?"readonly='true' style='background-color: #EEE'":"" } id="traveler-firstname" value="${traveler.firstname }">
								</td>
							</tr>
							
							<tr>
								<td class="label_bold">
									Last Name(E):
								</td>
								<td>
									<input type="text" name="traveler.lastnameEn" class="travelerName validator" ${traveler.travelerType!='traveler'?"readonly='true' style='background-color: #EEE'":"" } id="traveler-lastnameEn" value="${traveler.lastnameEn }">
								</td>
								<td class="label_bold">
									First Name(E):
								</td>
								<td>
									<input type="text" name="traveler.firstnameEn" class="travelerName validator" ${traveler.travelerType!='traveler'?"readonly='true' style='background-color: #EEE'":"" } id="traveler-firstnameEn" value="${traveler.firstnameEn }">
								</td>
							</tr>
							
							<tr>
								<td class="label_bold">
									Title:
								</td>
								<td>
									<input type="text" name="traveler.title" id="traveler-title" value="${traveler.title }"  class="validator">
								</td>
								<td class="label_bold">
									Mobile:
								</td>
								<td>
									<input type="text" name="traveler.mobile" id="traveler-mobile" class="validator" value="${traveler.mobile }">
								</td>
							</tr>
								
							<tr>
								<td class="label_bold">
									Office Phone:
								</td>
								<td>
									<input type="text" name="traveler.officePhone" id="traveler-officePhone" class="validator" value="${traveler.officePhone }">
								</td>
								<td class="label_bold">
									Fax No:
								</td>
								<td>
									<input type="text" name="traveler.faxNo" class="validator" id="traveler-faxNo" value="${traveler.faxNo }">
								</td>
							</tr>
							
							<tr>
								<td class="label_bold">
									Email:
								</td>
								<td>
									<input type="text" name="traveler.email" id="traveler-email" class="validator unique" value="${traveler.email }">
								</td>
								<td class="label_bold">
									Home Phone:
								</td>
								<td>
									<input type="text" name="traveler.homephone" id="traveler_homephone" class="validator" value="${traveler.homephone }">
								</td>	
							</tr>
							
							<tr>
								<td class="label_bold">
									Gender:
								</td>
								<td>
									<input type="radio" name="traveler.gender" id="traveler_gender_male" value="M" ${(traveler.gender=='M')?"checked":"" }>Male
									<input type="radio" name="traveler.gender" id="traveler_gender_female" value="F" ${traveler.gender=='F'?"checked":"" }>Female
								</td>							
								<td class="label_bold">
									Customer Type:
								</td>
								<td>
									<select name="traveler.custType" id="traveler-custType">
											<option value="Commercial">Commercial</option>
											<option value="Agent">Agent</option>
											<option value="FIT">FIT</option>
									</select>
								</td>
								
							</tr>
							
							<tr>
								<td class="label_bold">
									Marital Status:
								</td>
								<td>
									<select name="traveler.maritalStatus" id="traveler-maritalStatus">
										<option value="">Please select ...</option>
										<option value="0">Unmarried</option>
										<option value="1">Married</option>
									</select>
								</td>
								<td class="label_bold">
									Alias:
								</td>
								<td>
									<input type="text" name="traveler.alias" class="validator" id="traveler-alias" value="${traveler.alias }" />
								</td>
							</tr>
							
							<tr>
								
								<td class="label_bold">
									ICID NO:
								</td>
								<td>
									<input type="text" name="traveler.icidNo" id="traveler-icidNo" class="validator unique" value="${traveler.icidNo }">
								</td>
								<td class="label_bold">
									Insurance No:
								</td>
								<td>
									<input type="text" name="traveler.insuranceNo" class="validator" id="traveler-insuranceNo" value="${traveler.insuranceNo }">
								</td>
							</tr>
							
							<tr>
								<td class="label_bold">
									Country:
								</td>
								<td>
									<input type="text" name="traveler.country" class="validator" value="${traveler.country }" id="traveler-country">
								</td>
								<td class="label_bold">
									Internet:
								</td>
								<td>
									<input type="text" name="traveler.internet" class="validator" id="traveler-internet" value="${traveler.internet }">
								</td>	
							</tr>
							<tr>
								<td colspan="4" height="2" style="background-color:#837492">
								</td>
							</tr>
							<tr>
								<td class="label_bold">
									Nationality1:
								</td>
								<td>
									<input type="text" name="traveler.nationality1" value="${traveler.nationality1 }" class="validator" id="traveler-nationality1">
								</td>
								<td class="label_bold">
									Nationality2:
								</td>
								<td>
									<input type="text" name="traveler.nationality2" value="${traveler.nationality2 }" class="validator" id="traveler-nationality2">
								</td>
							</tr>
							
							<tr>
								<td class="label_bold">
									Birthday:
								</td>
								<td>
									<input type="text" name="traveler.birthday" id="traveler-birthday" class="validator" value="${traveler.birthdayStr }">
								</td>
								<td class="label_bold">
									Security:
								</td>
								<td>
									<input type="text" name="traveler.security" id="traveler-security" class="validator" value="${traveler.security }">
								</td>
							</tr>
	
							<tr>
								<td class="label_bold">
									
									<div id="jt_fieldDiv" >
										<input type="text" name="fieldnames" id="traveler-jt-desc" value="Job Title:" readonly="true" class="fielddesc_inact" />
										<span id="jt_fieldDivP" class="field_check">P</span>
										<span id="jt_fieldDivB" class="field_check">B</span>
										<span id="jt_fieldDivG" class="field_uncheck">G</span>
									</div>
									
								</td>
								<td>
									<input type="text" name="traveler.jobTitle" id="traveler-jobTitle" class="validator" value="${traveler.jobTitle }">
								</td>
								<td class="label_bold">

									<div id="emp_fieldDiv" >
										<input type="text" name="fieldnames" id="traveler-emp-desc" value="Employee No:" readonly="true" class="fielddesc_inact" />
										<span id="emp_fieldDivP"  class="field_check">P</span>
										<span id="emp_fieldDivB"  class="field_check">B</span>
										<span id="emp_fieldDivG"  class="field_uncheck">G</span>
									</div>
								</td>
								<td>
									<input type="text" name="traveler.employeeNo" id="traveler-employeeNo" class="validator" value="${traveler.employeeNo }">
								</td>
							</tr>
							<tr>
								<td class="label_bold">
									
									
									<div id="dp_fieldDiv" >
										<input type="text" name="fieldnames" id="traveler-dp-desc" value="Department1:" readonly="true" class="fielddesc_inact" />
										<span id="dp_fieldDivP"  class="field_check">P</span>
										<span id="dp_fieldDivB"  class="field_check">B</span>
										<span id="dp_fieldDivG"  class="field_uncheck">G</span>
									</div>
									
									
								</td>
								<td>
									<input type="text" name="traveler.department1" id="traveler-department1" class="validator" value="${traveler.department1 }">
								</td>
								<td class="label_bold" style="color:red">
									TMR:<input type="button" id="btnGuide" value="Guide Line" />
								</td>
								<td>
									<input type="text" value="${traveler.department2 }" class="validator" name="traveler.department2" id="traveler-department2">
									<input type="hidden" value="${traveler.department2bak }" name="traveler.department2bak" id="traveler-department2bak">
								</td>
							</tr>
							
							<tr>
								<td class="label_bold">

									<div id="cc_fieldDiv" >
										<input type="text" name="fieldnames" id="traveler-cc-desc" value="Cost Center:" readonly="true" class="fielddesc_inact" />
										<span id="cc_fieldDivP"  class="field_check">P</span>
										<span id="cc_fieldDivB"  class="field_check">B</span>
										<span id="cc_fieldDivG"  class="field_uncheck">G</span>
									</div>
								</td>
								<td>
									<input type="text" value="${traveler.costcenter }" class="validator" name="traveler.costcenter" id="traveler_costcenter">
								</td>
								<td class="label_bold">
									<div id="tr_fieldDiv" >
										<input type="text" name="fieldnames" id="traveler-tr-desc" value="TR:" readonly="true" class="fielddesc_inact" />
										<span id="tr_fieldDivP"  class="field_check">P</span>
										<span id="tr_fieldDivB"  class="field_check">B</span>
										<span id="tr_fieldDivG"  class="field_uncheck">G</span>
									</div>
									
								</td>
								<td>
									<input type="text" name="traveler.tr" id="traveler-tr" class="validator" value="${traveler.tr }">
								</td>
							</tr>
	
							<tr>
								<td class="label_bold">
									<div id="pn_fieldDiv" >
										<input type="text" name="fieldnames" id="traveler-projectNumber-desc" value="Project Number:" readonly="true" class="fielddesc_inact" />
										<span id="pn_fieldDivP"  class="field_check">P</span>
										<span id="pn_fieldDivB"  class="field_check">B</span>
										<span id="pn_fieldDivG"  class="field_uncheck">G</span>
									</div>									
								</td>
								<td>
									<input type="text" name="traveler.projectNumber" class="validator" id="traveler-projectNumber" value="${traveler.projectNumber }">
								</td>
								<td class="label_bold">
									
									
									<div id="an_fieldDiv" >
										<input type="text" name="fieldnames" id="traveler-an-desc" value="Account Number:" readonly="true" class="fielddesc_inact" />
										<span id="an_fieldDivP"  class="field_check">P</span>
										<span id="an_fieldDivB"  class="field_check">B</span>
										<span id="an_fieldDivG"  class="field_uncheck">G</span>
									</div>
									
								</td>
								<td>
									<input type="text" name="traveler.accountNumber" class="validator" id="traveler-accountNumber" value="${traveler.accountNumber }">
								</td>
							</tr>
							
							<tr>
								<td class="label_bold">
									Security Group:
								</td>
								<td>
									<input type="text" name="traveler.securityGroup" class="validator" id="traveler-securityGroup" value="${traveler.securityGroup }">
								</td>
								<td class="label_bold">
									Customer Category:
								</td>
								<td>
									<select name="traveler.custCategory" id="traveler-custCategory">
										<option value="">Please select...</option>
										<option value="A" ${traveler.custCategory=='A'?'selected':''} }>A/VIP</option>
										<option value="IB" ${traveler.custCategory=='IB'?'selected':''} }>IB/DC DR</option>
									</select>
								</td>
							</tr>

							<tr>
								<td class="label_bold">
									Exit Permit No:
								</td>
								<td>
									<input type="text" name="traveler.exitPermitNo" class="validator" id="traveler-exitPermitNo" value="${traveler.exitPermitNo }">
								</td>
								<td class="label_bold">
									Exit Permit Expiry Date:
								</td>
								<td>
									<input type="text" name="traveler.exitPermitExpDate" class="validator" id="traveler-exitPermitExpDate" value="${traveler.exitPermitExpDateStr }">
								</td>
							</tr>
							<tr>
								<td colspan="4" height="2" style="background-color:#837492">
								</td>
							</tr>
							<tr>
								<td class="label_bold">
									Passport1:
								</td>
								<td>
									<input type="text" name="traveler.passport1" class="validator unique" id="traveler-passport1" value="${traveler.passport1 }">
								</td>
								<td class="label_bold">
									
								</td>
								<td>
									
								</td>
							</tr>
							
							<tr>
								<td class="label_bold">
									IssueDate1:
								</td>
								<td>
									<input type="text" name="traveler.issueDate1" class="validator" id="traveler-issueDate1" value="${traveler.issueDate1Str }">
								</td>
								<td class="label_bold">
									ExpiryDate1
								</td>
								<td>
									<input type="text" name="traveler.expiryDate1" class="validator" id="traveler-expiryDate1" value="${traveler.expiryDate1Str }">
								</td>
							</tr>
	
							<tr>
								<td class="label_bold">
									Passport2:
								</td>
								<td>
									<input type="text" name="traveler.passport2" class="validator unique" id="traveler-passport2" value="${traveler.passport2 }">
								</td>
								<td class="label_bold">
									
								</td>
								<td>
									
								</td>
							</tr>
							<tr>
								<td class="label_bold">
									IssueDate2:
								</td>
								<td>
									<input type="text" name="traveler.issueDate2" class="validator" id="traveler-issueDate2" value="${traveler.issueDate2Str }">
								</td>
								<td class="label_bold">
									ExpiryDate2
								</td>
								<td>
									<input type="text" name="traveler.expiryDate2" class="validator" id="traveler-expiryDate2" value="${traveler.expiryDate2Str }">
								</td>
							</tr>
														<tr>
								<td class="label_bold">
									Other Card:
								</td>
								<td>
									<input type="text" name="traveler.otherCard" class="validator" id="traveler-otherCard" value="${traveler.otherCard }">
								</td>
								<td class="label_bold">
									Other Card Type:
								</td>
								<td>
									<select name="traveler.otherCardType" class="validator" id="traveler-otherCardType">
									</select>
								</td>
							</tr>
							
							<tr>
								<td class="label_bold">
									Other Card Issue Date:
								</td>
								<td>
									<input type="text" name="traveler.otherCardIssueDate" class="validator" id="traveler-otherCardIssueDate" value="${traveler.otherCardIssueDateStr }">
								</td>
								<td class="label_bold">
									Other Card Expiry Date:
								</td>
								<td>
									<input type="text" name="traveler.otherCardExpiryDate" class="validator" id="traveler-otherCardExpiryDate" value="${traveler.otherCardExpiryDateStr }">
								</td>
							</tr>
							
							<tr>
								<td colspan="4" height="2" style="background-color:#837492">
								</td>
							</tr>
							<tr>
								<td colspan="4" align="left">
									<input type="checkbox" name="traveler.reqStatement" class="validator" value="1" ${traveler.reqStatement==1?"checked":"" } />
									Request Statement.
								</td>
							</tr>
							
							<tr>
								<td colspan="4" align="left">
									<input type="checkbox" name="traveler.feeBased" class="validator" value="1" ${traveler.feeBased==1?"checked":"" } />
									Default Bill Company.
								</td>
							</tr>
							
							<tr>
								<td colspan="4" align="left">
									<input type="checkbox" name="traveler.tcProfile" class="validator" value="1" ${traveler.tcProfile==1?"checked":"" } />
									T/C Profile
								</td>
							</tr>

							<tr>
								<td colspan="4" align="left">
									<input type="hidden" id="traveler-unique" name="traveler.unique" class="validator" value="${traveler.unique }" />
								</td>
							</tr>

						</table>
			
			</td>
	</tr>
</table>