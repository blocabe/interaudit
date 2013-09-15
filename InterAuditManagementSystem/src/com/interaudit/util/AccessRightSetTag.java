package com.interaudit.util;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import org.apache.log4j.Logger;
import org.apache.struts.taglib.logic.ConditionalTagBase;

import com.interaudit.domain.model.Employee;

/**
 * <p>Title: Pericles</p>
 * <p>Description: Check that a user has a specified access right in the set state.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Intrasoft International</p>
 * @author Jean-Pol Landrain.
 * @version $Id: AccessRightSetTag.java,v 1.2 2004/06/17 08:43:12 bblocail Exp $
 *
 * <pre>
 * Changes
 * =======
 * Date            Name       Description
 *
 * </pre>
 */

public class AccessRightSetTag extends ConditionalTagBase {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

  protected static final Logger log = Logger.getLogger(AccessRightSetTag.class);

  private String rightName = null;

  public void setRight(String rightName) {
    this.rightName = rightName;
  }

  public String getRight() {
    return rightName;
  }

  protected boolean condition() throws JspException {
    return condition(false);
  }
  
  public static boolean isLoggedIn( HttpServletRequest request ){
	    if ( request.getSession(false) == null ||
	         (request.getSession().getAttribute( "context" ) == null)){
	      return false;
	    } else{
	      return true;
	    }
	  }

  @SuppressWarnings("unchecked")
protected boolean condition(boolean invertLogic) throws JspException {
    // Always return false if not logged in
    boolean loggedIn = isLoggedIn((HttpServletRequest)pageContext.getRequest());
    if(loggedIn == false)
      return false;

    // Get the right index
    // Always return false if right does not exist
    HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
    WebContext context = (WebContext) request.getSession(false).getAttribute("context");
    if(context == null) {
      log.warn("context is null");
      return false;
    }
    Employee userInfo = context.getCurrentUser();
    if(userInfo == null) {
      log.warn("UserInformation is null");
      return false;
    }
   // WebApplicationContext ctx = org.springframework.web.context.support.WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
    //ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
	// couche service
   // IUserService service = (IUserService) ctx.getBean("userService");
    
    Map<String, Boolean> globalAccessRights  = ( Map<String, Boolean>)request.getSession(false).getServletContext().getAttribute("globalAccessRights");
    if( globalAccessRights == null ){
		return false;
	}else{
		String key = getRight() +"##" + userInfo.getId();
		Boolean ret =  globalAccessRights.get(key);
		
		boolean result = (ret == null? false: ret.booleanValue());
		
		return (invertLogic ? !result : result);
	}
    
    

    // Get the right value
    /*service.getUserHasAccessRight(userInfo.getId(),getRight());**/
    
  }
}
