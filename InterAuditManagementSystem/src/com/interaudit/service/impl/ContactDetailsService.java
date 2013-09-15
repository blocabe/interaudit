package com.interaudit.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.interaudit.domain.model.Contact;
import com.interaudit.domain.model.Employee;
import com.interaudit.service.IContactDetailsService;
import com.interaudit.service.IContactService;
import com.interaudit.service.IUserService;
import com.interaudit.service.exc.BusinessException;
import com.interaudit.service.exc.ExceptionMessage;
import com.interaudit.service.view.ContactDetailsView;
import com.interaudit.util.BusinessMessagesConstants;

@Transactional
public class ContactDetailsService implements IContactDetailsService {
    
    private IContactService contactService;
    
    private IUserService jaspersUserService;


    public Contact activateContact(Contact contact, Employee loggedUser)
            throws BusinessException {

        throw new BusinessException("Not implemented");
    }

    public Contact createContact(Contact contact, Employee loggedUser)
            throws BusinessException {

        final Date now = Calendar.getInstance().getTime();
        //contact.setVersion(Constants.VERSION_NUMBER_FIRST_VALUE);
        contact.setCreateDate(now);
        contact.setUpdateDate(now);
        contact.setUpdatedUser(loggedUser.getFullName());
       // contact.setCreator(loggedUser);
        return contactService.saveOne(contact);
    }

  
    public Contact deactivateContact(Contact contact, Employee loggedUser)
            throws BusinessException {

        throw new BusinessException("Not implemented");
    }

 
    public ContactDetailsView getContactDetailsView(Long contactId, Employee currentUser) throws BusinessException {
        ContactDetailsView view = new ContactDetailsView();
        Contact contact = null;
        if(contactId != null) {
            contact = contactService.getOne(contactId);
            if(contact == null) {
                //it means the contact has been deleted
                throw new BusinessException(BusinessMessagesConstants.ERROR_CONTACT_NOT_FOUND);
            }
            //view.setContact(contact);
        } else {
        	contact = new Contact();
        	contact.setActive(true);
        	contact.setCreateDate(Calendar.getInstance().getTime());
        	//contact.setCreator(currentUser);
        }
    	//view.setContact(contact);
        
        return view;
    }

   
    public Contact updateContact(Contact contact, Employee loggedUser)
            throws BusinessException {

        Contact persistedInstance = contactService.getOne(contact.getId());
        if(persistedInstance == null) {
            //it means the contact has been deleted
            throw new BusinessException(BusinessMessagesConstants.ERROR_CONTACT_NOT_FOUND);
        }
        /*
        if(! persistedInstance.getVersion().equals(contact.getVersion())) {
            throw new BusinessException(
                    BusinessMessagesConstants.ERROR_CONTACT_CONCURRENT_UPDATE,
                    new Object[] { persistedInstance.getUpdatedUser() });
        }
        */
        
        final Date now = new Date();
        //persistedInstance.setVersion(new Integer(0));
        persistedInstance.setUpdateDate(now);
        persistedInstance.setUpdatedUser(loggedUser.getFullName());
        
        //update fields from the screen
        persistedInstance.setFirstName(contact.getFirstName());
        persistedInstance.setLastName(contact.getLastName());
        persistedInstance.setContactName(contact.getContactName());
        persistedInstance.setEmail(contact.getEmail());
        persistedInstance.setJobTitle(contact.getJobTitle());
        persistedInstance.setCompanyContactMail(contact.getCompanyContactMail());
        persistedInstance.setCompany(contact.getCompany());
        persistedInstance.setCompanyWebAddr(contact.getCompanyWebAddr());
        //persistedInstance.setCountry(contact.getCountry());
        //persistedInstance.setContactType(contact.getContactType());
        persistedInstance.setActive(contact.isActive());
        
        persistedInstance.setBusinessPhone(contact.getBusinessPhone());
        persistedInstance.setPersonalPhone(contact.getPersonalPhone());
        persistedInstance.setBusinessFax(contact.getBusinessFax());
        persistedInstance.setBusinessMobile(contact.getBusinessMobile());
        persistedInstance.setPersonalMobile(contact.getPersonalMobile());
        
        persistedInstance.setAddress(contact.getAddress());
        //persistedInstance.setCity(contact.getCity());
        //persistedInstance.setPostalCode(contact.getPostalCode());
        
        //persistedInstance.setDepartment(contact.getDepartment());
        //persistedInstance.setOffice(contact.getOffice());
       //persistedInstance.setProfession(contact.getProfession());
        
        persistedInstance.setMainActivity(contact.getMainActivity());

        return contactService.updateOne(persistedInstance);
    }

    private void cascadeRemoveContacts(List<Contact> contacts) {
    	excludeDeleted(contacts);
    	for(Contact contact : contacts) {
    		contact = contactService.getOne(contact.getId());
    		/*
    		List<User> users = contact.get;
    		for(JaspersUser user : users) {
    			List<Contact> persistedContacts = user.getContacts();
    			persistedContacts.remove(contact);
    			user.setContacts(persistedContacts);
    			jaspersUserService.updateOne(user);
    		}
    		*/
    	}
    }
    
    /**
     * Delete (from the database) the contacts contained in the given list.
     * @param contacts
     */
  
    public void deleteContacts(List<Contact> contacts) {
    	cascadeRemoveContacts(contacts);
    	contactService.deleteAll(contacts);
    }
    

    public List<ExceptionMessage> activateContacts(List<Contact> contacts) {
    	return updateActivation(contacts, true);
    }
    

    public List<ExceptionMessage> deactivateContacts(List<Contact> contacts) {
    	cascadeRemoveContacts(contacts);
    	return updateActivation(contacts, false);
    }
    
    private List<ExceptionMessage> updateActivation(List<Contact> contacts, boolean activate) {
    	List<ExceptionMessage> exceptionMessages = new ArrayList<ExceptionMessage>();
    	if(excludeDeleted(contacts)) {
            exceptionMessages.add(new ExceptionMessage(
                    BusinessMessagesConstants.ERROR_ADD_CONTACTS_NOT_FOUND));
    	}
    	if(activate) {
    		contactService.activateAll(contacts);
    	} else {
    		contactService.deactivateAll(contacts);
    	}
    	return exceptionMessages;
    }
    
    /**
     * Remove from the list those contacts which have been deleted since the page was displayed.
     * @param contacts
     * @return true if there was at least one contact removed from the list 
     * during the execution of this method; false otherwise. 
     */
    private boolean excludeDeleted(List<Contact> contacts) {
    	boolean returnValue = false;
    	for(int i=contacts.size()-1; i>=0; i--) {
    		Contact persistedContact = contactService.getOne(contacts.get(i).getId());
    		if(persistedContact == null) {
    			contacts.remove(i);
    			returnValue = true;
    		}
    	}
    	return returnValue;
    }

    public IContactService getContactService() {
        return contactService;
    }

    public void setContactService(IContactService contactService) {
        this.contactService = contactService;
    }

    
    public IUserService getJaspersUserService() {
        return jaspersUserService;
    }

    public void setJaspersUserService(IUserService jaspersUserService) {
        this.jaspersUserService = jaspersUserService;
    }
    
}
