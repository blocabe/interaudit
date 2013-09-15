package com.interaudit.util;

import java.beans.PropertyEditorSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.interaudit.domain.model.Employee;
import com.interaudit.service.IMissionService;
import com.interaudit.service.IUserService;

public class EmployeeEditor extends PropertyEditorSupport {
	

	private final IUserService userService;
	     protected final transient Log log = LogFactory.getLog(getClass());
	     public EmployeeEditor(IUserService userService) throws IllegalArgumentException {
	       this.userService = userService;
	   }
	     
	    
	   
	  /**
	   * Format a String to a User
	   */
	  @Override
	   public void setAsText(String text) throws IllegalArgumentException {
	             log.debug("entering UserEditor.setAsText function : " + text );
	       Long id = null;
	       Employee currentUser  = null;
	             if (text != null && text.length() > 0) {
	           try {
	               id = Long.valueOf(text); // new Integer(text);
	           } catch (NumberFormatException nfe) {
	               throw new IllegalArgumentException("wrong argument: id="+text, nfe);
	           }
	           currentUser = userService.getOneDetached(id);
	           /*
	           if (user == null) log.warn("user.getUser(" + text + ") retrn null value");
	           else log.debug("user has been find : " + user.toString());
	           */
	       }
	       this.setValue(currentUser);
	   }
	   
	  /**
	   * Format the Number as String
	   */
	   public String getAsText() {
	       return getValue().toString();
	   }

}
