package com.interaudit.util;


import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.apache.struts.taglib.logic.*;

/**
 * <p>Title: Pericles</p>
 * <p>Description: Tag that evaluates its body content if the detected browser is Microsoft Internet Explorer.</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Intrasoft International</p>
 * @author Jean-Pol Landrain.
 * @version $Id: InternetExplorerBrowserTag.java,v 1.2 2004/06/17 08:44:34 bblocail Exp $
 *
 * <pre>
 * Changes
 * =======
 * Date            Name       Description
 *
 * </pre>
 */

public class InternetExplorerBrowserTag extends ConditionalTagBase {
  protected boolean condition() throws JspException {
     return condition(false);
  }

  protected boolean condition(boolean invertLogic) throws JspException {
    boolean result = false;
    DetectBrowser detectBrowser = new DetectBrowser();
    detectBrowser.setRequest((HttpServletRequest)pageContext.getRequest());
    if(detectBrowser.isIE()) {
      result = true;
    }
    return (invertLogic ? !result : result);
  }
}
