<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<table width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td class="loginform" valign="top">
			<div>${custname_message }</div>

			<table cellpadding="4" cellspacing="4">
				<tr>
					<td class="label_bold">Gmax No:</td>
					<td>
					
					<select name="company.gmaxNo" id="gmaxes">
							<option value="-1">Please select ...</option>
					
							<c:forEach var="gmax" items="${customer.gmaxes }">
								<option value="${gmax }" ${gmax==company.gmaxNo?"selected":"" }>
									${gmax}
								</option>
							</c:forEach>
					
					</select>
					
					</td>
					<td class="label_bold">
						Company Name:
					</td>
					<td>
					<input type="text" name="company.companyName"
							value="${company.companyName }" id="company-companyName" class="validator"><!--  readonly="readonly" style="background-color: #eee" -->
					</td>
				</tr>

				<tr>
				
					<td class="label_bold">Company no:</td>
					<td><input type="text" name="company.companyNo"
						value="${company.companyNo }" id="companyNo" readonly="true" style="background-color: #EEE"></td>
						
					<td class="label_bold">Company alias:</td>
					<td>
						<input type="text" name="company.alias"
							value="${company.alias }" id="company-alias" class="validator">
					
				</td>
					
				</tr>


				<tr>
					<td class="label_bold">Sales:</td>
					<td><input type="text" name="company.sales"
						value="${company.sales }"></td>
					<td class="label_bold">Cost Center:</td>
					<td>
						<input type="text" value="" class="validator" name="company.costcenter" id="company_costcenter">
					</td>
				</tr>

				<tr>
					<td class="label_bold">Security Group:</td>
					<td><select name="company.securityGroup" id="sg">
							<option value="-1">Please select ...</option>
					</select></td>
					<td class="label_bold">Customer Type:</td>
					<td><select name="company.custType" id="ccc">
							<option value="-1">Please select ...</option>
							<option value="Agent" ${'Agent'==company.custType?"selected":"" }>Agent</option>
							<option value="FIT" ${'FIT'==company.custType?"selected":"" }>FIT</option>
							<option value="Commercial"
								${'Commercial'==company.custType?"selected":"" }>Commercial</option>
					</select></td>
				</tr>

				<tr>
					<td colspan="4" height="2"
						style="background-color: #837492"></td>
				</tr>
				<tr>

					<td class="label_bold">Business Request No:</td>
					<td><input type="text" name="company.businessReqNo"  class="validator"
						value="${company.businessReqNo }"></td>
					<td class="label_bold">Internet Address:</td>
					<td><input type="text"
						name="company.internetAddress"  class="validator"
						value="${company.internetAddress }"></td>
				</tr>

				<tr>

					<td class="label_bold">Insurance No:</td>
					<td><input type="text" name="company.insuranceNo" class="validator"
						value="${company.insuranceNo }"></td>
					<td class="label_bold">License No:</td>
					<td><input type="text" name="company.licenseNo" class="validator"
						value="${company.licenseNo }"></td>
				</tr>

				<tr>
					<td class="label_bold">AC Open Date:</td>
					<td><input type="text" id="datepicker1"
						name="company.acOpenDate" class="validator"
						value="${company.acOpenDateStr }"></td>
					<td class="label_bold">AC Close Date:</td>
					<td><input type="text" id="datepicker2"
						name="company.acCloseDate" class="validator"
						value="${company.acCloseDateStr }"></td>
				</tr>

				<tr>
					<td class="label_bold">IATA Number:</td>
					<td><input type="text" name="company.iataNumber" class="validator"
						value="${company.iataNumber }"></td>
					<td class="label_bold">Security:</td>
					<td><input type="text" name="company.security" class="validator"
						value="${company.security }"></td>
				</tr>
				<tr>
					<td colspan="4" height="2"
						style="background-color: #837492"></td>
				</tr>
				
				<tr>
					<td colspan="4" align="left">
						<table width="500">
							<tr>
								<td valign="top">
									<div>
										<input type="checkbox" class="flag_chk"	name="company.reqStmt" value="${company.reqStmt}"   />Request Statement.
									</div>
									<div>
										<input type="checkbox" class="flag_chk"	name="company.feeBased" value="${company.feeBased}" />Fee Based.
									</div>
									<div>
										<input type="checkbox" class="flag_chk" name="company.intAirplus" value="${company.intAirplus}" />Int Airplus.
									</div>
									<div>
										<input type="checkbox" class="flag_chk"	name="company.tcProfile" value="${company.tcProfile }"/> T/C Profile
									</div>
								</td>
								<td valign="top">
									<div>
										<input type="checkbox" class="flag_chk" id="company_ebilling" name="company.ebilling" value="${company.ebilling}" />E-Billing.
										<input type="hidden" id="company_ebilling_pre" name="company.ebillingPrevious" value="${company.ebilling}" />
									</div><div>
										<input type="checkbox" class="flag_chk"	name="company.lowestFareReasonCode" value="${company.lowestFareReasonCode}" />Lowest Fare Reason Code.
									</div><div>
										<input type="checkbox" class="flag_chk" name="company.purposeCode" value="${company.purposeCode}" />Purpose Code.
									</div>
								</td>
							</tr>
						</table>	
					</td>
				</tr>
			</table>
			
			<table width="500">
				<tr>
					<td valign="top">
						<div style="background-color:#458388;color:white">Booking Level</div>
						<div><input type="checkbox" class="flag_chk" name="company.tripDate" value="${company.tripDate }"/>Trip Date</div>
						<div><input type="checkbox" class="flag_chk" name="company.dateOfBooking" value="${company.dateOfBooking }"/>Date of Booking</div>
						<div><input type="checkbox" class="flag_chk" name="company.customerRefernce" value="${company.customerRefernce }"/>Customer Reference(CR)</div>
						<div><input type="checkbox" class="flag_chk" name="company.tripRequistition" value="${company.tripRequistition }"/>Trip Requisition(TR)</div>
						<div><input type="checkbox" class="flag_chk" name="company.jobNumber" value="${company.jobNumber }"/>Job Number</div>
						<div><input type="checkbox" class="flag_chk" name="company.projectNumber" value="${company.projectNumber }"/>Project Number</div>
						<div><input type="checkbox" class="flag_chk" name="company.accountNumber" value="${company.accountNumber }"/>Account Number</div>
						<div><input type="checkbox" class="flag_chk" name="company.jobTitle" value="${company.jobTitle }"/>Job Title</div>
					</td>
					
					<td valign="top">
						<div style="background-color:#458388;color:white">Item Level</div>
						<div><input type="checkbox" class="flag_chk" name="company.fareType" value="${company.fareType }"/>Fare Type</div>
						<div><input type="checkbox" class="flag_chk" name="company.shortLongHaul" value="${company.shortLongHaul }"/>Short/Long Haul</div>
						<div><input type="checkbox" class="flag_chk" name="company.destination" value="${company.destination }"/>Destination</div>
						<div><input type="checkbox" class="flag_chk" name="company.iataFare" value="${company.iataFare }"/>IATA Fare</div>
					</td>
				</tr>
			</table>
			<div class="label_bold">TMR Guide Line:</div>
			<textarea id="company_tmr_remark" name="company.tmrRemark" rows="6" cols="70">${company.tmrRemark }</textarea>
		</td>
	</tr>
</table>