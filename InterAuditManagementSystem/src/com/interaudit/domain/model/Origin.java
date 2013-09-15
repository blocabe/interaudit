package com.interaudit.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ORIGINS",schema="interaudit")
public class Origin implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue
	/*
	@GeneratedValue(generator="OriginSeq")
	@SequenceGenerator(name="OriginSeq",sequenceName="ORIGIN_SEQ", allocationSize=1)
	*/
	private Long id;
	
	
	@Column(name = "NAME", nullable = false)
	private String name;
	
	
	@Column(name = "CODE", unique= true, nullable = false)
	private String code;
	
	/**
	 * 
	 */
	public Origin(){}
	
	/**
	 * @param code
	 * @param name
	 */
	public Origin(String code,String name){
		this.code = code;
		this.name = name;
		
	}
	
	public boolean equals(Object obj){		
		if( (this.getId()== null ) || (null == obj) || ( !(obj instanceof Origin)  )){
			return false;
		}
		else{
			return this.getId().equals(((Origin)obj).getId());
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
	
	
	
	
	

}
