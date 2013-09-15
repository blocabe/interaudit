package com.interaudit.domain.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import com.interaudit.domain.dao.IOriginDao;
import com.interaudit.domain.dao.exc.DaoException;
import com.interaudit.domain.model.Origin;
import com.interaudit.service.exc.BusinessException;
import com.interaudit.service.exc.ExceptionMessage;

public class OriginDao implements IOriginDao {
	
	@PersistenceContext
	private EntityManager em;
	private static final Logger  logger      = Logger.getLogger(OriginDao.class);
	
	public void deleteOne(Long id) {
		
		try{			
			Origin origin = (Origin)em.find(Origin.class, id);
			if (origin == null) {
				throw new DaoException(2);
			}
			em.remove(origin);			
		}
		catch(Exception e){
			logger.error(e.getMessage());
			 throw new BusinessException(new ExceptionMessage("Failed in OriginDao :  deleteOne..."));
	        
		}finally{
			em.close();
		}
		
		
	}
	
	@SuppressWarnings("unchecked")

	public List<Origin> getAll() {
		
		try{
			return em
			.createQuery("select o from Origin o order by o.name")
			.getResultList();
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in OriginDao :  getAll..."));

		}finally{
			em.close();
		}
		
		
	}
	
	
	

	public Origin getOne(Long id) {
		
		try{
			return (Origin)em.find(Origin.class, id);
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in OriginDao :  getOne..."));

		}finally{
			em.close();
		}
		
		
	}
	
	@SuppressWarnings("unchecked")
	public Origin getOneDetached(Long id){
		
		try{
        	String queryString = "select c from Origin c  where c.id = :id";
            List resultList = em.createQuery(queryString).setParameter("id",id).getResultList();
            if (resultList.size() > 0) {
                return (Origin) resultList.get(0);
            }
            return null;
        	}catch(Exception e){
    			logger.error(e.getMessage());
    			throw new BusinessException(new ExceptionMessage("Failed in OriginDao :  getOneDetached..."));

    		}finally{
        		em.close();
        	}
	}


	public Origin saveOne(Origin origin) {		
		try{			
			em.persist(origin);	      
	        return origin;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in OriginDao :  saveOne..."));
	        
		}finally{
			em.close();
		}
		
		
		
	}


	public Origin updateOne(Origin origin) {
				
		try{			
			 em.merge(origin);		        
	        return origin;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in OriginDao :  updateOne..."));
	        
		}finally{
			em.close();
		}
		
	}
	
	

}
