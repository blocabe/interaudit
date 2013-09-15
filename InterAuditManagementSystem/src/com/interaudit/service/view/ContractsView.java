package com.interaudit.service.view;

import java.io.Serializable;
import java.util.List;

import com.interaudit.domain.model.data.ContractData;
import com.interaudit.service.param.SearchContractParam;

public class ContractsView implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private List<ContractData> contracts;
	private SearchContractParam param;
	private List<String> customers;
	
	
	public ContractsView() {
		super();
	}
	
	public ContractsView(List<ContractData> contracts, 
			SearchContractParam param,List<String> customers) {
		super();
		this.contracts = contracts;
		this.param = param;
		this.customers = customers;
	}

	

	public SearchContractParam getParam() {
		return param;
	}

	public void setParam(SearchContractParam param) {
		this.param = param;
	}

	

	public List<ContractData> getContracts() {
		return contracts;
	}

	public void setContracts(List<ContractData> contracts) {
		this.contracts = contracts;
	}

	public List<String> getCustomers() {
		return customers;
	}

	public void setCustomers(List<String> customers) {
		this.customers = customers;
	}

}
