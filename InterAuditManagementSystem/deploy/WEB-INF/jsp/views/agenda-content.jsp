<jsp:directive.page contentType="text/html;charset=UTF-8" />
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="t"      uri="/tags/tooltips-tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/interaudit" prefix="interaudit" %>
<%@ taglib prefix="t"      uri="/tags/tooltips-tiles" %>


<link type="text/css" href="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/themes/base/ui.all.css" rel="stylesheet" />
		<script type="text/javascript" src="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/jquery-1.3.2.js"></script>
		<script type="text/javascript" src="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/ui/ui.core.js"></script>
		<script type="text/javascript" src="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/ui/ui.draggable.js"></script>
		<script type="text/javascript" src="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/ui/ui.droppable.js"></script>
		<script type="text/javascript" src="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/ui/ui.resizable.js"></script>
		<script type="text/javascript" src="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/ui/ui.dialog.js"></script>
		
		
		


<%@ page import="java.util.*,java.text.*,java.io.PrintWriter,com.interaudit.service.view.AgendaPlanningView" %>

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
		<c:if test='${viewAgendaPerEmployee.modifiable eq false}'> 
										
		
		$( ".item_planning" ).draggable(
			{ 
			  cursor: 'crosshair',
			  handle:'img', 
			  opacity: 0.7,
			  helper: function(event) {
												var text = $(this).html() ;
												return $('<div class="ui-widget-header">'+text+'</div>');
										},
			  start: function(event, ui) {
											var missionId = $(this).attr('id');
											 var form = document.forms["dragAndDropEventForm"];
											 form.elements["mission"].value = missionId;
											 form.elements["typeAction"].value = "addNewEvent";
										}

			});
			
			
			$( ".item_planning_em" ).draggable(
			{ 
			  cursor: 'crosshair',
			  handle:'img', 
			  opacity: 0.7,
			  helper: function(event) {
												var text = $(this).html() ;
												return $('<div class="ui-widget-header">'+text+'</div>');
										},
			  start: function(event, ui) {
			  
											 var eventId = $(this).attr('id');
											 var form = document.forms["dragAndDropEventForm"];											
											 form.elements["eventId"].value = eventId;
											 form.elements["typeAction"].value = "changeStartHour";
											
										}

			});
			
			
		// let the trash be droppable, accepting the gallery items
        $( ".item_placeholder" ).droppable({
			activeClass: 'ui-state-hover',
			hoverClass: 'ui-state-active',
            drop: function( event, ui ) {
			    var key = $(this).attr('id');
				var starttime = key.substring(0,key.indexOf("##",0));				
				var jour = key.substring(key.indexOf("##",0)+2);
				
				 var form = document.forms["dragAndDropEventForm"];
				 form.elements["jour"].value = jour;
				 form.elements["starttime"].value = starttime;
				 changeDayOfYearValueForDragAndDropEventForm();
				 form.submit();
				 showMessage("The agenda has been successfully updated...","ok");

            }
        });
		</c:if>
	});
	</script>




<!-- START ADDED PART -->   

<style>

div#menu {
	float:left;
	width:20%;
	height:800px;
	background-color:#FFFFFF;
	}
div#contenu {
	float:left;
	width:80%;
	/*height:400px;*/
	/*background-color:#FFCC00;*/
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
		font-size: 12px;
		font-weight: normal;
		/*padding: 12px 15px;*/
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
	font-size: 12px;
	background: #fff;
	/*margin: 10px;*/
	/*width: 750px;*/
	border-collapse: collapse;
	text-align: center;
}
#hor-minimalist-b th
{
	font-size: 12px;
	font-weight: normal;
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
</style>



<script>
 
 function updateFromUsers( obj ){
	
	var dates = document.getElementsByName("day");
	var count = dates.length;
	
    if (count > 0)
    {
      for(index=0; index < count; index++)
      {
		
		var tdId = dates[index].id +"##"+ obj.id;
		
        if ( /*dates[index].checked && */obj.checked)
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
		
        if ( /*users[index].checked && */obj.checked)
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
		dates[index].checked = obj.checked;
		
      }
    }
}

</script>

<%
 						SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd MMMM ", new Locale("fr","FR"));
					 	AgendaPlanningView planningView = (AgendaPlanningView)request.getAttribute("viewAgendaPerEmployee");
					 	String year = planningView.getYear();
					 	String week = planningView.getWeek();
					 	
					   //Compute the maximun dates of the selected month
					   Calendar c = Calendar.getInstance();
					   c.set(Calendar.YEAR,Integer.parseInt(year));
					   c.set(Calendar.WEEK_OF_YEAR,Integer.parseInt(week));
					   
					   int month = c.get(Calendar.MONTH);
					
					   c.set(Calendar.DAY_OF_WEEK , Calendar.MONDAY);
					   String mondayDate = dateFormat.format(c.getTime());
					   int firstDate = c.get(Calendar.DAY_OF_YEAR);
					   long mondayInt = c.getTimeInMillis();
					   
					   c.set(Calendar.DAY_OF_WEEK , Calendar.TUESDAY);
					   String tuesdaydayDate = dateFormat.format(c.getTime());
					   long tuesdayInt =  c.getTimeInMillis();
					   
					   c.set(Calendar.DAY_OF_WEEK , Calendar.WEDNESDAY);
					   String wednesdayDate = dateFormat.format(c.getTime());
					   long wednesdayInt = c.getTimeInMillis();
					   
					   c.set(Calendar.DAY_OF_WEEK , Calendar.THURSDAY);
					   String thursdayDate = dateFormat.format(c.getTime());
					   long thursdayInt = c.getTimeInMillis();
						
					   c.set(Calendar.DAY_OF_WEEK , Calendar.FRIDAY);
					   String fridayDate = dateFormat.format(c.getTime());
					   long fridayInt = c.getTimeInMillis();
						
					   c.set(Calendar.DAY_OF_WEEK , Calendar.SATURDAY);
					   String saturdayDate = dateFormat.format(c.getTime());
					   long saturdayInt = c.getTimeInMillis();
						   
					   c.set(Calendar.DAY_OF_WEEK , Calendar.SUNDAY);
					   String sundayDate = dateFormat.format(c.getTime());
					   long sundayInt = c.getTimeInMillis();
	   
						int lastDate = c.get(Calendar.DAY_OF_YEAR);
						
					 %>





    
<div class="nav_alphabet" style="background-color: rgb(248, 246, 233); border: 1px solid  #0066aa; text-align:center; padding-top:1pt; font: bold 12px Verdana, sans-serif;">
	
	<form name="agendaSearchForm" action="${ctx}/showAgendaPage.htm" method="post" >
		
		<span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Exercise</strong> : </span>
		<select style="font:10px Verdana, sans-serif;margin-right:10pt;" name="year"  onchange="document.agendaSearchForm.submit();">
		    <c:forEach var="y" items="${viewAgendaPerEmployee.yearOptions}">
	             		<option value="${y.id}"<c:if test='${viewAgendaPerEmployee.year==y.id}'> selected</c:if>>${y.name}</option>
	        </c:forEach>
		</select> 
	
		<span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Week</strong> : </span>
		<select style="font:10px Verdana, sans-serif;margin-right:10pt;" name="week"  onchange="document.agendaSearchForm.submit();">
			<c:forEach var="weekNumber" begin="1" end="53" varStatus="loop">
				<option value="${weekNumber}"<c:if test='${viewAgendaPerEmployee.week==weekNumber}'> selected</c:if>>${weekNumber}</option>
			</c:forEach>
		</select>

		du : <span style ="color:red;"><%=mondayDate%></span> au <span style ="color:red;"><%=fridayDate%></span>
		
		<interaudit:accessRightSet right="MODIFY_CALENDAR">
		<span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Employee</strong> : </span>
		<select style="font:10px Verdana, sans-serif;margin-right:10pt;" name="employeeId"  onchange="document.agendaSearchForm.submit();">
			<c:forEach var="y" items="${viewAgendaPerEmployee.employeeOptions}">
				<option value="${y.id}"<c:if test='${viewAgendaPerEmployee.employee.id==y.id}'> selected</c:if>>${y.name}</option>
			</c:forEach> 									
		</select>
		</interaudit:accessRightSet>
						
		&nbsp;
		<!--input style="font:10px Verdana, sans-serif;" type="submit" class="button120" value="Search"/-->	
		&nbsp;&nbsp;&nbsp;
		<c:if test='${viewAgendaPerEmployee.modifiable eq true}'> 
			<span style="color:red;"><strong>BLOQUE</strong></span>								
		</c:if>
		<c:if test='${viewAgendaPerEmployee.modifiable eq false}'> 
			<span style="color:green;"><strong>OUVERT</strong></span>								
		</c:if>
	</form>

	
</div>
			
<br/>
 					
<div id="contenu">
 
       <table id="ver-zebra" width="100%" cellspacing="0" class="formlist">
					<thead>
						<tr>
							<th scope="col" style="width : 60px;">&nbsp;</th>
							
							<th id="1" scope="col">
							<span style=" color: #68a1e5;"><%=mondayDate%></span>
							
							</th>
							
							<th id="2" scope="col">							
							<span style=" color: #68a1e5;"><%=tuesdaydayDate%></span>
							</th>
							
							<th id="3" scope="col">							
							<span style=" color: #68a1e5;"><%=wednesdayDate%></span>
							</th>
							
							<th id="4" scope="col">
							
							<span style=" color: #68a1e5;"><%=thursdayDate%></span>
							</th>
							
							<th id="5" scope="col">							
							<span style=" color: #68a1e5;"><%=fridayDate%></span>
							</th>

							<%--th id="6"  scope="col">							
							<span style=" color: #68a1e5;"><%=saturdayDate></span>
							</th>
							
							<th id="7" scope="col">							
							<span style=" color: #68a1e5;"><%=sundayDate></span>
							</th--%>
							
							
						</tr>
					</thead>
					<tbody>
					 
					
					
					 <c:set var="row" value="0"/>
					 <c:forEach var="hour" begin="0" end="52">
					 	
							<tr  onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
								
									<c:if test='${row % 4 eq 0}'>
										<th scope="row"  ROWSPAN="4" id="${hour}"   >
											<span style=" color: #68a1e5;">
												<fmt:formatNumber  minFractionDigits="0">
													${(hour / 4 ) + 8 }
												</fmt:formatNumber>h
											</span>
										</th>
									</c:if>

									
									<c:choose>
													<c:when test='${row % 4 eq 0}'>
														<c:set var="borderlinedotted" value="border-bottom: 1px dotted #999;" />
													</c:when>
													<c:otherwise>
													  <c:set var="borderlinedotted" value="border-bottom: 1px dotted #999;" />
													</c:otherwise>
									</c:choose>
									
									<c:forEach var="day" begin="1" end="5" varStatus="loop">
										  <c:set var="key" value="${hour}-${day}"/>

										  <c:if test='${viewAgendaPerEmployee.activities[key] eq null}'>
											<td class="item_placeholder" id="${hour}##${day}" style="${borderlinedotted}" style="cursor:hand;"

											<c:if test='${viewAgendaPerEmployee.modifiable eq false}'> 
											onclick="addEventFromCell(${day}, '${viewAgendaPerEmployee.year}',${hour})"
											</c:if>>
												&nbsp;
											</td>
										  </c:if>


										  
											<%--rowspan="${viewAgendaPerEmployee.activities[key].endHour -	viewAgendaPerEmployee.activities[key].startHour }"--%>
										  <c:if test='${viewAgendaPerEmployee.activities[key] != null}'>
											
											<c:if test='${hour eq viewAgendaPerEmployee.activities[key].startHour}'>
												<td class="item_placeholder" id="${hour}##${day}" style="${borderlinedotted};background-color : white;border-bottom: 1px solid #D2A901;" >
													<div style="background-color : white;border: 0px solid blue;  top: 0; left: 0;border: 1px solid #D2A901;"">
													

														
														<t:tooltip>  
														   <t:text><em id="${viewAgendaPerEmployee.activities[key].id}" class="item_planning_em">
														   <span style=" color: purple;">	
														${viewAgendaPerEmployee.activities[key].startHourAsString} à ${viewAgendaPerEmployee.activities[key].endHourAsString}
														</span>
														&nbsp;
														<a href="#" onclick="editEvent(${viewAgendaPerEmployee.activities[key].id})" title="">
																			<span style=" color: #68a1e5;"><i>
																			${fn:substring(viewAgendaPerEmployee.activities[key].type,0,5)}
																			</i></span>
																		</a>
														<c:if test='${viewAgendaPerEmployee.modifiable eq false}'> 
															&nbsp;
															<a href="#" onclick="deleteEvent(${viewAgendaPerEmployee.activities[key].id},${viewAgendaPerEmployee.employee.id},${viewAgendaPerEmployee.year},${viewAgendaPerEmployee.week})">
																<img style="margin-right:0;" src="images/dep_delete_icon_n.png" border="0" alt="Delete event"/>
															</a>

													</c:if>
														</em></t:text>
														  
														<t:help width="150" >
														    ${viewAgendaPerEmployee.activities[key].type}
														   
														</t:help> 
													 </t:tooltip>

														

														
													
													</div>
													
												
												</td>
											</c:if>

											<c:if test='${hour != viewAgendaPerEmployee.activities[key].startHour}'>

												<c:choose>
													<c:when test='${hour eq ( viewAgendaPerEmployee.activities[key].endHour - 1)}'>


														<c:set var="thesthyle" value="border-bottom: 1px dotted #999;background-color :  #ffffcc;border-bottom: 1px solid #D2A901;" />

														<c:if test='${"CONGE" eq viewAgendaPerEmployee.activities[key].type}'>
															<c:set var="thesthyle" value="border-bottom: 1px dotted #999;background-color :  #BCFADC;border-bottom: 1px solid #BCFADC;" />
														</c:if>

														<c:if test='${"MALADIE" eq viewAgendaPerEmployee.activities[key].type}'>
															<c:set var="thesthyle" value="border-bottom: 1px dotted #999;background-color :  #E85976;border-bottom: 1px solid #E85976;" />
														</c:if>

														<c:if test='${"JOUR FERIE" eq viewAgendaPerEmployee.activities[key].type}'>
															<c:set var="thesthyle" value="border-bottom: 1px dotted #999;background-color :  #D2D2FF;border-bottom: 1px solid #D2D2FF;" />
														</c:if>


														



													</c:when>
													<c:otherwise>
													  <c:set var="thesthyle" value="border-bottom: 1px dotted #999;background-color : #ffffcc;border-bottom: 0px solid #D2A901;" />

													  <c:if test='${"CONGE" eq viewAgendaPerEmployee.activities[key].type}'>
															<c:set var="thesthyle" value="border-bottom: 1px dotted #999;background-color :  #BCFADC;border-bottom: 1px solid #BCFADC;" />
														</c:if>

														<c:if test='${"MALADIE" eq viewAgendaPerEmployee.activities[key].type}'>
															<c:set var="thesthyle" value="border-bottom: 1px dotted #999;background-color :  #E85976;border-bottom: 1px solid #E85976;" />
														</c:if>

														<c:if test='${"JOUR FERIE" eq viewAgendaPerEmployee.activities[key].type}'>
															<c:set var="thesthyle" value="border-bottom: 1px dotted #999;background-color :  #D2D2FF;border-bottom: 1px solid #D2D2FF;" />
														</c:if>
													</c:otherwise>
												</c:choose>
											  
												<td  class="item_placeholder" id="${hour}##${day}" style="${thesthyle};" >	

													<c:if test='${hour eq viewAgendaPerEmployee.activities[key].midHour}'>
													<t:tooltip>  
														   <t:text><em id="${viewAgendaPerEmployee.activities[key].id}" class="item_planning_em">
														<a href="#" onclick="editEvent(${viewAgendaPerEmployee.activities[key].id})" title="">
																			<span style=" color: #68a1e5;"><i>
																			${fn:substring(viewAgendaPerEmployee.activities[key].type,0,15)}
																			
																			</i></span>
																			<br/>
																			${fn:substring(viewAgendaPerEmployee.activities[key].title,0,15)}																			
																		</a>
														</em></t:text>
														  
														<t:help width="150" >
														   <font color="STEELBLUE"><strong>Info :</strong></font> ${viewAgendaPerEmployee.activities[key].title}
														   
														</t:help> 
													 </t:tooltip>
																																									
													
													</c:if>
													<c:if test='${hour != viewAgendaPerEmployee.activities[key].midHour}'>
													&nbsp;
													</c:if>
												</td>
											</c:if>
										  </c:if>
										
										  
									</c:forEach>
								</tr>	
							
							<c:set var="row" value="${row + 1}"/>  
					</c:forEach>
		
					</tbody>
		</table>
</div>
<div id="menu">
<table id="hor-minimalist-b" summary="Messages related to the mission" style="width:100%;">
 <thead>
			<tr>
				
				<th style="background-color:#372417;" scope="col" colspan="5" align="center"><span style="color:white;"><strong>MON PLANNING</strong></span></th>
				
			</tr>
		</thead>
<tbody>
 </table>
		<c:set var="row" value="0"/>
			<c:forEach var="item" items="${viewAgendaPerEmployee.events}" >	

			<c:choose>
					<c:when test='${row % 2 eq 0 }'>
					 <div id="${item.idEntity}" class="item_planning" style="color: blue;background-color:#F0E951;width: 100%;border:1px solid #0080FF;">							      			
					</c:when>
					<c:otherwise>					 
					 <div id="${item.idEntity}" class="item_planning" style="color: blue;background-color:#F0E951;width: 100%;border:1px solid #0080FF;">
					</c:otherwise>
				</c:choose>
			<%--div style="text-decoration: underline;color: black;">
				  <fmt:formatDate value="${item.dateOfEvent}" type="both" dateStyle="short"/>

			 </div--%>

			 
				
				<div >
				<img style="margin-right:0;" src="images/activite.png" border="0" alt=""/>

				<c:if test='${item.expectedStartDate != null && item.expectedEndDate != null}'>
				 <span style="color:black;"><fmt:formatDate value="${item.expectedStartDate}" type="both" dateStyle="short" pattern="dd/MM/yyyy"/></span>  au <span style="color:black;"><fmt:formatDate value="${item.expectedEndDate}" type="both" dateStyle="short" pattern="dd/MM/yyyy"/> </span>		
				 </c:if>				
				<p align="center">
				
				<a href="#" onclick="addNewEventFromPlanning(${item.idEntity},'${item.title}',${item.mission} )">
							<span style="color:black;"><strong>${item.title}</strong></span> 						
				</a>
				</p>
				</div>
				
			<c:set var="row" value="${row + 1}"/>
			</div>
			</c:forEach>


</div>




  <!-- END ADDED PART --> 
  
  <div class="bd">
		<form name="dragAndDropEventForm" action="${ctx}/handleEventPage.htm" method="post" >
			<input type="hidden" name="userId" value="${viewAgendaPerEmployee.employee.id}"/>
			<input type="hidden" name="year" value="${viewAgendaPerEmployee.year}"/>
			<input type="hidden" name="month" value="<%=month%>"/>			
			<input type="hidden" name="week" value="${viewAgendaPerEmployee.week}"/>
			<input type="hidden" name="dayOfYear" id="dayOfYear"/>
			
			<input type="hidden" name="eventId"/>
			<input type="hidden" name="jour"/>
			<input type="hidden" name="starttime"/>
			<input type="hidden" name="endtime"/>
			<input type="hidden" name="mission"/>
			<input type="hidden" name="typeAction"/>
			<input type="hidden" name="title"/>
		</form>
</div>
  
  <div id="addDialog">
		<div class="hd">
			<span id="formTitle" style="color:#039;">Add Event</span>
		</div>

		<div class="bd">
		<form name="addEventForm" action="${ctx}/handleEventPage.htm" method="post" >
		<input type="hidden" name="userId" value="${viewAgendaPerEmployee.employee.id}"/>
		<input type="hidden" name="year" value="${viewAgendaPerEmployee.year}"/>
		<input type="hidden" name="month" value="<%=month%>"/>
		
		<input type="hidden" name="week" value="${viewAgendaPerEmployee.week}"/>
		<input type="hidden" name="dayOfYear" id="dayOfYear"/>
		<input type="hidden" name="eventId"/>


		

    
    <fieldset>

			<dl>
				<label for="starttime"><span style="color:#039;"><strong>Jour :</strong></span></label>
				&nbsp;&nbsp;
				<select style="width:200px;" size="1" name="jour" id="jour" onChange="changeDayOfYearValue();">
						
							<option value="1"><%=mondayDate%></option>
							<option value="2"><%=tuesdaydayDate%></option>
							<option value="3"><%=wednesdayDate%></option>
							<option value="4"><%=thursdayDate%></option>
							<option value="5"><%=fridayDate%></option>	
				</select>
				
			</dl>


			 
			  <dl>
				<label for="starttime"><span style="color:#039;"><strong>Début :</strong></span></label>
				&nbsp;&nbsp;
				<select style="width:100px;" size="1" name="starttime" id="starttime">
						<c:forEach var="y" items="${viewAgendaPerEmployee.timeOptions}">
							<option value="${y.id}">${y.name}</option>
						</c:forEach>					
				</select>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<label for="endtime"><span style="color:#039;"><strong>Fin :</strong></span></label>
				&nbsp;&nbsp;
				<select style="width:100px;" size="1" name="endtime" id="endtime">
						<c:forEach var="y" items="${viewAgendaPerEmployee.timeOptions}">
							<option value="${y.id}">${y.name}</option>
						</c:forEach>
				</select>
			</dl>
	
   
		<dl>
        		<%--label for="object"><span style="color:#039;"><strong>Objet:</strong></span></label>
				<select name="object">
					
					<option value="MISSION">MISSION</option>
				</select>
				&nbsp;&nbsp;--%>
				<label><span style="color:#039;"><strong>Activité :</strong></span></label>
				<select name="mission"   onChange="displaysub()">		
					<option value="#">This is a place Holder text </option>
					<option value="#">This is a Place Holder text </option>
					<option value="#">This is a Place Holder text </option>
						<%--c:forEach var="y" items="${viewAgendaPerEmployee.taskOptions}">
							<option value="${y.id}">${y.name}</option>
						</c:forEach>

						<c:forEach var="y" items="${viewAgendaPerEmployee.missionsOptions}">
							<option value="${y.id}">${y.name}</option>
						</c:forEach--%>
				</select>				
		</dl>
		
		<%--dl>
				<label for="taskId"><span style="color:#039;"><strong>Task :</strong></span></label>
				<select name="taskId"  style="width:350px;">
						<c:forEach var="y" items="${viewAgendaPerEmployee.taskOptions}">
							<option value="${y.id}">${y.name}</option>
						</c:forEach>
				</select>				
		</dl--%>

		 <dl>
				<label for="title"><span style="color:#039;"><strong>Commentaires : </strong></span></label>
				<BR>
				<TEXTAREA NAME="title" style="width:650px;" COLS=5 ROWS=4></TEXTAREA>

		</dl>
    </fieldset>
   
</form>
	

</div>

</div>

<script>


//2-level combo box script- by javascriptkit.com
//Visit JavaScript Kit (http://javascriptkit.com) for script
//Credit must stay intact for use

//STEP 1 of 2: DEFINE the main category links below
//EXTEND array as needed following the laid out structure
//BE sure to preserve the first line, as it's used to display main title

var category=new Array();
category[0]=new Option("SELECT A CATEGORY ", "") ;//THIS LINE RESERVED TO CONTAIN COMBO TITLE
category[1]=new Option("Missions", "chargeable");
category[2]=new Option("Administratives tasks", "nonchargeable");


//STEP 2 of 2: DEFINE the sub category links below
//EXTEND array as needed following the laid out structure
//BE sure to preserve the LAST line, as it's used to display submain title

var chargeable=new Array();
<c:forEach var="y" items="${viewAgendaPerEmployee.missionsOptions}" varStatus="loop">
	chargeable[${loop.index}]=new Option("${y.name}","${y.id}");
</c:forEach>
chargeable[${ fn:length(viewAgendaPerEmployee.missionsOptions) }]=new Option("BACK TO CATEGORIES","");

var nonchargeable=new Array();
<c:forEach var="y" items="${viewAgendaPerEmployee.taskOptions}" varStatus="loop">
	nonchargeable[${loop.index}]=new Option("${y.name}","${y.id}");
</c:forEach>
nonchargeable[${ fn:length(viewAgendaPerEmployee.taskOptions) }]=new Option("BACK TO CATEGORIES","");

var curlevel=1;
var cacheobj=document.addEventForm.mission;

function populate(x){
for (m=cacheobj.options.length-1;m>0;m--)
cacheobj.options[m]=null;
selectedarray=eval(x);
for (i=0;i<selectedarray.length;i++)
cacheobj.options[i]=new Option(selectedarray[i].text,selectedarray[i].value);
cacheobj.options[0].selected=true;

}

function displaysub(){
	
if (curlevel==1){
populate(cacheobj.options[cacheobj.selectedIndex].value);
curlevel=2;
}
else
gothere();
}


function gothere(){
	if (curlevel==2){
		if (cacheobj.selectedIndex==cacheobj.options.length-1){
			curlevel=1;
			populate(category);
		}
	}
}

//SHOW categories by default
populate(category);
</script>


  <script>
YAHOO.namespace("example.container");
function init() {
	// Define various event handlers for Dialog
	var handleSubmit = function() {
		this.submit();		
	};

	var handleDelete = function() {
		this.submit();		
	};

	var handleCancel = function() {
		this.cancel();
	};
	var handleSuccess = function(o) {
		alert("Submission ok");
	};
	var handleFailure = function(o) {
		alert("Submission failed: " + o.status);
	};
	
	// Instantiate the Dialog
	YAHOO.example.container.dialog1 = new YAHOO.widget.Dialog("addDialog",
																{ width : "700px",
																  draggable: true,
																  fixedcenter : true,
																  visible : false,
																  modal: true,
																  constraintoviewport : false,
																  buttons : [ 
																	      		{ text:"Cancel", handler:handleCancel }
															<c:if test='${viewAgendaPerEmployee.modifiable eq false && currentExercise != null && currentExercise.year eq viewAgendaPerEmployee.year}'> 
																				,{ text:"Submit", handler:handleSubmit, isDefault:true }
															</c:if>]
																}
							);
	YAHOO.example.container.dialog1.cfg.queueProperty("postmethod","form");


	// Validate the entries in the form 
	YAHOO.example.container.dialog1.validate = function() {
		 
			  
			  
		var data = this.getData();
		if (data.starttime == "") {
			alert("Please enter a start time for the event");
			return false;
		} 
			
		if (data.endtime == "" ){
			alert("Please enter a end time for the event");
			return false;
		} 

		var nb1=parseInt(data.starttime); // nb vaut alors 182
		var nb2=parseInt(data.endtime); // nb vaut alors 182

		
		if ( nb1 >= nb2) {
			alert("End date must be greater than  Start date...");
			return false;
		} 
		


		

		if ( (data.mission == "SELECT A CATEGORY" ) || (data.mission == "chargeable" ) || (data.mission == "nonchargeable" ) ){
			alert("Please  provide a valid activity for the event.");
			return false;
		} 

		

		return true;
		
	};
	// Wire up the success and failure handlers
	YAHOO.example.container.dialog1.callback = {success: handleSuccess,failure: handleFailure };
	YAHOO.util.Event.addListener("show", "click", YAHOO.example.container.dialog1.show, YAHOO.example.container.dialog1,true);
	YAHOO.example.container.dialog1.render();
}

YAHOO.util.Event.onDOMReady(init);
function showUploadDialog() {
	YAHOO.example.container.dialog1.show();
}

function changeDayOfYearValueForDragAndDropEventForm(){
	 var indice = document.forms["dragAndDropEventForm"].elements["jour"].value;
	  
	    var year = ${viewAgendaPerEmployee.year};
	    if(indice == 1){
	        document.forms["dragAndDropEventForm"].elements["dayOfYear"].value = <%=mondayInt%>;                
	        document.forms["dragAndDropEventForm"].elements["title"].value = '<%=mondayDate%>' +year ;
	    }

	    if(indice == 2){
	        document.forms["dragAndDropEventForm"].elements["dayOfYear"].value = <%=tuesdayInt%>;
	        document.forms["dragAndDropEventForm"].elements["title"].value = '<%=tuesdaydayDate%>' + year ;
	    }

	    if(indice == 3){
	        document.forms["dragAndDropEventForm"].elements["dayOfYear"].value = <%=wednesdayInt%>;
	        document.forms["dragAndDropEventForm"].elements["title"].value =   '<%=wednesdayDate%>' + year ;
	    }

	    if(indice == 4){
	        document.forms["dragAndDropEventForm"].elements["dayOfYear"].value = <%=thursdayInt%>;
	        document.forms["dragAndDropEventForm"].elements["title"].value = '<%=thursdayDate%>' + year  ;
	    }

	    if(indice == 5){
	        document.forms["dragAndDropEventForm"].elements["dayOfYear"].value = <%=fridayInt%>;
	        document.forms["dragAndDropEventForm"].elements["title"].value = '<%=fridayDate%>'  + year;
	    }

	    if(indice == 6){
	        document.forms["dragAndDropEventForm"].elements["dayOfYear"].value = <%=saturdayInt%>;
	        document.forms["dragAndDropEventForm"].elements["title"].value = '<%=saturdayDate%>'  + year;
	    }

	    if(indice == 7){
	        document.forms["dragAndDropEventForm"].elements["dayOfYear"].value = <%=sundayInt%>;
	        document.forms["dragAndDropEventForm"].elements["title"].value =  '<%=sundayDate%>' + year;
	    }
}


function changeDayOfYearValue(){
	
    var indice = document.forms["addEventForm"].elements["jour"].value;
  
    var year = ${viewAgendaPerEmployee.year};
	if(indice == 1){
		document.forms["addEventForm"].elements["dayOfYear"].value = <%=mondayInt%>;				
		document.getElementById('formTitle').innerHTML = '<%=mondayDate%>' +year ;
	}

	if(indice == 2){
		document.forms["addEventForm"].elements["dayOfYear"].value = <%=tuesdayInt%>;
		document.getElementById('formTitle').innerHTML =   '<%=tuesdaydayDate%>' + year ;
	}

	if(indice == 3){
		document.forms["addEventForm"].elements["dayOfYear"].value = <%=wednesdayInt%>;
        document.getElementById('formTitle').innerHTML =   '<%=wednesdayDate%>' + year ;
	}

	if(indice == 4){
		document.forms["addEventForm"].elements["dayOfYear"].value = <%=thursdayInt%>;
        document.getElementById('formTitle').innerHTML =   '<%=thursdayDate%>' + year  ;
	}

	if(indice == 5){
		document.forms["addEventForm"].elements["dayOfYear"].value = <%=fridayInt%>;
        document.getElementById('formTitle').innerHTML =   '<%=fridayDate%>'  + year;
	}

	if(indice == 6){
		document.forms["addEventForm"].elements["dayOfYear"].value = <%=saturdayInt%>;
        document.getElementById('formTitle').innerHTML =   '<%=saturdayDate%>'  + year;
	}

	if(indice == 7){
		document.forms["addEventForm"].elements["dayOfYear"].value = <%=sundayInt%>;
        document.getElementById('formTitle').innerHTML =  '<%=sundayDate%>' + year;
	}


}

function addNewEventFromPlanning(idEntity, description,isMission){

	alert(idEntity);
	alert(isMission);

	//SHOW categories by default
   // populate(category);
  // populate(chargeable);

   //SHOW categories by default

          	if(isMission == true){	
				
          		populate(chargeable);				
          	}   		 		
          	else{
				
				populate(nonchargeable);          		
          	}
			
			curlevel=2;
	//curlevel=1;
/*
	var endhour = parseInt(starthour)+ 4;
	if(endhour >= 52 )endhour = 52;
*/
	
	 document.forms["addEventForm"].elements["jour"].value = 1;
    document.forms["addEventForm"].elements["eventId"].value = "";
    //document.forms["addEventForm"].elements["object"].value = "";
    document.forms["addEventForm"].elements["title"].value = description;
    document.forms["addEventForm"].elements["mission"].value = idEntity;
   // document.forms["addEventForm"].elements["taskId"].value = ""; 
    document.forms["addEventForm"].elements["starttime"].value = 0;
	document.forms["addEventForm"].elements["endtime"].value = 4;


	document.forms["addEventForm"].elements["dayOfYear"].value = <%=mondayInt%>;
	document.getElementById('formTitle').innerHTML = '<%=mondayDate%>' +${viewAgendaPerEmployee.year} ;
	

	/*
	if(indice == 2){
		document.forms["addEventForm"].elements["dayOfYear"].value = <%=tuesdayInt%>;
		document.getElementById('formTitle').innerHTML =   '<%=tuesdaydayDate%>' + year ;
	}

	if(indice == 3){
		document.forms["addEventForm"].elements["dayOfYear"].value = <%=wednesdayInt%>;
        document.getElementById('formTitle').innerHTML =   '<%=wednesdayDate%>' + year ;
	}

	if(indice == 4){
		document.forms["addEventForm"].elements["dayOfYear"].value = <%=thursdayInt%>;
        document.getElementById('formTitle').innerHTML =   '<%=thursdayDate%>' + year  ;
	}

	if(indice == 5){
		document.forms["addEventForm"].elements["dayOfYear"].value = <%=fridayInt%>;
        document.getElementById('formTitle').innerHTML =   '<%=fridayDate%>'  + year;
	}

	if(indice == 6){
		document.forms["addEventForm"].elements["dayOfYear"].value = <%=saturdayInt%>;
        document.getElementById('formTitle').innerHTML =   '<%=saturdayDate%>'  + year;
	}

	if(indice == 7){
		document.forms["addEventForm"].elements["dayOfYear"].value = <%=sundayInt%>;
        document.getElementById('formTitle').innerHTML =  '<%=sundayDate%>' + year;
	}

*/
   
    YAHOO.example.container.dialog1.render();
    YAHOO.example.container.dialog1.show();
}

function addNewEvent(dayOfYear, dateString){

	//SHOW categories by default
    populate(category);
	curlevel=1;
	
    document.forms["addEventForm"].elements["eventId"].value = "";
    //document.forms["addEventForm"].elements["object"].value = "";
    document.forms["addEventForm"].elements["title"].value = "";
    document.forms["addEventForm"].elements["mission"].value = "";
   //document.forms["addEventForm"].elements["taskId"].value = "";    
    document.forms["addEventForm"].elements["starttime"].value = "";
	document.forms["addEventForm"].elements["endtime"].value = "";
    document.forms["addEventForm"].elements["dayOfYear"].value = dayOfYear;
	//document.forms["addEventForm"].elements["date"].value = dateString;
	

    document.getElementById('formTitle').innerHTML =   dateString ;
    YAHOO.example.container.dialog1.render();
    YAHOO.example.container.dialog1.show();
}

function addEventFromCell(indice, year,starthour){

	
	//SHOW categories by default
    populate(category);
	curlevel=1;

	var endhour = parseInt(starthour)+ 4;
	if(endhour >= 52 )endhour = 52;

	
	 document.forms["addEventForm"].elements["jour"].value = indice;
    document.forms["addEventForm"].elements["eventId"].value = "";
    //document.forms["addEventForm"].elements["object"].value = "";
    document.forms["addEventForm"].elements["title"].value = "";
    document.forms["addEventForm"].elements["mission"].value = "";
   // document.forms["addEventForm"].elements["taskId"].value = ""; 
    document.forms["addEventForm"].elements["starttime"].value = starthour;
	document.forms["addEventForm"].elements["endtime"].value = endhour;

	if(indice == 1){
		document.forms["addEventForm"].elements["dayOfYear"].value = <%=mondayInt%>;
		document.getElementById('formTitle').innerHTML = '<%=mondayDate%>' +year ;
	}

	if(indice == 2){
		document.forms["addEventForm"].elements["dayOfYear"].value = <%=tuesdayInt%>;
		document.getElementById('formTitle').innerHTML =   '<%=tuesdaydayDate%>' + year ;
	}

	if(indice == 3){
		document.forms["addEventForm"].elements["dayOfYear"].value = <%=wednesdayInt%>;
        document.getElementById('formTitle').innerHTML =   '<%=wednesdayDate%>' + year ;
	}

	if(indice == 4){
		document.forms["addEventForm"].elements["dayOfYear"].value = <%=thursdayInt%>;
        document.getElementById('formTitle').innerHTML =   '<%=thursdayDate%>' + year  ;
	}

	if(indice == 5){
		document.forms["addEventForm"].elements["dayOfYear"].value = <%=fridayInt%>;
        document.getElementById('formTitle').innerHTML =   '<%=fridayDate%>'  + year;
	}

	if(indice == 6){
		document.forms["addEventForm"].elements["dayOfYear"].value = <%=saturdayInt%>;
        document.getElementById('formTitle').innerHTML =   '<%=saturdayDate%>'  + year;
	}

	if(indice == 7){
		document.forms["addEventForm"].elements["dayOfYear"].value = <%=sundayInt%>;
        document.getElementById('formTitle').innerHTML =  '<%=sundayDate%>' + year;
	}


   
    YAHOO.example.container.dialog1.render();
    YAHOO.example.container.dialog1.show();
}

var handleSuccess = function(o){
    var form = document.forms["addEventForm"];
    if(o.responseText !== undefined){
		
        // Use the JSON Utility to parse the data returned from the server   
         try {   
            var object = YAHOO.lang.JSON.parse(o.responseText);

			

          	//SHOW categories by default

          	if(object.taskId == null){	
				
          		populate(chargeable);				
          	}   		 		
          	else{
				
				populate(nonchargeable);          		
          	}
			
			curlevel=2;


			
			form.elements["jour"].value = object.indice;
            form.elements["eventId"].value = object.eventId;
			
            //form.elements["object"].value = object.object;
            form.elements["title"].value = object.title;
			 
            form.elements["mission"].value = object.mission;

            //form.elements["taskId"].value = object.taskId; 
			 
			form.elements["starttime"].value = object.starttime;
			 
			form.elements["endtime"].value = object.endtime;
			
			
			form.elements["dayOfYear"].value = object.dayOfYear;

			//document.getElementById('formTitle').innerHTML =   dateString ;
		
			//form.elements["date"].value = object.date;
			
            
            //no password as it is only sent from client to browser.
            document.getElementById('formTitle').innerHTML = 'Update event \'' + object.type + '\'';
         }
         catch (x) {   
             alert("JSON Parse failed in parsing intervention!");
             return;
         } 
		
        YAHOO.example.container.dialog1.render();
        YAHOO.example.container.dialog1.show();		
    }
};

var handleFailure = function(o){
    if(o.responseText !== undefined){
		/*
        div.innerHTML = "<li>Transaction id: " + o.tId + "<\/li>";
        div.innerHTML += "<li>HTTP status: " + o.status + "<\/li>";
        div.innerHTML += "<li>Status code message: " + o.statusText + "<\/li>";
		*/
		alert("Failed to connect to serveur...");
    }
};

var callback =
{
  success:handleSuccess,
  failure:handleFailure
};

var sEditEventUrl = "${ctx}/editEventPage.htm";

function editEvent(eventId){
    var postData = "eventId="+eventId;
    var request = YAHOO.util.Connect.asyncRequest('POST', sEditEventUrl, callback, postData);
}

function deleteEvent(id,userId,year,week)
  {
    if (confirm("Do you really want to delete Event with id "+id))
    {
      window.location="${ctx}/deleteEventPage.htm?eventId="+id+"&year="+year+"&userId="+userId+"&week="+week;
    }
  }

</script>