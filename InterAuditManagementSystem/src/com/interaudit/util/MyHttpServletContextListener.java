package com.interaudit.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;



public class MyHttpServletContextListener implements ServletContextListener{
	private static final Logger  logger      = Logger.getLogger(MyHttpServletContextListener.class);
	/** Cette méthode appelée lors du démarrage de
	l'application*/
	public void contextInitialized(ServletContextEvent sce) {
		logger.info("L'application vient de démarrer");
	}
	
	/** Cette méthode appelée lors de l'arret de
	l'application*/
	public void contextDestroyed(ServletContextEvent sce) {
		logger.info("L'application vient de s'arreter");
	}
}
