<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="t"      uri="/tags/tooltips-tiles" %>
<%@ taglib uri="/tags/interaudit" prefix="interaudit" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!-- START ADDED PART -->  

<script type="text/javascript" src="${ctx}/script/jquery-1.6.1.js"></script>
<link href="${ctx}/css/messageHandler.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/script/messageHandler.js"></script>

<script type="text/javascript">

$(document).ready(function()  {
			<c:if test="${not empty actionErrors}">
				<c:forEach var="actionError" items="${actionErrors}">					
					showMessage("${actionError}","error");
				</c:forEach>
				<c:set var="actionErrors" value="" scope="session" />
			</c:if>
			<c:if test="${not empty successMessage}">
				<c:forEach var="message" items="${successMessage}">
					showMessage("${message}","ok");
				</c:forEach>
				<c:set var="successMessage" value="" scope="session" />
			</c:if>
});

</script>
 

<style>

.pagination2 span.current {
	
}


.pagination2 span.off,
.pagination2 span a,
.pagination2 span.current {
	border:2px solid #9AAFE5;
	padding-left:5px;
	padding-right:5px;
}

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
		/*padding: 4px 7px;*/
		border-right: 0px solid #0066aa;
		/*border-left: 2px solid #fff;*/
		border-bottom: 1px solid #0066aa;
		/*color: #669;*/
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
    
    
		
 		<div class="nav_alphabet" style="background-color: rgb(248, 246, 233); border: 1px solid  #0066aa; text-align:center; padding-top:1pt; font: bold 12px Verdana, sans-serif;">

		 <div class="nav_bottom" style=" text-align:center; ">

			
				<%--div class="nav_alphabet"  style="text-align:center;font:  10px Verdana, sans-serif;">
					<span class="galphabet_center select"><a href="${ctx}/showEmployeesPage.htm"><span style="color:orange;">All</span></a></span>			
					<span class="galphabet_center"><a href="${ctx}/showEmployeesPage.htm?startingLetterName=A"><span style="color:orange;">A</span></a></span>
					<span class="galphabet_center"><a href="${ctx}/showEmployeesPage.htm?startingLetterName=B"><span style="color:orange;">B</span></a></span>
					<span class="galphabet_center"><a href="${ctx}/showEmployeesPage.htm?startingLetterName=C"><span style="color:orange;">C</span></a></span>
					<span class="galphabet_center"><a href="${ctx}/showEmployeesPage.htm?startingLetterName=D"><span style="color:orange;">D</span></a></span>
					<span class="galphabet_center"><a href="${ctx}/showEmployeesPage.htm?startingLetterName=E"><span style="color:orange;">E</span></a></span>
					<span class="galphabet_center"><a href="${ctx}/showEmployeesPage.htm?startingLetterName=F"><span style="color:orange;">F</span></a></span>
					<span class="galphabet_center"><a href="${ctx}/showEmployeesPage.htm?startingLetterName=G"><span style="color:orange;">G</span></a></span>
					<span class="galphabet_center"><a href="${ctx}/showEmployeesPage.htm?startingLetterName=H"><span style="color:orange;">H</span></a></span>
					<span class="galphabet_center"><a href="${ctx}/showEmployeesPage.htm?startingLetterName=I"><span style="color:orange;">I</span></a></span>
					<span class="galphabet_center"><a href="${ctx}/showEmployeesPage.htm?startingLetterName=J"><span style="color:orange;">J</span></a></span>
					<span class="galphabet_center"><a href="${ctx}/showEmployeesPage.htm?startingLetterName=K"><span style="color:orange;">K</span></a></span>
					<span class="galphabet_center"><a href="${ctx}/showEmployeesPage.htm?startingLetterName=L"><span style="color:orange;">L</span></a></span>
					<span class="galphabet_center"><a href="${ctx}/showEmployeesPage.htm?startingLetterName=M"><span style="color:orange;">M</span></a></span>
					<span class="galphabet_center"><a href="${ctx}/showEmployeesPage.htm?startingLetterName=N"><span style="color:orange;">N</span></a></span>
					<span class="galphabet_center"><a href="${ctx}/showEmployeesPage.htm?startingLetterName=O"><span style="color:orange;">O</span></a></span>
					<span class="galphabet_center"><a href="${ctx}/showEmployeesPage.htm?startingLetterName=P"><span style="color:orange;">P</span></a></span>
					<span class="galphabet_center"><a href="${ctx}/showEmployeesPage.htm?startingLetterName=Q"><span style="color:orange;">Q</span></a></span>
					<span class="galphabet_center"><a href="${ctx}/showEmployeesPage.htm?startingLetterName=R"><span style="color:orange;">R</span></a></span>
					<span class="galphabet_center"><a href="${ctx}/showEmployeesPage.htm?startingLetterName=S"><span style="color:orange;">S</span></a></span>
					<span class="galphabet_center"><a href="${ctx}/showEmployeesPage.htm?startingLetterName=T"><span style="color:orange;">T</span></a></span>
					<span class="galphabet_center"><a href="${ctx}/showEmployeesPage.htm?startingLetterName=U"><span style="color:orange;">U</span></a></span>
					<span class="galphabet_center"><a href="${ctx}/showEmployeesPage.htm?startingLetterName=V"><span style="color:orange;">V</span></a></span>
					<span class="galphabet_center"><a href="${ctx}/showEmployeesPage.htm?startingLetterName=W"><span style="color:orange;">W</span></a></span>
					<span class="galphabet_center"><a href="${ctx}/showEmployeesPage.htm?startingLetterName=X"><span style="color:orange;">X</span></a></span>
					<span class="galphabet_center"><a href="${ctx}/showEmployeesPage.htm?startingLetterName=Y"><span style="color:orange;">Y</span></a></span>
					<span class="galphabet_center" ><a href="${ctx}/showEmployeesPage.htm?startingLetterName=Z"><span style="color:orange;">Z</span></a></span>
				</div--%>
			</div>			
			<table align="center"><tr>
				<td>
					<form name="listForm1" action="findEmployeeByCodePage.htm" method="post" >
					<span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Employee Code</strong> :</span>
						<input name="employeeCode" value="${viewusers.param.employeeCode}" type="text" style="font:10px Verdana, sans-serif;margin-right:10pt;" />
						&nbsp;
						<input style="font:10px Verdana, sans-serif;" type="submit" class="button120" value="Find By Id"/>
					</form>
				</td>
				<td width="2%" ></td>
				<td>
					<form name="listForm" action="showEmployeesPage.htm" method="post" >
						 <input type="hidden" name="startingLetterName" id="startingLetterName">
						<span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Role</strong> : </span>
						<select style="font:10px Verdana, sans-serif;margin-right:10pt;" name="role">
						   <option value="">Any</option>
							<c:forEach var="r" items="${viewusers.roles}">
								<option value="${r.id}"<c:if test='${viewusers.param.roleName==r.id}'> selected</c:if>>${r.name}</option>
							</c:forEach>
						</select> 
						<span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Employee</strong> :</span>
						<input  type="text" style="font:10px Verdana, sans-serif;margin-right:10pt;" name="nameLike" value="${viewusers.param.nameLike}"/>
						&nbsp;
						<input style="font:10px Verdana, sans-serif;" type="submit" class="button120" value="Search" />		
					</form>
				</td>
			</tr></table>
			
			</div>

<br/>
		
									
			    
			        <table id="ver-zebra" width="100%" cellspacing="0" class="formlist" >
									<caption><span style="color:orange;">List of employees</span></caption>
									<thead>
									<tr>
										<th scope="col">&nbsp;</th>
										<th scope="col">Code</th>
										<th scope="col">Last Name</th>
										<th scope="col">First Name</th>
										<th scope="col">Email</th>
										<!--th scope="col">Telephone</th-->
										<th scope="col">Role</th>
										<th scope="col">Active</th>
										<th scope="col">Hiring date</th>
										<th scope="col">Actions</th>
									</tr>
									</thead>
									<tbody>
									
									 <c:set var="row" value="0"/>
									 <c:forEach var="item" items="${viewusers.employees}" varStatus="loop">
									 	
										 	<c:choose>
									    		<c:when test='${row % 2 eq 0 }'>
									      			<tr style="background: #eff2ff;" onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
									      		</c:when>
									      		<c:otherwise>
									      			<tr  onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
									      		</c:otherwise>
									     	</c:choose>
		     	
											<th>
											<c:choose>
												<c:when test='${item.position.name=="PARTNER"}'>
													<img src="images/repair.gif" border="0"/>
												</c:when>
												<c:when test='${item.position.name=="MGMT_PARTNER"}'>
													<img src="images/repair.gif" border="0"/>
												</c:when>
												<c:otherwise>
													<img src="images/user3.png" border="0"/>
												</c:otherwise>
											</c:choose>
											</th>
											<td>${item.code}</td>
											<td align="left">
											
											<t:tooltip>  
														   <t:text><em>&nbsp;
														   <a href="${ctx}/editEmployeePage.htm?employeeId=${item.id}"  title="">
														   
														   <c:choose>
														<c:when test='${item.userActive == true }'>
															<i>${item.lastName}</i>
														    
														</c:when>
														<c:otherwise>
															<span style="color: red;text-decoration:line-through;"><i>${item.lastName}</i></strong></span>
														</c:otherwise>
													</c:choose>
														   
														   </a></em></t:text>
														  <t:help width="400" >
																<font color="STEELBLUE"><strong>Code :</strong></font> ${item.code}<BR/>
																<font color="STEELBLUE"><strong>Nom :</strong></font> ${item.lastName}<BR/>
																<font color="STEELBLUE"><strong>Prenom :</strong></font> ${item.firstName}<BR/>
																<font color="STEELBLUE"><strong>Email : </strong></font>${item.email}<BR/>																
																<font color="STEELBLUE"><strong>Active : </strong></font>${item.userActive}<BR/>
																<font color="STEELBLUE"><strong>Position : </strong></font>${item.position.name}<BR/>
																
															</t:help> 
												</t:tooltip>
											
											
											
											</td>
											<td align="left">${item.firstName}</td>
											<td align="left">${item.email}</td>											
											<td align="left">${item.position.name}</td>
											<td>
											<c:choose>
														<c:when test='${item.userActive == true }'>
														    
														 	<img src="images/puce.gif" border="0"/>
														</c:when>
														<c:otherwise>
														
														 <img src="images/delete.gif" border="0"/>
														</c:otherwise>
													</c:choose>
											</td>
											<td>
											<fmt:formatDate value="${item.dateOfHiring}" pattern="dd/MM/yyyy"/>
											</td>
											<th>

											<interaudit:accessRightSet right="REGISTER_USER">
											<a href="${ctx}/showEmployeeRegisterPage.htm?id=${item.id}"><img src="images/Table-Edit.png" border="0" alt="Edit"/></a>
											</interaudit:accessRightSet>
											<interaudit:accessRightNotSet right="REGISTER_USER">
											---
											</interaudit:accessRightNotSet>

											
											</th>
										</tr>
									
									<c:set var="row" value="${row + 1}"/>
									 
									  </c:forEach>
									
							
									
									
									
									</tbody>
								</table>
								<br/>
						</div>
	
  <!-- END ADDED PART -->   
<p class="pagination2" align="center">
			<span class="pagelinks">
				<!--span style="color:purple;">${ fn:length(viewbudget.budgets) } budgets found...</span-->
				<c:if test="${totalPage > 1}">
				    <c:set var="url" value="p=" />
				    <c:if test="${startingLetterName != null}">
				        <c:set var="url" value="startingLetterName=${startingLetterName}&${url}" />
				    </c:if>
				    <c:if test="${role != null}">
				        <c:set var="url" value="role=${role}&${url}" />
				    </c:if>
				    <c:if test="${nameLike != null}">
				        <c:set var="url" value="nameLike=${nameLike}&${url}" />
				    </c:if>
					<span class="pagination2" align="center">
						<span><a href="${ctx}/showGeneralBudgetPage.htm?${url}${1}">|</a></span>
				    
				        <c:choose>
				            <c:when test="${currentPage <= 1}">
								<span> <a href="${ctx}/showEmployeesPage.htm?${url}${currentPage }">� Previous</a></span>
				            </c:when>
				            <c:otherwise>
				                <span> <a href="${ctx}/showEmployeesPage.htm?${url}${currentPage - 1}">� Previous</a></span>
				            </c:otherwise>
				        </c:choose>
					
						<c:set var="max_page" value="10" />
						
						<c:set var="page_toshow" value="${totalPage}" />
						<c:set var="start_toshow" value="${currentPage}" />
						
						<c:if test="${currentPage + max_page > totalPage}">
							<c:set var="page_toshow" value="${totalPage}" />
						</c:if>
						
						<c:if test="${currentPage + max_page <= totalPage}">
							<c:set var="page_toshow" value="${currentPage + max_page}" />
						</c:if>
						
						<c:if test="${page_toshow - currentPage < max_page}">
							<c:if test="${page_toshow - max_page > 0}">
								<c:set var="start_toshow" value="${page_toshow - max_page}" />
							</c:if>			
						</c:if>
					
				        <c:forEach var="x" begin="${start_toshow}" end="${page_toshow}" step="1">
				            <c:choose>
				                <c:when test="${currentPage == x}">
									<span style="margin-left:5px; margin-right:5px;"><a style='background-color: #7C9FCB;'><c:out value="${x}"/></a></span></b>
				                </c:when>
				                <c:otherwise>
									<span><a href="${ctx}/showEmployeesPage.htm?${url}${x}"><c:out value="${x}"/></a></span>
				                </c:otherwise>
				            </c:choose>
				        </c:forEach>
					
				        <c:choose>
				            <c:when test="${currentPage >= totalPage}">
				               <span><a href="${ctx}/showEmployeesPage.htm?${url}${currentPage }">  Next � </a></span>
				            </c:when>
				            <c:otherwise>
				                <span><a href="${ctx}/showEmployeesPage.htm?${url}${currentPage + 1}"> Next � </a></span>
				            </c:otherwise>
				        </c:choose>
						<span><a href="${ctx}/showEmployeesPage.htm?${url}${totalPage}">|</a></span>
						</span>
					</c:if>
			</span>
			</p>
  
  
 
