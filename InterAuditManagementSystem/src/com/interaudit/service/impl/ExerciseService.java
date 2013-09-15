package com.interaudit.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.transaction.annotation.Transactional;

import com.interaudit.domain.dao.IExerciseDao;
import com.interaudit.domain.model.AnnualBudget;
import com.interaudit.domain.model.Contract;
import com.interaudit.domain.model.Employee;
import com.interaudit.domain.model.Exercise;
import com.interaudit.domain.model.Invoice;
import com.interaudit.domain.model.Mission;
import com.interaudit.domain.model.ObjectifPerExercise;
import com.interaudit.domain.model.data.Option;
import com.interaudit.service.IActivityService;
import com.interaudit.service.IBudgetService;
import com.interaudit.service.IContractService;
import com.interaudit.service.IExerciseService;
import com.interaudit.service.IFactureService;
import com.interaudit.service.IMissionService;
import com.interaudit.service.IRoleService;
import com.interaudit.service.ITaskService;
import com.interaudit.service.IUserService;
import com.interaudit.service.exc.BusinessException;
import com.interaudit.service.view.ExerciseGeneralView;

@Transactional
public class ExerciseService implements IExerciseService , ApplicationContextAware {
	private Log logger = LogFactory.getLog(ExerciseService.class);
    private IExerciseDao exerciseDao;
    
    private IContractService contractService;
    private IFactureService  factureService;
    private IBudgetService   budgetService;
    private IMissionService  missionService;
    private IActivityService activityService;
    private ITaskService 	 taskService;
	private IRoleService     roleService;
	private IUserService     userService;
	private ApplicationContext context;
	    
	public void setApplicationContext(ApplicationContext applicationContext)  throws BeansException 
	    {                context = applicationContext;        }
	
	
	public boolean deletePendingExercise(Long idExercise){
		boolean ret = exerciseDao.deletePendingExercise(idExercise);
		RepositoryService repositoryService = (RepositoryService) context.getBean("repositoryService");
		repositoryService.refresh();
		return ret;
	}
	
    /**
     * 
     * @return Task objects list representing all tasks known by IAMS
     *         application.
     */
    public List<Exercise> getAll(){
    	return exerciseDao.getAll();
    }
    
    public int countExercises(){
    	return exerciseDao.countExercises();
    }
    
   public boolean hasOnGoingExercise(){
	   return exerciseDao.getCountOfOngoingExercises() > 0;
    }
   
   public Integer getFirstOnGoingExercise(){
	   return exerciseDao.getFirstOnGoingExercise();
   }
   
   public Integer getLastClosedExercise(){
	   return exerciseDao.getLastClosedExercise();
   }
   
   public Integer getMaxYear(){
	   return  exerciseDao.getMaxYear();
   }

    /**
     * Returns a Task object identified by given id.
     * 
     * @param id
     * @return Returns a Task object identified by given id.
     */
    public Exercise getOne(Long id){
    	return exerciseDao.getOne(id);
    }
    
    public Exercise getOneDetached(Long id){
    	return exerciseDao.getOneDetached(id);
    }
    
    
   public Exercise getOneFromYear(int year){
	   return exerciseDao.getOneFromYear(year);
    }
    
    
 
    /**
     * Delete a Task object identified by given id.
     * 
     * @param id
     */
    public void deleteOne(Long id){
    	exerciseDao.deleteOne(id);
    }
    
 
    /**
     * Returns a Task object identified by given id.
     * 
     * @param id
     * @return Returns a Task object identified by given id.
     */
   public Exercise saveOne(Exercise exercise){
	   return exerciseDao.saveOne(exercise);
   }
   
   
  
  /**
   * Returns a Exercise object identified by given id.
   * 
   * @return Returns a new Exercise object built from the current valid contracts .
   */
  public Exercise buildExercise(java.io.OutputStream out) throws BusinessException{
	  
	  Exercise exercise = exerciseDao.buildExercise(out);
	  RepositoryService repositoryService = (RepositoryService) context.getBean("repositoryService");
	  repositoryService.refresh();
	  return exercise;
	 
  }
  
  
	  /**
	 * @param year
	 * @return
	 * @throws BusinessException
	 */
  public Exercise recalculateExercise(int year) throws BusinessException{
	
	return exerciseDao.recalculateExercise(year);
	
  	}

	public Exercise applyInflation( float inflationPercentage,Long idExercise) throws BusinessException{
		
		return exerciseDao.applyInflation( inflationPercentage,idExercise);
		
	}
  
	
  
  /**
   * Returns a Exercise object identified by given id.
   * This method is supposed to create a mission object for each budget line
   * All non terminated activities will be reported in the next exercise and set to TRANSFERED state in the previous 
   * @return Returns  Exercise object  .
   */
  public Exercise approveExercise(Long idExercise) throws BusinessException{
	  
	  return exerciseDao.approveExercise(idExercise);  
  }
  
  /*
  private Activity createCopyIntervention(Activity intervention){
	//Save a new activity with status ONGOING 
	  	Activity copyActivity = new Activity();
		 
	  	 copyActivity.setActivityCodePrestation(intervention.getActivityCodePrestation());
		 copyActivity.setActivityDescription(intervention.getActivityDescription());
		 copyActivity.setActivityReference(intervention.getActivityReference());
		 
		 //copyActivity.setEmployee(intervention.getEmployee());
		 copyActivity.setStatus(Activity.STATUS_ONGOING);
		 copyActivity.setStartDate(intervention.getStartDate());
		 copyActivity.setEndDate(null);
		 
		 
		 copyActivity.setPriority(intervention.getPriority());
		 copyActivity.setProfile(intervention.getProfile());
		 copyActivity.setTotalEstimatedHours(intervention.getTotalEstimatedHours());
		 copyActivity.setPublished(intervention.isPublished());
		 copyActivity.setTotalWorkedHours(0);
		 copyActivity.setTask(intervention.getTask());
		 
		 return copyActivity;
  }
*/

  /**
   * Returns a Exercise object identified by given id.
   * This method will set all non terminated activities to BE TRANSFERED state , close all related mission and budget line and finally close the root exercise object
   * @return Returns a  Exercise object .
   */
  public Exercise closeExercise(Long idExercise) throws BusinessException{
	  
	  return exerciseDao.closeExercise( idExercise);
	  /*
	  //Retrieve the current exercise
	  Exercise exercise = this.getOne(idExercise);
	  if(exercise == null)  throw new BusinessException(new ExceptionMessage("No matching exercise found for identifier : " + idExercise));

	  //We found an exercise, let's close all related budget , missions and activities 
	  
	  for(AnnualBudget budget : exercise.getBudgets()){
		  
		  Mission mission = missionService.getOneDetachedForBudgetIdentifier(budget.getId());
		  
		  if(mission != null){
			  
			  Mission missionLife = missionService.getOne(mission.getId());
			  
			  //Pass all non terminated interventions to status TO_BE_TRANSFERED
			  for(Activity intervention :missionLife.getInterventions() ){
				 if( !intervention.getStatus().equalsIgnoreCase(Activity.STATUS_CLOSED) ){
					 intervention.setStatus(Activity.STATUS_TO_TRANSFERED);
				 }
			  }
			  
			  //Close the mission together with its activities
			  missionLife.setStatus(Mission.STATUS_CLOSED);
			  missionService.updateOne(missionLife);
		  }
		  
		  //Close the budget
		  budget.setStatus(AnnualBudget.STATUS_CLOSED);
		  budgetService.updateOne(budget);
		  
	  }
	  
	  //Finally we close the exercise
	  exercise.setStatus(Exercise.STATUS_CLOSED);
	  return this.updateOne(exercise);
	  */
  }
  
  public com.interaudit.domain.model.Exercise referenceBudgetPage(Long idExercise) throws BusinessException{
	  return exerciseDao.referenceBudgetPage( idExercise);
  }
    /**
     * Returns a Task object identified by given id.
     * 
     * @param id
     * @return Returns a Task object identified by given id.
     */
   public Exercise updateOne(Exercise exercise){
    	return exerciseDao.updateOne(exercise);
    }
   
   
   public  List<Option> getAllExercicesOptions(){
	   
	   List<Exercise> exercises = getAll();
	   List<Option> options= new ArrayList<Option>();
	   for(Exercise year : exercises){
			options.add(new Option(Integer.toString(year.getYear()),Integer.toString(year.getYear())));
	   }
	   
	   return options;
   }
   
   public void updateExerciceAndBudget(Long idExercise){	 
	   try{
		   
		   Exercise exercise = getOne(idExercise);
		   Integer currentYear = exercise.getYear();
		   if(exercise == null) return;
		   
		   //Check all the unpaid and sent invoices
		   factureService.checkAllUnpaidAndSentInvoices();
		   
		   //Retrieve the past contracts         
		  	contractService.updateInvalideContracts();
		  	
		   int countTransferredBudgets = exerciseDao.transferMissingBudgetsFromPreviousExercise( exercise);
		   
		   logger.info("Number of transferred budgets from previous exercise : " + countTransferredBudgets);
		   
		   for(AnnualBudget budget : exercise.getBudgets()){				 
				Contract contract = contractService.getOne(budget.getContract().getId());
				Mission mission = missionService.getOne(budget.getMission().getId());
				
				//Check that the mission and the budget are sharing the same status
				if( mission.getStatus().equalsIgnoreCase("ONGOING") && !budget.getStatus().equalsIgnoreCase(AnnualBudget.STATUS_ONGOING)  ){
					budget.setStatus(AnnualBudget.STATUS_ONGOING);
				}else if(mission.getStatus().equalsIgnoreCase("CLOSED") && !budget.getStatus().equalsIgnoreCase(AnnualBudget.STATUS_CLOSED)){
					budget.setStatus(AnnualBudget.STATUS_CLOSED);
				}else if(mission.getStatus().equalsIgnoreCase("CANCELLED") && !budget.getStatus().equalsIgnoreCase(AnnualBudget.STATUS_CANCELLED)){
					budget.setStatus(AnnualBudget.STATUS_CANCELLED);
				}
				
				if(mission.getStatus().equalsIgnoreCase(Mission.STATUS_TRANSFERED) && budget.getStatus().equalsIgnoreCase(AnnualBudget.STATUS_TRANSFERED)){
					budget.setStatus(AnnualBudget.STATUS_ONGOING);	
					mission.setStatus(Mission.STATUS_ONGOING);
				}
				
				
				//Update the facture mandat
				boolean updateMission = false;
				Set<Invoice> invoices = mission.getFactures();
	    		for(Invoice facture : invoices){
	    			if(facture.getExerciseMandat() != null && !facture.getExerciseMandat().equalsIgnoreCase(mission.getExercise())){
	    				facture.setExerciseMandat(mission.getExercise());
	    				updateMission = true;
	    			}
	    			
	    		}
	    		if(updateMission == true){
	    			missionService.updateOne(mission);
	    		}
	    		
				double effectiveAmount = factureService.getTotalInvoicedForContract(contract.getId(), Integer.toString(currentYear),mission.getExercise());
				budget.setEffectiveAmount(FactureService.formatDouble(effectiveAmount));
				boolean hasFinalBill = exerciseDao.existFinalBillForBudget(budget.getId(), currentYear);
				budget.setFinalBill(hasFinalBill);
				budgetService.updateOne(budget);			 
			}
		   
		   	recalculateExercise(exercise.getYear());
		   	exerciseDao.markexErciseForUpdate(exercise.getId(),false);
		   
	   }
	   catch(Exception e){
		   logger.error(e.getMessage());
	   }
	   
      
   }
   
   
   
			
 @Override
public ExerciseGeneralView getExerciseGeneralViewLight(int year){
	   
       //Load a detached exercise
	   Exercise exercise = getOneFromYear(year);	  
	   if(exercise == null) return null;
	   boolean deletable = ( getOneFromYear(year + 1)== null) && (exercise.getStatus().equalsIgnoreCase(Exercise.STATUS_PENDING));
	   double expectedBudget = exercise.getTotalExpectedAmount();
	   double reportedBudget = exercise.getTotalReportedAmount();
	   double totalBudget = factureService.getTotalInvoicedForExercise(exercise.getYear());
	   double totalInactifsBudget = exercise.getTotalInactifAmount();
	   
	   //Objectifs per associe
	   /*
	   Map<String,ObjectifPerExercise> objectifsPerAssocie = new HashMap<String,ObjectifPerExercise>();
	   List<Option> associeOptions = new ArrayList<Option>();
	   List<Employee> partners = userService.getPartners();
	   List<Employee> directors = userService.getDirectors();
	   for(Employee partner : partners){
		      ObjectifPerExercise objectif = budgetService.computeObjectifPerExerciseForAssocie(partner ,exercise);
			  objectifsPerAssocie.put(partner.getCode(), objectif );  
			  associeOptions.add(new Option(partner.getCode(),partner.getCode()));		  
	   }
	   
	   for(Employee director : directors){
		      ObjectifPerExercise objectif = budgetService.computeObjectifPerExerciseForAssocie(director ,exercise);
			  objectifsPerAssocie.put(director.getCode(), objectif );  
			  associeOptions.add(new Option(director.getCode(),director.getCode()));		  
	   }
	 	
	   //Objectifs per manager
	   List<Option> managerOptions = new ArrayList<Option>();
	   Map<String,ObjectifPerExercise> objectifsPerManager = new HashMap<String,ObjectifPerExercise>();
	   List<Employee> managers = userService.getManagers();
	   for(Employee manager : managers){
		   	   ObjectifPerExercise objectif = budgetService.computeObjectifPerExerciseForManager(manager ,exercise);
			   objectifsPerManager.put(manager.getCode(), objectif ); 
			   managerOptions.add(new Option(objectif.getResponsable().getCode(),objectif.getResponsable().getCode()));		 
	   }
	   
	   
	   for(Employee director : directors){
		   ObjectifPerExercise objectif = budgetService.computeObjectifPerExerciseForManager(director ,exercise);
		   objectifsPerManager.put(director.getCode(), objectif ); 
		   managerOptions.add(new Option(objectif.getResponsable().getCode(),objectif.getResponsable().getCode()));	 	  
	   }
	   
	 //Results per associates
	   Map<String,Double[]> resultsPerAssociate = new HashMap<String, Double[]>();
	   for(Employee partner : partners){
		   Double[] resulats = new Double[12];
		   for(int month=0 ; month<12;++month){
			   resulats[month] = factureService.getTotalInvoicedForMonthAndAssociate( month, exercise.getYear(), partner.getId());
		   }
		   resultsPerAssociate.put(partner.getCode(), resulats );  
	   }
	   
	   for(Employee director : directors){
		   Double[] resulats = new Double[12];
		   for(int month=0 ; month<12;++month){
			   resulats[month] = factureService.getTotalInvoicedForMonthAndAssociate( month, exercise.getYear(), director.getId());
		   }
		   resultsPerAssociate.put(director.getCode(), resulats ); 
	   }
	   
	   
	   //Results per manager
	   Map<String,Double[]> resultsPerManager = new HashMap<String, Double[]>();
	   for(Employee manager : managers){
		   Double[] resulats = new Double[12];
		   for(int month=0 ; month<12;++month){
			   resulats[month] = factureService.getTotalInvoicedForMonthAndManager( month, exercise.getYear(), manager.getId());
		   }
		   resultsPerManager.put(manager.getCode(), resulats );  
	   }
	   
	   for(Employee director : directors){
		   Double[] resulats = new Double[12];
		   for(int month=0 ; month<12;++month){
			   resulats[month] = factureService.getTotalInvoicedForMonthAndManager( month, exercise.getYear(), director.getId());
		   }
		   resultsPerManager.put(director.getCode(), resulats ); 
	   }
	   
	   
	   List<Integer> years =  exerciseDao.getExercisesAsInteger();
	   */
	   
	   return new ExerciseGeneralView(
								   		 exercise.getId(),
								   		 year, 
									   	 expectedBudget,
										 reportedBudget, 
										 totalBudget,
										 totalInactifsBudget
										 
									);
		
	   
   }
   
   
   public ExerciseGeneralView getExerciseGeneralView(int year){
	   
       //Load a detached exercise
	   Exercise exercise = getOneFromYear(year);	  
	   if(exercise == null) return null;
	   boolean deletable = ( getOneFromYear(year + 1)== null) && (exercise.getStatus().equalsIgnoreCase(Exercise.STATUS_PENDING));
	   double expectedBudget = exercise.getTotalExpectedAmount();
	   double reportedBudget = exercise.getTotalReportedAmount();
	   double totalBudget = factureService.getTotalInvoicedForExercise(exercise.getYear());
	   double totalInactifsBudget = exercise.getTotalInactifAmount();
	   
	   //Objectifs per associe
	   
	   Map<String,ObjectifPerExercise> objectifsPerAssocie = new HashMap<String,ObjectifPerExercise>();
	   List<Option> associeOptions = new ArrayList<Option>();
	   List<Employee> partners = userService.getPartners(year);
	   List<Employee> directors = userService.getDirectors(year);
	   for(Employee partner : partners){
		      ObjectifPerExercise objectif = budgetService.computeObjectifPerExerciseForAssocie(partner ,exercise);
			  objectifsPerAssocie.put(partner.getCode(), objectif );  
			  associeOptions.add(new Option(partner.getCode(),partner.getCode()));		  
	   }
	   
	   for(Employee director : directors){
		      ObjectifPerExercise objectif = budgetService.computeObjectifPerExerciseForAssocie(director ,exercise);
			  objectifsPerAssocie.put(director.getCode(), objectif );  
			  associeOptions.add(new Option(director.getCode(),director.getCode()));		  
	   }
	 	
	   //Objectifs per manager
	   List<Option> managerOptions = new ArrayList<Option>();
	   Map<String,ObjectifPerExercise> objectifsPerManager = new HashMap<String,ObjectifPerExercise>();
	   List<Employee> managers = userService.getManagers(year);
	   for(Employee manager : managers){
		   	   ObjectifPerExercise objectif = budgetService.computeObjectifPerExerciseForManager(manager ,exercise);
			   objectifsPerManager.put(manager.getCode(), objectif ); 
			   managerOptions.add(new Option(objectif.getResponsable().getCode(),objectif.getResponsable().getCode()));		 
	   }
	   
	   
	   for(Employee director : directors){
		   ObjectifPerExercise objectif = budgetService.computeObjectifPerExerciseForManager(director ,exercise);
		   objectifsPerManager.put(director.getCode(), objectif ); 
		   managerOptions.add(new Option(objectif.getResponsable().getCode(),objectif.getResponsable().getCode()));	 	  
	   }
	   
	 //Results per associates
	   Map<String,Double[]> resultsPerAssociate = new HashMap<String, Double[]>();
	   for(Employee partner : partners){
		   Double[] resulats = new Double[12];
		   for(int month=0 ; month<12;++month){
			   resulats[month] = factureService.getTotalInvoicedForMonthAndAssociate( month, exercise.getYear(), partner.getId());
		   }
		   resultsPerAssociate.put(partner.getCode(), resulats );  
	   }
	   
	   for(Employee director : directors){
		   Double[] resulats = new Double[12];
		   for(int month=0 ; month<12;++month){
			   resulats[month] = factureService.getTotalInvoicedForMonthAndAssociate( month, exercise.getYear(), director.getId());
		   }
		   resultsPerAssociate.put(director.getCode(), resulats ); 
	   }
	   
	   
	   //Results per manager
	   Map<String,Double[]> resultsPerManager = new HashMap<String, Double[]>();
	   for(Employee manager : managers){
		   Double[] resulats = new Double[12];
		   for(int month=0 ; month<12;++month){
			   resulats[month] = factureService.getTotalInvoicedForMonthAndManager( month, exercise.getYear(), manager.getId());
		   }
		   resultsPerManager.put(manager.getCode(), resulats );  
	   }
	   
	   for(Employee director : directors){
		   Double[] resulats = new Double[12];
		   for(int month=0 ; month<12;++month){
			   resulats[month] = factureService.getTotalInvoicedForMonthAndManager( month, exercise.getYear(), director.getId());
		   }
		   resultsPerManager.put(director.getCode(), resulats ); 
	   }
	   
	   
	   List<Integer> years =  exerciseDao.getExercisesAsInteger();
	   
	   return new ExerciseGeneralView(
								   		 exercise.getId(),
								   		 year, 
									   	 expectedBudget,
										 reportedBudget, 
										 totalBudget,
										 totalInactifsBudget,
										 objectifsPerAssocie,
										 objectifsPerManager,
										resultsPerManager,
										resultsPerAssociate,
										managerOptions,
										years,
										associeOptions,
										deletable,
										exercise.isNeedUpdate()
									);
		
	   
   }
   
   
   /**
    * 
    * @return AnnualBudget objects list representing all AnnualBudget known by IAMS
    *         application.
   
   public BudgetGeneralView getBudgetsForSelectedExercises(  SearchBudgetParam param){
	  return new BudgetGeneralView(exerciseDao.getBudgetsForSelectedExercises(param),param,null,null,null);
   }
    */

	public IExerciseDao getExerciseDao() {
		return exerciseDao;
	}

	public void setExerciseDao(IExerciseDao exerciseDao) {
		this.exerciseDao = exerciseDao;
	}

	public IContractService getContractService() {
		return contractService;
	}

	public void setContractService(IContractService contractService) {
		this.contractService = contractService;
	}
    
   
	public IFactureService getFactureService() {
		return factureService;
	}


	public void setFactureService(IFactureService factureService) {
		this.factureService = factureService;
	}

	public IBudgetService getBudgetService() {
		return budgetService;
	}

	public void setBudgetService(IBudgetService budgetService) {
		this.budgetService = budgetService;
	}

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

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	
     
    
	
}
