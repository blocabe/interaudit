package com.interaudit.service.param;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SearchProfitabilityPerCustomerParam implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Integer> years;
	private Long manager;
	private Long associe;
	private Long customer;
	private String customerStartString;
	
	public SearchProfitabilityPerCustomerParam(Long customer, Long manager,
			Long associe) {
		super();
		this.customer = customer;
		this.manager = manager;
		this.associe = associe;
	}
	
	public SearchProfitabilityPerCustomerParam( Long customer,Long manager,
			Long associe,List<Integer> years) {
		this( customer,manager,associe);
		this.years = years;
	}

	public SearchProfitabilityPerCustomerParam( Long customer,Long manager,
			Long associe,String customerStartString) {
		this( customer,manager,associe);
		this.customerStartString = customerStartString;
	}
	
	
	public SearchProfitabilityPerCustomerParam() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public List<String> getYearsAsString() {
		List<String> yearsAsTring = new ArrayList<String>();
		for(Integer year : years){
			yearsAsTring.add(year.toString());
		}
		return yearsAsTring;
	}
	
	public List<Integer> getYears() {
		return years;
	}
	public void setYears(List<Integer> years) {
		this.years = years;
	}
	public Long getManager() {
		return manager;
	}
	public void setManager(Long manager) {
		this.manager = manager;
	}
	public String getCustomerStartString() {
		return customerStartString;
	}
	public void setCustomerStartString(String customerStartString) {
		this.customerStartString = customerStartString;
	}
	public Long getAssocie() {
		return associe;
	}
	public void setAssocie(Long associe) {
		this.associe = associe;
	}

	public Long getCustomer() {
		return customer;
	}

	public void setCustomer(Long customer) {
		this.customer = customer;
	}

}
