package com.interaudit.web.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;



/**
 * The Welcome action.
 * 
 * @author Julien Dubois
 */
public class PerformanceController extends MultiActionController {

   // private final Log log = LogFactory.getLog(HandleBrowseDatasetPageController.class);
    
    
    
    
    
    
    
    
	
   
    
    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView showPerfOneYearPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
 
		return new ModelAndView("perf_one_year");		
	}
    
    
    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView showPerfYearsPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
 
		return new ModelAndView("perf_several_years");		
	}
    
    

	

	



	



	



	

	

	
}
