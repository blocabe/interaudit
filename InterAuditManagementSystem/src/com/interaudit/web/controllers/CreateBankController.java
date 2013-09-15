package com.interaudit.web.controllers;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.userdetails.User;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.interaudit.domain.model.Bank;
import com.interaudit.service.IBankService;
import com.interaudit.util.Utils;

/**
 * Controller for the Login screen.
 */
public class CreateBankController extends SimpleFormController
{
	
	private IBankService bankService;

	/**
	 * Always returns a new User object
	 * 
	 * @see User
	 */
	protected Object formBackingObject(HttpServletRequest request) throws Exception
	{
		String id = request.getParameter("id"); 
		
		if(id == null || id.trim().length() == 0){
			
			return new com.interaudit.domain.model.Bank();
		}
		else{
			return bankService.getOneDetached(Long.valueOf(id) );
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
		
		Bank bank = (Bank) command;
		if (request.getParameter("active") == null) {
			bank.setActive(false);
		}else{
			bank.setActive(true);
		}
		
		//Check if the account number is already used
		Bank otherBank = bankService.getOneFromAccountNumber(bank.getAccountNumber());
		if(otherBank != null){	
			if((bank.getId() == null) || (bank.getId().longValue() != otherBank.getId().longValue())){
				errors.reject("company","This account number is already used");
				request.getSession(false).setAttribute("accountNumberErrorMessage", "This account number is already used");
				
			}
		}
		
		
		//Check if the code number is already used
		otherBank = bankService.getOneFromCode(bank.getCode());
		if(otherBank != null){
			if((bank.getId() == null) || (bank.getId().longValue() != otherBank.getId().longValue())){
				errors.reject("company","This code is already used");
				request.getSession(false).setAttribute("codeErrorMessage", "This code is already used");
				
			}
		}
		
		
		//Check if the email of the contact has a correct format
		if( Utils.isValidEmail(bank.getContactPersonEmail()) == false){
	        	errors.reject("email","Invalid email format");
	        	request.getSession(false).setAttribute("emailErrorMessage", "Invalid email format");
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
		Bank bank = (Bank) command;
	    
		 if(bank.getId() == null){
			
			 getBankService().saveOne(bank);
			 messages.add("The bank : " + bank.getName() + " has been successfully created");
						
		 }else{
			 
			 getBankService().updateOne(bank);
			 messages.add("The bank : " + bank.getName()  + " has been successfully updated");
		 }
		
		request.getSession(false).setAttribute("successMessage", messages);
		return new ModelAndView(getSuccessView());
        
		
	}



	public IBankService getBankService() {
		return bankService;
	}



	public void setBankService(IBankService bankService) {
		this.bankService = bankService;
	}

	

	

	

	

	

}
