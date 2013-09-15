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
		<li><a  href="${ctx}/showCustomerPropertiesPage.htm">Proprietes</a></li>
		<!--/li><a  href="${ctx}/showCustomerDocumentsPage.htm">Documents</a></li-->
		</li><a  href="${ctx}/showCustomerContractsPage.htm">Contracts</a></li>
		</li><a id="current"  href="${ctx}/showCustomerContactsPage.htm">Contacts</a></li>

		</ul>
     </div>

	 <br/>

	 <div id="navcontainer">
       <ul id="navlist">
							<li><a   href="#">Add Contrat</a></li>

		</ul>
     </div>
  </div>
  <!-- End Left Column -->

  <!-- Begin Content Column -->
  <div id="content">


		 	<table id="hor-minimalist-b" summary="Employee Pay Sheet">
										<thead>
											<tr>
												<th scope="col">Title</th>
												<th scope="col">Name</th>
												<th scope="col">Job title</th>
												<th scope="col">email</th>
												<th scope="col">phone</th>
												<th scope="col">actions</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td><span style="color:orange;">Mr</span></td>
												<td>Dupont jean</td>
												<td>Senior engineer</td>
												<td>Dupont.jean@orange.fr</td>
												<td>+352 4410122133</td>
												<td><a href="${ctx}/showContactRegisterPage.htm?id=${item.id}">
												<img src="images/Table-Edit.png" border="0" alt="Edit"/></a>
												<a href="#">
												<img src="images/Table-Delete.png" border="0" alt="Edit"/></a></td>
											</tr>
											<tr>
												<td><span style="color:orange;">Mr</span></td>
												<td>Dupont jean</td>
												<td>Senior engineer</td>
												<td>Dupont.jean@orange.fr</td>
												<td>+352 4410122133</td>
												<td><a href="${ctx}/showContactRegisterPage.htm?id=${item.id}">
												<img src="images/Table-Edit.png" border="0" alt="Edit"/></a>
												<a href="#">
												<img src="images/Table-Delete.png" border="0" alt="Edit"/></a></td>
											</tr>
											<tr>
												<td><span style="color:orange;">Mr</span></td>
												<td>Dupont jean</td>
												<td>Senior engineer</td>
												<td>Dupont.jean@orange.fr</td>
												<td>+352 4410122133</td>
												<td><a href="${ctx}/showContactRegisterPage.htm?id=${item.id}">
												<img src="images/Table-Edit.png" border="0" alt="Edit"/></a>
												<a href="#">
												<img src="images/Table-Delete.png" border="0" alt="Edit"/></a></td>
											</tr>
											<tr>
												<td><span style="color:orange;">Mr</span></td>
												<td>Dupont jean</td>
												<td>Senior engineer</td>
												<td>Dupont.jean@orange.fr</td>
												<td>+352 4410122133</td>
												<td><a href="${ctx}/showContactRegisterPage.htm?id=${item.id}">
												<img src="images/Table-Edit.png" border="0" alt="Edit"/></a>
												<a href="#">
												<img src="images/Table-Delete.png" border="0" alt="Edit"/></a></td>
											</tr>
											
										</tbody>
									</table>					
  
  
  
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