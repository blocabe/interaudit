package com.interaudit.domain.model.data;

import java.io.Serializable;

import com.interaudit.domain.model.Message;



public class ProfitabilityPerCustomerData implements Serializable, Comparable<ProfitabilityPerCustomerData>{
	
	private static final long serialVersionUID = 1L;
	private String customerName;
	private int year;
	private double prixRevient;
	private double prixVente;
	private double prixFacture;
	private String associate;
	private String manager;
	private long id;
	private String status;
	private int exercise;
	
	
	public ProfitabilityPerCustomerData(
			int exercise,
			int year, 
			String customerName, 
			double prixRevient,
			double prixVente,
			double prixFacture,
			String associate,
			String manager,
			long id,
			String status) {
		super();
		this.exercise = exercise;
		this.year = year;
		this.customerName = customerName;
		this.prixRevient = prixRevient;
		this.prixVente = prixVente;
		this.prixFacture = prixFacture;
		this.associate = associate;
		this.manager = manager;
		this.id = id;
		this.status = status;
	}
	
	public int compareTo(ProfitabilityPerCustomerData o)
    {
		ProfitabilityPerCustomerData m = (ProfitabilityPerCustomerData)o;
		return customerName.compareTo(m.customerName);
    }
	
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	
	
	public String getAssociate() {
		return associate;
	}
	public void setAssociate(String associate) {
		this.associate = associate;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
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
	public double getPrixFacture() {
		return prixFacture;
	}
	public void setPrixFacture(double prixFacture) {
		this.prixFacture = prixFacture;
	}
	public double getMargePrixRevient() {
		return getPrixFacture() - getPrixRevient();
	}
	
	public double getRealisation() {
		if(getPrixVente() != 0)
			return (getPrixFacture() / getPrixVente() );
		else 
			return 0;
	}
	
	public double getPercentageMargePrixRevient() {
		if(getPrixRevient() != 0)
			return (getMargePrixRevient() / getPrixRevient() );
		else 
			return 0;
		
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public int getExercise() {
		return exercise;
	}

	public void setExercise(int exercise) {
		this.exercise = exercise;
	}
	
	

}
