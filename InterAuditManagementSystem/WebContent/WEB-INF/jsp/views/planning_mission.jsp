<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions"%>
<%
request.setAttribute("ctx", request.getContextPath()); 
%>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html><head>

<link href="css/odbb-skin.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${ctx}/script/build/fonts/fonts-min.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/script/build/button/assets/skins/sam/button.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/script/build/container/assets/skins/sam/container.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/script/build/fonts/fonts-min.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/script/build/autocomplete/assets/skins/sam/autocomplete.css" />

<script type="text/javascript" src="${ctx}/script/build/utilities/utilities.js"></script>
<script type="text/javascript" src="${ctx}/script/build/button/button-min.js"></script>
<script type="text/javascript" src="${ctx}/script/build/container/container-min.js"></script>
<script type="text/javascript" src="${ctx}/script/build/yahoo/yahoo-min.js"></script>
<script type="text/javascript" src="${ctx}/script/build/event/event-min.js"></script>
<script type="text/javascript" src="${ctx}/script/build/connection/connection-min.js"></script>
<script type="text/javascript" src="${ctx}/script/build/json/json-min.js"></script>

<link type="text/css" href="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/themes/base/ui.all.css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/jquery-1.3.2.js"></script>
<script type="text/javascript" src="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/ui/ui.core.js"></script>
<link type="text/css" href="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/demos.css" rel="stylesheet" />

<script type="text/javascript" src="${ctx}/script/build/yahoo-dom-event/yahoo-dom-event.js"></script>
<script type="text/javascript" src="${ctx}/script/build/animation/animation-min.js"></script>
<script type="text/javascript" src="${ctx}/script/datasource/datasource-min.js"></script>
<script type="text/javascript" src="${ctx}/script/build/autocomplete/autocomplete-min.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/script/build/fonts/fonts-min.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/script/build/calendar/assets/skins/sam/calendar.css" />
<script type="text/javascript" src="${ctx}/script/build/yahoo-dom-event/yahoo-dom-event.js"></script>
<script type="text/javascript" src="${ctx}/script/build/calendar/calendar-min.js"></script>

<title>Planning mission </title>
	
	
<script type="text/javascript">
	function comeBackToPlanning() {
		var url ="${ctx}/showPlanningPage.htm";
		window.location = url;
		
	}

	function exportExcel(id){
		var url ="${ctx}/showPlanningMissionExcelViewPage.htm?id=" + id;
		window.location = url;
	}
</script>
	
	<style>
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

#navlist {
		padding: 3px 0;
		margin-left: 0;
		margin-bottom: 0;
		border-bottom: 1px solid #778;
		/*border-bottom: 2px solid #778;*/
		font: bold 12px Verdana, sans-serif;
	}

	#navlist li {
		list-style: none;
		margin: 0;
		display: inline;
	}

	#navlist li a {
		padding: 3px 0.5em;
		/*margin-left: 3px;*/
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


</head>


<body class="yui-skin-sam">
   
			<div id="ODBB_HEADER"> 
				<a href="${ctx}/homePage.htm"><img src="images/bannieres/ban.base1.jpg" alt="IAMS" border="0"></a>
			</div>
			<div  style="background-color: rgb(248, 246, 233);"> 

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

			<div  style="border: 1px solid  #0066aa; text-align:center; padding-top:1pt; font: bold 11px Verdana, sans-serif;">
				<br/>
				<div>
				<span  class="galphabet_center" >PLANNING MISSION :  <span style ="color:blue;">${viewMissionDetails.missionData.customerName}</span></span>	
				<span class="galphabet_center" style ="color:red;"> ${viewMissionDetails.missionData.type} </span>		
				<span class="galphabet_center">FROM : <span style ="color:blue;">${viewMissionDetails.missionData.startDate}</span> TO 
				<span style ="color:blue;">${viewMissionDetails.missionData.dueDate}</span></span>
				</div>
				<br/>
			</div>

			<ul id="navlist">
				<li>
					<input style="font:10px Verdana, sans-serif;margin-right:10pt;" type="button" class="button120" value="Planning" onclick="comeBackToPlanning()"/>

					<input style="font:10px Verdana, sans-serif;margin-right:10pt;" type="button" class="button120" value="Add task" id="show2" onclick="resetForm()"/>
				
					<input style="font:10px Verdana, sans-serif;margin-right:10pt;" type="button" class="button120" value="Export to excel" onclick="javascript:exportExcel(${viewMissionDetails.missionData.id});" >
					
				</li>
			</ul>
	
			<div style="border-bottom: 1px solid gray; background-color: white;">
			        <table id="ver-zebra" width="100%" cellspacing="0" >
			        	<caption><span style="color:purple;">${viewMissionDetails.missionData.customerName}</span></caption>
									
									<thead>
										<tr class="odd">
												<th scope="col" >&nbsp;</th>
												<th scope="col">Task</th>
												<th scope="col">Employee</th>
												<th scope="col">Profil</th>
												<th scope="col">Hours</th>
												<th scope="col">Start date</th>
												<th scope="col">End date</th>
												<th scope="col">Expected cost</th>
												<th scope="col">Actual cost</th>
												<th scope="col">Published</th>
												<th scope="col">Status</th>
												<th scope="col">Actions</th>
										</tr>
									</thead>
									<tbody>
											<c:set var="totalHours" value="0"/>
											<c:set var="totalCosts" value="0"/>
											<c:set var="actualTotalCosts" value="0"/>
										<c:forEach var="item" items="${viewMissionDetails.mission.interventions}" varStatus="loop">
											<tr  onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
												<td style="background: #eff2ff;">${loop.index + 1}</td>
										 		<td align="left"><span style="color:purple;">${fn:substring(item.task.name, 0, 30)}</span></td>
												<td>${item.employee.lastName}</td>
												<td>${item.profile.name}</td>
												<td>${item.totalEstimatedHours} h</td>
												<td>
												<fmt:formatDate value="${item.startDate}" pattern="dd-MMM"/>
												
												</td>
												<td><fmt:formatDate value="${item.endDate}" pattern="dd-MMM"/></td>
												<td><span style="color:green;"><i>
										
														<fmt:formatNumber type="currency" currencySymbol="&euro;">
														${item.expectedCost}
														</fmt:formatNumber></i></span>
												</td>
												<td><span style="color:green;"><i>
										
														<fmt:formatNumber type="currency" currencySymbol="&euro;">
														${item.actualCost}
														</fmt:formatNumber></i></span>
												</td>
												<td>true</td>
												<td><span style="color:red;">${item.status}</span></td>
												<td>
												
												<a href="#">
												<img src="images/Table-Edit.png" border="0" alt="Edit task" onclick="editTask(${item.id})"/></a>
												&nbsp;
												<a href="#">
												<img src="images/Table-Delete.png" border="0" alt="Delete task" onclick="deleteTask(${item.id},${viewMissionDetails.missionId})"/></a>
												
												</td>
											</tr>
										
										<c:set var="totalHours" value="${totalHours + item.totalEstimatedHours}"/>
										<c:set var="totalCosts" value="${totalCosts + (item.expectedCost)}"/>
										<c:set var="actualTotalCosts" value="${actualTotalCosts + (item.actualCost)}"/>

										
									  </c:forEach>
											

											<tr  onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
												<td align="center" colspan="4">Totaux</td>
												<td>${totalHours} h</td>
												<td align="center" colspan="2"></td>
												<td><span style="color:green;"><i>
														<fmt:formatNumber type="currency" currencySymbol="&euro;">
														${totalCosts}
														</fmt:formatNumber></i></span></td>
												<td><span style="color:green;"><i>
														<fmt:formatNumber type="currency" currencySymbol="&euro;">
														${actualTotalCosts}
														</fmt:formatNumber></i></span></td>
												<td align="center" colspan="3"></td>
											</tr>
											
										</tbody>
								</table>
			</div>
		  <jsp:include page="footer.jsp"/>

			



<div id="addDialog">
		<div class="hd">
			<span id="formTitle" style="color:#039;">Add intervention</span>
		</div>

		<div class="bd">
		<form name="addInterventionForm" action="${ctx}/handleMissionInterventionPage.htm" method="post" >
		<input type="hidden" name="taskId" />
		<input type="hidden" name="id" value="${viewMissionDetails.missionId}"/>
		
		<fieldset>
		
		 <dl>
        		<label for="reason"><span style="color:#039;"><strong>Task:</strong></span></label>
				<select name="task" style="width:250px;">
					<c:forEach var="y" items="${viewMissionDetails.taskOptions}">
						<option value="${y.id}">${y.name}</option>
					</c:forEach>
				</select>
				<!--img src="images/_ast.gif" border="0" alt="mandatory"/-->
					&nbsp;&nbsp;
				<label for="password"><span style="color:#039;"><strong>Profil:</strong></span></label>
				<select name="profile"  style="width:250px;">
						<c:forEach var="y" items="${viewMissionDetails.profilOptions}">
							<option value="${y.id}">${y.name}</option>
						</c:forEach>
				</select>
				<!--img src="images/_ast.gif" border="0" alt="mandatory"/-->
		</dl>
		<hr/>
		
			<div id="cal1Container">
				<p><label for="in"><span style="color:#039;"><strong>Start date:</strong></span></label><input type="text" name="startdate" id="startdate"></p>
				<p><label for="out"><span style="color:#039;"><strong>End date:</strong></span></label><input type="text" name="enddate" id="enddate"></p>
			</div>
		
		
		
    </fieldset>

    
    <fieldset>
		<dl>
				<label for="currency"><span style="color:#039;"><strong>Employee:</strong></span></label>
				<select name="emloyee"  style="width:200px;">
						<c:forEach var="y" items="${viewMissionDetails.employeeOptions}">
							<option value="${y.id}">${y.name}</option>
						</c:forEach>
				</select>
				<br/>
			 </dl>
			 
			  <dl>
        	<label for="priority"><span style="color:#039;"><strong>Priority:</strong></span></label>
			<select style="width:100px;" size="1" name="priority" id="priority">
			  <option value="CRITICAL">Critical</option>
			  <option value="MAJOR">Major</option>
			  <option value="NORMAL" selected >Normal</option>
			  <option value="MINOR">Minor</option>
			</select>
			 </dl>
			  <dl>
			<label for="owner"><span style="color:#039;"><strong>Hours:</strong></span></label>
            <select style="width:100px;" size="1" name="hours" id="hours">
									  <option value="60" selected>1h</option>
									  <option value="90">1h30</option>
									  <option value="120">2h</option>
									  <option value="150">2h30</option>
									  <option value="180">3h</option>
									  <option value="210">3h30</option>
									  <option value="240">4h</option>
									  <option value="270">4h30</option>
									  <option value="300">5h</option>
									  <option value="330">5h30</option>
									  <option value="360">6h</option>
									  <option value="390">6h30</option>
									  <option value="420">7h</option>
									  <option value="450">7h30</option>
									  <option value="480">8h</option>
									  <option value="510">8h30</option>
									  <option value="540">9h</option>
									  <option value="570">9h30</option>
									  <option value="600">10h</option>
            	</select>
        </dl>
	
    </fieldset>
   
</form>
	

</div>

</div>

</body></html>

<script>
	  //On suppose que la date entrée a été validée auparavant
	  //au format dd/mm/yyyy
	  function getDate(strDate){	  
	    day = strDate.substring(0,2);
		month = strDate.substring(3,5);
		year = strDate.substring(6,10);
		d = new Date();
		d.setDate(day);
		d.setMonth(month);
		d.setFullYear(year); 
		return d;  
	  }
	  
	  //Retorune:
	  //   0 si date_1=date_2
  	  //   1 si date_1>date_2
	  //  -1 si date_1<date_2	  
	  function compare(date_1, date_2){
	    diff = date_1.getTime()-date_2.getTime();
	    return (diff==0?diff:diff/Math.abs(diff));
	  }
</script>


<script>
YAHOO.namespace("example.container");
function init() {
	// Define various event handlers for Dialog
	var handleSubmit = function() {
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
																{ width : "550px",
																  draggable: true,
																  fixedcenter : true,
																  visible : false,
																  modal: true,
																  constraintoviewport : false,
																  buttons : [ { text:"Submit", handler:handleSubmit, isDefault:true },
																	      	  { text:"Cancel", handler:handleCancel } ]
																}
							);
	YAHOO.example.container.dialog1.cfg.queueProperty("postmethod","form");


	// Validate the entries in the form 
	YAHOO.example.container.dialog1.validate = function() {
		var data = this.getData();
		if (data.task == "") {
			alert("Please enter a task for the intervention");
			return false;
		} 
			
		if (data.profile == "" ){
			alert("Please enter a profile for the intervention");
			return false;
		} 

		if (data.emloyee == "" ){
			alert("Please  provide an employee for the intervention.");
			return false;
		} 

		if (data.contract == "") {
			alert("Please enter contract");
			return false;
		} 


		if (data.startdate == "") {
			alert("Please enter a starting date for the intervention");
			return false;
		}

		if (data.enddate == "") {
			alert("Please enter an ending date for the intervention");
			return false;
		}
		
		if (data.hours == "") {
			alert("Please enter the hours  for the intervention");
			return false;
		}
		
		if (data.priority == "") {
			alert("Please enter the priority  for the intervention");
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

function resetForm(){

	initCalendar();
    document.forms["addInterventionForm"].elements["taskId"].value = "";
    document.forms["addInterventionForm"].elements["task"].value = "";
    document.forms["addInterventionForm"].elements["profile"].value = "";
    document.forms["addInterventionForm"].elements["emloyee"].value = "";
    document.forms["addInterventionForm"].elements["startdate"].value = "";
	document.forms["addInterventionForm"].elements["enddate"].value = "";
    document.forms["addInterventionForm"].elements["hours"].value = "";
	document.forms["addInterventionForm"].elements["priority"].value = "";
	

    document.getElementById('formTitle').innerHTML = 'New intervention';
    YAHOO.example.container.dialog1.render();
    YAHOO.example.container.dialog1.show();
}

var handleSuccess = function(o){
    var form = document.forms["addInterventionForm"];
    if(o.responseText !== undefined){
        // Use the JSON Utility to parse the data returned from the server   
         try {   
            var object = YAHOO.lang.JSON.parse(o.responseText);

            form.elements["taskId"].value = object.taskId;
            form.elements["task"].value = object.task;
            form.elements["profile"].value = object.profile;
            form.elements["emloyee"].value = object.emloyee;
			form.elements["startdate"].value = object.startdate;
			form.elements["enddate"].value = object.enddate;
			form.elements["hours"].value = object.hours;
			form.elements["priority"].value = object.priority;
			
            
            //no password as it is only sent from client to browser.
            document.getElementById('formTitle').innerHTML = 'Update intervention \'' + object.taskName + '\'';
         }
         catch (x) {   
             alert("JSON Parse failed in parsing intervention!");
             return;
         } 
		initCalendar();
        YAHOO.example.container.dialog1.render();
        YAHOO.example.container.dialog1.show();		
    }
};

var handleFailure = function(o){
    if(o.responseText !== undefined){
        div.innerHTML = "<li>Transaction id: " + o.tId + "<\/li>";
        div.innerHTML += "<li>HTTP status: " + o.status + "<\/li>";
        div.innerHTML += "<li>Status code message: " + o.statusText + "<\/li>";
    }
};

var callback =
{
  success:handleSuccess,
  failure:handleFailure
};

var sEditTaskUrl = "${ctx}/editMissionInterventionPage.htm";

function editTask(taskId){
    var postData = "taskId="+taskId;
    var request = YAHOO.util.Connect.asyncRequest('POST', sEditTaskUrl, callback, postData);
}

function deleteTask(id,missionId)
  {
    if (confirm("Do you really want to delete task with id "+id))
    {
      window.location="${ctx}/deleteMissionInterventionPage.htm?taskId="+id+"&id="+missionId;
    }
  }

</script>


<script type="text/javascript">

(function() {

    function IntervalCalendar(container, cfg) {
        /**
        * The interval state, which counts the number of interval endpoints that have
        * been selected (0 to 2).
        * 
        * @private
        * @type Number
        */
        this._iState = 0;

        // Must be a multi-select CalendarGroup
        cfg = cfg || {};
        cfg.multi_select = true;

        // Call parent constructor
        IntervalCalendar.superclass.constructor.call(this, container, cfg);

        // Subscribe internal event handlers
        this.beforeSelectEvent.subscribe(this._intervalOnBeforeSelect, this, true);
        this.selectEvent.subscribe(this._intervalOnSelect, this, true);
        this.beforeDeselectEvent.subscribe(this._intervalOnBeforeDeselect, this, true);
        this.deselectEvent.subscribe(this._intervalOnDeselect, this, true);
    }

    /**
    * Default configuration parameters.
    * 
    * @property IntervalCalendar._DEFAULT_CONFIG
    * @final
    * @static
    * @private
    * @type Object
    */
    IntervalCalendar._DEFAULT_CONFIG = YAHOO.widget.CalendarGroup._DEFAULT_CONFIG;

    YAHOO.lang.extend(IntervalCalendar, YAHOO.widget.CalendarGroup, {

        /**
        * Returns a string representation of a date which takes into account
        * relevant localization settings and is suitable for use with
        * YAHOO.widget.CalendarGroup and YAHOO.widget.Calendar methods.
        * 
        * @method _dateString
        * @private
        * @param {Date} d The JavaScript Date object of which to obtain a string representation.
        * @return {String} The string representation of the JavaScript Date object.
        */
        _dateString : function(d) {
            var a = [];
            a[this.cfg.getProperty(IntervalCalendar._DEFAULT_CONFIG.MDY_MONTH_POSITION.key)-1] = (d.getMonth() + 1);
            a[this.cfg.getProperty(IntervalCalendar._DEFAULT_CONFIG.MDY_DAY_POSITION.key)-1] = d.getDate();
            a[this.cfg.getProperty(IntervalCalendar._DEFAULT_CONFIG.MDY_YEAR_POSITION.key)-1] = d.getFullYear();
            var s = this.cfg.getProperty(IntervalCalendar._DEFAULT_CONFIG.DATE_FIELD_DELIMITER.key);
            return a.join(s);
        },

        /**
        * Given a lower and upper date, returns a string representing the interval
        * of dates between and including them, which takes into account relevant
        * localization settings and is suitable for use with
        * YAHOO.widget.CalendarGroup and YAHOO.widget.Calendar methods.
        * <p>
        * <b>Note:</b> No internal checking is done to ensure that the lower date
        * is in fact less than or equal to the upper date.
        * </p>
        * 
        * @method _dateIntervalString
        * @private
        * @param {Date} l The lower date of the interval, as a JavaScript Date object.
        * @param {Date} u The upper date of the interval, as a JavaScript Date object.
        * @return {String} The string representing the interval of dates between and
        *                   including the lower and upper dates.
        */
        _dateIntervalString : function(l, u) {
            var s = this.cfg.getProperty(IntervalCalendar._DEFAULT_CONFIG.DATE_RANGE_DELIMITER.key);
            return (this._dateString(l)
                    + s + this._dateString(u));
        },

        /**
        * Returns the lower and upper dates of the currently selected interval, if an
        * interval is selected.
        * 
        * @method getInterval
        * @return {Array} An empty array if no interval is selected; otherwise an array
        *                 consisting of two JavaScript Date objects, the first being the
        *                 lower date of the interval and the second being the upper date.
        */
        getInterval : function() {
            // Get selected dates
            var dates = this.getSelectedDates();
            if(dates.length > 0) {
                // Return lower and upper date in array
                var l = dates[0];
                var u = dates[dates.length - 1];
                return [l, u];
            }
            else {
                // No dates selected, return empty array
                return [];
            }
        },

        /**
        * Sets the currently selected interval by specifying the lower and upper
        * dates of the interval (in either order).
        * <p>
        * <b>Note:</b> The render method must be called after setting the interval
        * for any changes to be seen.
        * </p>
        * 
        * @method setInterval
        * @param {Date} d1 A JavaScript Date object.
        * @param {Date} d2 A JavaScript Date object.
        */
        setInterval : function(d1, d2) {
            // Determine lower and upper dates
            var b = (d1 <= d2);
            var l = b ? d1 : d2;
            var u = b ? d2 : d1;
            // Update configuration
            this.cfg.setProperty('selected', this._dateIntervalString(l, u), false);
            this._iState = 2;
        },

        /**
        * Resets the currently selected interval.
        * <p>
        * <b>Note:</b> The render method must be called after resetting the interval
        * for any changes to be seen.
        * </p>
        * 
        * @method resetInterval
        */
        resetInterval : function() {
            // Update configuration
            this.cfg.setProperty('selected', [], false);
            this._iState = 0;
        },

        /**
        * Handles beforeSelect event.
        * 
        * @method _intervalOnBeforeSelect
        * @private
        */
        _intervalOnBeforeSelect : function(t,a,o) {
            // Update interval state
            this._iState = (this._iState + 1) % 3;
            if(this._iState == 0) {
                // If starting over with upcoming selection, first deselect all
                this.deselectAll();
                this._iState++;
            }
        },

        /**
        * Handles selectEvent event.
        * 
        * @method _intervalOnSelect
        * @private
        */
        _intervalOnSelect : function(t,a,o) {
            // Get selected dates
            var dates = this.getSelectedDates();
            if(dates.length > 1) {
                /* If more than one date is selected, ensure that the entire interval
                    between and including them is selected */
                var l = dates[0];
                var u = dates[dates.length - 1];
                this.cfg.setProperty('selected', this._dateIntervalString(l, u), false);
            }
            // Render changes
            this.render();
        },

        /**
        * Handles beforeDeselect event.
        * 
        * @method _intervalOnBeforeDeselect
        * @private
        */
        _intervalOnBeforeDeselect : function(t,a,o) {
            if(this._iState != 0) {
                /* If part of an interval is already selected, then swallow up
                    this event because it is superfluous (see _intervalOnDeselect) */
                return false;
            }
        },

        /**
        * Handles deselectEvent event.
        *
        * @method _intervalOnDeselect
        * @private
        */
        _intervalOnDeselect : function(t,a,o) {
            if(this._iState != 0) {
                // If part of an interval is already selected, then first deselect all
                this._iState = 0;
                this.deselectAll();

                // Get individual date deselected and page containing it
                var d = a[0][0];
                var date = YAHOO.widget.DateMath.getDate(d[0], d[1] - 1, d[2]);
                var page = this.getCalendarPage(date);
                if(page) {
                    // Now (re)select the individual date
                    page.beforeSelectEvent.fire();
                    this.cfg.setProperty('selected', this._dateString(date), false);
                    page.selectEvent.fire([d]);
                }
                // Swallow up since we called deselectAll above
                return false;
            }
        }
    });

    YAHOO.namespace("example.calendar");
    YAHOO.example.calendar.IntervalCalendar = IntervalCalendar;
})();


function initCalendar() {

    var inTxt = YAHOO.util.Dom.get("startdate"),
        outTxt = YAHOO.util.Dom.get("enddate"),
        inDate, outDate, interval;

	inTxt.value = "";
    outTxt.value = "";

    var cal = new YAHOO.example.calendar.IntervalCalendar("cal1Container", {pages:2,
																			mindate:"${minDate}",
																			maxdate: "${maxDate}",
																			pagedate:"${pageDate}"});

    cal.selectEvent.subscribe(function() {
        interval = this.getInterval();

        if (interval.length == 2) {
            inDate = interval[0];
            inTxt.value =  inDate.getDate() +  "/" + ( inDate.getMonth() + 1)  +  "/" + inDate.getFullYear();

            if (interval[0].getTime() != interval[1].getTime()) {
                outDate = interval[1];
                outTxt.value =  outDate.getDate() + "/"  + (outDate.getMonth() + 1)   + "/" + outDate.getFullYear();
            } else {
                outTxt.value = "";
            }
        }
    }, cal, true);
    
    cal.render();
}
</script>




