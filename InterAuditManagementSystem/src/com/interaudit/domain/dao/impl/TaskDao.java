package com.interaudit.domain.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.interaudit.domain.dao.ITaskDao;
import com.interaudit.domain.dao.exc.DaoException;
import com.interaudit.domain.model.Task;
import com.interaudit.service.exc.BusinessException;
import com.interaudit.service.exc.ExceptionMessage;

/**
 * @author bernard
 *
 */
public class TaskDao implements ITaskDao {

	@PersistenceContext
	private EntityManager em;
	private static final Logger  logger      = Logger.getLogger(TaskDao.class);
	
	
    public void deleteOne(Long id) {
		
		try{		
			Task task =em.find(Task.class, id);
	        if (task == null) {
	            throw new DaoException(2);
	        }
	        em.remove(task);    	       
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in TaskDao :  deleteOne..."));
	        
		}finally{
			em.close();
		}
		
		    
    }

	
    @SuppressWarnings("unchecked")
    public List<Task> getAll() {
		
		try{
			//return em.createQuery("select distinct t from Task t order by t.name asc").getResultList();
			List<String> codes = new ArrayList<String>();			
			codes.add("CONGLEG");
			codes.add("FERIE");
			codes.add("MALAD");
			codes.add("CONGEPARE");
			codes.add("CONGESS");
			codes.add("FORMATION");
			codes.add("TEMPSPART");
			codes.add("REVUE");			
			codes.add("FCLIENT");
			codes.add("SEC");
			codes.add("DATEV");		
			//codes.add("NONCHARGEABLE");
			
			
			
			
			StringBuilder taskAsOptions = new StringBuilder("select  distinct t from Task t where t.code in (:codes)  order by t.name asc");			
			Query q = em.createQuery(taskAsOptions.toString());
			q.setParameter("codes", codes);
			List<Task>  resultList = (List<Task>) q.getResultList();
			return resultList;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in TaskDao :  getAll..."));

		}finally{
			em.close();
		}
 
    }
    
    
    @SuppressWarnings("unchecked")
    public List<Task> getAll3() {
		
		try{
			//return em.createQuery("select distinct t from Task t order by t.name asc").getResultList();
			List<String> codes = new ArrayList<String>();			
			codes.add("NONCHARGEABLE");			
			StringBuilder taskAsOptions = new StringBuilder("select  distinct t from Task t where t.code in (:codes)  order by t.name asc");			
			Query q = em.createQuery(taskAsOptions.toString());
			q.setParameter("codes", codes);
			List<Task>  resultList = (List<Task>) q.getResultList();
			return resultList;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in TaskDao :  getAll3..."));

		}finally{
			em.close();
		}
 
    }
    
    @SuppressWarnings("unchecked")
    public List<Task> getAll2() {
		
		try{
			
			List<String> codes = new ArrayList<String>();			
			codes.add("NONCHARGEABLE");	
			StringBuilder taskAsOptions = new StringBuilder("select  distinct t from Task t where t.optional=false and t.code not in (:codes) order by t.name asc");			
			Query q = em.createQuery(taskAsOptions.toString());
			q.setParameter("codes", codes);
			
			List<Task>  resultList = (List<Task>) q.getResultList();
			return resultList;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in TaskDao :  getAll2..."));

		}finally{
			em.close();
		}
		
        
    }
    
    /*
     * (non-Javadoc)
     * @see com.interaudit.domain.dao.ITaskDao#getOne(java.lang.Long)
     */

    
    
    public Task getOne(Long id) {
    	
    	try{
    		 return em.find(Task.class, id);
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in TaskDao :  getOne..."));

		}finally{
			em.close();
		}
		
       
    }

    
    

    
    public Task saveOne(Task task) {
    	
		try{			
			em.persist(task);	     
	        return task;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in TaskDao :  saveOne..."));
	        
		}finally{
			em.close();
		}
		
    	
        
    }

    
    public Task updateOne(Task task) {
    	    	
		try{			
			Task ret = em.merge(task);	        
	        return ret;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in TaskDao :  updateOne..."));
	        
		}finally{
			em.close();
		}
		
        
    }
    
    /**
     * Returns a Task object identified by given taskName.
     * 
     * @param taskName
     * @return Returns a Task object identified by given taskName.
     */
	@SuppressWarnings("unchecked")
	
	public  List<Task> getTaskByName(String taskName){
		try{
			return em
	        .createQuery(
	                "SELECT distinct t FROM Task t  WHERE t.name like upper(:taskName) ORDER BY t.name ")
	        .setParameter("taskName", taskName).getResultList();
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in TaskDao :  getTaskByName..."));

		}finally{
			em.close();
		}
		
		
	}
	
	
	public  Task getTaskByCode(String taskCode){
		try{
			return (Task)em
	        .createQuery(
	                "SELECT distinct t FROM Task t  WHERE t.code = :taskCode")
	        .setParameter("taskCode", taskCode).getResultList().get(0);
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in TaskDao :  getTaskByCode..."));

		}finally{
			em.close();
		}
		
		
	}
	
	public  Task getTaskByCodePrestation(String codePrestation){
		try{
			return (Task)em
	        .createQuery(
	                "SELECT distinct t FROM Task t  WHERE t.codePrestation = :codePrestation")
	        .setParameter("codePrestation", codePrestation).getResultList().get(0);
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in TaskDao :  getTaskByCodePrestation..."));

		}finally{
			em.close();
		}
		
		
	}
	

    
}
