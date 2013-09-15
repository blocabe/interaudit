package com.interaudit.service.jobs;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.interaudit.domain.dao.IActivityDao;
import com.interaudit.domain.dao.IExerciseDao;
import com.interaudit.domain.model.Comment;
import com.interaudit.domain.model.EmailData;
import com.interaudit.domain.model.Employee;
import com.interaudit.domain.model.Invoice;
import com.interaudit.domain.model.Message;
import com.interaudit.domain.model.Timesheet;
import com.interaudit.domain.model.data.AlerteData;
import com.interaudit.domain.model.data.MissionToCloseData;
import com.interaudit.service.IEmailService;
import com.interaudit.service.IFactureService;
import com.interaudit.service.IUserActionService;
import com.interaudit.service.IUserService;
import com.interaudit.service.impl.RepositoryService;
import com.interaudit.util.HtmlUtil;


public class EwsManager {
	
	private static final Logger  logger = Logger.getLogger(EwsManager.class);
	private IActivityDao activityDao;
	private IExerciseDao exerciseDao;
	private IUserService userService;
	private JavaMailSender mailSender;
	private IFactureService invoiceService;
	private IUserActionService userActionService;
	private IEmailService emailService;
	private String systemEmail;
	private VelocityEngine velocityEngine;
	private RepositoryService repositoryService;


	//Remettre à 15 ou 30
	public static int maxDaysBeforeFirstAlert = 40;
	public static int numDaysValidityForAlert = 30;
	private static final String     EMAIL_ENCODING         = "UTF-8";
	
	
	public void processRefreshRepository(){
		RepositoryService.getInstance().refresh();
	}
	
	public void processGeneralMailSending(){
		
		//processEmailsForInvoiceReminderJob();
		
		//processPendingEmailDataJob();
		
		processPendingMessageJob();
		
		processPendingCommentsJob();
		
		processEmailsReminderForMissionsWithoutInvoiceAndAlreadyCharged();
				
		processEmailsReminderForMissionsAlertes();
		
	}
	
	public void processEmailsReminderForMissionsAlertes(){
		//public  
		
		String emailSubject = "Liste des alertes activées";  
		List<AlerteData> alertes = exerciseDao.checkMissionsAlertes();
		if(alertes != null && !alertes.isEmpty()){
			
			List<Employee> partners = userService.getPartners();
			List<Employee> directors = userService.getDirectors();
			List<Employee> managers = userService.getManagers();
			//String[] to = new String[managers.size()+ directors.size()+partners.size()];		
			List<String> to = new ArrayList<String> ();
			String from = systemEmail;
			
			int index=0;
			
			for(Employee employee: managers){
				if((employee.isUserActive()==true) && (employee.getEmail() != null)){
					to.add(employee.getEmail());
				}						
			}	
			
			for(Employee employee: partners){
				if((employee.isUserActive()==true) && (employee.getEmail() != null)){
					to.add(employee.getEmail());
				}				
			}
			
			for(Employee employee: directors){
				if((employee.isUserActive()==true) && (employee.getEmail() != null)){
					to.add(employee.getEmail());
				}				
			}
			
			
			//Construire le contenu du mail
			String emailContents = createContentsForEmailsForMissionsAlertes( alertes);
			
			boolean ret = sendMailForMissionsAlertes(from, (String[])to.toArray(new String[0]),emailSubject, emailContents );
			if(ret == true){
				if(logger.isInfoEnabled()){
					logger.info("processEmailsReminderForMissionsAlertes successfully ");
				}				
			}
			else{
				
					logger.error("processEmailsReminderForMissionsAlertes failed ");
								
			}
		
		}
	}
	
	public String createContentsForEmailsForMissionsAlertes(List<AlerteData> alertes){
    	StringBuffer buffer = new StringBuffer().append("<br/>");
    	for (AlerteData data : alertes){    	    
    	   
    	    
    	    buffer.append("<table width=\"100%\" border=\"1\" cellpadding=\"0\" cellspacing=\"0\"  height=\"40\" bgcolor=\"#CCCCCC\">");
    	    buffer.append("<tr>");
    	    buffer.append("<td   width=\"28%\" style=\"background: #3b5998; color: #FFFFFF; font-weight: bold; font-family: 'lucida grande', tahoma, verdana, arial, sans-serif; padding:		4px 8px; vertical-align: middle; font-size: 12px; letter-spacing: -0.03em; text-align: center;\">");
    	    buffer.append("<p>");
    	    buffer.append("<font size=\"3\" face=\"Arial, Helvetica, sans-serif\"><strong>");
    	    buffer.append( "MISSION : " + data.getTitleMission()+ " EXERCICE : " + data.getExerciseNumber() + " MANDAT : " + data.getMandat() + " NUMERO : " + data.getNumero());
    	    buffer.append("</strong></font>");
    	    buffer.append("</p>");
    	    buffer.append("</td>");
    	    buffer.append("</tr>");
    	    buffer.append("</table>");
    	    buffer.append("<br/>");
    	}
    	
    	logger.info(buffer.toString());
    	
    	return buffer.toString();

    }
	
	public  String createEmailsForMissionsAlertes( final String emailSubject,final String emailContents )throws Exception
	{
	    	Map<String,String> model = new HashMap<String,String>();
	    	model.put("title", emailSubject);    
	    	model.put("date", new Date().toString());
	    	//model.put("politesse", "Dear colleague");
	    	model.put("contents", emailContents);
	
	        /*
	        String text = VelocityEngineUtils.mergeTemplateIntoString(
	           velocityEngine, "com/interaudit/util/reminder_invoice_email.vm", model);
	           */
	    	
	    	String text = VelocityEngineUtils.mergeTemplateIntoString(
			           velocityEngine, "com/interaudit/util/reminder_missions_alerts_email.vm", model);
	        
	        return text;
	    
	}
	
	 /**
     * @param userFrom
     * @param userTo
     * @param message
     */
    public boolean sendMailForMissionsAlertes(final String from,final String[] to,final String emailSubject,final String emailContents ) {

        try {
        	//Tester la validite du mail
            if ((from == null) || (from.length() == 0) || (to == null)
                    || (to.length == 0)) {
            	
                logger.error("Invalid to ('" + to + "') or from ('" + from
                        + "') address for email '");
                        
                return false;
            }

            
            
            final String subject = emailSubject;

            final MimeMessage message = mailSender.createMimeMessage();

            // use the true flag to indicate you need a multipart message
            final MimeMessageHelper helper = new MimeMessageHelper(message, true, EMAIL_ENCODING);;
            helper.setTo(to);
            helper.setFrom(from);
            helper.setSubject(subject);
            // use the true flag to indicate the text included is HTML

            String finalContents = createEmailsForMissionsAlertes(emailSubject,emailContents );
            helper.setText(finalContents, true);
            
            final String todo = "to send email '" + message + "' { to='" + to
                    + "', from='" + from + "', subject='" + subject
                    + "', contents='" + finalContents + "' }";
             logger.info("Will try " + todo);
            
             try {
            	 mailSender.send(message);             
	        } catch (final Exception ex) {
	            logger.error("Exception: ", ex);
	           // throw new RuntimeException("Failed to send message...");
	        }

            //logger.info("Managed " + todo);
            return true;
        } catch (final Exception ex) {
            logger.error("Exception: ", ex);
            throw new RuntimeException("Failed to send message...");
        }

    }
	
	
	public void processEmailsReminderForMissionsWithoutInvoiceAndAlreadyCharged(){
    	String emailSubject = "Liste des missions en cours de réalisation et non-facturées ";  
    	List<String> missionstoRemind = exerciseDao.generateReminderForMissionsWithoutInvoiceAndAlreadyCharged();
		if(missionstoRemind != null && !missionstoRemind.isEmpty()){
			
			List<Employee> partners = userService.getPartners();
			List<Employee> directors = userService.getDirectors();
			List<Employee> managers = userService.getManagers();
			//String[] to = new String[managers.size()+ directors.size()+partners.size()];		
			List<String> to = new ArrayList<String> ();
			String from = systemEmail;
			
			int index=0;
			
			for(Employee employee: managers){
				if((employee.isUserActive()==true) && (employee.getEmail() != null)){
					to.add(employee.getEmail());
				}						
			}	
			
			for(Employee employee: partners){
				if((employee.isUserActive()==true) && (employee.getEmail() != null)){
					to.add(employee.getEmail());
				}				
			}
			
			for(Employee employee: directors){
				if((employee.isUserActive()==true) && (employee.getEmail() != null)){
					to.add(employee.getEmail());
				}				
			}
			
			
			//Construire le contenu du mail
			String emailContents = createContentsForEmailsForMissionsWithoutInvoiceAndAlreadyCharged( missionstoRemind);
			
			boolean ret = sendMailForMissionsWithoutInvoiceAndAlreadyCharged(from, (String[])to.toArray(new String[0]),emailSubject, emailContents );
			if(ret == true){
				logger.info("processEmailsForInvoiceReminderJob successfully ");
			}
			else{
				logger.error("processEmailsForInvoiceReminderJob failed ");
			}
		
		}
	}
	
	
	 public  String createEmailsForMissionsWithoutInvoiceAndAlreadyCharged( final String emailSubject,final String emailContents )throws Exception
		{
		    	Map<String,String> model = new HashMap<String,String>();
		    	model.put("title", emailSubject);    
		    	model.put("date", new Date().toString());
		    	//model.put("politesse", "Dear colleague");
		    	model.put("contents", emailContents);
		
		        /*
		        String text = VelocityEngineUtils.mergeTemplateIntoString(
		           velocityEngine, "com/interaudit/util/reminder_invoice_email.vm", model);
		           */
		    	
		    	String text = VelocityEngineUtils.mergeTemplateIntoString(
				           velocityEngine, "com/interaudit/util/reminder_missions_without_invoices_email.vm", model);
		        
		        return text;
		    
		}
	
	
	public String createContentsForEmailsForMissionsWithoutInvoiceAndAlreadyCharged(List<String> missionstoRemind){
    	StringBuffer buffer = new StringBuffer().append("<br/>");
    	for (String customerName : missionstoRemind){    	    
    	   
    	    
    	    buffer.append("<table width=\"100%\" border=\"1\" cellpadding=\"0\" cellspacing=\"0\"  height=\"40\" bgcolor=\"#CCCCCC\">");
    	    buffer.append("<tr>");
    	    buffer.append("<td   width=\"28%\" style=\"background: #3b5998; color: #FFFFFF; font-weight: bold; font-family: 'lucida grande', tahoma, verdana, arial, sans-serif; padding:		4px 8px; vertical-align: middle; font-size: 12px; letter-spacing: -0.03em; text-align: center;\">");
    	    buffer.append("<p>");
    	    buffer.append("<font size=\"3\" face=\"Arial, Helvetica, sans-serif\"><strong>");
    	    buffer.append(customerName.toUpperCase());
    	    buffer.append("</strong></font>");
    	    buffer.append("</p>");
    	    buffer.append("</td>");
    	    buffer.append("</tr>");
    	    buffer.append("</table>");
    	    buffer.append("<br/>");
    	}
    	
    	logger.info(buffer.toString());
    	
    	return buffer.toString();

    }
	
	
	 /**
     * @param userFrom
     * @param userTo
     * @param message
     */
    public boolean sendMailForMissionsWithoutInvoiceAndAlreadyCharged(final String from,final String[] to,final String emailSubject,final String emailContents ) {

        try {
        	//Tester la validite du mail
            if ((from == null) || (from.length() == 0) || (to == null)
                    || (to.length == 0)) {
            	
                logger.error("Invalid to ('" + to + "') or from ('" + from
                        + "') address for email '");
                        
                return false;
            }

            
            
            final String subject = emailSubject;

            final MimeMessage message = mailSender.createMimeMessage();

            // use the true flag to indicate you need a multipart message
            final MimeMessageHelper helper = new MimeMessageHelper(message, true, EMAIL_ENCODING);;
            helper.setTo(to);
            helper.setFrom(from);
            helper.setSubject(subject);
            // use the true flag to indicate the text included is HTML

            String finalContents = createEmailsForMissionsWithoutInvoiceAndAlreadyCharged(emailSubject,emailContents );
            helper.setText(finalContents, true);
            
            final String todo = "to send email '" + message + "' { to='" + to
                    + "', from='" + from + "', subject='" + subject
                    + "', contents='" + finalContents + "' }";
             logger.info("Will try " + todo);
            

            mailSender.send(message);

            //logger.info("Managed " + todo);
            return true;
        } catch (final Exception ex) {
            logger.error("Exception: ", ex);
            throw new RuntimeException("Failed to send message...");
        }

    }
	
	
	
	public void processBudgetsToCloseJob(){
		 
		 Calendar c = Calendar.getInstance(); 
		 Integer currentYear =c.get(Calendar.YEAR);
		 List<MissionToCloseData> resultList = invoiceService.listBudgetsToClose(currentYear);
		 if(resultList != null && resultList.size()>0){			 
			 for(MissionToCloseData message : resultList) {
					boolean ret = sendMessage(message);
				}			 
		 }
	}
	
	
	//Gestion des emails
	public void processPendingMessageJob(){		
		List<Message> messages = activityDao.getMessagesNotSent(10);
		if(messages != null && messages.size()>0){
			
			for(Message message : messages) {
				boolean ret = sendMessage(message);
				if(ret == true){
					message.setSentDate(new Date());					
					activityDao.updateOne( message);
				}
			}
		}		
	}
	
	
	public void processPendingEmailDataJob(){		
		List<EmailData> messages = activityDao.getEmailDatasNotSent(10);
		
		if(messages != null && messages.size()>0){
			
			for(EmailData message : messages) {
				boolean ret = sendMessage(message);
				if(ret == true){
					message.setSentDate(new Date());
					message.setStatus(EmailData.STATUS_DONE_OK);
					activityDao.updateOne( message);
				}
				else{
					message.setStatus(EmailData.STATUS_DONE_NOK);
					activityDao.updateOne( message);
				}
			}
		}
			
	}
	
	
	public void processPendingCommentsJob(){
		List<Comment> messages = activityDao.getCommentsNotSent(10);
        if(messages != null && messages.size()>0){
			
			for(Comment message : messages) {
				boolean ret = sendMessage(message);
				//if(ret == true){}
				message.setSent(new Date());
				
				activityDao.updateOne( message);
				
				
			}
		}		
	}
	
	
	public void processTimesheetToRemindsJob(){
		List<Timesheet> timesheets = activityDao.searchTimesheetToRemind();
        if(timesheets != null && timesheets.size()>0){			
			for(Timesheet timesheet : timesheets) {
				boolean ret = sendMessage(timesheet,"TimeSheet Reminder...","Please submit your timesheet for week [ " + timesheet.getWeekNumber()+" ] before leaving...");
			}
		}		
	}
	
	
	  
	
	
	
	//Gestion des rappels de timesheets
	public void processTimesheetReminderJob(){		
		//List<Employee> employees =  userService.getAll();
		
		List<Employee> employees = activityDao.searchEmployeeToRemindTimesheet();
		if(employees != null && employees.size()>0){
			String[]resu = new String[employees.size()];
			int index =0;
			for(Employee employee : employees) {		
				if((employee.isUserActive()==true) && (employee.getEmail() != null)){
					resu[index++] = employee.getEmail().toLowerCase();	
				}					   			
			}
			
			
			try
			{
			 boolean ret = sendMail( systemEmail,resu,"TimeSheet Reminder...","Please submit your timesheet before leaving..." );									
			}
			catch (MailException ex)
			{
				logger.error(ex.getMessage());
				//System.err.println(ex.getMessage());					
			}
		}		
	}
	
	
	
	//Gestion des rappels de factures
	public void processInvoiceReminderJob(){
		/*
		try
		{
				String subject = "Rappel facture ";
				String contents = null;
				String from = this.systemEmail;
				List<Employee> partners = userService.getPartners();
				List<Employee> secretaires = userService.getSecretaires();
				String[] to = new String[secretaires.size()];
				String[]cc = new String[partners.size()];
				StringBuffer sender_addresses = new StringBuffer();
				StringBuffer receiver_addresses = new StringBuffer();
				
				int index=0;
				for(Employee employee: secretaires){
					//to[index++]=employee.getEmail();
					index++;
					
				}
				
				index=0;
				for(Employee employee: partners){
					cc[index++]=employee.getEmail();
				}
				
				Date today = new Date();
				List<Invoice> unpaidFactures = invoiceService.getAllUnpaidInvoices();
				if(unpaidFactures != null && unpaidFactures.size()>0){
					
					
					for(Invoice facture : unpaidFactures) {
						
						Date sentDateFacture = facture.getSentDate();
						
						//The invoice has not been sent yet to the customer--Send an email to send it as soon as possible--The invoice must have been approved yet
						if(sentDateFacture == null && facture.isApproved() ){
							
							//Envoi d'un email aux secretaires + associés
							contents = "Veuillez envoyer la facture : " + facture.getLibelle() + "au client...";
							EmailData  emailData = new EmailData(  sender_addresses.toString(),  receiver_addresses.toString(),subject+facture.getLibelle(), contents);
							emailService.saveOne(emailData);
							//boolean ret =  sendMessage( from, to, cc, subject+facture.getLibelle(), contents);
						}
						else{//The invoice has  been sent yet to the customer
							
							//Calcul du nombre de jours entre la date courante et la date d'envoi de la facture
							int nbDays = DateUtils.getNbDays(sentDateFacture);
							
							//Tester si on a atteint le nombre de jours minimun avant d'envoyer le premier rappel
							if (nbDays > EwsManager.maxDaysBeforeFirstAlert){
								
								RemindInvoice rappelActif = facture.getActiveRemindInvoice();
								
								//Il n'y a aucun rappel actif
								if(rappelActif == null){
									
									//Emission d'un nouveau rappel de facture							
									int order = facture.getCountRemindsInvoice() + 1;
									Date endValidityDate = DateUtils.addDays(EwsManager.numDaysValidityForAlert);
									RemindInvoice newRappelActif = new RemindInvoice(today,endValidityDate,order,facture);
									facture.getRappels().add(newRappelActif);
									invoiceService.updateOne(facture);
									
									//Enregistrer l'action de l'utilisateur
									userActionService.saveOne(new UserAction(facture.getPartner(), "RAPPEL FACTURE : " + order, facture.getClass().getName(),
											facture.getId(), Calendar.getInstance().getTime()) );
						
									
									//Send an email to secretaries and associes
									contents = "Veuillez envoyer le rappel numero  : " +newRappelActif.getOrder() +" pour la facture : " + facture.getLibelle() + "au client...";
									//boolean ret =  sendMessage( from, to, cc,subject+facture.getLibelle(), contents);
									
								}
								else{//Il y a un rappel actif
									
									Date sentDateRappelFacture = rappelActif.getRemindDate();
									
									Date endValidityInvoiceReminder = rappelActif.getEndValidityDate();
									
									Date maxDate = DateUtils.max(today, endValidityInvoiceReminder);
									
									//Le rappel de facture n'a pas été envoyé depuis son émission et la date de fin de validité est passée
									if(sentDateRappelFacture == null && maxDate.equals(today)){
										
										//Envoi d'un email aux secretaires + associés et repousser la date de validité
										contents = "Veuillez envoyer le rappel numero  : " +rappelActif.getOrder() +" pour la facture : " + facture.getLibelle() + "au client...";
										//boolean ret =  sendMessage( from, to, cc,subject+facture.getLibelle(), contents);
										
										//Repousser la date de fin de validité
										nbDays = DateUtils.getNbDays(rappelActif.getStartValidityDate());
										
										Date finalValidityDate = DateUtils.addDays(nbDays);
										
										rappelActif.setEndValidityDate(finalValidityDate);
										
										invoiceService.updateOne(facture);
										
										//Enregistrer l'action de l'utilisateur
										userActionService.saveOne(new UserAction(facture.getPartner(), "PROLONGATION RAPPEL FACTURE : " + rappelActif.getOrder(), facture.getClass().getName(),
												facture.getId(), Calendar.getInstance().getTime()) );
										
									}
									else{//Le rappel de facture a été envoyé depuis son émission
										
										 //endValidityInvoiceReminder = rappelActif.getEndValidityDate();
										
										// maxDate = DateUtils.max(today, endValidityInvoiceReminder);
										
										//This reminder is no longer valid in fact make it invalid and create a another one.
										if( maxDate.equals(today)){
											
											rappelActif.setActive(false);
											invoiceService.updateOne(facture);
											
											userActionService.saveOne(new UserAction(facture.getPartner(), "DESACTIVATION RAPPEL FACTURE : " + rappelActif.getOrder(), facture.getClass().getName(),
													facture.getId(), Calendar.getInstance().getTime()) );
											
											int order = facture.getCountRemindsInvoice() + 1;
											Date endValidityDate = DateUtils.addDays(EwsManager.numDaysValidityForAlert);
											RemindInvoice newRappelActif = new RemindInvoice(today,endValidityDate,order,facture);
											facture.getRappels().add(newRappelActif);
											invoiceService.updateOne(facture);
											
											//Enregistrer l'action de l'utilisateur
											userActionService.saveOne(new UserAction(facture.getPartner(), "RAPPEL FACTURE : " + order, facture.getClass().getName(),
													facture.getId(), Calendar.getInstance().getTime()) );
											
											//Send an email to secretaries and associes
											contents = "Veuillez envoyer le rappel numero  : " +newRappelActif.getOrder() +" pour la facture : " + facture.getLibelle() + "au client...";
											//boolean ret =  sendMessage( from, to, cc,subject+facture.getLibelle(), contents);
													 
											
											
										}
										
										
										
									}
									
									
									
								}
								
							}
							
						}
						
						
						
						
						
						
					}
					
				}
		
		}
		catch (Exception ex)
		{
			System.err.println(ex.getMessage());					
		}
		*/
	}
	
	private String[] buildEmailToListFromString(String emailList){
		List<String> results = new ArrayList<String>();
		String[] resu;
		java.util.StringTokenizer tokenizer = new java.util.StringTokenizer(emailList, ";");

		while ( tokenizer.hasMoreTokens() ) {
			String email = (String)tokenizer.nextToken();
		    //System.out.println(email);
		    results.add(email);
		}
		
		resu = (String[]) results.toArray(new String[0]);
		return resu;
		
	}
	
	
	
    public void processEmailsForInvoiceReminderJob(){
    	String emailSubject = "Liste des factures non-payées émises depuis plus de 40 jours ";  
		Map<String, List<Invoice>> unpaidInvoices =invoiceService.buildMapReminders();
		if(unpaidInvoices != null && !unpaidInvoices.isEmpty()){
			
			List<Employee> partners = userService.getPartners();
			List<Employee> directors = userService.getDirectors();
			List<Employee> secretaires = userService.getSecretaires();
			//String[] to = new String[secretaires.size()+partners.size()];		
			List<String> to = new ArrayList<String> ();
			String from = systemEmail;
			
			int index=0;
			for(Employee employee: secretaires){
				if((employee.isUserActive()==true) && (employee.getEmail() != null)){
					to.add(employee.getEmail());
				}						
			}	
			
			for(Employee employee: partners){
				if((employee.isUserActive()==true) && (employee.getEmail() != null)){
					to.add(employee.getEmail());
				}				
			}
			
			for(Employee employee: directors){
				if((employee.isUserActive()==true) && (employee.getEmail() != null)){
					to.add(employee.getEmail());
				}				
			}
			
			
			//Construire le contenu du mail
			String emailContents = createContentsForEmailsForInvoiceReminder(unpaidInvoices);
			
			boolean ret = sendMailForInvoiceReminders(from, (String[])to.toArray(new String[0]),emailSubject, emailContents );
			if(ret == true){
				logger.info("processEmailsForInvoiceReminderJob successfully ");
			}
			else{
				logger.error("processEmailsForInvoiceReminderJob failed ");
			}
		
		}
	}
   
    public String createContentsForEmailsForInvoiceReminder(Map<String, List<Invoice>> unpaidInvoices){
    	StringBuffer buffer = new StringBuffer().append("<br/>");
    	for (Map.Entry<String, List<Invoice>> e : unpaidInvoices.entrySet()){    	    
    	    String customerName = e.getKey();
    	    List<Invoice> invoices = e.getValue();
    	    
    	    buffer.append("<table width=\"100%\" border=\"1\" cellpadding=\"0\" cellspacing=\"0\"  height=\"40\" bgcolor=\"#CCCCCC\">");
    	    buffer.append("<tr>");
    	    buffer.append("<td   width=\"28%\" style=\"background: #3b5998; color: #FFFFFF; font-weight: bold; font-family: 'lucida grande', tahoma, verdana, arial, sans-serif; padding:		4px 8px; vertical-align: middle; font-size: 12px; letter-spacing: -0.03em; text-align: center;\">");
    	    buffer.append("<p>");
    	    buffer.append("<font size=\"3\" face=\"Arial, Helvetica, sans-serif\"><strong>");
    	    buffer.append(customerName.toUpperCase());
    	    buffer.append("</strong></font>");
    	    buffer.append("</p>");
    	    buffer.append("</td>");
    	    
    	    for(Invoice facture : invoices){
    	    	//We avoid sending Invoice.TYPE_CREDITNOTE and amount = 0
    	    	if( (!facture.getType().equalsIgnoreCase(Invoice.TYPE_CREDITNOTE)) && ( facture.getAmountEuro() > 0.0) ){
    	    		buffer.append("<tr>");
    	    		buffer.append("<td   width=\"28%\" style=\"background: white; color: #FFFFFF; font-weight: bold; font-family: 'lucida grande', tahoma, verdana, arial, sans-serif; padding:		4px 8px; vertical-align: middle; font-size: 12px; letter-spacing: -0.03em; text-align: center;\">");
    	    		buffer.append("<p>");
    	    		buffer.append("<span style=\" color: black\"><font size=\"3\" face=\"Arial, Helvetica, sans-serif\"><strong>");	    			 
    	    		buffer.append(facture.getReference().toUpperCase() + " du " + facture.getSentDate() + " : <span style=\" color: red\">" + facture.getAmountEuro() + "</span>");    			 
    	    		buffer.append(" &euro; :"+ facture.getPartner().getCode() + "</strong></font></span>");     			 
    	    		buffer.append("</p>");
    	    		buffer.append("</td>");
    	    		buffer.append("</tr>");
    	    	}
    	    }
    	    
    	    buffer.append("</tr>");
    	    buffer.append("</table>");
    	    buffer.append("<br/>");
    	}
    	
    	logger.info(buffer.toString());
    	
    	return buffer.toString();

    }
    
    
    public boolean sendMessage(MissionToCloseData missionToClose)
	{	
		 try {			 
			    final String from = systemEmail;
			    final String to = missionToClose.getEmailManager();
				final String cc = missionToClose.getEmailAssociate();
				final String emailContents = createMissionToCloseEmailHtmlFromTemplate(  missionToClose.getCustomerName(),missionToClose.getMandat() );
				final String subject = "Cloture mission";
				
	        	//Tester la validite du mail
	            if ((from == null) || (from.length() == 0) || (to == null) || (to.length() == 0)) {
	            	
	                logger.error("Invalid to ('" + to + "') or from ('" + from
	                        + "') address for email '");
	                        
	                return false;
	            }

	            final HtmlUtil htmlUtil = new HtmlUtil();

	            String mixedHtmlContent = emailContents;
	            mixedHtmlContent = htmlUtil.convertEolToHtmlEol(mixedHtmlContent);
	            mixedHtmlContent = htmlUtil.searchAndReplaceUrl(mixedHtmlContent);
	            final StringBuffer contents = new StringBuffer(mixedHtmlContent);
	            
	            final MimeMessage message = mailSender.createMimeMessage();

	            // use the true flag to indicate you need a multipart message
	            final MimeMessageHelper helper = new MimeMessageHelper(message, true, EMAIL_ENCODING);;
	            helper.setTo(to);
	           // helper.setCc(cc);
	            helper.setFrom(from);
	            helper.setSubject(subject);
	            // use the true flag to indicate the text included is HTML
	            helper.setText(mixedHtmlContent, true);

	            final String todo = "to send email '" + message + "' { to='" + to
	                    + "', from='" + from + "', subject='" + subject
	                    + "', contents='" + mixedHtmlContent + "' }";
	            logger.info("Will try " + todo);

	            mailSender.send(message);

	            logger.info("Managed " + todo);
	            return true;
	        } catch (final Exception ex) {
	            logger.error("Exception: ", ex);
	            throw new RuntimeException("Failed to send message...");
	        }
	}
	
	
	/**
	 * @param userFrom
	 * @param userTo
	 * @param message
	 */
	public boolean sendMessage(Message message)
	{	
		final String from = message.getFrom().getEmail();
		//final String to = message.getTo().getEmail();
		final String[] to = buildEmailToListFromString(message.getEmailsList());
		final String emailSubject = message.getSubject();
		final String emailContents=  message.getContents();
		
		boolean ret = sendMail( from,to,emailSubject,emailContents );
		return ret;
	}
	
	
	public boolean sendMessage(Comment message)
	{	
		final String from = message.getSender_address();
		//final String to = message.getTo().getEmail();
		final String[] to = buildEmailToListFromString(message.getReceiver_address());
		final String emailSubject = "Timesheet rejected...";
		final String emailContents=  message.getText();
		
		boolean ret = sendMail( from,to,emailSubject,emailContents );
		return ret;
	}
	
	
	
	
	
	public boolean sendMessage(EmailData email)
	{	
		//final String to = email.getReceiver_address();
		final String[] to = buildEmailToListFromString(email.getReceiver_address());
        final String from = email.getSender_address();
		final String emailSubject = email.getMailSubject();
		final String emailContents=  email.getMailContents();
		
		if(email.getType().equalsIgnoreCase(EmailData.TYPE_FACTURATION_COMMUNICATION)){
			boolean ret = sendMailForApprobationInvoice(from, to,emailSubject, emailContents);			
			return ret;
		}
		else{
			boolean ret = sendMail( from,to,emailSubject,emailContents );
			return ret;
		}
		
		
	}
	
	
	public boolean sendMessage(Timesheet timesheet,final String emailSubject,final String emailContents ){
		
		boolean ret = sendMailTimesheetReminder(timesheet, emailSubject, emailContents  );
		return ret;  
	}
	
	
	private boolean sendMailTimesheetReminder(Timesheet timesheet ,final String emailSubject,final String emailContents ){
		
		
		 try {
			 final String subject = "Timesheet reminder...";
			    final String from = systemEmail;
				final String[] to = buildEmailToListFromString(timesheet.getUser().getEmail());
	        	//Tester la validite du mail
	            if ((from == null) || (from.length() == 0) || (to == null)
	                    || (to.length == 0)) {
	            	
	                logger.error("Invalid to ('" + to + "') or from ('" + from
	                        + "') address for email '");
	                        
	                return false;
	            }

	            final HtmlUtil htmlUtil = new HtmlUtil();

	            String mixedHtmlContent = emailContents;
	            mixedHtmlContent = htmlUtil.convertEolToHtmlEol(mixedHtmlContent);
	            mixedHtmlContent = htmlUtil.searchAndReplaceUrl(mixedHtmlContent);
	            final StringBuffer contents = new StringBuffer(mixedHtmlContent);
	            
	            final MimeMessage message = mailSender.createMimeMessage();

	            // use the true flag to indicate you need a multipart message
	            final MimeMessageHelper helper = new MimeMessageHelper(message, true, EMAIL_ENCODING);;
	            helper.setTo(to);
	            helper.setFrom(from);
	            helper.setSubject(subject);
	            // use the true flag to indicate the text included is HTML

	            String finalContents = createEmailHtmlFromTemplate(emailSubject,contents.toString() );
	            helper.setText(finalContents, true);

	            final String todo = "to send email '" + message + "' { to='" + to
	                    + "', from='" + from + "', subject='" + subject
	                    + "', contents='" + finalContents + "' }";
	            logger.info("Will try " + todo);

	           mailSender.send(message);

	            logger.info("Managed " + todo);
	            return true;
	        } catch (final Exception ex) {
	            logger.error("Exception: ", ex);
	            throw new RuntimeException("Failed to send message...");
	        }
	        
		
	}
	
	public boolean sendMessage(String from,String[] to, String subject,String contents){
		
		boolean ret = sendMail( from,to,subject,contents );
		return ret;
	}
	/*
	public boolean sendMessage(String from,String[] to, String[]cc,String subject,String contents){
		try
		{
			SimpleMailMessage msg = new SimpleMailMessage();			
			msg.setSubject(subject);
			msg.setFrom(from);
			msg.setTo(to);	
			msg.setCc(cc);
			msg.setText(contents);
			
			mailSender.send(msg);
			return true;
		}
		catch (MailException ex)
		{
			System.err.println(ex.getMessage());
			//logger.error(ex);
			return false;
		}
	
	}

	 */
	/**
     * @param userFrom
     * @param userTo
     * @param message
     */
    public boolean sendMail(final String from,final String[] to,final String emailSubject,final String emailContents ) {

        try {
        	//Tester la validite du mail
            if ((from == null) || (from.length() == 0) || (to == null)
                    || (to.length == 0)) {
            	
                logger.error("Invalid to ('" + to + "') or from ('" + from
                        + "') address for email '");
                        
                return false;
            }

            final HtmlUtil htmlUtil = new HtmlUtil();

            String mixedHtmlContent = emailContents;/*email.getMailContents();*/
            mixedHtmlContent = htmlUtil.convertEolToHtmlEol(mixedHtmlContent);
            mixedHtmlContent = htmlUtil.searchAndReplaceUrl(mixedHtmlContent);
            final StringBuffer contents = new StringBuffer(mixedHtmlContent);
            
            final String subject = emailSubject;

            final MimeMessage message = mailSender.createMimeMessage();

            // use the true flag to indicate you need a multipart message
            final MimeMessageHelper helper = new MimeMessageHelper(message, true, EMAIL_ENCODING);;
            helper.setTo(to);
            helper.setFrom(from);
            helper.setSubject(subject);
            // use the true flag to indicate the text included is HTML

            String finalContents = createEmailHtmlFromTemplate(emailSubject,contents.toString() );
            helper.setText(finalContents, true);
            /*
            final String todo = "to send email '" + message + "' { to='" + to
                    + "', from='" + from + "', subject='" + subject
                    + "', contents='" + finalContents + "' }";
            logger.info("Will try " + todo);
            */

            mailSender.send(message);

            //logger.info("Managed " + todo);
            return true;
        } catch (final Exception ex) {
            logger.error("Exception: ", ex);
            throw new RuntimeException("Failed to send message...");
            //return false;
        }

    }
    
    /**
     * @param userFrom
     * @param userTo
     * @param message
     */
    public boolean sendMailForInvoiceReminders(final String from,final String[] to,final String emailSubject,final String emailContents ) {

        try {
        	//Tester la validite du mail
            if ((from == null) || (from.length() == 0) || (to == null)
                    || (to.length == 0)) {
            	
                logger.error("Invalid to ('" + to + "') or from ('" + from
                        + "') address for email '");
                        
                return false;
            }

            
            
            final String subject = emailSubject;

            final MimeMessage message = mailSender.createMimeMessage();

            // use the true flag to indicate you need a multipart message
            final MimeMessageHelper helper = new MimeMessageHelper(message, true, EMAIL_ENCODING);;
            helper.setTo(to);
            helper.setFrom(from);
            helper.setSubject(subject);
            // use the true flag to indicate the text included is HTML

            String finalContents = createInvoiceReminderEmailHtmlFromTemplate(emailSubject,emailContents );
            helper.setText(finalContents, true);
            
            final String todo = "to send email '" + message + "' { to='" + to
                    + "', from='" + from + "', subject='" + subject
                    + "', contents='" + finalContents + "' }";
             logger.info("Will try " + todo);
            

            mailSender.send(message);

            //logger.info("Managed " + todo);
            return true;
        } catch (final Exception ex) {
            logger.error("Exception: ", ex);
            throw new RuntimeException("Failed to send message...");
        }

    }
    
    
    /**
     * @param userFrom
     * @param userTo
     * @param message
     */
    public boolean sendMailForApprobationInvoice(final String from,final String[] to,final String emailSubject,final String emailContents ) {

        try {
        	//Tester la validite du mail
            if ((from == null) || (from.length() == 0) || (to == null)
                    || (to.length == 0)) {
            	
                logger.error("Invalid to ('" + to + "') or from ('" + from
                        + "') address for email '");
                        
                return false;
            }

            
            
            final String subject = emailSubject;

            final MimeMessage message = mailSender.createMimeMessage();

            // use the true flag to indicate you need a multipart message
            final MimeMessageHelper helper = new MimeMessageHelper(message, true, EMAIL_ENCODING);
            helper.setTo(to);
            helper.setFrom(from);
            helper.setSubject(subject);
            // use the true flag to indicate the text included is HTML

            String finalContents = createApprobationInvoiceEmailHtmlFromTemplate(emailSubject,emailContents );
            helper.setText(finalContents, true);
            
            /*
            final String todo = "to send email '" + message + "' { to='" + to
                    + "', from='" + from + "', subject='" + subject
                    + "', contents='" + finalContents + "' }";
            logger.info("Will try " + todo);
			*/
            mailSender.send(message);

            //logger.info("Managed " + todo);
            return true;
        } catch (final Exception ex) {
            logger.error("Exception: ", ex);
            throw new RuntimeException("Failed to send message...");
        }

    }
    
    
    
    
    
    public  String createEmailHtmlFromTemplate( final String emailSubject,final String emailContents )throws Exception
	{
	    	Map<String,String> model = new HashMap<String,String>();
	    	model.put("title", emailSubject);    
	    	model.put("date", new Date().toString());
	    	model.put("politesse", "Dear colleague");
	    	model.put("contents", emailContents);
	
	        
	        String text = VelocityEngineUtils.mergeTemplateIntoString(
	           velocityEngine, "com/interaudit/util/template_email.vm", model);
	        
	        return text;
	    
	}
    
    
    
    public  String createMissionToCloseEmailHtmlFromTemplate( final String nomClient,final String mandatMission )throws Exception
	{
	    	Map<String,String> model = new HashMap<String,String>();
	    	model.put("nomClient", nomClient);   
	    	model.put("mandatMission", mandatMission);	    	
	    	model.put("date", new Date().toString());
	    	String text = VelocityEngineUtils.mergeTemplateIntoString(
	           velocityEngine, "com/interaudit/util/missiontoclose_email.vm", model);	        
	        return text;	    
	}
    
    
    public  String createApprobationInvoiceEmailHtmlFromTemplate( final String emailSubject,final String emailContents )throws Exception
	{
	    	Map<String,String> model = new HashMap<String,String>();
	    	model.put("title", emailSubject);    
	    	model.put("date", new Date().toString());
	    	//model.put("politesse", "Dear colleague");
	    	model.put("numFacture", emailContents);
	
	        
	        String text = VelocityEngineUtils.mergeTemplateIntoString(
	           velocityEngine, "com/interaudit/util/approbation_invoice_email.vm", model);
	        
	        return text;
	    
	}
    
    
    public  String createInvoiceReminderEmailHtmlFromTemplate( final String emailSubject,final String emailContents )throws Exception
	{
	    	Map<String,String> model = new HashMap<String,String>();
	    	model.put("title", emailSubject);    
	    	model.put("date", new Date().toString());
	    	//model.put("politesse", "Dear colleague");
	    	model.put("contents", emailContents);
	
	        
	        String text = VelocityEngineUtils.mergeTemplateIntoString(
	           velocityEngine, "com/interaudit/util/reminder_invoice_email.vm", model);
	        
	        return text;
	    
	}



	public IActivityDao getActivityDao() {
		return activityDao;
	}

	public void setActivityDao(IActivityDao activityDao) {
		this.activityDao = activityDao;
	}


	public JavaMailSender getMailSender() {
		return mailSender;
	}


	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}


	

	public String getSystemEmail() {
		return systemEmail;
	}


	public void setSystemEmail(String systemEmail) {
		this.systemEmail = systemEmail;
	}

	public IFactureService getInvoiceService() {
		return invoiceService;
	}

	public void setInvoiceService(IFactureService invoiceService) {
		this.invoiceService = invoiceService;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
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


	public VelocityEngine getVelocityEngine() {
		return velocityEngine;
	}


	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	public IExerciseDao getExerciseDao() {
		return exerciseDao;
	}

	public void setExerciseDao(IExerciseDao exerciseDao) {
		this.exerciseDao = exerciseDao;
	}

	public RepositoryService getRepositoryService() {
		return repositoryService;
	}

	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

}
