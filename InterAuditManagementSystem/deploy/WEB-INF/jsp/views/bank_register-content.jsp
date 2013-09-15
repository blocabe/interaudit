<jsp:directive.page contentType="text/html;charset=UTF-8" />
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions"%>

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

<h2 style="font-size: 12px;font-family: Tahoma;font-weight: bold;border-bottom:2px solid #3399CC;padding:0 0 4px 0;margin:10px 0 0 0;">
<c:choose>
			<c:when test='${bank.id == null }'>
				New bank
			</c:when>
			<c:otherwise>
				${bank.name} 
			</c:otherwise>
		</c:choose>
</h2>
<br/>
<div id="global">
<form action="${ctx}/showBankRegisterPage.htm" method="post" class="niceform">
 <fieldset style="background-color: #eee;">
    	<table border="0" width="100%">
		<tr><td align="center">
		<span  style="color:black;font-weight: bold;">
				<c:choose>
			<c:when test='${bank.id == null }'>
				New bank
			</c:when>
			<c:otherwise>
				${bank.name} 
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
			<label style="color:#039;" for="password">Bank Name:</label>
				<div>
				<spring:bind path="bank.name" >
					<input style="width:300px;" type="text" name="${status.expression}" value="${status.value}">
					<span style="color: red;">${status.errorMessage}</span>
				</spring:bind>
				</div>
		 </dl>
		 <dl class="global2">
			<label style="color:#039;" for="password">Code:</label>
				<div>
				<spring:bind path="bank.code" >
					<input style="width:300px;" type="text" name="${status.expression}" value="${status.value}">
					<span style="color: red;">${status.errorMessage}</span>
					<c:if test="${not empty codeErrorMessage}">
						<span style="color: red;"><c:out value="${codeErrorMessage}" escapeXml="false" /></span>
						<c:set var="codeErrorMessage" value="" scope="session" />
					</c:if>
				</spring:bind>
			</div>
		 </dl>
		 
		 <dl class="global2">
				<label style="color:#039;" for="password">Account:</label>
				<div>
				<spring:bind path="bank.accountNumber" >
					<input style="width:300px;" type="text" name="${status.expression}" value="${status.value}">
					<span style="color: red;">${status.errorMessage}</span>

					<c:if test="${not empty accountNumberErrorMessage}">
						<span style="color: red;"><c:out value="${accountNumberErrorMessage}" escapeXml="false" /></span>
						<c:set var="accountNumberErrorMessage" value="" scope="session" />
					</c:if>
				</spring:bind>
				</div>
			</dl>
			
			<dl class="global2">
                <label style="color:#039;" for="codeBic">Code bic:</label>
                <div>
                <spring:bind path="bank.codeBic" >
                    <input style="width:300px;" type="text" name="${status.expression}" value="${status.value}">
                    <span style="color: red;">${status.errorMessage}</span>                    
                </spring:bind>
                </div>
            </dl>
        
    </fieldset>

    <fieldset style="background-color: #eee;">
    	<legend><span  style="color:black;font-weight: bold;">Contact</span></legend>

		<dl class="global2">
			<label style="color:#039;" for="email">Contact person:</label>
            <div>
				<spring:bind path="bank.contactPerson" >
					<input style="width:300px;" type="text" name="${status.expression}" value="${status.value}">
					<span style="color: red;">${status.errorMessage}</span>
				</spring:bind>
			</div>
		</dl>

		<dl class="global2">
        	<label style="color:#039;" for="email">Email Address:</label>
			 <div>
				<spring:bind path="bank.contactPersonEmail" >
					<input style="width:300px;" type="text" name="${status.expression}" value="${status.value}">
					<span style="color: red;">${status.errorMessage}</span>
					<c:if test="${not empty emailErrorMessage}">
						<span style="color: red;"><c:out value="${emailErrorMessage}" escapeXml="false" /></span>
						<c:set var="emailErrorMessage" value="" scope="session" />
					</c:if>
				</spring:bind>
			 </div>
		</dl>
			
		<dl class="global2">
        	<label style="color:#039;" for="email">Phone:</label>
            <div>
				<spring:bind path="bank.contactPersonPhone" >
					<input style="width:300px;" type="text" name="${status.expression}" value="${status.value}">
					<span style="color: red;">${status.errorMessage}</span>
				</spring:bind>
			</div>
		</dl>
			
		<dl class="global2">
			<label style="color:#039;" for="email">Fax:</label>
			<div>
				<spring:bind path="bank.contactPersonFax" >
					<input style="width:300px;" type="text" name="${status.expression}" value="${status.value}">
					<span style="color: red;">${status.errorMessage}</span>
				</spring:bind>
			</div>
        </dl>
        
    </fieldset>
    <fieldset style="background-color: #eee;">
    	<legend><span  style="color:black;font-weight: bold;">Administration</span></legend>
		 <dl>
		 <table border="0" width="100%">
		<tr><td align="center">
			<label style="color:#039;" for="email">Is active:</label>
            
				<spring:bind path="bank.active" >
					<input type="checkbox" name="${status.expression}" id="${status.expression}" value="true" 
					<c:if test='${status.value == true }'>checked</c:if> />
					<span style="color: red;">${status.errorMessage}</span>
				</spring:bind>
		</td></tr>
		</table>
        	
			
        </dl>
    </fieldset>
    <fieldset style="background-color: #eee;">
    	<table border="0" width="100%">
		<tr><td align="center">
				<input style="width:100px;
				" type="button" name="cancel" id="cancel" class="button120"  value="Cancel" onclick="cancelForm()"/>

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