package com.interaudit.domain.model;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;


public class ObjectifPerExercise implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "TAUX")
	private float percentage;
	
	@Column(name = "ANNUAL")
	private double annualAmount;
	
	@Column(name = "ANNUAL_CO")
	private double correctedAnnualAmount;
	private String role;
	
	@Id
	@GeneratedValue
	/*
	@GeneratedValue(generator="ObjectifPerExerciseSeq")
	@SequenceGenerator(name="ObjectifPerExerciseSeq",sequenceName="OBJECTIVES_SEQ", allocationSize=1)
	*/
	private Long id;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_exercise" , nullable = false)
	private Exercise exercise;
	
	@OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="fk_responsable" , nullable = false)
    private Employee responsable;
	
	public ObjectifPerExercise() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ObjectifPerExercise(Employee responsable, double annulAmount,
			double correctedAnnualAmount, float percentage,String role) {
		this();
		this.responsable = responsable;
		this.annualAmount = annulAmount;
		this.correctedAnnualAmount = correctedAnnualAmount;
		this.percentage = percentage;
		this.role = role;
	}
	
	public boolean equals(Object obj){		
		if( (this.getId()== null ) || (null == obj) || ( !(obj instanceof ObjectifPerExercise)  )){
			return false;
		}
		else{
			return this.getId().equals(((ObjectifPerExercise)obj).getId());
		}
	}
	
	public int hashCode(){
		final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
	}
	
	public float getPercentage() {
		return percentage;
	}
	public void setPercentage(float percentage) {
		this.percentage = percentage;
	}
	
	public double getGrossMonthlyAmount() {
		return correctedAnnualAmount / 12;
	}
	

	public double getAnnualAmount() {
		return annualAmount;
	}

	public void setAnnualAmount(double annualAmount) {
		this.annualAmount = annualAmount;
	}

	public double getCorrectedAnnualAmount() {
		return correctedAnnualAmount;
	}

	public void setCorrectedAnnualAmount(double correctedAnnualAmount) {
		this.correctedAnnualAmount = correctedAnnualAmount;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	

}
