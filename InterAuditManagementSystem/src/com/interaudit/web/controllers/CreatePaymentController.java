package com.interaudit.web.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.userdetails.User;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.interaudit.domain.model.Invoice;
import com.interaudit.domain.model.Payment;
import com.interaudit.domain.model.UserAction;
import com.interaudit.domain.model.data.Option;
import com.interaudit.service.IBankService;
import com.interaudit.service.IBudgetService;
import com.interaudit.service.IFactureService;
import com.interaudit.service.IPaymentService;
import com.interaudit.service.IUserActionService;
import com.interaudit.util.CustomDoubleEditor;
import com.interaudit.util.WebContext;


/**
 * Controller for the Login screen.
 */
public class CreatePaymentController extends SimpleFormController
{
	
	private IFactureService factureService;
	private IPaymentService paymentService;
	private IBankService bankService;
	private IUserActionService userActionService;
	
	/**
	 * Always returns a new User object
	 * 
	 * @see User
	 */
	protected Object formBackingObject(HttpServletRequest request) throws Exception
	{
		
		String id = request.getParameter("id"); 
		String invoiceid = request.getParameter("invoiceid");

		
		if(id == null || id.trim().length() == 0){
			//Payment payment = new com.interaudit.domain.model.Payment();
			//payment.setPaymentDate(new Date());
			//Invoice facture = factureService.getOneDetached(Long.valueOf(invoiceid));
			//payment.setFacture(facture);
			Payment payment = factureService.buildPaymentFromFacture(Long.valueOf(invoiceid));
			return payment;
		}
		else{
			return paymentService.getOneDetached(Long.valueOf(id) );
		}
	}

	
	@SuppressWarnings("unchecked")
	protected Map referenceData(HttpServletRequest request) {
		Map referenceData = new HashMap();
		//Country reference data
		List<Option> bankCodeOptions=bankService.getBankAsOptions2();
		
		 
		//Position reference data
	
		referenceData.put("bankCodeOptions", bankCodeOptions);
		
		return referenceData;
	}

	
	
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception
			{
		try{
		
			String dateFormat = "MM/dd/yyyy";
			SimpleDateFormat df = new SimpleDateFormat(dateFormat);
			df.setLenient(true);
			binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(
			df, true));
			
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
		
		if (errors.hasErrors())
		{
			return;
		}
		
		Payment payment = (Payment) command;
        if (payment == null)
        {
            return;
        }
        
        boolean reimbourse =  request.getParameter("reimbourse") !=  null;
        boolean escompte =  request.getParameter("escompte") !=  null;
        if( escompte && reimbourse){
        	errors.reject("typeErrorMessage", "Cannot be reimbourse and escompte...Please select only one choice...");
			request.getSession(false).setAttribute("typeErrorMessage", "Cannot be reimbourse and escompte...Please select only one choice...");
        }
        
        payment.setReimbourse(reimbourse);
        payment.setEscompte(escompte);
        
        if( request.getParameter("bankCode").trim().equalsIgnoreCase("-1")){
        	errors.reject("bankCodeErrorMessage","Invalid bank code");
			request.getSession(false).setAttribute("bankCodeErrorMessage", "Invalid bank code");
        }
        
        
        //Test if the invoice is valid
        Invoice invoice = factureService.getOneDetachedFromReference(request.getParameter("facture.reference").trim());
		
		if(invoice == null){
			errors.reject("factureReference","Invalid invoice code");
			request.getSession(false).setAttribute("factureReferenceErrorMessage", "Invalid invoice code");
			
		}
		
		
       //Check the amount 
        try{
        	 Double amount = Double.parseDouble(request.getParameter("amount").trim());
        	 if(amount < 0) throw new NumberFormatException();
        }catch(NumberFormatException nfe){
        	errors.reject("amount","Invalid amount");
			request.getSession(false).setAttribute("invalidAmountFormatErrorMessage", "Invalid amount");
        }
       
        if (errors.hasErrors())
		{
			return;
		}
    
	}

	/** returns ModelAndView(getSuccessView()) */
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception
	{
		ArrayList<String> messages = new ArrayList<String>();
		
		Payment payment = (Payment) command;
		
		//Test if the invoice is valid
        Invoice invoice = factureService.getOneDetachedFromReference(payment.getFacture().getReference().trim());
        
        WebContext context =  (WebContext) request.getSession(false).getAttribute("context");
		
		if(invoice == null){
			 messages.add("No invoice found with reference : " + payment.getFactureReference());
			 request.getSession(false).setAttribute("actionErrors", messages);
			 return new ModelAndView(getSuccessView());
			
		}
		
		payment.setFacture(invoice);
		
		 
		 if(payment.getId() == null){
			
			 paymentService.createPayment( payment);
			 
			//Enregistrer l'action de l'utilisateur
			userActionService.saveOne(new UserAction(context.getCurrentUser(), "ENREGISTREMENT PAYMENT " + payment.getReference(), payment.getClass().getName(),
					payment.getId(), Calendar.getInstance().getTime()) );
				
			 messages.add("The payment has been successfully saved for invoice : " + invoice.getReference());
						
		 }else{
			 
			 paymentService.updatePayment( payment);
			 messages.add("The payment has been successfully updated for invoice : " + invoice.getReference());
			 userActionService.saveOne(new UserAction(context.getCurrentUser(), "MODIFICATION PAYMENT " + payment.getReference(), payment.getClass().getName(),
					 payment.getId(), Calendar.getInstance().getTime()) );
		 }
		 
		 //Updating the invoice status
		 
		 Invoice invoice2 = factureService.getOne(invoice.getId());
		 factureService.updateInvoiceStatusafterPayment(invoice2);
		 /*
		 Invoice invoice2 = this.factureService.getOne(invoice.getId());
		 final ExecutorService  pool = Executors.newFixedThreadPool(1);
    	 pool.execute(new  Handler(factureService,invoice2));
    	 */  
    	 
    	
	
		 request.getSession(false).setAttribute("successMessage", messages);	
		 return  new ModelAndView(getSuccessView()+"?customerNameLike="+invoice2.getProject().getAnnualBudget().getContract().getCustomer().getId());		
	}



	public IFactureService getFactureService() {
		return factureService;
	}



	public void setFactureService(IFactureService factureService) {
		this.factureService = factureService;
	}



	public IPaymentService getPaymentService() {
		return paymentService;
	}



	public void setPaymentService(IPaymentService paymentService) {
		this.paymentService = paymentService;
	}


	public IBankService getBankService() {
		return bankService;
	}


	public void setBankService(IBankService bankService) {
		this.bankService = bankService;
	}


	public IUserActionService getUserActionService() {
		return userActionService;
	}


	public void setUserActionService(IUserActionService userActionService) {
		this.userActionService = userActionService;
	}


	
}

class Handler implements Runnable
{
	
 private Invoice invoice;
 private IFactureService factureService;

  Handler( IFactureService factureService,Invoice invoice)
  { 
	  this.factureService = factureService;
	  this.invoice =  invoice;
  }
  
  public void run()
  {
	  //Updating the invoice status	 
		 //this.factureService.updateInvoiceStatusafterPayment(invoice);
  }


}
