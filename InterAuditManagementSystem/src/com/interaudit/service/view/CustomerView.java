package com.interaudit.service.view;

import java.io.Serializable;
import java.util.List;

import com.interaudit.domain.model.data.CustomerData;
import com.interaudit.domain.model.data.Option;
import com.interaudit.service.param.SearchCustomerParam;

public class CustomerView implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private List<CustomerData> customers;
	private SearchCustomerParam param;
	private List<Option> managers;
	private List<Option> associes;
	private List<Option> directorsOptions;
	
	
	public CustomerView() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public CustomerView(List<CustomerData> customers, 
			SearchCustomerParam param,List<Option> managers, List<Option> directorsOptions,List<Option> associes) {
		super();
		this.customers = customers;
		this.param = param;
		this.managers = managers;
		this.directorsOptions = directorsOptions;
		this.associes = associes;
	}

	public List<CustomerData> getCustomers() {
		return customers;
	}

	public void setCustomers(List<CustomerData> customers) {
		this.customers = customers;
	}

	public SearchCustomerParam getParam() {
		return param;
	}

	public void setParam(SearchCustomerParam param) {
		this.param = param;
	}

	public List<Option> getManagers() {
		return managers;
	}

	public void setManagers(List<Option> managers) {
		this.managers = managers;
	}

	public List<Option> getDirectorsOptions() {
		return directorsOptions;
	}

	public void setDirectorsOptions(List<Option> directorsOptions) {
		this.directorsOptions = directorsOptions;
	}

	public List<Option> getAssocies() {
		return associes;
	}

	public void setAssocies(List<Option> associes) {
		this.associes = associes;
	}

	
	
	

	
	
	

}
