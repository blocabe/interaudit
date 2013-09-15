<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="t"      uri="/tags/tooltips-tiles" %>
<%@ taglib uri="/tags/interaudit" prefix="interaudit" %>
<%@ page import="java.util.*,java.text.*,java.io.PrintWriter,com.interaudit.service.view.AnnualPlanningView" %>






<script type="text/javascript" src="${ctx}/script/build/yahoo-dom-event/yahoo-dom-event.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/script/build/datatable/assets/skins/sam/datatable.css"/>
<script type="text/javascript" src="${ctx}/script/build/datatable/datatable-min.js"></script>


<link rel="stylesheet" type="text/css" href="${ctx}/script/build/fonts/fonts-min.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/script/build/autocomplete/assets/skins/sam/autocomplete.css" />
<script type="text/javascript" src="${ctx}/script/build/yahoo-dom-event/yahoo-dom-event.js"></script>
<script type="text/javascript" src="${ctx}/script/build/connection/connection-min.js"></script>
<script type="text/javascript" src="${ctx}/script/build/animation/animation-min.js"></script>
<script type="text/javascript" src="${ctx}/script/build/datasource/datasource-min.js"></script>
<script type="text/javascript" src="${ctx}/script/build/autocomplete/autocomplete-min.js"></script>

<link type="text/css" href="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/themes/base/ui.all.css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/jquery-1.3.2.js"></script>
<script type="text/javascript" src="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/ui/ui.core.js"></script>
<script type="text/javascript" src="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/ui/ui.draggable.js"></script>
<script type="text/javascript" src="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/ui/ui.droppable.js"></script>
<script type="text/javascript" src="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/ui/ui.resizable.js"></script>
<script type="text/javascript" src="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/ui/ui.dialog.js"></script>

<link href="${ctx}/css/messageHandler.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/script/messageHandler.js"></script>

<script type="text/javascript"> 
$(document).ready(function()  {
			<c:if test='${currentExercise.status eq "CLOSED"}'> 	
				showMessage("Aucun budget actif défini... cette fonctionnalité n'est pas utilisable..","error");                    
			</c:if> 
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
		
		$( "#item_planning" ).draggable(
			{ 
			  cursor: 'crosshair',
			  handle:'img', 
			  opacity: 0.7,
			  helper: function(event) {
										  var text = document.forms["addMultipleTaskForm"].elements["myInput"].value ;
										  var shorttext = text.substring(0,10);
						                  //return $('<div class="ui-widget-header">'+text+'</div>');
										 // return $('<div>'+text+'</div>');
										 return $('<div>'+shorttext+'</div>');
										},
			  start: function(event, ui) {
											/*
											var missionId = $(this).attr('id');
											 var form = document.forms["dragAndDropEventForm"];
											 form.elements["mission"].value = missionId;
											 form.elements["typeAction"].value = "addNewEvent";
											 */
											 //alert('start dragging item_planning');
										}

			});
			
			
			$( ".item_planning_em" ).draggable(
			{ 
			  cursor: 'crosshair',
			  handle:'img', 
			  opacity: 0.7,
			  helper: function(event) {
											return $('<div class="ui-widget-header">I\'m a custom helper</div>');
										},
			  start: function(event, ui) {
											 alert('start dragging item_planning');
											 /*
											 var eventId = $(this).attr('id');
											 var form = document.forms["dragAndDropEventForm"];											
											 form.elements["eventId"].value = eventId;
											 form.elements["typeAction"].value = "changeStartHour";
											 */
											
										}

			});
			
			
		// let the trash be droppable, accepting the gallery items
        $( ".item_placeholder" ).droppable({
			activeClass: 'ui-state-hover',
			hoverClass: 'ui-state-active',
            drop: function( event, ui ) {
			
				
				
			    var key = $(this).attr('id');
				//alert(key);
				var date = key.substring(0,key.indexOf("##",0));
				//alert(date);
				var userId = key.substring(key.indexOf("##",0)+2,key.indexOf("++",0));
				//alert(userId);
				var planningId = key.substring(key.indexOf("++",0)+2);
				//alert(planningId);
				var missionId = document.forms["addMultipleTaskForm"].elements["missionId"].value;
				//alert(missionId);
				
				var missionName = document.forms["addMultipleTaskForm"].elements["myInput"].value ;
			
				
				if(missionId == null || missionId == ""){
				showMessage("select a mission please","error");
				}
				else{
					
					var form = document.forms["dragAndDropEventForm"];
					form.elements["employeeId"].value = userId;
					form.elements["planningId"].value = planningId;
					form.elements["weekNumber"].value = date;
					form.elements["missionId"].value = missionId;	
					form.elements["missionName"].value = missionName;
					form.submit();
					$(this).addClass('ui-state-highlight').html('Dropped!');
					showMessage("The planning has been successfully updated...","ok");
				}

            },			
			over: function(event, ui) {
				//$(this).html( this.id );
			}

        });
	});
	</script>


<style type='text/css'>
#textDiv { position:absolute; top:8; right:8; border-style:solid; border-width:thin; background:red;}
</style>


<!-- START ADDED PART -->   

<style>

th.colonnex { 
/*width:30; */

} 


input.button120 {
	width:80px;
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

span.pagebanner {
	background-color: #eee;
	border: 1px dotted #999;
	padding: 2px 4px 2px 4px;
	width: 100%;
	margin-top: 10px;
	display: block;
	border-bottom: none;
}

span.pagelinks {
	background-color: #eee;
	border: 1px dotted #999;
	padding: 2px 4px 2px 4px;
	width: 100%;
	display: block;
	border-top: none;
	margin-bottom: -5px;
}
	#ver-zebra
	{
		font-family: "Lucida Sans Unicode", "Lucida Grande", Sans-Serif;
		font-size: 10px;
		/*margin: 45px;*/
		/*width: 480px;*/
		text-align: center;
		border-collapse: collapse;
	}
	#ver-zebra th
	{
		/*font-size: 12px;
		font-weight: normal;
		padding: 12px 15px;*/
		border-top: 1px solid #0066aa;
		border-right: 1px solid #0066aa;
		border-bottom: 1px solid #0066aa;
		border-left: 1px solid #fff;
		color: #039;
		background: #eff2ff;
	}
	#ver-zebra td
	{
		/*padding: 4px 7px;*/
		border-right: 1px solid #0066aa;
		/*border-left: 2px solid #fff;*/
		border-bottom: 1px solid #0066aa;
		color: #669;
	}
	.vzebra-odd
	{
		/*background: #eff2ff;*/
		background: #fff;
	}
	.vzebra-even
	{
		background: #e8edff;
	}
	#ver-zebra #vzebra-adventure, #ver-zebra #vzebra-children
	{
		background: #d0dafd;
		border-bottom: 1px solid #c8d4fd;
	}
	#ver-zebra #vzebra-comedy, #ver-zebra #vzebra-action
	{
		background: #dce4ff;
		border-bottom: 1px solid #d6dfff;
	}


#hor-minimalist-b
{
	font-family: "Lucida Sans Unicode", "Lucida Grande", Sans-Serif;
	font-size: 10px;
	background: #fff;
	/*margin: 10px;*/
	/*width: 750px;*/
	border-collapse: collapse;
	text-align: center;
}
#hor-minimalist-b th
{
	font-size: 10px;
	/*font-weight: normal;*/
	color: #039;
	padding: 5px 4px;
	border-bottom: 1px solid #6678b1;
	border-right: 1px solid #6678b1;
	border-top: 1px solid #6678b1;
	background: #eff2ff;
}
#hor-minimalist-b td
{
	border-bottom: 1px solid #ccc;
	border-right: 1px solid #ccc;
	color: #669;
	padding: 6px 8px;
}
#hor-minimalist-b tbody tr:hover td
{
	color: #009;
}

.normal { background-color: white }
.highlight { background-color: orange }
		 
		 
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

table.axial td .table.formlist th {
		border-top:1px solid #FFFFFF;
		border-left:1px solid #FFFFFF;
		border-right:1px solid #999999;
		border-bottom:1px solid #999999;	
		background-color: rgb(160,185,224);
		text-align:center;
		padding:5px 5px 5px 20px;
		vertical-align: middle;
		font-style: normal;
	}
	
	div.pic {float: left; height: 100px; width: 100px;
  padding: 15px; margin: 5px 3px; 
  border: 1px solid; border-color: #AAA #444 #444 #AAA;}
</style>

<script>

function exportExcel(){
	var url ="${ctx}/showPlanningExcelView.htm?year=${viewActivityPerWeekPerEmployee.year}";
	window.location = url;
}
 
 function updateFromUsers( obj ){
	
	var dates = document.getElementsByName("day");
	var count = dates.length;
	
    if (count > 0)
    {
      for(index=0; index < count; index++)
      {
		
		var tdId = dates[index].id +"##"+ obj.id;
		
        if ( obj.checked)
        {
			document.getElementById(tdId).setAttribute("bgColor",'green'); 
			
        }
		else
		{
			document.getElementById(tdId).setAttribute("bgColor",'white'); 
			
		}
      }
    }
 }



 function updateFromDates( obj ){
	 
	var users = document.getElementsByName("user");
	var count = users.length;
	
    if (count > 0)
    {
      for(index=0; index < count; index++)
      {
		 var tdId =  obj.id +"##"+ users[index].id;
		
        if ( obj.checked)
        {
			document.getElementById(tdId).setAttribute("bgColor",'green'); 
        }
		else
		{
			document.getElementById(tdId).setAttribute("bgColor",'white'); 
		}
      }
    }
 }


function selectAllUsers ( obj ){
	
	var users = document.getElementsByName("user");
	var count = users.length;
	
    if (count > 0)
    {
      for(index=0; index < count; index++)
      {
		users[index].checked = obj.checked;
		
      }
    }
}


function selectAllDates( obj ){
	var dates = document.getElementsByName("day");
	var count = dates.length;
	
    if (count > 0)
    {
      for(index=0; index < count; index++)
      {
		if(  dates[index].disabled == false){
			dates[index].checked = obj.checked;
		}
		
		
      }
    }
}

 // format the data to the controller 
  // that match withe the cheched boxes
  // moduleId##roleId, ....... 
  function getCheckDays()
  {
    var result = "";
    var boxes = document.getElementsByName("day");
    var count = boxes.length;
    if (count > 0)
    {
      for(index=0; index < count; index++)
      {
        if (boxes[index].checked)
        {
          if (result.length > 0) result += ",";
          result += boxes[index].id; 
        }
      }
    }
    return result;
  }
  
  
  function getAllDays()
  {
    var result = "";
    var boxes = document.getElementsByName("day");
    var count = boxes.length;
    if (count > 0)
    {
      for(index=0; index < count; index++)
      {       
          if (result.length > 0) result += ",";
          result += boxes[index].id;        
      }
    }
    return result;
  }

   // format the data to the controller 
  // that match withe the cheched boxes
  // moduleId##roleId, ....... 
  function getCheckUsers()
  {
    var result = "";
    var boxes = document.getElementsByName("user");
    var count = boxes.length;
    if (count > 0)
    {
      for(index=0; index < count; index++)
      {
        if (boxes[index].checked)
        {
          if (result.length > 0) result += ",";
          result += boxes[index].id; 
        }
      }
    }
    return result;
  }
  
   function getAllUsers()
  {
    var result = "";
    var boxes = document.getElementsByName("user");
    var count = boxes.length;
    if (count > 0)
    {
      for(index=0; index < count; index++)
      {
        
          if (result.length > 0) result += ",";
          result += boxes[index].id; 
       
      }
    }
    return result;
  }


  
   function refreshPlanning() {
		
			var url ="${ctx}/showAnnualPlanningPage.htm?year=" + ${viewActivityPerWeekPerEmployee.year} +"&startMonth="+${viewActivityPerWeekPerEmployee.startMonth}+"&usergroup=${ param['usergroup']}";
			window.location = url;		
	}

	
	function showPartnersOnly(){
			var url ="${ctx}/showAnnualPlanningPage.htm?year=" + ${viewActivityPerWeekPerEmployee.year} +"&startMonth="+${viewActivityPerWeekPerEmployee.startMonth}+"&usergroup=partners";
			window.location = url;		
	}

	function showManagersOnly(){
			var url ="${ctx}/showAnnualPlanningPage.htm?year=" + ${viewActivityPerWeekPerEmployee.year} +"&startMonth="+${viewActivityPerWeekPerEmployee.startMonth}+"&usergroup=managers";
			window.location = url;		
	}

	function showEmployeesOnly(){
			var url ="${ctx}/showAnnualPlanningPage.htm?year=" + ${viewActivityPerWeekPerEmployee.year} +"&startMonth="+${viewActivityPerWeekPerEmployee.startMonth}+"&usergroup=employees";
			window.location = url;		
	}

	function showSecretairesOnly(){
			var url ="${ctx}/showAnnualPlanningPage.htm?year=" + ${viewActivityPerWeekPerEmployee.year} +"&startMonth="+${viewActivityPerWeekPerEmployee.startMonth}+"&usergroup=secretaires";
			window.location = url;		
	}


	function showAll(){
		var url ="${ctx}/showAnnualPlanningPage.htm?year=" + ${viewActivityPerWeekPerEmployee.year} +"&startMonth="+${viewActivityPerWeekPerEmployee.startMonth}+"&usergroup=all";
			window.location = url;
	}


	

	

  function moveToStartOfPlanning() {
		
			var url ="${ctx}/showUpdatedAnnualPlanningPage.htm?year=" + ${viewActivityPerWeekPerEmployee.year} +"&startMonth=0"+"&usergroup=${ param['usergroup']}";
			window.location = url;		
	}

  function moveToPrevMonthOfPlanning() {
		
		var url ="${ctx}/showUpdatedAnnualPlanningPage.htm?year=" + ${viewActivityPerWeekPerEmployee.year} +"&startMonth="+ ${viewActivityPerWeekPerEmployee.startMonth - 1}+"&usergroup=${ param['usergroup']}";
		window.location = url;		
}

  function moveToNextMonthOfPlanning() {
		
		var url ="${ctx}/showUpdatedAnnualPlanningPage.htm?year=" + ${viewActivityPerWeekPerEmployee.year} +"&startMonth="+ ${viewActivityPerWeekPerEmployee.startMonth + 1}+"&usergroup=${ param['usergroup']}";
		window.location = url;		
  }

  function moveToEndOfPlanning() {
		
		var url ="${ctx}/showUpdatedAnnualPlanningPage.htm?year=" + ${viewActivityPerWeekPerEmployee.year} +"&startMonth=11"+"&usergroup=${ param['usergroup']}";
		window.location = url;		
  }

  function loadPlanningForYear(){
	  var value = document.getElementById("select_planning_year").value;
	  var url ="${ctx}/showAnnualPlanningPage.htm?year=" +value+"&usergroup=${ param['usergroup']}";
		window.location = url;
  }

  

  

function addMultipleTasks()
  {
	
    document.addMultipleTaskForm.days.value = getCheckDays();	
    document.addMultipleTaskForm.users.value = getCheckUsers();
	
	//alert(document.addMultipleTaskForm.users.value);

	document.addMultipleTaskForm.todo.value = "add";

	document.addMultipleTaskForm.year.value = ${viewActivityPerWeekPerEmployee.year};
	document.addMultipleTaskForm.startMonth.value = ${viewActivityPerWeekPerEmployee.startMonth};
	//document.addMultipleTaskForm.month.value = document.listForm.month.value;
	//document.addMultipleTaskForm.profile.value = document.listForm.role.value;
	
    document.addMultipleTaskForm.submit();
	
  }
  
 function  updateFromTs(){
 
	 var answer = confirm("Do you really want to update the selected items from their Timesheets?")
		if (answer){
			document.addMultipleTaskForm.days.value = getAllDays();
		    document.addMultipleTaskForm.users.value = getAllUsers();

			document.addMultipleTaskForm.todo.value = "updateTS";

			document.addMultipleTaskForm.year.value = ${viewActivityPerWeekPerEmployee.year};
			document.addMultipleTaskForm.startMonth.value = ${viewActivityPerWeekPerEmployee.startMonth};
			
		    document.addMultipleTaskForm.submit();
		}
 }

  function clearAllTasks()
  {
	  var answer = confirm("Do you really want to clean the selected items?")
		if (answer){
			document.addMultipleTaskForm.days.value = getCheckDays();
		    document.addMultipleTaskForm.users.value = getCheckUsers();

			document.addMultipleTaskForm.todo.value = "remove";

			document.addMultipleTaskForm.year.value = ${viewActivityPerWeekPerEmployee.year};
			document.addMultipleTaskForm.startMonth.value = ${viewActivityPerWeekPerEmployee.startMonth};
			
		    document.addMultipleTaskForm.submit();
		}
    
	
  }




  function editAgenda(url)
  {
     window.location=url;
  }
  
</script>

<style type="text/css">
table#calendar {background: white  center no-repeat;}
table#calendar a {text-decoration: none;}
/*
tr#days th {width: 18%;}
tr#days th.sat, tr#days th.sun {width: 5%;}
*/
table#calendar tr#days th {color: #CCE; background-color: #224;
   font-weight: bold; text-align: center;
   padding: 1px 0.33em;}
table#calendar tr#title th {background: #AAC; color: black;
   border: 1px solid #242; font-size: 120%;}
table#calendar td {vertical-align: top; padding: 0;
   border: 0px solid gray; border-width: 0 0 1px 1px;}
table#calendar td.sat {border-right: 1px solid gray;}
table#calendar a {font-weight: bold; display: block; margin: 0;}
table#calendar a:link {color: navy;}
table#calendar a:visited {color: purple;}
table#calendar a:hover {background: #FF6;}
table#calendar td.sat, table#calendar td.sun {background: #FDD;}
table#calendar td.jun, table#calendar td.aug {
   background: #AAB; color: #889;}
table#calendar tr#lastweek td {border-bottom: 2px solid #AAB;}
table#calendar td.holiday {background: #FAA;}
table#calendar td#jul16 {background-color: yellow;}
td#jul16 div.date {color: #C33; font-weight: bold; background: #FFC;}
div.event {margin: 0.5em;}
div.event span {display: block;}
div.holiday {font-style: italic;}
span.time {font-weight: bold;}
span.loc {color: #555; font-style: italic;}
div.date {float: right; text-align: center;
   border: 1px solid gray; border-width: 0 0 1px 1px;
   padding: 0.125em 0.25em 0 0.25em; margin: 0; 
   background: #F3F3F3;}
td.sat div.date, td.sun div.date {border-width: 0;
   color: gray; background: transparent;}
td.jun div.date, td.aug div.date {border-width: 0;
   color: gray; background: transparent;}
</style>



    
<!---div class="nav_alphabet" style="background-color: rgb(248, 246, 233); border: 1px solid  #0066aa; text-align:center; padding-top:1pt; font: bold 12px Verdana, sans-serif;">
</div-->


<table  width="100%" align="center" style="background-color: rgb(248, 246, 233);">
	<tr>
	<td align="center" style="border :0px dotted #0066aa;">
		
	<%--input class="button120" style="width:120px; font:10px Verdana, sans-serif;" type="button"  value="Configure screen" id="show2" onclick="resetForm()"/>
	&nbsp;
	<input class="button120"  style="width:120px; font:10px Verdana, sans-serif;" type="button" value="Export to Excel"  onclick="exportExcel()"/--%>

			<!--span><img src="images/pg-first.gif" border="0" onclick="moveToStartOfPlanning()"/></span>
			<span><img src="images/pg-prev.gif" border="0" onclick="moveToPrevMonthOfPlanning()"/></span>
			&nbsp;&nbsp;-->
			<span style="font:10px Verdana, sans-serif;margin-right:5pt;"><strong>Exercice</strong> : </span>
			<select style="font:10px Verdana, sans-serif;margin-right:5pt;" id="select_planning_year" name="year" onchange="loadPlanningForYear()">
		    <c:forEach var="y" items="${viewActivityPerWeekPerEmployee.yearOptions}">
	             		<option value="${y.id}"<c:if test='${viewActivityPerWeekPerEmployee.year==y.id}'> selected</c:if>>${y.name}</option>
	        </c:forEach>			
			</select> 
			<!--
			&nbsp;&nbsp;
			<span><img src="images/pg-next.gif" border="0" onclick="moveToNextMonthOfPlanning()"/></span>
			<span><img src="images/pg-last.gif" border="0" onclick="moveToEndOfPlanning()"/></span-->
			<interaudit:accessRightSet right="MODIFY_GENERAL_PLANNING">
				<c:if test='${currentExercise != null && currentExercise.year eq viewActivityPerWeekPerEmployee.year}'>	
					<input style="font:10px Verdana, sans-serif;" type="button" class="button120" value="Update Ts" onclick="updateFromTs();"/>
				</c:if>
			</interaudit:accessRightSet>
			<input type="radio" name="usergroup" value="employees" onclick="showEmployeesOnly();" <c:if test="${ param['usergroup'] eq 'employees' || empty param['usergroup']}">checked</c:if>> 
			
			<span style="font:10px Verdana, sans-serif;margin-right:10pt;" ><strong>Employees</strong></span>	
			<input type="radio" name="usergroup" value="all" onclick="showAll();" <c:if test="${param['usergroup'] eq 'all'}">checked</c:if>><span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>All</strong>    </span> 
				

	
	
	
		<form name="addMultipleTaskForm" action="${ctx}/handleMultipleEventsPage.htm" method="post"  style="margin :2px;">
		<input type="hidden" name="days"/>
		<input type="hidden" name="users"/>
		<input type="hidden" name="todo"/>
		<input type="hidden" name="year"/>
		<input type="hidden" name="startMonth"/>
		<input type="hidden" name="profile"/>
		<input type="hidden" name="usergroup" value="${param['usergroup']}"/>		
		<input  name="missionId" id="missionId"  value="${missionId}" type="hidden">
		 <interaudit:accessRightSet right="MODIFY_GENERAL_PLANNING">
		<c:if test='${currentExercise != null && currentExercise.year eq viewActivityPerWeekPerEmployee.year}'>		
			<!--div style="width:350px;" id="myAutoComplete">
									
									<span style="font:10px Verdana, sans-serif;margin-right:2pt;"><strong>Activite</strong> : </span>
									<img id="item_planning" src="images/add_todo2.gif" border="0" />&nbsp;&nbsp;
									<input id="myInput" value="${missionName}" type="text"> 
									<div id="myContainer"></div>
			</div-->
							
		
				 
		<!--br/-->
		
			<!--input style="font:10px Verdana, sans-serif;" type="button" class="button120" value="Add" onclick="addMultipleTasks();"/-->
			<!--img src="images/add.png" border="1"  onclick="javascript:addMultipleTasks();" style="cursor:hand;"  title="Add Tasks"/-->
			
			&nbsp;
			<!--input style="font:10px Verdana, sans-serif;" type="button" class="button120" value="Clear" onclick="clearAllTasks();"/-->
			<!--img src="images/ico_s_remove_item.gif" border="1"  onclick="javascript:clearAllTasks();" style="cursor:hand;" title="Remove Tasks"/-->

			
			&nbsp;
			
			<!--input style="font:10px Verdana, sans-serif;" type="button" class="button120" value="Update Ts" onclick="updateFromTs();"/-->
		</c:if>
		 </interaudit:accessRightSet>

		
		&nbsp;



		<!--input type="radio" name="usergroup" value="partners" onclick="showPartnersOnly();" <c:if test="${ param['usergroup'] eq 'partners'}">checked</c:if>> 
		<span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Partners</strong></span>
		<input type="radio" name="usergroup" value="managers" onclick="showManagersOnly();" <c:if test="${ param['usergroup'] eq 'managers'}">checked</c:if>> 
		<span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Managers</strong></span-->
		<!--input type="radio" name="usergroup" value="employees" onclick="showEmployeesOnly();" <c:if test="${ param['usergroup'] eq 'employees' || empty param['usergroup']}">checked</c:if>> 
		<span style="font:10px Verdana, sans-serif;margin-right:10pt;" ><strong>Employees</strong></span-->
		<!--input type="radio" name="usergroup" value="secretaires" onclick="showSecretairesOnly();" <c:if test="${ param['usergroup'] eq 'secretaires'}">checked</c:if>> 
		<span style="font:10px Verdana, sans-serif;margin-right:10pt;" ><strong>Secretaires</strong></span-->
		<!--input type="radio" name="usergroup" value="all" onclick="showAll();" <c:if test="${empty param['usergroup']}">checked</c:if>><span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>All</strong>    </span--> 
		
	


	</form>
	</td>
		
	</tr>
</table>




<div>

<div class="nav_alphabet" style="background-color: white; border: 1px solid  #0066aa; text-align:center; padding-top:1pt; font: bold 12px Verdana, sans-serif;">

		
			<span class="galphabet_center"><a style="text-decoration:none;" href="${ctx}/showUpdatedAnnualPlanningPage.htm?year=${viewActivityPerWeekPerEmployee.year}&startMonth=0&usergroup=${ param['usergroup']}">
			
													<c:choose>
														<c:when test='${viewActivityPerWeekPerEmployee.startMonth == 0}'>
														 	<span style="color:red;">Janv</span>
														</c:when>
														<c:when test='${viewActivityPerWeekPerEmployee.endMonth == 0}'>
														 	<span style="color:red;">Janv</span>
														</c:when>
														<c:otherwise>
														<span style="color:blue;">Janv</span>
														</c:otherwise>																								
													</c:choose> 
			</a></span>-
			<span class="galphabet_center"><a style="text-decoration:none;" href="${ctx}/showUpdatedAnnualPlanningPage.htm?year=${viewActivityPerWeekPerEmployee.year}&startMonth=1&usergroup=${ param['usergroup']}">
													<c:choose>
														<c:when test='${viewActivityPerWeekPerEmployee.startMonth == 1}'>
														 	<span style="color:red;">Fevr</span>
														</c:when>
														<c:when test='${viewActivityPerWeekPerEmployee.endMonth == 1}'>
														 	<span style="color:red;">Fevr</span>
														</c:when>
														<c:otherwise>
														<span style="color:blue;">Fevr</span>
														</c:otherwise>																								
													</c:choose> 
			</a></span>-

			<span class="galphabet_center"><a style="text-decoration:none;" href="${ctx}/showUpdatedAnnualPlanningPage.htm?year=${viewActivityPerWeekPerEmployee.year}&startMonth=2&usergroup=${ param['usergroup']}">
													<c:choose>
														<c:when test='${viewActivityPerWeekPerEmployee.startMonth == 2}'>
														 	<span style="color:red;">Mars</span>
														</c:when>
														<c:when test='${viewActivityPerWeekPerEmployee.endMonth == 2}'>
														 	<span style="color:red;">Mars</span>
														</c:when>
														<c:otherwise>
														<span style="color:blue;">Mars</span>
														</c:otherwise>																								
													</c:choose> 
			</a></span>-

			<span class="galphabet_center"><a style="text-decoration:none;" href="${ctx}/showUpdatedAnnualPlanningPage.htm?year=${viewActivityPerWeekPerEmployee.year}&startMonth=3&usergroup=${ param['usergroup']}">
													<c:choose>
														<c:when test='${viewActivityPerWeekPerEmployee.startMonth == 3}'>
														 	<span style="color:red;">Avril</span>
														</c:when>
														<c:when test='${viewActivityPerWeekPerEmployee.endMonth == 3}'>
														 	<span style="color:red;">Avril</span>
														</c:when>
														<c:otherwise>
														<span style="color:blue;">Avril</span>
														</c:otherwise>																								
													</c:choose> 
			</a></span>-

			<span class="galphabet_center"><a style="text-decoration:none;" href="${ctx}/showUpdatedAnnualPlanningPage.htm?year=${viewActivityPerWeekPerEmployee.year}&startMonth=4&usergroup=${ param['usergroup']}">
													<c:choose>
														<c:when test='${viewActivityPerWeekPerEmployee.startMonth == 4}'>
														 	<span style="color:red;">Mai</span>
														</c:when>
														<c:when test='${viewActivityPerWeekPerEmployee.endMonth == 4}'>
														 	<span style="color:red;">Mai</span>
														</c:when>
														<c:otherwise>
														<span style="color:blue;">Mai</span>
														</c:otherwise>																								
													</c:choose> 
			</a></span>-

			<span class="galphabet_center"><a style="text-decoration:none;" href="${ctx}/showUpdatedAnnualPlanningPage.htm?year=${viewActivityPerWeekPerEmployee.year}&startMonth=5&usergroup=${ param['usergroup']}">
													<c:choose>
														<c:when test='${viewActivityPerWeekPerEmployee.startMonth == 5}'>
														 	<span style="color:red;">Juin</span>
														</c:when>
														<c:when test='${viewActivityPerWeekPerEmployee.endMonth == 5}'>
														 	<span style="color:red;">Juin</span>
														</c:when>
														<c:otherwise>
														<span style="color:blue;">Juin</span>
														</c:otherwise>																								
													</c:choose> 
			</a></span>-

			<span class="galphabet_center"><a style="text-decoration:none;" href="${ctx}/showUpdatedAnnualPlanningPage.htm?year=${viewActivityPerWeekPerEmployee.year}&startMonth=6&usergroup=${ param['usergroup']}">
													<c:choose>
														<c:when test='${viewActivityPerWeekPerEmployee.startMonth == 6}'>
														 	<span style="color:red;">Juil</span>
														</c:when>
														<c:when test='${viewActivityPerWeekPerEmployee.endMonth == 6}'>
														 	<span style="color:red;">Juil</span>
														</c:when>
														<c:otherwise>
														<span style="color:blue;">Juil</span>
														</c:otherwise>																								
													</c:choose> 
			</a></span>-

			<span class="galphabet_center"><a style="text-decoration:none;" href="${ctx}/showUpdatedAnnualPlanningPage.htm?year=${viewActivityPerWeekPerEmployee.year}&startMonth=7&usergroup=${ param['usergroup']}">
													<c:choose>
														<c:when test='${viewActivityPerWeekPerEmployee.startMonth == 7}'>
														 	<span style="color:red;">Aout</span>
														</c:when>
														<c:when test='${viewActivityPerWeekPerEmployee.endMonth == 7}'>
														 	<span style="color:red;">Aout</span>
														</c:when>
														<c:otherwise>
														<span style="color:blue;">Aout</span>
														</c:otherwise>																								
													</c:choose> 
			</a></span>-

			<span class="galphabet_center"><a style="text-decoration:none;" href="${ctx}/showUpdatedAnnualPlanningPage.htm?year=${viewActivityPerWeekPerEmployee.year}&startMonth=8&usergroup=${ param['usergroup']}">
													<c:choose>
														<c:when test='${viewActivityPerWeekPerEmployee.startMonth == 8}'>
														 	<span style="color:red;">Sept</span>
														</c:when>
														<c:when test='${viewActivityPerWeekPerEmployee.endMonth == 8}'>
														 	<span style="color:red;">Sept</span>
														</c:when>
														<c:otherwise>
														<span style="color:blue;">Sept</span>
														</c:otherwise>																								
													</c:choose> 
			</a></span>-

			<span class="galphabet_center"><a style="text-decoration:none;" href="${ctx}/showUpdatedAnnualPlanningPage.htm?year=${viewActivityPerWeekPerEmployee.year}&startMonth=9&usergroup=${ param['usergroup']}">
			<c:choose>
														<c:when test='${viewActivityPerWeekPerEmployee.startMonth == 9}'>
														 	<span style="color:red;">Oct</span>
														</c:when>
														<c:when test='${viewActivityPerWeekPerEmployee.endMonth == 9}'>
														 	<span style="color:red;">Oct</span>
														</c:when>
														<c:otherwise>
														<span style="color:blue;">Oct</span>
														</c:otherwise>																								
													</c:choose> 
			</a></span>-

			<span class="galphabet_center"><a style="text-decoration:none;" href="${ctx}/showUpdatedAnnualPlanningPage.htm?year=${viewActivityPerWeekPerEmployee.year}&startMonth=10&usergroup=${ param['usergroup']}">
													<c:choose>
														<c:when test='${viewActivityPerWeekPerEmployee.startMonth == 10}'>
														 	<span style="color:red;">Nov</span>
														</c:when>
														<c:when test='${viewActivityPerWeekPerEmployee.endMonth == 10}'>
														 	<span style="color:red;">Nov</span>
														</c:when>
														<c:otherwise>
														<span style="color:blue;">Nov</span>
														</c:otherwise>																								
													</c:choose> 
			</a></span>-

			<span class="galphabet_center"><a style="text-decoration:none;" href="${ctx}/showUpdatedAnnualPlanningPage.htm?year=${viewActivityPerWeekPerEmployee.year}&startMonth=11&usergroup=${ param['usergroup']}">
												<c:choose>
														<c:when test='${viewActivityPerWeekPerEmployee.startMonth == 11}'>
														 	<span style="color:red;">Dec</span>
														</c:when>
														<c:when test='${viewActivityPerWeekPerEmployee.endMonth == 11}'>
														 	<span style="color:red;">Dec</span>
														</c:when>
														<c:otherwise>
														<span style="color:blue;">Dec</span>
														</c:otherwise>																								
													</c:choose> 
			</span></a></span>

			
</div>
 <div style="width:2400px;">      

		<!--Debut-->
		<%@ include file="/WEB-INF/jsp/views/planning_contents_div.jsp"%>	


</div>
<br/>

  

  

<%--div class="nav_alphabet" style="background-color: rgb(248, 246, 233); border: 1px solid  #0066aa; text-align:center; padding-top:1pt; font: bold 12px Verdana, sans-serif;">

		
			<span class="galphabet_center"><a href="${ctx}/showAnnualPlanningPage.htm?year=${viewActivityPerWeekPerEmployee.year}&startMonth=0&usergroup=${ param['usergroup']}">
			
													<c:choose>
														<c:when test='${viewActivityPerWeekPerEmployee.startMonth == 0}'>
														 	<span style="color:red;">Janv</span>
														</c:when>
														<c:when test='${viewActivityPerWeekPerEmployee.endMonth == 0}'>
														 	<span style="color:red;">Janv</span>
														</c:when>
														<c:otherwise>
														<span style="color:blue;">Janv</span>
														</c:otherwise>																								
													</c:choose> 
			</a></span>-
			<span class="galphabet_center"><a href="${ctx}/showAnnualPlanningPage.htm?year=${viewActivityPerWeekPerEmployee.year}&startMonth=1&usergroup=${ param['usergroup']}">
													<c:choose>
														<c:when test='${viewActivityPerWeekPerEmployee.startMonth == 1}'>
														 	<span style="color:red;">Fevr</span>
														</c:when>
														<c:when test='${viewActivityPerWeekPerEmployee.endMonth == 1}'>
														 	<span style="color:red;">Fevr</span>
														</c:when>
														<c:otherwise>
														<span style="color:blue;">Fevr</span>
														</c:otherwise>																								
													</c:choose> 
			</a></span>-

			<span class="galphabet_center"><a href="${ctx}/showAnnualPlanningPage.htm?year=${viewActivityPerWeekPerEmployee.year}&startMonth=2&usergroup=${ param['usergroup']}">
													<c:choose>
														<c:when test='${viewActivityPerWeekPerEmployee.startMonth == 2}'>
														 	<span style="color:red;">Mars</span>
														</c:when>
														<c:when test='${viewActivityPerWeekPerEmployee.endMonth == 2}'>
														 	<span style="color:red;">Mars</span>
														</c:when>
														<c:otherwise>
														<span style="color:blue;">Mars</span>
														</c:otherwise>																								
													</c:choose> 
			</a></span>-

			<span class="galphabet_center"><a href="${ctx}/showAnnualPlanningPage.htm?year=${viewActivityPerWeekPerEmployee.year}&startMonth=3&usergroup=${ param['usergroup']}">
													<c:choose>
														<c:when test='${viewActivityPerWeekPerEmployee.startMonth == 3}'>
														 	<span style="color:red;">Avril</span>
														</c:when>
														<c:when test='${viewActivityPerWeekPerEmployee.endMonth == 3}'>
														 	<span style="color:red;">Avril</span>
														</c:when>
														<c:otherwise>
														<span style="color:blue;">Avril</span>
														</c:otherwise>																								
													</c:choose> 
			</a></span>-

			<span class="galphabet_center"><a href="${ctx}/showAnnualPlanningPage.htm?year=${viewActivityPerWeekPerEmployee.year}&startMonth=4&usergroup=${ param['usergroup']}">
													<c:choose>
														<c:when test='${viewActivityPerWeekPerEmployee.startMonth == 4}'>
														 	<span style="color:red;">Mai</span>
														</c:when>
														<c:when test='${viewActivityPerWeekPerEmployee.endMonth == 4}'>
														 	<span style="color:red;">Mai</span>
														</c:when>
														<c:otherwise>
														<span style="color:blue;">Mai</span>
														</c:otherwise>																								
													</c:choose> 
			</a></span>-

			<span class="galphabet_center"><a href="${ctx}/showAnnualPlanningPage.htm?year=${viewActivityPerWeekPerEmployee.year}&startMonth=5&usergroup=${ param['usergroup']}">
													<c:choose>
														<c:when test='${viewActivityPerWeekPerEmployee.startMonth == 5}'>
														 	<span style="color:red;">Juin</span>
														</c:when>
														<c:when test='${viewActivityPerWeekPerEmployee.endMonth == 5}'>
														 	<span style="color:red;">Juin</span>
														</c:when>
														<c:otherwise>
														<span style="color:blue;">Juin</span>
														</c:otherwise>																								
													</c:choose> 
			</a></span>-

			<span class="galphabet_center"><a href="${ctx}/showAnnualPlanningPage.htm?year=${viewActivityPerWeekPerEmployee.year}&startMonth=6&usergroup=${ param['usergroup']}">
													<c:choose>
														<c:when test='${viewActivityPerWeekPerEmployee.startMonth == 6}'>
														 	<span style="color:red;">Juil</span>
														</c:when>
														<c:when test='${viewActivityPerWeekPerEmployee.endMonth == 6}'>
														 	<span style="color:red;">Juil</span>
														</c:when>
														<c:otherwise>
														<span style="color:blue;">Juil</span>
														</c:otherwise>																								
													</c:choose> 
			</a></span>-

			<span class="galphabet_center"><a href="${ctx}/showAnnualPlanningPage.htm?year=${viewActivityPerWeekPerEmployee.year}&startMonth=7&usergroup=${ param['usergroup']}">
													<c:choose>
														<c:when test='${viewActivityPerWeekPerEmployee.startMonth == 7}'>
														 	<span style="color:red;">Aout</span>
														</c:when>
														<c:when test='${viewActivityPerWeekPerEmployee.endMonth == 7}'>
														 	<span style="color:red;">Aout</span>
														</c:when>
														<c:otherwise>
														<span style="color:blue;">Aout</span>
														</c:otherwise>																								
													</c:choose> 
			</a></span>-

			<span class="galphabet_center"><a href="${ctx}/showAnnualPlanningPage.htm?year=${viewActivityPerWeekPerEmployee.year}&startMonth=8&usergroup=${ param['usergroup']}">
													<c:choose>
														<c:when test='${viewActivityPerWeekPerEmployee.startMonth == 8}'>
														 	<span style="color:red;">Sept</span>
														</c:when>
														<c:when test='${viewActivityPerWeekPerEmployee.endMonth == 8}'>
														 	<span style="color:red;">Sept</span>
														</c:when>
														<c:otherwise>
														<span style="color:blue;">Sept</span>
														</c:otherwise>																								
													</c:choose> 
			</a></span>-

			<span class="galphabet_center"><a href="${ctx}/showAnnualPlanningPage.htm?year=${viewActivityPerWeekPerEmployee.year}&startMonth=9&usergroup=${ param['usergroup']}">
			<c:choose>
														<c:when test='${viewActivityPerWeekPerEmployee.startMonth == 9}'>
														 	<span style="color:red;">Oct</span>
														</c:when>
														<c:when test='${viewActivityPerWeekPerEmployee.endMonth == 9}'>
														 	<span style="color:red;">Oct</span>
														</c:when>
														<c:otherwise>
														<span style="color:blue;">Oct</span>
														</c:otherwise>																								
													</c:choose> 
			</a></span>-

			<span class="galphabet_center"><a href="${ctx}/showAnnualPlanningPage.htm?year=${viewActivityPerWeekPerEmployee.year}&startMonth=10&usergroup=${ param['usergroup']}">
													<c:choose>
														<c:when test='${viewActivityPerWeekPerEmployee.startMonth == 10}'>
														 	<span style="color:red;">Nov</span>
														</c:when>
														<c:when test='${viewActivityPerWeekPerEmployee.endMonth == 10}'>
														 	<span style="color:red;">Nov</span>
														</c:when>
														<c:otherwise>
														<span style="color:blue;">Nov</span>
														</c:otherwise>																								
													</c:choose> 
			</a></span>-

			<span class="galphabet_center"><a href="${ctx}/showAnnualPlanningPage.htm?year=${viewActivityPerWeekPerEmployee.year}&startMonth=11&usergroup=${ param['usergroup']}">
												<c:choose>
														<c:when test='${viewActivityPerWeekPerEmployee.startMonth == 11}'>
														 	<span style="color:red;">Dec</span>
														</c:when>
														<c:when test='${viewActivityPerWeekPerEmployee.endMonth == 11}'>
														 	<span style="color:red;">Dec</span>
														</c:when>
														<c:otherwise>
														<span style="color:blue;">Dec</span>
														</c:otherwise>																								
													</c:choose> 
			</span></a></span>

			<!--
			
			<span><img src="images/pg-first.gif" border="0" onclick="moveToStartOfPlanning()"/></span>
			<span><img src="images/pg-prev.gif" border="0" onclick="moveToPrevMonthOfPlanning()"/></span>
			&nbsp;&nbsp;
			<select style="font:10px Verdana, sans-serif;margin-right:10pt;" id="select_planning_year" name="year" onchange="loadPlanningForYear()">
		    <c:forEach var="y" items="${viewActivityPerWeekPerEmployee.yearOptions}">
	             		<option value="${y.id}"<c:if test='${viewActivityPerWeekPerEmployee.year==y.id}'> selected</c:if>>${y.name}</option>
	        </c:forEach>			
			</select> 
			</select>
			&nbsp;&nbsp;
			<span><img src="images/pg-next.gif" border="0" onclick="moveToNextMonthOfPlanning()"/></span>
			<span><img src="images/pg-last.gif" border="0" onclick="moveToEndOfPlanning()"/></span>
			-->
</div--%>
<div style="border-bottom: 0px solid gray; background-color: white;">
		<c:if test="${not empty actionErrors}">
				<div style="width: 100%">
				<div class="validation"><c:forEach var="actionError"
					items="${actionErrors}">
					<li><span style="color: blue;"><c:out value="${actionError}" escapeXml="false" /></span></li>
				</c:forEach></div>
				</div>
				<c:set var="actionErrors" value="" scope="session" />
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
</div>
  
</div>


		<form name="dragAndDropEventForm" action="${ctx}/addSingleEventsFromDragAndDropPage.htm" method="post" >
			<input type="hidden" name="employeeId"/>
			<input type="hidden" name="planningId"/>
			<input type="hidden" name="weekNumber"/>			
			<input type="hidden" name="missionId"/>
			<input type="hidden" name="missionName"/>
			<input type="hidden" name="year" value="${viewActivityPerWeekPerEmployee.year}"/>
			<input type="hidden" name="startMonth" value="${viewActivityPerWeekPerEmployee.startMonth}"/>            
            <input type="hidden" name="usergroup" value="${ param['usergroup']}"/>
		</form>


<div id="addDialog">
	<div class="hd">
        <span id="formTitle" style="color:blue;"></span>
    </div>
    <div class="bd">
	<form name="editEventPlanningForm" method="post" action="${ctx}/showAnnualPlanningPage.htm">
			
			<input type="hidden" name="user"/>
			<input type="hidden" name="week"/>
			<input type="hidden" name="year"/>
			<input type="hidden" name="startMonth" value="${viewActivityPerWeekPerEmployee.startMonth}"/>
			<input type="hidden" name="idPlanning"/>
			<input type="hidden" name="selectedIds"/>
			<input type="hidden" name="usergroup" value="${ param['usergroup']}"/>
			<input type="hidden" name="year" value="${viewActivityPerWeekPerEmployee.year}"/>
			<!--input  name="missionId" id="missionId" type="hidden"-->

			<fieldset style="margin-bottom:10pt;">
				<legend>
					<span  style="color:purple;font-weight: bold;">
                    Activities from 
                    <span style="color:red;font-weight: bold;" id="firstDateOfWeek"></span> to 
                    <span style="color:red;font-weight: bold;" id="lastDateOfWeek"></span>
                    </span>
				</legend>

				<dl>

				
					
					<span style="font:10px Verdana, sans-serif;margin-right:1pt;"><strong>Semaine</strong> : </span>
					<img style="margin-top:10pt;" src="images/resultset_previous.png" border="0" />
					<select style="font:10px Verdana, sans-serif;margin-right:10pt;" name="semaine" onchange="loadEmployeePlanning();">						
						<c:forEach var="date" begin="${viewActivityPerWeekPerEmployee.startWeekNumber}" end="${viewActivityPerWeekPerEmployee.endWeekNumber}">									
									<option value="${date}">
									${date}
									</option>
						</c:forEach>
					</select> 
					<img src="images/resultset_next.png" border="0" />
					
					
					<span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Employ&eacute</strong> : </span>
					<select style="font:10px Verdana, sans-serif;margin-right:10pt;" name="employee" onchange="loadEmployeePlanning();">						
						<c:forEach var="item" items="${viewActivityPerWeekPerEmployee.employeeOptions}" varStatus="loop">								
								<option value="${item.id}">
									<font size="1">${fn:substring(fn:toUpperCase(item.longName), 0,20)}</font>
									</option>
							</c:forEach>	
					</select>
					&nbsp;

					<!--input style="font:10px Verdana, sans-serif;" type="button" class="button120" value="Load..." onclick="loadEmployeePlanning();"/-->	
					
					<interaudit:accessRightSet right="MODIFY_GENERAL_PLANNING">
						<c:if test='${currentExercise != null && currentExercise.year eq viewActivityPerWeekPerEmployee.year}'>
							<input style="font:10px Verdana, sans-serif;" type="button" class="button120" value="Remove..." onclick="deleteEventPlanningItemsPage();"/>
						</c:if>
					</interaudit:accessRightSet>
				</dl>

				

				<dl>			
					<div style="margin:auto;" id="containerEvents"></div>										
				</dl>					 
			</fieldset>  

			<fieldset style="margin-bottom:10pt;">
				<legend>
					<span  style="color:purple;font-weight: bold;">
					Manage activities for employee...
					</span>
				</legend>
					
				
				<dl>
				
					
					<select style="font:10px Verdana, sans-serif;margin-right:10pt;" id="missionId" name="missionId">
						<option value="-1" selected>Select one... </option>
						<c:forEach var="y" items="${viewActivityPerWeekPerEmployee.taskOptions}">									
									<option value="${y.id}">
									${fn:substring(fn:toUpperCase(y.name), 0, 20)}
									</option>
						</c:forEach>
						<c:forEach var="y" items="${viewActivityPerWeekPerEmployee.missionOptions}">									
									<option value="${y.id}">
									${fn:substring(fn:toUpperCase(y.name), 0, 20)}
									</option>
						</c:forEach>
					</select>
					
					
					
					<!--div style="float:left;margin-right:80pt;width:400px;" id="myAutoComplete2">
								<span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Activit&eacute</strong> : </span>
								<input id="myInput2" type="text">
								<div id="myContainer2"></div>
					</div-->
					&nbsp;
					
					

					&nbsp;
					<input type="radio" name="duration" value="AM">Matin 
					&nbsp;
					<input type="radio" name="duration"value="PM">Après-midi
					
				</dl>
				
				<dl>				
					<span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Début</strong> : </span>
					<select style="font:10px Verdana, sans-serif;margin-right:10pt;" name="startdate">
											
					</select> 
					&nbsp;
					<span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Fin</strong> : </span>
					<select style="font:10px Verdana, sans-serif;margin-right:10pt;" name="enddate">
											
					</select> 
					
					&nbsp;
					<span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Heures</strong> : </span>
					<select style="font:10px Verdana, sans-serif;margin-right:10pt;" name="nbhours">
						<c:forEach var="y" items="${viewActivityPerWeekPerEmployee.timeOptions}">									
									<option value="${y.id}">
									${fn:substring(fn:toUpperCase(y.name), 0, 20)}
									</option>
						</c:forEach>					
					</select> 

				<%--
					&nbsp;
					<span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Dur�e</strong> : </span>
					

					<select style="font:10px Verdana, sans-serif;margin-right:10pt;" name="qte">
						<option value="-1">... </option>
						<option value="0.5">0.5 </option>
						<option value="1.0" selected>1.0 </option>
						<option value="1.5">1.5</option>
						<option value="2.0">2.0 </option>
						<option value="2.5">2.5 </option>
						<option value="3.0">3.0</option>
						<option value="3.5">3.5 </option>
						<option value="4.0">4.0 </option>
						<option value="4.5">4.5</option>
						<option value="5.0">5.0 </option>						
					</select> 
					
					
					--%>
				</dl>	

			

					<dl align="right">
					<interaudit:accessRightSet right="MODIFY_GENERAL_PLANNING">
						<c:if test='${currentExercise != null && currentExercise.year eq viewActivityPerWeekPerEmployee.year}'>
						<input style="font:10px Verdana, sans-serif;" type="button" class="button120" value="Add..." onclick="addSingleEventPlanningItemsPage();"/>	
						</c:if>
					</interaudit:accessRightSet>
						<div id="textDiv"></div>
					</dl>	

				
			</fieldset> 
			<!--fieldset style="margin-bottom:10pt;">
				<legend>
					<span  style="color:purple;font-weight: bold;">
					Manage activities...
					</span>
				</legend>
								 
			</fieldset-->
			
			<!--fieldset >
				<legend>
					<span  style="color:purple;font-weight: bold;">
					Delete activities...
					</span>
				</legend>
				<dl>			
					
				</dl>					 
			</fieldset-->  
			
	</form>
	</div>
	
</div>



