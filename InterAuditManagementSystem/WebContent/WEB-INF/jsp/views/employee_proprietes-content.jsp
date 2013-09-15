<jsp:include page="common_css.jsp"/>


<!-- Begin Wrapper -->
<div id="wrapper">

  <!-- Begin Header -->
  <div id="header"><h3><span style="color:#039;">Employee </span>: <span style="color:orange;"> Agnes Dupont</span></h3></div>
  <!-- End Header -->

  <!-- Begin Left Column -->
  <div id="leftcolumn"> 
	<div id="navcontainer">
       <ul id="navlist">
                                <!-- CSS Tabs -->
							<li><a   id="current" href="${ctx}/showEmployeePropertiesPage.htm">Details</a></li>
							<!--li><a  href="${ctx}/showEmployeeFormationsPage.htm">Formations</a></li-->
							</li><a  href="${ctx}/showEmployeeDocumentsPage.htm">Documents</a></li>

		</ul>
     </div>
  </div>
  <!-- End Left Column -->

  <!-- Begin Content Column -->
  <div id="content">


	 <form id="form1" name="form1" method="post" action="">
			<table id="hor-minimalist-b">
			<tr>
				<td align="left" width="40%" >Lastname  </td>
				<td align="center"><span style="color:orange;">Dupont</span></td>
			</tr>

			<tr>
				<td align="left" width="40%" >Firstname  </td>
				<td align="center"><span style="color:orange;">Agnes</span></td>
			</tr>

			<tr>
				<td align="left" width="40%" >Email </td>
				<td align="center"><span style="color:orange;">2000</span></td>
			</tr>

			<tr>
				<td align="left" width="40%" >Role </td>
				<td align="center"><span style="color:orange;">Assistant</span></td>
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
				<td align="left" width="40%" >Status </td>
				<td align="center"><span style="color:green;">Active</span></td>
			</tr>
						
			</table>					
		</form> 
		 	
  <!--div id="basic" class="myform" style="background-color: white;">
   
							  <form id="form1" name="form1" method="post" action="">
								
								<h1><span style="color:orange;"> Agnes Dupont</span></h1>
								<p></p>
								
								<label>Username</label>
								<input type="text" name="textfield" id="textfield" value="ADupont" />

								<label>Lastname</label>
								<input type="text" name="textfield" id="textfield" value="Dupont" />
								
								<label>Firstname</label>
								<input type="text" name="textfield" id="textfield" value="Agnes" />

								<label>Email</label>
								<input type="text" name="textfield" id="textfield" value="Agnes.dupont@wanadoo.fr" />
								
								<label for="fromdate">Pro.Telephone</label>
								<input type="text" name="fromdate" id="fromdate" value="2046587896"/>
								
								<label for="todate">Mobile</label>
								<input type="text" name="todate" id="todate" value="204654"/>

								<label>Role</label>
								<select>
									  <option>Manager</option>
									  <option selected>Assistant</option>
									  <option>Partner</option>	
								</select> 
								

								<label for="todate">Is Active</label>
								<input type="checkbox" name="hours_spents" id="hours_spents" checked/>
   
								<label for="todate">Password</label>
								<input type="password" name="psswd" id="psswd" value="204654"/>

								<label for="todate">Confirm Password</label>
								<input type="password" name="confpsswd" id="confpsswd" value="204654"/>
								
								
								<div class="spacer"></div> 
								<hr/>  
				  				<button  type="cancel" style="margin-left:100px;">Cancel</button>
								<button  type="submit" style="margin-left:160px;">&nbspSave&nbsp</button>
								
							  </form-->
							</div> 
  
  
  </div>
  <!-- End Content Column -->
  <!-- Begin Right Column ->
  <div id="rightcolumn"></div>
  <!-- End Right Column -->
  <!-- Begin Footer 
  <div id="footer"> This is the Footer </div>
  <!-- End Footer -->
 </div>
<!-- End Wrapper -->