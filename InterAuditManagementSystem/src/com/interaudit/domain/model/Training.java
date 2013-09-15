package com.interaudit.domain.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TRAININGS",schema="interaudit")
public class Training implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	/*
	@GeneratedValue(generator="TrainingSeq")
	@SequenceGenerator(name="TrainingSeq",sequenceName="TRAINING_SEQ", allocationSize=1)
	*/
	private Long id;

	@Column(name = "TITLE", nullable = false)
	private String title;
	
	@Column( nullable = false)
	private String description;
	
	@Column( nullable = false)
	private Date startDate;
	
	@Column( nullable = false)
	private Date endDate;
	
	@Column( nullable = false)
	private String companyName;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Employee beneficiaire;
	
	

	public Training() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Training(String title, String description, Date startDate,
			Date endDate, String companyName, Employee beneficiaire) {
		super();
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.companyName = companyName;
		this.beneficiaire = beneficiaire;
	}
	
	public boolean equals(Object obj){		
		if( (this.getId()== null ) || (null == obj) || ( !(obj instanceof Training)  )){
			return false;
		}
		else{
			return this.getId().equals(((Training)obj).getId());
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Employee getBeneficiaire() {
		return beneficiaire;
	}

	public void setBeneficiaire(Employee beneficiaire) {
		this.beneficiaire = beneficiaire;
	}
	

}
