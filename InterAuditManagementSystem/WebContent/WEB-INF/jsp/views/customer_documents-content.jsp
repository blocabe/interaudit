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
		</li><a id="current"  href="${ctx}/showCustomerDocumentsPage.htm">Documents</a></li>
		</li><a  href="${ctx}/showCustomerContractsPage.htm">Contracts</a></li>
		</li><a  href="${ctx}/showCustomerContactsPage.htm">Contacts</a></li>

		</ul>
     </div>

	 <br/>

	 <div id="navcontainer">
       <ul id="navlist">
							<li><a href="${ctx}/showDocumentRegisterPage.htm?id=${item.id}">Add Document</a></li>

		</ul>
     </div>
  </div>
  <!-- End Left Column -->

  <!-- Begin Content Column -->
  <div id="content">


		 	<table id="hor-minimalist-b" summary="Employee Pay Sheet">
										<thead>
											<tr>
												<th scope="col">file</th>
												<th scope="col">size</th>
												<th scope="col">title</th>
												<th scope="col">description</th>
												<th scope="col">reg.date</th>
												<th scope="col">owner</th>
												<th scope="col">actions</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td><span style="color:orange;">client.doc</span></td>
												<td>2MB</td>
												<td>liste...</td>
												<td>Inter-Audit...</td>
												<td>20/12/2008</td>
												<td>E.dupont</td>
												<td><a href="${ctx}/showDocumentRegisterPage.htm?id=${item.id}">
												<img src="images/Table-Edit.png" border="0" alt="Edit"/></a>
												
												<a href="#">
												<img src="images/Table-Delete.png" border="0" alt="Edit"/></a></td>
											</tr>
											<tr>
												<td><span style="color:orange;">contrat.doc</span></td>
												<td>2MB</td>
												<td>liste...</td>
												<td>Inter-Audit...</td>
												<td>20/12/2008</td>
												<td>E.dupont</td>
												<td><a href="${ctx}/showDocumentRegisterPage.htm?id=${item.id}">
												<img src="images/Table-Edit.png" border="0" alt="Edit"/></a>
												
												<a href="#">
												<img src="images/Table-Delete.png" border="0" alt="Edit"/></a></td>
											</tr>
											<tr>
												<td><span style="color:orange;">design.doc</span></td>
												<td>2MB</td>
												<td>liste...</td>
												<td>Inter-Audit...</td>
												<td>20/12/2008</td>
												<td>E.dupont</td>
												<td><a href="${ctx}/showDocumentRegisterPage.htm?id=${item.id}">
												<img src="images/Table-Edit.png" border="0" alt="Edit"/></a>
												
												<a href="#">
												<img src="images/Table-Delete.png" border="0" alt="Edit"/></a></td>
											</tr>
											<tr>
												<td><span style="color:orange;">analyse.doc</span></td>
												<td>2MB</td>
												<td>liste...</td>
												<td>Inter-Audit...</td>
												<td>20/12/2008</td>
												<td>E.dupont</td>
												<td><a href="${ctx}/showDocumentRegisterPage.htm?id=${item.id}">
												<img src="images/Table-Edit.png" border="0" alt="Edit"/></a>
												
												<a href="#">
												<img src="images/Table-Delete.png" border="0" alt="Edit"/></a></td>
											</tr>
											
										</tbody>
									</table>					
  
  
  
  </div>
  <!-- End Content Column -->
  <!-- Begin Right Column ->
  <div id="rightcolumn">
  <div id="basic" class="myform">
  <form id="form1" name="form1" method="post" action="">
    <h1>
Document registration form</h1>
    <p>&nbsp;</p>

	<label>Title
        
    </label>
    <input style="width:260px;" type="text" name="textfield" id="textfield" />

    <label>Description
        
    </label>
    <textarea rows="5" cols="40" name="textfield" id="textfield" /> Document description... </textarea>
	

    <label>Document
    
    </label>
    <input style="width:250px;" type="file" name="textfield" id="textfield" />

	<br/>
    <button type="submit">Register</button>
	
    <div class="spacer"></div>
	<br/>
  </form>
</div>
  </div>
  <!-- End Right Column -->
  <!-- Begin Footer 
  <div id="footer"></div>
  <!-- End Footer -->
 </div>
<!-- End Wrapper -->