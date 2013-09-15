package com.interaudit.domain.dao.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.interaudit.domain.dao.IEventDao;
import com.interaudit.domain.dao.exc.DaoException;
import com.interaudit.domain.model.Event;
import com.interaudit.service.exc.BusinessException;
import com.interaudit.service.exc.ExceptionMessage;
import com.interaudit.service.param.SearchEventParam;

/**
 * @author bernard
 *
 */
public class EventDao implements IEventDao {

	@PersistenceContext
	private EntityManager em;
	private static final Logger  logger      = Logger.getLogger(EventDao.class);
	
	
	
    public void deleteOne(Long id) {		
		try{			
			Event activity =em.find(Event.class, id);
	        if (activity == null) {
	            throw new DaoException(2);
	        }
	        em.remove(activity);    			
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in EventDao :  getMessagesNotSent..."));
	        
		}finally{
			em.close();
		}

		
		    
    }
	
	@SuppressWarnings("unchecked")
	
	public List<Event> getAllEventsForPeriod( Date firstDate, Date secondDate,Long employeeId  ){
    	
    	try{
    		
    		Calendar calendar = Calendar.getInstance();
    		calendar.setTime(firstDate);    		
    		int startDateNumber = calendar.get(Calendar.DAY_OF_YEAR);
    		int year = calendar.get(Calendar.YEAR);
    		
    		calendar.setTime(secondDate);    		
    		int endDateNumber = calendar.get(Calendar.DAY_OF_YEAR);
    		
    		List<Event> result = em.createQuery(
			"select e  from Event e join e.employee u WHERE u.id=:userId and e.year=:year and e.day >=:d1 AND e.day <= :d2 ) ")
			.setParameter("userId", employeeId).setParameter("year", year).setParameter("d1", startDateNumber).setParameter("d2", endDateNumber).getResultList();
			
    		return result.size() > 0 ? result : null;

		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in EventDao :  getAllEventsForPeriod..."));

		}finally{
			em.close();
		}
    	
		
		
    }

	
	 /**
     * 
     * @return AnnualBudget objects list representing all AnnualBudget known by IAMS
     *         application.
     */
	
	public  List<Event> searchEvents(  SearchEventParam param){
		return findActivities( param);
	}
	
	@SuppressWarnings("unchecked")
	public Event findEvent( Long employeId, int year,int month,int day,int startHour, int endHour){
		try{		
	        //StringBuilder hql = new StringBuilder("select e  from Event e join e.activity.mission m join e.employee p where p.id = :employeId and e.year = :year and e.month =:month and e.day =:day and e.startHour = :startHour and e.endHour =:endHour ");	        
			StringBuilder hql = new StringBuilder("select e  from Event e join e.employee p where p.id = :employeId and e.year = :year and e.month =:month and e.day =:day and e.startHour = :startHour and e.endHour =:endHour ");
	        Query q = em.createQuery(hql.toString()).setParameter("employeId", employeId).setParameter("year", year).setParameter("day", day).setParameter("month", month).setParameter("startHour", startHour).setParameter("endHour", endHour);	        
	        List resultList = q.getResultList();	        
	        if (resultList.size() > 0) {
	            return (Event) resultList.get(0);
	        }else{
	        	return null;
	        }
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in EventDao :  findEvent..."));

		}finally{
			em.close();
		}
	}
	
	public int getNextAvailableStartHour(Long employeId, int year, int month, int dayOfYear){
		try{		   		 	
	        //StringBuilder hql = new StringBuilder("select max(e.endHour)  from Event e join e.activity.mission m join e.employee p where p.id = :employeId and e.year = :year and e.month =:month and e.day =:day ");	  
			StringBuilder hql = new StringBuilder("select max(e.endHour)  from Event e join e.employee p where p.id = :employeId and e.year = :year and e.month =:month and e.day =:day ");
	        Number result = (Number)  em.createQuery(hql.toString()).setParameter("employeId", employeId).setParameter("year", year).setParameter("day", dayOfYear).setParameter("month", month).getSingleResult();	        	       
	        if (result != null) {
	            return result.intValue();
	        }else{
	        	return 0;
	        }
		}
		catch(javax.persistence.NoResultException e){	
			return 0;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in EventDao :  getNextAvailableStartHour..."));

		}finally{
			em.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Event> findActivities(SearchEventParam param) {
		
		try{
			
		 	Map<String, Object> parameters = new HashMap<String, Object>();
		 	
	        //StringBuilder hql = new StringBuilder("select e  from Event e left join e.activity.mission m join e.employee p ");
		 	StringBuilder hql = new StringBuilder("select e  from Event e left join e.employee p ");
	        StringBuilder whereClause = new StringBuilder("");
	        
	        //Rechercher les années
	        if (param.getYear()!= 0) {
	            parameters.put("year", param.getYear());
	            whereClause.append("( upper(e.year) = upper(:year))");
	        }
	        
	      //Rechercher le mois
	        if (param.getMonth()!= -1) {
	            if (whereClause.length() > 0) {
	                whereClause.append(" AND ");
	            }
	            parameters.put("month", param.getMonth());
	            whereClause.append("( upper(e.month) = upper(:month))");
	        }
	        
	        //Rechercher le jour
	        if (param.getDay()!= 0) {
	            if (whereClause.length() > 0) {
	                whereClause.append(" AND ");
	            }
	            parameters.put("day", param.getDay());
	            whereClause.append("( upper(e.day) = upper(:day))");
	        }
	        
	      //Rechercher l'employee
	        if (param.getEmployee() != null) {
	            if (whereClause.length() > 0) {
	                whereClause.append(" AND ");
	            }
	            parameters.put("employeeId", param.getEmployee());
	            whereClause.append("( p.id = :employeeId )");
	        }
	        
	      //Rechercher la mission
	        /*
	        if (param.getMission() != null) {
	            if (whereClause.length() > 0) {
	                whereClause.append(" AND ");
	            }
	            parameters.put("missionId", param.getMission());
	            whereClause.append("( m.id = :missionId )");
	        }
	        */
	      
	        
	        if (whereClause.length() > 0) {
	            hql.append(" WHERE ").append(whereClause);
	        }
	        hql.append(" ORDER BY e.day asc ");
	        
	        Query q = em.createQuery(hql.toString());
	        for (Map.Entry<String, Object> me : parameters.entrySet()) {
	            q.setParameter(me.getKey(), me.getValue());
	           // log.debug("***************** parameters " + me.getKey() + " = " + me.getValue());
	        }
	    
	        return q.getResultList();

		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in EventDao :  findActivities..."));

		}finally{
			em.close();
		}
		
	    }
	
	
	
	
	
	
	 @SuppressWarnings("unchecked")
	    
		public Event getOneEventDetached(Long id){
	    	try{
		    	
	    		//String queryString = "select e  from Event e left join e.activity.mission m join e.employee p  where e.id = :id";
	    		String queryString = "select e  from Event e left join e.employee p  where e.id = :id";
		        List resultList = em.createQuery(queryString)
		        .setParameter("id",
		                id).getResultList();
		        if (resultList.size() > 0) {
		            return (Event) resultList.get(0);
		        }else{
		        	return null;
		        }
	        
	    	}catch(Exception e){
	        	
	    		logger.error(e.getMessage());
	    		throw new BusinessException(new ExceptionMessage("Failed in EventDao :  getOneEventDetached..."));
	        }
	    	finally{
	    		em.close();
	    	}
	    }
	 
	 
	


    
    
    public Event getOne(Long id) {
    	try{
    		return em.find(Event.class, id);
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in EventDao :  getOne..."));

		}finally{
			em.close();
		}
        
    }

    
    

    
    public Event saveOne(Event activity) {    	
		try{			
			em.persist(activity);	      
	        return activity;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in EventDao :  saveOne..."));
	        
		}finally{
			em.close();
		}

    	
        
    }

    
    public Event updateOne(Event activity) {
    	
		try{			
			Calendar calendar = Calendar.getInstance();
		    calendar.set(Calendar.YEAR, activity.getYear());
		    calendar.set(Calendar.MONTH, activity.getMonth());
		    calendar.set(Calendar.DAY_OF_YEAR, activity.getDay());
		    activity.setDateOfEvent(calendar.getTime());		
		    Event ret =  em.merge(activity);	        
	        return ret;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in EventDao :  updateOne..."));
	        
		}finally{
			em.close();
		}

		
    	
        
    }
    
    
    public int markHoursAsValid(List<Long> resultList){
 
    	try{
    		Query q = em.createQuery("update Event e set e.valid = true where e.id in ( :identifiers ) ").setParameter("identifiers", resultList);
			int result = q.executeUpdate();
			return result;
			
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in EventDao :  markHoursAsValid..."));
	        
		}finally{
			em.close();
		}
    }
    
  
    
    public int getCountUnvalidateHoursForMission(Long missionId) {
    	
    	try{    		
    		Number result = (Number) em
            .createQuery(
                    "select count(*) from from Event e where e.activity.mission.id = :missionId ")
            .setParameter("missionId", missionId).getSingleResult();
		    if ( result == null) {
		        return 0;
		    } else {
		        return result.intValue();
		    }
		}
    	catch(javax.persistence.NoResultException e){	
    		return 0; 
    	}    	
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in EventDao :  getCountUnvalidateHoursForMission..."));

		}finally{
			em.close();
		}
		
        
    }
  
}
