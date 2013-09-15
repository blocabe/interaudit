  <jsp:directive.page contentType="text/html;charset=UTF-8" />
<%@ taglib uri="/tags/interaudit" prefix="interaudit" %>

<style type="text/css">
	
	ul.zzul {list-style-type:none; display: block;}
	span.zzspace {left:11px;}
	a, a:visited {color: #0000ff;}
	a:hover { color: #ff0000;}

</style>


<script type="text/javascript">

function exp_coll(ind)

{
 s = document.getElementById("sp_" + ind);
 i = document.getElementById("im_" + ind);
 i2 = document.getElementById("im2_" + ind);
 if (s.style.display == 'none')
 {
   s.style.display = 'block';
   i.src = "../../images/minus.gif";
   i2.src = "../../images/folder-open.gif";
 }
 else if (s.style.display == 'block')
 {
   s.style.display = 'none';
   i.src = "../../images/plus.gif";
   i2.src = "../../images/folder.gif";
 }
}

function exp(ind)
{
 s = document.getElementById("sp_" + ind);
 i = document.getElementById("im_" + ind);
 i2 = document.getElementById("im2_" + ind);
 if (!(s && i && i2)) return false;
 s.style.display = 'block';
 i.src = "../../images/minus.gif";
 i2.src = "../../images/folder-open.gif";
}

function coll(ind)
{
 s = document.getElementById("sp_" + ind);
 i = document.getElementById("im_" + ind);
 i2 = document.getElementById("im2_" + ind);
 if (!(s && i && i2)) return false;
 s.style.display = 'none';
 i.src = "../../images/plus.gif";
 i2.src = "../../images/folder.gif";
}

</script>
<%
request.setAttribute("ctx", request.getContextPath()); 
%>
<table class="MENU" border="0" cellpadding="0" cellspacing="0">
					<tbody><tr><td >
						 <ul>

								<li>
									<a href="${ctx}/homePage.htm" title="Home Page" >
									   <span style="color: #ffe075;">Home Page</span>
									</a>
								</li>
								
								<li>
								    <a href="${ctx}/help.htm" title="Help">
								        <span style="color:#ffe075;">User guide</span>
								    </a>
								</li>
								

								<interaudit:atLeastOneAccessRightSet rights="REGISTER_CONTRACT,REGISTER_USER,REGISTER_CUST,REGISTER_BANK,REGISTER_CONTACT,REGISTER_DECLARATION,REGISTER_CONTRACT">
								<li><a  href="javascript:exp_coll(30);"><span style="color: #ffe075;">New</span></a>				
									<ul  class="zzul" id="sp_30" style="display:block;">
											
											<!--li><a href="${ctx}/showExerciseRegisterPage.htm" title="Create exercise " ><span style="font-size: 11px;">Exercise</span></a></li>

											<li><a href="${ctx}/showBudgetRegisterPage.htm" title="Create budgets " ><span style="font-size: 11px;">Budget</span></a></li-->

											

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
											  
											  <!--li><a href="${ctx}/showTrainingRegisterPage.htm" title="Register training " ><span style="font-size: 11px;">Training</span></a></li-->
											  
											  <!--li><a href="${ctx}/showReminderRegisterPage.htm" title="Register reminder " ><span style="font-size: 11px;">Reminder</span></a></li-->
											  
											  
									</ul>
								</li>
								</interaudit:atLeastOneAccessRightSet>


								<interaudit:atLeastOneAccessRightSet rights="CONSULT_RENTABILITE_CLIENTS,CONSULT_BUDGET,CONSULT_RESULTATS,CONSULT_FINANCIAL_DATA">
								<li><a href="javascript:exp_coll(0);"><span style="color: #ffe075;">Budget</span></a>			
									<ul class="zzul" id="sp_0" style="display:block;">			
											
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

											<!-->interaudit:accessRightSet right="CONSULT_RESULTATS">
											<li><a href="${ctx}/showMissionPropertiesPage.htm?id=6283" title="Details missions">
											<span style="font-size: 11px;">Missions</span></a></li>
											</interaudit:accessRightSet-->


										
									</ul>
								</li>	
								</interaudit:atLeastOneAccessRightSet>

							<interaudit:atLeastOneAccessRightSet rights="CONSULT_CALENDAR,CONSULT_GENERAL_PLANNING,CONSULT_MISSIONS">
							<li><a href="javascript:exp_coll(10);"><span style="color: #ffe075;">Planning</span></a>				
								<ul class="zzul" id="sp_10" style="display:block;">		
										
										<!--interaudit:accessRightSet right="CONSULT_PLANNING_MISSIONS">
										<li><a  href="${ctx}/showPlanningPage.htm" title="Manage planning">
										<span style="font-size: 11px;">Calendrier des missions</span></a></li>
										</interaudit:accessRightSet-->
										
										<interaudit:accessRightSet right="CONSULT_GENERAL_PLANNING">
										<li><a href="${ctx}/showAnnualPlanningPage.htm" title="Manage general planning">
										<span style="font-size: 11px;">Planning </span></a></li>
										</interaudit:accessRightSet>
										
										<interaudit:accessRightSet right="CONSULT_MISSIONS">
										<li><a href="${ctx}/showMissionsPage.htm" title="Manage missions">
										<span style="font-size: 11px;">PlanWeek</span></a></li>
										</interaudit:accessRightSet>
										

										<interaudit:accessRightSet right="CONSULT_CALENDAR">
										<li><a href="${ctx}/showAgendaPage.htm" title="Manage tasks" >
										<span style="font-size: 11px;">Mon Agenda</span></a></li>
										</interaudit:accessRightSet>

										<%--li><a href="${ctx}/showMessageRecusPage.htm?id=${context.currentUser.id}" title="Messagerie" >
												<span style="font-size: 11px;">Ma messagerie</span></a></li--%>

										
										
										<%--li><a href="${ctx}/showTasksPage.htm" title="Jobs In Process">
										<span style="font-size: 11px;">Activit�s</span></a></li--%>
										
										
								</ul>
							</li>
							</interaudit:atLeastOneAccessRightSet>

							<interaudit:atLeastOneAccessRightSet rights="VALIDATE_TIMESHEET,REGISTER_TIMESHEET">
							<li><a href="javascript:exp_coll(20);"><span style="color: #ffe075;"><span style="font-size: 11px;">Timesheets</span></a>	
								<ul class="zzul" id="sp_20" style="display:block;">

									<interaudit:accessRightSet right="VALIDATE_TIMESHEET">
									<li><a href="${ctx}/showTimesheetGlobalSituationsPage.htm" title="Situation globale des timesheets " ><span style="font-size: 11px;">Tableau de bord</span></a></li>
									</interaudit:accessRightSet>

									<interaudit:accessRightSet right="REGISTER_TIMESHEET">
									<li><a href="${ctx}/showTimesheetRegisterPage.htm" title="Register timesheet " ><span style="font-size: 11px;">Ma Timesheet </span></a></li>
									</interaudit:accessRightSet>
				
									<interaudit:accessRightSet right="REGISTER_TIMESHEET">
									<li><a href="${ctx}/showTimesheetsPage.htm?type=personal" title="list of timesheets"><span style="font-size: 11px;">Mes  timesheets</span></a></li>
									</interaudit:accessRightSet>

									 <interaudit:accessRightSet right="VALIDATE_TIMESHEET">
									<li><a href="${ctx}/showTimesheetsPage.htm" title="list of timesheets"><span style="font-size: 11px;">A traiter</span></a></li>
									</interaudit:accessRightSet>
									
								</ul>
							</li>
							</interaudit:atLeastOneAccessRightSet>

							<interaudit:atLeastOneAccessRightSet rights="CONSULT_INVOICE,CREATE_INVOICE">
								
									<li><a href="javascript:exp_coll(20);"><span style="color: #ffe075;">Facturation</span></a>				
								<ul class="zzul" id="sp_20" style="display:block;">	
											

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

									

										</ul>
									</a>
								</li>
								</interaudit:atLeastOneAccessRightSet>
							
								<interaudit:atLeastOneAccessRightSet rights="CONSULT_CONTRACT,CONSULT_USER,CONSULT_CUST,CONSULT_BANK,CONSULT_CONTACT,CONSULT_DECLARATION,CONSULT_CONTRACT">
								<li>
									<a href="javascript:exp_coll(40);"><span style="color: #ffe075;">Search</span></a>				
									<ul class="zzul" id="sp_40" style="display:block;">		

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
								</interaudit:atLeastOneAccessRightSet>
											
								
								<interaudit:atLeastOneAccessRightSet rights="MODIFY_ACCESS_RIGHTS">
								<li><a href="javascript:exp_coll(50);"><span style="color: #ffe075;">Administration</span></a>				
									<ul class="zzul" id="sp_50" style="display:block;">
											

											<!--li><a href="${ctx}/showEwsConfigurationPage.htm?command=new" title="Create user" class="standalone"><span style="font-size: 11px;">Ews</span></a></li-->
											<interaudit:atLeastOneAccessRightSet rights="MODIFY_ACCESS_RIGHTS">
											 <li><a href="${ctx}/showUserAccessRightsPage.htm?type=BUDGET" title="Access rights " >
											 <span style="font-size: 11px;">Droits d'accès</span></a></li>
											 <%--li><a href="${ctx}/showUserAccessRightsPage.htm?type=BUDGET" title="Access rights " >
											 <span style="font-size: 11px;">Alertes factures</span></a></li--%>
											</interaudit:atLeastOneAccessRightSet>

											
												
										
									</ul>
								</li>
								</interaudit:atLeastOneAccessRightSet>


								<li>
									<a href="${ctx}/logout.htm" title="Log out">

									<span style="color: #ffe075;">Log off</a>
								</li>				
						</ul>					
					</td></tr></tbody>
					</table>
 

