package com.interaudit.util;

import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>Title: Pericles</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Intrasoft International</p>
 * @author Jean-Pol Landrain.
 * @version $Id: DetectBrowser.java,v 1.2 2004/06/17 08:47:57 bblocail Exp $
 */

public final class DetectBrowser implements Serializable {

  private HttpServletRequest request = null;
  private String useragent = null;
  private boolean netEnabled = false;
  private boolean ie = false;
  private boolean ns6 = false;
  private boolean ns4 = false;

  public void setRequest(HttpServletRequest request) {
    this.request = request;
    useragent = request.getHeader("User-Agent");
    String user = useragent.toLowerCase();
    if(user.indexOf("msie") != -1) {
      ie = true;
    } else if(user.indexOf("netscape6") != -1) {
      ns6 = true;
    } else if(user.indexOf("mozilla") != -1) {
      ns4 = true;
    }

    if(user.indexOf(".net clr") != -1)
      netEnabled = true;
  }

  public String getUseragent() {
    return useragent;
  }

  public boolean isNetEnabled() {
    return netEnabled;
  }

  public boolean isIE() {
    return ie;
  }

  public boolean isNS6() {
    return ns6;
  }

  public boolean isNS4() {
    return ns4;
  }
}
