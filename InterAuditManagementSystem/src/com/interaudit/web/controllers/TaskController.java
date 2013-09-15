package com.interaudit.web.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.interaudit.domain.model.Activity;
import com.interaudit.domain.model.Document;
import com.interaudit.domain.model.Employee;
import com.interaudit.domain.model.Message;
import com.interaudit.domain.model.Mission;
import com.interaudit.service.IActivityService;
import com.interaudit.service.IDocumentService;
import com.interaudit.service.IMissionService;
import com.interaudit.service.IUserService;
import com.interaudit.service.impl.PlanningService;
import com.interaudit.service.param.SearchActivityParam;
import com.interaudit.service.view.ActivityView;
import com.interaudit.service.view.InterventionDetailsView;
import com.interaudit.util.DateUtils;
import com.interaudit.util.WebContext;



/**
 * The Welcome action.
 * 
 * @author Julien Dubois
 */
public class TaskController extends MultiActionController {

   // private final Log log = LogFactory.getLog(HandleBrowseDatasetPageController.class);
    
	private IActivityService service;
	private IMissionService missionService;
	private IUserService userService;
	private IDocumentService documentService;
	
	
	private static final String ACTIVITIES_KEY = "viewactivities";
	private static final String ACTIVITY_KEY = "activityDetails";
	
	public static final String PENDING_KEY ="PENDING";
	public static final String ONGOING_KEY ="ONGOING";
	public static final String STOPPED_KEY ="STOPPED";
	public static final String CLOSED_KEY ="CLOSED";
	public static final String CANCELLED_KEY ="CANCELLED";
	
	public static final String YEAR_KEY = "activities_year";
	public static final String EMPLOYEE_KEY = "activities_employee";
	public static final String CUSTOMER_KEY = "task_customer";
	private static final Logger  LOGGER  = Logger.getLogger(TaskController.class);
    
    
    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView showTasksPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
    	Map<String,Object> mapResults = new HashMap<String, Object>();
    	
		String year = request.getParameter(YEAR_KEY);
		String employee = request.getParameter(EMPLOYEE_KEY);
		
		
		//Defaults to current year
		if(year == null){
			 Calendar c = Calendar.getInstance();
	         Integer value = c.get(Calendar.YEAR);
	         year = value.toString();
		}
		
		if(employee != null && employee.equalsIgnoreCase("-1")){
			employee = null;
		}
		
    	
    	SearchActivityParam param = new SearchActivityParam( year, employee == null? null: Long.valueOf(employee));
    	
    	ActivityView view = service.searchActivities(param);
    	
    	mapResults.put(ACTIVITIES_KEY, view);
		return new ModelAndView("list_activities",mapResults);		
	}
    
    
    
    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView showTaskPropertiesPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
    	Map<String,Object> mapResults = new HashMap<String, Object>(); 
    	String taskId = request.getParameter("id");
    	InterventionDetailsView interventionDetailsView =  service.buildInterventionDetailsView (Long.valueOf(taskId));
    	
    	request.getSession(false).setAttribute(ACTIVITY_KEY, interventionDetailsView);
    	mapResults.put(ACTIVITY_KEY, interventionDetailsView);
		return new ModelAndView("task_proprietes");		
	}
    
    
    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView showTaskDocumentsPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView("task_documents");		
	}
    
      
   
   
    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView editTaskDocument(HttpServletRequest request,
			HttpServletResponse response) throws Exception { 
    	
    	String documentId = request.getParameter("documentId");
    	JSONObject ajaxReply = new JSONObject();
    	
		if (documentId != null){	
			
			    InterventionDetailsView interventionDetailsView = (InterventionDetailsView)request.getSession(false).getAttribute(ACTIVITY_KEY) ;
				if(interventionDetailsView == null)
				{			
					ajaxReply.put("result", "nok");
					response.setContentType("application/json;charset=UTF-8");
				}	
				
				Document document = null;
				for(Document document2 : interventionDetailsView.getDocuments()){
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
			LOGGER.error("taskId null");
			return null;
		}
    }
    
    public ModelAndView handleTaskDocument(HttpServletRequest request,
			HttpServletResponse response) throws Exception { 
    	
    	String taskIdAsString = request.getParameter("taskId");
    	String documentIdAsString = request.getParameter("documentId");
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		String type = request.getParameter("type");
		String category = request.getParameter("category");
		String ownerIdAsString = request.getParameter("owner");
		
		MultipartHttpServletRequest multipartRequest = 
			(MultipartHttpServletRequest) request;
		MultipartFile fileReport = multipartRequest.getFile("fileName");
		

		Long documentId = documentIdAsString == null || documentIdAsString.trim().length()==0 ? null:  Long.parseLong(documentIdAsString);
		Long taskId =  taskIdAsString == null || taskIdAsString.trim().length()==0 ? null:  Long.parseLong(taskIdAsString);
		Long ownerId = ownerIdAsString == null || ownerIdAsString.trim().length()==0 ? null:  Long.parseLong(ownerIdAsString);
		
		InterventionDetailsView interventionDetailsView = (InterventionDetailsView)request.getSession(false).getAttribute(ACTIVITY_KEY) ;
					
		
		//Intervention intervention = (Intervention)service.getOneInterventionDetached(taskId);
		Mission mission = missionService.getOneDetachedFromReference(interventionDetailsView.getInterventionData().getMissionCode());
		
		// create a new assist context object
		WebContext context =  (WebContext) request.getSession(false).getAttribute("context");
	
		Employee owner = userService.getOneDetached(ownerId);
		
		Date createDate = new Date();
		Document document = null;
		
		if(documentId == null){
			 document = new Document(fileReport.getOriginalFilename(),  description, owner,
						 createDate,  title);
			 document = documentService.saveOne(document);
			 saveFile(fileReport, fileReport.getOriginalFilename(), true);
			 mission.getDocuments().add(document);
			 missionService.updateOne(mission);
			 
		}else{
			document = documentService.getOneDetached(documentId);
			document.setDescription(description);
			document.setTitle(title);
			documentService.updateOne(document);
		}
		
		Map<String,Object> mapResults = new HashMap<String, Object>(); 
    	interventionDetailsView =  service.buildInterventionDetailsView (taskId);
    	
    	request.getSession(false).setAttribute(ACTIVITY_KEY, interventionDetailsView);
    	mapResults.put(ACTIVITY_KEY, interventionDetailsView);
		
		return new ModelAndView("redirect:/showTaskDocumentsPage.htm?id=" + taskId,mapResults);
    	
    }
    
    private void saveFile(MultipartFile file, String fileName, boolean create) throws IOException {
		File destination_file = new File(getServletContext()
				.getRealPath("/images/" + fileName));
		if (create) {
			if (destination_file.exists()) {
				//TODO do something
			}
			destination_file.createNewFile();
		}
		file.transferTo(destination_file);
	}
    
    public ModelAndView deleteTaskDocument(HttpServletRequest request,
			HttpServletResponse response) throws Exception { 
    	return new ModelAndView("task_documents");
    }
    
 public ModelAndView downloadTaskDocument(HttpServletRequest request, HttpServletResponse response) throws Exception{
    	
    	try{
    	 
	     	String documentIdAsString = request.getParameter("documentId");
	     	Long documentId = documentIdAsString == null || documentIdAsString.trim().length()==0 ? null:  Long.parseLong(documentIdAsString);
	     	Document document = null;
	     	document = documentService.getOneDetached(documentId);
	     	String fileName = document.getFileName();
	     	File destination_file = new File(getServletContext()
					.getRealPath("/images/" + fileName));
     
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
    
    
    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView showTaskCommunicationsPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView("task_communications");		
	}
    
    
	
    public ModelAndView editTaskMessage(HttpServletRequest request,
			HttpServletResponse response) throws Exception { 
    	
    	String messageId = request.getParameter("messageId");
    	JSONObject ajaxReply = new JSONObject();
    	
		if (messageId != null){	
			
				InterventionDetailsView interventionDetailsView = (InterventionDetailsView)request.getSession(false).getAttribute(ACTIVITY_KEY) ;
				if(interventionDetailsView == null)
				{			
					ajaxReply.put("result", "nok");
					response.setContentType("application/json;charset=UTF-8");
				}	
				
				Message message = null;
				for(Message message2 : interventionDetailsView.getMessages()){
					if(message2.getId().toString().equalsIgnoreCase(messageId)){
						message = message2;
						break;
					}
				}
				
				if( message != null){
					ajaxReply.put("result", "ok");
					ajaxReply.put("messageId",message.getId().toString());
					ajaxReply.put("recipient",message.getTo().getId());
					ajaxReply.put("subject",message.getSubject());
					ajaxReply.put("description",message.getContents());
					ajaxReply.put("createdate",message.getCreateDate());
					ajaxReply.put("from",message.getFrom().getId());
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
			LOGGER.error("messageId null");
			return null;
		}
    }
    
    public ModelAndView handleTaskMessage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	
    	
    	
    	String taskIdAsString = request.getParameter("taskId");
    	String messageIdAsString = request.getParameter("messageId");
		String recipientIdAsString = request.getParameter("recipient");
		String subject = request.getParameter("subject");
		String contents = request.getParameter("description");
		
		Long recipientId = recipientIdAsString == null || recipientIdAsString.trim().length()==0 ? null:  Long.parseLong(recipientIdAsString);
		Long messageId = messageIdAsString == null || messageIdAsString.trim().length()==0 ? null:  Long.parseLong(messageIdAsString);
		Long taskId =  taskIdAsString == null || taskIdAsString.trim().length()==0 ? null:  Long.parseLong(taskIdAsString);
		
		InterventionDetailsView interventionDetailsView = (InterventionDetailsView)request.getSession(false).getAttribute(ACTIVITY_KEY) ;
					
		
		//Intervention intervention = (Intervention)service.getOneInterventionDetached(taskId);
		Mission mission = missionService.getOneDetachedFromReference(interventionDetailsView.getInterventionData().getMissionCode());
		
		
		
		// create a new assist context object
		WebContext context =  (WebContext) request.getSession(false).getAttribute("context");;
		
		
		
		Message parent = null;
		Employee from = userService.getOne(context.getCurrentUser().getId());
		Employee to =  userService.getOne(recipientId);
		
		
		
		
		Message message = null;
		if(messageId == null){
			message = new Message( parent,  subject,  contents,
					 from,  to.getEmail());
			message.setMission(mission);
			mission.getMessages().add(message);
			missionService.updateOne(mission);
			
			
		}
		
		Map<String,Object> mapResults = new HashMap<String, Object>(); 
    	interventionDetailsView =  service.buildInterventionDetailsView (taskId);
    	
    	request.getSession(false).setAttribute(ACTIVITY_KEY, interventionDetailsView);
    	mapResults.put(ACTIVITY_KEY, interventionDetailsView);
		
		return new ModelAndView("redirect:/showTaskCommunicationsPage.htm?id=" + taskId,mapResults);
    	
    }
    
    
    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView showTaskWorkDonesPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView("task_workdone");		
	}
    
   
    
    public ModelAndView editTaskWorkDone(HttpServletRequest request,
			HttpServletResponse response) throws Exception { 
    	
    	String workDoneId = request.getParameter("workDoneId");
    	JSONObject ajaxReply = new JSONObject();
    	
		if (workDoneId != null){	
			/*
			    InterventionDetailsView interventionDetailsView = (InterventionDetailsView)request.getSession(false).getAttribute(ACTIVITY_KEY) ;
				if(interventionDetailsView == null)
				{			
					ajaxReply.put("result", "nok");
					response.setContentType("application/json;charset=UTF-8");
				}	
				
				WorkDoneDayly workDoneDayly = null;
				for(WorkDoneDayly workDoneDayly2 : interventionDetailsView.getIntervention().getWorkDoneList()){
					if(workDoneDayly2.getId().toString().equalsIgnoreCase(workDoneId)){
						workDoneDayly = workDoneDayly2;
						break;
					}
				}
				
				if( workDoneDayly != null){
					ajaxReply.put("result", "ok");
					ajaxReply.put("workDoneId",workDoneDayly.getId().toString());
					ajaxReply.put("dateOfWork",workDoneDayly.getDateOfWork());
					ajaxReply.put("spentHours",workDoneDayly.getSpentHours());
					ajaxReply.put("comments",workDoneDayly.getComments());
					//no password as it is only sent from client to server
				
				response.setContentType("text/json;charset=UTF-8");
			}else{			
				ajaxReply.put("result", "nok");
			    response.setContentType("application/json;charset=UTF-8");
			}	
			*/			
			ServletOutputStream out = response.getOutputStream();
			String reply = ajaxReply.toString();	
			out.write(reply.getBytes("UTF-8"));
			out.flush();
			out.close();
			return null;
		}
		else{
			LOGGER.error("taskId null");
			return null;
		}
    }
    
    public ModelAndView handleTaskWorkDone(HttpServletRequest request,
			HttpServletResponse response) throws Exception { 
    	
    	
    	
    	String taskIdAsString = request.getParameter("taskId");
    	String workDoneIdAsString = request.getParameter("workDoneId");
		String spentHours = request.getParameter("spentHours");
		String comments = request.getParameter("comments");
		String dateOfWork = request.getParameter("dateOfWork");
		
		Date dateOfWorkDate = DateUtils.getDate(dateOfWork,"MM/dd/yyyy");
		Long taskId =  taskIdAsString == null || taskIdAsString.trim().length()==0 ? null:  Long.parseLong(taskIdAsString);
		Long workDoneId =  workDoneIdAsString == null || workDoneIdAsString.trim().length()==0 ? null:  Long.parseLong(workDoneIdAsString);
		
		// retrieve the context object
		WebContext context =  (WebContext) request.getSession(false).getAttribute("context");
		
		//Handle the log work done entity
		Activity intervention = service.manageWorkDone( taskId, workDoneId, dateOfWorkDate, comments,Integer.parseInt(spentHours));
		
		
		Map<String,Object> mapResults = new HashMap<String, Object>(); 
    	InterventionDetailsView interventionDetailsView =  service.buildInterventionDetailsView (taskId);
    	request.getSession(false).setAttribute(ACTIVITY_KEY, interventionDetailsView);
    	mapResults.put(ACTIVITY_KEY, interventionDetailsView);
    	return new ModelAndView("redirect:/showTaskWorkDonePage.htm?id=" + taskId,mapResults);
    }
    
    public ModelAndView deleteTaskWorkDone(HttpServletRequest request,
			HttpServletResponse response) throws Exception { 
    	
    	String taskIdAsString = request.getParameter("taskId");
    	String workDoneIdAsString = request.getParameter("workDoneId");
    	Long taskId =  taskIdAsString == null || taskIdAsString.trim().length()==0 ? null:  Long.parseLong(taskIdAsString);
    	Long workDoneId =  workDoneIdAsString == null || workDoneIdAsString.trim().length()==0 ? null:  Long.parseLong(workDoneIdAsString);
    	
    	// retrieve the context object
		WebContext context =  (WebContext) request.getSession(false).getAttribute("context");
		
    	boolean update = service.removeWorkDoneFromIntervention(taskId,  workDoneId);
		
		if( update == true){
			Map<String,Object> mapResults = new HashMap<String, Object>(); 
	    	InterventionDetailsView interventionDetailsView =  service.buildInterventionDetailsView (taskId);
	    	
	    	request.getSession(false).setAttribute(ACTIVITY_KEY, interventionDetailsView);
	    	mapResults.put(ACTIVITY_KEY, interventionDetailsView);
			
	    	return new ModelAndView("redirect:/showTaskWorkDonePage.htm?id=" + taskId,mapResults);
		}else{
			return new ModelAndView("task_workdone");
		}
		
    	
    	
    	
    }
    
    
    public ModelAndView changeFieldActivityStatus(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	
    	String status = request.getParameter("value");
		String idAsString = request.getParameter("id");
		
		Long id = Long.parseLong(idAsString);
		Activity activity = service.getOneInterventionDetached(id);
		
		
    	if( activity != null){
    		activity.setStatus(status);
    		service.updateOne(activity);
    	}
				
    	response.getWriter().append(status).flush();
		return null;
    	
    }
    
    public ModelAndView changeFieldActivityTodo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	
    	String toDo = request.getParameter("value");
    	String idAsString = request.getParameter("id");
		
		Long id = Long.parseLong(idAsString);
		Activity activity = service.getOneInterventionDetached(id);
		
		
    	if( activity != null){
    		activity.setToDo(toDo);
    		service.updateOne(activity);
    	}
				
    	response.getWriter().append(toDo).flush();
		return null;
				
		
    }
    
    public ModelAndView changeFieldActivityComments(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	
    	String comments = request.getParameter("value");
    	String idAsString = request.getParameter("id");
		
		Long id = Long.parseLong(idAsString);
		Activity activity = service.getOneInterventionDetached(id);
		
		
    	if( activity != null){
    		activity.setComments(comments);
    		service.updateOne(activity);
    	}
				
    	response.getWriter().append(comments).flush();
		return null;
    	
    }
    
    
   


	public IActivityService getService() {
		return service;
	}



	public void setService(IActivityService service) {
		this.service = service;
	}



	public IMissionService getMissionService() {
		return missionService;
	}



	public void setMissionService(IMissionService missionService) {
		this.missionService = missionService;
	}



	public IUserService getUserService() {
		return userService;
	}



	public void setUserService(IUserService userService) {
		this.userService = userService;
	}



	public IDocumentService getDocumentService() {
		return documentService;
	}



	public void setDocumentService(IDocumentService documentService) {
		this.documentService = documentService;
	}

	
}
