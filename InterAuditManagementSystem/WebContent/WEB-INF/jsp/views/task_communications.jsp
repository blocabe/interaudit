<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<link rel="stylesheet" type="text/css" media="all" href="css/niceforms-default.css"/>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<tiles:insert page="/WEB-INF/jsp/views/layout.jsp" flush="true">

<tiles:put name="content" value="task_communications-content.jsp"/>

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
	YAHOO.example.container.dialog1.cfg.queueProperty("postmethod","form");
	// Validate the entries in the form 
	YAHOO.example.container.dialog1.validate = function() {
		var data = this.getData();
		if (data.recipient == "") {
			alert("Please enter a recipient for the message");
			return false;
		} 
			
		if (data.subject == "" ){
			alert("Please enter a subject for the message");
			return false;
		} 

		
		if (data.description == "" ){
			alert("Please  provide an content for the message.");
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

    document.forms["addMessageForm"].elements["messageId"].value = "";
	document.forms["addMessageForm"].elements["recipient"].value = "";
    document.forms["addMessageForm"].elements["subject"].value = "";
    document.forms["addMessageForm"].elements["description"].value = "";

    document.getElementById('formTitle').innerHTML = 'New message';
    YAHOO.example.container.dialog1.render();
    YAHOO.example.container.dialog1.show();
}

var handleSuccess = function(o){
    var form = document.forms["addMessageForm"];
    if(o.responseText !== undefined){
        // Use the JSON Utility to parse the data returned from the server   
         try {   
            var object = YAHOO.lang.JSON.parse(o.responseText);

            form.elements["messageId"].value = object.messageId;
            form.elements["recipient"].value = object.recipient;
            form.elements["subject"].value = object.subject;
            form.elements["description"].value = object.description;
          
            
            //no password as it is only sent from client to browser.
            document.getElementById('formTitle').innerHTML = 'Read message \'' + object.subject + '\'';
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

var sEditMessageUrl = "${ctx}/editTaskMessagePage.htm";

function editMessage(messageId){
    var postData = "messageId="+messageId;
    var request = YAHOO.util.Connect.asyncRequest('POST', sEditMessageUrl, callback, postData);
}


</script>

</html>