<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions"%>
<%
request.setAttribute("ctx", request.getContextPath()); 
%>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html><head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link href="css/odbb-skin.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="expires" content="-1">

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

<title>Planning Workshop </title>
	
	<script type="text/javascript">
	function comeBackToHomePage() {
		var url ="${ctx}/homePage.htm";
		window.location = url;
		
	}

	function exportExcel(){
		var url ="${ctx}/showPlanningExcelViewPage.htm";
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
		/*padding: 3px 0;*/
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
		/*padding: 3px 0.5em;
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


</head>


<body class="yui-skin-sam">
   
			<div id="ODBB_HEADER"> 
				<a href="${ctx}/homePage.htm"><img src="images/bannieres/ban.base1.jpg" alt="IAMS" border="0"></a>
			</div>
			<div  style="background-color: rgb(248, 246, 233);"> 

			<!-- START ADDED PART -->   

			<!-- START OF MENU PART --> 
			<%--jsp:include page="horizontal_menu.jsp"/--%>
			<!-- END OF MENU PART -->

			

			<div style="border: 1px solid  #0066aa; text-align:center;  font: bold 11px Verdana, sans-serif;">
				<form   name="planningReportForm"  method="post" action="${ctx}/showPlanningPage.htm" >
					
					<span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Exercise</strong> 
							<select style="font:10px Verdana, sans-serif;margin-right:10pt;"  name="planning_year">
								<c:forEach var="y" items="${viewPlanning.yearOptions}">
								<option value="${y.id}" <c:if test='${viewPlanning.param.year==y.id}'> selected</c:if> >${y.name}</option>
								</c:forEach>
							</select> 
					</span>			
					
					<span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Manager</strong> 
						<select style="font:10px Verdana, sans-serif;margin-right:10pt;" name="planning_manager">
						  <option value="-1">Any</option>
						  <c:forEach var="y" items="${viewPlanning.managerOptions}">
							<option value="${y.id}"<c:if test='${viewPlanning.param.manager==y.id}'> selected</c:if>>${y.name}</option>
						  </c:forEach>
						</select> 
						</span>	
					
					<span style="font:12px Verdana, sans-serif;margin-right:10pt;"><input  style="font:12px Verdana, sans-serif;margin-right:10pt;" type="submit" class="button120" value="Search" /></span>	
					
				</form>
				
			</div>
			<%--ul id="navlist">
					<input style="font:10px Verdana, sans-serif;margin-right:10pt;" type="button" class="button120" value="Home Page" onclick="comeBackToHomePage()"/>
					<input style="font:10px Verdana, sans-serif;margin-right:10pt;" type="button" class="button120" value="Export to excel" onclick="javascript:exportExcel();" >
			</ul--%>

			
	
	
			<div style="border-bottom: 1px solid gray; background-color: white;">
			        <table id="ver-zebra" width="100%" cellspacing="0" >
			        	<caption><span style="color:orange;">Planning annuel missions </span></caption>
									
									<thead>
										<tr class="odd">
											<th scope="col" >&nbsp;</th>
											<th scope="col" >Client</th>
											<th scope="col" style="width : 10;">Man</th>
											<th scope="col" style="width : 10;">Jours</th>
											<th scope="col" style="width : 15;">Debut</th>
											<th scope="col" style="width : 15;">Fin</th>
											<th scope="col" style="width : 10;">Janv</th>
											<th scope="col" style="width : 10;">Fevr</th>
											<th scope="col" style="width : 10;">Mars</th>
											<th scope="col" style="width : 10;">Avril</th>
											<th scope="col" style="width : 10;">Mai</th>
											<th scope="col" style="width : 10;">Juin</th>
											<th scope="col" style="width : 10;">Juil</th>
											<th scope="col" style="width : 10;">Aout</th>
											<th scope="col" style="width : 10;">Sept</th>
											<th scope="col" style="width : 10;">Oct</th>
											<th scope="col" style="width : 10;">Nov</th>
											<th scope="col" style="width : 10;">Dec</th>
											<%--th scope="col" style="width : 10;">Det</th--%>
										</tr>
									</thead>
									<tbody>
										<c:set var="row" value="0"/>
										 <c:forEach var="item" items="${viewPlanning.missions}" varStatus="loop">

													<c:choose>
														<c:when test='${row % 2 eq 0 }'>
															<c:set var="color" value="#6F6FFF" />
														</c:when>
														<c:otherwise>
														 <c:set var="color" value="#AA00AA" />
														</c:otherwise>
													</c:choose>

											<tr  onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">

												<td style="background: #eff2ff;">${loop.index + 1}</td>
												<td  align="left"><span style=" color: purple;"><i>
												<a href="#" onclick="resetForm(${item.id},'${item.customerName}')">${item.customerName}</i></a>
												</span></td>
												<td><span style="width : 10;">${item.manager}</span></td>
												<td><span style="width : 10;">${item.days}</td>
												<td><span id="startDate_${item.id}" class="startdate_cell" style=" color:blue;">
													<c:choose>
														<c:when test='${item.startDate != null }'>
														 	<fmt:formatDate value="${item.startDate}" pattern="dd-MMM"/>
														</c:when>
														<c:otherwise>
														 &nbsp;
														</c:otherwise>
													</c:choose>
												
												</span></td>
												<td><span id="dueDate_${item.id}" class="duedate_cell" style=" color:blue;">
													<c:choose>
														<c:when test='${item.dueDate != null }'>
														 	<fmt:formatDate value="${item.dueDate}" pattern="dd-MMM"/>
														</c:when>
														<c:otherwise>
														 &nbsp;
														</c:otherwise>
													</c:choose>
												</span></td>
												
												<c:forEach var="entry" begin="1" end="12">

													
													<c:choose>
														<c:when test='${entry >= item.startMonth && entry <= item.endMonth }'>
														 <td  style="width : 10;background: ${color};border-right: 0px;border-left: 0px;">&nbsp;</td>
														</c:when>
														<c:otherwise>
														 <td  style="width : 10;">&nbsp;</td>
														</c:otherwise>
													</c:choose>
												</c:forEach>

												<%--td>
													<a href="${ctx}/showMissionPlanningPage.htm?id=${item.id}"><span style=" color: purple;"><i>edit</i>
												</span></a>	
												</td--%>
											</tr>
											<c:set var="row" value="${row + 1}"/>
										  </c:forEach>

										  
									</tbody>
								</table>
			</div>
		  <jsp:include page="footer.jsp"/>

			



<div id="addDialog">
					<div class="hd">
						<span id="formTitle" style="color:#039;">Planifier mission</span>
					</div>
					<div class="bd">
						<form name="addInterventionForm" action="${ctx}/scheduleMissionPage.htm" method="post" >
									<input type="hidden" name="year" value="${viewPlanning.param.year}"/>
									<input type="hidden" name="id" />
									<div id="cal1Container">
										<br/>
										<p><label for="in"><span style="color:#039;"><strong>Start date:</strong></span></label><input type="text" name="startdate" id="startdate"></p>
										<p><label for="out"><span style="color:#039;"><strong>End date:</strong></span></label><input type="text" name="enddate" id="enddate"></p>
									</div>
									<br/>
						</form>
				</div>
</div>

</body></html>

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

        
        _dateString : function(d) {
            var a = [];
            a[this.cfg.getProperty(IntervalCalendar._DEFAULT_CONFIG.MDY_MONTH_POSITION.key)-1] = (d.getMonth() + 1);
            a[this.cfg.getProperty(IntervalCalendar._DEFAULT_CONFIG.MDY_DAY_POSITION.key)-1] = d.getDate();
            a[this.cfg.getProperty(IntervalCalendar._DEFAULT_CONFIG.MDY_YEAR_POSITION.key)-1] = d.getFullYear();
            var s = this.cfg.getProperty(IntervalCalendar._DEFAULT_CONFIG.DATE_FIELD_DELIMITER.key);
            return a.join(s);
        },

        
        _dateIntervalString : function(l, u) {
            var s = this.cfg.getProperty(IntervalCalendar._DEFAULT_CONFIG.DATE_RANGE_DELIMITER.key);
            return (this._dateString(l)
                    + s + this._dateString(u));
        },

       
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

       
        setInterval : function(d1, d2) {
            // Determine lower and upper dates
            var b = (d1 <= d2);
            var l = b ? d1 : d2;
            var u = b ? d2 : d1;
            // Update configuration
            this.cfg.setProperty('selected', this._dateIntervalString(l, u), false);
            this._iState = 2;
        },

       
        resetInterval : function() {
            // Update configuration
            this.cfg.setProperty('selected', [], false);
            this._iState = 0;
        },

       
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

    var cal = new YAHOO.example.calendar.IntervalCalendar("cal1Container");
																		

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
																{ width : "450px",
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

function resetForm(id,customer){
	initCalendar();
	document.getElementById('formTitle').innerHTML = "Planifier mission " + customer;
	document.forms["addInterventionForm"].elements["id"].value = id;
    YAHOO.example.container.dialog1.render();
    YAHOO.example.container.dialog1.show();
}





</script>




