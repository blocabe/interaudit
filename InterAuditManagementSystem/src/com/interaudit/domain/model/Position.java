package com.interaudit.domain.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;


@Entity
@Table(name = "PROFILES",schema="interaudit")
public class Position implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public static final String MANAGING_PARTNER ="MGMT_PARTNER";
	public static final String PARTNER ="PARTNER";
	public static final String DIRECTOR ="DIRECTOR";
	
	public static final String SECRETAIRE ="SECRETAIRE";
	public static final String SENIOR_MANAGER ="SENIOR_MANAGER";
	public static final String MANAGER ="MANAGER";
	
	public static final String ASSISTANT_MANAGER ="ASSISTANT_MANAGER";
	public static final String SUPERVISOR ="SENIOR_MANAGER";
	public static final String SENIORS ="SENIORS";
	public static final String ASSISTANTS ="ASSISTANTS";
	
	@Id
	@GeneratedValue
	/*
	@GeneratedValue(generator="PositionSeq")
	@SequenceGenerator(name="PositionSeq",sequenceName="PROFILE_SEQ", allocationSize=1)
	*/
	private Long id;
	
	@Column(name = "VERSION", nullable = false)
	@Version
	private int version;

    private String name;
    private Date fromDate;
	private Date toDate;
	private boolean active;
	private int ordre;
	
	public boolean isPartner(){
		return (
					this.name.equalsIgnoreCase(Position.MANAGING_PARTNER) ||
					this.name.equalsIgnoreCase(Position.PARTNER) ||
					this.name.equalsIgnoreCase(Position.DIRECTOR)
				);
	}
	
	public boolean isManagerMission(){
		return (
					this.name.equalsIgnoreCase(Position.SENIOR_MANAGER) ||
					/*this.name.equalsIgnoreCase(Position.ASSISTANT_MANAGER) ||*/
					this.name.equalsIgnoreCase(Position.MANAGER)
				);
	}
	
	public boolean hasManagingPosition(){
		return (
					!this.name.equalsIgnoreCase(Position.ASSISTANTS) && 
					!this.name.equalsIgnoreCase(Position.ASSISTANT_MANAGER) &&
					!this.name.equalsIgnoreCase(Position.SENIORS) &&
					!this.name.equalsIgnoreCase(Position.SECRETAIRE)
				);
	}
	
	public boolean isManagingPosition(){
		return (
					!this.name.equalsIgnoreCase(Position.ASSISTANTS) && 
					!this.name.equalsIgnoreCase(Position.ASSISTANT_MANAGER) &&
					!this.name.equalsIgnoreCase(Position.SENIORS) &&
					!this.name.equalsIgnoreCase(Position.SECRETAIRE)
				);
	}
	
	public boolean isSecretaire(){
		return (
					
					this.name.equalsIgnoreCase(Position.SECRETAIRE)
				);
	}
	
    /**
     * 
     */
    public Position(){}
    
    public boolean equals(Object obj){		
		if( (this.getId()== null ) || (null == obj) || ( !(obj instanceof Position)  )){
			return false;
		}
		else{
			return this.getId().equals(((Position)obj).getId());
		}
	}
	
	public int hashCode(){
		final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
	}
    
    
    
    public static List<String> partnersPositionNames(){
   	 List<String> names = new ArrayList<String>();   	 
   	 names.add(Position.PARTNER);    	    	 
   	 return names;
   }
    
    
    public static List<String> managerPositionNames(){
    	 List<String> names = new ArrayList<String>(); 
    	 names.add(Position.MANAGING_PARTNER);
    	 names.add(Position.DIRECTOR);
    	 names.add(Position.SENIOR_MANAGER);
    	 names.add(Position.MANAGER);
    	 return names;
    }
    
    
    public static List<String> employeePositionNames(){
   	 List<String> names = new ArrayList<String>();
   	 names.add(Position.ASSISTANTS);
   	 names.add(Position.ASSISTANT_MANAGER);    	 
   	 names.add(Position.SENIORS);
   	 
   	 return names;
   }
    
    public static List<String> secretairePositionNames(){
      	 List<String> names = new ArrayList<String>();      	 
      	 names.add(Position.SECRETAIRE);
      	 return names;
      }
    
   
   
	/**
	 * @param name
	 * @param fromDate
	 * @param toDate
	 * @param employee
	 */
	public Position(String name, Date fromDate, Date toDate,boolean active) {
		super();
		this.name = name;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.active =  active;
	}




	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
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




	




	public boolean isActive() {
		return active;
	}




	public void setActive(boolean active) {
		this.active = active;
	}

	public int getOrdre() {
		return ordre;
	}

	public void setOrdre(int ordre) {
		this.ordre = ordre;
	}
    
    

}
