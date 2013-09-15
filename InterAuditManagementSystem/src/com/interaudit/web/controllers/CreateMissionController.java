package com.interaudit.web.controllers;

import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.userdetails.User;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.interaudit.domain.model.Exercise;
import com.interaudit.domain.model.Mission;
import com.interaudit.service.ICustomerService;
import com.interaudit.service.IMissionService;
import com.interaudit.util.CustomDoubleEditor;

/**
 * Controller for the Login screen.
 */
public class CreateMissionController extends SimpleFormController
{
	
	 private IMissionService missionService;
	 private ICustomerService customerService;

	/**
	 * Always returns a new User object
	 * 
	 * @see User
	 */
	protected Object formBackingObject(HttpServletRequest request) throws Exception
	{
		String id = request.getParameter("id"); 
		
		if(id != null && id.trim().length() != 0){			
			return missionService.getOneDetached(Long.valueOf(id) );
		}
		else{
			throw new Exception();
		}
	}
	
	@SuppressWarnings("unchecked")
	protected Map referenceData(HttpServletRequest request) {
		Map referenceData = new HashMap();
		
		String id = request.getParameter("id"); 
		
		//Customer customer = customerService.getOneCustomerDetachedFromMissionId(Long.valueOf(id));
		
		//referenceData.put("customerName",customer.getCompanyName());
		referenceData.put("missionForUpdateStatusOptions",Mission.getAllStatusOptions());
		
		referenceData.put("missionJobProgressStatusOptions",Mission.getAllJobProgressStatusOptions());
		referenceData.put("missionJobTodoOptions",Mission.getAllJobtodoStatusOptions());
		referenceData.put("missionJobToFinishOptions",Mission.getAllJobtoFinishStatusOptions());
	
		return referenceData;
	}
	
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception
			{
		try{
		
			String dateFormat = "MM/dd/yyyy";
			SimpleDateFormat df = new SimpleDateFormat(dateFormat);
			df.setLenient(true);
			binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(
			df, true));
			
			
			binder.registerCustomEditor(double.class, new CustomDoubleEditor());

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
		
		Mission mission = (Mission)command;
		if(mission != null && mission.getDateCloture()==null && mission.getStatus().equalsIgnoreCase(Mission.STATUS_CLOSED)){
			mission.setDateCloture(new Date());
		}
		
		if (errors.hasErrors())
		{
			return;
		}
	}

	/** returns ModelAndView(getSuccessView()) */
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception
	{
		ArrayList<String> messages = new ArrayList<String>();
		Mission mission = (Mission) command;
		
		String fromPlanningWeek = request.getParameter("FromPlanningWeek"); 
		
		Exercise currentExercise = (Exercise) getServletContext().getAttribute("currentExercise");
	     
		getMissionService().updateOne(mission);
		messages.add("The mission : " + mission.getReference() + " has been successfully updated");
		
		request.getSession(false).setAttribute("successMessage", messages);
		
		StringBuffer buff=new StringBuffer();
		if(request.getSession(false).getAttribute("not_started_key") != null){    			
			//String not_started_key = (String)request.getSession(false).getAttribute("not_started_key"); 
			buff.append("&not_started_key=true");
		}
		
		if(request.getSession(false).getAttribute("pending_key") != null){    			
			//String pending_key = (String)request.getSession(false).getAttribute("pending_key"); 
			buff.append("&pending_key=true");
		}
		
		if(request.getSession(false).getAttribute("ongoing_key") != null){    			
			//String ongoing_key = (String)request.getSession(false).getAttribute("ongoing_key"); 
			buff.append("&ongoing_key=true");
		}
		
		if(request.getSession(false).getAttribute("stopped_key") != null){    			
			//String stopped_key = (String)request.getSession(false).getAttribute("stopped_key"); 
			buff.append("&stopped_key=true");
		}
		
		if(request.getSession(false).getAttribute("closed_key") != null){    			
			//String closed_key = (String)request.getSession(false).getAttribute("closed_key"); 
			buff.append("&closed_key=true");
		}
		
		if(request.getSession(false).getAttribute("cancelled_key") != null){    			
			//String cancelled_key = (String)request.getSession(false).getAttribute("cancelled_key"); 
			buff.append("&cancelled_key=true");
		}
		
		
		
		
		if(fromPlanningWeek == null || fromPlanningWeek.trim().length()==0){
			return new ModelAndView("redirect:/showGeneralBudgetPage.htm?year_" + currentExercise.getYear()+"="+ currentExercise.getYear()+"&forceReload=true");
		}
		else{
			
			try{
				//String encodedUrl = URLEncoder.encode(buff.toString(),"UTF-8");
				String encodedUrl =buff.toString();
				return new ModelAndView(getSuccessView()+encodedUrl);
			}
			catch(Exception mluer){
				return new ModelAndView(getSuccessView());
			}
			//return new ModelAndView(getSuccessView()+buff.toString());
		}
		
        
		
	}



	public IMissionService getMissionService() {
		return missionService;
	}



	public void setMissionService(IMissionService missionService) {
		this.missionService = missionService;
	}

	public ICustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}



	

	

	

	

	

}
