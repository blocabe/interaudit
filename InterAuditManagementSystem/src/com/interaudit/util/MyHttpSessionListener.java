package com.interaudit.util;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

public class MyHttpSessionListener implements HttpSessionListener{
	private static final Logger  logger  = Logger.getLogger(MyHttpSessionListener.class);
	/** Cette m�thode est appel�e lors de la cr�ation de
	session */
	public void sessionCreated(HttpSessionEvent hse){
		logger.info("Une session vient d'�tre cr��e");
	}
	/** Cette m�thode est appel�e lors de la destruction
	de session*/
	public void sessionDestroyed(HttpSessionEvent hse) {
		logger.info("Une session vient d'�tre d�truite");
	}
}
