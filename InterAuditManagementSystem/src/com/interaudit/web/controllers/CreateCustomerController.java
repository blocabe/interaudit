package com.interaudit.web.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.userdetails.User;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.interaudit.domain.model.Contract;
import com.interaudit.domain.model.Customer;
import com.interaudit.domain.model.Employee;
import com.interaudit.domain.model.MissionTypeTaskLink;
import com.interaudit.domain.model.Origin;
import com.interaudit.domain.model.data.Option;
import com.interaudit.service.IContractService;
import com.interaudit.service.ICustomerService;
import com.interaudit.service.IOriginService;
import com.interaudit.service.IUserService;
import com.interaudit.service.impl.RepositoryService;
import com.interaudit.util.Utils;
import com.interaudit.web.controllers.TimesheetController.Handler;

/**
 * Controller for the Login screen.
 */
public class CreateCustomerController extends SimpleFormController implements  ApplicationContextAware
{
	
	 private ICustomerService customerService;
	 private IOriginService originService;
	 private IUserService userService;
	 private IContractService contractService;
	
	    
	

	/**
	 * Always returns a new User object
	 * 
	 * @see User
	 */
	protected Object formBackingObject(HttpServletRequest request) throws Exception
	{
		
		String id = request.getParameter("id"); 
		
		if(id == null || id.trim().length() == 0){
			com.interaudit.domain.model.Customer customer = new com.interaudit.domain.model.Customer();
			
			 
			Employee associeSignataire = new Employee();//userService.getPartners().get(0);
			associeSignataire.setId(-1L);
			customer.setAssocieSignataire(associeSignataire);
			
			Employee customerManager =  new Employee();//userService.getManagers().get(0);
			customerManager.setId(-1L);
			customer.setCustomerManager(customerManager);
			
			Origin origin = new Origin();//originService.getAll().get(0);
			origin.setId(-1L);
			customer.setOrigin(origin);
			
			
			MissionTypeTaskLink defaultContractType = new MissionTypeTaskLink();
			defaultContractType.setId(-1L);
			customer.setDefaultContractType(defaultContractType);
			
			
			return customer;
		}
		else{
			return customerService.getOneDetached(Long.valueOf(id) );
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
		 
		//Origin reference data
		List<Option> originOptions =  repositoryService.getOriginOptions();//originService.getAllAsOptions();
		
		//Associate reference data
		List<Option> associateOptions =   repositoryService.getAssocieOptions();//userService.getPartnersAsOptions();
		
		//Managers reference data
		List<Option> managerOptions =   repositoryService.getManagerOptions();// userService.getManagersAsOptions();
		
		List<Option> directorsAsOptions = repositoryService.getDirectorsOptions();//userService.getDirectorsAsOptions();
		
		List<Option> missionOptions = repositoryService.getMissionTypeOptions2(); //contractService.getMissionTypeOptions2();
		referenceData.put("missionOptions", missionOptions);
		
		referenceData.put("countryOptions", countryOptions);
		referenceData.put("originOptions", originOptions);
		referenceData.put("associateOptions", associateOptions);
		referenceData.put("managerOptions", managerOptions);
		referenceData.put("directorsAsOptions", directorsAsOptions);
		
		
		String id = request.getParameter("id"); 
		if(id != null && id.trim().length() != 0){
			List<Option> allCustomerOptions =  repositoryService.getCustomerOptions();//customerService.getAllCustomerAsOptions();
			request.getSession().setAttribute( "allCustomerOptions",allCustomerOptions );
		}
		
		return referenceData;
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
		
		Customer customer = (Customer) command;
		if (request.getParameter("active") == null) {
			customer.setActive(false);
		}else{
			customer.setActive(true);
		}
		
		
		
		
		//Check the email of the customer
		if( Utils.isValidEmail(customer.getEmail()) == false){
	        	errors.reject("customer.email","Invalid email format");
	        	request.getSession(false).setAttribute("emailErrorMessage", "Invalid email format");   	
	     }
		
		//Check the customer code that must be unique
		boolean exist = customerService.getOneDetachedFromCompanyCode(customer.getCode()) != null;
      	
    	if(customer.getId() == null){
    		if( exist == true){
    			errors.reject("customer.code","code already used");
    			request.getSession(false).setAttribute("codeErrorMessage", "code already used");
    		}
    	}
		
		//Check the customer company name that must be unique
    	exist = customerService.getOneDetachedFromCompanyName(customer.getCompanyName()) != null;
      	
    	if(customer.getId() == null){
    		if( exist == true){
    			errors.reject("customer.code","code already used");
    			request.getSession(false).setAttribute("codeErrorMessage", "code already used");
    		}
    	}
    	
    	
    	Long missionTypeTaskLinkId = customer.getDefaultContractType().getId();
    	if(missionTypeTaskLinkId == -1L){
      	   errors.reject("invalidContracttypeErrorMessage","Invalid Contract Type");
      	   request.getSession(false).setAttribute("invalidContracttypeErrorMessage","Invalid Contract Type"); 
         }
        
        Long idManager = customer.getCustomerManager().getId();
        if(idManager == -1L){
     	   errors.reject("invalidCustomerManagerErrorMessage","Invalid Manager");
     	   request.getSession(false).setAttribute("invalidCustomerManagerErrorMessage", "Invalid Manager"); 
        }
        
        Long idAssocie = customer.getAssocieSignataire().getId();
        if(idAssocie == -1L){
     	   errors.reject("invalidCustomerAssociateErrorMessage","Invalid Associé");
 		request.getSession(false).setAttribute("invalidCustomerAssociateErrorMessage", "Invalid Associé"); 
        }
        
        
        Long idOrigin = customer.getOrigin().getId();
        if(idOrigin == -1L){
     	   errors.reject("invalidCustomerOriginErrorMessage","Invalid Origin");
 		request.getSession(false).setAttribute("invalidCustomerOriginErrorMessage", "Invalid Origin"); 
        }
        
        String country = customer.getCountry();
        if(country.equalsIgnoreCase("-1")){
     	   errors.reject("invalidCustomerCountryErrorMessage","Invalid Country");
 		request.getSession(false).setAttribute("invalidCustomerCountryErrorMessage", "Invalid Country"); 
        }
        
        
        String amountAsString = request.getParameter("defaultContractAmount");     
        try{
        	 Double amount = Double.parseDouble(amountAsString);
        	 if(amount <= 0) throw new NumberFormatException();
        }catch(NumberFormatException nfe){
        	errors.reject("amount","Invalid amount");
			request.getSession(false).setAttribute("invalidAmountFormatErrorMessage", "Invalid amount");
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
	    Customer customer = (Customer) command;
	    
	    if(customer.getCompanyName() != null){
	    	customer.setCompanyName(customer.getCompanyName().trim());	
	    }
	           	    
	    Employee associeSignataire = userService.getOneDetached(customer.getAssocieSignataire().getId());
		customer.setAssocieSignataire(associeSignataire);
		
		Employee customerManager = userService.getOneDetached(customer.getCustomerManager().getId());
		customer.setCustomerManager(customerManager);
		
		Origin origin = originService.getOneDetached(customer.getOrigin().getId());
		customer.setOrigin(origin);
		
		MissionTypeTaskLink defaultContractType = contractService.getOneMissionTypeTaskLink(customer.getDefaultContractType().getId());
		customer.setDefaultContractType(defaultContractType);
		
		 if(customer.getId() == null){
			
			 Customer customerSaved =getCustomerService().createCustomer(customer);			 
			 Contract contractSaved = getContractService().createContract(customerSaved);
			 
			 final ExecutorService  pool = Executors.newFixedThreadPool(1);
	    	 pool.execute(new  Handler());   
			 
			 messages.add("The customer : " + customer.getCompanyName() + " has been successfully created with a new contract as well");
			 request.getSession(false).setAttribute("successMessage", messages);
				//return new ModelAndView(getSuccessView());
			 return new ModelAndView("redirect:/showCustomerRegisterPage.htm?id="+customerSaved.getId());
		 }else{
			 
			 getCustomerService().updateCustomer(customer);
			 messages.add("The customer : " + customer.getCompanyName() + " has been successfully updated");
			 final ExecutorService  pool = Executors.newFixedThreadPool(1);
	    	 pool.execute(new  Handler());   			 
			 request.getSession(false).setAttribute("successMessage", messages);
			 return new ModelAndView("redirect:/showCustomerRegisterPage.htm?id="+customer.getId());
		 }
		
		
        
	}
	
	class Handler implements Runnable
    {

      Handler()
      { 
      }
      
      public void run()
      {
    	  RepositoryService repositoryService = (RepositoryService) getApplicationContext().getBean("repositoryService");
		  repositoryService.refresh();
      }
    }

	public ICustomerService getCustomerService() {
		return customerService;
	}


	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}

	public IOriginService getOriginService() {
		return originService;
	}

	public void setOriginService(IOriginService originService) {
		this.originService = originService;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public IContractService getContractService() {
		return contractService;
	}

	public void setContractService(IContractService contractService) {
		this.contractService = contractService;
	}

	

	

	

	

}
