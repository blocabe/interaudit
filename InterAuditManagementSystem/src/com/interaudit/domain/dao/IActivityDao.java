package com.interaudit.domain.dao;

import java.util.Date;
import java.util.List;
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
import com.interaudit.service.param.SearchActivityParam;


public interface IActivityDao {
	
	 
    /**
     * Returns a Mission object identified by given id.
     * 
     * @param id
     * @return Returns a Mission object identified by given id.
     */
	Activity getOne(Long id);
	
	double calculateNumberOfHoursSpent(Long userId,Long activityId, int dateOfWorkDate);
	
	public double calculateTotalNumberOfHoursSpentOnActivity(Long userId,Long activityId);
	
	public int getNextInterventionSequence(Long idMission);
	
	
	public Activity getOneInterventionDetached(Long id);
	
	
    /**
     * Delete a Mission object identified by given id.
     * 
     * @param id
     */
    void deleteOne(Long id); 
    
    public void deleteOneWorkDoneDayly(Long id);
    
 
    /**
     * Returns a Mission object identified by given id.
     * 
     * @param id
     * @return Returns a Task object identified by given id.
     */
    Activity saveOne(Activity activity); 
    
    public Activity getActivity(Employee employee,Mission mission,Task task);
    
   
    /**
     * Returns a Mission object identified by given id.
     * 
     * @param id
     * @return Returns a Task object identified by given id.
     */
    Activity updateOne(Activity activity);
    
    
    /**
     * 
     * @return Mission objects list matching the criterias
     */
    List<ActivityData> searchActivities(  SearchActivityParam param);
    
    /**
     * @param id
     * @return
     */
    ActivityData findActivityDataForIdenifier(Long id);
    
    /**
     * @param user
     * @param firstDate
     * @param secondDate
     * @return
     */
    List<Activity> getAllScheduledActivitiesForUserInPeriod(Employee user,Date firstDate, Date secondDate );
    
    /**
     * @param id
     * @return the messages from the target mission
     */
    List<Message> getMessagesFromParentMission(Long id) ;
    
    public List<Message> getMessagesNotSent(int count);
    
    public  List<Comment> getCommentsNotSent(int count);
    
    public List<EmailData> getEmailDatasNotSent(int count);
    
    public List<Timesheet> searchTimesheetToRemind();
    
    public List<Employee> searchEmployeeToRemindTimesheet();
    
    public Message updateOne(Message message);
    
    public EmailData updateOne(EmailData message);
    
    public Comment updateOne(Comment message);

	/**
	 * @param id
	 * @return the documents from the target mission
	 */
    List<Document> getDocumentsFromParentMission(Long id);
    
    public List<Employee> getTeamMembersForMission(Long id);

}
