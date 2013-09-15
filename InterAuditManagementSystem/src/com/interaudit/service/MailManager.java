package com.interaudit.service;

import org.springframework.mail.MailException;

import com.interaudit.domain.model.EmailData;

public interface MailManager {
	/*
	void send(String senderName, String senderAddress, Map<String, String> to, Map<String, String> cc, Map<String, String> bcc,String subject, Map<String, Object> mergeObjects, String template,
            String textTemplateSuffix) throws MailException;
            */
 
	//void send(Map to, List objects, String template) throws MailException;
	
	boolean send(EmailData simpleMessage) throws MailException;
	      
	void send(EmailData[] simpleMessages) throws MailException;

}
