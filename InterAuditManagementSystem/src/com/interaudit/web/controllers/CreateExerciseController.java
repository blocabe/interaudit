package com.interaudit.web.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.userdetails.User;
import org.springframework.beans.propertyeditors.CustomBooleanEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.interaudit.domain.model.Contract;
import com.interaudit.domain.model.Exercise;
import com.interaudit.service.IContractService;
import com.interaudit.service.IExerciseService;
import com.interaudit.service.exc.BusinessException;

/**
 * Controller for the Login screen.
 */
public class CreateExerciseController extends SimpleFormController
{
	
	private IExerciseService exerciseService;
	private IContractService contractService;

	/**
	 * Always returns a new User object
	 * 
	 * @see User
	 */
	protected Object formBackingObject(HttpServletRequest request) throws Exception
	{
		
		String id = request.getParameter("id"); 
		
		if(id == null || id.trim().length() == 0){
			/*
			int year = 0;int nextYear = 0;
			Exercise exercise  = null;
		  	 
		  	 int countExercise = exerciseService.countExercises();
		  	 
		  	 if(countExercise == 0){
		  		 Calendar c = Calendar.getInstance();
		  		 exercise  = new Exercise(nextYear);
		  		 nextYear =  year = c.get(Calendar.YEAR); 
		  	 }else{
		  		//Create a new exercise from an existing one
		      	 year =  exerciseService.getMaxYear();
		      	 nextYear = year + 1;
		  	 }
		  	
		  	 exercise  = new Exercise(nextYear);
			*/
			Exercise exercise = null;
			try{
				float inflationPercentage = 2.5f;
				exercise = exerciseService.buildExercise(null);
				return exercise;
			 }catch(BusinessException be){
				 //request.setAttribute("exercise", exercise);
				 request.getSession(false).setAttribute("exerciseErrorMessage", be.getExcpetionMessages().get(0).getMessage());
				 //return new ModelAndView("exercise_register");
				 return new ModelAndView(this.getFormView());
			 }	
			
		}
		else{
			return exerciseService.getOneDetached(Long.valueOf(id) );
		}
	}
	
	protected void initBinder(HttpServletRequest request,ServletRequestDataBinder binder) throws Exception{
		try{
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
		
		com.interaudit.domain.model.Exercise exercise  = (com.interaudit.domain.model.Exercise)command;
        if (exercise == null)
        {
            return;
        }
        
        
        if(exercise.getId() == null){
        	
        	//Check if any exercise are still in status ongoing
         	 if( exerciseService.hasOnGoingExercise() ){
         		request.getSession(false).setAttribute("exerciseErrorMessage", "At least one exercise is still ongoing...");
         		errors.reject("totalExpectedAmount", "At least one exercise is still ongoing...");
         	 }
         	 
         	//Retrieve the valid contract
           	List<Contract> validContracts = contractService.findActiveContracts();
           	if( validContracts == null ){
          		
           		errors.reject("totalExpectedAmount", "At least one exercise is still ongoing...");
          		request.getSession(false).setAttribute("exerciseErrorMessage","No active contracts found...");
          	 }
			 
		 }
        
        
        if (request.getParameter("appproved") == null) {
        	exercise.setAppproved(false);
		}else{
			exercise.setAppproved(true);
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
		com.interaudit.domain.model.Exercise exercise = (com.interaudit.domain.model.Exercise) command;
		 if(exercise.getId() == null){
			
				 //exerciseService.buildNextExercise(exercise.getInflationPercentage());
				 exerciseService.saveOne(exercise);
				 messages.add("The exercise : " + exercise.getYear() + " has been successfully created");
				 request.getSession(false).setAttribute("successMessage", messages);
				 return  new ModelAndView(getSuccessView());
				 
				 
		 }else{
			 if(exercise.isAppproved()){
				 try{
					 exerciseService.approveExercise(exercise.getId());
					 messages.add("The exercise : " + exercise.getYear() + " has been successfully approved");	
					 request.getSession(false).setAttribute("successMessage", messages);
					 return  new ModelAndView(getSuccessView());
				 }catch(BusinessException be){
					 request.setAttribute("exercise", exercise);
					 request.getSession(false).setAttribute("exerciseErrorMessage", be.getExcpetionMessages().get(0).getMessage());
					// return new ModelAndView("exercise_register");
					 return new ModelAndView(this.getFormView());
				 }
			 }
			 else{
				 exerciseService.updateOne(exercise);
				 messages.add("The exercise : " + exercise.getYear() + " has been successfully updated");	
				 request.getSession(false).setAttribute("successMessage", messages);
				 return  new ModelAndView(getSuccessView());
			 }
		 }
	}



	public IExerciseService getExerciseService() {
		return exerciseService;
	}



	public void setExerciseService(IExerciseService exerciseService) {
		this.exerciseService = exerciseService;
	}

	public IContractService getContractService() {
		return contractService;
	}

	public void setContractService(IContractService contractService) {
		this.contractService = contractService;
	}

	

	

	

	

	

}
