<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/tags/interaudit" prefix="interaudit" %>
<jsp:include page="common_css.jsp"/>
<!-- Begin Wrapper -->
<div id="wrapper">

  <!-- Begin Header -->
  <jsp:include page="missionDetailsHeader.jsp"/>
  <!-- End Header -->

  <!-- Begin Left Column -->
  <div id="leftcolumn"> 
	<div id="navcontainer">
       <ul id="navlist">
                                <!-- CSS Tabs -->
		
			

		<interaudit:accessRightSet right="CONSULT_COMMUNICATIONS_FROM_MISSION">
		<li><a href="${ctx}/showMissionCommunicationsPage.htm?id=${viewMissionDetails.mission.id}">
		Communications [<span style="color:black;"><strong>${ fn:length(viewMissionDetails.mission.sortedMessages) }</strong></span>]</a></li>
		</interaudit:accessRightSet>
		<interaudit:accessRightSet right="CONSULT_DOCUMENTS_FROM_MISSION">
		<li><a  href="${ctx}/showMissionDocumentsPage.htm?id=${viewMissionDetails.mission.id}">
		Documents [<span style="color:black;"><strong>${ fn:length(viewMissionDetails.mission.documents)}</strong></span>]</a></li>	
		</interaudit:accessRightSet>
		<interaudit:accessRightSet right="CONSULT_HEURES_PRESTEES_FROM_MISSION">
			<li><a href="${ctx}/showMissionBudgetPage.htm?id=${viewMissionDetails.mission.id}">
			Heures Prest&eacute;es [<span style="color:black;"><strong>${ fn:length(viewMissionDetails.budgets) + fn:length(viewMissionDetails.budgetNotValidated) }</strong></span>]</a></li>
		</interaudit:accessRightSet>	
		<interaudit:accessRightSet right="CONSULT_EXPENSES_FROM_MISSION">
			<li><a id="current" href="${ctx}/showMissionDepensesPage.htm?id=${viewMissionDetails.mission.id}">
			Note de frais[<span style="color:black;"><strong>${ fn:length(viewMissionDetails.mission.allExtraCosts) }</strong></span>]</a></li>
		</interaudit:accessRightSet>
		<interaudit:accessRightSet right="CONSULT_INVOICES_FROM_MISSION">
			<li><a href="${ctx}/showMissionFacturesPage.htm?id=${viewMissionDetails.mission.id}">
			Factures [<span style="color:black;"><strong>${ fn:length(viewMissionDetails.mission.sortedInvoices) }</strong></span>]</a></li>
		</interaudit:accessRightSet>
		<interaudit:accessRightSet right="CONSULT_RENTABILITY_FROM_MISSION">
			<li><a href="${ctx}/showMissionChartsPage.htm?id=${viewMissionDetails.mission.id}">Rentabilt&eacute;</a></li>
		</interaudit:accessRightSet>
		<interaudit:accessRightSet right="CONSULT_ALERTES_FROM_MISSION">
			<li><a  href="${ctx}/showMissionAlertesPage.htm?id=${viewMissionDetails.mission.id}">
			Alertes[<span style="color:black;"><strong>${ fn:length(viewMissionDetails.mission.alertes) }</strong></span>]</a></li>
		</interaudit:accessRightSet>
		
		

		</ul>
     </div>

	<c:if test="${viewMissionDetails.mission.status eq 'ONGOING'}"></c:if>
		<interaudit:accessRightSet right="ADD_EXPENSES_FROM_MISSION">
		 <br/>
		 <div id="navcontainer">
		   <ul id="navlist">
				<li><a  id="show" onclick="resetForm()" href="#">Add Depense</a></li>
			</ul>
		 </div>
		</interaudit:accessRightSet>
		
  </div>
  <!-- End Left Column -->

  <!-- Begin Content Column -->
  <%--div id="content"--%>
<div style="float: left;width:80%;">

  <table id="hor-minimalist-b" summary="Employee Pay Sheet">
										<%--caption><span style="color:blue;">Listes des d�penses enregistr�es </span></caption--%>
										<thead>
											<tr>
												<th scope="col">Employ&eacute;</th>
												<th scope="col">date</th>
												<th scope="col">raison</th>												
												
												<th scope="col">description</th>
												
												<th scope="col">montant</th>
												<th scope="col">actions</th>
																					
											</tr>
										</thead>
										<tbody>

										<c:set var="totalCost" value="0"/>

										<c:choose>
										<c:when test='${ fn:length(viewMissionDetails.mission.allExtraCosts) == 0}'>
											<tr><td align="center" colspan="4"><span  style="color:red;font-weight: bold;">Pas de d&eacute;penses... </span></td></tr>													
										</c:when>
										
										<c:otherwise>
											<c:forEach var="item" items="${viewMissionDetails.mission.allExtraCosts}" >
											<tr>
												<td style="border-bottom: 1px dotted #ccc;background-color:#ffffcc;"><span style="color:purple;">${item.owner.lastName}</span></td>
												<td style="border-bottom: 1px dotted #ccc;background-color:#ffffcc;">
												<fmt:formatDate value="${item.createDate}" pattern="dd/MM/yyyy"/>
												</td>
												<td style="border-bottom: 1px dotted #ccc;background-color:#ffffcc;">${item.costCode}</td>
												
												<%--td>${item.currencyCode}</td--%>
												<td>${item.description}</td>
												
												<td style="border-bottom: 1px dotted #ccc;background-color:#ffffcc;">
												<fmt:formatNumber type="currency" currencySymbol="&euro;">
														${item.amount}
												</fmt:formatNumber>
												</td>
												<td>
												<a href="#">
												<img src="images/Table-Edit.png" border="0" alt="Edit cost" onclick="editCost(${item.id})"/></a>
												
												<a href="#">
													<img src="images/Table-Delete.png" border="0" alt="Delete Cost" onclick="deleteCost(${item.id},${viewMissionDetails.mission.id})"/></a>
												</td>
												
											</tr>
										<c:set var="totalCost" value="${totalCost + item.amount}"/>
									  </c:forEach>
										</c:otherwise>
										</c:choose>
										
											
											<tr>
												<th align="left" >Total des d&eacute;penses</th>
												<th style="border-right: 1px solid #ccc;" align="right" colspan="3"></th>
												<th align="left" style="background-color:#FF6262;"><span>
												<fmt:formatNumber type="currency" currencySymbol="&euro;">
														${totalCost}
												</fmt:formatNumber>
												</span></th>
					
											</tr>
											
											
										</tbody>
									</table>

	
								
  
  
  
  </div>

<script type="text/javascript">
	$(function() {
	$('#datepicker').datepicker({
		showOn: 'button', buttonImage: 'images/calbtn.gif', buttonImageOnly: false,
	changeMonth: true,
		changeYear: true
	});
	});

</script>
   <style>
#ver-zebra2
{
	font-family: "Lucida Sans Unicode", "Lucida Grande", Sans-Serif;
	font-size: 10px;
	text-align: center;
}
#ver-zebra2 th
{
	font-size: 12px;
	font-weight: normal;
	
	color: #039;
	background: #eff2ff;
}
#ver-zebra2 td
{
	color: #669;
}
</style>

<div id="addDialog">
	<div class="hd">
        <span id="formTitle" style="color:#039;">Add expense</span>
    </div>
	<div id="container">
<form name="addCostForm" action="${ctx}/handleMissionCostPage.htm" method="post" class="niceform">
<input type="hidden" name="costId" />
<input type="hidden" name="id" value="${viewMissionDetails.missionId}"/>
<fieldset>

		 <dl>
        	<dt><label for="reason">Reason:</label></dt>
            <dd><input type="text" name="reason"  value="Hotel expense" id="reason" size="32" maxlength="32" /></dd>
		 </dl>
 </fieldset>

    
    <fieldset>
		 <dl>
			<dt><label for="password">Amount:</label></dt>
            <dd><input type="text" name="amount"  value="5 000" id="amount" size="32" maxlength="32" /></dd>
		</dl>
 </fieldset>

    
    <%--fieldset>
		<dl>
			<dt><label for="currency">Currency:</label></dt>
            <dd><select style="width:200px;" size="1" name="currency" id="currency">
                    <option value="EUR">Euro</option>
            	</select>
			</dd>
		</dl>
 </fieldset--%>

    
    <fieldset>
		<dl>
        	<dt><label for="description">Description:</label></dt>
            <dd><textarea name="description" id="description" rows="3" cols="60"></textarea></dd>
		 </dl>
 </fieldset>

    
    <%--fieldset>
		 
		 <dl>
			<dt><label for="createdate">Create date:</label></dt>
            <dd><input type="input" name="createdate" id="datepicker" value="20/01/2009" size="32" maxlength="32" /></dd>
		</dl>
    </fieldset--%>

    
    <fieldset>
    	
		<dl>
			<dt><label for="owner">Owner:</label></dt>
            <dd><select style="width:200px;" size="1" name="owner" id="owner">
					<c:forEach var="y" items="${viewMissionDetails.employeeOptions}">
						<option value="${y.id}">${y.name}</option>
					</c:forEach>
            	</select></dd>
		</dl>
	 </fieldset>

    
    <fieldset>

        <dl>
        	<dt><label for="email">Reimbursed:</label></dt>
            <dd><input type="checkbox" name="interests[]" id="interestsNews" value="News" checked/><label for="interestsNews" class="opt"></label></dd>
        </dl>
    </fieldset>
   
</form>

</div>
	
</div>
  
  <!-- End Footer -->
 </div>
<!-- End Wrapper -->
						
					


	