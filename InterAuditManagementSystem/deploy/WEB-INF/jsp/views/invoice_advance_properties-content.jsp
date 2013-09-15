<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="t"      uri="/tags/tooltips-tiles" %>
<jsp:include page="common_css.jsp"/>

<!-- Begin Wrapper -->
<div id="wrapper">

  <!-- Begin Header -->
  <div id="header"><h4><span style="color:#039;">Facture</span> : <span style="color:orange;">${viewInvoiceDetails.reference}</span> </h4></div>
  <!-- End Header -->

  <!-- Begin Left Column -->
  <div id="leftcolumn"> 
	<div id="navcontainer">
       <ul id="navlist">
                                <!-- CSS Tabs -->
		<li><a id="current" href="${ctx}/showAdvancePropertyPage.htm">Details</a></li>
		<li><a  href="${ctx}/showInvoicePaymentPage.htm">Payments</a></li>
		<li><a  href="${ctx}/showInvoiceReminderPage.htm">Rappels</a>							
		<%--li><a  href="${ctx}/showInvoiceDocumentsPage.htm">Documents</a></li--%>

		</ul>
     </div>
  </div>
  <!-- End Left Column -->

  <!-- Begin Content Column -->
  <div id="content">

<div id="basic" class="myform" style="background-color: white;>
 <form id="form1" name="form1" method="post" action="">							
  <table id="hor-minimalist-b">
				
											<tr>
												<td align="left" width="40%" >Statut </td>
												
												<td align="center">
												<c:choose>

													<c:when test='${viewInvoiceDetails.status eq "pending" }'>
													 <span style="color:black;">A Envoyer</span>
													</c:when>

													<c:when test='${viewInvoiceDetails.status eq "sent" }'>
													 <span style="color:black;">Envoyé</span>
													</c:when>													

													<c:when test='${viewInvoiceDetails.status eq "paid" }'>
													 <span style="color:black;">Payée</span>
													</c:when>

													<c:when test='${viewInvoiceDetails.status eq "cancelled" }'>
													 <span style="color:black;">Annulé</span>
													</c:when>
													
									     		</c:choose>   
												</td>
											</tr>

											<tr>
												<td align="left" width="40%" >Type </td>
												
												<td align="center">
												
												<c:choose>
													<c:when test='${viewInvoiceDetails.type eq "AD" }'>
													 <span style="color:black;">AVANCE</span>
													</c:when>	

													<c:when test='${viewInvoiceDetails.type eq "FB" }'>
													 <span style="color:black;">FACTURE FINAL</span>
													</c:when>	

													<c:when test='${viewInvoiceDetails.type eq "CN" }'>
													 <span style="color:black;">NOTE DE CREDIT</span>
													</c:when>	
												 
									     		</c:choose>
												
												</td>
											</tr>

											<tr>
												<td align="left" width="40%" >Crée le </td>
												
												<td align="center"><span style="color:black;"> ${viewInvoiceDetails.dateFacture}</span></td>
											</tr>
											<tr>
												<td align="left" width="40%" >Envoyé le </td>
												
												<td align="center"><span style="color:black;"> ${viewInvoiceDetails.sentDate}</span></td>
											</tr>

											<tr>
												<td align="left" width="40%" >Payé le </td>
												
												<td align="center"><span style="color:black;"> ${viewInvoiceDetails.dateOfPayment}</span></td>
											</tr>
											
											<tr>
												<td align="left" width="40%" >Client </td>
												
												<td align="center"><span style="color:black;"> ${viewCustomerName}</span></td>
											</tr>

											<tr>
												<td align="left" width="40%" >Montant </td>
												
												<td align="center"><span style="color:black;"> ${viewInvoiceDetails.amountEuro}</span></td>
											</tr>


											<%--tr>
												<td align="left" width="40%" >Billing address </td>
												
												<td align="center"><span style="color:black;"> 11 rue robert parisot 57000 Metz</span></td>
											</tr>


											<tr>
												<td align="left" width="40%" >Contact person </td>
												
												<td align="center"><span style="color:black;">Monsieur &nbsp; Black &nbsp;Adams</span></td>
											</tr--%>
										</tbody>
								</table>

								
						
								
							
								<%--table id="hor-minimalist-b" summary="Employee Pay Sheet">
										<!--caption>Invoice summary</caption-->
										<thead>
											<tr>
												<th scope="col"></th>
												<th scope="col">To impute</th>
												<th scope="col">To deduct</th>												
											</tr>
										</thead>
										<tbody>
											
											<tr>
												<td  align="left" width="40%"><span style="color:orange;">Advances</span></td>
												<td  align="center">+ 1380  €</td>
												<td  align="center"><span style="color:black;"> </span></td>
												
											</tr>
											
											<tr>
												<th align="left" width="40%">Vat </th>
												<th align="center" colspan="2"><span style="color:black;">15%</span></th>
												
											</tr>	
											<tr>
												<th align="left" width="40%">Total to charge </th>
												<th align="center" colspan="2"><span style="color:black;">1587 €</span></th>
												
											</tr>											
										</tbody>
								</table--%>
								
							  </form> 
			
							</div> 
  
  
  </div>
  <!-- End Content Column -->
  <!-- Begin Right Column ->
  <div id="rightcolumn"> Right Column </div>
  <!-- End Right Column -->
  <!-- Begin Footer 
  <div id="footer"></div>
  <!-- End Footer -->
 </div>
<!-- End Wrapper -->
						
					


	