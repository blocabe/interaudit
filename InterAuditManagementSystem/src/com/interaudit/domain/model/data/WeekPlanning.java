package com.interaudit.domain.model.data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.interaudit.util.Constants;


public class WeekPlanning {

	private static final long serialVersionUID = 1L;
	
	
	private Set<WeekAssignmentPerEmployee> cells;
	
	private AnnualPlanning planning;
	private String status;
	private Date startDateOfWeek;
	private Date endDateOfWeek;
	private int weekNumber;
	
	public WeekPlanning( AnnualPlanning planning,	
	 Date startDateOfWeek,
	 Date endDateOfWeek,
	 int weekNumber){
		
		this();
		
		this.planning = planning;
		this.status = Constants.WEEKPLANNING_STATUS_STRING_DRAFT;
		this.startDateOfWeek = startDateOfWeek;
		this.endDateOfWeek = endDateOfWeek;
		this.weekNumber = weekNumber;
	}
	
	public int getWeekNumber() {
		return weekNumber;
	}

	public void setWeekNumber(int weekNumber) {
		this.weekNumber = weekNumber;
	}

	public Set<WeekAssignmentPerEmployee> getCells() {
		return cells;
	}

	public WeekPlanning() {
		cells = new HashSet<WeekAssignmentPerEmployee>();
	}
	
	

	
	public void addCell(WeekAssignmentPerEmployee cell) {
		this.cells.add(cell);
	}

	public void removeCell(WeekAssignmentPerEmployee cell) {
		this.cells.remove(cell);
	}

	
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public AnnualPlanning getPlanning() {
		return planning;
	}

	public void setPlanning(AnnualPlanning planning) {
		this.planning = planning;
	}

	public Date getEndDateOfWeek() {
		return endDateOfWeek;
	}

	public void setEndDateOfWeek(Date endDateOfWeek) {
		this.endDateOfWeek = endDateOfWeek;
	}

	public Date getStartDateOfWeek() {
		return startDateOfWeek;
	}

	public void setStartDateOfWeek(Date startDateOfWeek) {
		this.startDateOfWeek = startDateOfWeek;
	}

	public void setCells(Set<WeekAssignmentPerEmployee> cells) {
		this.cells = cells;
	}
}
