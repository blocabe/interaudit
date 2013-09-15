<jsp:directive.page contentType="text/html;charset=UTF-8" />
<%@ taglib prefix="tab" uri="http://ditchnet.org/jsp-tabs-taglib"%>
<%@ taglib prefix="layout" uri="http://www.sourceforge.net/springLayout"%>
<%@ taglib prefix="tiles" uri="/WEB-INF/struts-tiles.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>



<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>Register invoice</title>	
	<link href="css/odbb-skin.css" rel="stylesheet" type="text/css" />

	<!--link rel="stylesheet" type="text/css" media="all" href="css/niceforms-default.css" /-->

	
</head>

<%
request.setAttribute("ctx", request.getContextPath()); 
%>


<body class="yui-skin-sam">
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
	<jsp:include page="select_mission_invoice-content.jsp"/>
	<br/>
</div>
<!-- END OF CONTENTS PART -->



<!-- START OF FOOTER PART-->  
  <jsp:include page="footer.jsp"/>
  <!-- END OF FOOTER PART -->  
</body>


</html>



