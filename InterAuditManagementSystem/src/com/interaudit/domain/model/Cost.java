package com.interaudit.domain.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "MISSION_COSTS",schema="interaudit")
public class Cost implements java.io.Serializable{
	
	
	public Cost() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	public Cost(String costCode, String description, double amount,
			String currencyCode, Date createDate, Employee owner,
			Mission mission) {
		super();
		this.costCode = costCode;
		this.description = description;
		this.amount = amount;
		this.currencyCode = currencyCode;
		this.createDate = createDate;
		this.owner = owner;
		this.mission = mission;
	}
	
	public boolean equals(Object obj){		
		if((this.getId()== null ) || (null == obj) || ( !(obj instanceof Cost)  )){
			return false;
		}
		else{
			return this.getId().equals(((Cost)obj).getId());
		}
	}
	
	public int hashCode(){
		final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
	}
	
	
	@Id
	@GeneratedValue
	/*
	@GeneratedValue(generator="CostSeq")
	@SequenceGenerator(name="CostSeq",sequenceName="COST_SEQ", allocationSize=1)
	*/
	private Long id;
	
	@Column(name = "VERSION", nullable = false)
	@Version
	private int version;
	
	private String costCode;
	
	 @Column(name = "DESCRIPTION", nullable = true)
	private String description;
	 
	private double amount;
	
	@Column(name = "CURRENCY", nullable = false, length=3)
	private String currencyCode;
	
	private Date createDate;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Employee owner;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Mission mission;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCostCode() {
		return costCode;
	}
	public void setCostCode(String costCode) {
		this.costCode = costCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Employee getOwner() {
		return owner;
	}
	public void setOwner(Employee owner) {
		this.owner = owner;
	}
	
	public Mission getMission() {
		return mission;
	}
	public void setMission(Mission mission) {
		this.mission = mission;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	
	
	
	

}
