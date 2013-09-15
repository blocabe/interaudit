<jsp:directive.page contentType="text/html;charset=UTF-8" />
<%@ taglib prefix="tab" uri="http://ditchnet.org/jsp-tabs-taglib"%>
<%@ taglib prefix="layout" uri="http://www.sourceforge.net/springLayout"%>
<%@ taglib prefix="tiles" uri="/WEB-INF/struts-tiles.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>



<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>Register customer</title>
	
	<link href="css/odbb-skin.css" rel="stylesheet" type="text/css" />
	<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="expires" content="-1">
	

	<link href="css/odbb-skin.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="${ctx}/script/build/fonts/fonts-min.css" />
	<link rel="stylesheet" type="text/css" href="${ctx}/script/build/button/assets/skins/sam/button.css" />
	<link rel="stylesheet" type="text/css" href="${ctx}/script/build/container/assets/skins/sam/container.css"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/script/build/fonts/fonts-min.css" />
	<link rel="stylesheet" type="text/css" href="${ctx}/script/build/autocomplete/assets/skins/sam/autocomplete.css" />

	<script type="text/javascript" src="${ctx}/script/build/utilities/utilities.js"></script>
	<script type="text/javascript" src="${ctx}/script/build/button/button-min.js"></script>
	<script type="text/javascript" src="${ctx}/script/build/container/container-min.js"></script>
	<script type="text/javascript" src="${ctx}/script/build/yahoo/yahoo-min.js"></script>
	<script type="text/javascript" src="${ctx}/script/build/event/event-min.js"></script>
	<script type="text/javascript" src="${ctx}/script/build/connection/connection-min.js"></script>
	<script type="text/javascript" src="${ctx}/script/build/json/json-min.js"></script>

	<link type="text/css" href="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/themes/base/ui.all.css" rel="stylesheet" />
	<script type="text/javascript" src="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/jquery-1.3.2.js"></script>
	<script type="text/javascript" src="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/ui/ui.core.js"></script>
	<!--script type="text/javascript" src="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/ui/ui.datepicker.js"></script-->
	<link type="text/css" href="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/demos.css" rel="stylesheet" />

	
	
	<script type="text/javascript" src="${ctx}/script/build/yahoo-dom-event/yahoo-dom-event.js"></script>
	<script type="text/javascript" src="${ctx}/script/build/animation/animation-min.js"></script>
	<script type="text/javascript" src="${ctx}/script/datasource/datasource-min.js"></script>
	<script type="text/javascript" src="${ctx}/script/build/autocomplete/autocomplete-min.js"></script>

</head>

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


<script language="JavaScript">

function disableEnterKey(e)
{
     var key;      
     if(window.event)
          key = window.event.keyCode; //IE
     else
          key = e.which; //firefox      

     return (key != 13);
}

</script>


</style>

<%
request.setAttribute("ctx", request.getContextPath()); 
%>


<body  class="yui-skin-sam"  OnKeyPress="return disableKeyPress(event)">
 <!-- START OF HEADER PART -->  
   <div id="ODBB_HEADER"> 
				<a href="${ctx}/homePage.htm"><img src="images/bannieres/ban.base1.jpg" alt="IAMS" border="0"></a>
	</div>

	<!-- START OF MENU PART --> 
	 <jsp:include page="horizontal_menu.jsp"/>
	<!-- END OF MENU PART -->  

<%--div id="ODBB_MENU"> 	
			<jsp:include page="menu.jsp"/>
			<img src="images/page2_1_cubes.jpg" alt="IAMS" height="250" width="250" border="0">
		</div--%>

<!-- START OF CONTENTS PART --> 
 <div style="font-size: .7em;background-color: #F8F6E9;">
	<br/>
	<jsp:include page="mission_register-content.jsp"/>
	<br/>
</div>
<!-- END OF CONTENTS PART -->



<!-- START OF FOOTER PART-->  
  <jsp:include page="footer.jsp"/>
  <!-- END OF FOOTER PART -->  
</body>


<input type="hidden" name="customerid" value=""/>
			<input type="hidden" name="type" value=""/>

<script>
YAHOO.namespace("example.container");

// Define various event handlers for Dialog
	var handleSubmit = function() {
		//this.submit();	
		
		if(YAHOO.example.container.dialog1.validate() == true){
			with(document.addFeeForm)
			 	{
					var url = "${ctx}/showInvoiceRegisterPage.htm?customerid=${param['customerid']}&type=${invoice.type}&id=${param['id']}&command=addfee";
			 		action.value=url;
					submit();
		 	  }
		}
		
			  
	};
	var handleCancel = function() {
		this.cancel();
	};

	var myButtonsAll = [ { text:"Submit", handler:handleSubmit, isDefault:true },{ text:"Cancel", handler:handleCancel } ];
	var myButtonsCancelOnly = [{ text:"Cancel", handler:handleCancel } ];

function init() {
	
	var handleSuccess = function(o) {
		alert("Submission ok");
	};
	var handleFailure = function(o) {
		alert("Submission failed: " + o.status);
	};
	
	var handleCloseMissionYes = function() {      
		markMissionAsClosed();
        this.hide();
    };
    
    
    var handleNo = function() {
        this.hide();
    };

	
	YAHOO.example.container.closeMissionDialog = new YAHOO.widget.SimpleDialog("closeMissionDialog", { 
        width: "80em", 
        effect:{
            effect: YAHOO.widget.ContainerEffect.FADE,
            duration: 0.25
        }, 
        fixedcenter: true,
        modal: true,
        visible: false,
        draggable: false,
        icon: YAHOO.widget.SimpleDialog.ICON_WARN,
        constraintoviewport: false, 
        buttons: [ { text:"Proceed", handler:handleCloseMissionYes, isDefault:true }, 
                    { text:"Cancel",  handler:handleNo } ] 

        });     
	
	
	// Instantiate the Dialog1
	YAHOO.example.container.dialog1 = new YAHOO.widget.Dialog("addDialog1",
																{ width : "40em",
																  draggable: true,
																  fixedcenter : true,
																  visible : false,
																  modal: true,
																  constraintoviewport : false,
																  buttons : [ { text:"Submit", handler:handleSubmit, isDefault:true },
																	      	  { text:"Cancel", handler:handleCancel } ]
																}
							);

	

	
	// Validate the entries in the form to require that both first and last name are entered
	YAHOO.example.container.dialog1.validate = function() {
		var data = this.getData();
		if (data.justification == "" || data.justification == "") {
			alert("Please enter justification ");
			return false;
		} 
			
		if (! is_numeric(data.prix) || data.prix == "" ){
			alert("Please  prix  must be a number.");
			return false;
		} 


		return true;
		
	};


	
	YAHOO.example.container.dialog1.cfg.queueProperty("postmethod","form");
	// Wire up the success and failure handlers
	YAHOO.example.container.dialog1.callback = {success: handleSuccess,failure: handleFailure };
	YAHOO.util.Event.addListener("show", "click", YAHOO.example.container.dialog1.show, YAHOO.example.container.dialog1,true);
	YAHOO.example.container.dialog1.render();
	
	
	
	// Render the Dialog
    YAHOO.example.container.closeMissionDialog.render();
    YAHOO.util.Event.addListener("terminer", "click", YAHOO.example.container.closeMissionDialog.show, YAHOO.example.container.closeMissionDialog, true);
    YAHOO.example.container.closeMissionDialog.hide();
    
}

YAHOO.util.Event.onDOMReady(init);
function showUploadDialog() {
	YAHOO.example.container.dialog1.show();
}


function resetForm(){

    document.forms["addFeeForm"].elements["justification"].value = "";
    document.forms["addFeeForm"].elements["prix"].value = "";


	YAHOO.example.container.dialog1.cfg.queueProperty("buttons", myButtonsAll);  

    YAHOO.example.container.dialog1.render();
    YAHOO.example.container.dialog1.show();
}


</script>

</html>





