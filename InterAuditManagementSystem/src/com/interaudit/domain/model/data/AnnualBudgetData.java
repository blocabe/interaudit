package com.interaudit.domain.model.data;

import java.io.Serializable;
import java.util.Date;

import com.interaudit.domain.model.Exercise;



public class AnnualBudgetData implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private String customerName;
	private String customerCode;
	private String contractCode;
	private int year;
	private double expectedAmount;
	private double effectiveAmount;
	
	private String status;
	private double reportedAmount;
	private String comments;
	
	private String origin;
	private String associate;
	private String manager;
	
	private Long originId;
	private Long associateId;
	private Long managerId;
	private String missionType;
	private Boolean fiable;
	private double expectedAmountRef;
	private double reportedAmountRef;
	private String mandat;
	private Long missionId;
	private boolean finalBill;
	private Boolean interim;
	
	private Double  expectedAmountEdit;
	private Double reportedAmountEdit;
	
	private String  exerciseStatus;	
	private boolean exerciseAppproved;
	
	
	public AnnualBudgetData(Long id,int year, String customerName, String customerCode,
			double expectedAmount,double reportedAmount, double effectiveAmount,
			 String status,String origin,String associate,String manager,
			  Long originId, Long associateId, Long managerId ,String contractCode, String comments,String missionType,Boolean fiable,
			  double expectedAmountRef,double reportedAmountRef,String mandat,Long missionId,boolean finalBill,Boolean interim,String  exerciseStatus,boolean exerciseAppproved) {
		super();
		this.id = id;
		this.year = year;
		this.customerName = customerName;
		this.customerCode = customerCode;
		this.expectedAmount = expectedAmount;
		this.reportedAmount = reportedAmount;
		this.expectedAmountEdit = expectedAmount;
		this.reportedAmountEdit= reportedAmount;
		this.effectiveAmount = effectiveAmount;
		this.status = status;
		this.origin = origin;
		this.associate = associate;
		this.manager = manager;
		this.originId = originId;
		this.associateId = associateId;
		this.managerId = managerId;
		this.contractCode = contractCode;
		this.comments = comments;
		this.missionType = missionType;
		this.fiable = fiable;
		this.expectedAmountRef = expectedAmountRef;
		this.reportedAmountRef = reportedAmountRef;
		this.mandat =  mandat;
		this.missionId =  missionId;
		this.finalBill = finalBill;
		this.interim = interim;
		this.exerciseStatus = exerciseStatus;	
		this.exerciseAppproved = exerciseAppproved;
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
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
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
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
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
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public Long getOriginId() {
		return originId;
	}
	public void setOriginId(Long originId) {
		this.originId = originId;
	}
	public Long getAssociateId() {
		return associateId;
	}
	public void setAssociateId(Long associateId) {
		this.associateId = associateId;
	}
	public Long getManagerId() {
		return managerId;
	}
	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}
	public String getMissionType() {
		return missionType;
	}
	public void setMissionType(String missionType) {
		this.missionType = missionType;
	}
	public boolean isFiable() {
		return fiable;
	}
	public void setFiable(boolean fiable) {
		this.fiable = fiable;
	}
	

	public boolean isDeletable(){
		
		//item.status == "PENDING" || item.status == "TRANSFERED" ||
		if(this.status != null && this.status.equalsIgnoreCase("PENDING")){
			return true;
		}
		else if( this.exerciseStatus != null && this.exerciseStatus.equalsIgnoreCase(Exercise.STATUS_PENDING) && this.exerciseAppproved == false ){
			return (this.expectedAmount + this.reportedAmount == 0.0);
		}
		else{
			return false;
		}
		
		
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
	public String getMandat() {
		return mandat;
	}
	public void setMandat(String mandat) {
		this.mandat = mandat;
	}
	public Long getMissionId() {
		return missionId;
	}
	public void setMissionId(Long missionId) {
		this.missionId = missionId;
	}
	public boolean isFinalBill() {
		return finalBill;
	}
	public void setFinalBill(boolean finalBill) {
		this.finalBill = finalBill;
	}
	public Double getReportedAmountEdit() {
		return reportedAmountEdit;
	}
	public void setReportedAmountEdit(Double reportedAmountEdit) {
		this.reportedAmountEdit = reportedAmountEdit;
	}
	public Boolean getFiable() {
		return fiable;
	}
	public void setFiable(Boolean fiable) {
		this.fiable = fiable;
	}
	public Double getExpectedAmountEdit() {
		return expectedAmountEdit;
	}
	public void setExpectedAmountEdit(Double expectedAmountEdit) {
		this.expectedAmountEdit = expectedAmountEdit;
	}
	public Boolean getInterim() {
		return interim;
	}
	public void setInterim(Boolean interim) {
		this.interim = interim;
	}
	public String getExerciseStatus() {
		return exerciseStatus;
	}
	public void setExerciseStatus(String exerciseStatus) {
		this.exerciseStatus = exerciseStatus;
	}
	public boolean isExerciseAppproved() {
		return exerciseAppproved;
	}
	public void setExerciseAppproved(boolean exerciseAppproved) {
		this.exerciseAppproved = exerciseAppproved;
	}
}
