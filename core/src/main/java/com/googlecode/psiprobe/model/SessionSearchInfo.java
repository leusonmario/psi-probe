/*
 * Licensed under the GPL License. You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING,
 * WITHOUT LIMITATION, THE IMPLIED WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE.
 */

package com.googlecode.psiprobe.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Data model class used by session search feature of application session screen.
 * 
 * @author Andy Shapoval
 * @author Vlad Ilyushchenko
 */
public class SessionSearchInfo implements Serializable {

  public static final String SESS_ATTR_NAME = "sessionSearchInfo";
  public static final String ACTION_NONE = "none";
  public static final String ACTION_APPLY = "apply";
  public static final String ACTION_CLEAR = "clear";

  private String searchAction = ACTION_NONE;
  private boolean apply = false;
  private boolean clear = false;
  private String sessionId;
  private Pattern sessionIdPattern;
  private String sessionIdMsg;
  private String attrName;
  private List<Pattern> attrNamePatterns = new ArrayList<Pattern>();
  private List<String> attrNameMsgs = new ArrayList<String>();
  private String ageFrom;
  private Integer ageFromSec;
  private String ageTo;
  private Integer ageToSec;
  private String idleTimeFrom;
  private Integer idleTimeFromSec;
  private String idleTimeTo;
  private Integer idleTimeToSec;
  private String lastIp;
  private String infoMessage;
  private List<String> errorMessages = new ArrayList<String>();

  public boolean isEmpty() {
    return sessionId == null && attrName == null && ageFrom == null && ageTo == null
        && idleTimeFrom == null && idleTimeTo == null && lastIp == null;
  }

  public boolean isSessionIdValid() {
    return sessionId == null || sessionIdPattern != null;
  }

  public boolean isAttrNameValid() {
    return attrName == null || !attrNamePatterns.isEmpty();
  }

  public boolean isAgeFromValid() {
    return ageFrom == null || ageFromSec != null;
  }

  public boolean isAgeToValid() {
    return ageTo == null || ageToSec != null;
  }

  public boolean isIdleTimeFromValid() {
    return idleTimeFrom == null || idleTimeFromSec != null;
  }

  public boolean isIdleTimeToValid() {
    return idleTimeTo == null || idleTimeToSec != null;
  }

  public boolean isValid() {
    return isSessionIdValid() && isAttrNameValid() && isAgeFromValid() && isAgeToValid()
        && isIdleTimeFromValid() && isIdleTimeToValid();
  }

  public boolean isUseSearch() {
    return isApply() && !isEmpty() && isValid();
  }

  public boolean isUseSessionId() {
    return sessionIdPattern != null;
  }

  public boolean isUseAttrName() {
    return !attrNamePatterns.isEmpty();
  }

  public boolean isUseAttr() {
    return isUseSearch() && isUseAttrName();
  }

  public boolean isUseAgeFrom() {
    return ageFromSec != null;
  }

  public boolean isUseAgeTo() {
    return ageToSec != null;
  }

  public boolean isUseIdleTimeFrom() {
    return idleTimeFromSec != null;
  }

  public boolean isUseIdleTimeTo() {
    return idleTimeToSec != null;
  }

  public boolean isUseLastIp() {
    return lastIp != null;
  }

  public String getSearchAction() {
    return searchAction;
  }

  public void setSearchAction(String searchAction) {
    this.searchAction = searchAction;
    if (searchAction == null) {
      apply = false;
      clear = false;
    } else {
      apply = searchAction.equals(ACTION_APPLY);
      clear = searchAction.equals(ACTION_CLEAR);
    }
  }

  public boolean isApply() {
    return apply;
  }

  public boolean isClear() {
    return clear;
  }

  public String getSessionId() {
    return sessionId;
  }

  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
    sessionIdPattern = null;
    sessionIdMsg = null;

    if (sessionId != null) {
      try {
        sessionIdPattern = Pattern.compile(sessionId);
      } catch (PatternSyntaxException e) {
        sessionIdMsg = e.getDescription();
      }
    }
  }

  public Pattern getSessionIdPattern() {
    return sessionIdPattern;
  }

  public String getSessionIdMsg() {
    return sessionIdMsg;
  }

  public String getAttrName() {
    return attrName;
  }

  public void setAttrName(String attrName) {
    this.attrName = attrName;
    attrNamePatterns.clear();
    attrNameMsgs.clear();

    if (attrName != null) {
      String[] attrNames = attrName.split(",");
      if (attrNames.length == 0 && !attrName.equals("")) {
        attrNameMsgs.add("");
      } else {
        for (String regex : attrNames) {
          try {
            attrNamePatterns.add(Pattern.compile(regex));
          } catch (PatternSyntaxException e) {
            attrNameMsgs.add(e.getDescription());
          }
        }
      }
    }
  }

  public List<Pattern> getAttrNamePatterns() {
    return attrNamePatterns;
  }

  public List<String> getAttrNameMsgs() {
    return attrNameMsgs;
  }

  public String getAgeFrom() {
    return ageFrom;
  }

  public void setAgeFrom(String ageFrom) {
    this.ageFrom = ageFrom;
    ageFromSec = null;

    if (ageFrom != null) {
      try {
        ageFromSec = Integer.valueOf(ageFrom);
      } catch (NumberFormatException e) {
        // ignore
      }
    }
  }

  public Integer getAgeFromSec() {
    return ageFromSec;
  }

  public String getAgeTo() {
    return ageTo;
  }

  public void setAgeTo(String ageTo) {
    this.ageTo = ageTo;
    ageToSec = null;

    if (ageTo != null) {
      try {
        ageToSec = Integer.valueOf(ageTo);
      } catch (NumberFormatException e) {
        // ignore
      }
    }
  }

  public Integer getAgeToSec() {
    return ageToSec;
  }

  public String getIdleTimeFrom() {
    return idleTimeFrom;
  }

  public void setIdleTimeFrom(String idleTimeFrom) {
    this.idleTimeFrom = idleTimeFrom;
    idleTimeFromSec = null;

    if (idleTimeFrom != null) {
      try {
        idleTimeFromSec = Integer.valueOf(idleTimeFrom);
      } catch (NumberFormatException e) {
        // ignore
      }
    }
  }

  public Integer getIdleTimeFromSec() {
    return idleTimeFromSec;
  }

  public String getIdleTimeTo() {
    return idleTimeTo;
  }

  public void setIdleTimeTo(String idleTimeTo) {
    this.idleTimeTo = idleTimeTo;
    idleTimeToSec = null;

    if (idleTimeTo != null) {
      try {
        idleTimeToSec = Integer.valueOf(idleTimeTo);
      } catch (NumberFormatException e) {
        // ignore
      }
    }
  }

  public Integer getIdleTimeToSec() {
    return idleTimeToSec;
  }

  public String getLastIp() {
    return lastIp;
  }

  public void setLastIp(String lastIp) {
    this.lastIp = lastIp;
  }

  public String getInfoMessage() {
    return infoMessage;
  }

  public void setInfoMessage(String infoMessage) {
    this.infoMessage = infoMessage;
  }

  public List<String> getErrorMessages() {
    return errorMessages;
  }

  public void addErrorMessage(String msg) {
    errorMessages.add(msg);
  }

}
