package com.interaudit.service.view;

import java.util.ArrayList;
import java.util.List;

import com.interaudit.domain.model.Timesheet;



public class SearchTimesheetView {

	private List<String> startPeriodDates;
	private List<String> endPeriodDates;
	
	private List<Timesheet> timesheets;
	
	public String treeJSonObject;
	
	public SearchTimesheetView() {
		this.timesheets = new ArrayList<Timesheet>();
	}
	
	public List<Timesheet> getTimesheets() {
		return timesheets;
	}
	public void setTimesheets(List<Timesheet> timesheets) {
		this.timesheets = timesheets;
	}
	public List<String> getStartPeriodDates() {
		return startPeriodDates;
	}
	public void setStartPeriodDates(List<String> startPeriodDates) {
		this.startPeriodDates = startPeriodDates;
	}
	public List<String> getEndPeriodDates() {
		return endPeriodDates;
	}
	public void setEndPeriodDates(List<String> endPeriodDates) {
		this.endPeriodDates = endPeriodDates;
	}

	//Assuming that timesheets are ordered by month then user full name
	public String getTreeJSonObject() {
		return treeJSonObject;
	}
	
	public void setTreeJSonObject(String jSonObject) {
		this.treeJSonObject = jSonObject;
	}
	
	public int getNumberOfTimesheetFound() {
		return timesheets.size();
	}
	
}
