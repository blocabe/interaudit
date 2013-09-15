package com.interaudit.service.exc;

public class SystemException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SystemException() { }

    public SystemException(Throwable t) {
        super(t);
    }
    
    public SystemException(String message) {
        super(message);
    }
}
