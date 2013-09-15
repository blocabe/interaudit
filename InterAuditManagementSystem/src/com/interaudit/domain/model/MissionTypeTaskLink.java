package com.interaudit.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MISSIONTYPE_TASK",schema="interaudit")
public class MissionTypeTaskLink implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(name = "ID", nullable = false)
	private Long id;
	private String missionTypeCode;
	private String libelle;
	private String taskCode;
	private double defaultValue;
	
	
	public MissionTypeTaskLink() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MissionTypeTaskLink(String missionTypeCode, String taskCode,String libelle,double defaultValue) {
		super();
		this.missionTypeCode = missionTypeCode;
		this.taskCode = taskCode;
		this.libelle = libelle;
		this.defaultValue = defaultValue;
	}
	
	public boolean equals(Object obj){		
		if( (this.getId()== null ) || (null == obj) || ( !(obj instanceof MissionTypeTaskLink)  )){
			return false;
		}
		else{
			return this.getId().equals(((MissionTypeTaskLink)obj).getId());
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
	
	public String getTaskCode() {
		return taskCode;
	}
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
	public String getMissionTypeCode() {
		return missionTypeCode;
	}
	public void setMissionTypeCode(String missionTypeCode) {
		this.missionTypeCode = missionTypeCode;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public double getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(double defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	

}
