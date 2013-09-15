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
		<li><a id="current" href="${ctx}/showTaskPropertiesPage.htm?id=${activityDetails.interventionId}">Proprietes</a></li>
		<li><a href="${ctx}/showTaskDocumentsPage.htm?id=${activityDetails.interventionId}">Documents</a></li>
		<li><a href="${ctx}/showTaskCommunicationsPage.htm?id=${activityDetails.interventionId}">Communications</a></li>
		<li><a href="${ctx}/showTaskWorkDonePage.htm?id=${activityDetails.interventionId}">Logged work</a></li>
		</ul>
     </div>

	  <br/>

	 <div id="navcontainer">
       <ul id="navlist">
							<li><a href="#">Start progress...</a></li>
		</ul>
     </div>
  </div>
  <!-- End Left Column -->

  <!-- Begin Content Column -->
  <div id="content">
<div id="basic" class="myform"  style="background-color: white;">
							
   <form id="form1" name="form1" method="post" action="">
			<table id="hor-minimalist-b">
			<tr>
				<td align="left" width="40%" >Reference </td>
				<td align="center"><span style="color:orange;"> ${activityDetails.interventionData.activityReference}</span></td>
			</tr>

			<tr>
				<td align="left" width="40%" >Mission code </td>
				<td align="center"><span style="color:orange;"> ${activityDetails.interventionData.missionCode}</span></td>
			</tr>

			<tr>
				<td align="left" width="40%" >Customer </td>
				<td align="center"><span style="color:orange;"> ${activityDetails.interventionData.customerName}</span></td>
			</tr>

			<tr>
				<td align="left" width="40%" >Exercise </td>
				<td align="center"><span style="color:orange;">${activityDetails.interventionData.year}</span></td>
			</tr>

			<tr>
				<td align="left" width="40%" >Manager </td>
				<td align="center"><span style="color:orange;">${activityDetails.interventionData.manager}</span></td>
			</tr>

			<tr>
				<td align="left" width="40%" >Assignee </td>
				<td align="center"><span style="color:orange;">${activityDetails.interventionData.employeeName}</span></td>
			</tr>

			<tr>
				<td align="left" width="40%" >Comments </td>
				<td align="center"><span style="color:orange;">${activityDetails.interventionData.activityDescription}</span></td>
			</tr>

			<tr>
				<td align="left" width="40%" >From date </td>
				<td align="center"><span style="color:orange;">${activityDetails.interventionData.startDate}</span></td>
			</tr>

			<tr>
				<td align="left" width="40%" >Due date </td>
				<td align="center"><span style="color:orange;">${activityDetails.interventionData.endDate}</span></td>
			</tr>

			<tr>
				<td align="left" width="40%" >Total estimated time </td>
				<td align="center"><span style="color:orange;">${activityDetails.interventionData.totalEstimatedHours}</span></td>
			</tr>

			<tr>
				<td align="left" width="40%" >Total worked time </td>
				<td align="center"><span style="color:orange;">${activityDetails.interventionData.totalWorkedHours}</span></td>
			</tr>

			<tr>
				<td align="left" width="40%" >Type </td>
				<td align="center"><span style="color:orange;">${activityDetails.interventionData.type}</span></td>
			</tr>
			<tr>
				<td align="left" width="40%" >Status </td>
				<td align="center"><span style="color:green;">${activityDetails.interventionData.status}</span></td>
			</tr>
						
			</table>					
		</form> 
							  
							  
							

		</div>
  
  </div>
  <!-- End Content Column -->
  <!-- Begin Right Column ->
  <div id="rightcolumn"></div>
  <!-- End Right Column -->
  <!-- Begin Footer ->
  <div id="footer"></div>
  <!-- End Footer -->
 </div>
<!-- End Wrapper -->
						
					


	
  
 