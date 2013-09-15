package com.interaudit.domain.model.data;

import java.util.Date;

import com.interaudit.domain.model.Mission;



public class WeekAssignmentPerEmployee {

	private static final long serialVersionUID = 1L;
	private Long id;
	private Date startDate;
	//private Date endDate;
	
	private Mission activity;
	private String code;
    private String description;
    private String status;

	private WeekPlanning week;
	
	public WeekAssignmentPerEmployee(){}
	
	public WeekAssignmentPerEmployee( WeekPlanning week,Date startDate,
	 /*Date endDate,*/Mission activity, String code,String description
	 ){
		
		this.week = week;
		this.startDate = startDate;
		//this.endDate = endDate;
		this.activity = activity;
		
		this.code = code;
		this.description = description;
		
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	

	public Mission getActivity() {
		return activity;
	}

	public void setActivity(Mission activity) {
		this.activity = activity;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/*
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	*/

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public WeekPlanning getWeek() {
		return week;
	}

	public void setWeek(WeekPlanning week) {
		this.week = week;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	

	


}
