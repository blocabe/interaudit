package com.interaudit.domain.dao;

import java.util.List;

import com.interaudit.domain.model.Customer;
import com.interaudit.domain.model.data.CustomerData;
import com.interaudit.service.param.SearchCustomerParam;

public interface ICustomerDao {

    // get a contact by its id
    public Customer getOne(Long id);
    
    public Customer getOneDetached(Long id);
    
//  get a contact by its id
    public Customer getOneFromCode(String code);
    
    public Customer getOneDetachedFromCompanyName(String companyName);
    
    public Customer getOneDetachedFromCompanyCode(String companyCode);
    
    public List<Customer> getFromCode(String code);
    public List<CustomerData> getCustomerDataFromCode(String code);
    
    public Customer getOneCustomerDetachedFromMissionId(Long idMission);
    

    // get all contacts
    public List<Customer> getAll();

    // save a contact
    public Customer saveOne(Customer customer);

    // update a contact
    public Customer updateOne(Customer contact);

    // delete a contact by its id
    public int deleteOne(Long id);
    
    public void deleteAll(List<Customer> customers);
    
    public List<Customer> searchCustomers(SearchCustomerParam param,int firstPos,int LINEPERPAGE);
    public List<CustomerData> searchCustomersData(SearchCustomerParam param,int firstPos,int LINEPERPAGE);
    
    public long getTotalCount(SearchCustomerParam param);
    
    
    
   

    /**
     * 
     * @param userId
     * @return
     */
    public List<Customer> getAllByUserManagerId(Long userManagerId);

    
    /**
     * Activate all given contacts.
     * @param contacts the list of contacts to be activated.
     */
    public void activateAll(List<Customer> customers);
    
    /**
     * Dedeactivate all given contacts.
     * @param contacts the list of contacts to be deactivated.
     */
    public void deactivateAll(List<Customer> customers);

}
