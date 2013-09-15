package com.interaudit.domain.model.data;

import java.io.Serializable;

public class AlerteData implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Integer exerciseNumber;
	private String titleMission;
	private String mandat;
	private String typeMission;	
	private Double amountAlerte;
	private Double prixRevient;
	private Integer numero;
	
	
	public AlerteData() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AlerteData(Integer exerciseNumber, String titleMission,
			String mandat, String typeMission, Double amountAlerte,
			Double prixRevient,Integer numero) {
		super();
		this.exerciseNumber = exerciseNumber;
		this.titleMission = titleMission;
		this.mandat = mandat;
		this.typeMission = typeMission;
		this.amountAlerte = amountAlerte;
		this.prixRevient = prixRevient;
		this.numero = numero;
	}
	public Integer getExerciseNumber() {
		return exerciseNumber;
	}
	public void setExerciseNumber(Integer exerciseNumber) {
		this.exerciseNumber = exerciseNumber;
	}
	public String getTitleMission() {
		return titleMission;
	}
	public void setTitleMission(String titleMission) {
		this.titleMission = titleMission;
	}
	public String getMandat() {
		return mandat;
	}
	public void setMandat(String mandat) {
		this.mandat = mandat;
	}
	public String getTypeMission() {
		return typeMission;
	}
	public void setTypeMission(String typeMission) {
		this.typeMission = typeMission;
	}
	public Double getAmountAlerte() {
		return amountAlerte;
	}
	public void setAmountAlerte(Double amountAlerte) {
		this.amountAlerte = amountAlerte;
	}
	public Double getPrixRevient() {
		return prixRevient;
	}
	public void setPrixRevient(Double prixRevient) {
		this.prixRevient = prixRevient;
	}
	public Integer getNumero() {
		return numero;
	}
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	
	

}
