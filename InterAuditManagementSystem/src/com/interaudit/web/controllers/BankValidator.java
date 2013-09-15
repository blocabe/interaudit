package com.interaudit.web.controllers;

import org.acegisecurity.userdetails.User;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

/**
 * Validator for LoginController
 */
public class BankValidator implements
        org.springframework.validation.Validator
{
    @SuppressWarnings("unchecked")
	public boolean supports(Class clazz)
    {
        return clazz.equals(com.interaudit.domain.model.Bank.class);
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
    	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name.empty", "mandatory");
    	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "code", "name.empty", "mandatory");
    	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "accountNumber", "name.empty", "mandatory");
    	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "codeBic", "name.empty", "mandatory");
    	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contactPerson", "name.empty", "mandatory");
    	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contactPersonPhone", "name.empty", "mandatory");
    	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contactPersonEmail", "name.empty", "mandatory");
    }
}
