package com.interaudit.service.view;

import java.io.Serializable;
import java.util.List;
import com.interaudit.domain.model.Mission;
import com.interaudit.domain.model.data.MissionBudgetData;
import com.interaudit.domain.model.data.MissionData;
import com.interaudit.domain.model.data.MissionHeurePresteeData;
import com.interaudit.domain.model.data.Option;
import com.interaudit.domain.model.data.ProfitabilityPerCustomerData;

public class MissionDetailsView implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long missionId;
	private MissionData missionData;
	private Mission mission;
	private List<MissionBudgetData> budgets =  null;
	private List<MissionBudgetData> budgetNotValidated = null;
	private List<MissionHeurePresteeData> heuresPrestees = null;
	private List<Option> taskOptions;
	private List<Option> profilOptions;
	private List<Option> employeeOptions;
	private List<Option> exercicesOptions;
	private List<Option> teamMembers;
	private List<Option>missionOptions;
	private ProfitabilityPerCustomerData statistiques;
	
	
	
	public MissionDetailsView() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	

	public MissionDetailsView(Long missionId, Mission mission,MissionData missionData,
			List<Option> taskOptions, List<Option> profilOptions,
			List<Option> employeeOptions,List<Option> exercicesOptions,List<MissionBudgetData> budgets,
			List<MissionHeurePresteeData> heuresPrestees,
			List<Option> teamMembers,List<Option>missionOptions,ProfitabilityPerCustomerData statistiques,List<MissionBudgetData> budgetNotValidated) {
		
		super();
		this.missionId = missionId;
		this.mission = mission;
		this.missionData = missionData;
		this.taskOptions = taskOptions;
		this.profilOptions = profilOptions;
		this.employeeOptions = employeeOptions;
		this.exercicesOptions = exercicesOptions;
		this.budgets = budgets;
		this.heuresPrestees = heuresPrestees;
		this.teamMembers = teamMembers;
		this.missionOptions = missionOptions;
		this.statistiques = statistiques;
		this.budgetNotValidated = budgetNotValidated;		
	}




	public Long getMissionId() {
		return missionId;
	}




	public void setMissionId(Long missionId) {
		this.missionId = missionId;
	}




	public Mission getMission() {
		return mission;
	}




	public void setMission(Mission mission) {
		this.mission = mission;
	}


	public List<Option> getTaskOptions() {
		return taskOptions;
	}


	public void setTaskOptions(List<Option> taskOptions) {
		this.taskOptions = taskOptions;
	}


	public List<Option> getProfilOptions() {
		return profilOptions;
	}


	public void setProfilOptions(List<Option> profilOptions) {
		this.profilOptions = profilOptions;
	}


	public List<Option> getEmployeeOptions() {
		return employeeOptions;
	}


	public void setEmployeeOptions(List<Option> employeeOptions) {
		this.employeeOptions = employeeOptions;
	}




	public MissionData getMissionData() {
		return missionData;
	}




	public void setMissionData(MissionData missionData) {
		this.missionData = missionData;
	}




	public List<MissionBudgetData> getBudgets() {
		return budgets;
	}




	public void setBudgets(List<MissionBudgetData> budgets) {
		this.budgets = budgets;
	}




	public List<MissionHeurePresteeData> getHeuresPrestees() {
		return heuresPrestees;
	}




	public void setHeuresPrestees(List<MissionHeurePresteeData> heuresPrestees) {
		this.heuresPrestees = heuresPrestees;
	}




	public List<Option> getExercicesOptions() {
		return exercicesOptions;
	}




	public void setExercicesOptions(List<Option> exercicesOptions) {
		this.exercicesOptions = exercicesOptions;
	}




	public List<Option> getTeamMembers() {
		return teamMembers;
	}




	public void setTeamMembers(List<Option> teamMembers) {
		this.teamMembers = teamMembers;
	}




	public List<Option> getMissionOptions() {
		return missionOptions;
	}




	public void setMissionOptions(List<Option> missionOptions) {
		this.missionOptions = missionOptions;
	}




	public ProfitabilityPerCustomerData getStatistiques() {
		return statistiques;
	}




	public void setStatistiques(ProfitabilityPerCustomerData statistiques) {
		this.statistiques = statistiques;
	}




	public List<MissionBudgetData> getBudgetNotValidated() {
		return budgetNotValidated;
	}




	public void setBudgetNotValidated(List<MissionBudgetData> budgetNotValidated) {
		this.budgetNotValidated = budgetNotValidated;
	}
	

	
	
	

}
