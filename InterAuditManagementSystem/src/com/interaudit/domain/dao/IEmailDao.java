package com.interaudit.domain.dao;

import java.util.List;

import com.interaudit.domain.model.EmailData;
import com.interaudit.service.param.SearchEmailParam;


public interface IEmailDao {
	
	 
    
	public EmailData getOne(Long id);
	
	
	public  EmailData  getOneDetached(Long id);
	
	
	
	
    /**
     * Delete a Mission object identified by given id.
     * 
     * @param id
     */
    void deleteOne(Long id); 
    
  
    
 
    /**
     * Returns a Mission object identified by given id.
     * 
     * @param id
     * @return Returns a Task object identified by given id.
     */
    EmailData saveOne(EmailData email); 
    
   
    
   
    /**
     * Returns a Mission object identified by given id.
     * 
     * @param id
     * @return Returns a Task object identified by given id.
     */
    EmailData updateOne(EmailData email);
    
    
    /**
     * 
     * @return Mission objects list matching the criterias
     */
    List<EmailData> searchEmails(  SearchEmailParam param);
    
    public List<EmailData> getMessagesNotSent();
    
    

}
