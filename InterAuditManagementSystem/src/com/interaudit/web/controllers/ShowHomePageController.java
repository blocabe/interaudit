package com.interaudit.web.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;




/**
 * The Welcome action.
 * 
 * @author Julien Dubois
 */
public class ShowHomePageController implements Controller {

    @SuppressWarnings("unused")
	private final Log log = LogFactory.getLog(ShowHomePageController.class);
    
    private String showHomePageView;
    
    
    /**
     * Welcome action.
     */
	public ModelAndView handleRequest(HttpServletRequest request,
					HttpServletResponse response) throws Exception {
	 
        return new ModelAndView(getShowHomePageView());
        
	}
	
	

	public String getShowHomePageView() {
		return showHomePageView;
	}

	public void setShowHomePageView(String showHomePageView) {
		this.showHomePageView = showHomePageView;
	}
}
