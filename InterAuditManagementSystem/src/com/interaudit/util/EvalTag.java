package com.interaudit.util;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

/**
 * <p>Title: Interaudit</p>
 * <p>Description: Tag that output its body content as a String object and stores it into the pageContext with the specified id as key.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Intrasoft International</p>
 * @author Jean-Pol Landrain.
 * @version $Id: EvalTag.java,v 1.5 2004/06/17 08:44:13 bblocail Exp $
 *
 * <pre>
 * Changes
 * =======
 * Date            Name       Description
 *
 * </pre>
 */

public class EvalTag extends BodyTagSupport {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private String id = null;

  public void setId(String id) {
    this.id = id;
  }

  public int doAfterBody() throws JspException {
    BodyContent bc = getBodyContent();
    String bodyContent = null;
    if(bc != null) {
      bodyContent = bc.getString();
    }
    pageContext.setAttribute(id, bodyContent, PageContext.PAGE_SCOPE);
    return SKIP_BODY;
  }
}
