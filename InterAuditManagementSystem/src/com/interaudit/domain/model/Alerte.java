package com.interaudit.domain.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "MISSION_ALERTES",schema="interaudit")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE )
public class Alerte implements java.io.Serializable,Comparable<Alerte>{
	
	public static final String ACTIVE = "ACTIVE";
	public static final String CANCELLED = "CANCELLED";
	public static final String CLOSED = "SENT";
	
	public int compareTo(Alerte o)
    {
		Alerte m = (Alerte)o;
		Integer id1 = m.getNumero();

		Integer id2 = this.numero;

		if( id2 > id1 )

		return 1;

		else if( id1 < id2 )

		return -1;

		else

		return 0;
    }
	
	
	public Alerte() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	public Alerte( int numero,double amount,String description,Employee owner,Mission mission) {
		super();
		this.numero = numero;
		this.amount = amount;
		this.description = description;
		this.createDate = new Date();
		this.owner = owner;
		this.mission = mission;
		this.status = Alerte.ACTIVE;
	}
	
	public boolean equals(Object obj){		
		if((this.getId()== null ) || (null == obj) || ( !(obj instanceof Alerte)  )){
			return false;
		}
		else{
			return this.getId().equals(((Alerte)obj).getId());
		}
	}
	
	public int hashCode(){
		final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
	}
	
	public String getCode(){
		return "ALERT_" + this.numero;
	}
	
	
	@Id
	@GeneratedValue
	
	private Long id;
	
	@Column(name = "VERSION", nullable = false)
	@Version
	private int version;
	
	private int numero;
	
	private double amount;
	
	private String description;
	
	private String status;
	
	private Date createDate;
	
	private Date sentDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Mission mission;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Employee owner;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Mission getMission() {
		return mission;
	}
	public void setMission(Mission mission) {
		this.mission = mission;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}

	public Date getSentDate() {
		return sentDate;
	}

	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Employee getOwner() {
		return owner;
	}

	public void setOwner(Employee owner) {
		this.owner = owner;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
