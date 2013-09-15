  package com.interaudit.web.controllers;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.interaudit.service.IUserService;

/**
 * Intercepts HTTP requests to check user is authenticated/authorised
 * (note: closes the Hibernate session for the current thread)
 * 
 * @author  royphil  (mailto:royphil@intrasoft.lu) 
 */
public class HttpRequestInterceptor
    extends HandlerInterceptorAdapter
{
    private String loginPage;
    private IUserService userService;
    
    
    /**
     * Uses AssistSecurityManager to ensure user is logged in; if not,
     * then user is forwarded to the log-in page.
     * @see AssistSecurityManager
     */
    public boolean preHandle (
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception
    {
    	HttpSession session = request.getSession(false);
        if (!(request.getRequestURI().indexOf(loginPage) > 0) )
        {	
	    	  if(session == null)
	        {	
	        	  request.getSession();
	        	  response.sendRedirect(loginPage);
	            return false;
	        }
	    	else{
	    		if(request.getSession(false).getServletContext().getAttribute("globalAccessRights") == null ){
	    			Map<String, Boolean> globalAccessRights = userService.accessRightsMap("All");
	    			request.getSession(false).getServletContext().setAttribute("globalAccessRights", globalAccessRights);
	    		}  
	    	}
	    
        }
        return true;
    }

    public String getLoginPage()
    {
        return loginPage;
    }

    public void setLoginPage(String loginPage)
    {
        this.loginPage = loginPage;
    }

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	
   
}
