
<jsp:directive.page contentType="text/html;charset=UTF-8" />
<%@ taglib prefix="tab" uri="http://ditchnet.org/jsp-tabs-taglib"%>
<%@ taglib prefix="layout" uri="http://www.sourceforge.net/springLayout"%>
<%@ taglib prefix="tiles" uri="/WEB-INF/struts-tiles.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib  prefix="fmt"    uri="http://java.sun.com/jstl/fmt"%>

<%
request.setAttribute("ctx", request.getContextPath()); 
request.setAttribute("serverName", request.getServerName()); 
request.setAttribute("serverPort", request.getServerPort()); 
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>Edit reminder  ${reminder.id} </title>
	<link href="css/HelpSupport.css" rel="stylesheet" type="text/css" />
	<link href="css/odbb-skin.css" rel="stylesheet" type="text/css" />

	<script type="text/javascript" src="script/scriptaculous/prototype.js"></script>
	<script type="text/javascript" src="script/scriptaculous/scriptaculous.js"></script>
</head>

<style type="text/css"> 
#ta { 
   padding: 5px; 
   height: 350px; 
   width: 500px; 
   /*border: medium inset #CCCCCC; */
   font-family: "Courier New", Courier, mono; 
   font-size: 12px; 
} 
</style> 

<style>
    .yui-editor-container {
        position: absolute;
        top: -9999px;
        left: -9999px;
        z-index: 999;
    }
    #editor {
        visibility: hidden;
        position: absolute;
    }
    .editable {
        border: 1px solid black;
        margin: .25em;
        float: left;
        width: 350px;
        height: 50px;
        overflow: auto;
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

 






<script>
  function cancelForm()
  {
      window.location="${ctx}/homePage.htm";
  }

  function approveInvoice(id)
  {
      window.location="${ctx}/approveInvoice.htm?invoiceId=" +id;
  }

  function markAsSentInvoice(id)
  {
      window.location="${ctx}/markAsSentReminderInvoice.htm?reminderId=" +id;
  }


  function print(id)
  {
	  // var url ="http://iaapp01:8080/birt/run?__report=facture_report.rptdesign&__format=pdf&invoiceIdentifier="+id;
      var url ="http://${serverName}:${serverPort}/birt/run?__report=alerte_report.rptdesign&__format=pdf&reminderID="+id;
	  window.open(url,"nom_de_la_fenetre","width =1000 ; height =1000");
	  
  }
  
  function printWord(id)
  {
	  // var url ="http://iaapp01:8080/birt/run?__report=facture_report.rptdesign&__format=pdf&invoiceIdentifier="+id;
      var url ="http://${serverName}:${serverPort}/birt/run?__report=alerte_report.rptdesign&__format=doc&reminderID="+id;
	  window.open(url,"nom_de_la_fenetre","width =1000 ; height =1000");
	  
  }


  
</script>





<script>
	var newWindow = null;
	  function cancelForm()
	  {
		  window.location="${ctx}/homePage.htm";
	  }

	function showInvoiceInfos(id){
	  if (newWindow != null){newWindow.close()}
        newWindow = window.showModalDialog('${ctx}/showInvoiceInfos.htm?customerid='+id,'top',"dialogWidth:1000px;dialogHeight:1000px");      
        newWindow.focus();
      } 

	  function removeAttachmentAction(code)
		 {
					if (confirm('Do you really want to remove this entry?'))
					{
						with(document.removeFeeForm)
			 			{
							document.forms["removeFeeForm"].elements["feecode"].value = code;
							var url = "${ctx}/showInvoiceRegisterPage.htm?customerid=${param['customerid']}&type=${invoice.type}&id=${param['id']}&command=removeFee&feecode="+code;
							action.value=url;
							submit();
		 				 }
		 	  }
		 }

	  function addPayment()
		 {
			var url = "${ctx}/showPaymentRegisterPage.htm?invoiceid=${invoice.id}";
			window.location=url;
		 }

		function createReminder()
		 {
			var url = "${ctx}/showInvoiceReminderRegisterPage.htm?invoiceid=${invoice.id}";
			window.location=url;
		 }

</script>








<body class="yui-skin-sam">
   <div id="ODBB_HEADER"> 
				<a href="${ctx}/homePage.htm"><img src="images/bannieres/ban.base1.jpg" alt="IAMS" border="0"></a>
	</div>
	 <jsp:include page="horizontal_menu.jsp"/>
	
 <div style="font-size: .7em;background-color: #F8F6E9;">
<br/>	
<h2></h2>

<br/>
<div id="global">

	<table cellpadding="3" cellspacing="2" border="0" class="axial" style="margin-left: auto;   margin-right: auto;   width: 700px;   text-align: left;">

	<tr>
		<td colspan="2" align="center">
				<span  style="color:blue;font-weight: bold;">
					Rappel de factures :  <span  style="color:red;font-weight: bold;">${reminder.id}</span>						
				</span>
		</td>
	</tr>

	<tr>
      <th>
		Référence
	  </th>
      <td>
        <span  style="color:blue;font-weight: bold;">${reminder.id}</span>
      </td>
    </tr>

	<tr>
      <th>Ref Ass/Sec</th>
      <td>
	  <span  style="color:black;font-weight: bold;">
	  <c:choose>
            <c:when test='${reminder.refAssSec != null }'>                                                  
                ${reminder.refAssSec}
            </c:when>
            <c:when test='${reminder.refAssSec == null }'>                                                  
                ---
            </c:when>                                           
        </c:choose>  
	  
	 </span>
         
      </td>
    </tr>

	<tr>
      <th>Associé</th>
      <td>
        <span  style="color:black;font-weight: bold;">${reminder.customer.associeSignataire.fullName}</span>
      </td>
    </tr>

	<tr>
      <th>
		Statut
	  </th>
      <td>
   
		<c:choose>
													<c:when test='${reminder.sent eq "false" }'>
													 <span style="color:red;font-weight: bold;">En Attente</span>
													</c:when>

													<c:when test='${reminder.sent eq "true" }'>
													 <span style="color:#05A5D8;font-weight: bold;">Envoyée</span>
													</c:when>
													
		</c:choose>  										
	
      </td>
    </tr>

	<tr>
      <th>Créé le</th>
      <td>
	  <span  style="color:black;font-weight: bold;">${reminder.remindDate}</span>
         
      </td>
    </tr>

	<tr>
      <th>Langue </th>
      <td>
	  <span id="lang" style="color:black;font-weight: bold;">

	  <c:choose>
			<c:when test='${reminder.lang eq "EN" }'>													
				Anglais &nbsp;<img src="images/flag_england.gif" border="0" alt="PDF"/>
			</c:when>

			<c:when test='${reminder.lang  eq "FR"  }'>													 
				Français &nbsp;<img src="images/flags_of_France.gif" border="0" alt="PDF"/>
			</c:when>

			<c:when test='${reminder.lang  eq "DE" }'>													
				Allemand &nbsp;<img src="images/flag_german.gif" border="0" alt="PDF"/>
			</c:when>													
		</c:choose>  
         
		 </span>&nbsp;
		 &nbsp; <!--img src="images/_ast.gif" border="0" alt="PDF"/-->
      </td>
    </tr>


	<tr>
      <th>Client</th>
      <td>        
       <span  style="color:black;font-weight: bold;">${reminder.customer.companyName}</span>
      </td>
    </tr>

	<tr>
      <th>Numero </th>
      <td>	 
		<span  style="color:red;font-weight: bold;">${reminder.order}</span>
       
      </td>
    </tr>

	<tr>
     <th>Factures</th>
	  <td align="center">
	  
				<table id="hor-minimalist-b" width="100%">
					 
					<thead>
					<tr>
					<th scope="col" align="center"><span  style="color:blue;font-weight: bold;">Reference</span></th>
					<th scope="col" align="center" width="10%"><span  style="color:blue;font-weight: bold;">Montant</span></th>									
					<th scope="col" align="center" width="10%"><span  style="color:blue;font-weight: bold;">Date</span></th>											
					</tr>
					</thead>
					<tbody>
					
						 <c:set var="row" value="0"/>
						 <c:forEach var="item" items="${reminder.invoices}" varStatus="loop">
								<c:choose>
									<c:when test='${row % 2 eq 0 }'>
										<tr onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
									</c:when>
									<c:otherwise>
										<tr   onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
									</c:otherwise>
								</c:choose>
								<td align="center" style="border: 0px;border: 1px solid black;">
								
									${item.reference}		
								
								
								</td>
								<td align="center" style="border: 0px;border: 1px solid black;">
								${item.amountEuro}	
								</td>

								<td width="10%" style="border: 0px;border: 1px solid black;">
								${item.dateFacture}	
								</td>
							</tr>
						<c:set var="row" value="${row + 1}"/>
						</c:forEach>
				</tbody>
				</table>
				
			</td>      
    </tr>

	<tr>
     
	  <td colspan="2" align="center">
				<input style="width:100px;
				" type="button" name="cancel" id="cancel" class="button120"  value="Cancel" onclick="cancelForm()"/>				
						<input style="width:100px;
				" type="button" name="preview" id="preview" class="button120"  value="Print PDF" onclick="print(${reminder.id})"/>
				
				<input style="width:100px;
				" type="button" name="preview" id="previewDOC" class="button120"  value="Print DOC " onclick="printWord(${reminder.id})"/>
				
				
				

				<c:if test='${reminder.sent eq false}'>  
					<input style="width:100px;
					" type="button" name="markAsSent" id="markAsSent" class="button120"  value="Mark as sent"   onclick="markAsSentInvoice(${reminder.id})"/>
				</c:if>
								
			</td>
      
    </tr>

	
	
	</table>




</div>

<h2></h2>
<br/>

</div>


<script type="text/javascript">
//for each cell, add cell-editing 'InPlaceEditor'

	function addInPlaceEditors() {
		var invoiceId = '${invoice.id}';
		var cells = $$('span.translation_cell');		
		for (i=0; i<cells.length;i++){
			var keyAmount = cells[i].id + '_v';
			var fraisId=cells[i].id;
			new Ajax.InPlaceEditor(cells[i].id, '${ctx}/updateFraisLibelle.htm',
			{
				rows:5,
				cols:50,
				cancelControl : "button",
                callback: function(form, value) {//call the controller with parameters
	                //get the cell id and trim it (remove 'prefix_' and trailing '-inplaceeditor')
	                var id = form.id;
	                id = id.substring(0,id.indexOf('-inplaceeditor'));
	                id = id.substring(id.lastIndexOf('_')+1,id.length);
	              // alert(id);
	                return 'invoiceId='+invoiceId  + '&fraisId='+ id + '&translation='+encodeURIComponent(value);
	            },
                onComplete:function(transport, element){//print the result using JSON
                    var msg = 'enter value';
                    if (transport.responseText.evalJSON().translation == ' ' || transport.responseText.evalJSON().translation == null
                    || transport.responseText.evalJSON().translation == ''){
                        element.innerHTML = '<font color="red"> <b>' + msg + '</b></font>';
                    }
                    else {
                        element.innerHTML = transport.responseText.evalJSON().translation;
                    }
                },
                onFailure:function(transport) {
                    alert('You must enter a valid entry!');
                }
			}

			);



					//amount frais
		new Ajax.InPlaceEditor(keyAmount, '${ctx}/updateInvoiceFraisValue.htm',
			{
				rows:1,
				cols:10,
				cancelControl : 'link',
                callback: function(form, value) {//call the controller with parameters
                    //get the cell id and trim it (remove 'prefix_' and trailing '-inplaceeditor')                    
                    //alert('invoiceId='+invoiceId);
					//alert(fraisId);
					var id = form.id;
					//alert(id);
	                id = id.substring(0,id.indexOf('_v-inplaceeditor'));
	                id = id.substring(id.lastIndexOf('_')+1,id.length);
					//alert(id);
                    return 'invoiceId='+invoiceId  + '&fraisId='+ id + '&translation='+encodeURIComponent(value);
                },
                onComplete:function(transport, element){//print the result using JSON
					window.location.reload();
                    var msg = 'enter value';
                    if (transport.responseText.evalJSON().translation == ' ' || transport.responseText.evalJSON().translation == null
                    || transport.responseText.evalJSON().translation == ''){
                        element.innerHTML = '<font color="red"> <b>' + msg + '</b></font>';
                    }
                    else {
                        element.innerHTML = transport.responseText.evalJSON().translation;
                    }
                },
                onFailure:function(transport) {
                    alert('You must enter a valid entry!');
                }
			}
			);
		}


		//deductions treatement
		var cells = $$('span.deduction_cell');      
        for (i=0; i<cells.length;i++){
            var keyAmount = cells[i].id + '_v';
            var deductionId=cells[i].id;
            new Ajax.InPlaceEditor(cells[i].id, '${ctx}/updateDeductionLibelle.htm',
            {
                rows:5,
                cols:50,
                cancelControl : "button",
                callback: function(form, value) {
                    var id = form.id;
                    id = id.substring(0,id.indexOf('-inplaceeditor'));
                    id = id.substring(id.lastIndexOf('_')+1,id.length);                  
                    return 'invoiceId='+invoiceId  + '&deductionId='+ id + '&translation='+encodeURIComponent(value);
                },
                onComplete:function(transport, element){//print the result using JSON
                    var msg = 'enter value';
                    if (transport.responseText.evalJSON().translation == ' ' || transport.responseText.evalJSON().translation == null
                    || transport.responseText.evalJSON().translation == ''){
                        element.innerHTML = '<font color="red"> <b>' + msg + '</b></font>';
                    }
                    else {
                        element.innerHTML = transport.responseText.evalJSON().translation;
                    }
                },
                onFailure:function(transport) {
                    alert('You must enter a valid entry!');
                }
            }

            );



         //amount frais pour deductions
        new Ajax.InPlaceEditor(keyAmount, '${ctx}/updateInvoiceDeductionValue.htm',
            {
                rows:1,
                cols:10,
                cancelControl : 'link',
                callback: function(form, value) {
					var id = form.id;
					//alert(id);
	                id = id.substring(0,id.indexOf('_v-inplaceeditor'));
	                id = id.substring(id.lastIndexOf('_')+1,id.length);
					//alert(id);
                    return 'invoiceId='+invoiceId  + '&deductionId='+ id + '&translation='+encodeURIComponent(value);
                },
                onComplete:function(transport, element){//print the result using JSON
                    window.location.reload();
                    var msg = 'enter value';
                    if (transport.responseText.evalJSON().translation == ' ' || transport.responseText.evalJSON().translation == null
                    || transport.responseText.evalJSON().translation == ''){
                        element.innerHTML = '<font color="red"> <b>' + msg + '</b></font>';
                    }
                    else {
                        element.innerHTML = transport.responseText.evalJSON().translation;
                    }
                },
                onFailure:function(transport) {
                    alert('You must enter a valid entry!');
                }
            }
            );
        }

		//Lib delai
		new Ajax.InPlaceEditor('libDelai', '${ctx}/updateInvoiceLibDelai.htm',
			{
				rows:5,
				cols:70,
				cancelControl : 'button',
                callback: function(form, value) {
                    return 'invoiceId='+invoiceId  + '&translation='+encodeURIComponent(value);
                },
                onComplete:function(transport, element){//print the result using JSON
                    var msg = 'enter value';
                    if (transport.responseText.evalJSON().translation == ' ' || transport.responseText.evalJSON().translation == null
                    || transport.responseText.evalJSON().translation == ''){
                        element.innerHTML = '<font color="red"> <b>' + msg + '</b></font>';
                    }
                    else {
                        element.innerHTML = transport.responseText.evalJSON().translation;
                    }
                },
                onFailure:function(transport) {
                    alert('You must enter a valid entry!');
                }
			}
			);

			//Lib honoraire
		new Ajax.InPlaceEditor('libHonoraires', '${ctx}/updateInvoiceLibHonoraires.htm',
			{
				rows:5,
				cols:70,
				cancelControl : 'button',
                callback: function(form, value) {
                    return 'invoiceId='+invoiceId  + '&translation='+encodeURIComponent(value);
                },
                onComplete:function(transport, element){//print the result using JSON
                    var msg = 'enter value';
                    if (transport.responseText.evalJSON().translation == ' ' || transport.responseText.evalJSON().translation == null
                    || transport.responseText.evalJSON().translation == ''){
                        element.innerHTML = '<font color="red"> <b>' + msg + '</b></font>';
                    }
                    else {
                        element.innerHTML = transport.responseText.evalJSON().translation;
                    }
                },
                onFailure:function(transport) {
                    alert('You must enter a valid entry!');
                }
			}
			);

			//adresse
		new Ajax.InPlaceEditor('addresse', '${ctx}/updateInvoiceAdresse.htm',
			{
				rows:5,
				cols:70,
				cancelControl : 'link',
                callback: function(form, value) {
                    return 'invoiceId='+invoiceId  + '&translation='+encodeURIComponent(value);
                },
                onComplete:function(transport, element){//print the result using JSON
                    var msg = 'enter value';
                    if (transport.responseText.evalJSON().translation == ' ' || transport.responseText.evalJSON().translation == null
                    || transport.responseText.evalJSON().translation == ''){
                        element.innerHTML = '<font color="red"> <b>' + msg + '</b></font>';
                    }
                    else {
                        element.innerHTML = transport.responseText.evalJSON().translation;
                    }
                },
                onFailure:function(transport) {
                    alert('You must enter a valid entry!');
                }
			}
			);


			//honoraires,tva,lang

				/*honoraires
		new Ajax.InPlaceEditor('honoraires', '${ctx}/updateInvoiceHonoraires.htm',
			{
				rows:1,
				cols:10,
				cancelControl : 'link',
                callback: function(form, value) {
                    return 'invoiceId='+invoiceId  + '&translation='+encodeURIComponent(value);
                },
                onComplete:function(transport, element){//print the result using JSON
					window.location.reload();
                    var msg = 'enter value';
                    if (transport.responseText.evalJSON().translation == ' ' || transport.responseText.evalJSON().translation == null
                    || transport.responseText.evalJSON().translation == ''){
                        element.innerHTML = '<font color="red"> <b>' + msg + '</b></font>';
                    }
                    else {
                        element.innerHTML = transport.responseText.evalJSON().translation;
                    }
                },
                onFailure:function(transport) {
                    alert('You must enter a valid entry!');
                }
			}
			);
			*/

				//tva
		new Ajax.InPlaceEditor('tva', '${ctx}/updateInvoiceTva.htm',
			{
				rows:1,
				cols:10,
				cancelControl : 'link',
                callback: function(form, value) {
                    return 'invoiceId='+invoiceId  + '&translation='+encodeURIComponent(value);
                },
                onComplete:function(transport, element){//print the result using JSON
					window.location.reload();
                    var msg = 'enter value';
                    if (transport.responseText.evalJSON().translation == ' ' || transport.responseText.evalJSON().translation == null
                    || transport.responseText.evalJSON().translation == ''){
                        element.innerHTML = '<font color="red"> <b>' + msg + '</b></font>';
                    }
                    else {
						alert("toto");
                        element.innerHTML = transport.responseText.evalJSON().translation;
						window.location.reload();
                    }
                },
                onFailure:function(transport) {
                    alert('You must enter a valid entry!');
                }
			}
			);
			
			/*

			new Ajax.InPlaceCollectionEditor( 'lang', '${ctx}/updateInvoiceLang.htm?invoiceId='+invoiceId, { 
				  collection: [['EN', 'Anglais'], ['FR', 'Francais'], ['DE', 'Allemand']] ,
                  callback: function(form, value) {
													//alert('invoiceId='+invoiceId);
													return 'invoiceId='+invoiceId  + '&translation='+encodeURIComponent(value);
												},
                  onComplete:function(transport, element){//print the result using JSON
														window.location.reload();
														var msg = 'enter value';
														if (transport.responseText.evalJSON().translation == ' ' || transport.responseText.evalJSON().translation == null
														|| transport.responseText.evalJSON().translation == ''){
															element.innerHTML = '<font color="red"> <b>' + msg + '</b></font>';
														}
														else {
															element.innerHTML = transport.responseText.evalJSON().translation;
														}
													},
                  onFailure:function(transport) {alert('You must enter a valid entry!');}
													
												
					
				  }
				  
				  );
				 */


	}
	
	<c:if test='${invoice.approved eq false}'>  
						addInPlaceEditors();
	</c:if>
	
</script>

</body>
</html>





