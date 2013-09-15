package com.interaudit.domain.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "EMAIL_DATA",schema="interaudit")
public class EmailData implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String STATUS_PENDING = "PENDING";
	public static final String STATUS_DONE_OK = "DONE_OK";
	public static final String STATUS_DONE_NOK = "DONE_NOK";
	
	public static final String TYPE_PLANNING_PUBLISH = "PLANNING_PUBLISH";
	public static final String TYPE_MISSION_COMMUNICATION = "MISSION_COMMUNICATION";
	public static final String TYPE_FACTURATION_COMMUNICATION = "FACTURATION_COMMUNICATION";
	public static final String TYPE_TIMESHEET_COMMUNICATION = "TIMESHEET_COMMUNICATION";
	public static final String TYPE_EWS_COMMUNICATION = "EWS_COMMUNICATION";
	public static final String TYPE_REMINDER_COMMUNICATION = "TYPE_REMINDER_COMMUNICATION";
	
	
	private String sender_address;
	private String receiver_address;
	private String mailSubject;
	private String mailContents;
	private Date created;
	private Date sentDate;
	private String status;	
	private String type;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_sender" , nullable = true)
	private Employee sender;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_receiver" , nullable = true)
	private Employee receiver;
	
	/*
	msg.email.new.request.to.user = "Dear User %n Thank you for your request which has been received by the ESDS network registered %n with the reference number < %1$s >. %n At any time you can find out the status of your request via this link:%n %2$s";
	public static final String TYPE_PLANNING_PUBLISH = "PLANNING_PUBLISH";
	public static final String TYPE_MISSION_COMMUNICATION = "MISSION_COMMUNICATION";
	public static final String TYPE_FACTURATION_COMMUNICATION = "FACTURATION_COMMUNICATION";
	public static final String TYPE_TIMESHEET_COMMUNICATION = "TIMESHEET_COMMUNICATION";
	public static final String TYPE_EWS_COMMUNICATION = "EWS_COMMUNICATION";
	*/
	
	public void markAsDone()
	{
		this.setStatus(EmailData.STATUS_DONE_OK);
	}
	
	public void markAsFailed()
	{
		this.setStatus(EmailData.STATUS_DONE_NOK);
	}
	
	public EmailData() {
		super();
	}

	public EmailData( Employee sender, Employee receiver,String mailSubject, String mailContents, String type) {
		super();
		this.sender = sender;
		this.receiver = receiver;
		this.sender_address = sender.getEmail();
		this.receiver_address = receiver.getEmail();
		this.mailSubject = mailSubject;
		this.mailContents = mailContents;
		this.created = new Date();
		this.status = EmailData.STATUS_PENDING;
		this.type = type;
		
	}
	

	public EmailData( String sender_addresses, String receiver_addresses,String mailSubject, String mailContents) {
		super();
		this.sender = null;
		this.receiver = null;
		this.sender_address = sender_addresses;
		this.receiver_address = receiver_addresses;
		this.mailSubject = mailSubject;
		this.mailContents = mailContents;
		this.created = new Date();
		this.status = EmailData.STATUS_PENDING;
		this.type = null;
		
	}
	
	public EmailData( String sender_addresses, String receiver_addresses,String mailSubject, String mailContents, String type) {
		super();
		this.sender = null;
		this.receiver = null;
		this.sender_address = sender_addresses;
		this.receiver_address = receiver_addresses;
		this.mailSubject = mailSubject;
		this.mailContents = mailContents;
		this.created = new Date();
		this.status = EmailData.STATUS_PENDING;
		this.type = type;
		
	}
	
	public boolean equals(Object obj){		
		if( (this.getId()== null ) || (null == obj) || ( !(obj instanceof EmailData)  )){
			return false;
		}
		else{
			return this.getId().equals(((EmailData)obj).getId());
		}
	}
	
	public int hashCode(){
		final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
	}


	@Id
	@GeneratedValue
	private Long id;

	public String getSender_address() {
		return sender_address;
	}

	public void setSender_address(String sender_address) {
		this.sender_address = sender_address;
	}

	public String getReceiver_address() {
		return receiver_address;
	}

	public void setReceiver_address(String receiver_address) {
		this.receiver_address = receiver_address;
	}

	public String getMailSubject() {
		return mailSubject;
	}

	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}

	public String getMailContents() {
		return mailContents;
	}

	public void setMailContents(String mailContents) {
		this.mailContents = mailContents;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Employee getSender() {
		return sender;
	}

	public void setSender(Employee sender) {
		this.sender = sender;
	}

	public Employee getReceiver() {
		return receiver;
	}

	public void setReceiver(Employee receiver) {
		this.receiver = receiver;
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

	public Date getSentDate() {
		return sentDate;
	}

	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}

}
