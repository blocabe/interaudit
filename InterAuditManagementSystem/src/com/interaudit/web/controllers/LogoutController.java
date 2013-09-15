package com.interaudit.web.controllers;

import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.interaudit.util.WebContext;

/**
 * Controller for "logging out" of the application.
 * @author  royphil  (mailto:royphil@intrasoft.lu)
 */
public class LogoutController
    implements Controller
{
    
    private String successView;
    private static final Logger  logger      = Logger.getLogger(LogoutController.class);
    

    /**
     * Removes User object from session using ApplicationSecurityManager
     * 
     * @see AssistSecurityManager 
     */
    @SuppressWarnings("static-access")
	public ModelAndView handleRequest(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
    	WebContext context = (WebContext) request.getSession(false).getAttribute("context");
    	logger.info("LogoutController.handleRequest : Entrance at : " + new Date());
      
    	request.getLocale().setDefault(Locale.FRANCE);
      
	      if(context!= null){
	    	  context.clearContext();
	    	  context = null;
	      }
      
	      request.getSession().invalidate();
	      logger.info("LogoutController.handleRequest : Session has been invalidated at : " + new Date());
	      return new ModelAndView(getSuccessView());
    }

   
    public String getSuccessView() { return successView; }
    public void setSuccessView(String successView) { this.successView = successView; }
     
}