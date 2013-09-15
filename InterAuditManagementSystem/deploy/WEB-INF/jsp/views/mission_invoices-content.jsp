<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib  prefix="fmt"    uri="http://java.sun.com/jstl/fmt"%>
<%@ taglib uri="/tags/interaudit" prefix="interaudit" %>
<jsp:include page="common_css.jsp"/>

<!-- Begin Wrapper -->
<div id="wrapper">

  <!-- Begin Header -->
  <jsp:include page="missionDetailsHeader.jsp"/>
  <!-- End Header -->

  <!-- Begin Left Column -->
  <div id="leftcolumn"> 
	<div id="navcontainer">
       <ul id="navlist">
                                <!-- CSS Tabs -->
		
			

		<interaudit:accessRightSet right="CONSULT_COMMUNICATIONS_FROM_MISSION">
		<li><a href="${ctx}/showMissionCommunicationsPage.htm?id=${viewMissionDetails.mission.id}">
		Communications [<span style="color:black;"><strong>${ fn:length(viewMissionDetails.mission.sortedMessages) }</strong></span>]</a></li>
		</interaudit:accessRightSet>
		<interaudit:accessRightSet right="CONSULT_DOCUMENTS_FROM_MISSION">
		<li><a  href="${ctx}/showMissionDocumentsPage.htm?id=${viewMissionDetails.mission.id}">
		Documents [<span style="color:black;"><strong>${ fn:length(viewMissionDetails.mission.documents)}</strong></span>]</a></li>	
		</interaudit:accessRightSet>
		<interaudit:accessRightSet right="CONSULT_HEURES_PRESTEES_FROM_MISSION">
			<li><a href="${ctx}/showMissionBudgetPage.htm?id=${viewMissionDetails.mission.id}">
			Heures Prest&eacute;es [<span style="color:black;"><strong>${ fn:length(viewMissionDetails.budgets) + fn:length(viewMissionDetails.budgetNotValidated) }</strong></span>]</a></li>
		</interaudit:accessRightSet>	
		<interaudit:accessRightSet right="CONSULT_EXPENSES_FROM_MISSION">
			<li><a  href="${ctx}/showMissionDepensesPage.htm?id=${viewMissionDetails.mission.id}">
			Note de frais[<span style="color:black;"><strong>${ fn:length(viewMissionDetails.mission.allExtraCosts) }</strong></span>]</a></li>
		</interaudit:accessRightSet>
		<interaudit:accessRightSet right="CONSULT_INVOICES_FROM_MISSION">
			<li><a id="current" href="${ctx}/showMissionFacturesPage.htm?id=${viewMissionDetails.mission.id}">
			Factures [<span style="color:black;"><strong>${ fn:length(viewMissionDetails.mission.sortedInvoices) }</strong></span>]</a></li>
		</interaudit:accessRightSet>
		<interaudit:accessRightSet right="CONSULT_RENTABILITY_FROM_MISSION">
			<li><a href="${ctx}/showMissionChartsPage.htm?id=${viewMissionDetails.mission.id}">Rentabilt&eacute;</a></li>
		</interaudit:accessRightSet>
		<interaudit:accessRightSet right="CONSULT_ALERTES_FROM_MISSION">
			<li><a  href="${ctx}/showMissionAlertesPage.htm?id=${viewMissionDetails.mission.id}">
			Alertes[<span style="color:black;"><strong>${ fn:length(viewMissionDetails.mission.alertes) }</strong></span>]</a></li>
		</interaudit:accessRightSet>
		
		

		</ul>
     </div>

	 <%--br/>

	 <div id="navcontainer">
       <ul id="navlist">
							<li><a  id="show" onclick="resetForm()" href="#">Create Invoice</a></li>
		</ul>
     </div--%>
  </div>
  <!-- End Left Column -->

  <!-- Begin Content Column -->
  <%--div id="content"--%>
<div style="float: left;width:80%;">
  <table id="hor-minimalist-b" summary="Employee Pay Sheet">
	<%--caption><span style="color:blue;">Listes des factures enregistr�es </span></caption--%>
										<thead>
											<tr>
												<th align="center" scope="col">Reference</th>
												<th align="center" scope="col">Exercise</th>
												<th align="center" scope="col">Mandat</th>
												<th align="center" scope="col">Created date</th>
												<th align="center" scope="col">Sent date</th>
												<th align="center" scope="col">Paid date</th>
												<th align="center" scope="col">Status</th>
												<th align="center" scope="col">Libelle</th>
												<th align="center" scope="col">Type</th>
												<th align="center" scope="col">Amount</th>
												
												
											</tr>
										</thead>
										<tbody>


										<c:set var="totalFacture" value="0"/>

										<c:choose>
										<c:when test='${ fn:length(viewMissionDetails.mission.sortedInvoices) == 0}'>
											<tr><td align="center" colspan="9"><span  style="color:red;font-weight: bold;">Pas de factures... </span></td></tr>													
										</c:when>									
										<c:otherwise>
											<c:forEach var="item" items="${viewMissionDetails.mission.sortedInvoices}" >
											<tr>
										 		<td align="center"><a href="${ctx}/editInvoicePage.htm?invoiceId=${item.id}"><span style="color:purple;">${item.reference}</span></a></td>
												<td style="border-bottom: 1px dotted #ccc;background-color:#ffffcc;" align="center">${item.exercise}</td>
												<td style="border-bottom: 1px dotted #ccc;background-color:#ffffcc;" align="center">${item.exerciseMandat}</td>
												<td style="border-bottom: 1px dotted #ccc;background-color:#ffffcc;" align="center">${item.dateFacture}</td>
												<td style="border-bottom: 1px dotted #ccc;background-color:#ffffcc;" align="center">${item.sentDate}</td>
												<td style="border-bottom: 1px dotted #ccc;background-color:#ffffcc;" align="center">${item.dateOfPayment}</td>
												<td style="border-bottom: 1px dotted #ccc;background-color:#ffffcc;" align="center">${item.status}</td>
												<td style="border-bottom: 1px dotted #ccc;background-color:#ffffcc;" align="center">${item.libelle}</td>
												<td style="border-bottom: 1px dotted #ccc;background-color:#ffffcc;" align="center">
												<c:choose>
									    		<c:when test='${item.type eq "AD" }'>
												 Avance
									      		</c:when>
												<c:when test='${item.type eq "CN" }'>
												 Note de credit
									      		</c:when>
												<c:when test='${item.type eq "SP" }'>												
												 Supplementaire
												</c:when>
									      		<c:otherwise>
												Facture finale
									      		</c:otherwise>
									     	</c:choose>
												</td>
												<td style="border-bottom: 1px dotted #ccc;background-color:#ffffcc;" align="center">
												
												<%--fmt:formatNumber type="currency" currencySymbol="&euro;">
														${item.amountNetEuro}
												</fmt:formatNumber--%>

												<c:choose>
													<c:when test='${item.type eq "CN" }'>												
													 <fmt:formatNumber type="currency" currencySymbol="&euro;">
															${item.amountNetEuro *-1}
													</fmt:formatNumber>
													</c:when>
													<c:otherwise>
													<fmt:formatNumber type="currency" currencySymbol="&euro;">
															${item.amountNetEuro}
													</fmt:formatNumber>
													</c:otherwise>
												</c:choose>
												</td>
												
												
											</tr>
										
										
												<c:choose>
												    <c:when test='${item.type eq "CN" }'>
												      <c:set var="totalFacture" value="${totalFacture - item.amountNetEuro}"/>
													</c:when>
													<c:otherwise>
													   <c:set var="totalFacture" value="${totalFacture + item.amountNetEuro}"/>
													</c:otherwise>																									
												</c:choose>
									  </c:forEach>
										</c:otherwise>
										</c:choose>
										

									  <thead>
											<tr>
												<th scope="col" colspan="9" style="border-right: 1px solid #ccc;">Total des factures</th>
												<th align="center" scope="col" style="background-color:#FF6262;">
												<fmt:formatNumber type="currency" currencySymbol="&euro;">
														${totalFacture}
												</fmt:formatNumber>
												</th>
											</tr>
										</thead>

										<%--thead>
											<tr>
												<th scope="col" colspan="8" style="border-right: 1px solid #ccc;">Total des d�penses</th>
												<th align="center" scope="col">
													<c:set var="totalCost" value="0"/>
													<c:forEach var="item" items="${viewMissionDetails.mission.extraCosts}" >
														<c:set var="totalCost" value="${totalCost + item.amount}"/>
													</c:forEach>
												${totalCost}</th>
												
											</tr>
										</thead>

										<thead>
											<tr>
												<th scope="col" colspan="8" style="border-right: 1px solid #ccc;">Solde total � facturer</th>
												<th align="center" scope="col">
												${viewMissionDetails.mission.annualBudget.expectedAmount} - ${ totalFacture} -
												${totalCost} =
												${viewMissionDetails.mission.annualBudget.expectedAmount - totalFacture - totalCost}
												</th>
												
											</tr>
										</thead--%>
											
											
										</tbody>
									</table>
  
  
  
  </div>

  <script type="text/javascript">
	$(function() {
	$('#datepicker').datepicker({
	changeMonth: true,
		changeYear: true
	});
	});

$(function() {
	$('#datepicker2').datepicker({
	changeMonth: true,
		changeYear: true
	});
	});
</script>


  <style>
#ver-zebra2
{
	font-family: "Lucida Sans Unicode", "Lucida Grande", Sans-Serif;
	font-size: 10px;
	text-align: center;
}
#ver-zebra2 th
{
	font-size: 12px;
	font-weight: normal;
	
	color: #039;
	background: #eff2ff;
}
#ver-zebra2 td
{
	color: #669;
}
</style>


<div id="addDialog">
	<div class="hd">
        <span id="formTitle" style="color:#039;">Add invoice</span>
    </div>

	<div id="container">

	<form action="${ctx}/handleMissionInvoicePage.htm" method="post" name="addInvoiceForm" class="niceform">
	<input type="hidden" name="invoiceId" />
	<input type="hidden" name="id" value="${viewMissionDetails.missionId}"/>
<fieldset>
<hr/>

    	<legend>Invoice</legend>
        <dl>
        	<dt><label for="libelle">Libelle:</label></dt>
            <dd><textarea name="libelle" id="libelle" rows="3" cols="60">Request for advance for mission</textarea></dd>
		 </dl>

		<dl>
			<dt><label for="exercise">Exercise:</label></dt>
            <dd>
			<input type="text" name="exercise"  value="${viewMissionDetails.mission.exercise}" id="exercise" size="10" maxlength="32" readOnly/>
			<%--select size="1" name="exercise" id="exercise">
					<c:forEach var="y" items="${viewMissionDetails.exercicesOptions}">
						<option value="${y.id}">${y.name}</option>
					</c:forEach>
                </select--%>
				
			</dd>
		</dl>

		<dl>
			<dt><label for="type">Type:</label></dt>
            <dd><select size="1" name="type" id="type">
                    <option value="AD">Advance</option>
                    <option value="CN">Note de credit</option>
            	</select>
			</dd>
		</dl>

		<dl>
			<dt><label for="amount">Amount:</label></dt>
            <dd><input type="text" name="amount"  value="5 000" id="amount" size="32" maxlength="32" /></dd>
		</dl>

		<dl>
			<dt><label for="currency">Currency:</label></dt>
            <dd><select size="1" name="currency" id="currency">
                    <option value="EUR">Euro</option>
            	</select>
			</dd>
		</dl>
    </fieldset>
    
    <fieldset>
    	<legend>Administration</legend>
        <dl>
			<dt><label for="status">Status:</label></dt>
            <dd>
			<input type="text" name="status"  value="Pending" id="status" size="10" maxlength="32" readOnly/>
            	<%--select size="1" name="status" id="status">
				<option value="pending">Pending</option>
                    <option value="ongoing">Sent</option>
                    <option value="paid">Paid</option>
                    <option value="cancelled">Cancelled</option>
            	</select--%>
            </dd>
		</dl>
    </fieldset>
    
</form>
	


</div>

   
	
</div>

  
 </div>
<!-- End Wrapper -->


						
					


	