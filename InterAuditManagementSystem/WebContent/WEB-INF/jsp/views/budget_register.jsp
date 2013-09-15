<%@ page contentType="text/html"%>
<%@ taglib prefix="tab" uri="http://ditchnet.org/jsp-tabs-taglib"%>
<%@ taglib prefix="layout" uri="http://www.sourceforge.net/springLayout"%>
<%@ taglib prefix="tiles" uri="/WEB-INF/struts-tiles.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>



<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Register customer budget</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link href="css/odbb-skin.css" rel="stylesheet" type="text/css" />

	<link rel="stylesheet" type="text/css" media="all" href="css/niceforms-default.css" />
</head>

<%
request.setAttribute("ctx", request.getContextPath()); 
%>


<body>
 <!-- START OF HEADER PART -->  
   <div id="ODBB_HEADER"> 
				<a href="${ctx}/homePage.htm"><img src="images/bannieres/ban.base1.jpg" alt="IAMS" border="0"></a>
	</div>

	<!-- START OF MENU PART --> 
	 <jsp:include page="horizontal_menu.jsp"/>
	<!-- END OF MENU PART -->  

<!-- START OF CONTENTS PART --> 
 <div ID="ODBB_MAIN_START" style="background-color: rgb(248, 246, 233);">
	<br/>
	<jsp:include page="budget_register-content.jsp"/>
</div>
<!-- END OF CONTENTS PART -->



<!-- START OF FOOTER PART-->  
  <jsp:include page="footer.jsp"/>
  <!-- END OF FOOTER PART -->  
</body>
</html>



