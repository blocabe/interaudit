package com.interaudit.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.interaudit.domain.dao.IUserDao;
import com.interaudit.domain.model.Document;
import com.interaudit.domain.model.Employee;
import com.interaudit.domain.model.Position;
import com.interaudit.domain.model.Right;
import com.interaudit.domain.model.data.Option;
import com.interaudit.service.IRoleService;
import com.interaudit.service.IUserService;
import com.interaudit.service.param.SearchEmployeeParam;
import com.interaudit.service.view.AccessRightsView;
import com.interaudit.service.view.EmployeeView;

@Transactional
public class UserService implements IUserService {
	private Log log = LogFactory.getLog(IUserService.class);
    private IUserDao userDao;
    private IRoleService roleService;
    
    public IUserDao getJaspersUserDao() {
        return userDao;
    }

    public void setJaspersUserDao(IUserDao jaspersUserDao) {
        this.userDao = jaspersUserDao;
    }
    
	public Employee authenticate(String userName, String password, boolean onlyActive) {
		return userDao.getOne(userName, password, onlyActive);
	}
	
	public int getMaxEmployeeLoginSequence(String login) {
		return userDao.getMaxEmployeeLoginSequence(login);
	}
	
	public void deleteOne(Long id) {
		userDao.deleteOne(id);
	}
	
	
	public EmployeeView getMatchingEmployees(SearchEmployeeParam param,int firstPos,int LINEPERPAGE){
		List<Employee> employees = userDao.searchUsers(param, firstPos, LINEPERPAGE);
		return new EmployeeView(employees,param,userDao.getAllDistinctPositions());
		 
	}
	
	public int createDefaultAccessRightsForEmployee(Employee employee){
		return userDao.createDefaultAccessRightsForEmployee(employee);
	}
	
	public AccessRightsView buildAccessRightsView(String type,String employeeId){
		
		List<Employee> employees = new ArrayList<Employee>();
		Employee employee = userDao.getOneDetached(Long.parseLong(employeeId));
		employees.add(employee);
		/*
		if(roleName.equalsIgnoreCase("All")){
			employees = userDao.getAll();
		}else{
			List<String> roleNames = new ArrayList<String>();
			roleNames.add(roleName);
			employees = userDao.getAllByRoleName(roleNames);
		}
		*/
		List<Option> employeeOptions = getAllEmployeeAsOptions();		
		List<Right> rights = userDao.getAllRights(type);		
		return new AccessRightsView(employees,rights,type,null/*userDao.getAllDistinctPositions()*/,employeeId,employeeOptions);
		
	}
	
	public boolean updateAccessRights(String rights,String type,String role){
		return userDao.updateAccessRights(rights,type,role);
	}
	
	public Map<String, Boolean> accessRightsMap(String roleName){
		return userDao.accessRightsMap(roleName);
	}
	
	public boolean getUserHasAccessRight(Long id,String right){
		return userDao.getUserHasAccessRight( id,right);
	}
	
	public  long getTotalCount(SearchEmployeeParam param){
		return userDao.getTotalCount(param);
	}
	
	/* (non-Javadoc)
	 * @see com.interaudit.service.IUserService#getEmployeeFromCode(com.interaudit.service.param.SearchEmployeeParam)
	 */
	public EmployeeView getEmployeeFromCode(SearchEmployeeParam param){
		List<Employee> employees = userDao.getFromCode(param.getEmployeeCode());
		return new EmployeeView(employees,param,roleService.getAll());
	}
	 
	 
	public Employee getOne(Long id) {
		return userDao.getOne(id);
	}
	
	public Employee getOneDetached(Long id){
		return userDao.getOneDetached(id);
	}
	public Employee saveOne(Employee jaspersUser) {
		return userDao.saveOne(jaspersUser);
	}
	
    public Employee getOneFromCode(String code){
    	return userDao.getOneFromCode(code);
    }
	
	
	//Create a new employee
	public Employee createEmployee(Employee employee 
			){
		
		Position position = roleService.getRoleByCode(employee.getPosition().getName());
		
		String loginCheck = employee.getLogin();
		
		//Build a code for the employee
		String code = employee.getFirstName().substring(0, 1).toUpperCase() + employee.getLastName().substring(0, 1).toUpperCase();
		int maxOccurrence = userDao.getMaxEmployeeCodeSequence(code);
		if(maxOccurrence > 0) code += Integer.toString(maxOccurrence+1) ;
		
		//Correct the login if necessary
		 maxOccurrence = userDao.getMaxEmployeeLoginSequence(loginCheck);
		if(maxOccurrence > 0) loginCheck += Integer.toString(maxOccurrence+1) ;
		
		//Update the employee bean accordingly
		employee.setPosition(position);
		employee.setCode(code);
		employee.setLogin(loginCheck);
		employee.setCreateDate(new Date());
		
		//Create the new employee
		return userDao.saveOne(employee);
	}
	
	public Employee updateOne(Employee employee) {
		Position position = roleService.getRoleByCode(employee.getPosition().getName());
		employee.setPosition( position);
		return userDao.updateOne(employee);
	}

    public List<Employee> getAllByRoleName(List<String> roleNames) {
        return userDao.getAllByRoleName(roleNames);
    }
    
    
    public List<Employee> getAllByRoleName(List<String> roleNames,int year){
    	return userDao.getAllByRoleName( roleNames,year);
    }
    
    
    
    
    public List<Employee> getPartners(){
    	
    	List<String> roleNames = new ArrayList<String>(2);
    	roleNames.add(Position.MANAGING_PARTNER);
    	roleNames.add(Position.PARTNER);
    	return this.getAllByRoleName(roleNames);
    }
    
    
    public List<Employee> getPartners(int year){
    	
    	List<String> roleNames = new ArrayList<String>(2);
    	roleNames.add(Position.MANAGING_PARTNER);
    	roleNames.add(Position.PARTNER);
    	return this.getAllByRoleName(roleNames,year);
    }
    
 
    
    public List<Employee> getManagers(){
    	
    	List<String> roleNames = new ArrayList<String>(3);
    	roleNames.add(Position.ASSISTANT_MANAGER);
    	roleNames.add(Position.SENIOR_MANAGER);
    	roleNames.add(Position.MANAGER);
    	roleNames.add(Position.MANAGING_PARTNER);
    	roleNames.add(Position.PARTNER);
    	return this.getAllByRoleName(roleNames);
    }
    
    
      public List<Employee> getManagers(int year){
    	
    	List<String> roleNames = new ArrayList<String>(3);
    	roleNames.add(Position.ASSISTANT_MANAGER);
    	roleNames.add(Position.SENIOR_MANAGER);
    	roleNames.add(Position.MANAGER);
    	roleNames.add(Position.MANAGING_PARTNER);
    	roleNames.add(Position.PARTNER);
    	return this.getAllByRoleName(roleNames,year);
    }
    
    
   public  List<Option> getAllEmployeeAsOptions(){
	   List<Option> options= new ArrayList<Option>();
	   
	// get all jaspers users
	 List<Employee> allemployees = userDao.getAll();
   
   	for(Employee employee : allemployees){
   		 options.add(new Option(employee.getId().toString(),employee.getCode() + " - " + employee.getLastName()));
   	}
   	return options; 
   }
    
    
    public List<Option> getManagersAsOptions(){
    	List<Option> options= new ArrayList<Option>();
    	List<Employee> partners = this.getManagers();
    	for(Employee employee : partners){
    		 options.add(new Option(employee.getId().toString(),employee.getCode()+ " - " + employee.getLastName()));
    	}
    	return options;
    }
    
    
   
    
    public List<Option> getPartnersAsOptions(){
    	List<Option> options= new ArrayList<Option>();
    	List<Employee> partners = this.getPartners();
    	for(Employee employee : partners){
    		 options.add(new Option(employee.getId().toString(),employee.getCode()+ " - " + employee.getLastName()));
    	}
    	return options;
    }
    
    public List<Employee> getAssistants(){
    	List<String> roleNames = new ArrayList<String>(2);
    	roleNames.add(Position.SENIORS);
    	roleNames.add(Position.ASSISTANTS);
    	return this.getAllByRoleName(roleNames);
    }
    
    public List<Employee> getDirectors(){
    	List<String> roleNames = new ArrayList<String>(1);
    	roleNames.add(Position.DIRECTOR);
    	return this.getAllByRoleName(roleNames);
    }
    
    public List<Employee> getDirectors(int year){
    	List<String> roleNames = new ArrayList<String>(1);
    	roleNames.add(Position.DIRECTOR);
    	return this.getAllByRoleName(roleNames,year);
    }
    
    public List<Option> getDirectorsAsOptions(){
    	List<Option> options= new ArrayList<Option>();
    	List<Employee> partners = this.getDirectors();
    	for(Employee employee : partners){
    		 options.add(new Option(employee.getId().toString(),employee.getCode()+ " - " + employee.getLastName()));
    	}
    	return options;
    }
    
    public List<Option> getEmployeesEmailAsOptions(){
    	List<Option> options= new ArrayList<Option>();
    	List<Employee> employees = this.getAll();
    	for(Employee employee : employees){
    		 options.add(new Option(employee.getEmail(), employee.getFullName()));
    	}
    	return options;
    }
    
    public List<Employee> getAll(){
    	return userDao.getAll();
    }
    
    
    public List<Employee> getSecretaires(){
    	List<String> roleNames = new ArrayList<String>(1);
    	roleNames.add(Position.SECRETAIRE);
    	return this.getAllByRoleName(roleNames);
    }
    
    public List<Employee> searchUser(String searchField, boolean onlyActive,int firstPos,int LINEPERPAGE){
    	return userDao.searchUser(searchField, onlyActive, firstPos, LINEPERPAGE);
    }

    public Document getDocumentByEntityIdAndFileName(Long entityId,
            String fileName) {
        Document storedDocument = null;
        try {
            storedDocument = userDao.getDocumentByEntityIdAndFileName(entityId,
                    fileName);
        } catch (NoResultException e) {
            log.debug("No document found for user with id = " + entityId
                    + " and fileName = " + fileName);
        }
        return storedDocument;
    }

	public IUserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	public IRoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}
}
