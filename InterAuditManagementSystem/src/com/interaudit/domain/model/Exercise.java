package com.interaudit.domain.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import com.interaudit.util.DateUtils;

@Entity
@Table(name = "EXERCISES",schema="interaudit")
public class Exercise implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static final String STATUS_PENDING ="PENDING";
	public static final String STATUS_ONGOING ="ONGOING";
	public static final String STATUS_STOPPED ="STOPPED";
	public static final String STATUS_CLOSED ="CLOSED";

	
	@Id
	@GeneratedValue
	/*
	@GeneratedValue(generator="ExerciseSeq")
	@SequenceGenerator(name="ExerciseSeq",sequenceName="EXERCISE_SEQ", allocationSize=1)
	*/
	private Long id;
	
	@Column(name = "VERSION", nullable = false)
	@Version
	private int version;
	
	@Column(unique=true , nullable = false)
	private int year;
	
	private Date taggedDate;
	
	private String status;
	private Date startDate;
	private Date endDate;
	private boolean isAppproved;
	@Column(name = "TOT_EXP_AMOUNT" , nullable = false)
	private double totalExpectedAmount = 0;
	
	@Column(name = "TOT_EXP_AMOUNT_REF" , nullable = false)
	private double totalExpectedAmountRef = 0;
	
	private float inflationPercentage = 1;
	
	@Column(name = "TOT_REP_AMOUNT" , nullable = false)
	private double totalReportedAmount = 0;
	
	@Column(name = "TOT_REP_AMOUNT_REF" , nullable = false)
	private double totalReportedAmountRef = 0;
	
	@Column(name = "TOT_REAL_AMOUNT")
	private double totalEffectiveAmount = 0;
	
	@Column(name = "TOT_INACTIF_AMOUNT")
	private double totalInactifAmount = 0;
	
	@Column(name = "TOT_INACTIF_AMOUNT_REF")
	private double totalInactifAmountRef = 0;
	
	private boolean needUpdate = false;
	
	@OneToMany(mappedBy = "exercise", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<AnnualBudget> budgets = new HashSet<AnnualBudget>();
	
	
	public Exercise(){this.status=Exercise.STATUS_PENDING;}
	
	public Exercise(int year){
		this();
		//First January of the year
		Date startDate = DateUtils.getDate(year, 1, 1);
		this.startDate = startDate;
		//this.setStartDate(startDate);
		//31 December of the year
		Date endDate = DateUtils.getDate(year, 12, 31);
		
		this.endDate =endDate;
		this.year=year;
		this.isAppproved = false;
		/*
		this.setEndDate(endDate);
		this.setYear(year);
		this.setAppproved(false);
		*/
		
	}
	
	public boolean equals(Object obj){		
		if( (this.getId()== null ) || (null == obj) || ( !(obj instanceof Exercise)  )){
			return false;
		}
		else{
			return this.getId().equals(((Exercise)obj).getId());
		}
	}
	
	public int hashCode(){
		final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public Set<AnnualBudget> getBudgets() {
		return budgets;
	}
	public void setBudgets(Set<AnnualBudget> budgets) {
		this.budgets = budgets;
	}
	
	
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public boolean isAppproved() {
		return isAppproved;
	}

	public void setAppproved(boolean isAppproved) {
		this.isAppproved = isAppproved;
	}

	public double getTotalExpectedAmount() {
		return totalExpectedAmount;
	}

	public void setTotalExpectedAmount(double totalExpectedAmount) {
		this.totalExpectedAmount = totalExpectedAmount;
	}

	public double getTotalEffectiveAmount() {
		return totalEffectiveAmount;
	}

	public void setTotalEffectiveAmount(double totalEffectiveAmount) {
		this.totalEffectiveAmount = totalEffectiveAmount;
	}

	public double getTotalReportedAmount() {
		return totalReportedAmount;
	}

	public void setTotalReportedAmount(double totalReportedAmount) {
		this.totalReportedAmount = totalReportedAmount;
	}

	public float getInflationPercentage() {
		return inflationPercentage;
	}

	public void setInflationPercentage(float inflationPercentage) {
		this.inflationPercentage = inflationPercentage;
	}

	public double getTotalInactifAmount() {
		return totalInactifAmount;
	}

	public void setTotalInactifAmount(double totalInactifAmount) {
		this.totalInactifAmount = totalInactifAmount;
	}

	public double getTotalReportedAmountRef() {
		return totalReportedAmountRef;
	}

	public void setTotalReportedAmountRef(double totalReportedAmountRef) {
		this.totalReportedAmountRef = totalReportedAmountRef;
	}

	public double getTotalExpectedAmountRef() {
		return totalExpectedAmountRef;
	}

	public void setTotalExpectedAmountRef(double totalExpectedAmountRef) {
		this.totalExpectedAmountRef = totalExpectedAmountRef;
	}

	public Date getTaggedDate() {
		return taggedDate;
	}

	public void setTaggedDate(Date taggedDate) {
		this.taggedDate = taggedDate;
	}

	public double getTotalInactifAmountRef() {
		return totalInactifAmountRef;
	}

	public void setTotalInactifAmountRef(double totalInactifAmountRef) {
		this.totalInactifAmountRef = totalInactifAmountRef;
	}

	public boolean isNeedUpdate() {
		return needUpdate;
	}

	public void setNeedUpdate(boolean needUpdate) {
		this.needUpdate = needUpdate;
	}

	
}
