package com.interaudit.service.view;

import java.util.Date;
import java.util.Set;

public class WeekPlanningView {
	
	private Long id;	
	private Set<DayAssignmentPerEmployeeView> cells;	
	private Long planningId;
	private String status;
	private Date startDateOfWeek;
	private Date endDateOfWeek;
	private int weekNumber;
	public Set<DayAssignmentPerEmployeeView> getCells() {
		return cells;
	}
	public void setCells(Set<DayAssignmentPerEmployeeView> cells) {
		this.cells = cells;
	}
	public Date getEndDateOfWeek() {
		return endDateOfWeek;
	}
	public void setEndDateOfWeek(Date endDateOfWeek) {
		this.endDateOfWeek = endDateOfWeek;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPlanningId() {
		return planningId;
	}
	public void setPlanningId(Long planningId) {
		this.planningId = planningId;
	}
	public Date getStartDateOfWeek() {
		return startDateOfWeek;
	}
	public void setStartDateOfWeek(Date startDateOfWeek) {
		this.startDateOfWeek = startDateOfWeek;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getWeekNumber() {
		return weekNumber;
	}
	public void setWeekNumber(int weekNumber) {
		this.weekNumber = weekNumber;
	}
	
	

}
