package com.interaudit.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.interaudit.domain.dao.IActivityDao;
import com.interaudit.domain.dao.IExerciseDao;
import com.interaudit.domain.dao.ITimesheetDao;
import com.interaudit.domain.model.Activity;
import com.interaudit.domain.model.Employee;
import com.interaudit.domain.model.Exercise;
import com.interaudit.domain.model.Mission;
import com.interaudit.domain.model.Task;
import com.interaudit.domain.model.Timesheet;
import com.interaudit.domain.model.TimesheetCell;
import com.interaudit.domain.model.TimesheetRow;
import com.interaudit.domain.model.data.ActivityData;
import com.interaudit.domain.model.data.Option;
import com.interaudit.service.IActivityService;
import com.interaudit.service.ICustomerService;
import com.interaudit.service.IRoleService;
import com.interaudit.service.ITaskService;
import com.interaudit.service.ITimesheetRowService;
import com.interaudit.service.IUserService;
import com.interaudit.service.param.SearchActivityParam;
import com.interaudit.service.view.ActivityView;
import com.interaudit.service.view.InterventionDetailsView;

@Transactional
public class ActivityService implements IActivityService {
	private Log logger = LogFactory.getLog(ActivityService.class);
    private IActivityDao activityDao;    
    private IUserService userService;
    private IExerciseDao exerciseDao;
    private ICustomerService customerService;    
	private ITaskService taskService;
	private IRoleService roleService;
	private ITimesheetDao timesheetDao;
	private ITimesheetRowService timesheetRowService;
	
	
	
	
	
	public double calculateNumberOfHoursSpent(Long userId,Long activityId, int dateOfWorkDate){
		return activityDao.calculateNumberOfHoursSpent( userId, activityId,  dateOfWorkDate);
	}
    
    
    public Activity createOrUpdateIntervention(
    Long missionId,
    Long interventionId,
    Long taskId,
    Long profileId,
    Long emloyeeId ,
    java.util.Date startdate,
    java.util.Date enddate,
	float hours,
	String priority){
    	
    	/*
    	Employee employee = userService.getOne(emloyeeId);
    	Task task = taskService.getOne(taskId);
		Mission mission = missionService.getOne(missionId);
		Position profile = roleService.getOne(profileId);
		Double expectedCost = hours * employee.getCoutHoraire();
		

		
		Intervention intervention = null;
		
		if(interventionId == null){
			Double actualCost = 0.0d;
			int order =  activityDao.getNextInterventionSequence(mission.getId());
			intervention = new Intervention( employee,  task,  startdate,
					enddate, mission, profile, hours,order,  priority,expectedCost,actualCost);
			intervention = (Intervention)this.saveOne(intervention);
		}else{
			intervention = (Intervention)activityDao.getOne(interventionId);
			intervention.setEmployee(employee);
			intervention.setTask(task);
			intervention.setStartDate(startdate);
			intervention.setEndDate(enddate);
			intervention.setTotalEstimatedHours(hours);
			intervention.setPriority(priority);
			intervention.setExpectedCost(expectedCost);
			intervention = (Intervention)this.updateOne(intervention);
		}
		 
		return intervention;
		*/
    	return null;
   
    }
    
    public Activity manageWorkDone( Long taskId,Long workDoneId,Date dateOfWorkDate,String comments,Integer spentHours){
    	return null;
				/*
    			Intervention intervention = (Intervention)this.getOneInterventionDetached(taskId);
    			
    			Long userId = intervention.getEmployee().getId();
				
				if(intervention != null){
					WorkDoneDayly workDoneDayly = null;
					if(workDoneId == null){
						//Create a new intervention
						workDoneDayly = new WorkDoneDayly( dateOfWorkDate,  spentHours,  comments, intervention);								
					}else{
						
						//Search for the matching workdone
						for(WorkDoneDayly workDoneDayly2 : intervention.getWorkDoneList()){
							if(workDoneDayly2.getId().longValue() == workDoneId.longValue()){
								workDoneDayly = workDoneDayly2;
								break;
							}
						}
						
						if(workDoneDayly != null){
							//Compute the dayOfYear number
							Calendar c = Calendar.getInstance();
							c.setTime(dateOfWorkDate);
							int dayOfYearNumber = c.get(Calendar.DAY_OF_YEAR);
							workDoneDayly.setDayOfYearNumber(dayOfYearNumber);
							workDoneDayly.setComments(comments);
							workDoneDayly.setDateOfWork(dateOfWorkDate);
							workDoneDayly.setSpentHours(spentHours);
							workDoneDayly.setIntervention(intervention);
						}
					}
					
					
					
					//Update the intervention
					intervention.setUpdateDate(new Date());
					intervention = (Intervention)this.updateOne(intervention);
					
					//Update the timesheet
					if(intervention != null){
						Timesheet timesheet = synchronizeTimesheet(userId,intervention.getId(), dateOfWorkDate);
						return intervention;
					}
					else{
						return null;
					}
				}else{
					return null;
				}
				*/
    }
    
   
     
    
    
    public boolean removeWorkDoneFromIntervention( Long taskId, Long workDoneId){
    	
    	boolean ret = false;
    	/*
    	//Long workDoneId =  workDoneIdAsString == null || workDoneIdAsString.trim().length()==0 ? null:  Long.parseLong(workDoneIdAsString);
		Intervention intervention = (Intervention) getOneInterventionDetached(taskId);
		Long userId = intervention.getEmployee().getId();
		boolean update = false;
		if(intervention != null){
			//WorkDoneDayly workDoneDayly = null;
			//Search for the matching workdone
			Date dateOfWorkDate = null; 
			for(WorkDoneDayly workDoneDayly : intervention.getWorkDoneList()){
				if(workDoneDayly.getId().longValue() == workDoneId.longValue()){
					dateOfWorkDate = workDoneDayly.getDateOfWork();
					workDoneDayly.setIntervention(null);
					intervention.getWorkDoneList().remove(workDoneDayly);
					deleteOneWorkDoneDayly(workDoneDayly.getId());
					update =true;
					break;
				}
			}
			
			//Update the intervention if needed
			if( update == true){
				updateOne(intervention);
				
				
				
				//Update the timesheet
				if(intervention != null){
					Timesheet timesheet = synchronizeTimesheet(userId,intervention.getId(), dateOfWorkDate);
					ret = true;
				}
			}
			
		}
		*/
		
		return ret;
    	
    }
    
    
public Timesheet synchronizeTimesheet(Long userId,Long activityId, Date dateOfWorkDate){
		
		Calendar c = Calendar.getInstance();
		c.setTime(dateOfWorkDate);
		
		int dayOfYearNumber = c.get(Calendar.DAY_OF_YEAR); 
		int weekNumber = c.get(Calendar.WEEK_OF_YEAR);
		int year = c.get(Calendar.YEAR); 

		
		Activity activity = this.getOne(activityId);
		
		Employee employee = userService.getOne(userId);
		
		boolean isNew = false;
		//Retrieve the matching timeshet or create a new one if needed
		Timesheet timesheet  = timesheetDao.findOneTimesheetsForUserId(userId, weekNumber,year);
		
		if(timesheet == null){
			//timesheet = new Timesheet( employee, Integer.toString(year) , weekNumber );
			timesheet = createOne(employee, weekNumber, Integer.toString(year));
			isNew = true;
		}
		
		assert(timesheet != null);
		
		//Retrieve the matching timeshetrow or create a new one if needed
		TimesheetRow timesheetrow = null;
		/*
		if(isNew == true){
			timesheetrow = new TimesheetRow( timesheet, activity);
		}else{
			*/
			
			//Search in a row exist yet for the activity
			for(TimesheetRow row : timesheet.getRows()){
				if(row.getActivity().getId().longValue() == activity.getId().longValue()){
					timesheetrow = row;
					break;
				}
			}
			
			//In case the line does not exist yet for the actibity
			/*
			if(timesheetrow == null){
				timesheetrow = new TimesheetRow( timesheet, activity);
			}
			*/
			//}
		
		assert(timesheetrow != null);
		
		TimesheetCell timesheetcell = null;
		//Search in a row exist yet for the cell
		for(TimesheetCell cell : timesheetrow.getCells()){
			if(cell.getDayNumber().intValue() == dayOfYearNumber){
				timesheetcell = cell;
				break;
			}
		}
			
		//In case the cell does not exist yet within the row
		if(timesheetcell == null){
			timesheetcell = new TimesheetCell( );
			timesheetcell.setRow(timesheetrow);
			timesheetrow.addCell(timesheetcell);
			timesheetcell.setDayNumber(dayOfYearNumber);
		}
		
		//Update the value for the target cell and calculate the actual cost for the activity
		double nbHoursSpent = this.calculateNumberOfHoursSpent(userId, activityId , dayOfYearNumber);
		
		//double nbTotalHoursSpentForActivity = activityDao.calculateTotalNumberOfHoursSpentOnActivity( userId, activityId);
		
		//Double actualCost = nbTotalHoursSpentForActivity * employee.getCoutHoraire();
		//activity.setActualCost(actualCost);
		timesheetcell.setValue(nbHoursSpent);
		
		
		updateOne(activity);
		timesheet = timesheetDao.updateOne(timesheet);
		return  timesheet;
		
	}


public Timesheet createOne(Employee user,int weekNumber, String exercise) {
	
	Timesheet timesheet = new Timesheet(user,  exercise , weekNumber );
	
	Date firstDate = timesheet.getStartDateOfWeek();
		
	Date secondDate =timesheet.getEndDateOfWeek();

	//Save timesheet to set its id
	timesheet = timesheetDao.saveOne(timesheet);

	//Synchronize the timesheet 	
	synchronize(timesheet, user, firstDate,  secondDate);
	
	return timesheetDao.updateOne(timesheet);
}

private boolean synchronize(Timesheet timesheet,Employee user,Date firstDate,Date secondDate) {
boolean needUpdate =false;

List<Activity> activities = this.getAllScheduledActivitiesForUserInPeriod(user,  firstDate, secondDate);
assert(activities != null);
if( activities == null)return false;
for(Activity activity : activities) {
	if ((activity.getStatus().equals(Activity.STATUS_CLOSED))
			|| (activity.getStatus().equals(Activity.STATUS_CANCELLED))) {
		//SKIP THESE ACTIVITIESS
		}
	else {//OTHERWISWE ADD THEM TO THE TIMESHEET
		if (timesheetRowService.synchronizeRowFromActivity(timesheet,activity))
			needUpdate = true;
	}
		
}

return needUpdate;
}

    /**
     * Returns a Task object identified by given id.
     * 
     * @param id
     * @return Returns a Task object identified by given id.
     */
    public Activity getOne(Long id){
    	return activityDao.getOne(id);
    }
    
    public Activity getOneInterventionDetached(Long id){
    	return activityDao.getOneInterventionDetached(id);
    }
    
    
 
    /**
     * Delete a Task object identified by given id.
     * 
     * @param id
     */
    public void deleteOne(Long id){
    	activityDao.deleteOne(id);
    }
    
    public void deleteOneWorkDoneDayly(Long id){
    	activityDao.deleteOneWorkDoneDayly(id);
    }
    
 
    /**
     * Returns a Task object identified by given id.
     * 
     * @param id
     * @return Returns a Task object identified by given id.
     */
   public Activity saveOne(Activity activity){
	   return activityDao.saveOne(activity);
   }
   
   public Activity getActivity(Employee employee,Mission mission,Task task){
	   return activityDao.getActivity( employee, mission,task);
   }
   
  
    /**
     * Returns a Task object identified by given id.
     * 
     * @param id
     * @return Returns a Task object identified by given id.
     */
   public Activity updateOne(Activity activity){
    	return activityDao.updateOne(activity);
    }
   
   
   /**
    * 
    * @return Mission objects list matching the criterias
    */
   public ActivityView searchActivities(  SearchActivityParam param){
	   List<ActivityData> activities = activityDao.searchActivities(param);
	   
	   List<Option> employeeOptions = userService.getAllEmployeeAsOptions();

	   List<Option> yearOptions = this.getAllExercicesOptions();
	   
	   
	   return new ActivityView(activities,param,yearOptions,employeeOptions);
   }
   
   
   /**
    * @param id
    * @return
    */
   public InterventionDetailsView buildInterventionDetailsView (Long id){
	   try{
		   /*
		   ActivityData interventionData = activityDao.findActivityDataForIdenifier(id);
			Intervention intervention = activityDao.getOneInterventionDetached(id);
			List<Message> messages = activityDao.getMessagesFromParentMission(id);
			List<Document> documents = activityDao.getDocumentsFromParentMission(id) ;
			List<Employee> teamMembersOptions =  activityDao.getTeamMembersForMission(id);
			return new InterventionDetailsView(id,
					 interventionData,  intervention,
					messages,  documents,
					 teamMembersOptions);
					 */
		   return null;
		   
	   }catch(Exception e){
		   logger.error(e.getMessage());
		   return null;
	   }
	    
		
		
	   
   }
   
   
	/**
	 * @param user
	 * @param weekNumber
	 * @param exercise
	 * @return
	 */
	public List<Activity> getAllScheduledActivitiesForUserInPeriod(Employee user,Date firstDate, Date secondDate ){
		return activityDao.getAllScheduledActivitiesForUserInPeriod(user, firstDate, secondDate);
	}



public IActivityDao getActivityDao() {
	return activityDao;
}



public void setActivityDao(IActivityDao activityDao) {
	this.activityDao = activityDao;
}



public IUserService getUserService() {
	return userService;
}



public void setUserService(IUserService userService) {
	this.userService = userService;
}






public ICustomerService getCustomerService() {
	return customerService;
}



public void setCustomerService(ICustomerService customerService) {
	this.customerService = customerService;
}



public IExerciseDao getExerciseDao() {
	return exerciseDao;
}



public void setExerciseDao(IExerciseDao exerciseDao) {
	this.exerciseDao = exerciseDao;
}
   
   
   
   
public  List<Option> getAllExercicesOptions(){
	   
	   List<Exercise> exercises = getExerciseDao().getAll();
	   List<Option> options= new ArrayList<Option>();
	   for(Exercise year : exercises){
			options.add(new Option(Integer.toString(year.getYear()),Integer.toString(year.getYear())));
	   }
	   
	   return options;
}


public ITaskService getTaskService() {
	return taskService;
}

public void setTaskService(ITaskService taskService) {
	this.taskService = taskService;
}

public IRoleService getRoleService() {
	return roleService;
}

public void setRoleService(IRoleService roleService) {
	this.roleService = roleService;
}



public ITimesheetDao getTimesheetDao() {
	return timesheetDao;
}


public void setTimesheetDao(ITimesheetDao timesheetDao) {
	this.timesheetDao = timesheetDao;
}


public ITimesheetRowService getTimesheetRowService() {
	return timesheetRowService;
}


public void setTimesheetRowService(ITimesheetRowService timesheetRowService) {
	this.timesheetRowService = timesheetRowService;
}




	
}
