package com.interaudit.web.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.userdetails.User;
import org.springframework.context.ApplicationContext;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.interaudit.domain.model.Contact;
import com.interaudit.domain.model.Customer;
import com.interaudit.domain.model.data.Option;
import com.interaudit.service.IContactService;
import com.interaudit.service.ICustomerService;
import com.interaudit.service.impl.RepositoryService;
import com.interaudit.util.Utils;

/**
 * Controller for the Login screen.
 */
public class CreateContactController extends SimpleFormController
{
	
	private ICustomerService  customerService;
	private IContactService contactService;

	/**
	 * Always returns a new User object
	 * 
	 * @see User
	 */
	protected Object formBackingObject(HttpServletRequest request) throws Exception
	{
		String id = request.getParameter("id"); 
		
		if(id == null || id.trim().length() == 0){
			
			com.interaudit.domain.model.Contact contact = new com.interaudit.domain.model.Contact();
			
			contact.setCustomer(new Customer());
			
			return contact;
		}
		else{
			return contactService.getOneDetached(Long.valueOf(id) );
		}
	}

	@SuppressWarnings("unchecked")
	protected Map referenceData(HttpServletRequest request) {
		Map referenceData = new HashMap();
		
		//Customer reference data
		 /** The application context. */
		ApplicationContext context = org.springframework.web.context.support.WebApplicationContextUtils
		.getRequiredWebApplicationContext(request.getSession().getServletContext());
		
		RepositoryService repositoryService = (RepositoryService) context.getBean("repositoryService");
		
		List<Option> allCustomersNames = repositoryService.getCustomerOptions(); //customerService.getAllCustomerAsOptions();
		referenceData.put("allCustomersNames", allCustomersNames);
		
		String id = request.getParameter("id"); 
		if(id != null && id.trim().length() != 0){
			List<Option> allContactOptions = repositoryService.getAllContactAsOptions();//contactService.getAllContactAsOptions();
			request.getSession().setAttribute( "allContactOptions",allContactOptions );			 
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
		
		Contact contact = (Contact) command;
		if (request.getParameter("active") == null) {
			contact.setActive(false);
		}else{
			contact.setActive(true);
		}
		
		//Test if the customer is valid
        Customer customer = customerService.getOneDetachedFromCompanyName(contact.getCompany());
		
		if(customer == null){
			errors.reject("company","Invalid customer name");
			request.getSession(false).setAttribute("companyNameErrorMessage", "Invalid customer name");
			
		}
		
		//Check if the email of the contact has a correct format
		if( Utils.isValidEmail(contact.getEmail()) == false){
	        	errors.reject("email","Invalid email format");
	        	request.getSession(false).setAttribute("emailErrorMessage", "Invalid email format");
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
		Contact contact = (Contact) command;
	    
		Customer customer = customerService.getOneDetachedFromCompanyName(contact.getCompany());
		
		if(customer == null){
			 messages.add("No customer found with name : " + contact.getCompany());
			 request.getSession(false).setAttribute("actionErrors", messages);
			 return new ModelAndView(getSuccessView());
		}
		
		contact.setCustomer(customer);
		
		
		 if(contact.getId() == null){
			
			 Contact newContact = getContactService().createContact(contact);
			 messages.add("The contact : " + newContact.getLastName() + " has been successfully created");
			 request.getSession(false).setAttribute("successMessage", messages);
			 //return new ModelAndView(getSuccessView());
			 return new ModelAndView("redirect:/showContactRegisterPage.htm?id="+newContact.getId());
		 }else{
			 
			 getContactService().updateContact(contact);
			 messages.add("The contact : " + contact.getLastName() + " has been successfully updated");
			 request.getSession(false).setAttribute("successMessage", messages);
			 return new ModelAndView("redirect:/showContactRegisterPage.htm?id="+contact.getId());
		 }
		
		
        
		
	}

	public ICustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}

	public IContactService getContactService() {
		return contactService;
	}

	public void setContactService(IContactService contactService) {
		this.contactService = contactService;
	}

	

	

	

	

	

}
