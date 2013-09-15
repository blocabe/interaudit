<%@ page contentType="text/html"%>
<%@ taglib prefix="tab" uri="http://ditchnet.org/jsp-tabs-taglib"%>
<%@ taglib prefix="layout" uri="http://www.sourceforge.net/springLayout"%>
<%@ taglib prefix="tiles" uri="/WEB-INF/struts-tiles.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>




<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Register customer</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link href="css/odbb-skin.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="expires" content="-1">
	

</head>

<%
request.setAttribute("ctx", request.getContextPath()); 
%>

<style>
#hor-minimalist-b
{
	font-family: "Lucida Sans Unicode", "Lucida Grande", Sans-Serif;
	font-size: 12px;
	background: #fff;
	/*margin: 15px;*/
	width: 100%;
	border-collapse: collapse;
	text-align: left;
}
#hor-minimalist-b th
{
	font-size: 12px;
	font-weight: normal;
	color: #039;
	padding: 8px 6px;
	border: 1px solid #6678b1;
	/*border-top: 2px solid #6678b1;*/
}
#hor-minimalist-b td
{
	border: 1px solid #ccc;
	color: #669;
	padding: 3px 4px;
}
#hor-minimalist-b tbody tr:hover td
{
	background-color: #dddddd;
}

</style>
<body>
 

	

	


<div id="addDialog1" class="yui-pe-content">
	<div class="hd">
        <span id="formTitle2" style="color:#039;"></span>
    </div>
	<div class="bd">
			<fieldset> 
				<dl>
					 <div id="content">
							<table id="hor-minimalist-b" summary="Summary">
										<caption><span style="color:blue;">Budget </span></caption>
										<!--thead>
											<tr>
												<th scope="col">Costs</th>
												<th scope="col">To impute</th>
												<th scope="col" align="right">To deduct</th>																	
											</tr>
										</thead-->
										<tbody>
											<!--tr>
												<td align="left" ><span style="color:orange;">Heures prestées</span></td>
												<td><span style="color:red;">
												<fmt:formatNumber type="currency" currencySymbol="&euro;">
														${totalPrixVente}
												</fmt:formatNumber>	
												</span></td>
												<td></td>
											</tr>

											<tr>
												<td align="left" ><span style="color:orange;">Factures émises</span></td>
												<td></td>
												<td align="right" ><span style="color:red;">
												<fmt:formatNumber type="currency" currencySymbol="&euro;">
														${totalFacture}
												</fmt:formatNumber>	
												</span></td>
											</tr>

											<tr>
												<td align="left" ><span style="color:orange;">Dépenses</span></td>
												<td><span style="color:red;">
												<fmt:formatNumber type="currency" currencySymbol="&euro;">
														${totalCost}
												</fmt:formatNumber>
												</span></td>
												<td align="right" ></td>
											</tr>

											

											<tr>
												<th scope="col">Total</th>
												<th scope="col">
												<fmt:formatNumber type="currency" currencySymbol="&euro;">
														${totalPrixVente + totalCost}
												</fmt:formatNumber>
												</th>
												<th scope="col" align="right">
												<fmt:formatNumber type="currency" currencySymbol="&euro;">
														${totalFacture}
												</fmt:formatNumber>
												</th>																	
											</tr-->

											<tr>
												<th  scope="col">Budget Mission</th>
												<th colspan="2" scope="col" align="center" style="background-color :  #ffffcc;">
												<fmt:formatNumber type="currency" currencySymbol="&euro;">
														${budgetOPtion}
												</fmt:formatNumber>
												</th>																	
											</tr>
											
											
										</tbody>
									</table>

									<br/>
  

						 <table id="hor-minimalist-b" summary="Registered invoices">
										<caption><span style="color:blue;">Factures enregistrées </span></caption>
										<thead>
											<tr>
												<th align="center" scope="col">Reference</th>
												<th align="center" scope="col">Exercise</th>
												<th align="center" scope="col">Created date</th>
												<!--th align="center" scope="col">Sent date</th>
												<th align="center" scope="col">Paid date</th-->
												<th align="center" scope="col">Status</th>
												<!--th align="center" scope="col">Libelle</th-->
												<th align="center" scope="col">Type</th>
												<th align="center" scope="col">Amount</th>
											</tr>
										</thead>
										<tbody>
										<c:set var="totalFacture" value="0"/>
										<c:forEach var="item" items="${invoices}" >
											<tr>
										 		<td align="center"><span style="color:orange;">${item.reference}</span></td>
												<td align="center">${item.exercise}</td>
												<td align="center">${item.dateFacture}</td>
												<!--td align="center">${item.sentDate}</td>
												<td align="center">${item.dateOfPayment}</td-->
												<td align="center">${item.status}</td>
												<!--td align="center">${item.libelle}</td-->
												<td align="center">
												<c:choose>
									    		<c:when test='${item.type eq "AD" }'>
												 Acompte
									      		</c:when>
												<c:when test='${item.type eq "CN" }'>
												 Note de credit
									      		</c:when>
									      		<c:otherwise>
												Facture finale
									      		</c:otherwise>
									     	</c:choose>
												</td>
												<td align="center">
												<fmt:formatNumber type="currency" currencySymbol="&euro;">
														${item.amountNetEuro}
										</fmt:formatNumber>
												</td>
											</tr>
										
										<c:set var="totalFacture" value="${totalFacture + item.amountNetEuro}"/>
									  </c:forEach>

									  <thead>
											<tr>
												<th scope="col" colspan="5" style="border-right: 1px solid #ccc;">Total des factures</th>
												<th align="center" scope="col" style="background-color :  #ffffcc;"><span style="color:red;">
												<fmt:formatNumber type="currency" currencySymbol="&euro;">
														${totalFacture}
												</fmt:formatNumber>
												</span></th>
												
											</tr>
										</thead>

										
											
											
										</tbody>
									</table>
									<br/>
									<table id="hor-minimalist-b" summary="Listes des dépenses supplémentaires">
										<caption><span style="color:blue;">Dépenses supplémentaires </span></caption>
										<thead>
											<tr>
												<th scope="col">Employé</th>
												<th scope="col">Date</th>
												<th scope="col">Reason</th>												
												
												<th scope="col" align="right">Amount</th>
																					
											</tr>
										</thead>
										<tbody>

										<c:set var="totalCost" value="0"/>
										<c:forEach var="item" items="${costs}" >
											<tr>
												<td><span style="color:orange;">${item.owner.lastName}</span></td>
												<td>
												<%--fmt:formatDate value="${item.createDate}" pattern="dd/MM/yyyy"/--%>
												${item.createDate}
												</td>
												<td>
												${item.costCode}
												
												</td>
												
												
												
												<td align="right" >
												<fmt:formatNumber type="currency" currencySymbol="&euro;">
														${item.amount}
												</fmt:formatNumber>
												</td>
												
											</tr>
										<c:set var="totalCost" value="${totalCost + item.amount}"/>
									  </c:forEach>
											
											<tr>
												<th align="left" >Total des dépenses</th>
												<th style="border-right: 1px solid #ccc;" align="right" colspan="2"></th>
												<th align="right" style="background-color :  #ffffcc;"><span style="color:red;">
												<fmt:formatNumber type="currency" currencySymbol="&euro;">
														${totalCost}
												</fmt:formatNumber>
												</span></th>
					
											</tr>
											
											
										</tbody>
									</table>
									<br/>
									<table id="hor-minimalist-b" summary="Employee Pay Sheet" width="100%">
										<thead>
											<tr>
												<th align="center" scope="col">Employé</th>
												<th align="center" scope="col">Profile</th>
												<th align="center" scope="col">Date</th>
												<th align="center" scope="col">Prix revient</th>
												<th align="center" scope="col">Prix vente</th>
												<th align="center" scope="col">Heures</th>
												<th align="center" scope="col">Validée</th>
												
												<th align="center" scope="col">Total prix revient</th>
												<th align="center" scope="col">Total prix vente</th>
											</tr>
										</thead>
										<tbody>
											<c:set var="totalPrixRevient" value="0"/>
											<c:set var="totalPrixVente" value="0"/>
											<c:set var="totalHeures" value="0"/>
											<c:set var="totalHeuresValidees" value="0"/>
											<c:forEach var="item" items="${budgets}" >
												<tr>
													<td align="center"><span style="color:purple;">${item.name}</span></td>
													<td align="center">${item.profile}</td>
													<td align="center">
													
													<fmt:formatDate value="${item.dateOfEvent}" pattern="dd/MM/yyyy"/>
													
													</td>
													<td align="center">${item.rate}</td>
													<td align="center">${item.prixVente}</td>
													<td align="center">${item.hours} h </td>
													<td align="center">
												
													<c:choose>
														<c:when test='${item.valid == true }'>
														    <c:set var="totalHeuresValidees" value="${item.hours + totalHeuresValidees}"/>
														 	<img src="images/puce.gif" border="0"/>
														</c:when>
														<c:otherwise>
														
														 <img src="images/delete.gif" border="0"/>
														</c:otherwise>
													</c:choose>
													
													</td>
													
													
													<td align="center">													
													<fmt:formatNumber type="currency" currencySymbol="&euro;">
															${item.hours * item.rate}
													</fmt:formatNumber>
													</td>
													<td align="center">
													<fmt:formatNumber type="currency" currencySymbol="&euro;">
															${item.hours * item.prixVente}
													</fmt:formatNumber>
													</td>
												</tr>
												<c:set var="totalHeures" value="${item.hours + totalHeures}"/>
												
												<c:set var="totalPrixRevient" value="${(item.hours * item.rate) + totalPrixRevient}"/>
												<c:set var="totalPrixVente" value="${(item.hours * item.prixVente) + totalPrixVente}"/>
											</c:forEach>
											<tr>
												<th align="center" colspan="5"></th>
												<th align="center">${totalHeures} h</th>
												<th align="center">${totalHeuresValidees} h</th>																				
												<th align="center">
													<fmt:formatNumber type="currency" currencySymbol="&euro;">
															${totalPrixRevient}
													</fmt:formatNumber>												
												</th>
												<th align="center" >
												<fmt:formatNumber type="currency" currencySymbol="&euro;">
														${totalPrixVente}
												</fmt:formatNumber>
												</th>
											</tr>
											
										</tbody>
									</table>

									<br/>
									
  
  
						</div>
				 </dl>					 
			</fieldset>  
	</div>
</div>

<!-- END OF CONTENTS PART -->




</body>


</html>





