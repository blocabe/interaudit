<jsp:include page="common_css.jsp"/>

<!-- Begin Wrapper -->
<div id="wrapper">

  <!-- Begin Header -->
  <div id="header"><h4>Customer : <span style="color:orange;">  LuxLait</span></h4></div>
  <!-- End Header -->

  <!-- Begin Left Column -->
  <div id="leftcolumn"> 
	<div id="navcontainer">
       <ul id="navlist">
                                <!-- CSS Tabs -->
		<li><a id="current" href="${ctx}/showCustomerPropertiesPage.htm">Proprietes</a></li>
		<!--/li><a  href="${ctx}/showCustomerDocumentsPage.htm">Documents</a></li-->
		</li><a  href="${ctx}/showCustomerContractsPage.htm">Contracts</a></li>
		</li><a  href="${ctx}/showCustomerContactsPage.htm">Contacts</a></li>

		</ul>
     </div>
  </div>
  <!-- End Left Column -->

  <!-- Begin Content Column -->
  <div id="content">

	<div id="basic" class="myform" style="background-color: white;">
		<form id="form1" name="form1" method="post" action="">
			<table id="hor-minimalist-b">
			<tr>
				<td align="left" width="40%" >Name  </td>
				<td align="center"><span style="color:orange;">Luxlait</span></td>
			</tr>

			<tr>
				<td align="left" width="40%" >Code  </td>
				<td align="center"><span style="color:orange;">LXLT</span></td>
			</tr>

			<tr>
				<td align="left" width="40%" >Contact person </td>
				<td align="center"><span style="color:orange;">Eric Dupont</span></td>
			</tr>

			<tr>
				<td align="left" width="40%" >Email </td>
				<td align="center"><span style="color:orange;">luxlait@wanadoo.fr</span></td>
			</tr>

			<tr>
				<td align="left" width="40%" >Pro.Telephone </td>
				<td align="center"><span style="color:orange;">4410122020</span></td>
			</tr>

			<tr>
				<td align="left" width="40%" >Mobile </td>
				<td align="center"><span style="color:orange;">0698784578</span></td>
			</tr>

			<tr>
				<td align="left" width="40%" >Shipping Address </td>
				<td align="center"><span style="color:green;">11 Rue du Grand Duc L-1618 Luxembourg</span></td>
			</tr>

			<tr>
				<td align="left" width="40%" >Billing Address </td>
				<td align="center"><span style="color:green;">11 Rue du Grand Duc L-1618 Luxembourg</span></td>
			</tr>

			<tr>
				<td align="left" width="40%" >Associate </td>
				<td align="center"><span style="color:green;">EK</span></td>
			</tr>

			<tr>
				<td align="left" width="40%" >Manager </td>
				<td align="center"><span style="color:green;">LM</span></td>
			</tr>
						
			</table>					
		</form> 


		
   
							  <!--form id="form1" name="form1" method="post" action="">
								
								<h1><span style="color:orange;">  </span><span style="color:orange;">  LuxLait</span></h1>
								<p></p>
								
								<label>Code</label>
								<input type="text" name="textfield" id="textfield" value="LXLT"/>

								<label>Name</label>
								<input type="text" name="textfield" id="textfield" value="LuxLait" />
								
								<label>Contact Name</label>
								<input type="text" name="textfield" id="textfield" value="Eric Dupont" />

								<label>Email</label>
								<input type="text" name="textfield" id="textfield" value="luxlait@wanadoo.fr" />

								<label>Shipping Address</label>
								<textarea rows=6 >11 Rue du Grand Duc L-1618 Luxembourg</textarea>

								<label>Billing Address</label>
								<textarea rows=6 >11 Rue du Grand Duc L-1618 Luxembourg</textarea>

								

								<label for="fromdate12">Pro.Telephone</label>
								<input type="text" name="fromdate12" id="fromdate12" value="2046587896"/>
								
								<label for="todate12">Mobile</label>
								<input type="text" name="todate12" id="todate12" value="204654"/>

								<label>Associate</label>
								<select>
									  <option>EK</option>
									  <option selected>VD</option>
								</select> 

								<label>Manager</label>
								<select>
									  <option>LM</option>
									  <option selected>LM</option>
								</select> 
								

								<label for="todate">Is Active</label>
								<input type="checkbox" name="hours_spents" id="hours_spents" checked/>
   
								
								<!--label for="fromdate">Contract start date</label>
								<input type="text" name="fromdate" id="fromdate" />
								<img id="fromDateShow" src="images/calbtn.gif" width="16" height="16" alt="Calendar" onclick="fnCallback2( this,'fromDateShow','fromdate');">
								
								
								<label for="todate">Contract end date</label>
								<input type="text" name="todate" id="todate"/>
								<img id="toDateShow" src="images/calbtn.gif" width="16" height="16" alt="Calendar" onclick="fnCallback2( this,'toDateShow','todate');">


								<label for="todate">Has Contract agreement</label>
								<input type="checkbox" name="hours_spents" id="hours_spents" checked/>
   

								<label for="todate">Max amount agreement</label>
								<input type="text" name="hours_spents" id="hours_spents" value="10 000€"/->
   
								
								<div class="spacer"></div> 
								<hr/>  
				  				<button type="cancel">Cancel</button>&nbsp&nbsp
								<button  type="submit">Save</button>
								
							  </form-->
							</div>  						
  
  
  
  </div>
  <!-- End Content Column -->
  <!-- Begin Right Column ->
  <div id="rightcolumn"> Right Column </div>
  <!-- End Right Column -->
  <!-- Begin Footer 
  <div id="footer"></div>
  <!-- End Footer -->
 </div>
<!-- End Wrapper -->