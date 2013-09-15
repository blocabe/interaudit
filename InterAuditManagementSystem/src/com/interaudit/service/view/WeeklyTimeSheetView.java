package com.interaudit.service.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.interaudit.domain.model.Employee;
import com.interaudit.domain.model.Timesheet;
import com.interaudit.domain.model.data.Option;
import com.interaudit.domain.model.data.TimesheetOption;

public class WeeklyTimeSheetView implements Serializable{
	
	private static final long serialVersionUID = 1L;

	
	private String year;
	private String week;
	private Employee employee;
	
	private List<Option> yearOptions;
	private List<Option> employeeOptions;
	private List<Option> missionOptions;
	private List<Option> taskOptions;
	private Timesheet timesheet;
	private List<Option> timeOptions;
	private List<Option> taskOptions2;
	private List<TimesheetOption> timesheetOptions;
	
	
	
	public WeeklyTimeSheetView(
			Timesheet timesheet, 
			String year,
			String week, 
			Employee employee,
			List<Option> yearOptions,
			List<Option> employeeOptions,
			List<Option> missionOptions,
			List<Option> taskOptions,List<Option> taskOptions2,List<TimesheetOption> timesheetOptions) {
		super();
		this.timesheet = timesheet;
		this.year = year;
		this.week = week;
		this.employee = employee;
		this.yearOptions = yearOptions;
		this.employeeOptions = employeeOptions;
		this.missionOptions = missionOptions;
		this.taskOptions = taskOptions;
		this.taskOptions2 = taskOptions2;
		this.timesheetOptions=timesheetOptions;
	}
	
	public WeeklyTimeSheetView() {
		super();
		// TODO Auto-generated constructor stub
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
	
	
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	

	public Timesheet getTimesheet() {
		return timesheet;
	}

	public void setTimesheet(Timesheet timesheet) {
		this.timesheet = timesheet;
	}

	public List<Option> getMissionOptions() {
		return missionOptions;
	}

	public void setMissionOptions(List<Option> missionOptions) {
		this.missionOptions = missionOptions;
	}

	public List<Option> getTaskOptions() {
		return taskOptions;
	}

	public void setTaskOptions(List<Option> taskOptions) {
		this.taskOptions = taskOptions;
	}
	
	public List<Option> getTimeOptions() {
		if(timeOptions == null){
			timeOptions = new ArrayList<Option>();
			for(int indice =0; indice <= /*32*/52; indice++){
				int part1 = (indice / 4);
				String key = part1 +"";
				int reste = indice % 4;
				switch(reste){
				case 0:
					key += "h00";
					break;
				case 1:
					key += "h15";
					break;
				case 2:
					key += "h30";
					break;
				case 3:
					key += "h45";
					break;
				}
				timeOptions.add(new Option(Integer.toString(indice),key));
		   }
		}

		return timeOptions;
	}

	public List<Option> getTaskOptions2() {
		return taskOptions2;
	}

	public void setTaskOptions2(List<Option> taskOptions2) {
		this.taskOptions2 = taskOptions2;
	}

	public void setTimeOptions(List<Option> timeOptions) {
		this.timeOptions = timeOptions;
	}

	public List<TimesheetOption> getTimesheetOptions() {
		return timesheetOptions;
	}

	public void setTimesheetOptions(List<TimesheetOption> timesheetOptions) {
		this.timesheetOptions = timesheetOptions;
	}
	
	
	
	
}
