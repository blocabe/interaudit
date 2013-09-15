
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<%@ taglib uri="/tags/interaudit" prefix="interaudit" %>



<interaudit:noCache/>

<interaudit:notLoggedIn>
<jsp:forward page="${ctx}/login.htm" />
</interaudit:notLoggedIn>

<interaudit:loggedIn>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
	<tiles:insert page="/WEB-INF/jsp/views/layout.jsp" flush="true">
	<tiles:put name="content" value="list-activity-per-employee-per-week-content.jsp"/>
</tiles:insert>
</interaudit:loggedIn>



<script>
	YAHOO.namespace("example.container");
	// Define various event handlers for Dialog
	var handleSubmit = function() {
		//this.submit();	
			var url ="${ctx}/showAnnualPlanningPage.htm?year=" + ${viewActivityPerWeekPerEmployee.year} +"&startMonth="+${viewActivityPerWeekPerEmployee.startMonth}+"&usergroup=${param['usergroup']}";
			window.location = url;		
	};
	var handleCancel = function() {
		this.cancel();
	};

	

	// Instantiate the Dialog configure
	YAHOO.example.container.dialog2 = new YAHOO.widget.Dialog("addDialog",
																{ width : "1000px",
																  draggable: true,
																  fixedcenter : true,
																  visible : true,
																  modal: true,
																  constraintoviewport : true,																
																  buttons : [ 
																	      	  { text:"Close", handler:handleSubmit }
																			 /* { text:"Close", handler:handleSubmit, isDefault:true }*/]
																			  
																}
															);

	
	

	


var handleSuccess = function(o){
	 			var form = document.forms["editEventPlanningForm"];
        
	            if(o.responseText != undefined){
	                // Use the JSON Utility to parse the data returned from the server   
	                 try {   
	                    var myJSONObject = YAHOO.lang.JSON.parse(o.responseText);

						//var myJSONArrayDays = myJSONObject.days;
						//alert(myJSONObject.days.length);
						for(index=0; index < myJSONObject.days.length; index++)
							  {
								//alert("day : " +myJSONObject.days[index].day);
								form.elements["startdate"].options[index]=new Option(myJSONObject.days[index].day,myJSONObject.days[index].day);
								form.elements["enddate"].options[index]=new Option(myJSONObject.days[index].day,myJSONObject.days[index].day);
								
							  }

						//form.elements["myInput2"].value ='';
						form.elements["startdate"].selectedIndex =0;
						//alert(myJSONObject.days.length-1);
						form.elements["enddate"].selectedIndex = 4;

	                    form.elements["year"].value = myJSONObject.year;
	                    form.elements["idPlanning"].value = myJSONObject.idPlanning;
						form.elements["semaine"].value = myJSONObject.week;
						form.elements["employee"].value = myJSONObject.employeeId;
						
						 form.elements["week"].value = myJSONObject.week;
						 form.elements["user"].value = myJSONObject.user;

	        			var myDataSource = new YAHOO.util.DataSource(myJSONObject.events);
	        			myDataSource.responseType = YAHOO.util.DataSource.TYPE_JSARRAY;
	        			myDataSource.responseSchema = {
	        				fields: ["id","label",/*"date",*/"startDate","endDate","durationType","duration","realStartDate","realEndDate","totalHoursSpent", {key:"check", parser:YAHOO.util.DataSource.parseBoolean}]
	        			};

						var myCheckBoxFormatter = function(elCell, oRecord, oColumn, oData) 
						  { 
						   var SchoolId = oRecord._oData.id; 
						  // var CheckBoxID = "che_" + SchoolId;
						  var CheckBoxID = "che_";
						  // alert(CheckBoxID);
						   elCell.innerHTML = "<input type=\"checkbox\" name=\""+ CheckBoxID + "\" value=\"" +SchoolId+ "\"/>"; 
						  };
						  
						  var myColumnDefs = [	                    
	                   /* {key:"id"},*/
						  {key:"label",label:"Mission"},
						  {key:"startDate",label:"Pl.Debut"},
	        			{key:"endDate",label:"Pl.Fin"},
	        			
	        			/*{key:"date"},*/

	        			
	        			{key:"durationType",label:"Pl.Type"},
	        			{key:"duration",label:"Pl.Dur&eacute;e"},

	        			
	        			{key:"realStartDate",label:"Ts.Debut"},
                        {key:"realEndDate",label:"Ts.Fin"},
                        {key:"totalHoursSpent",label:"Ts.Heures"},
	        			
						{key:"sel", formatter:myCheckBoxFormatter}
	                ];

	        			var myDataTable = new YAHOO.widget.DataTable("containerEvents", myColumnDefs, myDataSource);
/*
						myDataTable.subscribe("checkboxClickEvent", function(oArgs){ 
							var elCheckbox = oArgs.target;   
				            var oRecord = this.getRecord(elCheckbox);
							oRecord.setData("check",elCheckbox.checked);  

							var recordset = myDataTable.getRecordSet();
							var totalRows = recordset.getLength();
							//alert("totalRows : " +totalRows);
							form.elements["selectedIds"].value = "";
							//alert("selectedIds : " +form.elements["selectedIds"].value);
							for(index=0; index < totalRows; index++)
							  {
								//alert("index : " +index);
								var record = myDataTable.getRecord(index); 
								//alert("record : " +record);
								var currentCheckbox = record.getData("check");
								//alert("currentCheckbox : " +currentCheckbox);
								if (currentCheckbox == true)
								{
								  if (form.elements["selectedIds"].value.length > 0) form.elements["selectedIds"].value += ",";
								  form.elements["selectedIds"].value += record.getData("id"); 
								}
							  }

							//alert("click on checbbox : " +elCheckbox.value +" "+elCheckbox.checked + " " +totalRows);
							alert("selectedIds : " +form.elements["selectedIds"].value);
						});   

	            		*/
	                    //no password as it is only sent from client to browser.
	                    document.getElementById('formTitle').innerHTML =   myJSONObject.title ;
	                    document.getElementById("textDiv").innerHTML = myJSONObject.error;
	                    document.getElementById('firstDateOfWeek').innerHTML =   myJSONObject.firstDateOfWeek ;
	                    document.getElementById('lastDateOfWeek').innerHTML =   myJSONObject.lastDateOfWeek ;
	                    
	        			
	        			
	                 }
	                 catch (x) {   
	                     alert("JSON Parse failed !");
	                     return;
	                 } 

	        		

	                YAHOO.example.container.dialog2.render();
	                YAHOO.example.container.dialog2.show();	
				}
	
};




var handleFailure = function(o){
    if(o.responseText !== undefined){
		/*
        div.innerHTML = "<li>Transaction id: " + o.tId + "<\/li>";
        div.innerHTML += "<li>HTTP status: " + o.status + "<\/li>";
        div.innerHTML += "<li>Status code message: " + o.statusText + "<\/li>";
		*/
    }
};

var callback =
{
  success:handleSuccess,
  failure:handleFailure
};



var sEditWeekPlanningItemUrl = "${ctx}/editEventPlanningPage.htm";

function editWeekPlanningItem(week,user,planningId){
	var form = document.forms["editEventPlanningForm"];
	var year = ${viewActivityPerWeekPerEmployee.year};
    var postData = "planningId="+planningId+"&week="+week+"&user="+user+"&year="+year;
    var request = YAHOO.util.Connect.asyncRequest('POST', sEditWeekPlanningItemUrl, callback, postData);
};



var sEditWeekPlanningForEmployeeItemUrl = "${ctx}/loadEmployeePlanningPage.htm";
function loadEmployeePlanning(employeeId,weekId,year){	
    var weekId = document.editEventPlanningForm.semaine.value;	  
	var employeeId =document.editEventPlanningForm.employee.value;
    var postData = "year=" + ${viewActivityPerWeekPerEmployee.year} +"&week="+weekId+"&employee="+employeeId;
    var request = YAHOO.util.Connect.asyncRequest('POST', sEditWeekPlanningForEmployeeItemUrl, callback, postData);
};

var sDeleteEventPlanningItemsPageUrl = "${ctx}/deleteEventPlanningItemsPage.htm";

function deleteEventPlanningItemsPage(){
	if(getCountCheckBoxes() == 0){
	 alert("Please select the entries to remove first...");
	}
	else{
		var answer = confirm("Do you really want to remove the selected entries?");
		if (answer){
			var form = document.forms["editEventPlanningForm"];
			var planningId = form.elements["idPlanning"].value;
			var year = form.elements["year"].value;
			var week = form.elements["week"].value;
		    var user = form.elements["user"].value;
			var checkboxes = getCheckBoxes();
			//alert(checkboxes);
			var postData = "idPlanning="+planningId+"&year="+year+"&week="+week+"&user="+user+checkboxes;
			//alert(postData);
			var request = YAHOO.util.Connect.asyncRequest('POST', sDeleteEventPlanningItemsPageUrl, callback, postData);
			unCheckDurationBoxes();
		}
	}
    
	
};


var saddSingleEventPlanningItemsPageUrl = "${ctx}/addSingleEventsPage.htm";
function addSingleEventPlanningItemsPage(){	
	var form = document.forms["editEventPlanningForm"];
	var planningId = form.elements["idPlanning"].value;
	var missionId=form.elements["missionId"].value;
	var startdate = form.elements["startdate"].value;
	var enddate = form.elements["enddate"].value;
	var nbhours = form.elements["nbhours"].value;
	var year = ${viewActivityPerWeekPerEmployee.year};
	
	var week = form.elements["week"].value;
    var user = form.elements["user"].value;
	//var duration = form.elements["duration"].value; 
	var duration = getCheckDurationBoxes();
	//alert(duration);
    var postData = "planningId="+planningId+"&missionId="+missionId+"&startdate="+startdate+"&enddate="+enddate+"&duration="+duration+"&nbhours="+nbhours+"&week="+week+"&user="+user+"&year="+year;
	//alert(postData);
    var request = YAHOO.util.Connect.asyncRequest('POST', saddSingleEventPlanningItemsPageUrl, callback, postData);
	unCheckDurationBoxes();
};


var saddSingleEventPlanningItemsFromDragAndDropPageUrl = "${ctx}/addSingleEventsFromDragAndDropPage.htm";
function addSingleEventPlanningItemsFromDragAndDropPage(planningId, weekNumber,missionId,employeeId){	
	alert('start dropping within item_placeholder date : ' + date + ' user id : ' +employeeId + ' missionId : '+missionId + ' planningId : '+planningId);
    var postData = "planningId="+planningId+"&missionId="+missionId+"&weekNumber="+weekNumber+"&employeeId="+employeeId;
	//alert(postData);
    var request = YAHOO.util.Connect.asyncRequest('POST', saddSingleEventPlanningItemsFromDragAndDropPageUrl, callback, postData);
	
};


function getCheckBoxes()
  {
    var result = "";
    var boxes = document.getElementsByName("che_");
    var count = boxes.length;
	//alert(count);
    if (count > 0)
    {
      for(index=0; index < count; index++)
      {
        if (boxes[index].checked)
        {
          //if (result.length > 0) result += ",";
          result += "&che_="+boxes[index].value; 
        }
      }
    }
	//alert(result);
    return result;
  }
  
  function getCountCheckBoxes()
  {
    var result = 0;
    var boxes = document.getElementsByName("che_");
    var count = boxes.length;
	//alert(count);
    if (count > 0)
    {
      for(index=0; index < count; index++)
      {
        if (boxes[index].checked)
        {
          //if (result.length > 0) result += ",";
          result += 1; 
        }
      }
    }
	//alert(result);
    return result;
  }


function getCheckDurationBoxes()
  {
    var result = "";
    var boxes = document.getElementsByName("duration");
    var count = boxes.length;
	//alert(count);
    if (count > 0)
    {
      for(index=0; index < count; index++)
      {
        if (boxes[index].checked)
        {
          //if (result.length > 0) result += ",";
         // result += "&che_="+boxes[index].value; 
		 result = boxes[index].value; 
        }
      }
    }
	//alert(result);
    return result;
  }

  function unCheckDurationBoxes()
  {
    var result = "";
    var boxes = document.getElementsByName("duration");
    var count = boxes.length;
	//alert(count);
    if (count > 0)
    {
      for(index=0; index < count; index++)
      {
        if (boxes[index].checked)
        { 
			
		boxes[index].checked=false; 
        }
      }
    }
	//alert(result);
    return result;
  }



</script>

<script type="text/javascript">
YAHOO.example.ItemSelectHandler = function() {
    // Use a LocalDataSource
  var oDS = new YAHOO.util.XHRDataSource("${ctx}/editPlanningActivitiesAsAjaxStream.htm"); 
  oDS.responseType = YAHOO.util.XHRDataSource.TYPE_JSON;   
 // Define the schema of the JSON results   
 oDS.responseSchema = {   
 resultsList : "events",   
 fields : ["name","id"]   
 };   

   
    // Instantiate the AutoComplete
    var oAC = new YAHOO.widget.AutoComplete("myInput", "myContainer", oDS);
    oAC.resultTypeList = false;
	oAC.applyLocalFilter = true;
    
    // Define an event handler to populate a hidden form field
    // when an item gets selected
    var missionId = YAHOO.util.Dom.get("missionId");
    var myHandler = function(sType, aArgs) {
        var myAC = aArgs[0]; // reference back to the AC instance
        var elLI = aArgs[1]; // reference to the selected LI element
        var oData = aArgs[2]; // object literal of selected item's result data
        
        // update hidden form field with the selected item's ID
        missionId.value = oData.id;
    };
    oAC.itemSelectEvent.subscribe(myHandler);
    
    
    return {
        oDS: oDS,
        oAC: oAC
    };
}();



YAHOO.example.ItemSelectHandler2 = function() {
    // Use a LocalDataSource
  var oDS = new YAHOO.util.XHRDataSource("${ctx}/editPlanningActivitiesAsAjaxStream.htm"); 
  oDS.responseType = YAHOO.util.XHRDataSource.TYPE_JSON;   
 // Define the schema of the JSON results   
 oDS.responseSchema = {   
 resultsList : "events",   
 fields : ["name","id"]   
 };   

   
    // Instantiate the AutoComplete
	/*
    var oAC = new YAHOO.widget.AutoComplete("myInput2", "myContainer2", oDS);
    oAC.resultTypeList = false;
	oAC.applyLocalFilter = true;
	*/
    
    // Define an event handler to populate a hidden form field
    // when an item gets selected
	/*
    var missionId = document.forms["editEventPlanningForm"].elements["missionId"];
    var myHandler = function(sType, aArgs) {
        var myAC = aArgs[0]; // reference back to the AC instance
        var elLI = aArgs[1]; // reference to the selected LI element
        var oData = aArgs[2]; // object literal of selected item's result data
        
        // update hidden form field with the selected item's ID
        missionId.value = oData.id;
    };
	*/
    oAC.itemSelectEvent.subscribe(myHandler);
    
    
    return {
        oDS: oDS,
        oAC: oAC
    };
}();




</script>
</html>