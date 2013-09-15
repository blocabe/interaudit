package com.interaudit.web.controllers;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

/**
 * Validator for LoginController
 */
public class ContractValidator implements
        org.springframework.validation.Validator
{
    @SuppressWarnings("unchecked")
	public boolean supports(Class clazz)
    {
        return clazz.equals(com.interaudit.domain.model.Contract.class);
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
    	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customer.companyName", "name.empty", "mandatory");
    	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "name.empty", "mandatory");
    	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fromDate", "name.empty", "mandatory");
    	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "toDate", "name.empty", "mandatory");
    	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "amount", "name.empty", "mandatory");
    	
    }
}
