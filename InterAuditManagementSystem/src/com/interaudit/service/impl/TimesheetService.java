package com.interaudit.service.impl;

import java.io.OutputStream;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.interaudit.domain.dao.ITimesheetCellDao;
import com.interaudit.domain.dao.ITimesheetDao;
import com.interaudit.domain.dao.ITimesheetRowDao;
import com.interaudit.domain.dao.exc.DaoException;
import com.interaudit.domain.model.Activity;
import com.interaudit.domain.model.Employee;
import com.interaudit.domain.model.Event;
import com.interaudit.domain.model.Mission;
import com.interaudit.domain.model.Task;
import com.interaudit.domain.model.Timesheet;
import com.interaudit.domain.model.TimesheetCell;
import com.interaudit.domain.model.TimesheetRow;
import com.interaudit.domain.model.data.Option;
import com.interaudit.domain.model.data.TimesheetData;
import com.interaudit.domain.model.data.TimesheetOption;
import com.interaudit.service.IActivityService;
import com.interaudit.service.IEventService;
import com.interaudit.service.IMissionService;
import com.interaudit.service.ITaskService;
import com.interaudit.service.ITimesheetRowService;
import com.interaudit.service.ITimesheetService;
import com.interaudit.service.IUserService;
import com.interaudit.service.jobs.EwsManager;
import com.interaudit.service.param.SearchTimesheetParam;
import com.interaudit.service.view.ListTimesheetsView;
import com.interaudit.service.view.TimesheetRowView;
import com.interaudit.service.view.TimesheetView;
import com.interaudit.service.view.WeeklyTimeSheetView;
import com.interaudit.util.Constants;
import com.interaudit.util.DateUtils;

//All methods of this class take place in a transaction
@Transactional
public class TimesheetService implements ITimesheetService {

	
	private ITimesheetDao timesheetDao;
	private ITimesheetRowDao timesheetRowDao;
	private ITimesheetCellDao timesheetCellDao;
	//private IProjectDao projectDao;
	private ITimesheetRowService timesheetRowService;
	private IActivityService activityService;
	private IUserService userService;
	private IEventService eventService;
	private ITaskService taskService;
	private IMissionService missionService;
	private RepositoryService repositoryService;
	private EwsManager ewsManager;
	
	
	
	// Pure Business services
	private static long DAY_MILLIS = 1000*60*60*24;
	private static long MONTH_MILLIS = 31 * DAY_MILLIS;
	private static long PAST_MONTHS_LIMIT = 6;
	private static long FUTUR_MONTHS_LIMIT = 2;
	
	public void generateRawDataCsvFile(Integer exerciseNumber,String status,OutputStream os){
		timesheetDao.generateRawDataCsvFile( exerciseNumber, status, os);
	}
	
	
	public void sendTimesheetReminderEmails(){
		ewsManager.processTimesheetToRemindsJob();
		//ewsManager.processEmailsReminderForMissionsAlertes();
	}
	
	public void sendInvoicesReminderEmails(){
		ewsManager.processEmailsForInvoiceReminderJob();
	}
	
	public int getCountTimesheetToValidateForProject(Long projectId){
		return timesheetRowDao.getCountTimesheetToValidateForProject(projectId);
	}

	public List<TimesheetData> getAllTimesheetToValidateForProject(Long projectId){
		return timesheetRowDao.getAllTimesheetToValidateForProject(projectId);
	}
	
			
 public ListTimesheetsView buildTimeSheetListView(SearchTimesheetParam param){
		
	 	int week = param.getWeek();
	 	int year = Integer.parseInt(param.getYear());
	 	/*
	    List<Employee> employees = userService.getAll();
	    for(Employee employee : employees){
	    	if(employee.isUserActive()){
	    		Timesheet timesheet = timesheetDao.findOneTimesheetsForUserId(employee.getId(), week, year);
	    		
		    	if(timesheet == null){
		    		//buildWeeklyTimeSheetViewFromAgenda(employee,employee.getId(), week, year);
		    		timesheet = new Timesheet( employee, Integer.toString(year) ,week);
		    		this.saveOne(timesheet);
		    	}
		    	
	    	}
	    }
	    */
	 
	 	List<TimesheetData> timesheets = timesheetDao.searchTimesheets(param);
	 	List<Option> yearOptions = repositoryService.getYearOptions();//activityService.getAllExercicesOptions();
		List<Option> employeeOptions = repositoryService.getEmployeeOptions();//userService.getAllEmployeeAsOptions();
	 
		return  new ListTimesheetsView(
			 	 timesheets,
				 param, 
				yearOptions,
				employeeOptions);
				
	}
 
 
 public ListTimesheetsView searchdMyTimeSheetListView(Long userId,int year,SearchTimesheetParam param){
	 List<TimesheetData> timesheets = timesheetDao.findTimesheetsForUserId( userId, year);
	
	 List<Option> yearOptions = repositoryService.getYearOptions();//activityService.getAllExercicesOptions();
	 List<Option> employeeOptions = repositoryService.getEmployeeOptions();//userService.getAllEmployeeAsOptions();
	 
	 return  new ListTimesheetsView(
			 	 timesheets,
				 param, 
				yearOptions,
				employeeOptions);
 }
	
	
	public WeeklyTimeSheetView synchronizeWeeklyTimeSheetViewFromAgenda(Employee caller,Long employeeIdentifier,int week,int year){
		
		   List<Option> yearOptions = repositoryService.getYearOptions();// activityService.getAllExercicesOptions();
		   List<Option> employeeOptions = repositoryService.getEmployeeOptions();// userService.getAllEmployeeAsOptions();
		   List<Option> missionOptions = missionService.getAllOpenMissionForYearAsOptions( caller, Integer.toString(year));
		   List<Option> taskOptions = repositoryService.getTaskOptions();//taskService.getTaskAsOptions();
		   List<Option> taskOption2s = repositoryService.getTaskOption2s();//taskService.getTaskAsOptions2();
		   List<TimesheetOption> timesheetOptions  = timesheetDao.findTimesheetOptionsForUserId(employeeIdentifier, year);
		   
		   
		   Employee employee = userService.getOneDetached(employeeIdentifier);
		   Timesheet timesheet = null;
		   
		   //Try to find the timesheet first in the databse
		   timesheet = timesheetDao.findOneTimesheetsForUserId(employeeIdentifier, week, year);
		   
		   //Otherwise create a new one
		   if(timesheet == null){
			   timesheet = new Timesheet( employee, Integer.toString(year) ,week);
		   }else{
			   if( (timesheet.getRows() != null) && (timesheet.getRows().size() > 0)){
				   
				   java.util.Iterator<TimesheetRow> iter = timesheet.getRows().iterator();
				   while(iter.hasNext()){
					   TimesheetRow row = iter.next();
					   timesheetRowService.deleteOne(row.getId());
				   }

				   timesheet.getRows().clear();
				   this.updateOne(timesheet);
			   }
			  
		   }
		   
		   assert(timesheet != null);
		  
		  
		   //Calculate the dates
		   Calendar calendar = Calendar.getInstance();
		   calendar.set(Calendar.YEAR,year);
		   calendar.set(Calendar.WEEK_OF_YEAR,week);
		   
		
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
		   //calendar.add(Calendar.DAY_OF_YEAR, 1);
		   Date secondDate = calendar.getTime();
		   
		  //Search all the events for the year and the month
		   List<Event> events = this.eventService.getAllEventsForPeriod( firstDate, secondDate,employee.getId());
		   
		   //Build the time sheet
		   if(events != null && !events.isEmpty()){
			   for(Event event : events ){
				   
				   int dayNumber = event.getDayNumber(calendar);
				   boolean updateNeeded = false;
				   
				   //Find the row to update
				   TimesheetRow timesheetRow = null;		
				   Mission mission = null;
				   Task task = null;
				   Activity activity = event.getActivity();
				   
				   
				   if(activity != null){//Chargeable activity
					   mission = event.getActivity().getMission();
				   }else{//Non chargeable activity
					   task = event.getTask();
				   }
				    
				   //Treat the case of chargeable activities first 
				   if(mission !=  null ){
					   
					   //Chargeable activity
					   if( activity != null ){						   
						   //Try to find the row for the mission
						   timesheetRow = timesheet.findRowForMissionFromActivity(mission.getId(),activity.getId());
						   
					   }//Non chargeable activity
					   else if(task != null){
						   timesheetRow = timesheet.findRowForMissionFromTask(mission.getId(),task.getId());
					   }
					   
					   //Check if a row is found otherwise create a new one
					   if(timesheetRow ==  null){						   						   
						   assert(activity != null);
						   timesheetRow = new TimesheetRow( timesheet, activity,task,mission.getAnnualBudget().getContract().getCustomer().getCompanyName(),
								   activity.getTask().getCodePrestation(),/*timesheet.getExercise()*/mission.getExercise());							   
					   }
					   updateNeeded = true;
					   
				   }else{
					   //Treat the case of a non-chargeable activity
					   timesheetRow = timesheet.findRowForType(event.getType());					   
					   if(timesheetRow ==  null){
						   timesheetRow = new TimesheetRow( timesheet,null,task,event.getType(),task.getCodePrestation(), timesheet.getExercise());							   
					   }
					   updateNeeded = true;
				   }
				   
				  assert(timesheetRow != null);
				  if( updateNeeded == true){
					  float hours = (float) ( (event.getEndHour() - event.getStartHour())*0.25);
					  if(hours > 0){
						  boolean ret = timesheetRow.updateHoursForDay(dayNumber,hours);
						  assert(ret == true);   
					  }
					    
				  }
				   
			   } 
		   }
		   
		   //Persist the timesheet
		   if(timesheet.getId() == null){
			   this.saveOne(timesheet);
		   }else{
			   this.updateOne(timesheet);
		   }
		  
		   
		   //Build the weekly timesheet view
		   return new  WeeklyTimeSheetView(
				   							timesheet,  
				   							Integer.toString(year),
				   							Integer.toString(week),  
				   							employee, 
				   							yearOptions,
				   							employeeOptions,
				   							missionOptions,
				   							taskOptions,taskOption2s,timesheetOptions);
	   }
	
	
	public WeeklyTimeSheetView buildWeeklyTimeSheetViewFromAgenda(Employee caller,Long employeeIdentifier,int week,int year){
		   
		   
		   List<Option> yearOptions = repositoryService.getYearOptions();//activityService.getAllExercicesOptions();
		   List<Option> employeeOptions = repositoryService.getEmployeeOptions();//userService.getAllEmployeeAsOptions();
		   Employee employee = userService.getOneDetached(employeeIdentifier);
		   List<Option> missionOptions = missionService.getAllOpenMissionForYearAsOptions( caller, Integer.toString(year));/*missionService.getAllMissionForYearAsOptions( caller, Integer.toString(year));*/
			   
		   List<Option> taskOptions = repositoryService.getTaskOption3s();//taskService.getTaskAsOptions();
		   List<Option> taskOptions2 = repositoryService.getTaskOption2s();//taskService.getTaskAsOptions2();
		   
		   
		   Timesheet timesheet = null;
		   
		   //Try to find the timesheet first in the databse
		   timesheet = timesheetDao.findOneTimesheetsForUserId(employeeIdentifier, week, year);
		   List<TimesheetOption> timesheetOptions  = timesheetDao.findTimesheetOptionsForUserId(employeeIdentifier, year);
		   
		   int maxWeeksInYear = DateUtils.getNumWeeksForYear(year);
		   System.out.println("maxWeeksInYear : " + maxWeeksInYear);
		   
		   
		  /*
		   int maxSemaine = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);
		   
		   Integer monthValue = Calendar.getInstance().get(Calendar.MONTH);
			 
		   if(maxSemaine == 1 && monthValue== Calendar.DECEMBER){
				maxSemaine = DateUtils.getNumWeeksForYear(year);
		   }
		   */
		   
		   for(int weekNumber =1; weekNumber <=maxWeeksInYear; weekNumber++){
			   
			   boolean found= false;
			   for(TimesheetOption timesheetOption : timesheetOptions){
				   
				   if(timesheetOption.getWeekNumber()==weekNumber){
					   found = true;
					   break;
				   }				   
			   }
			   if(found == false){
				   timesheetOptions.add(new TimesheetOption("DRAFT", weekNumber,null));
			   } 
		   }
		   
		   Collections.sort(timesheetOptions);

		   
		   //Otherwise create a new one
		   if(timesheet == null){
			   timesheet = new Timesheet( employee, Integer.toString(year) ,week);
		   }
		   
		   assert(timesheet != null);		  
		  
		   
		   //Persist the newly created timesheet
		   if(timesheet.getId() == null){
			   this.saveOne(timesheet);
		   }
		  
		   
		   //Build the weekly timesheet view
		   return new  WeeklyTimeSheetView(
				   							timesheet,  
				   							Integer.toString(year),
				   							Integer.toString(week),  
				   							employee, 
				   							yearOptions,
				   							employeeOptions,
				   							missionOptions,
				   							taskOptions,taskOptions2,timesheetOptions);
	   }
	
	
	
	
	
	
	
	public void createMissingTimesheets(){
		
		 
		 Calendar c = Calendar.getInstance(); 
		 Integer currentYear =c.get(Calendar.YEAR);
		
		 c.set(Calendar.YEAR , currentYear);
		 c.set(Calendar.DAY_OF_WEEK , Calendar.MONDAY);
         Integer year = c.get(Calendar.YEAR);
         Integer weekMax = c.get(Calendar.WEEK_OF_YEAR);
	    
		
          List<Employee> allEmployes = userService.getAll();
          
          for(Employee employee : allEmployes){
        	  if(employee.isUserActive()){
        		  
        		  for(int week =1; week<=weekMax; week++){
        			   //Try to find the timesheet first in the databse
    	    	       Timesheet timesheet = timesheetDao.findOneTimesheetsForUserId(employee.getId(), week, year);        		   
    	    		   //Otherwise create a new one
    	    		   if(timesheet == null){	    			  
    	    			   timesheet = new Timesheet( employee, Integer.toString(year) ,week);
    	    			   this.saveOne(timesheet);
    	    		   } 
        		  }
        		  
        		 
	    		   
	    		   
        	  }
	        	  
          }
		  
		   
	}
	
	
	
	
	
	/**
	 * Check if the timesheet for the requested period can be created comparing
	 * to current period
	 * 
	 * The authorized creation scope is set between PAST_MONTHS_LIMIT and FUTUR_MONTH_LIMIT
	 * The calculation is done roughly as an xtrem precision is not needed.   
	 * 
	 */
	public boolean authorizedTimesheetCreation(int month, int year) {
		boolean auth = true;
		Calendar c = Calendar.getInstance();
		long currTime = c.getTimeInMillis();
		c.set(Calendar.MONTH,month-1);
		c.set(Calendar.YEAR,year);
		long requestTime = c.getTimeInMillis();
		
		//Test creation of past timesheets
		if (currTime > requestTime) {
			long diff = currTime - requestTime;
			if(diff > PAST_MONTHS_LIMIT * MONTH_MILLIS)
				auth = false;
		} else {
			//Test creation of futur timesheet
			long diff = requestTime - currTime ;
			if(diff > FUTUR_MONTHS_LIMIT * MONTH_MILLIS)
				auth = false;
		}

		return auth;
	}
	
	
	public Timesheet submitTimesheet(Timesheet t) {
		t.setSubmitDate(new Date());		
		t.setStatus(Constants.TIMESHEET_STATUS_STRING_SUBMITTED);
		t.setUpdateDate(new Date());   
		return updateOne(t);
	}
	
	public Timesheet unsubmitTimesheet(Timesheet t) {		
		t.setStatus(Constants.TIMESHEET_STATUS_STRING_DRAFT);
		t.setUpdateDate(new Date());   
		
		return updateOne(t);
	}
	
	public Timesheet validateTimesheet(Long id) {
		Timesheet t = timesheetDao.getOne(id);
		if (t != null) {	
			   t.setStatus(Constants.TIMESHEET_STATUS_STRING_VALIDATED);
			   t.setUpdateDate(new Date());   
			   t.setValidationDate(new Date());    
			   return updateOne(t); 
			/*
				Long employeeIdentifier = t.getUser().getId();
				int week = t.getWeekNumber();
				int year = Integer.parseInt(t.getExercise());
			
				//Calculate the dates
			   Calendar calendar = Calendar.getInstance();
			   calendar.set(Calendar.YEAR,year);
			   calendar.set(Calendar.WEEK_OF_YEAR,week);
			   
			
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
			   //calendar.add(Calendar.DAY_OF_YEAR, 1);
			   Date secondDate = calendar.getTime();
			   
			  //Search all the events for the year and the month
			   List<Event> events = this.eventService.getAllEventsForPeriod( firstDate, secondDate,employeeIdentifier);
			   
			   if(events != null && events.size() > 0){
				   
				   //Try to mark all the target hours as validated
				   List<Long> resultList = new ArrayList<Long>();
				   for(Event event : events){resultList.add(event.getId());}
				   int resultCount = eventService.markHoursAsValid(resultList);
				   if(resultCount == events.size() ){
					   t.setStatus(Constants.TIMESHEET_STATUS_STRING_VALIDATED);
					   t.setUpdateDate(new Date());   
					   t.setValidationDate(new Date());    
					   return updateOne(t);    
				   }else{
					   return null;
				   }
				   
			   }
			   
			  */ 
		}
		return null;
	}
	
	public Timesheet rejectTimesheet(Long id) {
		Timesheet t = timesheetDao.getOne(id);
		if (t != null) {			
			t.setStatus(Constants.TIMESHEET_STATUS_STRING_DRAFT);
			t.setUpdateDate(new Date()); 
			t.setRejectedDate(new Date());    	
			return updateOne(t);
		}
		return null;
	}
	
	public Timesheet getPreviousTimesheet(Timesheet t) {
		
		int weekNumber = t.getWeekNumber();
		int y = Integer.parseInt(t.getExercise()) ;
		
		if(weekNumber == 1){
			y--;
			Calendar c = Calendar.getInstance();
			c.set(Calendar.YEAR,y);
			weekNumber = c.getActualMaximum(Calendar.WEEK_OF_YEAR);
		}else{
			weekNumber--;
		}		
		return getOneTimesheetForUser(t.getUser().getId(),weekNumber,y);
	}
	
	public TimesheetView getTimesheetView(Timesheet t) {
		TimesheetView view = new TimesheetView(t);
		
		
		
		Calendar c = Calendar.getInstance();
		c.clear();
		int year = Integer.parseInt(t.getExercise());
		
		c.set(Calendar.YEAR, year);
		c.set(Calendar.WEEK_OF_YEAR,t.getWeekNumber());
		c.set(Calendar.DAY_OF_WEEK , Calendar.MONDAY);
		
		int month = c.get(Calendar.MONTH);
		
		Date weekStartDate = c.getTime();
		c.set(Calendar.DAY_OF_WEEK , Calendar.SATURDAY);
		Date weekEndDate = c.getTime();
		
		
		
		//Re-get Timesheet from Dao to have access to its rows and cells
		t = timesheetDao.getOne(t.getId());
		
		//Initialize view rows and cells
		view.setExercise(t.getExercise());
		view.setWeekNumber(String.valueOf(t.getWeekNumber()) );
		view.setTimesheetId(t.getId());
		view.setTimesheetStatus(t.getStatus());
		view.setUserName(t.getUser().getFullName());
		view.setWeekStartDate(weekStartDate);
		view.setWeekEndDate(weekEndDate);
		

		double[] columnTotal = new double[TimesheetRowView.TIMESHEET_ROW_MAX_SIZE];
		for (TimesheetRow row : t.getRows()) {
			
			TimesheetRowView rowView = new TimesheetRowView();
			rowView.setRow(row);   
			
			//Set Row Title
			String title = null/*row.getActivity().getActivityDescription()*/;
			rowView.setTitle(title);
			
			//Set code Prestation
			String codePrestation = null/*row.getActivity().getActivityCodePrestation()*/;
			rowView.setCodePrestation(codePrestation);
			
			//Set the year
			rowView.setYear(t.getExercise());
			
			//Set the month
			rowView.setMonth(String.valueOf(month));
			
			//Only for chargeable activities
			/*if(row.getActivity().getActivityType().equalsIgnoreCase("CHARGEABLE")){
				
				Activity activity = row.getActivity();
				Intervention intervention =  (Intervention)activity;
				
				//Set the customer code
				rowView.setCodeCustomer(intervention.getCodeCustomer());
			
				//Set the associate code
				rowView.setCodeAssocie(intervention.getAssocieCode());
				
				//Set the manager code
				rowView.setCodeManager(intervention.getManagerCode());
			 }
			 */
			
			//Set Row Cell Values.
			for (TimesheetCell cell : row.getCells()) {
				rowView.setCellValue(cell.getDayNumber(), cell.getValue());
				//Initialize column total
				columnTotal[cell.getDayNumber()] += cell.getValue();
			}
			
			//Add rowView to timesheetView
			view.addRow(rowView);
		}
		//Sort rows
		Collections.sort(view.getRows());
		
		//Initialize column Total view
		view.initializeColumnTotals(columnTotal);
		
		return view;
	}
	
	
	public Timesheet updateTimesheetFromTimesheetView(TimesheetView view) {
		Timesheet timesheet = timesheetDao.getOne(view.getTimesheetId());
		
		//Clean up deleted rows from timesheet
		for (TimesheetRow row: timesheet.getRows()) {
			//Skip Project rows (cannot be removed)
			boolean delete = true;	
			/*
			for (TimesheetRowView rowView : view.getRows()) {	
				
				if (row.getProject().getId().equals(rowView.getRow().getProject().getId())) {
							delete = false;
							break;
				}	
							
			}
			*/	
			
			if (delete) {
				//Children cells  are removed automatically by the cascade
				timesheetRowDao.deleteOne(row.getId());
			}
		}
		//TimesheetViewManager.getInstance().updateTimesheetFromView(timesheet,view);
		for (TimesheetRowView rowView : view.getRows()) {
			TimesheetRow row = rowView.getRow();
			//Test if the row was created on view only
			if (row.getId() == null) {
				//Attach row to timesheet to be saved
				row.setTimesheet(timesheet);
				timesheet.addRow(row);
				row = timesheetRowDao.saveOne(row);
			}
			//Update cells for the row
			TimesheetCell[] cells = new TimesheetCell[TimesheetRowView.TIMESHEET_ROW_MAX_SIZE];
			for(TimesheetCell cell : row.getCells()) {
				cells[cell.getDayNumber()] = cell;
			}
			
			for (int i = 0 ; i < view.getNumberOfDays() ; i++) {
				if (rowView.cellNeedUpdate(i)) {
					if (cells[i] == null) {
						//Create a new cell
						TimesheetCell c = new TimesheetCell();
						c.setDayNumber(i);
						c.setRow(row);
						row.addCell(c);
						cells[i] = timesheetCellDao.saveOne(c);
					}
					//update cell
					cells[i].setValue(rowView.getCellValue(i));
					timesheetCellDao.updateOne(cells[i]);
				}
			}
			//row.setTotalOfHours(new Float(rowView.getHoursTotal()));
			timesheetRowDao.updateOne(row);
		}
		return updateOne(timesheet);
	}
	
	
	
	

	// Services
	public void deleteOne(Long id) {
		timesheetDao.deleteOne(id);
	}

	public List<Timesheet> getAll() {
		return timesheetDao.getAll();
	}

	public Timesheet getOne(Long id) {
		return timesheetDao.getOne(id);
	}

	public Timesheet saveOne(Timesheet timesheet) {
		return timesheetDao.saveOne(timesheet);
	}

	public Timesheet updateOne(Timesheet timesheet) {
		timesheet.setUpdateDate(new Date());
		return timesheetDao.updateOne(timesheet);
	}

	public List<String> getAllDistinctTimesheetStatuses() {
		return timesheetDao.getAllDistinctTimesheetStatuses();
	}

	public Timesheet getOneTimesheetForUser(Long userId, int week, int year) {
		return timesheetDao.findOneTimesheetsForUserId(userId,week, year);
	}
	
	
	
	/**
	 * @param timesheet
	 * @param weekNumber
	 * @param exercise
	 */
	public void addScheduledProjectForWeek(Timesheet timesheet,Date firstDate,Date secondDate){
		Employee user = timesheet.getUser();

		List<Activity> activities = activityService.getAllScheduledActivitiesForUserInPeriod(user, firstDate, secondDate);
		
		for(Activity mission : activities){
			timesheetRowService.synchronizeRowFromActivity(timesheet,mission);
		}
	}
	
	/**
	 * @param timesheet
	 * @param project
	 */
	public void addActivityToTimesheet(Timesheet timesheet, Activity activity){
		timesheetRowService.synchronizeRowFromActivity( timesheet,  activity);
	}
	
	
	
	public void addRowToTimesheet(Long  timesheetId,Long missionId, Map<Integer,Float>  hours ,String codePrestation/*,String year*/){
	
		Timesheet timesheet = timesheetDao.getOne(timesheetId);
		//Otherwise create a new chargeable or non-chargeable event
		Activity activity  = null;
		Task task = null;
		String year = null;
		
		//We try to determine if it is a chargeable activity or not
		Mission mission = missionService.getOne(missionId);
        if (mission == null) {
        	task = taskService.getOne(missionId);
        	if(task == null){
        		throw new DaoException(2);
        	}
        	
        	//Prendre l'année courante pour les activités non-chargeables
			year = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
        		
        }
        else{
        	 //Load the activity by defaulft	        
	        Object[] activities = mission.getInterventions().toArray();
	        if(activities.length == 0){
	        	 throw new DaoException(2);
	        }
	        
	        Activity activity2  = (Activity)activities[0];
	        if (activity2 == null) {
	            throw new DaoException(2);
	        }
	        else{
	        	activity = activityService.getOne(activity2.getId());
	        }
	        
	      //Prendre l'année de mandat de la mission pour les activités chargeables
	        year = mission.getExercise();
        }
        
        TimesheetRow row = null;
        
        if(activity != null){
        	 row = new TimesheetRow( timesheet, activity,null,activity.getMission().getAnnualBudget().getContract().getCustomer().getCompanyName(),
        			 (codePrestation ==null || codePrestation.length()==0)?activity.getTask().getCodePrestation() : codePrestation, year);
        }
        else{
        	Task task2 = null;
        	assert(task != null);
        	String label = null;
        	try{
        		if (codePrestation !=null && codePrestation.length()!=0){
        			task2 = taskService.getTaskByCodePrestation(codePrestation.trim());
        			if(task2 != null){
        				label = task2.getDescription();
        			}
        			else{
        				label= task.getDescription();
        			}        			
        		}
        		else{
    				label= task.getDescription();
    			}
        		  
        	}
        	catch (Exception e){
        		
        	}
        	
        	 row = new TimesheetRow( timesheet, null,task2,label,(codePrestation ==null || codePrestation.length()==0)?task.getCodePrestation() : codePrestation, year);
        }
        
        //Add the hours to the row
        for(Map.Entry<Integer, Float> entry: hours.entrySet()){
        	if(entry.getValue() > 0){
	        	boolean ret = row.updateHoursForDay(entry.getKey(),entry.getValue());
	    		assert(ret == true);
        	}
        }
        
       
		
		//Update the timesheet
		timesheet.addRow(row);
		timesheetRowDao.saveOne(row);
		
	}
	
	
	/**
	 * @param timesheet
	 * @param project
	 */
	public void removeActivityFromTimesheet(Timesheet timesheet, Activity project){
		timesheetRowService.removeActivityFromTimesheet( timesheet,  project);
	}
	
	public void removeRowFromTimesheet(Long idRow){
		timesheetRowDao.deleteOne(idRow);
	}
	
	public void copyRowFromTimesheet(Long idRow){
		timesheetRowDao.copyRowFromTimesheet(idRow);
	}
	
	/*
	private boolean addFavorites(Timesheet timesheet) {
		boolean needUpdate =false;
		
		TimesheetFavorite fav = timesheetFavoriteService.getOneTimesheetFavoriteForUser(timesheet.getUser().getId());
		if (fav != null && fav.getRows().size() != 0) {
			for(TimesheetRowFavorite rowFav :  fav.getRows()) {
				TimesheetRow row = new TimesheetRow();
				TimesheetRowType type = rowFav.getType();
				row.setType(type);
				row.setTimesheet(timesheet);
				timesheet.addRow(row);
				if (type.getId().equals(Constants.TIMESHEET_ROW_TYPE_ID_ACTION_PLAN)) {
					row.setActionPlan(actionPlanService.getOne(rowFav.getActionPlan().getId()));
				} else if (type.getId().equals(Constants.TIMESHEET_ROW_TYPE_ID_PROJECT)) {
					row.setProject(projectService.getOne(rowFav.getProject().getId()));
				}
				timesheetRowDao.saveOne(row);
			}
			needUpdate =true;
		}
		
		return needUpdate;
	}
	*/
	
	public void deleleteEvent(Long eventId,int year,int week,Long userId){
   	 Event event = this.eventService.getOneEventDetached(eventId);
   	 if(event != null){
   		 
   		 Timesheet timesheet = this.getOneTimesheetForUser(userId,week,year);
   		 
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
					   Double totalHours = timesheetRow.getTotalOfHours();
					   if(totalHours <= 0.0f){
						   timesheet.getRows().remove(timesheetRow);
						   timesheetRowDao.deleteOne(timesheetRow.getId());
					   }else{
						 //Update the timesheet if needed
						   this.updateOne(timesheet);
					   }					   
				   }    			 
   		 }
   		 
   	 }
   	 
   	 //Delete the event
   	 this.eventService.deleteOne(eventId);
   }
	
	public List<Timesheet> findTimesheets(SearchTimesheetParam searchTimesheetParam) {
		return timesheetDao.findTimesheets(searchTimesheetParam);
    }
	
	
	public List<TimesheetData> searchTimesheets(SearchTimesheetParam searchTimesheetParam){
		return timesheetDao.searchTimesheets(searchTimesheetParam);
	}
	
	// Inject dao layer
	public ITimesheetDao getTimesheetDao() {
		return timesheetDao;
	}

	public void setTimesheetDao(ITimesheetDao timesheetDao) {
		this.timesheetDao = timesheetDao;
	}
	public ITimesheetRowDao getTimesheetRowDao() {
		return timesheetRowDao;
	}

	public void setTimesheetRowDao(ITimesheetRowDao timesheetRowDao) {
		this.timesheetRowDao = timesheetRowDao;
	}
	public ITimesheetCellDao getTimesheetCellDao() {
		return timesheetCellDao;
	}

	public void setTimesheetCellDao(ITimesheetCellDao timesheetCellDao) {
		this.timesheetCellDao = timesheetCellDao;
	}
	
	public ITimesheetRowService getTimesheetRowService() {
		return timesheetRowService;
	}

	public void setTimesheetRowService(ITimesheetRowService timesheetRowService) {
		this.timesheetRowService = timesheetRowService;
	}

	public IActivityService getActivityService() {
		return activityService;
	}


	public void setActivityService(IActivityService activityService) {
		this.activityService = activityService;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	public IEventService getEventService() {
		return eventService;
	}

	public void setEventService(IEventService eventService) {
		this.eventService = eventService;
	}


	public ITaskService getTaskService() {
		return taskService;
	}


	public void setTaskService(ITaskService taskService) {
		this.taskService = taskService;
	}


	public IMissionService getMissionService() {
		return missionService;
	}


	public void setMissionService(IMissionService missionService) {
		this.missionService = missionService;
	}


	public RepositoryService getRepositoryService() {
		return repositoryService;
	}


	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	public EwsManager getEwsManager() {
		return ewsManager;
	}

	public void setEwsManager(EwsManager ewsManager) {
		this.ewsManager = ewsManager;
	}   

	
	
	
}
