package com.interaudit.domain.model;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;


public class AnnualResult implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "JAN")
	private double resultJanuary = 0.0d;
	@Column(name = "FEB")
	private double resultFebruary = 0.0d;
	@Column(name = "MAR")
	private double resultMarch = 0.0d;
	
	@Column(name = "APR")
	private double resultApril = 0.0d;
	@Column(name = "MAY")
	private double resultMay = 0.0d;
	@Column(name = "JUN")
	private double resultJune = 0.0d;
	
	@Column(name = "JUL")
	private double resultJuly = 0.0d;
	@Column(name = "AUG")
	private double resultAugust = 0.0d;
	@Column(name = "SEP")
	private double resultSeptember = 0.0d;
	
	@Column(name = "OCT")
	private double resultOctober = 0.0d;
	@Column(name = "NOV")
	private double resultNovember = 0.0d;
	@Column(name = "DEC")
	private double resultDecember = 0.0d;
	private String role;
	
	
	public void updateAmountForMonth(int month, double amount){
		
		switch(month){
			case 0:
				resultJanuary = amount;
				break;
			
			case 1:
				resultFebruary = amount;
				break;
				
			case 2:
				resultMarch = amount;
				break;
			
			case 3:
				resultApril = amount;
				break;
			
			case 4:
				resultMay  = amount;
				break;
				
			case 5:
				resultJune = amount;
				break;
			
			case 6:
				resultJuly = amount;
				break;
			
			case 7:
				resultAugust = amount;
				break;
				
			case 8:
				resultSeptember  = amount;
				break;
			
			case 9:
				resultOctober = amount;
				break;
			
			case 10:
				resultNovember = amount;
				break;
				
			case 11:
				resultDecember = amount;
				break;
							
		}
		
	}
	
	@Id
	@GeneratedValue
	/*
	@GeneratedValue(generator="AnnualResultSeq")
	@SequenceGenerator(name="AnnualResultSeq",sequenceName="RESULTATS_SEQ", allocationSize=1)
	*/
	private Long id;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_exercise" , nullable = false)
	private Exercise exercise;
	
	@OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="fk_responsable" , nullable = false)
    private Employee responsable;
	
	public AnnualResult() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public AnnualResult(Exercise exercise, Employee responsable, String role
			) {
		super();
		this.role = role;
		this.exercise = exercise;
		this.responsable = responsable;
	}
	
	
	public boolean equals(Object obj){		
		if( (this.getId()== null ) || (null == obj) || ( !(obj instanceof AnnualResult)  )){
			return false;
		}
		else{
			return this.getId().equals(((AnnualResult)obj).getId());
		}
	}
	
	public int hashCode(){
		final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
	}


	public AnnualResult(Exercise exercise, Employee responsable, String role,double resultJanuary, double resultFebruary,
			double resultMarch, double resultApril, double resultMay,
			double resultJune, double resultJuly, double resultAugust,
			double resultSeptember, double resultOctober,
			double resultNovember, double resultDecember
			) {
		super();
		this.resultJanuary = resultJanuary;
		this.resultFebruary = resultFebruary;
		this.resultMarch = resultMarch;
		this.resultApril = resultApril;
		this.resultMay = resultMay;
		this.resultJune = resultJune;
		this.resultJuly = resultJuly;
		this.resultAugust = resultAugust;
		this.resultSeptember = resultSeptember;
		this.resultOctober = resultOctober;
		this.resultNovember = resultNovember;
		this.resultDecember = resultDecember;
		this.role = role;
		this.exercise = exercise;
		this.responsable = responsable;
	}
	
	public Double[] getArrayOfValues(){
		
		Double[] arrays = new Double[12];
		arrays[0] = resultJanuary;
		arrays[1] = resultFebruary;
		arrays[2] = resultMarch;
		arrays[3] = resultApril;
		
		arrays[4] = resultMay;
		arrays[5] = resultJune;
		arrays[6] = resultJuly;
		arrays[7] = resultAugust;
		
		arrays[8] = resultSeptember;
		arrays[9] = resultOctober;
		arrays[10] = resultNovember;
		arrays[11] = resultDecember;
		
		return arrays;
	}
	



	public Exercise getExercise() {
		return exercise;
	}

	public void setExercise(Exercise exercise) {
		this.exercise = exercise;
	}

	public Employee getResponsable() {
		return responsable;
	}

	public void setResponsable(Employee responsable) {
		this.responsable = responsable;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public double getResultJanuary() {
		return resultJanuary;
	}

	public void setResultJanuary(double resultJanuary) {
		this.resultJanuary = resultJanuary;
	}

	public double getResultFebruary() {
		return resultFebruary;
	}

	public void setResultFebruary(double resultFebruary) {
		this.resultFebruary = resultFebruary;
	}

	public double getResultMarch() {
		return resultMarch;
	}

	public void setResultMarch(double resultMarch) {
		this.resultMarch = resultMarch;
	}

	public double getResultApril() {
		return resultApril;
	}

	public void setResultApril(double resultApril) {
		this.resultApril = resultApril;
	}

	public double getResultMay() {
		return resultMay;
	}

	public void setResultMay(double resultMay) {
		this.resultMay = resultMay;
	}

	public double getResultJune() {
		return resultJune;
	}

	public void setResultJune(double resultJune) {
		this.resultJune = resultJune;
	}

	public double getResultJuly() {
		return resultJuly;
	}

	public void setResultJuly(double resultJuly) {
		this.resultJuly = resultJuly;
	}

	public double getResultAugust() {
		return resultAugust;
	}

	public void setResultAugust(double resultAugust) {
		this.resultAugust = resultAugust;
	}

	public double getResultSeptember() {
		return resultSeptember;
	}

	public void setResultSeptember(double resultSeptember) {
		this.resultSeptember = resultSeptember;
	}

	public double getResultOctober() {
		return resultOctober;
	}

	public void setResultOctober(double resultOctober) {
		this.resultOctober = resultOctober;
	}

	public double getResultNovember() {
		return resultNovember;
	}

	public void setResultNovember(double resultNovember) {
		this.resultNovember = resultNovember;
	}

	public double getResultDecember() {
		return resultDecember;
	}

	public void setResultDecember(double resultDecember) {
		this.resultDecember = resultDecember;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	

}
