package com.interaudit.domain.model.data;

import java.io.Serializable;



public class AnnualBudgetSumData implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private double expectedAmount;
	private double effectiveAmount;
	private double reportedAmount;
	private double expectedAmountRef;
	private double reportedAmountRef;
	
	
	public AnnualBudgetSumData(double expectedAmount, double effectiveAmount,
			double reportedAmount, double expectedAmountRef,
			double reportedAmountRef) {
		super();
		this.expectedAmount = expectedAmount;
		this.effectiveAmount = effectiveAmount;
		this.reportedAmount = reportedAmount;
		this.expectedAmountRef = expectedAmountRef;
		this.reportedAmountRef = reportedAmountRef;
	}
	public double getExpectedAmount() {
		return expectedAmount;
	}
	public void setExpectedAmount(double expectedAmount) {
		this.expectedAmount = expectedAmount;
	}
	public double getEffectiveAmount() {
		return effectiveAmount;
	}
	public void setEffectiveAmount(double effectiveAmount) {
		this.effectiveAmount = effectiveAmount;
	}
	public double getReportedAmount() {
		return reportedAmount;
	}
	public void setReportedAmount(double reportedAmount) {
		this.reportedAmount = reportedAmount;
	}
	public double getExpectedAmountRef() {
		return expectedAmountRef;
	}
	public void setExpectedAmountRef(double expectedAmountRef) {
		this.expectedAmountRef = expectedAmountRef;
	}
	public double getReportedAmountRef() {
		return reportedAmountRef;
	}
	public void setReportedAmountRef(double reportedAmountRef) {
		this.reportedAmountRef = reportedAmountRef;
	}
	
	
	
	
}
