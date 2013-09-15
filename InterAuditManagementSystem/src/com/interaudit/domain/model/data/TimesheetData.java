package com.interaudit.domain.model.data;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;



public class TimesheetData implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private String employeeName;
	private Long employeeId;
	private String year;
	private String status;	
	private int weekNumber;
	
	private Date validationDate;
	private Date startDateOfWeek;
	private Date endDateOfWeek;
	private Date lastRejectedDate;
	
	public TimesheetData(Long id,String employeeName, Long employeeId, String year,String status,int weekNumber,
			 Date validationDate,
	 Date startDateOfWeek,
	 Date endDateOfWeek, Date lastRejectedDate) {
		super();
		this.id = id;
		this.employeeName = employeeName;
		this.employeeId = employeeId;
		this.year = year;
		this.status = status;
		this.weekNumber = weekNumber;
		this.validationDate = validationDate;
		this.startDateOfWeek = startDateOfWeek;
		this.endDateOfWeek =endDateOfWeek;
		this.lastRejectedDate = lastRejectedDate;
		
	}
	
	public TimesheetData() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public Long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
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

	public Date getValidationDate() {
		return validationDate;
	}

	public void setValidationDate(Date validationDate) {
		this.validationDate = validationDate;
	}

	public Date getStartDateOfWeek() {
		return startDateOfWeek;
	}

	public void setStartDateOfWeek(Date startDateOfWeek) {
		this.startDateOfWeek = startDateOfWeek;
	}

	public Date getEndDateOfWeek() {
		return endDateOfWeek;
	}

	public void setEndDateOfWeek(Date endDateOfWeek) {
		this.endDateOfWeek = endDateOfWeek;
	}

	public Date getLastRejectedDate() {
		return lastRejectedDate;
	}

	public void setLastRejectedDate(Date lastRejectedDate) {
		this.lastRejectedDate = lastRejectedDate;
	}
	
	
	
	
	

	
	
	
	
	
	

}
