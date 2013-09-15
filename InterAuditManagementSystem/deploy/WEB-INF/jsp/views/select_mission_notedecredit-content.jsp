<jsp:directive.page contentType="text/html;charset=UTF-8" />
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions"%>

<%
request.setAttribute("ctx", request.getContextPath()); 
%>

<script type="text/javascript" src="${ctx}/script/jquery-1.6.1.js"></script>
<link href="${ctx}/css/messageHandler.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/script/messageHandler.js"></script>


<script type="text/javascript">
  
$(document).ready(function()  {
  <c:if test='${currentExercise.status eq "CLOSED"}'> 	
          showMessage("Aucun budget actif défini... Cette fonctionnalité n'est pas utilisable..","error");                    
    
  </c:if>                 
});  
</script>


	<script>
  function cancelForm()
  {
      window.location="${ctx}/homePage.htm";
  }

  function sendInvoiceForm()
  {
	  var facture=document.forms["form1"].elements["facture"].value;
	  var tva=document.forms["form1"].elements["tva"].value;
	  var honoraires=document.forms["form1"].elements["honoraires"].value;

	  //var forcecreation="on";
	  var interne=document.forms["form1"].elements["interne"].checked;
	  var forcecreation=document.forms["form1"].elements["forcecreation"].checked;

	  var url ="${ctx}/checkInvoiceForMissionPage.htm?facture="+facture+"&type=CN"+"&tva="+tva+"&honoraires="+honoraires+"&interne="+interne+"&forcecreation="+forcecreation;
      window.location=url;
  }


   function sendFinalInvoiceForm()
  {
	  var customerid=document.forms["form1"].elements["customerid"].value;
	  var type=document.forms["form1"].elements["type"].value;

	  var tva=document.forms["form1"].elements["tva"].value;
	  var honoraires=document.forms["form1"].elements["honoraires"].value;
	 // var forcecreation=document.forms["form1"].elements["forcecreation"].value;
	// var forcecreation="on";
	  var interne=document.forms["form1"].elements["interne"].checked;

	  var url ="${ctx}/showInvoiceRegisterPage.htm?customerid="+customerid+"&type="+type+"&tva="+tva+"&honoraires="+honoraires +"&interne="+interne;
      window.location=url;
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

<h2 style="font-size: 12px;font-family: Tahoma;font-weight: bold;border-bottom:2px solid #3399CC;padding:0 0 4px 0;margin:10px 0 0 0;">

</h2>
<br/>

<div id="global">
		<c:if test="${not empty invoiceErrorMessage}">
				<div style="width: 100%; align: center">
				<div class="validation">					
					<c:forEach var="message"
					items="${invoiceErrorMessage}">
					<li><span style="color: blue;"><c:out value="${message}" escapeXml="false" /></span></li>
				</c:forEach>
				</div>
				</div>
			<c:set var="invoiceErrorMessage" value="" scope="session" />
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

					
<form action="" name="form1" method="post">
 <fieldset style="background-color: #eee;">
    	<table border="0" width="100%">
		<tr><td align="center">
				<span  style="color:black;font-weight: bold;">
		Note de crédit
		</span>
			</td>
		</tr>
	</table>
    </fieldset>
	<fieldset style="background-color:#eee;">
    	<legend>
		<span  style="color:black;font-weight: bold;">
		Propriétés
		</span>
		</legend>

		
		 <dl class="global2">                
                <label  style="color:#039;" for="delaiPaiement">N° Facture :</label>  
                   <div> 
                  <input name="facture" type="text"/>
                  </div>                       
            </dl>

		 <dl class="global2">		         				
				  <label style="margin-letft:20px;color:#039;">Tva : </label> 				  
				  <input style="width:50px;margin-left:14px;" name="tva" type="text" size="1"/> % (<span  style="color:red;">15% par defaut</span>)
		 </dl>	


		 <dl class="global2">		 
          	  <label style="margin-letft:20px;color:#039;">Montant : </label> 
				  <div> 
				  <input name="honoraires" type="text"/>
				  </div> 					
		 </dl>	


		 <dl class="global2">		         	
			<div>                    
				  <label style="margin-letft:20px;color:#039;">Interne</label> 				 
				  <input name="interne" type="checkbox"/><br/>			
			</div> 
		 </dl>
		 
		 <!--dl class="global2" id="id_div_1" style="display:block;"-->       
         <dl class="global2">
            <div>                    
                  <label style="margin-letft:20px;color:#039;">Forcer la creation si nécessaire</label>                  
                  <input name="forcecreation" type="checkbox" <c:if test='${param["forcecreation"] eq true}'> checked</c:if>/><br/>         
            </div> 
         </dl>
		 

	</fieldset>
    <fieldset style="background-color: #eee;">
    	<table border="0" width="100%">
		<tr><td align="center">
				<input style="width:100px;
				" type="button" name="cancel" id="cancel" class="button120"  value="Cancel" onclick="cancelForm()"/>

				<input style="width:100px;
				" type="button" name="button" id="submit" class="button120"  value="Next>>" onclick="sendInvoiceForm()"/>
			</td>
		</tr>
	</table>
    </fieldset>
</form>

</div>

<h2 style="font-size: 12px;font-family: Tahoma;font-weight: bold;border-bottom:2px solid #3399CC;padding:0 0 4px 0;margin:10px 0 0 0;">
</h2>




