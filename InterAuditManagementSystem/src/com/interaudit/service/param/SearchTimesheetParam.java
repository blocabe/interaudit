package com.interaudit.service.param;

import java.util.ArrayList;
import java.util.List;

public class SearchTimesheetParam {
	private String year;
	private int  week;
	private Long  employeeId;
	
	private String draftKey = null;
	private String submittedKey = null;
	private String validatedKey = null;

	
	
	
	public SearchTimesheetParam() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SearchTimesheetParam(String year, int week, Long employeeId,
			String draftKey, String submittedKey, String validatedKey) {
		super();
		this.year = year;
		this.week = week;
		this.employeeId = employeeId;
		this.draftKey = draftKey;
		this.submittedKey = submittedKey;
		this.validatedKey = validatedKey;
	}
	
	public List<String> getListOfStatus() {
		
		List<String>  listOfStatus = new ArrayList<String>();
		
		if(draftKey != null)listOfStatus.add(draftKey);
		if(submittedKey != null)listOfStatus.add(submittedKey);
		if(validatedKey != null)listOfStatus.add(validatedKey);
	
		return listOfStatus;
	}
	
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public int getWeek() {
		return week;
	}
	public void setWeek(int week) {
		this.week = week;
	}
	public Long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	public String getDraftKey() {
		return draftKey;
	}
	public void setDraftKey(String draftKey) {
		this.draftKey = draftKey;
	}
	public String getSubmittedKey() {
		return submittedKey;
	}
	public void setSubmittedKey(String submittedKey) {
		this.submittedKey = submittedKey;
	}
	public String getValidatedKey() {
		return validatedKey;
	}
	public void setValidatedKey(String validatedKey) {
		this.validatedKey = validatedKey;
	}
	
   
    
}
