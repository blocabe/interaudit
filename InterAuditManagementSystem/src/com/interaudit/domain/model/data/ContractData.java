package com.interaudit.domain.model.data;

import java.util.Date;


public class ContractData implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Long id;	
	private String reference;
	private String description;
	private Date fromDate;
	private Date toDate;
	private String language;
	private double amount;
	private String missionType;
	private String customerName;
	private boolean agreed = false;
	private boolean valid = false;
	private boolean custActive = false;
	
	
	
	
	public ContractData(Long id, String reference, String description,
			Date fromDate, Date toDate, String language, double amount,
			String missionType, String customerName, boolean agreed,
			boolean valid,boolean custActive) {
		super();
		this.id = id;
		this.reference = reference;
		this.description = description;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.language = language;
		this.amount = amount;
		this.missionType = missionType;
		this.customerName = customerName;
		this.agreed = agreed;
		this.valid = valid;
		this.custActive = custActive;
	}




	public ContractData() {
		super();
		// TODO Auto-generated constructor stub
	}




	public Long getId() {
		return id;
	}




	public void setId(Long id) {
		this.id = id;
	}




	public String getReference() {
		return reference;
	}




	public void setReference(String reference) {
		this.reference = reference;
	}




	public String getDescription() {
		return description;
	}




	public void setDescription(String description) {
		this.description = description;
	}




	public Date getFromDate() {
		return fromDate;
	}




	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}




	public Date getToDate() {
		return toDate;
	}




	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}




	public String getLanguage() {
		return language;
	}




	public void setLanguage(String language) {
		this.language = language;
	}




	public double getAmount() {
		return amount;
	}




	public void setAmount(double amount) {
		this.amount = amount;
	}




	public String getMissionType() {
		return missionType;
	}




	public void setMissionType(String missionType) {
		this.missionType = missionType;
	}




	public String getCustomerName() {
		return customerName;
	}




	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}




	public boolean isAgreed() {
		return agreed;
	}




	public void setAgreed(boolean agreed) {
		this.agreed = agreed;
	}




	public boolean isValid() {
		return valid;
	}




	public void setValid(boolean valid) {
		this.valid = valid;
	}




	public boolean isCustActive() {
		return custActive;
	}




	public void setCustActive(boolean custActive) {
		this.custActive = custActive;
	}
	
	public boolean isPast() {
		return getToDate().before(new Date());
	}
	
	
	
	
	

}
