package com.interaudit.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;


@Entity
@Table(name = "TASKS",schema="interaudit")
public class Task implements java.io.Serializable{
	
	public Task(){}
	
	public Task(String name, String description, String codePrestation,
			boolean chargeable,String code,boolean optional) {
		super();
		this.name = name;
		this.description = description;
		this.codePrestation = codePrestation;
		this.chargeable = chargeable;
		this.optional = optional;
		this.code = code;
		
	}
	
	public boolean equals(Object obj){		
		if( (this.getId()== null ) || (null == obj) || ( !(obj instanceof Task)  )){
			return false;
		}
		else{
			return this.getId().equals(((Task)obj).getId());
		}
	}
	
	public int hashCode(){
		final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	/*
	@GeneratedValue(generator="TaskSeq")
	@SequenceGenerator(name="TaskSeq",sequenceName="TASK_SEQ", allocationSize=1)
	*/
	private Long id;	
	private String name;
	private String description;
	private String codePrestation;
	private boolean chargeable;
	private String code;
	private boolean optional;
	
	
	
	
	@Column(name = "VERSION", nullable = false)
	@Version
	private int version;
	
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
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getCodePrestation() {
		return codePrestation;
	}
	
	public void setCodePrestation(String codePrestation) {
		this.codePrestation = codePrestation;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public boolean isChargeable() {
		return chargeable;
	}
	public void setChargeable(boolean chargeable) {
		this.chargeable = chargeable;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean isOptional() {
		return optional;
	}

	public void setOptional(boolean optional) {
		this.optional = optional;
	}

	
	

}
