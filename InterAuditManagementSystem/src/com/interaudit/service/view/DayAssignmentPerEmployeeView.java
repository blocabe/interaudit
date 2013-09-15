package com.interaudit.service.view;

import java.util.Date;

public class DayAssignmentPerEmployeeView {
	
	private Date date;
	private int weekNumber;
	private String exercise;
	private long missionIdentifier;
	private long teamMemberIdentifier;
	private String description;
	private String activityIdentifier;
	
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getExercise() {
		return exercise;
	}
	public void setExercise(String exercise) {
		this.exercise = exercise;
	}
	public long getMissionIdentifier() {
		return missionIdentifier;
	}
	public void setMissionIdentifier(long missionIdentifier) {
		this.missionIdentifier = missionIdentifier;
	}
	
	public long getTeamMemberIdentifier() {
		return teamMemberIdentifier;
	}
	public void setTeamMemberIdentifier(long teamMemberIdentifier) {
		this.teamMemberIdentifier = teamMemberIdentifier;
	}
	public int getWeekNumber() {
		return weekNumber;
	}
	public void setWeekNumber(int weekNumber) {
		this.weekNumber = weekNumber;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getActivityIdentifier() {
		return activityIdentifier;
	}
	public void setActivityIdentifier(String activityIdentifier) {
		this.activityIdentifier = activityIdentifier;
	}

}
