<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript" src="${ctx}/script/tabs.js"></script>
<script type="text/javascript" src="${ctx}/script/scriptaculous/prototype.js"></script>
<script type="text/javascript" src="${ctx}/script/scriptaculous/scriptaculous.js"></script>

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
		padding: 5px 9px;
		border-right: 0px solid #0066aa;
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
	color: black;
	padding: 5px 4px;
	border-bottom: 1px solid #6678b1;
	border-right: 1px solid #6678b1;
	border-top: 1px solid #6678b1;
	/*background: #eff2ff;*/
}
#hor-minimalist-b td
{
	border-bottom: 1px solid #ccc;
	border-right: 0px solid #ccc;
	/*color: #669;*/
	padding: 6px 6px;
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

				<span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Exercise</strong> : </span>
				<select style="font:10px Verdana, sans-serif;margin-right:10pt;" name="activities_year" >
					<option value="0">Any</option>
				    <c:forEach var="y" items="${viewactivities.yearOptions}">
                		<option value="${y.id}"<c:if test='${viewactivities.param.year==y.id}'> selected</c:if>>${y.name}</option>
            		  </c:forEach>
				</select> 
				

				<span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Employee</strong> :</span>
				<select style="font:10px Verdana, sans-serif;margin-right:10pt;" name="activities_employee">
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
				<div>
								<table id="ver-zebra" summary="Employee Pay Sheet" width="100%">
										<thead>
											<tr>
												<th scope="col">Manager</th>												
												<th scope="col">Job In Process</th>
												<th scope="col">Year End</th>
												<th scope="col">Staff</th>
												<th scope="col">Week</th>
												<th scope="col">Status &nbsp;<img src="images/_ast.gif" border="0" alt="Manage"/></th>
												<th scope="col">To Do &nbsp;<img src="images/_ast.gif" border="0" alt="Manage"/></th>
												<th scope="col">Comments &nbsp;<img src="images/_ast.gif" border="0" alt="Manage"/></th>												
											</tr>
										</thead>

										
										
										<tbody>	
											<c:set var="row" value="0"/>
											<c:forEach var="item" items="${viewactivities.activities}" >
												
												<c:choose>
													<c:when test='${row % 2 eq 0 }'>
														<tr style="background: #eff2ff;" onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
													</c:when>
													<c:otherwise>
														<tr onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
													</c:otherwise>
									     		</c:choose>
											 		<td><span style=" color: orange;">${item.manager}</span></td>
											 		<td align="left"><span style=" color: purple;"><i>&nbsp;&nbsp;&nbsp;${item.customerName}</i></span></td>
											 		<td>${item.year}</td>
													<td align="left">${item.employeeName}</td>
											 		<td>${item.startDate}</td>
													<td align="center"  style="background-color :  #ffffcc; cursor:hand;" >
														<span id="${item.id}.status">${item.status}</span>
														<script>
																new Ajax.InPlaceCollectionEditor(
																  "${item.id}.status", "${ctx}/changeFieldActivityStatus.htm?id=${item.id}", 
																  { collection: ['FIELD WORK TO FINALISE','CLIENT APPROVAL/REP LETTER','FINISHED AND SIGNED','STOPPED','CANCELLED']});								
														</script>
													</td>
													<td align="center"  style=" cursor:hand;" >													
														<span id="${item.id}.todo">${item.activityDescription}</span>
														<script>
																new Ajax.InPlaceCollectionEditor(
																  "${item.id}.todo", "${ctx}/changeFieldActivityTodo.htm?id=${item.id}", 
																  { collection: ['TO_REVIEW','REVIEWED','SENT']});								
														</script>
													</td>														
													<td  style="cursor:hand;" >
													<span id="${item.id}.comments">&nbsp;${item.comments}</span>
													<script>
													new Ajax.InPlaceEditor("${item.id}.comments", 
																			"${ctx}/changeFieldActivityComments.htm?id=${item.id}",
																			{rows:3,cols:40});
													</script>

													
													
													</td>											
												</tr>
												<c:set var="row" value="${row + 1}"/>
									  		</c:forEach>
										</tbody>
									</table>
						</div>
						<br/>

  <!-- END ADDED PART -->   