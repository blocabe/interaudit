
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="common_css.jsp"/>

<!-- Begin Wrapper -->
<div id="wrapper">

  <!-- Begin Header -->
 <jsp:include page="taskDetailHeader.jsp"/>
  <!-- End Header -->

  <!-- Begin Left Column -->
  <div id="leftcolumn"> 
	<div id="navcontainer">
       <ul id="navlist">
                                <!-- CSS Tabs -->
		<li><a href="${ctx}/showTaskPropertiesPage.htm?id=${activityDetails.interventionId}">Proprietes</a></li>
		<li><a id="current" href="${ctx}/showTaskDocumentsPage.htm?id=${activityDetails.interventionId}">Documents</a></li>
		<li><a href="${ctx}/showTaskCommunicationsPage.htm?id=${activityDetails.interventionId}">Communications</a></li>
		<li><a href="${ctx}/showTaskWorkDonePage.htm?id=${activityDetails.interventionId}">Logged work</a></li>
		</ul>
     </div>

	 <br/>

	 <div id="navcontainer">
       <ul id="navlist">
							<li><a id="show" onclick="resetForm()"  href="#">Add Document</a></li>

		</ul>
     </div>
  </div>
  <!-- End Left Column -->

  <!-- Begin Content Column -->
  <div id="content">

  <table id="hor-minimalist-b" summary="Employee Pay Sheet">
										<thead>
											<tr>
												<th scope="col">file</th>
												<th scope="col">size</th>
												<th scope="col">title</th>
												<th scope="col">description</th>
												<th scope="col">create date</th>
												<th scope="col">owner</th>
												<th scope="col">actions</th>
											</tr>
										</thead>
										<tbody>
										<c:forEach var="item" items="${activityDetails.documents}" >
											<tr>
												<td><span style="color:orange;"><a href="${ctx}/downloadTaskDocument.htm?documentId=${item.id}">${item.fileName}</a></span></td>
												<td>2MB</td>
												<td>${item.title}</td>
												<td>${item.description}</td>
												<td>${item.createDate}</td>
												<td>${item.createdUser.lastName}</td>
												<td>
												<img src="images/Table-Edit.png" border="0" alt="Edit" onclick="editDocument(${item.id})"/>
												&nbsp;
												<img src="images/Table-Delete.png" border="0" alt="Edit" onclick="deleteDocument(${item.id})"/>
												</td>
											</tr>
											</c:forEach>
											
											
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
	<form  action="${ctx}/handleTaskDocumentPage.htm" name="addDocumentForm" method="post" class="niceform" enctype="multipart/form-data">
		<input type="hidden" name="documentId" />
		<input type="hidden" name="id" value="${viewMissionDetails.missionId}"/>
		<input type="hidden" name="taskId"  value="${activityDetails.interventionData.id}"/>
		<fieldset>
			<hr/>
			<legend>Document Info</legend>
			<dl>
				<dt><label for="title">Title:</label></dt>
				<dd><input type="input" name="title" id="title" value="Contract 2008 DIMPEX SA" size="32" maxlength="32" /></dd>
			 </dl>

			<dl>
				<dt><label for="description">Description:</label></dt>
				<dd><textarea name="description" id="description" rows="4" cols="60">Details of contract contents...</textarea></dd>
			</dl>

			<dl>
				<dt><label for="upload">Upload a File:</label></dt>
				<dd><input type="file" name="fileName" id="fileName" /></dd>
			</dl>

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
    			<legend>Administration</legend>
		
				<dl>
						<dt><label for="gender">Owner:</label></dt>
						<dd>
						<select style="width:200px;" size="1" name="owner" id="owner">
							<c:forEach var="y" items="${activityDetails.teamMembersOptions}">
								<option value="${y.id}">${y.lastName}</option>
							</c:forEach>
						</select>
						</dd>
						
				 </dl>

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
 
 </div>
<!-- End Wrapper -->
						
					


	