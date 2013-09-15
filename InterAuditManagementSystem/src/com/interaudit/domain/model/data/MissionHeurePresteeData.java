package com.interaudit.domain.model.data;

import java.io.Serializable;
import java.util.Date;



public class MissionHeurePresteeData implements Serializable{
	
	
	private static final long serialVersionUID = 1L;

	private String employeeName;
	private Date datePrestation;
	private String codePrestation;
	private String exercise;
	private double  hours;
	private double  prixRevient;
	private double  prixVente;
	
	
	
	public MissionHeurePresteeData() {
		super();
		// TODO Auto-generated constructor stub
	}


	public MissionHeurePresteeData(String employeeName, Date datePrestation,
			String codePrestation, String exercise, double hours,
			double prixRevient, double prixVente) {
		super();
		this.employeeName = employeeName;
		this.datePrestation = datePrestation;
		this.codePrestation = codePrestation;
		this.exercise = exercise;
		this.hours = hours;
		this.prixRevient = prixRevient;
		this.prixVente = prixVente;
	}
	
	
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public Date getDatePrestation() {
		return datePrestation;
	}
	public void setDatePrestation(Date datePrestation) {
		this.datePrestation = datePrestation;
	}
	public String getCodePrestation() {
		return codePrestation;
	}
	public void setCodePrestation(String codePrestation) {
		this.codePrestation = codePrestation;
	}
	public String getExercise() {
		return exercise;
	}
	public void setExercise(String exercise) {
		this.exercise = exercise;
	}
	public double getHours() {
		return hours;
	}
	public void setHours(double hours) {
		this.hours = hours;
	}
	public double getPrixRevient() {
		return prixRevient;
	}
	public void setPrixRevient(double prixRevient) {
		this.prixRevient = prixRevient;
	}
	public double getPrixVente() {
		return prixVente;
	}
	public void setPrixVente(double prixVente) {
		this.prixVente = prixVente;
	}
	
	
	
	

	
	
	
	
	
	

}
