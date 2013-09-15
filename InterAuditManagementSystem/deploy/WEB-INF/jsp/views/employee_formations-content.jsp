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
							<li><a   href="${ctx}/showEmployeePropertiesPage.htm">Details</a></li>
							<!--li><a id="current" href="${ctx}/showEmployeeFormationsPage.htm">Formations</a></li-->
							</li><a  href="${ctx}/showEmployeeDocumentsPage.htm">Documents</a></li>

		</ul>
     </div>

	 <br/>

	 <div id="navcontainer">
       <ul id="navlist">
							<li><a href="${ctx}/showTrainingRegisterPage.htm">Add Training</a></li>

		</ul>
     </div>
  </div>
  <!-- End Left Column -->

  <!-- Begin Content Column -->
  <div id="content">


		 	<table id="hor-minimalist-b" summary="Employee Pay Sheet">
										<thead>
											<tr>
												<th scope="col">formation</th>
												<th scope="col">title</th>
												<th scope="col">description</th>
												<th scope="col">start.date</th>
												<th scope="col">end.date</th>
												<th scope="col">company</th>
												<th scope="col">actions</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td><span style="color:orange;">excel</span></td>
												<td>starting with excel</td>
												<td>How to do basic with excel sheet...</td>
												<td>20/12/2008</td>
												<td>20/12/2008</td>
												<td>microsoft</td>
												<td><a href="${ctx}/showTrainingRegisterPage.htm?id=${item.id}">
												<img src="images/Table-Edit.png" border="0" alt="Edit"/></a>
												
												<a href="#">
												<img src="images/Table-Delete.png" border="0" alt="Edit"/></a></td>
											</tr>
											<tr>
												<td><span style="color:orange;">powerpoint</span></td>
												<td>starting with powerpoint</td>
												<td>How to do basic with powerpoint sheet...</td>
												<td>20/12/2008</td>
												<td>20/12/2008</td>
												<td>microsoft</td>
												<td><a href="${ctx}/showTrainingRegisterPage.htm?id=${item.id}">
												<img src="images/Table-Edit.png" border="0" alt="Edit"/></a>
												
												<a href="#">
												<img src="images/Table-Delete.png" border="0" alt="Edit"/></a></td>
											</tr>
											<tr>
												<td><span style="color:orange;">word</span></td>
												<td>starting with word</td>
												<td>How to do basic with word sheet...</td>
												<td>20/12/2008</td>
												<td>20/12/2008</td>
												<td>microsoft</td>
												<td><a href="${ctx}/showTrainingRegisterPage.htm?id=${item.id}">
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
Training registration form</h1>
    <p>&nbsp;</p>

	<label>Training</label>
	<input style="width:260px;" type="text" name="textfield" id="textfield" value="excel" />

	<label>Title</label>
    <input style="width:260px;" type="text" name="textfield" id="textfield" value="starting with excel"/>

	<label>Description</label>
    <textarea rows="5" cols="40" name="textfield" id="textfield" /> How to do basic with excel sheet and more </textarea>

    <label>Start date</label>
    <input style="width:260px;" type="text" name="textfield" id="textfield" value="20/12/2008"/>
	
	<label>End date</label>
    <input style="width:260px;" type="text" name="textfield" id="textfield" value="20/12/2008"/>

    <label>Company</label>
    <input style="width:250px;" type="text" name="textfield" id="textfield" value="microsoft"/>

	<br/>
    <button type="submit">Register</button>
	
    <div class="spacer"></div>

<br/>
  </form>
  </div>
  </div>
  <!-- End Right Column -->
  <!-- Begin Footer ->
  <div id="footer">

   
  
  </div>
  <!-- End Footer -->
 </div>
<!-- End Wrapper -->