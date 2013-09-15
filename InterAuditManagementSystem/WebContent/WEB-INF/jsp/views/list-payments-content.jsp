<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib  prefix="fmt"    uri="http://java.sun.com/jstl/fmt"%>
<%@ taglib uri="/tags/interaudit" prefix="interaudit" %>

<link type="text/css" href="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/themes/base/ui.all.css" rel="stylesheet" />
	<script type="text/javascript" src="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/jquery-1.3.2.js"></script>
	<script type="text/javascript" src="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/ui/ui.core.js"></script>
	<script type="text/javascript" src="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/ui/ui.datepicker.js"></script>
	<link type="text/css" href="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/demos.css" rel="stylesheet" />
	
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
<!-- START ADDED PART -->

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
	/*color: #039;*/
	padding: 5px 4px;
	border-bottom: 1px solid #6678b1;
	border-right: 1px solid #6678b1;
	border-top: 1px solid #6678b1;
	/*background: #eff2ff;*/
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
		<td  style="border :1px dotted #0066aa;">
			<form name="listForm1" action="showPaymentsPage.htm" method="post"  style="margin :2px;">
			<span style="font:10px Verdana, sans-serif;margin-right:5pt;"><strong>Invoice Id</strong> :</span>
				<input name="invoiceReference" value="${viewpayments.param.invoiceReference}" type="text" style="font:10px Verdana, sans-serif;margin-right:5pt;" />
				&nbsp;
				<input style="font:10px Verdana, sans-serif;" type="submit" class="button120" value="Find By Id"/>
			</form>
		</td >
		<td width="2%" >
		</td>
		<td style="border :1px dotted #0066aa;">
			<form name="listForm" action="showPaymentsPage.htm" method="post" style="margin :2px;">

				<span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Exercice</strong> : </span>
			<select style="font:10px Verdana, sans-serif;margin-right:10pt;" id="year" name="year" onchange="document.listForm.submit();">
		    <c:forEach var="y" items="${viewpayments.yearOptions}">
	             		<option value="${y.id}"<c:if test='${viewpayments.param.year==y.id}'> selected</c:if>>${y.name}</option>
	        </c:forEach>			
			</select> 
			
			
			
				<span style="font:10px Verdana, sans-serif;"><strong>Bank</strong> : </span>
				<select style="font:10px Verdana, sans-serif;margin-right:5pt;" name="bank" onchange="document.listForm.submit();">
				  	<option value="">Any</option>
				    <c:forEach var="y" items="${viewpayments.bankOptions}">
                		<option value="${y.id}"<c:if test='${viewpayments.param.bankCode==y.id}'> selected</c:if>>${fn:substring(y.name,0,15)}</option>
            		</c:forEach>
				</select> 

				<span style="font:10px Verdana, sans-serif;margin-right:5pt;"><strong>Customer</strong> :</span>
				<select style="font:10px Verdana, sans-serif;margin-right:5pt;" name="customerNameLike" onchange="document.listForm.submit();">
                    <c:forEach var="y" items="${viewpayments.customersOptions}">
                        <option value="${y.id}"<c:if test='${viewpayments.param.customerNameLike==y.id}'> selected</c:if>>${y.name}</option>
                    </c:forEach>
                </select> 
				<!--input name="customerNameLike" value="${viewpayments.param.customerNameLike}" type="text" style="font:10px Verdana, sans-serif;margin-right:5pt;" /-->
			
				
				
				<!--input style="font:10px Verdana, sans-serif;" type="submit" class="button120" value="Search"/>

				
				<div style="margin-top:5pt;">
				<span style="font:10px Verdana, sans-serif;margin-right:5pt;"><strong>Paid between</strong> :</span>
				<input name="fromDate" value="${viewpayments.param.fromDate}" id="datepicker1" type="text" style="font:10px Verdana, sans-serif;margin-right:5pt;" /> &nbsp;

				<span style="font:10px Verdana, sans-serif;margin-right:5pt;"><strong>And</strong> </span>
				<input name="toDate" value="${viewpayments.param.toDate}" id="datepicker2" type="text" style="font:10px Verdana, sans-serif;margin-right:5pt;" /> &nbsp;
				</div-->
				</form>
				</td></tr></table>
				
			</div>
			<br/>
			<%--span class="pagelinks"><span style="color:purple;">${ fn:length(viewpayments.payments) } payments found...</span>[First/Prev] <strong>1</strong>, <a href="/InterAuditWeb/WEB-INF/jsp/views/mission_interventions.jsp?id=227&amp;d-49653-p=2" title="Go to page 2">2</a>, <a href="/InterAuditWeb/WEB-INF/jsp/views/mission_interventions.jsp?id=227&amp;d-49653-p=3" title="Go to page 3">3</a> [<a href="/InterAuditWeb/WEB-INF/jsp/views/mission_interventions.jsp?id=227&amp;d-49653-p=2">Next</a>/<a href="/InterAuditWeb/WEB-INF/jsp/views/mission_interventions.jsp?id=227&amp;d-49653-p=3">Last</a>]</span--%>
			

			        <table id="ver-zebra" width="100%" cellspacing="0"  class="formlist">
									<caption><span>List of payments : </span> <span style="color:red;">${ fn:length(viewpayments.payments) } payments found...</span></caption>
									<thead>
											<tr>
												<th scope="col"></th>
												<th scope="col">Invoice</th>
												<th scope="col">Customer</th>
												<th scope="col">Bank</th>
												

												<th scope="col">Facture totale</th>
												<th scope="col">Note de credit</th>
												<!--th scope="col">Total paye</th>
												<th scope="col">Reste a payer</th-->
												<th scope="col">Montant pay&eacute;</th>

												<th scope="col">Currency</th>
												<th scope="col">Date of payment</th>
												<th scope="col">Type</th>
												<th scope="col">Actions</th>
												
											</tr>
										</thead>
										<tbody>
										
										  <c:choose>
											            <c:when test='${ fn:length(viewpayments.payments) == 0}'>
											                <tr><td align="center" colspan="13"><span  style="color:red;font-weight: bold;">Pas de payments... </span></td></tr>                                                 
											            </c:when>
											            
											            <c:otherwise>
											            
											                    <c:set var="row" value="0"/>
											                    <c:set var="totalPaye" value="0"/>
								                                <c:set var="totalResteAPayer" value="0"/>
								                                <c:set var="totalMontantPaye" value="0"/>
					                                            <c:forEach var="item" items="${viewpayments.payments}" varStatus="loop">
					
					                                            <c:choose>
					                                                <c:when test='${row % 2 eq 0 }'>
					                                                    <tr style="background: #eff2ff;"  onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
					                                                </c:when>
					                                                <c:otherwise>
					                                                    <tr onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
					                                                </c:otherwise>
					                                            </c:choose>
					                                            
					                                                <th>                                                
					                                                <interaudit:accessRightSet right="CREATE_INVOICE">
					                                                    <a href="${ctx}/showPaymentRegisterPage.htm?id=${item.id}"><span>${item.reference}</span></a>
					                                                </interaudit:accessRightSet>
					                                                <interaudit:accessRightNotSet right="CREATE_INVOICE">
					                                                    ---
					                                                </interaudit:accessRightNotSet>
					                                                </th>
					                                                <td>
					
					                                                <interaudit:accessRightSet right="CREATE_INVOICE">
					                                                    <a href="${ctx}/editInvoicePage.htm?invoiceId=${item.factureId}">${item.factureReference}</a>   
					                                                </interaudit:accessRightSet>
					                                                <interaudit:accessRightNotSet right="CREATE_INVOICE">
					                                                    ---
					                                                </interaudit:accessRightNotSet>
					                                                                                                
					                                                </td>
					                                                <td>${item.customerName}</td>
					                                                <td>${item.bankCode}</td>
					                                                
					
					                                                <td style="background-color :  #55FF55;">
					                                                <fmt:formatNumber type="currency" currencySymbol="&euro;">
					                                                        ${item.totalDu}
					                                                </fmt:formatNumber>
					                                                </td>
					                                                <td style="background-color :  #55FF55;">
					                                                <fmt:formatNumber type="currency" currencySymbol="&euro;">
					                                                        ${item.totalNoteCredit}
					                                                </fmt:formatNumber>
					                                                </td>
					                                                <!--td style="background-color :  #55FF55;">
					                                                <fmt:formatNumber type="currency" currencySymbol="&euro;">
					                                                        ${item.totalPaid}
					                                                </fmt:formatNumber>
					                                                </td>
					                                                <td style="background-color :  #55FF55;">
					                                                <fmt:formatNumber type="currency" currencySymbol="&euro;">
					                                                        ${item.totalRemaining}
					                                                </fmt:formatNumber>
					                                                </td-->
					                                                <td style="background-color :  #55FF55;">
					                                                <fmt:formatNumber type="currency" currencySymbol="&euro;">
					                                                        ${item.amount}
					                                                </fmt:formatNumber>
					                                                </td>
					                                                <%--td>${item.facture.amountEuro}</td--%>
					                                                <td>${item.currencyCode}</td>
					                                                <td>${item.paymentDate}</td>
					                                                <td>
																	
					                                                <c:choose>
					                                                    <c:when test='${item.reimbourse == true}'>
																			<c:set var="totalMontantPaye" value="${totalMontantPaye  - item.amount  }"/>
					                                                        REMBOURSEMENT
					                                                    </c:when>
																		 <c:when test='${item.escompte == true}'>
																			<c:set var="totalMontantPaye" value="${item.amount + totalMontantPaye }"/>
					                                                        ESCOMPTE
					                                                    </c:when>
					                                                    <c:otherwise>
																			<c:set var="totalMontantPaye" value="${item.amount + totalMontantPaye }"/>
					                                                        PAYMENT
					                                                    </c:otherwise>
					                                                 </c:choose>
					                                                </td>
					                                                <td><img src="images/engrenage.gif" border="0" onclick="editPaymentHistoryItem(${item.id},'${item.factureReference}')"  alt="Show History"/></td>
					                            
					                                            </tr>
					                                            
					                                             
					                                            <%--c:set var="totalPaye" value="${item.totalPaid + totalPaye }"/>
                                                                <c:set var="totalResteAPayer" value="${item.totalRemaining + totalResteAPayer }"/--%>
                                                                
					                                            <c:set var="row" value="${row + 1}"/>
					                                            </c:forEach>
					                                            
					                                            <tr>
							                                        <td colspan="6"><span style="color:red;">Total</span></td>
							                                        <!--td><span style="color:red;font-weight: bold;">
							                                        
							                                        <i><fmt:formatNumber type="currency" currencySymbol="&euro;">
							                                                        ${totalPaye}
							                                        </fmt:formatNumber></i>
							                                        
							                                        <td><span style="color:red;font-weight: bold;">
							                                        
							                                        <i><fmt:formatNumber type="currency" currencySymbol="&euro;">
							                                                        ${totalResteAPayer}
							                                        </fmt:formatNumber></i>
							                                        
							                                        </span></td-->
							                                        <td><span style="color:blue;font-weight: bold;" >
							                                        
							                                        <i><fmt:formatNumber type="currency" currencySymbol="&euro;">
							                                                        ${totalMontantPaye}
							                                        </fmt:formatNumber></i>
							                                        </span></td>
							                                        
							                                        <td colspan="4">&nbsp;</td>
							                                        
							                                      </tr>
			
											            </c:otherwise>
											</c:choose>

											

										</tbody>
									

								</table>
								<br/>
						</div>
  </div>

	<!-- END ADDED PART -->   
	
	<div id="showPaymentHistoryDialog">
    <div class="hd">
        <span id="formTitle" style="color:blue;"></span>
    </div>
    <div id="containerTableHistory" class="bd"></div>    
    </div>