
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="/tags/interaudit" prefix="interaudit" %>
<%@ taglib prefix="fmt"     uri="http://java.sun.com/jstl/fmt" %>
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
                                <!-- CSS Tabs 
-->
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
			<li><a id="current" href="${ctx}/showMissionChartsPage.htm?id=${viewMissionDetails.mission.id}">Rentabilt&eacute;</a></li>
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
  <div id="content">

	
	  <table id="hor-minimalist-b" summary="Employee Pay Sheet" width="80%">
	  <tr>
		<th align="center"  colspan="2">
		<img src="${ctx}/showMissionBartChartPage.htm?id=${viewMissionDetails.mission.id}" border="1" WIDTH=1000 HEIGHT=400/>
		
		</th>												
	 </tr>
	  <tr>
		<th align="center" style="background-color:grey;" width="70%"><strong>PRIX DE REVIENT</strong></th>
		<th align="center" style="background-color:white;"><strong>
		<fmt:formatNumber  type="currency" currencySymbol="&euro;">
					${viewMissionDetails.statistiques.prixRevient}
		</fmt:formatNumber></strong>
		</th>												
	 </tr>
	   <tr>
		<th align="center"  width="70%"><strong>PRIX DE VENTE</strong> </th>
		<th align="center" style="background-color:white;"><strong>	
		<fmt:formatNumber  type="currency" currencySymbol="&euro;">
					${viewMissionDetails.statistiques.prixVente}
		</fmt:formatNumber></strong>
		</th>												
	 </tr>
	  <tr>
		<th align="center" style="background-color:grey;" width="70%"><strong>TOTAL FACTURES</strong> </th>
		<th align="center" style="background-color:white;"><strong>
		<fmt:formatNumber  type="currency" currencySymbol="&euro;">
					${viewMissionDetails.statistiques.prixFacture}
		</fmt:formatNumber></strong>
		</th>												
	 </tr>
	  
	  <tr>
		<th align="center" width="70%"><strong>REALISATION</strong></th>
		<c:choose>
			<c:when test='${viewMissionDetails.statistiques.realisation < 0 }'>												
			 <c:set var="backgroundStyle" value="color:black;background-color:#FF6262;font-weight: bold;"/>
			</c:when>
			<c:otherwise>
			 <c:set var="backgroundStyle" value="color:black;background-color:#55FF55;font-weight: bold;"/>
			</c:otherwise>
		</c:choose>
		<th align="center" style="${backgroundStyle}"><strong>
		
		<fmt:formatNumber  type="percent" groupingUsed="false">
					${viewMissionDetails.statistiques.realisation}
		</fmt:formatNumber>		
		</strong>
		</th>												
	 </tr>
	 <tr>
		<th align="center"  style="background-color:grey;" width="70%"><strong>MARGE</strong></th>
		<c:choose>
			<c:when test='${viewMissionDetails.statistiques.percentageMargePrixRevient < 0 }'>												
			 <c:set var="backgroundStyle2" value="color:black;background-color:#FF6262;font-weight: bold;"/>
			</c:when>
			<c:otherwise>
			 <c:set var="backgroundStyle2" value="color:black;background-color:#55FF55;font-weight: bold;"/>
			</c:otherwise>
		</c:choose>
		<th align="center" style="${backgroundStyle2}"><strong>
		<fmt:formatNumber  type="percent" groupingUsed="false">
					${viewMissionDetails.statistiques.percentageMargePrixRevient}
		</fmt:formatNumber>	
		</strong>
		</th>												
	 </tr>
	</table>

</div>


<!-- End Wrapper -->
						
					


	