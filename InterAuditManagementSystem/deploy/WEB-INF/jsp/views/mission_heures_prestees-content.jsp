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
		
			<%--li><a href="${ctx}/showMissionPropertiesPage.htm?id=${viewMissionDetails.mission.id}">Proprietes</a></li--%>
		<li><a href="${ctx}/showMissionCommunicationsPage.htm?id=${viewMissionDetails.mission.id}">Communications</a></li>
		<li><a href="${ctx}/showMissionDocumentsPage.htm?id=${viewMissionDetails.mission.id}">Documents</a></li>		
		<interaudit:accessRightSet right="MODIFY_MISSIONS">
			<%--li><a href="${ctx}/showMissionInterventionsPage.htm?id=${viewMissionDetails.mission.id}">Activités</a></li--%>
			<li><a href="${ctx}/showMissionBudgetPage.htm?id=${viewMissionDetails.mission.id}">Budgets</a></li>
			<li><a id="current" href="${ctx}/showMissionHeuresPresteesPage.htm?id=${viewMissionDetails.mission.id}">Heures prestees</a></li>
			<li><a href="${ctx}/showMissionDepensesPage.htm?id=${viewMissionDetails.mission.id}">Depenses</a></li>
			<li><a href="${ctx}/showMissionFacturesPage.htm?id=${viewMissionDetails.mission.id}">Factures</a></li>
			<%--li><a href="${ctx}/showMissionChartsPage.htm?id=${viewMissionDetails.mission.id}">Graphiques</a></li--%>
		</interaudit:accessRightSet>
		

		</ul>
     </div>
  </div>
  <!-- End Left Column -->

  <!-- Begin Content Column -->
   <%--div id="content"--%>
<div style="float: left;width:87%;">

  <table id="hor-minimalist-b" summary="Employee Pay Sheet">
									<%--caption><span style="color:blue;">Listes des heures prestées enregistrées </span></caption--%>
										<thead>
											<tr>
												<th align="center" scope="col">Employee</th>
												<th align="center" scope="col">Date prestation</th>
												<th align="center" scope="col">Code prestation</th>
												<th align="center" scope="col">Année de clôture</th>
												<th align="center" scope="col">Heure prestees</th>
												<th align="center" scope="col">Prix de revient</th>
												<th align="center" scope="col">Prix de vente</th>
												
											</tr>
										</thead>
										<tbody>
										
										<c:set var="totalHours" value="0"/>
										<c:set var="totalPrixRevient" value="0"/>
										<c:set var="totalPrixVente" value="0"/>
										<c:forEach var="item" items="${viewMissionDetails.heuresPrestees}">
												<tr>
												<td align="center"><span style="color:purple;">${item.employeeName}</span></td>
												<td align="center">
												<c:choose>
														<c:when test='${item.datePrestation != null }'>
														 	<fmt:formatDate value="${item.datePrestation}" pattern="dd-MMMM"/>
														</c:when>
														<c:otherwise>
														 --
														</c:otherwise>
													</c:choose>
												</td>
												<td align="center">${item.codePrestation}</td>
												<td  align="center">${item.exercise}</td>
												<td  align="center">${item.hours}h</td>
												<td  align="center">
												<fmt:formatNumber type="currency" currencySymbol="&euro;">
														${item.prixRevient}
												</fmt:formatNumber>
												</td>
												<td  align="center"><span style="color:red;">
												<fmt:formatNumber type="currency" currencySymbol="&euro;">
														${item.prixVente}
												</fmt:formatNumber>
												</span></td>
											</tr>

											<c:set var="totalHours" value="${item.hours + totalHours}"/>
											<c:set var="totalPrixRevient" value="${item.prixRevient + totalPrixRevient}"/>
											<c:set var="totalPrixVente" value="${item.prixVente + totalPrixVente}"/>
										</c:forEach>
										
										<tr>
											<th align="center" colspan="4"></th>
											<th  align="center">${totalHours}h</th>
											<th  align="center">
												<fmt:formatNumber type="currency" currencySymbol="&euro;">
														${totalPrixRevient}
												</fmt:formatNumber>
											</th>
											<th  align="center">
											<fmt:formatNumber type="currency" currencySymbol="&euro;">
														${totalPrixVente}
												</fmt:formatNumber>
											</th>
										</tr>
											
										</tbody>
									</table>
  
  
  
  </div>

  
  <!-- End Footer -->
 </div>
<!-- End Wrapper -->
						
					


	