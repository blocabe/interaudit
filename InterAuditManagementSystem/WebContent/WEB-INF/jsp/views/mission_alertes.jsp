<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<link rel="stylesheet" type="text/css" media="all" href="css/niceforms-default.css" />
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<tiles:insert page="/WEB-INF/jsp/views/layout.jsp" flush="true">
<tiles:put name="content" value="mission_alertes-content.jsp"/>
</tiles:insert>

<script type="text/javascript">
function is_numeric(num)
	{
		var exp = new RegExp("^[0-9-.]*$","g");
		return exp.test(num);
	}

	

function is_numeric_positif(num)
	{
		return age.matches("^\\s*\\d+\\s*$");
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

	
	// Validate the entries in the form 
	YAHOO.example.container.dialog1.validate = function() {
		var data = this.getData();
		if (data.amount == "" ){
			alert("Please  provide an amount for the alerte.");
			return false;
		}

		if(!is_numeric(data.amount)){
			alert("Please  provide positive amount for the alerte.");
			return false;
		}
		
		if (data.amount < 0 ){
			alert("Please  provide positive amount for the alerte.");
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

    document.forms["addAlerteForm"].elements["alerteId"].value = "";
    document.forms["addAlerteForm"].elements["amount"].value = "";
	document.forms["addAlerteForm"].elements["description"].value = "";
    document.getElementById('formTitle').innerHTML = 'New alerte';
    YAHOO.example.container.dialog1.render();
    YAHOO.example.container.dialog1.show();
}

var handleSuccess = function(o){
    var form = document.forms["addAlerteForm"];
    if(o.responseText !== undefined){
        // Use the JSON Utility to parse the data returned from the server   
         try {   
            var object = YAHOO.lang.JSON.parse(o.responseText);
			//alert(o.responseText);
            form.elements["alerteId"].value = object.alerteId;
            form.elements["amount"].value = object.amount;
			form.elements["description"].value = object.description;
            
            //no password as it is only sent from client to browser.
            document.getElementById('formTitle').innerHTML = 'Update alerte';
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

var sEditAlerteUrl = "${ctx}/editMissionAlertePage.htm";

function editAlerte(alerteId){
	
    var postData = "alerteId="+alerteId;
    var request = YAHOO.util.Connect.asyncRequest('POST', sEditAlerteUrl, callback, postData);
}

function deleteAlerte(id,missionId)
  {
    if (confirm("Do you really want to delete alerte with id "+id))
    {
      window.location="${ctx}/deleteMissionAlertePage.htm?alerteId="+id+"&id="+missionId;
    }
  }
</script>
</html>