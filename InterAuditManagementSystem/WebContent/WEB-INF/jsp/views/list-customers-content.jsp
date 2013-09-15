<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="t"      uri="/tags/tooltips-tiles" %>
<%@ taglib uri="/tags/interaudit" prefix="interaudit" %>
<!-- START ADDED PART -->   
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
ul.pagination {
	margin: 0 auto;
	padding: 0;
	display: inline-block;
	background: #f2f2f2;	
	border: 1px solid #e6e6e6;
	color: #000;
	font-size: 10pt;
	font-weight: normal;
	list-style-type: none;
	white-space: nowrap;
	zoom:1;
	*display:inline;
}
ul.pagination li {
	float: left;
	padding: 1px;	
}
ul.pagination li.active {
	padding: 3px 1px;
	/*background: #e0e0e0;
	color: #174962;*/
	background: #11547a;
	color: #def;
}
ul.pagination a {
	display: block;
	float: left;
	padding: 3px 1px;
	/*background:url("../image/deco/menu_bg_pagination.gif") repeat-x #ffffff;*/
	background: #11547a;
	border: 1px solid #05314a;
	color: #fff;
	line-height: 1em;
	text-decoration: none;
}
ul.pagination a:hover {
	/*background: #11547a;*/
	background: #fff;
	border: 1px solid #036;
	/*color: #def;*/
	color:#11547a;
}

</style>

	

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
		/*color: #039;*/
		/*background: #eff2ff;*/
		/*background:  rgb(123,132,132);*/
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

		 
			<div  class="nav_alphabet"  style="text-align:center;font:  10px Verdana, sans-serif;">

					<ul class="pagination">
							  <li><a href="${ctx}/showCustomersPage.htm?startingLetterName=*">
										<c:if test='${viewcustomers.param.startingLetterName eq "*"}'><span style="color:orange;font-weight:bold">Any</span></c:if>
										<c:if test='${viewcustomers.param.startingLetterName != "*"}'>Any</c:if>					
							  </a></li>
							  <li>
								  <a href="${ctx}/showCustomersPage.htm?startingLetterName=A">
										<c:if test='${viewcustomers.param.startingLetterName eq "A"}'><span style="color:orange;font-weight:bold">A</span></c:if>
										<c:if test='${viewcustomers.param.startingLetterName != "A"}'>A</c:if>					
								  </a>
							  </li>
							  <li>
								  <a href="${ctx}/showCustomersPage.htm?startingLetterName=B">
										<c:if test='${viewcustomers.param.startingLetterName eq "B"}'><span style="color:orange;font-weight:bold">B</span></c:if>
										<c:if test='${viewcustomers.param.startingLetterName != "B"}'>B</c:if>
								  </a>
							  </li>
							  <li>
								  <a href="${ctx}/showCustomersPage.htm?startingLetterName=C">
										<c:if test='${viewcustomers.param.startingLetterName eq "C"}'><span style="color:orange;font-weight:bold">C</span></c:if>
										<c:if test='${viewcustomers.param.startingLetterName != "C"}'>C</c:if>
								  </a>
							  </li>
							  <li>
								<a href="${ctx}/showCustomersPage.htm?startingLetterName=D">
									<c:if test='${viewcustomers.param.startingLetterName eq "D"}'><span style="color:orange;font-weight:bold">D</span></c:if>
									<c:if test='${viewcustomers.param.startingLetterName != "D"}'>D</c:if>
								</a>
							  </li>
							  <li>
								<a href="${ctx}/showCustomersPage.htm?startingLetterName=E">
									<c:if test='${viewcustomers.param.startingLetterName eq "E"}'><span style="color:orange;font-weight:bold">E</span></c:if>
									<c:if test='${viewcustomers.param.startingLetterName != "E"}'>E</c:if>
								</a>
							  </li>
							  <li>
								<a href="${ctx}/showCustomersPage.htm?startingLetterName=F">
									<c:if test='${viewcustomers.param.startingLetterName eq "F"}'><span style="color:orange;font-weight:bold">F</span></c:if>
									<c:if test='${viewcustomers.param.startingLetterName != "F"}'>F</c:if>
								</a>
							  </li>
							  <li>
								<a href="${ctx}/showCustomersPage.htm?startingLetterName=G">
									<c:if test='${viewcustomers.param.startingLetterName eq "G"}'><span style="color:orange;font-weight:bold">G</span></c:if>
									<c:if test='${viewcustomers.param.startingLetterName != "G"}'>G</c:if>
								</a>
							  </li>
							  <li>
								<a href="${ctx}/showCustomersPage.htm?startingLetterName=H">
									<c:if test='${viewcustomers.param.startingLetterName eq "H"}'><span style="color:orange;font-weight:bold">H</span></c:if>
									<c:if test='${viewcustomers.param.startingLetterName != "H"}'>H</c:if>
								</a>
							 </li>
							 <li>
								<a href="${ctx}/showCustomersPage.htm?startingLetterName=I">
									<c:if test='${viewcustomers.param.startingLetterName eq "I"}'><span style="color:orange;font-weight:bold">I</span></c:if>
									<c:if test='${viewcustomers.param.startingLetterName != "I"}'>I</c:if>
								</a>
							 </li>
							 <li><a href="${ctx}/showCustomersPage.htm?startingLetterName=J">
									<c:if test='${viewcustomers.param.startingLetterName eq "J"}'><span style="color:orange;font-weight:bold">J</span></c:if>
									<c:if test='${viewcustomers.param.startingLetterName != "J"}'>J</c:if>
								</a>
							 </li>
							 <li><a href="${ctx}/showCustomersPage.htm?startingLetterName=K">
									<c:if test='${viewcustomers.param.startingLetterName eq "K"}'><span style="color:orange;font-weight:bold">K</span></c:if>
									<c:if test='${viewcustomers.param.startingLetterName != "K"}'>K</c:if>
								</a>
							 </li>
							 <li><a href="${ctx}/showCustomersPage.htm?startingLetterName=L">
									<c:if test='${viewcustomers.param.startingLetterName eq "L"}'><span style="color:orange;font-weight:bold">L</span></c:if>
									<c:if test='${viewcustomers.param.startingLetterName != "L"}'>L</c:if>
								</a>
							 </li>
							 <li><a href="${ctx}/showCustomersPage.htm?startingLetterName=M">
										<c:if test='${viewcustomers.param.startingLetterName eq "M"}'><span style="color:orange;font-weight:bold">M</span></c:if>
										<c:if test='${viewcustomers.param.startingLetterName != "M"}'>M</c:if>
									</a>
							 </li>
							 <li>
								<a href="${ctx}/showCustomersPage.htm?startingLetterName=N">
									<c:if test='${viewcustomers.param.startingLetterName eq "N"}'><span style="color:orange;font-weight:bold">N</span></c:if>
									<c:if test='${viewcustomers.param.startingLetterName != "N"}'>N</c:if>
								</a>
							 </li>
							 <li><a href="${ctx}/showCustomersPage.htm?startingLetterName=O">
									<c:if test='${viewcustomers.param.startingLetterName eq "O"}'><span style="color:orange;font-weight:bold">O</span></c:if>
									<c:if test='${viewcustomers.param.startingLetterName != "O"}'>O</c:if>
								</a>
							 </li>
							 <li>
								<a href="${ctx}/showCustomersPage.htm?startingLetterName=P">
									<c:if test='${viewcustomers.param.startingLetterName eq "P"}'><span style="color:orange;font-weight:bold">P</span></c:if>
									<c:if test='${viewcustomers.param.startingLetterName != "P"}'>P</c:if>
								</a>
							 </li>
							 <li>
								<a href="${ctx}/showCustomersPage.htm?startingLetterName=Q">
									<c:if test='${viewcustomers.param.startingLetterName eq "Q"}'><span style="color:orange;font-weight:bold">Q</span></c:if>
									<c:if test='${viewcustomers.param.startingLetterName != "Q"}'>Q</c:if>
								</a>
							 </li>
							 <li><a href="${ctx}/showCustomersPage.htm?startingLetterName=R">
									<c:if test='${viewcustomers.param.startingLetterName eq "R"}'><span style="color:orange;font-weight:bold">R</span></c:if>
									<c:if test='${viewcustomers.param.startingLetterName != "R"}'>R</c:if>
								</a>
							 </li>
							 <li>
								<a href="${ctx}/showCustomersPage.htm?startingLetterName=S">
									<c:if test='${viewcustomers.param.startingLetterName eq "S"}'><span style="color:orange;font-weight:bold">S</span></c:if>
									<c:if test='${viewcustomers.param.startingLetterName != "S"}'>S</c:if>
								</a>
							 </li>
							  <li>
								<a href="${ctx}/showCustomersPage.htm?startingLetterName=T">
									<c:if test='${viewcustomers.param.startingLetterName eq "T"}'><span style="color:orange;font-weight:bold">T</span></c:if>
									<c:if test='${viewcustomers.param.startingLetterName != "T"}'>T</c:if>
								</a>
							 </li>
							 <li><a href="${ctx}/showCustomersPage.htm?startingLetterName=U">
									<c:if test='${viewcustomers.param.startingLetterName eq "U"}'><span style="color:orange;font-weight:bold">U</span></c:if>
									<c:if test='${viewcustomers.param.startingLetterName != "U"}'>U</c:if>
								</a>
							 </li>
							 <li><a href="${ctx}/showCustomersPage.htm?startingLetterName=V">
									<c:if test='${viewcustomers.param.startingLetterName eq "V"}'><span style="color:orange;font-weight:bold">V</span></c:if>
									<c:if test='${viewcustomers.param.startingLetterName != "V"}'>V</c:if>
								</a>
							</li>
							<li>
								<a href="${ctx}/showCustomersPage.htm?startingLetterName=W">
									<c:if test='${viewcustomers.param.startingLetterName eq "W"}'><span style="color:orange;font-weight:bold">W</span></c:if>
									<c:if test='${viewcustomers.param.startingLetterName != "W"}'>W</c:if>
								</a>
							</li>
							<li><a href="${ctx}/showCustomersPage.htm?startingLetterName=X">
									<c:if test='${viewcustomers.param.startingLetterName eq "X"}'><span style="color:orange;font-weight:bold">X</span></c:if>
									<c:if test='${viewcustomers.param.startingLetterName != "X"}'>X</c:if>
								</a>
							</li>
							<li><a href="${ctx}/showCustomersPage.htm?startingLetterName=Y">
									<c:if test='${viewcustomers.param.startingLetterName eq "Y"}'><span style="color:orange;font-weight:bold">Y</span></c:if>
									<c:if test='${viewcustomers.param.startingLetterName != "Y"}'>Y</c:if>
								</a>
							</li>
							<li>
								<a href="${ctx}/showCustomersPage.htm?startingLetterName=Z">
									<c:if test='${viewcustomers.param.startingLetterName eq "Z"}'><span style="color:orange;font-weight:bold">Z</span></c:if>
									<c:if test='${viewcustomers.param.startingLetterName != "Z"}'>Z</c:if>
								</a>
							</li>
					</ul>	

					
				</div>
			<div class="nav_alphabet"  style="text-align:center;font:  10px Verdana, sans-serif;">
			<table align="center"><tr>
				<td  >
					<form style="margin-right :2px; text-align:center;" name="listForm1" action="findCustomerByIdPage.htm" method="post" >
					<span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Customer code</strong> :</span>
						<input name="customerCode" value="${viewcustomers.param.customerCode}" type="text" style="font:10px Verdana, sans-serif;margin-right:10pt;" />
						&nbsp;
						<input style="font:10px Verdana, sans-serif;" type="submit" class="button120" value="Find By Identifier"/>
					</form>
			</td>
			<td width="2%">&nbsp;</td>
			<td>
				<form name="listForm" action="showCustomersPage.htm" method="post" >
					<span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Associ&eacute;</strong> : </span>
					<select style="font:10px Verdana, sans-serif;margin-right:10pt;" name="associeName" onchange="document.listForm.submit();">
					    <option value="">Any</option>
						<c:forEach var="e" items="${viewcustomers.associes}">
							<option value="${e.id}"<c:if test='${viewcustomers.param.associeName==e.id}'> selected</c:if>>${e.name}</option>
						</c:forEach>						
					</select> 
					<span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Manager</strong> : </span>
					<select style="font:10px Verdana, sans-serif;margin-right:10pt;" name="managerName" onchange="document.listForm.submit();">
					    <option value="">Any</option>
						<c:forEach var="e" items="${viewcustomers.managers}">
							<option value="${e.id}"<c:if test='${viewcustomers.param.managerName==e.id}'> selected</c:if>>${e.name}</option>
						</c:forEach>
						<c:forEach var="e" items="${viewcustomers.directorsOptions}">
                            <option value="${e.id}"<c:if test='${viewcustomers.param.managerName==e.id}'> selected</c:if>>${e.name}</option>
                        </c:forEach>
					</select> 
					<span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Keywords</strong> : </span>
					<input name="nameLike" value="${viewcustomers.param.nameLike}" type="text" style="font:10px Verdana, sans-serif;margin-right:10pt;" />
					&nbsp;
					<!--  input style="font:10px Verdana, sans-serif;" type="submit" class="button120" value="Search" /-->		
				</form>

				</td></tr></table>
				</div>
			</div>
			<br/>
			
			        <table id="ver-zebra" width="100%" cellspacing="0" class="formlist">
									<caption><span style="color:orange;">List of customers </span></caption>
									<thead>
									<tr>
										<th scope="col">Code</th>
										<th scope="col">Name</th>
										<th scope="col">Origin</th>
										<th scope="col">Email</th>
										<th scope="col">Telephone</th>
										<th scope="col">Active</th>
										<th scope="col">Associate</th>
										<th scope="col">Manager</th>
										<th scope="col">Actions</th>
									</tr>
									</thead>
									<tbody>
									
								
					
									<c:set var="row" value="0"/>
									 <c:forEach var="item" items="${viewcustomers.customers}" varStatus="loop">
										 	<c:choose>
									    		<c:when test='${row % 2 eq 0 }'>
									      			<tr style="background: #eff2ff;" onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
									      		</c:when>
									      		<c:otherwise>
									      			<tr onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
									      		</c:otherwise>
									     	</c:choose>
												<th >${item.code}</th>
												<td align="left">
												
												

												<t:tooltip>  
														   <t:text><em><a href="${ctx}/editCustomerPage.htm?customerId=${item.id}" alt="" title=""/>&nbsp;
														   
														   
														   <c:choose>
														<c:when test='${item.active == true }'>
														    
														 	<span style="color:purple;"><i>${item.companyName}</i></span>
														</c:when>
														<c:otherwise>
														
														 <span style="color:red;text-decoration:line-through;"><i>${item.companyName}</i></span>
														</c:otherwise>
													</c:choose>
														   
														   
														   </a></em>
														   
														   </t:text>
														  <t:help width="400" >
														   <font color="STEELBLUE"><strong>Societe :</strong></font> ${item.companyName}<BR/>
														   <font color="STEELBLUE"><strong>Code :</strong></font> ${item.code}<BR/>
														   <font color="STEELBLUE"><strong>Origine : </strong></font>${item.originName}<BR/>
														   <font color="STEELBLUE"><strong>Email : </strong></font>${item.email}<BR/>
														   <font color="STEELBLUE"><strong>Telephone : </strong></font>${item.phone}<BR/>
															<font color="STEELBLUE"><strong>Active : </strong></font>${item.active}<BR/>
															<font color="STEELBLUE"><strong>Associe : </strong></font>${item.associeSignataireName}<BR/>
															<font color="STEELBLUE"><strong>Manager : </strong></font>${item.customerManagerName}<BR/>
															<font color="STEELBLUE"><strong>Activity : </strong></font>${item.mainActivity}<BR/>
															<font color="STEELBLUE"><strong>Addresse : </strong></font>${item.mainAddress}<BR/>
															
															
															</t:help> 
												</t:tooltip>
												
												</td>
												<td align="left">${item.originName}</td>
												<td align="left">${item.email}</td>
												<td align="left">${item.phone}</td>
												<td>
												<c:choose>
														<c:when test='${item.active == true }'>
														    
														 	<img src="images/puce.gif" border="0"/>
														</c:when>
														<c:otherwise>
														
														 <img src="images/delete.gif" border="0"/>
														</c:otherwise>
													</c:choose>
												</td>
												<td>${item.associeSignataireName}</td>
												<td>${item.customerManagerName}</td>
												<td>
												<interaudit:accessRightSet right="REGISTER_CUST">
													<a href="${ctx}/showCustomerRegisterPage.htm?id=${item.id}"><img src="images/Table-Edit.png" border="0" alt="Edit"/></a>
												</interaudit:accessRightSet>
												<interaudit:accessRightNotSet right="REGISTER_CUST">
													---
												</interaudit:accessRightNotSet>
												
												</td>
											</tr>
										<c:set var="row" value="${row + 1}"/>
									  </c:forEach>
									</tbody>
								</table>
								<BR/>
						</div>
	
  <!-- END ADDED PART -->   
  <p class="pagination2" align="center">
			<span class="pagelinks">				
				<c:if test="${totalPage > 1}">
				    <c:set var="url" value="p=" />
				    <c:if test="${!empty managerName}">
				        <c:set var="url" value="managerName=${managerName}&${url}" />
				    </c:if>
				    <c:if test="${!empty associeName}">
                        <c:set var="url" value="associeName=${associeName}&${url}" />
                    </c:if>
				    <c:if test="${!empty nameLike != null}">
				        <c:set var="url" value="nameLike=${nameLike}&${url}" />
				    </c:if>
					<span class="pagination2" align="center">
						<span><a href="${ctx}/showCustomersPage.htm?${url}${1}">|</a></span>
				    
				        <c:choose>
				            <c:when test="${currentPage <= 1}">
								<span> <a href="${ctx}/showCustomersPage.htm?${url}${currentPage }">� Previous</a></span>
				            </c:when>
				            <c:otherwise>
				                <span> <a href="${ctx}/showCustomersPage.htm?${url}${currentPage - 1}">� Previous</a></span>
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
									<span><a href="${ctx}/showCustomersPage.htm?${url}${x}"><c:out value="${x}"/></a></span>
				                </c:otherwise>
				            </c:choose>
				        </c:forEach>
					
				        <c:choose>
				            <c:when test="${currentPage >= totalPage}">
				               <span><a href="${ctx}/showCustomersPage.htm?${url}${currentPage }">  Next � </a></span>
				            </c:when>
				            <c:otherwise>
				                <span><a href="${ctx}/showCustomersPage.htm?${url}${currentPage + 1}"> Next � </a></span>
				            </c:otherwise>
				        </c:choose>
						<span><a href="${ctx}/showCustomersPage.htm?${url}${totalPage}">|</a></span>
						</span>
					</c:if>
			</span>
			</p>

			









  
