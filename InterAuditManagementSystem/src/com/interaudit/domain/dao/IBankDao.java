package com.interaudit.domain.dao;

import java.util.List;

import com.interaudit.domain.model.Bank;

public interface IBankDao {
	
	/**
     * 
     * @return Bank objects list representing all Banks known by IAMS
     *         application.
     */
	public  List<Bank> getAll();

    /**
     * Returns a Bank object identified by given id.
     * 
     * @param id
     * @return Returns a Bank object identified by given id.
     */
	public Bank getOne(Long id);
	
	 public  Bank  getOneDetached(Long id);
	 
	 public Bank getOneFromAccountNumber(String accountNumber);
	 public Bank getOneFromCode(String code);
    
    
 
    /**
     * Delete a Bank object identified by given id.
     * 
     * @param id
     */
	public void deleteOne(Long id); 
    
 
    /**
     * Returns a Bank object identified by given id.
     * 
     * @param id
     * @return Returns a Bank object identified by given id.
     */
	public Bank saveOne(Bank Bank); 

    /**
     * Returns a Bank object identified by given id.
     * 
     * @param id
     * @return Returns a Bank object identified by given id.
     */
	public Bank updateOne(Bank Bank);
    
    /**
     * Returns a Bank object identified by given BankName.
     * 
     * @param BankName
     * @return Returns a Bank object identified by given BankName.
     */
	public  List<Bank> getBankByName(String BankName);

}
