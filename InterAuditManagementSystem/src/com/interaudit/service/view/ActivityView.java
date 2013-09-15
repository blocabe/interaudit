package com.interaudit.service.view;

import java.io.Serializable;
import java.util.List;

import com.interaudit.domain.model.data.ActivityData;
import com.interaudit.domain.model.data.Option;
import com.interaudit.service.param.SearchActivityParam;

public class ActivityView implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private List<ActivityData>  activities;
	private SearchActivityParam param;
	private List<Option> yearOptions;
	//private List<Option> customerOptions;
	private List<Option> employeeOptions;
	
	
	public ActivityView() {
		super();
		// TODO Auto-generated constructor stub
	}



	public ActivityView(List<ActivityData> activities,
			SearchActivityParam param, List<Option> yearOptions,
			 List<Option> employeeOptions) {
		super();
		this.activities = activities;
		this.param = param;
		this.yearOptions = yearOptions;
		this.employeeOptions = employeeOptions;
	}


	public List<ActivityData> getActivities() {
		return activities;
	}




	public void setActivities(List<ActivityData> activities) {
		this.activities = activities;
	}




	public SearchActivityParam getParam() {
		return param;
	}




	public void setParam(SearchActivityParam param) {
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
