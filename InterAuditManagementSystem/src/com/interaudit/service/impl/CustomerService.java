package com.interaudit.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.interaudit.domain.dao.ICustomerDao;
import com.interaudit.domain.model.Customer;
import com.interaudit.domain.model.Employee;
import com.interaudit.domain.model.Origin;
import com.interaudit.domain.model.data.CustomerData;
import com.interaudit.domain.model.data.Option;
import com.interaudit.service.ICustomerService;
import com.interaudit.service.IOriginService;
import com.interaudit.service.IUserService;
import com.interaudit.service.param.SearchCustomerParam;
import com.interaudit.service.view.CustomerView;

@Transactional
public class CustomerService implements ICustomerService {
	//private Log log = LogFactory.getLog(IUserService.class);
    private ICustomerDao customerDao;
    private IUserService userService;
    private IOriginService originService;
    
    
    
	
	public void deleteOne(Long id) {
		customerDao.deleteOne(id);
	}
	public List<Customer> getAll() {
		return customerDao.getAll(); 
	}
	public Customer getOne(Long id) {
		return customerDao.getOne(id);
	}
	
	 public Customer getOneDetached(Long id){
		 return customerDao.getOneDetached(id);
	 }
	 
	 public Customer getOneDetachedFromCompanyName(String companyName){
	    	return  customerDao.getOneDetachedFromCompanyName(companyName);
	    }
	 
	 public Customer getOneDetachedFromCompanyCode(String companyCode){
		 return  customerDao.getOneDetachedFromCompanyCode(companyCode);
	 }
	 
	 public Customer createCustomer(Customer customer) {
		 	
		 	Employee associeSignataire = userService.getOne(customer.getAssocieSignataire().getId());
			customer.setAssocieSignataire(associeSignataire);
			
			Employee customerManager = userService.getOne(customer.getCustomerManager().getId());
			customer.setCustomerManager(customerManager);
			
			Origin origin = originService.getOne(customer.getOrigin().getId());
			customer.setOrigin(origin);
			
			return customerDao.saveOne(customer);
	 }
	
	public Customer saveOne(Customer jaspersUser) {
		return customerDao.saveOne(jaspersUser);
	}
	
	  public Customer getOneCustomerDetachedFromMissionId(Long idMission){
		  return customerDao.getOneCustomerDetachedFromMissionId(idMission);
	  }
	
	public Customer updateCustomer(Customer customer){
		
		Employee associeSignataire = userService.getOne(customer.getAssocieSignataire().getId());
		customer.setAssocieSignataire(associeSignataire);
		
		Employee customerManager = userService.getOne(customer.getCustomerManager().getId());
		customer.setCustomerManager(customerManager);
		
		Origin origin = originService.getOne(customer.getOrigin().getId());
		customer.setOrigin(origin);
		
		return updateOne(customer);
	}
	  
	public Customer updateOne(Customer jaspersUser) {
		return customerDao.updateOne(jaspersUser);
	}
	
    public List<Customer> searchCustomers(String searchField, boolean onlyActive,int firstPos,int LINEPERPAGE){
    	return null;
    }
    
    public CustomerView searchCustomers(SearchCustomerParam param,int firstPos,int LINEPERPAGE){
    	
    	//List<Customer> customers = customerDao.searchCustomers(param, firstPos, LINEPERPAGE);
    	List<CustomerData> customers = customerDao.searchCustomersData( param, firstPos, LINEPERPAGE);
    	return new CustomerView (customers, param, userService.getManagersAsOptions(),userService.getDirectorsAsOptions(),userService.getPartnersAsOptions());
    			 
    }
    
    public long getTotalCount(SearchCustomerParam param){
    	return customerDao.getTotalCount(param);
    }
    
    
    public  List<Option> getAllCustomerAsOptions(){
    	
	    List<Option> options= new ArrayList<Option>();
		   
		// get all customers
		 List<Customer> allcustomers = this.getAll();
	   
	   	for(Customer customer : allcustomers){
	   		 options.add(new Option(customer.getId().toString(),customer.getCompanyName()));
	   	}
	   	return options;
	   	
    }
    
    
    /**
	 * @param param
	 * @return
	 */
	public CustomerView getCustomerFromCode(SearchCustomerParam param){
		
		List<CustomerData> customers =  customerDao.getCustomerDataFromCode(param.getCustomerCode());
    	return new CustomerView (customers, param, userService.getManagersAsOptions(),userService.getDirectorsAsOptions(),userService.getPartnersAsOptions());
	}
    
	public ICustomerDao getCustomerDao() {
		return customerDao;
	}
	public void setCustomerDao(ICustomerDao customerDao) {
		this.customerDao = customerDao;
	}
	public IUserService getUserService() {
		return userService;
	}
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	public IOriginService getOriginService() {
		return originService;
	}
	public void setOriginService(IOriginService originService) {
		this.originService = originService;
	}

   
}
