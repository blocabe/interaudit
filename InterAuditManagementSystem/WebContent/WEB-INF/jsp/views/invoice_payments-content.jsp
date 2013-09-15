<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="t"      uri="/tags/tooltips-tiles" %>
<jsp:include page="common_css.jsp"/>

<!-- Begin Wrapper -->
<div id="wrapper">

  <!-- Begin Header -->
  <div id="header"><h4><span style="color:#039;">Facture</span> : <span style="color:orange;">${viewInvoiceDetails.reference}</span> </h4></div>
  <!-- End Header -->

  <!-- Begin Left Column -->
  <div id="leftcolumn"> 
	<div id="navcontainer">
       <ul id="navlist">
		<li><a  href="${ctx}/showAdvancePropertyPage.htm?id=${viewInvoiceDetails.id}">Details</a></li>
		<li><a id="current"  href="${ctx}/showInvoicePaymentPage.htm">Payments</a></li>
		<li><a  href="${ctx}/showInvoiceReminderPage.htm">Rappels</a>							
		<%--li><a  href="${ctx}/showInvoiceDocumentsPage.htm">Documents</a></li--%>
		</ul>
     </div>

	 <%--br/>

	 <div id="navcontainer">
       <ul id="navlist">
							<li><a href="${ctx}/showPaymentRegisterPage.htm?id=${item.id}">Add Payment</a></li>

		</ul>
     </div--%>
  </div>
  <!-- End Left Column -->

  <!-- Begin Content Column -->
  <div id="content">


		<table id="hor-minimalist-b" summary="Employee Pay Sheet">
										<thead>
											<tr>
												<th align="center">reference</th>
												<th align="center">bank</th>
												<th align="center">amount</th>
												<th align="center">currency</th>
												<th align="center">payment date</th>
												<%--th align="center">actions</th--%>
											</tr>
										</thead>
										<tbody>

											<c:set var="total" value="0"/>
											<c:forEach var="item" items="${viewInvoiceDetails.payments}" varStatus="loop">

											<tr>
											
												<td align="center"><a href="${ctx}/showPaymentRegisterPage.htm?id=${item.id}"><span style="color:orange;">${item.reference}</span></a></td>
												<td align="center">${item.bankCode}</td>
												<td align="center">${item.amount}</td>
												<td align="center">${item.currencyCode}</td>
												<td align="center">${item.paymentDate}</td>
												<%--td align="center">
													<a href="#"><img src="images/Table-Delete.png" border="0" alt="Edit"/></a>
												</td--%>
											</tr>
											
											<c:set var="total" value="${total + item.amount}"/>
											</c:forEach>
											
											<tr>
											
												<td align="center" colspan="2">Total</td>
												<td align="center">${total}</td>
												<td align="center" colspan="2"></td>
											</tr>
										</tbody>
						</table>						
  
  
  
  </div>
  <!-- End Content Column -->
  <!-- Begin Right Column ->
  <div id="rightcolumn"> 

	<div id="basic" class="myform">
  <form id="form1" name="form1" method="post" action="">
    <h1>Payment registration form</h1>
    <p>&nbsp;</p>
    <label>Bank
        
    </label>
    
	<select style="float:left;width:200px;">
									  <option>BNP</option>
									  <option>BGL</option>
									  <option selected>SG</option>	
	</select>



    
    <label>Amount
    
    </label>
    <input type="text" name="textfield" id="textfield" />


	 <label>Currency
        
    </label>
    
	<select style="width:200px;">
									  <option selected>EURO</option>
									  <option>DOLLAR</option>
	</select>
    
    <label>Date of payment
        
    </label>
    <input type="text" name="textfield" id="textfield" />
	
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