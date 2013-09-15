package com.interaudit.service.view;

import java.util.ArrayList;
import java.util.List;

import com.interaudit.domain.model.AccessRight;
import com.interaudit.domain.model.Employee;
import com.interaudit.domain.model.Position;
import com.interaudit.domain.model.Right;
import com.interaudit.domain.model.data.Option;

public class AccessRightsView {

	private List<Employee> employees;
	private List<Right> rights;
	private List<String> listBoxes;
	private List<Position> roles;
	private String type;
	private String employeeId;
	private List<Option> employeeOptions;
	
	public AccessRightsView(
			List<Employee> employees,
			List<Right> rights,
			String type,
			List<Position> roles,String employeeId,List<Option> employeeOptions) {
		super();
		this.employees = employees;
		this.rights = rights;
		this.type = type;
		this.roles = roles;
		this.employeeId = employeeId;
		buildListBoxes();
		this.employeeOptions = employeeOptions;
	}
	
	public AccessRightsView() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getCurrentEmployeName(){
		Long currentId=Long.parseLong(employeeId);
		for( Employee employee : employees ){
			if(currentId.longValue() == employee.getId()){
				return employee.getFullName();
			}
		}
		
		return null;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public List<Right> getRights() {
		return rights;
	}

	public void setRights(List<Right> rights) {
		this.rights = rights;
	}
	
	private void buildListBoxes()
	{
		
		this.listBoxes = new ArrayList<String>(); 
		for (Employee employee : this.employees)
		{
			for(AccessRight accessRight : employee.getAccessRights()){
				if( accessRight.isActive()){
					String checkBoxName = accessRight.getRight().getId()+"##"+employee.getCode();
					this.listBoxes.add(checkBoxName);
				}
			}
			
		}
	}

	public List<String> getListBoxes() {
		return listBoxes;
	}

	public void setListBoxes(List<String> listBoxes) {
		this.listBoxes = listBoxes;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Position> getRoles() {
		return roles;
	}

	public void setRoles(List<Position> roles) {
		this.roles = roles;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public List<Option> getEmployeeOptions() {
		return employeeOptions;
	}

	public void setEmployeeOptions(List<Option> employeeOptions) {
		this.employeeOptions = employeeOptions;
	}

	
/*
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
*/
	
}
