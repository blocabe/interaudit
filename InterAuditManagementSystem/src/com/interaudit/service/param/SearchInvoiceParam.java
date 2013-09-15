package com.interaudit.service.param;

import java.util.ArrayList;
import java.util.List;

import com.interaudit.domain.model.Invoice;

public class SearchInvoiceParam {
	
	private String year;
	private String mois;
	
	private String pending = "";
	private String approved= "";
	private String ongoing = "";
	private String paid = "";
	private String cancelled = "";
	private String unpaid ="";
	private String  customer;
	private String  type;
	private String invoiceId = "";
	
	
	
	public SearchInvoiceParam() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public SearchInvoiceParam(String invoiceId) {
		super();
		this.invoiceId = invoiceId;
	}

	public SearchInvoiceParam(String year,  String mois,
			String pending,
			String approved,
			String ongoing,
			String paid,
			String unpaid,
			String customer, String type) {
		super();
		this.year = year;
		this.mois = mois;
		this.pending = pending;
		this.approved = approved;
		this.ongoing = ongoing;
		this.paid = paid;
		this.unpaid = unpaid;
		this.customer = customer;
		this.type = type;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPending() {
		return pending;
	}

	public void setPending(String pending) {
		this.pending = pending;
	}

	public String getOngoing() {
		return ongoing;
	}

	public void setOngoing(String ongoing) {
		this.ongoing = ongoing;
	}

	public String getPaid() {
		return paid;
	}

	public void setPaid(String paid) {
		this.paid = paid;
	}

	public String getCancelled() {
		return cancelled;
	}

	public void setCancelled(String cancelled) {
		this.cancelled = cancelled;
	}
	
	public List<String>  getListOfStatus(){
		
		List<String> statuses = new ArrayList<String>();
		if(pending != null && pending.length() > 0){
			statuses.add(Invoice.FACTURE_STATUS_CODE_PENDING);
		}
		
		if(approved != null && approved.length() > 0){
			statuses.add(Invoice.FACTURE_STATUS_CODE_APPROVED);
		}
		
		if(ongoing != null && ongoing.length() > 0){
			statuses.add(Invoice.FACTURE_STATUS_CODE_ONGOING);
		}
		
		if(paid != null && paid.length() > 0){
			statuses.add(Invoice.FACTURE_STATUS_CODE_PAID);
		}
		/*
		if(unpaid != null && unpaid.length() > 0){
			statuses.add(Invoice.FACTURE_STATUS_CODE_CANCELLED);
		}
		*/
		
		return statuses;
	}

	public String getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getApproved() {
		return approved;
	}

	public void setApproved(String approved) {
		this.approved = approved;
	}

	public String getMois() {
		return mois;
	}

	public void setMois(String mois) {
		this.mois = mois;
	}

	public String getUnpaid() {
		return unpaid;
	}

	public void setUnpaid(String unpaid) {
		this.unpaid = unpaid;
	}
	
}
