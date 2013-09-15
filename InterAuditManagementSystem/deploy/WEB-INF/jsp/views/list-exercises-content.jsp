<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions"%>

<!-- START ADDED PART -->

<style>
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
</style>
    
	
	  
	
		
 		<div class="nav_alphabet" style="border-bottom: 1px solid  #0066aa; text-align:center; padding-top:1pt; font: bold 12px Verdana, sans-serif;">

		<table ><tr>
		
		<td>
			<form name="listForm" action="showInvoicesPage.htm" method="post" >
			
				<span style="font:10px Verdana, sans-serif;margin-right:5pt;"><strong>Exercise</strong> : </span>
				<select style="font:10px Verdana, sans-serif;margin-right:5pt;" name="year" >
				  	<option value="">2009</option>
				    <c:forEach var="y" items="${viewinvoices.exercicesOptions}">
                		<option value="${y.id}"<c:if test='${viewinvoices.param.year==y.id}'> selected</c:if>>${y.name}</option>
            		</c:forEach>
				</select> 

				 &nbsp;
	
				<input style="font:10px Verdana, sans-serif;" type="submit" class="button120" value="Search"/>
				</form>
				</td></tr></table>
				<br/>
			</div>
			

			        <table id="hor-minimalist-b" width="100%" cellspacing="0" >
									<caption><span style="color:orange;">List of exercises </span></caption>
									<!--thead>
									<tr>
										<th scope="col">Reference</th>
										<th scope="col">Customer</th>
										<th scope="col">Exercise</th>
										<th scope="col">Created date</th>
										<th scope="col">Sent date</th>
										<th scope="col">Paid date</th>
										<th scope="col">Status</th>
										<th scope="col">Libelle</th>
										<th scope="col">Amount</th>
										<th scope="col">Type</th>
										<th scope="col">Associe</th>
										<th scope="col">Actions</th>
									</tr>
									</thead>
									<tbody>
									
									
									
									<c:set var="row" value="0"/>
									 <c:forEach var="item" items="${viewinvoices.invoices}" varStatus="loop">
									 	
										 	<c:choose>
									    		<c:when test='${row % 2 eq 0 }'>
									      			<tr onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
									      		</c:when>
									      		<c:otherwise>
									      			<tr style="background: #dddddd;" onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
									      		</c:otherwise>
									     	</c:choose>
									     	
											<th>${item.reference}</th>
											<td>${item.customerName}</td>
											<td>${item.exercise}</td>
											<td>${item.dateFacture}</td>
											<td>${item.sentDate}</td>
											<td>${item.dateOfPayment}</td>
											<td>${item.status}</td>
											<td>${item.libelle}</td>
											<td>${item.amountEuro}</td>
											<td>${item.type}</td>
											<td>${item.associeCode}</td>
											<td><a href="${ctx}/showAdvancePropertyPage.htm?id=${item.id}"><img src="images/Table-Edit.png" border="0" alt="Edit"/></a></td>
										</tr>
									
										<c:set var="row" value="${row + 1}"/>
									 
									  </c:forEach>
									
									</tbody-->

									<thead>
											<tr>
												<th>year</th>
												<th>Create date</th>
												<th>currency</th>
												<th>Prevision</th>
												<th>Report</th>
												<th>Mission speciales</th>
												<th>Total</th>
												<th>Actions</th>
											</tr>
										</thead>
										<tbody>
											<tr onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
												<td>2009</td>
												<td>20/12/2008</td>
												<td>EURO</td>
												<td>1710 330,51</td>
												<td>423684</td>
												<td>443 450</td>
												<td>2 137 002</td>
												<td><a href="${ctx}/showExerciseRegisterPage.htm?id=${item.id}"><img src="images/Table-Edit.png" border="0" alt="Edit"/></a></td>
												
											</tr>
											<tr onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
												<td>2008</td>
												<td>20/12/2008</td>
												<td>EURO</td>
												<td>1710 330,51</td>
												<td>423684</td>
												<td>443 450</td>
												<td>2 137 002</td>
												<td><a href="${ctx}/showExerciseRegisterPage.htm?id=${item.id}"><img src="images/Table-Edit.png" border="0" alt="Edit"/></a></td>
												
											</tr>
											<tr onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
												<td>2007</td>
												<td>20/12/2008</td>
												<td>EURO</td>
												<td>1710 330,51</td>
												<td>423684</td>
												<td>443 450</td>
												<td>2 137 002</td>
												<td><a href="${ctx}/showExerciseRegisterPage.htm?id=${item.id}"><img src="images/Table-Edit.png" border="0" alt="Edit"/></a></td>
												
											</tr>
											<tr onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
												<td>2006</td>
												<td>20/12/2008</td>
												<td>EURO</td>
												<td>1710 330,51</td>
												<td>423684</td>
												<td>443 450</td>
												<td>2 137 002</td>
												<td><a href="${ctx}/showExerciseRegisterPage.htm?id=${item.id}"><img src="images/Table-Edit.png" border="0" alt="Edit"/></a></td>
												
											</tr>
											<tr onMouseOver="this.className='highlight'" onMouseOut="this.className='normal'">
												<td>2005</td>
												<td>20/12/2008</td>
												<td>EURO</td>
												<td>1710 330,51</td>
												<td>423684</td>
												<td>443 450</td>
												<td>2 137 002</td>
												<td><a href="${ctx}/showExerciseRegisterPage.htm?id=${item.id}"><img src="images/Table-Edit.png" border="0" alt="Edit"/></a></td>	
											</tr>

								</table>
								<br/>
						</div>
  </div>

	<!-- END ADDED PART -->   