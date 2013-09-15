package com.interaudit.util;

import java.util.*;
import javax.servlet.jsp.*;
import org.apache.log4j.*;
import org.apache.struts.taglib.logic.*;

/**
 * <p>Title: Pericles</p>
 * <p>Description: Check that at least one access right is set in a list of comma separated access rights.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Intrasoft International</p>
 * @author Jean-Pol Landrain.
 * @version $Id: AtLeastOneAccessRightSetTag.java,v 1.2 2004/06/17 08:44:03 bblocail Exp $
 *
 * <pre>
 * Changes
 * =======
 * Date            Name       Description
 *
 * </pre>
 */

public class AtLeastOneAccessRightSetTag extends ConditionalTagBase {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

protected static final Logger log = Logger.getLogger(AtLeastOneAccessRightSetTag.class);

  private String rightNames = null;

  public void setRights(String rightNames) {
    this.rightNames = rightNames;
  }

  public String getRights() {
    return rightNames;
  }

  protected boolean condition() throws javax.servlet.jsp.JspException {
    return condition(false);
  }

  protected boolean condition(boolean invertLogic) throws JspException {
    boolean result = false;
    if(getRights() != null && getRights().length() > 0) {
      AccessRightSetTag rigthChecker = new AccessRightSetTag();
      rigthChecker.setPageContext(pageContext);
      StringTokenizer tokenizer = new StringTokenizer(getRights(), ",");
      while (tokenizer.hasMoreTokens()) {
        String right = tokenizer.nextToken();
        rigthChecker.setRight(right);
        try {
          if(rigthChecker.condition() == true) {
            result = true;
            break;
          }
        } catch(JspException jspe) {}
      }
    }
    return (invertLogic ? !result : result);
  }
}
