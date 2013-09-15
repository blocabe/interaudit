<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/1999/REC-html401-19991224/loose.dtd">

<%
request.setAttribute("ctx", request.getContextPath()); 
%>
<html>
    <head>
        
	

        <script type="text/javascript" src="script/scriptaculous/prototype.js"></script>
        
	<script type="text/javascript" src="http://yui.yahooapis.com/2.7.0/build/yahoo-dom-event/yahoo-dom-event.js"></script>
	<script type="text/javascript" src="http://yui.yahooapis.com/2.7.0/build/connection/connection-min.js"></script>
	<script type="text/javascript" src="http://yui.yahooapis.com/2.7.0/build/animation/animation-min.js"></script>
	<script type="text/javascript" src="http://yui.yahooapis.com/2.7.0/build/dragdrop/dragdrop-min.js"></script>
	<script type="text/javascript" src="http://yui.yahooapis.com/2.7.0/build/container/container-min.js"></script>
        
        	<link href="css/wizard/assist3ToUse.css" rel="stylesheet" />
        <link href="css/wizard/lines.css" rel="stylesheet" />
        <link href="css/wizard.css" rel="stylesheet" />
	<link href="css/odbb-skin.css" rel="stylesheet" type="text/css" />
	<link href="css/tabs.css" rel="stylesheet" type="text/css" />
	<link href="css/style.css" rel="stylesheet" type="text/css" />
	
	
	<link rel="stylesheet" type="text/css" href="${ctx}/script/build/fonts/fonts-min.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/script/build/button/assets/skins/sam/button.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/script/build/container/assets/skins/sam/container.css" />

<link rel="stylesheet" type="text/css" href="${ctx}/style/assist3ToUse.css" />

<script type="text/javascript" src="${ctx}/script/build/utilities/utilities.js"></script>
<script type="text/javascript" src="${ctx}/script/build/button/button-min.js"></script>
<script type="text/javascript" src="${ctx}/script/build/container/container-min.js"></script>

<script type="text/javascript" src="${ctx}/script/build/yahoo/yahoo-min.js"></script>
<script type="text/javascript" src="${ctx}/script/build/event/event-min.js"></script>
<script type="text/javascript" src="${ctx}/script/build/connection/connection-min.js"></script>
<script type="text/javascript" src="${ctx}/script/build/json/json-min.js"></script>
	

<body class="yui-skin-sam" marginwidth="0" marginheight="0" class="composite"
	style="background-color: #F8F6E9;">>

<div id="ODBB_HEADER"> 
	<a href="${ctx}/showHomePage.action"><img src="images/page2_new.jpg" alt="ODBB" border="0"></a>
</div>

<div id="ODBBNAVIGATION">
	<jsp:include page="wizardNavigation.jsp" />
</div>

<!-- Set style for this -->
<div>
	<div style="width:70%; float: left;">
		
		<!--jsp:include page="wizardCenterTable.jsp" /-->	
		<input type="button" value="Upload a new dataset" onclick="javascript:showUploadDialog()" />	
	</div>

	<div style="width:30%; float: right;">
		
		
		<!-- jsp:include page="wizardDatasetDetails.jsp" /-->	
		
		
		<input type="button" value="Next >" />
	</div>		
</div>



<!-- Form to be displayed as a blocking dialog -->
<div id="dialog1">

	<div class="hd">Upload a data file</div>
	<div class="bd">
	
	
	<form name="myform" method="post" enctype="multipart/form-data">
		Dataset name: <input type="text" name="fileName">
		<input type="submit" value="Upload!" />

	</form>
	
	</div>

</div>




		

    </body>
    
    
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
	YAHOO.example.container.dialog1 = new YAHOO.widget.Dialog("dialog1",
																{ width : "45em",
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


