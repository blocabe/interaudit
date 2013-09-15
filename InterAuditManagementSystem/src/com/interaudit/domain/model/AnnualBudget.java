package com.interaudit.domain.model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.interaudit.domain.model.data.Option;

@Entity
@Table(name = "BUDGETS",schema="interaudit")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE )
public class AnnualBudget implements Serializable {
	
	public static final String STATUS_PENDING ="PENDING";
	public static final String STATUS_ONGOING ="ONGOING";
	public static final String STATUS_CLOSED ="CLOSED";
	public static final String STATUS_CANCELLED ="CANCELLED";
	public static final String STATUS_IDLE ="IDLE";
	public static final String STATUS_TRANSFERED = "TRANSFERED";
	
	public  static List<Option> getAllStatusesOptions(){
		   List<Option> options= new ArrayList<Option>();
		   options.add(new Option(AnnualBudget.STATUS_PENDING,AnnualBudget.STATUS_PENDING));
		   options.add(new Option(AnnualBudget.STATUS_ONGOING,AnnualBudget.STATUS_ONGOING));
		   options.add(new Option(AnnualBudget.STATUS_TRANSFERED,AnnualBudget.STATUS_TRANSFERED));
		   options.add(new Option(AnnualBudget.STATUS_CLOSED,AnnualBudget.STATUS_CLOSED));
		   options.add(new Option(AnnualBudget.STATUS_CANCELLED,AnnualBudget.STATUS_CANCELLED));
		   //options.add(new Option(AnnualBudget.STATUS_IDLE,AnnualBudget.STATUS_IDLE));
		   return options;
	   }
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "VERSION", nullable = false)
	@Version
	private int version;

	@Id
	@GeneratedValue
	/*
	@GeneratedValue(generator="BudgetSeq")
	@SequenceGenerator(name="BudgetSeq",sequenceName="BUDGET_SEQ", allocationSize=1)
	*/
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_exercise" , nullable = false)
	private Exercise exercise;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_contract" , nullable = false)
	private Contract contract;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="fk_parent" , nullable = true)
    private AnnualBudget parent;
	
	@Column(name = "EXP_AMOUNT" , nullable = false)
	private double expectedAmount;
	
	@Column(name = "EXP_AMOUNT_REF" , nullable = false)
	private double expectedAmountRef;
	
	@Column(name = "REAL_AMOUNT")
	private double effectiveAmount;
	
	@Column(name = "REP_AMOUNT" , nullable = true)
	private double reportedAmount;
	
	@Column(name = "REP_AMOUNT_REF" , nullable = true)
	private double reportedAmountRef;
	
	private String comments;
	
	private String status;
	
	private boolean fiable = true;
	
	private boolean finalBill = false;
	
	@Column(name = "interim")
	private boolean interim = false;
	
	@OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="fk_associe" , nullable = false)
    private Employee associeSignataire;
	
	
	@OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="fk_manager" , nullable = false)
    private Employee budgetManager;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_mission" , nullable = true)
	private Mission mission;
	
	
	/**
	 * Default constructor
	 */
	public AnnualBudget(){}
	
	public boolean equals(Object obj){		
		if( (this.getId()== null ) || (null == obj) || ( !(obj instanceof AnnualBudget)  )){
			return false;
		}
		else{
			return this.getId().equals(((AnnualBudget)obj).getId());
		}
	}
	
	public int hashCode(){
		final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
	}
	
	/**
	 * @param exercise
	 * @param contract
	 * @param associe
	 
	public AnnualBudget(Exercise exercise,Contract contract){
		this.exercise = exercise;
		this.contract = contract;
		this.expectedAmount = contract.getAmount();
		this.status = AnnualBudget.STATUS_PENDING;
		
	}
	*/
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Exercise getExercise() {
		return exercise;
	}
	public void setExercise(Exercise exercise) {
		this.exercise = exercise;
	}
	
	
	
	public Mission getMission() {
		return mission;
	}
	public void setMission(Mission mission) {
		this.mission = mission;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Contract getContract() {
		return contract;
	}
	public void setContract(Contract contract) {
		this.contract = contract;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}

	public Employee getBudgetManager() {
		return budgetManager;
	}

	public void setBudgetManager(Employee budgetManager) {
		this.budgetManager = budgetManager;
	}

	public double getReportedAmount() {
		return reportedAmount;
	}

	public void setReportedAmount(double reportedAmount) {
		this.reportedAmount = reportedAmount;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Employee getAssocieSignataire() {
		return associeSignataire;
	}

	public void setAssocieSignataire(Employee associeSignataire) {
		this.associeSignataire = associeSignataire;
	}

	public boolean isFiable() {
		return fiable;
	}

	public void setFiable(boolean fiable) {
		this.fiable = fiable;
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

	public AnnualBudget getParent() {
		return parent;
	}

	public void setParent(AnnualBudget parent) {
		this.parent = parent;
	}

	public boolean isFinalBill() {
		return finalBill;
	}

	public void setFinalBill(boolean finalBill) {
		this.finalBill = finalBill;
	}

	public boolean isInterim() {
		return interim;
	}

	public void setInterim(boolean interim) {
		this.interim = interim;
	}
	
	

}
