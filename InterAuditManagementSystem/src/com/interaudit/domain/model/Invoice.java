package com.interaudit.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.interaudit.util.HtmlUtil;

@Entity
@Table(name = "INVOICES",schema="interaudit")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE )
public  class Invoice implements Serializable , Comparable<Invoice>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String TYPE_ADVANCE = "AD";
	public static final String TYPE_FINALBILL = "FB";
	public static final String TYPE_CREDITNOTE = "CN";
	public static final String TYPE_SUPPLEMENT = "SP";
	
	public static final String FACTURE_STATUS_CODE_PENDING = "pending";
	public static final String FACTURE_STATUS_CODE_APPROVED = "approved";
	public static final String FACTURE_STATUS_CODE_ONGOING = "sent";
	public static final String FACTURE_STATUS_CODE_PAID = "paid";
	public static final String FACTURE_STATUS_CODE_CANCELLED = "cancelled";
	
	@Id
	@GeneratedValue
	/*
	@GeneratedValue(generator="InvoiceSeq")
	@SequenceGenerator(name="InvoiceSeq",sequenceName="INVOICE_SEQ", allocationSize=1)
	*/
	
	private Long id;
	
	
	@Column(name = "VERSION", nullable = false)
	@Version
	private int version;
	
	private String type;
	
	private String language;
	
	private double tva = 15;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Bank bank;
	
	private int delaiPaiement;
	
	@Temporal(TemporalType.DATE)
	private Date dateFacture;
	
	@Temporal(TemporalType.DATE)
	private Date sentDate;
	
	@Temporal(TemporalType.DATE)
	private Date dateEcheance;
	
	@Temporal(TemporalType.DATE)
	private Date dateFacturation;
	
	@Temporal(TemporalType.DATE)
	private Date dateOfPayment;
	
	@Column(name = "CURRENCY", nullable = false, length=3)
	private String currencyCode = "EUR";
	
	@Column(name = "YEAR", nullable = false)
	private String exercise;
	
	@Column(name = "MOIS", nullable = false)
	private String mois;
	
	@Column(nullable = false)
	private String libelle;
	
	@Column(name = "HONORAIRES", nullable = false)
	private double honoraires;
	
	@Column(name = "AMOUNT", nullable = false)
	private double amountEuro;
	
	@Column(name = "AMOUNT_NET", nullable = false)
	private double amountNetEuro;
	
	@Column(name = "REFERENCE", nullable = false, unique=true)
	private String reference;
	
	@Column(name = "PARENT_REFERENCE", nullable = true)
	private String parentReference;
	
	
	@Column(name = "REFASSSEC", nullable = true, unique=false)
	private String refAssSec;
	
	@Transient
	private String libTvaZeroOpt0;
	@Transient
	private String libTvaZeroOpt1;
	@Transient
	private String libTvaZeroOpt2;
	
	@Column(name = "TVAZEROLIB", nullable = true, unique=false)
	private String tvaZeroLibCode;
	
	
	
	
	@OneToMany(mappedBy = "facture", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<AddtionalFeeInvoice> frais = new HashSet<AddtionalFeeInvoice>();
	
	@OneToMany(mappedBy = "facture", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<DeductionFeeInvoice> deductions = new HashSet<DeductionFeeInvoice>();
	
	@ManyToMany(mappedBy="invoices")
    private Collection<RemindInvoice> rappels;

	
	public int compareTo(Invoice o)
    {
		Invoice m = (Invoice)o;
		return dateFacture.compareTo(m.dateFacture);
    }
		
	@ManyToOne(fetch = FetchType.EAGER)
	private Mission project = null;
	
	
	
	private String status;
	
	@Column(name = "PAYS", nullable = false)
	private String country;
	
	@Column(name = "LIBHONORAIRES", nullable = false)
	private String libHonoraires;
	
	
	@Column(name = "LIBDELAI", nullable = false)
	private String libDelai;
	
	
	
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="fk_sender" , nullable = true)
	private Employee sender;
	
	@Column(name = "BILLADDRESS", nullable = false)
    private String billingAddress;
	
	@Column(name = "CUSTCODE", nullable = false)
	private String customerCode;
	
	@Column(name = "CUSTNAME", nullable = false)
	private String customerName;
	
	 @Column(name = "approved")
	 private boolean approved;
	 
	 @Column(name = "sent")
	 private boolean sent;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="fk_partner" , nullable = false)
	private Employee partner;
	    	    
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="fk_creator" , nullable = false)
	private Employee creator;
	
	@ManyToMany
	@JoinTable(name = "DOCS_INVOICES", joinColumns = @JoinColumn(name = "ID_INVOICE"), inverseJoinColumns = @JoinColumn(name = "ID_DOCUMENT"))
	private List<Document> documents;
	
	@OneToMany(mappedBy = "facture", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	private Set<Payment> payments;
	
	private String exerciseMandat;
	private boolean interne = false;
	
	@Column(name = "EXCBALGE")
	private boolean excludedFromBalanceAgee = false;
	
	
	public Invoice(){}
	
	//Constructor for Advance and Final bill
	public Invoice(Mission project,Employee creator,String type, double honoraires ,double tva, int delaiPaiement,String language,Bank bank,String exerciseMandat,String exercise){
		this.project = project;
		this.dateFacture = new java.sql.Timestamp(System.currentTimeMillis());
		this.dateFacturation = this.dateFacture;
		
		Calendar c = Calendar.getInstance();
		c.clear();
		c.setTime(this.dateFacturation);
		
		this.exerciseMandat = exerciseMandat;
	
		this.mois = Integer.toString(c.get(Calendar.MONTH));
		this.exercise = exercise;//Integer.toString(Calendar.getInstance().get(Calendar.YEAR));//project.getExercise();		
		this.status = FACTURE_STATUS_CODE_PENDING;
		this.type = type;
		this.delaiPaiement = delaiPaiement;
		this.language=language;		
	    this.billingAddress  = project.getCustomer().getMainAddress();
	    this.customerCode = project.getCustomer().getCode();
	    this.customerName = project.getCustomer().getCompanyName();
	    this.country = project.getCustomer().getCountry();
		this.partner = project.getCustomer().getAssocieSignataire();		
		this.creator = creator;
		this.approved = false;
		this.honoraires = honoraires;
		this.tva = tva;	
		this.bank=bank;
		this.rappels = new ArrayList<RemindInvoice>();
		this.excludedFromBalanceAgee = false;
		    
	}
	
	
	
	
	//Constructor for Note de Credit only
	//String parentReference,Mission project,Employee creator ,double honoraires ,double tva, int delaiPaiement,String language
	public Invoice(Invoice parentInvoice,Employee creator ,double honoraires ,double tva,boolean interne){
		this.project = parentInvoice.getProject();
		this.dateFacture = new java.sql.Timestamp(System.currentTimeMillis());
		this.dateFacturation = this.dateFacture;
		Calendar c = Calendar.getInstance();
		c.clear();
		c.setTime(this.dateFacture);
		this.exerciseMandat = parentInvoice.getExerciseMandat();
	
		this.mois = Integer.toString(c.get(Calendar.MONTH));
		this.exercise = Integer.toString(c.get(Calendar.YEAR));// parentInvoice.getExercise();		
		this.status = FACTURE_STATUS_CODE_PENDING;
		this.type = Invoice.TYPE_CREDITNOTE;
		this.delaiPaiement = 0;
		this.language=parentInvoice.getLanguage();		
	    this.billingAddress  = this.project.getCustomer().getMainAddress();
	    this.customerCode = this.project.getCustomer().getCode();
	    this.customerName = this.project.getCustomer().getCompanyName();
	    this.country = this.project.getCustomer().getCountry();
		this.partner = this.project.getCustomer().getAssocieSignataire();		
		this.creator = creator;
		this.approved = false;
		this.honoraires = honoraires;
		this.tva = tva;	
		this.bank=null;
		this.parentReference=parentInvoice.getReference();
		this.interne= interne;
		this.excludedFromBalanceAgee = false;
	}
	
	public boolean equals(Object obj){		
		if( (this.getId()== null ) || (null == obj) || ( !(obj instanceof Invoice)  )){
			return false;
		}
		else{
			return this.getId().equals(((Invoice)obj).getId());
		}
	}
	
	public int hashCode(){
		final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
	}
	
	public Collection<RemindInvoice> getRappels() {
        return rappels;
    }
    
    public void addReminder(RemindInvoice reminder) {
        if (!getRappels().contains(reminder)) {
        	getRappels().add(reminder);
        }
        if (!reminder.getInvoices().contains(this)) {
        	reminder.getInvoices().add(this);
        }
    }

	
	
	
	public Customer getCustomer() {
		return project == null ? null:  project.getCustomer();
	}
	
	
	
	public double getAmountEuro() {
		return amountEuro;
	}
	public void setAmountEuro(double amountEuro) {
		this.amountEuro = amountEuro;
	}
	public Date getDateFacture() {
		return dateFacture;
	}
	public void setDateFacture(Date dateFacture) {
		this.dateFacture = dateFacture;
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
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Mission getProject() {
		return project;
	}
	public void setProject(Mission project) {
		this.project = project;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public Set<Payment> getPayments() {
		return payments;
	}

	public void setPayments(Set<Payment> payments) {
		this.payments = payments;
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

	

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public Date getSentDate() {
		return sentDate;
	}

	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}

	public List<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	

	

	public String getBillingAddress() {
		return billingAddress;
	}
	
	public String getBillingAddressHtml() {
		return  HtmlUtil.convertToHtmlFragment(billingAddress);
	}

	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public Employee getPartner() {
		return partner;
	}

	public void setPartner(Employee partner) {
		this.partner = partner;
	}

	public Employee getCreator() {
		return creator;
	}

	public void setCreator(Employee creator) {
		this.creator = creator;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	
	public Set<AddtionalFeeInvoice> getFrais() {
		return frais;
	}

	public void setFrais(Set<AddtionalFeeInvoice> frais) {
		this.frais = frais;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public double getTva() {
		return tva;
	}

	public void setTva(double tva) {
		this.tva = tva;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public int getDelaiPaiement() {
		return delaiPaiement;
	}

	public void setDelaiPaiement(int delaiPaiement) {
		this.delaiPaiement = delaiPaiement;
	}

	public boolean isSent() {
		return sent;
	}

	public void setSent(boolean sent) {
		this.sent = sent;
	}

	public double getAmountNetEuro() {
		return amountNetEuro;
	}

	public void setAmountNetEuro(double amountNetEuro) {
		this.amountNetEuro = amountNetEuro;
	}

	public double getHonoraires() {
		return honoraires;
	}

	public void setHonoraires(double honoraires) {
		this.honoraires = honoraires;
	}
	
	
	public int getCountRemindsInvoice(){
		
		return getRappels().size();
		
	}
	/*
	public RemindInvoice getActiveRemindInvoice(){
		
		for(RemindInvoice rappel : this.rappels){
			if(rappel.isActive() == true){
				return rappel;
			}
		}
		
		return null;
	}
	 */
	public String getLibHonoraires() {
		return libHonoraires;
	}
	
	public String getLibHonorairesHtml() {
		return  HtmlUtil.convertToHtmlFragment(libHonoraires);
	}

	public void setLibHonoraires(String libHonoraires) {
		this.libHonoraires = libHonoraires;
	}

	public Employee getSender() {
		return sender;
	}

	public void setSender(Employee sender) {
		this.sender = sender;
	}

	public String getLibDelai() {
		return libDelai;
	}

	public void setLibDelai(String libDelai) {
		this.libDelai = libDelai;
	}
	
	public String getLibDelaiHtml() {
		return  HtmlUtil.convertToHtmlFragment(libDelai);
	}

	public String getMois() {
		return mois;
	}

	public void setMois(String mois) {
		this.mois = mois;
	}

	public Set<DeductionFeeInvoice> getDeductions() {
		return deductions;
	}

	public void setDeductions(Set<DeductionFeeInvoice> deductions) {
		this.deductions = deductions;
	}

	public String getParentReference() {
		return parentReference;
	}

	public void setParentReference(String parentReference) {
		this.parentReference = parentReference;
	}
	
	
	public List<AddtionalFeeInvoice> getSortedFrais() {
			
			AddtionalFeeInvoice fraisArray[] = (AddtionalFeeInvoice[])frais.toArray(new AddtionalFeeInvoice[frais.size()]);
			Arrays.sort(fraisArray, new AddtionalFeeInvoiceComparateur());
			return new ArrayList<AddtionalFeeInvoice>(Arrays.asList(fraisArray));

		}
	
	public List<DeductionFeeInvoice> getSortedDeductions() {
		
		DeductionFeeInvoice deductionsArray[] = (DeductionFeeInvoice[])deductions.toArray(new DeductionFeeInvoice[deductions.size()]);
		Arrays.sort(deductionsArray, new DeductionFeeInvoiceComparateur());
		return new ArrayList<DeductionFeeInvoice>(Arrays.asList(deductionsArray));
	}
	
	
	public List<Payment> getSortedPayments() {
		
	    Payment paymentsArray[] = (Payment[])payments.toArray(new Payment[payments.size()]);
		Arrays.sort(paymentsArray, new PaymentComparateur());
		return new ArrayList<Payment>(Arrays.asList(paymentsArray));
	}
	
	class AddtionalFeeInvoiceComparateur implements Comparator<AddtionalFeeInvoice> {
		public int compare(AddtionalFeeInvoice obj1, AddtionalFeeInvoice obj2){

			//parameter are of type Object, so we have to downcast it to Employee objects

			Long id1 = ( (AddtionalFeeInvoice) obj1).getId();

			Long id2 = ( (AddtionalFeeInvoice) obj2).getId();

			if( id2 > id1 )

			return 1;

			else if( id1 < id2 )

			return -1;

			else

			return 0;

			}


		}
	
	class DeductionFeeInvoiceComparateur implements Comparator<DeductionFeeInvoice> {
		public int compare(DeductionFeeInvoice obj1, DeductionFeeInvoice obj2){

			//parameter are of type Object, so we have to downcast it to Employee objects

			Long id1 = ( (DeductionFeeInvoice) obj1).getId();

			Long id2 = ( (DeductionFeeInvoice) obj2).getId();

			if( id2 > id1 )

			return 1;

			else if( id1 < id2 )

			return -1;

			else

			return 0;

			}


		}
	
	class PaymentComparateur implements Comparator<Payment> {
		public int compare(Payment obj1, Payment obj2){

			//parameter are of type Object, so we have to downcast it to Employee objects

			Long id1 = ( (Payment) obj1).getId();

			Long id2 = ( (Payment) obj2).getId();

			if( id1 > id2 )

			return 1;

			else if( id1 < id2 )

			return -1;

			else

			return 0;

			}


		}
	
	
	class PaymentDateComparateur implements Comparator<Payment> {
		public int compare(Payment obj1, Payment obj2){

			//parameter are of type Object, so we have to downcast it to Employee objects

			Date datePayement1 = ( (Payment) obj1).getPaymentDate();

			Date datePayement2 = ( (Payment) obj2).getPaymentDate();
			
			if(datePayement1 == null && datePayement2==null){
				return 0;
			}else if(datePayement1 != null && datePayement2==null){
				return 1;
			}else if(datePayement1 == null && datePayement2!=null){
				return -1;
			}else if(datePayement1.compareTo(datePayement2)>0){
				return 1;
        	}else if(datePayement1.compareTo(datePayement2)<0){
        		return -1;
        	}else if(datePayement1.compareTo(datePayement2)==0){
        		return 0;
        	}else{
        		return 0;
        	}
		
		}
	}
	
	public Date getMAxPaymentDate(){
		
		List<Payment> list = new ArrayList<Payment>();
		list.addAll(payments);
		Collections.sort(list, new PaymentDateComparateur());
		Collections.reverse(list);
		Payment payement = list.get(0);
		
		if(payement != null){
			return payement.getPaymentDate();
		}
		
		return null;
		
	}

	public String getRefAssSec() {
		return refAssSec;
	}

	public void setRefAssSec(String refAssSec) {
		this.refAssSec = refAssSec;
	}

	public String getExerciseMandat() {
		return exerciseMandat;
	}

	public void setExerciseMandat(String exerciseMandat) {
		this.exerciseMandat = exerciseMandat;
	}

	public Date getDateEcheance() {
		return dateEcheance;
	}

	public void setDateEcheance(Date dateEcheance) {
		this.dateEcheance = dateEcheance;
	}

	public Date getDateFacturation() {
		return dateFacturation;
	}

	public void setDateFacturation(Date dateFacturation) {
		this.dateFacturation = dateFacturation;
	}

	public boolean isInterne() {
		return interne;
	}

	public void setInterne(boolean interne) {
		this.interne = interne;
	}

	public String getLibTvaZeroOpt0() {
		return libTvaZeroOpt0;
	}

	public void setLibTvaZeroOpt0(String libTvaZeroOpt0) {
		this.libTvaZeroOpt0 = libTvaZeroOpt0;
	}

	public String getLibTvaZeroOpt1() {
		return libTvaZeroOpt1;
	}

	public void setLibTvaZeroOpt1(String libTvaZeroOpt1) {
		this.libTvaZeroOpt1 = libTvaZeroOpt1;
	}

	public String getLibTvaZeroOpt2() {
		return libTvaZeroOpt2;
	}

	public void setLibTvaZeroOpt2(String libTvaZeroOpt2) {
		this.libTvaZeroOpt2 = libTvaZeroOpt2;
	}

	public String getTvaZeroLibCode() {
		return tvaZeroLibCode;
	}

	public void setTvaZeroLibCode(String tvaZeroLibCode) {
		this.tvaZeroLibCode = tvaZeroLibCode;
	}

	public boolean isExcludedFromBalanceAgee() {
		return excludedFromBalanceAgee;
	}

	public void setExcludedFromBalanceAgee(boolean excludedFromBalanceAgee) {
		this.excludedFromBalanceAgee = excludedFromBalanceAgee;
	}

	


}
