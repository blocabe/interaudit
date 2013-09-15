
<jsp:directive.page contentType="text/html;charset=UTF-8" />
<%@ taglib prefix="tab" uri="http://ditchnet.org/jsp-tabs-taglib"%>
<%@ taglib prefix="layout" uri="http://www.sourceforge.net/springLayout"%>
<%@ taglib prefix="tiles" uri="/WEB-INF/struts-tiles.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib  prefix="fmt"    uri="http://java.sun.com/jstl/fmt"%>

<%
request.setAttribute("ctx", request.getContextPath()); 
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>Edit employee  ${employee.fullName} </title>
	<!--link href="css/global_style.css" rel="stylesheet" type="text/css" /-->
	<link href="css/HelpSupport.css" rel="stylesheet" type="text/css" />
	<link href="css/odbb-skin.css" rel="stylesheet" type="text/css" />

	<script type="text/javascript" src="script/scriptaculous/prototype.js"></script>
	<script type="text/javascript" src="script/scriptaculous/scriptaculous.js"></script>
</head>

<style type="text/css"> 
#ta { 
   padding: 5px; 
   height: 350px; 
   width: 500px; 
   /*border: medium inset #CCCCCC; */
   font-family: "Courier New", Courier, mono; 
   font-size: 12px; 
} 
</style> 

<style>
    .yui-editor-container {
        position: absolute;
        top: -9999px;
        left: -9999px;
        z-index: 999;
    }
    #editor {
        visibility: hidden;
        position: absolute;
    }
    .editable {
        border: 1px solid black;
        margin: .25em;
        float: left;
        width: 350px;
        height: 50px;
        overflow: auto;
    }
</style>

<style type="text/css">
        /*
        * OK sous IE8, C2 et F3,
        * se positionne au-dessus du dialogue de z-index 1000,
        * se positionne juste en dessous de la ligne d'input.
        * Sous F3, si la fen�tre Firebug est ouverte
        * le datepicker remonte sur le dialog par manque de place, normal.
        */
        div#ui-datepicker-div {
            z-index: 4000;
        }

		input.button120 {
	width:120px;
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

#global {   margin-left: auto;   margin-right: auto;   width: 700px;   text-align: left; /* on r�tablit l'alignement normal du texte */ }
.global2 {   margin-left: auto;   margin-right: auto;   width: 300px;   text-align: left; /* on r�tablit l'alignement normal du texte */ }
    </style>

 






<script>

function editEmployee() {
    var id = document.listForm.id.value;    
    var url ="${ctx}/editEmployeePage.htm?employeeId=";
    window.location = url + id;
   }
  function cancelForm()
  {
      window.location="${ctx}/homePage.htm";
  }

  function approveInvoice(id)
  {
      window.location="${ctx}/approveInvoice.htm?invoiceId=" +id;
  }

  function markAsSentInvoice(id)
  {
      window.location="${ctx}/markAsSentInvoice.htm?invoiceId=" +id;
  }


 function previewInvoice(id)
  {
      window.location="http://localhost:8090/birt/run?__report=facture_report.rptdesign&__format=pdf&invoiceIdentifier="+id;
  }
  
</script>





<script>
	var newWindow = null;
	  function cancelForm()
	  {
		  window.location="${ctx}/homePage.htm";
	  }

	function showInvoiceInfos(id){
	  if (newWindow != null){newWindow.close()}
        newWindow = window.showModalDialog('${ctx}/showInvoiceInfos.htm?customerid='+id,'top',"dialogWidth:1000px;dialogHeight:1000px");      
        newWindow.focus();
      } 

	  function removeAttachmentAction(code)
		 {
					if (confirm('Do you really want to remove this entry?'))
					{
						with(document.removeFeeForm)
			 			{
							document.forms["removeFeeForm"].elements["feecode"].value = code;
							var url = "${ctx}/showInvoiceRegisterPage.htm?customerid=${param['customerid']}&type=${invoice.type}&id=${param['id']}&command=removeFee&feecode="+code;
							action.value=url;
							submit();
		 				 }
		 	  }
		 }

	  function addPayment()
		 {
			var url = "${ctx}/showPaymentRegisterPage.htm?invoiceid=${invoice.id}";
			window.location=url;
		 }

		function createReminder()
		 {
			var url = "${ctx}/showInvoiceReminderRegisterPage.htm?invoiceid=${invoice.id}";
			window.location=url;
		 }

</script>








<body class="yui-skin-sam">
   <div id="ODBB_HEADER"> 
				<a href="${ctx}/homePage.htm"><img src="images/bannieres/ban.base1.jpg" alt="IAMS" border="0"></a>
	</div>
	 <jsp:include page="horizontal_menu.jsp"/>
	
 <div style="font-size: .7em;background-color: #F8F6E9;">
<br/>	
<h2></h2>
<div class="nav_alphabet" style="background-color: rgb(248, 246, 233); border: 1px solid  #0066aa; text-align:center; padding-top:1pt; font: bold 12px Verdana, sans-serif;">
        <form name="listForm" action="" method="post" >                 
                        <span style="font:10px Verdana, sans-serif;margin-right:10pt;"><strong>Employe</strong> :</span>
                        <select style="font:10px Verdana, sans-serif;margin-right:10pt;" name="id" id="id" onchange="editEmployee();">                  
                              <c:forEach var="y" items="${allEmployeeOptions}">
                                <option value="${y.id}"<c:if test='${employee.id==y.id}'> selected</c:if>>${fn:substring(fn:toUpperCase(y.name), 0, 19)}</option>
                              </c:forEach>
                        </select>                                   
        </form>
    </div>
<br/>
<div id="global">

	<table cellpadding="3" cellspacing="2" border="0" class="axial" style="margin-left: auto;   margin-right: auto;   width: 700px;   text-align: left;">

	<tr>
		<td colspan="2" align="center">
				<span  style="color:blue;font-weight: bold;">
						 ${employee.fullName} 						
				</span>
		</td>
	</tr>

	<tr>
      <th>
		LastName
	  </th>
      <td>
        <span  style="color:black;font-weight: bold;">${employee.lastName}</span>
      </td>
    </tr>

	<tr>
      <th>
		First Name
	  </th>
      <td>
		<span  style="color:black;font-weight: bold;">${employee.firstName}</span>
      </td>
    </tr>

	<tr>
      <th>Code</th>
      <td>
	  <span  style="color:black;font-weight: bold;">${employee.code}</span>
         
      </td>
    </tr>

	<tr>
      <th>Position</th>
      <td>
	  <span  style="color:black;font-weight: bold;">${employee.position.name}</span>
         
      </td>
    </tr>

	<tr>
      <th>Email</th>
      <td>
	  <span  style="color:black;font-weight: bold;">${employee.email}</span>
         
      </td>
    </tr>

	<tr>
      <th>Actif</th>
      <td>
	 
	  <span  style="color:black;font-weight: bold;">${employee.userActive}</span>
	  
	 
         
      </td>
    </tr>

	<tr>
      <th>Date engagement </th>
      <td>
	  <span  style="color:black;font-weight: bold;">
	  
	  <c:choose>
			<c:when test='${employee.dateOfHiring != null }'>													
				${employee.dateOfHiring}
			</c:when>
            <c:when test='${employee.dateOfHiring == null }'>                                                  
                ---
            </c:when>											
		</c:choose> 
	</span>
      </td>
    </tr>


	
	

	


	<tr>
     
	  <td colspan="2" align="center">
				<input style="width:100px;
				" type="button" name="cancel" id="cancel" class="button120"  value="Cancel" onclick="cancelForm()"/>
				<%--
						<input style="width:100px;
				" type="button" name="preview" id="preview" class="button120"  value="Print" onclick="previewInvoice(${invoice.id})" />
				
				<c:if test='${invoice.approved eq false}'>  
						<input style="width:100px;
				" type="button" name="approve" id="approve" class="button120"  value="Approve"   onclick="approveInvoice(${invoice.id})"/>
				</c:if>

				<c:if test='${invoice.approved eq true  &&  invoice.sent eq false}'>  
					<input style="width:100px;
					" type="button" name="markAsSent" id="markAsSent" class="button120"  value="Mark as sent"   onclick="markAsSentInvoice(${invoice.id})"/>
				</c:if>
				
				<c:if test='${invoice.status eq "sent" && invoice.type != "CN"}'>
	                <input style="width:100px;" type="button" name="update"  class="button120"  value="Add payment" onclick="addPayment();"/>
	                <!--input style="width:100px;" type="button" name="update"  class="button120"  value="Create reminder" onclick="createReminder();"/-->
                </c:if>
					--%>
				
			</td>
      
    </tr>

	
	
	</table>




</div>

<h2></h2>
<br/>

</div>




</body>
</html>





