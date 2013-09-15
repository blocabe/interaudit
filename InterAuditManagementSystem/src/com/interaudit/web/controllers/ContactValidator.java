package com.interaudit.web.controllers;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

/**
 * Validator for LoginController
 */
public class ContactValidator implements
        org.springframework.validation.Validator
{
    @SuppressWarnings("unchecked")
	public boolean supports(Class clazz)
    {
        return clazz.equals(com.interaudit.domain.model.Contact.class);
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
    	/*ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "name.empty", "mandatory");*/
    	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "name.empty", "mandatory");
    	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "jobTitle", "name.empty", "mandatory");
    	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "company", "name.empty", "mandatory");
    	
    	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "name.empty", "mandatory");
    	
    	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "businessPhone", "name.empty", "mandatory");
    	
    	
    }
}