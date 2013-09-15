<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<tiles:insert page="/WEB-INF/jsp/views/layout.jsp" flush="true">
<tiles:put name="content" value="mission_interventions-content.jsp"/>
</tiles:insert>

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
																{ width : "400px",
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

	/*initCalendar();
    document.forms["addInterventionForm"].elements["taskId"].value = "";
    document.forms["addInterventionForm"].elements["task"].value = "";
    document.forms["addInterventionForm"].elements["profile"].value = "";
    document.forms["addInterventionForm"].elements["emloyee"].value = "";
    //document.forms["addInterventionForm"].elements["startdate"].value = "";
	//document.forms["addInterventionForm"].elements["enddate"].value = "";
    document.forms["addInterventionForm"].elements["hours"].value = "";
	document.forms["addInterventionForm"].elements["priority"].value = "";
	*/
	

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
			form.elements["status"].value = object.status;
            form.elements["todo"].value = object.todo;
            form.elements["comments"].value = object.comments;
           /* form.elements["task"].value = object.task;
            form.elements["profile"].value = object.profile;
            form.elements["emloyee"].value = object.emloyee;
			//form.elements["startdate"].value = object.startdate;
			//form.elements["enddate"].value = object.enddate;
			form.elements["hours"].value = object.hours;
			form.elements["priority"].value = object.priority;
			
            */
            //no password as it is only sent from client to browser.
            document.getElementById('formTitle').innerHTML = 'Update intervention \'' + object.taskName + '\'';
         }
         catch (x) {   
             alert("JSON Parse failed in parsing intervention!");
             return;
         } 
		//initCalendar();
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







</html>