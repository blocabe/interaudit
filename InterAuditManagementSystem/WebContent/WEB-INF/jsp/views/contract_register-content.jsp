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

   function newContractForm(){
	 window.location="${ctx}/showContractRegisterPage.htm";
	}

  function showContract() {
	    var id = document.listForm.id.value;    
	    var url ="${ctx}/showContractRegisterPage.htm?id=";
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

		$(function() {
		$('#datepicker4').datepicker({
			showOn: 'button', buttonImage: 'images/calbtn.gif', buttonImageOnly: false,
		changeMonth: true,
			changeYear: true
		});
		});
</script>


<h2 style="font-size: 12px;font-family: Tahoma;font-weight: bold;border-bottom:2px solid #3399CC;padding:0 0 4px 0;margin:10px 0 0 0;">
<c:choose>
			<c:when test='${contract.id == null }'>
				Nouveau contrat
			</c:when>
			<c:otherwise>
				Contrat : ${contract.reference}
			</c:otherwise>
		</c:choose>
</h2>
<br/>

<c:if test="${contract.id != null}">
    <div class="nav_alphabet" style="background-color: rgb(248, 246, 233); border: 1px solid  #0066aa; text-align:center; padding-top:1pt; font: bold 12px Verdana, sans-serif;">
        <form name="listForm" action="" method="post" >                 
                        <span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Contract</strong> :</span>
                        <select style="font:10px Verdana, sans-serif;margin-right:10pt;" name="id" id="id" onchange="showContract();">                  
                              <c:forEach var="y" items="${allContractOptions}">
                                <option value="${y.id}"<c:if test='${contract.id==y.id}'> selected</c:if>>${fn:substring(fn:toUpperCase(y.name), 0, 19)}</option>
                              </c:forEach>
                        </select>     
						<input style="width:100px;" type="button" name="newContract" id="newContract" class="button120"  value="New" onclick="newContractForm()"/>
        </form>
    </div>
</c:if>

<c:if test="${not empty successMessage}">
					<div style="width: 100%; align: center">
					<div class="success"><c:forEach var="message"
						items="${successMessage}">
						<li><span style="color: blue;"><c:out value="${message}" escapeXml="false" /></span></li>
					</c:forEach></div>
					</div>
					<c:set var="successMessage" value="" scope="session" />
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

					
<form action="${ctx}/showContractRegisterPage.htm" method="post">
 <fieldset style="background-color: #eee;">
    	<table border="0" width="100%">
		<tr><td align="center">
				<span  style="color:black;font-weight: bold;">
		<c:choose>
			<c:when test='${contract.id == null }'>
				Nouveau contrat
			</c:when>
			<c:otherwise>
				Reference du contrat : <span  style="color:blue;">${contract.reference}</span>
			</c:otherwise>
		</c:choose>
		</span>
			</td>
		</tr>
	</table>
    </fieldset>
<fieldset style="background-color:#eee;">
    	<legend>
		<span  style="color:black;font-weight: bold;">
		Contract properties
		</span>
		</legend>

		<dl class="global2">
				<label style="color:#039;" for="password">Customer:</label>
            	<spring:bind path="contract.customer.companyName" >
					<div id="myAutoComplete">   
					    <input style="width:350px;" type="text" name="${status.expression}"  id="customer" value="${status.value}" <c:if test='${contract.id != null }'>DISABLED</c:if>/>
						<div id="myContainer"></div>
					</div> 
				</spring:bind>
				<input  name="missionId" id="missionId" type="hidden">
		 </dl>

		

		<dl class="global2">
		 <label style="color:#039;" for="amount">Amount per year:</label>
            <div> 
            	<spring:bind path="contract.amount" >
					<input style="width:220px;" type="text" name="${status.expression}" value="${status.value}">
					<c:if test="${not empty status.errorMessage}">
					<span style="color: red;">Invalid amount</span>
					</c:if>

					<c:if test="${not empty invalidAmountFormatErrorMessage}">
						<span style="color: red;"><c:out value="${invalidAmountFormatErrorMessage}" escapeXml="false" /></span>
						<c:set var="invalidAmountFormatErrorMessage" value="" scope="session" />
					</c:if>
				</spring:bind>
			
			
				<spring:bind path="contract.currency" >
					<select  name="${status.expression}">
					 <option value="EUR" selected>Euro</option>
					</select>
				</spring:bind>
			</div> 
		 </dl>

		<dl class="global2">
		 <label style="color:#039;" for="password">Description:</label>
		 <div> 
			<spring:bind path="contract.description" >
			 <textarea name="${status.expression}" id="comments" rows="5" cols="42">${status.value}</textarea>
				<span style="color: red;">${status.errorMessage}</span>
			</spring:bind>
		</div> 
		 </dl>

</fieldset>


<fieldset style="background-color:#eee;">
    	<legend>
		<span  style="color:black;font-weight: bold;">
		Contract validity period
		</span>
		</legend>
		 
			<dl class="global2">
				<label style="color:#039;" for="password">Contract start date:</label>
				<spring:bind path="contract.fromDate" >
					<input style="width:100px;" type="text" name="${status.expression}" id="datepicker1" value="${status.value}">
					<span style="color: red;">${status.errorMessage}</span>

					<c:if test="${not empty invaliddateErrorMessage}">
						<span style="color: red;"><c:out value="${invaliddateErrorMessage}" escapeXml="false" /></span>
					</c:if>
				</spring:bind>
				<br/>
				<span  style="color:red;font-weight: bold;"> (mm / dd / YYYY)</span>
			 </dl>
			<dl class="global2">
				<label style="color:#039;" for="password">Contract end date:</label>
				<spring:bind path="contract.toDate" >
						<input style="width:100px;" type="text" name="${status.expression}" id="datepicker2" value="${status.value}">
						<span style="color: red;">${status.errorMessage}</span>

						<c:if test="${not empty invaliddateErrorMessage}">
							<span style="color: red;"><c:out value="${invaliddateErrorMessage}" escapeXml="false" /></span>
							<c:set var="invaliddateErrorMessage" value="" scope="session" />
						</c:if>
					</spring:bind>
					<br/>
					<span  style="color:red;font-weight: bold;"> (mm / dd / YYYY)</span>
			 </dl>
</fieldset>

<fieldset style="background-color:#eee;">
    	<legend>
		<span  style="color:black;font-weight: bold;">
		Mission properties
		</span>
		</legend>

			<dl class="global2">
		 
            <div> 
				<label style="color:#039;" for="mission">Type :</label>
				<spring:bind path="contract.missionType" >
					<!--select style="width:250px;" name="${status.expression}" <c:if test='${contract.id != null }'>DISABLED</c:if>-->
					<select style="width:250px;" name="${status.expression}">
					<c:forEach var="option" items="${missionOptions}" varStatus="loop">
						<option value="${option.id}" 
							<c:if test="${option.id == status.value}">selected</c:if>>
							${option.name} 
						</option>
					</c:forEach>
					</select>
				</spring:bind>
			</div> 
		 </dl>

		 <dl class="global2">
		 
            <div> 
				<label style="color:#039;" for="mission">Language :</label>
				<spring:bind path="contract.language" >
					<select style="width:250px;" name="${status.expression}">
						<option value="EN" <c:if test="${'EN' == status.value}">selected</c:if>>Anglais</option>
						<option value="FR" <c:if test="${'FR' == status.value}">selected</c:if>>Francais</option>
						<option value="DE" <c:if test="${'DE' == status.value}">selected</c:if>>Allemand</option>
					</select>
				</spring:bind>
			</div> 
		 </dl>
		 
			<%-- %>dl class="global2">
				<label style="color:#039;" for="password">Mission start date:</label>
				<spring:bind path="contract.startDateOfMission" >
					<input style="width:100px;" type="text" name="${status.expression}" id="datepicker3" value="${status.value}">
					<span style="color: red;">${status.errorMessage}</span>

					<c:if test="${not empty invaliddateErrorMessage}">
						<span style="color: red;"><c:out value="${invaliddateErrorMessage}" escapeXml="false" /></span>
					</c:if>
				</spring:bind>
			 </dl>
			<dl class="global2">
				<label style="color:#039;" for="password">Mission delivery date:</label>
				<spring:bind path="contract.dueDateOfMission" >
						<input style="width:100px;" type="text" name="${status.expression}" id="datepicker4" value="${status.value}">
						<span style="color: red;">${status.errorMessage}</span>

						<c:if test="${not empty invaliddateErrorMessage}">
							<span style="color: red;"><c:out value="${invaliddateErrorMessage}" escapeXml="false" /></span>
							<c:set var="invaliddateErrorMessage" value="" scope="session" />
						</c:if>
					</spring:bind>
			 </dl--%>
</fieldset>


    
    <fieldset style="background-color: #eee;">
    	<legend><span  style="color:black;font-weight: bold;">Administration</span></legend>
		

			<table border="0" width="100%">
			<tr>
			<td align="center">
			<label style="color:#039;" for="email">Contrat toujours valide:</label>
				<spring:bind path="contract.valid" >
					<input type="checkbox" name="${status.expression}" id="${status.expression}" value="true" 
					<c:if test='${status.value == true }'>checked</c:if> />
					<span style="color: red;">${status.errorMessage}</span>
				</spring:bind>
			</td>
			<td align="center">
			<label style="color:#039;" for="email">Accord pris avec client sur le montant:</label>
				<spring:bind path="contract.agreed" >
					<input type="checkbox" name="${status.expression}" id="${status.expression}" value="true" 
					<c:if test='${status.value == true }'>checked</c:if> />
					<span style="color: red;">${status.errorMessage}</span>
				</spring:bind>
			</td>
			
			<td align="center">
            <label style="color:#039;" for="email">Interim:</label>
                <spring:bind path="contract.interim" >
                    <input type="checkbox" name="${status.expression}" id="${status.expression}" value="true" 
                    <c:if test='${status.value == true }'>checked</c:if> />
                    <span style="color: red;">${status.errorMessage}</span>
                </spring:bind>
            </td>
			</tr>
		</table>

		
        <dl>
        	
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
