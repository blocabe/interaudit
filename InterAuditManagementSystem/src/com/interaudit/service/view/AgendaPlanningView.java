package com.interaudit.service.view;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.interaudit.domain.model.Employee;
import com.interaudit.domain.model.Event;
import com.interaudit.domain.model.ItemEventPlanning;
import com.interaudit.domain.model.data.Option;

public class AgendaPlanningView {

	
	private String year;
	private String week;
	private Employee employee;
	private boolean modifiable;
	
	private List<Option> yearOptions;
	private List<Option> employeeOptions;
	private List<Option> taskOptions;
	private Map <String,Event> activities;
	private List<Option> missionsOptions;
	private List<Option> timeOptions;
	private Set<ItemEventPlanning> events;
	
	
	public AgendaPlanningView(
			Map<String, Event> activities, String year,
			String week, Employee employee, List<Option> yearOptions,
			List<Option> employeeOptions,
			List<Option> missionsOptions,
			List<Option> taskOptions, boolean modifiable,Set<ItemEventPlanning> events) {
		super();
		this.activities = activities;
		this.year = year;
		this.week = week;
		this.modifiable = modifiable;
		this.employee = employee;
		this.yearOptions = yearOptions;
		this.employeeOptions = employeeOptions;
		this.missionsOptions = missionsOptions;
		this.taskOptions = taskOptions;
		this.events = events;
	}
	
	public AgendaPlanningView() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Map<String, Event> getActivities() {
		return activities;
	}
	public void setActivities(Map<String, Event> activities) {
		this.activities = activities;
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

	public List<Option> getMissionsOptions() {
		
		return missionsOptions;
	}

	public void setMissionsOptions(List<Option> missionsOptions) {
		this.missionsOptions = missionsOptions;
	}

	public List<Option> getTimeOptions() {
		if(timeOptions == null){
			timeOptions = new ArrayList<Option>();
			for(int indice =0; indice <= 52; indice++){
				int part1 = (indice / 4) + 8;
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

	public void setTimeOptions(List<Option> timeOptions) {
		this.timeOptions = timeOptions;
	}

	public boolean isModifiable() {
		return modifiable;
	}

	public void setModifiable(boolean modifiable) {
		this.modifiable = modifiable;
	}

	public List<Option> getTaskOptions() {
		return taskOptions;
	}

	public void setTaskOptions(List<Option> taskOptions) {
		this.taskOptions = taskOptions;
	}

	public Set<ItemEventPlanning> getEvents() {
		return events;
	}

	public void setEvents(Set<ItemEventPlanning> events) {
		this.events = events;
	}
	
	
	
	
	
}
