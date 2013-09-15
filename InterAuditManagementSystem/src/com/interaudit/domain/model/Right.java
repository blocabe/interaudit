package com.interaudit.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "RIGHTS",schema="interaudit")
public class Right implements java.io.Serializable{
	
	public Right(){}
	
	public Right(String name, String description, String code,String type) {
		super();
		this.name = name;
		this.description = description;
		this.code = code;
		this.type =  type;
		
	}
	
	public boolean equals(Object obj){		
		if( (this.getId()== null ) || (null == obj) || ( !(obj instanceof Right)  )){
			return false;
		}
		else{
			return this.getId().equals(((Right)obj).getId());
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
	@GeneratedValue(generator="RightSeq")
	@SequenceGenerator(name="RightSeq",sequenceName="RIGHT_SEQ", allocationSize=1)
	*/
	private Long id;
	
	private String name;
	private String description;
	private String code;
	private String type;
	

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
	
	
	
	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	

}
