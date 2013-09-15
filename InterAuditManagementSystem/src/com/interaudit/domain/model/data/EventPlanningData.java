package com.interaudit.domain.model.data;

import java.io.Serializable;
import java.util.Collection;

import com.interaudit.domain.model.ItemEventPlanning;

public class EventPlanningData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Collection<ItemEventPlanning> activities;
	private int year;
	private int weekNumber;    
	private boolean validated = false;
	private String employeeFullName;
	private Long id;
	
	
	public EventPlanningData(Long id,Collection<ItemEventPlanning> activities,
			int year, int weekNumber, boolean validated, String employeeFullName) {
		super();
		this.id=id;
		this.activities = activities;
		this.year = year;
		this.weekNumber = weekNumber;
		this.validated = validated;
		this.employeeFullName = employeeFullName;
	}
	public Collection<ItemEventPlanning> getActivities() {
		return activities;
	}
	public void setActivities(Collection<ItemEventPlanning> activities) {
		this.activities = activities;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getWeekNumber() {
		return weekNumber;
	}
	public void setWeekNumber(int weekNumber) {
		this.weekNumber = weekNumber;
	}
	public boolean isValidated() {
		return validated;
	}
	public void setValidated(boolean validated) {
		this.validated = validated;
	}
	public String getEmployeeFullName() {
		return employeeFullName;
	}
	public void setEmployeeFullName(String employeeFullName) {
		this.employeeFullName = employeeFullName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

}
