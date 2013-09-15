package com.interaudit.domain.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "USER_ACTIONS",schema="interaudit")
public class UserAction {
	
	 
		private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue		
		private Long id;
		

	    @ManyToOne(fetch = FetchType.EAGER)
		@JoinColumn(name="fk_employee" , nullable = false)
		private Employee employee;
	    
	    public UserAction() {
			super();
			// TODO Auto-generated constructor stub
		}

		public UserAction(Employee employee, String action, String entityClassName,
				Long entityId, Date time) {
			super();
			this.employee = employee;
			this.action = action;
			this.entityClassName = entityClassName;
			this.entityId = entityId;
			this.time = time;
		}
		
		public boolean equals(Object obj){		
			if( (this.getId()== null ) || (null == obj) || ( !(obj instanceof UserAction)  )){
				return false;
			}
			else{
				return this.getId().equals(((UserAction)obj).getId());
			}
		}
		
		public int hashCode(){
			final int prime = 31;
	        int result = 1;
	        result = prime * result + ((id == null) ? 0 : id.hashCode());
	        return result;
		}

		private String action;
	    
	    private String entityClassName;
	    
	    private Long entityId;
	    
	    private java.util.Date time;

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

		public String getAction() {
			return action;
		}

		public void setAction(String action) {
			this.action = action;
		}

		public java.util.Date getTime() {
			return time;
		}

		public void setTime(java.util.Date time) {
			this.time = time;
		}

		public String getEntityClassName() {
			return entityClassName;
		}

		public void setEntityClassName(String entityClassName) {
			this.entityClassName = entityClassName;
		}

		public Long getEntityId() {
			return entityId;
		}

		public void setEntityId(Long entityId) {
			this.entityId = entityId;
		}

}
