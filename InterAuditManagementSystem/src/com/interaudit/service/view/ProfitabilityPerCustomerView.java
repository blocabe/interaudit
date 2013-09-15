package com.interaudit.service.view;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.interaudit.domain.model.data.Option;
import com.interaudit.domain.model.data.ProfitabilityPerCustomerData;
import com.interaudit.service.param.SearchProfitabilityPerCustomerParam;

public class ProfitabilityPerCustomerView implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	//private List<ProfitabilityPerCustomerData>  data;
	//private Map <String,List<ProfitabilityPerCustomerData>> data;
	private List<ProfitabilityPerCustomerData> data;
	private SearchProfitabilityPerCustomerParam param;
	private List<Option> managerOptions;
	private List<Option> associeOptions;
	private List<Option> exercicesOptions;
	private List<Option> customerOptions;
	
	
	public ProfitabilityPerCustomerView() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ProfitabilityPerCustomerView( List<ProfitabilityPerCustomerData> data, 
			SearchProfitabilityPerCustomerParam param,
							 List<Option> managerOptions,
							 List<Option> associeOptions,
							 List<Option> exercicesOptions,
							 List<Option> customerOptions) {
		super();
		this.data = data;
		this.param = param;
		this.managerOptions = managerOptions;
		this.associeOptions = associeOptions;
		this.exercicesOptions =  exercicesOptions;
		this.customerOptions = customerOptions;
	}
	
	public int getCountYears() {
		return getYears().size();
	}
	

	public List<Integer> getYears() {
		return param == null? null: param.getYears();
	}

	public SearchProfitabilityPerCustomerParam getParam() {
		return param;
	}

	public void setParam(SearchProfitabilityPerCustomerParam param) {
		this.param = param;
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

	public List<ProfitabilityPerCustomerData> getData() {
		return data;
	}

	public void setData(List<ProfitabilityPerCustomerData> data) {
		this.data = data;
	}

	public List<Option> getCustomerOptions() {
		return customerOptions;
	}

	public void setCustomerOptions(List<Option> customerOptions) {
		this.customerOptions = customerOptions;
	}

	
	
	

}
