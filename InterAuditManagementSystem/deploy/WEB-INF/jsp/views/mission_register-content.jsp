<jsp:directive.page contentType="text/html;charset=UTF-8" />
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

<script type="text/javascript" src="${ctx}/script/jquery-1.6.1.js"></script>
<link href="${ctx}/css/messageHandler.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/script/messageHandler.js"></script>

<link type="text/css" href="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/themes/base/ui.all.css" rel="stylesheet" />
	<script type="text/javascript" src="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/jquery-1.3.2.js"></script>
	<script type="text/javascript" src="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/ui/ui.core.js"></script>
	<script type="text/javascript" src="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/ui/ui.datepicker.js"></script>
	<link type="text/css" href="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/demos.css" rel="stylesheet" />


<script>
	
	

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

	   div#ui-datepicker2-div {
           z-index: 4000;
       }

	    div#ui-datepicker3-div {
           z-index: 4000;
       }

	  

</script>


<script type="text/javascript">
  
$(document).ready(function()  {

  


<c:if test="${not empty actionErrors}">
				<c:forEach var="actionError"
					items="${actionErrors}">					
					showMessage("${actionError}","error");
				</c:forEach>
				<c:set var="actionErrors" value="" scope="session" />
			</c:if>
<c:if test="${not empty successMessage}">

	<c:forEach var="message"
		items="${successMessage}">
		showMessage("${message}","ok");
	</c:forEach>
	<c:set var="successMessage" value="" scope="session" />
</c:if>






            
      


});



  
</script>

<script type="text/javascript">
 
	$(function() {
	$('#datepicker').datepicker({
		showOn: 'button', buttonImage: 'images/calbtn.gif', buttonImageOnly: false,
	changeMonth: true,
		changeYear: true
	});
	});

	$(function() {
	$('#datepicker2').datepicker({
		showOn: 'button', buttonImage: 'images/calbtn.gif', buttonImageOnly: false,
	changeMonth: true,
		changeYear: true
	});
	});

	$(function() {
		$('#datepicker3').datepicker({
			showOn: 'button', buttonImage: 'images/calbtn.gif', buttonImageOnly: false,
		    changeMonth: true,
			changeYear: true
		});
		});


	
	  function cancelForm()
	  {
		  window.location="${ctx}/showGeneralBudgetPage.htm?year_${currentExercise.year}=${currentExercise.year}&forceReload=true&FromPlanningWeek=${param['FromPlanningWeek']}";
	  }

	
		
	
	function displayPropertyForm()
	  {
		  var url = "${ctx}/showMissionPropertiesPage.htm?id=${missionForUpdate.id}&FromPlanningWeek=${param['FromPlanningWeek']}";
		  window.location=url;
	  }
	  
	  function markMissionAsClosed()
	  {
		var answer = confirm("Do you really want to close this mission?");
        if (answer){
			window.location="${ctx}/markMissionAsClosed.htm?id=${missionForUpdate.id}&FromPlanningWeek=${param['FromPlanningWeek']}";
		}
		  
	  }
	  
	  
	  
	  function closeTimeSheetListToValidate()
	  {
		window.location="${ctx}/showAdminitrateMissionPage.htm?id=${missionForUpdate.id}&FromPlanningWeek=${param['FromPlanningWeek']}";
	  }
</script>


</style>


		<style type="text/css">
		#global {   margin-left: auto;   margin-right: auto;   width: 700px;   text-align: left; /* on r�tablit l'alignement normal du texte */ }
		.global2 {   margin-left: auto;   margin-right: auto;   width: 300px;   text-align: left; /* on r�tablit l'alignement normal du texte */ }

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
<style type="text/css">
/* CSS Tabs */
#navlist {
        padding: 3px 0;
        margin-left: 0;
        border-bottom: 1px solid #778;
        font: bold 12px Verdana, sans-serif;
}

#navlist li {
        list-style: none;
        margin: 0;
        display: inline;
}

#navlist li a {
        padding: 3px 0.5em;
        margin-left: 3px;
        border: 1px solid #778;
        border-bottom: none;
        background: #DDE;
        text-decoration: none;
}

#navlist li a:link { color: #448; }
#navlist li a:visited { color: #667; }

#navlist li a:hover {
        color: #000;
        background: #AAE;
        border-color: #227;
}

#navlist li a#current {
        background: white;
        border-bottom: 1px solid white;
}
</style>


<c:if test="${not empty listTimesheetsToValidate}">
<div style="background-color: #eee;margin-left: auto; margin-right: auto; width: 90%; text-align: center; ">
                                    
                
                    <table id="ver-zebra" width="100%" cellspacing="0" >
                                    <caption><span style="color:purple;">Reports activities to validate</span></caption>
                                    <thead>
                                    <tr>
                                        <th scope="col">Employé</th>
                                        <th scope="col">Semaine </th>
                                        <th scope="col">Date validation </th>
                                        <th scope="col">Date début </th>
                                        <th scope="col">Date fin </th>
                                        <th scope="col">Exercice</th>
                                        <th scope="col">Statut</th>
                                        <th scope="col">Actions</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    

                                            <c:set var="row" value="0"/>
                                            <c:forEach var="item" items="${listTimesheetsToValidate}" >


                                                <c:choose>
                                                <c:when test='${item.status eq "DRAFT"}'>                                               
                                                 <c:set var="backgroundStyle" value="background-color:#FF6262;"/>
                                                </c:when>
                                                <c:when test='${item.status eq "SUBMITTED"}'>                                               
                                                 <c:set var="backgroundStyle" value="background-color:#FFA042;"/>
                                                </c:when>
                                                <c:when test='${item.status eq "VALIDATED"}'>
                                                 <c:set var="backgroundStyle" value="background-color:#55FF55;"/>
                                                </c:when>
                                            </c:choose>
                                                
                                                <c:choose>
                                                    <c:when test='${row % 2 eq 0 }'>
                                                        <tr style="background: #eff2ff;" onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
                                                    </c:when>
                                                    <c:otherwise>
                                                        <tr onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
                                                    </c:otherwise>
                                                </c:choose>
                                                    <td  align="center"><span style=" color: purple;">${item.employeeName}</span></td>
                                                    <td align="center">${item.weekNumber}</td>                                                  
                                                    <td align="center">${item.validationDate}</td>
                                                    <td align="center">${item.startDateOfWeek}</td>
                                                    <td align="center">${item.endDateOfWeek}</td>                                                   
                                                    <td  align="center">${item.year}</td>
                                                    <td style=${backgroundStyle} align="center">${item.status}</td>
                                                    <td  align="center"><a href="${ctx}/showTimesheetRegisterPage.htm?year=${item.year}&week=${item.weekNumber}&employeeId=${item.employeeId}" title="Details activity report" ><img src="images/Table-Edit.png" border="0" alt="Edit"/></a></td>
                                                    
                                                </tr>
                                                <c:set var="row" value="${row + 1}"/>
                                            </c:forEach>
                                    
                                    
                                    </tbody>
                                </table>
                                <hr/>
                                <table border="0" width="100%">
                                <tr><td align="center">                             
                                        <c:if test="${empty hasError}">
                                            <input style="width:100px;
                                            " type="button" name="button" id="submit" class="button120"  value="Close>>" onclick="closeTimeSheetListToValidate()"/>
                                        </c:if>                                     
                                        <c:set var="hasError" value="" scope="session" />
                                    </td>
                                </tr>
                            </table>
                        </div>
                        <c:set var="listTimesheetsToValidate" value="" scope="session" />
</c:if>


<div id="navcontainer">
                        <ul id="navlist">
                                <!-- CSS Tabs -->
<li><a  id="current" href="${ctx}/showAdminitrateMissionPage.htm?id=${param['id']}&FromPlanningWeek=true">Administrate</a></li>
<li><a  href="${ctx}/showMissionTeamMembersPage.htm?id=${param['id']}&FromPlanningWeek=true">Team Members</a></li>


                        </ul>
                </div>
<div id="global">


<form  name="mainMissionForm"  action="${ctx}/showAdminitrateMissionPage.htm?FromPlanningWeek=${param['FromPlanningWeek']}" method="post" class="niceform">
<fieldset style="background-color: #eee;">
    	<table border="0" width="100%">
		<tr>
			<td align="center">
				<span  style="color:purple;font-weight: bold;">
						 <span  style="color:blue;font-weight: bold;"> ${missionForUpdate.title}</span>  
						<!-- &nbsp;Status : <span  style="color:red;font-weight: bold;">${missionForUpdate.status}</span-->
						 &nbsp;Mandat : <span  style="color:red;font-weight: bold;">${missionForUpdate.exercise}</span>
						 
				</span>
			</td>
			<td>
			<t:tooltip>  
														   <t:text><em><img src="images/info-bulle.png" border="0" alt="Edit"/></em></t:text>
														  <t:help width="300" >
														   <font color="STEELBLUE"><strong>Societe :</strong></font>${missionForUpdate.title}<BR/>
														   <font color="STEELBLUE"><strong>Code :</strong></font> ${missionForUpdate.reference}<BR/>
														   <font color="STEELBLUE"><strong> Associe: </strong></font>${missionForUpdate.annualBudget.associeSignataire.fullName}<BR/>										  
														   <font color="STEELBLUE"><strong>Manager : </strong></font>${missionForUpdate.annualBudget.budgetManager.fullName} <BR/>
														   <font color="STEELBLUE"><strong>Statut : </strong></font>${missionForUpdate.status}<BR/>
															<font color="STEELBLUE"><strong>Type : </strong></font>${missionForUpdate.type}														
															</t:help> 
												</t:tooltip>
			</td>
		</tr>
	</table> 
    </fieldset>

<%--fieldset style="background-color: #eee;">
    	<legend>
			<span  style="color:purple;font-weight: bold;">Propri�t�s</span>
		</legend>
		
		
		<dl class="global2">
			<label style="color:#039;" for="fax">Customer</label>
			<div>				
					<input style="width:350px;" size="30" type="text"  value="${customerName}" readonly="readonly">			
			</div>
		</dl>

		<dl class="global2">
			<label style="color:#039;" for="fax">R�f�rence</label>
			<div>				
					<input style="width:350px;" size="30" type="text"  value=" ${missionForUpdate.reference}" readonly="readonly">			
			</div>
		</dl>
		
		<dl class="global2">
			<label style="color:#039;" for="fax">Type mission</label>
			<div>				
					<input style="width:350px;" size="30" type="text"  value="${missionForUpdate.type}" readonly="readonly">			
			</div>
		</dl>
		
		<dl class="global2">
			<label style="color:#039;" for="fax">Statut</label>
			<div>				
					<input style="width:350px;" size="30" type="text"  value="${missionForUpdate.status}" readonly="readonly">			
			</div>
		</dl>
		
		<dl class="global2">
			<label style="color:#039;" for="fax">Associ�</label>
			<div>				
					<input style="width:350px;" size="30" type="text"  value="${missionForUpdate.annualBudget.associeSignataire.fullName}" readonly="readonly">			
			</div>
		</dl>
		
		<dl class="global2">
			<label style="color:#039;" for="fax">Manager</label>
			<div>				
					<input style="width:350px;" size="30" type="text"  value="${missionForUpdate.annualBudget.budgetManager.fullName}" readonly="readonly">			
			</div>
		</dl>
		
	</fieldset--%>
	

	<fieldset style="background-color: #eee;">
    	<legend>
			<span  style="color:purple;font-weight: bold;">Progress</span>
		</legend>
		
		
		<dl class="global2">
			<label style="color:#039;" for="recipient">Progress status</label>
            <div>
			<spring:bind path="missionForUpdate.jobStatus" >
					<select style="width:240px;" name="${status.expression}">
					<c:forEach var="option" items="${missionJobProgressStatusOptions}" varStatus="loop">
						<option value="${option.id}" 
							<c:if test="${option.id == status.value}">selected</c:if>>
							${option.name}
						</option>
					</c:forEach>
					</select>
				</spring:bind>
				
			</div>
		</dl>
		
		<dl class="global2">
			<label style="color:#039;" for="recipient">To do</label>
            <div>
			<spring:bind path="missionForUpdate.toDo" >
					<select style="width:240px;" name="${status.expression}">
					<option value="--">--</option>							
					<c:forEach var="option" items="${missionJobTodoOptions}" varStatus="loop">
						<option value="${option.id}" 
							<c:if test="${option.id == status.value}">selected</c:if>>
							${option.name}
						</option>
					</c:forEach>
					</select>
				</spring:bind>
				
			</div>
		</dl>
		
		<%--dl class="global2">
			<label style="color:#039;" for="recipient">To Finish</label>
            <div>
			<spring:bind path="missionForUpdate.toFinish" >
					<select style="width:240px;" name="${status.expression}">
					<option value="--">--</option>
					<c:forEach var="option" items="${missionJobToFinishOptions}" varStatus="loop">
						<option value="${option.id}" 
							<c:if test="${option.id == status.value}">selected</c:if>>
							${option.name}
						</option>
					</c:forEach>
					</select>
				</spring:bind>
				
			</div>
		</dl--%>
		
		<dl class="global2">
			<label style="color:#039;" for="password">Comments:</label>
            <div>
			<spring:bind path="missionForUpdate.jobComment" >
				 <textarea name="${status.expression}" id="comments" rows="3" cols="42">${status.value}</textarea>
					<span style="color: red;">${status.errorMessage}</span>
				</spring:bind>
			</div>
		</dl>

		
		
	</fieldset>	
	
	

	<fieldset style="background-color: #eee;">
    	<legend>
			<span  style="color:purple;font-weight: bold;">Administration</span>
		</legend>
		
		<dl class="global2">
		
				<label  style="color:#039;"  for="startWeek">Start Year</label> <span  style="color:red;font-weight: bold;"> </span>
               <div>
                    <spring:bind path="missionForUpdate.startYear" >
                        <select style="width:120px;" name="${status.expression}">
                        <c:forEach var="entry" begin="1990" end="2050">
                            <option value="${entry}" 
                                <c:if test="${entry == status.value}">selected</c:if>>
                                ${entry}
                            </option>
                        </c:forEach>
                    </select>
                    </spring:bind>
				</div>
			
				<label  style="color:#039;"  for="startWeek">Start Week</label> <span  style="color:red;font-weight: bold;"> </span>
				<div>
					<spring:bind path="missionForUpdate.startWeek" >
						<select style="width:120px;" name="${status.expression}">
						<c:forEach var="entry" begin="1" end="53">
							<option value="${entry}" 
								<c:if test="${entry == status.value}">selected</c:if>>
								${entry}
							</option>
						</c:forEach>
					</select>
					</spring:bind>
				<div>
				
				
			</dl>
		
		<dl class="global2">
				<label  style="color:#039;"  for="startDate">Draft date</label> <span  style="color:red;font-weight: bold;"> (mm / dd / YYYY)</span>
				<div>
					<spring:bind path="missionForUpdate.approvalDate" >
						<input style="width:200px;" size="30" type="text" name="${status.expression}" id="datepicker" value="${status.value}">
						<span style="color: red;">${status.errorMessage}</span>
					</spring:bind>
				</div>
			</dl>

			<dl class="global2">
				<label  style="color:#039;"  for="cloture">Finished & Signed Date</label> <span  style="color:red;font-weight: bold;"> (mm / dd / YYYY)</span>
				<div>
					<spring:bind path="missionForUpdate.signedDate" >
						<input style="width:200px;" size="30" type="text" name="${status.expression}" id="datepicker3" value="${status.value}">
						<span style="color: red;">${status.errorMessage}</span>
					</spring:bind>
				</div>
			</dl>
			
			<%--dl class="global2">
				<label  style="color:#039;"  for="dueDate">Due date</label> <span  style="color:red;font-weight: bold;"> (mm / dd / YYYY)</span>
				<div>
					<spring:bind path="missionForUpdate.dueDate" >
						<input style="width:200px;" size="30" type="text" name="${status.expression}" id="datepicker2" value="${status.value}">
						<span style="color: red;">${status.errorMessage}</span>
					</spring:bind>
				</div>
			</dl--%>
			
			<dl class="global2">
				<label  style="color:#039;"  for="dueDate">Date clotûre</label> <span  style="color:red;font-weight: bold;"> (mm / dd / YYYY)</span>
				<div>
					<spring:bind path="missionForUpdate.dateCloture" >
						<input style="width:200px;" size="30" type="text" name="${status.expression}" id="datepicker2" value="${status.value}">
						<span style="color: red;">${status.errorMessage}</span>
					</spring:bind>
				</div>
			</dl>
			
			
			
			
			
			
			
			<dl class="global2">
			<label style="color:#039;" for="recipient">Status</label>
            <div>
			<spring:bind path="missionForUpdate.status" >
					<select style="width:240px;" name="${status.expression}">
					<c:forEach var="option" items="${missionForUpdateStatusOptions}" varStatus="loop">
						<option value="${option.id}" 
							<c:if test="${option.id == status.value}">selected</c:if>>

							<c:choose>
														<c:when test='${option.name eq "PENDING" }'>
														 	En Attente
														</c:when>

														<c:when test='${option.name eq "ONGOING" }'>
														 	En cours
														</c:when>

														<c:when test='${option.name eq "STOPPED" }'>
														 	Arrêté
														</c:when>

														<c:when test='${option.name eq "CANCELLED" }'>
														 	Annulé
														</c:when>

														<c:when test='${option.name eq "CLOSED" }'>
														 	Terminé
														</c:when>

														<c:otherwise>
														 	Transférée
														</c:otherwise>
														
													</c:choose>
							
						</option>
					</c:forEach>
					</select>
				</spring:bind>
				
			</div>
		</dl>
		
		

		
		
			
		
    </fieldset>

    <fieldset style="background-color: #eee;">
    	<table border="0" width="100%">
			<tr><td align="center">
				<input style="width:100px;" type="button" name="cancel" id="cancel" class="button120"  value="Budget" onclick="cancelForm()"/>

				<input style="width:100px;" type="button" name="properties" id="properties" class="button120"  value="Details" onclick="displayPropertyForm()"/>
				
				
				<input style="width:100px;" type="submit" name="submit" id="submit" class="button120"  value="Enregistrer" />
				
				<c:if test='${missionForUpdate.status != "CLOSED" }'>
					<input style="width:100px;" type="button" name="terminer" id="terminer" class="button120"  value="Cloturer" />
				</c:if>
				
			</td></tr>
		</table> 
    </fieldset>
</form>




</div>

<div id="closeMissionDialog">
        <span id="closeMissionDialogTitle" style="color:blue;">Terms</span>
    
    <div class="bd">
        <form name="closeMissionDialogForm"  method="post">
           <div class="IAgree" title="Usage Terms">
			
					<div style="border: 1px solid blue">
						<p>
							<ul>
								<li>Les fonctionnalités suivantes permettent de gérer les exercices dans le système <b><i>I.A.M.S</i></b></li>
								<br/>
									<ul>
										<li><b><i>Génération d'un exercice </b></i> au statut <b><i><span style="color:blue;">PREVISIONNEL</span></i></b> </li>
										<li><b><i>Approbation d'un exercice</b></i> du statut <b><i><span style="color:blue;">PREVISIONNEL</span></i></b>  au statut <b><i><span style="color:green;">ACTIF</span></i></b></li>
										<li><b><i>Marquage d'un exercice</b></i> pour référencer les données d'un exercice au statut <b><i><span style="color:green;">ACTIF</span></i></b> à une date spécifique</li>
										<li><b><i>Clôture d'un exercice</b></i> du statut <b><i><span style="color:green;">ACTIF</span></i></b>  au statut <b><i><span style="color:red;">TERMINE</span></i></b> </li>
										<li><b><i>Suppression d'un exercice</b></i> ayant le statut <b><i><span style="color:blue;">PREVISIONNEL</span></i></b> </li>
									</ul>
									<br/>
								<li>Il n'y a qu'un seul exercice <b><i><span style="color:green;">ACTIF</span></i></b> à tout instant dans le système <b><i>I.A.M.S</i></b> .</li>	
								<li>Pour approuver un nouvel exercice et par conséquent le rendre  <b><i><span style="color:green;">ACTIF</span></i></b>, il faudra clôre l'exercice courant au préalable .</li>									
								<li>Seul l'exercice en  <b><i><span style="color:green;">ACTIF</span></i></b>  peut recevoir des factures, des payments et des timesheets. </li>								
								<li>Plusieurs exercices à l'état <b><i><span style="color:blue;">PREVISIONNEL</span></i></b> peuvent subsister à tout instant .</li>	
								
							</ul>							
						</p>
					</div>
					<br/>
					<div style="border: 1px solid blue">
						<p>
							<ul>
								<li>Une fois <b><i><span style="color:red;">CLOTUREE</span></i></b>, une mission ne sera plus disponible pour la facturation et les timesheets </li>
								<li>Avant de  clôturer une mission, verifier les conditions suivantes:
									<ul>
										<li><b><span style="color:red;">Toutes les  heures relatives à la mission sont enregistrées et  <b><i><span style="color:green;">APPROUVEES</span></i></b></span> </b></li>
										<li><b><span style="color:red;">Toutes les factures relatives à la mission sont enregistrées </span> </b></li>	
										<li><b><span style="color:red;">Verifier si une facture finale est nécéssaire pour cette mission </span> </b></li>									
									</ul>
								</li>
								<li>Le système refusera de clôturer une mission si il existe des timesheets non <b><i><span style="color:green;">APPROUVEES</span></i></b> pour celle-ci </li>
									
							</ul>							
						</p>
					</div>
			</div>
        </form>
    </div>
</div>






