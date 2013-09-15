
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
	<tiles:insert page="/WEB-INF/jsp/views/layout.jsp" flush="true">
	<tiles:put name="content" value="activity_report_workshop_content.jsp"/>
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
																  buttons : [{ text:"Cancel", handler:handleCancel },
																		     { text:"Send", handler:handleSubmit, isDefault:true }]
																}
							);
	YAHOO.example.container.dialog1.cfg.queueProperty("postmethod","form");


	// Instantiate the Dialog
	YAHOO.example.container.dialog2 = new YAHOO.widget.Dialog("addDialog2",
																{ width : "600px",
																  draggable: true,
																  fixedcenter : true,
																  visible : false,
																  modal: true,
																  constraintoviewport : false,
																  buttons : [{ text:"Cancel", handler:handleCancel },
																		     { text:"Send", handler:handleSubmit, isDefault:true }]
																}
							);
	YAHOO.example.container.dialog2.cfg.queueProperty("postmethod","form");

	// Validate the entries in the form 
	/*
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
	*/

	
	YAHOO.example.container.dialog2.validate = function() {
		var data = this.getData();
		if (data.missionId == "") {
			alert("Entrez une activitée...");
			return false;
		} 

		if (data.codePrestationId == "") {
			alert("Entrez un code prestation...");
			return false;
		} 

		if (data.year == "") {
			alert("Entrez une année...");
			return false;
		} 
		
		return true;
		
	};
	

	// Wire up the success and failure handlers
	YAHOO.example.container.dialog1.callback = {success: handleSuccess,failure: handleFailure };
	YAHOO.util.Event.addListener("show", "click", YAHOO.example.container.dialog1.show, YAHOO.example.container.dialog1,true);
	YAHOO.example.container.dialog1.render();


	YAHOO.example.container.dialog2.callback = {success: handleSuccess,failure: handleFailure };
	YAHOO.util.Event.addListener("show2", "click", YAHOO.example.container.dialog2.show, YAHOO.example.container.dialog2,true);
	YAHOO.example.container.dialog2.render();
}

YAHOO.util.Event.onDOMReady(init);
function showUploadDialog() {
	YAHOO.example.container.dialog1.show();
}

function showAddRowDialog() {
	YAHOO.example.container.dialog2.show();
}

function resetForm(){
	document.forms["addMessageForm"].action="${ctx}/rejectTimesheet.htm";
    document.forms["addMessageForm"].elements["description"].value = "";
    document.getElementById('formTitle').innerHTML = 'New comment';
    YAHOO.example.container.dialog1.render();
    YAHOO.example.container.dialog1.show();
}



function addComment(){
	document.forms["addMessageForm"].action="${ctx}/addComment.htm";
    document.forms["addMessageForm"].elements["description"].value = "";
    document.getElementById('formTitle').innerHTML = 'New comment';
    YAHOO.example.container.dialog1.render();
    YAHOO.example.container.dialog1.show();
}


function resetForm2(){
    document.getElementById('formTitle2').innerHTML = 'New row';
	var form = document.forms["activityReportForm"];
	form.elements["missionId"].value = '';
	form.elements["codePrestationId"].value = '';
	//form.elements["myInput2"].value = '';
	form.elements["myInput"].value = '';
	//form.elements["year"].value = new Date().getFullYear();
	
	form.elements["rowId"].value ='';
	form.elements["monday"].value = '0';
	form.elements["tuesday"].value = '0';
	form.elements["wednesday"].value = '0';
	form.elements["thursday"].value = '0';
	form.elements["friday"].value = '0';
    YAHOO.example.container.dialog2.render();
    YAHOO.example.container.dialog2.show();
}



var handleSuccess = function(o){
	
    var form = document.forms["activityReportForm"];
    if(o.responseText !== undefined){
        // Use the JSON Utility to parse the data returned from the server   
         try {   
            var object = YAHOO.lang.JSON.parse(o.responseText);

			//alert(document.forms["activityReportForm"].elements["missionId"]);
			 
            form.elements["missionId"].value = object.missionId;
		    form.elements["rowId"].value = object.rowId;
		    //form.elements["year"].value = object.year;
            form.elements["codePrestationId"].value = object.codePrestation;
			//form.elements["myInput2"].value = object.codePrestationName;
			form.elements["myInput"].value = object.missionName;			
            form.elements["monday"].value = object.monday;
			form.elements["tuesday"].value = object.tuesday;
			form.elements["wednesday"].value = object.wednesday;
			form.elements["thursday"].value = object.thursday;
			form.elements["friday"].value = object.friday;
			
			
            
            //no password as it is only sent from client to browser.
            document.getElementById('formTitle').innerHTML =  'Edit row' ;
         }
         catch (x) {   
             alert("JSON Parse failed in parsing intervention!");
             return;
         } 

        YAHOO.example.container.dialog2.render();
        YAHOO.example.container.dialog2.show();		
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

var sEditRowItemUrl = "${ctx}/editRowFromTimeSheet.htm";


function editRowFromTimeSheet(idRow){
	 var postData = "rowId="+idRow;
     var request = YAHOO.util.Connect.asyncRequest('POST', sEditRowItemUrl, callback, postData);
}

</script>

<script type="text/javascript">
YAHOO.example.ItemSelectHandler = function() {
    // Use a LocalDataSource
  var oDS = new YAHOO.util.XHRDataSource('${ctx}/editTimesheetActivitiesAsAjaxStream.htm'); 
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
	oAC.queryDelay = .5;
    
    // Define an event handler to populate a hidden form field
    // when an item gets selected
    var missionId = YAHOO.util.Dom.get("missionId");
	//alert(missionId);
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
	var oDS = new YAHOO.util.XHRDataSource('${ctx}/editTimesheetCodePrestationAsAjaxStream.htm'); 
	oDS.responseType = YAHOO.util.XHRDataSource.TYPE_JSON;   
	 // Define the schema of the JSON results   
	 oDS.responseSchema = {   
	 resultsList : "events",   
	 fields : ["name","id"]   
	 };   

   
    // Instantiate the AutoComplete
    var oAC = new YAHOO.widget.AutoComplete("myInput2", "myContainer2", oDS);
    oAC.resultTypeList = false;
	oAC.applyLocalFilter = true;
	oAC.queryDelay = .5;
    
    // Define an event handler to populate a hidden form field
    // when an item gets selected
    var codePrestationId = YAHOO.util.Dom.get("codePrestationId");
	//alert(codePrestationId);
    var myHandler = function(sType, aArgs) {
        var myAC = aArgs[0]; // reference back to the AC instance
        var elLI = aArgs[1]; // reference to the selected LI element
        var oData = aArgs[2]; // object literal of selected item's result data
        
        // update hidden form field with the selected item's ID
        codePrestationId.value = oData.id;
    };
    oAC.itemSelectEvent.subscribe(myHandler);
    
    
    return {
        oDS: oDS,
        oAC: oAC
    };
}();

</script>


</html>