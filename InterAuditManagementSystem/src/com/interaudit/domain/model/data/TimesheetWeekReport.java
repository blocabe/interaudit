package com.interaudit.domain.model.data;

import java.io.Serializable;

public class TimesheetWeekReport implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int weekNumber;
	private int nbValidated = 0;
	private int nbSubmitted = 0;
	private int nbRejected = 0;
	private int nbDraft = 0;
	private int nbTotal = 0;
	
	
	
	public TimesheetWeekReport() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TimesheetWeekReport(int weekNumber, int nbValidated,
			int nbSubmitted, int nbRejected, int nbDraft, int nbTotal) {
		super();
		this.weekNumber = weekNumber;
		this.nbValidated = nbValidated;
		this.nbSubmitted = nbSubmitted;
		this.nbRejected = nbRejected;
		this.nbDraft = nbDraft;
		this.nbTotal = nbTotal;
	}
	
	public int getWeekNumber() {
		return weekNumber;
	}
	public void setWeekNumber(int weekNumber) {
		this.weekNumber = weekNumber;
	}
	public int getNbValidated() {
		return nbValidated;
	}
	public void setNbValidated(int nbValidated) {
		this.nbValidated = nbValidated;
	}
	public int getNbSubmitted() {
		return nbSubmitted;
	}
	public void setNbSubmitted(int nbSubmitted) {
		this.nbSubmitted = nbSubmitted;
	}
	public int getNbRejected() {
		return nbRejected;
	}
	public void setNbRejected(int nbRejected) {
		this.nbRejected = nbRejected;
	}
	public int getNbDraft() {

		return ( nbTotal - nbValidated - nbSubmitted -nbRejected);
		
		//return nbDraft;
	}
	public void setNbDraft(int nbDraft) {
		this.nbDraft = nbDraft;
	}
	public int getNbTotal() {
		return nbTotal;
	}
	public void setNbTotal(int nbTotal) {
		this.nbTotal = nbTotal;
	}
	
	

}
