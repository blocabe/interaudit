package com.interaudit.util;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

public class MyHttpSessionListener implements HttpSessionListener{
	private static final Logger  logger  = Logger.getLogger(MyHttpSessionListener.class);
	/** Cette méthode est appelée lors de la création de
	session */
	public void sessionCreated(HttpSessionEvent hse){
		logger.info("Une session vient d'être créée");
	}
	/** Cette méthode est appelée lors de la destruction
	de session*/
	public void sessionDestroyed(HttpSessionEvent hse) {
		logger.info("Une session vient d'être détruite");
	}
}
