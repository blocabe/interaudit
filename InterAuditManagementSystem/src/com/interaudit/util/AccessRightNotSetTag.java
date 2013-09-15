package com.interaudit.util;

import javax.servlet.jsp.*;

/**
 * <p>Title: Pericles</p>
 * <p>Description: Check that a user has a specified access right in the not set state.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Intrasoft International</p>
 * @author Jean-Pol Landrain.
 * @version $Id: AccessRightNotSetTag.java,v 1.2 2004/06/17 08:43:01 bblocail Exp $
 *
 * <pre>
 * Changes
 * =======
 * Date            Name       Description
 *
 * </pre>
 */

public class AccessRightNotSetTag extends AccessRightSetTag {

  protected boolean condition() throws JspException {
    return condition(true);
  }

}
