package com.interaudit.service.view;

import java.io.Serializable;
import java.util.List;

import com.interaudit.domain.model.Contact;
import com.interaudit.domain.model.Employee;
import com.interaudit.service.param.SearchContactParam;

public class ContactsView implements Serializable{
	
	private static final long serialVersionUID = 1L;
	

	private List<Contact> contacts;
	private SearchContactParam param;
	private List<Employee> managers;
	
	
	public ContactsView() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ContactsView(List<Contact> contacts, 
			SearchContactParam param,List<Employee> managers) {
		super();
		this.contacts = contacts;
		this.param = param;
		this.managers = managers;
	}

	

	public SearchContactParam getParam() {
		return param;
	}

	public void setParam(SearchContactParam param) {
		this.param = param;
	}

	public List<Employee> getManagers() {
		return managers;
	}

	public void setManagers(List<Employee> managers) {
		this.managers = managers;
	}

	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	
	
	

	
	
	

}
