package com.interaudit.service.impl;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;

import com.interaudit.domain.model.data.Option;
import com.interaudit.service.IActivityService;
import com.interaudit.service.IBankService;
import com.interaudit.service.IBudgetService;
import com.interaudit.service.IContactService;
import com.interaudit.service.IContractService;
import com.interaudit.service.ICustomerService;
import com.interaudit.service.IOriginService;
import com.interaudit.service.IRoleService;
import com.interaudit.service.ITaskService;
import com.interaudit.service.IUserService;


public final class RepositoryService implements InitializingBean{
	
	private static RepositoryService uniqueInstance;// Stockage de l'unique instance de cette classe.
	
	private  List<Option> yearOptions = null;
	private  List<Option> employeeOptions = null;
	private  List<Option> taskOptions = null;
	private  List<Option> taskOption2s = null;
	private  List<Option> taskOption3s =  null;
	
	private  List<Option> typeOptions = null;
	private  List<Option> originOptions =  null;
	private  List<Option> managerOptions = null;
	private  List<Option> associeOptions = null;
	private  List<Option> directorsOptions = null;
	private  List<Option> exercicesOptions = null;
	private  List<String> validContractReferences = null;
	private  List<Option> customerOptions = null;
	private  List<Option> allContractOptions = null;
	private  List<Option> allContactOptions = null;
	private  List<Option> missionOption2s = null;
	private  List<Option> missionOptions = null;
	private  List<Option> positionOptions = null;
	private  List<Option> employeesEmailsAsOptions = null;
	private  List<Option> bankOptions = null;
	
	
	
	//Les services
	private IActivityService activityService;
	private IUserService userService;
	private ITaskService taskService;
	private IOriginService originService;
	private IContractService contractService;
	private IBudgetService budgetService;
	private ICustomerService customerService;
	private IContactService contactService;
	private IRoleService roleService;
	private IBankService bankService;
	
	
	// Constructeur en privé (donc inaccessible à l'extérieur de la classe).
	private RepositoryService(){}
	
	// Méthode statique qui sert de pseudo-constructeur (utilisation du mot clef "synchronized" pour le multithread).
	public static synchronized RepositoryService getInstance()
	{
		if(uniqueInstance==null)
		{
			uniqueInstance = new RepositoryService();
			//initialise();
		}
		return uniqueInstance;
	}
	
	public  void refresh(){
		synchronized(RepositoryService.class){
			
			//Empty all the collections
			uniqueInstance.yearOptions = null;
			uniqueInstance.employeeOptions = null;
			uniqueInstance.taskOptions = null;
			uniqueInstance.taskOption2s = null;
			uniqueInstance.taskOption3s = null;
			uniqueInstance.typeOptions = null;
			uniqueInstance.originOptions = null;
			uniqueInstance.managerOptions = null;
			uniqueInstance.associeOptions = null;
			uniqueInstance.directorsOptions = null;
			uniqueInstance.exercicesOptions = null;
			uniqueInstance.validContractReferences = null;
			uniqueInstance.customerOptions = null;
			uniqueInstance.allContractOptions = null;
			uniqueInstance.allContactOptions = null;
			uniqueInstance.missionOption2s = null;
			uniqueInstance.missionOptions = null;
			uniqueInstance.positionOptions = null;
			uniqueInstance.employeesEmailsAsOptions = null;
			uniqueInstance.bankOptions = null;
			
			//Step 1: Force the garbage collection to run
			Runtime r = Runtime.getRuntime();
			
			// Step 2: determine the current amount of free memory
		    long freeMem = r.freeMemory();
		    System.out.println("free memory before creating array: " + freeMem);

		   // Step 3: run the garbage collector, then check freeMemory
	       r.gc();
	       freeMem = r.freeMemory();
	       System.out.println("free memory after running gc():    " + freeMem);

			//Fill them again
			initialise();
		}
		
		
	}
	
	public static void initialise(){
		uniqueInstance.getYearOptions();
		uniqueInstance.getEmployeeOptions();
		uniqueInstance.getTaskOptions();
		uniqueInstance.getTaskOption3s();
		uniqueInstance.getTaskOption2s();
		uniqueInstance.getTypeOptions();
		uniqueInstance.getOriginOptions();
		uniqueInstance.getManagerOptions();
		uniqueInstance.getAssocieOptions();
		uniqueInstance.getDirectorsOptions();
		uniqueInstance.getExercicesOptions();
		uniqueInstance.getValidContractReferences();
		uniqueInstance.getCustomerOptions();
		uniqueInstance.getAllContractAsOptions();
		uniqueInstance.getAllContactAsOptions();
		uniqueInstance.getMissionTypeOptions2();
		uniqueInstance.getMissionTypeOptions();
		uniqueInstance.getPositionsAsOptions();
		uniqueInstance.getPositionsAsOptions();
		uniqueInstance.getEmployeesEmailAsOptions();
		uniqueInstance.getAllBankOptions();
	}
	
	
	
	//La liste des roles comme options
	public List<Option> getAllBankOptions(){
		synchronized(RepositoryService.class){
			if(bankOptions == null){
				bankOptions = bankService.getBankAsOptions2();
			}
			return bankOptions;
		}
	}
	
	//La liste des roles comme options
	public List<Option> getEmployeesEmailAsOptions(){
		synchronized(RepositoryService.class){
			if(employeesEmailsAsOptions == null){
				employeesEmailsAsOptions = userService.getEmployeesEmailAsOptions();
			}
			return employeesEmailsAsOptions;
		}
	}
	
	
	//La liste des roles comme options
	public List<Option> getPositionsAsOptions(){
		synchronized(RepositoryService.class){
			if(positionOptions == null){
				positionOptions = roleService.getPositionsAsOptions();
			}
			return positionOptions;
		}
	}
	
	//La liste des missions comme options
	public List<Option> getMissionTypeOptions(){
		synchronized(RepositoryService.class){
			if(missionOptions == null){
				missionOptions = contractService.getMissionTypeOptions();
			}
			return missionOptions;
		}
	}
	
	//La liste des missions comme options
	public List<Option> getMissionTypeOptions2(){
		synchronized(RepositoryService.class){
			if(missionOption2s == null){
				missionOption2s = contractService.getMissionTypeOptions2();
			}
			return missionOption2s;
		}
	}
	
	//La liste des contacts comme options
	public List<Option> getAllContactAsOptions(){
		synchronized(RepositoryService.class){
			if(allContactOptions == null){
				allContactOptions = contactService.getAllContactAsOptions();
			}
			return allContactOptions;
		}
	}
	
	//La liste des exercices comme options
	public List<Option> getYearOptions(){
		synchronized(RepositoryService.class){
			if(yearOptions == null){
				yearOptions = activityService.getAllExercicesOptions();
			}
			return yearOptions;
		}
	}
	
	//La liste des exercices comme options
	public List<Option> getEmployeeOptions(){
		synchronized(RepositoryService.class){
			if(employeeOptions == null){
				employeeOptions = userService.getAllEmployeeAsOptions();
			}
			return employeeOptions;
		}
	}
	
	public List<Option> getTaskOptions(){
		synchronized(RepositoryService.class){
			if(taskOptions == null){
				taskOptions = taskService.getTaskAsOptions();
			}
			return taskOptions;
		}
	}
	
	public List<Option> getTaskOption3s(){		
		synchronized(RepositoryService.class){
			if(taskOption3s == null){
				 taskOption3s = taskService.getTaskAsOptions3();
			}
			return taskOption3s;
		}
		
	}	
	
	public List<Option> getTaskOption2s(){
		synchronized(RepositoryService.class){
			if(taskOption2s == null){
				 taskOption2s = taskService.getTaskAsOptions2();
			}
			return taskOption2s;
		}
		
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

	public ITaskService getTaskService() {
		return taskService;
	}

	public void setTaskService(ITaskService taskService) {
		this.taskService = taskService;
	}

	public IContractService getContractService() {
		return contractService;
	}

	public void setContractService(IContractService contractService) {
		this.contractService = contractService;
	}

	public IBudgetService getBudgetService() {
		return budgetService;
	}

	public void setBudgetService(IBudgetService budgetService) {
		this.budgetService = budgetService;
	}
	

	public List<Option> getTypeOptions() {
		synchronized(RepositoryService.class){
			if(typeOptions == null){
				typeOptions = contractService.getMissionTypeOptions();
			}
			return typeOptions;
		}
		
	}

	public List<Option> getOriginOptions() {
		synchronized(RepositoryService.class){
			if(originOptions == null){
				originOptions = originService.getAllAsOptions();
			}
			return originOptions;
		}
		
	}

	public List<Option> getManagerOptions() {
		synchronized(RepositoryService.class){
			if(managerOptions == null){
				managerOptions = userService.getManagersAsOptions();
			}
			return managerOptions;
		}
		
	}

	public List<Option> getAssocieOptions() {
		synchronized(RepositoryService.class){
			if(associeOptions == null){
				associeOptions = userService.getPartnersAsOptions();
			}
			return associeOptions;
		}
		
	}

	public List<Option> getDirectorsOptions() {
		synchronized(RepositoryService.class){
			if(directorsOptions == null){
				directorsOptions = userService.getDirectorsAsOptions();
			}
			return directorsOptions;
		}
		
	}

	public List<Option> getExercicesOptions() {
		synchronized(RepositoryService.class){
			if(exercicesOptions == null){
				exercicesOptions = budgetService.getAllExercicesOptions();
			}
			return exercicesOptions;

		}
		
	}

	public List<String> getValidContractReferences() {
		synchronized(RepositoryService.class){
			if(validContractReferences == null){
				validContractReferences = contractService.getAllValidContractReference();
			}
			return validContractReferences;	
		}
		
	}
	
	public List<Option> getCustomerOptions() {
		synchronized(RepositoryService.class){
			if(customerOptions == null){
				customerOptions = customerService.getAllCustomerAsOptions();
			}
			return customerOptions;
		}
		
	}
	
	public List<Option> getAllContractAsOptions() {
		synchronized(RepositoryService.class){
			if(allContractOptions == null){
				allContractOptions = contractService.getAllContractAsOptions();;
			}
			return allContractOptions;
		}
		
	}
	
	
	public IOriginService getOriginService() {
		return originService;
	}

	public void setOriginService(IOriginService originService) {
		this.originService = originService;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		//initialise();
		//cache refresh thread	
		uniqueInstance.refresh();    
		/*
		Thread updatingThread = new Thread(){			         
			public void run() {			              
				while (true){                 
					System.out.println("!!!!! Doing RepositoryService refresh at : " + new Date());                   
					uniqueInstance.refresh();                    
					try {                       
						Thread.sleep(600000);               
					} catch (InterruptedException e) {                      
						System.out.println("Interrupted");                    
					}              
				}           
			}        
		};    
		updatingThread.setDaemon(true);     
		updatingThread.start();
		*/
		
	}

	public ICustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}

	public IContactService getContactService() {
		return contactService;
	}

	public void setContactService(IContactService contactService) {
		this.contactService = contactService;
	}

	public IRoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}

	public IBankService getBankService() {
		return bankService;
	}

	public void setBankService(IBankService bankService) {
		this.bankService = bankService;
	}

}
