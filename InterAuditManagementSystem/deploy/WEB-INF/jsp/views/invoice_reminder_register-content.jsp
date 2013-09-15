<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions"%>

<link type="text/css" href="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/themes/base/ui.all.css" rel="stylesheet" />
	<script type="text/javascript" src="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/jquery-1.3.2.js"></script>
	<script type="text/javascript" src="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/ui/ui.core.js"></script>
	<script type="text/javascript" src="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/ui/ui.datepicker.js"></script>
	<link type="text/css" href="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/demos.css" rel="stylesheet" />

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
</script>

 <script type="text/javascript">
	$(function() {
	$('#datepicker1').datepicker({
		showOn: 'button', buttonImage: 'images/calbtn.gif', buttonImageOnly: false,
	changeMonth: true,
		changeYear: true
	});
	});
</script>

<h2 style="font-size: 12px;font-family: Tahoma;font-weight: bold;border-bottom:2px solid #3399CC;padding:0 0 4px 0;margin:10px 0 0 0;">
<span  style="color:black;font-weight: bold;">   Rappel N° ${reminder.order} Facture ${reminder.facture.reference} [${reminder.facture.customerName}]</span>
</h2>
<br/>
<div id="global">

<form  action="${ctx}/showInvoiceReminderRegisterPage.htm" method="post" class="niceform">
<fieldset style="background-color: #eee;">
    	<table border="0" width="100%">
		<tr><td align="center">
				<span  style="color:black;font-weight: bold;">   Rappel N° ${reminder.order} Facture ${reminder.facture.reference} [${reminder.facture.customerName}]</span>
			</td>
		</tr>
	</table>
    </fieldset>
<fieldset style="background-color: #eee;">
    	<legend><span  style="color:black;font-weight: bold;">Properties</span></legend>
        
		<dl class="global2">
			<label  style="color:#039;" for="password">Date debut:</label>
			<div>
				<spring:bind path="reminder.startValidityDate" >
					<input  style="width:200px;" type="text" name="${status.expression}"  value="${status.value}" readonly="readonly">					
				</spring:bind>
			</div>
		</dl>
		
		<dl class="global2">
			<label  style="color:#039;" for="password">Date fin:</label>
			<div>
				<spring:bind path="reminder.endValidityDate" >
					<input  style="width:200px;" type="text" name="${status.expression}"  value="${status.value}" readonly="readonly">					
				</spring:bind>
			</div>
		</dl>
		
		<dl class="global2">
			<label  style="color:#039;" for="password">Date d'envoi:</label>
			<div>
				<spring:bind path="reminder.remindDate" >
					<input  style="width:200px;" type="text" name="${status.expression}" id="datepicker1" value="${status.value}">
					<span style="color: red;">${status.errorMessage}</span>
				</spring:bind>
			</div>
		</dl>
        	
			
		
		
    </fieldset>

	<fieldset style="background-color: #eee;">
		<legend><span  style="color:black;font-weight: bold;">Administration</span></legend>
    	
		<dl class="global2">
			<label  style="color:#039;" for="password">Reference facture :</label>
            <div>
				<spring:bind path="reminder.facture.reference" >
					<input  style="width:200px;" type="text" name="${status.expression}" value="${status.value}" readonly="readonly">					
				</spring:bind>
			</div>
		</dl>
		<c:if test="${reminder.sender != null}">
		<dl class="global2">
			<label  style="color:#039;" for="password">Responsable:</label>
            <div>
			
				<spring:bind path="reminder.sender.fullName" >
					<input  style="width:200px;" type="text" name="${status.expression}" value="${status.value}" readonly="readonly">
				</spring:bind>	
			
			</div>
		</dl>
		</c:if>

		<dl class="global2">
		
			<label style="color:#039;" for="approved">Actif</label>
         
				<spring:bind path="reminder.active" >
					<input style="margin-right:20px;" type="checkbox" name="${status.expression}" id="${status.expression}" value="true" 
					<c:if test='${status.value == true }'>checked</c:if>  DISABLED />
					
				</spring:bind>
		
		
		
			<label style="color:#039;" for="approved">Envoyé</label>
         
				<spring:bind path="reminder.sent" >
					<input type="checkbox" name="${status.expression}" id="${status.expression}" value="true" <c:if test='${status.value == true }'>checked</c:if> DISABLED/>					
				</spring:bind>
		
		
		</dl>
    </fieldset>

    <fieldset style="background-color: #eee;">
    	<table border="0" width="100%">
		<tr><td align="center">
				<input style="width:100px;
				" type="button" name="cancel" id="cancel" class="button120"  value="Cancel" onclick="cancelForm()"/>

				<input style="width:100px;
				" type="submit" name="submit" id="submit" class="button120"  value="Submit" />
			</td>
		</tr>
	</table>
    </fieldset>
</form>

</div>

<h2 style="font-size: 12px;font-family: Tahoma;font-weight: bold;border-bottom:2px solid #3399CC;padding:0 0 4px 0;margin:10px 0 0 0;">
</h2>