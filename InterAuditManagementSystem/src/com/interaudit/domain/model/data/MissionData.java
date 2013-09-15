package com.interaudit.domain.model.data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.interaudit.domain.model.Employee;
import com.interaudit.util.DateUtils;



public class MissionData implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private String missionReference;
	private String customerName;
	private String customerCode;
	private String year;
	private String status;
	private String origin;
	private String associate;
	private String manager;
	private String type;
	private Date startDate; 
	private Date dueDate; 
	private String jobStatus;	
	private String toDo;		
	private String jobComment;
	private Date dateCloture;
	private String toFinish;
	private int startWeekNumber;
	private Long parentid;
	private String budgetStatus;
	private Date approvalDate;
	private Date signedDate;
	private int exercice;
	private String memberAsString;
	private int startYearNumber;
	
	
	public MissionData(Long id, String customerName,
			String year,  String type, int exercice) {
		super();
		this.id = id;
		this.customerName = customerName;
		this.year = year;
		this.type = type;		
		this.exercice = exercice;	
		
		
	}
	
	
	public MissionData(Long id, String missionReference, String customerName,
			String customerCode, String year, String status, String origin,
			String associate, String manager, String type, Date startDate,
			Date dueDate,
			String jobStatus,	
			String toDo,		
			String jobComment,
			Date dateCloture,String toFinish,int startWeekNumber,Long parentid,String budgetStatus,Date approvalDate,Date signedDate,int exercice,String memberAsString,int startYearNumber) {
		super();
		this.id = id;
		this.missionReference = missionReference;
		this.customerName = customerName;
		this.customerCode = customerCode;
		this.year = year;
		this.status = status;
		this.origin = origin;
		this.associate = associate;
		this.manager = manager;
		this.type = type;		
		this.startDate = startDate; 
		this.dueDate = dueDate; 
		this.jobStatus =jobStatus;	
		this.toDo = toDo;		
		this.jobComment = jobComment;
		this.dateCloture = dateCloture;
		this.toFinish = toFinish;
		this.startWeekNumber = startWeekNumber;
		this.parentid = parentid;
		this.budgetStatus = budgetStatus;
		this.approvalDate = approvalDate;
		this.signedDate = signedDate;
		this.exercice = exercice;	
		this.memberAsString=memberAsString;
		this.startYearNumber =  startYearNumber;
		
	}
	
	public MissionData(Long id, String missionReference, String customerName,
			String customerCode, String year, String status, String origin,
			String associate, String manager, String type, Date startDate,
			Date dueDate,
			String jobStatus,	
			String toDo,		
			String jobComment,
			Date dateCloture,String toFinish,int startWeekNumber,Date approvalDate,Date signedDate,int exercice,String memberAsString,int startYearNumber) {
		super();
		this.id = id;
		this.missionReference = missionReference;
		this.customerName = customerName;
		this.customerCode = customerCode;
		this.year = year;
		this.status = status;
		this.origin = origin;
		this.associate = associate;
		this.manager = manager;
		this.type = type;		
		this.startDate = startDate; 
		this.dueDate = dueDate; 
		this.jobStatus =jobStatus;	
		this.toDo = toDo;		
		this.jobComment = jobComment;
		this.dateCloture = dateCloture;
		this.toFinish = toFinish;
		this.startWeekNumber = startWeekNumber;
		this.approvalDate = approvalDate;
		this.signedDate = signedDate;
		this.exercice = exercice;
		this.memberAsString=memberAsString;
		this.startYearNumber =  startYearNumber;
		
		
	}
	
	/**
	 * @return the number of days scheduled for the mission
	 * Weekend are not taken inot account
	 */
	public int getDays(){
		if(startDate == null || dueDate == null)return 0;
		return DateUtils.getNbDays(startDate, dueDate);
	}
	
	public int getStartMonth(){
		if(startDate == null)return -1;
		return DateUtils.getMonth(startDate);
	}
	
	public int getEndMonth(){
		if(dueDate == null)return -1;
		return DateUtils.getMonth(dueDate);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMissionReference() {
		return missionReference;
	}

	public void setMissionReference(String missionReference) {
		this.missionReference = missionReference;
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

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/*
	public Double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
	}

	public Double getTotalScheduledCost() {
		return totalScheduledCost;
	}

	public void setTotalScheduledCost(Double totalScheduledCost) {
		this.totalScheduledCost = totalScheduledCost;
	}
*/
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	public String getToDo() {
		return toDo;
	}

	public void setToDo(String toDo) {
		this.toDo = toDo;
	}

	public String getJobComment() {
		return jobComment;
	}

	public void setJobComment(String jobComment) {
		this.jobComment = jobComment;
	}

	public Date getDateCloture() {
		return dateCloture;
	}

	public void setDateCloture(Date dateCloture) {
		this.dateCloture = dateCloture;
	}

	public String getToFinish() {
		return toFinish;
	}

	public void setToFinish(String toFinish) {
		this.toFinish = toFinish;
	}

	public int getStartWeekNumber() {
		return startWeekNumber;
	}

	public void setStartWeekNumber(int startWeekNumber) {
		this.startWeekNumber = startWeekNumber;
	}

	public Long getParentid() {
		return parentid;
	}

	public void setParentid(Long parentid) {
		this.parentid = parentid;
	}

	public String getBudgetStatus() {
		return budgetStatus;
	}

	public void setBudgetStatus(String budgetStatus) {
		this.budgetStatus = budgetStatus;
	}

	public Date getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}

	public Date getSignedDate() {
		return signedDate;
	}

	public void setSignedDate(Date signedDate) {
		this.signedDate = signedDate;
	}

	public int getExercice() {
		return exercice;
	}

	public void setExercice(int exercice) {
		this.exercice = exercice;
	}

	

	

	public String getMemberAsString() {
		return memberAsString;
	}

	public void setMemberAsString(String memberAsString) {
		this.memberAsString = memberAsString;
	}

	public int getStartYearNumber() {
		return startYearNumber;
	}

	public void setStartYearNumber(int startYearNumber) {
		this.startYearNumber = startYearNumber;
	}

	
	
	
	
	
	
	

}
