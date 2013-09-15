package com.interaudit.service.impl;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.transaction.annotation.Transactional;

import com.interaudit.domain.dao.IBankDao;
import com.interaudit.domain.dao.IBudgetDao;
import com.interaudit.domain.dao.IContractDao;
import com.interaudit.domain.dao.ICustomerDao;
import com.interaudit.domain.dao.IExerciseDao;
import com.interaudit.domain.dao.IFactureDao;
import com.interaudit.domain.dao.IMissionDao;
import com.interaudit.domain.dao.IPaymentDao;
import com.interaudit.domain.dao.ITimesheetRowDao;
import com.interaudit.domain.model.AddtionalFeeInvoice;
import com.interaudit.domain.model.AnnualBudget;
import com.interaudit.domain.model.Bank;
import com.interaudit.domain.model.Contract;
import com.interaudit.domain.model.Cost;
import com.interaudit.domain.model.Customer;
import com.interaudit.domain.model.DeductionFeeInvoice;
import com.interaudit.domain.model.EmailData;
import com.interaudit.domain.model.Employee;
import com.interaudit.domain.model.Exercise;
import com.interaudit.domain.model.Invoice;
import com.interaudit.domain.model.Mission;
import com.interaudit.domain.model.Payment;
import com.interaudit.domain.model.RemindInvoice;
import com.interaudit.domain.model.UserAction;
import com.interaudit.domain.model.data.InvoiceData;
import com.interaudit.domain.model.data.MissionBudgetData;
import com.interaudit.domain.model.data.MissionInfo;
import com.interaudit.domain.model.data.MissionToCloseData;
import com.interaudit.domain.model.data.Option;
import com.interaudit.service.IEmailService;
import com.interaudit.service.IFactureService;
import com.interaudit.service.IUserActionService;
import com.interaudit.service.IUserService;
import com.interaudit.service.exc.ExcessiveAmountException;
import com.interaudit.service.jobs.EwsManager;
import com.interaudit.service.param.SearchBalanceAgeeParam;
import com.interaudit.service.param.SearchInvoiceParam;
import com.interaudit.service.param.SearchInvoiceReminderParam;
import com.interaudit.service.param.SearchPaymentParam;
import com.interaudit.service.view.BalanceAgeeView;
import com.interaudit.service.view.InvoiceReminderView;
import com.interaudit.service.view.InvoiceView;
import com.interaudit.service.view.PaymentsView;
import com.interaudit.util.DateUtils;

@Transactional
public class FactureService implements IFactureService, ApplicationContextAware{

	private ICustomerDao customerDao;
	private ITimesheetRowDao timesheetRowDao;
	private IFactureDao factureDao;
	private IMissionDao projectDao;
	private IExerciseDao exerciseDao;
	private IPaymentDao paymentDao;
	private IBankDao bankDao;
	private IBudgetDao budgetDao;
	private IContractDao contractDao;
	private IUserActionService userActionService;	
	private IEmailService emailService;
	private IUserService userService;	
	private ApplicationContext context;
	private String defaultAmountBlanchiment;
	private String defaultAmountCssf;
	
	private Double defaultAmountBlanchiment(){
		try{
			return Double.parseDouble(defaultAmountBlanchiment);
		}
		catch(NumberFormatException nfe){
			return 50.0d;
		}
		
	}
	
	private Double defaultAmountCssf(){
		try{
			return Double.parseDouble(defaultAmountCssf);
		}
		catch(NumberFormatException nfe){
			return 250.0d;
		}
	}
	
	
	
	public  boolean findFinalBillForMission(Long idMission){
		return factureDao.findFinalBillForMission(idMission);
	}
	
	public BalanceAgeeView buildBalanceAgeeView(SearchBalanceAgeeParam param){
		return factureDao.buildBalanceAgeeView(param);
	}
	    
	public void setApplicationContext(ApplicationContext applicationContext)  throws BeansException 
	    {                context = applicationContext;        }
	
	public List<MissionToCloseData> listBudgetsToClose(Integer currentYear){
		return exerciseDao.findBudgetsWithFinalBillToClose(currentYear);
	}
	
	
	public static double formatDouble(double value){
		double res = Math.rint((value*100 )+ 0.5 ) / 100 ;
		return res;
	}
	
	
   public int getMaxYearSequence(String year){
	   return factureDao.getMaxYearSequence(year);
   }
   
   public int getMaxYearSequenceForNc(String year){
	   return factureDao.getMaxYearSequenceForNc(year);
   }
   public int getMaxYearSequenceForDraft(String year){
	   return factureDao.getMaxYearSequenceForDraft(year);
   }
   public int getMaxYearSequenceForNcDraft(String year){
	   return factureDao.getMaxYearSequenceForNcDraft(year);
   }
   
   
	
	public  List<Option> getAllExercicesOptions(){
		   
		   List<Exercise> exercises = getExerciseDao().getAll();
		   List<Option> options= new ArrayList<Option>();
		   for(Exercise year : exercises){
				options.add(new Option(Integer.toString(year.getYear()),Integer.toString(year.getYear())));
		   }
		   
		   return options;
	}
	
	
	public  List<Option> getAllBankOptions(){
		   
		   List<Bank> banks = bankDao.getAll();
		   List<Option> options= new ArrayList<Option>();
		   for(Bank bank : banks){
				options.add(new Option(Long.toString(bank.getId()),bank.getName()));
		   }
		   
		   return options;
	} 
	
	public Payment buildPaymentFromFacture(Long invoiceid){
		Payment payment = new com.interaudit.domain.model.Payment();		
		Invoice facture = factureDao.getOneDetached(Long.valueOf(invoiceid));
		payment.setFacture(facture);
		payment.setTotalDu(facture.getAmountEuro());
		double totalNoteDeCredit = factureDao.calculateTotalNoteDeCreditForInvoice(facture.getReference());
		double totalPaid = factureDao.calculateTotalPaidForInvoice(facture.getReference());
		double totalRemaining = facture.getAmountEuro() - totalNoteDeCredit - totalPaid;
		if(totalRemaining == 0){
			totalRemaining =0.0d;
		}
		payment.setTotalNoteCredit(formatDouble(totalNoteDeCredit));
		payment.setTotalPaid(formatDouble(totalPaid));
		payment.setTotalRemaining(formatDouble(totalRemaining));
		
		payment.setFactureReference(facture.getReference());
		payment.setFactureId(invoiceid);
		
		return payment;
	}
	
	public Invoice createNoteDeCreditForMission(Invoice factureParent,Employee creator,String type,double tva,double honoraires,boolean interne,boolean forceCreation) throws ExcessiveAmountException{
		
		Invoice facture = new Invoice(factureDao.getOneDetached(factureParent.getId()), creator , honoraires , tva,interne);
		facture.setAmountNetEuro(formatDouble(calculateTotalAmountNet(facture)));
    	facture.setAmountEuro(formatDouble(calculateTotalAmount(facture)));
    	if(!forceCreation){
    		if(facture.getAmountEuro() > factureParent.getAmountEuro()){
        		throw new ExcessiveAmountException();
        	}
    	}
    	
    	//Internationaliser la facture
    	internationalizeInvoice(facture);
    	
    	/*
    	Calendar c = Calendar.getInstance();
    	String year = Integer.toString(c.get(Calendar.YEAR));
    	*/
    	
    	//Calculer le numero de la facture
		//String year = facture.getProject().getExercise();
		Integer currentYear = exerciseDao.getFirstOnGoingExercise();
	   	if(currentYear == null){
	   	 	 //currentYear = exerciseService.getMaxYear();
					 currentYear =  exerciseDao.getLastClosedExercise();
					 if(currentYear == null){
						Calendar c = Calendar.getInstance(); 
						currentYear =c.get(Calendar.YEAR);
					 } 
	   	 }
	   	
	   	
	   	
	   	 String year = Integer.toString(currentYear);
	   	facture.setExercise(year);
    	 int orderIntheYear =  getMaxYearSequenceForNcDraft(year) + 1;
    	
    	//Calculer le numero de la facture
    	//int orderIntheYear =  getMaxYearSequence(year) + 1;
    	//System.out.print("orderIntheYear : " + orderIntheYear);
    	
    	NumberFormat formatter = new DecimalFormat("000");
		String formattedNumber = formatter.format(orderIntheYear); 
		String reference = "NCB-" + year.substring(year.length()-2) +"/" +formattedNumber;
		reference = reference.toUpperCase(); 
		
		
		Invoice facture2 = getOneDetachedFromReference(reference);
	    while(facture2 != null){
	    	orderIntheYear += 1;    		    	
			formattedNumber = formatter.format(orderIntheYear); 
			reference = "NCB-" + year.substring(year.length()-2) +"/" +formattedNumber;			
			reference = reference.toUpperCase();
			facture2 = getOneDetachedFromReference(reference);
	    }
	    
	    reference = reference.toUpperCase(); 
	    facture.setReference(reference);
		
		//Enregistrer la facture
		saveOne(facture);
		
		//Update du budget
		updateExerciceAndBudget(facture);  
		
		//Enregistrer l'action de l'utilisateur
		userActionService.saveOne(new UserAction(creator, "CREATION FACTURE", facture.getClass().getName(),
					facture.getId(), Calendar.getInstance().getTime()) );
    	
		
		return facture;
	}
	
  public void checkAllUnpaidAndSentInvoices(){
	  	List<Invoice> invoices = factureDao.getAllUnpaidAndSentInvoices();
		for(Invoice facture : invoices){
			updateInvoiceStatusafterPayment( facture);
		}
	}
	
	public Map<String, List<Invoice>> buildMapReminders(){
		Map<String, List<Invoice>> results = new HashMap<String, List<Invoice>>();
		List<Invoice> invoices = factureDao.getAllUnpaidAndSentInvoices();
		for(Invoice facture : invoices){
			
			Date sentDateFacture = facture.getSentDate();
			if(sentDateFacture != null ){
				//Calcul du nombre de jours entre la date courante et la date d'envoi de la facture
				int nbDays = DateUtils.getNbDays(sentDateFacture);
				
				//Tester si on a atteint le nombre de jours minimun avant d'envoyer le premier rappel
				if (nbDays > EwsManager.maxDaysBeforeFirstAlert){
					List<Invoice> invoices2= results.get(facture.getCustomerName());
					if(invoices2 == null){//l'entrée n'existe pas encore
						invoices2= new ArrayList<Invoice>();
						results.put(facture.getCustomerName(), invoices2);						
					}
					invoices2.add(facture);					
				}
			}
				
		}
		return results;
	}
	
	public RemindInvoice createFactureReminder(Employee creator,Long customerId,String lang){
		 List<Invoice> invoices = factureDao.getAllUnpaidAndSentInvoicesForCustomer(customerId);
		 if(invoices == null || invoices.isEmpty()) {
			 return null;
		 }
		 else{
			   List<Invoice> targetInvoices = new ArrayList<Invoice>();
			   for(Invoice facture : invoices){
					
					Date sentDateFacture = facture.getSentDate();
					if(sentDateFacture != null ){
						//Calcul du nombre de jours entre la date courante et la date d'envoi de la facture
						int nbDays = DateUtils.getNbDays(sentDateFacture);
						
						//Tester si on a atteint le nombre de jours minimun avant d'envoyer le premier rappel
						if (nbDays >= EwsManager.maxDaysBeforeFirstAlert){
							targetInvoices.add(facture);		
						}
					}
						
				}
			   
			   if(targetInvoices.isEmpty())return null;
			 
			    Customer customer = customerDao.getOneDetached(customerId);
				Map<String, String> cachedProperties = factureDao.loadInvoiceTranslationsForLanguage(lang);
				
				String libelle = cachedProperties.get("lib.ra.men.haut");
				String libfin = cachedProperties.get("lib.ra.men.bas");
				
				int order = factureDao.getNextSequenceForReminderForCustomer(customer.getId());
				order = order +1;
				
				RemindInvoice reminder = new RemindInvoice( customer,order,lang,  libelle,libfin, invoices);
						 			
			    //Enregistrer le rappel
				factureDao.saveOneReminder(reminder);
				
				//Save the many to many relationship
				for(Invoice facture : targetInvoices){
					factureDao.saveInvoiceReminderRelation(facture.getId(),reminder.getId());
				}
			
			    //Enregistrer l'action de l'utilisateur
			    userActionService.saveOne(new UserAction(creator, "CREATION RAPPEL", reminder.getClass().getName(),
					reminder.getId(), Calendar.getInstance().getTime()) );
			  //Envoyer un mail aux secrétaires
				String subject = "Invoice reminder : " +reminder.getId();
				//String contents = "The invoice  [  " +facture.getReference() + " ] has been approved. Please take the necessary actions to send it to the customer...";
				String contents = "Please process with the following reminder : " + reminder.getId() + " for customer : " + customer.getCompanyName();
				String typeOfEmail=EmailData.TYPE_REMINDER_COMMUNICATION;
				
				sendEmailsToSecretaires(creator, subject, contents, typeOfEmail);
			
			    return reminder;
		 }
		
	}
	
	
	/**
	 * @param missionId
	 * @return
	 */
	public Invoice createFactureForMission(Long projectId,Employee creator,String type,double tva,double honoraires,int delaiPaiement,String language,Long bankId,String exercice){
		
		//Find the related project
		Mission mission = projectDao.getOneDetached(projectId);
		if(mission == null){
			return null;
		}
		
		//Load the bank
		Bank bank = bankDao.getOneDetached(bankId);

		//Calculer les honoraires si nécessaire
		if(honoraires == 0.0d){
			 honoraires = calculateHonoraires(mission,type);
		}
		
		String exerciceMandat = mission.getExercise();
    	
    	//Create the new facture
    	Invoice facture = new Invoice( mission,creator,  type, honoraires, tva, delaiPaiement, language,bank,exerciceMandat,exercice) ;
    	//For the final bill we must add adavnces
    	if(type.equalsIgnoreCase("FB")){
    		 facture = updateInvoiceWithDeductions( facture,mission.getId() );
    	}
    	facture.setAmountNetEuro(formatDouble(calculateTotalAmountNet(facture)));
    	facture.setAmountEuro(formatDouble(calculateTotalAmount(facture)));
    	
    	
    	
    	//Internationaliser la facture
    	internationalizeInvoice(facture);
    	/*
    	Calendar c = Calendar.getInstance();
    	String year = Integer.toString(c.get(Calendar.YEAR));
    	*/
    	 Integer currentYear = exerciseDao.getFirstOnGoingExercise();
    	 if(currentYear == null){
    	 	 //currentYear = exerciseService.getMaxYear();
				 currentYear =  exerciseDao.getLastClosedExercise();
				 if(currentYear == null){
					Calendar c = Calendar.getInstance(); 
					currentYear =c.get(Calendar.YEAR);
				 } 
    	 }
    	
    	 String year = Integer.toString(currentYear);
    	//Calculer le numero de la facture
		//String year = facture.getProject().getExercise();
    	
    	int orderIntheYear =  getMaxYearSequenceForDraft(year) + 1;
    	//System.out.print("orderIntheYear : " + orderIntheYear);
    	
    	NumberFormat formatter = new DecimalFormat("000");
		String formattedNumber = formatter.format(orderIntheYear); 
		String reference = "FB-" + year.substring(year.length()-2) +"/" +formattedNumber;
		reference = reference.toUpperCase(); 
		
		Invoice facture2 = getOneDetachedFromReference(reference);
	    while(facture2 != null){
	    	orderIntheYear += 1;    		    	
			formattedNumber = formatter.format(orderIntheYear); 
			reference = "FB-" + year.substring(year.length()-2) +"/" +formattedNumber;			
			reference = reference.toUpperCase();
			facture2 = getOneDetachedFromReference(reference);
	    }
	    
		facture.setReference(reference);
		
		//Enregistrer la facture
		saveOne(facture);
		
		//Update du budget
		updateExerciceAndBudget(facture);    	
		
		//Enregistrer l'action de l'utilisateur
		userActionService.saveOne(new UserAction(creator, "CREATION FACTURE", facture.getClass().getName(),
					facture.getId(), Calendar.getInstance().getTime()) );
    	
		
		return facture;
		
		
	}
	
	public Invoice updateInvoiceWithDeductions(Invoice facture,Long idMission ){
		Map<String, String> cachedProperties = factureDao.loadInvoiceTranslationsForLanguage(facture.getLanguage());
		List<Invoice> advances = new ArrayList<Invoice>();
		factureDao.getAdvancesForMission(idMission,advances);
		String libelleTemplate =cachedProperties.get("lib.fa.men.advance");
		String libelleDu =cachedProperties.get("lib.fa.du");
		
		for(Invoice advance: advances){
			String justification = libelleTemplate + " " + advance.getReference() + " " +libelleDu + " "  +advance.getDateFacturation();
			DeductionFeeInvoice deduction = new DeductionFeeInvoice("lib.fa.men.advance",justification, advance.getAmountNetEuro());
			deduction.setFacture(facture);
			facture.getDeductions().add(deduction);
		}
		return facture;
	}
	
	private void updateRelatedExercise(AnnualBudget budget){
		Exercise exercise = budget.getExercise();
		double reportedAmount = exerciseDao.calculateReportedAmountForBudget(budget.getId(),exercise.getYear());
		budgetDao.updateReportedAmountInChildBudget(budget.getId(),reportedAmount);
		exerciseDao.markexErciseForUpdate(exercise.getId(),true);
	}
   
	
	public Invoice approveInvoice(Long factureId,Employee currentUser){
		
		   
		//Enregsitrer l'état de la facture
		Invoice facture = getOne(factureId);
		//String year = facture.getProject().getExercise();
		   
	   Integer currentYear = exerciseDao.getFirstOnGoingExercise();
    	 if(currentYear == null){
    	 	 //currentYear = exerciseService.getMaxYear();
				 currentYear =  exerciseDao.getLastClosedExercise();
				 if(currentYear == null){
					Calendar c = Calendar.getInstance(); 
					currentYear =c.get(Calendar.YEAR);
				 } 
    	 }
		
    	 //Calendar c = Calendar.getInstance();
    	//String year = Integer.toString(c.get(Calendar.YEAR));
    	String year = Integer.toString(currentYear);
		int orderIntheYear =  factureDao.getMaxYearApprovedInvoiceSequence(year) + 1;
		facture.setApproved(true);
		facture.setStatus(Invoice.FACTURE_STATUS_CODE_APPROVED);		
		
		//Calculer le numero de la facture    	 	
    	NumberFormat formatter = new DecimalFormat("000");
		String formattedNumber = formatter.format(orderIntheYear); 
		String reference = facture.getType().equalsIgnoreCase(Invoice.TYPE_CREDITNOTE)?  "NC-" : "F-" ;
		reference += year.substring(year.length()-2) +"/" +formattedNumber;
		reference = reference.toUpperCase();
		
	    Invoice facture2 = getOneDetachedFromReference(reference);
	    while(facture2 != null){
	    	orderIntheYear += 1;    		    	
			formattedNumber = formatter.format(orderIntheYear); 
			reference = facture.getType().equalsIgnoreCase(Invoice.TYPE_CREDITNOTE)?  "NC-" : "F-" ;
			reference += year.substring(year.length()-2) +"/" +formattedNumber;
			reference = reference.toUpperCase();
			facture2 = getOneDetachedFromReference(reference);
	    }
		
		facture.setReference(reference);
		
    	facture = updateOne(facture);
    	
    	//Updating the related budgets in any previsonal execises
    	Mission mission = facture.getProject();
    	AnnualBudget budget = mission.getAnnualBudget();
    	updateRelatedExercise(budget);
    	
    	//Enregistrer l'action de l'utilisateur
		userActionService.saveOne(new UserAction(currentUser, "APPROBATION FACTURE", facture.getClass().getName(),
				facture.getId(), Calendar.getInstance().getTime()) );
		
		//Envoyer un mail aux secrétaires
		String subject = "Invoice [ " +facture.getReference() + " ] approved";
		//String contents = "The invoice  [  " +facture.getReference() + " ] has been approved. Please take the necessary actions to send it to the customer...";
		String contents = facture.getReference();
		String typeOfEmail=EmailData.TYPE_FACTURATION_COMMUNICATION;
		
		sendEmailsToSecretaires(currentUser, subject, contents, typeOfEmail);
		
		return facture;
	}
	
	
	public void sendEmailsToSecretaires(Employee sender,String subject,String contents,String typeOfEmail){
		List<Employee> secretaires = userService.getSecretaires();
		for(Employee secretaire: secretaires){			
			//Save a communication email for the message
			EmailData emailData = new EmailData( sender, secretaire,subject, contents,typeOfEmail );
			emailService.saveOne(emailData);
		}
	}
	
	
	public Invoice markAsSentInvoice(Long factureId,Employee sender ){
		Invoice facture = this.getOneDetached(factureId);
		facture.setSent(true);
		
		if( facture.getType().equalsIgnoreCase(Invoice.TYPE_CREDITNOTE)){
			facture.setStatus(Invoice.FACTURE_STATUS_CODE_PAID);
		}
		else{
			facture.setStatus(Invoice.FACTURE_STATUS_CODE_ONGOING);
		}
		
		
    	facture.setSentDate(new Date());
    	facture.setDateEcheance(DateUtils.addDays(new Date(), facture.getDelaiPaiement()));
    	facture.setSender(sender);
    	String refAssSec = buildRefAssSec( facture, sender );
    	facture.setRefAssSec(refAssSec);
    	facture = updateOne(facture);
    	
    	//Enregistrer l'action de l'utilisateur
		userActionService.saveOne(new UserAction(sender, "ENVOI FACTURE", facture.getClass().getName(),
				facture.getId(), Calendar.getInstance().getTime()) );
		return facture;
	}
	
	private String buildRefAssSec(Invoice facture,Employee sender ){
		StringBuffer buffer = new StringBuffer();
		Employee partner = facture.getPartner();
		String partnerShort= partner.getFirstName().substring(0, 1) + partner.getLastName().substring(0, 1);
		
		String senderShort= sender.getFirstName().substring(0, 1) + sender.getLastName().substring(0, 1);
		String exerciseShort = facture.getExerciseMandat().substring(facture.getExerciseMandat().length()-2);
		String customerNumber = facture.getCustomerCode();
		buffer.append(partnerShort.toUpperCase()).append("/").append(senderShort.toLowerCase()).append("-").append(exerciseShort).append("-").append(customerNumber);
		return buffer.toString();
		
		
	}
	
	
	public RemindInvoice markAsSentReminderInvoice(Long reminderId,Employee currentUser){
		RemindInvoice reminder = this.factureDao.getOneRemindInvoiceDetached(reminderId);
		reminder.setSent(true);
		reminder.setSender(currentUser);
		String refAssSec = buildRefAssSecFroReminder( reminder, currentUser );
		reminder.setRefAssSec(refAssSec);
		reminder = this.factureDao.updateOneReminder(reminder);
		//Enregistrer l'action de l'utilisateur
		userActionService.saveOne(new UserAction(currentUser, "ENVOI RAPPEL", reminder.getClass().getName(),
				reminder.getId(), Calendar.getInstance().getTime()) );
		return reminder;
	}
	
	
	private String buildRefAssSecFroReminder(RemindInvoice remindInvoice,Employee sender ){
		StringBuffer buffer = new StringBuffer();
		Employee partner = remindInvoice.getCustomer().getAssocieSignataire();
		String partnerShort= partner.getFirstName().substring(0, 1) + partner.getLastName().substring(0, 1);
		
		String senderShort= sender.getFirstName().substring(0, 1) + sender.getLastName().substring(0, 1);
		String year = Integer.toString(remindInvoice.getExercise());
		String exerciseShort = year.substring(year.length()-2);
		String customerNumber = remindInvoice.getCustomer().getCode();
		buffer.append(partnerShort.toUpperCase()).append("/").append(senderShort.toLowerCase()).append("-").append(exerciseShort).append("-").append(customerNumber);
		return buffer.toString();
		
		
	}
	
	
	
	
	public Invoice updateInvoiceAmountTotal(Long factureId ){
		Invoice facture = getOne(factureId);
		facture.setAmountNetEuro(calculateTotalAmountNet(facture));
    	facture.setAmountEuro(calculateTotalAmount(facture));
    	updateOne(facture);
    	updateExerciceAndBudget(facture);
    	return facture;
	}
	
	public void internationalizeInvoice(Invoice facture){
		Map<String, String> cachedProperties = factureDao.loadInvoiceTranslationsForLanguage(facture.getLanguage());
		
		String libelle = null;
		String libHonoraires = null;
		
		//Chargement du libelle et du libelle honoraires
		if(facture.getType().equalsIgnoreCase("AD") || facture.getType().equalsIgnoreCase("SP")){
			libelle=cachedProperties.get("lib.fa.acompte");
			libHonoraires = cachedProperties.get("lib.fa.honoraires.ad");
		}else if(facture.getType().equalsIgnoreCase("CN")){
			libelle=cachedProperties.get("lib.fa.note.credit");
			libHonoraires = cachedProperties.get("lib.fa.honoraires.nc");
		}else if(facture.getType().equalsIgnoreCase("FB")){
			libelle=cachedProperties.get("lib.fa.fact.finale");
			libHonoraires = cachedProperties.get("lib.fa.honoraires.fb");
		}
		
	
		//Traitement du libelle honoaraires pour les notes de credit
	   if(facture.getType().equalsIgnoreCase("CN")){
			
			Invoice parent = this.factureDao.getOneDetachedFromReference(facture.getParentReference().trim());
			libHonoraires = String.format(libHonoraires,parent.getReference().toUpperCase(),parent.getDateFacturation());
			
		}
		
		
		//String libHonoraires = cachedProperties.get("lib.fa.men.honoraires");
		String libDelai = cachedProperties.get("lib.fa.delai.paiement");
		
		libDelai = String.format(libDelai, facture.getDelaiPaiement(),facture.getBank()==null?"":facture.getBank().getAccountNumber(),facture.getBank()==null?"":facture.getBank().getName(),facture.getBank()==null?"":facture.getBank().getCodeBic());
		
		facture.setLibelle(libelle);
		facture.setLibHonoraires(libHonoraires);
		
		//Pas de libdelai pour les notes de credit
		if(!facture.getType().equalsIgnoreCase("CN")){
			facture.setLibDelai(libDelai);	
		}
		else{
			facture.setLibDelai("");	
		}
		
		//Charger les options pour la tva à 0 pourcent
		
		facture.setLibTvaZeroOpt0(cachedProperties.get("lib.fa.tva0.opt0"));
		facture.setLibTvaZeroOpt1(cachedProperties.get("lib.fa.tva0.opt1"));
		facture.setLibTvaZeroOpt2(cachedProperties.get("lib.fa.tva0.opt2"));
		
			
	}
	
	 public int findAdvancesNotSentForMission(Long projectId){
		return factureDao.findAdvancesNotSentForMission(projectId);
	 }
	
	
	public Long addDeductionToInvoice(Long invoiceId,String code,String lang){
		Map<String, String> cachedProperties = factureDao.loadInvoiceTranslationsForLanguage(lang);
		String libelle = cachedProperties.get(code);
		double value = 0.0d;		
		DeductionFeeInvoice fee = new DeductionFeeInvoice(code, libelle, value);
		Invoice facture = factureDao.getOne(invoiceId);
		if(facture!= null){
			facture.getDeductions().add(fee);
		   	fee.setFacture(facture);
		   	factureDao.updateOne(facture);
		   	return facture.getId();
		}
		else{
			return null;
		}
	}
	
	
	public void removeDeductionFromInvoice(Long id){
		factureDao.deleteOneDeduction(id);
	}
	
	public DeductionFeeInvoice updateDeductionLibelle(Long id, String libelle){
		// get a fee by its id
		DeductionFeeInvoice fee  = factureDao.getOneDeduction(id);
	    if(fee != null){
	    	fee.setJustification(libelle);
	    	return factureDao.updateOneDeduction(fee);
	    }	    
	    return null;
	}
	
	
	public DeductionFeeInvoice updateInvoiceDeductionValue(Long id,Double value){
		// get a fee by its id
		DeductionFeeInvoice fee  = factureDao.getOneDeduction(id);
	    if(fee != null){
	    	fee.setValue(value);
	    	return factureDao.updateOneDeduction(fee);
	    }	    
	    return null;
	}
	
	
	public Long addFraisToInvoice(Long invoiceId,String codeFrais,String lang){
		
		Map<String, String> cachedProperties = factureDao.loadInvoiceTranslationsForLanguage(lang);
		String libelle = cachedProperties.get(codeFrais);
		double value = 0.0d;
		if(codeFrais.equalsIgnoreCase("lib.fa.men.blanchiment")){
			value= defaultAmountBlanchiment();	
		}
		else if(codeFrais.equalsIgnoreCase("lib.fa.men.cssf")){
			value= defaultAmountCssf();
		}
		else
		{
			value= 0.0d;
		}
		
		
		AddtionalFeeInvoice fee = new AddtionalFeeInvoice(codeFrais, libelle, value);
		Invoice facture = factureDao.getOne(invoiceId);
		if(facture!= null){
			facture.getFrais().add(fee);
		   	fee.setFacture(facture);
		   	factureDao.updateOne(facture);
		   	return facture.getId();
		}
		else{
			return null;
		}
	   	
	}
	
	public void removeFraisFromInvoice(Long fraisId){
		
		factureDao.deleteOneFee(fraisId);
	 	
	}
	
	
	public AddtionalFeeInvoice updateFraisLibelle(Long feeId,String libelle){
		// get a fee by its id
	    AddtionalFeeInvoice fee  = factureDao.getOneFee(feeId);
	    if(fee != null){
	    	fee.setJustification(libelle);
	    	return factureDao.updateOneFee(fee);
	    }
	    
	    return null;
	}
	
	 public AddtionalFeeInvoice updateInvoiceFraisValue(Long feeId ,Double value){
	    	// get a fee by its id
		    AddtionalFeeInvoice fee  = factureDao.getOneFee(feeId);
		    if(fee != null){
		    	fee.setValue(value);
		    	return factureDao.updateOneFee(fee);
		    }
		    
		    return null;
	    }

    public Invoice updateInvoiceLibDelai(Long invoiceId,String libelle){
    	Invoice facture = factureDao.getOne(invoiceId);
		if(facture!= null){
			facture.setLibDelai(libelle);
		   	factureDao.updateOne(facture);
		   	return factureDao.getOneDetached(invoiceId);
		}
		else{
			return null;
		}
    }

    public Invoice updateInvoiceLibHonoraires(Long invoiceId,String libelle){
    	Invoice facture = factureDao.getOne(invoiceId);
		if(facture!= null){
			facture.setLibHonoraires(libelle);
		   	factureDao.updateOne(facture);
		   	return factureDao.getOneDetached(invoiceId);
		}
		else{
			return null;
		}
    }

    public Invoice updateInvoiceAdresse(Long invoiceId, String addresse){
    	Invoice facture = factureDao.getOne(invoiceId);
		if(facture!= null){
			facture.setBillingAddress(addresse);
		   	factureDao.updateOne(facture);
		   	return factureDao.getOneDetached(invoiceId);
		}
		else{
			return null;
		}	
    }
    
    
    public Invoice updateInvoiceCodeTvaZero(Long invoiceId, String tvaZeroLibCode){
    	Invoice facture = factureDao.getOne(invoiceId);
		if(facture!= null){
			facture.setTvaZeroLibCode(tvaZeroLibCode);
		   	factureDao.updateOne(facture);
		   	return factureDao.getOneDetached(invoiceId);
		}
		else{
			return null;
		}	
    }
    
    
    public Invoice changeExcludedFromBalanceAgeeStatus(Long invoiceId){
    	Invoice facture = factureDao.getOne(invoiceId);
		if(facture!= null){
			facture.setExcludedFromBalanceAgee(!facture.isExcludedFromBalanceAgee());
		   	factureDao.updateOne(facture);
		   	return factureDao.getOneDetached(invoiceId);
		}
		else{
			return null;
		}	
    }

   

    public Invoice updateInvoiceHonoraires(Long invoiceId,Double honoraires){
    	Invoice facture = factureDao.getOne(invoiceId);
		if(facture!= null){
			facture.setHonoraires(honoraires);
		   	factureDao.updateOne(facture);
		   	return factureDao.getOneDetached(invoiceId);
		}
		else{
			return null;
		}	
    }

    public Invoice updateInvoiceTva(Long invoiceId ,Double tva ){
    	Invoice facture = factureDao.getOne(invoiceId);
		if(facture!= null){
			facture.setTva(tva);
		   	factureDao.updateOne(facture);
		   	return factureDao.getOneDetached(invoiceId);
		}
		else{
			return null;
		}	
    }
    
    public Date updateInvoiceDateFacturation(Long invoiceId,String dateFacturationAsString){
    	Invoice facture = factureDao.getOne(invoiceId);
		if(facture!= null){
			boolean isValid = DateUtils.isValid(dateFacturationAsString, "yyyy-MM-dd");
			if(isValid){
				Date facturationDate = DateUtils.getDate(dateFacturationAsString, "yyyy-MM-dd");	
				if(facturationDate != null){
					facture.setDateFacturation(facturationDate);
					Calendar c = Calendar.getInstance();
					c.clear();
					c.setTime(facturationDate);
					facture.setMois(Integer.toString(c.get(Calendar.MONTH)));					
				   	factureDao.updateOne(facture);
				   	return facturationDate;
				}
				else{
					return null;
				}				
			}
			else{
				return null;
			}
		}
		else{
			return null;
		}	
    }

    public Invoice updateInvoiceLang(Long invoiceId,String language){
    	Invoice facture = factureDao.getOne(invoiceId);
		if(facture!= null){
			facture.setLanguage(language);
		   	factureDao.updateOne(facture);
		   	internationalizeInvoice(facture);
		   	return factureDao.getOneDetached(invoiceId);
		}
		else{
			return null;
		}
    }
    
    
    public Invoice updateInvoiceCustomer(Long invoiceId ,Long projectId){
    	Invoice facture = factureDao.getOne(invoiceId);
    	Mission project = projectDao.getOne(projectId);
		if(facture!= null){
			
			facture.setProject(project);
			facture.setExerciseMandat(project.getExercise());
			facture.setBillingAddress(project.getCustomer().getMainAddress());
			facture.setCustomerCode(project.getCustomer().getCode());
			facture.setCountry(project.getCustomer().getCountry());
			facture.setCustomerName(project.getCustomer().getCompanyName());
			facture.setPartner(project.getCustomer().getAssocieSignataire());
			
		   	factureDao.updateOne(facture);
		   	//internationalizeInvoice(facture);
		   	return factureDao.getOneDetached(invoiceId);
		}
		else{
			return null;
		}
    }
	

	
	/* (non-Javadoc)
	 * @see com.interaudit.service.IFactureService#updateInvoiceStatusafterPayment(com.interaudit.domain.model.Invoice)
	 */
	public void updateInvoiceStatusafterPayment(Invoice facture){
		
		factureDao.getOne(facture.getId());
		Set<Payment> payments = facture.getPayments();
		double totalPayment = 0.0d;
		double totalRemboursement = 0.0d;
		if(payments != null){
			for(Payment payment : payments){
				if(payment.isReimbourse() == true){
					totalRemboursement += payment.getAmount();
				}
				else{
					totalPayment += payment.getAmount();
				}
				
			}
		}
		double totalNoteDeCredit = factureDao.calculateTotalNoteDeCreditForInvoice(facture.getReference());
		//double total1 =  totalPayment - ( totalNoteDeCredit + totalRemboursement);
		double total1 =  ( totalPayment + totalNoteDeCredit )-totalRemboursement;

		//double diff = Math.abs( total1 - facture.getAmountEuro());
		//double diff =  Math.abs( facture.getAmountEuro()-total1 );
		double diff =   facture.getAmountEuro()-total1 ;
		

		diff=(double)((int)(diff*100))/100;
		 
/*
        DecimalFormat df = new DecimalFormat("#.##");
        
        try{
        	String temp = df.format(diff);
        	diff = Double.parseDouble(temp);
        }
        catch(Exception e){
        	System.out.print(diff);
        }
        
*/
		

		if(diff <= 0.01 ){
		//if(  diff >= 0.0d){
			//Mark the invoice as paid
			facture.setStatus(Invoice.FACTURE_STATUS_CODE_PAID);
			//Date datePayment = factureDao.getDatePaymentForInvoice(facture.getId());
			Date datePayment =  facture.getMAxPaymentDate();
			facture.setDateOfPayment(/*new Date()*/datePayment);
			
			//Pass all the note de credit to paid status
			List<Invoice> notes = factureDao.getNoteDeCreditForInvoice(facture.getReference());
			for(Invoice note : notes){
				note.setStatus(Invoice.FACTURE_STATUS_CODE_PAID);
				note.setDateOfPayment(new Date());
				this.updateOne(note);
			}		
		}else{
			facture.setStatus(Invoice.FACTURE_STATUS_CODE_ONGOING);
		}
		
		this.updateOne(facture);
		//
		updateExerciceAndBudget(facture);
		
	}
	
	
	
	public MissionInfo buildMissionInfo(Mission mission2,Customer customer){
		
		MissionInfo missionInfo = new MissionInfo();
		if(mission2 != null && customer != null){
			Mission mission = projectDao.getOneDetached(mission2.getId());
			missionInfo.setAssocie(customer.getAssocieSignataire().getFullName());		
			missionInfo.setManager(customer.getCustomerManager().getFullName());
			missionInfo.setSociete(customer.getCompanyName());
			missionInfo.setCode(customer.getCode());
			
			double budget = mission.getAnnualBudget().getExpectedAmount() + mission.getAnnualBudget().getReportedAmount();
			missionInfo.setBudget(budget +"");
			
			List<MissionBudgetData> heurePrestees = projectDao.calculateMissionBudget(mission.getId());
			
		    double totalPrixRevient = 0.0d;
		    double totalPrixVente = 0.0d;
		    float hours = 0;
		    
		    if(heurePrestees !=  null){
		    	 for(MissionBudgetData heuresPrestee :heurePrestees ){
		 	    	totalPrixRevient += heuresPrestee.getRate() * heuresPrestee.getHours();
		 		    totalPrixVente += heuresPrestee.getPrixVente() * heuresPrestee.getHours();
		 		   hours +=heuresPrestee.getHours();
		 	    }
		    }
		    missionInfo.setPrixRevient(totalPrixRevient+"");
		    missionInfo.setPrixVente(totalPrixVente+"");
		    missionInfo.setHours(hours+"");
		    missionInfo.setBudget(budget +"");
		    
		    double totalExtraCosts = 0.0d;
		    for(Cost extraCost : mission.getExtraCosts() ){
		    	totalExtraCosts += extraCost.getAmount();
		    }
		    
		    missionInfo.setDepenses(totalExtraCosts+"");
		}
		
	    
		
		return missionInfo;
		
	}
	
	public double calculateHonoraires(Mission mission,String type){
		
		double budget = mission.getAnnualBudget().getExpectedAmount() + mission.getAnnualBudget().getReportedAmount();		
		List<MissionBudgetData> heurePrestees = projectDao.calculateMissionBudget(mission.getId());	    
	    Contract contract = contractDao.getOneDetached(mission.getAnnualBudget().getContract().getId());
	    boolean agreed = contract.isAgreed();
	    
	    double totalPrixRevient = 0.0d;
	    double totalPrixVente = 0.0d;
	   
	    
	    if( (heurePrestees != null) && (!heurePrestees.isEmpty()) ){
	    	 for(MissionBudgetData heuresPrestee :heurePrestees ){
	 	    	totalPrixRevient += heuresPrestee.getRate() * heuresPrestee.getHours();
	 		    totalPrixVente += heuresPrestee.getPrixVente() * heuresPrestee.getHours();
	 	    }
	    }
	    else{
	    	totalPrixVente =budget;
	    }
	   
	    
	    double totalExtraCosts = 0.0d;
	    for(Cost extraCost : mission.getExtraCosts() ){
	    	totalExtraCosts += extraCost.getAmount();
	    }
	    
	    //Ajouter les couts extra au prix de revient
	    totalPrixRevient += totalExtraCosts;
	    
	    double deltaPrixVente = budget * 0.025;
	    
	    double deltaPrixRevient = totalPrixRevient * 0.025;
	    
	    //Calcul de la plage pour le prix de vente
	    double maxTotalPrixVente = totalPrixVente + deltaPrixVente;
	    double minTotalPrixVente = totalPrixVente - deltaPrixVente;
	    
	    //Calcul de la plage pour le prix de revient
	    double maxTotalPrixRevient = totalPrixRevient + deltaPrixRevient;
	    double minTotalPrixRevient = totalPrixRevient - deltaPrixRevient;
	      
	    boolean prochePrixVente =  (budget <= maxTotalPrixVente) && (budget >= minTotalPrixVente);	    
	    boolean superieurPrixVente =  (budget > maxTotalPrixVente);
	    boolean entrePrixVenteEtPrixRevient =  (budget < minTotalPrixVente) && (budget > maxTotalPrixRevient);
	    	
	    boolean prochePrixRevient = (budget <= maxTotalPrixRevient) && (budget >= minTotalPrixRevient);
	    boolean inferieurPrixRevient =  (budget < minTotalPrixRevient);
	    
	  //Calcul de l'honoraire pour une Facture finale
	    if(type.equalsIgnoreCase("FB")){
	   
	    	//Le budget se trouve au-dessus du prix de vente
	    	if(superieurPrixVente == true){
	    		
	    		if( agreed == true){
	    			return budget;
	    		}	    			
	    		else{	    			
	    			return totalPrixVente;
	    		}
	    		    		
	    		//Les deux sont proches : situation 1 (budget tres proche de prix de vente 
	    	}else if(prochePrixVente == true){
	    		
	    		if( agreed == true){
	    			return budget;
	    		}	    			
	    		else{	    			
	    			return totalPrixVente;
	    		}
	    		
	    	}//En dessous du Prix de Vente et au dessus du Prix de Revient
	    	else if(entrePrixVenteEtPrixRevient == true){
	    		return budget;
	    	}//Proche du Prix de Revient retourner le prix de Revient
	    	else if(prochePrixRevient == true){
	    		return totalPrixRevient;
	    	}//En dessous du Prix de Revient retourner le prix de Revient pour ne pas perdre de sous
	    	else if(inferieurPrixRevient == true){
	    		return totalPrixRevient;
	    	}
	    	else{
	    		return budget;
	    	}
	   	
	    }//Calcul de l'honoraire pour une demande d'avance
	    else if(type.equalsIgnoreCase("AD")){
	    	
	    	//20% de 90% du budget
	    	double amount = (budget*0.18);
	    	
			double totalInvoiced = getTotalInvoicedForMission(mission.getId());
			double totalInvoiced2 = totalInvoiced + amount;
			double budget2 = budget * 0.9;
			if(totalInvoiced2 >= budget2){
				amount = budget2 - totalInvoiced;
			}
			return  java.lang.Math.rint(amount) ;	    	
	    }
	    else{//Calcul de l'honoraire pour une Note de credit
	    	return 0.0d;
	    }
	}
	
	
	 public double getTotalInvoicedForMission(Long idMission){
		return getFactureDao().getTotalInvoicedForMission(idMission); 
	 }
	
public double calculateTotalAmountNet(Invoice facture){
		
		double totalFrais = 0.0;
		for(AddtionalFeeInvoice frais : facture.getFrais()){
			totalFrais += frais.getValue();
		}
		
		double todeduct = 0.0;		
		for(DeductionFeeInvoice deduction : facture.getDeductions()){
			todeduct += deduction.getValue();
		}
		/*
		if(facture.getType().equalsIgnoreCase("FB")){
			todeduct = getFactureDao().calculateTotalAdvancesForMission(facture.getProject());
		}
		*/
				
		return totalFrais + facture.getHonoraires() - todeduct;
	}
	
	public double calculateTotalAmountTotalVat(Invoice facture){
		double vatAmount = 0.0;
		
		if(facture.getTva() > 0.0){
			vatAmount = (calculateTotalAmountNet(facture) * facture.getTva()) * 0.01;
		}
		
		return vatAmount;
	}
	
	public double calculateTotalAmount(Invoice facture){
		return calculateTotalAmountTotalVat(facture) + calculateTotalAmountNet(facture);
	}
	
	public double getTotalInvoicedForContract(Long id, String year,String mandat){
		return getFactureDao().getTotalInvoicedForContract(id,year,mandat);
	}
	
    public double getTotalInvoicedForExercise( int year){
    	return getFactureDao().getTotalInvoicedForExercise(year);
    }
	

    public double getTotalInvoicedForMonthAndAssociate(int month, int year, Long responsable){
		 return getFactureDao().getTotalInvoicedForMonthAndAssociate( month,  year,responsable);
	 }
	
	public double getTotalInvoicedForMonthAndManager(int month, int year, Long responsable){
		 return getFactureDao().getTotalInvoicedForMonthAndManager( month,  year,responsable);
	 }
	
	public Invoice getOneDetachedFromReference(String reference){
		return getFactureDao().getOneDetachedFromReference(reference);
	}
	
	public Invoice getOneDetached(Long id){
		return getFactureDao().getOneDetached(id);
	}
	
	public RemindInvoice getOneRemindInvoiceDetached(Long id){
		return getFactureDao().getOneRemindInvoiceDetached(id);
	}
	
	// get a contact by its id
    public Invoice getOne(Long id){
    	return getFactureDao().getOne(id);
    }

    // get all contacts
    public List<Invoice> getAll(){
    	return getFactureDao().getAll();
    }
    
    public List<Invoice> getAllUnpaidInvoices(){
    	return getFactureDao().getAllUnpaidInvoices();
    }
    
    /**
     * @param model
     * @return
     */
    public List<Invoice> getAllLibelleLike(String model){
    	return getFactureDao().getAllLibelleLike(model);
    }

    // save an invoice
    public Invoice saveOne(Invoice facture){    	
    	getFactureDao().saveOne(facture);    	
    	return facture;
    }
    
 // update an invoice
    public Invoice updateOne(Invoice facture){
    	
    	getFactureDao().updateOne(facture);    	
    	return facture;
    }
    
    public RemindInvoice updateOneReminder(RemindInvoice reminder){
    	RemindInvoice ret = getFactureDao().updateOneReminder(reminder);    	
    	return ret;
    }
    
    
    public RemindInvoice saveOneReminder(RemindInvoice reminder,Employee sender){
    	RemindInvoice ret = getFactureDao().saveOneReminder(reminder); 
    	
    	//Envoyer un mail aux secrétaires
		String subject = "Invoice reminder : " +ret.getLibelle() + " ";
		//String contents = "The invoice  [  " +facture.getReference() + " ] has been approved. Please take the necessary actions to send it to the customer...";
		String contents = "Please process with the following reminder : " + ret.getLibelle();
		String typeOfEmail=EmailData.TYPE_REMINDER_COMMUNICATION;
		
		sendEmailsToSecretaires(sender, subject, contents, typeOfEmail);
    	return ret;
    }
    
    
    
    
	
    
    
    public void updateExerciceAndBudget(Invoice facture){
    	
    	Invoice factureLive =  getOne(facture.getId());
    	
    	Mission project = factureLive.getProject();
    	
    	AnnualBudget budget = budgetDao.getOne(project.getAnnualBudget().getId());
    	Exercise exercise = exerciseDao.getOne(budget.getExercise().getId());
    	
    	Contract contract = contractDao.getOne(budget.getContract().getId());
    	
    	double effectiveAmount = getTotalInvoicedForContract(contract.getId(), Integer.toString(budget.getExercise().getYear()),project.getExercise());
    	
    	double totalEffectiveAmount = getTotalInvoicedForExercise(exercise.getYear());
    	
    	
    	budget.setEffectiveAmount(formatDouble(effectiveAmount));
    	
    	exercise.setTotalEffectiveAmount(formatDouble(totalEffectiveAmount));
       
    }
    
    
   private double  calculateTotalWorkedHoursForMission(Mission project){
    	return getFactureDao().calculateTotalWorkedHoursForMission(project);
   }
	
  private double calculateTotalAdvancesForMission(Mission project){
	   return getFactureDao().calculateTotalAdvancesForMission(project);
   }
	
   private double calculateTotalExtraCostsForMission(Mission project){
	   return getFactureDao().calculateTotalExtraCostsForMission(project);
   }
	
   
  
   
    

    // delete a contact by its id
    public void deleteOne(Long id){
    	
    	Invoice facture =  getOne(id);
    	
    	Mission project = facture.getProject();    	
    	AnnualBudget budget = budgetDao.getOne(project.getAnnualBudget().getId());
    	Exercise exercise = exerciseDao.getOne(budget.getExercise().getId());    	
    	Contract contract = contractDao.getOne(budget.getContract().getId());
    	getFactureDao().deleteOne(id);
    	
    	//Updating the related budgets in any previsonal execises
    	updateRelatedExercise(budget);
    	
    	
    	
    	double effectiveAmount = getTotalInvoicedForContract(contract.getId(), Integer.toString(budget.getExercise().getYear()),project.getExercise());
    	
    	double totalEffectiveAmount = getTotalInvoicedForExercise(exercise.getYear());
    	
    	
    	budget.setEffectiveAmount(formatDouble(effectiveAmount));
    	
    	exercise.setTotalEffectiveAmount(formatDouble(totalEffectiveAmount));
    	 
    }
    
 // delete  a payment by its id
    public void deletePayment(Long paymentId){
    	paymentDao.deleteOne(paymentId);
    }
    
    public void deleteOneFee(Long id){
    	getFactureDao().deleteOneFee(id);
    }

    /**
     * 
     * @param userId
     * @return
     */
    public List<Invoice> getAllFactureByCustomerId(Long customerId){
    	return getFactureDao().getAllFactureByCustomerId(customerId);
    }
    
    public InvoiceView  searchInvoices(  SearchInvoiceParam param ,boolean usePagination ,int firstPos,int LINEPERPAGE){
    	
    	List<Option> statusOptions = getInvoiceStatusOptions();
    	List<Option> exercicesOptions = this.getAllExercicesOptions();
		List<Option> typeOptions = getInvoiceTypeOptions();
    	List<InvoiceData> invoices = getFactureDao().searchInvoices(param,usePagination , firstPos, LINEPERPAGE);
    	
    	return new InvoiceView(invoices, param,
    				  statusOptions, exercicesOptions,
    				  typeOptions);
    }
    
    public long getTotalCount(SearchInvoiceParam param){
    	return getFactureDao().getTotalCount(param);
    }
    
    
    public InvoiceView  getInvoiceFromIdentifier(SearchInvoiceParam param,boolean usePagination ,int firstPos,int LINEPERPAGE){
    	
    	List<Option> statusOptions = getInvoiceStatusOptions();
    	List<Option> exercicesOptions = this.getAllExercicesOptions();
		List<Option> typeOptions = getInvoiceTypeOptions();
    	
    	//List<InvoiceData> invoices = getFactureDao().getInvoiceFromIdentifier(param.getInvoiceId());
    	
		List<InvoiceData> invoices = getFactureDao().searchInvoices(param, usePagination , firstPos, LINEPERPAGE);
    	return new InvoiceView(invoices, param,
				statusOptions,  exercicesOptions,
				  typeOptions);
    }
    
    public InvoiceReminderView  searchInvoiceReminders(  SearchInvoiceReminderParam param){
    	  List<RemindInvoice>  invoiceReminders  = getFactureDao().searchInvoiceReminders(param);
    	  List<Option> exercicesOptions = this.getAllExercicesOptions();
    	  return new  InvoiceReminderView(invoiceReminders,  param,
    				exercicesOptions);
    	
    }
    
    public InvoiceReminderView  getsearchInvoiceRemindersFromIdentifier(SearchInvoiceReminderParam param){    	    	 
    	 List<RemindInvoice>  invoiceReminders  = getFactureDao().searchInvoiceReminders(param);
   	     List<Option> exercicesOptions = this.getAllExercicesOptions();
   	     return new  InvoiceReminderView(invoiceReminders,  param,
   				exercicesOptions);
    }
    
    
    
    public PaymentsView  searchPayments( SearchPaymentParam param){
    	
    	RepositoryService repositoryService = (RepositoryService) context.getBean("repositoryService");
    	
		List<Option> bankOptions = repositoryService.getAllBankOptions();
		List<String> customerNames = null;
		List<Option> exercicesOptions = repositoryService.getExercicesOptions();//this.getAllExercicesOptions();
		List<Option> customersOptions = repositoryService.getCustomerOptions();
		if(param.getCustomerNameLike() == null || param.getCustomerNameLike().trim().length() ==0){
			param.setCustomerNameLike( customersOptions.get(0).getId()); 
		}
		
		List<Payment> payments = paymentDao.searchPayments(param);
    	
    	return new PaymentsView( payments,  param,
    		 bankOptions,  customerNames,exercicesOptions,customersOptions);
    	
    }
    
    public PaymentsView  getPaymentFromIdentifier(SearchPaymentParam param){
    	
    	RepositoryService repositoryService = (RepositoryService) context.getBean("repositoryService");
    	
    	List<Payment> payments = paymentDao.getPaymentFromIdentifier(param.getInvoiceReference());
		List<Option> bankOptions = repositoryService.getAllBankOptions();
		List<String> customerNames = null;
		List<Option> exercicesOptions = repositoryService.getExercicesOptions();//this.getAllExercicesOptions();
		List<Option> customersOptions = repositoryService.getCustomerOptions();
    	
    	return new PaymentsView( payments,  param,
    		 bankOptions,  customerNames,exercicesOptions,customersOptions);
    }
    
    
    public PaymentsView  getPaymentFromInvoiceIdentifier(SearchPaymentParam param){
    	RepositoryService repositoryService = (RepositoryService) context.getBean("repositoryService");
    	
    	List<Payment> payments = paymentDao.getPaymentFromInvoiceIdentifier(param.getInvoiceReference());
    	List<Option> bankOptions = repositoryService.getAllBankOptions();
		List<String> customerNames = null;
		List<Option> exercicesOptions = repositoryService.getExercicesOptions();//this.getAllExercicesOptions();
		List<Option> customersOptions = repositoryService.getCustomerOptions();
    	
    	return new PaymentsView( payments,  param,
    		 bankOptions,  customerNames,exercicesOptions,customersOptions);
    }
	
	
	public IFactureDao getFactureDao() {
		return factureDao;
	}
	public void setFactureDao(IFactureDao factureDao) {
		this.factureDao = factureDao;
	}
	public ITimesheetRowDao getTimesheetRowDao() {
		return timesheetRowDao;
	}
	public void setTimesheetRowDao(ITimesheetRowDao timesheetRowDao) {
		this.timesheetRowDao = timesheetRowDao;
	}


	public IMissionDao getProjectDao() {
		return projectDao;
	}


	public void setProjectDao(IMissionDao projectDao) {
		this.projectDao = projectDao;
	}
	
	public List<Option> getInvoiceStatusOptions(){
		List<Option> options= new ArrayList<Option>();
		options.add(new Option(Invoice.FACTURE_STATUS_CODE_PENDING,"pending"));
    	options.add(new Option(Invoice.FACTURE_STATUS_CODE_ONGOING , "ongoing"));
    	options.add(new Option(Invoice.FACTURE_STATUS_CODE_PAID , "paid"));
    	options.add(new Option(Invoice.FACTURE_STATUS_CODE_CANCELLED , "cancelled"));
		return options;
	}
    
    public List<Option> getInvoiceTypeOptions(){
    	List<Option> options= new ArrayList<Option>();
    	options.add(new Option(Invoice.TYPE_ADVANCE,"acompte"));
    	options.add(new Option(Invoice.TYPE_FINALBILL,"final"));
    	options.add(new Option(Invoice.TYPE_CREDITNOTE,"note de credit"));
		return options;
    }

	
	public IExerciseDao getExerciseDao() {
		return exerciseDao;
	}
	public void setExerciseDao(IExerciseDao exerciseDao) {
		this.exerciseDao = exerciseDao;
	}
	public IPaymentDao getPaymentDao() {
		return paymentDao;
	}
	public void setPaymentDao(IPaymentDao paymentDao) {
		this.paymentDao = paymentDao;
	}
	public IBankDao getBankDao() {
		return bankDao;
	}
	public void setBankDao(IBankDao bankDao) {
		this.bankDao = bankDao;
	}


	public IBudgetDao getBudgetDao() {
		return budgetDao;
	}


	public void setBudgetDao(IBudgetDao budgetDao) {
		this.budgetDao = budgetDao;
	}


	public IContractDao getContractDao() {
		return contractDao;
	}


	public void setContractDao(IContractDao contractDao) {
		this.contractDao = contractDao;
	}

	public IUserActionService getUserActionService() {
		return userActionService;
	}

	public void setUserActionService(IUserActionService userActionService) {
		this.userActionService = userActionService;
	}

	

	public IEmailService getEmailService() {
		return emailService;
	}

	public void setEmailService(IEmailService emailService) {
		this.emailService = emailService;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public ICustomerDao getCustomerDao() {
		return customerDao;
	}

	public void setCustomerDao(ICustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	public String getDefaultAmountBlanchiment() {
		return defaultAmountBlanchiment;
	}

	public void setDefaultAmountBlanchiment(String defaultAmountBlanchiment) {
		this.defaultAmountBlanchiment = defaultAmountBlanchiment;
	}

	public String getDefaultAmountCssf() {
		return defaultAmountCssf;
	}

	public void setDefaultAmountCssf(String defaultAmountCssf) {
		this.defaultAmountCssf = defaultAmountCssf;
	}
	

}
