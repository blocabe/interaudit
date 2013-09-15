package com.interaudit.util;

import javax.servlet.jsp.tagext.*;

/**
 * <p>Title: Pericles</p>
 * <p>Description: Info class for the EvalTag, allowing to store the result of the tag in the page context.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Intrasoft International</p>
 * @author Jean-Pol Landrain.
 * @version $Id: EvalTagExtraInfo.java,v 1.4 2004/06/17 08:44:25 bblocail Exp $
 *
 * <pre>
 * Changes
 * =======
 * Date            Name       Description
 *
 * </pre>
 */

public class EvalTagExtraInfo extends TagExtraInfo {
  public VariableInfo[] getVariableInfo(TagData data) {
    return new VariableInfo[] {
      new VariableInfo(data.getAttributeString("id"), String.class.getName(), true, VariableInfo.AT_END)
    };
  }
}
