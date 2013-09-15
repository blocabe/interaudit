package com.interaudit.domain.model.data;

import java.io.Serializable;

public class MissionInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String societe;
	private String code;
	private String budget;
	private String hours;
	private String prixRevient;
	private String prixVente;
	private String manager;
	private String associe;
	private String depenses;
	private String factures;
	public String getSociete() {
		return societe;
	}
	public void setSociete(String societe) {
		this.societe = societe;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getBudget() {
		return budget;
	}
	public void setBudget(String budget) {
		this.budget = budget;
	}
	public String getHours() {
		return hours;
	}
	public void setHours(String hours) {
		this.hours = hours;
	}
	public String getPrixRevient() {
		return prixRevient;
	}
	public void setPrixRevient(String prixRevient) {
		this.prixRevient = prixRevient;
	}
	public String getPrixVente() {
		return prixVente;
	}
	public void setPrixVente(String prixVente) {
		this.prixVente = prixVente;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public String getAssocie() {
		return associe;
	}
	public void setAssocie(String associe) {
		this.associe = associe;
	}
	public String getDepenses() {
		return depenses;
	}
	public void setDepenses(String depenses) {
		this.depenses = depenses;
	}
	public String getFactures() {
		return factures;
	}
	public void setFactures(String factures) {
		this.factures = factures;
	}
	
	

}
