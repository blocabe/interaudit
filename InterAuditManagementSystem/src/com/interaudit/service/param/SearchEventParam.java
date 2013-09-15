package com.interaudit.service.param;


public class SearchEventParam {
	
	private int year;
	private int day;
	private int month;
	private Long  employee;
	private Long  mission;
	
	
	
	public SearchEventParam() {
		super();
		// TODO Auto-generated constructor stub
	}



	public SearchEventParam(int year, int day, int month, Long employee,
			Long mission) {
		super();
		this.year = year;
		this.day = day;
		this.month = month;
		this.employee = employee;
		this.mission = mission;
	}



	public int getYear() {
		return year;
	}



	public void setYear(int year) {
		this.year = year;
	}



	public int getDay() {
		return day;
	}



	public void setDay(int day) {
		this.day = day;
	}



	public int getMonth() {
		return month;
	}



	public void setMonth(int month) {
		this.month = month;
	}



	public Long getEmployee() {
		return employee;
	}



	public void setEmployee(Long employee) {
		this.employee = employee;
	}



	public Long getMission() {
		return mission;
	}



	public void setMission(Long mission) {
		this.mission = mission;
	}
	
	



	
	
	
	
	
	
	
	
	

}
