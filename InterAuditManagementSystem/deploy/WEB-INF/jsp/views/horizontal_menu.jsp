<%@ taglib uri="/tags/interaudit" prefix="interaudit" %>

<style type="text/css">

				.container23 {
						width: 100%;
						padding: 15px;
						/*margin: 3px 0 20px 0;*/
						border: 1px solid #ccc;
						background: #eee;
						text-align:center;
				}

				/* rounded */

				#nav23 {
						margin: 0;
						padding: 0 0 20px 10px;
						border-bottom: 1px solid #9FB1BC;
				}

				#nav23 li {
						margin: 0;
						width: 146px;
						padding: 0;
						display: inline;
						list-style-type: none;
				}

				#nav23 a:link, #nav23 a:visited {
						float: left;
						font-size: 10px;
						line-height: 14px;
						font-weight: bold;
						padding: 0 12px 6px 12px;
						text-decoration: none;
						
				}

				#nav23 a:link.active, #nav23 a:visited.active, #nav23 a:hover {
						color: #000;
						background: url(images/Pyramid.gif) no-repeat bottom center;
				}


				ul#nav23
	{  margin-bottom:0;  padding:0px 0; list-style:none;/* width:100%; height:19px;  border-bottom:1px solid #b9121b; font:normal 8pt verdana, arial, helvetica;*/}

ul#nav23 li
	{ margin:0; padding:0; display:block; float:left; position:relative; /*width:148px; border:2px solid yellow;*/ }
ul#nav23 li a:link,
ul#nav23 li a:visited
	{ padding:4px 0; display:block; text-align:center; text-decoration:none; background:#5F9EA0; color:#ffffff; width:148px; height:13px; }
ul#nav23 li:hover a,
ul#nav23 li a:hover,
ul#nav23 li a:active
	{ padding:4px 0; display:block; text-align:center; text-decoration:none;/* background:#ec454e; color:#ffffff; width:146px; height:13px; border-left:1px solid #ffffff; border-right:1px solid #ffffff; */}
ul#nav23 li ul.navigation-2
	{ margin:0; padding:1px 1px 0; list-style:none; display:none; background:#eee; width:146px; position:absolute; top:21px; left:-1px; /*border:1px solid #b9121b;*/ border-top:none; }
ul#nav23 li:hover ul.navigation-2
	{ display:block; }
ul#nav23 li ul.navigation-2 li
	{ width:146px; clear:left; width:146px; }

ul#nav23 li ul.navigation-2 li a:link,
ul#nav23 li ul.navigation-2 li a:visited
	{ clear:left; background:#white; padding:4px 0; width:146px; border:none; border-bottom:1px solid #ffffff; position:relative; z-index:1000; }
ul#nav23 li ul.navigation-2 li:hover a,
ul#nav23 li ul.navigation-2 li a:active,
ul#nav23 li ul.navigation-2 li a:hover
	{ clear:left; background:#ec454e; padding:4px 0; width:146px; border:none; border-bottom:1px solid #ffffff; position:relative; z-index:1000; }

</style>
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
               
		<div class="container23" >
				<ul id="nav23">
					
					<%--li>
					<a  href="${ctx}/homePage.htm" title="Home Page" >
									<span style="color: purple;">Home Page</span></a>
					</li--%>

					<li>
					<a href="${ctx}/homePage.htm"><img src="images/home.gif" border="0" alt="Edit"/></a>	
					<ul class="navigation-2">
								 <li>
									<a  href="${ctx}/homePage.htm" title="Home Page" >
									<span style="color: purple;">Home Page</span>
									</a>
								 </li>
								 <li>
								    <a href="${ctx}/showEmployeeRegisterPage.htm?id=${context.currentUser.id}" title="Register employee" >
												Mon profile
								    </a>
								  </li>
								 
								  <li>
								    <a href="${ctx}/help.htm" title="Help">User guide</a>
								  </li>
								 
								  <li>
									<a href="${ctx}/logout.htm" title="Log out">Log off</a>
								  </li>								 
						</ul>
					</li>

					<interaudit:atLeastOneAccessRightSet rights="REGISTER_CONTRACT,REGISTER_USER,REGISTER_CUST,REGISTER_BANK,REGISTER_CONTACT,REGISTER_DECLARATION,REGISTER_CONTRACT">
					<li>
					<a href="#">New</a>				
						<ul class="navigation-2">

								<!--li><a href="${ctx}/showExerciseRegisterPage.htm" title="Create exercise">Exercise</a></li>

								<li><a href="${ctx}/showBudgetRegisterPage.htm" title="Create budgets">Budget</a></li-->

								
								 <interaudit:accessRightSet right="REGISTER_USER">
								<li><a href="${ctx}/showEmployeeRegisterPage.htm" title="Register employee">Encoder un employé</a></li>
								</interaudit:accessRightSet>

								 <interaudit:accessRightSet right="REGISTER_CUST">
								<li><a href="${ctx}/showCustomerRegisterPage.htm?command=new" title="Create customer">Encoder un client</a></li>
								</interaudit:accessRightSet>


								 <interaudit:accessRightSet right="REGISTER_BANK">
								 <li><a href="${ctx}/showBankRegisterPage.htm" title="Register new bank">Encoder une banque</a></li>
								 </interaudit:accessRightSet>

								  
								  
								  <!--li><a href="${ctx}/showDocumentRegisterPage.htm" title="Register document">Document</a></li-->

								 
											
								  
								  
								   <interaudit:accessRightSet right="REGISTER_CONTACT">
								  <li><a href="${ctx}/showContactRegisterPage.htm" title="Register contact">Encoder un contact</a></li>
								  </interaudit:accessRightSet>
								  
								   <interaudit:accessRightSet right="REGISTER_DECLARATION">
								  <li><a href="${ctx}/showDeclarationRegisterPage.htm" title="Register declaration" >Encoder une déclaration</a></li>
								  </interaudit:accessRightSet>
								  
								  <!--li><a href="${ctx}/showExpenseRegisterPage.htm" title="Register expense " >Expense</a></li-->
								  
								   <interaudit:accessRightSet right="REGISTER_CONTRACT">
								  <li><a href="${ctx}/showContractRegisterPage.htm" title="Register contract " >Encoder un contrat</a></li>
								  </interaudit:accessRightSet>
								  
								  <!--li><a href="${ctx}/showTrainingRegisterPage.htm" title="Register training " >Training</a></li-->
								  
								  <!--li><a href="${ctx}/showReminderRegisterPage.htm" title="Register reminder " >Reminder</a></li-->
						</ul>
					</li>
					</interaudit:atLeastOneAccessRightSet>


					<interaudit:atLeastOneAccessRightSet rights="CONSULT_RENTABILITE_CLIENTS,CONSULT_BUDGET,CONSULT_RESULTATS,CONSULT_RESULTATS">
					<li><a href="#">Budgets</a>			
						<ul class="navigation-2">	
						
								 <interaudit:accessRightSet right="CONSULT_BUDGET">
								<li><a href="${ctx}/showGeneralBudgetPageFromMenu.htm" title="Budget par client">Budget général</a></li>
								</interaudit:accessRightSet>
								
								 <interaudit:accessRightSet right="CONSULT_RENTABILITE_CLIENTS">
								<li><a href="${ctx}/showProfitabilityPerCustomerPage.htm" title="Rentabilit� par client">
											Rentabilité par client</a></li>
								</interaudit:accessRightSet>

								 <interaudit:accessRightSet right="CONSULT_RESULTATS">
								<li><a href="${ctx}/showBudgetResultsPage.htm" title="Resultats">
											Objectifs & Resultats</a></li>
								</interaudit:accessRightSet>


								<interaudit:accessRightSet right="CONSULT_FINANCIAL_DATA">
										<li><a href="${ctx}/showEmployeesFinancialDataPage.htm" title="Financial data " >Financial data</a></li>
									  </interaudit:accessRightSet>

								 
						</ul>
					</li>
					</interaudit:atLeastOneAccessRightSet>
					

					<interaudit:atLeastOneAccessRightSet rights="CONSULT_CALENDAR,CONSULT_GENERAL_PLANNING,CONSULT_MISSIONS">
					<li>
					<a href="#">Planning</a>				
						<ul class="navigation-2">
								 
								
								 <interaudit:accessRightSet right="CONSULT_GENERAL_PLANNING">
								<li><a href="${ctx}/showAnnualPlanningPage.htm" title="Manage general planning">
										Planning </a></li>
								</interaudit:accessRightSet>


								<interaudit:accessRightSet right="CONSULT_MISSIONS">
								<li><a href="${ctx}/showMissionsPage.htm?FromMenu= true" title="Manage missions" >
								PlanWeek</a></li>
								</interaudit:accessRightSet>


								<interaudit:accessRightSet right="CONSULT_CALENDAR">
								<li><a href="${ctx}/showAgendaPage.htm" title="Manage tasks" >
								Mon Agenda</a></li>
								</interaudit:accessRightSet>

								

								 
						</ul>
					</li>
					</interaudit:atLeastOneAccessRightSet>

					<interaudit:atLeastOneAccessRightSet rights="VALIDATE_TIMESHEET,REGISTER_TIMESHEET,CONSULT_TS_BOARD,CONSULT_TS_ATRAITER">
					<li>
						<a href="#">Timesheets</a>	
						<ul class="navigation-2">

							<interaudit:accessRightSet right="CONSULT_TS_BOARD">
							<li><a href="${ctx}/showTimesheetGlobalSituationsPage.htm" title="Situation globale des timesheets " >Tableau de bord</a></li>
							</interaudit:accessRightSet>

							<interaudit:accessRightSet right="REGISTER_TIMESHEET">
							<li><a href="${ctx}/showTimesheetRegisterPage.htm" title="Register time"> Ma timesheet </a></li>
							</interaudit:accessRightSet>
							
							<interaudit:accessRightSet right="REGISTER_TIMESHEET">
							<li><a href="${ctx}/showTimesheetsPage.htm?type=personal" title="list of timesheets">Mes timesheets</a></li>
							</interaudit:accessRightSet>
							

							<interaudit:atLeastOneAccessRightSet rights="VALIDATE_TIMESHEET,CONSULT_TS_ATRAITER">
									 <li><a href="${ctx}/showTimesheetsPage.htm" title="list of timesheets">A traiter</a></li>
							</interaudit:atLeastOneAccessRightSet>
							
							<interaudit:atLeastOneAccessRightSet rights="VALIDATE_TIMESHEET,CONSULT_TS_ATRAITER">
                                     <li><a href="${ctx}/showDownLoadCvsFileData.htm" title="Download raw data in cvs format">Download Cvs File</a></li>
                            </interaudit:atLeastOneAccessRightSet>
							
							
															
						</ul>
					</li>
					</interaudit:atLeastOneAccessRightSet>
					

					<interaudit:atLeastOneAccessRightSet rights="CONSULT_INVOICE,CREATE_INVOICE">
					 <li>
						<a href="#">Facturation</a>			
									<ul class="navigation-2">

									<interaudit:accessRightSet right="CREATE_INVOICE">
									<li><a href="${ctx}/selectCustomerForInvoicePage.htm" title="Register invoice">Encoder une facture</a></li>
									</interaudit:accessRightSet>

									<interaudit:accessRightSet right="CREATE_INVOICE">
												<li><a href="${ctx}/selectCustomerForNoteCreditPage.htm" title="Register invoice">
												Encoder une note de credit</a></li>
												</interaudit:accessRightSet>
												
												
									<interaudit:accessRightSet right="CREATE_INVOICE">
                                    <li><a href="${ctx}/selectCustomerForInvoiceReminderPage.htm" title="Register invoice">Encoder un rappel</a></li>
                                    </interaudit:accessRightSet>

									<%--interaudit:accessRightSet right="REGISTER_PAYMENT">
									 <li><a href="${ctx}/showPaymentRegisterPage.htm" title="Register payment">Encoder un payment</a></li>
									</interaudit:accessRightSet--%>

									<interaudit:accessRightSet right="CONSULT_INVOICE">
									<li><a href="${ctx}/showDraftInvoicesPage.htm" title="Search">Factures en préparation</a></li>
									</interaudit:accessRightSet>

									<interaudit:accessRightSet right="CONSULT_INVOICE">
									<li><a href="${ctx}/showInvoicesPage.htm" title="Search">Factures approuvées</a></li>
									</interaudit:accessRightSet>

									<interaudit:accessRightSet right="CONSULT_INVOICE">
									<li><a href="${ctx}/showPaymentsPage.htm" title="Search">Payments</a></li>
									</interaudit:accessRightSet>

									<interaudit:accessRightSet right="CONSULT_INVOICE">
									<li><a href="${ctx}/showInvoicesReminderPage.htm" title="Search">Rappels de facture</a></li>
									</interaudit:accessRightSet>
									
									
									<interaudit:accessRightSet right="CONSULT_BALANCE_AGEE">
                                    <li><a href="${ctx}/showBalanceAgeePage.htm" title="Balance agee">Balance agee</a></li>
                                    </interaudit:accessRightSet>
									   

							</ul>
						</a>
					</li>
					</interaudit:atLeastOneAccessRightSet>

					

					<interaudit:atLeastOneAccessRightSet rights="CONSULT_CONTRACT,CONSULT_USER,CONSULT_CUST,CONSULT_BANK,CONSULT_CONTACT,CONSULT_DECLARATION,CONSULT_CONTRACT">
					
					<li><a href="#">Search</a>			
							<ul class="navigation-2">	
							
									 <interaudit:accessRightSet right="CONSULT_CONTRACT">
									<li><a href="${ctx}/showContractsPage.htm" title="Search">Liste des contrats</a></li>
									</interaudit:accessRightSet>

									 <%--interaudit:accessRightSet right="CONSULT_DOCUMENT">
									<li><a href="${ctx}/showDocumentsManagementPage.htm" title="Find documents " >Documents</a></li>
									</interaudit:accessRightSet--%>
									
									
									 
									  <interaudit:accessRightSet right="CONSULT_CUST">
									 <li><a href="${ctx}/showCustomersPage.htm" title="Find customers " >Liste des clients</a></li>
									 </interaudit:accessRightSet>

									 <interaudit:accessRightSet right="CONSULT_USER">
									 <li><a href="${ctx}/showEmployeesPage.htm" title="Find employees " >Liste des employés</a></li>
									 </interaudit:accessRightSet>

									   <interaudit:accessRightSet right="CONSULT_CONTACT">
									  <li><a href="${ctx}/showContactsPage.htm" title="Find contacts " >Liste des contacts</a></li>
									  </interaudit:accessRightSet>

									  <interaudit:accessRightSet right="CONSULT_BANK">
									  <li><a href="${ctx}/showBanksPage.htm" title="Find banks" >Liste des banques</a></li>
									  </interaudit:accessRightSet>
									  
									   <interaudit:accessRightSet right="CONSULT_DECLARATION">
									  <li><a href="${ctx}/showDeclarationsPage.htm" title="Find declarations">Liste des déclarations</a></li>
									  </interaudit:accessRightSet>

									  
							</ul>
					</li>
					</interaudit:atLeastOneAccessRightSet>
							
					<interaudit:atLeastOneAccessRightSet rights="MODIFY_ACCESS_RIGHTS">
					<li><a href="#">Administration</a>				
						<ul class="navigation-2">							
								 <interaudit:accessRightSet right="MODIFY_ACCESS_RIGHTS">
								 <li><a href="${ctx}/showUserAccessRightsPage.htm?type=BUDGET" title="Access rights " >Droits d'accès</a></li>								 
								 </interaudit:accessRightSet>	
								 <interaudit:atLeastOneAccessRightSet rights="MODIFY_ACCESS_RIGHTS">
											 <li><a href="#" onclick="sendReminderTimesheet()" title="Timesheet reminders " >
											 Send Timesheet Reminders</a></li>											 
								 </interaudit:atLeastOneAccessRightSet>
								 <interaudit:atLeastOneAccessRightSet rights="MODIFY_ACCESS_RIGHTS">
											 <li><a href="#" onclick="sendReminderInvoices()" title="Invoice reminders " >
											 Send Invoice Reminders</a></li>											 
								 </interaudit:atLeastOneAccessRightSet>
											
											
						</ul>
						
					</li>
					</interaudit:atLeastOneAccessRightSet>
				</ul>
		</div>