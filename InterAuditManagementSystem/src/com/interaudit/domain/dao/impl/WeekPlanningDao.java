package com.interaudit.domain.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.interaudit.domain.dao.IWeekPlanningDao;
import com.interaudit.domain.dao.exc.DaoException;
import com.interaudit.domain.model.data.WeekPlanning;
import com.interaudit.service.exc.BusinessException;
import com.interaudit.service.exc.ExceptionMessage;

public class WeekPlanningDao implements IWeekPlanningDao {
	
	@PersistenceContext
	private EntityManager em;
	
	
	public void deleteOne(Long id) {
		
		//EntityTransaction tx = em.getTransaction();
		try{
			//tx.begin();
			
			WeekPlanning weekPlanning = (WeekPlanning)em.find(WeekPlanning.class, id);
			if (weekPlanning == null) {
				throw new DaoException(2);
			}
			em.remove(weekPlanning);
				
	        //tx.commit();
		}
		catch(Exception e){
			throw new BusinessException(new ExceptionMessage("Failed in WeekPlanningDao : deleteOne ..."));
	        
		}finally{
			em.close();
		}
		
		
	}
	
	@SuppressWarnings("unchecked")
	
	public List<WeekPlanning> getAll() {
		try{
			return em
			.createQuery("select w from WeekPlanning w order by w.weekNumber")
			.getResultList();
		}
		catch(Exception e){
			
			throw new BusinessException(new ExceptionMessage("Failed in WeekPlanningDao : getAll ..."));
	        
		}finally{
			em.close();
		}
		
		
	}
	
	@SuppressWarnings("unchecked")
	
	public List<WeekPlanning> getAllForOnePlanningId(Long id){
		
		try{
			return em
			.createQuery("select w from WeekPlanning w order by w.weekNumber where w.planning.id = :planningId")
			.setParameter("planningId", id)
			.getResultList();
		}
		catch(Exception e){
			
			throw new BusinessException(new ExceptionMessage("Failed in WeekPlanningDao : getAllForOnePlanningId ..."));
		}finally{
			em.close();
		}
		
		
	}
	
	
	public WeekPlanning getOneFromWeekNumber(String exercise, int weekNumber){
		
		try{
			return ( WeekPlanning )em
			.createQuery("select w from WeekPlanning w where w.planning.exercise = :exercise and w.weekNumber = :weekNumber")
			.setParameter("exercise", exercise).setParameter("weekNumber", weekNumber)
			.getSingleResult();
		}
		catch(Exception e){
			
			throw new BusinessException(new ExceptionMessage("Failed in WeekPlanningDao : getOneFromWeekNumber ..."));
	        
		}finally{
			em.close();
		}
		
		
	}
	
	
	public WeekPlanning getOne(Long id) {		
		try{			
			WeekPlanning weekPlanning = (WeekPlanning)em.find(WeekPlanning.class, id);	      
	        return weekPlanning;
		}
		catch(Exception e){			
			throw new BusinessException(new ExceptionMessage("Failed in WeekPlanningDao : getOne ..."));
		}finally{
			em.close();
		}
		
	}

	
	public WeekPlanning saveOne(WeekPlanning weekPlanning) {
		
		try{			
			em.persist(weekPlanning);			
	        return weekPlanning;
		}
		catch(Exception e){			
			throw new BusinessException(new ExceptionMessage("Failed in WeekPlanningDao : saveOne ..."));
		}finally{
			em.close();
		}
		
	}

	
	public WeekPlanning updateOne(WeekPlanning weekPlanning) {
		
		try{			
			WeekPlanning ret = (WeekPlanning)em.merge(weekPlanning);	       
	        return ret;
		}
		catch(Exception e){			
			throw new BusinessException(new ExceptionMessage("Failed in WeekPlanningDao : updateOne ..."));
	        
		}finally{
			em.close();
		}
		
		
	}
	
	

}
