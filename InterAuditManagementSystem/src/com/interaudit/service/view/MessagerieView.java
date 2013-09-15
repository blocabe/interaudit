package com.interaudit.service.view;

import java.io.Serializable;
import java.util.List;

import com.interaudit.domain.model.Employee;
import com.interaudit.domain.model.data.MessageData;
import com.interaudit.domain.model.data.Option;

public class MessagerieView implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer exercise;
	private Long customerId;
	private List<MessageData> messages;
	private List<Integer> years;
	private List<Employee> teamMembers;
	private List<Option>missionOptions;
	
	public MessagerieView() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MessagerieView(Integer exercise,Long customerId, List<MessageData> messages,
			List<Integer> years,List<Employee> teamMembers,List<Option>missionOptions) {
		super();
		this.exercise = exercise;
		this.customerId = customerId;
		this.messages = messages;
		this.years = years;
		this.teamMembers = teamMembers;
		this.missionOptions = missionOptions;
	}

	public Integer getExercise() {
		return exercise;
	}

	public void setExercise(Integer exercise) {
		this.exercise = exercise;
	}

	public List<MessageData> getMessages() {
		return messages;
	}

	public void setMessages(List<MessageData> messages) {
		this.messages = messages;
	}

	public List<Integer> getYears() {
		return years;
	}

	public void setYears(List<Integer> years) {
		this.years = years;
	}

	public List<Employee> getTeamMembers() {
		return teamMembers;
	}

	public void setTeamMembers(List<Employee> teamMembers) {
		this.teamMembers = teamMembers;
	}

	public List<Option> getMissionOptions() {
		return missionOptions;
	}

	public void setMissionOptions(List<Option> missionOptions) {
		this.missionOptions = missionOptions;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	
	
	
	

}
