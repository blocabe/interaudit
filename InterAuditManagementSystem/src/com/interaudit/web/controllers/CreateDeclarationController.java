package com.interaudit.web.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.userdetails.User;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.ApplicationContext;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.interaudit.domain.model.Customer;
import com.interaudit.domain.model.data.Option;
import com.interaudit.service.ICustomerService;
import com.interaudit.service.IDeclarationService;
import com.interaudit.service.IExerciseService;
import com.interaudit.service.impl.RepositoryService;

/**
 * Controller for the Login screen.
 */
public class CreateDeclarationController extends SimpleFormController
{
	
	private IDeclarationService declarationService;
	private IExerciseService exerciseService;
	private ICustomerService  customerService;
	

	/**
	 * Always returns a new User object
	 * 
	 * @see User
	 */
	protected Object formBackingObject(HttpServletRequest request) throws Exception
	{
		String id = request.getParameter("id"); 
		
		if(id == null || id.trim().length() == 0){
			return new com.interaudit.domain.model.Declaration();
		}
		else{
			return declarationService.getOneDetached(Long.valueOf(id) );
		}
	}
	
	@SuppressWarnings("unchecked")
	protected Map referenceData(HttpServletRequest request) {
		Map referenceData = new HashMap(); 
		// reference data
		 /** The application context. */
		ApplicationContext context = org.springframework.web.context.support.WebApplicationContextUtils
		.getRequiredWebApplicationContext(request.getSession().getServletContext());
		
		RepositoryService repositoryService = (RepositoryService) context.getBean("repositoryService");
		List<Option> exerciseOptions = repositoryService.getExercicesOptions(); //exerciseService.getAllExercicesOptions();
		List<Option> allCustomersNames = repositoryService.getCustomerOptions(); //customerService.getAllCustomerAsOptions();
		referenceData.put("exerciseOptions", exerciseOptions);
		referenceData.put("allCustomersNames", allCustomersNames);
	
		return referenceData;
	}
	
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception
			{
		try{
			
			String dateFormat = "MM/dd/yyyy";
			SimpleDateFormat df = new SimpleDateFormat(dateFormat);
			df.setLenient(true);
			binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(
			df, true));
			
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
		
		com.interaudit.domain.model.Declaration declaration = (com.interaudit.domain.model.Declaration)command;
        if (declaration == null)
        {
            return;
        }
        
        if (request.getParameter("active") == null) {
        	declaration.setActive(false);
		}else{
			declaration.setActive(true);
		}
        
        
        if (request.getParameter("declaration") == null) {
        	declaration.setDeclaration(false);
		}else{
			declaration.setDeclaration(true);
		}
        
        if (request.getParameter("passport") == null) {
        	declaration.setPassport(false);
		}else{
			declaration.setPassport(true);
		}
        
      //Test if the customer is valid
        
		
        if(declaration.getId() == null){
        	
        	Customer customer = customerService.getOneDetachedFromCompanyName(declaration.getCustomer());
        	if(customer == null){
    			errors.reject("company","Invalid customer name");
    			request.getSession(false).setAttribute("companyNameErrorMessage", "Invalid customer name");
    			
    		}
        }
		
        
      //Check if the account number is already used
        com.interaudit.domain.model.Declaration   otherdeclaration =  getDeclarationService().getOneForExercise(declaration.getCustomer(), declaration.getExercise());
        if(otherdeclaration != null){
        	if((declaration.getId() == null) || (declaration.getId().longValue() != otherdeclaration.getId().longValue())){
				errors.reject("alreadyRegisteredErrorMessage","A declaration is already registered for this company");
				request.getSession(false).setAttribute("alreadyRegisteredErrorMessage", "A declaration is already registered for this company ");
			}
        }
		
		if (errors.hasErrors())
		{
			return;
		}

    
	}

	/** returns ModelAndView(getSuccessView()) */
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception
	{
		ArrayList<String> messages = new ArrayList<String>();
		com.interaudit.domain.model.Declaration declaration = (com.interaudit.domain.model.Declaration)command;
	    
		 if(declaration.getId() == null){
			
			 getDeclarationService().saveOne(declaration);
			 messages.add("The declaration for " + declaration.getCustomer() + " has been successfully created");
						
		 }else{
			 
			 getDeclarationService().updateOne(declaration);
			 messages.add("The declaration for " + declaration.getCustomer()  + " has been successfully updated");
		 }
		
		request.getSession(false).setAttribute("successMessage", messages);
		return new ModelAndView(getSuccessView());
        
		
	}

	public IDeclarationService getDeclarationService() {
		return declarationService;
	}

	public void setDeclarationService(IDeclarationService declarationService) {
		this.declarationService = declarationService;
	}

	public IExerciseService getExerciseService() {
		return exerciseService;
	}

	public void setExerciseService(IExerciseService exerciseService) {
		this.exerciseService = exerciseService;
	}

	public ICustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}

	

	

	

	

	

}
