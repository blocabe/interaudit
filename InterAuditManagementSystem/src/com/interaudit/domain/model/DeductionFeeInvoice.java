package com.interaudit.domain.model;

import java.io.Serializable;
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
import javax.persistence.Version;

import com.interaudit.util.HtmlUtil;


@Entity
@Table(name = "INVOICE_DEDUCTION_FEES",schema="interaudit")
public class DeductionFeeInvoice  implements Serializable {
	
	
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
	private Date createDate;
	
	private String codeJustification;
	
	private String justification;
	
	private double value;
	
	public DeductionFeeInvoice() {
		super();
		createDate = new Date();
	}
	public DeductionFeeInvoice(String codeJustification,String justification, double value) {
		this();
		this.codeJustification = codeJustification;
		this.justification = justification;
		this.value = value;
	}
	
	public boolean equals(Object obj){		
		if( (this.getId()== null ) || (null == obj) || ( !(obj instanceof DeductionFeeInvoice)  )){
			return false;
		}
		else{
			return this.getId().equals(((DeductionFeeInvoice)obj).getId());
		}
	}
	
	public int hashCode(){
		final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
	}
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_facture" , nullable = false)
	private Invoice facture;
	
	@Column(name = "VERSION", nullable = false)
	@Version
	private int version;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getJustification() {
		return justification;
	}
	
	public String getJustificationHtml() {
		return  HtmlUtil.convertToHtmlFragment(justification);
	}
	
	public void setJustification(String justification) {
		this.justification = justification;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	
	public String getCode(){
		return Long.toString( this.createDate.getTime());		
	}
	public String getCodeJustification() {
		return codeJustification;
	}
	public void setCodeJustification(String codeJustification) {
		this.codeJustification = codeJustification;
	}

}
