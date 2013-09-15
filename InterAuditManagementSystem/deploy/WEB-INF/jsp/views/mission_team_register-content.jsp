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





<div id="navcontainer">
                        <ul id="navlist">
                                <!-- CSS Tabs -->
<li><a  href="${ctx}/showAdminitrateMissionPage.htm?id=${param['id']}&FromPlanningWeek=true">Administrate</a></li>
<li><a id="current" href="${ctx}/showMissionTeamMembersPage.htm?id=${param['id']}&FromPlanningWeek=true">Team Members</a></li>

                        </ul>
                </div>
<div id="global">


<form  name="mainMissionForm"  action="${ctx}/showAddMemeberForMissionPage.htm?FromPlanningWeek=${param['FromPlanningWeek']}" method="post" class="niceform">
<input type="hidden" name="idMission" value="${param['id']}"> 

    <fieldset style="background-color: #eee;">
    	<table border="0" width="100%">
		<tr>
			<td align="center">
				<span  style="color:purple;font-weight: bold;">
						 <span  style="color:blue;font-weight: bold;"> ${missionForUpdate.title}</span>  
						&nbsp;Status : <span  style="color:red;font-weight: bold;">${missionForUpdate.status}</span>
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
    
    <fieldset style="background-color: #eee;">
		<legend>
			<span  style="color:purple;font-weight: bold;">Add Member</span>
		</legend>
        <table border="0" width="100%">
        <tr>
            <td align="center">
                <span  style="color:purple;font-weight: bold;">
                       <span  style="color:blue;font-weight: bold;"> Employees</span>  
                       <select style="width:240px;" name="employeId">
                        <c:forEach var="option" items="${employeesOptions}" varStatus="loop">
	                        <option value="${option.id}">
	                            ${option.name}
	                        </option>
                        </c:forEach>
                    </select>
                         
                </span>
            </td>
            <td>
                <input style="width:100px;" type="submit" name="submit" id="submit" class="button120"  value="Add Member" />
            </td>
        </tr>
    </table> 
    </fieldset>
    
    <fieldset style="background-color: #eee;">
		<legend>
			<span  style="color:purple;font-weight: bold;">Member List</span>
		</legend>
        <table border="0" width="100%">
        <c:forEach var="member" items="${members}" varStatus="loop">
        <tr>
        
            <td align="left">
                <span  style="color:purple;font-weight: bold;">
                        
                   ${member.lastName}      
                </span>
            </td>
            <td>
            <span  style="color:purple;font-weight: bold;">
                        
                   ${member.firstName}      
                </span>
            </td>
			 <td>
            <span  style="color:purple;font-weight: bold;">
                        
                   ${member.position.name}      
                </span>
            </td>
			
			
			 <td>
            <span  style="color:purple;font-weight: bold;">
                        
                   <a  href="${ctx}/showRemoveMemberForMissionPage.htm?id=${param['id']}&employeId=${member.id}">remove</a>      
                </span>
            </td>
        </tr>
        </c:forEach>
    </table> 
    </fieldset>
    
</form>




</div>








