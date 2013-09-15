<jsp:include page="common_css.jsp"/>
<!-- Begin Wrapper -->
<div id="wrapper">

  <!-- Begin Header -->
  <div id="header"><h4><span style="color:#039;">Invoice</span> : <span style="color:orange;">F-08/315</span> </h4></div>
  <!-- End Header -->

  <!-- Begin Left Column -->
  <div id="leftcolumn"> 
	<div id="navcontainer">
       <ul id="navlist">
                                <!-- CSS Tabs -->
		<li><a id="current" href="${ctx}/showFinalBillPropertyPage.htm">Details</a></li>
		<li><a  href="${ctx}/showInvoicePaymentPage.htm">Payments</a></li>
		<li><a  href="${ctx}/showInvoiceReminderPage.htm">Reminders</a>							
		<li><a  href="${ctx}/showInvoiceDocumentsPage.htm">Documents</a></li>

		</ul>
     </div>
  </div>
  <!-- End Left Column -->

  <!-- Begin Content Column -->
  <div id="content">
<div id="basic" class="myform" style="background-color: white;">
   
							  <form id="form1" name="form1" method="post" action="">
								
								
								<p></p>	
	<table id="hor-minimalist-b" summary="Employee Pay Sheet">
										
										<tbody>
								
											<tr>
												<td align="left" width="40%" >Status </td>
												
												<td align="center"><span style="color:orange;"> To be validated</span></td>
											</tr>	
											
											<tr>
												<td align="left" width="40%" >Reason </td>
												
												<td align="center"><span style="color:black;"> Note Honoraires</span></td>
											</tr>	
											<tr>
												<td align="left" width="40%" >Company </td>
												
												<td align="center"><span style="color:black;"> LuxLait S.a.r.l</span></td>
											</tr>
											<tr>
												<td align="left" width="40%" >Billing address </td>
												
												<td align="center"><span style="color:black;"> 11 rue robert parisot 57000 Metz</span></td>
											</tr>

											<tr>
												<td align="left" width="40%" >Contact person </td>
												
												<td align="center"><span style="color:black;">Monsieur &nbsp; Black &nbsp;Adams</span></td>
											</tr>
										</tbody>
								</table>

								

								

								
						
								
							
								<table id="hor-minimalist-b" summary="Employee Pay Sheet">
										<!--caption>Invoice summary</caption-->
										<thead>
											<tr>
												<th scope="col"></th>
												<th scope="col">To impute</th>
												<th scope="col">To deduct</th>												
											</tr>
										</thead>
										<tbody>
											<tr>
												<td  align="left"><span style="color:orange;">Worked hours</span></td>
												<td  align="center"><span style="color:black;"> + 22 500 €</span></td>
												<td  align="center"></td>												
											</tr>
											<tr>
												<td  align="left"><span style="color:orange;">Advances</span></td>
												<td  align="center"></td>
												<td  align="center"><span style="color:black;"> - 1380  €</span></td>
												
											</tr>
											<tr>
												<td  align="left" width="40%"><span style="color:orange;">Extra costs</span></td>
												<td  align="center"><span style="color:black;"> + 1380  €</span></td>
												<td  align="center"></td>
												
											</tr>
											<tr>
												<th align="left" width="40%">Vat </th>
												<th align="center" colspan="2"><span style="color:black;">15%</span></th>
												
											</tr>	
											<tr>
												<th align="left" width="40%">Total to charge </th>
												<th align="center" colspan="2"><span style="color:black;">25875 €</span></th>
												
											</tr>											
										</tbody>
								</table>

								 </form>
							  
			
							</div> 
								
 
  
  
  </div>
  <!-- End Content Column -->
  <!-- Begin Right Column ->
  <div id="rightcolumn"></div>
  <!-- End Right Column -->
  <!-- Begin Footer ->
  <div id="footer"></div>
  <!-- End Footer -->
 </div>
<!-- End Wrapper -->