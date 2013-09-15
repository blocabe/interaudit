package com.interaudit.domain.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "PLANNING_ANNUEL",schema="interaudit")
public class PlanningAnnuel implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Temporal(TemporalType.DATE)
    private Date lastUpdate;
	
	private int year;
	
	
	@OneToMany(mappedBy = "planning", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<EventPlanning> events = new HashSet<EventPlanning>();
	
	

	public PlanningAnnuel() {
		super();
		lastUpdate = new Date();
	}

	public PlanningAnnuel(int year) {
		super();
		this.year = year;
		lastUpdate = new Date();
	}

	
	public boolean equals(Object obj){		
		if( (this.getId()== null ) || (null == obj) || ( !(obj instanceof PlanningAnnuel)  )){
			return false;
		}
		else{
			return this.getId().equals(((PlanningAnnuel)obj).getId());
		}
	}
	
	public int hashCode(){
		final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
	}
	
	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	
	public Set<EventPlanning> getEvents() {
		return events;
	}

	public void setEvents(Set<EventPlanning> events) {
		this.events = events;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
	
	
}
