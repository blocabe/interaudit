package com.interaudit.web.controllers;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.userdetails.User;
import org.springframework.beans.propertyeditors.CustomBooleanEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.interaudit.domain.model.Employee;
import com.interaudit.domain.model.Message;
import com.interaudit.domain.model.Mission;
import com.interaudit.domain.model.data.Option;
import com.interaudit.service.IEmailService;
import com.interaudit.service.IExerciseService;
import com.interaudit.service.IMissionService;
import com.interaudit.service.IUserService;
import com.interaudit.util.EmployeeEditor;
import com.interaudit.util.MissonEditor;
import com.interaudit.util.WebContext;

/**
 * Controller for the Login screen.
 */
public class CreateMessageController extends SimpleFormController
{
	
	private IUserService userService;
	private IMissionService missionService;
	private IEmailService emailService;
	private IExerciseService exerciseService;

	/**
	 * Always returns a new User object
	 * 
	 * @see User
	 */
	protected Object formBackingObject(HttpServletRequest request) throws Exception
	{
		String id = request.getParameter("id"); 
		//Create a new message with the current user set as the sender
		if(id == null || id.trim().length() == 0){
			WebContext context =  (WebContext) request.getSession(false).getAttribute("context");
	        Employee currentUser = context.getCurrentUser();
			com.interaudit.domain.model.Message message = (Message) request.getSession(false).getAttribute("message");;
			
			if(message == null){
				message = new com.interaudit.domain.model.Message();
			}
			
			message.setFrom(currentUser);			
			message.setCreateDate(new Date());
			
			
			//Recuperation des recipients si disponibles
			StringBuffer emailsList = new StringBuffer();
			 Enumeration<String> paramNames = request.getParameterNames();
			 while(paramNames.hasMoreElements()) {
			      String paramName = (String)paramNames.nextElement();
			      if (paramName.startsWith("reci_")){
			    	  String recipient = request.getParameter(paramName);
			    	  emailsList.append(recipient).append(";");			         
			      }
			 }	
			 
			 String missionId = request.getParameter("missionId"); 
			 String sujet = request.getParameter("sujet"); 
			 String mailContents = request.getParameter("contents"); 
			 
			
			String parentId = request.getParameter("parentId"); 
			if(parentId != null){
				com.interaudit.domain.model.Message messageParent = missionService.getOneMessageDetached(Long.valueOf(parentId));
				
				if(mailContents== null  || mailContents.length()==0){
					StringBuffer contents = new StringBuffer().append("<br/><br/>");
					contents.append("---------------------------------------------").append("<br/>");
					contents.append("From:" + messageParent.getFrom().getFullName()).append("<br/>");
					contents.append("Sent:" + messageParent.getCreateDate()).append("<br/>");
					contents.append("To:" + messageParent.getEmailsList()).append("<br/>");
					contents.append("Subject:" + messageParent.getSubject()).append("<br/><br/>");
					
					contents.append(messageParent.getContents());
					message.setContents(contents.toString());	
				}
				else{
					message.setContents(mailContents);	
				}
				
				if(sujet== null  || sujet.length()==0){
					message.setSubject("Re : " + messageParent.getSubject());
				}
				else{
					message.setSubject(sujet);
				}
					
				
				//message.setTo(messageParent.getFrom());
				if(missionId== null  || missionId.length()==0){
					message.setMission(messageParent.getMission());
				}
				else{
					message.setMission(missionService.getOneDetached(Long.valueOf(missionId)));
				}
				
				if(emailsList== null  || emailsList.toString().length()==0){
					message.setEmailsList(messageParent.getFrom().getEmail());
				}
				else{
					message.setEmailsList(emailsList.toString());
				}
				
				
				
				
			}
			else{
				//message.setTo(new Employee());
				
				if(mailContents== null  || mailContents.length()==0){
					
					message.setContents("");	
				}
				else{
					message.setContents(mailContents);	
				}
				
				if(sujet== null  || sujet.length()==0){
					message.setSubject("");
				}
				else{
					message.setSubject(sujet);
				}
				
				//message.setTo(messageParent.getFrom());
				if(missionId== null  || missionId.length()==0){
					message.setMission(new Mission());
				}
				else{
					message.setMission(missionService.getOneDetached(Long.valueOf(missionId)));
				}
				
				if(emailsList== null  || emailsList.toString().length()==0){
					message.setEmailsList("");
				}
				else{
					message.setEmailsList(emailsList.toString());
				}
				
			}
			
			return message;
		}
		else{
			//return userService.getOneDetached(Long.valueOf(id) );
			com.interaudit.domain.model.Message message =  missionService.getOneMessageDetached(Long.valueOf(id));
			message.setRead(true);
			message = missionService.updateOneMessage(message);			
			return message;
		}
	}
	

	@SuppressWarnings("unchecked")
	protected Map referenceData(HttpServletRequest request) {
		Map referenceData = new HashMap();
		/*
		Calendar c = Calendar.getInstance();
        Integer value = c.get(Calendar.YEAR);
        */
		Integer currentYear = exerciseService.getFirstOnGoingExercise();
   	 	if(currentYear == null){
			 //currentYear = exerciseService.getMaxYear();
			 currentYear =  exerciseService.getLastClosedExercise();
			 if(currentYear == null){
				Calendar c = Calendar.getInstance(); 
				currentYear =c.get(Calendar.YEAR);
			 } 
   	 	}
        String year = currentYear.toString();
        
        WebContext context =  (WebContext) request.getSession(false).getAttribute("context");
        Employee currentUser = context.getCurrentUser();
		
		List<Option>missionOptions = missionService.getAllOpenMissionForYearAsOptions( currentUser, year); 		
		List<Employee> teamMembers = userService.getAll();
		
		referenceData.put("missionOptions", missionOptions);
		referenceData.put("teamMembers", teamMembers);
		return referenceData;
	}
	
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception
			{
		try{
			
			NumberFormat nf = NumberFormat.getInstance(); 
			binder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class, nf, true)); 
			binder.registerCustomEditor(Long.class, new CustomNumberEditor(Long.class, nf, true)); 
			binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
			
			binder.registerCustomEditor(Employee.class, new EmployeeEditor( userService)); 
			binder.registerCustomEditor(Mission.class, new MissonEditor(missionService));
			
			 
		     
			
			//String dateFormat = getMessageSourceAccessor().getMessage("format.date","dd.MM.yyyy");
			String dateFormat = "MM/dd/yyyy";
			SimpleDateFormat df = new SimpleDateFormat(dateFormat);
			df.setLenient(true);
			binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(
			df, true));
			 
			binder.registerCustomEditor(java.lang.Boolean.class, new CustomBooleanEditor( true));
			
		}catch(Exception e){
			//System.out.println(e.getMessage());
			throw e;
		}
			

	}

	

	
	/**
	 * Validates user/password against database
	 * 
	 * This is the authentication/authorisation check
	 * 
	 */
	public void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception
	{
		
		if (errors.hasErrors())
		{
			return;
		}
		Message message = (Message) command;
		if(message.getEmailsList()== null || message.getEmailsList().length()==0){
			 errors.reject("invalidRecipientErrorMessage","Invalid email recipient..");
			request.getSession(false).setAttribute("invalidRecipientErrorMessage", "Invalid email recipient..");
        	return;
		}
		
		if (errors.hasErrors())
		{
			return;
		}
		
		/*
		com.interaudit.domain.model.Employee employee = (com.interaudit.domain.model.Employee)command;
        if (employee == null)
        {
            return;
        }
        
        if (request.getParameter("userActive") == null) {
			employee.setUserActive(false);
		}else{
			employee.setUserActive(true);
		}
        
        if( Utils.isValidEmail(employee.getEmail()) == false){
        	errors.reject("employee.email","Invalid email format");
        	request.getSession(false).setAttribute("emailErrorMessage", "Invalid email format");
        	//return;
        }

        int nbOccurrence = userService.getMaxEmployeeLoginSequence( employee.getLogin());
      	
    	if(employee.getId() == null){
    		if( nbOccurrence > 0){
    			errors.reject("employee.login","Invalid login already used");
    			request.getSession(false).setAttribute("loginErrorMessage", "Invalid login already used");
    		}
    	}else{
    		if( nbOccurrence > 1){
    			errors.reject("employee.login","Invalid login already used");
    			request.getSession(false).setAttribute("loginErrorMessage", "Invalid login already used");
    		}
    	}
       

        if (errors.hasErrors())
		{
			return;
		}
        */
		
    
	}

	/** returns ModelAndView(getSuccessView()) */
	@SuppressWarnings("unchecked")
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception
	{
		/*
		 ArrayList<String> messages = new ArrayList<String>();
		 Employee employee = (Employee) command;
		 if(employee.getId() == null){
			
			 userService.createEmployee( employee);
			 messages.add("The employee : " + employee.getLastName() + " has been successfully created");
						
		 }else{
			 
			 userService.updateOne(employee);
			 messages.add("The employee : " + employee.getLastName() + " has been successfully updated");
		 }
		 
		
		request.getSession(false).setAttribute("successMessage", messages);
		*/
		

		
		Message message = (Message) command;
		
		if(message.getId() == null){
			
			Mission mission = missionService.getOneDetached(message.getMission().getId());
			message.setMission(mission);
			WebContext context =  (WebContext) request.getSession(false).getAttribute("context");
	        Employee currentUser = context.getCurrentUser();
	        message.setFrom(currentUser);
	      //  Employee toUser = userService.getOneDetached(message.getTo().getId());
	        //message.setTo(toUser);
	        mission.getMessages().add(message);
			missionService.updateOne(mission);
			//Save a new message
							 
			
			
			/*
			//Save a communication email for the message
			EmailData emailData = new EmailData( currentUser, toUser,message.getSubject(), message.getContents(), EmailData.TYPE_MISSION_COMMUNICATION);
			emailService.saveOne(emailData);
			*/	
		}
	
		 return  new ModelAndView(getSuccessView());
        
		
	}

	public IUserService getUserService() {
		return userService;
	}


	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	


	public IMissionService getMissionService() {
		return missionService;
	}


	public void setMissionService(IMissionService missionService) {
		this.missionService = missionService;
	}


	public IEmailService getEmailService() {
		return emailService;
	}


	public void setEmailService(IEmailService emailService) {
		this.emailService = emailService;
	}


	public IExerciseService getExerciseService() {
		return exerciseService;
	}


	public void setExerciseService(IExerciseService exerciseService) {
		this.exerciseService = exerciseService;
	}

	

	

	

	

}
