package com.interaudit.domain.model.data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.interaudit.domain.model.Employee;


public class AnnualPlanning {

	private static final long serialVersionUID = 1L;
	
	
	
	private Employee responsable;
	private Date createdate;
	private String exercise; 
	
	private Set<WeekPlanning> rows;
	
	
	public AnnualPlanning() {
		this.rows = new HashSet<WeekPlanning>();
		this.createdate = new Date();
	}
	
	public AnnualPlanning(String exercise,Employee responsable) {
		this();
		this.responsable = responsable;
		this.exercise = exercise;
	}
	
	
	

	public Employee getUser() {
		return responsable;
	}

	public void setUser(Employee user) {
		this.responsable = user;
	}


	public Set<WeekPlanning> getRows() {
		return rows;
	}

	public void setRows(Set<WeekPlanning> rows) {
		this.rows = rows;
	}

	public void addRow(WeekPlanning line) {
		this.rows.add(line);
	}

	public void removeRow(WeekPlanning line) {
		this.rows.add(line);
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public String getExercise() {
		return exercise;
	}

	public void setExercise(String exercise) {
		this.exercise = exercise;
	}

	public Employee getResponsable() {
		return responsable;
	}

	public void setResponsable(Employee responsable) {
		this.responsable = responsable;
	}

	

	
	
	

}
