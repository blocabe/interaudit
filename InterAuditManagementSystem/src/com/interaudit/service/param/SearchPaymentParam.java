package com.interaudit.service.param;

import java.util.Date;

public class SearchPaymentParam {
	
	private String year;
	private String invoiceReference;
	private String bankCode;
	private String customerNameLike;
	private Date  fromDate;
	private Date  toDate;
	
	
	
	
	public SearchPaymentParam() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	



	public SearchPaymentParam(String invoiceReference, String bankCode,
			String customerNameLike, Date fromDate, Date toDate,String year) {
		super();
		this.invoiceReference = invoiceReference;
		this.bankCode = bankCode;
		this.customerNameLike = customerNameLike;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.year = year;
		
	}





	public String getInvoiceReference() {
		return invoiceReference;
	}



	public void setInvoiceReference(String invoiceReference) {
		this.invoiceReference = invoiceReference;
	}



	public String getBankCode() {
		return bankCode;
	}



	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}



	public String getCustomerNameLike() {
		return customerNameLike;
	}



	public void setCustomerNameLike(String customerNameLike) {
		this.customerNameLike = customerNameLike;
	}



	public Date getFromDate() {
		return fromDate;
	}



	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}



	public Date getToDate() {
		return toDate;
	}



	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}





	public String getYear() {
		return year;
	}





	public void setYear(String year) {
		this.year = year;
	}





	
	
	

}
