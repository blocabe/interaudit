<jsp:directive.page contentType="text/html;charset=UTF-8" />
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="t"      uri="/tags/tooltips-tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/interaudit" prefix="interaudit" %>
<%@ page import="java.util.*,java.text.*,java.io.PrintWriter,com.interaudit.service.view.WeeklyTimeSheetView" %>

<%
request.setAttribute("ctx", request.getContextPath()); 
%>

<script type="text/javascript" src="${ctx}/script/jquery-1.6.1.js"></script>
<link href="${ctx}/css/messageHandler.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/script/messageHandler.js"></script>



	<link rel="stylesheet" type="text/css" href="${ctx}/script/build/fonts/fonts-min.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/script/build/autocomplete/assets/skins/sam/autocomplete.css" />
<script type="text/javascript" src="${ctx}/script/build/yahoo-dom-event/yahoo-dom-event.js"></script>
<script type="text/javascript" src="${ctx}/script/build/connection/connection.js"></script>
<script type="text/javascript" src="${ctx}/script/build/animation/animation.js"></script>
<script type="text/javascript" src="${ctx}/script/build/datasource/datasource.js"></script>
<script type="text/javascript" src="${ctx}/script/build/autocomplete/autocomplete.js"></script>

<script type="text/javascript">
  
$(document).ready(function()  {
  <c:if test='${currentExercise.status eq "CLOSED"}'> 	
          showMessage("Aucun budget actif défini... Cette fonctionnalité n'est pas utilisable..","error");                    
    
  </c:if>                 
});  
</script>

<style type="text/css">
#myAutoComplete {
    width:30em; /* set width here or else widget will expand to fit its container */
    padding-bottom:2em;
	margin-left:50px;
	margin-top:20px;
	margin-bottom:20px;
}

#myAutoComplete2 {
    width:40em; /* set width here or else widget will expand to fit its container */
    padding-bottom:2em;
	margin-left:50px;
	margin-top:20px;
	margin-bottom:20px;
}
</style>


	
<!--link rel="stylesheet" type="text/css" media="all" href="css/niceforms-default.css" /-->
	<style>
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

		#example {height:40em;}
		label { display:block;float:left;width:45%;clear:left; }
		.clear { clear:both; }
		#resp { margin:10px;padding:5px;border:1px solid #ccc;background:#fff;}
		#resp li { font-family:monospace }
		
		
		 .normal { background-color: white }
		 .highlight { background-color: orange }
		
		}
		
		
		.nav_bottom{
			
		}
		
		.nav_alphabet{
			line-height: 25px;
			padding-bottom: 5px;
		}
		
		.galphabet_left,.galphabet_right{ 
			padding-bottom: 2px; 
			padding-top: 2px;
			_padding-top: 0px; 
			_height: 20px;	
			_padding-bottom: 0px;
		}
		
		.galphabet_center{ 
			display: inline; 
			margin-left : px;
			margin-right : 4px;
			padding-left: 1px;
			padding-right: 1px;
			padding-bottom: 2px;
			padding-top: 1px;
			background-repeat: repeat-x; 
			background-position: left 0px; 
			line-height: 23px;
			_padding-bottom: 0px;
			_padding-top: 3px;
			_height: 10px;	
			_line-height: normal;
		}
		
		
		
		.nav_alphabet a{
			font-weight: bold;
			text-decoration: none;
		}
</style>

<style type="text/css">
#navbar {position: relative; right: 0px; width: 101;
  padding: 2px 0 2px 32px; white-space: nowrap;
  
 }
#navbar b {display: none;}
#navbar a {text-decoration: none; color: #000;
  border-bottom: 1px solid gray;
  padding: 0 1em 0px 0;}
  
  
  


div.titlebar{
  background-color: white;
  color:white;
  height: 16px;
}
</style>









<%
request.setAttribute("ctx", request.getContextPath()); 
%>



    
			
			

			<!-- START ADDED PART -->   

<style>
#one-column-emphasis
{
	font-family: "Lucida Sans Unicode", "Lucida Grande", Sans-Serif;
	font-size: 12px;
	
	
	text-align: left;
	border-collapse: collapse;
}
#one-column-emphasis th
{
	font-size: 14px;
	font-weight: normal;
	padding: 12px 15px;
	color: #039;
}
#one-column-emphasis td
{
	padding: 10px 15px;
	color: #669;
	border-top: 1px solid #e8edff;
}
.oce-first
{
	background: #d0dafd;
	border-right: 10px solid transparent;
	border-left: 10px solid transparent;
}
#one-column-emphasis tr:hover td
{
	color: #339;
	background: #eff2ff;
}

#newspaper-a
{
	font-family: "Lucida Sans Unicode", "Lucida Grande", Sans-Serif;
	font-size: 12px;
	margin-top: 45px;	
	text-align: left;
	border-collapse: collapse;
	border: 1px solid #69c;
}
#newspaper-a th
{
	/*padding: 12px 17px 12px 17px;*/
	font-weight: normal;
	font-size: 14px;
	color: #039;
	border-bottom: 1px dashed #69c;
}
#newspaper-a td
{
	/*padding: 7px 15px 7px 15px;*/
	color: #669;
}
#newspaper-a tbody tr:hover td
{
	color: #339;
	background: #d0dafd;
}


#newspaper-b
{
	font-family: "Lucida Sans Unicode", "Lucida Grande", Sans-Serif;
	font-size: 12px;
	margin: 45px;
	width: 480px;
	text-align: left;
	border-collapse: collapse;
	border: 1px solid #69c;
}
#newspaper-b th
{
	padding: 15px 10px 10px 10px;
	font-weight: normal;
	font-size: 14px;
	color: #039;
}
#newspaper-b tbody
{
	background: #e8edff;
}
#newspaper-b td
{
	padding: 10px;
	color: #669;
	border-top: 1px dashed #fff;
}
#newspaper-b tbody tr:hover td
{
	color: #339;
	background: #d0dafd;
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
	font-size: 10px;
	font-weight: normal;
	padding: 12px 15px;
	border-top: 1px solid #0066aa;
	border-right: 1px solid #0066aa;
	border-bottom: 1px solid #0066aa;
	border-left: 1px solid #0066aa;
	color: #039;
	background: #eff2ff;
}
#ver-zebra td
{
	padding: 4px 7px;
	border-right: 1px solid #0066aa;
	border-left: 2px solid #0066aa;
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





</style>

<%
 						SimpleDateFormat dateFormat = new SimpleDateFormat("EEE dd", new Locale("fr","FR"));
						WeeklyTimeSheetView timesheetView = (WeeklyTimeSheetView)request.getAttribute("viewTimesheetPerEmployee");
					 	String year = timesheetView.getYear();
					 	String week = timesheetView.getWeek();
					 	
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

						month++;
						
					 %>

		






<div style="border-bottom: 0px solid gray; background-color: white;">
&nbsp;
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


<div  style="border: 1px solid  #0066aa; text-align:center; padding-top:1pt; font: bold 11px Verdana, sans-serif; background-color: rgb(248, 246, 233);">
			<form style="margin:2pt" name="searchActivityReportForm"  action="${ctx}/showTimesheetRegisterPage.htm" method="post" >			
				<span style="font:10px Verdana, sans-serif;margin-right:2pt;"><strong>Exercise</strong> : </span>
				<select style="font:10px Verdana, sans-serif;margin-right:10pt;" name="year" onchange="document.searchActivityReportForm.submit();">
				    <c:forEach var="y" items="${viewTimesheetPerEmployee.yearOptions}">
			             		<option value="${y.id}"<c:if test='${viewTimesheetPerEmployee.year==y.id}'> selected</c:if>>${y.name}</option>
			        </c:forEach>
				</select> 
			
				<span style="font:10px Verdana, sans-serif;margin-right:2pt;"><strong>Week</strong> : </span>
				<c:choose>
									    		<c:when test='${viewTimesheetPerEmployee.timesheet.status eq "DRAFT"}'>																								
												 <c:set var="backgroundStyle" value="background-color:#FF6262;font:10px Verdana, sans-serif;margin-right:10pt;"/>
									      		</c:when>
												<c:when test='${viewTimesheetPerEmployee.timesheet.status eq "SUBMITTED"}'>																								
												 <c:set var="backgroundStyle" value="background-color:#FFA042;font:10px Verdana, sans-serif;margin-right:10pt;"/>
									      		</c:when>
									      		<c:when test='${viewTimesheetPerEmployee.timesheet.status eq "VALIDATED"}'>												 
												  <c:set var="backgroundStyle" value="background-color:#55FF55;font:10px Verdana, sans-serif;margin-right:10pt;"/>
									      		</c:when>
									     	</c:choose>
				<img style="margin-top:10pt;" src="images/resultset_previous.png" border="0" />
				<select style="${backgroundStyle}" name="week" onchange="document.searchActivityReportForm.submit();">
					
					<c:forEach var="y" items="${viewTimesheetPerEmployee.timesheetOptions}">
											<c:choose>
									    		<c:when test='${y.status eq "DRAFT"}'>												
												 <c:set var="backgroundStyle" value="background-color:#FF6262;"/>
									      		</c:when>
												<c:when test='${y.status eq "SUBMITTED"}'>												
												 <c:set var="backgroundStyle" value="background-color:#FFA042;"/>
									      		</c:when>
									      		<c:when test='${y.status eq "VALIDATED"}'>
												 <c:set var="backgroundStyle" value="background-color:#55FF55;"/>
									      		</c:when>
									     	</c:choose>
			             		<option style="${backgroundStyle}" value="${y.weekNumber}"<c:if test='${viewTimesheetPerEmployee.week==y.weekNumber}'> selected</c:if>>
								
								
											
											<span>${y.weekNumber} - 
											
											<c:choose>
									    		<c:when test='${y.status eq "DRAFT"}'>																																				 
													<c:choose>
														 <c:when test='${y.lastRejectedDate != null}'>												 
														   REJECTED 
														</c:when>
														<c:otherwise>
															${y.status}
														</c:otherwise>
													</c:choose>
									      		</c:when>
												<c:when test='${y.status eq "SUBMITTED"}'>																								
												 
												 <c:choose>
														 <c:when test='${y.lastRejectedDate != null}'>												 
														  RE-SUBMITTED
														</c:when>
														<c:otherwise>
															${y.status}
														</c:otherwise>
													</c:choose>
									      		</c:when>
									      		<c:when test='${y.status eq "VALIDATED"}'>												 
												  ${y.status}
									      		</c:when>
									     	</c:choose>


											</span>
											
								
								</option>
			        </c:forEach>
				</select>
				<img src="images/resultset_next.png" border="0" />

				<span class="galphabet_center">du : <span style ="color:red;"><%=mondayDate%></span> au <span style ="color:red;"><%=fridayDate%></span></span>
				
				<interaudit:accessRightSet right="VALIDATE_TIMESHEET">
				<span style="font:10px Verdana, sans-serif;margin-right:2pt;"><strong>Employee</strong> : </span>
				<select style="font:10px Verdana, sans-serif;margin-right:10pt;" name="employeeId" onchange="document.searchActivityReportForm.submit();">
						<c:forEach var="y" items="${viewTimesheetPerEmployee.employeeOptions}">
			             		<option value="${y.id}"<c:if test='${viewTimesheetPerEmployee.employee.id==y.id}'> selected</c:if>>${y.name}</option>
			        	</c:forEach> 
				</select>
				</interaudit:accessRightSet>
		
				&nbsp;&nbsp;&nbsp;&nbsp;
				<%--input  style="font:12px Verdana, sans-serif;margin-right:10pt;width:80px;" type="submit" class="button120" value="Search"/--%>			
			</form>
				
</div>


<div style="border-bottom: 0px solid gray; background-color: white;">
&nbsp;
</div>

<div style="border-bottom: 1px solid gray; background-color: white; ">
			    
			        <table id="ver-zebra" width="100%" cellspacing="0" >
			        	<caption>
						<span style="color:orange;">Activity Report Overview</span>						
						<span  class="galphabet_center" >Employee :  <span style ="color:blue;">${viewTimesheetPerEmployee.employee.firstName} &nbsp;&nbsp;${viewTimesheetPerEmployee.employee.lastName}&nbsp;&nbsp;(${viewTimesheetPerEmployee.employee.code})</span></span>	
						
											<c:choose>
									    		<c:when test='${viewTimesheetPerEmployee.timesheet.status eq "DRAFT"}'>																																				 
													<c:choose>
														 <c:when test='${viewTimesheetPerEmployee.timesheet.lastRejectedDate != null}'>												 
														  <span class="galphabet_center" style ="background-color:#FF6262;"> REJECTED </span>
														</c:when>
														<c:otherwise>
															<span class="galphabet_center" style ="background-color:#FF6262;"> ${viewTimesheetPerEmployee.timesheet.status} </span>
														</c:otherwise>
													</c:choose>
									      		</c:when>
												<c:when test='${viewTimesheetPerEmployee.timesheet.status eq "SUBMITTED"}'>																								
												 
												 <c:choose>
														 <c:when test='${viewTimesheetPerEmployee.timesheet.lastRejectedDate != null}'>												 
														  <span class="galphabet_center" style ="background-color:#FF6262;"> RE-SUBMITTED </span>
														</c:when>
														<c:otherwise>
															<span class="galphabet_center" style ="background-color:#FFA042;"> ${viewTimesheetPerEmployee.timesheet.status} </span>
														</c:otherwise>
													</c:choose>
									      		</c:when>
									      		<c:when test='${viewTimesheetPerEmployee.timesheet.status eq "VALIDATED"}'>												 
												  <span class="galphabet_center" style ="background-color:#55FF55;"> ${viewTimesheetPerEmployee.timesheet.status} </span>
									      		</c:when>
									     	</c:choose>
								
						<span class="galphabet_center">SEMAINE du : <span style ="color:blue;">${viewTimesheetPerEmployee.timesheet.startDateOfWeek}</span> AU <span style ="color:blue;">${viewTimesheetPerEmployee.timesheet.endDateOfWeek}</span></span>
						</caption>
						<thead>
							<tr>
								<th id="1" style="width : 40;" scope="col"><span style=" color: #68a1e5;"><%=mondayDate%></span></th>							
								<th id="2" style="width : 40;" scope="col"><span style=" color: #68a1e5;"><%=tuesdaydayDate%></span></th>							
								<th id="3" style="width : 40;" scope="col"><span style=" color: #68a1e5;"><%=wednesdayDate%></span></th>
								<th id="4" style="width : 40;" scope="col"><span style=" color: #68a1e5;"><%=thursdayDate%></span></th>							
								<th id="5" style="width : 40;" scope="col"><span style=" color: #68a1e5;"><%=fridayDate%></span></th>
															
								<th scope="col">Total</th>
								<th style="width : 20;" scope="col">Hrs supp</th>
								<th style="width : 20;" scope="col">Prestation</th>
								<th scope="col">Année</th>
								<th scope="col">Mois</th>
								<th style="width : 20;" scope="col">N°client</th>
								<th scope="col">Libelle client   </th>
								<th style="width : 10;" scope="col">As.</th>
								<th style="width : 10;" scope="col">Ma.</th>
								<th scope="col">Del</th>
							</tr>
						</thead>
						<tbody>
						
	
									<c:set var="totalHours" value="0"/>

									<c:set var="totalMondayHours" value="0"/>
									<c:set var="totalTuesdayHours" value="0"/>
									<c:set var="totalWednesdayHours" value="0"/>
									<c:set var="totalThursdayHours" value="0"/>
									<c:set var="totalFridayHours" value="0"/>
									

									<c:forEach var="timesheetRow" items="${viewTimesheetPerEmployee.timesheet.sortedRows}" varStatus="loop">
										<c:if test="${timesheetRow.activity != null}">
											<tr onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
											
											
											<td align="center" style="cursor:hand;" >
											<span id="1.${timesheetRow.custNumber}" style=" color: black;">${timesheetRow.cellsMap["1"].value}&nbsp;</span>
												<c:set var="totalMondayHours" value="${totalMondayHours + timesheetRow.cellsMap['1'].value}"/>		
											</td>
											

											<td style="cursor:hand;" >
											<span id="2.${timesheetRow.custNumber}" style=" color: black;">${timesheetRow.cellsMap["2"].value}&nbsp;</span>
												<c:set var="totalTuesdayHours" value="${totalTuesdayHours + timesheetRow.cellsMap['2'].value}"/>			
											</td>

											
											<td style="cursor:hand;" >
											<span id="3.${timesheetRow.custNumber}" style=" color: black;">${timesheetRow.cellsMap["3"].value}&nbsp;</span>
												<c:set var="totalWednesdayHours" value="${totalWednesdayHours + timesheetRow.cellsMap['3'].value}"/>			
											</td>

											<td style="cursor:hand;" >
											<span id="4.${timesheetRow.custNumber}" style=" color: black;">${timesheetRow.cellsMap["4"].value}&nbsp;</span>
												<c:set var="totalThursdayHours" value="${totalThursdayHours + timesheetRow.cellsMap['4'].value}"/>		
											</td>

											<td style="cursor:hand;" >
											<span id="5.${timesheetRow.custNumber}" style=" color: black;">${timesheetRow.cellsMap["5"].value}&nbsp;</span>
												<c:set var="totalFridayHours" value="${totalFridayHours + timesheetRow.cellsMap['5'].value}"/>		
											</td>

											<%--td style="width : 10; cursor:hand;" >
											<span id="6.${timesheetRow.custNumber}" style=" color: black;">${timesheetRow.cellsMap["6"].value}&nbsp;</span>
														
											</td>

											<td style="width : 10; cursor:hand;" >
											<span id="7.${timesheetRow.custNumber}" style=" color: black;">${timesheetRow.cellsMap["7"].value}&nbsp;</span>
														
											</td--%>

																			
											
											<td align="center">${timesheetRow.totalOfHours}</td>
											<td>${timesheetRow.totalOfExtraHours}</td>
											<td>${timesheetRow.codePrestation}</td>
											<td>${timesheetRow.year}</td>
											<td><%=month%></td>
											<td>${timesheetRow.custNumber}</td>
											<td>${timesheetRow.label}</td>
											<td>${timesheetRow.assCode}</td>
											<td>${timesheetRow.manCode}</td>
											<td style="width : 5;">
													<c:if test='${viewTimesheetPerEmployee.timesheet.status eq "DRAFT"}'> 
														<a href="#">
														<img src="images/Table-Delete.png" border="0" alt="Delete row" onclick="removeRowFromTimeSheet(${timesheetRow.id})"/>
														</a>
														<a href="#">
														<img src="images/Table-Borrow Structure.png" border="0" alt="Copy row" onclick="copyRowFromTimeSheet(${timesheetRow.id})"/>
														</a>
														<a href="#">
														<img src="images/Table-Edit.png" border="0" alt="Edit row" onclick="editRowFromTimeSheet(${timesheetRow.id})"/>
														</a>
													</c:if>
											</td>
											</tr>
											<c:set var="totalHours" value="${totalHours + timesheetRow.totalOfHours}"/>
										</c:if>
										
									</c:forEach>
								
								
									<tr>
									<td colspan="5" style="border: 2px solid #0066aa;">&nbsp;</td>
									<td align="center" style="border: 2px solid #0066aa;"><b><span style="color: red;">${totalHours}</span></b></td>
									<td colspan="9" style="border: 2px solid #0066aa;">&nbsp;</td>
									</tr>
									
									
									<c:forEach var="timesheetRow" items="${viewTimesheetPerEmployee.timesheet.sortedRows}" varStatus="loop">
										<c:if test="${timesheetRow.activity == null}">
											<tr onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
											
											<td align="center" style="cursor:hand;" >
											<span id="1.${timesheetRow.label}" style=" color: black;">${timesheetRow.cellsMap["1"].value}&nbsp;</span>
												<c:set var="totalMondayHours" value="${totalMondayHours + timesheetRow.cellsMap['1'].value}"/>			
											</td>
											

											<td style="cursor:hand;" >
											<span id="2.${timesheetRow.label}" style=" color: black;">${timesheetRow.cellsMap["2"].value}&nbsp;</span>
												<c:set var="totalTuesdayHours" value="${totalTuesdayHours + timesheetRow.cellsMap['2'].value}"/>			
											</td>

											
											<td style="cursor:hand;" >
											<span id="3.${timesheetRow.label}" style=" color: black;">${timesheetRow.cellsMap["3"].value}&nbsp;</span>
												<c:set var="totalWednesdayHours" value="${totalWednesdayHours + timesheetRow.cellsMap['3'].value}"/>		
											</td>

											<td style="cursor:hand;" >
											<span id="4.${timesheetRow.label}" style=" color: black;">${timesheetRow.cellsMap["4"].value}&nbsp;</span>
												<c:set var="totalThursdayHours" value="${totalThursdayHours + timesheetRow.cellsMap['4'].value}"/>		
											</td>

											<td style="cursor:hand;" >
											<span id="5.${timesheetRow.label}" style=" color: black;">${timesheetRow.cellsMap["5"].value}&nbsp;</span>
												<c:set var="totalFridayHours" value="${totalFridayHours + timesheetRow.cellsMap['5'].value}"/>				
											</td>

											



											<td align="center" >${timesheetRow.totalOfHours}</td>
											<td>${timesheetRow.totalOfExtraHours}</td>
											<td>${timesheetRow.codePrestation}</td>
											<td>${timesheetRow.year}</td>
											<td><%=month%></td>
											<td>${timesheetRow.custNumber}</td>
											<td  colspan="3">${timesheetRow.label}</td>	
											<td style="width : 5;">
												<c:if test='${viewTimesheetPerEmployee.timesheet.status eq "DRAFT"}'> 
													<a href="#">
													<img src="images/Table-Delete.png" border="0" alt="Delete row" onclick="removeRowFromTimeSheet(${timesheetRow.id})"/>
													</a>
													<a href="#">
														<img src="images/Table-Borrow Structure.png" border="0" alt="Copy row" onclick="copyRowFromTimeSheet(${timesheetRow.id})"/>
														</a>
														<a href="#">
														<img src="images/Table-Edit.png" border="0" alt="Edit row" onclick="editRowFromTimeSheet(${timesheetRow.id})"/>
														</a>
												</c:if>
											</td>
											</tr>
											<c:set var="totalHours" value="${totalHours + timesheetRow.totalOfHours}"/>
										</c:if>
										
									</c:forEach>
									

												<c:choose>
													<c:when test='${totalMondayHours > 8 }'>												
													 <c:set var="backgroundMondayStyle" value="border: 2px solid #0066aa;background-color:white;"/>
													</c:when>
													<c:otherwise>
													 <c:set var="backgroundMondayStyle" value="border: 2px solid #0066aa;background-color:white;"/>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test='${totalTuesdayHours > 8 }'>												
													 <c:set var="backgroundTuesdayStyle" value="border: 2px solid #0066aa;background-color:white;"/>
													</c:when>
													<c:otherwise>
													 <c:set var="backgroundTuesdayStyle" value="border: 2px solid #0066aa;background-color:white;"/>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test='${totalWednesdayHours > 8 }'>												
													 <c:set var="backgroundWednesdayStyle" value="border: 2px solid #0066aa;background-color:white;"/>
													</c:when>
													<c:otherwise>
													 <c:set var="backgroundWednesdayStyle" value="border: 2px solid #0066aa;background-color:white;"/>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test='${totalThursdayHours > 8 }'>												
													 <c:set var="backgroundThursdayStyle" value="border: 2px solid #0066aa;background-color:white;"/>
													</c:when>
													<c:otherwise>
													 <c:set var="backgroundThursdayStyle" value="border: 2px solid #0066aa;background-color:white;"/>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test='${totalFridayHours > 8 }'>												
													 <c:set var="backgroundFridayStyle" value="border: 2px solid #0066aa;background-color:white;"/>
													</c:when>
													<c:otherwise>
													 <c:set var="backgroundFridayStyle" value="border: 2px solid #0066aa;background-color:white;"/>
													</c:otherwise>
												</c:choose>


									<tr>
									<td style="${backgroundMondayStyle}">${totalMondayHours}</td>
									<td style="${backgroundTuesdayStyle}">${totalTuesdayHours}</td>
									<td style="${backgroundWednesdayStyle}">${totalWednesdayHours}</td>
									<td style="${backgroundThursdayStyle}">${totalThursdayHours}</td>
									<td style="${backgroundFridayStyle}">${totalFridayHours}</td>

									<td align="center" style="border: 2px solid #0066aa;"><b><span style="color: red;">${totalHours}</span></b></td>
									<td colspan="9" style="border: 2px solid #0066aa;">&nbsp;</td>
									</tr>

									</tbody>
								</table>
						</div>
			<div style="border-bottom: 0px solid gray; background-color: white;">
				&nbsp;
			</div>
			<div class="nav_alphabet" style="border: 1px solid  #0066aa; text-align:center;  font: bold 11px Verdana, sans-serif;background-color: rgb(248, 246, 233);">
			<form style="margin:2pt" name="activityReportForm3"  method="post" >

					<c:if test='${viewTimesheetPerEmployee.employee.id == context.currentUser.id}'> 
					<c:if test='${viewTimesheetPerEmployee.timesheet.status eq "DRAFT"}'>
						<span style="font:12px Verdana, sans-serif;margin-right:10pt;"><input style="font:12px Verdana, sans-serif;margin-right:10pt;" type="button" class="button120" value="Synch Agenda" onclick="Javascript:synchronizeWithAgenda();"/></span>	
						<span style="font:12px Verdana, sans-serif;margin-right:10pt;"><input style="font:12px Verdana, sans-serif;margin-right:10pt;" type="button" class="button120" value="Add Comment" id="show" onclick="addComment()" /></span>	
					</c:if>
					</c:if>
					<%--
					<interaudit:atLeastOneAccessRightSet rights="VALIDATE_TIMESHEET,VALIDATE_MY_TS">
					<span style="font:12px Verdana, sans-serif;margin-right:10pt;"><input style="font:12px Verdana, sans-serif;margin-right:10pt;" type="button" class="button120" value="To validate" onclick="Javascript:timesheetsToValidate();"/></span>	
					</interaudit:atLeastOneAccessRightSet>

					<span style="font:12px Verdana, sans-serif;margin-right:10pt;"><input style="font:12px Verdana, sans-serif;margin-right:10pt;" type="button" class="button120" value="my Timesheets" onclick="Javascript:myTimesheets();"/></span>	

					--%>
					<c:if test='${viewTimesheetPerEmployee.employee.id == context.currentUser.id}'> 
					<c:if test='${viewTimesheetPerEmployee.timesheet.status eq "DRAFT"}'> 

					<span  style="margin-left:80px;" style="font:12px Verdana, sans-serif;margin-right:10pt;"><input style="font:12px Verdana, sans-serif;margin-right:10pt;" type="button" class="button120" value="Submit" onclick="Javascript:submitTimesheet();"/></span>

					</c:if>
					</c:if>
					

					<c:if test='${viewTimesheetPerEmployee.timesheet.status eq "SUBMITTED"}'> 
						<interaudit:atLeastOneAccessRightSet rights="VALIDATE_TIMESHEET,VALIDATE_MY_TS">
							<c:if test='${"MB" == context.currentUser.code}'> 
							<span style="font:12px Verdana, sans-serif;margin-right:10pt;"><input style="font:12px Verdana, sans-serif;margin-right:10pt;width:140px;" type="button" class="button120" value="Validate & Update" onclick="Javascript:validateTimesheetAndUpdate();"/></span>	
							</c:if>
							<span style="font:12px Verdana, sans-serif;margin-right:10pt;"><input style="font:12px Verdana, sans-serif;margin-right:10pt;" type="button" class="button120" value="Validate Only" onclick="Javascript:validateTimesheet();"/></span>	
						</interaudit:atLeastOneAccessRightSet>
					</c:if>
					&nbsp;
					<c:if test='${viewTimesheetPerEmployee.timesheet.status eq "SUBMITTED"||viewTimesheetPerEmployee.timesheet.status eq "VALIDATED"}'>
						
						<interaudit:atLeastOneAccessRightSet rights="VALIDATE_TIMESHEET,VALIDATE_MY_TS">
						<span style="font:12px Verdana, sans-serif;margin-right:10pt;"><input style="font:12px Verdana, sans-serif;margin-right:10pt;" type="button" class="button120" value="Reject" id="show" onclick="resetForm()" /></span>	
						</interaudit:atLeastOneAccessRightSet>
					</c:if>
					&nbsp;

					<c:if test='${viewTimesheetPerEmployee.employee.id == context.currentUser.id}'> 
					<c:if test='${viewTimesheetPerEmployee.timesheet.status eq "DRAFT"}'>
					<span style="font:12px Verdana, sans-serif;margin-right:10pt;"><input style="font:12px Verdana, sans-serif;margin-right:10pt;" type="button" class="button120" value="Add" id="show2" onclick="resetForm2()" /></span>
					</c:if>
					</c:if>
					<%--input class="button120"  style="width:120px; font:10px Verdana, sans-serif;" type="button" value="Export to Excel"  onclick="exportExcel()"/--%>
			
			</form>
			</div>



			</div>
			
			</div>
			<div>
			<br/>

			<h4>Commentaires</h4>
			
			<c:set var="row" value="0"/>
			<c:forEach var="item" items="${viewTimesheetPerEmployee.timesheet.sortedComments}" >	

			<c:choose>
					<c:when test='${row % 2 eq 0 }'>
					 <div  style="color: blue;margin-left:10px;background-color:#CCDFE2;width: 98%;border:1px solid #333B66;">							      			
					</c:when>
					<c:otherwise>					 
					 <div style="color: blue;margin-left:10px;background-color:#EEEEDD;width: 98%;border:1px solid #333B66;">
					</c:otherwise>
				</c:choose>
			<div style="text-decoration: underline;color: black;">
				  <fmt:formatDate value="${item.created}" type="both" dateStyle="short"/>
				  &nbsp;|&nbsp; added by : &nbsp;
				  	${item.validator.lastName},&nbsp;${item.validator.firstName}
			 </div>

			 
			<div>	
				
				${item.textHtmlFormatted}</div>
			<c:set var="row" value="${row + 1}"/>
			
			</div>
			</div>
			<br/>
			</c:forEach>
			</div>

		 

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
		</div>

<script type="text/javascript">

function exportExcel(){
	var url ="${ctx}/showTimeSheetExcelView.htm?id=${viewTimesheetPerEmployee.timesheet.id}";
	window.location = url;
}

function myTimesheets(){
		var url ="${ctx}/showTimesheetsPage.htm?type=personal";
		window.location = url;
}

function timesheetsToValidate(){
		var url ="${ctx}/showTimesheetsPage.htm";
		window.location = url;
}

function saveTimesheet() {	
		var url ="${ctx}/saveTimesheet.htm";
		window.location = url;	
}

function submitTimesheet() {	
	var answer = confirm("Do you really want to submit your timesheet now?")
	if (answer){
		var url ="${ctx}/submitTimesheet.htm";
		window.location = url;	
	}
}

function rejectTimesheet() {	
		var url ="${ctx}/rejectTimesheet.htm";
		window.location = url;	
}

function validateTimesheet() {	
		var url ="${ctx}/validateTimesheet.htm";
		window.location = url;	
}

function validateTimesheetAndUpdate() {	
		var url ="${ctx}/validateTimesheet.htm?update=true";
		window.location = url;	
}

function synchronizeWithAgenda(){
	var answer = confirm("All the rows will be removed first...Do you want to proceed further?")
	if (answer){
	var url ="${ctx}/synchronizeTimesheetWithAgenda.htm";
	window.location = url;
	}
}

function removeRowFromTimeSheet(idRow){
	var answer = confirm("Do you really want to clean the selected items?")
	if (answer){
		var url ="${ctx}/removeRowToTimesheet.htm?id="+idRow;
		window.location = url;	
	}
	
}


function copyRowFromTimeSheet(idRow){
	
		var url ="${ctx}/copyRowFromTimeSheet.htm?id="+idRow;
		window.location = url;	
	
	
}




								




</script>
	

	<div id="addDialog">
	<div class="hd">
        <span id="formTitle" style="color:#039;">Add comment</span>
    </div>
	<div class="bd">
	<form   name="addMessageForm" method="post">
	
	<fieldset>
		<legend>
						<span style="font:10px Verdana, sans-serif;"><strong>Commentaire</strong> : </span>
					</legend>
		<dl>
			
            <dd><textarea style="width:450px;" name="description" id="description" rows="16" cols="120"></textarea></dd>
		</dl>
    </fieldset>
	</form>
	</div>	
	</div>


	<div id="addDialog2">
	<div class="hd">
        <span id="formTitle2" style="color:#039;"></span>
    </div>
	<div class="bd">
	<form  name="activityReportForm"  action="${ctx}/addRowToTimesheet.htm" method="post" >	
	
	            <input type="hidden" name="rowId" />
				
				
				
				<fieldset>
					<legend>
						<span style="font:10px Verdana, sans-serif;"><strong>Clients</strong> : <span style="color:red;"> (mandatory)</span></span>
					</legend>
					<dl>						
						<dd>
							<div id="myAutoComplete">
								<input id="myInput" type="text">
								<div id="myContainer"></div>
							</div>
							<input  name="missionId" id="missionId" type="hidden">
						</dd>
					</dl>

					
				</fieldset>
				<fieldset>
					<legend>
						<span style="font:10px Verdana, sans-serif;"><strong>Code prestation</strong> :<span style="color:red;"> (mandatory)</span>
					</legend>
					<dl>						
						<dd>
							
							<!--div id="myAutoComplete2">
								<input id="myInput2" type="text">
								<div id="myContainer2"></div>
							</div>
							<input name="codePrestationId" id="codePrestationId" type="hidden"-->
							<select style="font:12px Verdana" name="codePrestationId" id="codePrestationId" >
								<option value=""></option>
								<c:forEach var="y" items="${viewTimesheetPerEmployee.taskOptions2}">
											<option value="${y.id}">${y.name}</option>
										</c:forEach>
								
							</select> 	
						</dd>
					</dl>

					
				</fieldset>
				<fieldset>
					<%--legend>
						<span style="font:10px Verdana, sans-serif;"><strong>Année & Heures Prestées</strong> : </span>
					</legend>

					<dl>
						<dt><label><span>Année</span></label></dt>
						<dd>
							<select style="font:12px Verdana, sans-serif;margin-right:10pt;" name="year" id="year" >
							    <option value="" selected>...</option-->
								<c:forEach var="yearNumber" begin="2000" end="2020" varStatus="loop">
									
									<option value="${yearNumber}">${yearNumber}</option>
								</c:forEach>
								
							</select>  						
						</dd>
					</dl--%>


					

					<dl>
					
					<table style="font-family: "Lucida Sans Unicode", "Lucida Grande", Sans-Serif;font-size: 10px;text-align: center;border-collapse: collapse;" id="ver-zebra22" width="100%" cellspacing="0" >
			        	
						<thead>
							<tr>
								<th id="1" style="width : 40;border: 1px solid #0066aa;font-size: 10px;font-weight: normal;padding: 12px 15px;color: #039;background: #eff2ff;" scope="col"><span style=" color: #68a1e5;"><%=mondayDate%></span></th>							
								<th id="2" style="width : 40;border: 1px solid #0066aa;font-size: 10px;font-weight: normal;padding: 12px 15px;color: #039;background: #eff2ff;" scope="col"><span style=" color: #68a1e5;"><%=tuesdaydayDate%></span></th>							
								<th id="3" style="width : 40;border: 1px solid #0066aa;font-size: 10px;font-weight: normal;padding: 12px 15px;color: #039;background: #eff2ff;" scope="col"><span style=" color: #68a1e5;"><%=wednesdayDate%></span></th>
								<th id="4" style="width : 40;border: 1px solid #0066aa;font-size: 10px;font-weight: normal;padding: 12px 15px;color: #039;background: #eff2ff;" scope="col"><span style=" color: #68a1e5;"><%=thursdayDate%></span></th>							
								<th id="5" style="width : 40;border: 1px solid #0066aa;font-size: 10px;font-weight: normal;padding: 12px 15px;color: #039;background: #eff2ff;" scope="col"><span style=" color: #68a1e5;"><%=fridayDate%></span></th>
							</tr>
						</thead>
						
						<tr>
								<th style="width : 40;border: 1px solid #0066aa;font-size: 10px;font-weight: normal;padding: 12px 15px;color: #039;background: #eff2ff;" scope="col">
									<select style="width:70;height:20;margin-right:10pt;" size="1" name="monday">
										<c:forEach var="y" items="${viewTimesheetPerEmployee.timeOptions}">
											<option value="${y.id}">${y.name}</option>
										</c:forEach>
									</select>
								</th>
								<th  style="width : 40;border: 1px solid #0066aa;font-size: 10px;font-weight: normal;padding: 12px 15px;color: #039;background: #eff2ff;" scope="col">
									<select style="width:70;height:20;margin-right:10pt;" size="1" name="tuesday">
										<c:forEach var="y" items="${viewTimesheetPerEmployee.timeOptions}">
											<option value="${y.id}">${y.name}</option>
										</c:forEach>
									</select>
								</th>	
								<th id="1" style="width : 40;border: 1px solid #0066aa;font-size: 10px;font-weight: normal;padding: 12px 15px;color: #039;background: #eff2ff;" scope="col">
									<select style="width:70;height:20;margin-right:10pt;" size="1" name="wednesday">
										<c:forEach var="y" items="${viewTimesheetPerEmployee.timeOptions}">
											<option value="${y.id}">${y.name}</option>
										</c:forEach>
									</select>
								</th>	
								<th  style="width : 40;border: 1px solid #0066aa;font-size: 10px;font-weight: normal;padding: 12px 15px;color: #039;background: #eff2ff;" scope="col">
									<select style="width:70;height:20;margin-right:10pt;" size="1" name="thursday">
										<c:forEach var="y" items="${viewTimesheetPerEmployee.timeOptions}">
											<option value="${y.id}">${y.name}</option>
										</c:forEach>
									</select>
								</th>	
								<th  style="width : 40;border: 1px solid #0066aa;font-size: 10px;font-weight: normal;padding: 12px 15px;color: #039;background: #eff2ff;" scope="col">
									<select style="width:70;height:20;margin-right:10pt;" size="1" name="friday">
										<c:forEach var="y" items="${viewTimesheetPerEmployee.timeOptions}">
											<option value="${y.id}">${y.name}</option>
										</c:forEach>
									</select>
								</th>									
							</tr>
						
					</table>
					</dl>
				
				</fieldset>
		
			</form>
		 </div>
	
	</div>

	
