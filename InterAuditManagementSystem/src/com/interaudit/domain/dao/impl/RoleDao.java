package com.interaudit.domain.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import com.interaudit.domain.dao.IRoleDao;
import com.interaudit.domain.dao.exc.DaoException;
import com.interaudit.domain.model.Position;
import com.interaudit.service.exc.BusinessException;
import com.interaudit.service.exc.ExceptionMessage;

public class RoleDao  implements IRoleDao {
	
	@PersistenceContext
	private EntityManager em;
	private static final Logger  logger      = Logger.getLogger(RoleDao.class);
	
	public void deleteOne(Long id) {
		
		try{			
			Position userRole = (Position)em.find(Position.class, id);
			if (userRole == null) {
				throw new DaoException(2);
			}
			em.remove(userRole);	       
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in RoleDao :  deleteOne..."));
	        
		}finally{
			em.close();
		}
		
		
	}
	
	@SuppressWarnings("unchecked")
	
	public List<Position> getAll() {
		
		try{
			return em.createQuery("select u from Position u order by u.name").getResultList();
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in RoleDao :  getAll..."));

		}finally{
			em.close();
		}
		
		
	}
	
	@SuppressWarnings("unchecked")
	
	public List<Position> getAllLike(String model) {
		
		try{
			return em.createQuery("select u from Position u where u.name like :model")
			.setParameter("model", model).getResultList();
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in RoleDao :  getAllLike..."));

		}finally{
			em.close();
		}
		
		
	}
	
	
	public Position getOne(Long id) {
		
		try{
			return (Position)em.find(Position.class, id);
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in RoleDao :  getOne..."));

		}finally{
			em.close();
		}
		
		
	}

	
	public Position saveOne(Position userRole) {
		
		try{			
			em.persist(userRole);	        
	        return userRole;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in RoleDao :  saveOne..."));
	        
		}finally{
			em.close();
		}
		
		
		
	}

	
	public Position updateOne(Position userRole) {
		
		try{			
			Position ret = (Position)em.merge(userRole);	       
	        return ret;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in RoleDao :  updateOne..."));
	        
		}finally{
			em.close();
		}
		
		
	}
	
	@SuppressWarnings("unchecked")
	
    public Position getRoleByName(String roleName) {
		
		try{
			
			List<Position> result = em.createQuery("SELECT x FROM Position x WHERE x.name = :roleName ")
            .setParameter("roleName", roleName)
            	.getResultList();
			return result.size() > 0 ? result.iterator().next() : null;

		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in RoleDao :  getRoleByName..."));

		}finally{
			em.close();
		}
		
        
    }
	
	@SuppressWarnings("unchecked")
	
    public Position getRoleByCode(String roleCode) {
		
		try{
			
			 List<Position> result = em.createQuery("SELECT x FROM Position x WHERE x.name = :roleCode ")
             .setParameter("roleCode", roleCode)
             	.getResultList();
			 return result.size() > 0 ? result.iterator().next() : null;

		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in RoleDao :  getRoleByCode..."));

		}finally{
			em.close();
		}
		
    }
	
	

}
