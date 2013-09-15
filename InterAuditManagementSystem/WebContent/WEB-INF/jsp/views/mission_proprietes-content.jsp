
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions"%>
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
		
			<li><a id="current" href="${ctx}/showMissionPropertiesPage.htm?id=${viewMissionDetails.mission.id}">Proprietes</a></li>
		<interaudit:accessRightSet right="MODIFY_MISSIONS">
			<%--li><a href="${ctx}/showMissionInterventionsPage.htm?id=${viewMissionDetails.mission.id}">Activités</a></li--%>
			<li><a href="${ctx}/showMissionBudgetPage.htm?id=${viewMissionDetails.mission.id}">Budgets</a></li>
			<li><a href="${ctx}/showMissionHeuresPresteesPage.htm?id=${viewMissionDetails.mission.id}">Heures prestees</a></li>
			<li><a href="${ctx}/showMissionDepensesPage.htm?id=${viewMissionDetails.mission.id}">Depenses</a></li>
			<li><a href="${ctx}/showMissionFacturesPage.htm?id=${viewMissionDetails.mission.id}">Factures</a></li>
			<%--li><a href="${ctx}/showMissionChartsPage.htm?id=${viewMissionDetails.mission.id}">Graphiques</a></li--%>
		</interaudit:accessRightSet>
		<i><a href="${ctx}/showMissionDocumentsPage.htm?id=${viewMissionDetails.mission.id}">Documents</a></li>
		<li><a href="${ctx}/showMissionCommunicationsPage.htm?id=${viewMissionDetails.mission.id}">Communications</a></li>

		</ul>

		
     </div>

	 
  </div>
  <!-- End Left Column -->
  <!-- Begin Content Column -->
  <%--div id="content"--%>
<div style="float: left;width:85%;">
	<div id="basic" class="myform" style="background-color:white;">
   
		
			<table id="hor-minimalist-b">
			<tr>
				<td align="left" width="40%" >Customer </td>
				<td align="center"><span style="color:#039;"> ${viewMissionDetails.missionData.customerName}</span></td>
			</tr>

			<tr>
				<td align="left" width="40%" >Reference </td>
				<td align="center"><span style="color:#039;"> ${viewMissionDetails.missionData.missionReference}</span></td>
			</tr>

			<tr>
				<td align="left" width="40%" >Exercise </td>
				<td align="center"><span style="color:#039;"> ${viewMissionDetails.missionData.year}</span></td>
			</tr>

			<tr>
				<td align="left" width="40%" >Status </td>
				<td align="center"><span style="color:green;"> ${viewMissionDetails.missionData.status}</span></td>
			</tr>

			<tr>
				<td align="left" width="40%" >Asssociate </td>
				<td align="center"><span style="color:#039;"> ${viewMissionDetails.missionData.associate}</span></td>
			</tr>

			<tr>
				<td align="left" width="40%" >Manager </td>
				<td align="center"><span style="color:#039;"> ${viewMissionDetails.missionData.manager}</span></td>
			</tr>


			<tr>
				<td align="left" width="40%" >Start date </td>
				<td align="center"><span style="color:#039;">${viewMissionDetails.missionData.startDate}</span></td>
			</tr>

			<tr>
				<td align="left" width="40%" >Due date </td>
				<td align="center"><span style="color:#039;">${viewMissionDetails.missionData.dueDate}</span></td>
			</tr>

			<tr>
				<td align="left" width="40%" >Type </td>
				<td align="center"><span style="color:#039;"> ${viewMissionDetails.missionData.type}</span></td>
			</tr>

			<%--tr>
				<td align="left" width="40%" >Budget </td>
				<td align="center"><span style="color:#039;">${viewMissionDetails.missionData.totalScheduledCost}</span></td>
			</tr>

			<tr>
				<td align="left" width="40%" >Current cost </td>
				<td align="center"><span style="color:#039;"> ${viewMissionDetails.missionData.totalCost}</span></td>
			</tr--%>
						
			</table>					
						 
		</div>
	 </div>
 


<script type="text/javascript">

	$(function() {
	$('#datepicker1').datepicker({
		showOn: 'button', buttonImage: 'images/calbtn.gif', buttonImageOnly: true,
	changeMonth: true,
		changeYear: true
	});
	});

$(function() {
	$('#datepicker2').datepicker({
		showOn: 'button', buttonImage: 'images/calbtn.gif', buttonImageOnly: true,
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
        <span id="formTitle" style="color:#039;">Schedule mission</span>
    </div>
	<div id="container">
		<form name="changeMissionDateForm" action="${ctx}/handleMissionDatesPage.htm" method="post" class="niceform">
			<input type="hidden" name="missionId" />
			<fieldset>
				<hr/>
				<legend>Period details</legend>
			  
				<dl>
					<dt><label for="startdate">Start date:</label></dt>
					<dd><input  type="text" name="startdate" id="datepicker1" value="20/12/2008" /></dd>
				 </dl>
				 <dl>
					<dt><label for="enddate">Due date:</label></dt>
					<dd><input  type="text" name="enddate" id="datepicker2" value="20/12/2008" /></dd>
				</dl>
			</fieldset>
			 <fieldset>
				<legend>Administration</legend>
				<dl>
					<dt><label for="email">Status:</label></dt>
					<dd><select name="profile"  style="width:300px;">
									 
                						<option value="PENDING" <c:if test='${viewMissionDetails.missionData.status == "PENDING"}'> selected</c:if>>Pending</option>
										<option value="ONGOING" <c:if test='${viewMissionDetails.missionData.status == "ONGOING"}'> selected</c:if>>In Progress</option>
										<option value="STOPPED" <c:if test='${viewMissionDetails.missionData.status == "STOPPED"}'> selected</c:if>>Stopped</option>
										<option value="CANCELLED" <c:if test='${viewMissionDetails.missionData.status == "CANCELLED"}'> selected</c:if>>Cancelled</option>
										<option value="CLOSED" <c:if test='${viewMissionDetails.missionData.status == "CLOSED"}'> selected</c:if>>Closed</option>
            		  				 
							</select></dd>
				</dl>
			</fieldset>
		</form>
	</div>
 </div>

</div>


<!-- End Wrapper -->
						
					


	