package com.interaudit.service;

import java.util.Date;
import java.util.List;

import com.interaudit.domain.model.Activity;
import com.interaudit.domain.model.Employee;
import com.interaudit.domain.model.Mission;
import com.interaudit.domain.model.Task;
import com.interaudit.domain.model.data.Option;
import com.interaudit.service.param.SearchActivityParam;
import com.interaudit.service.view.ActivityView;
import com.interaudit.service.view.InterventionDetailsView;



/**
 * Service methods handling Mission entities.
 */
/**
 * @author blocabe
 *
 */
public interface IActivityService {
	
	
	public  List<Option> getAllExercicesOptions();
    
    /**
     * Returns a Activity object identified by given id.
     * 
     * @param id
     * @return Returns a Activity object identified by given id.
     */
	Activity getOne(Long id);
	
	public Activity getOneInterventionDetached(Long id);
	
	public Activity manageWorkDone(Long taskId,Long workDoneId,Date dateOfWorkDate,String comments,Integer spentHours);
	
	public double calculateNumberOfHoursSpent(Long userId,Long activityId, int dateOfWorkDate);
	
	
    public boolean removeWorkDoneFromIntervention( Long taskId, Long workDoneId);
    

    /**
     * Delete a Activity object identified by given id.
     * 
     * @param id
     */
    void deleteOne(Long id); 
    public void deleteOneWorkDoneDayly(Long id);
    
 
    /**
     * Returns a Activity object identified by given id.
     * 
     * @param id
     * @return Returns a Activity object identified by given id.
     */
    Activity saveOne(Activity activity); 
    
    Activity getActivity(Employee employee,Mission mission,Task task);
    
   
    /**
     * Returns a Activity object identified by given id.
     * 
     * @param id
     * @return Returns a Activity object identified by given id.
     */
    Activity updateOne(Activity activity);
    
    
    /**
     * 
     * @return Mission objects list matching the criterias
     */
    ActivityView searchActivities(  SearchActivityParam param);
    
    
    /**
     * @param id
     * @return
     */
    InterventionDetailsView buildInterventionDetailsView (Long id);
    
    
    /**
	 * @param user
	 * @param weekNumber
	 * @param exercise
	 * @return
	 */
    public List<Activity> getAllScheduledActivitiesForUserInPeriod(Employee user,Date firstDate, Date secondDate );
    
    
    public Activity createOrUpdateIntervention(
    	    Long missionId,
    	    Long interventionId,
    	    Long taskId,
    	    Long profileId,
    	    Long emloyeeId ,
    	    java.util.Date startdate,
    	    java.util.Date enddate,
    		float hours,
    		String priority);
    
    
    
}
