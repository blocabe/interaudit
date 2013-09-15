package com.interaudit.service.view;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.interaudit.domain.model.Exercise;
import com.interaudit.domain.model.data.AnnualBudgetData;
import com.interaudit.domain.model.data.AnnualBudgetSumData;
import com.interaudit.domain.model.data.Option;
import com.interaudit.service.param.SearchBudgetParam;

public class BudgetGeneralView implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private List<AnnualBudgetData>  budgets;
	private SearchBudgetParam param;
	private List<Option> customerOptions;
	private List<Option> typeOptions;
	private List<Option> originOptions;
	private List<Option> managerOptions;
	private List<Option> associeOptions;
	private List<Option> exercicesOptions;
	private List<String> validContractReferences;
	private List<Exercise>  exercises;
	private List<Option> directorsOptions;
	private Map<String,String> typeOptionsMap = new HashMap<String,String>();
	private AnnualBudgetSumData annualBudgetSumData = null;
	
	
	public BudgetGeneralView() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public BudgetGeneralView(List<AnnualBudgetData> budgets, 
							 SearchBudgetParam param,
							 List<Option> typeOptions,
							 List<Option> originOptions,
							 List<Option> managerOptions,
							 List<Option> associeOptions,
							 List<Option> exercicesOptions,
							 List<String> validContractReferences,
							 List<Exercise>  exercises,
							 List<Option> directorsOptions, List<Option> customerOptions,AnnualBudgetSumData annualBudgetSumData) {
		super();
		this.budgets = budgets;
		this.param = param;
		this.typeOptions = typeOptions;
		this.originOptions = originOptions;
		this.managerOptions = managerOptions;
		this.associeOptions = associeOptions;
		this.exercicesOptions =  exercicesOptions;
		this.validContractReferences = validContractReferences;
		this.exercises = exercises;
		this.directorsOptions = directorsOptions;
		//Fill the map
		for(Option option : typeOptions){
			this.typeOptionsMap.put(option.getId(),option.getName());
		}
		this.customerOptions =  customerOptions;
		this.annualBudgetSumData = annualBudgetSumData;
	}
	public List<AnnualBudgetData> getBudgets() {
		return budgets;
	}
	public void setBudgets(List<AnnualBudgetData> budgets) {
		this.budgets = budgets;
	}
	public int getCountYears() {
		return getYears().size();
	}
	

	public List<Integer> getYears() {
		return param == null? null: param.getYears();
	}

	public SearchBudgetParam getParam() {
		return param;
	}

	public void setParam(SearchBudgetParam param) {
		this.param = param;
	}

	

	public List<Option> getOriginOptions() {
		return originOptions;
	}

	public void setOriginOptions(List<Option> originOptions) {
		this.originOptions = originOptions;
	}

	public List<Option> getManagerOptions() {
		return managerOptions;
	}

	public void setManagerOptions(List<Option> managerOptions) {
		this.managerOptions = managerOptions;
	}

	public List<Option> getAssocieOptions() {
		return associeOptions;
	}

	public void setAssocieOptions(List<Option> associeOptions) {
		this.associeOptions = associeOptions;
	}

	public List<Option> getExercicesOptions() {
		return exercicesOptions;
	}

	public void setExercicesOptions(List<Option> exercicesOptions) {
		this.exercicesOptions = exercicesOptions;
	}

	public List<String> getValidContractReferences() {
		return validContractReferences;
	}

	public void setValidContractReferences(List<String> validContractReferences) {
		this.validContractReferences = validContractReferences;
	}

	public List<Exercise> getExercises() {
		return exercises;
	}

	public void setExercises(List<Exercise> exercises) {
		this.exercises = exercises;
	}

	public List<Option> getTypeOptions() {
		return typeOptions;
	}

	public void setTypeOptions(List<Option> typeOptions) {
		this.typeOptions = typeOptions;
	}

	

	public Exercise getCurrentExercise() {
		/*
		for(Exercise exercise : getExercises()){
			if(!exercise.getStatus().equalsIgnoreCase("CLOSED")){
				return exercise;
			}
		}
		*/
		return null;
	}

	public List<Option> getDirectorsOptions() {
		return directorsOptions;
	}

	public void setDirectorsOptions(List<Option> directorsOptions) {
		this.directorsOptions = directorsOptions;
	}

	public Map<String, String> getTypeOptionsMap() {
		return typeOptionsMap;
	}

	public void setTypeOptionsMap(Map<String, String> typeOptionsMap) {
		this.typeOptionsMap = typeOptionsMap;
	}

	public List<Option> getCustomerOptions() {
		return customerOptions;
	}

	public void setCustomerOptions(List<Option> customerOptions) {
		this.customerOptions = customerOptions;
	}

	public AnnualBudgetSumData getAnnualBudgetSumData() {
		return annualBudgetSumData;
	}

	public void setAnnualBudgetSumData(AnnualBudgetSumData annualBudgetSumData) {
		this.annualBudgetSumData = annualBudgetSumData;
	}
	

	
	
	

}
