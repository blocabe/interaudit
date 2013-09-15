package com.interaudit.service.param;

import java.io.Serializable;
import java.util.List;

public class SearchBalanceAgeeParam implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String customerCode;
	
	

	public SearchBalanceAgeeParam() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SearchBalanceAgeeParam(String customerCode) {
		super();
		this.customerCode = customerCode;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	

	



	
	
	

}
