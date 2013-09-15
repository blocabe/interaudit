<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="/tags/interaudit" prefix="interaudit" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:include page="common_css.jsp"/>
<fmt:setLocale value="fr" scope="session"/>

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
			<li><a id="current" href="${ctx}/showMissionBudgetPage.htm?id=${viewMissionDetails.mission.id}">
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
			<li><a  href="${ctx}/showMissionAlertesPage.htm?id=${viewMissionDetails.mission.id}">
			Alertes[<span style="color:black;"><strong>${ fn:length(viewMissionDetails.mission.alertes) }</strong></span>]</a></li>
		</interaudit:accessRightSet>
		

		</ul>
     </div>
  </div>
  <!-- End Left Column -->

  <!-- Begin Content Column -->
   <%--div id="content"--%>
<div style="float: left;width:80%;">
  <table id="hor-minimalist-b" summary="Employee Pay Sheet" width="100%">
										<thead>
											<tr>
											    <th align="center" scope="col">Validated</th>
											    <th align="center" scope="col">Code</th>
                                                <%--th align="center" scope="col">Mis</th--%>
                                                <th align="center" scope="col">Ts</th>
                                                
												<th align="center" scope="col">Employ&eacute;</th>
												<th align="center" scope="col">Profile</th>
												<th align="center" scope="col">N&ordm;&nbsp;Semaine</th>
												<th align="center" scope="col">Ann&eacute;e Prestation</th>
												<th align="center" scope="col">Ann&eacute;e Audit&eacute;e</th>
												<th align="center" scope="col">Prix revient</th>
												<th align="center" scope="col">Prix vente</th>
												<th align="center" scope="col">Heures</th>
												<!--th align="center" scope="col">Valid�e</th-->
												
												<th align="center" scope="col">Total prix revient</th>
												<th align="center" scope="col">Total prix vente</th>
											</tr>
										</thead>
										<tbody>
											<c:set var="totalPrixRevient" value="0"/>
											<c:set var="totalPrixVente" value="0"/>
											<c:set var="totalHeures" value="0"/>
											<c:set var="totalHeuresValidees" value="0"/>

											<c:choose>
										<c:when test='${ (fn:length(viewMissionDetails.budgets) == 0 )&& (fn:length(viewMissionDetails.budgetNotValidated) == 0 )}'>
											<tr><td align="center" colspan="7"><span  style="color:red;font-weight: bold;">Pas d'heures prestées... </span></td></tr>													
										</c:when>
										
										<c:otherwise>
											<c:if test='${fn:length(viewMissionDetails.budgets) != 0 }'>
										     <tr><th align="center" colspan="13" style="color:balck;background-color:#90EE90;"><strong>TS Validées [${fn:length(viewMissionDetails.budgets)}]</strong></th></tr>                                          
											 <c:forEach var="item" items="${viewMissionDetails.budgets}" >
												<tr>
												    <td  align="center"><span style="color:purple;"> <img src="images/accept.gif" border="0"/></span></td>
												    <td  align="center"><span style="color:purple;">${item.typeMission}</span></td>												    
												    <td  align="center"><span style="color:purple;">${item.codePrestation}</span></td>												    
													<td  align="center"><span style="color:purple;">${item.name}</span></td>
													<td style="border-bottom: 2px  #ccc;background-color:#ffffcc;" align="center">${item.profile}</td>
													<td align="center">${item.weekNumber}</td>
													<td align="center">${item.exercise}</td>
													<td style="border-bottom: 1px dotted #ccc;background-color:#D3F9BC;" align="center">${item.year}</td>
													<td style="border-bottom: 1px dotted #ccc;background-color:#ffffcc;" align="center">${item.rate}</td>
													<td style="border-bottom: 1px dotted #ccc;background-color:#ffffcc;" align="center">${item.prixVente}</td>
													<td style="border-bottom: 1px dotted #ccc;background-color:#ffffcc;" align="center">${item.hours} h </td>
													
													
													
													<td style="border-bottom: 1px dotted #ccc;background-color:#ffffcc;" align="center">													
													<fmt:formatNumber type="currency" currencySymbol="&euro;">
															${item.hours * item.rate}
													</fmt:formatNumber>
													</td>
													<td style="border-bottom: 1px dotted #ccc;background-color:#ffffcc;" align="center">
													<fmt:formatNumber type="currency" currencySymbol="&euro;">
															${item.hours * item.prixVente}
													</fmt:formatNumber>
													</td>
												</tr>
												<c:set var="totalHeures" value="${item.hours + totalHeures}"/>
												
												<c:set var="totalPrixRevient" value="${(item.hours * item.rate) + totalPrixRevient}"/>
												<c:set var="totalPrixVente" value="${(item.hours * item.prixVente) + totalPrixVente}"/>
											 </c:forEach>
											</c:if>
											
											<c:if test='${fn:length(viewMissionDetails.budgetNotValidated) != 0 }'>
											<tr><th align="center" colspan="13" style="color:balck;background-color:#FFA07A;"><strong>TS Non Validées [${fn:length(viewMissionDetails.budgetNotValidated)}]</strong></th></tr>                                          
											<c:forEach var="item" items="${viewMissionDetails.budgetNotValidated}" >
                                                <tr>
                                                    <td  align="center"><span style="color:purple;"> <img src="images/cancel.gif" border="0"/></span></td>
                                                    <td  align="center"><span style="color:purple;">${item.typeMission}</span></td>                                                 
                                                    <td  align="center"><span style="color:purple;">${item.codePrestation}</span></td>                                                  
                                                    <td  align="center"><span style="color:purple;">${item.name}</span></td>
                                                    <td style="border-bottom: 2px  #ccc;background-color:#ffffcc;" align="center">${item.profile}</td>
                                                    <td align="center">${item.weekNumber}</td>
                                                    <td align="center">${item.exercise}</td>
                                                    <td style="border-bottom: 1px dotted #ccc;background-color:#D3F9BC;" align="center">${item.year}</td>
                                                    <td style="border-bottom: 1px dotted #ccc;background-color:#ffffcc;" align="center">${item.rate}</td>
                                                    <td style="border-bottom: 1px dotted #ccc;background-color:#ffffcc;" align="center">${item.prixVente}</td>
                                                    <td style="border-bottom: 1px dotted #ccc;background-color:#ffffcc;" align="center">${item.hours} h </td>
                                                    
                                                    
                                                    
                                                    <td style="border-bottom: 1px dotted #ccc;background-color:#ffffcc;" align="center">                                                    
                                                    <fmt:formatNumber type="currency" currencySymbol="&euro;">
                                                            ${item.hours * item.rate}
                                                    </fmt:formatNumber>
                                                    </td>
                                                    <td style="border-bottom: 1px dotted #ccc;background-color:#ffffcc;" align="center">
                                                    <fmt:formatNumber type="currency" currencySymbol="&euro;">
                                                            ${item.hours * item.prixVente}
                                                    </fmt:formatNumber>
                                                    </td>
                                                </tr>
                                                <c:set var="totalHeures" value="${item.hours + totalHeures}"/>
                                                
                                                <c:set var="totalPrixRevient" value="${(item.hours * item.rate) + totalPrixRevient}"/>
                                                <c:set var="totalPrixVente" value="${(item.hours * item.prixVente) + totalPrixVente}"/>
                                            </c:forEach>
											</c:if>
										</c:otherwise>
										</c:choose>

											
										<tr>
											<th align="center" colspan="10"></th>
											<th align="center">${totalHeures} h</th>
											<%--th align="center">${totalHeuresValidees} h</th--%>																				
											<th align="center">
												<fmt:formatNumber type="currency" currencySymbol="&euro;">
														${totalPrixRevient}
												</fmt:formatNumber>												
											</th>
											<th align="center" >
											<fmt:formatNumber type="currency" currencySymbol="&euro;">
													${totalPrixVente}
											</fmt:formatNumber>
											</th>
										</tr>
										<tr>
											<th align="center" colspan="11">Estimation budget generale </th>
											<th align="center" colspan="2" style="background-color:#FF6262;">
											<fmt:formatNumber type="currency" currencySymbol="&euro;">
														${viewMissionDetails.mission.annualBudget.expectedAmount + viewMissionDetails.mission.annualBudget.reportedAmount}
												</fmt:formatNumber>	
											</th>												
										</tr>
											<!--tr>
												<th align="center" colspan="11">Percentage </th>
												<th align="center" colspan="2">
												<c:if test='${totalPrixRevient !=0}'> 
												<fmt:formatNumber  >
															${((viewMissionDetails.mission.annualBudget.expectedAmount + viewMissionDetails.mission.annualBudget.reportedAmount) / totalPrixRevient ) * 100}
														</fmt:formatNumber>
														 %
												</c:if>
												</th>												
											</tr-->
										</tbody>
									</table>
  </div>
  <!-- End Content Column -->
  <!-- Begin Right Column 
  <div id="rightcolumn"></div>
  <!-- End Right Column -->
  <!-- Begin Footer >
  <div id="footer"></div>
  <!-- End Footer -->
 </div>
<!-- End Wrapper -->
						
					


	