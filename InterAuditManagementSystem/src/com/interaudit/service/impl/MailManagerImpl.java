package com.interaudit.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.transaction.annotation.Transactional;

import com.interaudit.domain.dao.IUserDao;
import com.interaudit.domain.model.EmailData;
import com.interaudit.domain.model.Employee;
import com.interaudit.service.IEmailService;
import com.interaudit.service.MailManager;
/*
com.interaudit.service.impl.MailManagerImpl
private JavaMailSender mailSender;
private IEmailService emailService;
*/
@Transactional
public class MailManagerImpl implements MailManager {

	private static final Logger logger = Logger.getLogger(MailManagerImpl.class);
	private JavaMailSender mailSender;
	private IEmailService emailService;
	private IUserDao userDao;	
	private String systemEmail;
   // private Resource templateBase;
   // private VelocityEngine velocityEngine;
    
  //  private String defaultFromName;
   // private String defaultFromAddress;
    /*
    private String textTemplateSuffix = "text.vm";
    private String htmlTemplateSuffix = "html.vm";
    */
    
  //  private String overrideToAddress;
   
	//private String toAddress;
	//private String toName;
	
	//private String templateDirectory;
    
    // setters (and optionally getters) for all private instance fields
    /*
    public void send(Map to, List objects, String template)throws MailException {
        send(null,null, to, null, null, objects, template);
    }
    */
	
	public void processPendingMessageJob(){		
		
		List<EmailData> messages =  emailService.getMessagesNotSent();
		if(messages != null && messages.size()>0){
			int count =0;
			for(EmailData message : messages) {
				boolean ret = send(message);
				if(ret == true){
					//Update du status de l'email	
					message.setSentDate(new Date());
					message.markAsDone();					
					count ++; 
					if( count == 100) return;				
				}
				else{
					message.markAsFailed();
				  }
				message = this.emailService.updateOne(message);
			}
		}		
	}
	
	public void processTimesheetReminderJob(){		
		List<Employee> employees =  userDao.getAll();
		if(employees != null && employees.size()>0){			
			for(Employee employee : employees) {
				try
				{
					SimpleMailMessage msg = new SimpleMailMessage();			
					msg.setSubject("TimeSheet Reminder...");
					msg.setFrom(systemEmail);
					msg.setTo(employee.getEmail());			
					msg.setText("Please submit your timesheet before leaving...");					
					mailSender.send(msg);					
				}
				catch (MailException ex)
				{
					logger.error(ex.getMessage());					
				}
			}
		}		
	}
	
	public boolean send(EmailData email) throws MailException{
		try
		{
			//Tester la validité du mail
			if(  email.getSender_address() == null  || 
				 email.getSender_address().length() == 0 ||
				 email.getReceiver_address() == null ||
				 email.getReceiver_address().length() == 0 ){				
				return false;
			}
				
			SimpleMailMessage msg = new SimpleMailMessage();			
			msg.setSubject(email.getMailSubject());
			msg.setFrom(email.getSender_address());
			msg.setTo(email.getReceiver_address());
			msg.setText(email.getMailContents());
			mailSender.send(msg);
			return true;
		}
		catch (MailException ex)
		{			
			logger.error(ex);
			return false;
		}
	
	}
    
	public void send(EmailData[] emails) throws MailException{
		
		int count = 0;
		for(EmailData email : emails){
			boolean ret = send(email);
			if(ret == true)count++;
			
			if(ret == true){
				  email.markAsDone();
				  email.setSentDate(new Date());				  
				  count ++; 
				  if( count == 100) return;
			  }else{
				  email.markAsFailed();
			  }
			
			email = this.emailService.updateOne(email);
			  
			  //Update du status de l'email			 			  
			  if(ret == true){
				  
			  }				  
		}		
		return;		
	}
    /*
    public void send(String senderName,
                     String senderAddress,
                     Map<String, String> to, 
                     Map<String, String> cc, 
                     Map<String, String> bcc,
                     String subject, 
                     Map<String, Object> mergeObjects, 
                     String templateLocation,
                     String emailTemplateWithSuffix) 
    throws MailException {
    	
    	// create a mime message using the mail sender implementation
    	MimeMessage message = mailSender.createMimeMessage();
    	    
    	// create the message using the specified template
    	MimeMessageHelper helper;
    	try {
    	    helper = new MimeMessageHelper(message, true, "UTF-8");
    	
    	
    	// add the 'from’ 
    	// add the sender to the message
    	helper.setFrom(senderAddress, senderName);
    	    
    	// add the 'cc’ 
    	if(cc != null && !cc.isEmpty()) {
    	    Iterator it = cc.keySet().iterator();
    	    while(it.hasNext()) {
    	        String name = (String) it.next();
    	        String recipient = (String) cc.get(name);
    	        if(overrideToAddress != null) {
    	            recipient = overrideToAddress;
    	        }
    	        helper.addCc(recipient, name);
    	    }
    	}
    	    
    	// add the 'bcc’ 
    	if(bcc != null && !bcc.isEmpty()) {
    	    Iterator it = bcc.keySet().iterator();
    	    while(it.hasNext()) {
    	        String name = (String) it.next();
    	        String recipient = (String) bcc.get(name);
    	        if(overrideToAddress != null) {
    	            recipient = overrideToAddress;
    	        }
    	        helper.addBcc(recipient, name);
    	    }
    	}
    	    
    	// add the 'to’ 
    	if(to != null && !to.isEmpty()) {
    	    Iterator it = to.keySet().iterator();
    	    while(it.hasNext()) {
    	        String name = (String) it.next();
    	        String recipient = (String) to.get(name);
    	        if(overrideToAddress != null) {
    	            recipient = overrideToAddress;
    	        }
    	        helper.addTo(recipient, name);
    	    }
    	} else {
    	    // use the default 'to’
    	    if(overrideToAddress != null) {
    	        helper.addTo(overrideToAddress, toName);
    	    } else {
    	        helper.addTo(toAddress, toName);
    	    }
    	}
    	
    	
    	//Contents management with Velocity templates
    	StringWriter text = new StringWriter();
    	VelocityEngineUtils.mergeTemplate(
    	  velocityEngine, templateLocation + "/" + emailTemplateWithSuffix, 
    	  mergeObjects, text);
    	   
    	StringWriter html = new StringWriter();
    	VelocityEngineUtils.mergeTemplate(
    	  velocityEngine, templateLocation + "/" + htmlTemplateSuffix, 
    	  mergeObjects, text);
    	  
    	    
    	text.close();
    	//html.close();
    	
    	helper.setText(text.toString(), html.toString());    	    
    	helper.setSubject(subject);

    	//Attachments management
    	// retrieve contents of the 'attachments’
    	File attachmentsFolder = templateBase.createRelative(
    	        "mail/" + templateDirectory + "/attachment").getFile();
    	File[] attachments = attachmentsFolder.listFiles();
    	if(attachments != null) {
    	    for(int i = 0; i < attachments.length; i++) {
    	        // add the attachment to the message helper
    	        helper.addAttachment(attachments[i].getName(), 
    	                new FileDataSource(attachments[i]));
    	    }
    	}
    	    
    	// retrieve contents of the 'inline’ 
    	File inlinesFolder = templateBase.createRelative(
    	        "mail/" + templateDirectory + "/inline").getFile();
    	File[] inlines = inlinesFolder.listFiles();
    	if(inlines != null) {
    	    for(int i = 0; i < inlines.length; i++) {
    	        // add the attachment to the message helper
    	        helper.addAttachment(inlines[i].getName(), 
    	                new FileDataSource(inlines[i]));
    	    }
    	}
    	
    	
    	//Send the message finally
    	mailSender.send(message);
    	
    	} catch(Exception e) {
    	    throw new MailPreparationException(
    	            "unable to create the mime message helper", e);
    	}
    	

    }
*/
	

	

	public JavaMailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public IEmailService getEmailService() {
		return emailService;
	}

	public void setEmailService(IEmailService emailService) {
		this.emailService = emailService;
	}

}
