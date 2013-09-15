package com.interaudit.web.controllers;

import org.acegisecurity.userdetails.User;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

/**
 * Validator for LoginController
 */
public class EmployeeValidator implements
        org.springframework.validation.Validator
{
	
	
	
    @SuppressWarnings("unchecked")
	public boolean supports(Class clazz)
    {
        //return com.interaudit.domain.model.Employee.class.equals(clazz);
    	return clazz.isAssignableFrom(com.interaudit.domain.model.Employee.class);
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
    	
    	//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "name.empty", "mandatory");
    	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "name.empty", "mandatory");
    	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "name.empty", "mandatory");
    	//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dateOfBirth", "name.empty", "mandatory");
    	
    	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "position.name", "name.empty", "mandatory");
    	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "name.empty", "mandatory");
    	//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "name.empty", "mandatory");
    	
    	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login", "name.empty", "mandatory");
    	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "name.empty", "mandatory");
    	
    	
        
    }


}
