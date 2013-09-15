package com.interaudit.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.interaudit.domain.dao.IBankDao;
import com.interaudit.domain.model.Bank;
import com.interaudit.domain.model.data.Option;
import com.interaudit.service.IBankService;

@Transactional
public class BankService implements IBankService {
	//private Log log = LogFactory.getLog(BankService.class);
    private IBankDao bankDao;
    
    /**
     * 
     * @return Bank objects list representing all Banks known by IAMS
     *         application.
     */
    public List<Bank> getAll(){
    	return bankDao.getAll();
    }
    
    /**
     * @return the list of Bank as option
     */
    public List<Option> getBankAsOptions(){
    	List<Option> options= new ArrayList<Option>();
    	List<Bank> allBanks = this.getAll();
    	for(Bank bank : allBanks){
    		 options.add(new Option(bank.getId().toString(),bank.getName()+" : " + bank.getAccountNumber()));
    	}
    	return options;
    }
    
    public List<Option> getBankAsOptions2(){
    	List<Option> options= new ArrayList<Option>();
    	List<Bank> allBanks = this.getAll();
    	for(Bank bank : allBanks){
    		 options.add(new Option(bank.getCode(),bank.getName()+" : " + bank.getAccountNumber()));
    	}
    	return options;
    }
    

    /**
     * Returns a Bank object identified by given id.
     * 
     * @param id
     * @return Returns a Bank object identified by given id.
     */
    public Bank getOne(Long id){
    	return bankDao.getOne(id);
    }
    
   public  Bank  getOneDetached(Long id){
    	return bankDao.getOneDetached(id);
    }
   
  
  
	 public Bank getOneFromAccountNumber(String accountNumber){
		 	return bankDao.getOneFromAccountNumber(accountNumber);
	 }
	 
	 public Bank getOneFromCode(String code){
		 return bankDao.getOneFromCode(code);
	 }
    
    
 
    /**
     * Delete a Bank object identified by given id.
     * 
     * @param id
     */
    public void deleteOne(Long id){
    	bankDao.deleteOne(id);
    }
    
 
    /**
     * Returns a Bank object identified by given id.
     * 
     * @param id
     * @return Returns a Bank object identified by given id.
     */
   public Bank saveOne(Bank Bank){
	   return bankDao.saveOne(Bank);
   }

    /**
     * Returns a Bank object identified by given id.
     * 
     * @param id
     * @return Returns a Bank object identified by given id.
     */
   public Bank updateOne(Bank Bank){
    	return bankDao.updateOne(Bank);
    }
    
    /**
     * Returns a list of Bank object identified by given BankName.
     * 
     * @param BankName
     * @return Returns a list of Bank object identified by given BankName.
     */
   public List<Bank> getBankByName(String BankName){
    	return bankDao.getBankByName(BankName);
    }

	public IBankDao getbankDao() {
		return bankDao;
	}

	public void setbankDao(IBankDao bankDao) {
		this.bankDao = bankDao;
	}
    
    
	
}
