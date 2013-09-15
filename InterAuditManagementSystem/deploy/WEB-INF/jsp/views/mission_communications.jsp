<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">

<%
request.setAttribute("ctx", request.getContextPath()); 
%>
<head>
 <meta equiv="Content-Type" content="text/html;charset=UTF-8">
	<link rel="stylesheet" type="text/css" media="all" href="css/niceforms-default.css" />
<link href="css/HelpSupport.css" rel="stylesheet" type="text/css" />

<link rel="stylesheet" type="text/css" href="${ctx}/script/build/menu/assets/skins/sam/menu.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/script/build/editor/assets/skins/sam/editor.css" />


</head>


<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<tiles:insert page="/WEB-INF/jsp/views/layout.jsp" flush="true">
<tiles:put name="content" value="mission_communications-content.jsp"/>
</tiles:insert>



<script>
YAHOO.namespace("example.container");
function init() {
	
	// Define various event handlers for Dialog
	var handleSubmit = function() {
			myEditor.saveHTML();	
			this.submit();				
	};
	
	var handleCancel = function() {
		document.forms["messageForm"].elements["object"].value = "";
		document.getElementById("emailsListTd").innerHTML = "";
		//myEditor.clearEditorDoc();
		this.cancel();
	};
	
	var handleEmailSuccess = function(o) {
		var emailList = getCheckedEmailBoxes();
		var form = document.forms["messageForm"];
		form.elements["emailList"].value = emailList;		
		var td = document.getElementById("emailsListTd");
		td.innerHTML = emailList;
		resetEmailBoxes();
		this.hide();	
	};
	
	var handleFailure = function(o) {
		alert("Submission failed: " + o.status);
	};
	// Instantiate the Dialog
	YAHOO.example.container.dialog1 = new YAHOO.widget.Dialog("global",
																{ width : "760px",
																  draggable: true,
																  fixedcenter : true,
																  visible : false,
																  modal: true,
																  constraintoviewport : false,
																  buttons : [ 
																	      	  { text:"Cancel", handler:handleCancel },
																		      { text:"Send", handler:handleSubmit, isDefault:true }]
																}
							);
							
							
	
		
		// Instantiate the Dialog1
		YAHOO.example.container.dialog2 = new YAHOO.widget.Dialog("addDialog",
																	{ width : "30em",
																	  draggable: true,
																	  fixedcenter : true,
																	  visible : false,
																	  modal: true,
																	  constraintoviewport : false,
																	  buttons : [ { text:"Submit", handler:handleEmailSuccess, isDefault:true },
																				  { text:"Cancel", handler:handleCancel } ]
																	}
								);
								
		//YAHOO.example.container.dialog2.cfg.queueProperty("postmethod","form");
		// Wire up the success and failure handlers
		//YAHOO.example.container.dialog2.callback = {success: handleSuccess,failure: handleFailure };
		YAHOO.util.Event.addListener("show2", "click", YAHOO.example.container.dialog1.show, YAHOO.example.container.dialog2,true);
		YAHOO.example.container.dialog2.render();
		YAHOO.example.container.dialog2.cfg.queueProperty("postmethod","form");

		// Validate the entries in the form 
		YAHOO.example.container.dialog1.validate = function() {
		myEditor.saveHTML();	
		var data = this.getData();
		if (data.emailList == "") {
			alert("Please enter a recipient for the message");
			return false;
		} 
			
		if (data.object == "" ){
			alert("Please enter a subject for the message");
			return false;
		} 

		
		if (data.editor == "" ){
			alert("Please  provide an content for the message.");
			return false;
		}

		return true;
		
	};

	// Wire up the success and failure handlers
	//YAHOO.example.container.dialog1.callback = {success: handleSuccess,failure: handleFailure };
	YAHOO.util.Event.addListener("show", "click", YAHOO.example.container.dialog1.show, YAHOO.example.container.dialog1,true);
	YAHOO.example.container.dialog1.cfg.queueProperty("postmethod","form");
	YAHOO.example.container.dialog1.render();
	//YAHOO.example.container.dialog1.show();
}

YAHOO.util.Event.onDOMReady(init);
function showUploadDialog() {
	YAHOO.example.container.dialog1.show();
}

function resetEmailBoxes()
  {    
    var boxes = document.getElementsByName("reci_");	
    var count = boxes.length;
	//alert(count);
    if (count > 0)
    {
      for(index=0; index < count; index++)
      {
       boxes[index].checked = false;
      }
    }
  }



function getCheckedEmailBoxes()
  {
    var result = "";
    var boxes = document.getElementsByName("reci_");
    var count = boxes.length;
	//alert(count);
    if (count > 0)
    {
      for(index=0; index < count; index++)
      {
        if (boxes[index].checked)
        {
          if (result.length > 0) result += ";";
          result += boxes[index].value; 
        }
      }
    }
	//alert(result);
    return result;
  }



function resetForm(){

	alerte("ici");
    document.forms["messageForm"].elements["object"].value = "";
	var form = document.forms["messageForm"];
	form.elements["fromTd"].value = "kffdk";
	form.elements["emailList"].value = "jjhhjjh";		
	var td = document.getElementById("emailsListTd");
	td.innerHTML = "";
	//myEditor.clearEditorDoc();
}
  




</script>



<script type="text/javascript"> 


				(function() {
				 
					var Dom = YAHOO.util.Dom,
						Event = YAHOO.util.Event;
					
					var myConfig = {
						height: '300px',
						width: '600px',
						dompath: true,
						focusAtStart: true
					};
				
					//YAHOO.log('Create the Editor..', 'info', 'example');
					myEditor = new YAHOO.widget.SimpleEditor('editor', myConfig);
					
					myEditor._defaultToolbar.buttonType = 'advanced';    
					myEditor.render();	

								
				})();
				
				
			</script>
			

</html>