package com.interaudit.util;

import java.beans.PropertyEditorSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.interaudit.domain.model.Mission;
import com.interaudit.service.IMissionService;

public class MissonEditor extends PropertyEditorSupport {
	private final IMissionService missionService;
	   protected final transient Log log = LogFactory.getLog(getClass());
	     public MissonEditor(IMissionService missionService) throws IllegalArgumentException {
	       this.missionService = missionService;
	   }
	   
	  /**
	   * Format a String to a User
	   */
	  @Override
	   public void setAsText(String text) throws IllegalArgumentException {
	             log.debug("entering UserEditor.setAsText function : " + text );
	       Long id = null;
	       Mission mission = null;
	             if (text != null && text.length() > 0) {
	           try {
	               id = Long.valueOf(text); // new Integer(text);
	           } catch (NumberFormatException nfe) {
	               throw new IllegalArgumentException("wrong argument: id="+text, nfe);
	           }
	           mission = missionService.getOneDetached(id);
	           /*
	           if (user == null) log.warn("user.getUser(" + text + ") retrn null value");
	           else log.debug("user has been find : " + user.toString());
	           */
	       }
	       this.setValue(mission);
	   }
	   
	  /**
	   * Format the Number as String
	   */
	   public String getAsText() {
	       return getValue().toString();
	   }

}
