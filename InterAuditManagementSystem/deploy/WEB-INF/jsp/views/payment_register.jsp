﻿<%@ page contentType="text/html"%>
<%@ taglib prefix="tab" uri="http://ditchnet.org/jsp-tabs-taglib"%>
<%@ taglib prefix="layout" uri="http://www.sourceforge.net/springLayout"%>
<%@ taglib prefix="tiles" uri="/WEB-INF/struts-tiles.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>



<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Register payment</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link href="css/odbb-skin.css" rel="stylesheet" type="text/css" />

	<!--link rel="stylesheet" type="text/css" media="all" href="css/niceforms-default.css" /-->
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
	

<%--div id="ODBB_MENU"> 	
			<jsp:include page="menu.jsp"/>
			<img src="images/page2_1_cubes.jpg" alt="IAMS" height="300" width="250" border="0">
		</div--%>

<!-- START OF CONTENTS PART --> 
 <div style="font-size: .7em;background-color: #F8F6E9;">
	<br/>
	<jsp:include page="payment_register-content.jsp"/>
	<br/>
	 <jsp:include page="footer.jsp"/>
</div>
<!-- END OF CONTENTS PART -->



<!-- START OF FOOTER PART-->  
 
  <!-- END OF FOOTER PART -->  
</body>
</html>



