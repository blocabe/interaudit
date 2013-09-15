package com.interaudit.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.transaction.annotation.Transactional;

import com.interaudit.domain.dao.IExerciseDao;
import com.interaudit.domain.dao.IMissionDao;
import com.interaudit.domain.model.Employee;
import com.interaudit.domain.model.Event;
import com.interaudit.domain.model.EventPlanning;
import com.interaudit.domain.model.Exercise;
import com.interaudit.domain.model.Message;
import com.interaudit.domain.model.Mission;
import com.interaudit.domain.model.MissionMember;
import com.interaudit.domain.model.data.MessageData;
import com.interaudit.domain.model.data.MissionBudgetData;
import com.interaudit.domain.model.data.MissionData;
import com.interaudit.domain.model.data.MissionHeurePresteeData;
import com.interaudit.domain.model.data.Option;
import com.interaudit.domain.model.data.ProfitabilityPerCustomerData;
import com.interaudit.service.IActivityService;
import com.interaudit.service.ICustomerService;
import com.interaudit.service.IEventService;
import com.interaudit.service.IMissionService;
import com.interaudit.service.IRoleService;
import com.interaudit.service.ITaskService;
import com.interaudit.service.IUserService;
import com.interaudit.service.param.SearchMissionParam;
import com.interaudit.service.view.AgendaPlanningView;
import com.interaudit.service.view.AnnualPlanningView;
import com.interaudit.service.view.AnnualTimesheetReportView;
import com.interaudit.service.view.MessagerieView;
import com.interaudit.service.view.MissionDetailsView;
import com.interaudit.service.view.MissionView;

@Transactional
public class MissionService implements IMissionService, ApplicationContextAware {
	//private Log log = LogFactory.getLog(TaskService.class);
    private IMissionDao missionDao;
    private IUserService userService;
    private IRoleService roleService;
    private ITaskService taskService;
    private IExerciseDao exerciseDao;
    private ICustomerService customerService;
    private IEventService eventService;
    private IActivityService activityService;
   // private ITimesheetService timesheetService;
   // private ITimesheetRowDao timesheetRowDao;
    
    private ApplicationContext context;
    
    public ProfitabilityPerCustomerData calculateProfitabilityPerMission(Long idMission){
    	return this.missionDao.calculateProfitabilityPerMission(idMission);
    }
    
    public void setApplicationContext(ApplicationContext applicationContext)  throws BeansException 
    {                context = applicationContext;        }
    
    public Mission markMissionAsClosed(Long missionId){
    	return missionDao.markMissionAsClosed(missionId);
    }
    public List<Option> getTaskOptions(){
    	return taskService.getTaskAsOptions();
    }
	
    public IEventService getEventService() {
		return eventService;
	}

	public void setEventService(IEventService eventService) {
		this.eventService = eventService;
	}
    
    
    public  List<Option> getAllExercicesOptions(){
		   
		   List<Exercise> exercises = getExerciseDao().getAll();
		   List<Option> options= new ArrayList<Option>();
		   for(Exercise year : exercises){
				options.add(new Option(Integer.toString(year.getYear()),Integer.toString(year.getYear())));
		   }
		   
		   return options;
	}   
    
    public int getCountUnvalidateHoursForMission(Long missionId){
		 return eventService.getCountUnvalidateHoursForMission(missionId);
	 }

    

    /**
     * Returns a Task object identified by given id.
     * 
     * @param id
     * @return Returns a Task object identified by given id.
     */
    public Mission getOne(Long id){
    	return missionDao.getOne(id);
    }
    
    public EventPlanning createPlanningEvent(Long userId,int year, Integer weekNumber ){
    	return missionDao.createPlanningEvent( userId, year,weekNumber );
    }
    
    public int addMemberToMission(Long idMission,Long employeId){
    	 return missionDao.addMemberToMission( idMission, employeId);
    	 //return missionDao.updateMissionMemberstring(idMission);
    }
    
    public List<Employee> getMissionMembers(Long idMission){
    	return missionDao.getMissionMembers(idMission);
    }
    
    public int removeMemberToMission(Long idMission,Long employeId){    	      	  
    	  return  missionDao.removeMemberToMission( idMission, employeId);
    	 // return missionDao.updateMissionMemberstring(idMission);
    }
    /**
     * Returns a Mission detailsview objct fro a given identifier object identified by given id.
     * 
     * @param id for a mission
     * @return Returns a MissionDetailsView object identified by given id.
     */
    public MissionDetailsView buildMissionDetailsViewForId(Employee caller ,Long missionId){
    	
    	
    	Mission mission = this.getOneDetached(missionId);
    	
    	if(mission != null){
    		MissionData missionData = missionDao.findMissionDataForIdenifier(mission.getId());
    		List<MissionBudgetData> budgets = missionDao.calculateMissionBudget2(mission.getId());
    		List<MissionBudgetData> budgetNotValidated = missionDao.calculateMissionBudgetForNonValidatedTimeSheet(mission.getId());
    		List<MissionHeurePresteeData> heuresPrestees = null; /*missionDao.calculateMissionHeuresPrestees(mission.getId());*/
    		List<Option>missionOptions = getAllOpenMissionForYearAsOptions( caller, missionData.getYear());
    		
    		RepositoryService repositoryService = (RepositoryService) context.getBean("repositoryService");
    		List<Option> taskOptions = repositoryService.getTaskOptions();// taskService.getTaskAsOptions();
        	List<Option> profilOptions = repositoryService.getPositionsAsOptions();// roleService.getPositionsAsOptions();
    		List<Option> employeeOptions = repositoryService.getEmployeeOptions();//userService.getAllEmployeeAsOptions();
    		List<Option> exercicesOptions = repositoryService.getExercicesOptions();// this.getAllExercicesOptions();
    		List<Option> teamMembers = repositoryService.getEmployeesEmailAsOptions();//userService.getAll();/*missionDao.getTeamMembers(mission.getId());*/
    		ProfitabilityPerCustomerData statistiques = this.calculateProfitabilityPerMission(mission.getId());
    		
    		return new MissionDetailsView( 	missionId,  
    										mission,
    										missionData,
    										taskOptions, 
    										profilOptions,
    										employeeOptions,
    										exercicesOptions,
    										budgets,heuresPrestees,teamMembers,missionOptions,statistiques,budgetNotValidated);
    	}else{
    		return null;
    	}
    }
    
    
    public MissionDetailsView buildMissionDetailsViewForBudgetId(Employee caller ,Long id){
    	Mission mission = missionDao.getOneDetachedFromBudgetId(id);
    	
    	if(mission != null){
    		MissionData missionData = missionDao.findMissionDataForIdenifier(mission.getId());
    		List<MissionBudgetData> budgets = missionDao.calculateMissionBudget2(mission.getId());
    		List<MissionBudgetData> budgetNotValidated = missionDao.calculateMissionBudgetForNonValidatedTimeSheet(mission.getId());
    		List<MissionHeurePresteeData> heuresPrestees = null; /*missionDao.calculateMissionHeuresPrestees(mission.getId());*/
    		List<Option>missionOptions = null;/*getAllMissionForYearAsOptions( caller, missionData.getYear());*/
    		
    		
    		RepositoryService repositoryService = (RepositoryService) context.getBean("repositoryService");
    		List<Option> taskOptions = repositoryService.getTaskOptions();// taskService.getTaskAsOptions();
        	List<Option> profilOptions = repositoryService.getPositionsAsOptions();// roleService.getPositionsAsOptions();
    		List<Option> employeeOptions = repositoryService.getEmployeeOptions();//userService.getAllEmployeeAsOptions();
    		List<Option> exercicesOptions = repositoryService.getExercicesOptions();// this.getAllExercicesOptions();
    		List<Option> teamMembers = repositoryService.getEmployeesEmailAsOptions();//userService.getAll();/*missionDao.getTeamMembers(mission.getId());*/
    		ProfitabilityPerCustomerData statistiques = this.calculateProfitabilityPerMission(mission.getId());
    		
    		
    		return new MissionDetailsView( 	mission.getId(),
    										mission,
    										missionData,
    										taskOptions, 
    										profilOptions,
    										employeeOptions,
    										exercicesOptions,
    										budgets,heuresPrestees,
    										teamMembers,missionOptions,statistiques,budgetNotValidated);
    	}else{
    		return null;
    	}	
    }
    
    public List<MissionBudgetData> calculateMissionBudget(Mission mission){
    	 List<MissionBudgetData> budgets = missionDao.calculateMissionBudget(mission.getId());
    	 return budgets;
    }
    
    public List<MissionHeurePresteeData> calculateMissionHeuresPrestees(Mission mission ){
    	List<MissionHeurePresteeData> heuresPrestees = missionDao.calculateMissionHeuresPrestees(mission.getId());
    	return heuresPrestees;
    }
   
    public long  countMissionBudget(Long missionId){
    	return missionDao.countMissionBudget(missionId);
    }
   
    
    public Event createEvent(Long userId, Long missionId, int year,  int month,
			int dayOfYear, int startHour,  int endHour, String object,  String title){
    	
    	return missionDao.createEvent( userId,  missionId,  year,   month, dayOfYear,  startHour,   endHour,  object,   title);	
    }
    
    public AnnualPlanningView updateAnnualPlanningView(AnnualPlanningView annualPlanningView){
    	return missionDao.updateAnnualPlanningView(annualPlanningView);
    }
    
    public int createListOfPlanningEvents(List<Long> userIds,Long missionId,int year, List<Integer> weekList,String startdate,String enddate,String durationType,float duration,Date expectedStartDate,Date expectedEndDate,double hours){
    	return missionDao.createListOfPlanningEvents( userIds, missionId, year,   weekList, startdate, enddate, durationType, duration , expectedStartDate, expectedEndDate,hours);
    }
    
    public int  deleteListOfPlanningEvents(List<Long> userIds,int year,List<Integer> weekList ){
    	return  missionDao.deleteListOfPlanningEvents( userIds, year,weekList );
    }
    
    public int updatePlanningFromTimeSheets(List<Long> userIds,int year,List<Integer> dayList){
    	return  missionDao.updatePlanningFromTimeSheets( userIds, year, dayList);
    }
    
    public EventPlanning updateEventPlanning(EventPlanning eventPlanning){
    	return missionDao.updateEventPlanning(eventPlanning);
    }
    
    public void  deleteItemEventPlanning(Long identifier){
    	missionDao.deleteItemEventPlanning(identifier);
    }
    
    public void  deleteItemEventPlannings(List<Long> itemIds){
    	missionDao.deleteItemEventPlannings(itemIds);
    }
    
    public Event updateEvent(Long eventId,Long userId, Long missionId, int year,  int month,
			int dayOfYear, int startHour,  int endHour, String object,  String title,Date dateOfEvent){
    	
    	return missionDao.updateEvent( eventId, userId,  missionId,  year,   month,
    			 dayOfYear,  startHour,   endHour,  object,   title,dateOfEvent);
    }
    /*
   
    public void deleleteEvent(Long eventId,int year,int week,Long userId){
    	 Event event = this.eventService.getOneEventDetached(eventId);
    	 if(event != null){
    		 
    		 Timesheet timesheet = getTimesheetService().getOneTimesheetForUser(userId,week,year);
    		 
    		 if(timesheet != null){
    			 
    			   TimesheetRow timesheetRow = null;				   
				   Mission mission = event.getActivity().getMission();
				   Activity activity = event.getActivity();
				   Task task = event.getActivity().getTask();
				   
				   if(mission !=  null ){					   
					 //Chargeable activity
					   if( activity != null ){
						   
						   //Try to find the row for the mission
						   timesheetRow = timesheet.findRowForMissionFromActivity(mission.getId(),activity.getId());
						   
					   }//Non chargeable activity
					   else if(task != null){
						   timesheetRow = timesheet.findRowForMissionFromTask(mission.getId(),task.getId());
					   }					   
				   }
				   
				   //We found a matching row, we must remove the cell corresponding to the event
				   if(timesheetRow != null){
					   Calendar calendar = Calendar.getInstance();
					   calendar.set(Calendar.YEAR,year);
					   calendar.set(Calendar.WEEK_OF_YEAR,week);
					   boolean ret = timesheetRow.updateHoursForDay(event.getDayNumber(calendar), 0.0f);
					   
					   //Update the corresponding cell
					   Float totalHours = timesheetRow.getTotalOfHours();
					   if(totalHours <= 0.0f){
						   timesheet.getRows().remove(timesheetRow);
						   timesheetRowDao.deleteOne(timesheetRow.getId());
					   }else{
						 //Update the timesheet if needed
						   getTimesheetService().updateOne(timesheet);
					   }					   
				   }    			 
    		 }
    		 
    	 }
    	 
    	 //Delete the event
    	 this.eventService.deleteOne(eventId);
    }
    
    */
   public AnnualPlanningView buildAnnualPlanningView(Employee caller,String year,int startMonth,int endMonth,String role,int startWeekNumber,int endWeekNumber){	   
	  return  missionDao.buildAnnualPlanningView( caller, year,startMonth,endMonth,role, startWeekNumber, endWeekNumber);   
   }
   
   
   public AnnualTimesheetReportView buildAnnualTimesheetReportView(String year,int startMonth,int endMonth,int startWeekNumber,int endWeekNumber){
	   return missionDao.buildAnnualTimesheetReportView( year, startMonth, endMonth, startWeekNumber, endWeekNumber);
	}
   
   public EventPlanning getEventPlanningFromIdentifier(Long planningId){
	   return  missionDao.getEventPlanningFromIdentifier(planningId);
   }
   
   public EventPlanning getEventPlanning( int year,int weekNumber,Long employeId){
	   return  missionDao.getEventPlanning(  year, weekNumber, employeId);
   }
   
   public  List<Option> getAllMissionForYearAsOptions(Employee caller, String exercise){
	   
	  // SearchMissionParam param = new SearchMissionParam( 	null,  null,null,null,null,  null,  null,null,  null);
	   SearchMissionParam param = new SearchMissionParam( 	exercise,  null,null,null,null,null,  null,  null,null,  null,null);
	   List<MissionData> missions = missionDao.findMissionsForEmployee(caller,param,true,true);
	   
	   
	   List<Option> options= new ArrayList<Option>();
	   for(MissionData missionData : missions){
			options.add(new Option(Long.toString(missionData.getId()),missionData.getCustomerName()+" ["+missionData.getType()+"]"+" ["+missionData.getYear()+"] [" + missionData.getExercice() + " ]"));
	   }
	   
	   return options;
   } 
   
  
   
public  List<Option> getAllOpenMissionForYearAsOptions(Employee caller, String exercise){
	
	  SearchMissionParam param = new SearchMissionParam(exercise, null,null, null,null,
			Mission.STATUS_PENDING, Mission.STATUS_ONGOING, null,
			null, null,null);
	   
	   //SearchMissionParam param = new SearchMissionParam( 	exercise,  null,null,null,  null,  null,null,  null);
	  // List<MissionData> missions = missionDao.searchMissions(caller,param);
	  List<MissionData> missions = missionDao.searchMissions(null,param,true,true);
	   
		
	   List<Option> options= new ArrayList<Option>();
	   for(MissionData missionData : missions){
			//options.add(new Option(Long.toString(missionData.getId()),missionData.getCustomerName()+" ["+missionData.getType()+"]"));
		   int endIndex = missionData.getCustomerName().length() <= 20 ?missionData.getCustomerName().length(): 20;
		   String temp = missionData.getCustomerName().substring(0,endIndex).toUpperCase();
		   if(missionData.getCustomerName().length() > 20) temp +="...";
		   //options.add(new Option(Long.toString(missionData.getId()),missionData.getCustomerName()+" ["+missionData.getType()+"] ["+missionData.getYear()+"] [" + missionData.getExercice() + " ]"));
			options.add(new Option(Long.toString(missionData.getId()),temp+" ["+missionData.getType()+"] ["+missionData.getYear()+"] [" + missionData.getExercice() + " ]"));
	   }
	   
	   return options;
}   

public List<Option> getAllOpenMissionsWithoutFinalBillForYearAsOptions(String  exercise){
	return missionDao.getAllOpenMissionsWithoutFinalBillForYearAsOptions(exercise);
}
   
   
   
   public AgendaPlanningView buildAgendaPlanningView(Employee caller,String year,String week,String employeeIdentifier){	  	   
	   return missionDao.buildAgendaPlanningView( caller, year, week, employeeIdentifier);	   
   }
    
    /**
     * Returns a detached Mission object identified by given id.
     * 
     * @param id
     * @return Returns a detached Mission object identified by given id.
     */
    public Mission getOneDetached(Long id){
    	return missionDao.getOneDetached(id);
    }
    
    public Mission getOneDetachedForContractAndExercise(Long contractId, String exercise,int year ){
    	return missionDao.getOneDetachedForContractAndExercise( contractId,  exercise , year );
    }
    
    public Mission  getOneDetachedFromReference(String reference){
    	return missionDao.getOneDetachedFromReference(reference);
    }
    
    
    /**
     * Returns a detached Mission object identified by given id.
     * 
     * @param id
     * @return Returns a detached Mission object for an AnnualBudget object identified by its id.
     */
    public Mission getOneDetachedForBudgetIdentifier(Long id){
    	return missionDao.getOneDetachedFromBudgetId(id);
    }
    
 
    /**
     * Delete a Task object identified by given id.
     * 
     * @param id
     */
    public void deleteOne(Long id){
    	missionDao.deleteOne(id);
    }
    
   public void  deleteOneCost(Long id){
    	missionDao.deleteOneCost(id);
    }
   
   public void  deleteOneAlerte(Long id){
   	missionDao.deleteOneAlerte(id);
   }
    
   
    /**
     * Returns a Task object identified by given id.
     * 
     * @param id
     * @return Returns a Task object identified by given id.
     */
   public Mission saveOne(Mission Mission){
	   return missionDao.saveOne(Mission);
   }
   
  
    /**
     * Returns a Task object identified by given id.
     * 
     * @param id
     * @return Returns a Task object identified by given id.
     */
   public Mission updateOne(Mission Mission){
    	return missionDao.updateOne(Mission);
    }
   
   public Message updateOneMessage(Message message){
	   return missionDao.updateOneMessage(message);
   }
   
   
   /**
    * 
    * @return Mission objects list matching the criterias
    */
   public MissionView searchMissions( Employee caller, SearchMissionParam param){
	   
	   List<MissionData> missions = missionDao.searchMissions(caller,param,true,false);
	   
	   RepositoryService repositoryService = (RepositoryService) context.getBean("repositoryService");
	   
	   List<Option> customerOptions = repositoryService.getCustomerOptions();//customerService.getAllCustomerAsOptions();
	   
	   List<Option> managerOptions = repositoryService.getManagerOptions();//userService.getManagersAsOptions();
	   
	   List<Option> employeeOptions = repositoryService.getEmployeeOptions();//userService.getAllEmployeeAsOptions();

	   List<Option> yearOptions = repositoryService.getExercicesOptions();//this.getAllExercicesOptions();
	   
	   List<Option> missionOptions = getAllMissionForYearAsOptions( caller, param.getYear());
	   
	   return new MissionView(missions,param,yearOptions,customerOptions,managerOptions,employeeOptions,caller.getLastName(),missionOptions);
   }
   
   
   public MissionView findMissionsForEmployee(Employee caller,SearchMissionParam param,boolean sortedByName){
	   List<MissionData> missions = missionDao.findMissionsForEmployee( caller, param,sortedByName,false);
	   
	   RepositoryService repositoryService = (RepositoryService) context.getBean("repositoryService");
	   List<Option> customerOptions = repositoryService.getCustomerOptions();//customerService.getAllCustomerAsOptions();
	   
	   List<Option> managerOptions = repositoryService.getManagerOptions();//userService.getManagersAsOptions();
	   
	   List<Option> employeeOptions = repositoryService.getEmployeeOptions();//userService.getAllEmployeeAsOptions();
	   
	   List<Option> missionOptions = getAllMissionForYearAsOptions( caller, param.getYear());

	   //List<Option> yearOptions = this.getAllExercicesOptions();
	   List<Option> yearOptions = missionDao.getAllMissionsExercicesOptions();
	   return new MissionView(missions,param,yearOptions,customerOptions,managerOptions,employeeOptions,caller.getLastName(),missionOptions);
   }
   
   
   

	public IMissionDao getMissionDao() {
		return missionDao;
	}

	public void setMissionDao(IMissionDao missionDao) {
		this.missionDao = missionDao;
	}


	public IUserService getUserService() {
		return userService;
	}


	public void setUserService(IUserService userService) {
		this.userService = userService;
	}


	public IRoleService getRoleService() {
		return roleService;
	}


	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}


	public ITaskService getTaskService() {
		return taskService;
	}


	public void setTaskService(ITaskService taskService) {
		this.taskService = taskService;
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

	public IActivityService getActivityService() {
		return activityService;
	}

	public void setActivityService(IActivityService activityService) {
		this.activityService = activityService;
	}

	public MessagerieView searchMessages(Employee employee,Integer year,Long customerId,boolean received,boolean usePagination ,int firstPos,int LINEPERPAGE){
		 List<Integer> years =  exerciseDao.getExercisesAsInteger();
		 List<MessageData> messages =  missionDao.searchMessages(employee, year, customerId, received, usePagination , firstPos, LINEPERPAGE);
		 
		 List<Option>missionOptions = getAllOpenMissionForYearAsOptions( employee, Integer.toString(year)); 		
 		 List<Employee> teamMembers = userService.getAll();
		 return new MessagerieView( year,customerId,messages,years,teamMembers,missionOptions);		
	}
	
	public Message getMessageOne(Long id){
		return missionDao.getMessageOne(id);
	}
    public  Message  getOneMessageDetached(Long id){
    	return missionDao.getOneMessageDetached(id);
    }
	
	
    
   
    
    
	
}
