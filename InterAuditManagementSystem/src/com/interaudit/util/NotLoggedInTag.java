package com.interaudit.util;

import javax.servlet.jsp.*;

/**
 * <p>Title: Pericles</p>
 * <p>Description: Tag to check the user isn't logged in and hasn't a valid session in the FMS application</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Intrasoft International</p>
 * @author Jean-Pol Landrain.
 * @version $Id: NotLoggedInTag.java,v 1.5 2004/06/17 08:47:36 bblocail Exp $
 *
 * <pre>
 * Changes
 * =======
 * Date            Name       Description
 *
 * </pre>
 */

public class NotLoggedInTag extends LoggedInTag {

  protected boolean condition() throws JspException {
    return condition(true);
  }

}
