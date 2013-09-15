
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<table cellspacing="0" width="150%" id="calendar">
<%
					 	AnnualPlanningView planningView = (AnnualPlanningView)request.getAttribute("viewActivityPerWeekPerEmployee");
					 	String year = planningView.getYear();
					 	
					   //Compute the maximun dates of the selected month
					   Calendar c = Calendar.getInstance();
					   int weekOfYear = c.get(Calendar.WEEK_OF_YEAR);
					   int todayOfYear = c.get(Calendar.DAY_OF_YEAR);
					   c.set(Calendar.YEAR,Integer.parseInt(year));
					   int startDate = planningView.getStartWeekNumber();					   
						DateFormat dateFormat = new SimpleDateFormat("EEE d.MMM", new Locale("fr","FR"));
					 %>
<tr id="title">
<th width="6%"><a href="#" onclick="moveToPrevMonthOfPlanning()">&laquo;</a></th>
<th colspan="${ fn:length(viewActivityPerWeekPerEmployee.employeeOptions) }" id="thismonth">


														<c:if test='${viewActivityPerWeekPerEmployee.startMonth == 0}'>
														 	<span style="color:red;">Janvier à</span>
														</c:if>
														<c:if test='${viewActivityPerWeekPerEmployee.endMonth == 0}'>
														 	<span style="color:red;">Janvier</span>
														</c:if>														
														<c:if test='${viewActivityPerWeekPerEmployee.startMonth == 1}'>
														 	<span style="color:red;">Fevrier à</span>
														</c:if>
														<c:if test='${viewActivityPerWeekPerEmployee.endMonth == 1}'>
														 	<span style="color:red;">Fevrier</span>
														</c:if>														
														<c:if test='${viewActivityPerWeekPerEmployee.startMonth == 2}'>
														 	<span style="color:red;">Mars à</span>
														</c:if>
														<c:if test='${viewActivityPerWeekPerEmployee.endMonth == 2}'>
														 	<span style="color:red;">Mars</span>
														</c:if>														
														<c:if test='${viewActivityPerWeekPerEmployee.startMonth == 3}'>
														 	<span style="color:red;">Avril à</span>
														</c:if>
														<c:if test='${viewActivityPerWeekPerEmployee.endMonth == 3}'>
														 	<span style="color:red;">Avril</span>
														</c:if>
														<c:if test='${viewActivityPerWeekPerEmployee.startMonth == 4}'>
														 	<span style="color:red;">Mai à</span>
														</c:if>
														<c:if test='${viewActivityPerWeekPerEmployee.endMonth == 4}'>
														 	<span style="color:red;">Mai</span>
														</c:if>														
														<c:if test='${viewActivityPerWeekPerEmployee.startMonth == 5}'>
														 	<span style="color:red;">Juin à</span>
														</c:if>
														<c:if test='${viewActivityPerWeekPerEmployee.endMonth == 5}'>
														 	<span style="color:red;">Juin</span>
														</c:if>														
														<c:if test='${viewActivityPerWeekPerEmployee.startMonth == 6}'>
														 	<span style="color:red;">Juillet à</span>
														</c:if>
														<c:if test='${viewActivityPerWeekPerEmployee.endMonth == 6}'>
														 	<span style="color:red;">Juillet</span>
														</c:if>														
														<c:if test='${viewActivityPerWeekPerEmployee.startMonth == 7}'>
														 	<span style="color:red;">Aout à </span>
														</c:if>
														<c:if test='${viewActivityPerWeekPerEmployee.endMonth == 7}'>
														 	<span style="color:red;">Aout</span>
														</c:if>														
														<c:if test='${viewActivityPerWeekPerEmployee.startMonth == 8}'>
														 	<span style="color:red;">Septembre à</span>
														</c:if>
														<c:if test='${viewActivityPerWeekPerEmployee.endMonth == 8}'>
														 	<span style="color:red;">Septembre</span>
														</c:if>														
														<c:if test='${viewActivityPerWeekPerEmployee.startMonth == 9}'>
														 	<span style="color:red;">Octobre à</span>
														</c:if>
														<c:if test='${viewActivityPerWeekPerEmployee.endMonth == 9}'>
														 	<span style="color:red;">Octobre</span>
														</c:if>														
														<c:if test='${viewActivityPerWeekPerEmployee.startMonth == 10}'>
														 	<span style="color:red;">Novembre à</span>
														</c:if>
														<c:if test='${viewActivityPerWeekPerEmployee.endMonth == 10}'>
														 	<span style="color:red;">Novembre</span>
														</c:if>														
														<c:if test='${viewActivityPerWeekPerEmployee.startMonth == 11}'>
														 	<span style="color:red;">Decembre à </span>
														</c:if>
														<c:if test='${viewActivityPerWeekPerEmployee.endMonth == 11}'>
														 	<span style="color:red;">Decembre</span>
														</c:if>					
													

${viewActivityPerWeekPerEmployee.year}
</th>
<th width="6%" id="nextmonth"><a href="#" onclick="moveToNextMonthOfPlanning()">&raquo;</a></th>
</tr>
<tr id="days">
							<th  width="6%" class="sun">&nbsp;</th>	
							
							<c:forEach var="user" items="${viewActivityPerWeekPerEmployee.employeeOptions}" varStatus="loop">
								
								
																		
									<td class="jun" id="jun30">
									<input type="checkbox" class="text2" name="user" id="${user.id}" value="true" onclick="updateFromUsers( this )"/>
									<font size="1"><span style="color:black;"><strong>${fn:substring(fn:toUpperCase(user.longName), 0,10)}</strong></span></font></td>		
						


							</c:forEach>
							<th width="6%" class="sun">&nbsp;</th>	
														
</tr>
<c:set var="row" value="0"/>
<c:forEach var="date" begin="${viewActivityPerWeekPerEmployee.startWeekNumber}" end="${viewActivityPerWeekPerEmployee.endWeekNumber}">
<tr>
								<%
								 c.set(Calendar.WEEK_OF_YEAR,startDate); startDate++;											    					
								 int weekNumber = c.get(Calendar.WEEK_OF_YEAR);
								 c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
								 String firstDateOfWeek = dateFormat.format(c.getTime());
								 c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
								 String lastDateOfWeek = dateFormat.format(c.getTime());
								%>
								
								<td  width="6%" class="jun" id="jun30">
								<div class="date">${date}</div>
								<input type="checkbox" class="text2" name="day" id="${date}" onclick="updateFromDates( this )"/> 
								<span style="color:black;"><strong><%=firstDateOfWeek%></strong></span> <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:white;"><strong>au</strong></span> <br/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color:black;"><strong><%=lastDateOfWeek%></strong></span>
								</td>
						
									
									<%--c:forEach var="date" begin="${viewActivityPerWeekPerEmployee.startWeekNumber}" end="${viewActivityPerWeekPerEmployee.endWeekNumber}"--%>
									<c:forEach var="user" items="${viewActivityPerWeekPerEmployee.employeeOptions}" varStatus="loop">
								
										 <c:set var="key" value="${user.id}-${date}"/>		
										<td class="jul01 mon item_placeholder" style="height: 100px; width: 150px;" id="${date}##${user.id}++${viewActivityPerWeekPerEmployee.activities[key].id}" onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'" onclick="editWeekPlanningItem(${date},${user.id},'${viewActivityPerWeekPerEmployee.activities[key].id}')" title="">
										
										
										<div class="date">${date}</div>
										<div class="event recur">										
										

										<c:choose>
														<c:when test='${viewActivityPerWeekPerEmployee.activities[key] != null}'>
														 	<t:tooltip>  
																<t:text>
																<c:if test='${fn:length(viewActivityPerWeekPerEmployee.activities[key].activities) != 0}'>
																	<a href="${ctx}/showAgendaPage.htm?year=${viewActivityPerWeekPerEmployee.year}&employeeId=${user.id}&week=${date}"><img src="images/calbtn.gif" border="0"/></a>
																</c:if>
																<c:set var="index" value="1"/>
																	<c:forEach var="itemactivity" items="${viewActivityPerWeekPerEmployee.activities[key].compressedItemEventPlannings}" varStatus="loop">
																	   <c:if test='${itemactivity.totalHoursSpent >= 2}'>
																		<span class="time">
																	
																		<fmt:formatDate value="${itemactivity.dateOfEvent}" pattern="dd/MM/yyyy"/>
																		
																		
																		</span>
																		<c:choose>
																			<c:when test='${itemactivity.mission == true}'>																		
																				 <c:set var="activity_style" value="background-color:#eee;color:blue;border-bottom:1px solid #ccc;"/>
																			</c:when>
																			
																			<c:otherwise>
																				 <c:set var="activity_style" value="color:green;"/>					
																			</c:otherwise>	
																		</c:choose>
																		
																		<span class="loc" style="${activity_style}">											
																			${index}. ${fn:substring(fn:toLowerCase(itemactivity.title), 0,8)}[${itemactivity.totalHoursSpent}h]										
																		</span>
																		<c:set var="index" value="${index + 1}"/>
																	   </c:if>
																	</c:forEach>
																</t:text>
																<t:help width="300" >
																	<strong>${viewActivityPerWeekPerEmployee.activities[key].employee.fullName} [${date}]</strong>
																		
																	
																	<hr/>
																	<c:set var="index" value="1"/>
																	<c:forEach var="itemactivity" items="${viewActivityPerWeekPerEmployee.activities[key].activities}" varStatus="loop">
																		<span class="time"><fmt:formatDate value="${itemactivity.dateOfEvent}" pattern="dd/MM/yyyy"/></span>
																		<span class="loc">											
																			${index}. ${fn:substring(fn:toLowerCase(itemactivity.title), 0,50)}	[ ${itemactivity.totalHoursSpent} ]											
																		</span>
																		<c:set var="index" value="${index + 1}"/>
																	</c:forEach>
																</t:help> 
															</t:tooltip>
														</c:when>
														
														<c:otherwise>
														<%--t:tooltip>  
																<t:text>
																${user.longName} [${date}]
																</t:text>
																<t:help width="300" >
																	<strong>${user.longName} [${date}]</strong>																
																</t:help> 												
														</t:tooltip--%>			
														</c:otherwise>																								
													</c:choose> 

										

										
										
										</div>
										

										</td> 	

									</c:forEach>
									
									<td width="6%" class="jun">&nbsp;</td>
									
</tr>
<c:set var="row" value="${row + 1}"/> 
</c:forEach>

</table>