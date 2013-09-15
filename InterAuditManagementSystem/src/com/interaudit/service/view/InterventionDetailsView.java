package com.interaudit.service.view;

import java.io.Serializable;
import java.util.List;

import com.interaudit.domain.model.Activity;
import com.interaudit.domain.model.Document;
import com.interaudit.domain.model.Employee;
import com.interaudit.domain.model.Message;
import com.interaudit.domain.model.data.ActivityData;

public class InterventionDetailsView implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long interventionId;
	private ActivityData interventionData;
	private Activity intervention;
	private List<Message> messages;
	private List<Document> documents;
	private List<Employee> teamMembersOptions;
	
	
	
	
	public InterventionDetailsView() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public InterventionDetailsView(Long interventionId,
			ActivityData interventionData, Activity intervention,
			List<Message> messages, List<Document> documents,
			List<Employee> teamMembersOptions) {
		super();
		this.interventionId = interventionId;
		this.interventionData = interventionData;
		this.intervention = intervention;
		this.messages = messages;
		this.documents = documents;
		this.teamMembersOptions = teamMembersOptions;
	}


	public Long getInterventionId() {
		return interventionId;
	}




	public void setInterventionId(Long interventionId) {
		this.interventionId = interventionId;
	}




	public ActivityData getInterventionData() {
		return interventionData;
	}




	public void setInterventionData(ActivityData interventionData) {
		this.interventionData = interventionData;
	}




	public Activity getIntervention() {
		return intervention;
	}




	public void setIntervention(Activity intervention) {
		this.intervention = intervention;
	}




	public List<Message> getMessages() {
		return messages;
	}




	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}




	public List<Document> getDocuments() {
		return documents;
	}




	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}




	public List<Employee> getTeamMembersOptions() {
		return teamMembersOptions;
	}




	public void setTeamMembersOptions(List<Employee> teamMembersOptions) {
		this.teamMembersOptions = teamMembersOptions;
	}
	
	
	

	
	

	
	
	

}
