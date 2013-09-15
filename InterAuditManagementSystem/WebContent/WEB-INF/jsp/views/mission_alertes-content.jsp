<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
			<li><a href="${ctx}/showMissionFacturesPage.htm?id=${viewMissionDetails.mission.id}">
			Factures [<span style="color:black;"><strong>${ fn:length(viewMissionDetails.mission.sortedInvoices) }</strong></span>]</a></li>
		</interaudit:accessRightSet>
		<interaudit:accessRightSet right="CONSULT_RENTABILITY_FROM_MISSION">
			<li><a href="${ctx}/showMissionChartsPage.htm?id=${viewMissionDetails.mission.id}">Rentabilt&eacute;</a></li>
		</interaudit:accessRightSet>
		<interaudit:accessRightSet right="CONSULT_ALERTES_FROM_MISSION">
			<li><a id="current" href="${ctx}/showMissionAlertesPage.htm?id=${viewMissionDetails.mission.id}">
			Alertes[<span style="color:black;"><strong>${ fn:length(viewMissionDetails.mission.alertes) }</strong></span>]</a></li>
		</interaudit:accessRightSet>
		
		

		</ul>
     </div>

	<c:if test="${viewMissionDetails.mission.status eq 'ONGOING'}">
		<interaudit:accessRightSet right="ADD_ALERTES_FROM_MISSION">
		 <br/>
		 <div id="navcontainer">
		   <ul id="navlist">
				<li><a  id="show" onclick="resetForm()" href="#">Add Alerte</a></li>
			</ul>
		 </div>
		</interaudit:accessRightSet>
	</c:if>
		
  </div>
  <!-- End Left Column -->

  <!-- Begin Content Column -->
  <%--div id="content"--%>
<div style="float: left;width:80%;">

  <table id="hor-minimalist-b" summary="Employee Pay Sheet">
										<thead>
											<tr>
												<th scope="col">Employ&eacute;</th>
												<th scope="col">code</th>
												<th scope="col">date creation</th>
												<th scope="col">description</th>												
												<th scope="col">montant</th>
												<th scope="col">statut</th>
												<th scope="col">date envoi</th>
												<th scope="col">actions</th>								
											</tr>
										</thead>
										<tbody>
										<c:choose>
												<c:when test='${ fn:length(viewMissionDetails.mission.alertes) == 0}'>
													<tr><td align="center" colspan="5"><span  style="color:red;font-weight: bold;">Pas d'alertes enregistr&acute;es... </span></td></tr>													
												</c:when>
										
										        <c:otherwise>
													<c:forEach var="item" items="${viewMissionDetails.mission.alertes}" >
															<tr>
																<td style="border-bottom: 1px dotted #ccc;background-color:#ffffcc;"><span style="color:purple;">${item.owner.lastName}</span></td>
																<td>${item.code}</td>    
																<td style="border-bottom: 1px dotted #ccc;background-color:#ffffcc;">
																    <fmt:formatDate value="${item.createDate}" pattern="dd/MM/yyyy"/>
																</td>
																<td>${item.description}</td>												
																<td style="border-bottom: 1px dotted #ccc;background-color:#ffffcc;">
																	<fmt:formatNumber type="currency" currencySymbol="&euro;">
																			${item.amount}
																	</fmt:formatNumber>
																</td>																
				                                                <td>
					                                                <c:choose>
				                                                        <c:when test='${item.status eq "ACTIVE" }'>                                                
				                                                            <span style=" color: green;">${item.status}</span>
				                                                        </c:when>
				                                                        <c:when test='${item.status eq "CANCELLED" }'>                                             
				                                                            <span style=" color: black;">${item.status}</span>
				                                                        </c:when>
				                                                        <c:when test='${item.status eq "CLOSED" }'>                                             
				                                                            <span style=" color: red;">${item.status}</span>
				                                                        </c:when>
				                                                        <c:otherwise>
				                                                            <span style=" color: green;">${item.status}</span>
				                                                        </c:otherwise>
				                                                    </c:choose>				                                                
				                                                </td>
				                                                <td style="border-bottom: 1px dotted #ccc;background-color:#ffffcc;">
                                                                     <fmt:formatDate value="${item.sentDate}" pattern="dd/MM/yyyy"/>
                                                                </td>
																<td>
																	<c:if test='${ item.sentDate == null}'>
																		<a href="#">
																		   <img src="images/Table-Edit.png" border="0" alt="Edit cost" onclick="editAlerte(${item.id})"/>
																		</a>																
																	    <a href="#">
																		   <img src="images/Table-Delete.png" border="0" alt="Delete Cost" onclick="deleteAlerte(${item.id},${viewMissionDetails.mission.id})"/>
																		</a>
																	</c:if>
																</td>															
															</tr>											
											       </c:forEach>
										      </c:otherwise>
										</c:choose>		
										</tbody>
									</table>
  </div>

<script type="text/javascript">
	$(function() {
	$('#datepicker').datepicker({
		showOn: 'button', buttonImage: 'images/calbtn.gif', buttonImageOnly: false,
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
        <span id="formTitle" style="color:#039;">Add alerte</span>
    </div>
	<div id="container">
	    <form name="addAlerteForm" action="${ctx}/handleMissionAlertePage.htm" method="post" class="niceform">
			<input type="hidden" name="alerteId" />
			<input type="hidden" name="id" value="${viewMissionDetails.missionId}"/>
		    <fieldset>
				 <dl>
					<dt><label for="password">Montant:</label></dt>
		            <dd><input type="text" name="amount"  value="5 000" id="amount" size="32" maxlength="32" /></dd>
				</dl>
		    </fieldset>
		    <fieldset>
				<dl>
		        	<dt><label for="description">Description:</label></dt>
		            <dd><textarea name="description" id="description" rows="3" cols="60"></textarea></dd>
				 </dl>
		    </fieldset>       
	    </form>
    </div>	
</div>
  
  <!-- End Footer -->
 </div>
<!-- End Wrapper -->
						
					


	