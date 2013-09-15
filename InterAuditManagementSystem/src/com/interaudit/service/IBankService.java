package com.interaudit.service;

import java.util.List;

import com.interaudit.domain.model.Bank;
import com.interaudit.domain.model.data.Option;



/**
 * Service methods handling ank entities.
 */
/**
 * @author blocabe
 *
 */
public interface IBankService {
    /**
     * 
     * @return Bank objects list representing all Banks known by IAMS
     *         application.
     */
    List<Bank> getAll();
    
    /**
     * @return the list of Bank as option
     */
    List<Option> getBankAsOptions();
    
    public List<Option> getBankAsOptions2();

    /**
     * Returns a Bank object identified by given id.
     * 
     * @param id
     * @return Returns a Bank object identified by given id.
     */
    Bank getOne(Long id);
    
    
    Bank  getOneDetached(Long id);
    
    public Bank getOneFromAccountNumber(String accountNumber);
	 public Bank getOneFromCode(String code);
    
    
 
    /**
     * Delete a Bank object identified by given id.
     * 
     * @param id
     */
    void deleteOne(Long id); 
    
 
    /**
     * Returns a Bank object identified by given id.
     * 
     * @param id
     * @return Returns a Bank object identified by given id.
     */
    Bank saveOne(Bank Bank); 

    /**
     * Returns a Bank object identified by given id.
     * 
     * @param id
     * @return Returns a Bank object identified by given id.
     */
    Bank updateOne(Bank Bank);
    
    

    
}
