package com.interaudit.util;

import com.interaudit.domain.model.Employee;
import com.interaudit.domain.model.Invoice;

public class WebContext {
	
	private Employee currentUser;
	private String uilang = "";
	private Invoice currentInvoice;
	
	public void clearContext()
	  {
	  	 
	  	 currentUser = null;
	  }

	public Employee getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(Employee currentUser) {
		this.currentUser = currentUser;
	}

	public String getUilang() {
		return uilang;
	}

	public void setUilang(String uilang) {
		this.uilang = uilang;
	}

	public Invoice getCurrentInvoice() {
		return currentInvoice;
	}

	public void setCurrentInvoice(Invoice currentInvoice) {
		this.currentInvoice = currentInvoice;
	}

}
