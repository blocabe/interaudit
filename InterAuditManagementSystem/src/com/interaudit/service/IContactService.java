package com.interaudit.service;

import java.util.List;

import com.interaudit.domain.model.Contact;
import com.interaudit.domain.model.data.Option;
import com.interaudit.service.param.SearchContactParam;
import com.interaudit.service.view.ContactsView;



public interface IContactService {

    /**
     * Retrieves the Contact instance identified by the given id
     * from the persistence support.
     * @param id the id of the entity to be retrieved
     * @return the Contact instance identified by the given id
     * if it exists, or null otherwise.
     */
	
	public List<Option> getAllContactAsOptions();
	
    public Contact getOne(Long id);
    
    public Contact getOneDetached(Long id);
    
    public Contact createContact(Contact contact);
    
    public Contact updateContact(Contact contact);
    
   
	

    /**
     * Retrieves all existing Contact instances.
     * @return a list containing all existing Contact instances.
     */
    public List<Contact> getAll();

    /**
     * Retrieves all Contact objects which reside within the
     * "my contacts" collection of the JaspersUser identified by
     * the given id.
     * @param userId the id identifying the JaspersUser who's
     * contacts we are looking for.
     * @return a list containing all Contacts which reside within 
     * the "my contacts" collection of the given JaspersUser
     */
    public List<Contact> getAllByUserId(Long userId);

    /**
     * Adds a new created Contact to the persistence support.
     * @param contact the contact to be saved.
     * @return the given Contact after being persisted.
     */
    public Contact saveOne(Contact contact);

    /**
     * Merges the given Contact object with its existing (persisted)
     * version.
     * @param contact the Contact object to be updated
     * @return the given contact after it is updated.
     */
    public Contact updateOne(Contact contact);

    /**
     * Deletes the Contact object identified by the given id
     * (if it exists).
     * @param id the id of the object to be deleted.
     */
    public void deleteOne(Long id);

    
    /**
     * Search for Contact objects according to the filtering criteria
     * encapsulated by the given parameter.
     * @param searchContactParam
     * @return a list containing all the Contact objects which match
     * the filtering criteria
     */
    public ContactsView  findContacts(SearchContactParam searchContactParam,int firstPos,int LINEPERPAGE);
    long getTotalCount(SearchContactParam param);
    
    /**
     * Delete all given contacts.
     * @param contacts the list of contacts to be deleted.
     */
    public void deleteAll(List<Contact> contacts);
    
    /**
     * Activate all given contacts.
     * @param contacts the list of contacts to be activated.
     */
    public void activateAll(List<Contact> contacts);
    
    /**
     * Deactivate all given contacts.
     * @param contacts the list of contacts to be deactivated.
     */
    public void deactivateAll(List<Contact> contacts);
    
   
}
