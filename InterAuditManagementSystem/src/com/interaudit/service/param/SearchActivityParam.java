package com.interaudit.service.param;


public class SearchActivityParam {
	
	private String year;
	private Long  employee;
	
	
	public SearchActivityParam() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public SearchActivityParam(String year, Long employee) {
		super();
		this.year = year;
		this.employee = employee;
	}

	
	
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	

	public Long getEmployee() {
		return employee;
	}

	public void setEmployee(Long employee) {
		this.employee = employee;
	}



	
	
	
	
	
	
	
	
	

}
