package com.interaudit.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.transaction.annotation.Transactional;

import com.interaudit.domain.dao.IContactDao;
import com.interaudit.domain.model.Contact;
import com.interaudit.domain.model.Customer;
import com.interaudit.domain.model.Employee;
import com.interaudit.domain.model.data.Option;
import com.interaudit.service.IContactService;
import com.interaudit.service.ICustomerService;
import com.interaudit.service.IUserService;
import com.interaudit.service.param.SearchContactParam;
import com.interaudit.service.view.ContactsView;

@Transactional
public class ContactService implements IContactService {

    private IContactDao contactDao;
    private IUserService userService;
    private ICustomerService  customerService;

   
    public void deleteOne(Long id) {
       contactDao.deleteOne(id);
    	/*
    	int deleted = contactDao.deleteOne(id);
    	
        if(deleted == 0) {
           throw new Exception("Attempt to delete a contact that does not exist!");
       }
       */
    }


    public List<Contact> getAll() {
        return contactDao.getAll();
    }
    
    public List<Option> getAllContactAsOptions(){
    	List<Option> options= new ArrayList<Option>(); 	  
       	for(Contact contact : getAll()){
       		 options.add(new Option(contact.getId().toString(), contact.getFullName()));
       	}
       	return options;
    }
    
    public Contact createContact(Contact contact){
    	 try {
    	Customer customer = customerService.getOne(contact.getCustomer().getId());
		
		contact.setCustomer(customer);
		
		contact.setContactName(contact.getLastName());
		
		contact.setCompanyContactMail(customer.getEmail());
		
		contact.setMainActivity(customer.getMainActivity());
		
		contact.setCompany(customer.getCompanyName());
		
		contact.setCreateDate(new Date());
		
		contact.setUpdateDate(new Date());
		
		return contactDao.saveOne(contact);
		
    	 } catch (Exception e) {
			return null;
		}
	
    	
    }
    
    public Contact updateContact(Contact contact){
    	
    	 try {
    		 Customer customer = customerService.getOne(contact.getCustomer().getId());
    			
    			contact.setCustomer(customer);
    			
    			contact.setContactName(contact.getLastName());
    			
    			contact.setCompanyContactMail(customer.getEmail());
    			
    			contact.setMainActivity(customer.getMainActivity());
    			
    			contact.setCompany(customer.getCompanyName());
    			
    			contact.setUpdateDate(new Date());
    					
    			return contactDao.updateOne(contact);
 		} catch (Exception e) {
 			return null;
 		}
    	
    }


    public Contact getOne(Long id) {
        try {
			return contactDao.getOne(id);
		} catch (EntityNotFoundException e) {
			return null;
		}
    }
    
    public Contact getOneDetached(Long id){
    	return  contactDao.getOneDetached(id);
    }

  
    public Contact saveOne(Contact contact) {
        return contactDao.saveOne(contact);
    }

   
    public Contact updateOne(Contact contact) {
        return contactDao.updateOne(contact);
    }

    public IContactDao getContactDao() {
        return contactDao;
    }

    public void setContactDao(IContactDao contactDao) {
        this.contactDao = contactDao;
    }

  
    public List<Contact> getAllByUserId(Long userId) {
        //return contactDao.getAllByUserId(userId);
    	return null;
    }

   

 
    public ContactsView findContacts(SearchContactParam searchContactParam,int firstPos,int LINEPERPAGE) {
    	List<Contact> contacts = contactDao.findContacts(searchContactParam, firstPos, LINEPERPAGE);
    	return new ContactsView(contacts, searchContactParam,userService.getManagers());
    }
    
    

    public long getTotalCount(SearchContactParam param){
    	return contactDao.getTotalCount(param);
    }
    
    
    
    
    public void deleteAll(List<Contact> contacts) {
    	contactDao.deleteAll(contacts);
    }
    
 
    public void activateAll(List<Contact> contacts) {
    	contactDao.activateAll(contacts);
    }
    
    
    public void deactivateAll(List<Contact> contacts) {
    	contactDao.deactivateAll(contacts);
    }


	public IUserService getUserService() {
		return userService;
	}


	public void setUserService(IUserService userService) {
		this.userService = userService;
	}


	public ICustomerService getCustomerService() {
		return customerService;
	}


	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}
}