package com.interaudit.web.controllers;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.acegisecurity.userdetails.User;
import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.interaudit.domain.model.Employee;
import com.interaudit.domain.model.Exercise;
import com.interaudit.service.IExerciseService;
import com.interaudit.service.IUserService;
import com.interaudit.util.WebContext;
import com.interaudit.util.WebUtils;

/**
 * Controller for the Login screen.
 */
public class LoginController extends SimpleFormController
{
	
	//private final static String DIRECT_SUPPORT_PAGE = "redirect:request.htm?action=new_request";	
	//private final static String DIRECT_SUPPORT_LIST_REQUEST_PAGE = "redirect:requestlist.htm?command=support";
	private IUserService userService;
	private IExerciseService exerciseService;
	private static final Logger  logger      = Logger.getLogger(LoginController.class);

	/**
	 * Always returns a new User object
	 * 
	 * @see User
	 */
	protected Object formBackingObject(HttpServletRequest request) throws Exception
	{
		return new com.interaudit.domain.model.Employee();
	}

	/** Forwards to success view, if already logged in

	public ModelAndView showForm(HttpServletRequest request, HttpServletResponse response, BindException errors, Map controlModel) throws Exception
	{
		logger.info("LoginController.showForm : Entrance at : " + new Date());
		HttpSession session = request.getSession();

		if (session != null)
		{
			//@SuppressWarnings("unused")
			//WebContext context = (WebContext) request.getSession(false).getAttribute("context");
			//context = null;
			session.invalidate();
			logger.info("LoginController.showForm : Session has been invalidated at : " + new Date());
			//session = request.getSession(true);
			//session.setMaxInactiveInterval(14400); // set timeout to 4 hours
		}
		//request.getLocale().setDefault(Locale.FRANCE); 
		
		
		
		return super.showForm(request, response, errors, controlModel);
	}
	 */
	private void readAndSetVersionNumber(final HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.setAttribute("build_version", WebUtils.getApplicationVersion(request));
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

		Employee formUser = (Employee) command;
		Employee dbUser = userService.authenticate(formUser.getLogin(), formUser.getPassword(), true);
		
		if(dbUser == null){
			errors.reject("error.login.invalid","Invalid login name or password or Inactive account...");
			return;
		}
		// create a new assist context object
		WebContext context =  new WebContext();
		
		HttpSession session = request.getSession();

		if (session != null)
		{				
			session.invalidate();
			logger.info("onBindAndValidate : Session has been invalidated at : " + new Date());
			session = request.getSession(true);
		}
		
		
		readAndSetVersionNumber(request);
		context.setCurrentUser(dbUser);
		request.getSession(true).setAttribute("context",context);

		request.removeAttribute("lang");
		context.setUilang("EN");
		//request.getLocale().setDefault(Locale.UK);
		request.getSession(false).setAttribute("context", context);
		Exercise currentExercise = (Exercise) getServletContext().getAttribute("currentExercise");
		if (currentExercise == null) {			
			Integer maxYear = exerciseService.getFirstOnGoingExercise();
			 if(maxYear == null){
				//maxYear = exerciseService.getMaxYear();
				 maxYear =  exerciseService.getLastClosedExercise();
				 if(maxYear == null){
					Calendar c = Calendar.getInstance(); 
					maxYear =c.get(Calendar.YEAR);
				 } 
			 }
			if(maxYear != null){
				currentExercise =   getExerciseService().getOneFromYear(maxYear);
				getServletContext().setAttribute("currentExercise", currentExercise);	
			}			
		}
	}

	/** returns ModelAndView(getSuccessView()) */
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception
	{
		 return new ModelAndView(getSuccessView());
        
		
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public IExerciseService getExerciseService() {
		return exerciseService;
	}

	public void setExerciseService(IExerciseService exerciseService) {
		this.exerciseService = exerciseService;
	}

	

	

	

}
