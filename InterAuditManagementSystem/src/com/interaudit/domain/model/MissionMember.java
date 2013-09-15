package com.interaudit.domain.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="PROJ_EMP")
@IdClass(MissionMemberId.class)

public class MissionMember {
		
	public MissionMember(Long memberId, Long missionId) {
		super();
		this.memberId = memberId;
		this.missionId = missionId;
		this.starDate = new Date();
	}
	public MissionMember() {
		super();
		// TODO Auto-generated constructor stub
	}
	 @Id
	private Long memberId;
	 
	 @Id
	private Long missionId;
	 
	 @ManyToOne
	 @PrimaryKeyJoinColumn(name="EMPLOYEEID", referencedColumnName="ID")
	 private Employee employee;
	 
	 @ManyToOne
	 @PrimaryKeyJoinColumn(name="PROJECTID", referencedColumnName="ID")
	 private Mission project;

	private Date starDate;
	private Date endDate;
	
	
	public Date getStarDate() {
		return starDate;
	}
	public void setStarDate(Date starDate) {
		this.starDate = starDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public Long getMissionId() {
		return missionId;
	}
	public void setMissionId(Long missionId) {
		this.missionId = missionId;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public Mission getProject() {
		return project;
	}
	public void setProject(Mission project) {
		this.project = project;
	}

}
