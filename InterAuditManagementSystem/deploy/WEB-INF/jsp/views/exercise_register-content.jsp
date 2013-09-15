
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions"%>

<link type="text/css" href="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/themes/base/ui.all.css" rel="stylesheet" />
	<script type="text/javascript" src="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/jquery-1.3.2.js"></script>
	<script type="text/javascript" src="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/ui/ui.core.js"></script>
	<script type="text/javascript" src="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/ui/ui.datepicker.js"></script>
	<link type="text/css" href="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/demos.css" rel="stylesheet" />

	<link href="css/odbb-skin.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="${ctx}/script/build/fonts/fonts-min.css" />
	<link rel="stylesheet" type="text/css" href="${ctx}/script/build/button/assets/skins/sam/button.css" />
	<link rel="stylesheet" type="text/css" href="${ctx}/script/build/container/assets/skins/sam/container.css" />
	<link rel="stylesheet" type="text/css" href="${ctx}/script/build/fonts/fonts-min.css" />
	<link rel="stylesheet" type="text/css" href="${ctx}/script/build/autocomplete/assets/skins/sam/autocomplete.css" />

	<script type="text/javascript" src="${ctx}/script/build/utilities/utilities.js"></script>
	<script type="text/javascript" src="${ctx}/script/build/button/button-min.js"></script>
	<script type="text/javascript" src="${ctx}/script/build/container/container-min.js"></script>
	<script type="text/javascript" src="${ctx}/script/build/yahoo/yahoo-min.js"></script>
	<script type="text/javascript" src="${ctx}/script/build/event/event-min.js"></script>
	<script type="text/javascript" src="${ctx}/script/build/connection/connection-min.js"></script>
	<script type="text/javascript" src="${ctx}/script/build/json/json-min.js"></script>

	<script type="text/javascript" src="${ctx}/script/build/yahoo-dom-event/yahoo-dom-event.js"></script>
	<script type="text/javascript" src="${ctx}/script/build/animation/animation-min.js"></script>
	<script type="text/javascript" src="${ctx}/script/datasource/datasource-min.js"></script>
	<script type="text/javascript" src="${ctx}/script/build/autocomplete/autocomplete-min.js"></script>

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
</script>

<div id="container">
		<c:if test="${not empty exerciseErrorMessage}">
				<div style="width: 60%">
				<div class="validation">
					<li><span style="color: blue;"><c:out value="${exerciseErrorMessage}" escapeXml="false" /></span></li>
				</div>
				</div>
			<c:set var="exerciseErrorMessage" value="" scope="session" />
		</c:if>
<form action="${ctx}/showExerciseRegisterPage.htm" method="post" >

<fieldset style="background-color: #eee;">

	<table border="0" width="100%">
		<tr><td align="center">
				<span  style="color:purple;font-weight: bold;">
		<c:choose>
			<c:when test='${exercise.id == null }'>
				
			</c:when>
			<c:otherwise>
			 
				Update
			</c:otherwise>
		</c:choose>

		Exercise ${exercise.year}
		</span>
			</td>
		</tr>
	</table> 
    	
    </fieldset>
<fieldset style="background-color: #eee;">
    	<legend>
		<span  style="color:purple;font-weight: bold;">
		Properties
		</span>
		</legend>
		

		<table border="0" width="100%">
				<tr><td align="center">
					<label for="exercise">Exercise: </label>
            	<spring:bind path="exercise.year" >
					<input type="text" name="${status.expression}" value="${status.value}" READONLY>
				</spring:bind>
				&nbsp;
			<label for="percentage">Inflation </label>
            <spring:bind path="exercise.inflationPercentage" >
					<input type="text" name="${status.expression}" value="${status.value}">
					<span style="color: red;">${status.errorMessage}</span>
				</spring:bind>
			&nbsp;	(%)
				</td></tr>
			</table> 
		
		
    </fieldset>

	<fieldset style="background-color: #eee;">
	<legend><span  style="color:purple;font-weight: bold;">Budget</span></legend>
	<table border="0" width="100%">
				
				<tr><td align="center">
						<label for="defAmount">Budget initial </label>
						<spring:bind path="exercise.totalExpectedAmount" >
							<input style="width:100px;" type="text" name="${status.expression}" value="${status.value}" READONLY>
						</spring:bind>
					
				
					<label for="budgetreporte">Budget reporté</label>
					
						<spring:bind path="exercise.totalReportedAmount" >
							<input style="width:100px;" type="text" name="${status.expression}" value="${status.value}" READONLY>
						</spring:bind>

					
					<label for="budgetreporte">Budget total</label>
							<input style="width:100px;" type="text" name="${status.expression}" value="${status.value}" READONLY>
					
					<label for="facturation">Facturation</label>
					
						<spring:bind path="exercise.totalEffectiveAmount" >
							<input style="width:100px;" type="text" name="${status.expression}" value="${status.value}" READONLY>
						</spring:bind>
					</td>
				</tr>
			</table> 
	</fieldset>

  
    <fieldset style="background-color: #eee;">
    	<legend><span  style="color:purple;font-weight: bold;">Administration</span></legend>
			<table border="0" width="100%">
				<tr><td align="center">
						<label for="password">From date:</label>
            <spring:bind path="exercise.startDate" >
					<input style="width:100px;" type="text" name="${status.expression}" id="${status.expression}" value="${status.value}" readonly="readonly">
					<span style="color: red;">${status.errorMessage}</span>

					<c:if test="${not empty invaliddateErrorMessage}">
						<span style="color: red;"><c:out value="${invaliddateErrorMessage}" escapeXml="false" /></span>
					</c:if>
				</spring:bind>
			
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<label for="password">To date:</label>
            <spring:bind path="exercise.endDate" >
					<input style="width:100px;" type="text" name="${status.expression}" id="${status.expression}" value="${status.value}" readonly="readonly">
					<span style="color: red;">${status.errorMessage}</span>

					<c:if test="${not empty invaliddateErrorMessage}">
						<span style="color: red;"><c:out value="${invaliddateErrorMessage}" escapeXml="false" /></span>
						<c:set var="invaliddateErrorMessage" value="" scope="session" />
					</c:if>
				</spring:bind>
					
				</td></tr>
				<tr><td align="center">
					<c:if test='${exercise.id != null}'>
						<label for="email">Is Approved:</label>
						<spring:bind path="exercise.appproved" >
								<input type="checkbox" name="${status.expression}" id="${status.expression}" value="true" 
								<c:if test='${status.value == true }'>checked</c:if> />
								<span style="color: red;">${status.errorMessage}</span>
							</spring:bind>
					</c:if>
					<label for="exercise">Status:</label>
						<spring:bind path="exercise.status" >
							<input type="text" name="${status.expression}" value="${status.value}" READONLY>
						</spring:bind>	
				</td></tr>
			</table> 
    </fieldset>

    <fieldset style="background-color: #eee;">

	<table border="0" width="100%">
		<tr><td align="center">
				<input style="width:100px;
				" type="button" name="cancel" id="cancel" class="button120"  value="Cancel" />

				<input style="width:100px;
				" type="submit" name="submit" id="submit" class="button120"  value="Submit" />
			</td>
		</tr>
	</table> 
    	
    </fieldset>
</form>

</div>