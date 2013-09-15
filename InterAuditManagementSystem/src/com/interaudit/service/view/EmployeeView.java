package com.interaudit.service.view;

import java.io.Serializable;
import java.util.List;

import com.interaudit.domain.model.Employee;
import com.interaudit.domain.model.Position;
import com.interaudit.service.param.SearchEmployeeParam;

public class EmployeeView implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private List<Employee> employees;
	private SearchEmployeeParam param;
	private List<Position> roles;
	
	
	
	public EmployeeView() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public EmployeeView(List<Employee> employees, 
			SearchEmployeeParam param,List<Position> roles) {
		super();
		this.employees = employees;
		this.param = param;
		this.roles = roles;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public SearchEmployeeParam getParam() {
		return param;
	}

	public void setParam(SearchEmployeeParam param) {
		this.param = param;
	}

	public List<Position> getRoles() {
		return roles;
	}

	public void setRoles(List<Position> roles) {
		this.roles = roles;
	}

	

	
	

	
	
	

}
