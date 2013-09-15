package com.interaudit.domain.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

@Entity
@Table(name = "PAYMENTS",schema="interaudit")
public class Payment implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	/*
	@GeneratedValue(generator="PaymentSeq")
	@SequenceGenerator(name="PaymentSeq",sequenceName="PAYMENT_SEQ", allocationSize=1)
	*/
	private Long id;
	@Column(name = "CODE", nullable = false, unique=false)
	private String bankCode;
	
	@Column(nullable = false, unique=true)
	private String reference;
	
	@Column(name = "YEAR")
	private String year;
	
	@Column(nullable = false)
	private String customerName;
	
	@Column(name = "TOTALDU", nullable = false, unique=false)
	private double totalDu;
	
	@Column(name = "TOTALNC", nullable = false, unique=false)
	private double totalNoteCredit;
	
	@Column(name = "TOTALPAID", nullable = false, unique=false)
	private double totalPaid;
	
	@Column(name = "TOTALREMAINING", nullable = false, unique=false)
	private double totalRemaining;
	
	@Column(name = "AMOUNT", nullable = false, unique=false)
	private double amount;
	
	@Column(name = "REIMBOURSE", nullable = true, unique=false)
	private boolean reimbourse;
	
	@Column(name = "ESCOMPTE", nullable = false, unique=false)
	private boolean escompte;
	
	
	
	@Column(name = "CURRENCY", nullable = false, length=3)
	private String currencyCode;	
	
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date paymentDate; 
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_facture" , nullable = false)
	private Invoice facture;
	
	@Transient
	private String factureReference;
	
	@Transient
	private Long factureId;
	
	public Payment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Payment(Long id, String bankCode, String reference, String year,
			String customerName, double totalDu, double totalNoteCredit,
			double totalPaid, double totalRemaining, double amount,
			boolean reimbourse, String currencyCode, Date paymentDate,
			String factureReference, Long factureId,boolean escompte) {
		super();
		this.id = id;
		this.bankCode = bankCode;
		this.reference = reference;
		this.year = year;
		this.customerName = customerName;
		this.totalDu = totalDu;
		this.totalNoteCredit = totalNoteCredit;
		this.totalPaid = totalPaid;
		this.totalRemaining = totalRemaining;
		this.amount = amount;
		this.reimbourse = reimbourse;
		this.currencyCode = currencyCode;
		this.paymentDate = paymentDate;
		this.factureReference = factureReference;
		this.factureId = factureId;
		this.escompte = escompte;
	}

	@Column(name = "VERSION", nullable = false)
	@Version
	private int version;
	
	public boolean equals(Object obj){		
		if( (this.getId()== null ) || (null == obj) || ( !(obj instanceof Payment)  )){
			return false;
		}
		else{
			return this.getId().equals(((Payment)obj).getId());
		}
	}
	
	public int hashCode(){
		final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public Date getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
	public Invoice getFacture() {
		return facture;
	}
	public void setFacture(Invoice facture) {
		this.facture = facture;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getFactureReference() {
		return factureReference;
	}
	public void setFactureReference(String factureReference) {
		this.factureReference = factureReference;
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
	public double getTotalDu() {
		return totalDu;
	}
	public void setTotalDu(double totalDu) {
		this.totalDu = totalDu;
	}
	public double getTotalNoteCredit() {
		return totalNoteCredit;
	}
	public void setTotalNoteCredit(double totalNoteCredit) {
		this.totalNoteCredit = totalNoteCredit;
	}
	public double getTotalPaid() {
		return totalPaid;
	}
	public void setTotalPaid(double totalPaid) {
		this.totalPaid = totalPaid;
	}
	public double getTotalRemaining() {
		return totalRemaining;
	}
	public void setTotalRemaining(double totalRemaining) {
		this.totalRemaining = totalRemaining;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}

	public boolean isReimbourse() {
		return reimbourse;
	}

	public void setReimbourse(boolean reimbourse) {
		this.reimbourse = reimbourse;
	}

	public Long getFactureId() {
		return factureId;
	}

	public void setFactureId(Long factureId) {
		this.factureId = factureId;
	}

	public boolean isEscompte() {
		return escompte;
	}

	public void setEscompte(boolean escompte) {
		this.escompte = escompte;
	}
}
