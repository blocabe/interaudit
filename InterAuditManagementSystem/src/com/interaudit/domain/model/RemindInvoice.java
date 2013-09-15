package com.interaudit.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;


@Entity
@Table(name = "INVOICE_REMINDS",schema="interaudit")
public class RemindInvoice  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	/*
	@GeneratedValue(generator="RemindInvoiceSeq")
	@SequenceGenerator(name="RemindInvoiceSeq",sequenceName="INVOICE_REMIND_SEQ", allocationSize=1)
	*/
	private Long id;
	
	@Temporal(TemporalType.DATE)
	private Date remindDate;
	
	 
	@Column(name = "sent")
	private boolean sent;
	 
	
	
	@Column(name = "LIBELLE", nullable = false)
	private String libelle;
	
	
	@Column(name = "LIBFIN", nullable = false)
	private String libfin;
	
	@Column(name = "LANG", nullable = false)
	private String lang;
	
	@Column(name = "ORDRE", nullable = false)
	private int order;
	
	
	@Column(name = "YEAR", nullable = false)
	private int exercise;
	
	@Column(name = "REFASSSEC", nullable = true, unique=false)
	private String refAssSec;
	
	
	
	 @ManyToMany 
	    @JoinTable(name="INVOICE_REMINDS_RELATION", 
	          joinColumns=@JoinColumn(name="REMINDID"),
	          inverseJoinColumns=@JoinColumn(name="INVOICEID"))
	private Collection<Invoice> invoices;

	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="fk_sender" , nullable = true)
	private Employee sender;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="fk_customer" , nullable = false)
	private Customer customer;
	
	@Column(name = "VERSION", nullable = false)
	@Version
	private int version;
	
	
	
	
	public RemindInvoice() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	public RemindInvoice(Customer customer, int order,String lang, String libelle,
			String libfin, Collection<Invoice> invoices) {
		super();
		this.customer = customer;
		this.order=order;
		this.lang= lang;
		this.remindDate = Calendar.getInstance().getTime();
		this.exercise =  Calendar.getInstance().get(Calendar.YEAR);
		this.sent = false;
		this.libelle = libelle;
		this.libfin = libfin;
		this.invoices = new ArrayList<Invoice>();
		
	}

	public boolean equals(Object obj){		
		if( (this.getId()== null ) || (null == obj) || ( !(obj instanceof RemindInvoice)  )){
			return false;
		}
		else{
			return this.getId().equals(((RemindInvoice)obj).getId());
		}
	}
	
	public int hashCode(){
		final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
	}

	public void addInvoice(Invoice facture) {
        if (!getInvoices().contains(facture)) {
        	getInvoices().add(facture);
        }
        if (!facture.getRappels().contains(this)) {
        	facture.getRappels().add(this);
        }
    }

    public Collection<Invoice> getInvoices() {
        return invoices;
    }


	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getRemindDate() {
		return remindDate;
	}
	public void setRemindDate(Date remindDate) {
		this.remindDate = remindDate;
	}
	
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public Employee getSender() {
		return sender;
	}
	public void setSender(Employee sender) {
		this.sender = sender;
	}
	
	
	public boolean isSent() {
		return sent;
	}
	public void setSent(boolean sent) {
		this.sent = sent;
	}
	



	public String getLibelle() {
		return libelle;
	}



	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}



	public String getLibfin() {
		return libfin;
	}



	public void setLibfin(String libfin) {
		this.libfin = libfin;
	}



	public Customer getCustomer() {
		return customer;
	}



	public void setCustomer(Customer customer) {
		this.customer = customer;
	}



	public String getLang() {
		return lang;
	}



	public void setLang(String lang) {
		this.lang = lang;
	}



	public int getOrder() {
		return order;
	}



	public void setOrder(int order) {
		this.order = order;
	}



	public int getExercise() {
		return exercise;
	}



	public void setExercise(int exercise) {
		this.exercise = exercise;
	}



	public String getRefAssSec() {
		return refAssSec;
	}



	public void setRefAssSec(String refAssSec) {
		this.refAssSec = refAssSec;
	}



	


	
}
