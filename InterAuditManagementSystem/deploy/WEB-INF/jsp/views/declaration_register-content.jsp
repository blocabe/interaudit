<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions"%>

<link type="text/css" href="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/themes/base/ui.all.css" rel="stylesheet" />
	<script type="text/javascript" src="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/jquery-1.3.2.js"></script>
	<script type="text/javascript" src="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/ui/ui.core.js"></script>
	<script type="text/javascript" src="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/ui/ui.datepicker.js"></script>
	<link type="text/css" href="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/demos.css" rel="stylesheet" />

		<link rel="stylesheet" type="text/css" href="${ctx}/script/build/autocomplete/assets/skins/sam/autocomplete.css" />
		<script type="text/javascript" src="${ctx}/script/build/yahoo-dom-event/yahoo-dom-event.js"></script>
		<script type="text/javascript" src="${ctx}/script/build/connection/connection-min.js"></script>
		<script type="text/javascript" src="${ctx}/script/build/animation/animation-min.js"></script>
		<script type="text/javascript" src="${ctx}/script/build/datasource/datasource-min.js"></script>
		<script type="text/javascript" src="${ctx}/script/build/autocomplete/autocomplete-min.js"></script>
		<script type="text/javascript" src="${ctx}/script/jquery-1.6.1.js"></script>
<link href="${ctx}/css/messageHandler.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/script/messageHandler.js"></script>
<script type="text/javascript">
$(document).ready(function()  {	
			<c:if test="${not empty actionErrors}">
				<c:forEach var="actionError" items="${actionErrors}">					
					showMessage("${actionError}","error");
				</c:forEach>
				<c:set var="actionErrors" value="" scope="session" />
			</c:if>
			<c:if test="${not empty successMessage}">
				<c:forEach var="message" items="${successMessage}">
					showMessage("${message}","ok");
				</c:forEach>
				<c:set var="successMessage" value="" scope="session" />
			</c:if>
});

</script> 
	<script>
	  function cancelForm()
	  {
		  window.location="${ctx}/homePage.htm";
	  }
	</script>

	<!--begin custom header content for this example-->
	<style type="text/css">
	#myAutoComplete {
	    width:22em; /* set width here or else widget will expand to fit its container */
	    padding-bottom:2em;
		z-index:9000;
	}
	</style>

	</style>

		<style type="text/css">
		#global {   margin-left: auto;   margin-right: auto;   width: 700px;   text-align: left; /* on rétablit l'alignement normal du texte */ }
		.global2 {   margin-left: auto;   margin-right: auto;   width: 300px;   text-align: left; /* on rétablit l'alignement normal du texte */ }
		.info, .success, .warning, .error, .validation {  
			border: 1px solid;  
			margin: 10px 0px;  
			padding:15px 10px 15px 60px;  
			background-repeat: no-repeat;  
			background-position: 10px center;
		}  
		.info {  
			color: #00529B;  
			background-color: #BDE5F8;  
			background-image: url('images/info.png');  
		}  
		.success {  
			color: #4F8A10;  
			background-color: #DFF2BF;  
			background-image:url('images/success2.png');  
		}  
		.warning {  
			color: #9F6000;  
			background-color: #FEEFB3;  
			background-image: url('images/warning.png');  
		}  
		.error {  
			color: #D8000C;  
			background-color: #FFBABA;  
			background-image: url('../images/error.png');  
		}  
		.validation {  
			color: #D63301;  
			background-color: #FFCCBA;  
			background-image: url('images/validation.png');  
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
		</style>

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
    </style>

 <script type="text/javascript">
	$(function() {
	$('#datepicker1').datepicker({
		showOn: 'button', buttonImage: 'images/calbtn.gif', buttonImageOnly: false,
	changeMonth: true,
		changeYear: true
	});
	});

	$(function() {
		$('#datepicker2').datepicker({
			showOn: 'button', buttonImage: 'images/calbtn.gif', buttonImageOnly: false,
		changeMonth: true,
			changeYear: true
		});
		});

		$(function() {
		$('#datepicker3').datepicker({
			showOn: 'button', buttonImage: 'images/calbtn.gif', buttonImageOnly: false,
		changeMonth: true,
			changeYear: true
		});
		});
</script>

<h2 style="font-size: 12px;font-family: Tahoma;font-weight: bold;border-bottom:2px solid #3399CC;padding:0 0 4px 0;margin:10px 0 0 0;">
<c:choose>
			<c:when test='${declaration.id == null }'>
				New declaration
			</c:when>
			<c:otherwise>
				Declaration ${declaration.customer}  ${declaration.exercise}
			</c:otherwise>
		</c:choose>
</h2>
<br/>

<div id="global">

<c:if test="${not empty alreadyRegisteredErrorMessage}">
				<div style="width: 60%">
				<div class="validation">
					<li><span style="color: blue;"><c:out value="${alreadyRegisteredErrorMessage}" escapeXml="false" /></span></li>
				</div>
				</div>
			<c:set var="alreadyRegisteredErrorMessage" value="" scope="session" />
		</c:if>

		<c:if test="${not empty companyNameErrorMessage}">
				<div style="width: 60%">
				<div class="validation">
					<li><span style="color: blue;"><c:out value="${companyNameErrorMessage}" escapeXml="false" /></span></li>
				</div>
				</div>
			<c:set var="companyNameErrorMessage" value="" scope="session" />
		</c:if>
<form action="${ctx}/showDeclarationRegisterPage.htm" method="post" class="niceform">
 <fieldset style="background-color: #eee;">
    	<table border="0" width="100%">
		<tr><td align="center">
		<span  style="color:black;font-weight: bold;">
				<c:choose>
			<c:when test='${declaration.id == null }'>
				New declaration
			</c:when>
			<c:otherwise>
				Declaration ${declaration.customer}  ${declaration.exercise}
			</c:otherwise>
		</c:choose>
		</span>
			</td>
		</tr>
	</table>
    </fieldset>
<fieldset style="background-color: #eee;">
    	<legend>
		<span  style="color:black;font-weight: bold;">Properties</span>
		</legend>

		
        	<dl class="global2">
				<label style="color:#039;" for="password">Customer reference:</label>
				
				<spring:bind path="declaration.customer" >
					<div id="myAutoComplete">   
					    <input style="width:350px;" type="text" name="${status.expression}"  id="customer" value="${status.value}"/>
						<div id="myContainer"></div>
					</div> 
				</spring:bind>
				<input  name="missionId" id="missionId" type="hidden">
				
			</dl>


			<dl class="global2">
				<label style="color:#039;" for="password">Request date:</label><span  style="color:red;font-weight: bold;"> (mm / dd / YYYY)</span>
				<div>
				<spring:bind path="declaration.requestDate" >
					<input  style="width:250px;" type="text" name="${status.expression}" id="datepicker1" value="${status.value}">
					<c:if test="${not empty status.errorMessage}">
					<span style="color: red;">${status.errorMessage}</span>
					</c:if>
				</spring:bind>
				</div>
			</dl>

			<dl class="global2">
				<label style="color:#039;" for="password">Receipt date:</label><span  style="color:red;font-weight: bold;"> (mm / dd / YYYY)</span>
				<div>
					<spring:bind path="declaration.receiptDate" >
						<input  style="width:250px;" type="text" name="${status.expression}" id="datepicker2" value="${status.value}">
						<c:if test="${not empty status.errorMessage}">
						<span style="color: red;">${status.errorMessage}</span>
						</c:if>
					</spring:bind>
				</div>
			</dl>
			<dl class="global2">

				<label style="color:#039;" for="password">Validity date :</label><span  style="color:red;font-weight: bold;"> (mm / dd / YYYY)</span>
				<div>
					<spring:bind path="declaration.validityDate" >
						<input  style="width:250px;" type="text" name="${status.expression}" id="datepicker3" value="${status.value}">
						<c:if test="${not empty status.errorMessage}">
						<span style="color: red;">${status.errorMessage}</span>
						</c:if>
					</spring:bind>
				</div>
			</dl>
		 
            
		

    </fieldset>

    
    <fieldset style="background-color: #eee;">
    	<legend><span  style="color:black;font-weight: bold;">Administration</span></legend>
		<table border="0" width="100%">
			<tr><td align="center">
				<label style="color:#039;" for="gender">Exercise:</label>
            
				<spring:bind path="declaration.exercise" >
					<select  name="${status.expression}">
					<c:forEach var="option" items="${exerciseOptions}" varStatus="loop">
						<option value="${option.id}" 
							<c:if test="${option.id == status.value}">selected</c:if>>
							${option.name}
						</option>
					</c:forEach>
					</select>
				</spring:bind>

				<label style="color:#039;" for="password">Active:</label>
				<spring:bind path="declaration.active" >
					<input type="checkbox" name="${status.expression}" id="${status.expression}" value="true" 
					<c:if test='${status.value == true }'>checked</c:if> />
					<span style="color: red;">${status.errorMessage}</span>
				</spring:bind>

				<label style="color:#039;" for="email">Declaration:</label>
            
				<spring:bind path="declaration.declaration" >
					<input type="checkbox" name="${status.expression}" id="${status.expression}" value="true" 
					<c:if test='${status.value == true }'>checked</c:if> />
					<span style="color: red;">${status.errorMessage}</span>
				</spring:bind>
        	<label style="color:#039;" for="email">Passport:</label>
            
				<spring:bind path="declaration.passport" >
					<input type="checkbox" name="${status.expression}" id="${status.expression}" value="true" 
					<c:if test='${status.value == true }'>checked</c:if> />
					<span style="color: red;">${status.errorMessage}</span>
				</spring:bind>

			</td></tr>

		</table>

		<dl>
			
		</dl>
        
    </fieldset>
    <fieldset style="background-color: #eee;">
    	<table border="0" width="100%">
		<tr><td align="center">
				<input style="width:100px;
				" type="button" name="cancel" id="cancel" class="button120"  value="Cancel" onclick="cancelForm()" />


				<c:if test='${context.currentUser.position.partner eq true || context.currentUser.position.managerMission  eq true || context.currentUser.position.secretaire  eq true }'>
				<input style="width:100px;
				" type="submit" name="submit" id="submit" class="button120"  value="Submit" />
				</c:if>
			</td>
		</tr>
	</table>
    </fieldset>
</form>

</div>
<h2 style="font-size: 12px;font-family: Tahoma;font-weight: bold;border-bottom:2px solid #3399CC;padding:0 0 4px 0;margin:10px 0 0 0;">
</h2>