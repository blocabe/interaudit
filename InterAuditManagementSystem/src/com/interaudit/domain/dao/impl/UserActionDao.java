package com.interaudit.domain.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import com.interaudit.domain.dao.IUserActionDao;
import com.interaudit.domain.model.UserAction;
import com.interaudit.service.exc.BusinessException;
import com.interaudit.service.exc.ExceptionMessage;

public class UserActionDao  implements IUserActionDao {
	
	@PersistenceContext
	private EntityManager em;
	private static final Logger  logger      = Logger.getLogger(UserActionDao.class);

	
	/**
	 * @param id
	 * @return
	 */
	

	
	/**
	 * @return
	 */
	
	@SuppressWarnings("unchecked")
	
	public List<UserAction> getAll(String className,Long idEntity){
		
		try{
			return em.createQuery("select u from UserAction u where u.entityClassName = :className and u.entityId = :idEntity order by u.time asc")
			.setParameter("className", className).setParameter("idEntity", idEntity).getResultList();
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in UserActionDao : getAll ..."));

		}finally{
			em.close();
		}
		
		
	}
	
	
	public UserAction getOne(Long id) {
		
		try{
			return (UserAction)em.find(UserAction.class, id);
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in UserActionDao : getOne ..."));

		}finally{
			em.close();
		}
		
		
	}

	
	public UserAction saveOne(UserAction userAction){
		
		try{			
			em.persist(userAction);
	        return userAction;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in UserActionDao : getOne ..."));
	        
		}finally{
			em.close();
		}	
	}

}
