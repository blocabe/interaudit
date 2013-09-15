package com.interaudit.domain.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "CONTRACTS",schema="interaudit")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE )
public class Contract implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	/*
	@GeneratedValue(generator="ContractSeq")
	@SequenceGenerator(name="ContractSeq",sequenceName="CONTRACT_SEQ", allocationSize=1)
	*/
	private Long id;
	
	private String reference;
	private String description;
	private Date fromDate;
	private Date toDate;
	private Integer duration;
	private String language;
	
	/*private Date startDateOfMission;
	private Date dueDateOfMission;*/
	
	@Column(name = "VAL", nullable = false)
	private double amount;
	
	@Column(name = "CUR", nullable = false)
	private String currency;
	
	@Column(name = "VERSION", nullable = false)
	@Version
	private int version;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="fk_customer" , nullable = true)
	private Customer customer;
	
	@Column(nullable = false)
	private String missionType;
	
	 @Column(name = "agreed")
	 private boolean agreed = false;
	 
	 @Column(name = "valid")
	 private boolean valid = false;
	 
	 @Column(name = "interim")
	 private boolean interim = false;
	
	
	public Contract() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	public Contract( String reference, String description,
			Date fromDate, Date toDate, double amount, String currency,
			 String language, String missionType) {
		super();
		this.reference = reference;
		this.description = description;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.amount = amount;
		this.currency = currency;
		this.language = language;
		this.missionType = missionType;
		//this.startDateOfMission = startDateOfMission;
		//this.dueDateOfMission = dueDateOfMission;
	}

	
	public boolean equals(Object obj){		
		if( (this.getId()== null ) || (null == obj) || ( !(obj instanceof Contract)  )){
			return false;
		}
		else{
			return this.getId().equals(((Contract)obj).getId());
		}
	}
	
	public int hashCode(){
		final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
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
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}



	public String getMissionType() {
		return missionType;
	}



	public void setMissionType(String missionType) {
		this.missionType = missionType;
	}



	public boolean isAgreed() {
		return agreed;
	}



	public void setAgreed(boolean agreed) {
		this.agreed = agreed;
	}



	public Integer getDuration() {
		return duration;
	}



	public void setDuration(Integer duration) {
		this.duration = duration;
	}



	public String getLanguage() {
		return language;
	}



	public void setLanguage(String language) {
		this.language = language;
	}



	public boolean isValid() {
		return valid;
	}



	public void setValid(boolean valid) {
		this.valid = valid;
	}



	public boolean isInterim() {
		return interim;
	}



	public void setInterim(boolean interim) {
		this.interim = interim;
	}


/*
	public Date getStartDateOfMission() {
		return startDateOfMission;
	}



	public void setStartDateOfMission(Date startDateOfMission) {
		this.startDateOfMission = startDateOfMission;
	}



	public Date getDueDateOfMission() {
		return dueDateOfMission;
	}



	public void setDueDateOfMission(Date dueDateOfMission) {
		this.dueDateOfMission = dueDateOfMission;
	}
	*/
	

}
