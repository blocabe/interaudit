<jsp:directive.page contentType="text/html;charset=UTF-8" />
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="${ctx}/script/jquery-1.2.6.js"></script>
<link href="${ctx}/css/messageHandler.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/script/messageHandler.js"></script>



<script type="text/javascript">
$(document).ready(function()  {

			<c:if test='${currentExercise.status eq "CLOSED"}'> 	
				showMessage("Aucun budget actif défini... cette fonctionnalité n'est pas utilisable..","error");                    
			</c:if> 
  
			<c:if test="${not empty invoiceErrorMessage}">
				<c:forEach var="actionError" items="${invoiceErrorMessage}">					
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
			
			<c:if test="${not empty invalidBankErrorMessage}">            
			  showMessage("${invalidBankErrorMessage}","error");
			    <c:set var="invalidBankErrorMessage" value="" scope="session" />
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
	  var customerid=document.forms["form1"].elements["customerid"].value;
	  var type=document.forms["form1"].elements["type"].value;

	  var tva=document.forms["form1"].elements["tva"].value;
	  var lang=document.forms["form1"].elements["lang"].value;
	  var delai=document.forms["form1"].elements["delai"].value;
	  var bank=document.forms["form1"].elements["bank"].value;
	  var honoraires=document.forms["form1"].elements["honoraires"].value;
	  //var exercice=document.forms["form1"].elements["exercice"].value;
	  var forcecreation=document.forms["form1"].elements["forcecreation"].checked;
	  //alert(forcecreation);
	  //var forcecreation="on";

	  //var url ="${ctx}/checkInvoiceForMissionPage.htm?customerid="+customerid+"&type="+type+"&tva="+tva+"&honoraires="+honoraires +"&forcecreation="+forcecreation+"&lang="+lang+"&delai="+delai+"&bank="+bank+"&exercice="+exercice;
	 var answer = confirm("Do you really want to create this invoice?")
	 if (answer){
	 var url ="${ctx}/checkInvoiceForMissionPage.htm?customerid="+customerid+"&type="+type+"&tva="+tva+"&honoraires="+honoraires +"&forcecreation="+forcecreation+"&lang="+lang+"&delai="+delai+"&bank="+bank;
	  window.location=url;
	 }
	 
  }


   function sendFinalInvoiceForm()
  {
	  var customerid=document.forms["form1"].elements["customerid"].value;
	  var type=document.forms["form1"].elements["type"].value;

	  var tva=document.forms["form1"].elements["tva"].value;
	  var honoraires=document.forms["form1"].elements["honoraires"].value;
	 // var forcecreation=document.forms["form1"].elements["forcecreation"].value;
	 var forcecreation="on";

	  var url ="${ctx}/showInvoiceRegisterPage.htm?customerid="+customerid+"&type="+type+"&tva="+tva+"&honoraires="+honoraires +"&forcecreation="+forcecreation;
      window.location=url;
  }
  
   function closeTimeSheetListToValidate()
	  {
	  var customerid=document.forms["form1"].elements["customerid"].value;
	  var type=document.forms["form1"].elements["type"].value;

	  var tva=document.forms["form1"].elements["tva"].value;
	  var lang=document.forms["form1"].elements["lang"].value;
	  var delai=document.forms["form1"].elements["delai"].value;
	  var bank=document.forms["form1"].elements["bank"].value;
	  var honoraires=document.forms["form1"].elements["honoraires"].value;
	  //var exercice=document.forms["form1"].elements["exercice"].value;
	  var forcecreation=document.forms["form1"].elements["forcecreation"].checked;
		var url ="${ctx}/selectCustomerForInvoicePage.htm?customerid="+customerid+"&type="+type+"&tva="+tva+"&honoraires="+honoraires +"&forcecreation="+forcecreation+"&lang="+lang+"&delai="+delai+"&bank="+bank;
      window.location=url;
		  
	  }


function showHideForceCreationField(){	
	/*
	 var type=document.forms["form1"].elements["type"].value;
	 var targetElement = document.getElementById('id_div_1') ;
	
	
	 if(type =='AD'){				
		 document.forms["form1"].elements["forcecreation"].checked=false;
		 document.forms["form1"].elements["forcecreation"].disabled= true;		
		 if (targetElement.style.display != "none")
			{
				targetElement.style.display = "none" ;
			}
	 }
	 else{		
		  document.forms["form1"].elements["forcecreation"].disabled= false;
		  if (targetElement.style.display != "block")
			{
				targetElement.style.display = "block" ;
			}
	 }
	 */
}



function visibilite(thingId)
{ 
var targetElement;
targetElement = document.getElementById(thingId) ;
if (targetElement.style.display == "none")
{
targetElement.style.display = "" ;
} else {
targetElement.style.display = "none" ;
}
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
		.global2 {   margin-left: auto;   margin-right: auto;   width: 500px;   text-align: left; /* on r�tablit l'alignement normal du texte */ }
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

<c:if test="${not empty listTimesheetsToValidate}">
<div style="background-color: #eee;margin-left: auto; margin-right: auto; width: 90%; text-align: center; ">
									
			    
			        <table id="ver-zebra" width="100%" cellspacing="0" >
									<caption><span style="color:purple;">Reports activities to validate</span></caption>
									<thead>
									<tr>
										<th scope="col">Employé</th>
										<th scope="col">Semaine </th>
										<th scope="col">Date validation </th>
										<th scope="col">Date début </th>
										<th scope="col">Date fin </th>
										<th scope="col">Exercice</th>
										<th scope="col">Statut</th>
										<th scope="col">Actions</th>
									</tr>
									</thead>
									<tbody>
									

											<c:set var="row" value="0"/>
											<c:forEach var="item" items="${listTimesheetsToValidate}" >


												<c:choose>
									    		<c:when test='${item.status eq "DRAFT"}'>												
												 <c:set var="backgroundStyle" value="background-color:#FF6262;"/>
									      		</c:when>
												<c:when test='${item.status eq "SUBMITTED"}'>												
												 <c:set var="backgroundStyle" value="background-color:#FFA042;"/>
									      		</c:when>
									      		<c:when test='${item.status eq "VALIDATED"}'>
												 <c:set var="backgroundStyle" value="background-color:#55FF55;"/>
									      		</c:when>
									     	</c:choose>
												
												<c:choose>
													<c:when test='${row % 2 eq 0 }'>
														<tr style="background: #eff2ff;" onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
													</c:when>
													<c:otherwise>
														<tr onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
													</c:otherwise>
									     		</c:choose>
											 		<td  align="center"><span style=" color: purple;">${item.employeeName}</span></td>
											 		<td align="center">${item.weekNumber}</td>											 		
											 		<td align="center">${item.validationDate}</td>
											 		<td align="center">${item.startDateOfWeek}</td>
											 		<td align="center">${item.endDateOfWeek}</td>											 		
											 		<td  align="center">${item.year}</td>
													<td style=${backgroundStyle} align="center">${item.status}</td>
													<td  align="center"><a href="${ctx}/showTimesheetRegisterPage.htm?year=${item.year}&week=${item.weekNumber}&employeeId=${item.employeeId}" title="Details activity report" ><img src="images/Table-Edit.png" border="0" alt="Edit"/></a></td>
											 		
												</tr>
												<c:set var="row" value="${row + 1}"/>
									  		</c:forEach>
									
									
									</tbody>
								</table>
								<hr/>
								<table border="0" width="100%">
								<tr><td align="center">								
										
											<input style="width:100px;
											" type="button" name="button" id="submit" class="button120"  value="Close>>" onclick="closeTimeSheetListToValidate()"/>
										
									</td>
								</tr>
							</table>
						</div>
						<c:set var="listTimesheetsToValidate" value="" scope="session" />
</c:if>

<div id="global">
		

					
<form action="" name="form1" method="post">
 <fieldset style="background-color: #eee;">
    	<table border="0" width="100%">
		<tr><td align="center">
				<span  style="color:black;font-weight: bold;">
		Nouvelle facture
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
				<label style="color:#039;" for="mission">Missions :</label>
				<div> 
					<select name="customerid" id="customerid" >
					<c:forEach var="option" items="${customerForInvoiceOptions}" varStatus="loop">
						<option value="${option.id}" <c:if test='${param["customerid"] == option.id}'> selected</c:if>>
							${option.name}
						</option>
					</c:forEach>
					</select>
			</div> 
		 </dl>
		 <dl class="global2">		 
           
				<label style="color:#039;" for="mission">Type de facture:</label>
			<div> 
				<!--select style="width:120px;" size="1" name="type" id="type" onchange="showHideForceCreationField()" -->
				<select style="width:120px;" size="1" name="type" id="type">
                    <option value="AD" <c:if test='${param["type"] eq "AD"}'> selected</c:if>>Advance</option>
                    <!--option value="CN" <c:if test='${param["type"] eq "CN"}'> selected</c:if>>Note de credit</option-->
					<option value="FB" <c:if test='${param["type"] eq "FB"}'> selected</c:if>>Facture finale</option>
					<option value="SP" <c:if test='${param["type"] eq "SP"}'> selected</c:if>>Supplement</option>
            	</select>
			</div> 
		 </dl>	


		 <%--dl class="global2">		 
           
				<label style="color:#039;" for="exercice">Exercice audité:</label>
			<div> 
				<select style="width:150px;" size="1" name="exercice" id="exercice">
					<option value="-1"   <c:if test='${param["exercice"] eq "-1"}'> selected</c:if>>Choose an option...</option>
					<option value="2005" <c:if test='${param["exercice"] eq "2005"}'> selected</c:if>>2005</option>
					<option value="2006" <c:if test='${param["exercice"] eq "2006"}'> selected</c:if>>2006</option>
                    <option value="2007" <c:if test='${param["exercice"] eq "2007"}'> selected</c:if>>2007</option>
					<option value="2008" <c:if test='${param["exercice"] eq "2008"}'> selected</c:if>>2008</option>
					<option value="2009" <c:if test='${param["exercice"] eq "2009"}'> selected</c:if>>2009</option>
					<option value="2010" <c:if test='${param["exercice"] eq "2010"}'> selected</c:if>>2010</option>
					<option value="2011" <c:if test='${param["exercice"] eq "2011"}'> selected</c:if>>2011</option>
					<option value="2012" <c:if test='${param["exercice"] eq "2012"}'> selected</c:if>>2012</option>
					<option value="2013" <c:if test='${param["exercice"] eq "2013"}'> selected</c:if>>2013</option>
					<option value="2014" <c:if test='${param["exercice"] eq "2014"}'> selected</c:if>>2014</option>
					<option value="2015" <c:if test='${param["exercice"] eq "2015"}'> selected</c:if>>2015</option>
					<option value="2016" <c:if test='${param["exercice"] eq "2016"}'> selected</c:if>>2016</option>
            	</select>
			</div> 
		 </dl--%>	

		

		 <dl class="global2">		         	
			
                   
				  <label style="margin-letft:20px;color:#039;">Tva : </label> 
				  
				  <input style="width:50px;margin-left:14px;" name="tva" type="text" size="1"
				  
				   <c:if test='${param["tva"] != null}'> value="${param['tva']}"</c:if>
				   <c:if test='${param["tva"] == null}'> value="15"</c:if>/> % (<span  style="color:red;">15% par defaut</span>)
				 
			


		 </dl>	


		 <dl class="global2">
				<div>
				<label  style="color:#039;" for="delaiPaiement">Délai:</label>	
						<select style="width:120px;margin-left:14px;" name="delai">
							<option value="15"  <c:if test='${param["delai"] eq "15"}'> selected</c:if>>15 jours</option>
							<option value="30"  <c:if test='${param["delai"] eq "30"}'> selected</c:if>>30 jours</option>
							<option value="0"   <c:if test='${param["delai"] eq "0"}'> selected</c:if>>Immédiat</option>
							<option value="-1"  <c:if test='${param["delai"] eq "-1"}'> selected</c:if>>Suivant accord</option>
						</select>
						(<span  style="color:red;">jours</span>)
				
			</dl>


			 <dl class="global2">
				<div>
				
				<label  style="color:#039;" for="language">Langue:</label>					
						<select style="width:150px;margin-right:60px;" name="lang">
							<option value="-1" selected>Choose an option...</option>
							<option value="EN" <c:if test='${param["lang"] eq "EN"}'> selected</c:if>>Anglais</option>
							<option value="FR" <c:if test='${param["lang"] eq "FR"}'> selected</c:if>>Francais</option>
							<option value="DE" <c:if test='${param["lang"] eq "DE"}'> selected</c:if>>Allemand</option>
						</select>					
				</div>
			</dl>

			</dl>	

			<dl class="global2">
			<label style="color:#039;" for="recipient">Compte à créditer</label>&nbsp;<font color="red"><sup class="sm">*</sup></font>
            <div>
			
					<select style="width:320px;" name="bank">
					<option value="-1" selected>Choose an option...</option>
					<c:forEach var="option" items="${banksOptions}" varStatus="loop">
						<option value="${option.id}" 
							<c:if test="${option.id == param['bank']}">selected</c:if>>
							${option.name}
						</option>
					</c:forEach>
					</select>
				
				
				
			</div>
		</dl>
		 
		 <dl class="global2">		 
          
			<div> 
                   
				  <label style="margin-letft:20px;color:#039;">Honoraires : (<span  style="color:red;">Optionnel.Calculé automatiquement par défaut</span>)</label> 
				  <div> 
				  <input name="honoraires" type="text" 
				   <c:if test='${param["honoraires"] != null}'> value="${param['honoraires']}"</c:if>
				   <c:if test='${param["honoraires"] == null}'> value="auto"</c:if>/><br/>
				  </div> 
				  
				   
            	
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






<script>
 showHideForceCreationField();
</script>