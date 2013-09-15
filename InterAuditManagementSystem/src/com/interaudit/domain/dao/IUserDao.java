package com.interaudit.domain.dao;

import java.util.List;
import java.util.Map;

import com.interaudit.domain.model.Document;
import com.interaudit.domain.model.Employee;
import com.interaudit.domain.model.Position;
import com.interaudit.domain.model.Right;
import com.interaudit.service.param.SearchEmployeeParam;

public interface IUserDao {

	public int createDefaultAccessRightsForEmployee(Employee employee);
	
	public List<Position> getAllDistinctPositions();
	 
    // delete a jaspers users from its id
    public void deleteOne(Long id);

    // get all  users
    public List<Employee> getAll();
    
    // get all  users rights
    public List<Right> getAllRights(String type);
    
    public boolean updateAccessRights(String rights,String type,String role);
    
    public Map<String, Boolean> accessRightsMap(String roleName);
    
    public boolean getUserHasAccessRight(Long id,String right);

    // get a jaspers user from its id
    public Employee getOne(Long id);
    
    public Employee getOneDetached(Long id);

    // get a jaspers user from its userName and its password
    public Employee getOne(String userName, String password,
            boolean onlyActive);
    
    public int getMaxEmployeeCodeSequence(String code);
    
    public int getMaxEmployeeLoginSequence(String login);
    
    /**
     * @param code
     * @return
     */
    public Employee getOneFromCode(String code);
    
    public List<Employee> getFromCode(String code);

    // save a jaspers user
    public Employee saveOne(Employee jaspersUser);

    // update a jasper user
    public Employee updateOne(Employee user);

    // get all by role name
    List<Employee> getAllByRoleName(List<String> roleNames);
    
    public List<Employee> getAllByRoleName(List<String> roleNames,int year);

    // search all users with user name, first name, last name or email matching
    // the given search field
    List<Employee> searchUser(String searchField, boolean onlyActive,int firstPos,int LINEPERPAGE);

    List<Employee> searchUsers(SearchEmployeeParam param,int firstPos,int LINEPERPAGE);
    
    public  long getTotalCount(SearchEmployeeParam param);
    
    
    /**
     * 
     * @param entityId
     * @param fileName
     * @return
     */
    public Document getDocumentByEntityIdAndFileName(Long entityId,
            String fileName);
}
