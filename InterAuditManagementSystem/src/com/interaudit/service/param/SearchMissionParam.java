package com.interaudit.service.param;

import java.util.ArrayList;
import java.util.List;

public class SearchMissionParam {
	
	private String year;
	private Long  manager;
	private Long  customer;
	private Long  employee;
	private Long  missionId;
	
	private String not_started_key = null;
	private String pending_key = null;
	private String ongoing_key = null;
	private String stopped_key = null;
	private String closed_key  = null;
	private String cancelled_key = null;
	
	
	
	
	
	
	
	public SearchMissionParam(String year, Long manager, Long customer,Long  employee,String not_started_key,
			String pending_key, String ongoing_key, String stopped_key,
			String closed_key, String cancelled_key,Long  missionId) {
		super();
		this.year = year;
		this.manager = manager;
		this.customer = customer;
		this.employee = employee;
		this.not_started_key = not_started_key;
		this.pending_key = pending_key;
		this.ongoing_key = ongoing_key;
		this.stopped_key = stopped_key;
		this.closed_key = closed_key;
		this.cancelled_key = cancelled_key;
		this.missionId =  missionId;
	}

	public SearchMissionParam() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	
	public List<String> getListOfStatus() {
		
		List<String>  listOfStatus = new ArrayList<String>();
		if(not_started_key != null)listOfStatus.add(not_started_key);
		if(pending_key != null)listOfStatus.add(pending_key);
		if(ongoing_key != null)listOfStatus.add(ongoing_key);
		if(stopped_key != null)listOfStatus.add(stopped_key);
		if(closed_key != null)listOfStatus.add(closed_key);
		if(cancelled_key != null)listOfStatus.add(cancelled_key);
		
		return listOfStatus;
	}
	
	public Long getManager() {
		return manager;
	}
	public void setManager(Long manager) {
		this.manager = manager;
	}
	public Long getCustomer() {
		return customer;
	}
	public void setCustomer(Long customer) {
		this.customer = customer;
	}

	public String getPending_key() {
		return pending_key;
	}

	public void setPending_key(String pending_key) {
		this.pending_key = pending_key;
	}

	public String getOngoing_key() {
		return ongoing_key;
	}

	public void setOngoing_key(String ongoing_key) {
		this.ongoing_key = ongoing_key;
	}

	public String getStopped_key() {
		return stopped_key;
	}

	public void setStopped_key(String stopped_key) {
		this.stopped_key = stopped_key;
	}

	public String getClosed_key() {
		return closed_key;
	}

	public void setClosed_key(String closed_key) {
		this.closed_key = closed_key;
	}

	public String getCancelled_key() {
		return cancelled_key;
	}

	public void setCancelled_key(String cancelled_key) {
		this.cancelled_key = cancelled_key;
	}

	public Long getEmployee() {
		return employee;
	}

	public void setEmployee(Long employee) {
		this.employee = employee;
	}

	public Long getMissionId() {
		return missionId;
	}

	public void setMissionId(Long missionId) {
		this.missionId = missionId;
	}

	public String getNot_started_key() {
		return not_started_key;
	}

	public void setNot_started_key(String not_started_key) {
		this.not_started_key = not_started_key;
	}
	
	
	
	
	
	

}
