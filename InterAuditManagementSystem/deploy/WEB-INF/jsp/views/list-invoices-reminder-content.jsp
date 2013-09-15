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
<script type="text/javascript" src="${ctx}/script/build/datasource/datasource-beta-min.js"></script>
<script type="text/javascript" src="${ctx}/script/build/datatable/datatable-beta-min.js"></script>

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

  a {text-decoration:none;color: blue;}

 .normal { background-color: white }
 .highlight { border: 4px solid red;font-family:Arial,Helvetica,sans-serif;font-size: 200%;font-weight:bold;color: blue;}
    		 
		 
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
				<!--td style="border :1px dotted #0066aa;">
					<form name="listForm1" action="showInvoicesReminderPage.htm" method="post"  style="margin :2px;">
					<span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Invoice Id</strong> :</span>
						<input name="invoiceReminderId" value="${viewinvoicereminders.param.invoiceReminderId}" type="text" style="font:10px Verdana, sans-serif;margin-right:10pt;" />
						&nbsp;
						<input style="font:10px Verdana, sans-serif;" type="submit" class="button120" value="Find By Id"/>
					</form>
				</td>
				<td width="2%"></td-->
				
				<td style="border :1px dotted #0066aa;">
							<form name="listForm" action="showInvoicesReminderPage.htm" method="post"  style="margin :2px;">
							
								<span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Exercise</strong> : </span>
								<select style="font:10px Verdana, sans-serif;margin-right:10pt;" name="year" >
									<option value="">Any</option>
									<c:forEach var="y" items="${viewinvoicereminders.exercicesOptions}">
										<option value="${y.id}"<c:if test='${viewinvoicereminders.param.year==y.id}'> selected</c:if>>${y.name}</option>
									</c:forEach>
								</select> 

								<span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Customer</strong> :</span>
								<input name="customer" value="${viewinvoicereminders.param.customer}" type="text" style="font:10px Verdana, sans-serif;margin-right:10pt;" />
								
								
								<span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Status</strong> : </span> &nbsp;
									<%--c:choose>
										<c:when test='${viewinvoicereminders.param.active=="1"}'>
											<input type="checkbox" name="active" value="1" checked>&nbsp;<span style="font:10px Verdana, sans-serif;margin-right:10pt; color: blue;">Actif</span> &nbsp;
										</c:when>
										<c:otherwise>
											<input type="checkbox" name="active" value="1">&nbsp;<span style="font:10px Verdana, sans-serif;margin-right:10pt; color: blue;">Actif</span> &nbsp;
										</c:otherwise>
									</c:choose>
									
									<c:choose>
										<c:when test='${viewinvoicereminders.param.notActive=="1"}'>
											<input type="checkbox" name="notActive" value="1" checked>&nbsp;<span style="font:10px Verdana, sans-serif;margin-right:10pt; color: blue;">Inactif</span> &nbsp;
										</c:when>
										<c:otherwise>
											<input type="checkbox" name="notActive" value="1">&nbsp;<span style="font:10px Verdana, sans-serif;margin-right:10pt; color: blue;">Inactif</span> &nbsp;
										</c:otherwise>
									</c:choose--%>
								
									<c:choose>		     			      		
										<c:when test='${viewinvoicereminders.param.sent=="1"}'>
											<input type="checkbox" name="sent" value="1" checked>&nbsp;<span style="font:10px Verdana, sans-serif;margin-right:10pt;color: blue;">Envoy&eacute;</span> &nbsp;
										</c:when>
										<c:otherwise>
											<input type="checkbox" name="sent" value="1">&nbsp;<span style="font:10px Verdana, sans-serif;margin-right:10pt;color: blue;">Envoy&eacute;</span> &nbsp;
										</c:otherwise>
									</c:choose>
									
									<c:choose>		     			      		
										<c:when test='${viewinvoicereminders.param.notSent=="1"}'>
											<input type="checkbox" name="notSent" value="1" checked>&nbsp;<span style="font:10px Verdana, sans-serif;margin-right:10pt;color: blue;">Pas envoy&eacute;</span> &nbsp;
										</c:when>
										<c:otherwise>
											<input type="checkbox" name="notSent" value="1">&nbsp;<span style="font:10px Verdana, sans-serif;margin-right:10pt;color: blue;">Pas envoy&eacute;</span> &nbsp;
										</c:otherwise>
									</c:choose>									
									&nbsp;
								<input style="font:10px Verdana, sans-serif;" type="submit" class="button120" value="Search"/>
								</form>
				</td></tr></table>
				
			</div>
			<br/>
					        <table id="ver-zebra" width="100%" cellspacing="0" class="formlist">
									<caption><span style="color:orange;">reminders</span></caption>
									<thead>
									<tr>
										<th scope="col">Reference</th>
										<th scope="col">Client</th>
										<th scope="col">Exercice</th>
										<th scope="col">Numero</th>
										<th scope="col">Date creation</th>
										<th scope="col">Envoy&eacute;</th>
										<th scope="col">Envoy&eacute; par</th>
										<!--th scope="col">Debut</th>
										<th scope="col">Envoy� le</th>
										<th scope="col">Fin</th>
										<th scope="col">Actif</th>
										<th scope="col">Envoy�</th>
										<th scope="col">Envoy� par</th>
										<th scope="col">Numero</th>
										<th scope="col">Facture</th>
										<th scope="col">Facture statut</th-->
										<th scope="col">Print</th>
									</tr>
									</thead>
									<tbody>
									
									
									
									<c:set var="row" value="0"/>
									
									
									 <c:forEach var="item" items="${viewinvoicereminders.invoiceReminders}" varStatus="loop">

										 	<c:choose>
									    		<c:when test='${row % 2 eq 0 }'>
									      			<tr style="background: #eff2ff;" onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
									      		</c:when>
									      		<c:otherwise>
									      			<tr onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
									      		</c:otherwise>
									     	</c:choose>
									     	
											<th>												
											<a href="${ctx}/editRemindInvoicePage.htm?reminderId=${item.id}">${item.id}</a>
											</th>
											<td>${item.customer.companyName}</td>
											<td>${item.exercise}</td>
											<td>${item.order}</td>	
											<td>${item.remindDate}</td>
											<td>											
												<c:choose>
														<c:when test='${item.sent eq true }'>
														 <span style="color:green;">Envoy&eacute;</span>
														</c:when>
	
														<c:when test='${item.sent eq false }'>
														 <span style="color:red;">Pas envoy&eacute;</span>
														</c:when>
										     	</c:choose>  
											</td>
											<td>	
											
											<c:choose>
														<c:when test='${item.sender == null }'>
														 <span style="color:green;">---</span>
														</c:when>
	
														<c:when test='${item.sender != null }'>
														 <span style="color:red;">${item.sender.fullName}	</span>
														</c:when>
										     	</c:choose>
																								
											</td>
											<th>
											
												<c:choose>

													<c:when test='${item.lang eq "EN" }'>
													<a target="_disclaimer" href="http://${serverName}:${serverPort}/birt/run?__report=alerte_report.rptdesign&__format=pdf&reminderID=${item.id}">
												<img src="images/flag_england.gif" border="0" alt="PDF"/></a>
													</c:when>

													<c:when test='${item.lang eq "FR"  }'>
													 <a target="_disclaimer" href="http://${serverName}:${serverPort}/birt/run?__report=alerte_report.rptdesign&__format=pdf&reminderID=${item.id}">
												<img src="images/flags_of_France.gif" border="0" alt="PDF"/></a>
													</c:when>

													<c:when test='${item.lang eq "DE" }'>
													<a target="_disclaimer" href="http://${serverName}:${serverPort}/birt/run?__report=alerte_report.rptdesign&__format=pdf&reminderID=${item.id}">
												<img src="images/flag_german.gif" border="0" alt="PDF"/></a>
													</c:when>													

												
													
									     		</c:choose>  
											</th>
											<%--td>${item.startValidityDate}</td>
											<td>${item.remindDate}</td>
											<td>${item.endValidityDate}</td>
											<td  style="background-color :  #ffffcc; cursor:hand;">											
												<c:choose>
														<c:when test='${item.active eq true }'>
														 <span style="color:green;">En cours</span>
														</c:when>
	
														<c:when test='${item.active eq false }'>
														 <span style="color:red;">Arr�t�</span>
														</c:when>
										     	</c:choose>  
											</td>
											<td  style="background-color :  #ffffcc; cursor:hand;">											
												<c:choose>
														<c:when test='${item.sent eq true }'>
														 <span style="color:green;">Envoy�</span>
														</c:when>
	
														<c:when test='${item.sent eq false }'>
														 <span style="color:red;">Pas envoy�</span>
														</c:when>
										     	</c:choose>  
											</td>
											<td>											
														${item.sender.fullName}											
											</td>
											
											<td>
											
														${item.facture.reference}										
										</td>
										<td>
											
														${item.facture.status}										
										</td>

										<th>

												<c:choose>

													<c:when test='${item.facture.language eq "EN" }'>
													<a target="_disclaimer" href="http://iaapp01:8080/birt/run?__report=alerte_report.rptdesign&__format=pdf&invoiveIdentifier=${item.facture.id}">
												<img src="images/flag_england.gif" border="0" alt="PDF"/></a>
													</c:when>

													<c:when test='${item.facture.language eq "FR"  }'>
													 <a target="_disclaimer" href="http://iaapp01:8080/birt/run?__report=alerte_report.rptdesign&__format=pdf&invoiveIdentifier=${item.facture.id}">
												<img src="images/flags_of_France.gif" border="0" alt="PDF"/></a>
													</c:when>

													<c:when test='${item.facture.language eq "DE" }'>
													<a target="_disclaimer" href="http://iaapp01:8080/birt/run?__report=alerte_report.rptdesign&__format=pdf&invoiveIdentifier=${item.facture.id}">
												<img src="images/flag_german.gif" border="0" alt="PDF"/></a>
													</c:when>													

												
													
									     		</c:choose>  

											
												
										</th--%>
										</tr>
									
										<c:set var="row" value="${row + 1}"/>
									 
									  </c:forEach>

									</tbody>
								</table>
								<br/>
						</div>
  </div>

	<!-- END ADDED PART -->  
	
	<div id="showInvoiceHistoryDialog">
	<div class="hd">
        <span id="formTitle" style="color:blue;"></span>
    </div>
    <div id="containerTableHistory" class="bd"></div>
	
</div>