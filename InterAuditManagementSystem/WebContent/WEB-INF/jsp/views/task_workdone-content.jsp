<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
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
		<li><a href="${ctx}/showTaskDocumentsPage.htm?id=${activityDetails.interventionId}">Documents</a></li>
		<li><a href="${ctx}/showTaskCommunicationsPage.htm?id=${activityDetails.interventionId}">Communications</a></li>
		<li><a id="current" href="${ctx}/showTaskWorkDonePage.htm?id=${activityDetails.interventionId}">Logged work</a></li>
		</ul>
     </div>

	 <br/>

	 <div id="navcontainer">
       <ul id="navlist">
							<li><a id="show" onclick="resetForm()"  href="#">Log work</a></li>

		</ul>
     </div>
  </div>
  <!-- End Left Column -->

  <!-- Begin Content Column -->
  <div id="content">

	<table id="hor-minimalist-b" summary="Employee Pay Sheet">
			<thead>
				<tr>
					<th scope="col">Date</th>
					<th scope="col">Spent hours</th>
					<th scope="col">Comments</th>
					<th scope="col">Actions</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${activityDetails.intervention.workDoneList}" >
				<tr>
					<td><span style="color:orange;">${item.dateOfWork}</span></td>
					<td>${item.spentHours}&nbsp;h</td>
					<td>${item.comments}</td>
					
					<td>
							<img src="images/Table-Edit.png" border="0" alt="Edit" onclick="editWorkDone(${item.id})"/>
							&nbsp;
							<img src="images/Table-Delete.png" border="0" alt="Edit" onclick="deleteWorkDone(${item.id},${activityDetails.intervention.id})"/>
					</td>
				</tr>
				</c:forEach>
			</tbody>
	</table>
  </div>

  <script type="text/javascript">
	$(function() {
	$('#datepicker').datepicker({
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
        <span id="formTitle" style="color:#039;">Add work done</span>
		<span style="color:#039;"><strong>${activityDetails.interventionData.activityReference} ( ${activityDetails.interventionData.customerName} ) ${activityDetails.interventionData.year}</strong></span>
    </div>
	<div id="container">
	<form  action="${ctx}/handleTaskWorkdonePage.htm" name="addWorkDoneForm" method="post" class="niceform">
	<input type="hidden" name="workDoneId" />
	<input type="hidden" name="taskId"  value="${activityDetails.interventionData.id}"/>
	
	<fieldset>
		<hr/>
    	<legend>Logged work</legend>
        <dl>
        	<dt><label for="dateOfWork">Date:</label></dt>
            <dd><input type="input" name="dateOfWork" id="datepicker" value="Luxlait status" size="32" maxlength="32" />
			<img src="images/_ast.gif" border="0" alt="mandatory"/>
			</dd>
		 </dl>

		<dl>
			<dt><label for="spentHours">Hours:</label></dt>
            <dd>
            <select style="width:60px;" size="1" name="spentHours" id="spentHours">
									  <option value="60" selected>1h</option>
									  <option value="90">1h30</option>
									  <option value="120">2h</option>
									  <option value="150">2h30</option>
									  <option value="180">3h</option>
									  <option value="210">3h30</option>
									  <option value="240">4h</option>
									  <option value="270">4h30</option>
									  <option value="300">5h</option>
									  <option value="330">5h30</option>
									  <option value="360">6h</option>
									  <option value="390">6h30</option>
									  <option value="420">7h</option>
									  <option value="450">7h30</option>
									  <option value="480">8h</option>
									  <option value="510">8h30</option>
									  <option value="540">9h</option>
									  <option value="570">9h30</option>
									  <option value="600">10h</option>
            	</select>

				<img src="images/_ast.gif" border="0" alt="mandatory"/>
				</dd>
		</dl>

		<dl>
			<dt><label for="comments">Comments:</label></dt>
            <dd><textarea name="comments" id="comments" rows="8" cols="60">Any news about customer luxlait?</textarea></dd>
		</dl>
    </fieldset>

    
    <fieldset>
    	<legend>Administration</legend>
		
		<dl>
				<dt><label for="taskref"><strong>Task</strong>:</label></dt>
				<dd>
					
				</dd>
				
		 </dl>

    </fieldset>
    
</form>

</div>
	
</div>
  
 </div>
<!-- End Wrapper -->
						
					


	