package com.interaudit.web.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.userdetails.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomBooleanEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.ApplicationContext;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.interaudit.domain.model.Employee;
import com.interaudit.domain.model.Position;
import com.interaudit.domain.model.data.Option;
import com.interaudit.service.IRoleService;
import com.interaudit.service.IUserService;
import com.interaudit.service.impl.RepositoryService;
import com.interaudit.util.Utils;

/**
 * Controller for the Login screen.
 */
public class CreateEmployeeController extends SimpleFormController
{
	
	private IUserService userService;
	private IRoleService roleService;
	private Log log = LogFactory.getLog(CreateEmployeeController.class);

	/**
	 * Always returns a new User object
	 * 
	 * @see User
	 */
	protected Object formBackingObject(HttpServletRequest request) throws Exception
	{
		String id = request.getParameter("id"); 
		
		if(id == null || id.trim().length() == 0){
			com.interaudit.domain.model.Employee employee = new com.interaudit.domain.model.Employee();
			Position positionDefault = roleService.getRoleByCode(Position.ASSISTANTS);
			employee.setPosition( positionDefault);
			return employee;
		}
		else{
			return userService.getOneDetached(Long.valueOf(id) );
		}
	}
	

	@SuppressWarnings("unchecked")
	protected Map referenceData(HttpServletRequest request) {
		Map referenceData = new HashMap();
		 /** The application context. */
		ApplicationContext context = org.springframework.web.context.support.WebApplicationContextUtils
		.getRequiredWebApplicationContext(request.getSession().getServletContext());
		
		RepositoryService repositoryService = (RepositoryService) context.getBean("repositoryService");
		//Country reference data
		List<Option> countryOptions= new ArrayList<Option>();
		countryOptions.add(new Option("LU","LUXEMBOURG"));
		countryOptions.add(new Option("DK","GERMANY"));
		countryOptions.add(new Option("FR","FRANCE"));
		countryOptions.add(new Option("BE","BELGIQUE"));
		countryOptions.add(new Option("AT","AUTRE"));
		 
		//Position reference data
		List<Option> positionOptions = repositoryService.getPositionsAsOptions();//roleService.getPositionsAsOptions();
		referenceData.put("countryOptions", countryOptions);
		referenceData.put("positionOptions", positionOptions);
		
		String id = request.getParameter("id"); 
		if(id != null && id.trim().length() != 0){
			List<Option> allEmployeeOptions = repositoryService.getEmployeeOptions();//userService.getAllEmployeeAsOptions();
			request.getSession().setAttribute( "allEmployeeOptions",allEmployeeOptions );
		}
		return referenceData;
	}
	
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception
			{
		try{
			
			//String dateFormat = getMessageSourceAccessor().getMessage("format.date","dd.MM.yyyy");
			String dateFormat = "MM/dd/yyyy";
			SimpleDateFormat df = new SimpleDateFormat(dateFormat);
			df.setLenient(true);
			binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(
			df, true));
			 
			binder.registerCustomEditor(java.lang.Boolean.class, new CustomBooleanEditor( true));
			
			
			
			//CustomNumberEditor(Class numberClass, boolean allowEmpty)
			
		}catch(Exception e){
			//System.out.println(e.getMessage());
			throw e;
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
		
		com.interaudit.domain.model.Employee employee = (com.interaudit.domain.model.Employee)command;
        if (employee == null)
        {
            return;
        }
        
        if (request.getParameter("userActive") == null) {
			employee.setUserActive(false);
		}else{
			employee.setUserActive(true);
		}
        
        if( Utils.isValidEmail(employee.getEmail()) == false){
        	errors.reject("employee.email","Invalid email format");
        	request.getSession(false).setAttribute("emailErrorMessage", "Invalid email format");        	
        }

        int nbOccurrence = userService.getMaxEmployeeLoginSequence( employee.getLogin());
      	
    	if(employee.getId() == null){
    		if( nbOccurrence > 0){
    			errors.reject("employee.login","Invalid login already used");
    			request.getSession(false).setAttribute("loginErrorMessage", "Invalid login already used");
    		}
    	}else{
    		if( nbOccurrence > 1){
    			errors.reject("employee.login","Invalid login already used");
    			request.getSession(false).setAttribute("loginErrorMessage", "Invalid login already used");
    		}
    	}
    	
    	if(employee.getDateOfHiring() == null){
    		errors.reject("employee.dateOfHiring","Please enter a date Of Hiring...");
			request.getSession(false).setAttribute("dateOfHiring", "Please enter a date Of Hiring...");
    	}
    	
    	if(employee.getDateEndOfHiring() == null){
    		errors.reject("employee.dateEndOfHiring","Please enter an End date Of Hiring...");
			request.getSession(false).setAttribute("dateEndOfHiring", "Please enter an End date Of Hiring...");
    	}
    	
    	if( (employee.getDateOfHiring() != null) && (employee.getDateEndOfHiring() != null)){
    		
    		if(employee.getDateOfHiring().after(employee.getDateEndOfHiring())){
    			errors.reject("employee.dateEndOfHiring","End date Of Hiring cannot be before date Of Hiring...");
    			request.getSession(false).setAttribute("dateOfHiring", "End date Of Hiring cannot be before date Of Hiring...");
    		}
    		
    	}
       

        if (errors.hasErrors())
		{
			return;
		}
        
		
    
	}

	/** returns ModelAndView(getSuccessView()) */
	@SuppressWarnings("unchecked")
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception
	{
		 ArrayList<String> messages = new ArrayList<String>();
		 Employee employee = (Employee) command;
		 if(employee.getId() == null){			
			 Employee newEmployee = userService.createEmployee( employee);
			 
			 //Creation des droits
			 int countRights = userService.createDefaultAccessRightsForEmployee(newEmployee);
			 log.info(countRights + "droits crées...");
			 
			 if( countRights > 0 ){
    			 Map<String, Boolean> globalAccessRights = userService.accessRightsMap("All");
    			 request.getSession(false).getServletContext().removeAttribute("globalAccessRights");
    			 request.getSession(false).getServletContext().setAttribute("globalAccessRights", globalAccessRights);    			
    			 logger.info( "The access rights changes have been successfully updated...");
    		}else{
    			logger.error( "Failed to updated access rights changes ...");    			
    		}
			 
			 messages.add("The employee : " + newEmployee.getLastName() + " has been successfully created");
			 request.getSession(false).setAttribute("successMessage", messages);	
			 //return  new ModelAndView(getSuccessView());
			 return new ModelAndView("redirect:/showEmployeeRegisterPage.htm?id="+newEmployee.getId());
		 }else{			 
			 userService.updateOne(employee);
			 messages.add("The employee : " + employee.getLastName() + " has been successfully updated");
			 request.getSession(false).setAttribute("successMessage", messages);
			 return new ModelAndView("redirect:/showEmployeeRegisterPage.htm?id="+employee.getId());
		 }
	}

	public IUserService getUserService() {
		return userService;
	}


	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public IRoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}

	

	

	

	

}
