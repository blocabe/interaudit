package com.interaudit.util;

import javax.servlet.jsp.*;

/**
 * <p>Title: Pericles</p>
 * <p>Description: Tag that evaluates its body content if the detected browser is not Microsoft Internet Explorer.</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Intrasoft International</p>
 * @author Jean-Pol Landrain.
 * @version $Id: NotInternetExplorerBrowserTag.java,v 1.2 2004/06/17 08:47:25 bblocail Exp $
 *
 * <pre>
 * Changes
 * =======
 * Date            Name       Description
 *
 * </pre>
 */

public class NotInternetExplorerBrowserTag extends InternetExplorerBrowserTag {
  protected boolean condition() throws JspException {
    return condition(true);
  }
}
