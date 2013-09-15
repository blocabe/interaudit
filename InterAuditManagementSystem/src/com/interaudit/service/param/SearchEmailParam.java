package com.interaudit.service.param;

import java.util.Date;




public class SearchEmailParam {
	
	
	private String sender_address;
	private String receiver_address;
	private String mailSubject;
	private String mailContents;
	private Date created;
	private String status;	
	private String type;	
	private Long sender;
	private Long receiver;
	
	
	public SearchEmailParam() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public SearchEmailParam(
			String sender_address,
			String receiver_address,
			String mailSubject,
			String mailContents,
			Date created,
			String status,
			String type,	
			Long sender,
			Long receiver) {
		super();
		this.sender_address = sender_address;
		this.receiver_address =receiver_address;
		this.mailSubject = mailSubject;
		this.mailContents = mailContents;
		this.created = created;
		this.status = status;
		this.type = type;	
		this.sender = sender;
		this.receiver  = receiver;
	}

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getSender() {
		return sender;
	}

	public void setSender(Long sender) {
		this.sender = sender;
	}

	public Long getReceiver() {
		return receiver;
	}

	public void setReceiver(Long receiver) {
		this.receiver = receiver;
	}

	
	
	


	
	
	
	
	
	
	
	
	

}
