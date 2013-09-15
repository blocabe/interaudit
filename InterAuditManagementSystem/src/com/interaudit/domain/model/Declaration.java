package com.interaudit.domain.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DECLARATIONS",schema="interaudit")
public class Declaration implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 
	@Id
	@GeneratedValue
	/*
	@GeneratedValue(generator="DeclarationSeq")
	@SequenceGenerator(name="DeclarationSeq",sequenceName="DECLARATION_SEQ", allocationSize=1)
	*/
	private Long id;
	
	private String exercise;
	private String customer;
	private Date requestDate;
	private Date receiptDate;
	private Date validityDate;
	
	boolean declaration;
	boolean passport;
	boolean active;
	
	public boolean equals(Object obj){		
		if( (this.getId()== null ) || (null == obj) || ( !(obj instanceof Declaration)  )){
			return false;
		}
		else{
			return this.getId().equals(((Declaration)obj).getId());
		}
	}
	
	public int hashCode(){
		final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
	}
	
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public Date getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
	public Date getReceiptDate() {
		return receiptDate;
	}
	public void setReceiptDate(Date receiptDate) {
		this.receiptDate = receiptDate;
	}
	public Date getValidityDate() {
		return validityDate;
	}
	public void setValidityDate(Date validityDate) {
		this.validityDate = validityDate;
	}
	public boolean isDeclaration() {
		return declaration;
	}
	public void setDeclaration(boolean declaration) {
		this.declaration = declaration;
	}
	public boolean isPassport() {
		return passport;
	}
	public void setPassport(boolean passport) {
		this.passport = passport;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getExercise() {
		return exercise;
	}
	public void setExercise(String exercise) {
		this.exercise = exercise;
	}

}
