package com.interaudit.domain.dao;

import java.util.List;

import com.interaudit.domain.model.Contact;
import com.interaudit.service.param.SearchContactParam;

public interface IContactDao {

    // get a contact by its id
    public Contact getOne(Long id);
    
    public Contact getOneDetached(Long id);

    // get all contacts
    public List<Contact> getAll();

    // save a contact
    public Contact saveOne(Contact contact);

    // update a contact
    public Contact updateOne(Contact contact);

    // delete a contact by its id
    public int deleteOne(Long id);
   

    /**
     * 
     * @param userId
     * @return
     */
   // public List<Contact> getAllByUserId(Long userId);

    /**
     * 
     * @return
     */
   // public List<String> getAllDistinctContactCountries();

    /**
     * 
     * @return
     */
    //public List<String> getAllDistinctContactTypes();

    /**
     * 
     * @param searchContactParam
     * @return
     */
    public List<Contact> findContacts(SearchContactParam searchContactParam,int firstPos,int LINEPERPAGE);
 
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
     * Dedeactivate all given contacts.
     * @param contacts the list of contacts to be deactivated.
     */
    public void deactivateAll(List<Contact> contacts);

}
