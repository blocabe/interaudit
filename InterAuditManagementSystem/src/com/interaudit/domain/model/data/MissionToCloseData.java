package com.interaudit.domain.model.data;

import java.io.Serializable;



public class MissionToCloseData implements Serializable{
	
	private static final long serialVersionUID = 1L;		
	private String customerName;
	private String mandat;	
	private String associate;
	private String emailAssociate;
	private String manager;
	private String emailManager;
	private String budgetStatus;
	private String exercice;
	
	
	
	public MissionToCloseData() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public MissionToCloseData(String customerName,
			String mandat,  String associate,String emailAssociate,
			String manager,String emailManager, String budgetStatus, String exercice) {
		super();
		this.customerName = customerName;
		this.mandat = mandat;		
		this.associate = associate;
		this.emailAssociate = emailAssociate;
		this.manager = manager;
		this.emailManager = emailManager;
		this.budgetStatus = budgetStatus;
		this.exercice = exercice;
	}
	
	
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getMandat() {
		return mandat;
	}
	public void setMandat(String mandat) {
		this.mandat = mandat;
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
	public String getBudgetStatus() {
		return budgetStatus;
	}
	public void setBudgetStatus(String budgetStatus) {
		this.budgetStatus = budgetStatus;
	}
	public String getExercice() {
		return exercice;
	}
	public void setExercice(String exercice) {
		this.exercice = exercice;
	}

	public String getEmailAssociate() {
		return emailAssociate;
	}

	public void setEmailAssociate(String emailAssociate) {
		this.emailAssociate = emailAssociate;
	}

	public String getEmailManager() {
		return emailManager;
	}

	public void setEmailManager(String emailManager) {
		this.emailManager = emailManager;
	}
	
	
	
	
	
	

}
