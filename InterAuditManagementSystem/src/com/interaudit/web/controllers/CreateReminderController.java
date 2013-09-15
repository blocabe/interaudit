package com.interaudit.web.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.userdetails.User;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.interaudit.domain.model.Invoice;
import com.interaudit.domain.model.RemindInvoice;
import com.interaudit.service.IFactureService;
import com.interaudit.service.jobs.EwsManager;
import com.interaudit.util.CustomDoubleEditor;
import com.interaudit.util.DateUtils;
import com.interaudit.util.WebContext;

/**
 * Controller for the Login screen.
 */
public class CreateReminderController extends SimpleFormController
{
	
	private IFactureService factureService;

	/**
	 * Always returns a new User object
	 * 
	 * @see User
	 */
	protected Object formBackingObject(HttpServletRequest request) throws Exception
	{
		String id = request.getParameter("id"); 
				
		if(id != null ){
			RemindInvoice reminder = factureService.getOneRemindInvoiceDetached(Long.valueOf(id) );
			
			//Set the current user as sender
			if(reminder.getSender() == null){
				WebContext context =  (WebContext) request.getSession(false).getAttribute("context");
				reminder.setSender(context.getCurrentUser());
			}
			
			//Set the current date
			if(reminder.getRemindDate() == null){				
				reminder.setRemindDate(new Date());
			}
			return reminder;
			
		}
		else{
			
			
			String invoiceid = request.getParameter("invoiceid"); 
			
			if(invoiceid == null)
				return new com.interaudit.domain.model.RemindInvoice();
			else{
				//Emission d'un nouveau rappel de facture	
				Invoice facture = factureService.getOneDetached(Long.parseLong(invoiceid));
				int order = facture.getCountRemindsInvoice() + 1;
				Date endValidityDate = DateUtils.addDays(EwsManager.numDaysValidityForAlert);
				RemindInvoice newRappelActif = null;//new RemindInvoice(new Date(),endValidityDate,order,facture);
				
				return newRappelActif;
			}
		}
		
		
	}

	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception
			{
		try{
		
			String dateFormat = "yyyy-MM-dd";
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
/*
		Employee formUser = (Employee) command;
		Employee dbUser = userService.authenticate(formUser.getLogin(), formUser.getPassword(), true);
		
		if(dbUser == null){
			errors.reject("error.login.invalid","Invalid login name or password");
			return;
		}
		// create a new assist context object
		WebContext context =  new WebContext();
		
		context.setCurrentUser(dbUser);
		request.getSession(false).getAttribute("context");

		request.removeAttribute("lang");
		context.setUilang("EN");
		//request.getLocale().setDefault(Locale.UK);
		request.getSession(false).setAttribute("context", context);
		*/
		
    
	}

	/** returns ModelAndView(getSuccessView()) */
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception
	{
		 RemindInvoice reminder = (RemindInvoice) command;
		 WebContext context =  (WebContext) request.getSession(false).getAttribute("context");
		 
		 if(reminder.getId() == null ){
			 /*
			 if(reminder.isSent() == false){
				 reminder.setSent(true);
			 }
			 */
				 factureService.saveOneReminder(reminder,context.getCurrentUser()); 
				 /*
				 String invoiceid = request.getParameter("invoiceid"); 
				 Invoice facture = factureService.getOneDetached(Long.parseLong(invoiceid));
				 facture.getRappels().add(reminder);
				 factureService.updateOne(facture);
				 */
			 
		 }
		 else{
			 if(reminder.isSent() == false){
				 reminder.setSent(true);
				 factureService.updateOneReminder(reminder); 
			 } 
		 }
		 
		 
		 return new ModelAndView(getSuccessView());
        
		
	}

	public IFactureService getFactureService() {
		return factureService;
	}

	public void setFactureService(IFactureService factureService) {
		this.factureService = factureService;
	}

	

	

	

	

	

}
