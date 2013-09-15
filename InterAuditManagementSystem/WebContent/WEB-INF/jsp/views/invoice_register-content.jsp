<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="t"      uri="/tags/tooltips-tiles" %>

<link rel="stylesheet" type="text/css" href="${ctx}/script/build/container/assets/skins/sam/container.css" />
<script type="text/javascript" src="${ctx}/script/build/utilities/utilities.js"></script>
<script type="text/javascript" src="${ctx}/script/build/container/container-min.js"></script>
<script type="text/javascript" src="${ctx}/script/build/yahoo/yahoo-min.js"></script>
<script type="text/javascript" src="${ctx}/script/build/event/event-min.js"></script>
<script type="text/javascript" src="${ctx}/script/build/connection/connection-min.js"></script>
<script type="text/javascript" src="${ctx}/script/scriptaculous/prototype.js"></script>
<script type="text/javascript" src="${ctx}/script/scriptaculous/scriptaculous.js"></script>


<script>
	var newWindow = null;
	  function cancelForm()
	  {
		  window.location="${ctx}/homePage.htm";
	  }

	function showInvoiceInfos(id){
	  if (newWindow != null){newWindow.close()}
        newWindow = window.showModalDialog('${ctx}/showInvoiceInfos.htm?customerid='+id,'top',"dialogWidth:1000px;dialogHeight:1000px");      
        newWindow.focus();
      } 

	  function removeAttachmentAction(code)
		 {
					if (confirm('Do you really want to remove this entry?'))
					{
						with(document.removeFeeForm)
			 			{
							document.forms["removeFeeForm"].elements["feecode"].value = code;
							var url = "${ctx}/showInvoiceRegisterPage.htm?customerid=${param['customerid']}&type=${invoice.type}&id=${param['id']}&command=removeFee&feecode="+code;
							action.value=url;
							submit();
		 				 }
		 	  }
		 }

	  function addPayment()
		 {
			var url = "${ctx}/showPaymentRegisterPage.htm?invoiceid=${invoice.id}";
			window.location=url;
		 }

		function createReminder()
		 {
			var url = "${ctx}/showInvoiceReminderRegisterPage.htm?invoiceid=${invoice.id}";
			window.location=url;
		 }



		 

	  

</script>


</style>


		<style type="text/css">
		#global {   margin-left: auto;   margin-right: auto;   width: 700px;   text-align: left; /* on rétablit l'alignement normal du texte */ }
		.global2 {   margin-left: auto;   margin-right: auto;   width: 300px;   text-align: left; /* on rétablit l'alignement normal du texte */ }

		.info, .success, .warning, .error, .validation {  
			border: 1px solid;  
			margin: 10px 0px;         
			padding:15px 10px 15px 60px;  
			background-repeat: no-repeat;  
			background-position: 10px center;
		}  
		.info {  
			color: #00529B;  
			background-color: #BDE5F8;  
			background-image: url('images/info.png');  
		}  
		.success {  
			color: #4F8A10;  
			background-color: #DFF2BF;  
			background-image:url('images/success2.png');  
		}  
		.warning {  
			color: #9F6000;  
			background-color: #FEEFB3;  
			background-image: url('images/warning.png');  
		}  
		.error {  
			color: #D8000C;  
			background-color: #FFBABA;  
			background-image: url('../images/error.png');  
		}  
		.validation {  
			color: #D63301;  
			background-color: #FFCCBA;  
			background-image: url('images/validation.png');  
		}

		input.button120 {
	width:120px;
	margin:1px 0 1px 0;
	background-color:rgb(241,241,237);
	font-family:tahoma;
	font-size:11px;
	border:1px solid;
	border-top-color:rgb(0,60,116);
	border-left-color:rgb(0,60,116);
	border-right-color:rgb(0,60,116);
	border-bottom-color:rgb(0,60,116);
	border-style: outset;
}
</style>

<style>
#hor-minimalist-b
{
	font-family: "Lucida Sans Unicode", "Lucida Grande", Sans-Serif;
	font-size: 12px;
	background: #fff;
	/*margin: 15px;*/
	width: 100%;
	border-collapse: collapse;
	text-align: left;
}
#hor-minimalist-b th
{
	font-size: 12px;
	font-weight: normal;
	color: #039;
	padding: 4px 3px;
	border: 1px solid #6678b1;
	/*border-top: 1px solid #6678b1;*/
}
#hor-minimalist-b td
{
	border: 1px solid #ccc;
	color: #669;
	padding: 3px 4px;
}
#hor-minimalist-b tbody tr:hover td
{
	background-color: #dddddd;
}

</style>

<h2 style="font-size: 12px;font-family: Tahoma;font-weight: bold;border-bottom:2px solid #3399CC;padding:0 0 4px 0;margin:10px 0 0 0;">
<c:choose>
						<c:when test='${invoice.type eq "AD" }'>
						 Acompte :<span  style="color:blue;font-weight: bold;"> ${invoice.reference} </span> pour  ${invoice.customerName} 
						 : <span  style="color:red;font-weight: bold;">${ fn:toUpperCase(invoice.status) }</span>
						</c:when>
						<c:when test='${invoice.type eq "CN" }'>
						 Note de credit :<span  style="color:blue;font-weight: bold;"> ${invoice.reference} </span>  pour  ${invoice.customerName}
						 : <span  style="color:red;font-weight: bold;">${ fn:toUpperCase(invoice.status) }</span> 
						</c:when>
						<c:otherwise>
						Facture finale : <span  style="color:blue;font-weight: bold;"> ${invoice.reference} </span> pour  ${invoice.customerName}
						 : <span  style="color:red;font-weight: bold;"> ${ fn:toUpperCase(invoice.status) }</span>
						</c:otherwise>
					</c:choose>
</h2>
<br/>

<div id="global">
<form  name="mainForm"  action="${ctx}/showInvoiceRegisterPage.htm" method="post" class="niceform">
<fieldset style="background-color: #eee;">
    	<table border="0" width="100%">
		<tr><td align="center">
				<span  style="color:black;font-weight: bold;">
		
		<c:choose>
						<c:when test='${invoice.type eq "AD" }'>
						 Acompte :<span  style="color:blue;font-weight: bold;"> ${invoice.reference} </span> pour  ${invoice.customerName} 
						 : <span  style="color:red;font-weight: bold;">${ fn:toUpperCase(invoice.status) }</span>
						</c:when>
						<c:when test='${invoice.type eq "CN" }'>
						 Note de credit :<span  style="color:blue;font-weight: bold;"> ${invoice.reference} </span>  pour  ${invoice.customerName}
						 : <span  style="color:red;font-weight: bold;">${ fn:toUpperCase(invoice.status) }</span> 
						</c:when>
						<c:otherwise>
						Facture finale : <span  style="color:blue;font-weight: bold;"> ${invoice.reference} </span> pour  ${invoice.customerName}
						 : <span  style="color:red;font-weight: bold;"> ${ fn:toUpperCase(invoice.status) }</span>
						</c:otherwise>
					</c:choose>
					
		</span>
			</td>
			<td>
			<t:tooltip>  
														   <t:text><em><img src="images/info-bulle.png" border="0" alt="Edit"/></em></t:text>
														  <t:help width="300" >
														   <font color="STEELBLUE"><strong>Societe :</strong></font> ${missionInfo.societe}<BR/>
														   <font color="STEELBLUE"><strong>Code :</strong></font> ${missionInfo.code}<BR/>
														   <font color="STEELBLUE"><strong> Associe: </strong></font>${missionInfo.associe}<BR/>										  
														   <font color="STEELBLUE"><strong>Manager : </strong></font>${missionInfo.manager} <BR/>
														   <font color="STEELBLUE"><strong>Heures : </strong></font>${missionInfo.hours}h<BR/>
															<font color="STEELBLUE"><strong>Budget : </strong></font>${missionInfo.budget} &euro;<BR/>
															<font color="STEELBLUE"><strong> Prix Revient: </strong></font>${missionInfo.prixRevient} &euro;<BR/>
															<font color="STEELBLUE"><strong>Prix Vente : </strong></font>${missionInfo.prixVente} &euro;<BR/>
															<font color="STEELBLUE"><strong>Depenses : </strong></font>${missionInfo.depenses} &euro;<BR/>															
															</t:help> 
												</t:tooltip>
			</td>
		</tr>
	</table> 
    </fieldset>

	<%--fieldset style="background-color: #eee;">
    	<legend>
			<span  style="color:purple;font-weight: bold;">Information</span>
		</legend>
		
		<%--dl class="global2">
				<div>
					   <label  style="color:#039;margin-left:17px;" for="delaiPaiement">Budget </label>	
						<input style="width:60px;background-color :  #ffffcc;" type="text" value="1000" readonly="readonly">
						
						<label  style="color:#039;margin-left:60px;" for="delaiPaiement">Accord</label>					
						<input style="width:60px;background-color :  #ffffcc;" type="text"  value="No" readonly="readonly">
				</div>
		</dl--%>

		<%--dl class="global2">
				<div>
					   <label  style="color:#039;" for="delaiPaiement">Prix revient</label>					
						<input style="width:60px;margin-right:30px;background-color :  #ffffcc;" type="text"  value="1500" readonly="readonly">
						
						<label  style="color:#039;" for="delaiPaiement">Prix de vente</label>					
						<input style="width:60px;background-color :  #ffffcc;" type="text"  value="2000" readonly="readonly">					
				</div>
		</dl--%>
		
		
		<%--dl class="global2">
				<div>
					   <label  style="color:#039;" for="delaiPaiement">Coûts total</label>					
						<input style="width:60px;margin-right:38px;background-color :  #ffffcc;" type="text"  value="1800" readonly="readonly">

						<label  style="color:#039;margin-left:9px;" for="delaiPaiement">Acomptes</label>					
						<input style="width:60px;background-color :  #ffffcc;" type="text"  value="1000" readonly="readonly">	
				</div>
		</dl>

		
		
		
		
	
		
	</fieldset--%>	

<fieldset style="background-color: #eee;">
    	<legend>
			<span  style="color:black;font-weight: bold;">Propriétés</span>
		</legend>
		
		
		<dl class="global2">
			<label style="color:#039;" for="fax">Libelle</label>&nbsp;<font color="red">&nbsp;<font color="red"><sup class="sm">*</sup></font>
			<div>
				<spring:bind path="invoice.libelle" >
					<input style="width:350px;" size="30" type="text" name="${status.expression}" value="${status.value}">
					<span style="color: red;">${status.errorMessage}</span>
					
					
				</spring:bind>
			</div>
		</dl>
		
		<dl class="global2">
			<label style="color:#039;" for="recipient">A l'attention de </label>&nbsp;<font color="red"><sup class="sm">*</sup></font>
            <div>
			<spring:bind path="invoice.contact.id" >
					<select style="width:350px;" name="${status.expression}">
					<option value="-1" selected>Choose an option...</option>
					<c:forEach var="option" items="${contactsOptions}" varStatus="loop">
						<option value="${option.id}" 
							<c:if test="${option.id == status.value}">selected</c:if>>
							${option.name}
						</option>
					</c:forEach>
					</select>
				</spring:bind>
				
				<c:if test="${not empty invalidContactErrorMessage}">
						<span style="color: red;"><c:out value="${invalidContactErrorMessage}" escapeXml="false" /></span>
						<c:set var="invalidContactErrorMessage" value="" scope="session" />
					</c:if>
			</div>
		</dl>
		
		
		
		<dl class="global2">
			<label style="color:#039;" for="billingAddress">Adresse de destination</label>&nbsp;<font color="red"><sup class="sm">*</sup></font>
            <div>
			<spring:bind path="invoice.billingAddress" >
				 <textarea name="${status.expression}" id="billing.address" rows="3" cols="42">${status.value}</textarea>
					<span style="color: red;">${status.errorMessage}</span>
				</spring:bind>
			</div>
		</dl>
		
		
		<dl class="global2">
				<div>
				<label  style="color:#039;" for="language">Langue:</label>
					<spring:bind path="invoice.language" >
						<select style="width:100px;margin-right:60px;" name="${status.expression}">
						<option value="EN" <c:if test="${'EN' == status.value}">selected</c:if>>Anglais</option>
						<option value="FR" <c:if test="${'FR' == status.value}">selected</c:if>>Francais</option>
						<option value="DE" <c:if test="${'DE' == status.value}">selected</c:if>>Allemand</option>
					</select>
					</spring:bind>
					<label  style="color:#039;" for="delaiPaiement">Délai:</label>
					<spring:bind path="invoice.delaiPaiement" >
						<input style="width:20px;" type="text" name="${status.expression}" value="${status.value}">&nbsp;<span style="color:#039;">jours</span>
						<span style="color: red;">${status.errorMessage}</span>
					</spring:bind>
					
				</div>
			</dl>

			<dl class="global2">
			<label style="color:#039;" for="recipient">Compte à créditer</label>&nbsp;<font color="red"><sup class="sm">*</sup></font>
            <div>
			<spring:bind path="invoice.bank.id" >
					<select style="width:350px;" name="${status.expression}">
					<option value="-1" selected>Choose an option...</option>
					<c:forEach var="option" items="${banksOptions}" varStatus="loop">
						<option value="${option.id}" 
							<c:if test="${option.id == status.value}">selected</c:if>>
							${option.name}
						</option>
					</c:forEach>
					</select>
				</spring:bind>
				
				<c:if test="${not empty invalidBankErrorMessage}">
						<span style="color: red;"><c:out value="${invalidBankErrorMessage}" escapeXml="false" /></span>
						<c:set var="invalidBankErrorMessage" value="" scope="session" />
					</c:if>
			</div>
		</dl>
		
		
	</fieldset>	
	
	<fieldset style="background-color: #eee;">
    	<legend>
			<span  style="color:black;font-weight: bold;">Facturation</span>
		</legend>
		

		

		<c:if test='${invoice.type eq "FB" }'>
			<dl class="global2">
				<div>
				<table id="hor-minimalist-b" style="width:350px;">
					   <caption><span style="color:blue;">Frais supplémentaires </span></caption> 	
					<thead>
					<tr>
					<th scope="col" align="center">Raison</th>
					<th scope="col" align="center" colspan="2" width="25%">Prix (Euro)</th>									
															
					</tr>
					</thead>
					<tbody>
					
						 <c:set var="row" value="0"/>
						 <c:forEach var="item" items="${invoice.frais}" varStatus="loop">
								<c:choose>
									<c:when test='${row % 2 eq 0 }'>
										<tr onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
									</c:when>
									<c:otherwise>
										<tr   onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
									</c:otherwise>
								</c:choose>
								<td align="left"><span style="color:purple;" >${item.codeJustification}</span></td>
								<td><span style="color:red;">${item.value}</span></td>
								<td>
								<c:if test='${invoice.status eq "pending"  }'>
									<a href="javascript:removeAttachmentAction('${item.code}');"><img src="images/dep_delete_icon_n.png" border="0"; alt="Delete"/></a>
								</c:if>
								</td>
							</tr>
						<c:set var="row" value="${row + 1}"/>
						</c:forEach>
				</tbody>
				</table>
				</div>
			</dl>
		</c:if>



		<dl class="global2">			
			<div>
				<label style="color:#039;" for="fax">Honoraires</label>&nbsp;<font color="red"><sup class="sm">*</sup></font>
				<spring:bind path="invoice.honoraires" >
					<input style="width:70px;background-color :  #ffffcc" size="30" type="text" name="${status.expression}" value="${status.value}" onKeyPress=”return disableEnterKey(event)”/>
					<span style="color: red;">${status.errorMessage}</span>
					
					<c:if test="${not empty invalidAmountFormatErrorMessage}">
						<span style="color: red;"><c:out value="${invalidAmountFormatErrorMessage}" escapeXml="false" /></span>
						<c:set var="invalidAmountFormatErrorMessage" value="" scope="session" />
					</c:if>
				</spring:bind>
				
				<label style="color:#039;margin-left:40px;margin-right:25px;" for="tva">Tva</label>&nbsp;<font color="red"><sup class="sm">*</sup></font>
				<spring:bind path="invoice.tva" >
					<input style="width:40px;background-color :  #ffffcc" size="30" type="text" name="${status.expression}" value="${status.value}" onKeyPress=”return disableEnterKey(event)”>&nbsp;<span style="color:#039;">%</span>
					<span style="color: red;">${status.errorMessage}</span>
				</spring:bind>
			</div>
		</dl>
		
		
		
		
		
		

		<dl class="global2">			
			<div>
				<label style="color:#039;margin-right:20px;" for="fax">Total net</label>
					<input style="width:70px; color:green;background-color:#ffffcc;margin-right:2px;" size="30" type="text" value="${invoice.amountNetEuro}" readonly="readonly">
					
				
				<label style="color:#039;margin-left:30px;margin-right:10px;" for="tva">Total brut</label>
				
					<input style="width:60px;color:green;background-color :  #ffffcc" size="30" type="text"  value="${invoice.amountEuro}" readonly="readonly">
				
				
			</div>
		</dl>
		


		
		
	</fieldset>
	<fieldset style="background-color: #eee;">
    	<legend>
			<span  style="color:black;font-weight: bold;">Administration</span>
		</legend>
		
		<dl class="global2">
		
			<label style="color:#039;" for="approved">Approved</label>
         
				<spring:bind path="invoice.approved" >
					<input style="margin-right:20px;" type="checkbox" name="${status.expression}" id="${status.expression}" value="true" 
					<c:if test='${status.value == true }'>checked</c:if> <c:if test='${invoice.approved eq true }'> DISABLED </c:if>/>
					<span style="color: red;">${status.errorMessage}</span>
				</spring:bind>
		
		
		
			<label style="color:#039;" for="approved">Mark as sent</label>
         
				<spring:bind path="invoice.sent" >
					<input type="checkbox" name="${status.expression}" id="${status.expression}" value="true" 
					<c:if test='${status.value == true }'>checked</c:if> <c:if test='${invoice.sent eq true || invoice.approved eq false}'> DISABLED </c:if>/>
					<span style="color: red;">${status.errorMessage}</span>
				</spring:bind>
		
		
		</dl>


		
		
			
		
    </fieldset>

    <fieldset style="background-color: #eee;">
    	<table border="0" width="100%">
			<tr><td align="center">
				<input style="width:100px;" type="button" name="cancel" id="cancel" class="button120"  value="Cancel" onclick="cancelForm()"/>

				<%--input style="width:100px;" type="button" name="info"  id="show1" class="button120"  value="Info" onclick="javascript:showInvoiceInfos(${targetMission});return false;" style="cursor:hand;"/--%>

				

				<c:if test='${invoice.type eq "FB" }'>
					<c:if test='${invoice.status eq "pending" }'>
					<input style="width:100px;" type="button" name="addfee" id="show" class="button120"  value="Add fee" onclick="resetForm();"/>
					</c:if>
				</c:if>

				<%--c:if test='${invoice.type eq "AD" }'>
					<c:if test='${invoice.status eq "pending" }'>
					<input style="width:100px;" type="button" name="recalculate" id="show" class="button120"  value="Calculate" onclick="reCalculate();"/>
					</c:if>
				</c:if--%>

				
				<c:if test='${invoice.status eq "sent" }'>
				<input style="width:100px;" type="button" name="update"  class="button120"  value="Add payment" onclick="addPayment();"/>
				<input style="width:100px;" type="button" name="update"  class="button120"  value="Create reminder" onclick="createReminder();"/>
				</c:if>
				<c:if test='${invoice.status != "paid" }'>
					<c:if test='${invoice.status != "sent" }'>
					<input style="width:100px;" type="submit" name="submit" id="submit" class="button120"  value="Save" />
					</c:if>
				</c:if>
				
			</td></tr>
		</table> 
    </fieldset>

	
</form>






</div>

<div id="addDialog1" class="yui-pe-content">
	<div class="hd">
        <span id="formTitle2" style="color:blue;">Add fee</span>
    </div>

	<div class="bd">
	
		<form name="addFeeForm"   method="post">
			<input type="hidden" name="customerid" value="${param['customerid']}"/>
			<input type="hidden" name="command"  value="addfee"/>
			<fieldset > 
							<dl>
							<label for="justification"><span style="color:#039;">Reason </span></label>
							<div> 
							<select style="width: 360px;"  name="justification" id="justification">
								<option value="-1">Choose one...</option>
								<option value="Supplement travail">Supplement travail</option>
								<option value="Loi blanchiment">Loi blanchiment</option>
								<option value="Frais Bureau">Frais Bureau</option>
							</select>
							</div>
							</dl>

							 <dl>
								<label for="comments"><span style="color:#039;">Justification </span></label>
								<div>   
								<textarea name="textchoice" id="textchoice" rows="4" cols="45" readonly="readonly"></textarea>
								</div> 
							</dl>

							<dl>
								<label for="prevision"><span style="color:#039;">Coût</span></label>
								<div>   
									<input style="width: 360px;" type="text" name="prix"  id="prix" value="" size="32" maxlength="32" />
								</div> 
							 </dl>
			</fieldset> 
		</form>
	</div>
</div>

<h2 style="font-size: 12px;font-family: Tahoma;font-weight: bold;border-bottom:2px solid #3399CC;padding:0 0 4px 0;margin:10px 0 0 0;">
</h2>

<form name="removeFeeForm"   method="post">
			<input type="hidden" name="customerid" value="${param['customerid']}"/>
			<input type="hidden" name="command"  value="removefee"/>
			<input type="hidden" name="feecode"/>
</form>

<form name="updateInvoiceForm"   method="post">
			<input type="hidden" name="customerid" value="${param['customerid']}"/>
			<input type="hidden" name="command"  value="updateInvoice"/>			
			<input type="hidden" name="tempHonoraire"/>
			<input type="hidden" name="tempTva"/>
</form>

