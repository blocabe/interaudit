package com.interaudit.web.controllers;

import org.springframework.validation.Errors;

/**
 * Validator for LoginController
 */
public class BudgetValidator implements
        org.springframework.validation.Validator
{
    @SuppressWarnings("unchecked")
	public boolean supports(Class clazz)
    {
        return clazz.equals(com.interaudit.domain.model.AnnualBudget.class);
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
    	/*
    	com.interaudit.domain.model.AnnualBudget budget = (com.interaudit.domain.model.AnnualBudget)command;
        if (budget == null)
        {
            return;
        }

        String login_name = user.getLogin();
        String password = user.getPassword();

        if (login_name == null || login_name.length () == 0)
        {
            errors.reject("error.login.invalid","Invalid login name or password");
            return;
        }
        if ( password == null || password.trim().length() < 5)
        {
            errors.reject("error.login.invalid","Invalid login name or password");
            return;
        }
        */
    }
}
