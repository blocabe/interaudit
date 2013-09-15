<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions"%>




<!-- START ADDED PART -->   

<style>
input.button120 {
	width:120px;
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
		color: #039;
		background: #eff2ff;
	}
	#ver-zebra td
	{
		/*padding: 4px 7px;*/
		border-right: 1px solid #0066aa;
		/*border-left: 2px solid #fff;*/
		border-bottom: 1px solid #0066aa;
		color: #669;
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

 .normal { background-color: white }
		 .highlight { background-color: orange }
		 
		 
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
			<form name="listForm" action="${ctx}/showTasksPage.htm" method="post" >
				<span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Status</strong> : </span> &nbsp;
				
				<input type="checkbox" name="PENDING" value="PENDING" <c:if test='${viewactivities.param.pending_key=="PENDING"}'> checked</c:if> /><span style="font:10px Verdana, sans-serif;margin-right:10pt; color: black;">Pending </span> &nbsp;
				<input type="checkbox" name="ONGOING" value="ONGOING" <c:if test='${viewactivities.param.ongoing_key=="ONGOING"}'> checked</c:if> /><span style="font:10px Verdana, sans-serif;margin-right:10pt;color: black;">In Progress </span> &nbsp;
				<input type="checkbox" name="STOPPED" value="STOPPED" <c:if test='${viewactivities.param.stopped_key=="STOPPED"}'> checked</c:if> /><span style="font:10px Verdana, sans-serif;margin-right:10pt;color: black;">Stopped</span> &nbsp;
				<input type="checkbox" name="CANCELLED" value="CANCELLED" <c:if test='${viewactivities.param.cancelled_key=="CANCELLED"}'> checked</c:if> /><span style="font:10px Verdana, sans-serif;margin-right:10pt;color: black;">Cancelled </span> &nbsp;
				<input type="checkbox" name="CLOSED" value="CLOSED" <c:if test='${viewactivities.param.closed_key=="CLOSED"}'> checked</c:if> /><span style="font:10px Verdana, sans-serif;margin-right:10pt;color: black;">Closed </span> 
				
				<br/> 
				<span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Exercise</strong> : </span>
				<select style="font:10px Verdana, sans-serif;margin-right:10pt;" name="task_year">
				  <c:forEach var="y" items="${viewactivities.yearOptions}">
                		<option value="${y.id}"<c:if test='${viewactivities.param.year==y.id}'> selected</c:if>>${y.name}</option>
            		  </c:forEach>
				</select> 

				<span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Month</strong> : </span>
				<select style="font:10px Verdana, sans-serif;margin-right:10pt;" name="task_month" >
						<option value="0">Any</option>
                		<option value="1">Janvier</option>
						<option value="2">Fevrier</option>
						<option value="3">Mars</option>
						<option value="4">Avril</option>
						<option value="5">Mai</option>
						<option value="6">Juin</option>
						<option value="7">Juillet</option>
						<option value="8">Aout</option>
						<option value="9">Septembre</option>
						<option value="10">Octobre</option>
						<option value="11">Novembre</option>
						<option value="12">Decembre</option>
            		 
				</select>

				<span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Customer</strong> :</span>
				<select style="font:10px Verdana, sans-serif;margin-right:10pt;" name="task_customer">
				  <option value="-1">Any</option>
				  	  <c:forEach var="y" items="${viewactivities.customerOptions}">
                		<option value="${y.id}"<c:if test='${viewactivities.param.customer==y.id}'> selected</c:if>>${y.name}</option>
            		  </c:forEach>
				</select> 
				
				<span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Employee</strong> :</span>
				<select style="font:10px Verdana, sans-serif;margin-right:10pt;" name="task_employee">
				  <option value="-1">Any</option>
				  	  <c:forEach var="y" items="${viewactivities.employeeOptions}">
                		<option value="${y.id}"<c:if test='${viewactivities.param.employee==y.id}'> selected</c:if>>${y.name}</option>
            		  </c:forEach>
				</select> 

				
				&nbsp;
				<input style="font:10px Verdana, sans-serif;" type="submit" class="button120" value="Search"/>		

				</form>
				
			</div>
			<br/>
			 <span class="pagelinks">[First/Prev] <strong>1</strong>, <a href="/InterAuditWeb/WEB-INF/jsp/views/mission_interventions.jsp?id=227&amp;d-49653-p=2" title="Go to page 2">2</a>, <a href="/InterAuditWeb/WEB-INF/jsp/views/mission_interventions.jsp?id=227&amp;d-49653-p=3" title="Go to page 3">3</a> [<a href="/InterAuditWeb/WEB-INF/jsp/views/mission_interventions.jsp?id=227&amp;d-49653-p=2">Next</a>/<a href="/InterAuditWeb/WEB-INF/jsp/views/mission_interventions.jsp?id=227&amp;d-49653-p=3">Last</a>]</span>
			<div>
			        <table id="ver-zebra" width="100%" cellspacing="0" class="formlist" >
									<caption><span style="color:orange;">List of tasks for exercise 2008 </span></caption>
									<thead>
									<tr>
										<th scope="col">Key</th>
										<th scope="col">Exercise</th>
										<th scope="col">Type</th>
										<th scope="col">Summary</th>
										<th scope="col">Customer</th>
										<th scope="col">Assignee</th>
										<th scope="col">Reporter</th>
										<th scope="col">Priotity</th>
										<th scope="col">Status</th>
										<!--th scope="col">Estimated time</th-->
										<%--th scope="col">Expected cost</th>
										<th scope="col">Actual cost</th--%>
										<th scope="col">Started</th>
										<th scope="col">Updated</th>
										<th scope="col">Due </th>
										<th scope="col">Time spent</th>
										<th scope="col">Actions</th>
									</tr>
									</thead>
									<tbody>
									
									<c:set var="row" value="0"/>
									 <c:forEach var="item" items="${viewactivities.activities}" varStatus="loop">
										 	<c:choose>
									    		<c:when test='${row % 2 eq 0 }'>
									      			<tr onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
									      		</c:when>
									      		<c:otherwise>
									      			<tr  onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
									      		</c:otherwise>
									     	</c:choose>
											<td><span>${item.activityReference}</span></td>
											<td><span style=" color: blue;">${item.year}</span></td>
											<td><span style=" color: orange;">${item.type}</span></td>
											<td><span style=" color: blue;">${item.activityDescription}</span></td>
											<td><span style=" color: red;"><i>${item.customerName}</i></span></td>
											<td><span style=" color: blue;">${item.employeeName}</span></td>
											<td><span style=" color: blue;">${item.manager}</span></td>
											<td><span style=" color: blue;">${item.priority}</span></td>
											<td><span style=" color: blue;">${item.status}</span></td>
											<%--td><span style="color:green;"><i>
										
														<fmt:formatNumber type="currency" currencySymbol="&euro;">
														${item.expectedCost}
														</fmt:formatNumber></i></span>
												</td>
												<td><span style="color:green;"><i>
										
														<fmt:formatNumber type="currency" currencySymbol="&euro;">
														${item.actualCost}
														</fmt:formatNumber></i></span>
												</td--%>
											<%--td>${item.totalEstimatedHours} &nbsp; h</td--%>
											<td><span style=" color: orange;">${item.startDate}</span></td>
											<td><span style=" color: orange;">${item.updateDate}</span></td>
											<td><span style=" color: orange;">${item.endDate}</span></td>
											<td><span style=" color: blue;">${item.totalWorkedHours} &nbsp; h</span></td>
											<th><a href="${ctx}/showTaskPropertiesPage.htm?id=${item.id}"><img src="images/Table-Edit.png" border="0" alt="Edit"/></a></th>
										</tr>
									<c:set var="row" value="${row + 1}"/>
									  </c:forEach>
									</tbody>
								</table>
								<br/>
						</div>

  <!-- END ADDED PART -->   
  