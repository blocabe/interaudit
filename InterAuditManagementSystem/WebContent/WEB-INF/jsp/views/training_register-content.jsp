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

<form action="${ctx}/showTrainingRegisterPage.htm" method="post" class="niceform">
<fieldset>
    	<legend>Training details</legend>

		<dl>
        	<dt><label for="password">Title:</label></dt>
            <dd>
				<spring:bind path="training.title" >
					<input type="text" name="${status.expression}" value="${status.value}"><img name="surNamerequired" src="images/ast.gif">
					<span style="color: red;">${status.errorMessage}</span>
				</spring:bind>
			</dd>
		 </dl>
        <dl>
        	<dt><label for="password">Description:</label></dt>
            <dd>
				<spring:bind path="training.description" >
					<textarea name="${status.expression}" value="${status.value}" rows="3" cols="60">${status.value}</textarea><img name="surNamerequired" src="images/ast.gif">
					<span style="color: red;">${status.errorMessage}</span>
				</spring:bind>
			</dd>
		 </dl>

		 <dl>
			<dt><label for="password">Start  date:</label></dt>
            <dd><spring:bind path="training.startDate" >
					<input type="text" name="${status.expression}" id="datepicker1" value="${status.value}">
					<img name="surNamerequired" src="images/ast.gif">
					<span style="color: red;">${status.errorMessage}</span>
				</spring:bind>
			</dd>
		</dl>

		<dl>
			<dt><label for="password">End  date:</label></dt>
            <dd><spring:bind path="training.endDate" >
					<input type="text" name="${status.expression}" id="datepicker2" value="${status.value}">
					<img name="surNamerequired" src="images/ast.gif">
					<span style="color: red;">${status.errorMessage}</span>
				</spring:bind>
			</dd>
		</dl>

		<dl>
			<dt><label for="password">Company:</label></dt>
            <dd><spring:bind path="training.companyName" >
					<input type="text" name="${status.expression}" value="${status.value}"><img name="surNamerequired" src="images/ast.gif">
					<span style="color: red;">${status.errorMessage}</span>
				</spring:bind>
			</dd>
		</dl>


    </fieldset>

    
    <fieldset>
    	<legend>Administration</legend>
		<dl>
			<dt><label for="password">Owner:</label></dt>
            <dd><spring:bind path="training.beneficiaire.lastName" >
					<div id="myAutoComplete">   
					    <input type="text" name="${status.expression}"  id="beneficiaire" value="${status.value}"/>   
					    <div id="myContainer"></div>  
					</div> 
				</spring:bind>
			</dd>
		</dl>
    </fieldset>
    <fieldset class="action">
    	<input type="submit" name="submit" id="submit" class="button120"  value="Submit" />
    </fieldset>
</form>

</div>