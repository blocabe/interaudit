package com.interaudit.domain.model;

import java.util.Date;
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
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.interaudit.domain.dao.exc.ValidationException;


@Entity
@Table(name = "EMPLOYEES",schema="interaudit")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE )
public class Employee implements java.io.Serializable{
		
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Long id;
	/*@SequenceGenerator(name="EmployeeSeq",sequenceName="EMPLOYEE_SEQ", allocationSize=1)*/
	
	@Column(name = "VERSION", nullable = false)
	@Version
	private int version;
	
    //private String userName;
    private String firstName;
    private String lastName;
    private String jobTitle;
    //private String country;
    //private String phone;
    private String mobile;
    //private String personalPhone;
    //private String personalMobile;
  
    private String email;
    //private String webAddress;
    //private String photo;
    private String code;
    
    @Column(name = "active")
    private boolean userActive = true;
    private String password;
    
    @Column( unique = true)
    private String login;
    private String updateUser;
    
    @Column(name = "date_of_hiring")
    @Temporal(TemporalType.DATE)
    private Date dateOfHiring;
    
    @Column(name = "date_end_of_hiring")
    @Temporal(TemporalType.DATE)
    private Date dateEndOfHiring;
    
    @Transient
    private Integer age;
   
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    
    
	//private String address;
    
   
    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="fk_position" , nullable = true)
	private Position position = null;
  
    @OneToMany(mappedBy = "beneficiaire", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Set<Training> trainings;
    
    @OneToMany(mappedBy = "employee", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Set<AccessRight> accessRights;
    
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	 private List<MissionMember> missions;

    
    
    private double prixVente = 0;
	private double prixRevient = 0;
	private double coutHoraire = 0;
	
	public boolean getUserHasAccessRight(String rightCode){
		
		if(this.accessRights == null ||  this.accessRights.size()== 0){
			return false;
		}
		
		for(AccessRight accessRight : this.accessRights){
			
			if(accessRight.getRight().getCode().equalsIgnoreCase(rightCode)){
				return accessRight.isActive();
			}
		}
		
		return false;
		
	}
    
    
    /**
     * Default constructor
     */
    public Employee( ){}
    
    /**
     * @param reference
     * @param userName
     * @param firstName
     * @param lastName
     * @param password
     * @param login
     * @param userActive
     */
    public Employee( Position position, String code, /*String userName, */String firstName, String lastName, String password, String login,String email,/*String phone,*/ boolean userActive,
    		 double prixVente,double prixRevient,double coutHoraire
    )
    {
    	    	
    	this.position=position;
    	this.code=code;
    	//this.setUserName(userName);
    	this.firstName=firstName;
    	this.lastName=lastName;
    	this.password=password;
    	this.login=login;
    	this.email=email;
    	//this.setPhone(phone);
    	this.userActive=userActive;
    	this.createDate=new Date();    	
    	this.prixVente = prixVente;
    	this.prixRevient = prixRevient;
    	this.coutHoraire = coutHoraire;
    	
    }
    
    
    public boolean equals(Object obj){		
		if( (this.getId()== null ) || (null == obj) || ( !(obj instanceof Employee)  )){
			return false;
		}
		else{
			return this.getId().equals(((Employee)obj).getId());
		}
	}
	
	public int hashCode(){
		final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
	}
    
    // ======================================
    @SuppressWarnings("unused")
	// =     Methodes Lifecycle Callback    =
    // ======================================
    @PrePersist
    @PreUpdate
    private void validateData() {
        if (firstName == null || "".equals(firstName))
            throw new ValidationException("Invalid first name");
        if (lastName == null || "".equals(lastName))
            throw new ValidationException("Invalid last name");
        if (login == null || "".equals(login))
            throw new ValidationException("Invalid login");
        if (password == null || "".equals(password))
            throw new ValidationException("Invalid password");
    }

    /**
     * Cette méthode calcul l'age du client.
     */
    @PostLoad
    @PostPersist
    @PostUpdate
    public void calculateAge() {
    	/*
        if (dateOfBirth == null) {
            age = null;
            return;
        }

        Calendar birth = new GregorianCalendar();
        birth.setTime(dateOfBirth);
        Calendar now = new GregorianCalendar();
        now.setTime(new Date());
        int adjust = 0;
        if (now.get(Calendar.DAY_OF_YEAR) - birth.get(Calendar.DAY_OF_YEAR) < 0) {
            adjust = -1;
        }
        age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR) + adjust;
        */
    }

    // ======================================
    // =          Methodes publiques        =
    // ======================================

    /**
     * Given a password, this method then checks if it matches the user
     *
     * @param pwd
     * @throws ValidationException thrown if the password is empty or different than the one
     *                             store in database
     */
    public void matchPassword(String pwd) {
        if (pwd == null || "".equals(pwd))
            throw new ValidationException("Invalid password");

        // The password entered by the customer is not the same stored in database
        if (!pwd.equals(password))
            throw new ValidationException("Passwords doen't match");
    }
    /*
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	*/
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
	public String getRole() {
		return null;
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
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	/*
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	*/
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public boolean isUserActive() {
		return userActive;
	}
	public void setUserActive(boolean userActive) {
		this.userActive = userActive;
	}
	/*
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getWebAddress() {
		return webAddress;
	}
	public void setWebAddress(String webAddress) {
		this.webAddress = webAddress;
	}
	*/
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/*
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	*/
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getFullName() {
        StringBuilder sb = new StringBuilder();
        if (lastName != null) {
            sb.append(lastName);
        }
        if (firstName != null) {
            sb.append(" ").append(firstName);
        }
        return sb.toString();
    }
	/*
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	*/
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}

	/*
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	*/
	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Set<Training> getTrainings() {
		return trainings;
	}

	public void setTrainings(Set<Training> trainings) {
		this.trainings = trainings;
	}

	public double getPrixVente() {
		return prixVente;
	}

	public void setPrixVente(double prixVente) {
		this.prixVente = prixVente;
	}

	public double getPrixRevient() {
		return prixRevient;
	}

	public void setPrixRevient(double prixRevient) {
		this.prixRevient = prixRevient;
	}

	public double getCoutHoraire() {
		return coutHoraire;
	}

	public void setCoutHoraire(double coutHoraire) {
		this.coutHoraire = coutHoraire;
	}

	public Set<AccessRight> getAccessRights() {
		return accessRights;
	}

	public void setAccessRights(Set<AccessRight> accessRights) {
		this.accessRights = accessRights;
	}


	public Date getDateOfHiring() {
		return dateOfHiring;
	}


	public void setDateOfHiring(Date dateOfHiring) {
		this.dateOfHiring = dateOfHiring;
	}


	public List<MissionMember> getMissions() {
		return missions;
	}


	public void setMissions(List<MissionMember> missions) {
		this.missions = missions;
	}


	public Date getDateEndOfHiring() {
		return dateEndOfHiring;
	}


	public void setDateEndOfHiring(Date dateEndOfHiring) {
		this.dateEndOfHiring = dateEndOfHiring;
	}

	
    
    

}
