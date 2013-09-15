package com.interaudit.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.StringTokenizer;

import javax.persistence.EntityNotFoundException;

import org.springframework.transaction.annotation.Transactional;

import com.interaudit.domain.dao.IContractDao;
import com.interaudit.domain.model.Contract;
import com.interaudit.domain.model.Customer;
import com.interaudit.domain.model.MissionTypeTaskLink;
import com.interaudit.domain.model.data.ContractData;
import com.interaudit.domain.model.data.Option;
import com.interaudit.service.IContractService;
import com.interaudit.service.ICustomerService;
import com.interaudit.service.param.SearchContractParam;

@Transactional
public class ContractService implements IContractService {

    private IContractDao ContractDao;
    private ICustomerService  customerService;

    public void updateInvalideContracts(){
    	ContractDao.updateInvalideContracts();
    }
   
    public void deleteOne(Long id) {
        ContractDao.deleteOne(id);
//        int deleted = ContractDao.deleteOne(id);
//        if(deleted == 0) {
//            throw new RuntimeException("Attempt to delete a Contract that does not exist!");
//        }
    }


    public List<Contract> getAll() {
        return ContractDao.getAll();
    }
    
    public List<Option> getMissionTypeOptions(){
    	return  ContractDao.getMissionTypeOptions();
    }
    
    public List<Option> getMissionTypeOptions2(){
    	return  ContractDao.getMissionTypeOptions2();
    }
    
    public List<Option> getAllContractAsOptions(){
    	List<Option> options= new ArrayList<Option>(); 	  
       	for(Contract contract : getAll()){
       		 options.add(new Option(contract.getId().toString(), contract.getReference()));
       	}
       	return options;
    }
    
    public List<String> getAllValidContractReference(){
    	List<Contract>  contracts = this.getAll();
    	List<String> allReferences = new ArrayList<String>();
    	for(Contract contract :  contracts)allReferences.add(contract.getReference());
    	
    	return allReferences;
    }
    
    public List<Contract> findActiveContracts(){
    	return ContractDao.findActiveContracts();
    }
    
    
    public String removeSpaces(String s) {
    	  StringTokenizer st = new StringTokenizer(s," ",false);
    	  String t="";
    	  while (st.hasMoreElements()) t += st.nextElement();
    	  return t;
    	}


    
    
    public Contract createContract(Contract contract){
    	
    	Customer customer = customerService.getOne(contract.getCustomer().getId());
    	
    	String temp =  removeSpaces(customer.getCompanyName().trim().toUpperCase());
    	String reference = temp + "_" + ContractDao.getNextContractSequence(contract.getCustomer().getId());
		
    	contract.setReference(reference);
		
    	contract.setCustomer(customer);
		
		customer.getContracts().add(contract);
		
		return ContractDao.saveOne(contract);
    	
    }
    
    public Contract createContract(com.interaudit.domain.model.Customer customerSaved){
    	
    	Customer customer = customerService.getOne(customerSaved.getId());
    	
    	String temp =  removeSpaces(customer.getCompanyName().trim().toUpperCase());
    	String reference = temp + "_" + ContractDao.getNextContractSequence(customer.getId());
		
    	String description = customer.getMainActivity();
    	double amount =  customer.getDefaultContractAmount();
    	String currency = "EUR";
		String language = "EN";
		
		GregorianCalendar calendar = new java.util.GregorianCalendar(); 
		Date fromDate = calendar.getTime() ;
		calendar.setTime( fromDate );
		calendar.add (Calendar.YEAR, 10);		 
		Date toDate = calendar.getTime() ;;
		String missionType = customer.getDefaultContractType().getTaskCode();
    	
		Contract contract = new Contract(  reference,  description,
    			 fromDate,  toDate,  amount,  currency,
   			  language, missionType) ;
    
    	contract.setCustomer(customer);
		
		customer.getContracts().add(contract);
		
		return ContractDao.saveOne(contract);
    }
    
    public Contract updateContract(Contract contract){
    	Customer customer = customerService.getOne(contract.getCustomer().getId());
		
		contract.setCustomer(customer);
				
		return ContractDao.updateOne(contract);
    }
    
    public boolean existContractForCustomerAndType(Long customerId, String type){
    	return ContractDao.existContractForCustomerAndType(customerId, type);
    }
    


    public Contract getOne(Long id) {
        try {
			return ContractDao.getOne(id);
		} catch (EntityNotFoundException e) {
			return null;
		}
    }
    
    public MissionTypeTaskLink getOneMissionTypeTaskLink(Long id){
    	try {
			return ContractDao.getOneMissionTypeTaskLink(id);
		} catch (EntityNotFoundException e) {
			return null;
		}
    }
    
    public Contract getOneDetached(Long id){
    	return  ContractDao.getOneDetached(id);
    }
    
   
    
    public Contract getOneFromCode(String reference){
    	return ContractDao.getOneFromCode(reference);
    }

  
    public Contract saveOne(Contract Contract) {
        return ContractDao.saveOne(Contract);
    }

   
    public Contract updateOne(Contract Contract) {
        return ContractDao.updateOne(Contract);
    }

    public IContractDao getContractDao() {
        return ContractDao;
    }

    public void setContractDao(IContractDao ContractDao) {
        this.ContractDao = ContractDao;
    }

  
    public List<Contract> getAllByUserId(Long userId) {
        //return ContractDao.getAllByUserId(userId);
    	return null;
    }

   
    

    public long getTotalCount(SearchContractParam searchContractParam){
    	return ContractDao.getTotalCount( searchContractParam);
    }
 
    public List<ContractData> findContracts(SearchContractParam searchContractParam,int firstPos,int LINEPERPAGE) {
        //return ContractDao.findContracts(searchContractParam, firstPos, LINEPERPAGE);
    	return ContractDao.findContractsData(searchContractParam, firstPos, LINEPERPAGE);
    }
    
    
    public void deleteAll(List<Contract> Contracts) {
    	ContractDao.deleteAll(Contracts);
    }
    
 
    public void activateAll(List<Contract> Contracts) {
    	ContractDao.activateAll(Contracts);
    }
    
    
    public void deactivateAll(List<Contract> Contracts) {
    	ContractDao.deactivateAll(Contracts);
    }


	public ICustomerService getCustomerService() {
		return customerService;
	}


	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}
}