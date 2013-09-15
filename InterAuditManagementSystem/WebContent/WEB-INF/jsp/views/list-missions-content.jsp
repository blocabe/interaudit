<jsp:directive.page contentType="text/html;charset=UTF-8" />
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="t"      uri="/tags/tooltips-tiles" %>
<%@ taglib uri="/tags/interaudit" prefix="interaudit" %>

<script type="text/javascript" src="${ctx}/script/tabs.js"></script>
<script type="text/javascript" src="${ctx}/script/scriptaculous/prototype.js"></script>
<script type="text/javascript" src="${ctx}/script/scriptaculous/scriptaculous.js"></script>
<script type="text/javascript" src="${ctx}/script/jquery-ui-1.7.2.custom/development-bundle/jquery-1.3.2.js"></script>
<link href="${ctx}/css/messageHandler.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/script/messageHandler.js"></script>



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
		/*color: #039;
		background: #eff2ff;*/
	}
	#ver-zebra td
	{
		padding: 4px 7px;
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
	border-right: 0px solid #ccc;
	color: #669;
	padding: 6px 8px;
}
#hor-minimalist-b tbody tr:hover td
{
	color: #009;
}

 a {text-decoration:none;color: blue;}

 .normal { background-color: white }
 .highlight { border: 4px solid red;font-family:Arial,Helvetica,sans-serif;font-size: 200%;font-weight:bold;color: blue;}
    		 
		 
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

table.formlist tr.even{
	background: #dddddd;
	border:0px
}
table.formlist tr.even:hover{
	background-color: rgb(226,176,84);
	border:0px
}
table.formlist tr.odd{	
	background-color: #fbfbfb;
	border:0px
}
table.formlist tr.odd:hover{
	background-color: rgb(226,176,84);
	border:0px
}
table.formlist tr.selected{	
	background-color: #AAF;
	border:0px
}
table.formlist tr.selected:hover{
	background-color: rgb(226,176,84);
	border:0px
}

table.formlist th {
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

table.axial td .table.formlist 
{
	width:98%;
	/*clear:left;*/
	background-color: #fbfbfb;
	border-style : solid;
	border-width : 4px;  
	border-color: #cccccc;
}

table.axial td .table.formlist tr.even{
	background: #dddddd;
	border:0px
}

table.axial td .table.formlist tr.odd{	
	background-color: #fbfbfb;
	border:0px
}

table.axial td .table.formlist td.even{
	background-color: #dddddd;
	border:0px
}

table.axial td .table.formlist td.odd{	
	background-color: #fbfbfb;
	border:0px
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

<script type="text/javascript"> 
$(document).ready(function()  {
		<c:if test="${not empty actionErrors}">
						<c:forEach var="actionError"
							items="${actionErrors}">					
							showMessage("${actionError}","error");
						</c:forEach>
						<c:set var="actionErrors" value="" scope="session" />
					</c:if>
		<c:if test="${not empty successMessage}">
			alert(${successMessage});
			<c:forEach var="message"
				items="${successMessage}">
				showMessage("${message}","ok");
			</c:forEach>
			<c:set var="successMessage" value="" scope="session" />
		</c:if>
});
</script>



    
 		<div class="nav_alphabet" style="background-color: rgb(248, 246, 233); border: 1px solid  #0066aa; text-align:center; padding-top:1pt; font: bold 12px Verdana, sans-serif;">
		<c:if test="${not empty successMessage}">
				<div style="width: 100%; align: center">
				<div class="success"><c:forEach var="message"
					items="${successMessage}">
					<li><span style="color: blue;"><c:out value="${message}" escapeXml="false" /></span></li>
				</c:forEach></div>
				</div>
				<c:set var="successMessage" value="" scope="session" />
		</c:if>

			<form name="listForm" action="${ctx}/showMissionsPage.htm" method="post" >
				
			
				
				
				<%--span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Mandat</strong> : </span>
				<select style="font:10px Verdana, sans-serif;margin-right:10pt;" name="mission_year" onchange="document.listForm.submit();">
				    <c:forEach var="y" items="${viewmissions.yearOptions}">
                		<option value="${y.id}"<c:if test='${viewmissions.param.year==y.id}'> selected</c:if>>${y.name}</option>
            		  </c:forEach>
				</select--%> 

				<span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Employee</strong> : </span>
				<select style="font:10px Verdana, sans-serif;margin-right:10pt;" name="mission_employee" onchange="document.listForm.submit();" >
					<option value="-1">Any</option>
				    <c:forEach var="y" items="${viewmissions.employeOptions}">
                		<option value="${y.id}"<c:if test='${viewmissions.param.employee==y.id}'> selected</c:if>>${y.name}</option>
            		  </c:forEach>
				</select> 
				
				
				
				<c:if test='${viewmissions.missionOptions != null}'>
                      <span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Customer</strong> : </span>
                      <c:set var="currentCustCode" value="-1"/>
                      <select style="font:10px Verdana, sans-serif;margin-right:10pt;" name="missionId" id="missionId" onchange="document.listForm.submit();">   
                        <option value="-1" selected>Any...</option>                                
                        <c:forEach var="item" items="${viewmissions.missionOptions}" varStatus="loop">
                          <c:if test='${item.id != currentCustCode }'>
                              <option value="${item.id}" <c:if test='${viewmissions.param.missionId==item.id}'> selected</c:if>>${item.name}</option>
                              <c:set var="currentCustCode" value="${item.id}"/>                                       
                          </c:if>
                        </c:forEach>
                      </select>                               
                 </c:if>


				<%--span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Month</strong> : </span>
				<select style="font:10px Verdana, sans-serif;margin-right:10pt;" name="mission_month" >
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
            		 
				</select--%>
				

				<%--span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Customer</strong> :</span>
				<select style="font:10px Verdana, sans-serif;margin-right:10pt;" name="mission_customer">
					<option value="-1">Any</option>
				  	  <c:forEach var="y" items="${viewmissions.customerOptions}">
                		<option value="${y.id}"<c:if test='${viewmissions.param.customer==y.id}'> selected</c:if>>${fn:substring(fn:toUpperCase(y.name), 0, 19)}</option>
            		  </c:forEach>
				</select> 
				
				<span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Manager</strong> :</span>
				<select style="font:10px Verdana, sans-serif;margin-right:10pt;" name="mission_manager">
						<option value="-1">Any</option>
				  	  <c:forEach var="y" items="${viewmissions.managerOptions}">
                		<option value="${y.id}"<c:if test='${viewmissions.param.manager==y.id}'> selected</c:if>>${y.name}</option>
            		  </c:forEach>
				</select--%> 
				
				&nbsp;
				<input type="checkbox" name="sortedByName" value="sortedByName" <c:if test='${viewmissions.sortedByName=="true"}'> checked</c:if> /><span style="font:10px Verdana, sans-serif;margin-right:10pt; color: blue;">Sorted by Name </span> &nbsp;
				<input style="font:10px Verdana, sans-serif;" type="submit" class="button120" value="Search"/>	
			
				<br/>
				<span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Job Progress</strong> : </span> &nbsp;
				<input type="checkbox" name="JOB NOT STARTED" value="JOB NOT STARTED" <c:if test='${viewmissions.param.not_started_key=="JOB NOT STARTED"}'> checked</c:if> /><span style="font:10px Verdana, sans-serif;margin-right:10pt; color: blue;">JOB NOT STARTED </span> &nbsp;
				<input type="checkbox" name="FIELD WORK TO FINALISE" value="FIELD WORK TO FINALISE" <c:if test='${viewmissions.param.pending_key=="FIELD WORK TO FINALISE"}'> checked</c:if> /><span style="font:10px Verdana, sans-serif;margin-right:10pt;color: blue;">FIELD WORK TO FINALISE </span> &nbsp;
				<input type="checkbox" name="CLIENT APPROVAL/REP LETTER" value="CLIENT APPROVAL/REP LETTER" <c:if test='${viewmissions.param.ongoing_key=="CLIENT APPROVAL/REP LETTER"}'> checked</c:if> /><span style="font:10px Verdana, sans-serif;margin-right:10pt;color: blue;">CLIENT APPROVAL/REP LETTER</span> &nbsp;
				<input type="checkbox" name="STOPPED" value="STOPPED" <c:if test='${viewmissions.param.stopped_key=="STOPPED"}'> checked</c:if> /><span style="font:10px Verdana, sans-serif;margin-right:10pt;color: blue;">STOPPED </span> &nbsp;
				<input type="checkbox" name="FINISHED AND SIGNED" value="FINISHED AND SIGNED" <c:if test='${viewmissions.param.closed_key =="FINISHED AND SIGNED"}'> checked</c:if> /><span style="font:10px Verdana, sans-serif;margin-right:10pt;color: blue;">FINISHED AND SIGNED </span> 
				<input type="checkbox" name="CANCELLED" value="CANCELLED" <c:if test='${viewmissions.param.cancelled_key=="CANCELLED"}'> checked</c:if> /><span style="font:10px Verdana, sans-serif;margin-right:10pt;color: blue;">CANCELLED</span> 
				
				 

				</form>

			
			</div>
			

	


 
				<div>
									<br/>
									
			    
			        <table id="ver-zebra" width="100%" cellspacing="0" class="formlist">
									<caption><span style="color:orange;"></span></caption>
									<thead>
									<tr>
										<%--th scope="col">&nbsp;</th--%>
										<th scope="col">Admin</th>
										<th scope="col">Nom du client</th>
										<th scope="col">Ass</th>
										<th scope="col">Man</th>
										<th scope="col">Team</th>
										
										
										
										
										
										<th scope="col">Type</th>
										<th scope="col">N° Sem</th>
										<th scope="col">Ex-Audit</th>
										<!--th scope="col">Fin</th-->
										<th scope="col">Clôture</th>
										
										
										
										<th scope="col">Job progress</th>
										
										<th scope="col">To do</th>
										<th scope="col">Draft date</th>
										<th scope="col">Signed  date</th>
										<%--th scope="col">To finish</th--%>
										
										<%--th scope="col">Status</th--%>
										
										
										
										<th scope="col">Comments</th>
										<%--th scope="col">Parent</th--%>
										<%--th scope="col">Actions</th--%>
									</tr>
									</thead>
									<tbody>
									
									
									<c:set var="row" value="0"/>
									<c:set var="couleur" value="black"/>
									 <c:forEach var="item" items="${viewmissions.missions}" varStatus="loop">
									 
										<c:choose>
												<c:when test='${item.jobStatus eq "CLIENT APPROVAL/REP LETTER" }'>												
													<c:set var="couleur" value="red"/>
												</c:when>
												<c:when test='${item.jobStatus eq "FINISHED AND SIGNED" }'>												
													<c:set var="couleur" value="blue"/>
												</c:when>
												<c:otherwise>
													<c:set var="couleur" value="black"/>
												</c:otherwise>
											</c:choose>
											
											
									 	
										 	
									     	
											<c:choose>
									    		<c:when test='${row % 2 eq 0 }'>
									      			<tr class="odd" >
									      		</c:when>
									      		<c:otherwise>
									      			<tr class="odd">
									      		</c:otherwise>
									     	</c:choose>
									     	<%--th>${viewmissions.employeeName}</th--%>
									     	<th>
											<interaudit:accessRightSet right="MODIFY_MISSIONS">																									
														<c:if test='${item.budgetStatus != "CLOSED" }'>
															<c:if test='${item.manager == context.currentUser.code }'>
																<a href="${ctx}/showAdminitrateMissionPage.htm?id=${item.id}&FromPlanningWeek=true"><span style=" color: blue;"><img src="images/engrenage.gif" border="0"/></span></a>
															</c:if>
															<c:if test='${item.manager != context.currentUser.code }'>
																--
															</c:if>
														</c:if>
														<c:if test='${item.budgetStatus eq "CLOSED" }'>
															--
														</c:if>
													
											</interaudit:accessRightSet>
											<interaudit:accessRightNotSet right="MODIFY_MISSIONS">
														--
											 </interaudit:accessRightNotSet>
									     	
									     	
									     	</th>
											<td  width="10%" align="left">
											<t:tooltip>
											 <t:text><em>
														   <a href="${ctx}/showMissionPropertiesPage.htm?id=${item.id}"><span style=" color: purple;"><i>${fn:substring(item.customerName,0,20)}</i></span></a></em></t:text>
														  <t:help width="200" >
														   <font color="black"><strong>${item.customerName}</strong></font> 														   
															</t:help> 
											</t:tooltip>
											
											</td>
											<th><span style=" color: purple;">${item.associate}</span></th>
											<th><span style=" color: purple;">${item.manager}</span></th>
											<th>		
											
											<c:forTokens var="p" items="${item.memberAsString}" delims=",">
											<span style=" color: purple;">${p}</span><br/>
											</c:forTokens>
																					 												
											</th>
											
											<td align="center"><span style=" color: black;">${item.type}</span></td>
											<%--td><span style=" color: blue;">EURO</span></td--%>
											
											<td><span id="startDate_${item.id}" class="startdate_cell" style=" color:black;">

													<c:choose>
														<c:when test='${item.startWeekNumber != 0 }'>
														 	<!--fmt:formatDate value="${item.startDate}" pattern="dd-MMM"/-->
														 	${item.startWeekNumber}-${item.startYearNumber}
														</c:when>
														<c:otherwise>
														 --
														</c:otherwise>
													</c:choose>
												
												</span></td>
												
												<td><span style=" color: blue;">${item.year}</span></td>
												
												
												<!--td><span id="dueDate_${item.id}" class="duedate_cell" style=" color:black;">
													<c:choose>
														<c:when test='${item.dueDate != null }'>
														 	<fmt:formatDate value="${item.dueDate}" pattern="dd-MMM"/>
														</c:when>
														<c:otherwise>
														 --
														</c:otherwise>
													</c:choose>
												</span></td-->
												<td><span style=" color: black;">
													<c:choose>
														<c:when test='${item.dateCloture != null }'>
														 	<fmt:formatDate value="${item.dateCloture}" pattern="dd-MMM-yyyy"/>
														</c:when>
														<c:otherwise>
														 --
														</c:otherwise>
													</c:choose>
												</span></td>
												
												

												

											
											<td>											
												<span style="color: ${couleur};"><i>${item.jobStatus}</i></span>											
											</td>
											

											

											<td><span style="color: ${couleur};">
											
											<c:choose>
                                                        <c:when test='${item.toDo eq "SENT" }'>
                                                            DRAFT SENT
                                                        </c:when>

                                                        <c:otherwise>
                                                            ${item.toDo}
                                                        </c:otherwise>
                                                    </c:choose> 
											
											</span></td>
											
											<td><span style="color: ${couleur};">
												<c:choose>
														<c:when test='${item.approvalDate != null }'>
														 	<fmt:formatDate value="${item.approvalDate}" pattern="dd-MMM-yyyy"/>
														</c:when>
														<c:otherwise>
														 --
														</c:otherwise>
													</c:choose>
											
											</span></td>

											<td><span style="color: ${couleur};">
											<c:choose>
														<c:when test='${item.signedDate != null }'>
														 	<fmt:formatDate value="${item.signedDate}" pattern="dd-MMM-yyyy"/>
														</c:when>
														<c:otherwise>
														 --
														</c:otherwise>
													</c:choose>
											
											</span></td>

											<%--td><span style=" color: black;">
											${item.toFinish}
											<t:tooltip>  
														   <t:text><em>
														   <span style="color:purple;"><i>${fn:substring(item.toFinish,0,10)}</i></span></em></t:text>
														  <t:help width="100" >
														   <font color="black"><strong>${item.toFinish}</strong></font> 														   
															</t:help> 
												</t:tooltip>
											</span></td--%>
											
											<%--td style="background-color :  #ffffcc; cursor:hand;" >
												<c:choose>
														<c:when test='${item.status eq "PENDING" }'>
														 	<span id="${item.id}.status" style=" color: blue;">En Attente</span>
														</c:when>

														<c:when test='${item.status eq "ONGOING" }'>
														 	<span id="${item.id}.status" style=" color: green;">En cours</span>
														</c:when>

														<c:when test='${item.status eq "STOPPED" }'>
														 	<span id="${item.id}.status" style=" color: black;">Arrêté</span>
														</c:when>

														<c:when test='${item.status eq "CANCELLED" }'>
														 	<span id="${item.id}.status" style=" color: bleck;">Annulé</span>
														</c:when>

														<c:when test='${item.status eq "CLOSED" }'>
														 	<span id="${item.id}.status" style=" color: red;">Terminé</span>
														</c:when>

														<c:when test='${item.status eq "TRANSFERED" }'>
														 	<span id="${item.id}.status" style=" color: blue;">Transféré</span>
														</c:when>
													</c:choose>		
											</td--%>

											
											
											
											
											<td width="20%" align="left">
											<t:tooltip>  
														   <t:text><em>
														   <span style="color: ${couleur};"><i> ${fn:substring(item.jobComment,0,250)}</i></span></em></t:text>
														  <t:help width="300" >
														   <font color="black"><strong>${item.jobComment}</strong></font> 														   
															</t:help> 
												</t:tooltip>
											</td>
											<%--th>
													<c:choose>
														<c:when test='${item.parentid != null }'>
														<a href="${ctx}/showMissionPropertiesPage.htm?id=${item.parentid}"><span style=" color: purple;"><i>parent</i></span></a>
														 	
														</c:when>
														<c:otherwise>
														 --
														</c:otherwise>
													</c:choose>
											</th--%>
											
											
											<%--td><a href="${ctx}/showMissionPropertiesPage.htm?id=${item.id}"><img src="images/Table-Edit.png" border="0" alt="Edit"/></a></td--%>
										</tr>
									
									<c:set var="row" value="${row + 1}"/>
									 
									  </c:forEach>
									
									</tbody>
								</table>
						</div>

  <!-- END ADDED PART -->   