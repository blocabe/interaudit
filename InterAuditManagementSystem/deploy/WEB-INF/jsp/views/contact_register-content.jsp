<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions"%>



<link rel="stylesheet" type="text/css" href="${ctx}/script/build/autocomplete/assets/skins/sam/autocomplete.css" />
<script type="text/javascript" src="${ctx}/script/build/yahoo-dom-event/yahoo-dom-event.js"></script>
<script type="text/javascript" src="${ctx}/script/build/connection/connection-min.js"></script>
<script type="text/javascript" src="${ctx}/script/build/animation/animation-min.js"></script>
<script type="text/javascript" src="${ctx}/script/build/datasource/datasource-min.js"></script>
<script type="text/javascript" src="${ctx}/script/build/autocomplete/autocomplete-min.js"></script>
<script type="text/javascript" src="${ctx}/script/jquery-1.2.6.js"></script>
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

  function newContactForm(){
	 window.location="${ctx}/showContactRegisterPage.htm";
}

  function showContact() {
	var id = document.listForm.id.value;	
	var url ="${ctx}/showContactRegisterPage.htm?id=";
    window.location = url + id;
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

	<style type="text/css">
		#global {   margin-left: auto;   margin-right: auto;   width: 700px;   text-align: left; /* on r�tablit l'alignement normal du texte */ }
		.global2 {   margin-left: auto;   margin-right: auto;   width: 300px;   text-align: left; /* on r�tablit l'alignement normal du texte */ }
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

<h2 style="font-size: 12px;font-family: Tahoma;font-weight: bold;border-bottom:2px solid #3399CC;padding:0 0 4px 0;margin:10px 0 0 0;">
<c:choose>
					<c:when test='${contact.id == null }'>
						Nouveau contact
					</c:when>
					<c:otherwise>
						Contact : ${contact.lastName}
					</c:otherwise>
				</c:choose>
</h2>

<c:if test="${contact.id != null}">
	<div class="nav_alphabet" style="background-color: rgb(248, 246, 233); border: 1px solid  #0066aa; text-align:center; padding-top:1pt; font: bold 12px Verdana, sans-serif;">
		<form name="listForm" action="" method="post" >                 
		                <span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Contact</strong> :</span>
		                <select style="font:10px Verdana, sans-serif;margin-right:10pt;" name="id" id="id" onchange="showContact();">                  
		                      <c:forEach var="y" items="${allContactOptions}">
		                        <option value="${y.id}"<c:if test='${contact.id==y.id}'> selected</c:if>>${fn:substring(fn:toUpperCase(y.name), 0, 19)}</option>
		                      </c:forEach>
		                </select> 
						
						<input style="width:100px;" type="button" name="newCustomer" id="newCustomer" class="button120"  value="New" onclick="newContactForm()"/>
		</form>
	</div>
</c:if>


<div id="global">

<c:if test="${not empty companyNameErrorMessage}">
				<div style="width: 60%">
				<div class="validation">
					<li><span style="color: blue;"><c:out value="${companyNameErrorMessage}" escapeXml="false" /></span></li>
				</div>
				</div>
			<c:set var="companyNameErrorMessage" value="" scope="session" />
		</c:if>
<form action="${ctx}/showContactRegisterPage.htm" method="post" class="niceform">

	<fieldset style="background-color: #eee;">
    	<table border="0" width="100%">
		<tr><td align="center">
				<span  style="color:black;font-weight: bold;">
						<c:choose>
					<c:when test='${contact.id == null }'>
						Nouveau contact
					</c:when>
					<c:otherwise>
						Contact : ${contact.lastName}
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
			<label style="color:#039;" for="password">FirstName:</label>
            <div>
            	<spring:bind path="contact.firstName" >
					<input style="width:350px;" type="text" name="${status.expression}" value="${status.value}">
					<span style="color: red;">${status.errorMessage}</span>
				</spring:bind>
			</div>
		</dl>
		<dl class="global2">
        	<label style="color:#039;" for="password">LastName:</label>
            <div>
            	<spring:bind path="contact.lastName" >
					<input style="width:350px;" type="text" name="${status.expression}" value="${status.value}">
					<span style="color: red;">${status.errorMessage}</span>
				</spring:bind>
			</div>
		 </dl>
		 
		 
		 <dl class="global2">
		 
            <div> 
				<label style="color:#039;" for="mission">Sex :</label>
				<spring:bind path="contact.sex" >
					<select style="width:350px;" name="${status.expression}">
						<option value="F" <c:if test="${'F' == status.value}">selected</c:if>>Female</option>
						<option value="M" <c:if test="${'M' == status.value}">selected</c:if>>Male</option>
					</select>
				</spring:bind>
			</div> 
		 </dl>
		 
		 <dl class="global2">
			<label style="color:#039;" for="gender">Job title:</label>
            <div>
            	<spring:bind path="contact.jobTitle" >
					<input style="width:350px;" type="text" name="${status.expression}" value="${status.value}">
					<span style="color: red;">${status.errorMessage}</span>
				</spring:bind>
				
			</div>
		 </dl>

		 <dl class="global2">
			<label style="color:#039;" for="gender">Company</label>
            <spring:bind path="contact.company" >
					<div id="myAutoComplete">  
					    <input  style="width:350px;" size="100" type="text" name="${status.expression}"  id="customer" value="${status.value}"/>   
					    <div id="myContainer"></div>  
					</div>
				</spring:bind>
				<input  name="missionId" id="missionId" type="hidden">
		 </dl>
		 
		
    </fieldset>

	 

    <fieldset style="background-color: #eee;">
    	<legend><span  style="color:black;font-weight: bold;">Contact</span></legend>
			<dl class="global2">
				<label style="color:#039;" for="email">Email Address:</label>
				 <div>
            	<spring:bind path="contact.email" >
					<input style="width:350px;" type="text"size="70" name="${status.expression}" value="${status.value}">
					<span style="color: red;">${status.errorMessage}</span>

					<c:if test="${not empty emailErrorMessage}">
						<span style="color: red;"><c:out value="${emailErrorMessage}" escapeXml="false" /></span>
						<c:set var="emailErrorMessage" value="" scope="session" />
					</c:if>
				</spring:bind>
				 </div>
			</dl>

			<dl class="global2">
				<label style="color:#039;" for="email">Busines phone:</label>
				<div>
            	<spring:bind path="contact.businessPhone" >
					<input style="width:350px;" type="text" name="${status.expression}" value="${status.value}">
					<span style="color: red;">${status.errorMessage}</span>
				</spring:bind>
				</div>
			</dl>
			<dl class="global2">
				<label style="color:#039;" for="email">phone:</label>
				<div>
            	<spring:bind path="contact.personalPhone" >
					<input style="width:350px;" style="width:100px;" type="text" name="${status.expression}" value="${status.value}">
					<span style="color: red;">${status.errorMessage}</span>
				</spring:bind>
				</div>
			</dl>
			<dl class="global2">
            
				<label style="color:#039;" for="email">mobile:</label>
				<div>
				<spring:bind path="contact.personalMobile" >
					<input style="width:350px;" style="width:100px;" type="text" name="${status.expression}" value="${status.value}">
					<span style="color: red;">${status.errorMessage}</span>
				</spring:bind>
				</div>
			</dl>

        
    </fieldset>
    <fieldset style="background-color: #eee;">
	<legend><span  style="color:black;font-weight: bold;">Administration</span> </legend>
	<table border="0" width="100%">
		<tr><td align="center">
				<label style="color:#039;" for="email">Is active:</label>
            <spring:bind path="contact.active" >
					<input type="checkbox" name="${status.expression}" id="${status.expression}" value="${status.value}" 
					<c:if test='${status.value == true }'>checked</c:if> />
					<span style="color: red;">${status.errorMessage}</span>
				</spring:bind>
			</td></tr>
		</table>
    </fieldset>
    <fieldset style="background-color: #eee;">
    	<table border="0" width="100%">
		<tr><td align="center">
				<input style="width:100px;
				" type="button" name="cancel" id="cancel" class="button120"  value="Cancel"  onclick="cancelForm()"/>

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

<h2 style="font-size: 12px;font-family: Tahoma;font-weight: bold;border-bottom:2px solid #3399CC;padding:0 0 4px 0;margin:10px 0 0 0;"></h2>