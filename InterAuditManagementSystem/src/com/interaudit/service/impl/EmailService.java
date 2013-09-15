package com.interaudit.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.interaudit.domain.dao.IEmailDao;
import com.interaudit.domain.model.EmailData;
import com.interaudit.service.IEmailService;
import com.interaudit.service.param.SearchEmailParam;

@Transactional
public class EmailService implements IEmailService {
	//private Log log = LogFactory.getLog(TaskService.class);
    private IEmailDao emailDao;
    
    
    public EmailData getOne(Long id){
    	return emailDao.getOne(id);
    }
  
    public  EmailData  getOneDetached(Long id){
    	return emailDao.getOneDetached(id);
    }
	
    /**
     * Delete a Mission object identified by given id.
     * 
     * @param id
     */
   public  void deleteOne(Long id) {
    	emailDao.deleteOne(id);
    }
    
  
    
 
    /**
     * Returns a Mission object identified by given id.
     * 
     * @param id
     * @return Returns a Task object identified by given id.
     */
    public EmailData saveOne(EmailData email){
    	return emailDao.saveOne(email);
    }
    
   
    
   
    /**
     * Returns a Mission object identified by given id.
     * 
     * @param id
     * @return Returns a Task object identified by given id.
     */
    public EmailData updateOne(EmailData email){
    	return emailDao.updateOne(email);
    }
    
    
    /**
     * 
     * @return Mission objects list matching the criterias
     */
   public List<EmailData> searchEmails(  SearchEmailParam param){
    	return emailDao.searchEmails(param);
    }




	public IEmailDao getEmailDao() {
		return emailDao;
	}




	public void setEmailDao(IEmailDao emailDao) {
		this.emailDao = emailDao;
	}
   
   
    public List<EmailData> getMessagesNotSent(){
	 return this.emailDao.getMessagesNotSent();
    }
    
    
	
}
