package com.interaudit.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.transaction.annotation.Transactional;

import com.interaudit.domain.dao.IBudgetDao;
import com.interaudit.domain.dao.IExerciseDao;
import com.interaudit.domain.dao.IMissionDao;
import com.interaudit.domain.model.Activity;
import com.interaudit.domain.model.AnnualBudget;
import com.interaudit.domain.model.Contract;
import com.interaudit.domain.model.Employee;
import com.interaudit.domain.model.Exercise;
import com.interaudit.domain.model.Invoice;
import com.interaudit.domain.model.Mission;
import com.interaudit.domain.model.ObjectifPerExercise;
import com.interaudit.domain.model.data.AnnualBudgetData;
import com.interaudit.domain.model.data.AnnualBudgetSumData;
import com.interaudit.domain.model.data.Option;
import com.interaudit.domain.model.data.ProfitabilityPerCustomerData;
import com.interaudit.service.IBudgetService;
import com.interaudit.service.IContractService;
import com.interaudit.service.ICustomerService;
import com.interaudit.service.IOriginService;
import com.interaudit.service.IUserService;
import com.interaudit.service.exc.BusinessException;
import com.interaudit.service.exc.ExceptionMessage;
import com.interaudit.service.param.SearchBudgetParam;
import com.interaudit.service.param.SearchProfitabilityPerCustomerParam;
import com.interaudit.service.view.BudgetGeneralView;
import com.interaudit.service.view.ProfitabilityPerCustomerView;

@Transactional
public class BudgetService implements IBudgetService , ApplicationContextAware  {
	//private Log log = LogFactory.getLog(TaskService.class);
    private IBudgetDao budgetDao;
    private IMissionDao missionDao;
    private IOriginService originService;
    private IUserService userService;
    private IExerciseDao exerciseDao;
    private IContractService contractService;
    private ICustomerService customerService;
    
    private ApplicationContext context;
    
    public void setApplicationContext(ApplicationContext applicationContext)  throws BeansException 
    {                context = applicationContext;        }
   
   
    public List<AnnualBudgetData> findBudgetsForExpression(String expression,List<Integer> years){
    	return  budgetDao.findBudgetsForExpression( expression, years);
    }
    
    public List<AnnualBudgetData> findBudgetsForCustomerCode(String code,List<Integer> years){
    	return  budgetDao.findBudgetsForCustomerCode( code, years);
    }
    

    /**
     * Returns a Task object identified by given id.
     * 
     * @param id
     * @return Returns a AnnualBudget object identified by given id.
     */
    public AnnualBudget getOne(Long id){
    	return budgetDao.getOne(id);
    }
    
    
   public AnnualBudget getOneDetachedFromContract(Long id){
	   return  budgetDao.getOneDetachedFromContract(id);
   }
    

    /**
     * Delete a AnnualBudget object identified by given id.
     * 
     * @param id
     */
    public void deleteOne(Long id){
    	budgetDao.deleteOne(id);
    }
    
    /**
     * Cancel a AnnualBudget object identified by given id.
     * 
     * @param id
     */
    public void cancelOne(Long id){
    	AnnualBudget budget = budgetDao.getOne(id);
    	if(budget != null)
    		budget.setStatus(AnnualBudget.STATUS_CANCELLED);
    	
    	 Mission missionDetached = missionDao.getOneDetachedFromBudgetId(budget.getId());
    	 if(missionDetached != null){
    		 
    		 missionDetached.setStatus(Mission.STATUS_CANCELLED);
    		 
    		 for(Activity intervention : missionDetached.getInterventions() ){
    			 intervention.setStatus(Activity.STATUS_CANCELLED);
    		 }
    		 
    		 missionDao.updateOne(missionDetached);
    	 }
    	 
    	 budgetDao.updateOne(budget);
    }
    
 
    /**
     * Returns a AnnualBudget object identified by given id.
     * 
     * @param id
     * @return Returns a Task object identified by given id.
     */
    public AnnualBudget saveOne(AnnualBudget budget){
    	return budgetDao.saveOne(budget);
    }
    
    public AnnualBudgetData getOneAnnualBudgetDataFromId(Long id){
    	return this.budgetDao.getOneAnnualBudgetDataFromId(id);
    }
    
    /**
     * @param data
     * @return
     */
    public AnnualBudgetData saveOrUpdateBudget(AnnualBudgetData data) throws BusinessException{
    	
    	AnnualBudget budget = null;
    	Contract contract = null;
    	
    	Exercise exercise = exerciseDao.getOneFromYear(data.getYear());
    	
    	if(exercise == null){
    		throw new BusinessException(new ExceptionMessage("Invalid exercise..."));
    	}
    	
    	if(exercise.getStatus().equalsIgnoreCase(Exercise.STATUS_CLOSED)){
    		throw new BusinessException(new ExceptionMessage("Cannot add budget to a closed exercise..."));
    	}
    	
    	//Verifier qu'un mandat n'existe pas déja avec ce contrat et cet année de mandat
    	String mandat = data.getMandat();
    	
    	
    	boolean isNewBudget = ( data.getId() == null);    	
    	if(isNewBudget){ //Nouveau mandat à créer   		
    		
    		//Verifier que un ligne budgetaire n'existe pas déja pour ce contrat et cet exercice    		
    		contract = contractService.getOneFromCode(data.getContractCode());
        	
        	//Verifier la validité du contrat
        	if(data.getContractCode() != null && data.getContractCode().length() > 0){    		
        		if( contract == null){
        			throw new BusinessException(new ExceptionMessage("No valid contract found for the code [" + data.getContractCode() +"]...Please make sure a valid contract is registered for this code"));
        		}        		
        		contract = contractService.getOne(contract.getId());
        	}
        	else{
        		throw new BusinessException(new ExceptionMessage("Not a valid contract code"));
        	}
        	
    		//Verifier q'aucune mission n'existe déja pour ce contrat et ce mandat
    		Mission existingMandat = missionDao.getOneDetachedForContractAndExercise(contract.getId() , mandat , exercise.getYear());
    		if(existingMandat != null){
    			throw new BusinessException(new ExceptionMessage("A mission exists already for customer [ "+ data.getContractCode() +"] and mandat ["+ mandat +"]..."));	
    		}
    		
    		//Création du budget + mission
    		budget = new AnnualBudget();
    		budget.setContract(contract);
    		
    	}else{ //Mandat existant à modifier    		
    		budget = budgetDao.getOne(data.getId());
    		contract = budget.getContract();
    	}
    	
    	//Update the exercice
    	budget.setExercise(exerciseDao.getOne(exercise.getId()));
	
    	//Filled the other fields
    	budget.setComments(data.getComments());
    	
    	//Si le budget n'est pas fourni alors le prendre dans celui du contrat
    	//budget.setExpectedAmount(data.getExpectedAmount());
    	
    	if(data.getExpectedAmount() >= 0.0){    	
    		budget.setExpectedAmount(data.getExpectedAmount());
    	}
    	else{
    		budget.setExpectedAmount(contract.getAmount());
    	}
    	
    	budget.setReportedAmount(data.getReportedAmount());
    	
    	if(data.getManagerId() != -1L){
    		budget.setBudgetManager(userService.getOne(data.getManagerId()));
    	}
    	else{
    		budget.setBudgetManager(userService.getOne(contract.getCustomer().getCustomerManager().getId()));
    	}
    
    	if(data.getAssociateId() != -1L){
    		budget.setAssocieSignataire(userService.getOneDetached(data.getAssociateId()));
    	}
    	else{
    		budget.setAssocieSignataire(userService.getOne(contract.getCustomer().getAssocieSignataire().getId()));
    	}
    
    	budget.setFiable(data.isFiable());
    	
    	budget.setInterim(data.getInterim());
    	
    	//Forcer le contrat a interim egale true
    	if(data.getInterim() == true){
    		contract.setInterim(true);
    	}
    	else{
    		contract.setInterim(false);
    	}
    	
    	
    	//Set the status
    	if(isNewBudget){
    		 budget.setStatus(AnnualBudget.STATUS_PENDING);
    		 budget = budgetDao.saveOne(budget);    		
			 Mission mission = budgetDao.createMission(budget.getId(), null,mandat);
			 if (mission != null){
				 budget.setStatus(AnnualBudget.STATUS_ONGOING);
				 budget.setMission(mission);
		    	 budget = budgetDao.updateOne(budget);
			 }    			     	
    	}else{
    			//Update de la mission avec le mandat
        		budget.setStatus(data.getStatus());
        		budget = budgetDao.updateOne(budget);
        		
        		//Verifier q'aucune mission n'existe déja pour ce contrat et ce mandat
    			Mission existingMandat = missionDao.getOneDetachedForContractAndExercise(contract.getId(), mandat , exercise.getYear());
        		if(existingMandat == null){
        			Mission mission = budget.getMission();
            		mission.setExercise(mandat);
            		
            		Set<Invoice> invoices = mission.getFactures();
            		for(Invoice facture : invoices){
            			facture.setExerciseMandat(mission.getExercise());
            		}
            		
            		missionDao.updateOne(mission);
            		
        		}
        		else{
        			//throw new BusinessException(new ExceptionMessage("A mission exists already for customer [ "+ contract.getReference() +"] and mandat ["+ mandat +"]..."));	
        		} 
        		
        		//Updating the related budgets in any previsonal execises
        		double reportedAmount = exerciseDao.calculateReportedAmountForBudget(budget.getId(),exercise.getYear());
        		budgetDao.updateReportedAmountInChildBudget(budget.getId(),reportedAmount);
        		exerciseDao.markexErciseForUpdate(exercise.getId(),true);
    	}
    	
    	if(isNewBudget){
    		exercise.getBudgets().add(budget);
    	}
    	
    	
    	return this.budgetDao.getOneAnnualBudgetDataFromId(budget.getId());
    }
    

    /**
     * Returns a Task object identified by given id.
     * 
     * @param id
     * @return Returns a Task object identified by given id.
     */
    public AnnualBudget updateOne(AnnualBudget budget){
    	return budgetDao.updateOne(budget);
    }
   
    
    /**
     * 
     * @return AnnualBudget objects list representing all AnnualBudget known by IAMS
     *         application.
     */
    public BudgetGeneralView searchBudgets(  SearchBudgetParam param,boolean usePagination,int firstPos,int LINEPERPAGE){
    	
    	 RepositoryService repositoryService = (RepositoryService) context.getBean("repositoryService");
    	 List<Option> typeOptions = repositoryService.getTypeOptions();//contractService.getMissionTypeOptions();Mission.getAllTypeOptions();
		 List<Option> originOptions =  repositoryService.getOriginOptions();//originService.getAllAsOptions();
		 List<Option> managerOptions = repositoryService.getManagerOptions();//userService.getManagersAsOptions();
		 List<Option> associeOptions = repositoryService.getAssocieOptions();//userService.getPartnersAsOptions();
		 List<Option> directorsOptions = repositoryService.getDirectorsOptions();//userService.getDirectorsAsOptions();
		 List<Option> exercicesOptions = repositoryService.getExercicesOptions();//this.getAllExercicesOptions();
		 List<Exercise>  exercises = exerciseDao.getExercises(param.getYears());
		 List<Option> customerOptions = null;
		 if( param.getCustomerStartString() != null){
			 customerOptions = budgetDao.findCustomerOptionsForLetter(param.getCustomerStartString(),param.getYears()) ;
		 }
		
		 
		 List<String> validContractReferences =repositoryService.getValidContractReferences(); //contractService.getAllValidContractReference();
		 List<AnnualBudgetData>  budgets = budgetDao.searchBudgets(param,usePagination, firstPos,LINEPERPAGE);
		 AnnualBudgetSumData annualBudgetSumData =null;// budgetDao.findBudgetSums(param);
    	return new BudgetGeneralView(budgets,param,typeOptions,originOptions,managerOptions,associeOptions,exercicesOptions,validContractReferences,exercises,directorsOptions,customerOptions,annualBudgetSumData);
    }
    

    
    public long getTotalCount(SearchBudgetParam  param){
    	return budgetDao.getTotalCount( param);
    }
    
    public long getTotalCountProfitabilityPerCustomer(SearchProfitabilityPerCustomerParam  param){
    	return budgetDao.getTotalCountProfitabilityPerCustomer( param);
    }
    
    public  List<Option> getAllExercicesOptions(){
 	   
 	   List<Exercise> exercises = getExerciseDao().getAll();
 	   List<Option> options= new ArrayList<Option>();
 	   for(Exercise year : exercises){
 			options.add(new Option(Integer.toString(year.getYear()),Integer.toString(year.getYear()),year.isNeedUpdate()));
 	   }
 	   
 	   return options;
 }   
    
    /**
     * @param budget
     * @return the mission created for the budget
     
    public Mission createMission(Long budgetId){
    	
    	return budgetDao.createMission(budgetId);
    	
    }
    */
    
    /**
     * @param budget
     * @return the mission created for the budget
    
    public Mission createMission(AnnualBudget budgetSaved){
    	
    	return budgetDao.createMission(budgetSaved.getId());
    	
    	
    }
*/
    
   
    
    public ProfitabilityPerCustomerView calculateProfitabilityPerCustomer(SearchProfitabilityPerCustomerParam param,boolean usePagination,int firstPos,int LINEPERPAGE){
    	
    	/*
    	  List<Option> originOptions =  repositoryService.getOriginOptions();//originService.getAllAsOptions();
		 List<Option> managerOptions = repositoryService.getManagerOptions();//userService.getManagersAsOptions();
		 List<Option> associeOptions = repositoryService.getAssocieOptions();//userService.getPartnersAsOptions();
		 List<Option> directorsOptions = repositoryService.getDirectorsOptions();//userService.getDirectorsAsOptions();
		 List<Option> exercicesOptions = repositoryService.getExercicesOptions();//this.getAllExercicesOptions();
    	 */
     RepositoryService repositoryService = (RepositoryService) context.getBean("repositoryService");
     List<Option> directorOptions = repositoryService.getDirectorsOptions();//userService.getDirectorsAsOptions();
	 List<Option> managerOptions = repositoryService.getManagerOptions();//userService.getManagersAsOptions();
	 List<Option> associeOptions = repositoryService.getAssocieOptions();//userService.getPartnersAsOptions();
	 associeOptions.addAll(directorOptions);
	 List<Option> exercicesOptions = repositoryService.getExercicesOptions();//this.getAllExercicesOptions();
	 List<Option> customerOptions = repositoryService.getCustomerOptions();//customerService.getAllCustomerAsOptions();
	 
	 //Take the first one by default
	 /*
	if( param.getCustomer() == null){
		param.setCustomer(Long.parseLong(customerOptions.get(0).getId()));
	}
	*/
	 
	 List<ProfitabilityPerCustomerData> data = this.missionDao.calculateProfitabilityPerCustomer(param, usePagination, firstPos, LINEPERPAGE);    	
 	  
 	 return new ProfitabilityPerCustomerView( data, 
 			 								  param,
 			 								  managerOptions,
 			 								  associeOptions,
 			 								  exercicesOptions,
 			 								  customerOptions);
    	
    }
    
    
    class ProfitabilityPerCustomerDataComparateur implements Comparator<ProfitabilityPerCustomerData> {
  	  public int compare(ProfitabilityPerCustomerData obj1, ProfitabilityPerCustomerData obj2){
  	    return ((Comparable<ProfitabilityPerCustomerData>)obj2).compareTo(obj1);
  	  }
  	  public boolean equals(Object obj){
  	    return this.equals(obj);
  	  }
  	}


    
    public ObjectifPerExercise computeObjectifPerExerciseForAssocie(Employee responsable ,Exercise exercise){
    	return getBudgetDao().computeObjectifPerExerciseForAssocie(responsable, exercise);
    }
    public ObjectifPerExercise computeObjectifPerExerciseForManager(Employee responsable ,Exercise exercise){
    	return getBudgetDao().computeObjectifPerExerciseForManager(responsable, exercise);
    }
	public IMissionDao getMissionDao() {
		return missionDao;
	}


	public void setMissionDao(IMissionDao missionDao) {
		this.missionDao = missionDao;
	}


	public IBudgetDao getBudgetDao() {
		return budgetDao;
	}


	public void setBudgetDao(IBudgetDao budgetDao) {
		this.budgetDao = budgetDao;
	}


	public IOriginService getOriginService() {
		return originService;
	}


	public void setOriginService(IOriginService originService) {
		this.originService = originService;
	}


	public IUserService getUserService() {
		return userService;
	}


	public void setUserService(IUserService userService) {
		this.userService = userService;
	}


	public IContractService getContractService() {
		return contractService;
	}


	public void setContractService(IContractService contractService) {
		this.contractService = contractService;
	}


	public IExerciseDao getExerciseDao() {
		return exerciseDao;
	}


	public void setExerciseDao(IExerciseDao exerciseDao) {
		this.exerciseDao = exerciseDao;
	}


	public ICustomerService getCustomerService() {
		return customerService;
	}


	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}


	


	
}
