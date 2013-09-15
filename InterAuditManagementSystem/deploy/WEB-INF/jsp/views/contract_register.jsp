<%@ page contentType="text/html"%>
<%@ taglib prefix="tab" uri="http://ditchnet.org/jsp-tabs-taglib"%>
<%@ taglib prefix="layout" uri="http://www.sourceforge.net/springLayout"%>
<%@ taglib prefix="tiles" uri="/WEB-INF/struts-tiles.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>



<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Register invoice</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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
	<br/>

<%--div id="ODBB_MENU"> 	
			<jsp:include page="menu.jsp"/>
			<img src="images/page2_1_cubes.jpg" alt="IAMS" height="300" width="250" border="0">
		</div--%>

<!-- START OF CONTENTS PART --> 
 <div style="font-size: .7em;background-color: #F8F6E9;">
	<br/>
	
	
	<jsp:include page="contract_register-content.jsp"/>
	<br/>
</div>
<!-- END OF CONTENTS PART -->



<!-- START OF FOOTER PART-->  
  <jsp:include page="footer.jsp"/>
  <!-- END OF FOOTER PART -->  
</body>


<script type="text/javascript">
YAHOO.example.ItemSelectHandler = function() {
    // Use a LocalDataSource
  var oDS = new YAHOO.util.XHRDataSource('${ctx}/editCustomersAsAjaxStream.htm'); 
  oDS.responseType = YAHOO.util.XHRDataSource.TYPE_JSON;   
 // Define the schema of the JSON results   
 oDS.responseSchema = {   
 resultsList : "events",   
 fields : ["name","id"]   
 };   

   
    // Instantiate the AutoComplete
    var oAC = new YAHOO.widget.AutoComplete("customer", "myContainer", oDS);
    oAC.resultTypeList = false;
	oAC.applyLocalFilter = true;
	oAC.queryDelay = .5;
    
    // Define an event handler to populate a hidden form field
    // when an item gets selected
	
    var missionId = YAHOO.util.Dom.get("missionId");
    var myHandler = function(sType, aArgs) {
        var myAC = aArgs[0]; // reference back to the AC instance
        var elLI = aArgs[1]; // reference to the selected LI element
        var oData = aArgs[2]; // object literal of selected item's result data
        
        // update hidden form field with the selected item's ID
        missionId.value = oData.id;
    };
    oAC.itemSelectEvent.subscribe(myHandler);
    
    
    return {
        oDS: oDS,
        oAC: oAC
    };
}();


</script>


</html>



