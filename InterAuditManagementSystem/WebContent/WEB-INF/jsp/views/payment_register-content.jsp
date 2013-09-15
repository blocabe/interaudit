<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib  prefix="fmt"    uri="http://java.sun.com/jstl/fmt"%>

<script type="text/javascript" src="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/jquery-1.3.2.js"></script>
<link href="${ctx}/css/messageHandler.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/script/messageHandler.js"></script>
<link type="text/css" href="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/themes/base/ui.all.css" rel="stylesheet" />
	<script type="text/javascript" src="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/jquery-1.3.2.js"></script>
	<script type="text/javascript" src="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/ui/ui.core.js"></script>
	<script type="text/javascript" src="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/ui/ui.datepicker.js"></script>
	<link type="text/css" href="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/demos.css" rel="stylesheet" />
<link href="css/global_style.css" rel="stylesheet" type="text/css" />
	<link href="css/HelpSupport.css" rel="stylesheet" type="text/css" />
	
	
	<style type="text/css">
	



        /*
        * OK sous IE8, C2 et F3,
        * se positionne au-dessus du dialogue de z-index 1000,
        * se positionne juste en dessous de la ligne d'input.
        * Sous F3, si la fen�tre Firebug est ouverte
        * le datepicker remonte sur le dialog par manque de place, normal.
        */
        div#ui-datepicker-div {
            z-index: 4000;
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

#global {   margin-left: auto;   margin-right: auto;   width: 700px;   text-align: left; /* on r�tablit l'alignement normal du texte */ }
.global2 {   margin-left: auto;   margin-right: auto;   width: 300px;   text-align: left; /* on r�tablit l'alignement normal du texte */ }

    </style>
	
<script type="text/javascript">


  
$(document).ready(function()  {


   


		<c:if test="${not empty bankCodeErrorMessage}">
						showMessage("${bankCodeErrorMessage}","error");
						<c:set var="bankCodeErrorMessage" value="" scope="session" />
		</c:if>
		
		<c:if test="${not empty factureReferenceErrorMessage}">
						showMessage("${factureReferenceErrorMessage}","error");
						<c:set var="factureReferenceErrorMessage" value="" scope="session" />
		</c:if>
		
		<c:if test="${not empty invalidAmountFormatErrorMessage}">
						showMessage("${invalidAmountFormatErrorMessage}","error");
						<c:set var="invalidAmountFormatErrorMessage" value="" scope="session" />
		</c:if>
		
		
		<c:if test="${not empty typeErrorMessage}">
						showMessage("${typeErrorMessage}","error");
						<c:set var="typeErrorMessage" value="" scope="session" />
		</c:if>
		


});



  
</script>

<script>
  function cancelForm()
  {
      window.location="${ctx}/homePage.htm";
  }

  function deletePayment(id)
  {
	 var answer = confirm("Do you really want to delete this payment?")
     if (answer){
      window.location="${ctx}/deletePayment.htm?paymentId=" +id;
	}
  }
</script>

 <script type="text/javascript">
	$(function() {
	$('#datepicker1').datepicker({
		showOn: 'button', buttonImage: 'images/calbtn.gif', buttonImageOnly: false,
	changeMonth: true,
		changeYear: true
	});
	});
</script>

<h2 style="font-size: 12px;font-family: Tahoma;font-weight: bold;border-bottom:2px solid #3399CC;padding:0 0 4px 0;margin:10px 0 0 0;">
<!--span  style="color:black;font-weight: bold;">Payment ${payment.reference}  [${payment.customerName}]</span-->
				<c:choose>
					<c:when test='${payment.id != null }'>
						<span  style="color:black;font-weight: bold;">Payment ${payment.reference}  [${payment.customerName}]</span>
					</c:when>
					<c:otherwise>
						<span  style="color:black;font-weight: bold;">New Payment</span>
					</c:otherwise>
				</c:choose>
</h2>
<br/>
<div id="global" >
<table cellpadding="3" cellspacing="2" border="0" class="axial" style="margin-left: auto;   margin-right: auto;   width: 700px;   text-align: left;">

	<tr><td colspan="2" align="center">
				<span  style="color:black;font-weight: bold;">Informations sur la facture  [${payment.facture.reference}]</span>
			</td>
		</tr>

	<tr>

	 <th>
		Reference facture  
	  </th>
      <td>
        <span  style="color:blue;font-weight: bold;"> 
				
					${payment.facture.reference}
				
		</span>
      </td>
    </tr>

	
      <th>
		Facture totale 
	  </th>
      <td>
        <span  style="color:blue;font-weight: bold;"> 
				<fmt:formatNumber type="currency" currencySymbol="&euro;">
					${payment.totalDu}
				</fmt:formatNumber>
		</span>
      </td>
    </tr>
	

	<tr>
      <th>
		Note de credit
	  </th>
      <td>
        <span  style="color:blue;font-weight: bold;">
				<fmt:formatNumber type="currency" currencySymbol="&euro;">
					${payment.totalNoteCredit}
				</fmt:formatNumber>
		</span>
      </td>
    </tr>

	<tr>
      <th>
		Total paye a ce jour
	  </th>
      <td>
        <span  style="color:blue;font-weight: bold;">
		       <fmt:formatNumber type="currency" currencySymbol="&euro;">
					${payment.totalPaid}
				</fmt:formatNumber>
		</span>
      </td>
    </tr>

	<tr>
      <th>
		Reste a payer
	  </th>
      <td>
        <span  style="color:blue;font-weight: bold;">		 
		       <fmt:formatNumber type="currency" currencySymbol="&euro;">
					${payment.totalRemaining}
				</fmt:formatNumber>
		</span>
		
      </td>
    </tr>
    
    

</table>
<form  action="${ctx}/showPaymentRegisterPage.htm" method="post" class="niceform" style="border:1px solid grey; ">

<input type="hidden" id="facture.reference" name="facture.reference" value="${payment.facture.reference}" />

<fieldset style="background-color: #eee;">
    	<table border="0" width="100%">
		<tr><td align="center">		
				<c:choose>
					<c:when test='${payment.id != null }'>
						<span  style="color:black;font-weight: bold;">Payment ${payment.reference}  [${payment.customerName}]</span>
					</c:when>
					<c:otherwise>
						<span  style="color:black;font-weight: bold;">New Payment</span>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
	</table>
    </fieldset>
<fieldset style="background-color: #eee;">
    	<legend><span  style="color:black;font-weight: bold;">Properties</span></legend>
        
		
		<dl class="global2">
				<label style="color:#039;" for="password">Montant a enregistrer:</label>
				<div >
				
					<spring:bind path="payment.amount" >
						<input style="width:200px;" type="text" name="${status.expression}" value="${status.value}">
						<span style="color: red;">${status.errorMessage}</span>
						
						<c:if test="${not empty invalidAmountFormatErrorMessage}">
							<span style="color: red;"><c:out value="${invalidAmountFormatErrorMessage}" escapeXml="false" /></span>
							<c:set var="invalidAmountFormatErrorMessage" value="" scope="session" />
						</c:if>
					</spring:bind>

					<spring:bind path="payment.currencyCode" >
						<select  name="${status.expression}">
						 <option value="EUR" selected>Euro</option>
						</select>
					</spring:bind>
				</div>
		</dl>
		
		<dl class="global2">
			<label  style="color:#039;" for="password">Date de r&eacute;ception du paiement:</label><span  style="color:red;font-weight: bold;"> (mm / dd / YYYY)</span>
			<div>
				<spring:bind path="payment.paymentDate" >
					<input  style="width:200px;" type="text" name="${status.expression}" id="datepicker1" value="${status.value}">
					<span style="color: red;">${status.errorMessage}</span>
				</spring:bind>
			</div>
		</dl>
        	
			
		
		
    </fieldset>
	

	<fieldset style="background-color: #eee;">
		<legend><span  style="color:black;font-weight: bold;">Administration</span></legend>
    	 
		<dl class="global2">
			<label  style="color:red;"><i>* Select one of both</i></label>
		   <div>
                <label  style="color:#039;" for="password">Remboursement</label>                
                <spring:bind path="payment.reimbourse" >                
                    <input  style="width:200px;" type="checkbox" name="${status.expression}" id="${status.expression}" value="${status.value}" <c:if test='${status.value == true}'> checked</c:if>/>
                </spring:bind>
            </div>
			
			<div>
                <label  style="color:#039;" for="password">Escompte&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>                
                <spring:bind path="payment.escompte" >                
                    <input  style="width:200px;" type="checkbox" name="${status.expression}" id="${status.expression}" value="${status.value}" <c:if test='${status.value == true}'> checked</c:if>/>
                </spring:bind>
            </div>
            
            <c:if test="${not empty typeErrorMessage}">
                        <span style="color: red;"><c:out value="${typeErrorMessage}" escapeXml="false" /></span>
                        <c:set var="typeErrorMessage" value="" scope="session" />
            </c:if>
			
		</dl>
		<dl class="global2">
			<label  style="color:#039;" for="password">Code banque:</label>
            <div>
				<spring:bind path="payment.bankCode" >
					<select  style="width:350px;" name="${status.expression}">
					<option value="-1" selected>Choose an option...</option>
					<c:forEach var="option" items="${bankCodeOptions}" varStatus="loop">
						<option value="${option.id}" 
							<c:if test="${option.id == status.value}">selected</c:if>>
							${option.name}
						</option>
					</c:forEach>
					</select>
					
					<c:if test="${not empty bankCodeErrorMessage}">
						<span style="color: red;"><c:out value="${bankCodeErrorMessage}" escapeXml="false" /></span>
						<c:set var="bankCodeErrorMessage" value="" scope="session" />
					</c:if>
				</spring:bind>	
			</div>
		</dl>
    </fieldset>



    <fieldset style="background-color: #eee;">
    	<table border="0" width="100%">
		<tr><td align="center">
				<c:if test='${payment.id != null }'>
					<input style="width:100px;
					" type="button" name="delete" id="cancel" class="button120"  value="Delete" onclick="deletePayment(${payment.id})"/>
				</c:if>

				<input style="width:100px;
				" type="button" name="cancel" id="cancel" class="button120"  value="Cancel" onclick="cancelForm()"/>

				<input style="width:100px;
				" type="submit" name="submit" id="submit" class="button120"  value="Enregistrer" />
			</td>
		</tr>
	</table>
    </fieldset>
</form--%>
<script>
function changePayment(id){
	window.location="${ctx}/showPaymentRegisterPage.htm?id="+id;
}
</script>
</div>
<h2 style="font-size: 12px;font-family: Tahoma;font-weight: bold;border-bottom:2px solid #3399CC;padding:0 0 4px 0;margin:10px 0 0 0;">
</h2>
<c:if test="${not empty payment.facture.payments && payment.id!= null}">
<br/>
<div style="background-color: #eee;margin-left: auto; margin-right: auto; width: 80%; text-align: center; border:1px solid grey; ">
 <table id="ver-zebra" width="100%" cellspacing="0"  class="formlist">
									<caption><span>List of payments for invoice [${payment.facture.reference}]</span></caption>
									<thead>
											<tr>
												<th scope="col"></th>
												<th scope="col">Invoice</th>
												<th scope="col">Customer</th>
												<th scope="col">Bank</th>
												<th scope="col">Amount</th>												
												<th scope="col">Currency</th>
												<th scope="col">Date of payment</th>
												<th scope="col">Actions</th>
												
											</tr>
										</thead>
										<tbody>

											<c:set var="row" value="0"/>
											<c:forEach var="item" items="${payment.facture.sortedPayments}" varStatus="loop">

											<c:choose>
									    		<c:when test='${payment.id == item.id }'>
									      			<tr style="background: orange;"  onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
									      		</c:when>
									      		<c:otherwise>
									      			<tr onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
									      		</c:otherwise>
									     	</c:choose>
											
												<th>
												<a href="${ctx}/showPaymentRegisterPage.htm?id=${item.id}"><span>${item.reference}</span></a>												
												</th>
												<td>
												<a href="${ctx}/editInvoicePage.htm?invoiceId=${item.facture.id}">${item.facture.reference}</a>
												</td>
												<td>${item.customerName}</td>
												<td>${item.bankCode}</td>
												<td style="background-color :  #55FF55;">
												<fmt:formatNumber type="currency" currencySymbol="&euro;">
														${item.amount}
												</fmt:formatNumber>
												</td>
												<td>${item.currencyCode}</td>
												<td>${item.paymentDate}</td>
												<th>
												<input type="radio" name="group1" value="" onclick="changePayment(${item.id });" <c:if test='${payment.id == item.id }'>checked </c:if>/> 
												</th>
											</tr>
											
											<c:set var="row" value="${row + 1}"/>
											</c:forEach>

										</tbody>
									

								</table>
</div>
</c:if>