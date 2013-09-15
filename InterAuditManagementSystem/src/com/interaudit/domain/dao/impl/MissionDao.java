package com.interaudit.domain.dao.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.interaudit.domain.dao.IMissionDao;
import com.interaudit.domain.dao.ITimesheetDao;
import com.interaudit.domain.dao.exc.DaoException;
import com.interaudit.domain.model.Activity;
import com.interaudit.domain.model.Alerte;
import com.interaudit.domain.model.AnnualBudget;
import com.interaudit.domain.model.Cost;
import com.interaudit.domain.model.Employee;
import com.interaudit.domain.model.Event;
import com.interaudit.domain.model.EventPlanning;
import com.interaudit.domain.model.ItemEventPlanning;
import com.interaudit.domain.model.Message;
import com.interaudit.domain.model.Mission;
import com.interaudit.domain.model.MissionMember;
import com.interaudit.domain.model.MissionMemberId;
import com.interaudit.domain.model.PlanningAnnuel;
import com.interaudit.domain.model.Position;
import com.interaudit.domain.model.Task;
import com.interaudit.domain.model.Timesheet;
import com.interaudit.domain.model.TimesheetCell;
import com.interaudit.domain.model.TimesheetRow;
import com.interaudit.domain.model.data.EventPlanningData;
import com.interaudit.domain.model.data.MessageData;
import com.interaudit.domain.model.data.MissionBudgetData;
import com.interaudit.domain.model.data.MissionData;
import com.interaudit.domain.model.data.MissionHeurePresteeData;
import com.interaudit.domain.model.data.Option;
import com.interaudit.domain.model.data.ProfitabilityPerCustomerData;
import com.interaudit.domain.model.data.TimesheetDataForPlanning;
import com.interaudit.domain.model.data.TimesheetWeekReport;
import com.interaudit.service.exc.BusinessException;
import com.interaudit.service.exc.ExceptionMessage;
import com.interaudit.service.param.SearchMissionParam;
import com.interaudit.service.param.SearchProfitabilityPerCustomerParam;
import com.interaudit.service.view.AgendaPlanningView;
import com.interaudit.service.view.AnnualPlanningView;
import com.interaudit.service.view.AnnualTimesheetReportView;
import com.interaudit.util.Constants;
import com.interaudit.util.DateUtils;

/**
 * @author bernard
 *
 */
public class MissionDao implements IMissionDao {

	@PersistenceContext
	private EntityManager em;
	private static final Logger  logger      = Logger.getLogger(MissionDao.class);
	private ITimesheetDao timesheetDao;
	
	
public List<ProfitabilityPerCustomerData> calculateProfitabilityPerCustomer(SearchProfitabilityPerCustomerParam param,boolean usePagination,int firstPos,int LINEPERPAGE){
		
		try{
			Map<String, Object> parameters = new HashMap<String, Object>();	       
			//StringBuilder hql = new StringBuilder("select m.annualBudget.contract.customer.companyName , m.annualBudget.exercise.year, m.annualBudget.associeSignataire.code,m.annualBudget.budgetManager.code,m.id,m.status,m.exercise from Mission m ");
			StringBuilder hql = new StringBuilder("select m.id from Mission m ");
	        StringBuilder whereClause = new StringBuilder("");
	        
	        //Rechercher les années
	        if (param.getYears()!= null) {
	           // parameters.put("years", param.getYearsAsString());
	            //whereClause.append("(m.exercise in (:years))");
	        	parameters.put("years", param.getYears());	            
	            whereClause.append("(m.annualBudget.exercise.year in (:years))");
	        }
	      
	        //Rechercher l'associe
	        if (param.getAssocie() != null && param.getAssocie() != -1L ) {
	            if (whereClause.length() > 0) {
	                whereClause.append(" AND ");
	            }
	            parameters.put("associeId", param.getAssocie());
	            whereClause.append(" m.annualBudget.associeSignataire.id = :associeId");
	        }
	        
	        //Rechercher l'associe
	        if (param.getManager() != null && param.getManager() != -1L) {
	            if (whereClause.length() > 0) {
	                whereClause.append(" AND ");
	            }
	            parameters.put("managerId", param.getManager());
	            whereClause.append("m.annualBudget.budgetManager.id = :managerId");
	        }
	        
	        //Rechercher pour une mission spécifique
	        if (param.getCustomer() != null && param.getCustomer() != -1L) {
	            if (whereClause.length() > 0) {
	                whereClause.append(" AND ");
	            }
	            parameters.put("customerId", param.getCustomer());
	            whereClause.append("m.annualBudget.contract.customer.id = :customerId");
	        }

	        
	      
	        
	        if (whereClause.length() > 0) {	         
	        	hql.append(" WHERE ").append(whereClause);
	        }
	        
	       
	        //hql.append(" ORDER BY m.annualBudget.contract.customer.companyName,m.type,m.annualBudget.exercise.year ASC");
	        //hql.append(" ORDER BY  m.annualBudget.exercise.year, m.exercise ASC");
	        Query q = em.createQuery(hql.toString());
	        for (Map.Entry<String, Object> me : parameters.entrySet()) {
	            q.setParameter(me.getKey(), me.getValue());
	        }
	        
	      //Gestion de la pagination
	        if(usePagination == true){
	        	q.setFirstResult(firstPos);
		        q.setMaxResults(LINEPERPAGE);
	        }
	 
	       // List<Object[]> resultList = (List<Object[]>) q.getResultList();
	        List<Long> resultList = (List<Long>) q.getResultList();
			if (resultList.size() > 0) {
				
				List<ProfitabilityPerCustomerData> profits = new ArrayList<ProfitabilityPerCustomerData>();
				for(int i = 0;i<resultList.size();i++){
					Long idMission = resultList.get(i);
					
					ProfitabilityPerCustomerData result = calculateProfitabilityPerMission(idMission);
					profits.add(result);
												
				}
					
				return	profits;
				
            }else{
            	return null;
            }

		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in BudgetDao : calculateProfitabilityPerCustomer..."));
			//return null;

		}finally{
			em.close();
		}
		
	}
	
	
	public ProfitabilityPerCustomerData calculateProfitabilityPerMission(Long idMission){
		try{
			 
			Mission mission =em.find(Mission.class, idMission);
	        if (mission == null) {
	            throw new DaoException(2);
	        }
	       // m.annualBudget.contract.customer.companyName , m.annualBudget.exercise.year, m.annualBudget.associeSignataire.code,m.annualBudget.budgetManager.code,m.id,m.status,m.exercise from Mission m
	        
	        ProfitabilityPerCustomerData resultat = new ProfitabilityPerCustomerData(
	        		mission.getAnnualBudget().getExercise().getYear(),
	        		Integer.parseInt(mission.getExercise()), 
					mission.getAnnualBudget().getContract().getCustomer().getCompanyName(), 
					0.0d,
					0.0d,
					0.0d,
					mission.getAnnualBudget().getAssocieSignataire().getCode(),
					mission.getAnnualBudget().getBudgetManager().getCode(),
					idMission,
					mission.getStatus());
	        
	        calculateProfitabilityPerMission( mission,resultat);
	        
	        
	        return resultat;
	        
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in BudgetDao : calculateProfitabilityPerCustomer..."));
			//return null;

		}finally{
			em.close();
		}
	}
	
	private void calculateProfitabilityPerMission(Mission mission,ProfitabilityPerCustomerData resultData){
		
		try{
			
					
			//Calcul du prix de revient
			Number result = null;			               			       
			try{	
				
				 result = (Number) em
			       .createQuery(
			               "select sum(c.value * t.prixRevient)  from TimesheetCell c join c.row.activity.mission m join c.row.timesheet t where t.status = :status and m.id = :idMission ").setParameter("status", Constants.TIMESHEET_STATUS_STRING_VALIDATED).setParameter("idMission", mission.getId()).getSingleResult();
			               
			}
			catch(javax.persistence.NoResultException e){	
				result = null;
			}
			if ( result != null) {	
				resultData.setPrixRevient(resultData.getPrixRevient() + result.doubleValue());
			}
						
			//Calcul du prix de vente
			try{	
				
				result = (Number) em
			       .createQuery(
			               "select sum(c.value * t.prixVente)  from TimesheetCell c join c.row.activity.mission m join c.row.timesheet t where t.status = :status and  m.id=:idMission ").setParameter("status", Constants.TIMESHEET_STATUS_STRING_VALIDATED).setParameter("idMission", mission.getId()).getSingleResult();
			             
			}
			catch(javax.persistence.NoResultException e){	
				result = null;
			}						 			               			      
			if ( result != null) {							
				resultData.setPrixVente(resultData.getPrixVente() + result.doubleValue());
			}
					
			//Calcul de la somme facturée
			try{	 
				 result = (Number) em
			       .createQuery(
			               "select sum(f.amountNetEuro) from Invoice f where f.project.id=:idMission ").setParameter("idMission", mission.getId()).getSingleResult();
			}
			catch(javax.persistence.NoResultException e){	
				result = null;
			}	
			if ( result != null) {			
				resultData.setPrixFacture(resultData.getPrixFacture() + result.doubleValue());
			}
			
			//Appel récursif
			if(mission.getParent() != null){
				calculateProfitabilityPerMission(mission.getParent(),resultData);
			}
					

		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in BudgetDao : calculateProfitabilityPerCustomer..."));
			//return null;

		}finally{
			em.close();
		}
		
	}
	
	 public Mission markMissionAsClosed(Long missionId){
	
	 try{
		 	//Close the mission
			Mission mission = em.find(Mission.class, missionId);
	        if (mission == null) {        	
	        	throw new DaoException(2);	                		
	        }	       
	        mission.setStatus(Mission.STATUS_CLOSED);
	 		mission =  em.merge(mission); 
	 		
	 		//Close the budget
	 		AnnualBudget annualBudget = mission.getAnnualBudget();
	 		if (annualBudget == null) {        	
		        	throw new DaoException(2);	                		
		    }	 		
	 		annualBudget.setStatus(AnnualBudget.STATUS_CLOSED);
	 		annualBudget =  em.merge(annualBudget); 
	 		
	 		//Delete the children budgets
	 		deleteChildrenBudgets(annualBudget.getId());
	 		
	 		return mission;
	      
		 }catch(Exception e){
			 logger.error(e.getMessage());
			 throw new BusinessException(new ExceptionMessage("Failed in MissionDao :  markMissionAsClosed..."));
	 	}finally{
				em.close();
		}
		
		 
	 }
	 
	 public void deleteChildrenBudgets(Long id) {
			
			try{			
				
				List<AnnualBudget> resultList = em
			        .createQuery(
			                "select b  from AnnualBudget b  where b.parent.id = :parentId")
			        .setParameter("parentId", id).getResultList();
				
				if(( resultList != null) && (resultList.size()>0)){
					AnnualBudget budgetChild  = resultList.get(0);
					deleteChildrenBudgets(budgetChild.getId());
					
					Mission mission = budgetChild.getMission();
			        
			        mission.setAnnualBudget(null);
			        
			        budgetChild.setMission(null);
			        
			        em.remove(budgetChild); 
			        
			        em.remove(mission);  
		        }
				
			}
			catch(Exception e){
				logger.error(e.getMessage());			
				throw new BusinessException(new ExceptionMessage("Failed in MissionDao : deleteChildrenBudgets..."));			
			}finally{
				em.close();
			}  
	    }
	
	 @SuppressWarnings("unchecked")
	public Event updateEvent(Long eventId,Long userId, Long missionId, int year,  int month,
				int dayOfYear, int startHour,  int endHour, String object,  String title,Date dateOfEvent){
		 
		 
		 try{
	    	
		 	//Try to find out an existing event first
		 	Event event = em.find(Event.class, eventId);
		 	
 			StringBuilder hql = new StringBuilder("select e  from Event e join e.employee p where p.id = :employeId and e.year = :year and e.month =:month and e.day =:day and e.id != :targetEventId order by e.startHour");
	        		        
	        Query q = em.createQuery(hql.toString()).setParameter("employeId", userId).setParameter("year", year).setParameter("day", dayOfYear).setParameter("month", month).setParameter("targetEventId", eventId);
	        
	        List<Event> existingEventsForDay = (List<Event>) q.getResultList();
	        
	        //Verifier qu'il n'y a pas d'overlapping ---Dans ce cas on ne fait rien
    		if (existingEventsForDay.size() > 0) {    			
    			for(Event event0 : existingEventsForDay ){    				
    				if( ( startHour >= event0.getStartHour() ) && ( startHour < event0.getEndHour() ) ){
    					return null;
    				}
    			} 	
	        }
	        
	        
	    	if(event == null) return null;
	    	
	    	//Otherwise create a new chargeable or non-chargeable event
    		Activity activity  = null;
			Task task = null;
			
			//We try to determine if it is a chargeable activity or not
			Mission mission = em.find(Mission.class, missionId);
	        if (mission == null) {
	        	task = em.find(Task.class, missionId);
	        	if(task == null){
	        		throw new DaoException(2);
	        	}
	        		
	        }
	        
	        if (mission != null) {
	        	 //Load the activity by defaulft	        
		        Object[] activities = mission.getInterventions().toArray();
		        if(activities.length == 0){
		        	 throw new DaoException(2);
		        }
		        
		        activity  = (Activity)activities[0];
		        if (activity == null) {
		            throw new DaoException(2);
		        }
	        }
    		
    	
    		if( object == null){
    			 if (mission != null) {
        			 object = activity.getMission().getCustomer().getCompanyName();
        		 }else{
        			 object = task.getDescription();
        		 }
    		}
    		
    		if( title == null){ 
    			title = object;
    		}
    		
    		Event targetEvent = event;
    		int endHoursDiff = endHour - event.getEndHour();
    		
    		
    		
    		//Mise à jour des autres évènements 
    		if (existingEventsForDay.size() > 0) {    			
    		     //
    		     
    		     for( int index = 0; index < existingEventsForDay.size(); index++ ){     				
    		    	 	 
    		    	    Event event0  = existingEventsForDay.get(index)  ; 		 
    		    		 
    		    	 	 if( ( event0.getStartHour() > targetEvent.getStartHour() ) && ( (targetEvent.getEndHour() + endHoursDiff ) > event0.getStartHour() ) ){
    		    			 
    		    			 //if( endHoursDiff > 0){   
	    		    			 event0.setStartHour(event0.getStartHour() + endHoursDiff );
	    		    			 event0.setEndHour(event0.getEndHour() + endHoursDiff);	    		    			 
	    		    			 targetEvent =  em.merge(event0); 
	    		    			 
	    		    			 if(index + 1 <= existingEventsForDay.size()-1){
	    		    				 endHoursDiff =  targetEvent.getEndHour() - existingEventsForDay.get(index + 1).getStartHour();
	    		    			 }
    		    			// }    		    		 
    		    	 	 }    
    		    	 	 
     			}	        	
	        }
	    	
	    	
    		event.setTask(task);
			event.setActivity(activity);
	    	event.setYear(year);
	    	event.setMonth(month);
	    	event.setDay(dayOfYear);
	    	event.setDateOfEvent(dateOfEvent);
	    	event.setTitle(title);
	    	event.setType(object);
	    	event.setStartHour(startHour);
	    	event.setEndHour(endHour);
			
	    	//Update the event
			event =  em.merge(event);
	    	 
			
			//Update the status of the mission to ONGOING if needed
    	 	if (mission != null) {
    	 		if(!mission.getStatus().equalsIgnoreCase(Mission.STATUS_ONGOING)){
    	 		mission.setStatus(Mission.STATUS_ONGOING);
    	 		mission =  em.merge(mission); 
    	 		}   	    	 		
    	 	}
			
	    	
	    	return event;
	    	
		 }catch(Exception e){
			 logger.error(e.getMessage());
			 throw new BusinessException(new ExceptionMessage("Failed in MissionDao :  updateEvent..."));
	 	}finally{
				em.close();
		}
	    	
	    	
	    }
	
	 @SuppressWarnings("unchecked")
	public Event createEvent(Long userId, Long missionId, int year,  int month,
				int dayOfYear, int startHour,  int endHour, String object,  String title){
	    	
	    	
	    	try{
	    		
	    		//Try to find out an existing event first
	    		Event existingEvent = null;
	    		StringBuilder hql = new StringBuilder("select e  from Event e join e.employee p where p.id = :employeId and e.year = :year and e.month =:month and e.day =:day and e.startHour = :startHour and e.endHour =:endHour ");
		        		        
		        Query q = em.createQuery(hql.toString()).setParameter("employeId", userId).setParameter("year", year).setParameter("day", dayOfYear).setParameter("month", month).setParameter("startHour", startHour).setParameter("endHour", endHour);
		        
		        List<Event> resultList = (List<Event>) q.getResultList();
		        
		        if (resultList.size() > 0) {
		        	existingEvent = (Event) resultList.get(0);
		        }else{
		        	existingEvent = null;
		        }
		        
	        	if(existingEvent != null)return existingEvent;
	        	
	        	//Otherwise create a new chargeable or non-chargeable event
	    		Activity activity  = null;
				Task task = null;
				
				//We try to determine if it is a chargeable activity or not
				Mission mission = em.find(Mission.class, missionId);
		        if (mission == null) {
		        	task = em.find(Task.class, missionId);
		        	if(task == null){
		        		throw new DaoException(2);
		        	}
		        		
		        }
		        
		        if (mission != null) {
		        	 //Load the activity by defaulft	        
			        Object[] activities = mission.getInterventions().toArray();
			        if(activities.length == 0){
			        	 throw new DaoException(2);
			        }
			        
			        activity  = (Activity)activities[0];
			        if (activity == null) {
			            throw new DaoException(2);
			        }
		        }
	    		
	    		
	        	
	        	//Find theemployee
		        Employee employee = em.find(Employee.class, userId);
		        
        		if( object == null){
        			 if (mission != null) {
            			 //object = activity.getMission().getCustomer().getCompanyName();
        				 object = mission.getComment();
            		 }else{
            			 object = task.getDescription();
            		 }
        		}
        		
        		if( title == null){ 
        			title = object;
        		}
        		
        		
        		//Create the new event and save it
            	Event event = new   Event( employee,activity,task,  year,  month,
            			dayOfYear,  startHour,  endHour, object,  title);
            	
            	
            	
            	//Save the event
            	em.persist(event);
	    		
            	//Update the status of the mission to ONGOING if needed
   	    	 	if (mission != null) {
   	    	 		
	   	    	 	if(mission.getJobStatus() == null){    	 		
	       	 		 mission.setJobStatus(Mission.JOB_STATUS_PENDING); 		
	   	    	 	} 
	   	    	 	
   	    	 		if(!mission.getStatus().equalsIgnoreCase(Mission.STATUS_ONGOING)){
   	    	 			mission.setStatus(Mission.STATUS_ONGOING);   	    	 		
   	    	 		}   	
   	    	 		mission =  em.merge(mission); 
   	    	 	}
	        	
	        	return event;
	    		
	    	}catch(Exception e){
	    		logger.error(e.getMessage());
	    		throw new BusinessException(new ExceptionMessage("Failed in MissionDao :  createEvent..."));
	    	}finally{
				em.close();
			}
	    	
	    	
	    }
	 
	 public EventPlanning createPlanningEvent(Long userId,int year, Integer weekNumber ){
		 try{
			 String findPlanningqueryString = "select p from PlanningAnnuel p  where p.year = :year";
			EventPlanning eventPlanning = null;
			Employee employee = em.find(Employee.class, userId);
			PlanningAnnuel planning = null;
       	    List resultList = em.createQuery(findPlanningqueryString).setParameter("year",year).getResultList();
       	    if (resultList.size() > 0) {
       	    	planning = (PlanningAnnuel) resultList.get(0);
       	    }
       	    
	       	 if(planning == null){
	        	 throw new DaoException(2);
	         }
	        eventPlanning =  new EventPlanning( employee,  year,  weekNumber); 
	        eventPlanning.setPlanning(planning);
	        em.persist(eventPlanning);
	        return eventPlanning;
		   
		 }
		 catch(Exception e){
			 logger.error(e.getMessage());
			 throw new BusinessException(new ExceptionMessage("Failed in MissionDao :  createPlanningEvent..."));	        
		}finally{
			em.close();
		}
	 }
	
	 
	 public int createListOfPlanningEvents(List<Long> userIds,Long idEntity, int year, List<Integer> weekList,String startdate,String enddate,String durationType,float duration,Date expectedStartDate,Date expectedEndDate,double totalHoursSpent ){
		 try{
				Activity activity  = null;
				Task task = null;				
				String findEventPlanningqueryString = "select e from EventPlanning e join fetch e.employee u  where u.id = :employeId and e.year = :year and e.weekNumber = :weekNumber)";				
				String findPlanningqueryString = "select p from PlanningAnnuel p  where p.year = :year";
				
				//We try to determine if it is a chargeable activity or not
				Mission mission = em.find(Mission.class, idEntity);
		        if (mission == null) {
		        	task = em.find(Task.class, idEntity);
		        	if(task == null){
		        		throw new DaoException(2);
		        	}
		        		
		        }
		        
		        if (mission != null) {
		        	 //Load the activity by defaulft	        
			        Object[] activities = mission.getInterventions().toArray();
			        if(activities.length == 0){
			        	 throw new DaoException(2);
			        }
			        
			        activity  = (Activity)activities[0];
			        if (activity == null) {
			            throw new DaoException(2);
			        }
		        }
		        boolean isMission = true;
		        String object = null;
	       		 if (mission != null) {
	       			 object = activity.getMission().getCustomer().getCompanyName() + " [" + activity.getMission().getExercise() + "]";
	       		 }else{
	       			 object = task.getDescription();
	       			isMission = false;
	       		 }
	       		  
	       		String title = object;
		       
	       		ItemEventPlanning itemEventPlanning = null; 
	       		
	       		PlanningAnnuel planning = null;
	       	    List resultList = em.createQuery(findPlanningqueryString).setParameter("year",year).getResultList();
	       	    if (resultList.size() > 0) {
	       	    	planning = (PlanningAnnuel) resultList.get(0);
	       	    }
	       	    
		       	 if(planning == null){
		        	 throw new DaoException(2);
		         }
		       	 
		       	 boolean mustCalculateDate =  ( expectedStartDate == null) || ( expectedEndDate == null);
	        
		        
		        
		        for(Long userId: userIds){
		        	
		        	Employee employee = em.find(Employee.class, userId);
		        		
		        	for(Integer weekNumber : weekList){
		        		
		        		EventPlanning eventPlanning = null;
		        		
		        		
		        		//Try to find the event first otherwise create a new one
		                 resultList = em.createQuery(findEventPlanningqueryString).setParameter("year",year).setParameter("weekNumber",weekNumber).setParameter("employeId",employee.getId()).getResultList();
		                
		                 if(mustCalculateDate == true){
		                	 
		                	 	Calendar c = Calendar.getInstance();
				       		 	c.set(Calendar.YEAR, year);
				       		 	c.set(Calendar.WEEK_OF_YEAR,weekNumber); 											    					
				       	     
					       		 //Monday
					       		 c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);	    
					       		 Date theFirstDate = c.getTime();

					       		 //Friday
					       	     c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
					       	     Date theSecondDate = c.getTime();
				       	     
					       	     int nbDays = DateUtils.getNbDays( theFirstDate,  theSecondDate);
					       	     float duration2 = nbDays + 1;
					       	     String durationType2 = ItemEventPlanning.DURATION_TYPE_NA;
				        		
					       	     itemEventPlanning = new ItemEventPlanning( title,  idEntity,  isMission, durationType2, new Double(duration2), theFirstDate, theSecondDate); 
					       	     itemEventPlanning.setTotalHoursSpent(totalHoursSpent);
					       	  
		                 }
		                 else{
		                	 	itemEventPlanning = new ItemEventPlanning( title,  idEntity,  isMission,/* startdate, enddate,*/ durationType, new Double(duration), expectedStartDate, expectedEndDate);
		                	 	itemEventPlanning.setTotalHoursSpent(totalHoursSpent);
		                 }
		                  
		                 
		                if (resultList.size() > 0) {
		                	eventPlanning = (EventPlanning) resultList.get(0);
		                	eventPlanning.setPlanning(planning);
		                	eventPlanning.getActivities().add(itemEventPlanning);	
		                	itemEventPlanning.setEventPlanning(eventPlanning);
		                	em.persist(itemEventPlanning);
		                	em.merge(eventPlanning);
		                }
		                else{
		                	eventPlanning =  new EventPlanning( employee,  year,  weekNumber); 
		                	eventPlanning.setPlanning(planning);
		                	eventPlanning.getActivities().add(itemEventPlanning);
		                	itemEventPlanning.setEventPlanning(eventPlanning);
		                	
		                	try{
		            			//tx.begin();
		                		em.persist(eventPlanning);
		            	        //tx.commit();
		            	       
		            		}
		            		catch(Exception e){
		            			//tx.rollback();
		            			logger.error(e);
		            	        
		            		}
		                	
			        		
		                }
		        		
		        	}
		        }
		        
		        //Update the status of the mission to ONGOING if needed
	    	 	if (mission != null) {
	    	 		  
	    	 		if(mission.getJobStatus() == null){    	 		
	        	 		 mission.setJobStatus(Mission.JOB_STATUS_PENDING); 		
	        	     } 
	    	 		
	    	 		//Mark the mission as started and update the start date
	    	 		if(!mission.getStatus().equalsIgnoreCase(Mission.STATUS_ONGOING)){    	 		
		    	 		 mission.setStatus(Mission.STATUS_ONGOING);
		    	 		 mission.setStartDate(new Date());		    	 		
		    			 GregorianCalendar  calendar = new java.util.GregorianCalendar();
		    	 		 calendar.setTime( new Date());
		    	 		 int startWeek = calendar.get(Calendar.WEEK_OF_YEAR);
		    	 		 mission.setStartWeek(startWeek);
	    	 		} 
	    	 		
	    	 		mission =  em.merge(mission); 
	    	 	}
		        
		        return userIds.size() * weekList.size();
		        
			}
			catch(Exception e){
				logger.error(e.getMessage());
				throw new BusinessException(new ExceptionMessage("Failed in MissionDao :  createListOfPlanningEvents..."));	  
		        
			}finally{
				em.close();
			}
	 }
	 
	 public EventPlanning updateEventPlanning(EventPlanning eventPlanning){
		
			try{				
				eventPlanning = em.merge(eventPlanning);		        
		         return eventPlanning;
			}
			catch(Exception e){	
				logger.error(e.getMessage());
				throw new BusinessException(new ExceptionMessage("Failed in MissionDao :  updateEventPlanning..."));    
			}finally{
				em.close();
			}
			
	 }
	 
	 public void  deleteItemEventPlanning(Long identifier){
		
			try{
				
				StringBuilder hql = new StringBuilder("delete from ItemEventPlanning e where e.id = :eventId");
		        	        
		        Query q = em.createQuery(hql.toString()).setParameter("eventId", identifier);
		        
		        int result = q.executeUpdate();
		        
		        return ;
		       
			}
			catch(Exception e){
				logger.error(e.getMessage());
				throw new BusinessException(new ExceptionMessage("Failed in MissionDao :  deleteItemEventPlanning..."));    
		        
			}finally{
				em.close();
			}		  
	 }
	 
	 public void  deleteItemEventPlannings(List<Long> itemIds){
			
				try{
								        
					StringBuilder hql = new StringBuilder("delete from ItemEventPlanning e where e.id in (:eventIds)");
			        	        
			        Query q = em.createQuery(hql.toString()).setParameter("eventIds", itemIds);
			        
			        int result = q.executeUpdate();
			        
			        return ;			        
				}
				catch(Exception e){
					logger.error(e.getMessage());
					throw new BusinessException(new ExceptionMessage("Failed in MissionDao :  deleteItemEventPlannings(List<Long> itemIds)...")); 
			        
				}finally{
					em.close();
				}		  
		 }
	    
	  public int deleteListOfPlanningEvents(List<Long> userIds,int year,List<Integer> weekList ){
		  try{
				//Delete the items first
			  
			    StringBuilder hqIds = new StringBuilder("select e.id from EventPlanning e where e.employee.id in (:employeIds)  and e.year = :year and e.weekNumber in (:weekNumbers)");
				
			   Query q1 = em.createQuery(hqIds.toString()).setParameter("employeIds", userIds).setParameter("year", year).setParameter("weekNumbers", weekList);
		        
			   List<Long> resultList = (List<Long>) q1.getResultList();
			  
			   if(resultList != null && !resultList.isEmpty()){
				   
			
				   StringBuilder hq0 = new StringBuilder("delete from ItemEventPlanning i where i.eventPlanning.id in (:Ids)");
					
					Query q0 = em.createQuery(hq0.toString()).setParameter("Ids", resultList);
			        
			        int result = q0.executeUpdate(); 
			        
			        return result;
			       
			   }
			   else{
				   return 0;
			   }
			   
		        
			}
			catch(Exception e){
				logger.error(e.getMessage());
				throw new BusinessException(new ExceptionMessage("Failed in MissionDao : deleteListOfPlanningEvents..."));
		        
			}finally{
				em.close();
			}  
	  }
	  
	  
	  public int updatePlanningFromTimeSheets(List<Long> userIds,int year,List<Integer> weekList){
		  try{
			  int countRowsAdded=0;
			  //Delete the already registered entries
			  int countDeleted = deleteListOfPlanningEvents(userIds, year, weekList );
			  for(Long userId :  userIds){
				  for(Integer weekNumber :  weekList){

					  Timesheet timesheet = timesheetDao.findOneTimesheetsForUserId( userId, weekNumber, year);
					  if(timesheet != null){
						  timesheet= timesheetDao.getOne(timesheet.getId());
						   EventPlanning eventPlanning = getEventPlanning( year ,weekNumber,userId);    	
				        	if(eventPlanning == null){			        		
				        		eventPlanning = createPlanningEvent(userId,year,weekNumber );
				        	}
				        	else{
				        		if(!eventPlanning.getActivities().isEmpty()){
				        			List<Long> itemIds = new ArrayList<Long>();
				            		for(ItemEventPlanning itemPlanned : eventPlanning.getActivities()){ 
				            			itemIds.add(itemPlanned.getId());
				            		}
				        			deleteItemEventPlannings(itemIds);
				        			eventPlanning.getActivities().clear();
				        		}
				        		
				        	}
				        	
				        	//Update the planning from the timesheet
				    		for(TimesheetRow row : timesheet.getRows()){
				    			countRowsAdded++;
				    			long idEntityTofind = -1l;
				    			Activity activity = row.getActivity();
				    			//It is a mission
				    			if(activity != null){				
				    				idEntityTofind = activity.getMission().getId();
				    			}
				    			else{
				    				//It is a task				
				    				idEntityTofind = Long.parseLong(row.getTaskIdentifier());
				    			}
				    			
				    			ItemEventPlanning item = new ItemEventPlanning(row.getLabel(),
				    														  idEntityTofind,
				    														  row.getTaskIdentifier()==null ,
				    														  ItemEventPlanning.DURATION_TYPE_WHOLEDAY,
				    														  0,null,null);
				    			
				    			item.setTotalHoursSpent(row.getTotalOfHours());
				    			
				    			int minDay = timesheet.minDayNumberForRow(row);
				    			int maxDay =  timesheet.maxDayNumberForRow(row);
				    			TimesheetDataForPlanning ret = timesheet.buildTimesheetDataForPlanning( minDay, maxDay, row.getTotalOfHours());
				    			if(ret != null){
				    				item.setRealStartDate(ret.getStartDate());
				    				item.setRealEndDate(ret.getEndDate());
				    			}
				    			item.setEventPlanning(eventPlanning);
				    			
				    			eventPlanning.getActivities().add(item);
				    		}
				    		
				    		//Mark the event planning as validated
				    		eventPlanning.setValidated(true);
				    		updateEventPlanning(eventPlanning);
						  
					  }
				  }
			  }
			  
			  return countRowsAdded;
		  }
		  catch(Exception e){
				logger.error(e.getMessage());
				throw new BusinessException(new ExceptionMessage("Failed in MissionDao : updatePlanningFromTimeSheets..."));
		        
			}finally{
				em.close();
			}  
	  }
	
	public int createListOfEvents(List<Long> userIds,Long missionId,int temps,int year, int month,List<Integer> dayList ){
		try{
			Activity activity  = null;
			Task task = null;
			//tx.begin();
			StringBuilder startHourSql = new StringBuilder("select max(e.endHour)  from Event e join e.employee p where p.id = :employeId and e.year = :year and e.month =:month and e.day =:day ");
			
			//We try to determine if it is a chargeable activity or not
			Mission mission = em.find(Mission.class, missionId);
	        if (mission == null) {
	        	task = em.find(Task.class, missionId);
	        	if(task == null){
	        		throw new DaoException(2);
	        	}
	        		
	        }
	        
	        if (mission != null) {
	        	 //Load the activity by defaulft	        
		        Object[] activities = mission.getInterventions().toArray();
		        if(activities.length == 0){
		        	 throw new DaoException(2);
		        }
		        
		        activity  = (Activity)activities[0];
		        if (activity == null) {
		            throw new DaoException(2);
		        }
	        }
	       
	        
	        
	        
	        for(Long userId: userIds){
	        	
	        	Employee employee = em.find(Employee.class, userId);
	        		
	        	for(Integer dayOfYear : dayList){
	        		
	        		int startHour = -1;
	        		int endHour = -1;
        	        
	    	        Number result = null;
	    	        
	    	        try{	 
	    	        	result =(Number)  em.createQuery(startHourSql.toString()).setParameter("employeId", userId).setParameter("year", year).setParameter("day", dayOfYear).setParameter("month", month).getSingleResult();
	    	        }
	    	        catch(javax.persistence.NoResultException e){	
	    	        	result = null;
	    	        }	       
	    	        if (result != null) {
	    	        	 startHour =  result.intValue();
	    	        }else{
	    	        	startHour = 0;
	    	        }
	        		        	
	        		//Add one additionnal hour to the end hour
	        		endHour = startHour + temps;
	        		if(endHour > 52 )endHour = 52;
	        		
	        		String object = null;
	        		 if (mission != null) {
	        			 object = activity.getMission().getCustomer().getCompanyName();
	        		 }else{
	        			 object = task.getDescription();
	        		 }
	        		  
	        		String title = object;
	        		
	        		//Create the new event and save it
	            	Event event = new   Event( employee,activity,task,  year,  month,
	            			dayOfYear,  startHour,  endHour, object,  title);
	            	
	            	//Save the event
	            	em.persist(event);
	        		
	        	}
	        }
	        
	        //Update the status of the mission to ONGOING if needed
    	 	if (mission != null) {
    	 		  
    	 		if(mission.getJobStatus() == null){    	 		
        	 		 mission.setJobStatus(Mission.JOB_STATUS_PENDING); 		
        	     } 
    	 		
    	 		if(!mission.getStatus().equalsIgnoreCase(Mission.STATUS_ONGOING)){    	 		
    	 		 mission.setStatus(Mission.STATUS_ONGOING);    	 		
    	 		} 
    	 		
    	 		mission =  em.merge(mission); 
    	 	}
	        
	        return userIds.size() * dayList.size();
	        
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in MissionDao : createListOfEvents..."));
	        
		}finally{
			em.close();
		}
	}
	
	
	public int deleteListOfEvents(List<Long> userIds,int year, int month,List<Integer> dayList ){
		try{
			
	        //StringBuilder hql = new StringBuilder("delete from Event e join e.employee p where p.id in (:employeIds)  and e.year = :year and e.month =:month and e.day in (:days)");
			StringBuilder hql = new StringBuilder("delete from Event e where e.employee.id in (:employeIds)  and e.year = :year and e.month =:month and e.day in (:days)");
	        	        
	        Query q = em.createQuery(hql.toString()).setParameter("employeIds", userIds).setParameter("year", year).setParameter("days", dayList).setParameter("month", month);
	        
	        int result = q.executeUpdate();
	        
	        return result;
	        
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in MissionDao : deleteListOfEvents..."));
	        
		}finally{
			em.close();
		}
	}
	
	
	 public  List<Option> getAllMissionsExercicesOptions(){
	
	 //Build the open mission options
			List<Option> yearOptions= new ArrayList<Option>();
			StringBuilder openMissionsAsOptions = new StringBuilder("select distinct m.exercise from Mission m");
			Query q = em.createQuery(openMissionsAsOptions.toString());
			List<Object[]> resultList = (List<Object[]>) q.getResultList();
			if (resultList.size() > 0) {				
				for(int i = 0;i<resultList.size();i++){
					Object resultat = resultList.get(i);														      
					yearOptions.add(new Option((String)resultat,(String)resultat));														
				}
            }
			
			return yearOptions;
       }
	 
	
	
	public AnnualPlanningView buildAnnualPlanningView(Employee caller,String year, int startMonth,int endMonth,/*String month,String employeeType,*/String role,int startWeekNumber,int endWeekNumber){
		
		try{
			
			
			List<String> statuses = new ArrayList<String>();
			statuses.add(Mission.STATUS_PENDING);
			statuses.add(Mission.STATUS_ONGOING);
			//statuses.add(Mission.STATUS_STOPPED);
			//statuses.add(Mission.STATUS_CLOSED);
						
			
			//Build the open mission options
			List<Option> missionOptions= new ArrayList<Option>();
			StringBuilder openMissionsAsOptions = new StringBuilder("select m.id,m.title from Mission m where m.status in (:statuses) and  upper(m.exercise) = upper(:year) order by m.title asc");
			Query q = em.createQuery(openMissionsAsOptions.toString());
			q.setParameter("statuses", statuses).setParameter("year", year);
			List<Object[]> resultList = (List<Object[]>) q.getResultList();
			if (resultList.size() > 0) {				
				for(int i = 0;i<resultList.size();i++){
					Object[] resultat = resultList.get(i);
					Long id =  (Long)resultat[0] ;
					String value =  (String)resultat[1] ;					             
					missionOptions.add(new Option(Long.toString(id),value));														
				}
            }
			
			
			//Build the exercices options
			List<Option> yearOptions= new ArrayList<Option>();
			StringBuilder exerciceAsOptions = new StringBuilder("select e.id,e.year from Exercise e ");
			q = em.createQuery(exerciceAsOptions.toString());			
			resultList = (List<Object[]>) q.getResultList();
			if (resultList.size() > 0) {				
				for(int i = 0;i<resultList.size();i++){
					Object[] resultat = resultList.get(i);
					//Long id =  (Long)resultat[0] ;
					Integer value =  (Integer)resultat[1] ;					             
					yearOptions.add(new Option(Integer.toString(value),Integer.toString(value)));														
				}
            }
			
			
			//Build the employees options
			List<Option> employeeOptions= new ArrayList<Option>();
			List<Long> employeids= new ArrayList<Long>();
			StringBuilder employeeAsOptions = null;
			if(role.equalsIgnoreCase("All")){
				employeeAsOptions = new StringBuilder("select  e.id, e.code, e.lastName, e.firstName from Employee e WHERE e.userActive = true and e.position.name != 'SECRETAIRE' order by e.dateOfHiring asc");
				q = em.createQuery(employeeAsOptions.toString());
				resultList = (List<Object[]>) q.getResultList();
			}
			else{
				
				
				if(role.equalsIgnoreCase("managers")){
				
					employeeAsOptions = new StringBuilder("select  e.id,e.code, e.lastName, e.firstName from Employee e JOIN e.position p WHERE e.userActive = true and p.name in (:roleNames) order by e.dateOfHiring asc");
					q = em.createQuery(employeeAsOptions.toString());
					resultList = (List<Object[]>) q.setParameter("roleNames", Position.managerPositionNames()).getResultList();

				}else if(role.equalsIgnoreCase("partners")){					
					
					employeeAsOptions = new StringBuilder("select  e.id,e.code, e.lastName, e.firstName from Employee e JOIN e.position p WHERE e.userActive = true and p.name in (:roleNames) order by e.dateOfHiring asc");
					q = em.createQuery(employeeAsOptions.toString());
					resultList = (List<Object[]>) q.setParameter("roleNames", Position.partnersPositionNames()).getResultList();	
				}
				else if(role.equalsIgnoreCase("employees")){					
					
					employeeAsOptions = new StringBuilder("select  e.id,e.code, e.lastName, e.firstName from Employee e JOIN e.position p WHERE e.userActive = true and p.name in (:roleNames) order by e.dateOfHiring  asc");
					q = em.createQuery(employeeAsOptions.toString());
					resultList = (List<Object[]>) q.setParameter("roleNames", Position.employeePositionNames()).getResultList();	
				}
				else{
					
					employeeAsOptions = new StringBuilder("select  e.id,e.code, e.lastName, e.firstName from Employee e JOIN e.position p WHERE e.userActive = true and p.name in (:roleNames) order by e.dateOfHiring  asc");
					q = em.createQuery(employeeAsOptions.toString());
					resultList = (List<Object[]>) q.setParameter("roleNames", Position.secretairePositionNames()).getResultList();	
					
				}
							
			}
			 
					
			
			if (resultList.size() > 0) {				
				for(int i = 0;i<resultList.size();i++){
					Object[] resultat = resultList.get(i);
					Long id =  (Long)resultat[0] ;
					String value =  (String)resultat[1] ;
					String longName = (String)resultat[2] ;
					//employeeOptions.add(new Option(Long.toString(id),value));	
					//public Option(String id,String name,String longName,String selected)
					employeeOptions.add(new Option(Long.toString(id),value,longName,"false"));	
					employeids.add(id);
				}
            }
			
			
			 
			String positionsQuery = "select  distinct e.position from Employee e ";
			q = em.createQuery(positionsQuery.toString());
			List<Position> rolesList = (List<Position>) q.getResultList();
			
			
			//Build the tasks list options			
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
			codes.add("CONGEFOR");
			
			List<Option> taskOptions= new ArrayList<Option>();
			StringBuilder taskAsOptions = new StringBuilder("select  t.id, t.name from Task t where t.code in (:codes)  order by t.name asc");			
			q = em.createQuery(taskAsOptions.toString());
			q.setParameter("codes", codes);
			resultList = (List<Object[]>) q.getResultList();
			if (resultList.size() > 0) {				
				for(int i = 0;i<resultList.size();i++){
					Object[] resultat = resultList.get(i);
					Long id =  (Long)resultat[0] ;
					String value =  (String)resultat[1] ;					             
					taskOptions.add(new Option(Long.toString(id),value));														
				}
            }
			
			
			//Build the events list
			StringBuilder hql = new StringBuilder("select distinct e  from EventPlanning e where e.year = :year and e.weekNumber >= :startWeekNumber and e.weekNumber <= :endWeekNumber and e.employee.id in (:employeids) ORDER BY e.weekNumber asc");
			q = em.createQuery(hql.toString());		
			q.setParameter("startWeekNumber", startWeekNumber).setParameter("endWeekNumber", endWeekNumber).setParameter("year", Integer.parseInt(year)).setParameter("employeids", employeids);
			List<EventPlanning> events = (List<EventPlanning>) q.getResultList();
			
			 Map <String,EventPlanningData> activities = new HashMap<String,EventPlanningData>();
			   if(events != null && !events.isEmpty()){
				   for(EventPlanning event : events ){					 
					   int numberOfWeek = event.getWeekNumber();
					   String key = event.getEmployee().getId() + "-" + numberOfWeek;
					   activities.put(key, event.getEventPlanningData());					 			   
				   }
			   }
			   
			    Date lastUpdate = null;
			    hql = new StringBuilder("select p.lastUpdate  from PlanningAnnuel p where p.year = :year");
				q = em.createQuery(hql.toString());		
				q.setParameter("year", Integer.parseInt(year));
				List<Date> dates = (List<Date>) q.getResultList();
				if(dates != null && !dates.isEmpty()){
					lastUpdate = dates.get(0) ;
				}
				
				
			   
			   return new  AnnualPlanningView(
						activities,  year,startMonth, endMonth,
						 yearOptions,employeeOptions,missionOptions,
						 taskOptions,rolesList,role, 
						 startWeekNumber, endWeekNumber,lastUpdate);
			
	        
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in MissionDao : buildAnnualPlanningView..."));        
		}finally{
			em.close();
		}   
	}
	
	
public AnnualPlanningView updateAnnualPlanningView(AnnualPlanningView annualPlanningView){
		
		try{
		
			//Build the events list
			StringBuilder hql = new StringBuilder("select distinct e  from EventPlanning e where e.year = :year and e.weekNumber >= :startWeekNumber and e.weekNumber <= :endWeekNumber and e.employee.id in (:employeids) ORDER BY e.weekNumber asc");
			Query q = em.createQuery(hql.toString());		
			q.setParameter("startWeekNumber", annualPlanningView.getStartWeekNumber()).setParameter("endWeekNumber", annualPlanningView.getEndWeekNumber()).setParameter("year",Integer.parseInt(annualPlanningView.getYear()) ).setParameter("employeids", annualPlanningView.getAllUserIds());
			List<EventPlanning> events = (List<EventPlanning>) q.getResultList();
			
			 Map <String,EventPlanningData> activities = new HashMap<String,EventPlanningData>();
			   if(events != null && !events.isEmpty()){
				   for(EventPlanning event : events ){					 
					   int numberOfWeek = event.getWeekNumber();
					   String key = event.getEmployee().getId() + "-" + numberOfWeek;
					   activities.put(key, event.getEventPlanningData());					 			   
				   }
			   }
			   
			    Date lastUpdate = null;
			    hql = new StringBuilder("select p.lastUpdate  from PlanningAnnuel p where p.year = :year");
				q = em.createQuery(hql.toString());		
				q.setParameter("year", Integer.parseInt(annualPlanningView.getYear()));
				List<Date> dates = (List<Date>) q.getResultList();
				if(dates != null && !dates.isEmpty()){
					lastUpdate = dates.get(0) ;
				}
				
				annualPlanningView.setActivities(activities);
			   
			   return annualPlanningView;
	        
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in MissionDao : buildAnnualPlanningView..."));        
		}finally{
			em.close();
		}   
	}
	
	public AnnualTimesheetReportView buildAnnualTimesheetReportView(String year,int startMonth,int endMonth,int startWeekNumber,int endWeekNumber){
		
		
		AnnualTimesheetReportView annualTimesheetReportView = null;
		List<Option> yearOptions = null;
		List<TimesheetWeekReport> timesheetWeekReports = null;
		
		try{
			
		int nbTotal = 0;
		StringBuffer queryStringBuffer = new StringBuffer("select count(*) from Employee e where e.userActive = :userActive");
		 Query q = em.createQuery(queryStringBuffer.toString());
		 q.setParameter("userActive", Boolean.TRUE);
		 Number result = null;		 
		 try{	 
			 result =(Number) q.getSingleResult();
		 }
		 catch(javax.persistence.NoResultException e){	
			 result = null;
		 }		 
		 if ( result == null) {
			 nbTotal = 0;
		 } else {
			 nbTotal = result.intValue();
		 }
		
		//Build the exercices options
		yearOptions= new ArrayList<Option>();
		StringBuilder exerciceAsOptions = new StringBuilder("select e.id,e.year from Exercise e ");
		q = em.createQuery(exerciceAsOptions.toString());			
		List<Object[]> resultList = (List<Object[]>) q.getResultList();
		if (resultList.size() > 0) {				
			for(int i = 0;i<resultList.size();i++){
				Object[] resultat = resultList.get(i);			
				Integer value =  (Integer)resultat[1] ;					             
				yearOptions.add(new Option(Integer.toString(value),Integer.toString(value)));														
			}
        }
		
		//Initialize the timesheetWeekReports list
		timesheetWeekReports = new ArrayList<TimesheetWeekReport>();		
		for(int week = startWeekNumber; week<=endWeekNumber; week++ )
		{
			timesheetWeekReports.add(new TimesheetWeekReport(week, 0,0, 0, 0,nbTotal));
		}
		
		
				
		//Processing the submitted timesheets
		StringBuilder submittedQuery = new StringBuilder("select t.weekNumber, count(*) from Timesheet t where upper(t.exercise) = upper(:year) and t.status = 'SUBMITTED' group by t.weekNumber");
		q = em.createQuery(submittedQuery.toString());
		q.setParameter("year", year);
		 resultList = (List<Object[]>) q.getResultList();
		if (resultList.size() > 0) {				
			for(int i = 0;i<resultList.size();i++){
				Object[] resultat = resultList.get(i);
				int weekNumber =  (Integer)resultat[0] ;
				long nbSubmitted =  (Long)resultat[1] ;		
				 
				for(TimesheetWeekReport report : timesheetWeekReports )
				{
					if( report.getWeekNumber() == weekNumber ){
						report.setNbSubmitted((int)nbSubmitted);
						break;
					}
				}
																		
			}
        }

		//Processing the draft timesheets
		/*
		StringBuilder draftQuery = new StringBuilder("select t.weekNumber, count(*) from Timesheet t where upper(t.exercise) = upper(:year) and t.status = 'DRAFT' group by t.weekNumber");
		q = em.createQuery(draftQuery.toString());
		q.setParameter("year", year);
		resultList = (List<Object[]>) q.getResultList();
		if (resultList.size() > 0) {				
			for(int i = 0;i<resultList.size();i++){
				Object[] resultat = resultList.get(i);
				int weekNumber =  (Integer)resultat[0] ;
				long nbDraft =  (Long)resultat[1] ;
				
				for(TimesheetWeekReport report : timesheetWeekReports )
				{
					if( report.getWeekNumber() == weekNumber ){
						report.setNbDraft((int)nbDraft);
						break;
					}
				}
																	
			}
        }
        */

		//Processing the validated timesheets
		StringBuilder validatedQuery = new StringBuilder("select t.weekNumber, count(*) from Timesheet t where upper(t.exercise) = upper(:year) and t.status = 'VALIDATED' group by t.weekNumber");
		q = em.createQuery(validatedQuery.toString());
		q.setParameter("year", year);
		resultList = (List<Object[]>) q.getResultList();
		if (resultList.size() > 0) {				
			for(int i = 0;i<resultList.size();i++){
				Object[] resultat = resultList.get(i);
				int weekNumber =  (Integer)resultat[0] ;
				long nbValidated =  (Long)resultat[1] ;
				
				for(TimesheetWeekReport report : timesheetWeekReports )
				{
					if( report.getWeekNumber() == weekNumber ){
						report.setNbValidated((int)nbValidated);
						break;
					}
				}
																
			}
        }

		//Processing the rejected timesheets
		StringBuilder rejectedQuery = new StringBuilder("select t.weekNumber, count(*) from Timesheet t where upper(t.exercise) = upper(:year) and t.status = 'REJECTED' group by t.weekNumber");
		q = em.createQuery(rejectedQuery.toString());
		q.setParameter("year", year);
		resultList = (List<Object[]>) q.getResultList();
		if (resultList.size() > 0) {				
			for(int i = 0;i<resultList.size();i++){
				Object[] resultat = resultList.get(i);
				int weekNumber =  (Integer)resultat[0] ;
				long nbRejected =  (Long)resultat[1] ;
				
				for(TimesheetWeekReport report : timesheetWeekReports )
				{
					if( report.getWeekNumber() == weekNumber ){
						report.setNbRejected((int)nbRejected);
						break;
					}
				}
																	
			}
        }

		
		annualTimesheetReportView = new AnnualTimesheetReportView( year,  startMonth,  endMonth,
				 yearOptions,
				 timesheetWeekReports,
				 startWeekNumber,  endWeekNumber);
		
		
		return annualTimesheetReportView;
	
		
	}
	catch(Exception e){
		logger.error(e.getMessage());
		throw new BusinessException(new ExceptionMessage("Failed in MissionDao : buildAnnualTimesheetReportView..."));         
	}finally{
		em.close();
	}   


	
	}
	
	
	@SuppressWarnings("unchecked")
	public AgendaPlanningView buildAgendaPlanningView(Employee caller,String year,String week,String employeeIdentifier){
try{
			
			
			List<String> statuses = new ArrayList<String>();
			statuses.add(Mission.STATUS_PENDING);
			statuses.add(Mission.STATUS_ONGOING);
			//statuses.add(Mission.STATUS_STOPPED);
			//statuses.add(Mission.STATUS_CLOSED);
			
			//Build the open mission options
			List<Option> missionOptions= new ArrayList<Option>();
			//StringBuilder openMissionsAsOptions = new StringBuilder("select m.id,m.title from Mission m where m.status in (:statuses) and  upper(m.exercise) = upper(:year) order by m.title asc");
			StringBuilder openMissionsAsOptions = new StringBuilder("select m.id,m.title,m.exercise,m.type,e.year from Mission m join m.annualBudget.exercise e where m.status in (:statuses) and e.year = :year order by m.title,m.exercise asc");
			Query q = em.createQuery(openMissionsAsOptions.toString());
			//q.setParameter("statuses", statuses).setParameter("year", year);
			q.setParameter("statuses", statuses);
			q.setParameter("year", Integer.parseInt(year));
			List<Object[]> resultList = (List<Object[]>) q.getResultList();
			if (resultList.size() > 0) {				
				for(int i = 0;i<resultList.size();i++){
					Object[] resultat = resultList.get(i);
					String missionName =  (String)resultat[1];
					if( missionName != null && missionName.length() > 30){
						missionName = missionName.substring(0,30);
					}
					Long id =  (Long)resultat[0] ;
					
					String value = missionName + " [ "+(String)resultat[3] + " ] " + " [ "+(String)resultat[2] + " ] [ "+resultat[4] +" ]";					             
					missionOptions.add(new Option(Long.toString(id),value));														
				}
            }
			
			
			//Build the exercices options
			List<Option> yearOptions= new ArrayList<Option>();
			StringBuilder exerciceAsOptions = new StringBuilder("select e.id,e.year from Exercise e ");
			q = em.createQuery(exerciceAsOptions.toString());			
			resultList = (List<Object[]>) q.getResultList();
			if (resultList.size() > 0) {				
				for(int i = 0;i<resultList.size();i++){
					Object[] resultat = resultList.get(i);
					//Long id =  (Long)resultat[0] ;
					Integer value =  (Integer)resultat[1] ;					             
					yearOptions.add(new Option(Integer.toString(value),Integer.toString(value)));														
				}
            }
			
			
			//Build the employees options
			List<Option> employeeOptions= new ArrayList<Option>();
			StringBuilder employeeAsOptions = new StringBuilder("select  e.id, e.lastName, e.firstName from Employee e where e.userActive = true order by e.lastName , e.firstName  asc");
			q = em.createQuery(employeeAsOptions.toString());			
			resultList = (List<Object[]>) q.getResultList();
			if (resultList.size() > 0) {				
				for(int i = 0;i<resultList.size();i++){
					Object[] resultat = resultList.get(i);
					Long id =  (Long)resultat[0] ;
					String value =  (String)resultat[1] ;					             
					employeeOptions.add(new Option(Long.toString(id),value));														
				}
            }
			
			
			//Build the tasks list options
			//Build the tasks list options			
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
			codes.add("CONGEFOR");
			
			List<Option> taskOptions= new ArrayList<Option>();
			StringBuilder taskAsOptions = new StringBuilder("select  t.id, t.name from Task t where t.code in (:codes)  order by t.name asc");			
			q = em.createQuery(taskAsOptions.toString());
			q.setParameter("codes", codes);
			resultList = (List<Object[]>) q.getResultList();
			if (resultList.size() > 0) {				
				for(int i = 0;i<resultList.size();i++){
					Object[] resultat = resultList.get(i);
					Long id =  (Long)resultat[0] ;
					String value =  (String)resultat[1] ;					             
					taskOptions.add(new Option(Long.toString(id),value));														
				}
            }
			/*
			List<Option> taskOptions= new ArrayList<Option>();
			StringBuilder taskAsOptions = new StringBuilder("select  t.id, t.name from Task t where t.chargeable = false  order by t.name asc");
			q = em.createQuery(taskAsOptions.toString());			
			resultList = (List<Object[]>) q.getResultList();
			if (resultList.size() > 0) {				
				for(int i = 0;i<resultList.size();i++){
					Object[] resultat = resultList.get(i);
					Long id =  (Long)resultat[0] ;
					String value =  (String)resultat[1] ;					             
					taskOptions.add(new Option(Long.toString(id),value));														
				}
            }
            */
			
			//Load a detached employee instance
			Employee employee = null;
			String queryString = "select e from Employee e  where e.id = :id";
            List resultList2 = em.createQuery(queryString).setParameter("id",Long.parseLong(employeeIdentifier)).getResultList();
            if (resultList2.size() > 0) {
            	 employee = (Employee) resultList2.get(0);
            }
			
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.YEAR,Integer.parseInt(year));
			calendar.set(Calendar.WEEK_OF_YEAR,Integer.parseInt(week));
			
			calendar.set(Calendar.DAY_OF_WEEK , Calendar.MONDAY);
			calendar.set(Calendar.HOUR, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			//calendar.add(Calendar.DAY_OF_YEAR, -1);
			Date firstDate = calendar.getTime();
			   
			//calendar.set(Calendar.DAY_OF_WEEK , Calendar.SUNDAY);
			calendar.add(Calendar.DATE, 6);
			calendar.set(Calendar.HOUR, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			///calendar.add(Calendar.DAY_OF_YEAR, 1);
			Date secondDate = calendar.getTime();
			
			//Build the events list
			
			boolean pastDate = secondDate.before(new Date());
			
			Calendar calendar2 = Calendar.getInstance();
			calendar2.setTime(firstDate);    		
    		int startDateNumber = calendar2.get(Calendar.DAY_OF_YEAR);
    		int year2 = calendar2.get(Calendar.YEAR);
    		
    		calendar2.setTime(secondDate);    		
    		int endDateNumber = calendar.get(Calendar.DAY_OF_YEAR);
    		
    		//Problème de la fin d'année
    		if(endDateNumber < startDateNumber)endDateNumber = 365;
    		
    		List<Event> events = em.createQuery(
			"select e  from Event e join e.employee u WHERE u.id=:userId and e.year=:year and e.day >=:d1 AND e.day <= :d2 ) ")
			.setParameter("userId", Long.parseLong(employeeIdentifier)).setParameter("year", year2).setParameter("d1", startDateNumber).setParameter("d2", endDateNumber).getResultList();
			
    		//Build the map
 		    //<c:set var="key" value="${hour}-${day}"/>
 		    Map <String,Event> activities = new HashMap<String,Event>();
 		    if(events != null && !events.isEmpty()){
 			   for(Event event : events ){
 				   int dayNumber = event.getDayNumber(calendar);
 				   for(int i = event.getStartHour(); i < event.getEndHour(); i++){
 					   String key = i + "-" + dayNumber;
 					   activities.put(key, event);
 				   }  
 			   } 
 		    }
 		    
 		   boolean isModifiable = false;
 		   List<Timesheet> result = em.createQuery(
			"SELECT t FROM Timesheet t WHERE t.user.id=:userId AND t.weekNumber= :weekNumber AND UPPER(t.exercise) = UPPER(:exercise)")
			.setParameter("userId", Long.parseLong(employeeIdentifier)).setParameter("weekNumber", Integer.parseInt(week)).setParameter("exercise", year).getResultList();
			
 		   Timesheet timesheet = result.size() > 0 ? result.iterator().next() : null;			 
		   if(timesheet != null){
			   isModifiable = timesheet.getStatus().equalsIgnoreCase("SUBMITTED") ||  timesheet.getStatus().equalsIgnoreCase("VALIDATED") ;
			   
		   }
		   
		   isModifiable = isModifiable || pastDate;
		   
		   Set<ItemEventPlanning> eventsPlanned = null;
		   EventPlanning eventPlanning = null;
			 queryString = "select e from EventPlanning e  where e.year = :year and e.weekNumber = :weekNumber and e.employee.id = :employeeId";
            resultList2 = em.createQuery(queryString).setParameter("year",Integer.parseInt(year)).setParameter("weekNumber",Integer.parseInt(week)).setParameter("employeeId",Long.parseLong(employeeIdentifier)).getResultList();
           if (resultList2.size() > 0) {
        	   eventPlanning = (EventPlanning) resultList2.get(0);
        	   eventsPlanned = eventPlanning.getActivities();
           }
 		    
 		   return new  AgendaPlanningView(
					 activities,  year,
					 week,  employee, yearOptions,
					 employeeOptions,missionOptions,taskOptions,isModifiable,eventsPlanned);
					 
	        
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in MissionDao : buildAgendaPlanningView..."));
	        
		}finally{
			em.close();
		} 
		   
	   }
	

    public void deleteOne(Long id) {
		
		//EntityTransaction tx = em.getTransaction();
		try{
			//tx.begin();
			
			Mission mission =em.find(Mission.class, id);
	        if (mission == null) {
	            throw new DaoException(2);
	        }
	        em.remove(mission);     
				
	        //tx.commit();
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in MissionDao : deleteOne..."));
	        
		}finally{
			em.close();
		}
		
		   
    }
	
	public void  deleteOneCost(Long id){
		
		//EntityTransaction tx = em.getTransaction();
		try{
			//tx.begin();
			
			Cost cost =em.find(Cost.class, id);
	        if (cost == null) {
	            throw new DaoException(2);
	        }
	        em.remove(cost); 
				
	        //tx.commit();
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in MissionDao : deleteOneCost..."));
	        
		}finally{
			em.close();
		}		  
	}
	
public void  deleteOneAlerte(Long id){
		
		//EntityTransaction tx = em.getTransaction();
		try{
			//tx.begin();
			
			Alerte alerte =em.find(Alerte.class, id);
	        if (alerte == null) {
	            throw new DaoException(2);
	        }
	        em.remove(alerte); 
				
	        //tx.commit();
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in MissionDao : deleteOneAlerte..."));
	        
		}finally{
			em.close();
		}		  
	}
	
	
	
	
	
	@SuppressWarnings("unchecked")
	public List<Employee> getTeamMembers(Long missionId){
		
		try{			
			//StringBuilder hql = new StringBuilder("select distinct p from Event e join e.employee p join e.activity.mission m  where m.id = :missionId order by p.lastName");
			
			StringBuilder hql = new StringBuilder("select distinct i.eventPlanning.employee from ItemEventPlanning i where i.mission = true and i.idEntity = :missionId");
			
			Query q = em.createQuery(hql.toString());
			
			q.setParameter("missionId", missionId);
			List<Employee> resultList = (List<Employee>) q.getResultList();
			
			if (resultList.size() > 0) {				
				return	resultList;
				
            }else{
            	return null;
            }			
		}catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in MissionDao : getTeamMembers..."));
		}finally{
			em.close();
		}
        
	}
	
	
	@SuppressWarnings("unchecked")
	public long  countMissionBudget(Long missionId){
		
		
		
		
		try{	
			//We try to determine if it is a chargeable activity or not
			Mission mission = em.find(Mission.class, missionId);
			boolean hasParent = mission.getParent() != null;
			long resultat =0L;			
			StringBuilder hql = new StringBuilder("select count(c) from TimesheetCell c join c.row.activity.mission m join c.row.timesheet t join c.row.timesheet.user p  where m.id = :missionId and t.status = :validatedStatus ");
			Query q = em.createQuery(hql.toString());
			q.setParameter("missionId", missionId);
			q.setParameter("validatedStatus", "VALIDATED");
			Long result = (Long) q.getSingleResult();					
			if(result == null){
				resultat =  0L;
			}
			else{
				resultat =  result.longValue();
			}
			
					
			if( hasParent == true){
				resultat += countMissionBudget(mission.getParent().getId());
				return	resultat;
			}
			else{
				return	resultat;
			}
				
              
           
            
			
		}catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in MissionDao : calculateMissionBudget..."));
		}finally{
			em.close();
		}
        
	}
	
	
	@SuppressWarnings("unchecked")
	public List<MissionBudgetData>  calculateMissionBudget(Long missionId){
		
		
		
		
		try{	
			//We try to determine if it is a chargeable activity or not
			Mission mission = em.find(Mission.class, missionId);
			boolean hasParent = mission.getParent() != null;
			List<MissionBudgetData> budgets = new ArrayList<MissionBudgetData>();
			//StringBuilder hql = new StringBuilder("select p.lastName ,p.position.name, sum((e.endHour - e.startHour)), e.prixRevient, e.prixVente, e.coutHoraire, e.valid,e.dateOfEvent,e.month  from Event e join e.employee p join e.activity.mission m  where m.id = :missionId group by p.id,p.lastName,p.position.name,e.prixRevient, e.prixVente, e.coutHoraire, e.valid,e.dateOfEvent,e.month order by p.position.name");
			//StringBuilder hql = new StringBuilder("select p.lastName ,p.position.name, sum((e.endHour - e.startHour)), avg(e.prixRevient), avg(e.prixVente), avg(e.coutHoraire), e.valid,e.dateOfEvent,e.month  from Event e join e.employee p join e.activity.mission m  where m.id = :missionId group by p.id,p.lastName,p.position.name,e.prixRevient, e.prixVente, e.coutHoraire, e.valid,e.dateOfEvent,e.month order by p.lastName");
			StringBuilder hql = new StringBuilder("select p.lastName ,p.position.name, sum(c.value), avg(t.prixRevient), avg(t.prixVente), avg(t.coutHoraire),t.weekNumber,t.exercise from TimesheetCell c join c.row.activity.mission m join c.row.timesheet t join c.row.timesheet.user p  where m.id = :missionId and t.status = :validatedStatus group by p.id,p.lastName,p.position.name,t.weekNumber,t.exercise order by p.lastName");
			Query q = em.createQuery(hql.toString());
			q.setParameter("missionId", missionId);
			q.setParameter("validatedStatus", "VALIDATED");
			List<Object[]> resultList = (List<Object[]>) q.getResultList();
			if (resultList.size() > 0) {
				
				
				for(int i = 0;i<resultList.size();i++){
					Object[] resultat = resultList.get(i);
					Float value =  new Float((Double)resultat[2]) ;
					//float hours = (float)(value * 0.25); 
					float hours = (float)(value);
					Boolean valid = Boolean.TRUE/*((Boolean)resultat[6])*/ ;					
					budgets.add(new MissionBudgetData( (String)resultat[0],
														(String)resultat[1],
															hours,
														(Double)resultat[3],
														(Double)resultat[4],
														(Double)resultat[5],
														(Integer)resultat[6],
														(String)resultat[7]
														
													)
														);
				}
					
				if( hasParent == true){
					budgets.addAll(calculateMissionBudget(mission.getParent().getId()));
					return	budgets;
				}
				else{
					return	budgets;
				}
				
            }else{
            	if( hasParent == true){
					budgets.addAll(calculateMissionBudget(mission.getParent().getId()));
					return	budgets;
				}
				else{
					return budgets;
				}    
            }
            
			
		}catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in MissionDao : calculateMissionBudget..."));
		}finally{
			em.close();
		}
        
	}
	
	
	@SuppressWarnings("unchecked")
	public List<MissionBudgetData>  calculateMissionBudget2(Long missionId){
		try{
			
			//We try to determine if it is a chargeable activity or not
			Mission mission = em.find(Mission.class, missionId);
			boolean hasParent = mission.getParent() != null;
			List<MissionBudgetData> budgets = new ArrayList<MissionBudgetData>();
			
			
			//StringBuilder hql = new StringBuilder("select p.lastName ,p.position.name, sum((e.endHour - e.startHour)), avg(e.prixRevient), avg(e.prixVente), avg(e.coutHoraire)  from Event e join e.employee p join e.activity.mission m  where m.id = :missionId group by p.id,p.lastName,p.position.name order by p.lastName");
			//StringBuilder hql = new StringBuilder("select p.lastName ,p.position.name, sum(c.value), avg(t.prixRevient), avg(t.prixVente), avg(t.coutHoraire),t.weekNumber,t.exercise  from TimesheetCell c  join c.row.activity.mission m join c.row.timesheet t join c.row.timesheet.user p  where m.id = :missionId and t.status = :validatedStatus group by p.id,p.lastName,p.position.name,t.weekNumber,t.exercise order by p.lastName");
			
			//Remettre celui-là
			StringBuilder hql = new StringBuilder("select p.lastName ,p.position.name, c.value, t.prixRevient, t.prixVente, t.coutHoraire,t.weekNumber,t.exercise,m.exercise,m.type,m.id,r.id,r.codePrestation from TimesheetCell c join c.row r join c.row.activity.mission m join c.row.timesheet t join c.row.timesheet.user p  where m.id = :missionId and t.status = :validatedStatus");
			//StringBuilder hql = new StringBuilder("select p.lastName ,p.position.name, c.value, t.prixRevient, t.prixVente, t.coutHoraire,t.weekNumber,t.exercise,r.year,m.type,m.id,r.id from TimesheetCell c join c.row r join c.row.activity.mission m join c.row.timesheet t join c.row.timesheet.user p  where m.id = :missionId");
			Query q = em.createQuery(hql.toString());
			q.setParameter("missionId", missionId);
			q.setParameter("validatedStatus", "VALIDATED");
			List<Object[]> resultList = (List<Object[]>) q.getResultList();
			if (resultList.size() > 0) {
				
				
				for(int i = 0;i<resultList.size();i++){
					Object[] resultat = resultList.get(i);
					Float value =  new Float((Double)resultat[2]) ;
					//float hours = (float)(value * 0.25); 
					float hours = (float)(value);
									
					budgets.add(new MissionBudgetData( (String)resultat[0],
														(String)resultat[1],
															hours,
														(Double)resultat[3],
														(Double)resultat[4],
														(Double)resultat[5],
														(Integer)resultat[6],
														(String)resultat[7],
														(String)resultat[8],														
														(String)resultat[9],
														(Long)resultat[10],
														(Long)resultat[11],
														(String)resultat[12])
													
														);
				}
				
				if( hasParent == true){
						budgets.addAll(calculateMissionBudget2(mission.getParent().getId()));
						return	budgets;
				}
				else{
					return	budgets;
				}
					
				
				
            }else{
            	
            	if( hasParent == true){
					budgets.addAll(calculateMissionBudget2(mission.getParent().getId()));
					return	budgets;
				}
				else{
					return budgets;
				}            	
            }
            
			
		}catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in MissionDao : calculateMissionBudget2..."));
		}finally{
			em.close();
		}
        
	}
	
	
	@SuppressWarnings("unchecked")
	public List<MissionBudgetData>  calculateMissionBudgetForNonValidatedTimeSheet(Long missionId){
		try{
			
			//We try to determine if it is a chargeable activity or not
			Mission mission = em.find(Mission.class, missionId);
			boolean hasParent = mission.getParent() != null;
			List<MissionBudgetData> budgets = new ArrayList<MissionBudgetData>();
		
			//Remettre celui-là
			StringBuilder hql = new StringBuilder("select p.lastName ,p.position.name, c.value, t.prixRevient, t.prixVente, t.coutHoraire,t.weekNumber,t.exercise,m.exercise,m.type,m.id,r.id,r.codePrestation from TimesheetCell c join c.row r join c.row.activity.mission m join c.row.timesheet t join c.row.timesheet.user p  where m.id = :missionId and t.status != :validatedStatus");			
			Query q = em.createQuery(hql.toString());
			q.setParameter("missionId", missionId);
			q.setParameter("validatedStatus", "VALIDATED");
			List<Object[]> resultList = (List<Object[]>) q.getResultList();
			if (resultList.size() > 0) {
				
				
				for(int i = 0;i<resultList.size();i++){
					Object[] resultat = resultList.get(i);
					Float value =  new Float((Double)resultat[2]) ;
					//float hours = (float)(value * 0.25); 
					float hours = (float)(value);
									
					budgets.add(new MissionBudgetData( (String)resultat[0],
														(String)resultat[1],
															hours,
														(Double)resultat[3],
														(Double)resultat[4],
														(Double)resultat[5],
														(Integer)resultat[6],
														(String)resultat[7],
														(String)resultat[8],														
														(String)resultat[9],
														(Long)resultat[10],
														(Long)resultat[11],
														(String)resultat[12])
													
														);
				}
				
				if( hasParent == true){
						budgets.addAll(calculateMissionBudgetForNonValidatedTimeSheet(mission.getParent().getId()));
						return	budgets;
				}
				else{
					return	budgets;
				}
					
				
				
            }else{
            	
            	if( hasParent == true){
					budgets.addAll(calculateMissionBudgetForNonValidatedTimeSheet(mission.getParent().getId()));
					return	budgets;
				}
				else{
					return budgets;
				}            	
            }
            
			
		}catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in MissionDao : calculateMissionBudget2..."));
		}finally{
			em.close();
		}
        
	}
	
	@SuppressWarnings("unchecked")
	public List<MissionHeurePresteeData>  calculateMissionHeuresPrestees(Long missionId){
		try{	
			//We try to determine if it is a chargeable activity or not
			Mission mission = em.find(Mission.class, missionId);
			boolean hasParent = mission.getParent() != null;
			
			
			StringBuilder hql = new StringBuilder("select t  from TimesheetRow t join t.activity.mission m  join t.timesheet.user u where m.id = :missionId ");
			Query q = em.createQuery(hql.toString());
			q.setParameter("missionId", missionId);
			List<TimesheetRow> resultList = (List<TimesheetRow>) q.getResultList();
			if (resultList.size() > 0) {
				
				Map<String,MissionHeurePresteeData> heuresPrestees = new HashMap<String,MissionHeurePresteeData>();
				for(int i = 0;i<resultList.size();i++){
					TimesheetRow timesheetRow = resultList.get(i);
					String employeeName = timesheetRow.getTimesheet().getUser().getFullName();
					Date startDateOfWeek = timesheetRow.getTimesheet().getStartDateOfWeek();
					//Date endDateOfWeek = timesheetRow.getTimesheet().getEndDateOfWeek();
					String codePrestation = timesheetRow.getCodePrestation();
					String exercise = timesheetRow.getTimesheet().getExercise();
					String custNumber = timesheetRow.getCustNumber();
					
					 Calendar calendar = Calendar.getInstance();
					
					for(TimesheetCell cell:timesheetRow.getCells()){
						String key = cell.getDayNumber() + "." + custNumber + "." + timesheetRow.getTimesheet().getUser().getId();
												
						calendar.setTime(startDateOfWeek);
						calendar.add(Calendar.DAY_OF_YEAR, cell.getDayNumber()-1);
						Date datePrestation = calendar.getTime();
						
						
						MissionHeurePresteeData data = heuresPrestees.get(key);
						if(data == null){
							data = new MissionHeurePresteeData( employeeName,  datePrestation,
									 codePrestation,  exercise, cell.getValue(),
									 (double)(cell.getValue()*timesheetRow.getTimesheet().getPrixRevient()) , (double)(cell.getValue()*timesheetRow.getTimesheet().getPrixVente()));							
							heuresPrestees.put(key, data);
						}else{							
							data.setHours(data.getHours() + cell.getValue());	
							data.setPrixRevient((float)(data.getHours()*timesheetRow.getTimesheet().getPrixRevient()));
							data.setPrixVente((float)(data.getHours()*timesheetRow.getTimesheet().getPrixVente()));
						}
					}
				}
				
				if( hasParent == true){
					List<MissionHeurePresteeData> temp = new ArrayList<MissionHeurePresteeData>(heuresPrestees.values());
					temp.addAll(calculateMissionHeuresPrestees( mission.getParent().getId()));
					return temp;
				}
				else{
					return	 new ArrayList<MissionHeurePresteeData>(heuresPrestees.values());
				}
					
				
				
            }else{
            	
            	if( hasParent == true){
					return calculateMissionHeuresPrestees( mission.getParent().getId());
				}
				else{
					return null;	
				}
            	
            }
            
			
		}catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in MissionDao : calculateMissionHeuresPrestees..."));
		}finally{
			em.close();
		}
	}
	
	
	private List<Long> listOfMissionIdentifiersForEmployee(Long idEmployee){
		try{			
			StringBuilder hql = new StringBuilder("select distinct e.idEntity from ItemEventPlanning e join e.eventPlanning p  where p.employee.id = :employeeId and e.mission = true");
			
			Query q = em.createQuery(hql.toString());
			
			q.setParameter("employeeId", idEmployee);
			List<Long> resultList = (List<Long>) q.getResultList();
			
			if (resultList.size() > 0) {				
				return	resultList;
				
            }else{
            	return null;
            }			
		}catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in MissionDao : listOfMissionIdentifiersForEmployee..."));
		}finally{
			em.close();
		}
	}
	
	
	//" select min(STARTDATE) as debut, max (ENDDATE) as fin, sum(TOTALWORKEDHOURS) as totalWorkedHours, (sum(TOTALESTIMATEDHOURS)/8) as totalEstimatedDays from ACTIVITIES where MISSION_ID = 21"

	
	 /**
     * 
     * @return AnnualBudget objects list representing all AnnualBudget known by IAMS
     *         application.
     */

	public List<MissionData> searchMissions( Employee caller,SearchMissionParam param, boolean sortedByName, boolean isforOption){
		
		//return findMissions( caller,param);
		return findMissionsForEmployee( caller, param,  sortedByName, isforOption);
	}
	
	@SuppressWarnings("unchecked")
	public List<MissionData> findMissions(Employee caller,SearchMissionParam param, boolean sortedByName, boolean isforOption) {
		
		try{
			
			Map<String, Object> parameters = new HashMap<String, Object>();
			
			 StringBuilder hql = null;
		     StringBuilder whereClause = new StringBuilder("");
		        /*
			 if( caller != null && caller.getPosition().hasManagingPosition() == false ){	
				 List<Long> identifiers = listOfMissionIdentifiersForEmployee(caller.getId());
					System.out.println(identifiers);
				// hql = new StringBuilder("select DISTINCT new com.interaudit.domain.model.data.MissionData(m.id,m.reference,c.companyName,c.code,m.exercise,m.status, c.origin.name, c.associeSignataire.code, c.customerManager.code, m.type ,m.startDate,m.dueDate,m.jobStatus,m.toDo,m.jobComment,m.dateCloture)  from Event e join e.activity.mission m join e.activity.mission.annualBudget.contract.customer c  where e.employee.id = :employeeId  ");
					 hql = new StringBuilder("select DISTINCT new com.interaudit.domain.model.data.MissionData(m.id,m.reference,c.companyName,c.code,m.exercise,m.status, c.origin.name, c.associeSignataire.code, c.customerManager.code, m.type , m.startDate,m.dueDate,m.jobStatus,m.toDo,m.jobComment,m.dateCloture,m.toFinish,m.startWeek)  from Mission m join m.annualBudget.contract.customer c where m.id in (:missionIds) ");
				     parameters.put("missionIds", identifiers);
		      }else{
		    	  hql = new StringBuilder("select DISTINCT new com.interaudit.domain.model.data.MissionData(m.id,m.reference,c.companyName,c.code,m.exercise,m.status, c.origin.name, c.associeSignataire.code, c.customerManager.code, m.type , m.startDate,m.dueDate,m.jobStatus,m.toDo,m.jobComment,m.dateCloture,m.toFinish,m.startWeek)  from Mission m join m.annualBudget.contract.customer c  ");
		      }
		 	
			 if( caller != null  && caller.getPosition().hasManagingPosition() == false ){
				 whereClause.append(" AND "); 
			 }
			 
			 
			  private Employee associeSignataire;
	
	
	@OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="fk_manager" , nullable = false)
    private Employee budgetManager;
			
	  MissionData(Long id, String customerName,String year,  String type, int exercice)
			 */
		     List<Long> ids = null;
		     if (param.getEmployee() != null) {
		    	  ids =  listOfMissionIdentifiersForEmployeeAsTeamMember(param.getEmployee(), Integer.parseInt(param.getYear()));
		     }
		     
		    if( isforOption ){
		    	 hql = new StringBuilder("select DISTINCT new com.interaudit.domain.model.data.MissionData(m.id,c.companyName,m.exercise, m.type ,e.year)  from Mission m join m.annualBudget.exercise e join m.annualBudget.contract.customer c  ");
		    }
		    else{
		    	 hql = new StringBuilder("select DISTINCT new com.interaudit.domain.model.data.MissionData(m.id,m.reference,c.companyName,c.code,m.exercise,m.status, c.origin.name, m.annualBudget.associeSignataire.code, m.annualBudget.budgetManager.code, m.type , m.startDate,m.dueDate,m.jobStatus,m.toDo,m.jobComment,m.dateCloture,m.toFinish,m.startWeek,m.approvalDate,m.signedDate,e.year,m.memberAsString,m.startYear)  from Mission m join m.annualBudget.exercise e join m.annualBudget.contract.customer c  ");
		    }
		   
		        
		       
	        
	        //Rechercher les années
	        if (param.getYear()!= null) {
	            //parameters.put("year", param.getYear());
	            //whereClause.append("( upper(m.exercise) = upper(:year))");
	        	parameters.put("year", Integer.parseInt(param.getYear()) );
	            whereClause.append("(e.year = :year)");
	        }
	        
	        //Rechercher le status
	        if ((param.getListOfStatus() != null) && (param.getListOfStatus().isEmpty() == false)) {
	            if (whereClause.length() > 0) {
	                whereClause.append(" AND ");
	            }
	            parameters.put("status", param.getListOfStatus());
	            whereClause.append("( m.jobStatus in (:status) OR m.status in (:status)) ");
	           // whereClause.append("( m.status in (:status)) ");
	        }
	        
	      //Rechercher le customer
	        if (param.getCustomer() != null) {
	            if (whereClause.length() > 0) {
	                whereClause.append(" AND ");
	            }
	            parameters.put("customerId", param.getCustomer());
	            whereClause.append("(c.id = :customerId )");
	        }
	        
	      //Rechercher le manager
	        if (param.getManager() != null) {
	            if (whereClause.length() > 0) {
	                whereClause.append(" AND ");
	            }
	            parameters.put("managerId",  param.getManager() );
	            whereClause.append("(c.customerManager.id = :managerId)");
	           
	        }
	        
	      //Rechercher l'employe
	        if (param.getEmployee() != null) {
	        	
	        	
	            if (whereClause.length() > 0) {
	                whereClause.append(" AND ");
	            }
	            parameters.put("employeId",  param.getEmployee() );
	            parameters.put("missionIds",  ids );
	            
	          
	         
	           // whereClause.append("( (m.annualBudget.budgetManager.id = :employeId)  or (m.annualBudget.associeSignataire.id = :employeId) OR (:employeId in m.members.memberId) )");
	           whereClause.append(" ( m.annualBudget.budgetManager.id = :employeId  or m.annualBudget.associeSignataire.id = :employeId or m.id in (:missionIds)) ");
	            
	            //whereClause.append(" ( (m.annualBudget.budgetManager.id = :employeId ) or( m.annualBudget.associeSignataire.id = :employeId) ) ");
	           
	        }
	        
	        //Rechercher une mission particulière
	        if (param.getMissionId() != null) {
	            if (whereClause.length() > 0) {
	                whereClause.append(" AND ");
	            }
	            parameters.put("missionId", param.getMissionId());
	            whereClause.append("(m.id = :missionId )");
	        }
	        
	        
	        
	        if (whereClause.length() > 0) {
	        	/*
	        	if( caller != null &&  caller.getPosition().hasManagingPosition() == true ){
	        		//hql.append(" WHERE ").append(whereClause);
	        		hql.append(whereClause);
				 }else{
					 hql.append(" WHERE ").append(whereClause); 					 
				 }
				 */	 
	        	 hql.append(" WHERE ").append(whereClause);
	        }
	        
	        //Tri alphabétique ou chronologique
	        if(sortedByName){
	        	 hql.append(" ORDER BY c.companyName,m.exercise asc ");
	        }
	        else{
	        	hql.append(" ORDER BY m.startYear,m.startWeek asc ");
	        }
	      
	        
	      
	      
	        
	        Query q = em.createQuery(hql.toString());
	        for (Map.Entry<String, Object> me : parameters.entrySet()) {
	            q.setParameter(me.getKey(), me.getValue());
	           // log.debug("***************** parameters " + me.getKey() + " = " + me.getValue());
	        }
	    
	        return q.getResultList();

		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in MissionDao : findMissions..."));

		}finally{
			em.close();
		}
		
		 	
	    }
	
	
	public  List<Option> getAllOpenMissionsWithoutFinalBillForYearAsOptions(String  exercise){
		
try{
			
			Map<String, Object> parameters = new HashMap<String, Object>();
			
		     StringBuilder whereClause = new StringBuilder("");
		     
		     StringBuilder hql =  new StringBuilder("select DISTINCT new com.interaudit.domain.model.data.MissionData(m.id,c.companyName,m.exercise, m.type ,e.year)  from Mission m join m.annualBudget.exercise e join m.annualBudget.contract.customer c  ");
		   
		   
		        
		       
	        
	        //Rechercher les années
	        if (exercise!= null) {	            
	        	parameters.put("year", Integer.parseInt(exercise) );
	            whereClause.append("(e.year = :year)");
	        }
	        
	        
	        
	        
	        
	        if (whereClause.length() > 0) {
	        	
	        	 hql.append(" WHERE m.annualBudget.finalBill=false and ").append(whereClause);
	        }
	        
	        //Tri alphabétique ou chronologique
	        
	        	hql.append(" ORDER BY c.companyName,m.exercise asc ");
	        
	        
	      
	      
	        
	        Query q = em.createQuery(hql.toString());
	        for (Map.Entry<String, Object> me : parameters.entrySet()) {
	            q.setParameter(me.getKey(), me.getValue());
	           // log.debug("***************** parameters " + me.getKey() + " = " + me.getValue());
	        }
	    
	        List<MissionData> missions = q.getResultList();
	        
	        List<Option> options= new ArrayList<Option>();
	 	   for(MissionData missionData : missions){
	 			options.add(new Option(Long.toString(missionData.getId()),missionData.getCustomerName()+" ["+missionData.getType()+"]"+" ["+missionData.getYear()+"] [" + missionData.getExercice() + " ]"));
	 	   }
	 	   
	 	   return options;

		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in MissionDao : findMissions..."));

		}finally{
			em.close();
		}
	}
	
	
	private List<Long> listOfMissionIdentifiersForEmployeeAsTeamMember(Long idEmployee,int year){
		
		
		try{			
			StringBuilder hql = new StringBuilder("select distinct m.missionId from MissionMember m join m.project.annualBudget.exercise e  where m.memberId = :employeeId and e.year = :year");
			
			Query q = em.createQuery(hql.toString());
			
			
			q.setParameter("employeeId", idEmployee).setParameter("year", year);
			List<Long> resultList = (List<Long>) q.getResultList();
			
			if (resultList.size() > 0) {				
				return	resultList;
				
            }else{
            	return null;
            }			
		}catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in MissionDao : listOfMissionIdentifiersForEmployeeAsTeamMember..."));
		}finally{
			em.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<MissionData> findMissionsForEmployee(Employee caller,SearchMissionParam param, boolean sortedByName, boolean isforOption) {
		
		return findMissions( caller,param,  sortedByName,  isforOption);
		
	}
	
	
	@SuppressWarnings("unchecked")

	public MissionData findMissionDataForIdenifier(Long id) {
	        try{
	        	//String queryString = "select new com.interaudit.domain.model.data.MissionData(m.id,m.reference,c.companyName,c.code,m.exercise,m.status, c.origin.name, c.associeSignataire.code, c.customerManager.code, m.type , m.totalScheduledCost , m.totalCost,m.startDate,m.dueDate)  from Mission m join m.annualBudget.contract.customer c  where upper(m.id) = upper(:id)";
	        	String queryString = "select new com.interaudit.domain.model.data.MissionData(m.id,m.reference,c.companyName,c.code,m.exercise,m.status, c.origin.name, c.associeSignataire.code, c.customerManager.code, m.type ,m.startDate,m.dueDate,m.jobStatus,m.toDo,m.jobComment,m.dateCloture,m.toFinish,m.startWeek,m.approvalDate,m.signedDate,e.year,m.memberAsString,m.startYear) from Mission m join m.annualBudget.exercise e join m.annualBudget.contract.customer c  where m.id = :id";
	            List resultList = em.createQuery(queryString)
	            .setParameter("id",
	                    id).getResultList();
	            if (resultList.size() > 0) {
	                return (MissionData) resultList.get(0);
	            }
	            return null;
	        }
	            catch(Exception e){
	            	logger.error(e.getMessage());
	            	throw new BusinessException(new ExceptionMessage("Failed in MissionDao : findMissionDataForIdenifier..."));
			
	        	}finally{
	        		em.close();
	        	}
	}
	
	public List<Employee> getMissionMembers(Long idMission){
		try{
			Mission mission = em.find(Mission.class, idMission);
			List<MissionMember> members = mission.getMembers();
			List<Employee> employees = new ArrayList<Employee>();
			for(MissionMember member : members){
				employees.add(member.getEmployee());
			}
			em.clear() ;
			return employees;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in MissionDao : getMissionMembers..."));

		}finally{
			em.close();
		}
	}
		
			
	
    
    
    public Mission getOne(Long id) {
    	
    	try{
    		 return em.find(Mission.class, id);
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in MissionDao : getOne..."));

		}finally{
			em.close();
		}
       
    }
    
    public int addMemberToMission(Long idMission,Long employeId){
    	try{
    		Mission mission =  em.find(Mission.class, idMission);
    		Employee member =  em.find(Employee.class, employeId);
    		MissionMember association = new  MissionMember();
   		  	association.setEmployee(member);
   		  	association.setProject(mission);
   		  	association.setMemberId(member.getId());
   		  	association.setMissionId(mission.getId());
   		  	association.setStarDate(new Date());    		
    		em.persist(association);
    		em.flush();
    		mission =  em.find(Mission.class, idMission);
    		//Update mission member string
    		List<MissionMember> members = mission.getMembers();
			StringBuffer buffer = new StringBuffer();
			int index=0;
			for(MissionMember member2 : members){
				if(index > 0){
					buffer.append(",");
				}
				buffer.append(member2.getEmployee().getCode());
				index++;
			}
			mission.setMemberAsString(buffer.toString());
			em.merge(mission);
			
    		return 1;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in MissionDao : addMemberToMission..."));

		}finally{
			em.close();
		}
    }
    
    public int removeMemberToMission(Long idMission,Long employeId){
    	try{
    		MissionMemberId pkey = new MissionMemberId(employeId, idMission);
    		MissionMember association =  em.find(MissionMember.class, pkey);    		  		
    		em.remove(association);
    		em.flush();
    		
    		Mission mission =  em.find(Mission.class, idMission);
    		//Update mission member string
    		List<MissionMember> members = mission.getMembers();
			StringBuffer buffer = new StringBuffer();
			int index=0;
			for(MissionMember member2 : members){
				if(index > 0){
					buffer.append(",");
				}
				buffer.append(member2.getEmployee().getCode());
				index++;
			}
			mission.setMemberAsString(buffer.toString());
			em.merge(mission);
    		
    		return 1;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in MissionDao : removeMemberToMission..."));

		}finally{
			em.close();
		}
    }
    
    public int updateMissionMemberstring(Long idMission){
    	try{
    		
    		//Update mission member string
    		Mission mission =  em.find(Mission.class, idMission);
    		List<MissionMember> members = mission.getMembers();
			StringBuffer buffer = new StringBuffer();
			int index=0;
			for(MissionMember member2 : members){
				if(index > 0){
					buffer.append(",");
				}
				buffer.append(member2.getEmployee().getCode());
				index++;
			}
			mission.setMemberAsString(buffer.toString());
			em.merge(mission);
    		return 1;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in MissionDao : removeMemberToMission..."));

		}finally{
			em.close();
		}
    }
    
   
    
    
    @SuppressWarnings("unchecked")
    
	public Mission getOneDetached(Long id){
    	try{
	    	
    		String queryString = "select distinct(m) from Mission m join fetch m.annualBudget b left join fetch m.interventions interventions left join fetch m.extraCosts extraCosts left join fetch m.documents documents left join fetch m.factures factures  left join fetch m.messages messages where m.id = :id";
	        List resultList = em.createQuery(queryString)
	        .setParameter("id",
	                id).getResultList();
	        if (resultList.size() > 0) {
	            return (Mission) resultList.get(0);
	        }else{
	        	return null;
	        }
        
    	}catch(Exception e){
        	
    		logger.error(e.getMessage());
    		throw new BusinessException(new ExceptionMessage("Failed in MissionDao : getOneDetached..."));
        }
    	finally{
    		em.close();
    	}
    }
    
    
    @SuppressWarnings("unchecked")
  
	public Mission getOneDetachedFromBudgetId(Long id){
    	try{
		    	String queryString = "select distinct(m) from Mission m join fetch m.annualBudget b left join fetch m.interventions interventions left join fetch m.extraCosts extraCosts left join fetch m.documents documents left join fetch m.factures factures  left join fetch m.messages messages where b.id = :id";
		        List resultList = em.createQuery(queryString)
		        .setParameter("id",
		                id).getResultList();
		        if (resultList.size() > 0) {
		            return (Mission) resultList.get(0);
		        }
		        return null;
    	}catch(Exception e){
    		logger.error(e.getMessage());
    		throw new BusinessException(new ExceptionMessage("Failed in MissionDao : getOneDetachedFromBudgetId..."));
    	}finally{
    		em.close();
    	}
    }
    
    @SuppressWarnings("unchecked")
	public Mission getOneDetachedForContractAndExercise(Long contractId, String exercise, int year ){
    	try{
	    	String queryString = "select distinct(m) from Mission m join fetch m.annualBudget b  where b.contract.id = :contractId and b.exercise.year = :year and m.exercise = :exercise ";
	        List resultList = em.createQuery(queryString).setParameter("contractId",contractId).setParameter("year",year).setParameter("exercise",exercise).getResultList();
	          
	        if (resultList.size() > 0) {
	            return (Mission) resultList.get(0);
	        }
	        return null;
	}catch(Exception e){
		logger.error(e.getMessage());
		throw new BusinessException(new ExceptionMessage("Failed in MissionDao : getOneDetachedForContractAndExercise..."));
	}finally{
		em.close();
	}
    }
    
    @SuppressWarnings("unchecked")
	public Mission  getOneDetachedFromReference(String reference){
    	try{
    		String queryString = "select distinct(m) from Mission m join fetch m.annualBudget b left join fetch m.interventions interventions left join fetch m.extraCosts extraCosts left join fetch m.documents documents left join fetch m.factures factures  left join fetch m.messages messages where upper(m.reference) = upper(:reference)";
	        List resultList = em.createQuery(queryString)
	        .setParameter("reference",
	        		reference).getResultList();
	        if (resultList.size() > 0) {
	            return (Mission) resultList.get(0);
	        }else{
	        	return null;
	        }
        
    	}catch(Exception e){        	
    		logger.error(e.getMessage());
    		throw new BusinessException(new ExceptionMessage("Failed in MissionDao : getOneDetachedFromReference..."));
        }
    	finally{
    		em.close();
    	}
    }
    
    
    


    public Mission saveOne(Mission mission) {    	
		try{
			//tx.begin();
			em.persist(mission);
	        //tx.commit();
	        return mission;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in MissionDao : saveOne..."));
	        
		}finally{
			em.close();
		}
		
    	
        
    }


    public Mission updateOne(Mission mission) {
    	
		try{		
			mission = em.merge(mission);
			
			AnnualBudget annualBudget = mission.getAnnualBudget();
			
			if(mission.getStatus().equalsIgnoreCase("ONGOING")){
				annualBudget.setStatus(AnnualBudget.STATUS_ONGOING);
			}else if(mission.getStatus().equalsIgnoreCase("CLOSED")){
				annualBudget.setStatus(AnnualBudget.STATUS_CLOSED);
			}else if(mission.getStatus().equalsIgnoreCase("CANCELLED")){
				annualBudget.setStatus(AnnualBudget.STATUS_CANCELLED);
			}
			annualBudget = em.merge(annualBudget);
			
	        return mission;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in MissionDao : updateOne..."));
	        
		}finally{
			em.close();
		}       
    }
    
    
    public Message updateOneMessage(Message message){
    	
		try{		
			message = em.merge(message);	        
	        return message;
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in MissionDao : updateOneMessage..."));
	        
		}finally{
			em.close();
		}   
    }
    
    
    public EventPlanning getEventPlanningFromIdentifier(Long planningId){
    	
		try{
				String queryString = "select distinct(e) from EventPlanning e  where e.id = :id";
	            List resultList = em.createQuery(queryString).setParameter("id",planningId).getResultList();
	            if (resultList.size() > 0) {
	                return (EventPlanning) resultList.get(0);
	            }
	            return null;
	        	
			
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in MissionDao : getEventPlanningFromIdentifier..."));
	        
		}finally{
			em.close();
		}
		
    }
    
    
    
    public EventPlanning getEventPlanning( int year,int weekNumber,Long employeId){
    	try{
			String queryString = "select distinct(e) from EventPlanning e  where e.year = :year and e.employee.id = :employeeId and e.weekNumber = :weekNumber";
            List resultList = em.createQuery(queryString).setParameter("year",year).setParameter("weekNumber",weekNumber).setParameter("employeeId",employeId).getResultList();
            if (resultList.size() > 0) {
                return (EventPlanning) resultList.get(0);
            }
            return null;
        	
		
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in MissionDao : getEventPlanning..."));
	        
		}finally{
			em.close();
		}
    }
    
    
    
    @SuppressWarnings("unchecked")
	 
	public  Message  getOneMessageDetached(Long id){
		 try{
	        	//String queryString = "select m from Message m  join m.mission e join m.from f join m.to t where upper(m.id) = upper(:id)";
			 String queryString = "select m from Message m  join m.mission e join m.from f  where m.id = :id";
	            List resultList = em.createQuery(queryString).setParameter("id",id).getResultList();
	            if (resultList.size() > 0) {
	                return (Message) resultList.get(0);
	            }
	            return null;
	        	}catch(Exception e){
	    			logger.error(e.getMessage());
	    			throw new BusinessException(new ExceptionMessage("Failed in MissionDao : getOneMessageDetached..."));
	    	        
	    		}finally{
	        		em.close();
	        	}
	 }
	 
	
    
    public Message getMessageOne(Long id) {
    	

		try{
			 return em.find(Message.class, id);
		}
		catch(Exception e){
			logger.error(e.getMessage());
			throw new BusinessException(new ExceptionMessage("Failed in MissionDao : getMessageOne..."));

		}finally{
			em.close();
		}
       
    }
    

    @SuppressWarnings("unchecked")
	public List<MessageData> searchMessages(Employee employee,Integer year,Long customerIdentifier,boolean received,boolean usePagination ,int firstPos,int LINEPERPAGE) {
		
		try{
			
				Map<String, Object> parameters = new HashMap<String, Object>();
		        //StringBuilder hql = new StringBuilder("select me.id,me.subject, me.contents, f.lastName,f.id, t.lastName,t.id,me.createDate, me.sentDate,m.id, c.companyName, me.read from Message me  join me.mission m  join me.mission.annualBudget.contract.customer c join me.from f join me.to t ");*
				StringBuilder hql = new StringBuilder("select me.id,me.subject, me.contents, f.lastName,f.id, me.emailsList,me.id,me.createDate, me.sentDate,m.id, c.companyName, me.read from Message me  join me.mission m  join me.mission.annualBudget.contract.customer c  join me.from f  ");
		        StringBuilder whereClause = new StringBuilder("");
		        
		        //Rechercher la direction des emails
		        
		        if (received == true) {
		            
		        	parameters.put("emailList","%"+ employee.getEmail() +"%");
		            whereClause.append(" ( me.emailsList like :emailList) ");
		            //whereClause.append("(me.to.id = :idEmploye)");
		        }
		        else{	     
		        	parameters.put("idEmploye", employee.getId());
			        whereClause.append("(me.from.id = :idEmploye)");
		        }
		        
		        //Rechercher les années
		        if (year!= null) {
		        	if (whereClause.length() > 0) {
			                whereClause.append(" AND ");
			        }
		            parameters.put("year", year.toString());
		            whereClause.append("( upper(m.exercise) = upper(:year))");
		        }
		       
		        
		        //Rechercher le customer
		        if (customerIdentifier != null) {
		            if (whereClause.length() > 0) {
		                whereClause.append(" AND ");
		            }
		            parameters.put("customerId", customerIdentifier);
		            whereClause.append("(m.id = :customerId )");
		        }
		        
		        
		        if (whereClause.length() > 0) {
		           hql.append(" WHERE ").append(whereClause);
		        }
		        
		        hql.append(" ORDER BY me.createDate desc ");
		        Query q = em.createQuery(hql.toString());
		        for (Map.Entry<String, Object> me : parameters.entrySet()) {
		            q.setParameter(me.getKey(), me.getValue());
		           // log.debug("***************** parameters " + me.getKey() + " = " + me.getValue());
		        }
		        //Gestion de la pagination
		        if(usePagination == true){
		        	q.setFirstResult(firstPos);
			        q.setMaxResults(LINEPERPAGE);
		        }
		        
		    
		       
		        
		        List<Object[]> resultList = (List<Object[]>) q.getResultList();
				if (resultList.size() > 0) {
					
					List<com.interaudit.domain.model.data.MessageData> resultats = new ArrayList<com.interaudit.domain.model.data.MessageData>();
					for(int i = 0;i<resultList.size();i++){
						Object[] resultat = resultList.get(i);
						Long id = (Long)resultat[0];
						String subject = (String)resultat[1]; 
						String contents = (String)resultat[2];  
						String from = (String)resultat[3]; 
						Long fromId = (Long)resultat[4]; 
						String to = (String)resultat[5]; 
						Long toId = (Long)resultat[6]; 
						Date createDate = (Date)resultat[7]; 
						Date sentDate = (Date)resultat[8]; 
						Long missionId= (Long)resultat[9];  
						String customerName = (String)resultat[10]; 
						boolean read= (Boolean)resultat[11]; 
						
										
						resultats.add(new com.interaudit.domain.model.data.MessageData(  id,  subject,  contents,  from,
								 fromId,  to,  toId,  createDate,  sentDate,
								 missionId,  customerName,  read)
														
															);
					}
						
				return	resultats;
					
	            }else{
	            	return null;
	            }

			}
			catch(Exception e){
				logger.error(e.getMessage());
				throw new BusinessException(new ExceptionMessage("Failed in MissionDao : searchMessages..."));
	
			}finally{
				em.close();
			}
	        
		 	
	    }


	public ITimesheetDao getTimesheetDao() {
		return timesheetDao;
	}


	public void setTimesheetDao(ITimesheetDao timesheetDao) {
		this.timesheetDao = timesheetDao;
	}
    
   

    
}
