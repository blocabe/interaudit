
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
	<tiles:insert page="/WEB-INF/jsp/views/layout.jsp" flush="true">
	<tiles:put name="content" value="resultat-workshop-content.jsp"/>
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
	// Instantiate the Dialog1
	YAHOO.example.container.dialog1 = new YAHOO.widget.Dialog("addDialog1",
																{ width : "850px",
																  draggable: true,
																  fixedcenter : true,
																  visible : false,
																  modal: true,
																  constraintoviewport : false,
																  buttons : [{ text:"Close", handler:handleCancel } ]
																}
							);

	// Instantiate the Dialog2
	YAHOO.example.container.dialog2 = new YAHOO.widget.Dialog("addDialog2",
																{ width : "850px",
																  draggable: true,
																  fixedcenter : true,
																  visible : false,
																  modal: true,
																  constraintoviewport : false,
																  buttons : [{ text:"Close", handler:handleCancel } ]
																	      	  
																}
							);


	YAHOO.example.container.dialog1.cfg.queueProperty("postmethod","form");
	// Wire up the success and failure handlers
	YAHOO.example.container.dialog1.callback = {success: handleSuccess,failure: handleFailure };
	YAHOO.util.Event.addListener("show1", "click", YAHOO.example.container.dialog1.show, YAHOO.example.container.dialog1,true);
	YAHOO.example.container.dialog1.render();

	
	YAHOO.example.container.dialog2.cfg.queueProperty("postmethod","form");
	// Wire up the success and failure handlers
	YAHOO.example.container.dialog2.callback = {success: handleSuccess,failure: handleFailure };
	YAHOO.util.Event.addListener("show2", "click", YAHOO.example.container.dialog2.show, YAHOO.example.container.dialog2,true);
	YAHOO.example.container.dialog2.render();

}

YAHOO.util.Event.onDOMReady(init);
function showUploadDialog() {
	YAHOO.example.container.dialog1.show();
	YAHOO.example.container.dialog2.show();
}

function chartPerAssocie(){
    YAHOO.example.container.dialog1.render();
    YAHOO.example.container.dialog1.show();
}

function chartPerManager(){
    YAHOO.example.container.dialog2.render();
    YAHOO.example.container.dialog2.show();
}
</script>

</html>