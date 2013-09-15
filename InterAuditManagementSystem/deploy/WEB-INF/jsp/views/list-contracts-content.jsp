<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="t"      uri="/tags/tooltips-tiles" %>
<%@ taglib uri="/tags/interaudit" prefix="interaudit" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


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

function quelle_touche(evenement)
{

	var touche = window.event ? evenement.keyCode : evenement.which;
	if (touche == 13) {
		
		var expression = document.getElementById("custId").value;
		if (expression.length < 3)
		{
			alert("Entrez 3 caractères au minimun...!");
			return false;
		}
		else{
		
			 var expression = document.getElementById("custId").value;
			 var url = "${ctx}/showContractsPage.htm?customer=" + expression;
			  window.location = url;
		}
	}
}

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
	width:90px;
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

			<div  class="nav_alphabet"  style="text-align:center;font:  10px Verdana, sans-serif;">

					<ul class="pagination">
					
								<li style="margin-right:15px;border: 3px solid red;">
									<span style="font:10px Verdana, sans-serif;"><strong>Quick Search</strong> :</span>
									<input name="customer" id="custId" value=""  type="text"  onKeyDown="return quelle_touche(event);"><span style="color: blue;"><i>(3 car min)</i></span>
								</li>
								
							  <li><a href="${ctx}/showContractsPage.htm?startingLetterName=*">
										<c:if test='${viewcontracts.param.startingLetterName eq "*"}'><span style="color:orange;font-weight:bold">Any</span></c:if>
										<c:if test='${viewcontracts.param.startingLetterName != "*"}'>Any</c:if>					
							  </a></li>
							  <li>
								  <a href="${ctx}/showContractsPage.htm?startingLetterName=A">
										<c:if test='${viewcontracts.param.startingLetterName eq "A"}'><span style="color:orange;font-weight:bold">A</span></c:if>
										<c:if test='${viewcontracts.param.startingLetterName != "A"}'>A</c:if>					
								  </a>
							  </li>
							  <li>
								  <a href="${ctx}/showContractsPage.htm?startingLetterName=B">
										<c:if test='${viewcontracts.param.startingLetterName eq "B"}'><span style="color:orange;font-weight:bold">B</span></c:if>
										<c:if test='${viewcontracts.param.startingLetterName != "B"}'>B</c:if>
								  </a>
							  </li>
							  <li>
								  <a href="${ctx}/showContractsPage.htm?startingLetterName=C">
										<c:if test='${viewcontracts.param.startingLetterName eq "C"}'><span style="color:orange;font-weight:bold">C</span></c:if>
										<c:if test='${viewcontracts.param.startingLetterName != "C"}'>C</c:if>
								  </a>
							  </li>
							  <li>
								<a href="${ctx}/showContractsPage.htm?startingLetterName=D">
									<c:if test='${viewcontracts.param.startingLetterName eq "D"}'><span style="color:orange;font-weight:bold">D</span></c:if>
									<c:if test='${viewcontracts.param.startingLetterName != "D"}'>D</c:if>
								</a>
							  </li>
							  <li>
								<a href="${ctx}/showContractsPage.htm?startingLetterName=E">
									<c:if test='${viewcontracts.param.startingLetterName eq "E"}'><span style="color:orange;font-weight:bold">E</span></c:if>
									<c:if test='${viewcontracts.param.startingLetterName != "E"}'>E</c:if>
								</a>
							  </li>
							  <li>
								<a href="${ctx}/showContractsPage.htm?startingLetterName=F">
									<c:if test='${viewcontracts.param.startingLetterName eq "F"}'><span style="color:orange;font-weight:bold">F</span></c:if>
									<c:if test='${viewcontracts.param.startingLetterName != "F"}'>F</c:if>
								</a>
							  </li>
							  <li>
								<a href="${ctx}/showContractsPage.htm?startingLetterName=G">
									<c:if test='${viewcontracts.param.startingLetterName eq "G"}'><span style="color:orange;font-weight:bold">G</span></c:if>
									<c:if test='${viewcontracts.param.startingLetterName != "G"}'>G</c:if>
								</a>
							  </li>
							  <li>
								<a href="${ctx}/showContractsPage.htm?startingLetterName=H">
									<c:if test='${viewcontracts.param.startingLetterName eq "H"}'><span style="color:orange;font-weight:bold">H</span></c:if>
									<c:if test='${viewcontracts.param.startingLetterName != "H"}'>H</c:if>
								</a>
							 </li>
							 <li>
								<a href="${ctx}/showContractsPage.htm?startingLetterName=I">
									<c:if test='${viewcontracts.param.startingLetterName eq "I"}'><span style="color:orange;font-weight:bold">I</span></c:if>
									<c:if test='${viewcontracts.param.startingLetterName != "I"}'>I</c:if>
								</a>
							 </li>
							 <li><a href="${ctx}/showContractsPage.htm?startingLetterName=J">
									<c:if test='${viewcontracts.param.startingLetterName eq "J"}'><span style="color:orange;font-weight:bold">J</span></c:if>
									<c:if test='${viewcontracts.param.startingLetterName != "J"}'>J</c:if>
								</a>
							 </li>
							 <li><a href="${ctx}/showContractsPage.htm?startingLetterName=K">
									<c:if test='${viewcontracts.param.startingLetterName eq "K"}'><span style="color:orange;font-weight:bold">K</span></c:if>
									<c:if test='${viewcontracts.param.startingLetterName != "K"}'>K</c:if>
								</a>
							 </li>
							 <li><a href="${ctx}/showContractsPage.htm?startingLetterName=L">
									<c:if test='${viewcontracts.param.startingLetterName eq "L"}'><span style="color:orange;font-weight:bold">L</span></c:if>
									<c:if test='${viewcontracts.param.startingLetterName != "L"}'>L</c:if>
								</a>
							 </li>
							 <li><a href="${ctx}/showContractsPage.htm?startingLetterName=M">
										<c:if test='${viewcontracts.param.startingLetterName eq "M"}'><span style="color:orange;font-weight:bold">M</span></c:if>
										<c:if test='${viewcontracts.param.startingLetterName != "M"}'>M</c:if>
									</a>
							 </li>
							 <li>
								<a href="${ctx}/showContractsPage.htm?startingLetterName=N">
									<c:if test='${viewcontracts.param.startingLetterName eq "N"}'><span style="color:orange;font-weight:bold">N</span></c:if>
									<c:if test='${viewcontracts.param.startingLetterName != "N"}'>N</c:if>
								</a>
							 </li>
							 <li><a href="${ctx}/showContractsPage.htm?startingLetterName=O">
									<c:if test='${viewcontracts.param.startingLetterName eq "O"}'><span style="color:orange;font-weight:bold">O</span></c:if>
									<c:if test='${viewcontracts.param.startingLetterName != "O"}'>O</c:if>
								</a>
							 </li>
							 <li>
								<a href="${ctx}/showContractsPage.htm?startingLetterName=P">
									<c:if test='${viewcontracts.param.startingLetterName eq "P"}'><span style="color:orange;font-weight:bold">P</span></c:if>
									<c:if test='${viewcontracts.param.startingLetterName != "P"}'>P</c:if>
								</a>
							 </li>
							 <li>
								<a href="${ctx}/showContractsPage.htm?startingLetterName=Q">
									<c:if test='${viewcontracts.param.startingLetterName eq "Q"}'><span style="color:orange;font-weight:bold">Q</span></c:if>
									<c:if test='${viewcontracts.param.startingLetterName != "Q"}'>Q</c:if>
								</a>
							 </li>
							 <li><a href="${ctx}/showContractsPage.htm?startingLetterName=R">
									<c:if test='${viewcontracts.param.startingLetterName eq "R"}'><span style="color:orange;font-weight:bold">R</span></c:if>
									<c:if test='${viewcontracts.param.startingLetterName != "R"}'>R</c:if>
								</a>
							 </li>
							 <li>
								<a href="${ctx}/showContractsPage.htm?startingLetterName=S">
									<c:if test='${viewcontracts.param.startingLetterName eq "S"}'><span style="color:orange;font-weight:bold">S</span></c:if>
									<c:if test='${viewcontracts.param.startingLetterName != "S"}'>S</c:if>
								</a>
							 </li>
							  <li>
								<a href="${ctx}/showContractsPage.htm?startingLetterName=T">
									<c:if test='${viewcontracts.param.startingLetterName eq "T"}'><span style="color:orange;font-weight:bold">T</span></c:if>
									<c:if test='${viewcontracts.param.startingLetterName != "T"}'>T</c:if>
								</a>
							 </li>
							 <li><a href="${ctx}/showContractsPage.htm?startingLetterName=U">
									<c:if test='${viewcontracts.param.startingLetterName eq "U"}'><span style="color:orange;font-weight:bold">U</span></c:if>
									<c:if test='${viewcontracts.param.startingLetterName != "U"}'>U</c:if>
								</a>
							 </li>
							 <li><a href="${ctx}/showContractsPage.htm?startingLetterName=V">
									<c:if test='${viewcontracts.param.startingLetterName eq "V"}'><span style="color:orange;font-weight:bold">V</span></c:if>
									<c:if test='${viewcontracts.param.startingLetterName != "V"}'>V</c:if>
								</a>
							</li>
							<li>
								<a href="${ctx}/showContractsPage.htm?startingLetterName=W">
									<c:if test='${viewcontracts.param.startingLetterName eq "W"}'><span style="color:orange;font-weight:bold">W</span></c:if>
									<c:if test='${viewcontracts.param.startingLetterName != "W"}'>W</c:if>
								</a>
							</li>
							<li><a href="${ctx}/showContractsPage.htm?startingLetterName=X">
									<c:if test='${viewcontracts.param.startingLetterName eq "X"}'><span style="color:orange;font-weight:bold">X</span></c:if>
									<c:if test='${viewcontracts.param.startingLetterName != "X"}'>X</c:if>
								</a>
							</li>
							<li><a href="${ctx}/showContractsPage.htm?startingLetterName=Y">
									<c:if test='${viewcontracts.param.startingLetterName eq "Y"}'><span style="color:orange;font-weight:bold">Y</span></c:if>
									<c:if test='${viewcontracts.param.startingLetterName != "Y"}'>Y</c:if>
								</a>
							</li>
							<li>
								<a href="${ctx}/showContractsPage.htm?startingLetterName=Z">
									<c:if test='${viewcontracts.param.startingLetterName eq "Z"}'><span style="color:orange;font-weight:bold">Z</span></c:if>
									<c:if test='${viewcontracts.param.startingLetterName != "Z"}'>Z</c:if>
								</a>
							</li>
							</ul>					
				</div>
			<%--form  name="listForm" action="showContractsPage.htm" method="post" >
			
				

				
				
				<span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Status</strong> : </span> &nbsp;
					<c:choose>
			    		<c:when test='${viewcontracts.param.pending=="1"}'>
			      			<input type="checkbox" name="active" value="1" checked>&nbsp;<span style="font:10px Verdana, sans-serif;margin-right:10pt; color: black;">Active only </span> &nbsp;
			      		</c:when>
			      		<c:otherwise>
			      			<input type="checkbox" name="active" value="1">&nbsp;<span style="font:10px Verdana, sans-serif;margin-right:10pt; color: black;">Pending </span> &nbsp;
			      		</c:otherwise>
			     	</c:choose>
					&nbsp;
				<input style="font:10px Verdana, sans-serif;" type="submit" class="button120" value="Search"/>
				</form--%>
				
				
			</div>
			<br/>
			


			        <table id="ver-zebra" width="100%" cellspacing="0" class="formlist">
									<caption><span style="color:orange;">List of contracts </span></caption>
									<thead>
									<tr>
										<th scope="col">&nbsp;</th>
										<%--th scope="col">Reference</th--%>
										<th scope="col">Client</th>
										<th scope="col">Reference</th>
										
										<th scope="col">Amount</th>
										<th scope="col">Active</th>
										<th scope="col">Client</th>
										<th scope="col">Mission</th>
										<th scope="col">From date</th>
										<th scope="col">To date</th>
										
										<th scope="col">Actions</th>
									</tr>
									</thead>
									<tbody>
									
									
									
									<c:set var="row" value="0"/>
									 <c:forEach var="item" items="${viewcontracts.contracts}" varStatus="loop">
									 	
										 	<c:choose>
									    		<c:when test='${row % 2 eq 0 }'>
									      			<tr style="background: #eff2ff;"  onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
									      		</c:when>
									      		<c:otherwise>
									      			<tr onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
									      		</c:otherwise>
									     	</c:choose>
									     	
											
											<%--td><img src="images/contract.png" border="0"/></td--%>
											<%--td align="left">&nbsp;<a href="${ctx}/editContractPage.htm?contractId=${item.id}"><i>${item.reference}</i></a></td--%>
											<th scope="row" class="column1">
													<span style=" color: black;">
														<fmt:formatNumber  minFractionDigits="0">
															${1+ (loop.index )}
														</fmt:formatNumber>
													</span>
												</th>
												
											<td  align="left">
											
											<c:choose>
														<c:when test='${(item.custActive == false) && (item.valid == true)}'>
															<t:tooltip>  
																	   <t:text>
																	   <span style="color: red;text-decoration:line-through;"><strong>${fn:substring(item.customerName,0,30)}</strong></span><em>
																		<img src="images/warning.gif" border="0"/>	                                                                                                                  
																	   </em>																   
																	  
																	   </t:text>
																	  <t:help width="200">
																		<font color="red"><strong>This contrat is active but its client is inactive...So it won't be taken into account when building new budgets...
																		<c:if test='${item.past == true}'>
																		  Beside the validity period is also over...
																		</c:if>
																		
																		</strong></font><br/><hr/> 	                                                                                                                   
																	   </t:help> 
															</t:tooltip>
														</c:when>
														<c:when test='${(item.custActive == true) && (item.valid == false)}'>
														<span style="color: red;text-decoration:line-through;"><strong>${fn:substring(item.customerName,0,30)}</strong></span>
														</c:when>
														<c:when test='${(item.custActive == false) && (item.past == true)}'>
                                                        <t:tooltip>  
                                                                       <t:text>
                                                                       <span style="color: red;text-decoration:line-through;"><strong>${fn:substring(item.customerName,0,30)}</strong></span><em>
                                                                        <img src="images/warning.gif" border="0"/>                                                                                                                    
                                                                       </em>                                                                   
                                                                      
                                                                       </t:text>
                                                                      <t:help width="200">
                                                                        <font color="red"><strong>This contrat  validity period is over...So it won't be taken into account when building new budgets...</strong></font><br/><hr/>                                                                                                                     
                                                                       </t:help> 
                                                            </t:tooltip>
                                                        </c:when>
														<c:otherwise>
															<span style="color: blue;"><strong>${fn:substring(item.customerName,0,30)}</strong></span>
														</c:otherwise>
													</c:choose>
											
											
											
											</td>
											<td align="left">

											
											
											<t:tooltip>  
														  <t:text><em><a href="${ctx}/editContractPage.htm?contractId=${item.id}" alt="" title=""><span style="color:black;">${item.reference}</span></a></em>
														  </t:text>
														  <t:help width="400" >
														   <font color="STEELBLUE"><strong>Societe :</strong></font> ${item.customerName}<BR/>
														   <font color="STEELBLUE"><strong>Amount/Year :</strong></font> ${item.amount}<BR/>
														   <font color="STEELBLUE"><strong>Mission : </strong></font>${item.missionType}	
														  </t:help> 
												</t:tooltip>
											
											</td>
											
											<td><span style="color:green;">
											<fmt:formatNumber type="currency" currencySymbol="&euro;">
														${item.amount}
											</fmt:formatNumber></span>
											</td>
											<td>
                                                <c:choose>
                                                        <c:when test='${item.valid == true }'>
                                                            
                                                            <img src="images/puce.gif" border="0"/>
                                                        </c:when>
                                                        <c:otherwise>
                                                        
                                                         <img src="images/delete.gif" border="0"/>
                                                        </c:otherwise>
                                                    </c:choose>
                                            </td>
                                            <td>
                                                <c:choose>
                                                        <c:when test='${item.custActive == true }'>
                                                            
                                                            <img src="images/puce.gif" border="0"/>
                                                        </c:when>
                                                        <c:otherwise>
                                                        
                                                         <img src="images/delete.gif" border="0"/>
                                                        </c:otherwise>
                                                    </c:choose>
                                            </td>
                                            
											<td  align="left">${item.missionType}</td>
											<td>
											
											
											<fmt:formatDate value="${item.fromDate}" pattern="dd/MM/yyyy"/>
											
											</td>
											<td>
											<fmt:formatDate value="${item.toDate}" pattern="dd/MM/yyyy"/>
											</td>
											
											<th>
											
											<interaudit:accessRightSet right="REGISTER_CONTRACT">
													<a href="${ctx}/showContractRegisterPage.htm?id=${item.id}"><img src="images/Table-Edit.png" border="0" alt="Edit"/></a>
												</interaudit:accessRightSet>
												<interaudit:accessRightNotSet right="REGISTER_CONTRACT">
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
  </div>

	<!-- END ADDED PART -->   
	<p class="pagination2" align="center">
			<span class="pagelinks">
				
				<c:if test="${totalPage > 1}">
				    <c:set var="url" value="p=" />
				    <c:if test="${customer != null}">
				        <c:set var="url" value="customer=${customer}&${url}" />
				    </c:if>
					<span class="pagination2" align="center">
						<span><a href="${ctx}/showContractsPage.htm?${url}${1}">|</a></span>
				    
				        <c:choose>
				            <c:when test="${currentPage <= 1}">
								<span> <a href="${ctx}/showContractsPage.htm?${url}${currentPage }">Previous</a></span>
				            </c:when>
				            <c:otherwise>
				                <span> <a href="${ctx}/showContractsPage.htm?${url}${currentPage - 1}"> Previous</a></span>
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
									<span><a href="${ctx}/showContractsPage.htm?${url}${x}"><c:out value="${x}"/></a></span>
				                </c:otherwise>
				            </c:choose>
				        </c:forEach>
					
				        <c:choose>
				            <c:when test="${currentPage >= totalPage}">
				               <span><a href="${ctx}/showContractsPage.htm?${url}${currentPage }">  Next  </a></span>
				            </c:when>
				            <c:otherwise>
				                <span><a href="${ctx}/showContractsPage.htm?${url}${currentPage + 1}"> Next </a></span>
				            </c:otherwise>
				        </c:choose>
						<span><a href="${ctx}/showContractsPage.htm?${url}${totalPage}">|</a></span>
						</span>
					</c:if>
			</span>
			</p>