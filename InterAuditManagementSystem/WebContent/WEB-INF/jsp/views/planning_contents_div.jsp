<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

    
<style type="text/css">
#main{		
		 float:left;
}

#contener{
		/*width:4300px;*/
		margin:auto;
		/*background-color:#1E374B;*/
		
}

#content{
		width:200px;
		/*
		margin:8px;
		margin-top:2px;
		margin-bottom:4px;	
*/		
		margin-bottom:4px;
		margin-right:4px;
		float:left;
}

#content_date{
		width:75px;
		/*
		margin:8px;
		margin-top:2px;
		margin-bottom:4px;	
	
		margin-right:2px;
		margin-bottom:4px;
		*/	
		margin-right:4px;
		float:left;
		
		
}
#img_date {
	width:75px;
	height:150px;
	float:left;
	
	/*margin-top:5px;*/
	/*border: 1px solid black;*/
	
	
}

#img_date p{
	
	vertical-align:middle;
	
	
}


#img {
	width:200px;
	height:150px;
	float:left;
	/*margin-top:5px;*/
	/*border: 1px solid black;*/
	
}







#txt{

	width:100%;

	float:left;

	margin-top:2px;

	font-family: Arial, Helvetica, sans-serif;

	font-size: 12px;

	color: #FFFFFF;

	font-weight: bold;

}





#txt a{

	font-family: Arial, Helvetica, sans-serif;

	font-size: 12px;

	font-weight: bold;

	color: #ff66cf;

	text-decoration: none;

}



#txt strong{

color:#9898a7;}

</style>


<!-- main end-->
<div id="contener" style="width:${ (fn:length(viewActivityPerWeekPerEmployee.employeeOptions)*204) + 100}px;">
<!--div id="main">
<div id="contents"-->


<%
AnnualPlanningView planningView = (AnnualPlanningView)request.getAttribute("viewActivityPerWeekPerEmployee");
String year = planningView.getYear();

//Compute the maximun dates of the selected month
Calendar c = Calendar.getInstance();
int weekOfYear = c.get(Calendar.WEEK_OF_YEAR);
int todayOfYear = c.get(Calendar.DAY_OF_YEAR);
c.set(Calendar.YEAR,Integer.parseInt(year));
int startDate = planningView.getStartWeekNumber();					   
DateFormat dateFormat = new SimpleDateFormat("d.M", new Locale("fr","FR"));
%>


<c:set var="row" value="0"/>
<c:forEach var="date" begin="${viewActivityPerWeekPerEmployee.startWeekNumber}" end="${viewActivityPerWeekPerEmployee.endWeekNumber}">

								<%
								 c.set(Calendar.WEEK_OF_YEAR,startDate); startDate++;											    					
								 int weekNumber = c.get(Calendar.WEEK_OF_YEAR);
								 c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
								 String firstDateOfWeek = dateFormat.format(c.getTime());
								 c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
								 String lastDateOfWeek = dateFormat.format(c.getTime());
								%>
									<div id="content_date">
										<div id="img_date" style="border:1px solid black;background-color:#B0C4DE;">
											<p></strong></a>  <strong><%=firstDateOfWeek%></strong>  <span style="color:red;">au</span> <strong><%=lastDateOfWeek%></strong></p>
										</div>
									</div>
									<c:forEach var="user" items="${viewActivityPerWeekPerEmployee.employeeOptions}" varStatus="loop">
								
										 <c:set var="key" value="${user.id}-${date}"/>	
										 <!-- content start-->
										 
										 
										 
											<div id="content">
												<div id="img"  <%
										if ( weekNumber != weekOfYear){
									%>
									 style="border:1px solid black;background-color:#E6E6FA;"
									<%
										} else {
									%>
										style="border:1px solid black;background-color:#F0E68C;"
									<%
										}
									%>
											>
													<a style="text-decoration:none;" href="${ctx}/showAgendaPage.htm?year=${viewActivityPerWeekPerEmployee.year}&employeeId=${user.id}&week=${date}"><strong>${fn:substring(user.longName, 0,8)}</strong></a> 
													
													<div class="date" ><strong>${date} <img src="images/add.png" border="0" onclick="editWeekPlanningItem(${date},${user.id},'${viewActivityPerWeekPerEmployee.activities[key].id}')"/></strong></div>
													<c:choose>
														<c:when test='${viewActivityPerWeekPerEmployee.activities[key] != null}'>
														 	<t:tooltip>  
																<t:text>																
																<c:set var="index" value="1"/>
																
																<ul style="list-style-type: none;padding-left:2px;padding-top:2px;">
																	<c:forEach var="itemactivity" items="${viewActivityPerWeekPerEmployee.activities[key].activities}" varStatus="loop">
																	   <c:if test='${itemactivity.totalHoursSpent >= 2}'>
																	   <li>
																			<span class="time">
																		
																			<fmt:formatDate value="${itemactivity.dateOfEvent}" pattern="dd/MM/yyyy"/>
																			
																			
																			</span>
																			<c:choose>
																				<c:when test='${itemactivity.mission == true}'>																		
																					 <c:set var="activity_style" value="color:black;"/>
																				</c:when>
																				
																				<c:otherwise>
																					 <c:set var="activity_style" value="color:green;"/>					
																				</c:otherwise>	
																			</c:choose>
																			
																			<span class="loc" style="${activity_style}">											
																				${index}. ${fn:substring(fn:toLowerCase(itemactivity.title), 0,8)}[${itemactivity.totalHoursSpent}h]										
																			</span>
																		</li>
																		<c:set var="index" value="${index + 1}"/>
																	   </c:if>
																	</c:forEach>
																</ul>
																</t:text>
																<t:help width="350" >
																	<strong>${viewActivityPerWeekPerEmployee.activities[key].employeeFullName} [${date}]</strong>
																	<br/>
																	<c:set var="index" value="1"/>
																	<c:forEach var="itemactivity" items="${viewActivityPerWeekPerEmployee.activities[key].activities}" varStatus="loop">
																		<span class="time"><fmt:formatDate value="${itemactivity.dateOfEvent}" pattern="dd/MM/yyyy"/></span>
																		<span class="loc">											
																			${index}. ${fn:substring(fn:toLowerCase(itemactivity.title), 0,50)}	[ ${itemactivity.totalHoursSpent} h]											
																		</span><br/>
																		<c:set var="index" value="${index + 1}"/>
																	</c:forEach>
																</t:help> 
															</t:tooltip>
														</c:when>
														
														<c:otherwise>
															
														</c:otherwise>																								
													</c:choose> 
												</div>
												<!--div id="txt">
													<table width="100%" border="0">
														<tr>
															<td colspan="3" class="red">
															<div class="videoTitleTD"><a href="">${key}</a></div>
															</td>
														</tr>
														<tr>
															<td width="38%" class="red"><strong>Dur&eacute;e: </strong>27:48</td>
															<td width="39%" class="red"><strong>Vues:</strong> 3790</td>
															<td width="32%">
																<div class="rate">
																	<span class="over50">56%</span>		
																</div>
															</td>
														</tr>
													</table>
												</div-->
											</div>
											<!-- content end-->
										

									</c:forEach>
									
									
<c:set var="row" value="${row + 1}"/> 
</c:forEach>

<!--/div>
</div-->
</div>