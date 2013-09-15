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
		<li><a id="current" href="${ctx}/showMissionDocumentsPage.htm?id=${viewMissionDetails.mission.id}">
		Documents [<span style="color:black;"><strong>${ fn:length(viewMissionDetails.mission.documents)}</strong></span>]</a></li>	
		</interaudit:accessRightSet>
		<interaudit:accessRightSet right="CONSULT_HEURES_PRESTEES_FROM_MISSION">
			<li><a href="${ctx}/showMissionBudgetPage.htm?id=${viewMissionDetails.mission.id}">
			Heures Prest&eacute;es [<span style="color:black;"><strong>${ fn:length(viewMissionDetails.budgets) + fn:length(viewMissionDetails.budgetNotValidated) }</strong></span>]</a></li>
		</interaudit:accessRightSet>	
		<interaudit:accessRightSet right="CONSULT_EXPENSES_FROM_MISSION">
			<li><a  href="${ctx}/showMissionDepensesPage.htm?id=${viewMissionDetails.mission.id}">
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

	
	 <br/>

	 <div id="navcontainer">
       <ul id="navlist">
							<li><a id="show" onclick="resetForm()" href="#">Add Document</a></li>

		</ul>
     </div>
	 
  </div>
  <!-- End Left Column -->


  <!-- Begin Content Column -->
  <%--div id="content"--%>
<div style="float: left;width:80%;">

  <table id="hor-minimalist-b" summary="Employee Pay Sheet">
										<thead>
											<tr>
												<th scope="col"  align="center">fichier</th>												
												<th scope="col"  align="center">titre</th>
												<th scope="col"  align="center">description</th>
												<th scope="col"  align="center">create date</th>
												<th scope="col"  align="center">owner</th>
												<th scope="col"  align="center">actions</th>	
											</tr>
										</thead>
										<tbody>

										<c:choose>
										<c:when test='${ fn:length(viewMissionDetails.mission.documents) == 0}'>
											<tr><td colspan="6" align="center"><span  style="color:red;font-weight: bold;">Pas de documents...</span></td></tr>													
										</c:when>
										
										<c:otherwise>
											<c:forEach var="item" items="${viewMissionDetails.mission.documents}" >
												<tr>
													
													<td  align="center" style="border-bottom: 1px dotted #ccc;background-color:#ffffcc;">					
													 <a href="${ctx}/downloadMissionDocument.htm?documentId=${item.id}">${item.fileName}</a> 
													</td>
													<td>${item.title}</td>
													

													
													
													<td>${item.description}</td>

													<td><fmt:formatDate value="${item.createDate}" pattern="dd/MM/yyyy"/></td>
													
													 
													<td align="center">
													 <a href="mailto:${item.createdUser.email}?subject=${item.title}">${item.createdUser.lastName}</a>
													</td>

													<td align="center">
													<img src="images/Table-Edit.png" border="0" alt="Edit" onclick="editDocument(${item.id})"/>						<img src="images/Table-Delete.png" border="0" alt="Edit" onclick="deleteDocument(${item.id})"/>
													</td>
													
												</tr>
												</c:forEach>
										</c:otherwise>
									</c:choose>

										
											
											
										</tbody>
									</table>

									
  
  
  
  </div>

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
        <span id="formTitle" style="color:#039;">Add document</span>
    </div>
	<div id="container">
	<form  action="${ctx}/handleMissionDocumentPage.htm" name="addDocumentForm" method="post" class="niceform"  enctype="multipart/form-data">
		<input type="hidden" name="documentId" />
		<input type="hidden" name="id" value="${viewMissionDetails.missionId}"/>
		<fieldset>			
			<dl>
				<dt><label for="title">Title:</label></dt>
				<dd><input type="input" name="title" id="title" value="" size="32" maxlength="32" /></dd>
			 </dl>
		</fieldset>

		<fieldset>

			<dl>
				<dt><label for="description">Description:</label></dt>
				<dd><textarea name="description" id="description" rows="4" cols="60">Details of contract contents...</textarea></dd>
			</dl>
		</fieldset>

		<fieldset>

			<dl>
				<dt><label for="upload">Upload a File:</label></dt>
				<dd><input type="file" name="fileName" id="fileName" /></dd>
			</dl>
		</fieldset>

		<fieldset>

			<dl>
				<dt><label for="gender">Category:</label></dt>
				<dd>
					<select style="width:200px;" size="1" name="category" id="category">
						<option value="contract">Contract</option>
						<option value="invoice">Invoice</option>
						<option value="other">Other</option>
					</select>
				</dd>

			</dl>
		</fieldset>

    
		<fieldset>
    			
				<dl>
						<dt><label for="gender">Owner:</label></dt>
						<dd>
						<select style="width:200px;" size="1" name="owner" id="owner">
							<c:forEach var="y" items="${viewMissionDetails.employeeOptions}">
								<option value="${y.id}">${y.name}</option>
							</c:forEach>
						</select>
						</dd>
						
				 </dl>
		</fieldset>

		<fieldset>

				 <dl>
						<dt><label for="email">Visible all:</label></dt>
						<dd>
						<input type="checkbox" name="interests[]" id="interestsNews" value="News" checked/><label for="interestsNews"></label>
						</dd>
				 </dl>

		
		</fieldset>
    
	</form>

</div>
	
</div>
  
  <!-- End Footer -->
 </div>
<!-- End Wrapper -->
						
					


	