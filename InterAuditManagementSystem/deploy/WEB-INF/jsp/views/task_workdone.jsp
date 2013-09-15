<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<link rel="stylesheet" type="text/css" media="all" href="css/niceforms-default.css"/>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<tiles:insert page="/WEB-INF/jsp/views/layout.jsp" flush="true">

<tiles:put name="content" value="task_workdone-content.jsp"/>

</tiles:insert>

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
																{ width : "621px",
																  draggable: true,
																  fixedcenter : true,
																  visible : false,
																  modal: true,
																  constraintoviewport : false,
																  buttons : [ { text:"Send", handler:handleSubmit, isDefault:true },
																	      	  { text:"Cancel", handler:handleCancel } ]
																}
							);
	
	// Validate the entries in the form 
	YAHOO.example.container.dialog1.validate = function() {
		var data = this.getData();
		if (data.dateOfWork == "") {
			alert("Please enter a date for the intervention");
			return false;
		} 
			

		if (data.spentHours == "" ){
			alert("Please enter a spent hours for the intervention");
			return false;
		} 


		return true;
		
	};

	YAHOO.example.container.dialog1.cfg.queueProperty("postmethod","form");
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

	var d = new Date(); 
	var jour =d.getDate(); 
	var mois =d.getMonth(); 
	var annee =d.getFullYear(); 

	var date=mois+"/"+jour+"/"+annee; 

    document.forms["addWorkDoneForm"].elements["workDoneId"].value = "";
	document.forms["addWorkDoneForm"].elements["dateOfWork"].value = date;
    document.forms["addWorkDoneForm"].elements["spentHours"].value = "";
    document.forms["addWorkDoneForm"].elements["comments"].value = "";

    document.getElementById('formTitle').innerHTML = 'Log work done on task';
    YAHOO.example.container.dialog1.render();
    YAHOO.example.container.dialog1.show();
}

var handleSuccess = function(o){
    var form = document.forms["addWorkDoneForm"];
    if(o.responseText !== undefined){
        // Use the JSON Utility to parse the data returned from the server   
         try {   
            var object = YAHOO.lang.JSON.parse(o.responseText);

            form.elements["workDoneId"].value = object.workDoneId;
            form.elements["dateOfWork"].value = object.dateOfWork;
            form.elements["spentHours"].value = object.spentHours;
            form.elements["comments"].value = object.comments;
          
            
            //no password as it is only sent from client to browser.
            document.getElementById('formTitle').innerHTML = 'update work done \'' + object.subject + '\'';
         }
         catch (x) {   
             alert("JSON Parse failed in parsing cost!");
             return;
         } 
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

var sEditWorkDoneUrl = "${ctx}/editTaskWorkDonePage.htm";

function editWorkDone(workDoneId){
    var postData = "workDoneId="+workDoneId;
    var request = YAHOO.util.Connect.asyncRequest('POST', sEditWorkDoneUrl, callback, postData);
}

function deleteWorkDone(id,taskId)
  {
    if (confirm("Do you really want to delete document with id "+id))
    {
      window.location="${ctx}/deleteTaskWorkDonePage.htm?workDoneId="+id+"&taskId="+taskId;
    }
  }


</script>

</html>