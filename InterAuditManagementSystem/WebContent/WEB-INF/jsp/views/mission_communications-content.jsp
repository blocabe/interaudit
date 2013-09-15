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
		<li><a id="current" href="${ctx}/showMissionCommunicationsPage.htm?id=${viewMissionDetails.mission.id}">
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
			<li><a  href="${ctx}/showMissionAlertesPage.htm?id=${viewMissionDetails.mission.id}">
			Alertes[<span style="color:black;"><strong>${ fn:length(viewMissionDetails.mission.alertes) }</strong></span>]</a></li>
		</interaudit:accessRightSet>
		</ul>
     </div>
	 
	 <c:if test="${viewMissionDetails.mission.status eq 'ONGOING'}">
		<interaudit:accessRightSet right="ADD_COMMUNICATIONS_FROM_MISSION">
			<br/>
			 <div id="navcontainer">
			   <ul id="navlist">
					<li><a  id="show" onclick="resetForm()" href="#">Add Communication</a></li>
				</ul>
			 </div>
		</interaudit:accessRightSet>
	</c:if>
	

	
  </div>
  <!-- End Left Column -->

  <!-- Begin Content Column -->
  <%--div id="content"--%>

  <div style="float: left;width: 80%">
  
  <c:choose>
			<c:when test='${ fn:length(viewMissionDetails.mission.sortedMessages) == 0}'>
				<tr><td align="center" colspan="9"><span  style="color:red;font-weight: bold;">Pas de communications... </span></td></tr>                                                 
			</c:when>
			
			<c:otherwise>

				<c:set var="row" value="0"/>
				<c:forEach var="item" items="${viewMissionDetails.mission.sortedMessages}" >	

				<c:choose>
					<c:when test='${row % 2 eq 0 }'>
						<div  style="background-color:#CCDFE2;width: 100%;border:1px solid #333B66;">							      			
					</c:when>
					<c:otherwise>					 
						<div style="background-color:#EEEEDD;width: 100%;border:1px solid #333B66;">
					</c:otherwise>
				</c:choose>
			
				<div style="text-decoration: underline;color: black;">
					  <fmt:formatDate value="${item.createDate}" type="both" dateStyle="short"/>
					  &nbsp;|&nbsp; Posted by : &nbsp;
						${item.from.firstName},&nbsp; ${item.from.lastName} 
						[<span  style="color:red;font-weight: bold;">${item.subject}</span>]
				 </div>

			 
				
				<div  style="color: blue;margin-left:10px;">	
				${item.contentsTextHtmlFormatted}</div>
			
				<br/>
				</div>
				<br/>
			 <c:set var="row" value="${row + 1}"/>
			</c:forEach>
			</c:otherwise>
   </c:choose>
  

  </div>
   

   
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

#global {   margin-left: auto;   margin-right: auto;   width: 700px;   text-align: left; /* on r굡blit l'alignement normal du texte */ }
.global2 {   margin-left: auto;   margin-right: auto;   width: 300px;   text-align: left; /* on r굡blit l'alignement normal du texte */ }
</style>

<SCRIPT LANGUAGE="JavaScript">
	var myEditor;
	jours = new Array('Dimanche', 'Lundi', 'Mardi', 'Mercredi', 'Jeudi', 'Vendredi', 'Samedi'); 
	lesmois = new Array('Janvier', 'Février', 'Mars', 'Avril', 'Mai', 'Juin', 'Juillet','Août','Septembre','Octobre','Novembre','Décembre'); 
	Today = new Date();
	Jour = Today.getDate();
	Mois = (Today.getMonth())+1;
	Annee = Today.getFullYear();

	Heure = Today.getHours(); 
	Minute = Today.getMinutes();
	Seconde = Today.getSeconds();

	Message = jours[Today.getDay()] + " "  + Jour +" " +lesmois[Today.getMonth()] + " " + Annee + "  " + Heure + "h " + Minute + "m " + Seconde + "s";
</SCRIPT>



<div id="global">
<form   name="messageForm" action="${ctx}/handleMissionMessagePage.htm" method="post" >
	<input type="hidden" name="emailList"/>
	<input type="hidden" name="missionId" value="${viewMissionDetails.mission.id}"/>
	<table cellpadding="3" cellspacing="2" border="0" class="axial" style="margin-left: auto;   margin-right: auto;   width: 700px;   text-align: left;">

	<tr>
		<td colspan="2" align="center">
				<span  style="color:black;font-weight: bold;">
		Ecrire un message
		</span>
			</td>
	</tr>

	<tr>
      <th>&nbsp;De : &nbsp;&nbsp;</th>
      <td id="fromTd"><span  style="color:blue;font-weight: bold;">${context.currentUser.fullName}</span></td>
    </tr>

	<tr>
      <th><input style="font:10px Verdana, sans-serif;margin-right:10pt;" type="button" class="button120" value="A" id="show2"/></th>
      <td id="emailsListTd"><span  style="color:blue;font-weight: bold;">${emailsList}</span></td>
    </tr>

	<tr>
      <th>&nbsp;Client : &nbsp;&nbsp;</th>
      <td>
        <span style="color:purple;">${viewMissionDetails.missionData.customerName} </span> [ <span style=" color: blue;">${viewMissionDetails.missionData.exercice} ${viewMissionDetails.missionData.year}</span> ]&nbsp;[ <span style=" color: blue;"> ${viewMissionDetails.mission.type}</span> ]
      </td>
    </tr>

	
	<tr>
      <th>&nbsp;Date : &nbsp;&nbsp;</th>
      <td>
	  <span  style="color:blue;font-weight: bold;">${message.createDate}</span>
	  <SCRIPT LANGUAGE="JavaScript">
			document.write(Message);
			</SCRIPT>
         
      </td>
    </tr>

	<tr>
      <th>&nbsp;Objet : &nbsp;&nbsp;</th>
      <td>
      
						<input style="width:500px;" name="object" id="object" type="text" size="30"  />
											
					
      </td>
    </tr>

	<tr>
      <th>&nbsp;Message : &nbsp;&nbsp;</th>
      <td>
		
			
				 <textarea name="editor" id="editor" rows="20" cols="60"> </textarea> 
				 
				

      </td>
    </tr>


	
	
	
	</table>
   
	
    
</form>



</div>




<div id="addDialog">
	<div class="hd">
        <span id="formTitle" style="color:blue;">Select recipients</span>
    </div>
    <div class="bd">
	<form name="addRecipientsForm"  method="get">
	
	<input type="hidden" name="sujet"/>
	<input type="hidden" name="contents"/>
			<table id="ver-zebra2" width="100%"  class="formlist">
			 <c:set var="line" value="even"/>
			 
			 <c:set var="row" value="0"/>
			 <c:forEach var="option" items="${viewMissionDetails.teamMembers}">
						
						<c:choose>
							<c:when test='${row % 2 eq 0 }'>
							 <c:set var="line" value="veen" />									      			
							</c:when>
							<c:otherwise>
							 <c:set var="line" value="odd" />									      			
							</c:otherwise>
						</c:choose>

						<tr class="${line}">
						<td style="border: 1px solid #0066aa;">
							<input type="checkbox" name="reci_" value="${option.id}"/>
						</td>
						<td style="border: 1px solid #0066aa;">
							<span style="color:rgb(218,69,37);"><strong>${option.name}</strong></span>
						</td>
				</tr>
			
						<c:set var="row" value="${row + 1}"/>
			</c:forEach>
			
			
			</table>
	</form>
	</div>
	
</div>
  
  <!-- End Footer -->
 </div>
<!-- End Wrapper -->
						
					


	