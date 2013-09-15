package com.interaudit.web.controllers;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.acegisecurity.userdetails.User;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.interaudit.util.WebContext;

/**
 * Controller for the Login screen.
 */
public class CreateExpenseController extends SimpleFormController
{
	
	

	/**
	 * Always returns a new User object
	 * 
	 * @see User
	 */
	protected Object formBackingObject(HttpServletRequest request) throws Exception
	{
		return new com.interaudit.domain.model.Cost();
	}

	/** Forwards to success view, if already logged in */
	@SuppressWarnings({ "static-access",  "unchecked" })
	public ModelAndView showForm(HttpServletRequest request, HttpServletResponse response, BindException errors, Map controlModel) throws Exception
	{
		HttpSession session = request.getSession();

		if (session != null)
		{
			@SuppressWarnings("unused")
			WebContext context = (WebContext) request.getSession(false).getAttribute("context");
			context = null;
			session.invalidate();
			session = request.getSession(true);
			session.setMaxInactiveInterval(14400); // set timeout to 4 hours
		}
		request.getLocale().setDefault(Locale.UK);    
		
		return super.showForm(request, response, errors, controlModel);
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
		 return new ModelAndView(getSuccessView());
        
		
	}

	

	

	

	

	

}
