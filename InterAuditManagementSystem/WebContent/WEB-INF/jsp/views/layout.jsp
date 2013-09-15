<jsp:directive.page contentType="text/html;charset=UTF-8" />
<%@ taglib prefix="tab" uri="http://ditchnet.org/jsp-tabs-taglib"%>


<%@ taglib prefix="layout" uri="http://www.sourceforge.net/springLayout"%>
<%@ taglib prefix="tiles" uri="/WEB-INF/struts-tiles.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
request.setAttribute("ctx", request.getContextPath()); 
%>

<head>
 <meta equiv="Content-Type" content="text/html;charset=UTF-8">
<title>Inter-Audit Management System</title>
	<link href="css/odbb-skin.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="${ctx}/script/build/fonts/fonts-min.css" />
	<link rel="stylesheet" type="text/css" href="${ctx}/script/build/button/assets/skins/sam/button.css" />
	<link rel="stylesheet" type="text/css" href="${ctx}/script/build/container/assets/skins/sam/container.css"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/script/build/fonts/fonts-min.css" />
	<link rel="stylesheet" type="text/css" href="${ctx}/script/build/autocomplete/assets/skins/sam/autocomplete.css" />

	<script type="text/javascript" src="${ctx}/script/build/utilities/utilities.js"></script>
	<script type="text/javascript" src="${ctx}/script/build/button/button-min.js"></script>
	<script type="text/javascript" src="${ctx}/script/build/container/container-min.js"></script>
	<script type="text/javascript" src="${ctx}/script/build/yahoo/yahoo-min.js"></script>
	<script type="text/javascript" src="${ctx}/script/build/event/event-min.js"></script>
	<script type="text/javascript" src="${ctx}/script/build/connection/connection-min.js"></script>
	<script type="text/javascript" src="${ctx}/script/build/json/json-min.js"></script>
	
	<script type="text/javascript" src="${ctx}/script/build/yahoo-dom-event/yahoo-dom-event.js"></script>
	<script type="text/javascript" src="${ctx}/script/build/animation/animation-min.js"></script>
	<script type="text/javascript" src="${ctx}/script/datasource/datasource-min.js"></script>
	<script type="text/javascript" src="${ctx}/script/build/autocomplete/autocomplete-min.js"></script>
	
	<link href="${ctx}/css/messageHandler.css" rel="stylesheet" type="text/css" />
	<script src="${ctx}/script/messageHandler.js"></script>
	<script type="text/javascript" src="${ctx}/script/build/editor/simpleeditor-min.js"></script>
	<script type="text/javascript" src="${ctx}/script/build/element/element-min.js"></script>
<script type="text/javascript" src="${ctx}/script/build/container/container-min.js"></script>
<script type="text/javascript" src="${ctx}/script/build/container/container_core-min.js"></script>
<script type="text/javascript" src="${ctx}/script/build/menu/menu-min.js"></script>
<script type="text/javascript" src="${ctx}/script/build/button/button-min.js"></script>


	



	<style type="text/css">
        /*
        * OK sous IE8, C2 et F3,
        * se positionne au-dessus du dialogue de z-index 1000,
        * se positionne juste en dessous de la ligne d'input.
        * Sous F3, si la fenêtre Firebug est ouverte
        * le datepicker remonte sur le dialog par manque de place, normal.
        */
        div#ui-datepicker-div {
            z-index: 4000;
        }
    </style>

		<style type="text/css">
		.info2, .success2, .warning2, .error2, .validation2 {  
			border: 1px solid;  
			margin: 10px 0px;  
			padding:10px 5px 10px 55px;  
			background-repeat: no-repeat;  
			background-position: 10px center;
		}  
		.info2 {  
			color: #00529B;  
			background-color: #BDE5F8;  
			background-image: url('images/info.png');  
		}  
		.success2 {  
			color: #4F8A10;  
			background-color: #DFF2BF;  
			background-image:url('images/success.png');  
		}  
		.warning2 {  
			color: #9F6000;  
			background-color: #FEEFB3;  
			background-image: url('images/warning.png');  
		}  
		.error2 {  
			color: #D8000C;  
			background-color: #FFBABA;  
			background-image: url('../images/error.png');  
		}  
		.validation2 {  
			color: #D63301;  
			background-color: #FFCCBA;  
			background-image: url('images/validation.png');  
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
		</style>
	
	


</head>

<body class="yui-skin-sam">
     <!-- START OF HEADER PART  
   <div id="ODBB_HEADER"> 
				<a href="${ctx}/homePage.htm"><img src="images/bannieres/ban.base1.jpg" alt="IAMS" border="0"></a>
	</div>
	--> 
	
	<!-- END OF HEADER PART -->  

	<!-- START OF MENU PART --> 
	<jsp:include page="horizontal_menu.jsp"/>
	<!-- END OF MENU PART -->  

	<div>
	<br/>
			<!-- START OF CONTENT PART-->
			<tiles:insert attribute="content" ignore="true" />
	</div>

	 <!--div id="ODBB_HEADER"> 
				<a href="${ctx}/homePage.htm"><img src="images/bannieres/ban.base1.jpg" alt="IAMS" border="0"></a>
	</div-->


 <!-- END OF CONTENT PART -->  

	<!-- START OF FOOTER PART -->  
  <%--jsp:include page="footer.jsp"/--%>
  <!-- END OF FOOTER PART -->  
</body>
