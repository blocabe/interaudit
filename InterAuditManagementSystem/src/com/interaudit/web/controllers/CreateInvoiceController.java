package com.interaudit.web.controllers;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.interaudit.domain.model.AddtionalFeeInvoice;
import com.interaudit.domain.model.Bank;
import com.interaudit.domain.model.Contact;
import com.interaudit.domain.model.Customer;
import com.interaudit.domain.model.EmailData;
import com.interaudit.domain.model.Employee;
import com.interaudit.domain.model.Invoice;
import com.interaudit.domain.model.Mission;
import com.interaudit.domain.model.UserAction;
import com.interaudit.domain.model.data.MissionInfo;
import com.interaudit.domain.model.data.Option;
import com.interaudit.service.IBankService;
import com.interaudit.service.IContactService;
import com.interaudit.service.ICustomerService;
import com.interaudit.service.IEmailService;
import com.interaudit.service.IFactureService;
import com.interaudit.service.IMissionService;
import com.interaudit.service.IUserActionService;
import com.interaudit.service.IUserService;
import com.interaudit.util.CustomDoubleEditor;
import com.interaudit.util.WebContext;

/**
 * Controller for the Login screen.
 */
public class CreateInvoiceController extends SimpleFormController
{
	
	private IFactureService service;
	private IUserService userService;
	private IMissionService missionService;
	private IContactService contactService;
	private ICustomerService customerService;
	private IBankService bankService;
	private String secretaryManagerCode;
	private IEmailService emailService;
	private IUserActionService userActionService;

	/**
	 * Always returns a new User object
	 * 
	 * @see User
	 */
	

	/** Forwards to success view, if already logged in */
	protected Object formBackingObject(HttpServletRequest request) throws Exception
	{
		 WebContext context =  (WebContext) request.getSession(false).getAttribute("context");
		 //String cmd = request.getParameter("command");
		 String invoiceid = request.getParameter("id");
		 String customerid = request.getParameter("customerid"); 
		 String type = request.getParameter("type");		 
		 String tvaAstring = request.getParameter("tva"); 
		 String honorairesAstring = request.getParameter("honoraires");			
			
		
		
		if(invoiceid == null || invoiceid.trim().length() == 0){
			// create a new assist context object
			float tva = 0.0f;
			double honoraires = 0.0d;			
			//converts the tva amount
			try{
				tva= Float.parseFloat(tvaAstring);
			}catch(NumberFormatException nfe){
				
				tva = 0.0f;
			}
			
			//converts the honoraires amount
			if(!honorairesAstring.equalsIgnoreCase("auto")){
				try{			
					honoraires= Double.parseDouble(honorairesAstring);			
				}catch(NumberFormatException nfe){
					honoraires = 0.0d;
				}	
			}
			else{
				honoraires = 0.0d;
			}
	     
		 
		 
		 Invoice result = context.getCurrentInvoice();
		 
		
		 
		 if(result != null)
	     {			
	         return result;
	     }
	        
			
			Employee creator = userService.getOne(context.getCurrentUser().getId());
			
			String libelle = null;
			if(type.equalsIgnoreCase("AD")){
				libelle="Avance";
			}else if(type.equalsIgnoreCase("CN")){
				libelle="Note de credit";
			}else if(type.equalsIgnoreCase("FB")){
				libelle="Facture finale";
			}
			
			Contact contact = new Contact();
			contact.setId(-1L);
			
			Bank bank = new Bank();
			bank.setId(-1L);
			
			
			
			Invoice facture = null;//service.createFactureForMission(Long.parseLong(customerid),creator,libelle,type, tva, honoraires);
			//facture.setContact(contact);
			//facture.setBank(bank);
			context.setCurrentInvoice(facture);
			return facture;
		}
		else{			
			Invoice facture = service.getOneDetached(Long.valueOf(invoiceid) );
			context.setCurrentInvoice(facture);
			return facture;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	protected Map referenceData(HttpServletRequest request) {
		Map referenceData = new HashMap();
		String invoiceid = request.getParameter("id");
		String missionid = request.getParameter("customerid"); 
		Mission mission = null;
		Customer customer = null;
		if(missionid!= null && ( invoiceid == null || invoiceid.trim().length() == 0)){
			mission = missionService.getOne( Long.parseLong(missionid));
			referenceData.put("targetMission", missionid);
			
			customer = customerService.getOneCustomerDetachedFromMissionId(Long.parseLong(missionid));			
			customer = customerService.getOneDetached(customer.getId());			
			List<Option> contactsOptions =  customer.getContactOptions();
			//referenceData.put("contactsOptions", contactsOptions);
			request.getSession(false).setAttribute("contactsOptions", contactsOptions);
		}
		else if(invoiceid != null){
			Invoice facture = service.getOne(Long.valueOf(invoiceid) );
			mission = missionService.getOne( facture.getProject().getId());
			referenceData.put("targetMission", mission.getId());
			
			customer = customerService.getOneCustomerDetachedFromMissionId(mission.getId());			
			customer = customerService.getOneDetached(customer.getId());			
			List<Option> contactsOptions =  customer.getContactOptions();
			//referenceData.put("contactsOptions", contactsOptions);
			request.getSession(false).setAttribute("contactsOptions", contactsOptions);
		}
		
	
		MissionInfo missionInfo = service.buildMissionInfo(mission,customer);
		referenceData.put("missionInfo",missionInfo);
		
		List<Option> banksOptions = bankService.getBankAsOptions();
		referenceData.put("banksOptions",banksOptions);
		
		return referenceData;
	}
	
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception
			{
		try{
		/*
			String dateFormat = "MM/dd/yyyy";
			SimpleDateFormat df = new SimpleDateFormat(dateFormat);
			df.setLenient(true);
			binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(
			df, true));
			*/
			
			binder.registerCustomEditor(double.class, new CustomDoubleEditor());

	
			
		}catch(Exception e){
			//System.out.println(e.getMessage());
			throw e;
		}
		

	}


	/**
	 * Validates user/password against database
	 * 
	 * This is the authentication/authorisation check
	 * 
	 */
	public void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception
	{
		  String action = org.springframework.web.bind.ServletRequestUtils.getStringParameter(request, "command");
		  
			 if(action == null ||( !action.equalsIgnoreCase("addfee") && !action.equalsIgnoreCase("removeFee"))){
				
				 if (errors.hasErrors())
					{
						return;
					}
				 
				    Invoice invoice = (Invoice) command;
					
				 
					String amountAsString = request.getParameter("honoraires");
					Double amount = null;
			  
			        try{
			        	 amount = Double.parseDouble(amountAsString);
			        	 if(amount < 0) throw new NumberFormatException();
			        }catch(NumberFormatException nfe){
			        	errors.reject("amountEuro","Invalid amount");
						request.getSession(false).setAttribute("invalidAmountFormatErrorMessage", "Invalid amount");
			        }
			        
			        //Verifier que la somme des avances ne dépasse pas 90% du budget total
			        if(invoice.getId()==null && invoice.getType().equalsIgnoreCase(Invoice.TYPE_ADVANCE)){
			        	Mission mission = missionService.getOneDetached( invoice.getProject().getId());
			        	
				        double budget = mission.getAnnualBudget().getExpectedAmount() + mission.getAnnualBudget().getReportedAmount();
						double totalInvoiced = service.getTotalInvoicedForMission(mission.getId());
						totalInvoiced += amount;
						if(invoice.getId() != null){
							totalInvoiced -= invoice.getAmountNetEuro();
						}
						budget = budget * 0.9;
						if(totalInvoiced > budget){
							errors.reject("amountEuro","Invalid amount");
							request.getSession(false).setAttribute("invalidAmountFormatErrorMessage", "Total avances exceeds 90% of budget");
						}			        	
			        }
			        
			        /*
			        
			       Long idContact = invoice.getContact().getId();
			       if(idContact == -1L){
			    	   errors.reject("invalidContactErrorMessage","Invalid Recipient");
					request.getSession(false).setAttribute("invalidContactErrorMessage", "Invalid Recipient"); 
			       }
			       */
			       
			       Long idBank = invoice.getBank().getId();
			       if(idBank == -1L){
			    	   errors.reject("invalidBankErrorMessage","Invalid Bank");
					request.getSession(false).setAttribute("invalidBankErrorMessage", "Invalid Bank"); 
			       }
			       
			       
			       if (errors.hasErrors())
					{
						return;
					}
			       
			 }
		
		
		
		
	}

	/** returns ModelAndView(getSuccessView()) */
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception
	{
		
		ArrayList<String> messages = new ArrayList<String>();
	    Invoice facture = (Invoice) command;
	    
	    String action = org.springframework.web.bind.ServletRequestUtils.getStringParameter(request, "command");
		 if(action != null && action.equalsIgnoreCase("addfee")){
			 
			 logger.info("save_user_communication");
	    	 String code = request.getParameter("justification"); 
	    	 
	    	 String justification = "";
	    	 if(code.equalsIgnoreCase("Supplement travail")){
	    		 //justification = AddtionalFeeInvoice.text1; 
	    	 }
	    	 
	    	 if(code.equalsIgnoreCase("Loi blanchiment")){
	    		 //justification = AddtionalFeeInvoice.text2; 
	    	 }
	    	 
	    	 if(code.equalsIgnoreCase("Frais Bureau")){
	    		 //justification = AddtionalFeeInvoice.text3; 
	    	 }
	    	 
	    	 String prix = request.getParameter("prix");   
	    	 String customerid = request.getParameter("customerid");   
	    	 String type = request.getParameter("type");
	    	 String invoiceid = request.getParameter("id");
	    	 
	    	 AddtionalFeeInvoice fee = new AddtionalFeeInvoice(code, justification, Double.parseDouble(prix));
	    	 facture.getFrais().add(fee);
	    	 fee.setFacture(facture);
	    	 
	    	 if(invoiceid == null){
	    		 //return  showForm ( request, errors,"redirect:showInvoiceRegisterPage.htm?customerid=" +customerid +"&type=" +type+"&command=addfee");
	    		 return  showForm ( request, errors,"redirect:showInvoiceRegisterPage.htm?customerid=" +customerid +"&type=" +type);
	    	 }else{
	    		// return  showForm ( request, errors,"redirect:showInvoiceRegisterPage.htm?command=addfee&id="+invoiceid );
	    		 return  showForm ( request, errors,"redirect:showInvoiceRegisterPage.htm?id="+invoiceid );
	    	 } 
		 }
		 else if(action != null &&  action.equalsIgnoreCase("removeFee") ) {
			 
			    //WebContext context =  (WebContext) request.getSession(false).getAttribute("context");
	        	//String feeCode = request.getParameter("feecode");
			    String feeCode = org.springframework.web.bind.ServletRequestUtils.getStringParameter(request, "feecode");
	        	 String customerid = request.getParameter("customerid");   
		    	 String type = request.getParameter("type");
		    	 String invoiceid = request.getParameter("id");
	        	//Set<AddtionalFeeInvoice> atts = context.getCurrentInvoice().getFrais();
		    	 Set<AddtionalFeeInvoice> atts = facture.getFrais();
	        	
	        	Iterator<AddtionalFeeInvoice> itr = atts.iterator();
	        	AddtionalFeeInvoice att = null;
	        	
	        	while (itr.hasNext())
	        	{
	        		att = itr.next();
	        		if (att.getCode().equals(feeCode))
	        		{	
	        		  Long id = att.getId();
	        		  atts.remove(att);
	        		  if(id != null)
	        		  {
	        			  service.deleteOneFee( id);  
	        		  }	        		  
	        		  break;
	        		}  
	        	}
	        	
	        	if(invoiceid == null){
		    		// return  showForm ( request, errors,"redirect:showInvoiceRegisterPage.htm?customerid=" +customerid +"&type=" +type+"&command=addfee");
	        		 return  showForm ( request, errors,"redirect:showInvoiceRegisterPage.htm?customerid=" +customerid +"&type=" +type);
		    	 }else{
		    		 //return  showForm ( request, errors,"redirect:showInvoiceRegisterPage.htm?command=addfee&id="+invoiceid );
		    		 return  showForm ( request, errors,"redirect:showInvoiceRegisterPage.htm?id="+invoiceid );
		    	 } 
	        }
		 
		 else{
			    WebContext context =  (WebContext) request.getSession(false).getAttribute("context");
			 	//Contact contact = contactService.getOneDetached(facture.getContact().getId());
			    //facture.setContact(contact);
			    
			    Bank bank = getBankService().getOneDetached(facture.getBank().getId());
			    facture.setBank(bank);
			    boolean approvedInvoice = false;
			    boolean sentInvoice = false;
			    if(facture.isApproved() == true && facture.isSent() == false){
			    	facture.setStatus(Invoice.FACTURE_STATUS_CODE_APPROVED);	
			    	approvedInvoice = true;			    	
			    }
			    
			    if(facture.isSent() == true){
			    	facture.setStatus(Invoice.FACTURE_STATUS_CODE_ONGOING);
			    	facture.setSentDate(new Date());
			    	sentInvoice = true;
			    }
			    
			    facture.setAmountNetEuro(service.calculateTotalAmountNet(facture));
		    	facture.setAmountEuro(service.calculateTotalAmount(facture));
			    
				if(facture.getId() == null){
					
					//Calculer le numero de la facture
					String year = facture.getProject().getExercise();
			    	int orderIntheYear =  service.getMaxYearSequence(year) + 1;
			    	//System.out.print("orderIntheYear : " + orderIntheYear);
			    	
			    	NumberFormat formatter = new DecimalFormat("000");
					String formattedNumber = formatter.format(orderIntheYear); 
					String reference = "F-" + facture.getExercise().substring(facture.getExercise().length()-2) +"/" +formattedNumber;
					reference = reference.toUpperCase(); 
					facture.setReference(reference);
					
					//Enregistrer la facture
					service.saveOne(facture);
					
					//Enregistrer l'action de l'utilisateur
					userActionService.saveOne(new UserAction(context.getCurrentUser(), "CREATION FACTURE", facture.getClass().getName(),
								facture.getId(), Calendar.getInstance().getTime()) );
					
					messages.add("The invoice : " + facture.getReference() + " has been successfully created");								
				 }else{					 
					service.updateOne(facture);
					service.updateExerciceAndBudget(facture);  
					messages.add("The invoice : " + facture.getReference() + " has been successfully updated");
				 }
				
				if( approvedInvoice == true){
					String secretaryCode = getSecretaryManagerCode();
					Employee to = userService.getOneFromCode(secretaryCode);
					String subject = "Invoice [ " +facture.getReference() + " ] approved";
					String contents = "The invoice  [  " +facture.getReference() + " ] has been approved. Please take the necessary actions to send it to the customer...";
					
					//Enregistrer l'action de l'utilisateur
					userActionService.saveOne(new UserAction(context.getCurrentUser(), "APPROBATION FACTURE", facture.getClass().getName(),
							facture.getId(), Calendar.getInstance().getTime()) );
					
					//Save a communication email for the message
					EmailData emailData = new EmailData( context.getCurrentUser(), to,subject, contents, EmailData.TYPE_FACTURATION_COMMUNICATION);
					emailService.saveOne(emailData);
					
				}
				
				if( sentInvoice == true){
					
					Employee to = facture.getPartner();
					String subject = "Invoice [ " +facture.getReference() + " ] sent";
					String contents = "The invoice  [ " +facture.getReference() + " ] has been sent to the customer...";
			    	//Save a communication email for the message
					
					//Enregistrer l'action de l'utilisateur
					userActionService.saveOne(new UserAction(context.getCurrentUser(), "ENVOI FACTURE", facture.getClass().getName(),
							facture.getId(), Calendar.getInstance().getTime()) );
					
			    	//Save a communication email for the message
					EmailData emailData = new EmailData( context.getCurrentUser(), to,subject, contents, EmailData.TYPE_FACTURATION_COMMUNICATION);
					emailService.saveOne(emailData);
				}
				   
				
				
				 context.setCurrentInvoice(null);
				 request.getSession(false).setAttribute("successMessage", messages);
				 return new ModelAndView(getSuccessView()); 
		 }
	    
	    
		
	}

	public IFactureService getService() {
		return service;
	}

	


	public void setService(IFactureService service) {
		this.service = service;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}


	public IMissionService getMissionService() {
		return missionService;
	}


	public void setMissionService(IMissionService missionService) {
		this.missionService = missionService;
	}


	public IContactService getContactService() {
		return contactService;
	}


	public void setContactService(IContactService contactService) {
		this.contactService = contactService;
	}


	


	public ICustomerService getCustomerService() {
		return customerService;
	}


	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}


	public IBankService getBankService() {
		return bankService;
	}


	public void setBankService(IBankService bankService) {
		this.bankService = bankService;
	}


	public String getSecretaryManagerCode() {
		return secretaryManagerCode;
	}


	public void setSecretaryManagerCode(String secretaryManagerCode) {
		this.secretaryManagerCode = secretaryManagerCode;
	}


	public IEmailService getEmailService() {
		return emailService;
	}


	public void setEmailService(IEmailService emailService) {
		this.emailService = emailService;
	}


	public IUserActionService getUserActionService() {
		return userActionService;
	}


	public void setUserActionService(IUserActionService userActionService) {
		this.userActionService = userActionService;
	}


	

	

	

	

	

}
