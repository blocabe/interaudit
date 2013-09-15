<jsp:include page="common_css.jsp"/>

<!-- Begin Wrapper -->
<div id="wrapper">

  <!-- Begin Header -->
  <div id="header"><h4>Customer : <span style="color:orange;">  LuxLait</span></h4>
  
  <c:if test="${not empty actionErrors}">
	<div style="width: 60%">
	<div class="validation"><c:forEach var="actionError"
		items="${actionErrors}">
		<li><c:out value="${actionError}" escapeXml="false" /></li>
	</c:forEach></div>
	</div>
	<c:set var="actionErrors" value="" scope="session" />
</c:if>
<c:if test="${not empty successMessage}">
	<div style="width: 60%; align: center">
	<div class="success"><c:forEach var="message"
		items="${successMessage}">
		<li><c:out value="${message}" escapeXml="false" /></li>
	</c:forEach></div>
	</div>
	<c:set var="successMessage" value="" scope="session" />
</c:if>
  </div>
  <!-- End Header -->

  <!-- Begin Left Column -->
  <div id="leftcolumn"> 
	<div id="navcontainer">
       <ul id="navlist">
                                <!-- CSS Tabs -->
		<li><a  href="${ctx}/showCustomerPropertiesPage.htm">Proprietes</a></li>
		<!--/li><a   href="${ctx}/showCustomerDocumentsPage.htm">Documents</a></li-->
		</li><a id="current" href="${ctx}/showCustomerContractsPage.htm">Contracts</a></li>
		</li><a  href="${ctx}/showCustomerContactsPage.htm">Contacts</a></li>

		</ul>
     </div>
	 <br/>

	 <div id="navcontainer">
       <ul id="navlist">
							<li><a href="${ctx}/showContractRegisterPage.htm?id=${item.id}">Add Contract</a></li>

		</ul>
     </div>
  </div>
  <!-- End Left Column -->

  <!-- Begin Content Column -->
  <div id="content">



		 	<table id="hor-minimalist-b" summary="Employee Pay Sheet">
										<thead>
											<tr>
												<th scope="col">reference</th>
												<th scope="col">description</th>
												<th scope="col">fromDate</th>
												<th scope="col">toDate</th>
												<th scope="col">amount</th>
												<th scope="col">agreement</th>
												<th scope="col">actions</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td><span style="color:orange;">ref 2007</span></td>
												<td>contract desc...</td>
												<td>20/12/2008</td>
												<td>20/12/2018</td>
												<td>10.000</td>
												<td>yes</td>
												<td>
												<a href="${ctx}/showContractRegisterPage.htm?id=${item.id}">
												<img src="images/Table-Edit.png" border="0" alt="Edit"/></a>
												<a href="#">
												<img src="images/Table-Delete.png" border="0" alt="Edit"/></a></td>
											</tr>
											<tr>
												<td><span style="color:orange;">ref 2008</span></td>
												<td>contract desc...</td>
												<td>20/12/2008</td>
												<td>20/12/2018</td>
												<td>20.000</td>
												<td>no</td>
												<td>
												<a href="${ctx}/showContractRegisterPage.htm?id=${item.id}">
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