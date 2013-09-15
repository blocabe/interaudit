package com.interaudit.web.controllers;

import org.acegisecurity.userdetails.User;
import org.springframework.validation.Errors;

import com.interaudit.domain.model.Mission;

/**
 * Validator for LoginController
 */
public class MissionValidator implements
        org.springframework.validation.Validator
{
    @SuppressWarnings("unchecked")
	public boolean supports(Class clazz)
    {
        //return clazz.equals(com.interaudit.domain.model.Invoice.class);
    	return com.interaudit.domain.model.Mission.class.isAssignableFrom(clazz);
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
    	Mission mission = (Mission) command;
    	
    	
    	
		
		
		
    }
}
