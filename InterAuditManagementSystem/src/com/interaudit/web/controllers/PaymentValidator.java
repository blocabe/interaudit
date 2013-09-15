package com.interaudit.web.controllers;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

/**
 * Validator for LoginController
 */
public class PaymentValidator implements
        org.springframework.validation.Validator
{
    @SuppressWarnings("unchecked")
	public boolean supports(Class clazz)
    {
        return clazz.equals(com.interaudit.domain.model.Payment.class);
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
    	//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "facture.reference", "name.empty", "mandatory");
    	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "bankCode", "name.empty", "mandatory");
    	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "amount", "name.empty", "mandatory");
    	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "paymentDate", "name.empty", "mandatory");
    	
   
    }
}
