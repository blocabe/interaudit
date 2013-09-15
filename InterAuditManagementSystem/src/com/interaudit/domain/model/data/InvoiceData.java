package com.interaudit.domain.model.data;

import java.io.Serializable;
import java.util.Date;

public class InvoiceData implements Serializable{
	private static final long serialVersionUID = 1L;
	
	
	private Long id;
	private String type;
	private Date dateFacture;
	private Date sentDate;
	private Date dateOfPayment;
	private String exercise;
	private String libelle;
	private Double amountEuro;
	private Double amountPaidEuro;
	private String reference;
	private String customerName;
	private String associeCode;
	private String status;
	private Double amountEuroTva;
	private String language;
	private Long countRappels;
	private String parentReference;
	private double totalNoteDeCredit;
	
	private Date dateFacturation;
	private String customerCode;
	private boolean excludedFromBalanceAgee;
	
	
	
	public InvoiceData() {
		super();
		// TODO Auto-generated constructor stub
	}
	public InvoiceData(Long id, String type, Date dateFacture, Date sentDate,
			Date dateOfPayment, String exercise, String libelle,
			Double amountEuro, String reference, String customerName,
			String associeCode, String status,Double amountPaidEuro, Double amountEuroTva,String language,Long countRappels,String parentReference,double totalNoteDeCredit,
			Date dateFacturation,
			String customerCode,
			boolean excludedFromBalanceAgee) {
		super();
		this.id = id;
		this.type = type;
		this.dateFacture = dateFacture;
		this.sentDate = sentDate;
		this.dateOfPayment = dateOfPayment;
		this.exercise = exercise;
		this.libelle = libelle;
		this.amountEuro = amountEuro;
		this.reference = reference;
		this.customerName = customerName;
		this.associeCode = associeCode;
		this.status = status;
		this.amountPaidEuro = amountPaidEuro;
		this.amountEuroTva = amountEuroTva;
		this.language = language;
		this.countRappels = countRappels;
		this.parentReference =  parentReference;
		this.totalNoteDeCredit= totalNoteDeCredit;
		this.dateFacturation=dateFacturation;
		this.customerCode=customerCode;
		this.excludedFromBalanceAgee =  excludedFromBalanceAgee;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getDateFacture() {
		return dateFacture;
	}
	public void setDateFacture(Date dateFacture) {
		this.dateFacture = dateFacture;
	}
	public Date getSentDate() {
		return sentDate;
	}
	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}
	public Date getDateOfPayment() {
		return dateOfPayment;
	}
	public void setDateOfPayment(Date dateOfPayment) {
		this.dateOfPayment = dateOfPayment;
	}
	public String getExercise() {
		return exercise;
	}
	public void setExercise(String exercise) {
		this.exercise = exercise;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public Double getAmountEuro() {
		return amountEuro;
	}
	public void setAmountEuro(Double amountEuro) {
		this.amountEuro = amountEuro;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getAssocieCode() {
		return associeCode;
	}
	public void setAssocieCode(String associeCode) {
		this.associeCode = associeCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Double getAmountPaidEuro() {
		return amountPaidEuro;
	}
	public void setAmountPaidEuro(Double amountPaidEuro) {
		this.amountPaidEuro = amountPaidEuro;
	}
	public Double getAmountEuroTva() {
		return amountEuroTva;
	}
	public void setAmountEuroTva(Double amountEuroTva) {
		this.amountEuroTva = amountEuroTva;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public Long getCountRappels() {
		return countRappels;
	}
	public void setCountRappels(Long countRappels) {
		this.countRappels = countRappels;
	}
	public String getParentReference() {
		return parentReference;
	}
	public void setParentReference(String parentReference) {
		this.parentReference = parentReference;
	}
	public double getTotalNoteDeCredit() {
		return totalNoteDeCredit;
	}
	public void setTotalNoteDeCredit(double totalNoteDeCredit) {
		this.totalNoteDeCredit = totalNoteDeCredit;
	}
	public Date getDateFacturation() {
		return dateFacturation;
	}
	public void setDateFacturation(Date dateFacturation) {
		this.dateFacturation = dateFacturation;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public boolean isExcludedFromBalanceAgee() {
		return excludedFromBalanceAgee;
	}
	public void setExcludedFromBalanceAgee(boolean excludedFromBalanceAgee) {
		this.excludedFromBalanceAgee = excludedFromBalanceAgee;
	}
	
	
	
}
