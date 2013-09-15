package com.interaudit.util;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import org.apache.log4j.Logger;
import org.apache.struts.taglib.logic.ConditionalTagBase;

/**
 * <p>Title: Pericles</p>
 * <p>Description: Tag to check the user is logged in and has a valid session in the FMS application</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Intrasoft International</p>
 * @author Jean-Pol Landrain.
 * @version $Id: LoggedInTag.java,v 1.5 2004/06/17 08:47:02 bblocail Exp $
 *
 * <pre>
 * Changes
 * =======
 * Date            Name       Description
 *
 * </pre>
 */

public class LoggedInTag extends ConditionalTagBase {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger  logger      = Logger.getLogger(LoggedInTag.class);


protected boolean condition() throws JspException {
    return condition(false);
  }

  protected boolean condition(boolean invertLogic) throws JspException {
    boolean result = isLoggedIn((HttpServletRequest)pageContext.getRequest());
    return (invertLogic ? !result : result);
  }
  
 
  public static boolean isLoggedIn( HttpServletRequest request ){
	  	logger.info("LoggedInTag.isLoggedIn : Entrance at : " + new Date());
	    if ( request.getSession(false) == null ||
	         (request.getSession().getAttribute( "context" ) == null)){
	    	logger.info("LoggedInTag.isLoggedIn : Return false at : " + new Date());
	      return false;
	    } else{
	    	logger.info("LoggedInTag.isLoggedIn : Return true at : " + new Date());
	      return true;
	    }
	  }
}
