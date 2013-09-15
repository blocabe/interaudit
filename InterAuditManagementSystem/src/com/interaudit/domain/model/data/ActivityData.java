package com.interaudit.domain.model.data;

import java.io.Serializable;
import java.util.Date;



public class ActivityData implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private String customerName;
	private String missionCode;
	private String activityDescription;
	private Date   startDate;
	private Date   endDate;
	private Date   updateDate;
	private String status;
	private String manager;
	private String employeeName;
	private String year;
	private String comments;
	
	public ActivityData(Long id,
			String customerName,
			String missionCode, 
			String activityDescription,
			Date startDate,
			Date endDate,  
			Date updateDate,
			String status,
			String manager,
			String employeeName,
			String year,
			String comments) {
		super();
		this.id = id;		
		this.customerName = customerName;
		this.missionCode = missionCode;
		this.activityDescription = activityDescription;
		this.startDate = startDate;
		this.endDate = endDate;
		this.updateDate = updateDate;
		this.status = status;
		this.manager = manager;
		this.employeeName = employeeName;
		this.year = year;
		this.comments = comments;
	}

	
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getMissionCode() {
		return missionCode;
	}

	public void setMissionCode(String missionCode) {
		this.missionCode = missionCode;
	}

	public String getActivityDescription() {
		return activityDescription;
	}

	public void setActivityDescription(String activityDescription) {
		this.activityDescription = activityDescription;
	}

	

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}





	public String getComments() {
		return comments;
	}





	public void setComments(String comments) {
		this.comments = comments;
	}

}
