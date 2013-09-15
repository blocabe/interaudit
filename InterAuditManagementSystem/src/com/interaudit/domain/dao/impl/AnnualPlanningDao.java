package com.interaudit.domain.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import com.interaudit.domain.dao.IAnnualPlanningDao;
import com.interaudit.domain.dao.exc.DaoException;
import com.interaudit.domain.model.data.AnnualPlanning;
import com.interaudit.service.exc.BusinessException;
import com.interaudit.service.exc.ExceptionMessage;

public class AnnualPlanningDao implements IAnnualPlanningDao {
	
	@PersistenceContext
	private EntityManager em;
	private static final Logger  logger      = Logger.getLogger(AnnualPlanningDao.class);
	
	
	public void deleteOne(Long id) {		
		try{			
			AnnualPlanning planning = (AnnualPlanning)em.find(AnnualPlanning.class, id);
			if (planning == null) {
				throw new DaoException(2);
			}
			em.remove(planning);	       
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in AnnualPlanningDao : deleteOne..."));					        
		}finally{
			em.close();
		}
		
		
	}
	
	@SuppressWarnings("unchecked")
	
	public List<AnnualPlanning> getAll() {
		
		try{
			
			return em
			.createQuery("select p from AnnualPlanning p order by f.exercise")
			.getResultList();

		}
		catch(Exception e){
				logger.error(e.getMessage());
				throw new BusinessException(new ExceptionMessage("Failed in AnnualPlanningDao : getAll..."));				

		}finally{
			em.close();
		}
		
		
	}
	
	
	public AnnualPlanning getOneFromExercise(String exercise){
		
		try{
			
			return ( AnnualPlanning )em
			.createQuery("select p from AnnualPlanning p  where p.exercise = :exercise")
			.setParameter("exercise", exercise)
			.getSingleResult();

		}
		catch(javax.persistence.NoResultException e){	
			 return null;
		}
		catch(Exception e){
				logger.error(e.getMessage());
				throw new BusinessException(new ExceptionMessage("Failed in AnnualPlanningDao : getOneFromExercise..."));				

		}finally{
			em.close();
		}
		
		
	}
	
	
	public AnnualPlanning getOne(Long id) {
		
		try{
			
			return (AnnualPlanning)em.find(AnnualPlanning.class, id);

		}
		catch(Exception e){
				logger.error(e.getMessage());
				throw new BusinessException(new ExceptionMessage("Failed in AnnualPlanningDao : getOne..."));				

		}finally{
			em.close();
		}
		
		
	}

	
	public AnnualPlanning saveOne(AnnualPlanning planning) {		
		try{			
			em.persist(planning);	       
	        return planning;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in AnnualPlanningDao : saveOne..."));				
	        
		}finally{
			em.close();
		}

	}

	
	public AnnualPlanning updateOne(AnnualPlanning planning) {		
		try{			
			em.merge(planning);	        
	        return planning;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in AnnualPlanningDao : saveOne..."));			
	        
		}finally{
			em.close();
		}
	}
	
}
