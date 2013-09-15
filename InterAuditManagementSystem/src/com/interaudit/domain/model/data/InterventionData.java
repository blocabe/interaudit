package com.interaudit.domain.model.data;

import java.io.Serializable;



public class InterventionData implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private String interventionReference;
	private String customerName;
	private String customerCode;
	private String year;
	private String status;
	private String origin;
	private String associate;
	private String manager;
	private String type;
	private String assignee;
	private String description;
	
	
	
	public InterventionData() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public InterventionData(Long id, String interventionReference,
			String customerName, String customerCode, String year,
			String status, String origin, String associate, String manager,
			String type, String assignee, String description) {
		super();
		this.id = id;
		this.interventionReference = interventionReference;
		this.customerName = customerName;
		this.customerCode = customerCode;
		this.year = year;
		this.status = status;
		this.origin = origin;
		this.associate = associate;
		this.manager = manager;
		this.type = type;
		this.assignee = assignee;
		this.description = description;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getInterventionReference() {
		return interventionReference;
	}
	public void setInterventionReference(String interventionReference) {
		this.interventionReference = interventionReference;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getAssociate() {
		return associate;
	}
	public void setAssociate(String associate) {
		this.associate = associate;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAssignee() {
		return assignee;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	

	
	
	
	
	
	

}
