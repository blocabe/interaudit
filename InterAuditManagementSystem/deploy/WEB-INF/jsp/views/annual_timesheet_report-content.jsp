<jsp:directive.page contentType="text/html;charset=UTF-8" />
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="java.util.*,java.text.*,java.io.PrintWriter,com.interaudit.service.view.AnnualTimesheetReportView" %>

<link rel="stylesheet" type="text/css" href="${ctx}/script/build/fonts/fonts-min.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/script/build/container/assets/skins/sam/container.css" />
<script type="text/javascript" src="${ctx}/script/build/utilities/utilities.js"></script>
<script type="text/javascript" src="${ctx}/script/build/container/container-min.js"></script>
<script type="text/javascript" src="${ctx}/script/build/yahoo/yahoo-min.js"></script>
<script type="text/javascript" src="${ctx}/script/build/event/event-min.js"></script>
<script type="text/javascript" src="${ctx}/script/build/connection/connection-min.js"></script>
<script type="text/javascript" src="${ctx}/script/scriptaculous/prototype.js"></script>
<script type="text/javascript" src="${ctx}/script/scriptaculous/scriptaculous.js"></script>


<link rel="stylesheet" type="text/css" href="${ctx}/script/build/datatable/assets/skins/sam/datatable.css" />
<script type="text/javascript" src="${ctx}/script/build/yahoo-dom-event/yahoo-dom-event.js"></script>
<script type="text/javascript" src="${ctx}/script/build/dragdrop/dragdrop-min.js"></script>
<script type="text/javascript" src="${ctx}/script/build/element/element-min.js"></script>
<script type="text/javascript" src="${ctx}/script/build/datasource/datasource-beta-min.js"></script>
<script type="text/javascript" src="${ctx}/script/build/datatable/datatable-beta-min.js"></script>





<!-- START ADDED PART -->   

<style>

th.colonnex { 
width:30; 

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
</style>

<script>


  function moveToStartOfPlanning() {
		
			var url ="${ctx}/showTimesheetGlobalSituationsPage.htm?year=" + ${viewActivityPerWeekPerEmployee.year} +"&startMonth=0";
			window.location = url;		
	}

  function moveToPrevMonthOfPlanning() {
		
		var url ="${ctx}/showTimesheetGlobalSituationsPage.htm?year=" + ${viewActivityPerWeekPerEmployee.year} +"&startMonth="+ ${viewActivityPerWeekPerEmployee.startMonth - 1};
		window.location = url;		
}

  function moveToNextMonthOfPlanning() {
		
		var url ="${ctx}/showTimesheetGlobalSituationsPage.htm?year=" + ${viewActivityPerWeekPerEmployee.year} +"&startMonth="+ ${viewActivityPerWeekPerEmployee.startMonth + 1};
		window.location = url;		
  }

  function moveToEndOfPlanning() {
		
		var url ="${ctx}/showTimesheetGlobalSituationsPage.htm?year=" + ${viewActivityPerWeekPerEmployee.year} +"&startMonth=11";
		window.location = url;		
  }

  function loadPlanningForYear(){
  
	var year  = document.getElementById('select_planning_year');
	alert(year);
	
	  var url ="${ctx}/showTimesheetGlobalSituationsPage.htm?year=" +${viewActivityPerWeekPerEmployee.year};
		window.location = url;
  }

  

  


  
</script>




    
<div class="nav_alphabet" style="background-color: rgb(248, 246, 233); border: 1px solid  #0066aa; text-align:center; padding-top:1pt; font: bold 12px Verdana, sans-serif;">



<table align="center">
	<tr>
	<td style="border :0px dotted #0066aa;">
		
	
	<%--input class="button120"  style="width:120px; font:10px Verdana, sans-serif;" type="button" value="Export to Excel"  onclick="exportExcel()"/--%>
	<form style="margin:3pt" name="searchBudgetForm" action="${ctx}/showTimesheetGlobalSituationsPage.htm" method="post" >
			
			<span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Exercice</strong> : </span>
			<select style="font:10px Verdana, sans-serif;margin-right:10pt;" id="select_planning_year" name="year" onchange="document.searchBudgetForm.submit();">
		    <c:forEach var="y" items="${viewTimesheetReports.yearOptions}">
	             		<option value="${y.id}"<c:if test='${viewTimesheetReports.year==y.id}'> selected</c:if>>${y.name}</option>
	        </c:forEach>			
			</select> 
	</form>		
		
					

	</td>
	
		
	</tr>
</table>
	

	
</div>





<div>
<form name="planningForm" action="${ctx}/showTimesheetGlobalSituationsPage.htm" method="post" >
       <table id="ver-zebra" width="100%" cellspacing="0" class="formlist">
	   				<caption> 
	   				<span style=" color: purple">
					TIMESHEET BOARD&nbsp; &nbsp;&nbsp; &nbsp;${viewTimesheetReports.year}&nbsp; &nbsp;MISE A JOUR :  De   <span style="color:orange;">${viewTimesheetReports.startMonthAsString}</span> à   <span style="color:orange;">${viewTimesheetReports.endMonthAsString} </span>
					</span>
					</caption>

					<thead>
						
						<tr>
												
							<th width="5%"><font size="1">Week</font></th>
							<th width="10%"><font size="1">Dates</font></th>
							<th><font size="1">Validated</font></th>
							<th><font size="1">Submitted</font></th>
							<th><font size="1">Rejected</font></th>
							<th><font size="1">Draft</font></th>
												
						</tr>
					</thead>

					<%
						AnnualTimesheetReportView planningView = (AnnualTimesheetReportView)request.getAttribute("viewTimesheetReports");
					 	String year = planningView.getYear();
					 	
					   //Compute the maximun dates of the selected month
					   Calendar c = Calendar.getInstance();
					    int weekOfYear = c.get(Calendar.WEEK_OF_YEAR);
					   int todayOfYear = c.get(Calendar.DAY_OF_YEAR);
					   c.set(Calendar.YEAR,Integer.parseInt(year));
					   int startDate = planningView.getStartWeekNumber();					   
						DateFormat dateFormat = new SimpleDateFormat("EEE d.MMM", new Locale("fr","FR"));
					 %>
					<tbody>

					

					<c:set var="row" value="0"/>
					
					 <%--c:forEach var="date" begin="${viewTimesheetReports.startWeekNumber}" end="${viewTimesheetReports.endWeekNumber}"--%>
					 <c:forEach var="report" items="${viewTimesheetReports.timesheetWeekReports}" varStatus="loop">	
							<%
							 c.set(Calendar.WEEK_OF_YEAR,startDate); startDate++;											    					
							 int weekNumber = c.get(Calendar.WEEK_OF_YEAR);
						     c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
							 String firstDateOfWeek = dateFormat.format(c.getTime());
						     c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
							 String lastDateOfWeek = dateFormat.format(c.getTime());
							%>

						<tr  onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
									<th height="30px">
									<p style="margin-bottom: 1px;" align="center"><font size="1" color="#FF0000"><%=weekNumber%></font></p>
									</th>
									<th wi>									
										<p style="margin-top: 1px;" align="center"><font size="1"><%=firstDateOfWeek%><br/><%=lastDateOfWeek%></font></p>
									</th>
									
									
									<%
										if ( weekNumber != weekOfYear){
									%>
									<td   bgcolor="white">
									<%
										} else {
									%>
										<td  bgcolor="#01B0DB">
									<%
										}
									%>
									
									
									<font  color="black">${report.nbValidated} / ${report.nbTotal}</font></td>
									
									<%
										if ( weekNumber != weekOfYear){
									%>
									<td   bgcolor="white">
									<%
										} else {
									%>
										<td  bgcolor="#01B0DB">
									<%
										}
									%>
									
									
									<font color="black">${report.nbSubmitted} / ${report.nbTotal}</font></td>
									<%
										if ( weekNumber != weekOfYear){
									%>
									<td   bgcolor="white">
									<%
										} else {
									%>
										<td  bgcolor="#01B0DB">
									<%
										}
									%>
									
									<font color="black">${report.nbRejected} / ${report.nbTotal}</font></td>
									<%
										if ( weekNumber != weekOfYear){
									%>
									<td   bgcolor="white">
									<%
										} else {
									%>
										<td  bgcolor="#01B0DB">
									<%
										}
									%>
									
									
									<font color="black">${report.nbDraft} / ${report.nbTotal}</font></td>
									
															
							</tr>
							<c:set var="row" value="${row + 1}"/>  
					 </c:forEach>
					</tbody>
					
		</table>
 </form>
</div>


  

  

<div class="nav_alphabet" style="background-color: rgb(248, 246, 233); border: 1px solid  #0066aa; text-align:center; padding-top:1pt; font: bold 12px Verdana, sans-serif;">

		
			<span class="galphabet_center"><a href="${ctx}/showTimesheetGlobalSituationsPage.htm?year=${viewTimesheetReports.year}&startMonth=0">
			
													<c:choose>
														<c:when test='${viewTimesheetReports.startMonth == 0}'>
														 	<span style="color:red;">Janv</span>
														</c:when>
														<c:when test='${viewTimesheetReports.endMonth == 0}'>
														 	<span style="color:red;">Janv</span>
														</c:when>
														<c:otherwise>
														<span style="color:blue;">Janv</span>
														</c:otherwise>																								
													</c:choose> 
			</a></span>-
			<span class="galphabet_center"><a href="${ctx}/showTimesheetGlobalSituationsPage.htm?year=${viewTimesheetReports.year}&startMonth=1">
													<c:choose>
														<c:when test='${viewTimesheetReports.startMonth == 1}'>
														 	<span style="color:red;">Fevr</span>
														</c:when>
														<c:when test='${viewTimesheetReports.endMonth == 1}'>
														 	<span style="color:red;">Fevr</span>
														</c:when>
														<c:otherwise>
														<span style="color:blue;">Fevr</span>
														</c:otherwise>																								
													</c:choose> 
			</a></span>-

			<span class="galphabet_center"><a href="${ctx}/showTimesheetGlobalSituationsPage.htm?year=${viewTimesheetReports.year}&startMonth=2">
													<c:choose>
														<c:when test='${viewTimesheetReports.startMonth == 2}'>
														 	<span style="color:red;">Mars</span>
														</c:when>
														<c:when test='${viewTimesheetReports.endMonth == 2}'>
														 	<span style="color:red;">Mars</span>
														</c:when>
														<c:otherwise>
														<span style="color:blue;">Mars</span>
														</c:otherwise>																								
													</c:choose> 
			</a></span>-

			<span class="galphabet_center"><a href="${ctx}/showTimesheetGlobalSituationsPage.htm?year=${viewTimesheetReports.year}&startMonth=3">
													<c:choose>
														<c:when test='${viewTimesheetReports.startMonth == 3}'>
														 	<span style="color:red;">Avril</span>
														</c:when>
														<c:when test='${viewTimesheetReports.endMonth == 3}'>
														 	<span style="color:red;">Avril</span>
														</c:when>
														<c:otherwise>
														<span style="color:blue;">Avril</span>
														</c:otherwise>																								
													</c:choose> 
			</a></span>-

			<span class="galphabet_center"><a href="${ctx}/showTimesheetGlobalSituationsPage.htm?year=${viewTimesheetReports.year}&startMonth=4">
													<c:choose>
														<c:when test='${viewTimesheetReports.startMonth == 4}'>
														 	<span style="color:red;">Mai</span>
														</c:when>
														<c:when test='${viewTimesheetReports.endMonth == 4}'>
														 	<span style="color:red;">Mai</span>
														</c:when>
														<c:otherwise>
														<span style="color:blue;">Mai</span>
														</c:otherwise>																								
													</c:choose> 
			</a></span>-

			<span class="galphabet_center"><a href="${ctx}/showTimesheetGlobalSituationsPage.htm?year=${viewTimesheetReports.year}&startMonth=5">
													<c:choose>
														<c:when test='${viewTimesheetReports.startMonth == 5}'>
														 	<span style="color:red;">Juin</span>
														</c:when>
														<c:when test='${viewTimesheetReports.endMonth == 5}'>
														 	<span style="color:red;">Juin</span>
														</c:when>
														<c:otherwise>
														<span style="color:blue;">Juin</span>
														</c:otherwise>																								
													</c:choose> 
			</a></span>-

			<span class="galphabet_center"><a href="${ctx}/showTimesheetGlobalSituationsPage.htm?year=${viewTimesheetReports.year}&startMonth=6">
													<c:choose>
														<c:when test='${viewTimesheetReports.startMonth == 6}'>
														 	<span style="color:red;">Juil</span>
														</c:when>
														<c:when test='${viewTimesheetReports.endMonth == 6}'>
														 	<span style="color:red;">Juil</span>
														</c:when>
														<c:otherwise>
														<span style="color:blue;">Juil</span>
														</c:otherwise>																								
													</c:choose> 
			</a></span>-

			<span class="galphabet_center"><a href="${ctx}/showTimesheetGlobalSituationsPage.htm?year=${viewTimesheetReports.year}&startMonth=7">
													<c:choose>
														<c:when test='${viewTimesheetReports.startMonth == 7}'>
														 	<span style="color:red;">Août</span>
														</c:when>
														<c:when test='${viewTimesheetReports.endMonth == 7}'>
														 	<span style="color:red;">Août</span>
														</c:when>
														<c:otherwise>
														<span style="color:blue;">Août</span>
														</c:otherwise>																								
													</c:choose> 
			</a></span>-

			<span class="galphabet_center"><a href="${ctx}/showTimesheetGlobalSituationsPage.htm?year=${viewTimesheetReports.year}&startMonth=8">
													<c:choose>
														<c:when test='${viewTimesheetReports.startMonth == 8}'>
														 	<span style="color:red;">Sept</span>
														</c:when>
														<c:when test='${viewTimesheetReports.endMonth == 8}'>
														 	<span style="color:red;">Sept</span>
														</c:when>
														<c:otherwise>
														<span style="color:blue;">Sept</span>
														</c:otherwise>																								
													</c:choose> 
			</a></span>-

			<span class="galphabet_center"><a href="${ctx}/showTimesheetGlobalSituationsPage.htm?year=${viewTimesheetReports.year}&startMonth=9">
			<c:choose>
														<c:when test='${viewTimesheetReports.startMonth == 9}'>
														 	<span style="color:red;">Oct</span>
														</c:when>
														<c:when test='${viewTimesheetReports.endMonth == 9}'>
														 	<span style="color:red;">Oct</span>
														</c:when>
														<c:otherwise>
														<span style="color:blue;">Oct</span>
														</c:otherwise>																								
													</c:choose> 
			</a></span>-

			<span class="galphabet_center"><a href="${ctx}/showTimesheetGlobalSituationsPage.htm?year=${viewTimesheetReports.year}&startMonth=10">
													<c:choose>
														<c:when test='${viewTimesheetReports.startMonth == 10}'>
														 	<span style="color:red;">Nov</span>
														</c:when>
														<c:when test='${viewTimesheetReports.endMonth == 10}'>
														 	<span style="color:red;">Nov</span>
														</c:when>
														<c:otherwise>
														<span style="color:blue;">Nov</span>
														</c:otherwise>																								
													</c:choose> 
			</a></span>-

			<span class="galphabet_center"><a href="${ctx}/showTimesheetGlobalSituationsPage.htm?year=${viewTimesheetReports.year}&startMonth=11">
												<c:choose>
														<c:when test='${viewTimesheetReports.startMonth == 11}'>
														 	<span style="color:red;">Dec</span>
														</c:when>
														<c:when test='${viewTimesheetReports.endMonth == 11}'>
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





