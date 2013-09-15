package com.interaudit.service.view;

import java.io.Serializable;
import java.util.List;

import com.interaudit.domain.model.data.MissionData;
import com.interaudit.domain.model.data.Option;
import com.interaudit.service.param.SearchMissionParam;

public class MissionView implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private List<MissionData>  missions;
	private SearchMissionParam param;
	private List<Option> yearOptions;
	private List<Option> customerOptions;
	private List<Option> managerOptions;
	private List<Option> employeOptions;
	private List<Option> missionOptions;
	private String employeeName;
	private String sortedByName ;
	
	
	public MissionView() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	


	public MissionView(List<MissionData> missions, SearchMissionParam param,
			List<Option> yearOptions, List<Option> customerOptions,
			List<Option> managerOptions,List<Option> employeOptions,String employeeName,List<Option> missionOptions) {
		super();
		this.missions = missions;
		this.param = param;
		this.yearOptions = yearOptions;
		this.customerOptions = customerOptions;
		this.managerOptions = managerOptions;
		this.employeOptions = employeOptions;
		this.employeeName = employeeName;
		this.missionOptions = missionOptions;
	}




	public List<MissionData> getMissions() {
		return missions;
	}


	public void setMissions(List<MissionData> missions) {
		this.missions = missions;
	}


	public SearchMissionParam getParam() {
		return param;
	}


	public void setParam(SearchMissionParam param) {
		this.param = param;
	}


	

	public List<Option> getManagerOptions() {
		return managerOptions;
	}


	public void setManagerOptions(List<Option> managerOptions) {
		this.managerOptions = managerOptions;
	}




	public List<Option> getYearOptions() {
		return yearOptions;
	}




	public void setYearOptions(List<Option> yearOptions) {
		this.yearOptions = yearOptions;
	}




	public List<Option> getCustomerOptions() {
		return customerOptions;
	}




	public void setCustomerOptions(List<Option> customerOptions) {
		this.customerOptions = customerOptions;
	}




	public List<Option> getEmployeOptions() {
		return employeOptions;
	}




	public void setEmployeOptions(List<Option> employeOptions) {
		this.employeOptions = employeOptions;
	}




	public String getEmployeeName() {
		return employeeName;
	}




	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}




	public List<Option> getMissionOptions() {
		return missionOptions;
	}




	public void setMissionOptions(List<Option> missionOptions) {
		this.missionOptions = missionOptions;
	}




	public String getSortedByName() {
		return sortedByName;
	}




	public void setSortedByName(String sortedByName) {
		this.sortedByName = sortedByName;
	}
	

	
	
	

}
