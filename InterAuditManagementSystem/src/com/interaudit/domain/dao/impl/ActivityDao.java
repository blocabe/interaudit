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

import com.interaudit.domain.dao.IActivityDao;
import com.interaudit.domain.dao.exc.DaoException;
import com.interaudit.domain.model.Activity;
import com.interaudit.domain.model.Comment;
import com.interaudit.domain.model.Document;
import com.interaudit.domain.model.EmailData;
import com.interaudit.domain.model.Employee;
import com.interaudit.domain.model.Message;
import com.interaudit.domain.model.Mission;
import com.interaudit.domain.model.Task;
import com.interaudit.domain.model.Timesheet;
import com.interaudit.domain.model.data.ActivityData;
import com.interaudit.service.exc.BusinessException;
import com.interaudit.service.exc.ExceptionMessage;
import com.interaudit.service.param.SearchActivityParam;
import com.interaudit.util.Constants;

/**
 * @author bernard
 *
 */
public class ActivityDao implements IActivityDao {

	@PersistenceContext
	private EntityManager em;
	private static final Logger  logger      = Logger.getLogger(ActivityDao.class);
	

	public double calculateNumberOfHoursSpent(Long userId,Long activityId, int dateOfWorkDate){
		
		try{
			//String query = "select sum(w.spentHours) from WorkDoneDayly w join w.intervention i where i.employee.id = :userId and i.id = :activityId and w.dayOfYearNumber = :dayOfYearNumber";
			String query = null;
			Number result = (Number) em
	        .createQuery(query)
	        .setParameter("userId",userId)
	        .setParameter("activityId",activityId)
	        .setParameter("dayOfYearNumber",dateOfWorkDate).getSingleResult();
			if ( result == null) {
			    return 0d;
			} else {
			    return result.doubleValue();
			}

		}
		catch(javax.persistence.NoResultException e){	
			return 0.0; 
		}
		catch(Exception e){
				logger.error(e.getMessage());
				throw new BusinessException(new ExceptionMessage("Failed in ActivityDao : calculateNumberOfHoursSpent..."));		
		}finally{
			em.close();
		}
		
		
	}
	
	public double calculateTotalNumberOfHoursSpentOnActivity(Long userId,Long activityId){
		
		try{
			//String query = "select sum(w.spentHours) from WorkDoneDayly w join w.intervention i where i.employee.id = :userId and i.id = :activityId";
			String query = null;
			Number result = (Number) em
	        .createQuery(query)
	        .setParameter("userId",userId)
	        .setParameter("activityId",activityId).getSingleResult();
			if ( result == null) {
			    return 0d;
			} else {
			    return result.doubleValue();
			}

		}
		catch(javax.persistence.NoResultException e){	
			return 0.0; 
		}
		catch(Exception e){
				logger.error(e.getMessage());
				throw new BusinessException(new ExceptionMessage("Failed in ActivityDao : calculateTotalNumberOfHoursSpentOnActivity..."));				

		}finally{
			em.close();
		}
		
		
	}
	

    public void deleteOneWorkDoneDayly(Long id) {
		/*
		//EntityTransaction tx = em.getTransaction();
		try{
			//tx.begin();
			
			WorkDoneDayly workDoneDayly =em.find(WorkDoneDayly.class, id);
	        if (workDoneDayly == null) {
	            throw new DaoException(2);
	        }
	        em.remove(workDoneDayly);   
				
	        //tx.commit();
		}
		catch(Exception e){
			//tx.rollback();
			return ;
	        
		}finally{
			em.close();
		}

		*/
		     
    }
	

    public void deleteOne(Long id) {
		
		//EntityTransaction tx = em.getTransaction();
		try{
			//tx.begin();
			
			Activity activity =em.find(Activity.class, id);
	        if (activity == null) {
	            throw new DaoException(2);
	        }
	        em.remove(activity);    
				
	        //tx.commit();
		}
		catch(Exception e){
			//tx.rollback();
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in ActivityDao : deleteOne..."));								
	        
		}finally{
			em.close();
		}

		
		    
    }

	
	 /**
     * 
     * @return AnnualBudget objects list representing all AnnualBudget known by IAMS
     *         application.
     */

	public List<ActivityData> searchActivities(  SearchActivityParam param){
		return findActivities( param);
	}
	
	@SuppressWarnings("unchecked")
	public List<ActivityData> findActivities(SearchActivityParam param) {
		
		try{
			
			
			

			
		 	Map<String, Object> parameters = new HashMap<String, Object>();
		 	
	       StringBuilder hql = new StringBuilder("select new com.interaudit.domain.model.data.ActivityData(a.id, c.companyName,m.reference, a.toDo, a.startDate,a.endDate,a.updateDate, a.status, c.customerManager.code,e.lastName, m.exercise, a.comments)  from Activity a join a.mission m join a.mission.annualBudget.contract.customer c join a.employee e  ");
		 	
		 	
	        StringBuilder whereClause = new StringBuilder("");
	        
	        //Rechercher les années
	        if (param.getYear()!= null) {
	            parameters.put("year", param.getYear());
	            whereClause.append("( upper(m.exercise) = upper(:year))");
	        }
	        
	        //Rechercher le status
	        /*
	        if ((param.getListOfStatus() != null) && (param.getListOfStatus().isEmpty() == false)) {
	            if (whereClause.length() > 0) {
	                whereClause.append(" AND ");
	            }
	            parameters.put("status", param.getListOfStatus());
	            whereClause.append("( a.status in (:status) )");
	        }
	        */
	        
	      //Rechercher le customer
	        if (param.getEmployee() != null) {
	            if (whereClause.length() > 0) {
	                whereClause.append(" AND ");
	            }
	            parameters.put("employeeId", param.getEmployee());
	            whereClause.append("( e.id = :employeeId )");
	        }
	        
	      //Rechercher le manager
	        /*
	        if (param.getManager() != null) {
	            if (whereClause.length() > 0) {
	                whereClause.append(" AND ");
	            }
	            parameters.put("manager", "%" + param.getManager() +"%");
	            whereClause.append("c.customerManager.lastName like :manager");
	        }
	        */
	        
	        //Rechercher le customer
	        /*
	        if (param.getCustomer() != null) {
	            if (whereClause.length() > 0) {
	                whereClause.append(" AND ");
	            }
	            parameters.put("customerId", param.getCustomer());
	            whereClause.append("(c.id = :customerId )");
	        }
	        */
	        
	        if (whereClause.length() > 0) {
	            hql.append(" WHERE ").append(whereClause);
	        }
	        hql.append(" ORDER BY c.companyName asc ");
	        
	        Query q = em.createQuery(hql.toString());
	        for (Map.Entry<String, Object> me : parameters.entrySet()) {
	            q.setParameter(me.getKey(), me.getValue());
	           // log.debug("***************** parameters " + me.getKey() + " = " + me.getValue());
	        }
	    
	        return q.getResultList();

		}
		catch(Exception e){
				logger.error(e.getMessage());
				throw new BusinessException(new ExceptionMessage("Failed in ActivityDao : findActivities..."));						

		}finally{
			em.close();
		}
		
	    }
	
	
	@SuppressWarnings("unchecked")
	public ActivityData findActivityDataForIdenifier(Long id) {
		
	        try{
	        	//String queryString = "select new com.interaudit.domain.model.data.ActivityData(a.id,a.activityReference, c.companyName,m.reference, a.activityDescription,a.totalEstimatedHours, a.totalWorkedHours, a.startDate,a.endDate,a.updateDate, a.status, t.name, c.customerManager.lastName,e.lastName, m.exercise, a.priority)  from Intervention a join a.mission m join a.mission.annualBudget.contract.customer c join a.task t join a.employee e   where upper(a.id) = upper(:id)";
	        	String queryString = null;
	            List resultList = em.createQuery(queryString)
	            .setParameter("id",
	                    id).getResultList();
	            if (resultList.size() > 0) {
	                return (ActivityData) resultList.get(0);
	            }
	            return null;
	        	}catch(Exception e){
					logger.error(e.getMessage());
					throw new BusinessException(new ExceptionMessage("Failed in ActivityDao : findActivityDataForIdenifier..."));						

			}finally{
	        		em.close();
	        	}
	        
	}
	
	
	
	 @SuppressWarnings("unchecked")
		public Activity getOneInterventionDetached(Long id){
	    	try{
		    	
	    		String queryString = "select i from Activity i join fetch i.employee e  where upper(i.id) = upper(:id)";
		        List resultList = em.createQuery(queryString)
		        .setParameter("id",
		                id).getResultList();
		        if (resultList.size() > 0) {
		            return (Activity) resultList.get(0);
		        }else{
		        	return null;
		        }
	        
	    	}catch(Exception e){
	        	
	    		logger.error(e.getMessage());
	    		throw new BusinessException(new ExceptionMessage("Failed in ActivityDao : getOneInterventionDetached..."));				
	        }
	    	finally{
	    		em.close();
	    	}
	    }
	 
	 
	 /**
	     * @param id
	     * @return the messages from the target mission
	     */
	 @SuppressWarnings("unchecked")
	 public List<Message> getMessagesFromParentMission(Long id) { 
		 String queryString = "select m.messages from Mission m join m.interventions a  left join  m.messages where upper(a.id)in ( upper(:id) )";
		 try{   	
			
		        List resultList = em.createQuery(queryString)
		        .setParameter("id",
		                id).getResultList();
		        return resultList;
	        
	    	}catch(Exception e){
	        	
	    		logger.error(e.getMessage());
	    		throw new BusinessException(new ExceptionMessage("Failed in ActivityDao : getMessagesFromParentMission..."));	
	        }
	    	finally{
	    		em.close();
	    	}
	    
	    }
	 
	 
	 /**
	     * @param id
	     * @return the messages from the target mission
	     */
	 @SuppressWarnings("unchecked")
	 public List<Message> getMessagesNotSent(int count) { 
		 String queryString = "select m from Message m  where m.sentDate IS NULL order by m.createDate)";
		 try{   				
		        List resultList = em.createQuery(queryString).setMaxResults(count).getResultList();
		        return resultList;	        
	    	}catch(Exception e){	        	
	    		logger.error(e.getMessage());
	    		throw new BusinessException(new ExceptionMessage("Failed in ActivityDao : getMessagesNotSent..."));			
	        }
	    	finally{
	    		em.close();
	    	}
	    
	    }
	 
	
	 public List<Timesheet> searchTimesheetToRemind(){
		try{				
				Calendar c = Calendar.getInstance();
				int weekNumber= c.get(Calendar.WEEK_OF_YEAR);
				@SuppressWarnings("unchecked")
				List<Timesheet> result = em.createQuery(
						"SELECT t FROM Timesheet t join t.user u WHERE t.status = :status AND t.weekNumber < :weekNumber AND u.userActive = :userActive")
				.setParameter("weekNumber", weekNumber)
				.setParameter("status", Constants.TIMESHEET_STATUS_STRING_DRAFT)
				.setParameter("userActive", Boolean.TRUE)
				.getResultList();
				return result;
			}
			catch(Exception e){
				logger.error(e.getMessage());
				throw new BusinessException(new ExceptionMessage("Failed in ActivityDao : searchTimesheetToRemind..."));						        
			}finally{
				em.close();
			}
		}
	 
	 
	 public List<Employee> searchEmployeeToRemindTimesheet(){
			try{				
					Calendar c = Calendar.getInstance();
					int weekNumber= c.get(Calendar.WEEK_OF_YEAR);
					@SuppressWarnings("unchecked")
					List<Employee> result = em.createQuery(
							"SELECT distinct( t.user) FROM Timesheet t join t.user u WHERE t.status = :status AND t.weekNumber < :weekNumber AND u.userActive = :userActive")
					.setParameter("weekNumber", weekNumber)
					.setParameter("status", Constants.TIMESHEET_STATUS_STRING_DRAFT)
					.setParameter("userActive", Boolean.TRUE)
					.getResultList();
					return result;
				}
				catch(Exception e){
					logger.error(e.getMessage());
					throw new BusinessException(new ExceptionMessage("Failed in ActivityDao : searchTimesheetToRemind..."));						        
				}finally{
					em.close();
				}
			}
	 
	 

	  public List<EmailData> getEmailDatasNotSent(int count){ 
			 String queryString = "select m from EmailData m  where m.sentDate IS NULL order by m.created)";
			 try{   	
				
			        List resultList = em.createQuery(queryString).setMaxResults(count).getResultList();
			        return resultList;
		        
		    	}catch(Exception e){		        	
		    		logger.error(e.getMessage());
		    		throw new BusinessException(new ExceptionMessage("Failed in ActivityDao : getEmailDatasNotSent..."));				
		        }
		    	finally{
		    		em.close();
		    	}
		    
		    }
	  
	
	  public List<Comment> getCommentsNotSent(int count){ 
			 String queryString = "select m from Comment m  where m.sent IS NULL and m.timesheet.status = 'DRAFT' order by m.created)";
			 try{   	
				
			        List resultList = em.createQuery(queryString).setMaxResults(count).getResultList();
			        return resultList;
		        
		    	}catch(Exception e){		        	
		    		logger.error(e.getMessage());
		    		throw new BusinessException(new ExceptionMessage("Failed in ActivityDao : getCommentsNotSent..."));				
		        }
		    	finally{
		    		em.close();
		    	}
		    
		    }
	    

		/**
		 * @param id
		 * @return the documents from the target mission
		 */
	 @SuppressWarnings("unchecked")
	 public List<Document> getDocumentsFromParentMission(Long id){
		 String queryString = "select m.documents from Mission m join m.interventions a  where upper(a.id) in ( upper(:id) )";
		 try{   	
				
			 List resultList = em.createQuery(queryString)
		        .setParameter("id",
		                id).getResultList();
		        return resultList;
		        
		    	}catch(Exception e){		        	
		    		logger.error(e.getMessage());
		    		throw new BusinessException(new ExceptionMessage("Failed in ActivityDao : getDocumentsFromParentMission..."));				
		        }
		    	finally{
		    		em.close();
		    	}
		}
	 
	 @SuppressWarnings("unchecked")
	 public List<Employee> getTeamMembersForMission(Long id){
		 String queryString = "select m from Intervention a join a.mission  m  where upper(a.id) = upper(:id)";
			try{   	
				
			        List resultList = em.createQuery(queryString)
			        .setParameter("id",
			                id).getResultList();
			        if (resultList.size() > 0) {
			            Mission m = (Mission) resultList.get(0);
			            return m.getTeamMembers();
			        }else{
			        	return null;
			        }
		        
		    	}catch(Exception e){
		        	
		    		logger.error(e.getMessage());
		    		throw new BusinessException(new ExceptionMessage("Failed in ActivityDao : getTeamMembersForMission..."));				
		        }
		    	finally{
		    		em.close();
		    	} 
	 }


    
    public Activity getOne(Long id) {
    	try{
    		return em.find(Activity.class, id);
		}
		catch(Exception e){
				logger.error(e.getMessage());
				throw new BusinessException(new ExceptionMessage("Failed in ActivityDao : getOne..."));				

		}finally{
			em.close();
		}
        
    }
    
    
    public Activity getActivity(Employee employee,Mission mission,Task task){
    	
    	try{
	    	
    		String queryString = "select i from Activity i join  i.employee e join  i.mission m  join  i.task t where upper(e.id) = upper(:employeeId) and upper(m.id) = upper(:missionId) and upper(t.id) = upper(:taskId)";
	        List resultList = em.createQuery(queryString)
	        .setParameter("employeeId",employee.getId()).setParameter("missionId",mission.getId()).setParameter("taskId",task.getId()).getResultList();
	        if (resultList.size() > 0) {
	            return (Activity) resultList.get(0);
	        }else{
	        	return null;
	        }
    	}catch(Exception e){
    		logger.error(e.getMessage());
    		throw new BusinessException(new ExceptionMessage("Failed in ActivityDao : getActivity..."));						
        }
    	finally{
    		em.close();
    	}
    }

    
    

    
    public Activity saveOne(Activity activity) {
    	
    	//EntityTransaction tx = em.getTransaction();
		try{
			//tx.begin();
			em.persist(activity);
	       // tx.commit();
	        return activity;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in ActivityDao : saveOne..."));				
	        
		}finally{
			em.close();
		}

    	
        
    }

    
    public Activity updateOne(Activity activity) {
    	
    	//EntityTransaction tx = em.getTransaction();
		try{
			//tx.begin();
			Activity ret =  em.merge(activity);
	        //tx.commit();
	        return ret;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in ActivityDao : updateOne(Activity activity)..."));				
	        
		}finally{
			em.close();
		}       
    }
    
    
    public Message updateOne(Message message){
		try{			
			Message ret =  em.merge(message);	       
	        return ret;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in ActivityDao :  updateOne(Message message)..."));				
	        
		}finally{
			em.close();
		}       
    }
    
    
    public EmailData updateOne(EmailData message){    	
		try{			
			EmailData ret =  em.merge(message);	        
	        return ret;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in ActivityDao :  updateOne(EmailData message)..."));				
	        
		}finally{
			em.close();
		}       
    }
    
    public Comment updateOne(Comment message){    	
		try{			
			Comment ret =  em.merge(message);	        
	        return ret;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in ActivityDao :  updateOne(Comment message)..."));				
	        
		}finally{
			em.close();
		}       
    }
    
    
    @SuppressWarnings("unchecked")
	public List<Activity> getAllScheduledActivitiesForUserInPeriod(Employee user,Date firstDate,Date secondDate ){
    	
    	try{
    		
    		List<Activity> result = em.createQuery(
			"SELECT a FROM Activity a join a.employee e WHERE e.id=:userId AND (( a.startDate BETWEEN :d1 AND :d2) OR ( a.endDate BETWEEN :d1 AND :d2))")
	.setParameter("userId", user.getId()).setParameter("d1", firstDate).setParameter("d2", secondDate).getResultList();
	
	/*
	List<Activity> result = em.createQuery(
	"SELECT a FROM Activity a join a.employee e WHERE e.id=:userId AND a.startDate >=:d1 AND  a.endDate <=:d2")
	.setParameter("userId", user.getId()).setParameter("d1", firstDate).setParameter("d2", secondDate).getResultList();
	*/
	return result.size() > 0 ? result : null;

		}
		catch(Exception e){
				logger.error(e.getMessage());
				throw new BusinessException(new ExceptionMessage("Failed in ActivityDao :  getAllScheduledActivitiesForUserInPeriod..."));				

		}finally{
			em.close();
		}
    	
		
		
    }
    
    
    public int getNextInterventionSequence(Long idMission) {
    	
    	try{
    		
    		 Number result = (Number) em
             .createQuery(
                     "select count(*) from Activity i where i.mission.id = :idMission")
             .setParameter("idMission", idMission).getSingleResult();
		     if ( result == null) {
		         return 1;
		     } else {
		         return result.intValue() + 1;
		     }

		}
    	catch(javax.persistence.NoResultException e){	
			return 1; 
		}
		catch(Exception e){
				logger.error(e.getMessage());
				throw new BusinessException(new ExceptionMessage("Failed in ActivityDao :  getNextInterventionSequence..."));				

		}finally{
			em.close();
		}
		
       
    }
   

    
}
