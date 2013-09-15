package com.interaudit.web.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.interaudit.service.IUserService;
import com.interaudit.service.view.AccessRightsView;
import com.interaudit.util.WebContext;



/**
 * The Welcome action.
 * 
 * @author Julien Dubois
 */
public class AccessRigthsController extends MultiActionController {

   private final Log logger = LogFactory.getLog(AccessRigthsController.class);
    
    private static final String VIEW_ACCESS_RIGHTS = "viewAccessRights";
    private IUserService userService;
    
    
    
    
    
    
    
   
    
    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView showUserAccessRightsPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	Map<String,Object> mapResults = new HashMap<String, Object>();
    	
    	String type = request.getParameter("type");
    	if( type == null){
    		type = "All";
    	}
    	/*
    	String role = request.getParameter("role");
    	if( role == null){
    		role = "All";
    	}
    	*/
    	String employeeId = request.getParameter("employee");
    	if(employeeId == null || employeeId.length() == 0){
    		 WebContext context =  (WebContext) request.getSession(false).getAttribute("context");
    		 employeeId = context.getCurrentUser().getId().toString();
    	}
    	
    	
    	
    	AccessRightsView view = userService.buildAccessRightsView(type,employeeId);	
		mapResults.put(VIEW_ACCESS_RIGHTS, view);
		return new ModelAndView("accessrights",mapResults);		
	}


    public ModelAndView updateUserAccessRightsPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	
    	String rights = request.getParameter("rights");
    	String type = request.getParameter("type");
    	
    	if( type == null){
    		type = "All";
    	}
    	
    	String employeeId = request.getParameter("employee");
    	if(employeeId == null || employeeId.length() == 0){
    		 WebContext context =  (WebContext) request.getSession(false).getAttribute("context");
    		 employeeId = context.getCurrentUser().getId().toString();
    	}
    	/*
    	String role = request.getParameter("role");
    	if( role == null){
    		role = "All";
    	}
    	*/
    	
    	//if(rights != null && rights.length() > 0){
    	
    		//Find the list of access rights to set to true, the others will be set to false
    		try{
    			
    			boolean ret = userService.updateAccessRights(rights,type,employeeId);
        		if( ret ){
        			 Map<String, Boolean> globalAccessRights = userService.accessRightsMap("All");
        			 request.getSession(false).getServletContext().removeAttribute("globalAccessRights");
        			 request.getSession(false).getServletContext().setAttribute("globalAccessRights", globalAccessRights);
        			 request.getSession(false).setAttribute("successMessage", "The access rights changes have been successfully updated...");
        			 logger.info( "The access rights changes have been successfully updated...");
        		}else{
        			logger.error( "Failed to updated access rights changes ...");
        			request.getSession(false).setAttribute("actionErrors", "Failed to updated access rights changes ...");
        		}
    			
    		}catch(Exception e){
    			logger.error( "Failed to updated access rights changes ..." + e);
    			request.getSession(false).setAttribute("actionErrors", "Failed to updated access rights changes ...");
    		}
    		
    	//}
    	
    	return showUserAccessRightsPage( request, response);
    }

    




	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
    
    

	

	



	



	



	

	

	
}
