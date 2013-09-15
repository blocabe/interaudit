package com.interaudit.service;

import java.util.List;

import com.interaudit.domain.model.Contact;
import com.interaudit.domain.model.Employee;
import com.interaudit.service.exc.BusinessException;
import com.interaudit.service.exc.ExceptionMessage;


/**
 * Model describing service methods that must be provided in order to handle a
 * Contact.
 * 
 * @author Daniel Doboga
 * 
 */
public interface IContactDetailsService {
    /**
     * Provides an object encapsulating all information to be displayed on
     *         the contact details screen.
     * @param contactId
     * @param loggedUser
     * @return Returns an object encapsulating all information displayed on
     *         contact details screen.
     * @throws BusinessException
     *             Throws BusinessException if the given contactId does not
     *             identify a contact.
     
    public ContactDetailsView getContactDetailsView(Long contactId,
            User loggedUser) throws BusinessException;
            */

    /**
     * Merges the given Contact object with its persisted version, by performing
     * all required processing related to an update made by the logged user
     * against the given contact, from the user GUI.
     * @param contact the Contact object to be updated
     * @param loggedUser the user currently logged in to the application
     * @return the updated contact
     * @throws BusinessException
     */
    public Contact updateContact(Contact contact, Employee loggedUser)
            throws BusinessException;

    /**
     * Persists a new created Contact object to the application persistence support
     * (such as the database).
     * @param contact the Contact object to be persisted.
     * @param loggedUser the user currently logged in to the application
     * @return the contact after persisting it.
     * @throws BusinessException
     */
    public Contact createContact(Contact contact, Employee loggedUser)
            throws BusinessException;

    /**
     * Activates the given contact by setting the active property to true against
     * its persisted version from the database.
     * @param contact the Contact instance to be activated
     * @param loggedUser the user currently logged in to the application
     * @return the contact after being activated
     * @throws BusinessException
     */
    public Contact activateContact(Contact contact, Employee loggedUser)
            throws BusinessException;

    /**
     * Deactivates the given contact by setting the active property to true against
     * its persisted version from the database.
     * @param contact the Contact instance to be activated
     * @param loggedUser the user currently logged in to the application
     * @return the contact after being deactivated
     * @throws BusinessException
     */
    public Contact deactivateContact(Contact contact, Employee loggedUser)
            throws BusinessException;

    /**
     * Deletes the Contact objects contained in the given list.
     * @param contacts the list of contacts to be deleted
     */
    public void deleteContacts(List<Contact> contacts);

    /**
     * Activates all the contacts contained by the given list.
     * by setting the active property to true against
     * the persisted version of each contact.
     * @param contacts the list of contacts to be activated
     * @return a list of exception messages to be displayed to the
     * user GUI, if there will be encountered some synchronization 
     * problems with the database during the execution of this method
     * (ex: some of the given contacts have been deleted by someone else)
     */
    public List<ExceptionMessage> activateContacts(List<Contact> contacts);

    /**
     * Deactivates all the contacts contained by the given list.
     * by setting the active property to true against
     * the persisted version of each contact.
     * @param contacts the list of contacts to be deactivated
     * @return a list of exception messages to be displayed to the
     * user GUI, if there will be encountered some synchronization 
     * problems with the database during the execution of this method
     * (ex: some of the given contacts have been deleted by someone else)
     */
    public List<ExceptionMessage> deactivateContacts(List<Contact> contacts);

}
