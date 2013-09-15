
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles"  uri="/WEB-INF/struts-tiles.tld" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<html>
<head>

<fmt:setLocale value="fr" scope="session"/>

<title>Login to Inter Audit Management System</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="IAMS">
	<meta http-equiv="description" content="Inter Audit Management System">
</head>


<body background="images/base2.jpg" style="background-repeat:no-repeat;background-position:120 15;" >

  <div style="margin-left: 450px;margin-top: 300px;" >
	<form method="POST">
      
      <table >
        <tr>
          <td style="font-size : 11px;">Login: </td>
          <td>
          
          	<spring:bind path="user.login" >              
              <input style="font-size : 11px;"	type="text" 
			           		name="${status.expression}" 
			           		value="${status.value}"
			           		size="20" 
			           		maxlength="50" >
        	</spring:bind>
          	<span><font color="red"><strong>*</strong></font></span>			
			           		
	           				
	      </td>
        </tr>
        <tr>
          <td style="font-size : 11px;">Password:</td>
          <td>
          
          	<spring:bind path="user.password">
          		<input style="font-size : 11px;" type="password" name="${status.expression}" value="${status.value}" size="22" maxlength="50"/>
          		
        	</spring:bind>
        	<span><font color="red"><strong>*</strong></font></span>
        	</td>
        </tr>
		<tr>
		 
		  <td colspan="2" align="right">
		  <input style="font:11px Verdana, sans-serif;" type="submit" name="Submit" onclick="return monitor()" value="Please login">
		  </td>
		</tr>
        
        <tr>
         <td style="font-size : 11px; color : #FF6600; " >
         	
   			
   			<spring:bind path="user.*">
	          <c:if test="${not empty status.errorMessages}">
	            <c:forEach var="error" items="${status.errorMessages}">
	              <font color="red"><c:out value="${error}" escapeXml="false" /></font>
	              <br />
	            </c:forEach>
	          </c:if>
        	</spring:bind> <!-- status messages -->
          
	       	<c:if test="${not empty message}">
	         	<font color="green"><c:out value="${message}" escapeXml="false"/></font>
	         	<c:set var="message" value="" scope="session" />
	       	</c:if>
         </td>
         </tr>         
        
      </table>
    </form>
 </div>
</body>
</html>