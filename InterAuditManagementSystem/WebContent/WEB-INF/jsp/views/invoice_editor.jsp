
<jsp:directive.page contentType="text/html;charset=UTF-8" />
<%@ taglib prefix="tab" uri="http://ditchnet.org/jsp-tabs-taglib"%>
<%@ taglib prefix="layout" uri="http://www.sourceforge.net/springLayout"%>
<%@ taglib prefix="tiles" uri="/WEB-INF/struts-tiles.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib  prefix="fmt"    uri="http://java.sun.com/jstl/fmt"%>

<%
request.setAttribute("ctx", request.getContextPath()); 
request.setAttribute("serverName", request.getServerName()); 
request.setAttribute("serverPort", request.getServerPort()); 
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>Edit invoice  ${invoice.reference} </title>
	<link href="css/HelpSupport.css" rel="stylesheet" type="text/css" />
	<link href="css/odbb-skin.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="script/scriptaculous/prototype.js"></script>
	<script type="text/javascript" src="script/scriptaculous/scriptaculous.js"></script>
	<script type="text/javascript" src="${ctx}/script/jquery-1.6.1.js"></script>
	<link href="${ctx}/css/messageHandler.css" rel="stylesheet" type="text/css" />
	<script src="${ctx}/script/messageHandler.js"></script>

</head>

<script type="text/javascript">
$(document).ready(function()  {
			<c:if test="${not empty actionErrors}">
				<c:forEach var="actionError" items="${actionErrors}">					
					showMessage("${actionError}","error");
				</c:forEach>
				<c:set var="actionErrors" value="" scope="session" />
			</c:if>
			<c:if test="${not empty successMessage}">
				<c:forEach var="message" items="${successMessage}">
					showMessage("${message}","ok");
				</c:forEach>
				<c:set var="successMessage" value="" scope="session" />
			</c:if>
});



	function confirmationSelectOption(code) {   
			var url = "${ctx}/updateInvoiceTvaZeroLibCodeValue.htm?invoiceId=${invoice.id}&code="+ code;		
			//we make the ajax request to create the line on the server
			 window.location=url;
	}
	
   function confirmationSelectBalanceAgee() {  
           var url = "${ctx}/changeExcludedFromBalanceAgeeStatus.htm?invoiceId=${invoice.id}";        
           //we make the ajax request to create the line on the server
            window.location=url;
   }

</script>

<style type="text/css"> 
#ta { 
   padding: 5px; 
   height: 350px; 
   width: 500px; 
   /*border: medium inset #CCCCCC; */
   font-family: "Courier New", Courier, mono; 
   font-size: 12px; 
} 
</style> 

<style>
    .yui-editor-container {
        position: absolute;
        top: -9999px;
        left: -9999px;
        z-index: 999;
    }
    #editor {
        visibility: hidden;
        position: absolute;
    }
    .editable {
        border: 1px solid black;
        margin: .25em;
        float: left;
        width: 350px;
        height: 50px;
        overflow: auto;
    }
</style>

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

 






<script>
  function cancelForm()
  {
      window.location="${ctx}/homePage.htm";
  }
  
  function deleteInvoice(id)
  {
	var answer = confirm("Do you really want to delete this invoice?")
    if (answer){
      window.location="${ctx}/deleteInvoice.htm?invoiceId=" +id;
	}
     
  }

  function approveInvoice(id)
  {
      window.location="${ctx}/approveInvoice.htm?invoiceId=" +id;
	   showMessage("Approved","ok");
  }

  function markAsSentInvoice(id)
  {
      window.location="${ctx}/markAsSentInvoice.htm?invoiceId=" +id;
	  showMessage("Sent","ok");
  }

  function closeInvoice(id)
  {
	  //alert(${invoice_pending_status} + ${invoice_ongoing_status} + ${invoice_ongoing_status} + ${invoice_paid_status});
      //window.location="${ctx}/showInvoicesPage.htm?pending=${invoice_pending_status}&approved=${invoice_approved_status}&ongoing=${invoice_ongoing_status}&paid=${invoice_paid_status}";
	  window.location="${ctx}/closeInvoicePage.htm?invoiceId=" +id;
  }




 function previewInvoice(id)
  {
	 
     // window.location="http://iaapp01:8080/birt/run?__report=facture_report.rptdesign&__format=pdf&invoiceIdentifier="+id;
	 // var url ="http://iaapp01:8080/birt/run?__report=facture_report.rptdesign&__format=pdf&invoiceIdentifier="+id;
	 //var url ="${ctx}/birt/run?__report=facture_report.rptdesign&__format=pdf&invoiceIdentifier="+id;
	//  alert(url);
	var url ="http://${serverName}:${serverPort}/birt/run?__report=facture_report.rptdesign&__format=pdf&invoiceIdentifier="+id;	
	window.open(url,"nom_de_la_fenetre","width =1000 ; height =1000");
  }
  
  function previewInvoiceWord(id)
  {
	 
     // window.location="http://iaapp01:8080/birt/run?__report=facture_report.rptdesign&__format=pdf&invoiceIdentifier="+id;
	 // var url ="http://iaapp01:8080/birt/run?__report=facture_report.rptdesign&__format=pdf&invoiceIdentifier="+id;
	 //var url ="${ctx}/birt/run?__report=facture_report.rptdesign&__format=pdf&invoiceIdentifier="+id;
	//  alert(url);
	// var url ="http://${serverName}:${serverPort}/birt/run?__report=facture_report.rptdesign&__format=pdf&invoiceIdentifier="+id;
	
	 var url ="http://${serverName}:${serverPort}/birt/run?__report=facture_report.rptdesign&__format=doc&invoiceIdentifier="+id;
	
	  window.open(url,"nom_de_la_fenetre","width =1000 ; height =1000");
  }
  
</script>





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
		 
		 
		 function showPayments()
		 {
			var url = "${ctx}/showPaymentsPage.htm?invoiceReference=${invoice.reference}";
			window.location=url;
		 }

		function createReminder()
		 {
			var url = "${ctx}/showInvoiceReminderRegisterPage.htm?invoiceid=${invoice.id}";
			window.location=url;
		 }

</script>








<body class="yui-skin-sam">
   <!--div id="ODBB_HEADER"> 
				<a href="${ctx}/homePage.htm"><img src="images/bannieres/ban.base1.jpg" alt="IAMS" border="0"></a>
	</div-->
	 <jsp:include page="horizontal_menu.jsp"/>
	
 <div style="font-size: .7em;background-color: #F8F6E9;">
<br/>	
<h2></h2>

<br/>
<div id="global">

	<c:if test="${not empty invoiceErrorMessage}">
				<div style="width: 100%; align: center">
				<div class="validation">					
					<c:forEach var="message"
					items="${invoiceErrorMessage}">
					<li><span style="color: blue;"><c:out value="${message}" escapeXml="false" /></span></li>
				</c:forEach>
				</div>
				</div>
			<c:set var="invoiceErrorMessage" value="" scope="session" />
		</c:if>
		
		<c:if test="${not empty successMessage}">
				<div style="width: 100%; align: center">
				<div class="success"><c:forEach var="message"
					items="${successMessage}">
					<li><span style="color: blue;"><c:out value="${message}" escapeXml="false" /></span></li>
				</c:forEach></div>
				</div>
				<c:set var="successMessage" value="" scope="session" />
		</c:if>

	<table cellpadding="3" cellspacing="2" border="0" class="axial" style="margin-left: auto;   margin-right: auto;   width: 700px;   text-align: left;">

	<tr>
		<td colspan="2" align="center">
				<span  style="color:black;font-weight: bold;">
						<c:choose>
							<c:when test='${invoice.type eq "AD" }'>
							 Acompte :<span  style="color:blue;font-weight: bold;"> ${invoice.reference} </span> 
							</c:when>
							<c:when test='${invoice.type eq "CN" }'>
							 Note de credit :<span  style="color:blue;font-weight: bold;"> ${invoice.reference} </span>  
							</c:when>
							<c:when test='${invoice.type eq "SP" }'>
							Facture supplémentaire :<span  style="color:blue;font-weight: bold;"> ${invoice.reference} </span>  
							</c:when>
							<c:otherwise>
							Facture finale : <span  style="color:blue;font-weight: bold;"> ${invoice.reference} </span> 
							</c:otherwise>
					</c:choose>
				</span>
		</td>
	</tr>

	<tr>
      <th>
		Référence
	  </th>
      <td>
        <span  style="color:blue;font-weight: bold;">${invoice.reference}</span>
      </td>
    </tr>

	<c:if test='${invoice.type eq "CN" }'>
	  <tr>
		<th>
		Facture Référencée
	   </th>
       <td>
        <span  style="color:blue;font-weight: bold;">${invoice.parentReference}</span>
       </td>
	  </tr>

	  <tr>
		<th>
		Int/Ext
	   </th>
       <td>        
		<c:choose>
							<c:when test='${invoice.interne eq true }'>
							<span  style="color:blue;font-weight: bold;"> Interne</span> 
							</c:when>
							
							<c:otherwise>
							<span  style="color:blue;font-weight: bold;">Externe </span> 
							</c:otherwise>
					</c:choose>
       </td>
	  </tr>
	</c:if>

	<tr>
      <th>
		Statut
	  </th>
      <td>


   
	   <c:choose>

													<c:when test='${invoice.status eq "pending" }'>
													 <span style="color:red;font-weight: bold;">En Attente</span>
													</c:when>

													<c:when test='${invoice.status eq "approved" }'>
													 <span style="color:#05A5D8;font-weight: bold;">Approuvée</span>
													</c:when>

													<c:when test='${invoice.status eq "sent" }'>
													 <span style="color:blue;font-weight: bold;">Envoyée</span>
													</c:when>													

													<c:when test='${invoice.status eq "paid" }'>
													 <span style="color:#0392DE;font-weight: bold;">Payée le ${invoice.dateOfPayment }</span>
													</c:when>

													<c:when test='${invoice.status eq "cancelled" }'>
													 <span style="color:red;font-weight: bold;">Annulée</span>
													</c:when>
													
									     		</c:choose>  
	
      </td>
    </tr>

	<tr>
      <th>Mandat</th>
      <td>
	  <span  style="color:black;font-weight: bold;">${invoice.exerciseMandat}</span>
         
      </td>
    </tr>

	<tr>
      <th>Crée le</th>
      <td>
	  <span  style="color:black;font-weight: bold;">${invoice.dateFacture}</span>
         
      </td>
    </tr>
    
    <tr>
      <th>Facturation</th>
      <td>
      <span id="facturation" style="color:black;font-weight: bold;">${invoice.dateFacturation}</span>
      <c:if test='${invoice.approved eq false}'>  
                    &nbsp; <img src="images/_ast.gif" border="0" alt="PDF"/>
      </c:if>
         
      </td>
    </tr>
    
    <tr>
      <th>Envoyé le</th>
      <td>
      <span  style="color:black;font-weight: bold;">
        <c:choose>
            <c:when test='${invoice.sentDate != null }'>                                                  
                ${invoice.sentDate}
            </c:when>
            <c:when test='${invoice.sentDate == null }'>                                                  
                ---
            </c:when>                                           
        </c:choose>        
      </span>
         
      </td>
    </tr>
    
    <tr>
      <th>Echéance</th>
      <td>
      <span  style="color:black;font-weight: bold;">
        <c:choose>
            <c:when test='${invoice.dateEcheance != null }'>                                                  
                ${invoice.dateEcheance}
            </c:when>
            <c:when test='${invoice.dateEcheance == null }'>                                                  
                ---
            </c:when>                                           
        </c:choose> 
      </span>         
      </td>
    </tr>

	<tr>
      <th>Ref Ass/Sec</th>
      <td>
	  <span  style="color:black;font-weight: bold;">
	  <c:choose>
            <c:when test='${invoice.refAssSec != null }'>                                                  
                ${invoice.refAssSec}
            </c:when>
            <c:when test='${invoice.refAssSec == null }'>                                                  
                ---
            </c:when>                                           
        </c:choose>  
	  
	 </span>
         
      </td>
    </tr>

	<tr>
      <th>Langue </th>
      <td>
	  <span id="lang" style="color:black;font-weight: bold;">

	  <c:choose>
			<c:when test='${invoice.language eq "EN" }'>													
				Anglais &nbsp;<img src="images/flag_england.gif" border="0" alt="PDF"/>
			</c:when>

			<c:when test='${invoice.language eq "FR"  }'>													 
				Français &nbsp;<img src="images/flags_of_France.gif" border="0" alt="PDF"/>
			</c:when>

			<c:when test='${invoice.language eq "DE" }'>													
				Allemand &nbsp;<img src="images/flag_german.gif" border="0" alt="PDF"/>
			</c:when>													
		</c:choose>  
         
		 </span>&nbsp;
		 &nbsp; <!--img src="images/_ast.gif" border="0" alt="PDF"/-->
      </td>
    </tr>


	<tr>
      <th>Associé</th>
      <td>
        <span  style="color:black;font-weight: bold;">${invoice.partner.fullName}</span>
      </td>
    </tr>

	<tr>
      <th>Traité par</th>
      <td>
        <span  style="color:black;font-weight: bold;">
		<c:choose>
			<c:when test='${invoice.sender != null }'>													
				${invoice.sender.fullName}
			</c:when>
            <c:when test='${invoice.sender == null }'>                                                  
                ---
            </c:when>											
		</c:choose>  
		</span>
      </td>
    </tr>

	<tr>
      <th>Client</th>
      <td>        
       <span id="customerName" style="color:black;font-weight: bold;">${invoice.customerName}</span>&nbsp; <img src="images/_ast.gif" border="0" alt="PDF"/>
      </td>
    </tr>

	<tr>
      <th>Addresse </th>
      <td>	  	
		<div id="editable_cont">
			<div id="addresse" class="editable">${invoice.billingAddressHtml}</div>			
		</div>
		<c:if test='${invoice.approved eq false}'>  
						&nbsp; <img src="images/_ast.gif" border="0" alt="PDF"/>
		</c:if>
		
	  </td>
    </tr>

	<tr>
      <th>TVA (%) </th>
      <td>
			<span id="tva" style="color:red;font-weight: bold;">${invoice.tva}</span>
			
			<c:if test='${invoice.approved eq false}'>  
					&nbsp; <img src="images/_ast.gif" border="0" alt="PDF"/>
			</c:if>
      </td>
    </tr>

	 

	<tr>
      <th>Lib honoraires </th>
      <td>	 
		<div id="editable_cont">
			<div id="libHonoraires" class="editable">${invoice.libHonorairesHtml}</div>			
		</div>
       <c:if test='${invoice.approved eq false}'>  
						&nbsp; <img src="images/_ast.gif" border="0" alt="PDF"/>
		</c:if>
      </td>
    </tr>

	<tr>
      <th>Honoraires </th>
      <td>
			<span id="honoraires" style="color:red;font-weight: bold;">
				<fmt:formatNumber type="currency" currencySymbol="&euro;">
					${invoice.honoraires}
				</fmt:formatNumber>
					
				
			</span>
			 
				
      </td>
    </tr>


   

	<c:if test='${invoice.type != "CN"}'>
	          

	<tr>
     <th>Frais</th>
	  <td align="center">
	  <c:if test='${invoice.status eq "pending"  }'>
				<form name="addFraisToInvoiceForm" action="${ctx}/addFraisToInvoice.htm" method="post"   style="border: 1px solid black;">
					<input type="hidden" name="invoiceId" value="${invoice.id}"/>
					<input type="hidden" name="lang" value="${invoice.language}"/>
					<span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Frais</strong> : </span>
					<select style="font:10px Verdana, sans-serif;margin-right:10pt;" name="frais" id="frais">
								<option value="lib.fa.men.blanchiment">blanchiment </option>
								<option value="lib.fa.men.cssf">cssf</option>
								<option value="lib.fa.men.supplement">supplement</option>						
					</select> 							
					&nbsp;
					<input class="button120" style="font:10px Verdana, sans-serif; width:100px;" type="Submit"  value="Add" />
					<c:if test='${invoice.approved eq false}'>  
						&nbsp; <img src="images/_ast.gif" border="0" alt="PDF"/>
					</c:if>
		

			</form>
			</c:if>
			<div style="">
				<table id="hor-minimalist-b" width="100%">
					 
					<%--thead>
					<tr>
					<th scope="col" align="center"><span  style="color:blue;font-weight: bold;">Justification &nbsp; <img src="images/_ast.gif" border="0" alt="PDF"/></span></th>
					<th scope="col" align="center" width="10%"><span  style="color:blue;font-weight: bold;">Montant &nbsp; <img src="images/_ast.gif" border="0" alt="PDF"/></span></th>									
					<th scope="col" align="center" width="10%"><span  style="color:blue;font-weight: bold;">Action</span></th>											
					</tr>
					</thead--%>
					<tbody>
					
						 <c:set var="row" value="0"/>
						 <c:forEach var="item" items="${invoice.sortedFrais}" varStatus="loop">
								<c:choose>
									<c:when test='${row % 2 eq 0 }'>
										<tr onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
									</c:when>
									<c:otherwise>
										<tr   onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
									</c:otherwise>
								</c:choose>
								<td align="left" style="border: 0px;border: 1px solid black;">
								
									<div style="width:300px; border: 0px;"><span id="${item.id}" class="translation_cell" style="color:purple;" >${item.justificationHtml}</span></div>			
								
								
								</td>
								<td align="left" style="border: 0px;border: 1px solid black;"><span id="${item.id}_v" style="color:red;font-weight: bold;">${item.value}</span>&nbsp;&euro;</td>

								<td width="10%" style="border: 0px;border: 1px solid black;">
								<c:if test='${invoice.status eq "pending"  }'>
									<a href="${ctx}/removeFraisFromInvoice.htm?fraisId=${item.id}&invoiceId=${invoice.id}"><img src="images/Table-Delete.png" border="0"; alt="Delete"/></a>

									
								</c:if>
								</td>
							</tr>
						<c:set var="row" value="${row + 1}"/>
						</c:forEach>
				</tbody>
				</table>
				</div>
				
			</td>      
    </tr>


	<tr>
     <th>A deduire</th>
	  <td align="center">
	  <c:if test='${invoice.status eq "pending"  }'>
				<form name="addDeductionToInvoiceForm" action="${ctx}/addDeductionToInvoice.htm" method="post"  style="border: 1px solid black;">
					<input type="hidden" name="invoiceId" value="${invoice.id}"/>
					<input type="hidden" name="lang" value="${invoice.language}"/>
					<span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Reductions</strong> : </span>
					<select style="font:10px Verdana, sans-serif;margin-right:10pt;" name="frais" id="frais">
								<option value="lib.fa.men.trop.percu">Trop percu </option>
								<option value="lib.fa.men.remise.exceptionnelle">Remise exceptionnelle</option>	
								<option value="lib.fa.men.advance">Demande d'avance</option>	
								
					</select> 							
					&nbsp;
					<input class="button120" style="font:10px Verdana, sans-serif; width:100px;" type="Submit"  value="Add" />	
					<c:if test='${invoice.approved eq false}'>  
						&nbsp; <img src="images/_ast.gif" border="0" alt="PDF"/>
					</c:if>

			</form>
		</c:if>
			
			<div style="">
				<table id="hor-minimalist-b" width="100%">
					 
					<%--thead>
					<tr>
					<th scope="col" align="center"><span  style="color:blue;font-weight: bold;">Justification &nbsp; <img src="images/_ast.gif" border="0" alt="PDF"/></span></th>
					<th scope="col" align="center" width="10%"><span  style="color:blue;font-weight: bold;">Montant &nbsp; <img src="images/_ast.gif" border="0" alt="PDF"/></span></th>									
					<th scope="col" align="center" width="10%"><span  style="color:blue;font-weight: bold;">Action</span></th>											
					</tr>
					</thead--%>
					<tbody>
					
						 <c:set var="row" value="0"/>
						 <c:forEach var="item" items="${invoice.sortedDeductions}" varStatus="loop">
								<c:choose>
									<c:when test='${row % 2 eq 0 }'>
										<tr onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'" >
									</c:when>
									<c:otherwise>
										<tr   onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
									</c:otherwise>
								</c:choose>
								<td align="left" style="border: 0px;border: 1px solid black;">
								
									<div style="width: 300px;border: 0px;"><span id="${item.id}" class="deduction_cell" style="color:purple;" >${item.justificationHtml}</span></div>			
								
								
								</td>
								<td align="left" style="border: 0px;border: 1px solid black;"><span id="${item.id}_v" style="color:red;font-weight: bold;">${item.value}</span>&nbsp;&euro;</td>
								<td width="10%" style="border: 0px;border: 1px solid black;">
							
								<c:if test='${invoice.status eq "pending"  }'>
									<a href="${ctx}/removeDeductionFromInvoice.htm?deductionId=${item.id}&invoiceId=${invoice.id}">
									<img src="images/Table-Delete.png" border="0"; alt="Delete"/></a>									
								</c:if>
								</td>
							</tr>
						<c:set var="row" value="${row + 1}"/>
						</c:forEach>
				</tbody>
				</table>
				</div>
				
			</td>      
    </tr>

	      
   </c:if>

	<tr>
      <th>Hors taxes</th>
      <td>
			<span  style="color:red;font-weight: bold;">
				<fmt:formatNumber type="currency" currencySymbol="&euro;">
					${invoice.amountNetEuro}
				</fmt:formatNumber>
			</span>
				
      </td>
    </tr>

	<tr>
      <th>Montant TVA</th>
      <td>
			<span  style="color:red;font-weight: bold;">
				<fmt:formatNumber type="currency" currencySymbol="&euro;">
					${ (invoice.amountNetEuro * invoice.tva )*0.01 }
				</fmt:formatNumber>
			</span>
				
      </td>
    </tr>

	<tr>
      <th>Net à payer</th>
      <td>
			<span  style="color:red;font-weight: bold;">
				<fmt:formatNumber type="currency" currencySymbol="&euro;">
					${invoice.amountEuro}
				</fmt:formatNumber>
			</span>
				
      </td>
    </tr>

	

	<tr>
      <th>&nbsp;Lib delai </th>
      <td>		
		<div id="editable_cont">
			<div id="libDelai" style="width: 500px;" class="editable">${invoice.libDelaiHtml}</div>			
		</div>
       <c:if test='${invoice.approved eq false}'>  
						&nbsp; <img src="images/_ast.gif" border="0" alt="PDF"/>
		</c:if>
      </td>
    </tr>
	
        
      
	<c:if test='${invoice.tva == 0  }'>
	<TR>
		<Th ROWSPAN=3>&nbsp;Lib Tva 0%</Th>
		<TD ><INPUT TYPE=RADIO NAME="tva0pourcent" onclick="confirmationSelectOption('lib.fa.tva0.opt0');" VALUE="lib.fa.tva0.opt0"  <c:if test='${invoice.tvaZeroLibCode eq "lib.fa.tva0.opt0" }'>checked</c:if>>${invoice.libTvaZeroOpt0}</TD>
	</TR>
	<TR>
		<TD ><INPUT TYPE=RADIO NAME="tva0pourcent" onclick="confirmationSelectOption('lib.fa.tva0.opt1');" VALUE="lib.fa.tva0.opt1" <c:if test='${invoice.tvaZeroLibCode eq "lib.fa.tva0.opt1" }'>checked</c:if>>${invoice.libTvaZeroOpt1}</TD>
	</TR>
	<TR>
		<TD ><INPUT TYPE=RADIO NAME="tva0pourcent" onclick="confirmationSelectOption('lib.fa.tva0.opt2');" VALUE="lib.fa.tva0.opt2" <c:if test='${invoice.tvaZeroLibCode eq "lib.fa.tva0.opt2" }'>checked</c:if>>${invoice.libTvaZeroOpt2}</TD>
	</TR>
	</c:if>

	<c:if test='${invoice.approved eq true  &&  invoice.sent eq true  && invoice.status != "paid"}'>     
        <TR>
            <Th>Balance agée</Th>
            <TD ><INPUT TYPE=checkbox NAME="compte_balance_agee" onclick="confirmationSelectBalanceAgee();"   <c:if test='${invoice.excludedFromBalanceAgee eq true }'>checked</c:if>>
            Cochez cette case si vous ne voulez pas que cette facture soit prise en compte dans la balance agée
            </TD>
        </TR>
    </c:if>


	<tr>
     
	  <td colspan="2" align="center">
	           <c:if test='${invoice.approved eq false  &&  invoice.sent eq false}'>  
				<input style="width:100px;
				" type="button" name="delete" id="cancel" class="button120"  value="Delete" onclick="deleteInvoice(${invoice.id})"/>
			   </c:if>
				
						<input style="width:100px;
				" type="button" name="preview" id="preview" class="button120"  value="Print PDF" onclick="previewInvoice(${invoice.id})" />
				
				<input style="width:100px;
				" type="button" name="preview" id="preview" class="button120"  value="Print DOC" onclick="previewInvoiceWord(${invoice.id})" />
			
				
				<c:if test='${invoice.approved eq false}'>  
						<input style="width:100px;
				" type="button" name="approve" id="approve" class="button120"  value="Approve"   onclick="approveInvoice(${invoice.id})"/>
				</c:if>

				<c:if test='${invoice.approved eq true  &&  invoice.sent eq false}'>  
					<c:if test='${invoice.type != "CN"}'>
						<input style="width:100px;
						" type="button" name="markAsSent" id="markAsSent" class="button120"  value="Mark as sent"   onclick="markAsSentInvoice(${invoice.id})"/>
					</c:if>
					<c:if test='${invoice.type == "CN"}'>
					   <input style="width:150px;
                        " type="button" name="markAsSent" id="markAsSent" class="button120"  value="Mark as sent and paid"   onclick="markAsSentInvoice(${invoice.id})"/>
					</c:if>
					
				</c:if>
				
				<c:if test='${invoice.approved eq true  && invoice.type != "CN"}'>
	                <input style="width:100px;" type="button" name="update"  class="button120"  value="Add payment" onclick="addPayment();"/>
	                
                </c:if>
				
				<input style="width:100px;" type="button" name="showPayments"  class="button120"  value="Show Payments" onclick="showPayments();">

				<input style="width:100px;" type="button" name="close"  class="button120"  value="Close" onclick="closeInvoice(${invoice.id});"/>
					
				
			</td>
      
    </tr>

	
	
	</table>




</div>

<h2></h2>
<br/>

</div>


<script type="text/javascript">



//for each cell, add cell-editing 'InPlaceEditor'

	function addInPlaceEditors() {
		var invoiceId = '${invoice.id}';
		var cells = $$('span.translation_cell');		
		for (i=0; i<cells.length;i++){
			var keyAmount = cells[i].id + '_v';
			var fraisId=cells[i].id;
			new Ajax.InPlaceEditor(cells[i].id, '${ctx}/updateFraisLibelle.htm',
			{
				rows:5,
				cols:50,
				cancelControl : "button",
                callback: function(form, value) {//call the controller with parameters
	                //get the cell id and trim it (remove 'prefix_' and trailing '-inplaceeditor')
	                var id = form.id;
	                id = id.substring(0,id.indexOf('-inplaceeditor'));
	                id = id.substring(id.lastIndexOf('_')+1,id.length);
	              // alert(id);
	                return 'invoiceId='+invoiceId  + '&fraisId='+ id + '&translation='+encodeURIComponent(value);
	            },
                onComplete:function(transport, element){//print the result using JSON
                    var msg = 'enter value';
                    if (transport.responseText.evalJSON().translation == ' ' || transport.responseText.evalJSON().translation == null
                    || transport.responseText.evalJSON().translation == ''){
                        element.innerHTML = '<font color="red"> <b>' + msg + '</b></font>';
                    }
                    else {
                        element.innerHTML = transport.responseText.evalJSON().translation;
                    }
                },
                onFailure:function(transport) {
                    alert('You must enter a valid entry!');
                }
			}

			);



					//amount frais
		new Ajax.InPlaceEditor(keyAmount, '${ctx}/updateInvoiceFraisValue.htm',
			{
				rows:1,
				cols:10,
				cancelControl : 'link',
                callback: function(form, value) {//call the controller with parameters
                    //get the cell id and trim it (remove 'prefix_' and trailing '-inplaceeditor')                    
                    //alert('invoiceId='+invoiceId);
					//alert(fraisId);
					var id = form.id;
					//alert(id);
	                id = id.substring(0,id.indexOf('_v-inplaceeditor'));
	                id = id.substring(id.lastIndexOf('_')+1,id.length);
					//alert(id);
                    return 'invoiceId='+invoiceId  + '&fraisId='+ id + '&translation='+encodeURIComponent(value);
                },
                onComplete:function(transport, element){//print the result using JSON
					window.location.reload();
                    var msg = 'enter value';
                    if (transport.responseText.evalJSON().translation == ' ' || transport.responseText.evalJSON().translation == null
                    || transport.responseText.evalJSON().translation == ''){
                        element.innerHTML = '<font color="red"> <b>' + msg + '</b></font>';
                    }
                    else {
                        element.innerHTML = transport.responseText.evalJSON().translation;
                    }
                },
                onFailure:function(transport) {
                    alert('You must enter a valid entry!');
                }
			}
			);
		}


		//deductions treatement
		var cells = $$('span.deduction_cell');      
        for (i=0; i<cells.length;i++){
            var keyAmount = cells[i].id + '_v';
            var deductionId=cells[i].id;
            new Ajax.InPlaceEditor(cells[i].id, '${ctx}/updateDeductionLibelle.htm',
            {
                rows:5,
                cols:50,
                cancelControl : "button",
                callback: function(form, value) {
                    var id = form.id;
                    id = id.substring(0,id.indexOf('-inplaceeditor'));
                    id = id.substring(id.lastIndexOf('_')+1,id.length);                  
                    return 'invoiceId='+invoiceId  + '&deductionId='+ id + '&translation='+encodeURIComponent(value);
                },
                onComplete:function(transport, element){//print the result using JSON
                    var msg = 'enter value';
                    if (transport.responseText.evalJSON().translation == ' ' || transport.responseText.evalJSON().translation == null
                    || transport.responseText.evalJSON().translation == ''){
                        element.innerHTML = '<font color="red"> <b>' + msg + '</b></font>';
                    }
                    else {
                        element.innerHTML = transport.responseText.evalJSON().translation;
                    }
                },
                onFailure:function(transport) {
                    alert('You must enter a valid entry!');
                }
            }

            );



         //amount frais pour deductions
        new Ajax.InPlaceEditor(keyAmount, '${ctx}/updateInvoiceDeductionValue.htm',
            {
                rows:1,
                cols:10,
                cancelControl : 'link',
                callback: function(form, value) {
					var id = form.id;
					//alert(id);
	                id = id.substring(0,id.indexOf('_v-inplaceeditor'));
	                id = id.substring(id.lastIndexOf('_')+1,id.length);
					//alert(id);
                    return 'invoiceId='+invoiceId  + '&deductionId='+ id + '&translation='+encodeURIComponent(value);
                },
                onComplete:function(transport, element){//print the result using JSON
                    window.location.reload();
                    var msg = 'enter value';
                    if (transport.responseText.evalJSON().translation == ' ' || transport.responseText.evalJSON().translation == null
                    || transport.responseText.evalJSON().translation == ''){
                        element.innerHTML = '<font color="red"> <b>' + msg + '</b></font>';
                    }
                    else {
                        element.innerHTML = transport.responseText.evalJSON().translation;
                    }
                },
                onFailure:function(transport) {
                    alert('You must enter a valid entry!');
                }
            }
            );
        }

		//Lib delai
		new Ajax.InPlaceEditor('libDelai', '${ctx}/updateInvoiceLibDelai.htm',
			{
				rows:5,
				cols:70,
				cancelControl : 'button',
                callback: function(form, value) {
                    return 'invoiceId='+invoiceId  + '&translation='+encodeURIComponent(value);
                },
                onComplete:function(transport, element){//print the result using JSON
                    var msg = 'enter value';
                    if (transport.responseText.evalJSON().translation == ' ' || transport.responseText.evalJSON().translation == null
                    || transport.responseText.evalJSON().translation == ''){
                        element.innerHTML = '<font color="red"> <b>' + msg + '</b></font>';
                    }
                    else {
                        element.innerHTML = transport.responseText.evalJSON().translation;
                    }
                },
                onFailure:function(transport) {
                    alert('You must enter a valid entry!');
                }
			}
			);

			//Lib honoraire
		new Ajax.InPlaceEditor('libHonoraires', '${ctx}/updateInvoiceLibHonoraires.htm',
			{
				rows:5,
				cols:70,
				cancelControl : 'button',
                callback: function(form, value) {
                    return 'invoiceId='+invoiceId  + '&translation='+encodeURIComponent(value);
                },
                onComplete:function(transport, element){//print the result using JSON
                    var msg = 'enter value';
                    if (transport.responseText.evalJSON().translation == ' ' || transport.responseText.evalJSON().translation == null
                    || transport.responseText.evalJSON().translation == ''){
                        element.innerHTML = '<font color="red"> <b>' + msg + '</b></font>';
                    }
                    else {
                        element.innerHTML = transport.responseText.evalJSON().translation;
                    }
                },
                onFailure:function(transport) {
                    alert('You must enter a valid entry!');
                }
			}
			);

			//adresse
		new Ajax.InPlaceEditor('addresse', '${ctx}/updateInvoiceAdresse.htm',
			{
				rows:5,
				cols:70,
				cancelControl : 'link',
                callback: function(form, value) {
                    return 'invoiceId='+invoiceId  + '&translation='+encodeURIComponent(value);
                },
                onComplete:function(transport, element){//print the result using JSON
                    var msg = 'enter value';
                    if (transport.responseText.evalJSON().translation == ' ' || transport.responseText.evalJSON().translation == null
                    || transport.responseText.evalJSON().translation == ''){
                        element.innerHTML = '<font color="red"> <b>' + msg + '</b></font>';
                    }
                    else {
                        element.innerHTML = transport.responseText.evalJSON().translation;
                    }
                },
                onFailure:function(transport) {
                    alert('You must enter a valid entry!');
                }
			}
			);


			//honoraires,tva,lang

				/*honoraires
		new Ajax.InPlaceEditor('honoraires', '${ctx}/updateInvoiceHonoraires.htm',
			{
				rows:1,
				cols:10,
				cancelControl : 'link',
                callback: function(form, value) {
                    return 'invoiceId='+invoiceId  + '&translation='+encodeURIComponent(value);
                },
                onComplete:function(transport, element){//print the result using JSON
					window.location.reload();
                    var msg = 'enter value';
                    if (transport.responseText.evalJSON().translation == ' ' || transport.responseText.evalJSON().translation == null
                    || transport.responseText.evalJSON().translation == ''){
                        element.innerHTML = '<font color="red"> <b>' + msg + '</b></font>';
                    }
                    else {
                        element.innerHTML = transport.responseText.evalJSON().translation;
                    }
                },
                onFailure:function(transport) {
                    alert('You must enter a valid entry!');
                }
			}
			);
			*/

				//tva
		new Ajax.InPlaceEditor('tva', '${ctx}/updateInvoiceTva.htm',
			{
				rows:1,
				cols:10,
				cancelControl : 'link',
                callback: function(form, value) {
                    return 'invoiceId='+invoiceId  + '&translation='+encodeURIComponent(value);
                },
                onComplete:function(transport, element){//print the result using JSON
					window.location.reload();
                    var msg = 'enter value';
                    if (transport.responseText.evalJSON().translation == ' ' || transport.responseText.evalJSON().translation == null
                    || transport.responseText.evalJSON().translation == ''){
                        element.innerHTML = '<font color="red"> <b>' + msg + '</b></font>';
                    }
                    else {
						//alert("toto");
                        element.innerHTML = transport.responseText.evalJSON().translation;
						window.location.reload();
                    }
                },
                onFailure:function(transport) {
                    alert('You must enter a valid entry!');
                }
			}
			);
			
			
			
			//facturation
        new Ajax.InPlaceEditor('facturation', '${ctx}/updateInvoiceDateFacturation.htm',
            {
                rows:1,
                cols:10,
                cancelControl : 'link',
                callback: function(form, value) {
                    return 'invoiceId='+invoiceId  + '&translation='+encodeURIComponent(value);
                },
                onComplete:function(transport, element){//print the result using JSON
                    window.location.reload();
                    var msg = 'enter value';
                    if (transport.responseText.evalJSON().translation == ' ' || transport.responseText.evalJSON().translation == null
                    || transport.responseText.evalJSON().translation == ''){
                        element.innerHTML = '<font color="red"> <b>' + msg + '</b></font>';
                    }
                    else {
                        //alert("toto");
                        element.innerHTML = transport.responseText.evalJSON().translation;
                        window.location.reload();
                    }
                },
                onFailure:function(transport) {
                    alert('You must enter a valid entry!');
                }
            }
            );

	}
	

	function addInPlaceEditorsForCustomer() {
		var invoiceId = '${invoice.id}';
		new Ajax.InPlaceCollectionEditor( 'customerName', '${ctx}/updateInvoiceCustomer.htm?invoiceId='+invoiceId, { 
				  loadCollectionURL: '${ctx}/loadCustomersForInvoice.htm?invoiceId='+invoiceId ,
                  callback: function(form, value) {
													//alert('invoiceId='+invoiceId);
													return 'invoiceId='+invoiceId  + '&value='+encodeURIComponent(value);
												},
                  onComplete:function(transport, element){//print the result using JSON
														window.location.reload();
														var msg = 'enter value';
														if (transport.responseText.evalJSON().translation == ' ' || transport.responseText.evalJSON().translation == null
														|| transport.responseText.evalJSON().translation == ''){
															element.innerHTML = '<font color="red"> <b>' + msg + '</b></font>';
														}
														else {
															element.innerHTML = transport.responseText.evalJSON().translation;
														}
													},
                  onFailure:function(transport) {alert('You must enter a valid entry!');}
													
												
					
				  }
				  
				  );
			
	
	}

	
	<c:if test='${invoice.approved eq false}'>  
						addInPlaceEditors();
						
	</c:if>
	addInPlaceEditorsForCustomer();

	
	
</script>

</body>
</html>





