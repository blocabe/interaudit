package com.interaudit.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BANKS",schema="interaudit")
public class Bank implements java.io.Serializable{
	
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	/*
	@GeneratedValue(generator="BankSeq")
	@SequenceGenerator(name="BankSeq",sequenceName="BANK_SEQ", allocationSize=1)
	*/
	private Long id;

	@Column(name = "NAME", nullable = false, unique = true)
	private String name;
	
	@Column(name = "CODE", nullable = false, unique = true)
	private String code;
	
	@Column(name = "ACCOUNT", nullable = false, unique = true)
	private String accountNumber;
	private String contactPerson;
	private String contactPersonEmail;
	private String contactPersonPhone;
	private String contactPersonFax;
	private String codeBic;
	private boolean active = true;
	
	public Bank() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Bank(String name, String code, String accountNumber,
			String contactPerson, String contactPersonEmail,
			String contactPersonPhone, String contactPersonFax,String codeBic, boolean active) {
		super();
		this.name = name;
		this.code = code;
		this.accountNumber = accountNumber;
		this.contactPerson = contactPerson;
		this.contactPersonEmail = contactPersonEmail;
		this.contactPersonPhone = contactPersonPhone;
		this.contactPersonFax = contactPersonFax;
		this.codeBic = codeBic;
		this.active = active;
	}
	
	public boolean equals(Object obj){		
		if( (this.getId()== null ) || (null == obj) || ( !(obj instanceof Bank)  )){
			return false;
		}
		else{
			return this.getId().equals(((Bank)obj).getId());
		}
	}
	
	public int hashCode(){
		final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getContactPerson() {
		return contactPerson;
	}
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	public String getContactPersonEmail() {
		return contactPersonEmail;
	}
	public void setContactPersonEmail(String contactPersonEmail) {
		this.contactPersonEmail = contactPersonEmail;
	}
	public String getContactPersonPhone() {
		return contactPersonPhone;
	}
	public void setContactPersonPhone(String contactPersonPhone) {
		this.contactPersonPhone = contactPersonPhone;
	}
	public String getContactPersonFax() {
		return contactPersonFax;
	}
	public void setContactPersonFax(String contactPersonFax) {
		this.contactPersonFax = contactPersonFax;
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

	public String getCodeBic() {
		return codeBic;
	}

	public void setCodeBic(String codeBic) {
		this.codeBic = codeBic;
	}
	
	
	

}
