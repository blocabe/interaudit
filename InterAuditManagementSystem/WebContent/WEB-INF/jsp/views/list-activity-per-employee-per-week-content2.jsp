<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="t"      uri="/tags/tooltips-tiles" %>
<%@ page import="java.util.*,java.text.*,java.io.PrintWriter,com.interaudit.service.view.AnnualPlanningView" %>

<link rel="stylesheet" type="text/css" href="${ctx}/script/build/fonts/fonts-min.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/script/build/container/assets/skins/sam/container.css" />
<script type="text/javascript" src="${ctx}/script/build/utilities/utilities.js"></script>
<script type="text/javascript" src="${ctx}/script/build/container/container-min.js"></script>
<script type="text/javascript" src="${ctx}/script/build/yahoo/yahoo-min.js"></script>
<script type="text/javascript" src="${ctx}/script/build/event/event-min.js"></script>
<script type="text/javascript" src="${ctx}/script/build/connection/connection-min.js"></script>
<script type="text/javascript" src="${ctx}/script/scriptaculous/prototype.js"></script>
<script type="text/javascript" src="${ctx}/script/scriptaculous/scriptaculous.js"></script>





<!-- START ADDED PART -->   

<style>

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


function addMultipleTasks()
  {
    document.addMultipleTaskForm.days.value = getCheckDays();	
    document.addMultipleTaskForm.users.value = getCheckUsers();

	document.addMultipleTaskForm.todo.value = "add";

	document.addMultipleTaskForm.currentyear.value = ${viewActivityPerWeekPerEmployee.year};
	document.addMultipleTaskForm.month.value = document.listForm.month.value;
	document.addMultipleTaskForm.profile.value = document.listForm.role.value;
	
    document.addMultipleTaskForm.submit();
  }

  function clearAllTasks()
  {
    document.addMultipleTaskForm.days.value = getCheckDays();
    document.addMultipleTaskForm.users.value = getCheckUsers();

	document.addMultipleTaskForm.todo.value = "remove";

	document.addMultipleTaskForm.currentyear.value = ${viewActivityPerWeekPerEmployee.year};
	document.addMultipleTaskForm.month.value = document.listForm.month.value;
	
    document.addMultipleTaskForm.submit();
  }




  function editAgenda(url)
  {
      window.location=url;
  }
  
</script>




    
<div class="nav_alphabet" style="background-color: rgb(248, 246, 233); border: 1px solid  #0066aa; text-align:center; padding-top:1pt; font: bold 12px Verdana, sans-serif;">

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

<table>
	<tr>
	<td style="border :0px dotted #0066aa;">
		
	<input class="button120" style="width:120px; font:10px Verdana, sans-serif;" type="button"  value="Configure screen" id="show2" onclick="resetForm()"/>
	&nbsp;
	<input class="button120"  style="width:120px; font:10px Verdana, sans-serif;" type="button" value="Export to Excel"  onclick="exportExcel()"/>
		
					

	</td>
	<td width="2%" >&nbsp;</td>
	<td style="border :1px dotted #0066aa;">
		<form name="addMultipleTaskForm" action="${ctx}/handleMultipleEventsPage.htm" method="post"  style="margin :2px;">
		<input type="hidden" name="days"/>
		<input type="hidden" name="users"/>
		<input type="hidden" name="todo"/>
		<input type="hidden" name="currentyear"/>
		<input type="hidden" name="month"/>
		<input type="hidden" name="profile"/>
		
		<span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Activité</strong> : </span>
		<select style="font:10px Verdana, sans-serif;margin-right:10pt;" name="missionId"  onChange="displaysub()">
					<option value="#">This is a place Holder text </option>
					<option value="#">This is a Place Holder text </option>
					<option value="#">This is a Place Holder text </option>
		    <%--c:forEach var="y" items="${viewActivityPerWeekPerEmployee.missionOptions}">
	             		<option value="${y.id}">
						${fn:substring(fn:toUpperCase(y.name), 0, 20)}
						</option>
	        </c:forEach--%>
		</select> 
		&nbsp;
		<span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Temps</strong> : </span>
		<select style="font:10px Verdana, sans-serif;margin-right:10pt;" name="time" >
		   
	             		<option value="4">1 heure</option>
	             		<option value="8">2 heures</option>
	             		<option value="12">3 heures</option>
	             		<option value="16">4 heures</option>
	             		<option value="20">5 heures</option>
	             		<option value="24">6 heures</option>
	             		<option value="28">7 heures</option>
	             		<option value="32">8 heures</option>
	       
		</select> 
				
		&nbsp;
		<input style="font:10px Verdana, sans-serif;" type="button" class="button120" value="Add" onclick="addMultipleTasks();"/>	
		&nbsp;
		<input style="font:10px Verdana, sans-serif;" type="button" class="button120" value="Clear" onclick="clearAllTasks();"/>
		&nbsp;
		<%--input style="font:10px Verdana, sans-serif;" type="button" class="button120" value="Publish" onclick="publishAllTasks();"/--%>	
	</form>
	</td>
		
	</tr>
</table>
	

	
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
<c:forEach var="y" items="${viewActivityPerWeekPerEmployee.missionOptions}" varStatus="loop">
	chargeable[${loop.index}]=new Option("${fn:substring(fn:toUpperCase(y.name), 0, 19)}","${y.id}");
</c:forEach>
chargeable[${ fn:length(viewActivityPerWeekPerEmployee.missionOptions) }]=new Option("BACK TO CATEGORIES","");

var nonchargeable=new Array();
<c:forEach var="y" items="${viewActivityPerWeekPerEmployee.taskOptions}" varStatus="loop">
	nonchargeable[${loop.index}]=new Option("${fn:substring(fn:toUpperCase(y.name), 0, 19)}","${y.id}");
</c:forEach>
nonchargeable[${ fn:length(viewActivityPerWeekPerEmployee.taskOptions) }]=new Option("BACK TO CATEGORIES","");

var curlevel=1;
var cacheobj=document.addMultipleTaskForm.missionId;

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
//populate(category);
</script>
			
<script>
//SHOW categories by default
populate(category);
</script>



<div>
<form name="planningForm" action="${ctx}/showAnnualPlanningPage.htm" method="post" >
       <table id="ver-zebra" width="100%" cellspacing="0" class="formlist">
	   				<caption> 
	   				<span style=" color: purple">
					PLANNING BOARD&nbsp; &nbsp;&nbsp; &nbsp;${viewActivityPerWeekPerEmployee.year}&nbsp; &nbsp;MISE A JOUR : ${viewActivityPerWeekPerEmployee.lastUpdate}
					</span>
					</caption>
	   

					<thead>
						<tr>
							<th scope="col" style="width : 20px;">&nbsp;</th>
							<th scope="col" style="width : 20px;">
								<input type="checkbox" class="text2" name="allUsers" onclick="selectAllUsers( this )" value="true"/>
							</th>
							<c:forEach var="item" items="${viewActivityPerWeekPerEmployee.employeeOptions}" varStatus="loop">
								<th scope="col">
									<input type="checkbox" class="text2" name="user" id="${item.id}" value="true" onclick="updateFromUsers( this )"/>
								</th>
							</c:forEach>							
						</tr>
						<tr>
							<th scope="col" style="width : 20px;">
								<input type="checkbox" class="text2" name="allDates" onclick="selectAllDates( this )" value="true"/>
							</th>
							<th scope="col" style="width : 20px;">&nbsp;</th>							
							<c:forEach var="item" items="${viewActivityPerWeekPerEmployee.employeeOptions}" varStatus="loop">
								<th scope="col" title="">

									<t:tooltip>  
														   <t:text><em><span style=" color: #68a1e5">${fn:substring(fn:toUpperCase(item.name), 0, 3)}</span></em></t:text>
														  <t:help><font color="STEELBLUE"><strong>
															 ${item.name}  
															</strong></font></t:help> 
									</t:tooltip>
								
								</th>
							</c:forEach>
							
						</tr>
					</thead>
					<tbody>
					
					 
					 <%
					 	AnnualPlanningView planningView = (AnnualPlanningView)request.getAttribute("viewActivityPerWeekPerEmployee");
					 	String year = planningView.getYear();
					 	//String month = planningView.getMonth();
					 	
					   //Compute the maximun dates of the selected month
					   Calendar c = Calendar.getInstance();
					   int todayOfYear = c.get(Calendar.DAY_OF_YEAR);
					   //c.set(Integer.parseInt(year),Integer.parseInt(month), 1);
					   c.set(Calendar.YEAR,Integer.parseInt(year));
					   
					    // int maxDateInteger = c.getActualMaximum(Calendar.DAY_OF_MONTH);
					     int startDate = planningView.getStartWeekNumber();
					    // int monthToDisplay = Integer.parseInt(month) + 1;
					    // int numberOfWeekInMonth = c.getActualMaximum(Calendar.WEEK_OF_MONTH);
						// int preWeekNumber = 0;
						// int startDateOfWeek = 1;
						// int currentDayOfYear = -1;
						// int currentDayOfMonth = -1;

						DateFormat dateFormat2 = new SimpleDateFormat("dd-MMMMM-yyyy", new Locale("fr","FR"));
						DateFormat dateFormat = new SimpleDateFormat("EEE d", new Locale("fr","FR"));
					 %>
					
					 <c:set var="row" value="0"/>
					 <c:forEach var="date" begin="${viewActivityPerWeekPerEmployee.startWeekNumber}" end="${viewActivityPerWeekPerEmployee.endWeekNumber}">
					 
						<%
						   // c.set(Integer.parseInt(year),Integer.parseInt(month),startDate);
						   //startDate++;
						  
						   c.set(Calendar.WEEK_OF_YEAR,startDate); startDate++;
						   
						   // boolean showDate = false;
							//currentDayOfMonth = c.get(Calendar.DAY_OF_MONTH);
							// if( currentDayOfMonth >= planningView.getStartDateNumber() && currentDayOfMonth <= planningView.getEndDateNumber()  ){
							//	  showDate = true;
							// }
							 
						    boolean disableDate = false;
							int currentDayOfYear = c.get(Calendar.DAY_OF_YEAR);
							if( currentDayOfYear < todayOfYear){
								  disableDate = true;
							}
							
						   // int currentDate = c.get(Calendar.DAY_OF_MONTH);
						   //boolean hasChanged = false;
						   int weekNumber = c.get(Calendar.WEEK_OF_YEAR);
						   //if( preWeekNumber != weekNumber){
							 // preWeekNumber = weekNumber;
							//  hasChanged = true;
							 // startDateOfWeek = currentDate;
						   //}
						  						 
						 // int maxDateOfWeek = (currentDate + 6 ) % maxDateInteger;
						  //boolean isEndOfWeek = ( ( c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ) || currentDate == maxDateInteger );
						  //boolean isWeekend = ( c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ) || (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY);
						 // int currentWeekNumberInMonth = c.get(Calendar.WEEK_OF_MONTH);
						 String currentDateFormated = dateFormat.format(c.getTime());
						 //String weekStartDateFormated = dateFormat2.format(c.getTime());
						 
						// String weekEndDateFormated = null; 
						// if( hasChanged == true) {
						//	 Calendar c2 = Calendar.getInstance();							 
						//	 c2.set(Calendar.WEEK_OF_YEAR,weekNumber);
						//	 c2.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
						//	 weekEndDateFormated = dateFormat2.format(c2.getTime());
						// }
						%>

						
						<!--% if( showDate == true) {%-->
						
						<!-- %if( isWeekend == false && hasChanged == true) {%-->
							<!--tr  onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
										<th colspan ="${ fn:length(viewActivityPerWeekPerEmployee.employeeOptions) +2}" align="left">
											<span style=" color: purple;">Du <!--%=weekStartDateFormated%> au <!--%=weekEndDateFormated%></span>											
										</th>
							</tr-->
						<!--%}%-->

  

						
						
						
						<!-- % if( isWeekend == false) {%-->
						
							<tr  onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
									<th style="width : 10px;">
										<span style=" color: blue;">
											<input type="checkbox" class="text2" name="day" id="${date}" onclick="updateFromDates( this )" 
											<!-- %if( disableDate == true) {%> DISABLED <%}%>value="true"/-->
										</span>
									</th>
									
									<th style="width : 50px;"><span style=" color: #68a1e5;">										
										<%=currentDateFormated%>
									</span></th>
									<c:forEach var="user" items="${viewActivityPerWeekPerEmployee.employeeOptions}" varStatus="loop">
										  <c:set var="key" value="${user.id}-${date}"/>
										 
										  <td id="${date}##${user.id}"
										  <%if( disableDate == false) {%> style="height : 40px;width : 300px; background-color :  #ffffcc; cursor:hand;" <%}%>
										  <%if( disableDate == true) {%> style="height : 40px;width : 300px; background-color :  white; cursor:hand;" <%}%>
										    onclick="editAgenda('${ctx}/showAgendaPage.htm?year=${viewActivityPerWeekPerEmployee.year}&week=<%=weekNumber%>&employeeId=${user.id}')">
										 
										  <c:forEach var="event" items="${viewActivityPerWeekPerEmployee.activities[key]}" varStatus="loop">
											<t:tooltip>  
											   <t:text><em>
												<!-- a href="${ctx}/showAgendaPage.htm?year=${viewActivityPerWeekPerEmployee.year}&week=<!--%=weekNumber%>&employeeId=${user.id}" alt="" title="">
													<span style=" color: purple; font-size:xx-small">
														<i>${fn:substring(event.type,0,7)}</i></span>																	
												</a-->
											</em></t:text>
											  
											<t:help width="150" >											
												<!--  font color="STEELBLUE"><strong>Employee :</strong></font> ${event.employee.lastName}<BR/>
											   <font color="STEELBLUE"><strong>Mission :</strong></font> ${event.type}<BR/>
											   <font color="STEELBLUE"><strong>Année :</strong></font> ${event.year}<BR/>
											   <font color="STEELBLUE"><strong>Mois : </strong></font>${event.monthAsString}<BR/>
											   <font color="STEELBLUE"><strong>Jour : </strong></font><!--%=currentDateFormated%><BR/>
											   <font color="STEELBLUE"><strong>Heures tot : </strong></font>${ ( (event.endHour - event.startHour) * 0.25)} h<BR/-->
											</t:help> 
										 </t:tooltip>
										  	 
										  	
										  </c:forEach>
										  </td>
										 
									</c:forEach>
									
							</tr>
							<c:set var="row" value="${row + 1}"/>  
						<!--%}%-->
						
						<!--%}%-->
						
					</c:forEach>

					
					
		
					</tbody>
		</table>
 </form>
</div>




  <!-- END ADDED PART -->   

  <div id="addDialog" class="yui-pe-content">
	<div class="hd">
        <span id="formTitle2" style="color:blue;">Configure screen...</span>
    </div>

	<div class="bd">
	
	<form name="listForm" action="${ctx}/showAnnualPlanningPage.htm" method="post" >
		<input type="hidden" name="budgetId" />
    	<fieldset > 
						<dl>
							<label for="year"><span style="color:#039;"><strong>Exercise</strong> </span></label>
							<select style="width:120px; font:10px Verdana, sans-serif;margin-right:5pt;" name="year" >
								<c:forEach var="y" items="${viewActivityPerWeekPerEmployee.yearOptions}">
											<option value="${y.id}"<c:if test='${viewActivityPerWeekPerEmployee.year==y.id}'> selected</c:if>>${y.name}</option>
								</c:forEach>
							</select>
						 </dl>
						 <dl>
							<label for="month"><span style="color:#039;"><strong>Month</strong> </span></label>
							<select style="width:120px; font:10px Verdana, sans-serif;margin-left:12pt;" name="month" >
									<!--option value="0" <c:if test='${viewActivityPerWeekPerEmployee.month=="0"}'> selected</c:if>>Janvier</option>
									<option value="1" <c:if test='${viewActivityPerWeekPerEmployee.month=="1"}'> selected</c:if>>Fevrier</option>
									<option value="2" <c:if test='${viewActivityPerWeekPerEmployee.month=="2"}'> selected</c:if>>Mars</option>
									<option value="3" <c:if test='${viewActivityPerWeekPerEmployee.month=="3"}'> selected</c:if>>Avril</option>
									<option value="4" <c:if test='${viewActivityPerWeekPerEmployee.month=="4"}'> selected</c:if>>Mai</option>
									<option value="5" <c:if test='${viewActivityPerWeekPerEmployee.month=="5"}'> selected</c:if>>Juin</option>
									<option value="6" <c:if test='${viewActivityPerWeekPerEmployee.month=="6"}'> selected</c:if>>Juillet</option>
									<option value="7" <c:if test='${viewActivityPerWeekPerEmployee.month=="7"}'> selected</c:if>>Aout</option>
									<option value="8" <c:if test='${viewActivityPerWeekPerEmployee.month=="8"}'> selected</c:if>>Septembre</option>
									<option value="9" <c:if test='${viewActivityPerWeekPerEmployee.month=="9"}'> selected</c:if>>Octobre</option>
									<option value="10" <c:if test='${viewActivityPerWeekPerEmployee.month=="10"}'> selected</c:if>>Novembre</option>
									<option value="11" <c:if test='${viewActivityPerWeekPerEmployee.month=="11"}'> selected</c:if>>Decembre</option-->
							</select>
						 </dl>
						 <dl>
							<label for="month"><span style="color:#039;"><strong>Profile</strong></span></label>
							<select style="width:120px; font:10px Verdana, sans-serif;margin-left:12pt;" name="role">
							   <option value="All">Any</option>
								<c:forEach var="r" items="${viewActivityPerWeekPerEmployee.roles}">
									<option value="${r.name}"<c:if test='${viewActivityPerWeekPerEmployee.roleName==r.name}'> selected</c:if>>${r.name}</option>
								</c:forEach>
							</select>   
						 </dl>

						 <dl>
							<label for="month"><span style="color:#039;"><strong>Between</strong></span></label>
							<select style="width:60px; font:10px Verdana, sans-serif;margin-right:5pt;" name="startDateNumber">
							   <!--c:forEach var="date1" begin="1" end="<!--%=maxDateInteger%>">								
									<option value="${date1}" <c:if test='${viewActivityPerWeekPerEmployee.startDateNumber==date1}'> selected</c:if>>${date1}</option>
								</c:forEach-->
							</select>
							&nbsp;<span style="color:#039;"><strong>And</strong></span>&nbsp;
							<select style="width:60px; font:10px Verdana, sans-serif;margin-right:5pt;" name="endDateNumber">
							   <!--c:forEach var="date1" begin="1" end="<!--%=maxDateInteger%>">								
									<option value="${date1}" <c:if test='${viewActivityPerWeekPerEmployee.endDateNumber==date1}'> selected</c:if>>${date1}</option>
								</c:forEach-->
							</select>
						 </dl>
						 
    </fieldset>

    
   
   
</form>

</div>
</div>
