package com.interaudit.domain.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.interaudit.domain.model.data.Option;


@Entity
@Table(name = "CUSTOMERS",schema="interaudit")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE )
public class Customer implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	/*
	@GeneratedValue(generator="CustomerSeq")
	@SequenceGenerator(name="CustomerSeq",sequenceName="CUSTOMER_SEQ", allocationSize=1)
	*/
	private Long id;
	
	 @Column(name = "WEB_ADDRESS", nullable = true)
	private String companyWebAddr;
    private boolean active = true;
    
    private String country;
    private String mainAddress;
    private String mainBillingAddress;
    
    @Column(name = "ACTIVITY", nullable = true)
    private String mainActivity;
    
    @Temporal(TemporalType.DATE)
    private Date createDate;
    
    @Temporal(TemporalType.DATE)
    private Date updateDate;
    
    private String updatedUser;
    
    @Column(name = "COMPY_NAME", nullable = true, unique = true)
    private String companyName;
    
    @Column(name = "FAX", nullable = true)
    private String fax;
    
    @Column(name = "PHONE", nullable = true)
    private String phone;
    
    @Column(name = "MOBILE", nullable = true)
    private String mobile;
   
    
    @Column(name = "P_PHONE", nullable = true)
    private String personalPhone;
    
    @Column(name = "P_MOBILE", nullable = true)
    private String personalMobile;
    
    private String email;
    
    @Column(name = "CODE", nullable = false , unique = true)
    private String code;
    
    @Column(name = "VERSION", nullable = false)
	@Version
	private int version;
    
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="fk_associe" , nullable = false)
    private Employee associeSignataire;
    
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="fk_manager" , nullable = false)
    private Employee customerManager;
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="fk_contracttype" , nullable = false)
    private MissionTypeTaskLink defaultContractType;
    
    @Column(name = "CONTRACT_AMOUNT", nullable = true)
    private double defaultContractAmount;
    
    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="fk_origin" , nullable = true)
    private Origin origin;
    
    @OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Set<Contact> contacts;
    
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Contract> contracts = new HashSet<Contract>();
   
    
    public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    public  List<Option> getContactOptions(){
		   
 
		   List<Option> options= new ArrayList<Option>();
		   for(Contact contact : this.contacts){
				options.add(new Option(Long.toString(contact.getId()), contact.getFullName() ));
		   }
		   
		   return options;
	} 
    
    
  
	public Customer(Employee associeSignataire,Employee customerManager,Origin origin, String code, boolean active, 
			String companyName, String mainActivity,String email, 
			 String phone,MissionTypeTaskLink defaultContractType,double defaultContractAmount) {
		super();
		this.associeSignataire = associeSignataire;
		this.customerManager = customerManager;
		this.origin = origin;
		this.code = code;
		this.active = active;
		this.mainActivity = mainActivity;
		this.companyName = companyName;		
		this.email = email;
		this.phone = phone;
		this.defaultContractType = defaultContractType;
		this.defaultContractAmount = defaultContractAmount;
		
		//contract.setCustomer(this);
		//this.getContracts().add(contract);
		this.createDate = new Date();
		this.updateDate = new Date();
	}
	
	
	public boolean equals(Object obj){		
		if( (this.getId()== null ) || (null == obj) || ( !(obj instanceof Customer)  )){
			return false;
		}
		else{
			return this.getId().equals(((Customer)obj).getId());
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

	

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyWebAddr() {
		return companyWebAddr;
	}

	public void setCompanyWebAddr(String companyWebAddr) {
		this.companyWebAddr = companyWebAddr;
	}

	public Set<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(Set<Contact> contacts) {
		this.contacts = contacts;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMainActivity() {
		return mainActivity;
	}

	public void setMainActivity(String mainActivity) {
		this.mainActivity = mainActivity;
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
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}	
	
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	

	

	public Set<Contract> getContracts() {
		return contracts;
	}

	public void setContracts(Set<Contract> contracts) {
		this.contracts = contracts;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}



	public Employee getAssocieSignataire() {
		return associeSignataire;
	}



	public void setAssocieSignataire(Employee associeSignataire) {
		this.associeSignataire = associeSignataire;
	}



	public Employee getCustomerManager() {
		return customerManager;
	}



	public void setCustomerManager(Employee customerManager) {
		this.customerManager = customerManager;
	}



	public Origin getOrigin() {
		return origin;
	}



	public void setOrigin(Origin origin) {
		this.origin = origin;
	}



	public String getCountry() {
		return country;
	}



	public void setCountry(String country) {
		this.country = country;
	}



	public String getMainAddress() {
		return mainAddress;
	}



	public void setMainAddress(String mainAddress) {
		this.mainAddress = mainAddress;
	}



	public String getMainBillingAddress() {
		return mainBillingAddress;
	}



	public void setMainBillingAddress(String mainBillingAddress) {
		this.mainBillingAddress = mainBillingAddress;
	}

	public MissionTypeTaskLink getDefaultContractType() {
		return defaultContractType;
	}

	public void setDefaultContractType(MissionTypeTaskLink defaultContractType) {
		this.defaultContractType = defaultContractType;
	}

	public double getDefaultContractAmount() {
		return defaultContractAmount;
	}

	public void setDefaultContractAmount(double defaultContractAmount) {
		this.defaultContractAmount = defaultContractAmount;
	}
	
	
    
    
}
