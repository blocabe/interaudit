package com.interaudit.util;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

/**
 * <p>Title: Pericles</p>
 * <p>Description: Tag to inform the page should not be cached in the browser</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Intrasoft International</p>
 * @author Jean-Pol Landrain.
 * @version $Id: NoCacheTag.java,v 1.2 2004/06/17 08:47:13 bblocail Exp $
 *
 * <pre>
 * Changes
 * =======
 * Date            Name       Description
 *
 * </pre>
 */

public class NoCacheTag extends TagSupport {
  public int doStartTag() throws JspException {
    HttpServletResponse response = (HttpServletResponse)pageContext.getResponse();
    response.addHeader("Pragma", "No-Cache");
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.addHeader("Cache-Control", "pre-check=0, post-check=0");
    response.setDateHeader("Expires", 0);
    return EVAL_PAGE;
  }
}
