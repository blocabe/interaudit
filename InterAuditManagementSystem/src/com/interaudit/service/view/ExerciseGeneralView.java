package com.interaudit.service.view;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.interaudit.domain.model.ObjectifPerExercise;
import com.interaudit.domain.model.data.Option;

public class ExerciseGeneralView implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Integer exercise;
	private double expectedBudget;
	private double reportedBudget;
	private double totalBudget;
	private double totalInactifsBudget;
	private Map<String,ObjectifPerExercise> objectifsPerAssocie;
	private Map<String,ObjectifPerExercise> objectifsPerManager;
	private Map<String,Double[]> resultsPerManager;
	private Map<String,Double[]> resultsPerAssociate;
	private List<Integer> years;
	private List<Option> managerOptions;
	private List<Option> associeOptions;
	private boolean deletable;
	private boolean needUpdate;
	
	
	public ExerciseGeneralView() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	


	public ExerciseGeneralView(Long id,Integer exercise, double expectedBudget,
			double reportedBudget, double totalBudget,double totalInactifsBudget,
			Map<String, ObjectifPerExercise> objectifsPerAssocie,
			Map<String, ObjectifPerExercise> objectifsPerManager,
			Map<String, Double[]> resultsPerManager,
			Map<String,Double[]> resultsPerAssociate,
			List<Option> managerOptions,
			List<Integer> years,
			List<Option> associeOptions,boolean deletable,boolean needUpdate) {
		super();
		this.id = id;
		this.exercise = exercise;
		this.expectedBudget = expectedBudget;
		this.reportedBudget = reportedBudget;
		this.totalInactifsBudget = totalInactifsBudget;
		this.totalBudget = totalBudget;
		this.objectifsPerAssocie = objectifsPerAssocie;
		this.objectifsPerManager = objectifsPerManager;
		this.resultsPerManager = resultsPerManager;
		this.resultsPerAssociate = resultsPerAssociate;
		this.managerOptions = managerOptions;
		this.associeOptions= associeOptions;
		this.years = years;
		this.deletable = deletable;
		this.needUpdate = needUpdate;
	}
	
	public ExerciseGeneralView(Long id,Integer exercise, double expectedBudget,
			double reportedBudget, double totalBudget,double totalInactifsBudget
			) {
		super();
		this.id = id;
		this.exercise = exercise;
		this.expectedBudget = expectedBudget;
		this.reportedBudget = reportedBudget;
		this.totalInactifsBudget = totalInactifsBudget;
		this.totalBudget = totalBudget;
		
	}
	
	
	public Double getTotalForManagersForMonth(int indiceMonth){
		
		Double result = 0.0;
		
		Collection<Double[]> values = resultsPerManager.values();
		for(Double[] entry : values){
			result += entry[indiceMonth];
		}
		 
		return result;
		
	}




	public Integer getExercise() {
		return exercise;
	}


	public void setExercise(Integer exercise) {
		this.exercise = exercise;
	}


	public double getExpectedBudget() {
		return expectedBudget;
	}


	public void setExpectedBudget(double expectedBudget) {
		this.expectedBudget = expectedBudget;
	}


	public double getReportedBudget() {
		return reportedBudget;
	}


	public void setReportedBudget(double reportedBudget) {
		this.reportedBudget = reportedBudget;
	}


	


	public Map<String, ObjectifPerExercise> getObjectifsPerAssocie() {
		return objectifsPerAssocie;
	}


	public void setObjectifsPerAssocie(
			Map<String, ObjectifPerExercise> objectifsPerAssocie) {
		this.objectifsPerAssocie = objectifsPerAssocie;
	}


	public Map<String, ObjectifPerExercise> getObjectifsPerManager() {
		return objectifsPerManager;
	}


	public void setObjectifsPerManager(
			Map<String, ObjectifPerExercise> objectifsPerManager) {
		this.objectifsPerManager = objectifsPerManager;
	}


	public Map<String, Double[]> getResultsPerManager() {
		return resultsPerManager;
	}


	public void setResultsPerManager(Map<String, Double[]> resultsPerManager) {
		this.resultsPerManager = resultsPerManager;
	}


	public List<Option> getManagerOptions() {
		return managerOptions;
	}


	public void setManagerOptions(List<Option> managerOptions) {
		this.managerOptions = managerOptions;
	}


	public static long getSerialVersionUID() {
		return serialVersionUID;
	}




	public List<Integer> getYears() {
		return years;
	}




	public void setYears(List<Integer> years) {
		this.years = years;
	}




	public double getTotalBudget() {
		return totalBudget;
	}




	public void setTotalBudget(double totalBudget) {
		this.totalBudget = totalBudget;
	}




	public double getTotalInactifsBudget() {
		return totalInactifsBudget;
	}




	public void setTotalInactifsBudget(double totalInactifsBudget) {
		this.totalInactifsBudget = totalInactifsBudget;
	}




	public List<Option> getAssocieOptions() {
		return associeOptions;
	}




	public void setAssocieOptions(List<Option> associeOptions) {
		this.associeOptions = associeOptions;
	}




	public Map<String, Double[]> getResultsPerAssociate() {
		return resultsPerAssociate;
	}




	public void setResultsPerAssociate(Map<String, Double[]> resultsPerAssociate) {
		this.resultsPerAssociate = resultsPerAssociate;
	}




	public Long getId() {
		return id;
	}




	public void setId(Long id) {
		this.id = id;
	}




	public boolean isDeletable() {
		return deletable;
	}




	public void setDeletable(boolean deletable) {
		this.deletable = deletable;
	}




	public boolean isNeedUpdate() {
		return needUpdate;
	}




	public void setNeedUpdate(boolean needUpdate) {
		this.needUpdate = needUpdate;
	}



}
