package com.interaudit.web.controllers;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.interaudit.domain.model.Activity;
import com.interaudit.domain.model.Comment;
import com.interaudit.domain.model.EmailData;
import com.interaudit.domain.model.Employee;
import com.interaudit.domain.model.EventPlanning;
import com.interaudit.domain.model.ItemEventPlanning;
import com.interaudit.domain.model.Timesheet;
import com.interaudit.domain.model.TimesheetCell;
import com.interaudit.domain.model.TimesheetRow;
import com.interaudit.domain.model.data.Option;
import com.interaudit.domain.model.data.TimesheetDataForPlanning;
import com.interaudit.domain.model.data.TimesheetOption;
import com.interaudit.service.IEmailService;
import com.interaudit.service.IExerciseService;
import com.interaudit.service.IMissionService;
import com.interaudit.service.ITaskService;
import com.interaudit.service.ITimesheetService;
import com.interaudit.service.param.SearchTimesheetParam;
import com.interaudit.service.view.ListTimesheetsView;
import com.interaudit.service.view.WeeklyTimeSheetView;
import com.interaudit.util.DateUtils;
import com.interaudit.util.WebContext;



/**
 * The Welcome action.
 * 
 * @author Julien Dubois
 */
public class TimesheetController extends MultiActionController {

   // private final Log log = LogFactory.getLog(HandleBrowseDatasetPageController.class);
	private ITimesheetService timesheetService;
	private IEmailService emailService;
	private IMissionService missionService;
	private IExerciseService exerciseService;
	private ITaskService taskService;
	private boolean checkHoursFlag = false;
    
	private static final String TIMESHEET_DETAILS_KEY = "viewTimesheetPerEmployee";
	private static final String TIMESHEET_LIST_KEY = "viewTimesheetList";
	public static final String TIMESHEET_STATUS_STRING_DRAFT = "DRAFT";
    public static final String TIMESHEET_STATUS_STRING_SUBMITTED = "SUBMITTED";
    public static final String TIMESHEET_STATUS_STRING_VALIDATED = "VALIDATED";
    private static final Logger  LOGGER  = Logger.getLogger(TimesheetController.class);
    
    
    
    public ModelAndView downLoadCvsFileData(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		
        	try{
        		  
        		String year = request.getParameter("year");
        		response.setHeader("Content-Disposition", "attachment; filename=\"time_report.cvs" + "\";");

                response.setContentType("application/vnd.ms-excel");

                OutputStream os = response.getOutputStream();
                Integer exerciseNumber = Calendar.getInstance().get(Calendar.YEAR);
                if(year != null){
                	exerciseNumber = Integer.parseInt(year);
                }
                
                String status = "VALIDATED";
               
        		
                timesheetService.generateRawDataCsvFile( exerciseNumber, status, os);
                                                                              
            	return null;
        		
        	}catch(Exception e){
        		logger.error(e);
        		return null;
        	}
        	
        
		
	}
    
  
    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView showTimesheetsPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
    	Map<String,Object> mapResults = new HashMap<String, Object>();
    	
    	
    	
    	String draftKey = request.getParameter(TIMESHEET_STATUS_STRING_DRAFT);
		String submittedKey = request.getParameter(TIMESHEET_STATUS_STRING_SUBMITTED);
		String validatedKey = request.getParameter(TIMESHEET_STATUS_STRING_VALIDATED);
		
		String year = request.getParameter("year");
		String week = request.getParameter("week");
		String employeeIdentifier = request.getParameter("employeeId");
		
		String type = request.getParameter("type");
		
		boolean personal = (type != null) && (type.equalsIgnoreCase("personal"));
		
		
		
		//Defaults to current year
		if(year == null){
			Calendar c = Calendar.getInstance(); 
			Integer currentYear = exerciseService.getFirstOnGoingExercise();
			if(currentYear == null){
								//currentYear = exerciseService.getMaxYear();
								 currentYear =  exerciseService.getLastClosedExercise();
								 if(currentYear == null){
									
									currentYear =c.get(Calendar.YEAR);
								 } 
							 }
			 c.set(Calendar.YEAR , currentYear);
			 c.set(Calendar.DAY_OF_WEEK , Calendar.MONDAY);
	         Integer value = c.get(Calendar.YEAR);
	         year = value.toString();
	         value = c.get(Calendar.WEEK_OF_YEAR);
 	         week = value.toString();
		}
		
		if(year != null && week == null){
			Calendar c = Calendar.getInstance();
			 c.set(Calendar.YEAR , Integer.parseInt(year));
			 c.set(Calendar.DAY_OF_WEEK , Calendar.MONDAY);
			 Integer value = c.get(Calendar.WEEK_OF_YEAR);
	         week = value.toString();
		}
		
		if(employeeIdentifier == null || employeeIdentifier.equalsIgnoreCase("-1")){
			employeeIdentifier = null;
		}
		

		ListTimesheetsView view = null;
		SearchTimesheetParam param = new SearchTimesheetParam( year, Integer.parseInt(week) , employeeIdentifier == null?null:Long.parseLong(employeeIdentifier) ,
				 draftKey,  submittedKey,  validatedKey);
		
		if(personal == true ){
			WebContext context =  (WebContext) request.getSession(false).getAttribute("context");
			view = getTimesheetService().searchdMyTimeSheetListView(context.getCurrentUser().getId(), Integer.parseInt(year),param);
		}
		else if( week.equalsIgnoreCase("-1") && employeeIdentifier != null){
			view = getTimesheetService().searchdMyTimeSheetListView(employeeIdentifier == null?null:Long.parseLong(employeeIdentifier), Integer.parseInt(year),param);
		}
		else{
			
			if( week.equalsIgnoreCase("-1")){						 
				week = Integer.toString(Calendar.getInstance().get(Calendar.WEEK_OF_YEAR));	
				param.setWeek(Integer.parseInt(week));
			}
			
			view = getTimesheetService().buildTimeSheetListView(param);			
		}

		
		mapResults.put(TIMESHEET_LIST_KEY, view);
		
		
		return new ModelAndView("list_activity_reports",mapResults);		
    	
	
	}
    
    
    
    public ModelAndView showDownLoadCvsFileData(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	Map<String,Object> mapResults = new HashMap<String, Object>();
    	
		List<Option> yearOptions = getTimesheetService().getRepositoryService().getYearOptions();
		mapResults.put("yearOptions", yearOptions);
		return new ModelAndView("list_activity_downloads_reports",mapResults);
    }
		
    	
    
    
    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView showTimesheetPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	
    	Map<String,Object> mapResults = new HashMap<String, Object>();
    	
    	
    	WebContext context =  (WebContext) request.getSession(false).getAttribute("context");
		
		String year = request.getParameter("year");
		String week = request.getParameter("week");
		String employeeIdentifier = request.getParameter("employeeId");
		
		
		//Defaults to current year
		if(year == null){
			
			Integer currentYear = exerciseService.getFirstOnGoingExercise();
			if(currentYear == null){
								//currentYear = exerciseService.getMaxYear();
								 currentYear =  exerciseService.getLastClosedExercise();
								 if(currentYear == null){
									Calendar c = Calendar.getInstance(); 
									currentYear =c.get(Calendar.YEAR);
								 } 
							 }
			 Calendar c = Calendar.getInstance();
			 c.set(Calendar.YEAR , currentYear);			
			 c.set(Calendar.DAY_OF_WEEK , Calendar.MONDAY);
	         Integer value = c.get(Calendar.YEAR);
	         year = value.toString();
	         value = c.get(Calendar.WEEK_OF_YEAR);
	         
	         Integer monthValue = c.get(Calendar.MONTH);			 
			 if(value == 1 && monthValue== Calendar.DECEMBER){
				 value = DateUtils.getNumWeeksForYear(Integer.parseInt(year));
			 }
	         week = value.toString(); 	        
		}
		
		
		if(year != null && week == null){
			Calendar c = Calendar.getInstance();
			 c.set(Calendar.YEAR , Integer.parseInt(year));
			 c.set(Calendar.DAY_OF_WEEK , Calendar.MONDAY);
			 Integer value = c.get(Calendar.WEEK_OF_YEAR);
			 Integer monthValue = c.get(Calendar.MONTH);
			 
			 if(value == 1 && monthValue== Calendar.DECEMBER){
				 value = DateUtils.getNumWeeksForYear(Integer.parseInt(year));
			 }
	         week = value.toString();
		}
		
		
		
		if(employeeIdentifier == null ){			
			// get the context object			
			employeeIdentifier = context.getCurrentUser().getId().toString();
		}
		
		WeeklyTimeSheetView view = getTimesheetService().buildWeeklyTimeSheetViewFromAgenda(context.getCurrentUser(),Long.parseLong(employeeIdentifier),
				Integer.parseInt(week), Integer.parseInt(year));
				
		mapResults.put(TIMESHEET_DETAILS_KEY, view);
		request.getSession(false).setAttribute(TIMESHEET_DETAILS_KEY, view);
		
		return new ModelAndView("activity_report_workshop",mapResults);		
	}
    
    
    public ModelAndView editTimesheetActivitiesAsAjaxStream(HttpServletRequest request,
			HttpServletResponse response) throws Exception { 
    	    	
    	JSONObject ajaxReply = new JSONObject();
    	JSONArray eventList = null;
    	eventList =  (JSONArray)request.getSession(false).getAttribute("ActivitiesAsAjaxStream");
    	if(eventList == null){
    		
        	eventList = new JSONArray();
        	JSONObject ajaxObject = null;
        	WeeklyTimeSheetView view = (WeeklyTimeSheetView)request.getSession(false).getAttribute(TIMESHEET_DETAILS_KEY);
        	
    		if(view == null)
    		{	
    			WebContext context =  (WebContext) request.getSession(false).getAttribute("context");
    			Calendar c = Calendar.getInstance();
    			 //Integer currentYear = c.get(Calendar.YEAR);
    			Integer currentYear = exerciseService.getFirstOnGoingExercise();
    			if(currentYear == null){
				    				//currentYear = exerciseService.getMaxYear();
					 				 currentYear =  exerciseService.getLastClosedExercise();
    								 if(currentYear == null){    									
    									currentYear =c.get(Calendar.YEAR);
    								 } 
    							 }	
    			 String week = Integer.toString(c.get(Calendar.WEEK_OF_YEAR));
    			 String employeeIdentifier = context.getCurrentUser().getId().toString();
    			
    			view = getTimesheetService().buildWeeklyTimeSheetViewFromAgenda(context.getCurrentUser(),Long.parseLong(employeeIdentifier),
    					Integer.parseInt(week), currentYear);							
    			request.getSession(false).setAttribute(TIMESHEET_DETAILS_KEY, view);			
    		}
    		
    		
    			for(Option option : view.getTaskOptions()){
        			ajaxObject = new JSONObject();
            		ajaxObject.put("name", option.getName().toLowerCase());
            		ajaxObject.put("id", option.getId());
            		eventList.put(ajaxObject);
        		}
    		
    		
    		
	    		for(Option option : view.getMissionOptions()){
	    			ajaxObject = new JSONObject();
	        		ajaxObject.put("name", option.getName().toLowerCase());
	        		ajaxObject.put("id", option.getId());
	        		eventList.put(ajaxObject);
	    		}
    		
    		
	    		request.getSession(false).setAttribute("ActivitiesAsAjaxStream", eventList);
    		
    	}
    	
		ajaxReply.put("events", eventList);
    	response.setContentType("text/json;charset=UTF-8");
    	ServletOutputStream out = response.getOutputStream();
		
    	String reply = ajaxReply.toString();
		out.write(reply.getBytes("UTF-8"));
		out.flush();
		out.close();
		return null;
    }
    
    
    public ModelAndView editTimesheetCodePrestationAsAjaxStream(HttpServletRequest request,
			HttpServletResponse response) throws Exception { 
    	
    	JSONObject ajaxReply = new JSONObject();
    	JSONArray eventList = null;
    	eventList =  (JSONArray)request.getSession(false).getAttribute("CodePrestationAsAjaxStream");
    	if(eventList == null){
    		
    		eventList = new JSONArray();
        	JSONObject ajaxObject = null;
        	WeeklyTimeSheetView view = (WeeklyTimeSheetView)request.getSession(false).getAttribute(TIMESHEET_DETAILS_KEY);
        	
    		if(view == null)
    		{	
    			WebContext context =  (WebContext) request.getSession(false).getAttribute("context");
    			Calendar c = Calendar.getInstance();
    			 Integer currentYear = c.get(Calendar.YEAR);
    			 String week = Integer.toString(c.get(Calendar.WEEK_OF_YEAR));
    			 String employeeIdentifier = context.getCurrentUser().getId().toString();
    			
    			view = getTimesheetService().buildWeeklyTimeSheetViewFromAgenda(context.getCurrentUser(),Long.parseLong(employeeIdentifier),
    					Integer.parseInt(week), currentYear);
    					
    		
    			request.getSession(false).setAttribute(TIMESHEET_DETAILS_KEY, view);			
    		}	
    		
    		for(Option option : view.getTaskOptions2()){
    			ajaxObject = new JSONObject();
        		ajaxObject.put("name", option.getName().toLowerCase());
        		ajaxObject.put("id", option.getId());
        		eventList.put(ajaxObject);
    		}
    		
    		
    		request.getSession(false).setAttribute("CodePrestationAsAjaxStream", eventList);
    		
    	}
    	
    	
		
		ajaxReply.put("events", eventList);
    	response.setContentType("text/json;charset=UTF-8");
    	ServletOutputStream out = response.getOutputStream();
		
    	String reply = ajaxReply.toString();
		out.write(reply.getBytes("UTF-8"));
		out.flush();
		out.close();
    	return null;
    }
    
    
    public ModelAndView editTimesheetRowItem(HttpServletRequest request,
			HttpServletResponse response) throws Exception { 
    	
    	String rowId = request.getParameter("rowId");
    	JSONObject ajaxReply = new JSONObject();
    	
		if (rowId != null){	
			
			    WeeklyTimeSheetView view = (WeeklyTimeSheetView)request.getSession(false).getAttribute(TIMESHEET_DETAILS_KEY);
				if(view == null)
				{			
					ajaxReply.put("result", "nok");
					response.setContentType("application/json;charset=UTF-8");
				}	
				
				TimesheetRow rowTofind = null;
				for(TimesheetRow row : view.getTimesheet().getRows()){
					if(row.getId().toString().equalsIgnoreCase(rowId)){
						rowTofind = row;
						break;
					}
				}
				
				
				
				
				if( rowTofind != null){
					ajaxReply.put("result", "ok");
					ajaxReply.put("rowId",rowTofind.getId().toString());
					
					String missionId = null;
					String missionName = null;
					String codePrestationName = null;
					Activity activity = rowTofind.getActivity();
					if(activity != null){
						missionId = activity.getMission().getId().toString();	
						missionName = activity.getMission().getComment() + " [ " +activity.getMission().getExercise() + " ] " ;
					}
					else{						
						missionId = rowTofind.getTaskIdentifier();
						missionName = taskService.getOne(Long.parseLong(rowTofind.getTaskIdentifier())).getName();
					}
					ajaxReply.put("missionId",missionId);
					ajaxReply.put("missionName",missionName);
					
					// ajaxReply.put("year",rowTofind.getYear());
					ajaxReply.put("codePrestation",rowTofind.getCodePrestation());
					
				//	codePrestationName = taskService.getOne(Long.parseLong(rowTofind.getCodePrestation())).getName();
					codePrestationName = taskService.getTaskByCodePrestation(rowTofind.getCodePrestation()).getName();
					ajaxReply.put("codePrestationName",codePrestationName);
					
					Map<String,TimesheetCell> cellsMap = rowTofind.getCellsMap();
					
					TimesheetCell cell = cellsMap.get("1");
					Double value =  cell == null?0.0d:cell.getValue()*4;
					ajaxReply.put("monday", Long.toString(Math.round(value)));
					
					
					cell = cellsMap.get("2");
					value =  cell == null?0:cell.getValue()*4;
					ajaxReply.put("tuesday",Long.toString(Math.round(value)));
					
					cell = cellsMap.get("3");
					value =  cell == null?0:cell.getValue()*4;
					ajaxReply.put("wednesday",Long.toString(Math.round(value)));
					
					cell = cellsMap.get("4");
					value =  cell == null?0:cell.getValue()*4;
					ajaxReply.put("thursday",Long.toString(Math.round(value)));
					
					
					cell = cellsMap.get("5");
					value =  cell == null?0:cell.getValue()*4;
					ajaxReply.put("friday",Long.toString(Math.round(value)));
					
					//no password as it is only sent from client to server
					
					response.setContentType("text/json;charset=UTF-8");
			}else{			
				ajaxReply.put("result", "nok");
			    response.setContentType("application/json;charset=UTF-8");
			}				
			ServletOutputStream out = response.getOutputStream();
			String reply = ajaxReply.toString();	
			out.write(reply.getBytes("UTF-8"));
			out.flush();
			out.close();
			return null;
		}
		else{
			LOGGER.error("budgetId null");
			return null;
		}
	}
    
    public ModelAndView addRowToTimesheet(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	//WebContext context =  (WebContext) request.getSession(false).getAttribute("context");
		
    	ArrayList<String> messages = new ArrayList<String>();
    	WeeklyTimeSheetView view = (WeeklyTimeSheetView)request.getSession(false).getAttribute(TIMESHEET_DETAILS_KEY);
    	
    	
    	String year = view.getYear();
		String week = view.getWeek();
		String employeeIdentifier = Long.toString(view.getEmployee().getId());
		String timesheetIdAsString = Long.toString(view.getTimesheet().getId());
		
		//String dayAsString = request.getParameter("jour");
		//String heuresPresteesAsString = request.getParameter("heuresPrestees");
		String missionIdAsString = request.getParameter("missionId");
		
		String rowIdAsString = request.getParameter("rowId");
		//String yearAsString = request.getParameter("year");
		String codePrestationIdAsString =request.getParameter("codePrestationId");
		String mondayHoursAsString = request.getParameter("monday");
		String tuesdayHoursAsString = request.getParameter("tuesday");
		String wednesdayHoursAsString = request.getParameter("wednesday");
		String thursdayHoursAsString = request.getParameter("thursday");
		String fridayHoursAsString = request.getParameter("friday");
		
		//Integer dayNumber = Integer.parseInt(dayAsString);
		
		//Check that required inputs are entered
		if(missionIdAsString== null || missionIdAsString.trim().length() == 0){
			messages.add("Please select an activity");
		}
		/*
		if(dayNumber < 1){
			messages.add("Please select a date");		
		}
		*/
		if(!messages.isEmpty()){
			//Something went wrong
			request.getSession(false).setAttribute("actionErrors", messages);
			//Redirect to the jsp page					
			return new ModelAndView("redirect:/showTimesheetRegisterPage.htm?year="+year+"&week=" + week+"&employeeId="+employeeIdentifier);
		}
		
		
		//float hours = (float)(Float.parseFloat(heuresPresteesAsString)*0.25);
		Long missionId = Long.parseLong(missionIdAsString);
		Long  timesheetId = Long.parseLong(timesheetIdAsString);
		
		Map<Integer,Float>  mapHours =  new HashMap<Integer,Float>();
		mapHours.put(1, (Float.parseFloat(mondayHoursAsString)*0.25f));
		mapHours.put(2, (Float.parseFloat(tuesdayHoursAsString)*0.25f));
		mapHours.put(3, (Float.parseFloat(wednesdayHoursAsString)*0.25f));
		mapHours.put(4, (Float.parseFloat(thursdayHoursAsString)*0.25f));
		mapHours.put(5, (Float.parseFloat(fridayHoursAsString)*0.25f));
		
		if( rowIdAsString != null  && rowIdAsString.trim().length() > 0 ){
			
			try{
				Long idRow = Long.parseLong(rowIdAsString);
				getTimesheetService().removeRowFromTimesheet(idRow);
			}catch(NumberFormatException nfe){
				
			}
			
		}
		
		
		getTimesheetService().addRowToTimesheet(  timesheetId, missionId, mapHours,codePrestationIdAsString);
		
		
		
		return new ModelAndView("redirect:/showTimesheetRegisterPage.htm?year="+year+"&week=" + week+"&employeeId="+employeeIdentifier);

    }
    
    
    public ModelAndView copyRowToTimesheet(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	String idAsString = request.getParameter("id");
    	WeeklyTimeSheetView view = (WeeklyTimeSheetView)request.getSession(false).getAttribute(TIMESHEET_DETAILS_KEY);
    	
    	String year = view.getYear();
		String week = view.getWeek();
		String employeeIdentifier = Long.toString(view.getEmployee().getId());
		Long idRow = Long.parseLong(idAsString);
		
		getTimesheetService().copyRowFromTimesheet(idRow);
		//String timesheetIdAsString = Long.toString(view.getTimesheet().getId());
		return new ModelAndView("redirect:/showTimesheetRegisterPage.htm?year="+year+"&week=" + week+"&employeeId="+employeeIdentifier);
    }
    
    public ModelAndView removeRowFromTimesheet(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	
    	String idAsString = request.getParameter("id");
    	WeeklyTimeSheetView view = (WeeklyTimeSheetView)request.getSession(false).getAttribute(TIMESHEET_DETAILS_KEY);
    	
    	String year = view.getYear();
		String week = view.getWeek();
		String employeeIdentifier = Long.toString(view.getEmployee().getId());
		Long idRow = Long.parseLong(idAsString);
		getTimesheetService().removeRowFromTimesheet(idRow);
		//String timesheetIdAsString = Long.toString(view.getTimesheet().getId());
		return new ModelAndView("redirect:/showTimesheetRegisterPage.htm?year="+year+"&week=" + week+"&employeeId="+employeeIdentifier);
    }
    
    public ModelAndView synchronizeTimesheetWithAgenda(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	Map<String,Object> mapResults = new HashMap<String, Object>();
    	WebContext context =  (WebContext) request.getSession(false).getAttribute("context");
    	WeeklyTimeSheetView view = (WeeklyTimeSheetView)request.getSession(false).getAttribute(TIMESHEET_DETAILS_KEY);
    	
    	String year = view.getYear();
		String week = view.getWeek();
		String employeeIdentifier = Long.toString(view.getEmployee().getId());
		//String timesheetIdAsString = Long.toString(view.getTimesheet().getId());
		
		
		WeeklyTimeSheetView view2 = getTimesheetService().synchronizeWeeklyTimeSheetViewFromAgenda(context.getCurrentUser(),Long.parseLong(employeeIdentifier),Integer.parseInt(week), Integer.parseInt(year));
		
		mapResults.put(TIMESHEET_DETAILS_KEY, view2);
		request.getSession(false).setAttribute(TIMESHEET_DETAILS_KEY, view2);
		
		return new ModelAndView("activity_report_workshop",mapResults);		
    }
    
    public ModelAndView saveTimesheet(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	
    	WeeklyTimeSheetView view = (WeeklyTimeSheetView)request.getSession(false).getAttribute(TIMESHEET_DETAILS_KEY);
    	
    	Timesheet timesheet = view.getTimesheet();
    	timesheet.setUpdateDate(new Date());
    	timesheet = getTimesheetService().updateOne(timesheet);
    	
    	return new ModelAndView("redirect:/showTimesheetRegisterPage.htm?year="+view.getYear()+"&week=" + view.getWeek()+"&employeeId="+view.getEmployee().getId());

    }
    
    public ModelAndView submitTimesheet(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	WeeklyTimeSheetView view = (WeeklyTimeSheetView)request.getSession(false).getAttribute(TIMESHEET_DETAILS_KEY);
    	Timesheet timesheet = view.getTimesheet();
    	ArrayList<String> messages = new ArrayList<String>();
    	Double totalOfHoursPerWeek  = timesheet.getTotalOfHours();
    	
    	if( (checkHoursFlag == true) && (totalOfHoursPerWeek > 40.0)){
    		 
    		 messages.add("The total of hours exceeds 40 hours for this week...");
    		 request.getSession(false).setAttribute("actionErrors", messages);
    	}
    	else if(totalOfHoursPerWeek == 0.0){
   		 
   		 messages.add("You cannot submit an empty timesheet...");
   		 request.getSession(false).setAttribute("actionErrors", messages);
   	   }
    	else if((checkHoursFlag==true) && (timesheet.checkHoursPerDay() == false)){
    		messages.add("The total of hours per day cannot exceed 8 hours ...");
   		   request.getSession(false).setAttribute("actionErrors", messages);
    	}
    	else{
    		timesheet.setUpdateDate(new Date());
        	timesheet = getTimesheetService().submitTimesheet(timesheet);  
        	messages.add("The timesheet has been successfully submited for this week...");
        	request.getSession(false).setAttribute("successMessage", messages);
    	}
    	
    	/*
    	view.getTimesheet().setStatus(TIMESHEET_STATUS_STRING_SUBMITTED);
		Map<String,Object> mapResults = new HashMap<String, Object>();
    	mapResults.put(TIMESHEET_DETAILS_KEY, view);
		request.getSession(false).setAttribute(TIMESHEET_DETAILS_KEY, view);
    	
    	return new ModelAndView("activity_report_workshop",mapResults);
    	*/
    	return new ModelAndView("redirect:/showTimesheetRegisterPage.htm?year="+view.getYear()+"&week=" + view.getWeek()+"&employeeId="+view.getEmployee().getId());
    	
    }
    

    public ModelAndView addComment(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	WebContext context =  (WebContext) request.getSession(false).getAttribute("context");
    	String description = request.getParameter("description");
    	Employee validator = context.getCurrentUser();
    	
    	WeeklyTimeSheetView view = (WeeklyTimeSheetView)request.getSession(false).getAttribute(TIMESHEET_DETAILS_KEY);
    	Timesheet timesheet = getTimesheetService().getOne(view.getTimesheet().getId()); 
    	Comment comment = new Comment(description,validator,validator.getEmail(),timesheet.getUser().getEmail());
    	
    	//In order to avoid this comment to be sent as a rejected timesheet
    	comment.setSent(new Date());
    	
    	comment.setTimesheet(timesheet);
    	timesheet.getComments().add(comment);
    	
    	timesheet = getTimesheetService().updateOne(timesheet);
    	
    	return new ModelAndView("redirect:/showTimesheetRegisterPage.htm?year="+view.getYear()+"&week=" + view.getWeek()+"&employeeId="+view.getEmployee().getId());
    	
    }
    
    public ModelAndView rejectTimesheet(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	WebContext context =  (WebContext) request.getSession(false).getAttribute("context");
    	String description = request.getParameter("description");
    	Employee validator = context.getCurrentUser();
    	
    	WeeklyTimeSheetView view = (WeeklyTimeSheetView)request.getSession(false).getAttribute(TIMESHEET_DETAILS_KEY);
    	Timesheet timesheet = getTimesheetService().getOne(view.getTimesheet().getId()); 
    	Comment comment = new Comment(description,validator,validator.getEmail(),timesheet.getUser().getEmail());
    	
    	comment.setTimesheet(timesheet);
    	timesheet.getComments().add(comment);
    	timesheet.setLastRejectedDate(new Date());
    	
    	timesheet = getTimesheetService().updateOne(timesheet);
    	
    	timesheet = getTimesheetService().rejectTimesheet(timesheet.getId()); 
    	
    	//Save a communication email for the message
		EmailData emailData = new EmailData( validator, timesheet.getUser(),"Timesheet rejected for year["+view.getYear()+"] and week[" + view.getWeek() + "]", description, EmailData.TYPE_MISSION_COMMUNICATION);
		emailService.saveOne(emailData);
		/*
		view.getTimesheet().setStatus(TIMESHEET_STATUS_STRING_DRAFT);
		Map<String,Object> mapResults = new HashMap<String, Object>();
    	mapResults.put(TIMESHEET_DETAILS_KEY, view);
		request.getSession(false).setAttribute(TIMESHEET_DETAILS_KEY, view);
    	
    	return new ModelAndView("activity_report_workshop",mapResults);
    	*/
		
    	return new ModelAndView("redirect:/showTimesheetRegisterPage.htm?year="+view.getYear()+"&week=" + view.getWeek()+"&employeeId="+view.getEmployee().getId());
    	
    }
    
    /**
     * @param targetEmployee
     * @param row
     * @return
     */
    public boolean isActivityRetainForPlanning(Employee targetEmployee,TimesheetRow row){
    	Activity activity = row.getActivity();
    	if(activity == null){//Une tâche
    		 /*
    		 *CONGE:9930-9931-9932-9920
	   		 *MALADIE:9910
	   		 *FORMATION:9859
	   		 */
    		if(
    				( row.getCodePrestation().equalsIgnoreCase("9930"))||
    				( row.getCodePrestation().equalsIgnoreCase("9931"))||
    				( row.getCodePrestation().equalsIgnoreCase("9932"))||
    				( row.getCodePrestation().equalsIgnoreCase("9920"))||
    				( row.getCodePrestation().equalsIgnoreCase("9910"))||
    				( row.getCodePrestation().equalsIgnoreCase("9859"))
    			)
    		{
    			return true;	
    		}
    		else{
    			return false;
    		}
    	}
    	else{//Une mission
    		
    		boolean isManagingPosition = targetEmployee.getPosition().isManagingPosition();
    		
    		return (isManagingPosition == false);
    		
    	}
   }
    
    public ModelAndView validateTimesheet(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	String update = request.getParameter("update");
    	WeeklyTimeSheetView view = (WeeklyTimeSheetView)request.getSession(false).getAttribute(TIMESHEET_DETAILS_KEY);
    	Timesheet timesheet = view.getTimesheet();
    	timesheet = getTimesheetService().validateTimesheet(timesheet.getId()); 
    	
    	if(update != null){
    		//Employee targetEmployee = view.getEmployee();
        	
        	//Update the 
        	//public EventPlanning(Employee employee, int year, int weekNumber)
        	EventPlanning eventPlanning = missionService.getEventPlanning( Integer.parseInt(view.getYear()) ,Integer.parseInt(view.getWeek()),view.getEmployee().getId());    	
        	if(eventPlanning != null){
        		
        		//Remove all planned events 
        		if(!eventPlanning.getActivities().isEmpty()){
        			List<Long> itemIds = new ArrayList<Long>();
            		for(ItemEventPlanning itemPlanned : eventPlanning.getActivities()){ 
            			itemIds.add(itemPlanned.getId());
            		}
            		missionService.deleteItemEventPlannings(itemIds);
            		eventPlanning.getActivities().clear();
        		}
        		
        		
        	}
        	else{
        		eventPlanning = missionService.createPlanningEvent(view.getEmployee().getId(),Integer.parseInt(view.getYear()), Integer.parseInt(view.getWeek()) );
        			
        	}
        		
    		//Update the planning from the timesheet
    		for(TimesheetRow row : timesheet.getRows()){
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
    			/*
    		   boolean ret1 = isActivityRetainForPlanning( targetEmployee, row);
    		   if(ret1 == false){
    			   continue;
    		   }
    		   */
    			
    		   /*
    			boolean found = false;
    			for(ItemEventPlanning itemPlanned : eventPlanning.getActivities()){ 
    				long idEntity = itemPlanned.getIdEntity();
    				if(idEntityTofind == idEntity){
    					found = true;
    					break;
    				}
    			}
    			//Add the timesheet row entry
    			if(found == false){}
    			*/	
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
    		
    		missionService.updateEventPlanning(eventPlanning);
    	}
    	
    	
    	
    	
    	//Search for the next timesheet to validate
    	String nextWeek = view.getWeek();
    	boolean foundNew=false;
    	List<TimesheetOption> timesheetOptions = view.getTimesheetOptions();
    	for(TimesheetOption timesheetOption : timesheetOptions){
    		if(timesheetOption.getWeekNumber() != Integer.parseInt(view.getWeek()) && timesheetOption.getStatus().equalsIgnoreCase(TIMESHEET_STATUS_STRING_SUBMITTED)){
    			nextWeek = Integer.toString(timesheetOption.getWeekNumber());
    			foundNew=true;
    			break;
    		}
    	}
    		
    	if(foundNew==false){
    		int weekNumber = Integer.parseInt(view.getWeek());
    		//Update the corresponding timesheet option
    		for(TimesheetOption timesheetOption : view.getTimesheetOptions()){				   
				   if(timesheetOption.getWeekNumber()==weekNumber ){
					   timesheetOption.setStatus(TIMESHEET_STATUS_STRING_VALIDATED);
					   break;
				   }				   
			}
    		view.getTimesheet().setStatus(TIMESHEET_STATUS_STRING_VALIDATED);
    		Map<String,Object> mapResults = new HashMap<String, Object>();
        	mapResults.put(TIMESHEET_DETAILS_KEY, view);
    		request.getSession(false).setAttribute(TIMESHEET_DETAILS_KEY, view);
        	
        	return new ModelAndView("activity_report_workshop",mapResults);
    	}
    	else{
    		return new ModelAndView("redirect:/showTimesheetRegisterPage.htm?year="+view.getYear()+"&week=" + nextWeek+"&employeeId="+view.getEmployee().getId());
    	}
    	
    	
    	
    }
    
    

    
    
    @SuppressWarnings("unchecked")
	public ModelAndView showTimeSheetExcelView(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
    	Map<String,Object> mapResults = new HashMap<String, Object>();
    	
    	WebContext context =  (WebContext) request.getSession(false).getAttribute("context");
    	WeeklyTimeSheetView view = (WeeklyTimeSheetView)request.getSession(false).getAttribute(TIMESHEET_DETAILS_KEY);
    	//String id = request.getParameter("id");			
		
		
     	mapResults.put("timesheetView", view);
    	return new ModelAndView("timesheetExcelView",mapResults);	
    }
    
    
    public ModelAndView sendTimesheetReminderEmails(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	 final ExecutorService  pool = Executors.newFixedThreadPool(1);
    	 pool.execute(new  Handler());    	
    	 return showTimesheetsPage( request,response);
    }
    
    class Handler implements Runnable
    {

      Handler()
      { 
      }
      
      public void run()
      {
    	  timesheetService.createMissingTimesheets();
    	  timesheetService.sendTimesheetReminderEmails();
      }
    }
    
    


	public ITimesheetService getTimesheetService() {
		return timesheetService;
	}


	public void setTimesheetService(ITimesheetService timesheetService) {
		this.timesheetService = timesheetService;
	}


	public IEmailService getEmailService() {
		return emailService;
	}


	public void setEmailService(IEmailService emailService) {
		this.emailService = emailService;
	}


	public IMissionService getMissionService() {
		return missionService;
	}


	public void setMissionService(IMissionService missionService) {
		this.missionService = missionService;
	}


	public IExerciseService getExerciseService() {
		return exerciseService;
	}


	public void setExerciseService(IExerciseService exerciseService) {
		this.exerciseService = exerciseService;
	}


	public ITaskService getTaskService() {
		return taskService;
	}


	public void setTaskService(ITaskService taskService) {
		this.taskService = taskService;
	}


	public boolean isCheckHoursFlag() {
		return checkHoursFlag;
	}


	public void setCheckHoursFlag(boolean checkHoursFlag) {
		this.checkHoursFlag = checkHoursFlag;
	}
    
    
    

	

    



	



	



	

	

	
}
