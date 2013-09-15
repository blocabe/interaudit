<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions"%>

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

function newCustomerForm(){
	 window.location="${ctx}/showCustomerRegisterPage.htm";
}

function newOriginForm(){
	
     var origin= document.listForm.new_origine.value;
	 var id=${ param['id']};
	 window.location="${ctx}/registerNewOriginPage.htm?origin="+origin+"&id="+id;
}


  function showCustomer() {
	    var id = document.listForm.id.value;    
	    var url ="${ctx}/showCustomerRegisterPage.htm?id=";
	    window.location = url + id;
	   }
</script>

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
			<c:when test='${customer.id == null }'>
				Nouveau client
			</c:when>
			<c:otherwise>
				Client : ${customer.companyName}
			</c:otherwise>
		</c:choose>
</h2>
<br/>
<c:if test="${customer.id != null}">
    <div class="nav_alphabet" style="background-color: rgb(248, 246, 233); border: 1px solid  #0066aa; text-align:center; padding-top:1pt; font: bold 12px Verdana, sans-serif;">
	
        <form name="listForm" action="" method="post" >   
						<input style="width:100px;" type="button" name="newCustomer" id="newCustomer" class="button120"  value="New Customer" onclick="newCustomerForm()"/>
                        <span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Change Customer</strong> :</span>
                        <select style="font:10px Verdana, sans-serif;margin-right:10pt;" name="id" id="id" onchange="showCustomer();">                  
                              <c:forEach var="y" items="${allCustomerOptions}">
                                <option value="${y.id}"<c:if test='${customer.id==y.id}'> selected</c:if>>${fn:substring(fn:toUpperCase(y.name), 0, 19)}</option>
                              </c:forEach>
                        </select> 
						 <span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>New Origine</strong> :</span>	
						<input style="width:350px;" type="text" size="60" i="new_origine" name="new_origine"> 
						<input style="width:100px;" type="button" name="newOrigin" id="newOrigin" class="button120"  value="Add Origine" onclick="newOriginForm()"/>						
		 </form>
    </div>
	
</c:if>


<div id="global">
<form  action="${ctx}/showCustomerRegisterPage.htm" method="post" class="niceform">
<fieldset style="background-color: #eee;">
    	<table border="0" width="100%">
		<tr><td align="center">
				<span  style="color:black;font-weight: bold;">
		<c:choose>
			<c:when test='${customer.id == null }'>
				Nouveau client
			</c:when>
			<c:otherwise>
				Client : ${customer.companyName}
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
				<label style="color:#039;" for="password">Code:</label>
					<div>
						<spring:bind path="customer.code" >
							<input style="width:350px;" type="text" name="${status.expression}" value="${status.value}">
							<span style="color: red;">${status.errorMessage}</span>
							<c:if test="${not empty codeErrorMessage}">
								<span style="color: red;"><c:out value="${codeErrorMessage}" escapeXml="false" /></span>
								<c:set var="codeErrorMessage" value="" scope="session" />
							</c:if>
						</spring:bind>
					</div>
		</dl>
		<dl class="global2">
				<label style="color:#039;" for="password">Company Name:</label>
				<div>
					<spring:bind path="customer.companyName" >
						<input style="width:350px;" type="text" size="60" name="${status.expression}" value="${status.value}">
						<span style="color: red;">${status.errorMessage}</span>

						<c:if test="${not empty companyNameErrorMessage}">
							<span style="color: red;"><c:out value="${companyNameErrorMessage}" escapeXml="false" /></span>
							<c:set var="companyNameErrorMessage" value="" scope="session" />
						</c:if>
					</spring:bind>
				</div>
		</dl>
		<dl class="global2">
			<label style="color:#039;" for="password">Activity:</label>
            <div>
			<spring:bind path="customer.mainActivity" >
				 <textarea name="${status.expression}" id="comments" rows="2" cols="42">${status.value}</textarea>
					<span style="color: red;">${status.errorMessage}</span>
				</spring:bind>
			</div>
		</dl>
        	
			
		 </dl>
    </fieldset>
    
    <fieldset style="background-color:#eee;">
        <legend>
        <span  style="color:black;font-weight: bold;">
        Contract default values
        </span>
        </legend>

            <dl class="global2">         
            <div> 
                <label style="color:#039;" for="mission">Type :</label>
                <spring:bind path="customer.defaultContractType.id" >
                    <select style="width:250px;" name="${status.expression}">
                    <option value="-1">Choose an option...</option>
                    <c:forEach var="option" items="${missionOptions}" varStatus="loop">                        
                        <option value="${option.id}" 
                            <c:if test="${option.id == status.value}">selected</c:if>>
                            ${option.name} 
                        </option>
                    </c:forEach>
                    </select>

					<c:if test="${not empty invalidContracttypeErrorMessage}">
							<span style="color: red;"><c:out value="${invalidContracttypeErrorMessage}" escapeXml="false" /></span>
							<c:set var="invalidContracttypeErrorMessage" value="" scope="session" />
						</c:if>
                </spring:bind>
            </div> 
         </dl>
         
         <dl class="global2">
         <label style="color:#039;" for="amount">Amount per year: <span  style="color:red;"> [ use . as decimal separator ]</span></label>
            <div> 
                <spring:bind path="customer.defaultContractAmount" >
                    <input style="width:220px;" type="text" name="${status.expression}" value="${status.value}">
                    <c:if test="${not empty status.errorMessage}">
                    <span style="color: red;">Invalid amount</span>
                    </c:if>

                    <c:if test="${not empty invalidAmountFormatErrorMessage}">
                        <span style="color: red;"><c:out value="${invalidAmountFormatErrorMessage}" escapeXml="false" /></span>
                        <c:set var="invalidAmountFormatErrorMessage" value="" scope="session" />
                    </c:if>
                </spring:bind> 
				<select  name="currency">
					 <option value="EUR" selected>Euro</option>
					</select>
            </div> 
         </dl>
</fieldset>

    <fieldset style="background-color: #eee;">
    	<legend><span  style="color:black;font-weight: bold;">Contact</span></legend>
		 <dl>

		 <dl class="global2">
				<label style="color:#039;" for="email">Email Address:</label>
				<div>
					<spring:bind path="customer.email" >
						<input style="width:350px;" type="text" size="40" name="${status.expression}" value="${status.value}">
						<span style="color: red;">${status.errorMessage}</span>

						<c:if test="${not empty emailErrorMessage}">
							<span style="color: red;"><c:out value="${emailErrorMessage}" escapeXml="false" /></span>
							<c:set var="emailErrorMessage" value="" scope="session" />
						</c:if>
					</spring:bind>
				</div>
		</dl>
		<dl class="global2">
			
				<label style="color:#039;" for="email">Web Address:</label>
				<div>
					<spring:bind path="customer.companyWebAddr" >
						<input style="width:350px;" type="text" name="${status.expression}" value="${status.value}">
						<span style="color: red;">${status.errorMessage}</span>
					</spring:bind>
				</div>
		</dl>
		<dl class="global2">
				<label style="color:#039;" for="email">Phone:</label>
				<div>
					<spring:bind path="customer.phone" >
						<input style="width:350px;" size="30" type="text" name="${status.expression}" value="${status.value}">
						<span style="color: red;">${status.errorMessage}</span>
					</spring:bind>
				</div>
		</dl>
		<dl class="global2">
			<label style="color:#039;" for="fax">Fax:</label>
			<div>
				<spring:bind path="customer.fax" >
					<input style="width:350px;" size="30" type="text" name="${status.expression}" value="${status.value}">
					<span style="color: red;">${status.errorMessage}</span>
				</spring:bind>
			</div>
		</dl>
		<dl class="global2">
				<label style="color:#039;" for="mainAddress">Address:</label>
				<div>
					<spring:bind path="customer.mainAddress" >
					 <textarea name="${status.expression}" id="comments" rows="2" cols="42">${status.value}</textarea>
						<span style="color: red;">${status.errorMessage}</span>
					</spring:bind>
				</div>
		</dl>
		<dl class="global2">
				<label style="color:#039;"  for="mainBillingAddress">Bill Address:</label>
				<div>
					<spring:bind path="customer.mainBillingAddress" >
					 <textarea name="${status.expression}" id="comments" rows="2" cols="42">${status.value}</textarea>
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
				
			
    
			<label style="color:#039;" for="gender">Manager:</label>            
            	<spring:bind path="customer.customerManager.id" >
					<select  name="${status.expression}">
					<option value="-1" selected>Choose an option...</option>
						<c:forEach var="option" items="${managerOptions}" varStatus="loop">
							<option value="${option.id}" 
								<c:if test="${option.id == status.value}">selected</c:if>>
								${option.name}
							</option>
						</c:forEach>
						
						<c:forEach var="option" items="${directorsAsOptions}" varStatus="loop">
                        <option value="${option.id}" 
                            <c:if test="${option.id == status.value}">selected</c:if>>
                            ${option.name}
                        </option>
                    </c:forEach>
					</select>
					
					<c:if test="${not empty invalidCustomerManagerErrorMessage}">
						<span style="color: red;"><c:out value="${invalidCustomerManagerErrorMessage}" escapeXml="false" /></span>
						<c:set var="invalidCustomerManagerErrorMessage" value="" scope="session" />
					</c:if>
				</spring:bind>
            
			&nbsp;
			<label style="color:#039;" for="gender">Associate:</label>
			<spring:bind path="customer.associeSignataire.id" >
					<select  name="${status.expression}">
					<option value="-1" selected>Choose an option...</option>
					<c:forEach var="option" items="${associateOptions}" varStatus="loop">
						<option value="${option.id}" 
							<c:if test="${option.id == status.value}">selected</c:if>>
							${option.name}
						</option>
					</c:forEach>
					
					<c:forEach var="option" items="${directorsAsOptions}" varStatus="loop">
                        <option value="${option.id}" 
                            <c:if test="${option.id == status.value}">selected</c:if>>
                            ${option.name}
                        </option>
                    </c:forEach>
					
					</select>
					
					<c:if test="${not empty invalidCustomerAssociateErrorMessage}">
						<span style="color: red;"><c:out value="${invalidCustomerAssociateErrorMessage}" escapeXml="false" /></span>
						<c:set var="invalidCustomerAssociateErrorMessage" value="" scope="session" />
					</c:if>
				</spring:bind>
			</td></tr>

			<tr><td align="center">
				<label style="color:#039;" for="gender">Origin:</label>
            
				<spring:bind path="customer.origin.id" >
					<select  style="margin-right:0px;" name="${status.expression}">
					<option value="-1" selected>Choose an option...</option>
					<c:forEach var="option" items="${originOptions}" varStatus="loop">
						<option value="${option.id}" 
							<c:if test="${option.id == status.value}">selected</c:if>>
							${option.name}
						</option>
					</c:forEach>
					</select>
					
					<c:if test="${not empty invalidCustomerOriginErrorMessage}">
						<span style="color: red;"><c:out value="${invalidCustomerOriginErrorMessage}" escapeXml="false" /></span>
						<c:set var="invalidCustomerOriginErrorMessage" value="" scope="session" />
					</c:if>
				</spring:bind>
            
			&nbsp;
			<label style="color:#039" for="gender">Country:</label>

            	<spring:bind path="customer.country" >
					<select  style="margin-left:10px;" name="${status.expression}">
					<option value="-1" selected>Choose an option...</option>
					<c:forEach var="option" items="${countryOptions}">
						<option value="${option.id}" 
						<c:if test="${option.id == status.value}">selected</c:if>>${option.name}
						</option>
					</c:forEach>
					</select>
					
					<c:if test="${not empty invalidCustomerCountryErrorMessage}">
						<span style="color: red;"><c:out value="${invalidCustomerCountryErrorMessage}" escapeXml="false" /></span>
						<c:set var="invalidCustomerCountryErrorMessage" value="" scope="session" />
					</c:if>
				</spring:bind>
			</td></tr>

			<tr><td align="center">

			<label style="color:#039;" for="active">Is active:</label>
            
				<spring:bind path="customer.active" >
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
			</td></tr>
		</table> 
    </fieldset>
</form>

</div>
<h2 style="font-size: 12px;font-family: Tahoma;font-weight: bold;border-bottom:2px solid #3399CC;padding:0 0 4px 0;margin:10px 0 0 0;"></h2>