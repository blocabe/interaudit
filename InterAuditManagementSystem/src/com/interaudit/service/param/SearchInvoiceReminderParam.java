package com.interaudit.service.param;

import java.util.ArrayList;
import java.util.List;


public class SearchInvoiceReminderParam {
	
	private String year;	
	private String active = "";
	private String notActive = "";
	private String sent= "";	
	private String notSent= "";	
	private String  customer;	
	private String invoiceReminderId = "";
	
	public SearchInvoiceReminderParam() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public SearchInvoiceReminderParam(String invoiceReminderId) {
		super();
		this.invoiceReminderId = invoiceReminderId.trim();
	}

	
	public SearchInvoiceReminderParam(String year, String active,String notActive, String sent,String notSent,
			String customer) {
		super();
		this.year = year;
		this.active = active;
		this.notActive = notActive;
		this.sent = sent;
		this.notSent = notSent;
		this.customer = customer;
	}
	
	public List<Boolean> getListOfActiveStatus() {
		
		List<Boolean>  listOfStatus = new ArrayList<Boolean>();
		
		if(active != null)listOfStatus.add(Boolean.TRUE);
		if(notActive != null)listOfStatus.add(Boolean.FALSE);
		
		return listOfStatus;
	}
	
	public List<Boolean> getListOfSentStatus() {
		
		List<Boolean>  listOfStatus = new ArrayList<Boolean>();
		
		if(sent != null)listOfStatus.add(Boolean.TRUE);
		if(notSent != null)listOfStatus.add(Boolean.FALSE);
		
		return listOfStatus;
	}

	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getSent() {
		return sent;
	}

	public void setSent(String sent) {
		this.sent = sent;
	}

	public String getInvoiceReminderId() {
		return invoiceReminderId;
	}

	public void setInvoiceReminderId(String invoiceReminderId) {
		this.invoiceReminderId = invoiceReminderId;
	}

	public String getNotActive() {
		return notActive;
	}

	public void setNotActive(String notActive) {
		this.notActive = notActive;
	}

	public String getNotSent() {
		return notSent;
	}

	public void setNotSent(String notSent) {
		this.notSent = notSent;
	}

	
	
}
