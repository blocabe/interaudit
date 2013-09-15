package com.interaudit.domain.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.interaudit.domain.dao.IWeekAssignmentPerEmployeDao;
import com.interaudit.domain.dao.exc.DaoException;
import com.interaudit.domain.model.Employee;
import com.interaudit.domain.model.Mission;
import com.interaudit.domain.model.data.WeekAssignmentPerEmployee;
import com.interaudit.service.exc.BusinessException;
import com.interaudit.service.exc.ExceptionMessage;

public class WeekAssignmentPerEmployeDao  implements IWeekAssignmentPerEmployeDao {
	
	@PersistenceContext
	private EntityManager em;
	
	
	public void deleteOne(Long id) {
		
		//EntityTransaction tx = em.getTransaction();
		try{
			//tx.begin();
			WeekAssignmentPerEmployee weekAssignmentPerEmployee = (WeekAssignmentPerEmployee)em.find(WeekAssignmentPerEmployee.class, id);
			if (weekAssignmentPerEmployee == null) {
				throw new DaoException(2);
			}
			em.remove(weekAssignmentPerEmployee);
				
	        //tx.commit();
		}
		catch(Exception e){
			throw new BusinessException(new ExceptionMessage("Failed in WeekAssignmentPerEmployeDao : deleteOne ..."));
	        
		}finally{
			em.close();
		}

		
	}
	
	@SuppressWarnings("unchecked")
	
	public List<WeekAssignmentPerEmployee> getAll() {
		try{
			return em
			.createQuery("select w from WeekAssignmentPerEmployee w order by w.id")
			.getResultList();
		}
		catch(Exception e){
			
			throw new BusinessException(new ExceptionMessage("Failed in WeekAssignmentPerEmployeDao : getAll ..."));
		}finally{
			em.close();
		}
		
		
	}
	
	
	
	
	
	public WeekAssignmentPerEmployee getOne(Long id) {
		//EntityTransaction tx = em.getTransaction();
		try{
			//tx.begin();
			WeekAssignmentPerEmployee weekAssignmentPerEmployee = (WeekAssignmentPerEmployee)em.find(WeekAssignmentPerEmployee.class, id);
	        //tx.commit();
	        return weekAssignmentPerEmployee;
		}
		catch(Exception e){
			throw new BusinessException(new ExceptionMessage("Failed in WeekAssignmentPerEmployeDao : getOne ..."));
		}finally{
			em.close();
		}

		
	}

	
	public WeekAssignmentPerEmployee saveOne(WeekAssignmentPerEmployee weekAssignmentPerEmployee) {
		
		//EntityTransaction tx = em.getTransaction();
		try{
			//tx.begin();
			em.persist(weekAssignmentPerEmployee);
			
	       // tx.commit();
	        return weekAssignmentPerEmployee;
		}
		catch(Exception e){
			throw new BusinessException(new ExceptionMessage("Failed in WeekAssignmentPerEmployeDao : saveOne ..."));
	        
		}finally{
			em.close();
		}

		
	}

	
	public WeekAssignmentPerEmployee updateOne(WeekAssignmentPerEmployee weekAssignmentPerEmployee) {		
		try{			
			WeekAssignmentPerEmployee ret = (WeekAssignmentPerEmployee)em.merge(weekAssignmentPerEmployee);
	        return ret;
		}
		catch(Exception e){			
			throw new BusinessException(new ExceptionMessage("Failed in WeekAssignmentPerEmployeDao : updateOne ..."));
	        
		}finally{
			em.close();
		}

		
	}
	
	
	@SuppressWarnings("unchecked")
	
	public List<WeekAssignmentPerEmployee> getAllAssignmentForUserIdInPeriod(Long userId,Long projectId,Date firstDate/*,Date secondDate*/) {
		
		try{
			
			List<WeekAssignmentPerEmployee> result = em.createQuery(
			"SELECT t FROM WeekAssignmentPerEmployee t WHERE t.teamMember.id=:userId AND t.activity.id=:projectId AND ( t.startDate = :d1 )")
					//"SELECT t FROM WeekAssignmentPerEmployee t WHERE t.teamMember.id=:userId AND t.activity.id=:projectId AND ( t.startDate >= :d1 OR t.startDate <= :d2 )")
			.setParameter("userId", userId)
			.setParameter("projectId", projectId)
			.setParameter("d1", firstDate).getResultList();
			//.setParameter("d2", secondDate).list();
			return result;

		}
		catch(Exception e){			
			throw new BusinessException(new ExceptionMessage("Failed in WeekAssignmentPerEmployeDao : getAllAssignmentForUserIdInPeriod ..."));
		}finally{
			em.close();
		}
		
		
	}
	
	
	/**
	 * @param user
	 * @param weekNumber
	 * @param exercise
	 * @return
	 */
	

	@SuppressWarnings("unchecked")
	
	public List<Mission> getAllScheduledProjectForUserInWeek(Employee user,int weekNumber, String exercise ){
		
		try{
			
			List<Mission> result = em.createQuery(
			"SELECT DISTINCT t.activity FROM WeekAssignmentPerEmployee t WHERE t.teamMember.user.id=:userId AND t.activity.exercise=:exercise AND t.week.weekNumber = :weekNumber")
			.setParameter("userId", user.getId())
			.setParameter("exercise", exercise)
			.setParameter("weekNumber", weekNumber)
			.getResultList();
			return result;

		}
		catch(Exception e){
			throw new BusinessException(new ExceptionMessage("Failed in WeekAssignmentPerEmployeDao : getAllScheduledProjectForUserInWeek ..."));
		}finally{
			em.close();
		}
		
		
		
	}
	
	

}
