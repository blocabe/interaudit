<jsp:directive.page contentType="text/html;charset=UTF-8" />
<%@ taglib prefix="tab" uri="http://ditchnet.org/jsp-tabs-taglib"%>
<%@ taglib prefix="layout" uri="http://www.sourceforge.net/springLayout"%>
<%@ taglib prefix="tiles" uri="/WEB-INF/struts-tiles.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib  prefix="fmt"    uri="http://java.sun.com/jstl/fmt"%>
<%@ taglib uri="/tags/interaudit" prefix="interaudit" %>


<%
request.setAttribute("ctx", request.getContextPath()); 
%>
<script type="text/javascript" src="${ctx}/script/jquery-1.2.3.pack.js"></script>
<script type="text/javascript" src="${ctx}/script/jquery-1.6.1.js"></script>
<link href="${ctx}/css/messageHandler.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/script/messageHandler.js"></script>

﻿<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<link href="css/odbb-skin.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" href="style2.css" type="text/css" charset="utf-8" />
	<title>Inter Audit Managment System</title>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />

	<SCRIPT LANGUAGE="JavaScript">
	jours = new Array('Dimanche', 'Lundi', 'Mardi', 'Mercredi', 'Jeudi', 'Vendredi', 'Samedi'); 
	lesmois = new Array('Janvier', 'Février', 'Mars', 'Avril', 'Mai', 'Juin', 'Juillet','Août','Septembre','Octobre','Novembre','Décembre'); 
	Today = new Date();
	Jour = Today.getDate();
	Mois = (Today.getMonth())+1;
	Annee = Today.getFullYear();

	Heure = Today.getHours(); 
	Minute = Today.getMinutes();
	Seconde = Today.getSeconds();

	Message = jours[Today.getDay()] + " "  + Jour +" " +lesmois[Today.getMonth()] + " " + Annee + "  " + Heure + "h " + Minute + "m " + Seconde + "s";
</SCRIPT>


<script type="text/javascript">
  
$(document).ready(function()  {
  <c:if test='${currentExercise.status eq "CLOSED"}'> 	
          showMessage("Aucun budget actif défini... certaines fonctionnalités peuvent être inutilisables..","warning");                    
    
  </c:if>   
  
//first slide down and blink the alert box
  $("#object").animate({ 
      top: "0px"
    }, 2000 ).fadeOut(100).fadeIn(100).fadeOut(100).fadeIn(100).fadeOut(100).fadeIn(100).fadeOut(100).fadeIn(100);
 
 //close the message box when the cross image is clicked 
 $("#close_message").click(function()
  {
     $("#object").fadeOut("slow");
  });
});  
</script>

<script>
   
  function sendReminderTimesheet()
  {
	var answer = confirm("Do you really want to send emails remider for timesheets?")
    if (answer){
      window.location="${ctx}/sendTimesheetReminderEmails.htm";
	}
     
  }
  
  function sendReminderInvoices()
  {
	var answer = confirm("Do you really want to send emails remider for invoices?")
    if (answer){
      window.location="${ctx}/sendInvoicesReminderEmails.htm";
	}
     
  }
</script>
</head>


<style>
*{margin:0;padding:0;}
body{background:#1a1a1a;color:#444;font:76% Verdana,Helvetica,sans-serif;}
p{line-height:1.7em;margin-bottom:15px;}
a{color:#467aa7;}
a:hover{color:#333;}
h1{color:#2a5a7a;font-family:Georgia,serif;font-size:2.4em;}
h2{color:#467aa7;font-family:Georgia,serif;font-size:2.2em;font-weight:400;margin:0 0 10px 0;}
h3{color:#467aa7;font-family:Georgia,serif;font-size:1.6em;font-weight:400;margin:0 0 8px;}
ul,ol,dl{margin:0 0 20px 20px; padding:0;}
li{padding-bottom:5px;}

#containerfull,#container980,#container760,#container600{margin:0 auto;padding:0;overflow:hidden;border-left:1px solid #ddd;border-right:1px solid #ddd;}
#containerfull{width:100%;border:0;}
#container980{width:980px;}
#container760{width:760px;}
#container600{width:600px;}

#header{background-color:#467aa7;margin:0 auto;padding:10px 50px;}
#header h1{margin:0;padding:0px 0 0px;}
#header h1 a{color:#eee;font-family:Georgia,serif;font-size:2em;font-weight:400;text-decoration:none;}
#header h2{color:#ddd;padding:0 0 10px 25px;margin:0;font-family:Georgia,serif;font-size:1.8em;font-weight:400;}

#menu{background-color:#467aa7;height:40px;}
#menu ul{float:right;height:40px;list-style:none;margin:0;padding:0 25px 0 0;}
#menu ul li{background-color:#2a5a7a;display:block;float:left;margin:0 0 0 5px;padding:0;}
#menu ul li a{background-color:#2a5a7a;color:#ddd;display:block;font-size:1.4em;padding:10px 12px;text-decoration:none;}
#menu ul li a:hover{color:#fff;}
#menu ul li a.current{background-color:#eee;color:#333;border-bottom:1px solid #eee;}

#feature{background-color:#eee;color:#555;padding:25px 25px 5px;border-top:1px solid #ccc;border-bottom:1px solid #ccc;}
#feature h2{color:#467aa7;font-size:2em;line-height:1em;}
#feature p{font-size:1.3em;margin:0 0 15px 0; line-height:1.5em;}

#main{clear:both;margin:0;background-color:#fff;padding:25px 25px 5px;}
#content{width:72%;}

#sidebar{background:transparent url('images/sidebar-top.jpg') no-repeat scroll top left;float:right;width:25%;}
#sidebar .sidebarbox {margin:0 0 25px 0;background-color:#fff; border:1px solid #467aa7;}
#sidebar p{margin:10px 0 10px 10px;}
#sidebar ul{list-style:none;margin:10px 0 10px 10px;padding:0;}
#sidebar ul li{margin:0 0 5px 0;}
#sidebar ul li a{color:#467aa7;font-weight:400;}
#sidebar ul.sidemenu {margin:0;}
#sidebar ul.sidemenu li{display:inline;padding:0;margin:0;}
#sidebar ul.sidemenu li a{display:block;padding:7px 5px 6px 10px;font-size:1.2em; font-weight:400;text-decoration:none;border-bottom:1px solid #ddd;}
#sidebar ul.sidemenu li a:hover{background-color:#eee;color:#333;border-bottom:1px solid #ccc;}
#sidebar ul.sidemenu ul{margin:0;padding:0;font-size:0.9em;border-bottom:1px solid #ccc;}
#sidebar ul.sidemenu ul li a{padding:5px 5px 5px 25px;border:0;font-weight:400;}
#sidebar ul.sidemenu ul li a:hover{border:0;}
#sidebar ul li a:hover{color:#333;}
#sidebar h2{background-color:#467aa7;color:#fff;font-size:1.5em;margin:0 0 0 0;padding:10px;}
#sidebar h3{font-size:1.4em; padding:10px 10px 0 10px;}

#footer{background-color:#ddd;margin:0 auto;padding:20px 25px 10px;border-top:1px solid #ccc;}
#footer h2{color:#467aa7;font-weight:400;font-size:1.5em;}
#footer p{color:#555;margin:0 0 10px 0;padding:0;}
#footer ul{border-top:1px solid #ccc;list-style:none;margin:0 0 15px 0;padding:0;}
#footer ul li{padding:0;margin:0;}
#footer ul li a{border-bottom:1px solid #ccc;color:#333;display:block;padding:7px 10px;text-decoration:none;}
#footer ul li a:hover{background-color:#eee;}

#footersections{display:block;margin:0 auto;}
#footersections .half{float:left;margin:0 3% 0 0;width:47%;}
#footersections .lasthalf{float:left;margin:0;width:50%;}
#footersections .quarter{float:left;margin:0 3% 0 0;width:22%;}
#footersections .lastquarter{float:left;width:25%;margin:0;}

#credits{background-color:#333;color:#aaa;padding:15px 25px;}
#credits p{text-align:left;font-size:1.2em;margin:0;padding:0;line-height:1.4em;}
#credits a{color:#aaa;font-weight:400;}

.left{float:left;width:49%;}
.right{float:right;width:48%;}
.clear{clear:both;visibility:hidden;}
.small{font-size:0.8em;}

.message{
    border:1px solid #CCCCCC;
    position:absolute;
    width:150px;
    border:1px solid #c93;
    background:#ffc;
    padding:5px;
    left:950px;
    top : -170px;
}
/*
#main { background: #FFF; width: 1000px;color: #808080; font-size: .7em;}  
#main .right_side { float: left;
	padding:0px 0px 0 10px; margin:0;
	background-color: #FFF;
	width: 220px; 
}

#main .right_side .hitems { margin: 0; 	padding: 0; }
#main .right_side .hitems ul { 
	margin: 5px 0 5px 0; 
	padding : 0; 
	color: #a90000;
	list-style-image: url(arrow.gif);
}
	
#main .right_side .hitems li { 
	margin: 0 0 2px 20px;
	padding: 0 0 0 0px;
	color: #555;
	 
}

#main .left_side { float: left; width: 500px; background: #FFF; padding:15px 15px 0 20px; margin:0; }
#main h3 {  font: 85% Arial, Sans-Serif; margin: 0 0 10px 0px; padding: 0; color: #5f5f5f; background: inherit; 
			border-bottom: 1px solid #036CB4;
}

#main .box  {	background: #efefef; padding: 5px; border: 1px solid #ccc;}

#main .right_side ul { 
	margin: 5px 0 5px 0; 
	padding : 0; 
	list-style : none; 
	border-bottom: 0px solid #eee; 
	list-style-type: square;
	color: #a90000;
}
	
#main .right_side li { 
	margin: 0 0 2px 15px;
	padding: 0 0 0 0px;
	color: #555;
}

#main .right_side .padding {
margin: 0 0 20px 15px;
	padding: 0 0 0 0px;
	color: #555;
}
*/
</style>




<body style="background:#ffc;">

		<%--div id="ODBB_HEADER"> 
			<a href="${ctx}/homePage.htm"><img src="images/bannieres/ban.base1.jpg" alt="IAMS" border="0"></a>
		</div>
		<div id="ODBB_MENU"> 	
			<jsp:include page="menu.jsp"/>
			<img src="images/page2_1_cubes.jpg" alt="IAMS" height="250" width="250" border="0">
		</div--%>
		<!--div>
		
		
		
		
		<!-- starting-->

		<!--div id="main" style="background-image: url('http://epp.eurostat.ec.europa.eu/portal/page/portal/PGP_ADM_IMAGES/rss/countriesBg2.jpg');"-->
		<!--div id="main" style="background-image: url('images/background/fd.base1.jpg');">
		
			<span  style="color:black;font-weight: bold;">
						<c:choose>
							<c:when test='${currentExercise != null }'>
							 EXERCICE : <span  style="color:red;font-weight: bold;"> ${currentExercise.year}  ${currentExercise.status}</span> 
							</c:when>
							
							<c:when test='${currentExercise == null }'>
							 <span  style="color:red;font-weight: bold;"> No Exercise started </span> 
							</c:when>
					</c:choose>
			

			<span style="color:blue ; ">${context.currentUser.fullName}</span>&nbsp;[<span style="color:red ; ">${context.currentUser.position.name}</span> ] &nbsp; connected on :
			<span style="color:blue ; ">
			<SCRIPT LANGUAGE="JavaScript">
			document.write(Message);
			</SCRIPT>
			</span>
			

			
			<span style="color:black;font-size:smaller;">&copy; InterAudit. Version ${build_version}</span> 
			
			<div class="left_side">
			  <h2><a href="http://www.interaudit.lu/">Interaudit</a></h2>
			  <h3>NOTRE METIER</h3>
			  
			  <span style="color:black;">
				Soci&eacute;t&eacute; luxembourgeoise cr&eacute;&eacute;e en 1988, Interaudit est sp&eacute;cialis&eacute;e dans l'audit d'&eacute;tats financiers.
				Interaudit est membre de l'Institut des R&eacute;viseurs d'Entreprises (IRE) et de l'Ordre des Experts-comptables au Luxembourg (OEC).<br/>
				Depuis presque 20 ans, Interaudit a connu une croissance continuelle de ses activit&eacute;s et s'est d&eacute;velopp&eacute;e sur le march&eacute; national et international en int&eacute;grant notamment le r&eacute;seau "Fidunion International".<br/>Ce r&eacute;seau regroupe des cabinets et soci&eacute;t&eacute;s de r&eacute;viseurs d'entreprises et d'expertise comptable, pr&eacute;sents dans la plupart des pays europ&eacute;ens, mais aussi en Afrique, au Moyen-Orient, en Am&eacute;rique et en Asie (en coop&eacute;ration avec un groupement d'Extr&ecirc;me-Orient).<br/>
				Nous croyons en des valeurs s&ucirc;res : ind&eacute;pendance, int&eacute;grit&eacute;, confiance, valeurs auxquelles nous sommes fid&egrave;les au quotidien dans toutes nos actions.<br/>Gr&acirc;ce &agrave; une structure &agrave; taille humaine, nous assurons une disponibilit&eacute; et une flexibilit&eacute; &agrave; nos clients face &agrave; leurs besoins.<br/>
				Notre &eacute;quipe polyglotte et exp&eacute;riment&eacute;e nous permet d'offrir des services toujours qualitatifs. Nous entretenons des relations fortes et p&eacute;rennes avec nos clients gr&acirc;ce &agrave; la stabilit&eacute; de notre &eacute;quipe.
			</span>




			  <h2><a href="#">I.A.M.S</a></h2>
			  <h3>I.A.M.S : UN SYSTEME QUI CENTRALISE L'INFORMATION...</h3>
			  <ul>
				  <li><a href="${ctx}/showAgendaPage.htm" title="Mon agenda" ><span style="font-size: 11px;">Mon Agenda</span></a></li>
				  <li><a href="${ctx}/showTimesheetRegisterPage.htm" title="Register timesheet " ><span style="font-size: 11px;">Ma Timesheet </span></a></li>
				  <li><a href="${ctx}/showMissionsPage.htm" title="Manage missions"><span style="font-size: 11px;">Mes missions</span></a></li>
				  <li><a href="${ctx}/showEmployeeRegisterPage.htm?id=${context.currentUser.id}" title="" ><span style="font-size: 11px;">Mon profile</span></a></li>
				</ul>			  
			  <br />
			</div>

			

			<div class="right_side">
			
			  <div class="nav">
				
				<h2>Nos services</h2>
				<ul>
				  <li><a href="http://www.interaudit.lu/index.php?id=5">Audit de comptes</a></li>
				  <li><a href="http://www.interaudit.lu/index.php?id=18">Mandat de commissaire</a></li>
				  <li><a href="http://www.interaudit.lu/index.php?id=17">Consolidation de comptes</a></li>
				  <li><a href="http://www.interaudit.lu/index.php?id=16">Conseil</a></li>
				  
				</ul>
				<br />
				<h2>Nos partenaires</h2>
				<ul>
				  <li> <a href="http://www.interfiduciaire.lu/">Inter FIDUCIAIRE S.A.</a></li>
				  <li> <a href="http://www.fortisintertrust.com/">Fortis intertrust Luxembourg S.A.</a></li>
				  <li> <a href="#">Signes S.A.</a></li>
				  <li> <a href="#">CHD Luxembourg S.A.</a></li>
				  <li> <a href="#">PME Xpertise</a></li>
				  <li> <a href="#">ASYRIS S.A.</a></li>
				</ul>
			  </div>
			  <br />
			  <h3>Contact</h3>
				<div class="padding">
				 <span style="color:blue;">
				  Interaudit, Réviseurs d'entreprises
					119, avenue de la Faïencerie
					L-1511 Luxembourg
					<br />
					T. +352 47 68 461
					<br />
					F. +352 47 47 72 .
					</span>
				</div>
			</div>
		</div-->

		
		<div id="containerfull">
			<div id="header" style="background:white;">
				<h1><a href="${ctx}/homePage.htm"><img src="images/bannieres/ban.base1.jpg" alt="IAMS" border="0"></a></h1>				
			</div>
		<div id="sidebar" style="float:left;width:15%;">
			<div class="sidebarbox">
               <h2>
				<!--a href="${ctx}/homePage.htm" title="Home Page" >
				   <span style="color: #ffe075;">Home Page</span>
				</a-->
				<a style="background-color:#467aa7;" href="${ctx}/logout.htm" title="Log out">
									<span style="color: #ffe075;"> Log out</span>
				</a>
				</h2>
				<ul class="sidemenu">
					<li>
						<a href="${ctx}/help.htm" title="Help">
							<span>User guide</span>
						</a>
					</li>
					
					<interaudit:atLeastOneAccessRightSet rights="CONSULT_RENTABILITE_CLIENTS,CONSULT_BUDGET,CONSULT_RESULTATS,CONSULT_FINANCIAL_DATA">
					<li ><a style="background-color:#467aa7;" href="#"><span style="color: white;"> Budget</span></a>
						<ul>
							<interaudit:accessRightSet right="CONSULT_BUDGET">
							<li><a href="${ctx}/showGeneralBudgetPageFromMenu.htm" title="Budget par client">
							<span style="font-size: 11px;">Budget général</span></a></li>
							</interaudit:accessRightSet>
							
							<interaudit:accessRightSet right="CONSULT_RENTABILITE_CLIENTS">
							<li><a href="${ctx}/showProfitabilityPerCustomerPage.htm" title="Rentabilit� par client">
							<span style="font-size: 11px;">Rentabilité par client</span></a></li>
							</interaudit:accessRightSet>
							
							<interaudit:accessRightSet right="CONSULT_RESULTATS">
							<li><a href="${ctx}/showBudgetResultsPage.htm" title="Resultats">
							<span style="font-size: 11px;">Objectifs & Resultats</span></a></li>
							</interaudit:accessRightSet>


							<interaudit:accessRightSet right="CONSULT_FINANCIAL_DATA">
							<li><a href="${ctx}/showEmployeesFinancialDataPage.htm" title="Financial data " ><span style="font-size: 11px;">Financial data</span></a></li>
							</interaudit:accessRightSet>
						</ul>
					</li>
					</interaudit:atLeastOneAccessRightSet>
					<interaudit:atLeastOneAccessRightSet rights="CONSULT_CALENDAR,CONSULT_GENERAL_PLANNING,CONSULT_MISSIONS">
					<li><a style="background-color:#467aa7;" href="#"><span style="color: white;"> Planning</span></a>
						<ul>
							<interaudit:accessRightSet right="CONSULT_GENERAL_PLANNING">
										<li><a href="${ctx}/showAnnualPlanningPage.htm" title="Manage general planning">
										<span style="font-size: 11px;">Planning </span></a></li>
										</interaudit:accessRightSet>
										
										<interaudit:accessRightSet right="CONSULT_MISSIONS">
										<li><a href="${ctx}/showMissionsPage.htm?FromMenu= true" title="Manage missions">
										<span style="font-size: 11px;">PlanWeek</span></a></li>
										</interaudit:accessRightSet>
										

										<interaudit:accessRightSet right="CONSULT_CALENDAR">
										<li><a href="${ctx}/showAgendaPage.htm" title="Manage tasks" >
										<span style="font-size: 11px;">Mon Agenda</span></a></li>
										</interaudit:accessRightSet>

										<!--li><a href="${ctx}/showMessageRecusPage.htm?id=${context.currentUser.id}" title="Messagerie" >
												<span style="font-size: 11px;">Ma messagerie</span></a></li-->
						</ul>
					</li>
					</interaudit:atLeastOneAccessRightSet>
					<interaudit:atLeastOneAccessRightSet rights="VALIDATE_TIMESHEET,REGISTER_TIMESHEET,CONSULT_TS_BOARD,CONSULT_TS_ATRAITER">
							<li><a style="background-color:#467aa7;" href="#"><span style="color: white;"> Timesheets</span></a>	
								<ul>

									<interaudit:accessRightSet right="CONSULT_TS_BOARD">
									<li><a href="${ctx}/showTimesheetGlobalSituationsPage.htm" title="Situation globale des timesheets " ><span style="font-size: 11px;">Tableau de bord</span></a></li>
									</interaudit:accessRightSet>

									<interaudit:accessRightSet right="REGISTER_TIMESHEET">
									<li><a href="${ctx}/showTimesheetRegisterPage.htm" title="Register timesheet " ><span style="font-size: 11px;">Ma Timesheet </span></a></li>
									</interaudit:accessRightSet>
				
									<interaudit:accessRightSet right="REGISTER_TIMESHEET">
									<li><a href="${ctx}/showTimesheetsPage.htm?type=personal" title="list of timesheets"><span style="font-size: 11px;">Mes  timesheets</span></a></li>
									</interaudit:accessRightSet>

									 <interaudit:atLeastOneAccessRightSet rights="VALIDATE_TIMESHEET,CONSULT_TS_ATRAITER">
									<li><a href="${ctx}/showTimesheetsPage.htm" title="list of timesheets"><span style="font-size: 11px;">A traiter</span></a></li>
									</interaudit:atLeastOneAccessRightSet>
									
								</ul>
							</li>
							</interaudit:atLeastOneAccessRightSet>
							<interaudit:atLeastOneAccessRightSet rights="CONSULT_INVOICE,CREATE_INVOICE">
								
									<li><a style="background-color:#467aa7;" href="#"><span style="color: white;"> Facturation</span></a>				
								<ul>	
											

												<interaudit:accessRightSet right="CREATE_INVOICE">
												<li><a href="${ctx}/selectCustomerForInvoicePage.htm" title="Register invoice">
												<span style="font-size: 11px;">Encoder une facture</span></a></li>
												</interaudit:accessRightSet>


												<interaudit:accessRightSet right="CREATE_INVOICE">
												<li><a href="${ctx}/selectCustomerForNoteCreditPage.htm" title="Register invoice">
												<span style="font-size: 11px;">Encoder une note de credit</span></a></li>
												</interaudit:accessRightSet>
												
												<interaudit:accessRightSet right="CREATE_INVOICE">
				                                    <li><a href="${ctx}/selectCustomerForInvoiceReminderPage.htm" title="Register invoice">
				                                    <span style="font-size: 11px;">
				                                    Encoder un rappel 
				                                    </span>
				                                    </a></li>
				                                    </interaudit:accessRightSet>

												<interaudit:accessRightSet right="CONSULT_INVOICE">
													<li><a href="${ctx}/showDraftInvoicesPage.htm" title="Search"><span style="font-size: 11px;">Factures en préparation</span></a></li>
												</interaudit:accessRightSet>

												<interaudit:accessRightSet right="CONSULT_INVOICE">
														<li><a href="${ctx}/showInvoicesPage.htm" title="Search"><span style="font-size: 11px;">
														Factures approuvées</span></a></li>
												</interaudit:accessRightSet>

												<interaudit:accessRightSet right="CONSULT_INVOICE">
												<li><a href="${ctx}/showPaymentsPage.htm" title="Find payments"><span style="font-size: 11px;">
												Payments</span></a></li>
												</interaudit:accessRightSet>

												<interaudit:accessRightSet right="CONSULT_INVOICE">
														<li><a href="${ctx}/showInvoicesReminderPage.htm" title="Search"><span style="font-size: 11px;">
														Rappels de facture</span></a></li>
												</interaudit:accessRightSet>
												
												<interaudit:accessRightSet right="CONSULT_BALANCE_AGEE">
                                                 <li><a href="${ctx}/showBalanceAgeePage.htm" title="Balance agee"><span style="font-size: 11px;">Balance agee</span></a></li>
                                                 </interaudit:accessRightSet>

									

										</ul>
									</a>
								</li>
								</interaudit:atLeastOneAccessRightSet>
							
								<%--interaudit:atLeastOneAccessRightSet rights="CONSULT_CONTRACT,CONSULT_USER,CONSULT_CUST,CONSULT_BANK,CONSULT_CONTACT,CONSULT_DECLARATION,CONSULT_CONTRACT">
								<li>
									<a href="#">Search</a>				
									<ul>		

											<interaudit:accessRightSet right="CONSULT_CONTRACT">
											<li><a href="${ctx}/showContractsPage.htm" title="Search">
											<span style="font-size: 11px;">Liste des contrats</span></a></li>
											</interaudit:accessRightSet>

											 <interaudit:accessRightSet right="CONSULT_CUST">
											 <li><a href="${ctx}/showCustomersPage.htm" title="Find customers " ><span style="font-size: 11px;">Liste des  clients</span></a></li>
											 </interaudit:accessRightSet>


											<interaudit:accessRightSet right="CONSULT_USER">
											 <li><a href="${ctx}/showEmployeesPage.htm" title="Find employees " ><span style="font-size: 11px;"> Liste des  employés</span></a></li>
											 </interaudit:accessRightSet>

											<interaudit:accessRightSet right="CONSULT_CONTACT">
											  <li><a href="${ctx}/showContactsPage.htm" title="Find contacts " ><span style="font-size: 11px;">Liste des  contacts</span></a></li>
											 </interaudit:accessRightSet>

											<interaudit:accessRightSet right="CONSULT_BANK">
											  <li><a href="${ctx}/showBanksPage.htm" title="Find banks" ><span style="font-size: 11px;">Liste des banques</span></a></li>
											 </interaudit:accessRightSet>

											<interaudit:accessRightSet right="CONSULT_DECLARATION">
											  <li><a href="${ctx}/showDeclarationsPage.htm" title="Find declarations" ><span style="font-size: 11px;">Liste des déclarations</span></a></li>
											</interaudit:accessRightSet>

											
									</ul>
								</li>
								</interaudit:atLeastOneAccessRightSet--%>
								<%--interaudit:atLeastOneAccessRightSet rights="REGISTER_CONTRACT,REGISTER_USER,REGISTER_CUST,REGISTER_BANK,REGISTER_CONTACT,REGISTER_DECLARATION,REGISTER_CONTRACT">
								<li><a href="#">New</a>
									<ul>
										<interaudit:accessRightSet right="REGISTER_USER">
															<li><a href="${ctx}/showEmployeeRegisterPage.htm" title="Register employee" >
															<span style="font-size: 11px;">Encoder un employé</span></a></li>
														 </interaudit:accessRightSet>

														 <interaudit:accessRightSet right="REGISTER_CUST">
														<li><a href="${ctx}/showCustomerRegisterPage.htm?command=new" title="Create user" class="standalone"><span style="font-size: 11px;">Encoder un client</span></a></li>
														</interaudit:accessRightSet>

														<interaudit:accessRightSet right="REGISTER_BANK">
														 <li><a href="${ctx}/showBankRegisterPage.htm" title="Register new bank" ><span style="font-size: 11px;">Encoder une banque</span></a></li>
														 </interaudit:accessRightSet>

														 
														  
														  <!--li><a href="${ctx}/showDocumentRegisterPage.htm" title="Register document " ><span style="font-size: 11px;">Document</span></a></li-->
														  
														 
														  
														  <interaudit:accessRightSet right="REGISTER_CONTACT">
														  <li><a href="${ctx}/showContactRegisterPage.htm" title="Register contact " ><span style="font-size: 11px;">Encoder un contact</span></a></li>
														  </interaudit:accessRightSet>
														  
														  <interaudit:accessRightSet right="REGISTER_DECLARATION">
														  <li><a href="${ctx}/showDeclarationRegisterPage.htm" title="Register declaration " ><span style="font-size: 11px;">Encoder une déclaration</span></a></li>
														  </interaudit:accessRightSet>
														  
														  <!--li><a href="${ctx}/showExpenseRegisterPage.htm" title="Register expense " ><span style="font-size: 11px;">Expense</span></a></li-->
														  
														  <interaudit:accessRightSet right="REGISTER_CONTRACT">
														  <li><a href="${ctx}/showContractRegisterPage.htm" title="Register contract " ><span style="font-size: 11px;">Encoder un contrat</span></a></li>
														  </interaudit:accessRightSet>							
									</ul>
								</li>
								</interaudit:atLeastOneAccessRightSet--%>
								<interaudit:atLeastOneAccessRightSet rights="MODIFY_ACCESS_RIGHTS">
								<li><a style="background-color:#467aa7;" href="#"><span style="color: white;"> Administration</span></a>				
									<ul>
											<interaudit:atLeastOneAccessRightSet rights="MODIFY_ACCESS_RIGHTS">
											 <li><a href="${ctx}/showUserAccessRightsPage.htm?type=BUDGET" title="Access rights " >
											 <span style="font-size: 11px;">Droits d'accès</span></a></li>											 
											</interaudit:atLeastOneAccessRightSet>
									</ul>
									
									<ul>
											<interaudit:atLeastOneAccessRightSet rights="MODIFY_ACCESS_RIGHTS">
											 <li><a href="#" onclick="sendReminderTimesheet()" title="Timesheet reminders " >
											 <span style="font-size: 11px;">Send Timesheet Reminders</span></a></li>											 
											</interaudit:atLeastOneAccessRightSet>
									</ul>
									<ul>
											<interaudit:atLeastOneAccessRightSet rights="MODIFY_ACCESS_RIGHTS">
											 <li><a href="#" onclick="sendReminderInvoices()" title="Invoice reminders " >
											 <span style="font-size: 11px;">Send Invoice Reminders</span></a></li>											 
											</interaudit:atLeastOneAccessRightSet>
									</ul>
									
									
								</li>
								</interaudit:atLeastOneAccessRightSet>
					<%--li><a style="background-color:#467aa7;" href="${ctx}/logout.htm" title="Log out">

									<span style="color: #ffe075;"> Log off</span></a></li--%>
				</ul>
			</div>

			<!--div class="sidebarbox">
				<h2>Text box</h2>
				<p>This is a sidebar text box. It can be used for regular links:</p>
				<ul>
					<li><a href="http://andreasviklund.com/templates/">More free templates</a></li>
					<li><a href="http://andreasviklund.com/blog/">Andreas' blog</a></li>
				</ul>
			</div-->
		</div>
		<div style="width:85%;float:right;">
		<div id="main">
			<div id="sidebar">
				<div class="sidebarbox">
				   <h2>Search</h2>
				   <ul class="sidemenu">		

											<interaudit:accessRightSet right="CONSULT_CONTRACT">
											<li><a href="${ctx}/showContractsPage.htm" title="Search">
											<span style="font-size: 11px;">Liste des contrats</span></a></li>
											</interaudit:accessRightSet>

											 <interaudit:accessRightSet right="CONSULT_CUST">
											 <li><a href="${ctx}/showCustomersPage.htm" title="Find customers " ><span style="font-size: 11px;">Liste des  clients</span></a></li>
											 </interaudit:accessRightSet>


											<interaudit:accessRightSet right="CONSULT_USER">
											 <li><a href="${ctx}/showEmployeesPage.htm" title="Find employees " ><span style="font-size: 11px;"> Liste des  employés</span></a></li>
											 </interaudit:accessRightSet>

											<interaudit:accessRightSet right="CONSULT_CONTACT">
											  <li><a href="${ctx}/showContactsPage.htm" title="Find contacts " ><span style="font-size: 11px;">Liste des  contacts</span></a></li>
											 </interaudit:accessRightSet>

											<interaudit:accessRightSet right="CONSULT_BANK">
											  <li><a href="${ctx}/showBanksPage.htm" title="Find banks" ><span style="font-size: 11px;">Liste des banques</span></a></li>
											 </interaudit:accessRightSet>

											<interaudit:accessRightSet right="CONSULT_DECLARATION">
											  <li><a href="${ctx}/showDeclarationsPage.htm" title="Find declarations" ><span style="font-size: 11px;">Liste des déclarations</span></a></li>
											</interaudit:accessRightSet>

											
									</ul>					
				</div>

				<div class="sidebarbox">
				   <h2>New</h2>
								<ul class="sidemenu">
										<interaudit:accessRightSet right="REGISTER_USER">
															<li><a href="${ctx}/showEmployeeRegisterPage.htm" title="Register employee" >
															<span style="font-size: 11px;">Encoder un employé</span></a></li>
														 </interaudit:accessRightSet>

														 <interaudit:accessRightSet right="REGISTER_CUST">
														<li><a href="${ctx}/showCustomerRegisterPage.htm?command=new" title="Create user" class="standalone"><span style="font-size: 11px;">Encoder un client</span></a></li>
														</interaudit:accessRightSet>

														<interaudit:accessRightSet right="REGISTER_BANK">
														 <li><a href="${ctx}/showBankRegisterPage.htm" title="Register new bank" ><span style="font-size: 11px;">Encoder une banque</span></a></li>
														 </interaudit:accessRightSet>

														 
														  
														  <!--li><a href="${ctx}/showDocumentRegisterPage.htm" title="Register document " ><span style="font-size: 11px;">Document</span></a></li-->
														  
														 
														  
														  <interaudit:accessRightSet right="REGISTER_CONTACT">
														  <li><a href="${ctx}/showContactRegisterPage.htm" title="Register contact " ><span style="font-size: 11px;">Encoder un contact</span></a></li>
														  </interaudit:accessRightSet>
														  
														  <interaudit:accessRightSet right="REGISTER_DECLARATION">
														  <li><a href="${ctx}/showDeclarationRegisterPage.htm" title="Register declaration " ><span style="font-size: 11px;">Encoder une déclaration</span></a></li>
														  </interaudit:accessRightSet>
														  
														  <!--li><a href="${ctx}/showExpenseRegisterPage.htm" title="Register expense " ><span style="font-size: 11px;">Expense</span></a></li-->
														  
														  <interaudit:accessRightSet right="REGISTER_CONTRACT">
														  <li><a href="${ctx}/showContractRegisterPage.htm" title="Register contract " ><span style="font-size: 11px;">Encoder un contrat</span></a></li>
														  </interaudit:accessRightSet>							
									</ul>				
				</div>

				<div class="sidebarbox">
					<h2>Contacts</h2>
					<p> 
				  Interaudit, Réviseurs d'entreprises
					119, avenue de la Faïencerie
					L-1511 Luxembourg
					<br/>
					T. +352 47 68 461
					<br/>
					F. +352 47 47 72 .
					</p>					
				</div>
			</div>

			

			<div id="content">
				<h3> <span style="color:blue ; ">${context.currentUser.fullName}</span>&nbsp;[<span style="color:red ; ">${context.currentUser.position.name}</span> ]</h3>
				<h2>Notre Metier</h2> 

				<p>Soci&eacute;t&eacute; luxembourgeoise cr&eacute;&eacute;e en 1988, Interaudit est sp&eacute;cialis&eacute;e dans l'audit d'&eacute;tats financiers.
					Interaudit est membre de l'Institut des R&eacute;viseurs d'Entreprises (IRE) et de l'Ordre des Experts-comptables au Luxembourg (OEC).
					Depuis presque 20 ans, Interaudit a connu une croissance continuelle de ses activit&eacute;s et s'est d&eacute;velopp&eacute;e sur le march&eacute; national et international en int&eacute;grant notamment le r&eacute;seau "Fidunion International".Ce r&eacute;seau regroupe des cabinets et soci&eacute;t&eacute;s de r&eacute;viseurs d'entreprises et d'expertise comptable, pr&eacute;sents dans la plupart des pays europ&eacute;ens, mais aussi en Afrique, au Moyen-Orient, en Am&eacute;rique et en Asie (en coop&eacute;ration avec un groupement d'Extr&ecirc;me-Orient).
					Nous croyons en des valeurs s&ucirc;res : ind&eacute;pendance, int&eacute;grit&eacute;, confiance, valeurs auxquelles nous sommes fid&egrave;les au quotidien dans toutes nos actions. Gr&acirc;ce &agrave; une structure &agrave; taille humaine, nous assurons une disponibilit&eacute; et une flexibilit&eacute; &agrave; nos clients face &agrave; leurs besoins.
					Notre &eacute;quipe polyglotte et exp&eacute;riment&eacute;e nous permet d'offrir des services toujours qualitatifs. Nous entretenons des relations fortes et p&eacute;rennes avec nos clients gr&acirc;ce &agrave; la stabilit&eacute; de notre &eacute;quipe..</p>

				<div class="left">			
					<h3>Nos services</h3>
					<p>
					<ul>
					  <li><a href="http://www.interaudit.lu/index.php?id=5">Audit de comptes</a></li>
					  <li><a href="http://www.interaudit.lu/index.php?id=18">Mandat de commissaire</a></li>
					  <li><a href="http://www.interaudit.lu/index.php?id=17">Consolidation de comptes</a></li>
					  <li><a href="http://www.interaudit.lu/index.php?id=16">Conseil</a></li>
					</ul>
					</p>					
				</div>

				<div class="right">
					<h3>Nos partenaires</h3>
					<p><ul>
				  <li> <a href="http://www.interfiduciaire.lu/">Inter FIDUCIAIRE S.A.</a></li>
				  <li> <a href="http://www.fortisintertrust.com/">Fortis intertrust Luxembourg S.A.</a></li>
				  <li> <a href="#">Signes S.A.</a></li>
				  <li> <a href="#">CHD Luxembourg S.A.</a></li>
				  <li> <a href="#">PME Xpertise</a></li>
				  <li> <a href="#">ASYRIS S.A.</a></li>
				</ul></p>
				</div>
				

				 <h2><a href="#">I.A.M.S</a></h2>
			  <h4>I.A.M.S : UN SYSTEME QUI CENTRALISE L'INFORMATION...</h4>
			  <ul>
				  <li><a href="${ctx}/showAgendaPage.htm" title="Mon agenda" ><span style="font-size: 11px;">Mon Agenda</span></a></li>
				  <li><a href="${ctx}/showTimesheetRegisterPage.htm" title="Register timesheet " ><span style="font-size: 11px;">Ma Timesheet </span></a></li>
				  <li><a href="${ctx}/showMissionsPage.htm" title="Manage missions"><span style="font-size: 11px;">Mes missions</span></a></li>
				  <li><a href="${ctx}/showEmployeeRegisterPage.htm?id=${context.currentUser.id}" title="" ><span style="font-size: 11px;">Mon profile</span></a></li>
				</ul>	
				
				<div class="clear">&nbsp;</div>
			</div>
			</div>


		<div class="clear">&nbsp;</div>
	</div>

	
	</div>


		

		<!-- ending-->

		
		
		
		
		
		<!--/div-->
	<div id="object" class="message"> 
	  <img id="close_message" style="float:right;cursor:pointer"  src="images/12-em-cross.png" />
	    <strong>Pensez à :</strong>
	  <hr/>
	  <p>
	  <ul>
	  <li><span style="color:blue ; font-size: 11px;"><a href="${ctx}/showAgendaPage.htm" title="Mon agenda" ><span style="color:blue ;">Gérer votre agenda...</span></a></span></li>
	  
	  <li><span style="color:blue ; font-size: 11px;"><a href="${ctx}/showTimesheetRegisterPage.htm" title="Register timesheet " ><span style="color:blue ;">Enregistrer votre timsesheet... </span></a></span></li>
	 
	  <c:if test='${context.currentUser.position.managingPosition == true}'> 	
		<li><span style="color:blue ; font-size: 11px;"><a href="${ctx}/showGeneralBudgetPageFromMenu.htm" title="Manage budgets " ><span style="color:blue ;">Gérer vos missions... </span></a></span></li>
	  </c:if> 
	  </ul>
	  </p>
    </div>
		
		
</body>

</html>