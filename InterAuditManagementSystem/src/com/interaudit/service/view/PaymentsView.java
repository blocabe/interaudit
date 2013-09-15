package com.interaudit.service.view;

import java.io.Serializable;
import java.util.List;
import com.interaudit.domain.model.Payment;
import com.interaudit.domain.model.data.Option;
import com.interaudit.service.param.SearchPaymentParam;

public class PaymentsView implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private List<Payment>  payments;
	private SearchPaymentParam param;
	private List<Option> bankOptions;
	private List<String> customerNames;
	private List<Option> yearOptions;
	private List<Option> customersOptions;
	
	
	public PaymentsView() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	


	public PaymentsView(List<Payment> payments, SearchPaymentParam param,
			List<Option> bankOptions, List<String> customerNames,List<Option> yearOptions,List<Option> customersOptions) {
		super();
		this.payments = payments;
		this.param = param;
		this.bankOptions = bankOptions;
		this.customerNames = customerNames;
		this.yearOptions = yearOptions;
		this.customersOptions = customersOptions;
	}




	public List<Payment> getPayments() {
		return payments;
	}


	public void setPayments(List<Payment> payments) {
		this.payments = payments;
	}


	public SearchPaymentParam getParam() {
		return param;
	}


	public void setParam(SearchPaymentParam param) {
		this.param = param;
	}


	public List<Option> getBankOptions() {
		return bankOptions;
	}


	public void setBankOptions(List<Option> bankOptions) {
		this.bankOptions = bankOptions;
	}


	public List<String> getCustomerNames() {
		return customerNames;
	}


	public void setCustomerNames(List<String> customerNames) {
		this.customerNames = customerNames;
	}




	public List<Option> getYearOptions() {
		return yearOptions;
	}




	public void setYearOptions(List<Option> yearOptions) {
		this.yearOptions = yearOptions;
	}




	public List<Option> getCustomersOptions() {
		return customersOptions;
	}




	public void setCustomersOptions(List<Option> customersOptions) {
		this.customersOptions = customersOptions;
	}
	
	
	

}
