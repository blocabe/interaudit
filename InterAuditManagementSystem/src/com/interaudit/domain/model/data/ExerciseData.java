package com.interaudit.domain.model.data;

import java.util.Date;

public class ExerciseData {
	
	private Long id;
	private double budgetCurrent;
	private double budgetReported;
	private double budgetTotal;
	private int year;
	private String status;
	private Date startDate;
	private Date endDate;
	private boolean isAppproved;
	
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public double getBudgetCurrent() {
		return budgetCurrent;
	}
	public void setBudgetCurrent(double budgetCurrent) {
		this.budgetCurrent = budgetCurrent;
	}
	public double getBudgetReported() {
		return budgetReported;
	}
	public void setBudgetReported(double budgetReported) {
		this.budgetReported = budgetReported;
	}
	public double getBudgetTotal() {
		return budgetTotal;
	}
	public void setBudgetTotal(double budgetTotal) {
		this.budgetTotal = budgetTotal;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public boolean isAppproved() {
		return isAppproved;
	}
	public void setAppproved(boolean isAppproved) {
		this.isAppproved = isAppproved;
	}

}
