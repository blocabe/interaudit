package com.interaudit.service;

import java.util.List;
import java.util.Map;

import com.interaudit.domain.model.Document;
import com.interaudit.domain.model.Employee;
import com.interaudit.domain.model.data.Option;
import com.interaudit.service.param.SearchEmployeeParam;
import com.interaudit.service.view.AccessRightsView;
import com.interaudit.service.view.EmployeeView;



public interface IUserService {
	
	public int createDefaultAccessRightsForEmployee(Employee employee);

    // delete a jaspers users from its id
    void deleteOne(Long id);

    // get all jaspers users
    EmployeeView getMatchingEmployees(SearchEmployeeParam param,int firstPos,int LINEPERPAGE);

    long getTotalCount(SearchEmployeeParam param);
    
    List<Option> getAllEmployeeAsOptions();
    
    List<Option> getManagersAsOptions();
    
    List<Option> getPartnersAsOptions();
    
    List<Option> getDirectorsAsOptions();
    
    List<Option> getEmployeesEmailAsOptions();

    // get a jaspers user from its id
    Employee getOne(Long id);
    
    Employee getOneDetached(Long id);
    
    public Employee getOneFromCode(String code);

    // get a jaspers user from its userName and its password
    Employee authenticate(String userName, String password,
            boolean onlyActive);
    
    public int getMaxEmployeeLoginSequence(String login);

    // save a jaspers user
    Employee saveOne(Employee jaspersUser);
    
    //Create a new employee
    Employee createEmployee( Employee employee);

    // update a jasper user
    Employee updateOne(Employee jaspersUser);

    List<Employee> getAllByRoleName(List<String> roleNames);
    public List<Employee> getAllByRoleName(List<String> roleNames,int year);

    List<Employee> searchUser(String searchField, boolean onlyActive,int firstPos,int LINEPERPAGE);
    
    List<Employee> getPartners();
    
    List<Employee> getManagers();
    
    List<Employee> getAssistants();
    
    List<Employee> getDirectors();
    
    List<Employee> getSecretaires();
    
    EmployeeView getEmployeeFromCode(SearchEmployeeParam param);
    
    public List<Employee> getAll();

    /**
     * 
     * @param entityId
     * @param fileName
     * @return
     */
    public Document getDocumentByEntityIdAndFileName(Long entityId,
            String fileName);
    
    
    public AccessRightsView buildAccessRightsView(String type,String roleName);
    public boolean updateAccessRights(String rights,String type,String role);
    public boolean getUserHasAccessRight(Long id,String right);
    public Map<String, Boolean> accessRightsMap(String roleName);
    
    
    public List<Employee> getManagers(int year);
	public List<Employee> getPartners(int year);
	public List<Employee> getDirectors(int year);
}
