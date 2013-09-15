package com.interaudit.service.exc;

import java.util.ArrayList;
import java.util.List;

public class ExcessiveAmountException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ExcessiveAmountException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ExcessiveAmountException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ExcessiveAmountException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	private List<ExceptionMessage> excpetionMessages;

    public ExcessiveAmountException(String errorKey, Object[] params) {
        this.excpetionMessages = new ArrayList<ExceptionMessage>();
        this.excpetionMessages.add(new ExceptionMessage(errorKey, params));
    }

    public ExcessiveAmountException(String errorKey) {
        this.excpetionMessages = new ArrayList<ExceptionMessage>();
        this.excpetionMessages.add(new ExceptionMessage(errorKey));
    }
    
    public ExcessiveAmountException(ExceptionMessage exceptionMessage) {
        this.excpetionMessages = new ArrayList<ExceptionMessage>();
        this.excpetionMessages.add(exceptionMessage);
    }
    public ExcessiveAmountException(List<ExceptionMessage> exceptionMessages) {
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
