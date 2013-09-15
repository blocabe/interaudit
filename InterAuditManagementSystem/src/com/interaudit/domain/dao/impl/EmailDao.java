package com.interaudit.domain.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import com.interaudit.domain.dao.IEmailDao;
import com.interaudit.domain.dao.exc.DaoException;
import com.interaudit.domain.model.EmailData;
import com.interaudit.service.exc.BusinessException;
import com.interaudit.service.exc.ExceptionMessage;
import com.interaudit.service.param.SearchEmailParam;

/**
 * @author bernard
 *
 */
public class EmailDao implements IEmailDao {

	@PersistenceContext
	private EntityManager em;
	private static final Logger  logger      = Logger.getLogger(EmailDao.class);
	
	
    public void deleteOne(Long id) {
				
		try{			
			EmailData email =em.find(EmailData.class, id);
	        if (email == null) {
	            throw new DaoException(2);
	        }
	        em.remove(email);   	        
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in EmailDao :  deleteOne..."));
	        
		}finally{
			em.close();
		}
		
		     
    }

	
    
	
	 @SuppressWarnings("unchecked")
	 
	public  EmailData  getOneDetached(Long id){
		 try{
	        	String queryString = "select b from EmailData b  where b.id = :id";
	            List resultList = em.createQuery(queryString).setParameter("id",id).getResultList();
	            if (resultList.size() > 0) {
	                return (EmailData) resultList.get(0);
	            }
	            return null;
	        	}catch(Exception e){
	    			logger.error(e.getMessage());
	    			throw new BusinessException(new ExceptionMessage("Failed in EmailDao :  getOneDetached..."));
	    	        
	    		}finally{
	        		em.close();
	        	}
	 }
	 
	
    
    public EmailData getOne(Long id) {
    	

		try{
			 return em.find(EmailData.class, id);
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in EmailDao :  getOne..."));

		}finally{
			em.close();
		}
       
    }

    
    

    
    public EmailData saveOne(EmailData email) {    	
		try{			
			em.persist(email);
	        
	        return email;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in EmailDao :  saveOne..."));
	        
		}finally{
			em.close();
		}
		
    	
        
    }

    
    public EmailData updateOne(EmailData email) {
    	
		try{			
			EmailData ret = em.merge(email);	        
	        return ret;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in EmailDao :  updateOne..."));
	        
		}finally{
			em.close();
		}
    }
    
    /**
     * 
     * @return Mission objects list matching the criterias
     */
    public List<EmailData> searchEmails(  SearchEmailParam param){
    	return null;
    }
    
    
    /**
     * @param id
     * @return the messages from the target mission
     */
 @SuppressWarnings("unchecked")
 public List<EmailData> getMessagesNotSent() { 
	 String queryString = "select m from EmailData m  where m.status ='PENDING' and m.sentDate IS NULL order by m.created)";
	 try{   	
		
	        List resultList = em.createQuery(queryString).getResultList();
	        return resultList;
        
    	}catch(Exception e){        	
    		logger.error(e.getMessage());
    		throw new BusinessException(new ExceptionMessage("Failed in EmailDao :  getMessagesNotSent..."));
        }
    	finally{
    		em.close();
    	}
    
    }
	 

    
}
