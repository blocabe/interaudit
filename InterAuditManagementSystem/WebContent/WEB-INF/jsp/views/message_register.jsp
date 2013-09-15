<%@ page contentType="text/html"%>
<%@ taglib prefix="tab" uri="http://ditchnet.org/jsp-tabs-taglib"%>
<%@ taglib prefix="layout" uri="http://www.sourceforge.net/springLayout"%>
<%@ taglib prefix="tiles" uri="/WEB-INF/struts-tiles.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions"%>

<%
request.setAttribute("ctx", request.getContextPath()); 
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Create Message</title>
	
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link href="css/HelpSupport.css" rel="stylesheet" type="text/css" />
	<!--link href="css/odbb-skin.css" rel="stylesheet" type="text/css" /-->

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

	<!--link type="text/css" href="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/themes/base/ui.all.css" rel="stylesheet" />
	<script type="text/javascript" src="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/jquery-1.3.2.js"></script>
	<script type="text/javascript" src="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/ui/ui.core.js"></script>
	<script type="text/javascript" src="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/ui/ui.datepicker.js"></script>
	<link type="text/css" href="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/demos.css" rel="stylesheet" />

	
	
	<script type="text/javascript" src="${ctx}/script/build/yahoo-dom-event/yahoo-dom-event.js"></script>
	<script type="text/javascript" src="${ctx}/script/build/animation/animation-min.js"></script>
	<script type="text/javascript" src="${ctx}/script/datasource/datasource-min.js"></script>
	<script type="text/javascript" src="${ctx}/script/build/autocomplete/autocomplete-min.js"></script-->

	



	<style type="text/css"> 
#ta { 
   padding: 5px; 
   height: 350px; 
   width: 500px; 
   /*border: medium inset #CCCCCC; */
   font-family: "Courier New", Courier, mono; 
   font-size: 12px; 
} 
</style> 
</head>

<style type="text/css">
        /*
        * OK sous IE8, C2 et F3,
        * se positionne au-dessus du dialogue de z-index 1000,
        * se positionne juste en dessous de la ligne d'input.
        * Sous F3, si la fenêtre Firebug est ouverte
        * le datepicker remonte sur le dialog par manque de place, normal.
        */
        div#ui-datepicker-div {
            z-index: 4000;
        }

		input.button120 {
	width:120px;
	margin:1px 0 1px 0;
	background-color:rgb(241,241,237);
	font-family:tahoma;
	font-size:11px;
	border:1px solid;
	border-top-color:rgb(0,60,116);
	border-left-color:rgb(0,60,116);
	border-right-color:rgb(0,60,116);
	border-bottom-color:rgb(0,60,116);
	border-style: outset;
}

#global {   margin-left: auto;   margin-right: auto;   width: 700px;   text-align: left; /* on rétablit l'alignement normal du texte */ }
.global2 {   margin-left: auto;   margin-right: auto;   width: 300px;   text-align: left; /* on rétablit l'alignement normal du texte */ }
    </style>

 






<script>
  function cancelForm()
  {
      window.location="${ctx}/homePage.htm";
  }

  function replyMessage(id)
  {
      window.location="${ctx}/createMessagePage.htm?parentId=" +id;
  }
</script>


<script>
  function newMessage()
  {
      window.location="${ctx}/createMessagePage.htm";
  }
</script>


<!--link rel="stylesheet" type="text/css" href="${ctx}/script/build/fonts/fonts-min.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/script/build/menu/assets/skins/sam/menu.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/script/build/button/assets/skins/sam/button.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/script/build/editor/assets/skins/sam/simpleeditor.css" />
<script type="text/javascript" src="${ctx}/script/build/yahoo-dom-event/yahoo-dom-event.js"></script>
<script type="text/javascript" src="${ctx}/script/build/element/element-beta-min.js"></script>
<script type="text/javascript" src="${ctx}/script/build/container/container_core-min.js"></script>
<script type="text/javascript" src="${ctx}/script/build/menu/menu-min.js"></script>
<script type="text/javascript" src="${ctx}/script/build/button/button-min.js"></script>
<script type="text/javascript" src="${ctx}/script/build/editor/simpleeditor-beta-min.js"></script-->

<link rel="stylesheet" type="text/css" href="${ctx}/script/build/menu/assets/skins/sam/menu.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/script/build/editor/assets/skins/sam/editor.css" />
<script type="text/javascript" src="${ctx}/script/build/element/element-min.js"></script>
<script type="text/javascript" src="${ctx}/script/build/container/container-min.js"></script>
<script type="text/javascript" src="${ctx}/script/build/menu/menu-min.js"></script>
<script type="text/javascript" src="${ctx}/script/build/editor/editor-min.js"></script>
<style>
.yui-skin-sam .yui-toolbar-container .yui-toolbar-subcont {
border-bottom:1px solid #808080;
padding:0.4em 1em 0.4em;
}
</style>




<body class="yui-skin-sam">
   <div id="ODBB_HEADER"> 
				<a href="${ctx}/homePage.htm"><img src="images/bannieres/ban.base1.jpg" alt="IAMS" border="0"></a>
	</div>
	 <jsp:include page="horizontal_menu.jsp"/>
	
 <div style="font-size: .7em;background-color: #F8F6E9;">
	<br/>
	
	
	<h2>
		Messagerie : 

	<a id="current" href="${ctx}/showMessageRecusPage.htm"><img src="images/picto_inbox.gif" border="0"/> reçus</a>
	<a  href="${ctx}/showMessageEnvoyesPage.htm"><img src="images/picto_sent.gif" border="0"/>  envoyés</a></li>
	&nbsp;&nbsp;
	<span style="font:12px Verdana, sans-serif;margin-right:10pt;"><input  style="font:10px Verdana, sans-serif;margin-right:10pt;" type="button" onclick="newMessage();"class="button120" value="New message" /></span>	
	</h2>

<br/>
<div id="global">
<form   name="messageForm" action="${ctx}/createMessagePage.htm" method="post" >
	<table cellpadding="3" cellspacing="2" border="0" class="axial" style="margin-left: auto;   margin-right: auto;   width: 700px;   text-align: left;">

	<tr>
		<td colspan="2" align="center">
				<span  style="color:black;font-weight: bold;">
		<c:choose>
			<c:when test='${message.id == null }'>
				Ecrire un message
			</c:when>
			<c:otherwise>
				Lire un message 
			</c:otherwise>
		</c:choose>
		</span>
			</td>
	</tr>

	<tr>
      <th>&nbsp;De : &nbsp;&nbsp;</th>
      <td>
        ${message.from.fullName}
      </td>
    </tr>

	<tr>
      <th><input style="font:10px Verdana, sans-serif;margin-right:10pt;" type="button" class="button120" value="A" id="show"/></th>
      <td><span  style="color:blue;font-weight: bold;">${message.emailsList}</span></td>
      <c:if test="${not empty invalidRecipientErrorMessage}">
                        <span style="color: red;"><c:out value="${invalidRecipientErrorMessage}" escapeXml="false" /></span>
                        <c:set var="invalidRecipientErrorMessage" value="" scope="session" />
      </c:if>
    </tr>

	<tr>
      <th>&nbsp;Client : &nbsp;&nbsp;</th>
      <td>
        
        <spring:bind path="message.mission.id">
					<select  name="${status.expression}">
					<option value="" selected>Choose an option...</option>
					<c:forEach var="option" items="${missionOptions}">
					
						<option value="${option.id}" 
						<c:if test="${option.id == message.mission.id}">selected</c:if>>${option.name}
						</option>
					</c:forEach>
					</select>	
					<span style="color: red;">${status.errorMessage}</span>				
		</spring:bind>
      </td>
    </tr>

	
	<tr>
      <th>&nbsp;Date : &nbsp;&nbsp;</th>
      <td>
	  <span  style="color:blue;font-weight: bold;">${message.createDate}</span>
         
      </td>
    </tr>

	<tr>
      <th>&nbsp;Objet : &nbsp;&nbsp;</th>
      <td>
        <spring:bind path="message.subject" >
						<input style="width:500px;"  type="text" size="30" name="${status.expression}" value="${status.value}" <c:if test="${message.id != null }">READONLY</c:if>/>
						<span style="color: red;">${status.errorMessage}</span>						
					</spring:bind>
      </td>
    </tr>

	<tr>
      <th>&nbsp;Message : &nbsp;&nbsp;</th>
      <td>
		<c:if test="${message.id == null }">
			<spring:bind path="message.contents" >
				 <textarea name="${status.expression}" id="contents" rows="20" cols="60"  
				 <c:if test="${message.id != null }">READONLY</c:if>>${status.value}
				 </textarea>
				<span style="color: red;">${status.errorMessage}</span>
				</spring:bind>

				
		</c:if>
		<c:if test="${message.id != null }">
		<div id="ta"> 
		  <p>${message.contentsTextHtmlFormatted}</p> 		  
		</div> 		
		</c:if>


		

      </td>
    </tr>


	<tr>
     
	  <td colspan="2" align="center">
				<input style="width:100px;
				" type="button" name="cancel" id="cancel" class="button120"  value="Cancel" onclick="cancelForm()"/>
				<c:choose>
					<c:when test='${message.id == null }'>
						<input style="width:100px;
				" type="submit" name="submit" id="submitbutton" class="button120"  value="Envoyer" />
					</c:when>
					<c:otherwise>
						<input style="width:100px;
				" type="button" name="repondre" id="submit" class="button120"  value="Repondre"   onclick="replyMessage(${message.id})"/>
					</c:otherwise>
				</c:choose>
				
			</td>
      
    </tr>

	
	
	</table>
   
	
    
</form>

</div>
	<br/>
</div>

<h2></h2>


</body>
</html>









<div id="addDialog">
	<div class="hd">
        <span id="formTitle" style="color:blue;">Select recipients</span>
    </div>
    <div class="bd">
	<form name="addRecipientsForm" action="${ctx}/createMessagePage.htm" method="get">
	<input type="hidden" name="missionId"/>
	<input type="hidden" name="sujet"/>
	<input type="hidden" name="contents"/>
			<table id="ver-zebra2" width="100%"  class="formlist">
			 <c:set var="line" value="even"/>
			 
			 <c:set var="row" value="0"/>
			 <c:forEach var="option" items="${teamMembers}">
						
						<c:choose>
							<c:when test='${row % 2 eq 0 }'>
							 <c:set var="line" value="veen" />									      			
							</c:when>
							<c:otherwise>
							 <c:set var="line" value="odd" />									      			
							</c:otherwise>
						</c:choose>

						<tr class="${line}">
						<td style="border: 1px solid #0066aa;">
							<input type="checkbox" name="reci_${option.id}" value="${option.email}"/>
						</td>
						<td style="border: 1px solid #0066aa;">
							<span style="color:rgb(218,69,37);"><strong>${option.fullName}</strong></span>
						</td>
				</tr>
			
						<c:set var="row" value="${row + 1}"/>
			</c:forEach>
			
			
			</table>
	</form>
	</div>
	
</div>

<script>
	YAHOO.namespace("example.container");
	// Define various event handlers for Dialog
	var handleSubmit = function() {

	   document.forms["addRecipientsForm"].elements["missionId"].value = document.forms["messageForm"].elements["mission.id"].value;
       document.forms["addRecipientsForm"].elements["sujet"].value = document.forms["messageForm"].elements["subject"].value;
	   document.forms["addRecipientsForm"].elements["contents"].value = document.forms["messageForm"].elements["contents"].value;

		this.submit();		
	};
	var handleCancel = function() {
		this.cancel();
	};

	

	function init() {
	
		var handleSuccess = function(o) {
			alert("Submission ok");
		};
		var handleFailure = function(o) {
			alert("Submission failed: " + o.status);
		};

		
		// Instantiate the Dialog1
		YAHOO.example.container.dialog1 = new YAHOO.widget.Dialog("addDialog",
																	{ width : "30em",
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
					var myEditor = new YAHOO.widget.SimpleEditor('contents', myConfig);
					myEditor._defaultToolbar.buttonType = 'advanced';    
					myEditor.render();

					YAHOO.util.Event.on('submitbutton', 'click', function() {
						//Put the HTML back into the textarea
						myEditor.saveHTML();						
						//document.forms["messageForm"].submit();                
					});
					

				 
				})();
</script>



