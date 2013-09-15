package com.interaudit.domain.model.data;

import java.io.Serializable;




public class BalanceAgeeData implements Serializable, java.lang.Comparable {
	
	private static final long serialVersionUID = 1L;
	private String customerName;
	private String customerCode;
	private Double[] amount = new Double[7];
	private Double amountPaid;

	
	
	
	public BalanceAgeeData(String customerName, String customerCode,
			Double amountPaid) {
		super();
		this.customerName = customerName;
		this.customerCode = customerCode;
		this.amountPaid = amountPaid;
		
		for(int index =0 ; index <7 ; index++){
			this.amount[index] = 0.0d;
		}
	}

	
	public BalanceAgeeData() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Double getTotalAmount(){
		Double totalAmount = 0.0d;
		for(int index =0 ; index <7 ; index++){
			totalAmount += amount[index];
		}
		
		return totalAmount;
	}
	
	public void updateAmountArray(int nbAverageDays, Double amountToAdd){
		int index = nbAverageDays / 31;
		
		if(index < 0){
			index = 0;
		}
		
		if(index > 6){
			index = 6;
		}
		
		this.amount[index] = this.amount[index] + amountToAdd;
	}
	
	
	
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	
	public double getAmountPaid() {
		return amountPaid;
	}
	public void setAmountPaid(double amountPaid) {
		this.amountPaid = amountPaid;
	}



	public Double[] getAmount() {
		return amount;
	}



	public void setAmount(Double[] amount) {
		this.amount = amount;
	}



	public void setAmountPaid(Double amountPaid) {
		this.amountPaid = amountPaid;
	}


	
	 public int compareTo(Object other) { 
		 return ( this.getCustomerName().compareTo(((BalanceAgeeData)other).getCustomerName())) ;
	   } 

	
	
	
}
