package com.interaudit.domain.model.data;

import java.io.Serializable;
import java.util.Date;



public class MissionBudgetData implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String name;
	private String profile;
	private float hours;
	//Prix revient
	private double rate;
	private double prixVente = 0;
	private double coutHoraire = 0;
	private boolean valid;
	private Date dateOfEvent;
	private int month;
	private int weekNumber;
	private String exercise;
	private String year;
	private String codePrestation;
	
	
	 private String typeMission;
	 private Long idMission;
	 private Long idRow;
	
	
	public MissionBudgetData() {
		super();
		// TODO Auto-generated constructor stub
	}


	public MissionBudgetData( String name,String profile, float hours, double rate,double prixVente,double coutHoraire,boolean valid,
			 Date dateOfEvent,
	 int month) {
		super();
		this.name = name;
		this.profile = profile;
		this.hours = hours;
		this.rate = rate;
		this.prixVente = prixVente;
		this.coutHoraire = coutHoraire;
		this.valid = valid;
		this.dateOfEvent = dateOfEvent;
		this.month = month;
	}
	
	public MissionBudgetData( String name,String profile, float hours, double rate,double prixVente,double coutHoraire, int weekNumber,
	 String exercise) {
		super();
		this.name = name;
		this.profile = profile;
		this.hours = hours;
		this.rate = rate;
		this.prixVente = prixVente;
		this.coutHoraire = coutHoraire;
		this.weekNumber = weekNumber;
		this.exercise = exercise;
		
	}
	
	
	public MissionBudgetData( String name,String profile, float hours, double rate,double prixVente,double coutHoraire, int weekNumber,
			 String exercise,String year, String typeMission,
			  Long idMission,
			  Long idRow) {
				super();
				this.name = name;
				this.profile = profile;
				this.hours = hours;
				this.rate = rate;
				this.prixVente = prixVente;
				this.coutHoraire = coutHoraire;
				this.weekNumber = weekNumber;
				this.exercise = exercise;
				this.year=year;	
				this.typeMission=typeMission;
				this.idMission=idMission;
				this.idRow=idRow;
			}
	
	public MissionBudgetData( String name,String profile, float hours, double rate,double prixVente,double coutHoraire, int weekNumber,
			 String exercise,String year, String typeMission,
			  Long idMission,
			  Long idRow,String codePrestation) {
				super();
				this.name = name;
				this.profile = profile;
				this.hours = hours;
				this.rate = rate;
				this.prixVente = prixVente;
				this.coutHoraire = coutHoraire;
				this.weekNumber = weekNumber;
				this.exercise = exercise;
				this.year=year;	
				this.typeMission=typeMission;
				this.idMission=idMission;
				this.idRow=idRow;
				this.codePrestation = codePrestation;
			}
	
	public MissionBudgetData( String name,String profile, float hours, double rate,double prixVente,double coutHoraire, int weekNumber,
			 String exercise,String year) {
				super();
				this.name = name;
				this.profile = profile;
				this.hours = hours;
				this.rate = rate;
				this.prixVente = prixVente;
				this.coutHoraire = coutHoraire;
				this.weekNumber = weekNumber;
				this.exercise = exercise;
				this.year=year;				
			}
	
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public float getHours() {
		return hours;
	}
	public void setHours(float hours) {
		this.hours = hours;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public double getPrixVente() {
		return prixVente;
	}


	public void setPrixVente(double prixVente) {
		this.prixVente = prixVente;
	}


	public double getCoutHoraire() {
		return coutHoraire;
	}


	public void setCoutHoraire(double coutHoraire) {
		this.coutHoraire = coutHoraire;
	}


	public boolean isValid() {
		return valid;
	}


	public void setValid(boolean valid) {
		this.valid = valid;
	}


	public Date getDateOfEvent() {
		return dateOfEvent;
	}


	public void setDateOfEvent(Date dateOfEvent) {
		this.dateOfEvent = dateOfEvent;
	}


	public int getMonth() {
		return month;
	}


	public void setMonth(int month) {
		this.month = month;
	}


	public int getWeekNumber() {
		return weekNumber;
	}


	public void setWeekNumber(int weekNumber) {
		this.weekNumber = weekNumber;
	}


	public String getExercise() {
		return exercise;
	}


	public void setExercise(String exercise) {
		this.exercise = exercise;
	}


	public String getYear() {
		return year;
	}


	public void setYear(String year) {
		this.year = year;
	}


	public String getTypeMission() {
		return typeMission;
	}


	public void setTypeMission(String typeMission) {
		this.typeMission = typeMission;
	}


	public Long getIdMission() {
		return idMission;
	}


	public void setIdMission(Long idMission) {
		this.idMission = idMission;
	}


	public Long getIdRow() {
		return idRow;
	}


	public void setIdRow(Long idRow) {
		this.idRow = idRow;
	}


	public String getCodePrestation() {
		return codePrestation;
	}


	public void setCodePrestation(String codePrestation) {
		this.codePrestation = codePrestation;
	}
	
	
	

	
	
	
	
	
	

}
