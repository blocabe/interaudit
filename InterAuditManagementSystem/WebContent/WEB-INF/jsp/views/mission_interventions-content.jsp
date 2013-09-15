<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="/tags/interaudit" prefix="interaudit" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<link rel="stylesheet" type="text/css" href="${ctx}/script/build/fonts/fonts-min.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/script/build/calendar/assets/skins/sam/calendar.css" />
<script type="text/javascript" src="${ctx}/script/build/yahoo-dom-event/yahoo-dom-event.js"></script>
<script type="text/javascript" src="${ctx}/script/build/calendar/calendar-min.js"></script>
<!--begin custom header content for this example-->
<style type="text/css">
    #dates {
        float:left;
        border: 1px solid #000;
        background-color: #ccc;
        padding:10px;
        margin:10px;
    }

    #dates p {
        clear:both;
    }

    #dates label {
        float:left;
        display:block;
        width:7em;
        font-weight:bold;
    }
</style>

<jsp:include page="common_css.jsp"/>
<fmt:setLocale value="fr" scope="session"/>




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
		
			<li><a href="${ctx}/showMissionPropertiesPage.htm?id=${viewMissionDetails.mission.id}">Proprietes</a></li>
		<interaudit:accessRightSet right="MODIFY_MISSIONS">
			<li><a id="current" href="${ctx}/showMissionInterventionsPage.htm?id=${viewMissionDetails.mission.id}">Activités</a></li>
			<li><a href="${ctx}/showMissionBudgetPage.htm?id=${viewMissionDetails.mission.id}">Budgets</a></li>
			<li><a href="${ctx}/showMissionHeuresPresteesPage.htm?id=${viewMissionDetails.mission.id}">Heures prestees</a></li>
			<li><a href="${ctx}/showMissionDepensesPage.htm?id=${viewMissionDetails.mission.id}">Depenses</a></li>
			<li><a href="${ctx}/showMissionFacturesPage.htm?id=${viewMissionDetails.mission.id}">Factures</a></li>
			<li><a href="${ctx}/showMissionChartsPage.htm?id=${viewMissionDetails.mission.id}">Graphiques</a></li>
		</interaudit:accessRightSet>
		<li><a href="${ctx}/showMissionDocumentsPage.htm?id=${viewMissionDetails.mission.id}">Documents</a></li>
		<li><a href="${ctx}/showMissionCommunicationsPage.htm?id=${viewMissionDetails.mission.id}">Communications</a></li>

		</ul>
     </div>

	 <%--br/>

	 <div id="navcontainer">
       <ul id="navlist">
							<li><a  id="show" onclick="resetForm()" href="#">Add Intervention</a></li>
							
		</ul>
     </div--%>
  </div>
  <!-- End Left Column -->

 

  <!-- Begin Content Column -->
  <div id="content">

  <table id="hor-minimalist-b" summary="Employee Pay Sheet">
										<thead>
											<tr>
												
												<th align="left" scope="col">Task</th>
												<th align="left" scope="col">code</th>
												<th align="left" scope="col">Status</th>
												<th align="left" scope="col">To do</th>
												<th align="left" scope="col">Comments</th>
												<th align="left" scope="col">Start date</th>
												<th align="left" scope="col">End date</th>
												<th scope="col" align="center">Actions</th>
		
											</tr>
										</thead>
										
									
										<tbody>
											
										<c:forEach var="item" items="${viewMissionDetails.mission.interventions}" >
											<tr>
										 		
												<td width="15%" align="left">${item.task.name}</td>
												<td width="20%" align="left"><span style="color:orange;">${item.task.codePrestation} </span></td>
												<td width="15%" align="left">${item.status}</td>
												<td width="10%" align="left">${item.toDo}</td>
												<td align="left">${item.comments}</td>
												<td width="10%" align="left"><fmt:formatDate value="${item.startDate}" pattern="dd/MM/yyyy"/></td>
												<td width="10%" align="letf"><fmt:formatDate value="${item.endDate}" pattern="dd/MM/yyyy"/></td>
												<td align="center">
												
												<a href="#">
												<img src="images/Table-Edit.png" border="0" alt="Edit task" onclick="editTask(${item.id})"/></a>
												
												
												</td>
											</tr>										
									  </c:forEach>											
										</tbody>
									</table>
  </div>


  <!--script type="text/javascript">
	$(function() {
	$('#datepicker').datepicker({
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
</script-->


 

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
			<span id="formTitle" style="color:#039;">Add intervention</span>
		</div>

		<div class="bd">
		<form name="addInterventionForm" action="${ctx}/handleMissionInterventionPage.htm" method="post" >
		<input type="hidden" name="taskId" />
		<input type="hidden" name="id" value="${viewMissionDetails.missionId}"/>
		
		<fieldset>
		<hr/>
    	<legend>Status</legend>

		
		
		 <dl>        		
				<select name="status" style="width:350px;">
					<option value="FIELD WORK TO FINALISE">FIELD WORK TO FINALISE</option>
					<option value="CLIENT APPROVAL/REP LETTER">CLIENT APPROVAL/REP LETTER</option>
					<option value="FINISHED AND SIGNED">FINISHED AND SIGNED</option>
					<option value="STOPPED">STOPPED</option>
					<option value="CANCELLED">CANCELLED</option>
					<%--c:forEach var="y" items="${viewMissionDetails.taskOptions}">
						<option value="${y.id}">${y.name}</option>
					</c:forEach--%>
				</select>
		</dl>
    </fieldset>

	<fieldset>		
    	<legend>To do</legend> 
		<dl>				
				<select name="todo"  style="width:350px;">
						<option value="TO_REVIEW">TO_REVIEW</option>
						<option value="REVIEWED">REVIEWED</option>
						<option value="SENT">SENT</option>
						<%--c:forEach var="y" items="${viewMissionDetails.profilOptions}">
							<option value="${y.id}">${y.name}</option>
						</c:forEach--%>
				</select>
		</dl>	
    </fieldset>

    
    <fieldset>
    	<legend>Comments</legend>
		<dl>
				<div>
				 <textarea name="comments" id="comments" rows="6" cols="42"></textarea>
				</div>
        </dl>
	
    </fieldset>
   
</form>
	

</div>

</div>

 </div>


 
<!-- End Wrapper -->
