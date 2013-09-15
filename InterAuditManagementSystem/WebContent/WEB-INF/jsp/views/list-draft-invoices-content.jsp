
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib  prefix="fmt"    uri="http://java.sun.com/jstl/fmt"%>
<%@ taglib uri="/tags/interaudit" prefix="interaudit" %>

<%
request.setAttribute("serverName", request.getServerName()); 
request.setAttribute("serverPort", request.getServerPort()); 
%>

<script type="text/javascript" src="${ctx}/script/tabs.js"></script>
<script type="text/javascript" src="${ctx}/script/scriptaculous/prototype.js"></script>
<script type="text/javascript" src="${ctx}/script/scriptaculous/scriptaculous.js"></script>
<!-- START ADDED PART -->

<link rel="stylesheet" type="text/css" href="${ctx}/script/build/fonts/fonts-min.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/script/build/container/assets/skins/sam/container.css" />
<script type="text/javascript" src="${ctx}/script/build/utilities/utilities.js"></script>
<script type="text/javascript" src="${ctx}/script/build/container/container-min.js"></script>
<script type="text/javascript" src="${ctx}/script/build/yahoo/yahoo-min.js"></script>
<script type="text/javascript" src="${ctx}/script/build/event/event-min.js"></script>
<script type="text/javascript" src="${ctx}/script/build/connection/connection-min.js"></script>
<script type="text/javascript" src="${ctx}/script/scriptaculous/prototype.js"></script>
<script type="text/javascript" src="${ctx}/script/scriptaculous/scriptaculous.js"></script>


<link rel="stylesheet" type="text/css" href="${ctx}/script/build/datatable/assets/skins/sam/datatable.css" />
<script type="text/javascript" src="${ctx}/script/build/yahoo-dom-event/yahoo-dom-event.js"></script>
<script type="text/javascript" src="${ctx}/script/build/dragdrop/dragdrop-min.js"></script>
<script type="text/javascript" src="${ctx}/script/build/element/element-min.js"></script>
<script type="text/javascript" src="${ctx}/script/build/datasource/datasource.js"></script>
<script type="text/javascript" src="${ctx}/script/build/datatable/datatable.js"></script>

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

<style>
ul.pagination {
    margin: 0 auto;
    padding: 0;
    display: inline-block;
    background: #f2f2f2;    
    border: 1px solid #e6e6e6;
    color: #000;
    font-size: 10pt;
    font-weight: normal;
    list-style-type: none;
    white-space: nowrap;
    zoom:1;
    *display:inline;
}
ul.pagination li {
    float: left;
    padding: 1px;   
}
ul.pagination li.active {
    padding: 3px 1px;
    /*background: #e0e0e0;
    color: #174962;*/
    background: #11547a;
    color: #def;
}
ul.pagination a {
    display: block;
    float: left;
    padding: 3px 1px;
    /*background:url("../image/deco/menu_bg_pagination.gif") repeat-x #ffffff;*/
    background: #11547a;
    border: 1px solid #05314a;
    color: #fff;
    line-height: 1em;
    text-decoration: none;
}
ul.pagination a:hover {
    /*background: #11547a;*/
    background: #fff;
    border: 1px solid #036;
    /*color: #def;*/
    color:#11547a;
}

</style>

<style>
input.button120 {
	width:90px;
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

span.pagebanner {
	background-color: #eee;
	border: 1px dotted #999;
	padding: 2px 4px 2px 4px;
	width: 100%;
	margin-top: 10px;
	display: block;
	border-bottom: none;
}

span.pagelinks {
	background-color: #eee;
	border: 1px dotted #999;
	padding: 2px 4px 2px 4px;
	width: 100%;
	display: block;
	border-top: none;
	margin-bottom: -5px;
}
	#ver-zebra
	{
		font-family: "Lucida Sans Unicode", "Lucida Grande", Sans-Serif;
		font-size: 10px;
		/*margin: 45px;*/
		/*width: 480px;*/
		text-align: center;
		border-collapse: collapse;
	}
	#ver-zebra th
	{
		font-size: 12px;
		font-weight: normal;
		/*padding: 12px 15px;*/
		border-top: 1px solid #0066aa;
		border-right: 1px solid #0066aa;
		border-bottom: 1px solid #0066aa;
		border-left: 1px solid #fff;
		/*color: #039;
		background: #eff2ff;*/
	}
	#ver-zebra td
	{
		/*padding: 4px 7px;*/
		border-right: 0px solid #0066aa;
		/*border-left: 2px solid #fff;*/
		border-bottom: 1px solid #0066aa;
		/*color: #669;*/
	}
	.vzebra-odd
	{
		/*background: #eff2ff;*/
		background: #fff;
	}
	.vzebra-even
	{
		background: #e8edff;
	}
	#ver-zebra #vzebra-adventure, #ver-zebra #vzebra-children
	{
		background: #d0dafd;
		border-bottom: 1px solid #c8d4fd;
	}
	#ver-zebra #vzebra-comedy, #ver-zebra #vzebra-action
	{
		background: #dce4ff;
		border-bottom: 1px solid #d6dfff;
	}

#hor-minimalist-b
{
	font-family: "Lucida Sans Unicode", "Lucida Grande", Sans-Serif;
	font-size: 12px;
	background: #fff;
	/*margin: 10px;*/
	/*width: 750px;*/
	border-collapse: collapse;
	text-align: center;
}
#hor-minimalist-b th
{
	font-size: 12px;
	font-weight: normal;
	color: #039;
	padding: 5px 4px;
	border-bottom: 1px solid #6678b1;
	border-right: 1px solid #6678b1;
	border-top: 1px solid #6678b1;
	background: #eff2ff;
}
#hor-minimalist-b td
{
	border-bottom: 1px solid #ccc;
	border-right: 1px solid #ccc;
	color: #669;
	padding: 6px 8px;
}
#hor-minimalist-b tbody tr:hover td
{
	color: #009;
}

 .normal { background-color: white }
		 .highlight { background-color: orange }
		 
		 
/* CSS Tabs */
#navlist {
	padding: 3px 0;
	margin-left: 0;
	border-bottom: 1px solid #778;
	font: bold 12px Verdana, sans-serif;
}

#navlist li {
	list-style: none;
	margin: 0;
	display: inline;
}

#navlist li a {
	padding: 3px 0.5em;
	margin-left: 3px;
	border: 1px solid #778;
	border-bottom: none;
	background: #DDE;
	text-decoration: none;
}

#navlist li a:link { color: #448; }
#navlist li a:visited { color: #667; }

#navlist li a:hover {
	color: #000;
	background: #AAE;
	border-color: #227;
}

#navlist li a#current {
	background: white;
	border-bottom: 1px solid white;
}

table.formlist tr.even{
	background: #dddddd;
	border:0px
}
table.formlist tr.even:hover{
	background-color: rgb(226,176,84);
	border:0px
}
table.formlist tr.odd{	
	background-color: #fbfbfb;
	border:0px
}
table.formlist tr.odd:hover{
	background-color: rgb(226,176,84);
	border:0px
}
table.formlist tr.selected{	
	background-color: #AAF;
	border:0px
}
table.formlist tr.selected:hover{
	background-color: rgb(226,176,84);
	border:0px
}

table.formlist th {
	border-top:1px solid #FFFFFF;
	border-left:1px solid #FFFFFF;
	border-right:1px solid #999999;
	border-bottom:1px solid #999999;	
	background-color: rgb(160,185,224);
	text-align:center;
	padding:5px 5px 5px 20px;
	vertical-align: middle;
	font-style: normal;
}

table.axial td .table.formlist 
{
	width:98%;
	/*clear:left;*/
	background-color: #fbfbfb;
	border-style : solid;
	border-width : 4px;  
	border-color: #cccccc;
}

table.axial td .table.formlist tr.even{
	background: #dddddd;
	border:0px
}

table.axial td .table.formlist tr.odd{	
	background-color: #fbfbfb;
	border:0px
}

table.axial td .table.formlist td.even{
	background-color: #dddddd;
	border:0px
}

table.axial td .table.formlist td.odd{	
	background-color: #fbfbfb;
	border:0px
}

table.axial td .table.formlist th {
	border-top:1px solid #FFFFFF;
	border-left:1px solid #FFFFFF;
	border-right:1px solid #999999;
	border-bottom:1px solid #999999;	
	background-color: rgb(160,185,224);
	text-align:center;
	padding:5px 5px 5px 20px;
	vertical-align: middle;
	font-style: normal;
}
</style>
    
	
	  
	
		
 		<div class="nav_alphabet" style="background-color: rgb(248, 246, 233); border: 1px solid  #0066aa; text-align:center; padding-top:1pt; font: bold 12px Verdana, sans-serif;">

		

	<table align="center"><tr>
				<td style="border :1px dotted #0066aa;">
					<form name="listForm1" action="findInvoiceByIdPage.htm" method="post"  style="margin :2px;">
					<span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Invoice Id</strong> :</span>
						<input name="invoiceId" value="${viewinvoices.param.invoiceId}" type="text" style="font:10px Verdana, sans-serif;margin-right:10pt;" />
						&nbsp;
						<input style="font:10px Verdana, sans-serif;" type="submit" class="button120" value="Find By Id"/>
					</form>
				</td>
				<td width="2%"></td>
				
				<td style="border :1px dotted #0066aa;">
							<form name="listForm" action="showDraftInvoicesPage.htm" method="post"  style="margin :2px;">
							
								<span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Exercice</strong> : </span>
								<select style="font:10px Verdana, sans-serif;margin-right:10pt;" name="year" >
									<option value="">Any</option>
									<c:forEach var="y" items="${viewinvoices.exercicesOptions}">
										<option value="${y.id}"<c:if test='${viewinvoices.param.year==y.id}'> selected</c:if>>${y.name}</option>
									</c:forEach>
								</select> 

								<span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Mois</strong> : </span>
								<select style="font:10px Verdana, sans-serif;margin-right:10pt;" name="mois" >
										<option value="-1">Any</option>
										<option value="0" <c:if test='${viewinvoices.param.mois==0}'> selected</c:if>>Janvier</option>
										<option value="1" <c:if test='${viewinvoices.param.mois==1}'> selected</c:if>>Fevrier</option>
										<option value="2" <c:if test='${viewinvoices.param.mois==2}'> selected</c:if>>Mars</option>
										<option value="3" <c:if test='${viewinvoices.param.mois==3}'> selected</c:if>>Avril</option>
										<option value="4" <c:if test='${viewinvoices.param.mois==4}'> selected</c:if>>Mai</option>
										<option value="5" <c:if test='${viewinvoices.param.mois==5}'> selected</c:if>>Juin</option>
										<option value="6" <c:if test='${viewinvoices.param.mois==6}'> selected</c:if>>Juillet</option>
										<option value="7" <c:if test='${viewinvoices.param.mois==7}'> selected</c:if>>Aout</option>
										<option value="8" <c:if test='${viewinvoices.param.mois==8}'> selected</c:if>>Septembre</option>
										<option value="9" <c:if test='${viewinvoices.param.mois==9}'> selected</c:if>>Octobre</option>
										<option value="10" <c:if test='${viewinvoices.param.mois==10}'> selected</c:if>>Novembre</option>
										<option value="11" <c:if test='${viewinvoices.param.mois==11}'> selected</c:if>>Decembre</option>									 
								</select>

								<span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Client</strong> :</span>
								<input name="customer" value="${viewinvoices.param.customer}" type="text" style="font:10px Verdana, sans-serif;margin-right:10pt;" />
								
								<span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Type</strong> :</span>
								<select style="font:10px Verdana, sans-serif;margin-right:10pt;" name="type" >
								  <option value="">Any</option>
								  <c:forEach var="t" items="${viewinvoices.typeOptions}">
										<option value="${t.id}"<c:if test='${viewinvoices.param.type==t.id}'> selected</c:if>>${t.name}</option>
									</c:forEach>
								</select> 
								
								

								<%-- br/> 
								<span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Status</strong> : </span> &nbsp;
									<c:choose>
										<c:when test='${viewinvoices.param.pending=="1"}'>
											<input type="checkbox" name="pending" value="1" checked>&nbsp;<span style="font:10px Verdana, sans-serif;margin-right:10pt; color: blue;">En Attente</span> &nbsp;
										</c:when>
										<c:otherwise>
											<input type="checkbox" name="pending" value="1">&nbsp;<span style="font:10px Verdana, sans-serif;margin-right:10pt; color: blue;">En Attente</span> &nbsp;
										</c:otherwise>
									</c:choose>
									
									<c:choose>
										<c:when test='${viewinvoices.param.approved=="1"}'>
											<input type="checkbox" name="approved" value="1" checked>&nbsp;<span style="font:10px Verdana, sans-serif;margin-right:10pt; color: blue;">Approuvée</span> &nbsp;
										</c:when>
										<c:otherwise>
											<input type="checkbox" name="approved" value="1">&nbsp;<span style="font:10px Verdana, sans-serif;margin-right:10pt; color: blue;">Approuvée</span> &nbsp;
										</c:otherwise>
									</c:choose>
								
									<c:choose>		     			      		
										<c:when test='${viewinvoices.param.ongoing=="1"}'>
											<input type="checkbox" name="ongoing" value="1" checked>&nbsp;<span style="font:10px Verdana, sans-serif;margin-right:10pt;color: blue;">Envoyée & Non payée</span> &nbsp;
										</c:when>
										<c:otherwise>
											<input type="checkbox" name="ongoing" value="1">&nbsp;<span style="font:10px Verdana, sans-serif;margin-right:10pt;color: blue;">Envoyée & Non payée</span> &nbsp;
										</c:otherwise>
									</c:choose>
									
									<c:choose>		     			      		
										<c:when test='${viewinvoices.param.paid=="1"}'>
											<input type="checkbox" name="paid" value="1" checked>&nbsp;<span style="font:10px Verdana, sans-serif;margin-right:10pt;color: blue;">Payée</span> &nbsp;
										</c:when>
										<c:otherwise>
											<input type="checkbox" name="paid" value="1">&nbsp;<span style="font:10px Verdana, sans-serif;margin-right:10pt;color: blue;">Payée</span> &nbsp;
										</c:otherwise>
									</c:choose>
									
									<c:choose>		     			      		
										<c:when test='${viewinvoices.param.unpaid=="1"}'>
											<input type="checkbox" name="unpaid" value="1" checked>&nbsp;<span style="font:10px Verdana, sans-serif;margin-right:10pt;color: blue;">Non payé</span> &nbsp;
										</c:when>
										<c:otherwise>
											<input type="checkbox" name="unpaid" value="1">&nbsp;<span style="font:10px Verdana, sans-serif;margin-right:10pt;color: blue;">Non payé</span> &nbsp;
										</c:otherwise>
									</c:choose--%>
									&nbsp;
								<input style="font:10px Verdana, sans-serif;" type="submit" class="button120" value="Search"/>
								</form>
				</td></tr></table>
				
			</div>
			<br/>
			

			        <table id="ver-zebra" width="100%" cellspacing="0" class="formlist">
									<caption><span style="color:orange;">invoices</span></caption>
									<thead>
									<tr>
										<th scope="col"></th>
										<th scope="col">Parent</th>
										<th scope="col">Client</th>
										<th scope="col">Exercice</th>
										<th scope="col">Crée le</th>
										<th scope="col">Envoyé le</th>
										<th scope="col">Payée le</th>
										<th scope="col">Statut</th>
										<th scope="col">Mont. brut</th>
										<th scope="col">Mont. net</th>
										<th scope="col">Total payé</th>
										<th scope="col">Solde</th>
										<th scope="col">Type</th>
										<th scope="col">Ass.</th>
										<th scope="col">Rap</th>
										<th scope="col">Impr</th>
										<th scope="col">Hist</th>
									</tr>
									</thead>
									<tbody>
									
									
									
									<c:set var="row" value="0"/>
									<c:set var="totalFactureNet" value="0"/>
									<c:set var="totalFacture" value="0"/>
									<c:set var="totalPaid" value="0"/>
									
									 <c:forEach var="item" items="${viewinvoices.invoices}" varStatus="loop">

											
											<c:choose>
												<c:when test='${item.type eq "CN" }'>												
													<c:set var="totalFactureNet" value="${ totalFactureNet - item.amountEuro}"/>
													<c:set var="totalFacture" value="${ totalFacture - item.amountEuroTva}"/>
													<c:set var="totalPaid" value="${ totalPaid - item.amountPaidEuro}"/>
												</c:when>
												<c:otherwise>
													<c:set var="totalFactureNet" value="${item.amountEuro + totalFactureNet }"/>
													<c:set var="totalFacture" value="${item.amountEuroTva + totalFacture}"/>
													<c:set var="totalPaid" value="${item.amountPaidEuro + totalPaid}"/>
												</c:otherwise>
											</c:choose>

											<c:if test='${item.type != "CN" }'>
												<c:choose>
													<c:when test='${item.amountPaidEuro - item.amountEuro < 0 }'>												
													 <c:set var="backgroundStyle" value="background-color:#FF6262;"/>
													</c:when>
													<c:otherwise>
													 <c:set var="backgroundStyle" value="background-color:#55FF55;"/>
													</c:otherwise>
												</c:choose>
											</c:if>

											<c:if test='${item.type eq "CN" }'>
												<c:set var="backgroundStyle" value="background-color:#55FF55;"/>
											</c:if>

											
									 	
										 	<c:choose>
									    		<c:when test='${row % 2 eq 0 }'>
									      			<tr style="background: #eff2ff;" onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
									      		</c:when>
									      		<c:otherwise>
									      			<tr onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
									      		</c:otherwise>
									     	</c:choose>
									     	
											<th>	
											<interaudit:accessRightSet right="CREATE_INVOICE">
											<a href="${ctx}/editInvoicePage.htm?invoiceId=${item.id}">${item.reference}</a>
											</interaudit:accessRightSet>
											<interaudit:accessRightNotSet right="CREATE_INVOICE">
											---
											</interaudit:accessRightNotSet>

											</th>											
											<td> <span style="color:red;font-weight: bold;">${item.parentReference}</span></td>
											<td align="left">${fn:substring(item.customerName,0,15)}</td>
											<td>${item.exercise}</td>
											<td>${item.dateFacture}</td>
											<td>${item.sentDate}</td>
											<td>${item.dateOfPayment}</td>
											<td  style="background-color :  #ffffcc; cursor:hand;">
											<span id="${item.id}.status">
											<c:choose>

													<c:when test='${item.status eq "pending" }'>
													 <span style="color:red;font-weight: bold;">En Attente</span>
													</c:when>

													<c:when test='${item.status eq "approved" }'>
													 <span style="color:#05A5D8;font-weight: bold;">Approuvée</span>
													</c:when>

													<c:when test='${item.status eq "sent" }'>
													 <span style="color:blue;font-weight: bold;">Envoyée</span>
													</c:when>													

													<c:when test='${item.status eq "paid" }'>
													 <span style="color:#0392DE;font-weight: bold;">Payée</span>
													</c:when>

													<c:when test='${item.status eq "cancelled" }'>
													 <span style="color:red;font-weight: bold;">Annulée</span>
													</c:when>
													
									     		</c:choose>  
											
											</span>
														<!--script>
																new Ajax.InPlaceCollectionEditor(
																  "${item.id}.status", "${ctx}/changeFieldInvoiceStatus.htm?id=${item.id}", 
																  { collection: ['sent','cancelled']});								
														</script-->
											</td>
											<td>
											<c:choose>
												<c:when test='${item.type eq "CN" }'>												
												 <fmt:formatNumber type="currency" currencySymbol="&euro;">
														${item.amountEuro *-1}
												</fmt:formatNumber>
												</c:when>
												<c:otherwise>
												<fmt:formatNumber type="currency" currencySymbol="&euro;">
														${item.amountEuro}
											</fmt:formatNumber>
												</c:otherwise>
											</c:choose>
											
											</td>
											<td>

											<c:choose>
												<c:when test='${item.type eq "CN" }'>												
													<fmt:formatNumber type="currency" currencySymbol="&euro;">
														${item.amountEuroTva *-1}
													</fmt:formatNumber>
												</c:when>
												<c:otherwise>
													<fmt:formatNumber type="currency" currencySymbol="&euro;">
														${item.amountEuroTva}
													</fmt:formatNumber>
												</c:otherwise>
											</c:choose>
											
											</td>
											<td>											
										    <c:choose>
												<c:when test='${item.type eq "CN" }'>												
													<fmt:formatNumber type="currency" currencySymbol="&euro;">
														${item.amountPaidEuro *-1}
													</fmt:formatNumber>
												</c:when>
												<c:otherwise>
													<fmt:formatNumber type="currency" currencySymbol="&euro;">
														${item.amountPaidEuro}
													</fmt:formatNumber>
												</c:otherwise>
											</c:choose>
										</td>
											<td style=${backgroundStyle}>

											<c:choose>
												<c:when test='${item.type eq "CN" }'>												
													<fmt:formatNumber type="currency" currencySymbol="&euro;">
														${(item.amountPaidEuro - item.amountEuroTva + item.totalNoteDeCredit) *-1}
													</fmt:formatNumber>
												</c:when>
												<c:otherwise>
													<fmt:formatNumber type="currency" currencySymbol="&euro;">
														${item.amountPaidEuro - item.amountEuroTva + item.totalNoteDeCredit}
													</fmt:formatNumber>
												</c:otherwise>
											</c:choose>											
											</td>
											<td>
											<c:choose>
												<c:when test='${item.type eq "AD" }'>												 
												 <span style="color:black;">Acompte</span>
												</c:when>
												<c:when test='${item.type eq "CN" }'>												
												 <span style="color:black;">Note de credit</span>
												</c:when>
												<c:otherwise>
												<span style="color:black;">Facture finale</span>
												</c:otherwise>
											</c:choose>
											</td>
											<td>${item.associeCode}</td>
											<td>${item.countRappels}</td>
											<th align="center">
												
												<c:choose>
													<c:when test='${item.language eq "EN" }'>
													<a target="_disclaimer" href="http://${serverName}:${serverPort}/birt/run?__report=facture_report.rptdesign&__format=pdf&invoiceIdentifier=${item.id}">
												<img src="images/flag_england.gif" border="0" alt="PDF"/></a>
													</c:when>

													<c:when test='${item.language eq "FR"  }'>
													 <a target="_disclaimer" href="http://${serverName}:${serverPort}/birt/run?__report=facture_report.rptdesign&__format=pdf&invoiceIdentifier=${item.id}">
												<img src="images/flags_of_France.gif" border="0" alt="PDF"/></a>
													</c:when>

													<c:when test='${item.language eq "DE" }'>
													<a target="_disclaimer" href="http://${serverName}:${serverPort}/birt/run?__report=facture_report.rptdesign&__format=pdf&invoiceIdentifier=${item.id}">
												<img src="images/flag_german.gif" border="0" alt="PDF"/></a>
													</c:when>													
									     		</c:choose>  

											
												
										</th>
										
										<td><img src="images/engrenage.gif" border="0" onclick="editInvoiceHistoryItem(${item.id},'${item.reference}')"  alt="Show History"/></td>
										
											
										</tr>
									
										<c:set var="row" value="${row + 1}"/>
									 
									  </c:forEach>

									  <c:choose>
									    		<c:when test='${totalPaid - totalFacture < 0 }'>												
												 <c:set var="backgroundStyle" value="color:black;background-color:#FF6262;font-weight: bold;"/>
									      		</c:when>
									      		<c:otherwise>
												 <c:set var="backgroundStyle" value="color:black;background-color:#55FF55;font-weight: bold;"/>
									      		</c:otherwise>
									     	</c:choose>

									  <tr>
										<td colspan="8"><span style="color:red;">Total</span></td>
										<td><span style="color:red;font-weight: bold;">
										
										<i><fmt:formatNumber type="currency" currencySymbol="&euro;">
														${totalFactureNet}
										</fmt:formatNumber></i>
										
										<td><span style="color:red;font-weight: bold;">
										
										<i><fmt:formatNumber type="currency" currencySymbol="&euro;">
														${totalFacture}
										</fmt:formatNumber></i>
										
										</span></td>
										<td><span style="color:blue;font-weight: bold;" >
										
										<i><fmt:formatNumber type="currency" currencySymbol="&euro;">
														${totalPaid}
										</fmt:formatNumber></i>
										</span></td>
										<td style=${backgroundStyle}>
										
										<fmt:formatNumber type="currency" currencySymbol="&euro;">
														${totalPaid - totalFacture}
										</fmt:formatNumber>
										
										
										</td>
										<td colspan="5">&nbsp;</td>
										
									  </tr>
									
									</tbody>
								</table>
								<br/>
						</div>
  </div>
  
  <div  class="nav_alphabet"  style="text-align:center;font:  10px Verdana, sans-serif;">           
                <ul class="pagination" align="center">
                <c:if test="${totalPage > 1}">
                    <c:set var="url" value="p=" />
                    <c:set var="url2" value="showDraftInvoicesPage.htm" />
                    <c:if test="${!empty viewinvoices.param.invoiceId}">
                        <c:set var="url" value="invoiceId=${viewinvoices.param.invoiceId}&${url}" />
                        <c:set var="url2" value="findInvoiceByIdPage.htm" />
                    </c:if>
                    <c:if test="${!empty viewinvoices.param.year}">
                        <c:set var="url" value="year=${viewinvoices.param.year}&${url}" />
                    </c:if>
                    <c:if test="${!empty viewinvoices.param.mois}">
                        <c:set var="url" value="mois=${viewinvoices.param.mois}&${url}" />
                    </c:if>
                    <c:if test="${!empty viewinvoices.param.type}">
                        <c:set var="url" value="type=${viewinvoices.param.type}&${url}" />
                    </c:if>
                    <c:if test="${!empty  viewinvoices.param.approved}">
                        <c:set var="url" value="approved=${viewinvoices.param.approved}&${url}" />
                    </c:if>
                    <c:if test="${!empty viewinvoices.param.ongoing}">
                        <c:set var="url" value="ongoing=${viewinvoices.param.ongoing}&${url}" />
                    </c:if>
                    <c:if test="${!empty  viewinvoices.param.paid}">
                        <c:set var="url" value="paid=${viewinvoices.param.paid}&${url}" />
                    </c:if>
                    <c:if test="${!empty  viewinvoices.param.customer}">
                        <c:set var="url" value="customer=${viewinvoices.param.customer}&${url}" />
                    </c:if>
                    
                    
                    
                    
                    
                        <li><a style="background:#fff;" href="${ctx}/${url2}?${url}${1}"><img src="images/pg-first.gif" border="0"/></a></li>
                    
                        <c:choose>
                            <c:when test="${currentPage <= 1}">
                                <li> <a style="background:#fff;" href="${ctx}/${url2}?${url}${currentPage }"><img src="images/pg-prev.gif" border="0"/></a></li>
                            </c:when>
                            <c:otherwise>
                                <li> <a style="background:#fff;" href="${ctx}/${url2}?${url}${currentPage - 1}"><img src="images/pg-prev.gif"  border="0"/></a></li>
                            </c:otherwise>
                        </c:choose>
                    
                        <c:set var="max_page" value="10" />
                        
                        <c:set var="page_toshow" value="${totalPage}" />
                        <c:set var="start_toshow" value="${currentPage}" />
                        
                        <c:if test="${currentPage + max_page > totalPage}">
                            <c:set var="page_toshow" value="${totalPage}" />
                        </c:if>
                        
                        <c:if test="${currentPage + max_page <= totalPage}">
                            <c:set var="page_toshow" value="${currentPage + max_page}" />
                        </c:if>
                        
                        <c:if test="${page_toshow - currentPage < max_page}">
                            <c:if test="${page_toshow - max_page > 0}">
                                <c:set var="start_toshow" value="${page_toshow - max_page}" />
                            </c:if>         
                        </c:if>

                        
                    
                        <c:forEach var="x" begin="${start_toshow}" end="${page_toshow}" step="1">
                            <c:choose>
                                <c:when test="${currentPage == x}">
                                    <li><a style='background-color: #7C9FCB;'><c:out value="${x}"/></a></li>                                    
                                </c:when>
                                <c:otherwise>
                                    <li><a href="${ctx}/${url2}?${url}${x}"><c:out value="${x}"/></a></li>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    
                        <c:choose>
                            <c:when test="${currentPage >= totalPage}">
                               <li><a  style="background:#fff;" href="${ctx}/${url2}?${url}${currentPage }">   <img src="images/pg-next.gif" border="0"/></a></li>
                            </c:when>
                            <c:otherwise>
                                <li>
                                <a  style="background:#fff;" href="${ctx}/${url2}?${url}${currentPage + 1}"> <img src="images/pg-next.gif" border="0"/></a></li>
                            </c:otherwise>
                        </c:choose>
                        <li><a  style="background:#fff;" href="${ctx}/${url2}?${url}${totalPage}"><img src="images/pg-last.gif" border="0"/></a></li>                     
                    </c:if>

                    </ul>
</div>

	<!-- END ADDED PART -->  
	
	<div id="showInvoiceHistoryDialog">
	<div class="hd">
        <span id="formTitle" style="color:blue;"></span>
    </div>
    <div id="containerTableHistory" class="bd"></div>
	
</div>