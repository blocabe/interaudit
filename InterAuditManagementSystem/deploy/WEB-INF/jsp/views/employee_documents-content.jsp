<jsp:include page="common_css.jsp"/>


<!-- Begin Wrapper -->
<div id="wrapper">

  <!-- Begin Header -->
  <div id="header"><h4><span style="color:#039;">Employee</span> : <span style="color:orange;"> Agnes Dupont</span></h4></div>
  <!-- End Header -->

  <!-- Begin Left Column -->
  <div id="leftcolumn"> 
	<div id="navcontainer">
       <ul id="navlist">
                                <!-- CSS Tabs -->
							<li><a   href="${ctx}/showEmployeePropertiesPage.htm">Details</a></li>
							</li><a  href="${ctx}/showEmployeeFormationsPage.htm">Formations</a></li>
							</li><a id="current" href="${ctx}/showEmployeeDocumentsPage.htm">Documents</a></li>

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
												<td>A.Dupont</td>
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
												<td>A.Dupont</td>
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
												<td>A.Dupont</td>
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
												<td>A.Dupont</td>
												<td><a href="${ctx}/showDocumentRegisterPage.htm?id=${item.id}">
												<img src="images/Table-Edit.png" border="0" alt="Edit"/></a>
												
												<a href="#">
												<img src="images/Table-Delete.png" border="0" alt="Edit"/></a></td>
											</tr>
											<tr>
												<td><span style="color:orange;">client.doc</span></td>
												<td>2MB</td>
												<td>liste...</td>
												<td>Inter-Audit...</td>
												<td>20/12/2008</td>
												<td>A.Dupont</td>
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
												<td>A.Dupont</td>
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
												<td>A.Dupont</td>
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
												<td>A.Dupont</td>
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

	<label>Title</label>
    <input style="width:260px;" type="text" name="textfield" id="textfield" value="contract cdi" />

    <label>Description
        
    </label>
    <textarea rows="5" cols="40" name="textfield" id="textfield" /> The contract for A.Dupont </textarea>


	<label>Category
        
    </label>
    
	<select style="float:left;width:200px;">
									  <option>diploma</option>
									  <option>certification</option>
									  <option selected>contract</option>
									  <option>other</option>
	</select>
	

    <label>Document
    
    </label>
    <input style="width:250px;" type="file" name="textfield" id="textfield" value="contract.doc" />

	<br/>
    <button type="submit">Register</button>
	
    <div class="spacer"></div>

<br/>
  </form>
</div>
  
  </div>
  <!-- End Right Column -->
  <!-- Begin Footer ->
  <div id="footer"></div>
  <!-- End Footer -->
 </div>
<!-- End Wrapper -->