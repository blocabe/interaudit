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
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.interaudit.domain.model.Contract;
import com.interaudit.domain.model.Customer;
import com.interaudit.domain.model.Mission;
import com.interaudit.domain.model.data.Option;
import com.interaudit.service.IContractService;
import com.interaudit.service.ICustomerService;
import com.interaudit.util.CustomDoubleEditor;

/**
 * Controller for the Login screen.
 */
public class CreateContractController extends SimpleFormController
{
	
	private ICustomerService  customerService;
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
			
			com.interaudit.domain.model.Contract contract = new com.interaudit.domain.model.Contract();
			
			contract.setCustomer(new Customer());
			
			return contract;
		}
		else{
			return contractService.getOneDetached(Long.valueOf(id) );
		}
	}

	@SuppressWarnings("unchecked")
	protected Map referenceData(HttpServletRequest request) {
		Map referenceData = new HashMap();
		 
		//Customer reference data
		List<Option> allCustomersNames = customerService.getAllCustomerAsOptions();
		referenceData.put("allCustomersNames", allCustomersNames);
		
		List<Option> missionOptions = contractService.getMissionTypeOptions();
		/*
		List<Option> missionOptions = new ArrayList<Option>();
		missionOptions.add(new Option("OTHER","OTHER"));
		for(String type: Mission.types){
			missionOptions.add(new Option(type,type));
		}
		*/
		
		referenceData.put("missionOptions", missionOptions);
		
		String id = request.getParameter("id"); 
		if(id != null && id.trim().length() != 0){
			List<Option> allContractOptions = (List<Option>) request.getSession().getAttribute( "allContractOptions" );
			//if ( request.getSession().getAttribute( "allContractOptions" ) == null){
				allContractOptions = contractService.getAllContractAsOptions();
				request.getSession().setAttribute( "allContractOptions",allContractOptions );
			   // }
		}
		
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
			
			binder.registerCustomEditor(double.class, new CustomDoubleEditor());

	
			
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
		
		Contract contract = (Contract) command;
        if (contract == null)
        {
            return;
        }
        
       
        
        if (request.getParameter("agreed") == null) {
        	contract.setAgreed(false);
		}else{
			contract.setAgreed(true);
		}
        
        if (request.getParameter("valid") == null) {
        	contract.setValid(false);
		}else{
			contract.setValid(true);
		}
        
        if (request.getParameter("interim") == null) {
        	contract.setInterim(false);
		}else{
			contract.setInterim(true);
		}
        
        //Test if the customer is valid
        Customer customer = customerService.getOneDetachedFromCompanyName(contract.getCustomer().getCompanyName());
		
		if(customer == null){
			errors.reject("customer.companyName","Invalid customer name");
			request.getSession(false).setAttribute("companyNameErrorMessage", "Invalid customer name");
			
		}
		/*
		 boolean exist = contractService.existContractForCustomerAndType(customer.getId(), contract.getMissionType());
		 if(exist && contract.getId()==null){
			 errors.reject("customer.companyName","Invalid period");
			 request.getSession(false).setAttribute("invaliddateErrorMessage", "A contract of type [ " +contract.getMissionType() + " ] is already registered for customer [ " +customer.getCompanyName() +" ]");
		 }
		 */
		
        //Test if the period is ok
        if( contract.getFromDate().after(contract.getToDate()) ){
        	errors.reject("customer.companyName","Invalid period");
			request.getSession(false).setAttribute("invaliddateErrorMessage", "Invalid period");
        }
        
        String amountAsString = request.getParameter("amount");
        
        try{
        	 Double amount = Double.parseDouble(amountAsString);
        	 if(amount < 0) throw new NumberFormatException();
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
		Contract contract = (Contract) command;
	    
		Customer customer = customerService.getOneDetachedFromCompanyName(contract.getCustomer().getCompanyName());
		
		if(customer == null){
			 messages.add("No customer found with name : " + contract.getCustomer().getCompanyName());
			 request.getSession(false).setAttribute("actionErrors", messages);
			 return new ModelAndView(getSuccessView());
		}
		
		
		contract.setCustomer(customer);
		
		
		 if(contract.getId() == null){
			
			 Contract newContract = getContractService().createContract(contract);
			 messages.add("The contract : " + newContract.getReference() + " has been successfully created");
			 request.getSession(false).setAttribute("successMessage", messages);
			// return new ModelAndView(getSuccessView());
			 return new ModelAndView("redirect:/showContractRegisterPage.htm?id="+newContract.getId());
						
		 }else{
			 
			 getContractService().updateContract(contract);
			 messages.add("The contract : " + contract.getReference() + " has been successfully updated");
			 request.getSession(false).setAttribute("successMessage", messages);
			 return new ModelAndView("redirect:/showContractRegisterPage.htm?id="+contract.getId());
		 }
		
		 
        
		
	}

	public ICustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}

	public IContractService getContractService() {
		return contractService;
	}

	public void setContractService(IContractService contractService) {
		this.contractService = contractService;
	}

	

	

	

	

	

}
