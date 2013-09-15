package com.interaudit.service.view;

import java.io.Serializable;
import java.util.List;

import com.interaudit.domain.model.data.Option;
import com.interaudit.domain.model.data.TimesheetData;
import com.interaudit.service.param.SearchTimesheetParam;

public class ListTimesheetsView implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private List<TimesheetData>  timesheets;
	private SearchTimesheetParam param;
	private List<Option> yearOptions;
	private List<Option> employeeOptions;
	
	
	
	public ListTimesheetsView() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	



	public ListTimesheetsView(List<TimesheetData> timesheets,
			SearchTimesheetParam param, List<Option> yearOptions,
			List<Option> employeeOptions) {
		super();
		this.timesheets = timesheets;
		this.param = param;
		this.yearOptions = yearOptions;
		this.employeeOptions = employeeOptions;
	}





	public List<TimesheetData> getTimesheets() {
		return timesheets;
	}



	public void setTimesheets(List<TimesheetData> timesheets) {
		this.timesheets = timesheets;
	}



	public SearchTimesheetParam getParam() {
		return param;
	}



	public void setParam(SearchTimesheetParam param) {
		this.param = param;
	}



	public List<Option> getYearOptions() {
		return yearOptions;
	}



	public void setYearOptions(List<Option> yearOptions) {
		this.yearOptions = yearOptions;
	}



	public List<Option> getEmployeeOptions() {
		return employeeOptions;
	}



	public void setEmployeeOptions(List<Option> employeeOptions) {
		this.employeeOptions = employeeOptions;
	}
	
	


	


	
	

	
	
	

}
