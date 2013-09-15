package com.interaudit.web.controllers;

import org.acegisecurity.userdetails.User;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.interaudit.domain.model.Invoice;

/**
 * Validator for LoginController
 */
public class InvoiceValidator implements
        org.springframework.validation.Validator
{
    @SuppressWarnings("unchecked")
	public boolean supports(Class clazz)
    {
        //return clazz.equals(com.interaudit.domain.model.Invoice.class);
    	return com.interaudit.domain.model.Invoice.class.isAssignableFrom(clazz);
    }

    /**
     * Validates an User command object. Ensures that login name
     * is set and that a password has been specified
     * Note: this is a validation of the entered values
     * (not null, correct range, etc.) and not the authentication
     * of the user/password submitted
     * @see User 
     */
    public void
    validate ( Object command, Errors errors)
    {
    	Invoice invoice = (Invoice) command;
    	//System.out.print(invoice.getAmountEuro());
    	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "honoraires", "name.empty", "mandatory");
    	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "libelle", "name.empty", "mandatory");
    	//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "name.empty", "mandatory");
    	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "billingAddress", "name.empty", "mandatory");
    	
    	
		
		
		
    }
}
