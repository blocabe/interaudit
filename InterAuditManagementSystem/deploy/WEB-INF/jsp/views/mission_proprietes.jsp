<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<link rel="stylesheet" type="text/css" media="all" href="css/niceforms-default.css" />

<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<tiles:insert page="/WEB-INF/jsp/views/layout.jsp" flush="true">
<tiles:put name="content" value="mission_proprietes-content.jsp"/>
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
</script>
</html>