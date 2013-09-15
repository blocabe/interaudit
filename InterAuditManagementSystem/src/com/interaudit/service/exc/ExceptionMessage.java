package com.interaudit.service.exc;

public class ExceptionMessage extends RuntimeException{
    private String message;
    private Object[] params;
    /**
     * A flag indicating whether this message is just a warning.
     */
    private boolean warning;

    public ExceptionMessage(String message) {
        this.message = message;
    }

    public ExceptionMessage(String message, Object[] params) {
        this.message = message;
        this.params = params;
        this.warning = false;
    }
    public ExceptionMessage(String message, boolean warning, Object[] params) {
        this.message = message;
        this.params = params;
        this.warning = warning;
    }
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    public boolean isWarning() {
        return warning;
    }

    public void setWarning(boolean warning) {
        this.warning = warning;
    }
}
