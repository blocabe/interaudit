package com.interaudit.service.exc;

import java.util.ArrayList;
import java.util.List;

public class BusinessException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<ExceptionMessage> excpetionMessages;

    public BusinessException(String errorKey, Object[] params) {
        this.excpetionMessages = new ArrayList<ExceptionMessage>();
        this.excpetionMessages.add(new ExceptionMessage(errorKey, params));
    }

    public BusinessException(String errorKey) {
        this.excpetionMessages = new ArrayList<ExceptionMessage>();
        this.excpetionMessages.add(new ExceptionMessage(errorKey));
    }
    
    public BusinessException(ExceptionMessage exceptionMessage) {
        this.excpetionMessages = new ArrayList<ExceptionMessage>();
        this.excpetionMessages.add(exceptionMessage);
    }
    public BusinessException(List<ExceptionMessage> exceptionMessages) {
        this.excpetionMessages = new ArrayList<ExceptionMessage>();
        this.excpetionMessages.addAll(exceptionMessages);
    }

    public List<ExceptionMessage> getExcpetionMessages() {
        return excpetionMessages;
    }

    public void setExcpetionMessages(List<ExceptionMessage> excpetionMessages) {
        this.excpetionMessages = excpetionMessages;
    }
}
