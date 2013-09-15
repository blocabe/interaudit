<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<link rel="stylesheet" type="text/css" media="all" href="css/niceforms-default.css"/>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<tiles:insert page="/WEB-INF/jsp/views/layout.jsp" flush="true">
<tiles:put name="content" value="mission_documents-content.jsp"/>
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
																  buttons : [ { text:"Submit", handler:handleSubmit, isDefault:true },
																	      	  { text:"Cancel", handler:handleCancel } ]
																}
							);
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

    document.forms["addDocumentForm"].elements["documentId"].value = "";
    document.forms["addDocumentForm"].elements["title"].value = "";
    document.forms["addDocumentForm"].elements["description"].value = "";
    document.forms["addDocumentForm"].elements["fileName"].value = "";
	document.forms["addDocumentForm"].elements["category"].value = "";
	document.forms["addDocumentForm"].elements["owner"].value = "";
	
    document.getElementById('formTitle').innerHTML = 'Upload new document';
    YAHOO.example.container.dialog1.render();
    YAHOO.example.container.dialog1.show();
}

var handleSuccess = function(o){
    var form = document.forms["addDocumentForm"];
    if(o.responseText !== undefined){
        // Use the JSON Utility to parse the data returned from the server   
         try {   
            var object = YAHOO.lang.JSON.parse(o.responseText);
			
            form.elements["documentId"].value = object.documentId;
            form.elements["title"].value = object.title;
			form.elements["description"].value = object.description;
            //form.elements["fileName"].value = object.fileName;
			form.elements["category"].value = object.category;
            form.elements["owner"].value = object.owner;
           
			
            
            //no password as it is only sent from client to browser.
            document.getElementById('formTitle').innerHTML = 'Update document \'' + object.fileName + '\'';
         }
         catch (x) {   
             alert("JSON Parse failed in parsing document!");
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

var sEditDocumentUrl = "${ctx}/editMissionDocumentPage.htm";

function editDocument(documentId){
    var postData = "documentId="+documentId;
    var request = YAHOO.util.Connect.asyncRequest('POST', sEditDocumentUrl, callback, postData);
}

function deleteDocument(id)
  {
    if (confirm("Do you really want to delete document with id "+id))
    {
      window.location="${ctx}/deleteMissionDocumentPage.htm?documentId="+id;
    }
  }
</script>
</html>