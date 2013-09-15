package com.interaudit.web.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.interaudit.domain.model.Activity;
import com.interaudit.domain.model.Alerte;
import com.interaudit.domain.model.Cost;
import com.interaudit.domain.model.Document;
import com.interaudit.domain.model.EmailData;
import com.interaudit.domain.model.Employee;
import com.interaudit.domain.model.Event;
import com.interaudit.domain.model.EventPlanning;
import com.interaudit.domain.model.Exercise;
import com.interaudit.domain.model.Invoice;
import com.interaudit.domain.model.ItemEventPlanning;
import com.interaudit.domain.model.Message;
import com.interaudit.domain.model.Mission;
import com.interaudit.domain.model.data.MessageData;
import com.interaudit.domain.model.data.Option;
import com.interaudit.domain.model.data.TimesheetData;
import com.interaudit.service.IActivityService;
import com.interaudit.service.IDocumentService;
import com.interaudit.service.IEmailService;
import com.interaudit.service.IExerciseService;
import com.interaudit.service.IFactureService;
import com.interaudit.service.IMissionService;
import com.interaudit.service.ITimesheetService;
import com.interaudit.service.IUserService;
import com.interaudit.service.param.SearchMissionParam;
import com.interaudit.service.view.AgendaPlanningView;
import com.interaudit.service.view.AnnualPlanningView;
import com.interaudit.service.view.AnnualTimesheetReportView;
import com.interaudit.service.view.MessagerieView;
import com.interaudit.service.view.MissionDetailsView;
import com.interaudit.service.view.MissionView;
import com.interaudit.util.Constants;
import com.interaudit.util.DateUtils;
import com.interaudit.util.WebContext;
import com.interaudit.util.WebUtils;



/**
 * The Welcome action.
 * 
 * @author Julien Dubois
 */
public class MissionController extends MultiActionController {

   // private final Log log = LogFactory.getLog(HandleBrowseDatasetPageController.class);
	
    private IMissionService missionService;
    private IActivityService activityService;
    private IUserService userService;
    private IFactureService factureService;
    private IDocumentService documentService;
    private IEmailService emailService;
    private String rootDocumentPath;
    private ITimesheetService timesheetService;
    private IExerciseService exerciseService;
  
    
    
    
    
	private static final String MISSIONS_KEY = "viewmissions";
	private static final String MISSIONS_PLANNING_KEY ="viewPlanning";
	private static final String MISSION_KEY = "viewMissionDetails";
	private static final String MISSIONS_ANNUAL_PLANNING_KEY = "viewActivityPerWeekPerEmployee";
	private static final String MISSIONS_AGENDA_PLANNING_KEY = "viewAgendaPerEmployee";
	private static final String MISSIONS_ANNUAL_TIMESHEETREPORT_KEY ="viewTimesheetReports";
	
	public static final String PENDING_KEY ="PENDING";
	public static final String ONGOING_KEY ="ONGOING";
	public static final String STOPPED_KEY ="STOPPED";
	public static final String CLOSED_KEY ="CLOSED";
	public static final String CANCELLED_KEY ="CANCELLED";
	
	public static final String YEAR_KEY = "mission_year";
	public static final String MANAGER_KEY = "mission_manager";
	public static final String CUSTOMER_KEY = "mission_customer";
	public static final String EMPLOYEE_KEY = "mission_employee";
	
	public static final String ID_KEY = "id";
	public static final String REPORT_TYPE = "type";
	public static final String PDF_FORMAT = "PDF";
	public static final String CSV_FORMAT = "CSV";
	public static final String MSEXCEL_FORMAT = "MSEXCEL";
	public static final String ENRICHED_FORMAT = "ENRICHED";
	
	public static final String PLANNING_VIEW_KEY = "planning_view";
	public static final String PLANNING_YEAR_KEY = "planning_year";
	public static final String PLANNING_STMONTH_KEY = "planning_start_month";
	
	public static final String MESSAGES_RECUS_KEY = "message_recus";
	public static final String MESSAGES_ENVOYES_KEY = "message_envoyes";
	
	
	
	
	 public ModelAndView showPlanningPage(HttpServletRequest request,
				HttpServletResponse response) throws Exception {
		 
		 Map<String,Object> mapResults = new HashMap<String, Object>();
	    	
		 WebContext context =  (WebContext) request.getSession(false).getAttribute("context");
			 
			String year = request.getParameter("planning_year");
			String manager = request.getParameter("planning_manager");
			
			
			
			
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
		         year = currentYear.toString();
		         /*
				 Calendar c = Calendar.getInstance();
		         Integer value = c.get(Calendar.YEAR);
		         year = value.toString();
		         */
			}
			
			if(manager != null && manager.equalsIgnoreCase("-1")){
				manager = null;
			}
			
	    	SearchMissionParam param = new SearchMissionParam( 	year,  manager == null? null: Long.valueOf(manager),null,null,null,
	    			 											null,  null,  null,
	    			 											null,  null,null);
	    	
	    	MissionView view = missionService.searchMissions(context.getCurrentUser() ,param);		
			mapResults.put(MISSIONS_PLANNING_KEY, view);
			
			
			
	 
			return new ModelAndView("planning",mapResults);
		 
	 }
	 
	 public ModelAndView showAnnualPlanningPage(HttpServletRequest request,
				HttpServletResponse response) throws Exception {
		 
		    //Clean the session scope first
			WebUtils.cleanSession(request);
			
		 	Map<String,Object> mapResults = new HashMap<String, Object>();
	    	
	    	
		 	WebContext context =  (WebContext) request.getSession(false).getAttribute("context");
		 	String missionId = request.getParameter("missionId");
			mapResults.put("missionId", missionId);
			
			String missionName = request.getParameter("missionName");
			mapResults.put("missionName", missionName);
			String year = request.getParameter("year");
			String startMonthAsString = request.getParameter("startMonth");
			String usergroup = request.getParameter("usergroup");
			String role = null;
			
			/*
			String role = request.getParameter("role");
	    	if( role == null || role.length() == 0){
	    		role = "All";
	    	}
	    	*/
			if( usergroup == null || usergroup.length()==0){
				//role = "All";
				role = "employees";
				
			}else if(usergroup.equalsIgnoreCase("all")){
				role = "All";				
			}
			else if(usergroup.equalsIgnoreCase("partners")){
				role = "partners";				
			}
			else if(usergroup.equalsIgnoreCase("managers")){
				role = "managers";				
			}else if(usergroup.equalsIgnoreCase("employees")){
				role = "employees";
			}else{
				role = "secretaires";
			}
			
			
			
			
			
			int startMonth = 0;
			int endMonth = 0;
			int startWeekNumber = 0;
			int endWeekNumber = 0;
						
			GregorianCalendar  calendar = new java.util.GregorianCalendar();
			Integer currentYear = exerciseService.getFirstOnGoingExercise();
			if(currentYear == null){
				//currentYear = exerciseService.getMaxYear();
				 currentYear =  exerciseService.getLastClosedExercise();
				 if(currentYear == null){
					Calendar c = Calendar.getInstance(); 
					currentYear =c.get(Calendar.YEAR);
				 }								 
			}				 
			
			calendar.set(Calendar.YEAR, currentYear);
			if(year == null || year.trim().length()==0){
				
		        year = currentYear.toString();
				//year = Integer.toString(calendar.get(Calendar.YEAR));
				startMonth = calendar.get(Calendar.MONTH);				
				calendar.set(Calendar.DAY_OF_MONTH, 1);
			
			}else{
				
				if(startMonthAsString == null || startMonthAsString.trim().length()==0) {
					startMonth = Integer.parseInt(request.getSession().getAttribute(MissionController.PLANNING_STMONTH_KEY).toString()) ;
				}
				else{					
					startMonth = Integer.parseInt(startMonthAsString);					
				}
				
				//Check the limits
				if(startMonth < 0) startMonth =0;
				if(startMonth > 11) startMonth =11;
				calendar.set(Calendar.YEAR, Integer.parseInt(year));
				calendar.set(Calendar.MONTH, startMonth);				
				calendar.set(Calendar.DAY_OF_MONTH, 1);								
			}
			
			startWeekNumber = calendar.get(Calendar.WEEK_OF_YEAR);
			if(startMonth ==0){
				startWeekNumber = 1;
			}
			
			//Ajouter 3 mois au calendrier
			if(startMonth <= 9){
				calendar.add (Calendar.MONTH, AnnualPlanningView.ECART_MOIS);
			}else if(startMonth == 10){
				calendar.add (Calendar.MONTH, 1);
			}
			
			//System.out.println("MOIS : "+calendar.get(Calendar.MONTH));
			
			int maxiWeekNumber = calendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
			int maxiWeekYearNumber = calendar.getActualMaximum(Calendar.WEEK_OF_YEAR);
			//System.out.println("NB MAX WEEK IN YEAR : " +maxiWeekYearNumber);
			calendar.set(Calendar.WEEK_OF_MONTH, maxiWeekNumber);
			calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			
			//System.out.println("DATE : " +calendar.getTime());
			endWeekNumber = calendar.get(Calendar.WEEK_OF_YEAR);
			endMonth = calendar.get(Calendar.MONTH);
			
			if(endWeekNumber <= startWeekNumber){
				endWeekNumber = maxiWeekYearNumber;
			}
			
			
	    	AnnualPlanningView view = missionService.buildAnnualPlanningView(context.getCurrentUser(),year,startMonth, endMonth,role,startWeekNumber,endWeekNumber);		
			mapResults.put(MISSIONS_ANNUAL_PLANNING_KEY, view);
			
			request.getSession().setAttribute(MissionController.PLANNING_VIEW_KEY, view);
			request.getSession().setAttribute(MissionController.PLANNING_YEAR_KEY, year);
			request.getSession().setAttribute(MissionController.PLANNING_STMONTH_KEY, startMonth);
			
			return new ModelAndView("list-activity-per-employee-per-week",mapResults);
		 
	 }
	 
	 
	 public ModelAndView showUpdatedAnnualPlanningPage(HttpServletRequest request,
				HttpServletResponse response) throws Exception {
		 
		    //Clean the session scope first
			WebUtils.cleanSession(request);
			
		 	Map<String,Object> mapResults = new HashMap<String, Object>();
	    	
	    	
		 	WebContext context =  (WebContext) request.getSession(false).getAttribute("context");
		 	String missionId = request.getParameter("missionId");
			mapResults.put("missionId", missionId);
			
			String missionName = request.getParameter("missionName");
			mapResults.put("missionName", missionName);
			String year = request.getParameter("year");
			String startMonthAsString = request.getParameter("startMonth");
			String usergroup = request.getParameter("usergroup");
			String role = null;
			
			/*
			String role = request.getParameter("role");
	    	if( role == null || role.length() == 0){
	    		role = "All";
	    	}
	    	*/
			if( usergroup == null || usergroup.length()==0){
				//role = "All";
				role = "employees";
				
			}else if(usergroup.equalsIgnoreCase("all")){
				role = "All";				
			}
			else if(usergroup.equalsIgnoreCase("partners")){
				role = "partners";				
			}
			else if(usergroup.equalsIgnoreCase("managers")){
				role = "managers";				
			}else if(usergroup.equalsIgnoreCase("employees")){
				role = "employees";
			}else{
				role = "secretaires";
			}
			
			
			
			
			
			int startMonth = 0;
			int endMonth = 0;
			int startWeekNumber = 0;
			int endWeekNumber = 0;
						
			GregorianCalendar  calendar = new java.util.GregorianCalendar();
			Integer currentYear = exerciseService.getFirstOnGoingExercise();
			if(currentYear == null){
				//currentYear = exerciseService.getMaxYear();
				 currentYear =  exerciseService.getLastClosedExercise();
				 if(currentYear == null){
					Calendar c = Calendar.getInstance(); 
					currentYear =c.get(Calendar.YEAR);
				 }								 
			}				 
			
			calendar.set(Calendar.YEAR, currentYear);
			if(year == null || year.trim().length()==0){
				
		        year = currentYear.toString();
				//year = Integer.toString(calendar.get(Calendar.YEAR));
				startMonth = calendar.get(Calendar.MONTH);				
				calendar.set(Calendar.DAY_OF_MONTH, 1);
			
			}else{
				
				if(startMonthAsString == null || startMonthAsString.trim().length()==0) {
					startMonth = Integer.parseInt(request.getSession().getAttribute(MissionController.PLANNING_STMONTH_KEY).toString()) ;
				}
				else{					
					startMonth = Integer.parseInt(startMonthAsString);					
				}
				
				//Check the limits
				if(startMonth < 0) startMonth =0;
				if(startMonth > 11) startMonth =11;
				calendar.set(Calendar.YEAR, Integer.parseInt(year));
				calendar.set(Calendar.MONTH, startMonth);				
				calendar.set(Calendar.DAY_OF_MONTH, 1);								
			}
			
			startWeekNumber = calendar.get(Calendar.WEEK_OF_YEAR);
			if(startMonth ==0){
				startWeekNumber = 1;
			}
			
			//Ajouter 3 mois au calendrier
			if(startMonth <= 9){
				calendar.add (Calendar.MONTH, AnnualPlanningView.ECART_MOIS);
			}else if(startMonth == 10){
				calendar.add (Calendar.MONTH, 1);
			}
			
			//System.out.println("MOIS : "+calendar.get(Calendar.MONTH));
			
			int maxiWeekNumber = calendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
			int maxiWeekYearNumber = calendar.getActualMaximum(Calendar.WEEK_OF_YEAR);
			//System.out.println("NB MAX WEEK IN YEAR : " +maxiWeekYearNumber);
			calendar.set(Calendar.WEEK_OF_MONTH, maxiWeekNumber);
			calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			
			//System.out.println("DATE : " +calendar.getTime());
			endWeekNumber = calendar.get(Calendar.WEEK_OF_YEAR);
			endMonth = calendar.get(Calendar.MONTH);
			
			if(endWeekNumber <= startWeekNumber){
				endWeekNumber = maxiWeekYearNumber;
			}
			AnnualPlanningView view = (AnnualPlanningView)request.getSession().getAttribute(MissionController.PLANNING_VIEW_KEY);
			view.setStartMonth(startMonth);
			view.setStartWeekNumber(startWeekNumber);
			view.setEndWeekNumber(endWeekNumber);
			view.setEndMonth(endMonth);
			view.setYear(year);
			
			view = missionService.updateAnnualPlanningView(view);
	    	//AnnualPlanningView view = missionService.buildAnnualPlanningView(context.getCurrentUser(),year,startMonth, endMonth,role,startWeekNumber,endWeekNumber);		
			mapResults.put(MISSIONS_ANNUAL_PLANNING_KEY, view);
			
			request.getSession().setAttribute(MissionController.PLANNING_VIEW_KEY, view);
			request.getSession().setAttribute(MissionController.PLANNING_YEAR_KEY, year);
			request.getSession().setAttribute(MissionController.PLANNING_STMONTH_KEY, startMonth);
			
			return new ModelAndView("list-activity-per-employee-per-week",mapResults);
		 
	 }
	 
	 
	 
	
	 
	 
	 public ModelAndView showAnnualTimesheetReportPage(HttpServletRequest request,
				HttpServletResponse response) throws Exception {
		 
		 	//Clean the session scope first
			WebUtils.cleanSession(request);
			
		 	Map<String,Object> mapResults = new HashMap<String, Object>();
	    	
	    	
		 	//WebContext context =  (WebContext) request.getSession(false).getAttribute("context");
			
			String year = request.getParameter("year");
			String startMonthAsString = request.getParameter("startMonth");
			int startMonth = 0;
			int endMonth = 0;
			int startWeekNumber = 0;
			int endWeekNumber = 0;
						
			GregorianCalendar  calendar = new java.util.GregorianCalendar();
			
			if(year == null || year.trim().length()==0){
				
				year = Integer.toString(calendar.get(Calendar.YEAR));
				startMonth = calendar.get(Calendar.MONTH);				
				calendar.set(Calendar.DAY_OF_MONTH, 1);
			
			}else{
				
				if(startMonthAsString == null || startMonthAsString.trim().length()==0) {
					startMonth = Integer.parseInt(request.getSession().getAttribute(MissionController.PLANNING_STMONTH_KEY).toString()) ;
				}
				else{					
					startMonth = Integer.parseInt(startMonthAsString);					
				}
				
				//Check the limits
				if(startMonth < 0) startMonth =0;
				if(startMonth > 11) startMonth =11;
				calendar.set(Calendar.YEAR, Integer.parseInt(year));
				calendar.set(Calendar.MONTH, startMonth);				
				calendar.set(Calendar.DAY_OF_MONTH, 1);								
			}
			
			startWeekNumber = calendar.get(Calendar.WEEK_OF_YEAR);
			if(startMonth ==0){
				startWeekNumber = 1;
			}
			
			//Ajouter 3 mois au calendrier
			if(startMonth <= 9){
				calendar.add (Calendar.MONTH, AnnualPlanningView.ECART_MOIS);
			}else if(startMonth == 10){
				calendar.add (Calendar.MONTH, 1);
			}
			
			int maxiWeekNumber = calendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
			calendar.set(Calendar.WEEK_OF_MONTH, maxiWeekNumber);
			calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			endWeekNumber = calendar.get(Calendar.WEEK_OF_YEAR);
			endMonth = calendar.get(Calendar.MONTH);
			
			String role = request.getParameter("role");
	    	if( role == null || role.length() == 0){
	    		role = "All";
	    	}
			
	    	AnnualTimesheetReportView view = missionService.buildAnnualTimesheetReportView( year, startMonth, endMonth, startWeekNumber, endWeekNumber);
	    	//buildAnnualPlanningView(context.getCurrentUser(),year,startMonth, endMonth,role,startWeekNumber,endWeekNumber);		
			mapResults.put(MISSIONS_ANNUAL_TIMESHEETREPORT_KEY, view);
			request.getSession().setAttribute(MissionController.PLANNING_YEAR_KEY, year);
			request.getSession().setAttribute(MissionController.PLANNING_STMONTH_KEY, startMonth);
			
			return new ModelAndView("annual_timesheet_report",mapResults);
		 
	 }
	 
	
	 
	 public ModelAndView addSingleEventsFromDragAndDropPage(HttpServletRequest request,
				HttpServletResponse response) throws Exception {
		 
		 	String weekNumberAsString = request.getParameter("weekNumber");
		 	String missionIdAsString = request.getParameter("missionId");
		 	String missionName = request.getParameter("missionName");
	    	String employeeIdAsString = request.getParameter("employeeId");
	    	String yearAsString = request.getParameter("year");    	
	    	String planningIdAsString = request.getParameter("planningId");	    	
	    	String startMonth = request.getParameter("startMonth");    	
	    	String usergroup = request.getParameter("usergroup");
	    	
	    	
	    	 Calendar c = Calendar.getInstance();	    	 
    		 c.set(Calendar.YEAR, Integer.parseInt(yearAsString) );
    		 c.set(Calendar.WEEK_OF_YEAR,Integer.parseInt(weekNumberAsString)); 											    					
			 int weekNumber = c.get(Calendar.WEEK_OF_YEAR);
		     
			 //Monday
			 c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			 Date dateOfMonday = c.getTime();
			 
			 //Friday
		     c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);		    
		     Date dateOfFriday = c.getTime();
		     
		     String startdate = new SimpleDateFormat("d.M" , new Locale("fr","FR")).format(dateOfMonday);
		     String	enddate = new SimpleDateFormat("d.M" , new Locale("fr","FR")).format(dateOfFriday);
		     
		  // get the data from the page		
			try{
				List<Long> userIds = new ArrayList<Long>();
				userIds.add(Long.parseLong(employeeIdAsString));
				
				List<Integer> weekList = new ArrayList<Integer>();
				weekList.add(Integer.parseInt(weekNumberAsString));	
				int count = 0;
				Long mission = missionIdAsString == null? null:Long.parseLong(missionIdAsString); 					
				count = missionService.createListOfPlanningEvents(userIds,mission,Integer.parseInt(yearAsString),weekList, startdate, enddate, ItemEventPlanning.DURATION_TYPE_NA, 1.0f,dateOfMonday,dateOfFriday,2.0d);
									
				if(count > 0){
					//Everything went ok
					request.getSession(false).setAttribute("successMessage", "The planning has been successfully updated...");
				}else{
					//Something went wrong
					request.getSession(false).setAttribute("actionErrors", "A problem occurred while processing the update");
				}				
				
				
			}catch(Exception e){
				 request.getSession(false).setAttribute("actionErrors", e.getMessage());
			}
				
			//Redirect to the jsp page		
			return new ModelAndView("redirect:/showAnnualPlanningPage.htm?year=" +yearAsString +"&startMonth=" + startMonth  +"&usergroup=" +usergroup +"&missionId="+missionIdAsString +"&missionName=" +missionName );
				
				
		 
	 }
	 
	 
	 
	 public ModelAndView editEventPlanningPage(HttpServletRequest request,
				HttpServletResponse response) throws Exception {
	    	JSONObject ajaxReply = new JSONObject();
	    	JSONArray eventList = new JSONArray();
	    	//JSONArray timesheetEventList = new JSONArray();
	    	JSONArray dayList = new JSONArray();
	    	JSONObject ajaxObject = null;
	    	
	    	
	    	String weekAsString = request.getParameter("week");
	    	String userAsString = request.getParameter("user");
	    	String yearAsString = request.getParameter("year");
	    	
	    	 ajaxReply.put("week", weekAsString);
	    	 ajaxReply.put("user", userAsString);
	    	
	    	String planningId = request.getParameter("planningId");
	    	String errorCode = request.getParameter("error");
	    	String error =null;
	    	if(errorCode!=null && errorCode.equalsIgnoreCase("1")){
	    		error ="Please select an activity...";
	    	}else if(errorCode!=null && errorCode.equalsIgnoreCase("2")){
	    		error ="Please select a valid period...";
	    	}else if(errorCode!=null && errorCode.equalsIgnoreCase("3")){
	    		error ="Please select a valid period...";
	    	}
			else if(errorCode!=null && errorCode.equalsIgnoreCase("4")){
		 		error ="This activity is already planned...";
		 	}
	    	else{
	    		error ="";
	    	}
	    	ajaxReply.put("error", error);	
	    	
	    	Long planningIdentifier = null;
	    	EventPlanning eventPlanning = null;
	    	String dateOfWeek = null;
	    	
	    	//TimesheetData timesheetValidated = null;
	    	//int lastDateOfWeek = 0;
	    	DateFormat dateFormat = null;
	    	try{
	    		
	    		
	    		
	    		
	    		planningIdentifier = Long.parseLong(planningId);
	    		eventPlanning = missionService.getEventPlanningFromIdentifier(planningIdentifier);
	    		
	    		 Calendar c = Calendar.getInstance();
	    		 dateFormat = new SimpleDateFormat(/*"d.M"*/"EEE d MMM yyyy" , new Locale("fr","FR"));
	    		 //dateFormat = new SimpleDateFormat(/*"d.M"*/"EEE d MMM" , new Locale("fr","FR"));
	    		 c.set(Calendar.YEAR, Integer.parseInt(yearAsString) );
	    		 c.set(Calendar.WEEK_OF_YEAR,Integer.parseInt(weekAsString)); 											    					
				 int weekNumber = c.get(Calendar.WEEK_OF_YEAR);
			     
				 //Monday
				 c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			     //dateOfWeek = c.get(Calendar.DAY_OF_MONTH);
				 dateOfWeek = dateFormat.format(c.getTime());
				 ajaxObject = new JSONObject();
	    		 ajaxObject.put("day", dateOfWeek);	    	
	    		 dayList.put(ajaxObject);
	    		 ajaxReply.put("firstDateOfWeek", dateOfWeek);
	    		 
	    		//Tuesday
				 c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
			     //dateOfWeek = c.get(Calendar.DAY_OF_MONTH);
				 dateOfWeek = dateFormat.format(c.getTime());
				 ajaxObject = new JSONObject();
	    		 ajaxObject.put("day", dateOfWeek);	    	
	    		 dayList.put(ajaxObject);
	    		 
	    		 
	    		//Wednesday
				 c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
			     //dateOfWeek = c.get(Calendar.DAY_OF_MONTH);
				 dateOfWeek = dateFormat.format(c.getTime());
				 ajaxObject = new JSONObject();
	    		 ajaxObject.put("day", dateOfWeek);	    	
	    		 dayList.put(ajaxObject);
	    		 
	    		//Thursday
				 c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
			     //dateOfWeek = c.get(Calendar.DAY_OF_MONTH);
				 dateOfWeek = dateFormat.format(c.getTime());
				 ajaxObject = new JSONObject();
	    		 ajaxObject.put("day", dateOfWeek);	    	
	    		 dayList.put(ajaxObject);
		    		
	    		 //Friday
			     c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
			     //dateOfWeek = c.get(Calendar.DAY_OF_MONTH);
			     dateOfWeek = dateFormat.format(c.getTime());
			     ajaxObject = new JSONObject();
	    		 ajaxObject.put("day", dateOfWeek);	    	
	    		 dayList.put(ajaxObject);
	    		 ajaxReply.put("lastDateOfWeek", dateOfWeek);
	    		 
	    		 
	    		//Try to find the validated timesheet for the end user
	    		 /*
	    		SearchTimesheetParam searchTimesheetParam = new SearchTimesheetParam( Integer.toString(eventPlanning.getYear()), eventPlanning.getWeekNumber() ,eventPlanning.getEmployee().getId() ,
						 null,  null,  "VALIDATED");
	    		List<TimesheetData> timesheets = timesheetService.searchTimesheets(searchTimesheetParam);
	    		if(timesheets != null && timesheets.size() > 0){
	    			timesheetValidated = timesheets.get(0);
	    		}
				 */
	    	}
	    	catch(Exception e){
	    		try{
		    		Integer week = Integer.parseInt(weekAsString);
		    		Long user = Long.parseLong(userAsString);
		    		Integer year = Integer.parseInt(yearAsString);	    			
	    			//int count = 0;
	    					
	    			//count = missionService.createPlanningEvent( user, year, week );
	    								
	    			//if(count > 0){	    
	    				return new ModelAndView("redirect:/loadEmployeePlanningPage.htm?year="+year+"&week="+week+"&employee="+user);
	    			//}				    			
	    		}catch(Exception e2){
	    			 //request.getSession(false).setAttribute("actionErrors", e.getMessage());
	    		}
	    	}
	    	
	    
	    	String sFormat="d.M.yyyy";
	    	if(eventPlanning != null){
		    	//for(ItemEventPlanning itemEventPlanning : eventPlanning.getActivities()){
		    	for(ItemEventPlanning itemEventPlanning : eventPlanning.getSortedItemEventPlannings()){
		    		ajaxObject = new JSONObject();
		    		ajaxObject.put("id", itemEventPlanning.getId());
		    		ajaxObject.put("check", "false");
		    		ajaxObject.put("label", itemEventPlanning.getTitle());
		    		
		    		Date startDate =  itemEventPlanning.getExpectedStartDate();//stringToDate(itemEventPlanning.getStartDate()+"."+eventPlanning.getYear(),  sFormat);
		    		Date endDate = itemEventPlanning.getExpectedEndDate(); //stringToDate(itemEventPlanning.getEndDate()+"."+eventPlanning.getYear(),  sFormat);
		    		ajaxObject.put("startDate",itemEventPlanning.getExpectedStartDate()==null?"":dateFormat.format(startDate));
		    		ajaxObject.put("endDate", itemEventPlanning.getExpectedEndDate() ==null?"":dateFormat.format(endDate));
		    		ajaxObject.put("durationType", itemEventPlanning.getDurationTypeConverted());
		    		ajaxObject.put("duration", itemEventPlanning.getDuration());
		    		
		    		ajaxObject.put("realStartDate", /*itemEventPlanning.getStartDate()*/itemEventPlanning.getRealStartDate()==null?"": dateFormat.format(itemEventPlanning.getRealStartDate()));
		    		ajaxObject.put("realEndDate", /*itemEventPlanning.getEndDate()*/itemEventPlanning.getRealEndDate()==null?"": dateFormat.format(itemEventPlanning.getRealEndDate()));
		    		ajaxObject.put("totalHoursSpent", /*itemEventPlanning.getEndDate()*/Double.toString(itemEventPlanning.getTotalHoursSpent()));
		    		
		    		ajaxObject.put("date", itemEventPlanning.getDateOfEvent() == null?new Date():itemEventPlanning.getDateOfEvent());
		    		eventList.put(ajaxObject);
		    	}
	    	}
	    	/*
	    	if( timesheetValidated != null){
	    		
	    		for(TimesheetRow row : timesheetValidated.getRows()){
	    			ajaxObject = new JSONObject();
		    		ajaxObject.put("id", row.getId());		    		
		    		ajaxObject.put("label", row.getLabel());
		    		
		    		
		    		ajaxObject.put("startDate",row.getStartDate());
		    		ajaxObject.put("endDate", row.getEndDate());
		    		ajaxObject.put("totalHours",row.getTotalOfHours());
		    		timesheetEventList.put(ajaxObject);
	    		}
	    		  
	    		
	    	}
	    	*/
	    	/*
	    	for(int day =  firstDateOfWeek; day <= lastDateOfWeek;day++){
	    		ajaxObject = new JSONObject();
	    		ajaxObject.put("day", day);	    	
	    		dayList.put(ajaxObject);
	    	}
	    	
	    	*/
	    	
	    	ajaxReply.put("title", "Planning for " +eventPlanning.getEmployee().getFullName() + " for week [" + eventPlanning.getWeekNumber()+"]");
	    	ajaxReply.put("year", eventPlanning.getYear());
	    	ajaxReply.put("week", eventPlanning.getWeekNumber());
	    	ajaxReply.put("employeeId", eventPlanning.getEmployee().getId());
	    	ajaxReply.put("idPlanning", eventPlanning.getId());	    	
	    	ajaxReply.put("events", eventList);
	    	ajaxReply.put("days", dayList);
	    	
	    	response.setContentType("text/json;charset=UTF-8");
	    	ServletOutputStream out = response.getOutputStream();
			
	    	String reply = ajaxReply.toString();
			out.write(reply.getBytes("UTF-8"));
			out.flush();
			out.close();
			return null;
	    	
	    }
	 
	 public static Date stringToDate(String sDate, String sFormat) throws Exception {
	        SimpleDateFormat sdf = new SimpleDateFormat(sFormat);
	        return sdf.parse(sDate);
	} 
	 //var url ="${ctx}/loadEmployeePlanningPage.htm?year=" + ${viewActivityPerWeekPerEmployee.year} +"&week="+weekId+"&employee="+employeeId;
	 
	 public ModelAndView loadEmployeePlanningPage(HttpServletRequest request,
				HttpServletResponse response) throws Exception {
	    	JSONObject ajaxReply = new JSONObject();
	    	JSONArray eventList = new JSONArray();
	    	JSONArray dayList = new JSONArray();
	    	JSONObject ajaxObject = null;
	    	
	    	String year = request.getParameter("year");
	    	String week = request.getParameter("week");
	    	String employeeId = request.getParameter("employee");
	    	EventPlanning eventPlanning = null;
	    	String dateOfWeek = null;
	    	//int lastDateOfWeek = 0;
	    	//TimesheetData timesheetValidated = null;
	    	
	    	

	    	
	    	//Try to find the validated timesheet for the end user
	    	/*
    		SearchTimesheetParam searchTimesheetParam = new SearchTimesheetParam( year, Integer.parseInt(week) ,Long.parseLong(employeeId) ,
					 null,  null,  "VALIDATED");
    		List<Timesheet> timesheets = timesheetService.findTimesheets(searchTimesheetParam);
    		if(timesheets != null && timesheets.size() > 0){
    			timesheetValidated = timesheets.get(0);
    		}
	    	*/
	    	String errorCode = request.getParameter("error");
	    	String error =null;
	    	if(errorCode!=null && errorCode.equalsIgnoreCase("1")){
	    		error ="Please select an activity...";
	    	}else if(errorCode!=null && errorCode.equalsIgnoreCase("2")){
	    		error ="Please select a valid period...";
	    	}else if(errorCode!=null && errorCode.equalsIgnoreCase("3")){
	    		error ="Please select a valid period...";
	    	}
	    	else if(errorCode!=null && errorCode.equalsIgnoreCase("4")){
		 		error ="This activity is already planned...";
		 	}
	    	else{
	    		error ="";
	    	}
	    	ajaxReply.put("error", error);	
	    	
	    	eventPlanning = missionService.getEventPlanning( Integer.parseInt(year) ,Integer.parseInt(week),Long.parseLong(employeeId));
	    	if(eventPlanning == null){
	    		//int count = 0;
				
	    		eventPlanning = missionService.createPlanningEvent( Long.parseLong(employeeId), Integer.parseInt(year), Integer.parseInt(week) );
    								
    			if(eventPlanning != null){	    
    				return new ModelAndView("redirect:/loadEmployeePlanningPage.htm?year="+year+"&week="+week+"&employee="+employeeId);
    			}	
    			else{
    				return null;
    			}
	    	}
	    	
	    	
	    	Calendar c = Calendar.getInstance();
	    	DateFormat dateFormat = new SimpleDateFormat(/*"d.M"*/"EEE d MMM yyyy", new Locale("fr","FR"));
	    	//DateFormat dateFormat = new SimpleDateFormat(/*"d.M"*/"EEE d MMM", new Locale("fr","FR"));
   		    c.set(Calendar.WEEK_OF_YEAR,Integer.parseInt(week)); 											    					
			 int weekNumber = c.get(Calendar.WEEK_OF_YEAR);
			 
			 //Monday
			 c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		     //dateOfWeek = c.get(Calendar.DAY_OF_MONTH);
			 dateOfWeek = dateFormat.format(c.getTime());
			 ajaxObject = new JSONObject();
    		 ajaxObject.put("day", dateOfWeek);	    	
    		 dayList.put(ajaxObject);
    		 
    		
    		 ajaxReply.put("firstDateOfWeek", dateOfWeek);	
 	    	
    		 
    		//Tuesday
			 c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
		     //dateOfWeek = c.get(Calendar.DAY_OF_MONTH);
			 dateOfWeek = dateFormat.format(c.getTime());
			 ajaxObject = new JSONObject();
    		 ajaxObject.put("day", dateOfWeek);	    	
    		 dayList.put(ajaxObject);
    		 
    		 
    		//Wednesday
			 c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
		     //dateOfWeek = c.get(Calendar.DAY_OF_MONTH);
			 dateOfWeek = dateFormat.format(c.getTime());
			 ajaxObject = new JSONObject();
    		 ajaxObject.put("day", dateOfWeek);	    	
    		 dayList.put(ajaxObject);
    		 
    		//Thursday
			 c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
		     //dateOfWeek = c.get(Calendar.DAY_OF_MONTH);
			 dateOfWeek = dateFormat.format(c.getTime());
			 ajaxObject = new JSONObject();
    		 ajaxObject.put("day", dateOfWeek);	    	
    		 dayList.put(ajaxObject);
	    		
    		 //Friday
		     c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
		     //dateOfWeek = c.get(Calendar.DAY_OF_MONTH);
		     dateOfWeek = dateFormat.format(c.getTime());
		     ajaxObject = new JSONObject();
    		 ajaxObject.put("day", dateOfWeek);	    	
    		 dayList.put(ajaxObject);
    		 
    		 ajaxReply.put("lastDateOfWeek", dateOfWeek);
			 
			 /*
		     c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			 firstDateOfWeek = c.get(Calendar.DAY_OF_MONTH);
		     c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
			 lastDateOfWeek = c.get(Calendar.DAY_OF_MONTH);
			 */
    		 
    		//Try to find the validated timesheet for the end user
    		 /*
    		SearchTimesheetParam searchTimesheetParam = new SearchTimesheetParam( Integer.toString(eventPlanning.getYear()), eventPlanning.getWeekNumber() ,eventPlanning.getEmployee().getId() ,
					 null,  null,  "VALIDATED");
    		List<TimesheetData> timesheets = timesheetService.searchTimesheets(searchTimesheetParam);
    		if(timesheets != null && timesheets.size() > 0){
    			timesheetValidated = timesheets.get(0);
    		}
	    	*/
    		// String sFormat="d.M.yyyy";
    		
	    	//for(ItemEventPlanning itemEventPlanning : eventPlanning.getActivities()){
    		 if(eventPlanning != null){
    			 for(ItemEventPlanning itemEventPlanning : eventPlanning.getSortedItemEventPlannings()){
    		    		ajaxObject = new JSONObject();
    		    		ajaxObject.put("id", itemEventPlanning.getId());
    		    		ajaxObject.put("check", "false");
    		    		ajaxObject.put("label", itemEventPlanning.getTitle());
    		    		Date startDate =  itemEventPlanning.getExpectedStartDate();//stringToDate(itemEventPlanning.getStartDate()+"."+eventPlanning.getYear(),  sFormat);
    		    		Date endDate = itemEventPlanning.getExpectedEndDate(); //stringToDate(itemEventPlanning.getEndDate()+"."+eventPlanning.getYear(),  sFormat);
    		    		ajaxObject.put("startDate",itemEventPlanning.getExpectedStartDate()==null?"":dateFormat.format(startDate));
    		    		ajaxObject.put("endDate", itemEventPlanning.getExpectedEndDate() ==null?"":dateFormat.format(endDate));
    		    		ajaxObject.put("durationType", itemEventPlanning.getDurationTypeConverted());
    		    		ajaxObject.put("duration", itemEventPlanning.getDuration());
    		    		ajaxObject.put("realStartDate", /*itemEventPlanning.getStartDate()*/itemEventPlanning.getRealStartDate()==null?"": dateFormat.format(itemEventPlanning.getRealStartDate()));
    		    		ajaxObject.put("realEndDate", /*itemEventPlanning.getEndDate()*/itemEventPlanning.getRealEndDate()==null?"": dateFormat.format(itemEventPlanning.getRealEndDate()));
    		    		ajaxObject.put("totalHoursSpent", /*itemEventPlanning.getEndDate()*/Double.toString(itemEventPlanning.getTotalHoursSpent()));
    		    		
    		    		ajaxObject.put("date", itemEventPlanning.getDateOfEvent() == null?new Date():itemEventPlanning.getDateOfEvent());
    		    		eventList.put(ajaxObject);
    		    	} 
    		 }
    		 
	    	
	    	/*
	    	for(int day =  firstDateOfWeek; day <= lastDateOfWeek;day++){
	    		ajaxObject = new JSONObject();
	    		ajaxObject.put("day", day);	    	
	    		dayList.put(ajaxObject);
	    	}
	    	*/
	    	ajaxReply.put("title", "Planning for " +eventPlanning.getEmployee().getFullName() + " for week [" + eventPlanning.getWeekNumber()+"]");
	    	ajaxReply.put("year", eventPlanning.getYear());
	    	ajaxReply.put("week", eventPlanning.getWeekNumber());
	    	ajaxReply.put("employeeId", eventPlanning.getEmployee().getId());
	    	ajaxReply.put("idPlanning", eventPlanning.getId());	
	    	
	    	
	    	
	    	ajaxReply.put("events", eventList);
	    	ajaxReply.put("days", dayList);
	    	
	    	response.setContentType("text/json;charset=UTF-8");
	    	ServletOutputStream out = response.getOutputStream();
			
	    	String reply = ajaxReply.toString();
			out.write(reply.getBytes("UTF-8"));
			out.flush();
			out.close();
			return null;
	    	
	    }
	 
	 
	 
		public ModelAndView showPlanningExcelView(HttpServletRequest request,
				HttpServletResponse response) throws Exception{
	    	Map<String,Object> mapResults = new HashMap<String, Object>();	    	
	    	WebContext context =  (WebContext) request.getSession(false).getAttribute("context");	    	
	    	String year = request.getParameter("year");			
			
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
		         year = currentYear.toString();
		         /*
				Calendar c = Calendar.getInstance();
		         Integer value = c.get(Calendar.YEAR);
		         year = value.toString();
		         */		       
			}
			
			List<AnnualPlanningView> data = new ArrayList<AnnualPlanningView>();
			String role = "All";
			int startWeekNumber = 1;
			int endWeekNumber = 0;
			/*TO DO : A refactorer */
			for(int month =0; month<12;month++){				
				   Calendar c = Calendar.getInstance();				   
				   c.set(Integer.parseInt(year),month, 1);
				   endWeekNumber = c.getActualMaximum(Calendar.DAY_OF_MONTH);
				   AnnualPlanningView view = missionService.buildAnnualPlanningView(context.getCurrentUser(),year,0,11,/*Integer.toString(month),employeeType,*/role,startWeekNumber,endWeekNumber);		
				   data.add(view);
			}
			
	     	mapResults.put("data", data);
	    	return new ModelAndView("planningExcelView",mapResults);	
	    }
	 
	 
	 
	 public ModelAndView scheduleMissionPage(HttpServletRequest request,
				HttpServletResponse response) throws Exception {
		 
		 	String missionIdAstring = request.getParameter("id");
		 	String year = request.getParameter("year");
			String startdate = request.getParameter("startdate");
			String enddate = request.getParameter("enddate");

			Date startDate = DateUtils.getDate(startdate,"dd/MM/yyyy");
			Date endDate = DateUtils.getDate(enddate,"dd/MM/yyyy");
		
			//On ne fait la modification que si les dates sont valides
	    	if(startDate != null && endDate != null){
	    		Long missionId =  Long.parseLong(missionIdAstring);
				Mission mission = missionService.getOneDetached(missionId);
				mission.setStartDate(startDate);
				//mission.setDueDate(endDate);
				mission = missionService.updateOne(mission);
	    	}
	    	
	    	return new ModelAndView("redirect:/showPlanningPage.htm?planning_year=" + year);
	 }
	
    
    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView showMissionsPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
    	Map<String,Object> mapResults = new HashMap<String, Object>();
    	WebContext context =  (WebContext) request.getSession(false).getAttribute("context");
    	
    	
    
    	
    	boolean fromMenu = request.getParameter("FromMenu") != null;
    	//boolean fromRegisterMenu = request.getParameter("fromRegisterMenu") != null;
    	boolean sortedByName = false;
    	if(fromMenu == true){
    		if(request.getSession(false).getAttribute("sortedByName") != null){
    			sortedByName = (Boolean)request.getSession(false).getAttribute("sortedByName");
    		}
    	}
    	else{
    		sortedByName = request.getParameter("sortedByName") != null;
    	}
         
        
        request.getSession(false).setAttribute("sortedByName",sortedByName);
    	
    	String not_started_key = request.getParameter(Mission.JOB_STATUS_NOT_STARTED);
    	String pending_key = request.getParameter(Mission.JOB_STATUS_PENDING);
		String ongoing_key = request.getParameter(Mission.JOB_STATUS_ONGOING);
		String stopped_key = request.getParameter(Mission.JOB_STATUS_STOPPED);
		String closed_key = request.getParameter(Mission.JOB_STATUS_CLOSED);
		String cancelled_key = request.getParameter(Mission.JOB_STATUS_CANCELLED);
		
		if(request.getParameter("not_started_key")  != null){
			not_started_key = Mission.JOB_STATUS_NOT_STARTED;
		}
		
		if(request.getParameter("pending_key")  != null){
			pending_key = Mission.JOB_STATUS_PENDING;
		}
		
		if(request.getParameter("ongoing_key")  != null){
			ongoing_key = Mission.JOB_STATUS_ONGOING;
		}
		
		if(request.getParameter("stopped_key")  != null){
			stopped_key = Mission.JOB_STATUS_STOPPED;
		}
		
		if(request.getParameter("closed_key")  != null){
			closed_key = Mission.JOB_STATUS_CLOSED;
		}
		
		if(request.getParameter("cancelled_key")  != null){
			cancelled_key = Mission.JOB_STATUS_CANCELLED;
		}
		
		
		
		
		
		if(fromMenu == true){
			not_started_key = null;
	    	pending_key = Mission.JOB_STATUS_PENDING;
			ongoing_key = Mission.JOB_STATUS_ONGOING;
			stopped_key = null;
			closed_key = null;
			cancelled_key = null;
			
			
		}
		
		String year = request.getParameter(MissionController.YEAR_KEY);
		String manager = request.getParameter(MissionController.MANAGER_KEY);
		String customer = request.getParameter(MissionController.CUSTOMER_KEY);
		String employee = request.getParameter(MissionController.EMPLOYEE_KEY);
		String missionId = request.getParameter("missionId");
		
		if(manager != null && manager.equalsIgnoreCase("-1")){
			manager = null;
		}
		
		if(customer != null && customer.equalsIgnoreCase("-1")){
			customer = null;
		}
		
		if(missionId != null && missionId.equalsIgnoreCase("-1")){
			missionId = null;
		}
		
		if(missionId != null){
			not_started_key = null;
	    	pending_key = null;
			ongoing_key = null;
			stopped_key = null;
			closed_key = null;
			cancelled_key = null;
			
			
		}
		
		
		request.getSession(false).setAttribute("not_started_key",not_started_key);
		request.getSession(false).setAttribute("pending_key",pending_key); 
		request.getSession(false).setAttribute("ongoing_key",ongoing_key); 
		request.getSession(false).setAttribute("stopped_key",stopped_key); 
		request.getSession(false).setAttribute("closed_key",closed_key); 
		request.getSession(false).setAttribute("cancelled_key",cancelled_key);
		
		
		
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
			/*
			 Calendar c = Calendar.getInstance();
	         Integer value = c.get(Calendar.YEAR);
	         */
	         year = currentYear.toString();
		}
		/*
		if(manager == null && context.getCurrentUser().getPosition().isManagerMission()){
			manager = context.getCurrentUser().getId().toString();
		}
		*/
		
		
		
		if(employee == null){
			employee = context.getCurrentUser().getId().toString();
		}else if(  employee.equalsIgnoreCase("-1")){
			employee = null;
		}
    	
    	SearchMissionParam param = new SearchMissionParam( 	year,  
    														manager == null? null: Long.valueOf(manager),
    														customer == null? null:  Long.valueOf( customer),
    														employee == null? null:  Long.valueOf( employee),
    														not_started_key,
    			 											pending_key,  
    			 											ongoing_key,  
    			 											stopped_key,
    			 											closed_key,  
    			 											cancelled_key,
    			 											missionId == null? null:  Long.valueOf( missionId));
    	
    	Employee caller =  context.getCurrentUser();//userService.getOneDetached(Long.valueOf( employee))/*context.getCurrentUser()*/;
    	
    	MissionView view = missionService.findMissionsForEmployee(caller, param,sortedByName);//missionService.searchMissions(caller, param);	
    	
    	view.setSortedByName( sortedByName ==true? "true":"false");
		mapResults.put(MISSIONS_KEY, view);
		
		return new ModelAndView("list_missions",mapResults);		
	}
    
    
    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView showMissionBudgetPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
 
		return new ModelAndView("mission_budget");		
	}
    
    public ModelAndView showAgendaPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	
    	//Clean the session scope first
		WebUtils.cleanSession(request);
    	
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
	         year = currentYear.toString();
	         
			 Calendar c = Calendar.getInstance();
			 c.set(Calendar.YEAR, currentYear);
			 c.set(Calendar.DAY_OF_WEEK , Calendar.MONDAY);
	         //Integer value = c.get(Calendar.YEAR);
	         //year = value.toString();
	        Integer value = c.get(Calendar.WEEK_OF_YEAR);
 	         week = value.toString();
		}
		
		if(year != null && week == null){
			Calendar c = Calendar.getInstance();
			 c.set(Calendar.YEAR , Integer.parseInt(year));
			 c.set(Calendar.DAY_OF_WEEK , Calendar.MONDAY);
			 Integer value = c.get(Calendar.WEEK_OF_YEAR);
	         week = value.toString();
		}
		
		if(employeeIdentifier == null ){			
			// get the context object			
			employeeIdentifier = context.getCurrentUser().getId().toString();
		}
		
		AgendaPlanningView view = missionService.buildAgendaPlanningView(context.getCurrentUser(), year,week,employeeIdentifier);		
		mapResults.put(MISSIONS_AGENDA_PLANNING_KEY, view);
		request.getSession(false).setAttribute(MISSIONS_AGENDA_PLANNING_KEY, view);
		
		return new ModelAndView("agenda",mapResults);
	}
    
    
   
    
    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView showMissionPropertiesPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	
    		
    	Map<String,Object> mapResults = new HashMap<String, Object>(); 
    	String missionId = request.getParameter("id");
    	WebContext context =  (WebContext) request.getSession(false).getAttribute("context");
    	Employee caller = context.getCurrentUser();
    	MissionDetailsView missionDetails = missionService.buildMissionDetailsViewForId(caller,Long.valueOf(missionId));
    	
    	request.getSession(false).setAttribute(MISSION_KEY, missionDetails);
    	
    	mapResults.put(MISSION_KEY, missionDetails);
    	return new ModelAndView("mission_budget",mapResults);
    	
	}
    
    
    public ModelAndView showMissionTeamMembersPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	/*
    	
    	String missionId = request.getParameter("id");
    	WebContext context =  (WebContext) request.getSession(false).getAttribute("context");
    	Employee caller = context.getCurrentUser();
    	MissionDetailsView missionDetails = missionService.buildMissionDetailsViewForId(caller,Long.valueOf(missionId));
    	
    	*/
    	Map<String,Object> mapResults = new HashMap<String, Object>();
    	String idMission = request.getParameter("id");
    	List<Employee> members = missionService.getMissionMembers(Long.parseLong(idMission));    	
    	List<Option>  employeesOptions = userService.getAllEmployeeAsOptions();
    	mapResults.put("missionForUpdate", missionService.getOneDetached(Long.valueOf(idMission)));
    	mapResults.put("employeesOptions", employeesOptions);
    	mapResults.put("members", members);
    	return new ModelAndView("mission_team_register",mapResults);
    }
    
    public ModelAndView showAddMemeberForMissionPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	    	String idMission = request.getParameter("idMission");	
	    	String employeId = request.getParameter("employeId");	
		  try{
		    	missionService.addMemberToMission(Long.parseLong(idMission),Long.parseLong(employeId) );
		    	
		    	return new ModelAndView("redirect:/showMissionTeamMembersPage.htm?id="+idMission+"&FromPlanningWeek=true");
	     }catch(Exception be){
			 request.getSession(false).setAttribute("actionErrors","Error occured while adding a member...Maybe the member is already registered for the mission..");			 
			 return new ModelAndView("redirect:/showMissionTeamMembersPage.htm?id="+idMission+"&FromPlanningWeek=true");
		 }
    	
    }
    
    public ModelAndView showRemoveMemberForMissionPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	String idMission = request.getParameter("id");	
    	String employeId = request.getParameter("employeId");	
    	try{
	    	missionService.removeMemberToMission(Long.parseLong(idMission),Long.parseLong(employeId) );	    	
	    	return new ModelAndView("redirect:/showMissionTeamMembersPage.htm?id="+idMission+"&FromPlanningWeek=true");	    	
	    }catch(Exception be){
			 request.getSession(false).setAttribute("actionErrors","Error occured while removing a member...");			 
			 return new ModelAndView("redirect:/showMissionTeamMembersPage.htm?id="+idMission+"&FromPlanningWeek=true");
		}
    }
    
   
    	
    
    
    public ModelAndView showMissionPlanningPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	
    		
    	Map<String,Object> mapResults = new HashMap<String, Object>(); 
    	String missionId = request.getParameter("id");
    	WebContext context =  (WebContext) request.getSession(false).getAttribute("context");
    	Employee caller = context.getCurrentUser();
    	
    	MissionDetailsView missionDetails = missionService.buildMissionDetailsViewForId(caller,Long.valueOf(missionId));
    	
    	request.getSession(false).setAttribute(MISSION_KEY, missionDetails);
    	
    	mapResults.put(MISSION_KEY, missionDetails);
		return new ModelAndView("planning_mission",mapResults);		
	}
    
    
    public ModelAndView handleMissionDatesPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	
		return new ModelAndView("mission_proprietes");		
	}
    
    
	public ModelAndView showMessageRecusPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	
		Map<String,Object> mapResults = new HashMap<String, Object>();     	
    	WebContext context =  (WebContext) request.getSession(false).getAttribute("context");
    	
    	String year = request.getParameter("year");	
    	String mission = request.getParameter("mission");	
    	Integer exercise = null;
    	Long missionId = null;
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
			exercise =currentYear;
	       /*
	         
			 Calendar c = Calendar.getInstance();
			 exercise = c.get(Calendar.YEAR);	 
			 */        
		}
		else{
			exercise = Integer.parseInt(year);
		}
		
		
    	
		if(mission != null && ! mission.equalsIgnoreCase("-1")){
			missionId = Long.parseLong(mission);
		}
		
    	Employee caller = context.getCurrentUser();
    	MessagerieView messages = missionService.searchMessages(caller,exercise,missionId,true,false ,0,10);
		mapResults.put(MESSAGES_RECUS_KEY, messages);
		request.getSession(false).setAttribute("messages", messages);
		return new ModelAndView("messagerie_recus",mapResults);		
	}
	
	public ModelAndView showMessageEnvoyesPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String,Object> mapResults = new HashMap<String, Object>();     	
    	WebContext context =  (WebContext) request.getSession(false).getAttribute("context");
    	String year = request.getParameter("year");	
    	String mission = request.getParameter("mission");	    	
    	Long missionId = null;
    	Integer exercise = null;
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
    		exercise =currentYear;
    		/*
			 Calendar c = Calendar.getInstance();
			 exercise = c.get(Calendar.YEAR);	
			 */         
		}
		else{
			exercise = Integer.parseInt(year);
		}
    	
		
		if(mission != null && ! mission.equalsIgnoreCase("-1")){
			missionId = Long.parseLong(mission);
		}
    	Employee caller = context.getCurrentUser();
    	MessagerieView messages = missionService.searchMessages(caller,exercise,missionId,false,false ,0,10);
		mapResults.put(MESSAGES_ENVOYES_KEY, messages);
		request.getSession(false).setAttribute("messages", messages);
		return new ModelAndView("messagerie_envoyes",mapResults);		
	}
	
	/*
	public ModelAndView createMessagePage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String,Object> mapResults = new HashMap<String, Object>();     	
		return new ModelAndView("email_register",mapResults);
	}
	
	public ModelAndView editMessagePage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String,Object> mapResults = new HashMap<String, Object>();     	
		return new ModelAndView("email_register",mapResults);
	}
    */
    
    
    
    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView showMissionPropertiesFromBudgetPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	    		
    	Map<String,Object> mapResults = new HashMap<String, Object>(); 
    	String missionId = request.getParameter("id");
    	WebContext context =  (WebContext) request.getSession(false).getAttribute("context");
    	Employee caller = context.getCurrentUser();
    	MissionDetailsView missionDetails = missionService.buildMissionDetailsViewForBudgetId(caller,Long.valueOf(missionId));
    	request.getSession(false).setAttribute(MISSION_KEY, missionDetails);
    	mapResults.put(MISSION_KEY, missionDetails);
		
		//return new ModelAndView("mission_proprietes",mapResults);	    	
    	return new ModelAndView("mission_budget",mapResults);
	}
    
    
    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView showMissionHeuresPresteesPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView("mission_heures_prestees");		
	}
    
    
    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView showMissionInterventionsPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView("mission_interventions");		
	}
    
    public ModelAndView showMissionChartsPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView("mission_graphiques");		
	}
    
    
    public ModelAndView showMissionBartChartPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
    	OutputStream out = response.getOutputStream();
		try {
			org.jfree.data.category.DefaultCategoryDataset dataset = new DefaultCategoryDataset();
				
		    	MissionDetailsView missionDetails = (MissionDetailsView)request.getSession(false).getAttribute(MISSION_KEY);
		    	
	    	
		    
				dataset.addValue(missionDetails.getStatistiques().getPrixRevient(), "Prix revient", "Prix revient");
				dataset.addValue(missionDetails.getStatistiques().getPrixVente(), "Prix vente", "Prix vente");
				dataset.addValue(missionDetails.getStatistiques().getPrixFacture(), "Factures", "Factures");
				//dataset.addValue(totalDepenses, "Depenses", "Depenses");
				//dataset.addValue(totalBudget, "Budget", "Budget");
				
				
				
				String title= "Statistiques mission " + missionDetails.getMissionData().getCustomerName() ;
				
				JFreeChart chart = ChartFactory.createBarChart(
				title,
				"Category",
				"Statistiques",
				dataset,
				PlotOrientation.VERTICAL,
				true, true, false
				);
				response.setContentType("image/png");
				ChartUtilities.writeChartAsPNG(out, chart, 1000, 400);
				}
		catch (Exception e) {
			logger.error(e.toString());
		}
		finally {
		out.close();
		}
		
		return null;	
	}
    
    
   
    public ModelAndView handleMultipleEventsPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception { 
    	
    	
    	AnnualPlanningView view = (AnnualPlanningView)request.getSession().getAttribute(MissionController.PLANNING_VIEW_KEY);		
			
    	ArrayList<String> messages = new ArrayList<String>();
    	String todo = request.getParameter("todo");
		String days = request.getParameter("days");
		String users = request.getParameter("users");
    	
		//String year = request.getParameter("currentyear");
		String year = request.getParameter("year");
		String month = request.getParameter("startMonth");
		//String profile = request.getParameter("profile");
		
		String missionId = request.getParameter("missionId");
		//String time = request.getParameter("time");
		String usergroup = request.getParameter("usergroup");
		
		/*
		//Check the days
		if(days == null || days.length() == 0){
			messages.add("Please select the dates");
		}
		
		//Check the users
		if(users == null || users.length() == 0){
			messages.add("Please select the users");
		}
		*/
		
		//Check the activity
		if(!todo.equalsIgnoreCase("remove")&& !todo.equalsIgnoreCase("updateTS")  && (missionId == null || missionId.length() == 0)){
			messages.add("Please select an activity");
		}
		
		if(!messages.isEmpty()){
			//Something went wrong
			request.getSession(false).setAttribute("actionErrors", messages);
			//Redirect to the jsp page		
			//return new ModelAndView("redirect:/showAnnualPlanningPage.htm?year="+year+"&month=" + month+"&role="+profile);
			return new ModelAndView("redirect:/showAnnualPlanningPage.htm?year="+year+"&month=" + month+"&usergroup="+usergroup);
		}
		
	
		
		// get the data from the page		
		try{
			List<Long> userIds =null;
			if(users != null && users.length() != 0){
				userIds = new ArrayList<Long>();
				StringTokenizer stmrUsers = new StringTokenizer(users,",");
				while(stmrUsers.hasMoreTokens())
				{
					String userAsString = stmrUsers.nextToken();
					Long userId = Long.parseLong(userAsString);
					userIds.add(userId);
				}
			}
			else{
				userIds = view.getAllUserIds();
			}
			
			
			List<Integer> weekList = null;
			if(days != null && days.length() != 0){
				weekList = new ArrayList<Integer>();
				StringTokenizer stmrDays = new StringTokenizer(days,",");
				while(stmrDays.hasMoreTokens())
				{
					String weekOfYear = stmrDays.nextToken();
					weekList.add(Integer.parseInt(weekOfYear));				 
				}
			}
			else{
				weekList = view.getAllWeeks();
			}
			
			
			if(userIds.isEmpty() || weekList.isEmpty()){
				request.getSession(false).setAttribute("actionErrors", "Please in order to proceed further make sure you have selected employee(s) and date(s) ...");
			}
			else{
				int count = 0;
				
				if(todo.equalsIgnoreCase("add")){
					Long mission = missionId == null? null:Long.parseLong(missionId); 					
					count = missionService.createListOfPlanningEvents(userIds,mission,Integer.parseInt(year),weekList, null,null, null, -1,null,null,2.0d);
				}
				if(todo.equalsIgnoreCase("updateTS")){								
					count = missionService.updatePlanningFromTimeSheets(userIds,Integer.parseInt(year),weekList);
				}
				else{
					count = missionService.deleteListOfPlanningEvents(userIds,Integer.parseInt(year), weekList );
				}
								
				if(count > 0){
					//Everything went ok
					request.getSession(false).setAttribute("successMessage", "The planning has been successfully updated...");
				}else{
					//Something went wrong
					request.getSession(false).setAttribute("actionErrors", "A problem occurred while processing the update");
				}				
			}
			
		}catch(Exception e){
			 request.getSession(false).setAttribute("actionErrors", e.getMessage());
		}
		
		//Redirect to the jsp page		
		//return new ModelAndView("redirect:/showAnnualPlanningPage.htm?year="+year+"&month=" + month+"&role="+profile);
		//return new ModelAndView("redirect:/showAnnualPlanningPage.htm?year="+year);
		return new ModelAndView("redirect:/showAnnualPlanningPage.htm?year="+year+"&month=" + month+"&usergroup="+usergroup);
		
    }
   
    
    public ModelAndView addSingleEventsPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception { 
    	
    	String planningId = request.getParameter("planningId");
    	EventPlanning eventPlanning = missionService.getEventPlanningFromIdentifier(Long.parseLong(planningId));
    	
    	String week = request.getParameter("week");
    	String user = request.getParameter("user");
    	String year = request.getParameter("year");
		String missionId = request.getParameter("missionId");
		String startdate = request.getParameter("startdate");
		String enddate = request.getParameter("enddate");
		String durationType = request.getParameter("duration");
		String nbhours = request.getParameter("nbhours");
		Integer hoursInt=null;
		if(nbhours != null){
			hoursInt=Integer.parseInt(nbhours);
		}
		else{
			hoursInt=0;
		}
		
		double hours = AnnualPlanningView.fromTimeOptionToDouble(hoursInt);
		float duration = 0.0f;
		
		//Error missing mission
		if( missionId.equalsIgnoreCase("-1")){
			return new ModelAndView("redirect:/editEventPlanningPage.htm?planningId="+planningId+"&error=1"+"&week="+week+"&user="+user+"&year="+year);
		}
		
		/*
		 //Avoid duplicate mission
		boolean exist = eventPlanning.containsMissionWithIdentifier(Long.parseLong(missionId) );
		
		
		if( exist == true){
			return new ModelAndView("redirect:/editEventPlanningPage.htm?planningId="+planningId+"&error=4");
		}
		*/
		
		Calendar c = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat(/*"d.M"*/"EEE d MMM yyyy" , new Locale("fr","FR"));
		//DateFormat dateFormat = new SimpleDateFormat(/*"d.M"*/"EEE d MMM" , new Locale("fr","FR"));
    	//DateFormat dateFormat = new SimpleDateFormat("d.M.yyyy", new Locale("fr","FR"));
    	String startDateAsString = startdate + "." + eventPlanning.getYear();
    	String endDateAsString = enddate + "." + eventPlanning.getYear();
    	
    	Date theFirstDate = dateFormat.parse(startdate);
    	Date theSecondDate = dateFormat.parse(enddate);
    	
    	
    	//Error debut > fin
		if( theFirstDate.after(theSecondDate) ){
			return new ModelAndView("redirect:/editEventPlanningPage.htm?planningId="+planningId+"&error=2"+"&week="+week+"&user="+user+"&year="+year);
		}
		
		
		//Error AM or PM incompatible with period
		/*
		if( (durationType.equalsIgnoreCase("AM") || durationType.equalsIgnoreCase("MM")) && (!theFirstDate.equals(theSecondDate) )){
			return new ModelAndView("redirect:/editEventPlanningPage.htm?planningId="+planningId+"&error=3");
		}
    	*/
    	
    	startdate = new SimpleDateFormat("d.M" , new Locale("fr","FR")).format(theFirstDate);
    	enddate = new SimpleDateFormat("d.M" , new Locale("fr","FR")).format(theSecondDate);
    	int nbDays = DateUtils.getNbDays( theFirstDate,  theSecondDate);
    	
    	if(nbDays > 1){
    		if(durationType.equalsIgnoreCase(ItemEventPlanning.DURATION_TYPE_HALFDAYAM) || durationType.equalsIgnoreCase(ItemEventPlanning.DURATION_TYPE_HALFDAYPM)){
     		   duration = 0.5f * (nbDays + 1);
     		}else{
     			duration = nbDays + 1;        		
        		durationType = ItemEventPlanning.DURATION_TYPE_NA;
     		}
    		
    		
    	}
    	else{ 
    		
    		
    	   if(durationType.equalsIgnoreCase(ItemEventPlanning.DURATION_TYPE_HALFDAYAM) || durationType.equalsIgnoreCase(ItemEventPlanning.DURATION_TYPE_HALFDAYPM)){
    		   duration = 0.5f;
    		}
    		else{
    			duration = nbDays + 1;
    			durationType = ItemEventPlanning.DURATION_TYPE_NA;
    		}    		
    	}

    	//String startdate,String enddate,String durationType,float duration
				
		// get the data from the page		
		try{
			List<Long> userIds = new ArrayList<Long>();
			userIds.add(eventPlanning.getEmployee().getId());
			
			List<Integer> weekList = new ArrayList<Integer>();
			weekList.add(eventPlanning.getWeekNumber());	
			int count = 0;
			Long mission = missionId == null? null:Long.parseLong(missionId); 					
			count = missionService.createListOfPlanningEvents(userIds,mission,eventPlanning.getYear(),weekList, startdate, enddate, durationType, duration,theFirstDate,theSecondDate,hours);
								
			if(count > 0){
				//Everything went ok
				request.getSession(false).setAttribute("successMessage", "The planning has been successfully updated...");
			}else{
				//Something went wrong
				request.getSession(false).setAttribute("actionErrors", "A problem occurred while processing the update");
			}				
			
			
		}catch(Exception e){
			 request.getSession(false).setAttribute("actionErrors", e.getMessage());
		}
		
		
		
		//Redirect to the jsp page		
		return new ModelAndView("redirect:/editEventPlanningPage.htm?planningId="+planningId+"&week="+week+"&user="+user+"&year="+year);
		
    }
    
    
    
    @SuppressWarnings("unchecked")
	public ModelAndView deleteEventPlanningItemsPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
    	//Map<String,Object> mapResults = new HashMap<String, Object>();
    	
    	String year = request.getParameter("year");
		String idPlanning = request.getParameter("idPlanning");
		String week = request.getParameter("week");
    	String user = request.getParameter("user");
    	
		
		//Recuperation des exercices si disponibles
		 List<Long> items = new ArrayList<Long> ();
		 String[] checks = request.getParameterValues("che_");
         if(checks != null){
             for (int i=0 ; i < checks.length ; i++){
            	 Long idItem = Long.valueOf(checks[i]);
		    	 items.add(idItem);  
             }
         } 

		 /*
		 Enumeration<String> paramNames = request.getParameterNames();
		 while(paramNames.hasMoreElements()) {
		      String paramName = (String)paramNames.nextElement();
		      if (paramName.startsWith("che_")){
		    	  String paramValue = request.getParameter(paramName);
		    	  Long idItem = Long.valueOf(paramValue);
		    	  items.add(idItem);      
		      }
		 }
		 */	
		
		 if(!items.isEmpty()){
			 
			 missionService.deleteItemEventPlannings(items);			
		 }
		
		 //return new ModelAndView("redirect:/showAnnualPlanningPage.htm?year="+year);
		 return new ModelAndView("redirect:/editEventPlanningPage.htm?planningId="+idPlanning+"&week="+week+"&user="+user+"&year="+year);
		
    }
    
    
    
   
    
    
    
    public ModelAndView handleEventPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception { 
    	
    	AgendaPlanningView agenda = (AgendaPlanningView)request.getSession(false).getAttribute(MISSIONS_AGENDA_PLANNING_KEY);
    	String eventIdAsString = request.getParameter("eventId");		
		String title = request.getParameter("title");
		String userIdAsString = request.getParameter("userId");
		String missionIdAsString = request.getParameter("mission");
		
		
		if(missionIdAsString.trim().length() == 0){
			if(eventIdAsString.trim().length() == 0){
				return new ModelAndView("redirect:/showAgendaPage.htm?year="+agenda.getYear()+"&week=" + agenda.getWeek()+"&employeeId="+agenda.getEmployee().getId());
			}
			else{					
					
					Event event = null;
					Collection<Event> events = agenda.getActivities().values();
					for(Event event2 : events){
						if(event2.getId().toString().equalsIgnoreCase(eventIdAsString)){
							event = event2;
							break;
						}
					}
					if( event != null){							
						missionIdAsString = (event.getActivity() == null)? (event.getTask().getId().toString()):(event.getActivity().getMission().getId().toString());
					}
					else{
						return new ModelAndView("redirect:/showAgendaPage.htm?year="+agenda.getYear()+"&week=" + agenda.getWeek()+"&employeeId="+agenda.getEmployee().getId());
					}					
			}				
		}
		
		
		
		
		String object = null;

		int startHour = Integer.parseInt(request.getParameter("starttime")) ;
		int endHour = request.getParameter("endtime")== null || request.getParameter("endtime").length() == 0? 0 :Integer.parseInt(request.getParameter("endtime"));
		endHour = endHour <= startHour ? startHour+4 : endHour;
		long millis = Long.parseLong(request.getParameter("dayOfYear")); 
		
		Long userId = Long.parseLong(userIdAsString);
		Long missionId = missionIdAsString == null? null:Long.parseLong(missionIdAsString); 
		
				
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
		
		Date dateOfEvent = calendar.getTime();
		
		
		if(eventIdAsString == null || eventIdAsString.trim().length() == 0){
			missionService.createEvent(userId,missionId, year,  month,
					dayOfYear,  startHour,  endHour, object,  title);
		}else{
			Long eventId = Long.parseLong(eventIdAsString); 
			missionService.updateEvent(eventId,userId,missionId, year,  month,
					dayOfYear,  startHour,  endHour, object,  title,dateOfEvent);			
		}
		
		
    	return new ModelAndView("redirect:/showAgendaPage.htm?year="+agenda.getYear()+"&week=" + agenda.getWeek()+"&employeeId="+agenda.getEmployee().getId());
    	
    }
    
    
      public ModelAndView editEventPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception { 
    	
    	String eventId = request.getParameter("eventId");
    	JSONObject ajaxReply = new JSONObject();
    	
		if (eventId != null){	
			
				AgendaPlanningView agenda = (AgendaPlanningView)request.getSession(false).getAttribute(MISSIONS_AGENDA_PLANNING_KEY) ;
				if(agenda == null)
				{			
					ajaxReply.put("result", "nok");
					response.setContentType("application/json;charset=UTF-8");
				}	
				
				Event event = null;
				Collection<Event> events = agenda.getActivities().values();
				for(Event event2 : events){
					if(event2.getId().toString().equalsIgnoreCase(eventId)){
						event = event2;
						break;
					}
				}
				
				
				
				if( event != null){
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(event.getDateOfEvent());
					long timeAsMillis = calendar.getTimeInMillis();		
					
					int indiceDay = event.getDayNumber(event.getDateOfEvent());
					
					ajaxReply.put("result", "ok");
					ajaxReply.put("indice",Integer.toString(indiceDay));
					ajaxReply.put("eventId",event.getId().toString());
					ajaxReply.put("title",event.getTitle());
					//ajaxReply.put("taskId",event.getTask().getId().toString());
					ajaxReply.put("taskId",event.getTask() == null?null:event.getTask().getId().toString());
			
					ajaxReply.put("dayOfYear",timeAsMillis);
					ajaxReply.put("starttime",event.getStartHour());
					ajaxReply.put("endtime",event.getEndHour());
					ajaxReply.put("mission",event.getActivity() == null? event.getTask().getId().toString():event.getActivity().getMission().getId());
					
					
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
			logger.error("invoiceId null");
			return null;
		}
    }
    
    public ModelAndView deleteEvent(HttpServletRequest request,
			HttpServletResponse response) throws Exception { 
    	
    	String eventIdAstring = request.getParameter("eventId");
    	String userIdAsString = request.getParameter("userId");
    	
    	String yearAstring = request.getParameter("year");
    	String weekAsString = request.getParameter("week");
    	
    	Long eventId =  Long.parseLong(eventIdAstring );
    	Long userId = Long.parseLong(userIdAsString);
    	int year = Integer.parseInt(yearAstring);
    	int week = Integer.parseInt(weekAsString);
    	timesheetService.deleleteEvent(eventId, year, week, userId);
    	
    	
    	AgendaPlanningView agenda = (AgendaPlanningView)request.getSession(false).getAttribute(MISSIONS_AGENDA_PLANNING_KEY);
    	return new ModelAndView("redirect:/showAgendaPage.htm?year="+agenda.getYear()+"&week=" + agenda.getWeek()+"&employeeId="+agenda.getEmployee().getId());
    }
    
    
    public ModelAndView handleMissionIntervention(HttpServletRequest request,
			HttpServletResponse response) throws Exception { 
    	
    	String missionIdAstring = request.getParameter("id");
    	String interventionId = request.getParameter("taskId");
    	String status = request.getParameter("status");
    	String comments = request.getParameter("comments");
		String toDo = request.getParameter("todo");
		
		
		Long missionId =  Long.parseLong(missionIdAstring);
		Long id = Long.parseLong(interventionId);
		Activity activity = activityService.getOneInterventionDetached(id);
		
		
    	if( activity != null){
    		activity.setStatus(status);
    		activity.setComments(comments);
    		activity.setToDo(toDo);
    		activityService.updateOne(activity);
    	}
		
		Map<String,Object> mapResults = new HashMap<String, Object>(); 
		WebContext context =  (WebContext) request.getSession(false).getAttribute("context");
    	Employee caller = context.getCurrentUser();
    	
		MissionDetailsView missionDetails = missionService.buildMissionDetailsViewForId(caller,missionId);
    	request.getSession(false).setAttribute(MISSION_KEY, missionDetails);
    	mapResults.put(MISSION_KEY, missionDetails);
		
    	return new ModelAndView("redirect:/showMissionInterventionsPage.htm?id=" + missionDetails.getMissionId(),mapResults);
    }
    
    public ModelAndView deleteMissionIntervention(HttpServletRequest request,
			HttpServletResponse response) throws Exception { 
    	
	    	String taskIdAstring = request.getParameter("taskId");
	    	Long taskId =  Long.parseLong(taskIdAstring);
	    	String missionIdAstring = request.getParameter("id");
	    	Long missionId =  Long.parseLong(missionIdAstring);
	    	activityService.deleteOne(taskId); 
	    	
	    	Map<String,Object> mapResults = new HashMap<String, Object>(); 
	    	WebContext context =  (WebContext) request.getSession(false).getAttribute("context");
	    	Employee caller = context.getCurrentUser();
	    	
			MissionDetailsView missionDetails = missionService.buildMissionDetailsViewForId(caller,missionId);
	    	request.getSession(false).setAttribute(MISSION_KEY, missionDetails);
	    	mapResults.put(MISSION_KEY, missionDetails);
				    	
	    	return new ModelAndView("redirect:/showMissionPlanningPage.htm?id=" + missionDetails.getMissionId(),mapResults);
    }
    
    public ModelAndView editIntervention(HttpServletRequest request,
			HttpServletResponse response) throws Exception { 
    	
    	String taskId = request.getParameter("taskId");
    	JSONObject ajaxReply = new JSONObject();
    	
		if (taskId != null){	
			
				MissionDetailsView missionDetails = (MissionDetailsView)request.getSession(false).getAttribute(MISSION_KEY) ;
				if(missionDetails == null)
				{			
					ajaxReply.put("result", "nok");
					response.setContentType("application/json;charset=UTF-8");
				}	
				
				Activity task = null;
				for(Activity intervention : missionDetails.getMission().getInterventions()){
					if(intervention.getId().toString().equalsIgnoreCase(taskId)){
						task = intervention;
						break;
					}
				}
				
				if( task != null){
				ajaxReply.put("result", "ok");
				ajaxReply.put("taskId",task.getId().toString());
				
				ajaxReply.put("status",task.getStatus());
				ajaxReply.put("todo",task.getToDo());
				ajaxReply.put("comments",task.getComments());
				
				
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
			logger.error("taskId null");
			return null;
		}
	}
    
    
    
    
    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView showMissionDocumentsPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
 
		return new ModelAndView("mission_documents");		
	}
    
    
    public ModelAndView editMissionDocument(HttpServletRequest request,
			HttpServletResponse response) throws Exception { 
    	
    	String documentId = request.getParameter("documentId");
    	JSONObject ajaxReply = new JSONObject();
    	
		if (documentId != null){	
			
				MissionDetailsView missionDetails = (MissionDetailsView)request.getSession(false).getAttribute(MISSION_KEY) ;
				if(missionDetails == null)
				{			
					ajaxReply.put("result", "nok");
					response.setContentType("application/json;charset=UTF-8");
				}	
				
				Document document = null;
				for(Document document2 : missionDetails.getMission().getDocuments()){
					if(document2.getId().toString().equalsIgnoreCase(documentId)){
						document = document2;
						break;
					}
				}
				
				if( document != null){
					ajaxReply.put("result", "ok");
					ajaxReply.put("documentId",document.getId().toString());
					ajaxReply.put("title",document.getTitle());
					ajaxReply.put("description",document.getDescription());
					ajaxReply.put("fileName",document.getFileName());
					ajaxReply.put("category","contrat");
					ajaxReply.put("owner",document.getCreatedUser().getId());
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
			logger.error("taskId null");
			return null;
		}
    }
    
    public ModelAndView handleMissionDocument(HttpServletRequest request,
			HttpServletResponse response) throws Exception { 
    	
   
		
    	String missionIdAstring = request.getParameter("id");
    	String documentIdAsString = request.getParameter("documentId");
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		//String type = request.getParameter("type");
		//String category = request.getParameter("category");
		String ownerIdAsString = request.getParameter("owner");
		
		MultipartHttpServletRequest multipartRequest = 
			(MultipartHttpServletRequest) request;
		MultipartFile fileReport = multipartRequest.getFile("fileName");
		

		Long documentId = documentIdAsString == null || documentIdAsString.trim().length()==0 ? null:  Long.parseLong(documentIdAsString);
		Long missionId = missionIdAstring == null || missionIdAstring.trim().length()==0 ? null:  Long.parseLong(missionIdAstring);
		Long ownerId = ownerIdAsString == null || ownerIdAsString.trim().length()==0 ? null:  Long.parseLong(ownerIdAsString);
		
		// get the context object
		//WebContext context =  (WebContext) request.getSession(false).getAttribute("context");
	
		Employee owner = userService.getOneDetached(ownerId);
		
		Date createDate = new Date();
		Document document = null;
		
		if(documentId == null){
			 document = new Document(fileReport.getOriginalFilename(),  description, owner,
						 createDate,  title);
			 document = documentService.saveOne(document);
			 Mission mission = missionService.getOneDetached(missionId);
			 saveFile(fileReport,mission, fileReport.getOriginalFilename(), true);
			 
			 mission.getDocuments().add(document);
			 missionService.updateOne(mission);
			 
		}else{
			document = documentService.getOneDetached(documentId);
			document.setDescription(description);
			document.setTitle(title);
			documentService.updateOne(document);
		}
		
		Map<String,Object> mapResults = new HashMap<String, Object>(); 
		WebContext context =  (WebContext) request.getSession(false).getAttribute("context");
    	Employee caller = context.getCurrentUser();
		MissionDetailsView missionDetails = missionService.buildMissionDetailsViewForId(caller,missionId);
    	request.getSession(false).setAttribute(MISSION_KEY, missionDetails);
    	mapResults.put(MISSION_KEY, missionDetails);
		
    	return new ModelAndView("redirect:/showMissionDocumentsPage.htm?id=" + missionDetails.getMissionId(),mapResults);
    }
    
    private void saveFile(MultipartFile file,Mission mission, String fileName, boolean create) throws IOException {
    	
    	//File documents_directory = new File(getServletContext().getRealPath("/documents/")); 
    	File documents_directory = new File(getRootDocumentPath());    	 
    	if (!documents_directory.exists()) {
    		documents_directory.mkdir();
		}
    	
    	//File exercise_directory = new File(getServletContext().getRealPath("/documents/"+mission.getExercise())); 
    	File exercise_directory = new File(getRootDocumentPath()+mission.getExercise());
    	if (!exercise_directory.exists()) {
    		exercise_directory.mkdir();
		}
    	
    	//File mission_directory = new File(getServletContext().getRealPath("/documents/"+mission.getExercise()+"/"+mission.getReference()));    	
    	File mission_directory = new File(getRootDocumentPath()+mission.getExercise()+"/"+mission.getReference());
    	if (!mission_directory.exists()) {
    		mission_directory.mkdir();
		}
		
    	
		//File destination_file = new File(getServletContext().getRealPath("/documents/"+mission.getExercise()+"/"+mission.getReference()+ "/" + fileName));
		File destination_file = new File(getRootDocumentPath()+mission.getExercise()+"/"+mission.getReference()+ "/" + fileName);
		if (create) {
			if (destination_file.exists()) {
				//TODO do something
			}
			
			
			try{
				destination_file.createNewFile();
			}
			catch(IOException ioe){
				logger.error(ioe);
			}
			catch(SecurityException se){
				logger.error(se);
			}
			
		}
		file.transferTo(destination_file);
	}
	
    
    public ModelAndView deleteMissionDocument(HttpServletRequest request,
			HttpServletResponse response) throws Exception { 
    	
    	return new ModelAndView("mission_documents");
    }
    
    
    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView showMissionDepensesPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
 
		return new ModelAndView("mission_depenses");		
	}
    
    public ModelAndView showMissionAlertes(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
 
		return new ModelAndView("mission_alertes");		
	}
    
    
    
    public ModelAndView editMissionCost(HttpServletRequest request,
			HttpServletResponse response) throws Exception { 
    	
    	String costId = request.getParameter("costId");
    	JSONObject ajaxReply = new JSONObject();
    	
		if (costId != null){	
			
				MissionDetailsView missionDetails = (MissionDetailsView)request.getSession(false).getAttribute(MISSION_KEY) ;
				if(missionDetails == null)
				{			
					ajaxReply.put("result", "nok");
					response.setContentType("application/json;charset=UTF-8");
				}	
				
				Cost cost = null;
				for(Cost cost2 : missionDetails.getMission().getExtraCosts()){
					if(cost2.getId().toString().equalsIgnoreCase(costId)){
						cost = cost2;
						break;
					}
				}
				
				if( cost != null){
					ajaxReply.put("result", "ok");
					ajaxReply.put("costId",cost.getId().toString());
					ajaxReply.put("reason",cost.getCostCode());
					ajaxReply.put("amount",cost.getAmount());
					ajaxReply.put("currency",cost.getCurrencyCode());
					ajaxReply.put("description",cost.getDescription());
					ajaxReply.put("owner",cost.getOwner().getId());
					ajaxReply.put("createdate",cost.getCreateDate());
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
			logger.error("taskId null");
			return null;
		}
    }
    
    public ModelAndView editMissionAlerte(HttpServletRequest request,
			HttpServletResponse response) throws Exception { 
    	
    	String alerteId = request.getParameter("alerteId");
    	JSONObject ajaxReply = new JSONObject();
    	
		if (alerteId != null){	
			
				MissionDetailsView missionDetails = (MissionDetailsView)request.getSession(false).getAttribute(MISSION_KEY) ;
				if(missionDetails == null)
				{			
					ajaxReply.put("result", "nok");
					response.setContentType("application/json;charset=UTF-8");
				}	
				
				Alerte alerte = null;
				for(Alerte alerte2 : missionDetails.getMission().getAlertes()){
					if(alerte2.getId().toString().equalsIgnoreCase(alerteId)){
						alerte = alerte2;
						break;
					}
				}
				
				if( alerte != null){
					ajaxReply.put("result", "ok");
					ajaxReply.put("alerteId",alerte.getId().toString());					
					ajaxReply.put("amount",alerte.getAmount());
					ajaxReply.put("description",alerte.getDescription());
					ajaxReply.put("owner",alerte.getOwner().getId());
					ajaxReply.put("createdate",alerte.getCreateDate());
					ajaxReply.put("sentdate",alerte.getSentDate());
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
			logger.error("alerteId null");
			return null;
		}
    }
    
    public ModelAndView handleMissionAlerte(HttpServletRequest request,
			HttpServletResponse response) throws Exception { 
    	
    	
    	String missionIdAstring = request.getParameter("id");
    	String alerteIdAsString = request.getParameter("alerteId");
		String description = request.getParameter("description");
		String amountAsString = request.getParameter("amount");
		String createdateAsString = DateUtils.dateAsString();
		
		double amount = amountAsString == null || amountAsString.trim().length()==0 ? null:  Double.parseDouble(amountAsString);
		Long alerteId = alerteIdAsString == null || alerteIdAsString.trim().length()==0 ? null:  Long.parseLong(alerteIdAsString);
		Long missionId = missionIdAstring == null || missionIdAstring.trim().length()==0 ? null:  Long.parseLong(missionIdAstring);
		
		WebContext context =  (WebContext) request.getSession(false).getAttribute("context");
    	Employee owner = context.getCurrentUser();

		Mission mission = missionService.getOneDetached(missionId);
		
		int countAlerte = mission.getAlertes().size() +1;
		
		Alerte alerte = null;
		if(alerteId == null){ //Create a new alerte
			alerte =  new Alerte(countAlerte , amount,description,  owner,mission);
			mission.getAlertes().add(alerte);
			missionService.updateOne(mission);
		}else{//Update an existing cost
			for(Alerte alerte2 : mission.getAlertes()){
				if(alerte2.getId().toString().equalsIgnoreCase(alerteIdAsString)){
					alerte = alerte2;
					alerte.setAmount(amount);
					alerte.setDescription(description);
					missionService.updateOne(mission);
					break;
				}
			}
			
		}
		
		Map<String,Object> mapResults = new HashMap<String, Object>(); 
		
		MissionDetailsView missionDetails = missionService.buildMissionDetailsViewForId(owner,missionId);
		request.getSession(false).setAttribute(MISSION_KEY, missionDetails);
		mapResults.put(MISSION_KEY, missionDetails);
		return new ModelAndView("redirect:/showMissionAlertesPage.htm?id=" + missionDetails.getMissionId(),mapResults);
		 
    }
    
    public ModelAndView handleMissionCost(HttpServletRequest request,
			HttpServletResponse response) throws Exception { 
    	
    	
    	String missionIdAstring = request.getParameter("id");
    	String costIdAsString = request.getParameter("costId");
		String reason = request.getParameter("reason");
		String description = request.getParameter("description");
		//String type = request.getParameter("type");
		String amountAsString = request.getParameter("amount");
		/*String createdateAsString = request.getParameter("createdate");*/
		String createdateAsString = DateUtils.dateAsString();
		String currency = "EUR";/*request.getParameter("currency");*/
		//String status = request.getParameter("status");
		String ownerIdAsString = request.getParameter("owner");
		
		
		double amount = amountAsString == null || amountAsString.trim().length()==0 ? null:  Double.parseDouble(amountAsString);
		Long costId = costIdAsString == null || costIdAsString.trim().length()==0 ? null:  Long.parseLong(costIdAsString);
		Long missionId = missionIdAstring == null || missionIdAstring.trim().length()==0 ? null:  Long.parseLong(missionIdAstring);
		Long ownerId = ownerIdAsString == null || ownerIdAsString.trim().length()==0 ? null:  Long.parseLong(ownerIdAsString);
		
		// create a new assist context object
		//WebContext context =  (WebContext) request.getSession(false).getAttribute("context");;
	
		Employee owner = userService.getOne(ownerId);
		
		Date createDate = DateUtils.getDate(createdateAsString,"dd/MM/yyyy");
		
		Mission mission = missionService.getOneDetached(missionId);
		
		Cost cost = null;
		if(costId == null){ //Create a new cost
			cost =  new Cost(reason,description,amount,currency,  createDate,  owner,mission);
			mission.getExtraCosts().add(cost);
			missionService.updateOne(mission);
		}else{//Update an existing cost
			for(Cost cost2 : mission.getExtraCosts()){
				if(cost2.getId().toString().equalsIgnoreCase(costIdAsString)){
					cost = cost2;
					cost.setAmount(amount);
					cost.setCostCode(reason);
					cost.setCreateDate(createDate);
					cost.setCurrencyCode(currency);
					cost.setDescription(description);
					cost.setOwner(owner);
					missionService.updateOne(mission);
					break;
				}
			}			
		}
		
		Map<String,Object> mapResults = new HashMap<String, Object>(); 
		WebContext context =  (WebContext) request.getSession(false).getAttribute("context");
    	Employee caller = context.getCurrentUser();
		MissionDetailsView missionDetails = missionService.buildMissionDetailsViewForId(caller,missionId);
		request.getSession(false).setAttribute(MISSION_KEY, missionDetails);
		mapResults.put(MISSION_KEY, missionDetails);
		return new ModelAndView("redirect:/showMissionDepensesPage.htm?id=" + missionDetails.getMissionId(),mapResults);
		 
    }
    
    public ModelAndView markMissionAsClosed(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	ArrayList<String> messages = new ArrayList<String>();
    	String missionIdAstring = request.getParameter("id");    	
		Long missionId = missionIdAstring == null || missionIdAstring.trim().length()==0 ? null:  Long.parseLong(missionIdAstring);
		Exercise currentExercise = (Exercise) getServletContext().getAttribute("currentExercise");
		Mission mission = null;
		try{
			
			boolean hasError = false;
			List<TimesheetData> timesheets = timesheetService.getAllTimesheetToValidateForProject(missionId);
			if( timesheets != null){			
				
				for(TimesheetData timesheetData : timesheets){
					if(!timesheetData.getStatus().equalsIgnoreCase(Constants.TIMESHEET_STATUS_STRING_VALIDATED)){
							messages.add("Please validate all the reports activity listed below in order to proceed further...");
							request.getSession(false).setAttribute("listTimesheetsToValidate", timesheets);						
							hasError = true;
							break;
					}
				}
			}
			
			//We must stop if we have found some errors
			if(hasError){
				request.getSession(false).setAttribute("actionErrors", messages);	
				return new ModelAndView("redirect:/showAdminitrateMissionPage.htm?id=" + missionId);
			}
			else{
				mission = missionService.markMissionAsClosed(missionId);	
				messages.add("The mission : " + mission.getReference() + " has been successfully closed");			
				request.getSession(false).setAttribute("successMessage", messages);		
			}
			
				
		}
		catch(Exception e){
			messages.add("Failed to close  mission with : " + mission.getReference() );			
			request.getSession(false).setAttribute("actionErrors", messages);			
		}
		
		 String type =  (String)request.getSession(false).getAttribute("budget_type");
		 String origin = (String)request.getSession(false).getAttribute("budget_origin");
		 String manager = (String)request.getSession(false).getAttribute("budget_manager");
		 String associe = (String)request.getSession(false).getAttribute("budget_associe");
		 //String toCloseKey = (String)request.getSession(false).getAttribute("TO_CLOSE");
		 String startingLetterName = (String)request.getSession(false).getAttribute("startingLetterName");
		 
		 StringBuffer urlCompletion =  new StringBuffer();
		 if(type !=null && type.length()>0){
			 urlCompletion.append("&type=" + type);
		 }
		 if(origin !=null && origin.length()>0){
			 urlCompletion.append("&origin=" + origin);
		 }
		 if(manager !=null && manager.length()>0){
			 urlCompletion.append("&manager=" + manager);
		 }
		 if(associe !=null && associe.length()>0){
			 urlCompletion.append("&associe=" + associe);
		 }
		 /*
		 if(toCloseKey !=null && toCloseKey.length()>0){
			 urlCompletion.append("&toCloseKey=" + toCloseKey);
		 }
		 */
		 if(startingLetterName !=null && startingLetterName.length()>0){
			 urlCompletion.append("&startingLetterName=" + startingLetterName);
		 }
		
		return new ModelAndView("redirect:/showGeneralBudgetPage.htm?year_" + currentExercise.getYear()+"="+ currentExercise.getYear()+ "&forceReload=true" + urlCompletion.toString());
    	
    }
    
    public ModelAndView deleteMissionCost(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	
    	String missionIdAstring = request.getParameter("id");
    	String costIdAsString = request.getParameter("costId");
    	//Long costId = costIdAsString == null || costIdAsString.trim().length()==0 ? null:  Long.parseLong(costIdAsString);
		Long missionId = missionIdAstring == null || missionIdAstring.trim().length()==0 ? null:  Long.parseLong(missionIdAstring);
		Mission mission = missionService.getOneDetached(missionId);
		boolean update = false;
		if(mission != null){
	
			//Search for the matching cost
			for(Cost cost : mission.getExtraCosts()){
				if(cost.getId().toString().equalsIgnoreCase(costIdAsString)){
					cost.setMission(null);					
					mission.getExtraCosts().remove(cost);
					missionService.deleteOneCost(cost.getId());
					missionService.updateOne(mission);
					update =true;
					break;
				}
			}			
		}
		
		if( update == true){
			Map<String,Object> mapResults = new HashMap<String, Object>(); 
			WebContext context =  (WebContext) request.getSession(false).getAttribute("context");
	    	Employee caller = context.getCurrentUser();
			MissionDetailsView missionDetails = missionService.buildMissionDetailsViewForId(caller,missionId);
			request.getSession(false).setAttribute(MISSION_KEY, missionDetails);
			mapResults.put(MISSION_KEY, missionDetails);
			return new ModelAndView("redirect:/showMissionDepensesPage.htm?id=" + missionDetails.getMissionId(),mapResults);
		}else{
			return new ModelAndView("mission_depenses");
		}
    	
    	
    }
    
    
    public ModelAndView deleteMissionAlerte(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	
    	String missionIdAstring = request.getParameter("id");
    	String alerteIdAsString = request.getParameter("alerteId");
		Long missionId = missionIdAstring == null || missionIdAstring.trim().length()==0 ? null:  Long.parseLong(missionIdAstring);
		Mission mission = missionService.getOneDetached(missionId);
		boolean update = false;
		if(mission != null){
	
			//Search for the matching cost
			for(Alerte alerte : mission.getAlertes()){
				if(alerte.getId().toString().equalsIgnoreCase(alerteIdAsString)){
					//alerte.setMission(null);					
					//mission.getAlertes().remove(alerte);
					//missionService.deleteOneAlerte(alerte.getId());
					alerte.setStatus(Alerte.CANCELLED);
					missionService.updateOne(mission);
					update =true;
					break;
				}
			}
			
		}
		
		if( update == true){
			Map<String,Object> mapResults = new HashMap<String, Object>(); 
			WebContext context =  (WebContext) request.getSession(false).getAttribute("context");
	    	Employee caller = context.getCurrentUser();
			MissionDetailsView missionDetails = missionService.buildMissionDetailsViewForId(caller,missionId);
			request.getSession(false).setAttribute(MISSION_KEY, missionDetails);
			mapResults.put(MISSION_KEY, missionDetails);
			return new ModelAndView("redirect:/showMissionAlertesPage.htm?id=" + missionDetails.getMissionId(),mapResults);
		}else{
			return new ModelAndView("mission_alertes");
		}
    	
    	
    }
    
    public ModelAndView editPlanningActivitiesAsAjaxStream(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
    	JSONObject ajaxReply = new JSONObject();
    	JSONArray eventList = null;
    	//eventList =  (JSONArray)request.getSession(false).getAttribute("ActivitiesPlanningAsAjaxStream");
    	//if(eventList == null){
    		
        	eventList = new JSONArray();
        	JSONObject ajaxObject = null;
        	
	        	WebContext context =  (WebContext) request.getSession(false).getAttribute("context");				
	        	Integer currentYear = exerciseService.getFirstOnGoingExercise();
				 if(currentYear == null){
					//currentYear = exerciseService.getMaxYear();
	 				 currentYear =  exerciseService.getLastClosedExercise();
					 if(currentYear == null){
						Calendar c = Calendar.getInstance(); 
						currentYear =c.get(Calendar.YEAR);
					 } 
				 }
				//String week = Integer.toString(c.get(Calendar.WEEK_OF_YEAR));
				//String employeeIdentifier = context.getCurrentUser().getId().toString();
				
        	
    			for(Option option : missionService.getTaskOptions()){
        			ajaxObject = new JSONObject();
            		ajaxObject.put("name", option.getName().toLowerCase());
            		ajaxObject.put("id", option.getId());
            		eventList.put(ajaxObject);
        		}
    		
    		
    			List<Option> missionOptions = missionService.getAllOpenMissionForYearAsOptions( context.getCurrentUser(), Integer.toString(currentYear));
	    		for(Option option : missionOptions){
	    			ajaxObject = new JSONObject();
	        		ajaxObject.put("name", option.getName().toLowerCase());
	        		ajaxObject.put("id", option.getId());
	        		eventList.put(ajaxObject);
	    		}
    		
    		
	    		//request.getSession(false).setAttribute("ActivitiesPlanningAsAjaxStream", eventList);
    		
    	//}
    	
		ajaxReply.put("events", eventList);
    	response.setContentType("text/json;charset=UTF-8");
    	ServletOutputStream out = response.getOutputStream();
		
    	String reply = ajaxReply.toString();
		out.write(reply.getBytes("UTF-8"));
		out.flush();
		out.close();
		return null;
    }
    
    
    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView showMissionCommunicationsPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView("mission_communications");		
	}
    
    public ModelAndView editMissionMessage(HttpServletRequest request,
			HttpServletResponse response) throws Exception { 
    	
    	String messageId = request.getParameter("messageId");
    	JSONObject ajaxReply = new JSONObject();
    	
		if (messageId != null){	
			
				//MissionDetailsView missionDetails = (MissionDetailsView)request.getSession(false).getAttribute(MISSION_KEY) ;
				MessagerieView messages = (MessagerieView)request.getSession(false).getAttribute("messages") ;
				
				if(messages == null)
				{			
					ajaxReply.put("result", "nok");
					response.setContentType("application/json;charset=UTF-8");
				}	
				/*
				MessageData(Long id, String subject, String contents, String from,
						Long fromId, String to, Long toId, Date createDate, Date sentDate,
						Long missionId, String customerName, boolean read) 
						*/
				
				MessageData message = null;
				for(MessageData message2 : messages.getMessages()){
					if(message2.getId().toString().equalsIgnoreCase(messageId)){
						message = message2;
						break;
					}
				}
				
				if( message != null){
					ajaxReply.put("result", "ok");
					ajaxReply.put("messageId",message.getId().toString());
					ajaxReply.put("recipient",message.getToId());
					ajaxReply.put("subject",message.getSubject());
					ajaxReply.put("description",/*message.getContents()*/message.getContentsTextHtmlFormatted());
					ajaxReply.put("createdate",message.getCreateDate());
					ajaxReply.put("from",message.getFromId());
					ajaxReply.put("missionId",message.getMissionId());
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
			logger.error("taskId null");
			return null;
		}
    }
    
    public ModelAndView handleMissionMessage(HttpServletRequest request,
			HttpServletResponse response) throws Exception { 
    	
    	String missionIdAstring = request.getParameter("missionId");
    	String messageIdAsString = request.getParameter("messageId");
		//String recipientIdAsString = request.getParameter("recipient");
		String subject = request.getParameter("object");
		String contents = request.getParameter("editor");
		String emailList = request.getParameter("emailList");
		String messageParentIdAstring = request.getParameter("messageParentId");
		
		//Long recipientId = recipientIdAsString == null || recipientIdAsString.trim().length()==0 ? null:  Long.parseLong(recipientIdAsString);
		Long messageId = messageIdAsString == null || messageIdAsString.trim().length()==0 ? null:  Long.parseLong(messageIdAsString);
		Long missionId = missionIdAstring == null || missionIdAstring.trim().length()==0 ? null:  Long.parseLong(missionIdAstring);
		
		// create a new assist context object
		WebContext context =  (WebContext) request.getSession(false).getAttribute("context");;
		
		Message parent = null;
		if(messageParentIdAstring != null){
			parent = missionService.getOneMessageDetached(Long.valueOf(messageParentIdAstring));
		}
			
		Employee from = userService.getOne(context.getCurrentUser().getId());
		
		Mission mission = missionService.getOneDetached(missionId);
		
		Message message = null;
		if(messageId == null){
			
			//Save a new message
			message = new Message( parent,  subject,  contents,from,  emailList);
			//message.setEmailsList(emailList);
			message.setMission(mission);
			mission.getMessages().add(message);
			missionService.updateOne(mission);
			
			//Save a communication email for the message
			java.util.StringTokenizer tokenizer = new java.util.StringTokenizer(emailList,";");
			while ( tokenizer.hasMoreTokens() ) {
			    String toEmail =  (String)tokenizer.nextToken();
			    EmailData emailData = new EmailData( from.getEmail(), toEmail,subject, contents, EmailData.TYPE_MISSION_COMMUNICATION);
				emailService.saveOne(emailData);
			} 			
		}
		
		Map<String,Object> mapResults = new HashMap<String, Object>();
		
    	Employee caller = context.getCurrentUser();
		MissionDetailsView missionDetails = missionService.buildMissionDetailsViewForId(caller,missionId);
		request.getSession(false).setAttribute(MISSION_KEY, missionDetails);
		mapResults.put(MISSION_KEY, missionDetails);
		return new ModelAndView("redirect:/showMissionCommunicationsPage.htm?id=" + missionDetails.getMissionId(),mapResults);
		 
    }
    
    
    
    /***Factures***/
    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView showMissionInvoicePage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView("mission_invoices");		
	}
    
    public ModelAndView editMissionInvoice(HttpServletRequest request,
			HttpServletResponse response) throws Exception { 
    	
    	String invoiceId = request.getParameter("invoiceId");
    	JSONObject ajaxReply = new JSONObject();
    	
		if (invoiceId != null){	
			
				MissionDetailsView missionDetails = (MissionDetailsView)request.getSession(false).getAttribute(MISSION_KEY) ;
				if(missionDetails == null)
				{			
					ajaxReply.put("result", "nok");
					response.setContentType("application/json;charset=UTF-8");
				}	
				
				Invoice invoice = null;
				for(Invoice invoice2 : missionDetails.getMission().getFactures()){
					if(invoice2.getId().toString().equalsIgnoreCase(invoiceId)){
						invoice = invoice2;
						break;
					}
				}
				
				if( invoice != null)
				{
					ajaxReply.put("result", "ok");
					ajaxReply.put("invoiceId",invoice.getId().toString());
					ajaxReply.put("libelle",invoice.getLibelle());
					ajaxReply.put("exercise",invoice.getExercise());
					ajaxReply.put("currency",invoice.getCurrencyCode());
					ajaxReply.put("amount",invoice.getAmountEuro());
					ajaxReply.put("type",invoice.getType());
					ajaxReply.put("status",invoice.getStatus());
					ajaxReply.put("reference",invoice.getReference());					
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
				logger.error("invoiceId null");
				return null;
		}
    }
    
    public ModelAndView deleteMissionInvoice(HttpServletRequest request,
			HttpServletResponse response) throws Exception { 
    	
    	String invoiceIdAstring = request.getParameter("invoiceId");
    	Long invoiceId =  Long.parseLong(invoiceIdAstring);
    	String missionIdAstring = request.getParameter("id");
    	Long missionId =  Long.parseLong(missionIdAstring);
    	factureService.deleteOne(invoiceId); 
    	
    	Map<String,Object> mapResults = new HashMap<String, Object>(); 
    	WebContext context =  (WebContext) request.getSession(false).getAttribute("context");
    	Employee caller = context.getCurrentUser();
		MissionDetailsView missionDetails = missionService.buildMissionDetailsViewForId(caller,missionId);
    	request.getSession(false).setAttribute(MISSION_KEY, missionDetails);
    	mapResults.put(MISSION_KEY, missionDetails);
		
    	return new ModelAndView("redirect:/showMissionFacturesPage.htm?id=" + missionDetails.getMissionId(),mapResults);
 
    }
    
    public ModelAndView handleMissionInvoice(HttpServletRequest request,
			HttpServletResponse response) throws Exception { 
    	
    	String missionIdAstring = request.getParameter("id");
		String invoiceIdAsString = request.getParameter("invoiceId");
		String libelle = request.getParameter("libelle");
		//String exercise = request.getParameter("exercise");
		//String type = request.getParameter("type");
		String amountAsString = request.getParameter("amount");
		//String enddate = request.getParameter("enddate");
		//String currency = request.getParameter("currency");
		//String status = request.getParameter("status");
		
		double amount = amountAsString == null || amountAsString.trim().length()==0 ? null:  Double.parseDouble(amountAsString.trim());
		Long invoiceId = invoiceIdAsString == null || invoiceIdAsString.trim().length()==0 ? null:  Long.parseLong(invoiceIdAsString);
		Long missionId = missionIdAstring == null || missionIdAstring.trim().length()==0 ? null:  Long.parseLong(missionIdAstring);
		
		//Mission project = missionService.getOneDetached(missionId);
	   
	    Invoice facture = null;
		if(invoiceId == null){
			facture = null;//factureService.createAdvance( project, libelle, type,  amount);
		}else{
			facture = factureService.getOneDetached(invoiceId);
			facture.setAmountEuro(amount);
			facture.setCurrencyCode("EUR");
			facture.setLibelle(libelle);
			facture = factureService.updateOne(facture);
			factureService.updateExerciceAndBudget(facture);
		
		}
		
		Map<String,Object> mapResults = new HashMap<String, Object>(); 
		WebContext context =  (WebContext) request.getSession(false).getAttribute("context");
    	Employee caller = context.getCurrentUser();
		MissionDetailsView missionDetails = missionService.buildMissionDetailsViewForId(caller,missionId);
    	request.getSession(false).setAttribute(MISSION_KEY, missionDetails);
    	mapResults.put(MISSION_KEY, missionDetails);
		
    	return new ModelAndView("redirect:/showMissionFacturesPage.htm?id=" + missionDetails.getMissionId(),mapResults);
		
		
    }
    
    
    
    public ModelAndView downloadMissionDocument(HttpServletRequest request, HttpServletResponse response) throws Exception{
    	
    	try{
    	
	     	String documentIdAsString = request.getParameter("documentId");
	     	Long documentId = documentIdAsString == null || documentIdAsString.trim().length()==0 ? null:  Long.parseLong(documentIdAsString);
	     	Document document = null;
	     	document = documentService.getOneDetached(documentId);
	     	String fileName = document.getFileName();
	     	MissionDetailsView missionDetails = (MissionDetailsView)request.getSession(false).getAttribute(MISSION_KEY) ;
			if(missionDetails == null)
			{			
				return null;
			}	
			Mission mission = missionDetails.getMission();
			File destination_file = new File(getRootDocumentPath()+mission.getExercise()+"/"+mission.getReference()+ "/" + fileName);
	     	/*File destination_file = new File(getServletContext()
					.getRealPath("/documents/"+mission.getExercise()+"/"+mission.getReference()+ "/" + fileName));
					*/
     
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName+ "\";");
	
	        response.setContentType("application/octet-stream");
	
	        OutputStream os = response.getOutputStream();
	        
	        InputStream is = new FileInputStream(destination_file);
			if (is != null)
			{
				try
				{
					org.springframework.util.FileCopyUtils.copy(is, os);
				}
				catch (IOException e)
				{
					//throw new ModelException(e.getMessage());
					//throw new DataAccessException(e.getMessage());
				}
			}
	        
	                                                                      
	    	return null;
			
		}catch(Exception e){
			logger.error(e);
			return null;
		}
    }
    
public ModelAndView downloadHelpDocument(HttpServletRequest request, HttpServletResponse response) throws Exception{
    	
    	try{
    	
    		
			File destination_file = new File(getRootDocumentPath());
	     	
			response.setHeader("Content-Disposition", "attachment; filename=\"" + destination_file.getName()+ "\";");
	
	        response.setContentType("application/octet-stream");
	
	        OutputStream os = response.getOutputStream();
	        
	        InputStream is = new FileInputStream(destination_file);
			if (is != null)
			{
				try
				{
					org.springframework.util.FileCopyUtils.copy(is, os);
				}
				catch (IOException e)
				{
					//throw new ModelException(e.getMessage());
					//throw new DataAccessException(e.getMessage());
				}
			}
	        
	                                                                      
	    	return null;
			
		}catch(Exception e){
			logger.error(e);
			return null;
		}
    }
    
  
    
    public ModelAndView changeFieldMissionStatus(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	
    	String status = request.getParameter("value");
		String idAsString = request.getParameter("id");
		
		Long id = Long.parseLong(idAsString);
		Mission mission = getMissionService().getOneDetached(id);
		
		
    	if( mission != null){
    		mission.setStatus(status);
    		getMissionService().updateOne(mission);
    	}
				
    	response.getWriter().append(status).flush();
		return null;
    	
    }
    /***************/
    
   
	public IMissionService getMissionService() {
		return missionService;
	}


	public void setMissionService(IMissionService missionService) {
		this.missionService = missionService;
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


	public IFactureService getFactureService() {
		return factureService;
	}


	public void setFactureService(IFactureService factureService) {
		this.factureService = factureService;
	}


	public IDocumentService getDocumentService() {
		return documentService;
	}


	public void setDocumentService(IDocumentService documentService) {
		this.documentService = documentService;
	}

	public IEmailService getEmailService() {
		return emailService;
	}

	public void setEmailService(IEmailService emailService) {
		this.emailService = emailService;
	}

	public String getRootDocumentPath() {
		return rootDocumentPath;
	}

	public void setRootDocumentPath(String rootDocumentPath) {
		this.rootDocumentPath = rootDocumentPath;
	}

	public ITimesheetService getTimesheetService() {
		return timesheetService;
	}

	public void setTimesheetService(ITimesheetService timesheetService) {
		this.timesheetService = timesheetService;
	}

	public IExerciseService getExerciseService() {
		return exerciseService;
	}

	public void setExerciseService(IExerciseService exerciseService) {
		this.exerciseService = exerciseService;
	}

	
}
