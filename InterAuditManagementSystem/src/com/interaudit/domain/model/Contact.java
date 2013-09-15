package com.interaudit.domain.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "CONTACTS",schema="interaudit")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE )
public class Contact implements Serializable{

    public Contact() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	/*
	@GeneratedValue(generator="ContactSeq")
	@SequenceGenerator(name="ContactSeq",sequenceName="CONTACT_SEQ", allocationSize=1)
	*/
	private Long id;
	
	@Column(name = "SEX" , nullable = false)
	private String sex;
	
	@Column(name = "NAME" , nullable = false)
    private String contactName;
	
	@Column(name = "F_NAME" , nullable = false)
    private String firstName;
	
	@Column(name = "L_NAME" , nullable = false)
    private String lastName;
	
	@Column(name = "JOB" , nullable = true)
    private String jobTitle;
   
	@Column(name = "B_PHONE" , nullable = true)
    private String businessPhone;
	
	@Column(name = "B_MOBILE" , nullable = true)
    private String businessMobile;
	
	@Column(name = "B_FAX" , nullable = true)
    private String businessFax;
	
	@Column(name = "P_PHONE" , nullable = false)
    private String personalPhone;
	
	@Column(name = "P_MOBILE" , nullable = true)
    private String personalMobile;   
	
	@Column(name = "B_EMAIL" , nullable = true)
    private String companyContactMail;
	
	@Column(name = "P_EMAIL" , nullable = true)
    private String email;
	
	@Column(name = "B_WEB_ADDR" , nullable = true)
    private String companyWebAddr;
	
    private boolean active = true;
    
    @Column(name = "B_ACTIVITY" , nullable = true)
    private String mainActivity;
    
    private String updatedUser;
    
    @Column(name = "COMPANY" , nullable = false)
    private String company;
    
    
    private String address;
    
    @Temporal(TemporalType.DATE)
    private Date createDate;
    
    @Temporal(TemporalType.DATE)
    private Date updateDate;
        
    @Column(name = "VERSION", nullable = false)
	@Version
	private int version;
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="fk_customer" , nullable = false)
    private Customer customer;
    
   
    
	public Contact(String contactName, String firstName, String lastName,
			String jobTitle, String businessPhone, String businessMobile,
			String businessFax, String personalPhone, String personalMobile,
			String companyContactMail, String email, String companyWebAddr,
			boolean active, String mainActivity, 
			String company, 
			String address,
			String sex,
			Customer customer) {
		super();
		this.contactName = contactName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.jobTitle = jobTitle;
		this.businessPhone = businessPhone;
		this.businessMobile = businessMobile;
		this.businessFax = businessFax;
		this.personalPhone = personalPhone;
		this.personalMobile = personalMobile;
		this.companyContactMail = companyContactMail;
		this.email = email;
		this.companyWebAddr = companyWebAddr;
		this.active = active;
		this.mainActivity = mainActivity;
		this.company = company;
		this.address = address;
		this.sex = sex;
		this.customer = customer;
	}
	
	
	public boolean equals(Object obj){		
		if( (this.getId()== null ) || (null == obj) || ( !(obj instanceof Contact)  )){
			return false;
		}
		else{
			return this.getId().equals(((Contact)obj).getId());
		}
	}
	
	public int hashCode(){
		final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
	}
	
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBusinessFax() {
		return businessFax;
	}
	public void setBusinessFax(String businessFax) {
		this.businessFax = businessFax;
	}
	public String getBusinessMobile() {
		return businessMobile;
	}
	public void setBusinessMobile(String businessMobile) {
		this.businessMobile = businessMobile;
	}
	public String getBusinessPhone() {
		return businessPhone;
	}
	public void setBusinessPhone(String businessPhone) {
		this.businessPhone = businessPhone;
	}
	
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getCompanyContactMail() {
		return companyContactMail;
	}
	public void setCompanyContactMail(String companyContactMail) {
		this.companyContactMail = companyContactMail;
	}
	public String getCompanyWebAddr() {
		return companyWebAddr;
	}
	public void setCompanyWebAddr(String companyWebAddr) {
		this.companyWebAddr = companyWebAddr;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	
	public String getFullName(){
		return this.getFirstName() + " " +this.getLastName();
	}
	
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMainActivity() {
		return mainActivity;
	}
	public void setMainActivity(String mainActivity) {
		this.mainActivity = mainActivity;
	}
	public String getPersonalMobile() {
		return personalMobile;
	}
	public void setPersonalMobile(String personalMobile) {
		this.personalMobile = personalMobile;
	}
	public String getPersonalPhone() {
		return personalPhone;
	}
	public void setPersonalPhone(String personalPhone) {
		this.personalPhone = personalPhone;
	}
	
	
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getUpdatedUser() {
		return updatedUser;
	}
	public void setUpdatedUser(String updatedUser) {
		this.updatedUser = updatedUser;
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
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	

    

}
