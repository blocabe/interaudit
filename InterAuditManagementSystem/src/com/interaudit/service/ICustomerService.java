package com.interaudit.service;

import java.util.List;

import com.interaudit.domain.model.Customer;
import com.interaudit.domain.model.data.Option;
import com.interaudit.service.param.SearchContractParam;
import com.interaudit.service.param.SearchCustomerParam;
import com.interaudit.service.view.CustomerView;




/**
 * @author blocabe
 *
 */
public interface ICustomerService {

    /**
     * @param id
     */
    public void deleteOne(Long id);

 
    /**
     * @return
     */
    public List<Customer> getAll();

    
    /**
     * @param id
     * @return
     */
    public Customer getOne(Long id);
    
    public Customer getOneDetached(Long id);
    
    public Customer getOneDetachedFromCompanyName(String companyName);
    
    public Customer getOneDetachedFromCompanyCode(String companyCode);
    
    public Customer createCustomer(Customer customer);
    
    public Customer updateCustomer(Customer customer);
    
    public Customer getOneCustomerDetachedFromMissionId(Long idMission);

  
    /**
     * @param customer
     * @return
     */
    public Customer saveOne(Customer customer);

   
    /**
     * @param customer
     * @return
     */
    public Customer updateOne(Customer customer);
    
    
    public  List<Option> getAllCustomerAsOptions();


    /**
     * @param searchField
     * @param onlyActive
     * @return
     */
    public List<Customer> searchCustomers(String searchField, boolean onlyActive,int firstPos,int LINEPERPAGE);
    
    public CustomerView searchCustomers(SearchCustomerParam param,int firstPos,int LINEPERPAGE);

    long getTotalCount(SearchCustomerParam param);
    
	/**
	 * @param param
	 * @return
	 */
	public CustomerView getCustomerFromCode(SearchCustomerParam param);

    
}
