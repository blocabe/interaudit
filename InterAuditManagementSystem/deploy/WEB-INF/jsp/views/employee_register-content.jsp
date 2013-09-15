
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page import="java.io.PrintWriter"%>

<link type="text/css" href="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/themes/base/ui.all.css" rel="stylesheet" />
	<script type="text/javascript" src="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/jquery-1.3.2.js"></script>
	<script type="text/javascript" src="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/ui/ui.core.js"></script>
	<script type="text/javascript" src="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/ui/ui.datepicker.js"></script>
	<link type="text/css" href="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/demos.css" rel="stylesheet" />
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
	<style type="text/css">
        /*
        * OK sous IE8, C2 et F3,
        * se positionne au-dessus du dialogue de z-index 1000,
        * se positionne juste en dessous de la ligne d'input.
        * Sous F3, si la fen�tre Firebug est ouverte
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

#global {   margin-left: auto;   margin-right: auto;   width: 700px;   text-align: left; /* on r�tablit l'alignement normal du texte */ }
.global2 {   margin-left: auto;   margin-right: auto;   width: 300px;   text-align: left; /* on r�tablit l'alignement normal du texte */ }
    </style>

 <script type="text/javascript">
	$(function() {
	$('#datepicker').datepicker({
		showOn: 'button', buttonImage: 'images/calbtn.gif', buttonImageOnly: false,
	changeMonth: true,
		changeYear: true
	});
	});
</script>

<script>
  function cancelForm()
  {
      window.location="${ctx}/homePage.htm";
  }

   function newEmployeeForm(){
	 window.location="${ctx}/showEmployeeRegisterPage.htm";
}

  function showEmployee() {
	    var id = document.listForm.id.value;    
	    var url ="${ctx}/showEmployeeRegisterPage.htm?id=";
	    window.location = url + id;
	   }
</script>

<h2 style="font-size: 12px;font-family: Tahoma;font-weight: bold;border-bottom:2px solid #3399CC;padding:0 0 4px 0;margin:10px 0 0 0;">
<c:choose>
			<c:when test='${employee.id == null }'>
				New employee
			</c:when>
			<c:otherwise>
				${employee.lastName}&nbsp; ${employee.firstName} 
			</c:otherwise>
		</c:choose>
</h2>
<br/>
<c:if test="${employee.id != null}">
    <div class="nav_alphabet" style="background-color: rgb(248, 246, 233); border: 1px solid  #0066aa; text-align:center; padding-top:1pt; font: bold 12px Verdana, sans-serif;">
        <form name="listForm" action="" method="post" >                 
                        <span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Contact</strong> :</span>
                        <select style="font:10px Verdana, sans-serif;margin-right:10pt;" name="id" id="id" onchange="showEmployee();">                  
                              <c:forEach var="y" items="${allEmployeeOptions}">
                                <option value="${y.id}"<c:if test='${employee.id==y.id}'> selected</c:if>>${fn:substring(fn:toUpperCase(y.name), 0, 19)}</option>
                              </c:forEach>
                        </select>    
						<input style="width:100px;" type="button" name="newCustomer" id="newCustomer" class="button120"  value="New" onclick="newEmployeeForm()"/>
        </form>
    </div>
</c:if>


<div id="global">
<form action="${ctx}/showEmployeeRegisterPage.htm" method="post" class="niceform">

	<fieldset style="background-color: #eee;" >
    	<table border="0" width="100%">
		<tr><td align="center">
				<span  style="color:black;font-weight: bold;">
		<c:choose>
			<c:when test='${employee.id == null }'>
				New employee
			</c:when>
			<c:otherwise>
				${employee.lastName}&nbsp; ${employee.firstName} 
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
				<label style="color:#039;"  for="lastName">LastName</label>
				<div>
					<spring:bind path="employee.lastName" >
						<input style="width:300px;"  type="text" size="30" name="${status.expression}" value="${status.value}">
						<span style="color: red;">${status.errorMessage}</span>
					</spring:bind>
				</div>
            </dl>
			<dl class="global2">
					<label style="color:#039;"  for="firstName">FirstName</label>
					<div>
						<spring:bind path="employee.firstName" >
							<input style="width:300px;"  type="text" size="30" name="${status.expression}" value="${status.value}">
							<span style="color: red;">${status.errorMessage}</span>
						</spring:bind>
					</div>
			</dl>
			<dl class="global2">
				<label style="color:#039;"  for="jobTitle">Job title</label>
				<div>
					<spring:bind path="employee.position.name" >
						<select style="width:300px;" name="${status.expression}" 		
						<c:if test='${context.currentUser.position.partner eq false }'>DISABLED</c:if>>
						<c:forEach var="option" items="${positionOptions}" varStatus="loop">
							<option value="${option.name}" 
								<c:if test="${option.name == status.value}">selected</c:if>>
								${option.name}
							</option>
						</c:forEach>
						</select>
					</spring:bind>
				</div>
			</dl>
			<dl class="global2">
				<label style="color:#039;" for="email">Email Address:</label>
				<div>
					<spring:bind path="employee.email" >
						<input style="width:300px;" type="text" size="50" name="${status.expression}" value="${status.value}">
						<span style="color: red;">${status.errorMessage}</span>
						<c:if test="${not empty emailErrorMessage}">
							<span style="color: red;"><c:out value="${emailErrorMessage}" escapeXml="false" /></span>
							<c:set var="emailErrorMessage" value="" scope="session" />
						</c:if>
					</spring:bind>
				</div>
			</dl>
			<dl class="global2">
				<label  style="color:#039;"  for="dateOfBirth">Hiring date</label><span  style="color:red;font-weight: bold;"> (mm / dd / YYYY)</span>
				<div>
					<spring:bind path="employee.dateOfHiring" >
						<input style="width:200px;" size="30" type="text" name="${status.expression}" id="datepicker" value="${status.value}">
						<span style="color: red;">${status.errorMessage}</span>
					</spring:bind>
					<c:if test="${not empty dateOfHiring}">
							<span style="color: red;"><c:out value="${dateOfHiring}" escapeXml="false" /></span>
							<c:set var="dateOfHiring" value="" scope="session" />
					</c:if>
				</div>
			</dl>
			
			<dl class="global2">
                <label  style="color:#039;"  for="dateOfBirth">Last working date</label><span  style="color:red;font-weight: bold;"> (mm / dd / YYYY)</span>
                <div>
                    <spring:bind path="employee.dateEndOfHiring" >
                        <input style="width:200px;" size="30" type="text" name="${status.expression}" id="datepicker2" value="${status.value}">
                        <span style="color: red;">${status.errorMessage}</span>
                    </spring:bind>
					<c:if test="${not empty dateEndOfHiring}">
							<span style="color: red;"><c:out value="${dateEndOfHiring}" escapeXml="false" /></span>
							<c:set var="dateEndOfHiring" value="" scope="session" />
					</c:if>
                </div>
            </dl>

			<dl class="global2">
			<label  style="color:#039;" for="mobile">Mobile:</label>
				<div>					
					<spring:bind path="employee.mobile" >
						<input style="width:300px;" type="text" name="${status.expression}" value="${status.value}">
						<span style="color: red;">${status.errorMessage}</span>
					</spring:bind>
				</div>
			</dl>
    </fieldset>

    <%--fieldset style="background-color: #eee;">
    	<legend><span  style="color:purple;font-weight: bold;">Contact</span></legend>

		 <dl class="global2">
				<label style="color:#039;" for="email">Email Address:</label>
				<div>
					<spring:bind path="employee.email" >
						<input style="width:300px;" type="text" size="50" name="${status.expression}" value="${status.value}">
						<span style="color: red;">${status.errorMessage}</span>
						<c:if test="${not empty emailErrorMessage}">
							<span style="color: red;"><c:out value="${emailErrorMessage}" escapeXml="false" /></span>
							<c:set var="emailErrorMessage" value="" scope="session" />
						</c:if>
					</spring:bind>
				</div>
			</dl>
			<dl class="global2">
				<label  style="color:#039;" for="country">Country:</label>
				<div>
            	<spring:bind path="employee.country" >
					<select  style="width:300px;"  name="${status.expression}">
					<c:forEach var="option" items="${countryOptions}">
						<option value="${option.id}" 
						<c:if test="${option.id == status.value}">selected</c:if>>${option.name}
						</option>
					</c:forEach>
					</select>
				</spring:bind>
				</div>
			</dl>
			<dl class="global2">
					<label  style="color:#039;" for="phone">Busines phone:</label>
					<div>
					<spring:bind path="employee.phone" >
						<input style="width:300px;"  type="text" name="${status.expression}" value="${status.value}">
						<span style="color: red;">${status.errorMessage}</span>
					</spring:bind>
					</div>
			</dl>
			<dl class="global2">
				<div>
				<label  style="color:#039;" for="personalPhone">phone:</label>
					<spring:bind path="employee.personalPhone" >
						<input style="width:100px;" type="text" name="${status.expression}" value="${status.value}">
						<span style="color: red;">${status.errorMessage}</span>
					</spring:bind>
					<label  style="color:#039;" for="personalMobile">mobile:</label>
					<spring:bind path="employee.personalMobile" >
						<input style="width:100px;" type="text" name="${status.expression}" value="${status.value}">
						<span style="color: red;">${status.errorMessage}</span>
					</spring:bind>
				</div>
			</dl>
			<dl class="global2">
				<label  style="color:#039;" for="Address"> Address:</label>&nbsp;&nbsp;
				<div>
            	<spring:bind path="employee.address" >
					<textarea name="${status.expression}" id="comments" rows="5" cols="40">${status.value}</textarea>
					<span style="color: red;">${status.errorMessage}</span>
				</spring:bind>
				</div> 
        	
        </dl>

		
		
		
		

		    
    </fieldset--%>
 
    <fieldset style="background-color: #eee;">
    	<legend><span  style="color:black;font-weight: bold;">Administration</span> </legend>
        <dl>

			<table border="0" width="100%">
			<tr><td align="center">
					<label  style="color:#039;" for="login">Login:</label>
				
					<spring:bind path="employee.login" >
						<input type="text" name="${status.expression}" value="${status.value}">
						<span style="color: red;">${status.errorMessage}</span>

						<c:if test="${not empty loginErrorMessage}">
							<span style="color: red;"><c:out value="${loginErrorMessage}" escapeXml="false" /></span>
							<c:set var="loginErrorMessage" value="" scope="session" />
						</c:if>
					</spring:bind>
				&nbsp;
				<label  style="color:#039;" for="password">Password:</label>
				<spring:bind path="employee.password" >
						<input type="password" name="${status.expression}" value="${status.value}">
						<span style="color: red;">${status.errorMessage}</span>
					</spring:bind>

				
				&nbsp;
					<label  style="color:#039;" for="email">Is active:</label>
				
					<spring:bind path="employee.userActive" >
						<input type="checkbox" name="${status.expression}" id="${status.expression}" value="true" 
						<c:if test='${status.value == true }'>checked</c:if> />
						<span style="color: red;">${status.errorMessage}</span>
					</spring:bind>
				
				</td>
			</tr>
		</table> 
        	
			
        </dl>

		<c:if test='${context.currentUser.position.partner eq true }'>

		<dl>
			<table border="0" width="100%">
			<tr><td align="center">
					<label  style="color:#039;" for="prixRevient">Prix.Revient:</label>
				
					<spring:bind path="employee.prixRevient" >
						<input style="width:40px;" type="text" name="${status.expression}" value="${status.value}" size="10">
					</spring:bind>
				
				&nbsp;
					<label style="color:#039;"  for="prixVente">Prix.Vente:</label>
					<spring:bind path="employee.prixVente" >
						<input style="width:40px;" type="text" name="${status.expression}" value="${status.value}" size="10">
					</spring:bind>
				&nbsp;
					<label  style="color:#039;" for="coutHoraire">Co�t.Horaire:</label>
					<spring:bind path="employee.coutHoraire" >
						<input style="width:40px;" type="text" name="${status.expression}" value="${status.value}" size="10">
					</spring:bind>
				</td>
			</tr>
			</table> 
			
		</dl>
		</c:if>

    </fieldset>
    
    <fieldset style="background-color: #eee;">
    	<table border="0" width="100%">
		<tr><td align="center">
				<input style="width:100px;
				" type="button" name="cancel" id="cancel" class="button120"  value="Cancel" onclick="cancelForm()"/>

                <c:if test='${context.currentUser.position.partner eq true || context.currentUser.position.managerMission  eq true || context.currentUser.position.secretaire  eq true || context.currentUser.id == employee.id}'>			
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