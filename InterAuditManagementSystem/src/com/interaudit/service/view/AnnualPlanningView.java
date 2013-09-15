package com.interaudit.service.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.interaudit.domain.model.EventPlanning;
import com.interaudit.domain.model.Position;
import com.interaudit.domain.model.data.EventPlanningData;
import com.interaudit.domain.model.data.Option;

public class AnnualPlanningView {

	public static final int ECART_MOIS = 2;
	
	private String year;
	private int startMonth;
	private int endMonth;
	/*private String month;*/
	/*private String employeeType;*/
	
	private List<Option> yearOptions;
	private List<Option> employeeOptions;
	private List<Option> missionOptions;
	private List<Option> taskOptions;
	private Map <String,EventPlanningData> activities;
	
	private List<Position> roles;
	private String roleName;
	private int startWeekNumber;
	private int endWeekNumber;
	private Date lastUpdate;
	private List<Option> timeOptions;
	
	
	public AnnualPlanningView(
			Map<String, EventPlanningData> activities, String year,int startMonth,int endMonth,
			/*String month, String employeeType, */List<Option> yearOptions,
			List<Option> employeeOptions,List<Option> missionOptions,List<Option> taskOptions,
			List<Position> roles,String roleName,
			int startWeekNumber, int endWeekNumber,Date lastUpdate) {
		super();
		this.activities = activities;
		this.year = year;
		this.startMonth = startMonth;
		this.endMonth = endMonth;
		/*this.month = month;
		this.employeeType = employeeType;*/
		this.yearOptions = yearOptions;
		this.employeeOptions = employeeOptions;
		this.missionOptions = missionOptions;
		this.taskOptions = taskOptions;
		this.roles = roles;
		this.roleName = roleName;
		this.startWeekNumber = startWeekNumber;
		this.endWeekNumber = endWeekNumber;
		this.lastUpdate = lastUpdate;
	}
	
	public AnnualPlanningView() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public List<Long> getAllUserIds(){
		List<Long> userIds = new ArrayList<Long>();
		for(Option  option :employeeOptions){
			userIds.add(Long.parseLong(option.getId()));
		}
		return userIds;
	}
	
	public List<Integer> getAllWeeks(){
		List<Integer> weeks = new ArrayList<Integer>();
		for(int weekNumber = this.startWeekNumber;weekNumber<= this.endWeekNumber ;weekNumber++ ){
			weeks.add(weekNumber);
		}
		return weeks;
	}
	
	

	public Map<String, EventPlanningData> getActivities() {
		return activities;
	}
	public void setActivities(Map<String, EventPlanningData> activities) {
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
	/*
	public String getEmployeeType() {
		return employeeType;
	}
	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}
	
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	*/
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
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

	public List<Position> getRoles() {
		return roles;
	}

	public void setRoles(List<Position> roles) {
		this.roles = roles;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
/*
	public int getStartDateNumber() {
		return startDateNumber;
	}

	public void setStartDateNumber(int startDateNumber) {
		this.startDateNumber = startDateNumber;
	}

	public int getEndDateNumber() {
		return endDateNumber;
	}

	public void setEndDateNumber(int endDateNumber) {
		this.endDateNumber = endDateNumber;
	}
	*/

	public int getStartWeekNumber() {
		return startWeekNumber;
	}

	public void setStartWeekNumber(int startWeekNumber) {
		this.startWeekNumber = startWeekNumber;
	}

	public int getEndWeekNumber() {
		return endWeekNumber;
	}

	public void setEndWeekNumber(int endWeekNumber) {
		this.endWeekNumber = endWeekNumber;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public int getStartMonth() {
		return startMonth;
	}

	public void setStartMonth(int startMonth) {
		this.startMonth = startMonth;
	}

	public int getEndMonth() {
		return endMonth;
	}

	public void setEndMonth(int endMonth) {
		this.endMonth = endMonth;
	}
	
	public String getStartMonthAsString() {
		return getMonthAsString(this.startMonth);
	}
	
	public String getEndMonthAsString() {
		int index = this.startMonth + AnnualPlanningView.ECART_MOIS;
		if(index > 11)index = 11;
		return getMonthAsString(index);
	}
	
	
	
	
private  String getMonthAsString(int month){
    	
    	String ret = null;
    	
    	switch(month){
    	case 0:
    		ret = "Janvier";
    		break;
    		
    	case 1:
    		ret = "Fevrier";
    		break;
    		
    	case 2:
    		ret = "Mars";
    		break;
    		
    	case 3:
    		ret = "Avril";
    		break;
    		
    	case 4 :
    		ret = "Mai";
    		break;
    	
    	case 5 :
    		ret = "Juin";
    		break;
    		
    	case 6 :
    		ret = "Juillet";
    		break;
    		
    		
    	case 7:
    		ret = "Août";
    		break;
    		
    	case 8:
    		ret = "Septembre";
    		break;
    		
    	case 9:
    		ret = "Octobre";
    		break;
    		
    	case 10 :
    		ret = "Novembre";
    		break;
    	
    	case 11 :
    		ret = "Decembre";
    		break;
    		
 
    	}
    	
    	return ret;
    }


public List<Option> getTimeOptions() {
	if(timeOptions == null){
		timeOptions = new ArrayList<Option>();
		for(int indice =0; indice <= 160; indice++){
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

public static double fromTimeOptionToDouble(int indice){
	int quotient = indice/4;
	double result= 0.0d + quotient;
	int reste = indice % 4;
	switch(reste){
	case 0:
		result += 0.0d;
		break;
	case 1:
		result += 0.15d;
		break;
	case 2:
		result += 0.30d;
		break;
	case 3:
		result += 0.45d;
		break;
	}
	return result;
}
	
	
	
}
