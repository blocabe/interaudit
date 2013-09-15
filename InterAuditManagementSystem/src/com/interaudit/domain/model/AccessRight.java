package com.interaudit.domain.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


@Entity
@Table(name = "ACCESS_RIGHTS",schema="interaudit")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE )
public class AccessRight implements java.io.Serializable{
	
	public AccessRight(){}
	
	public AccessRight(Employee employee, Right right, boolean active) {
		super();
		this.employee = employee;
		this.right = right;
		this.active = active;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	/*
	@GeneratedValue(generator="AccessRightSeq")
	@SequenceGenerator(name="AccessRightSeq",sequenceName="ACCESS_RIGHT_SEQ", allocationSize=1)
	*/
	private Long id;
	

    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="fk_employee" , nullable = false)
	private Employee employee;
	
	
    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="fk_right" , nullable = false)
	private Right right;
	
	private boolean active;
	

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public Right getRight() {
		return right;
	}
	public void setRight(Right right) {
		this.right = right;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public boolean equals(Object obj){		
		if( (this.getId()== null ) || (null == obj) || ( !(obj instanceof AccessRight)  )){
			return false;
		}
		else{
			return this.getId().equals(((AccessRight)obj).getId());
		}
	}
	
	public int hashCode(){
		final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
	}

}
