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

package com.googlecode.psiprobe.controllers;

import com.googlecode.psiprobe.UptimeListener;
import com.googlecode.psiprobe.Utils;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author Vlad Ilyushchenko
 */
public class DecoratorController extends ParameterizableViewController {

  private String messagesBasename;

  public String getMessagesBasename() {
    return messagesBasename;
  }

  public void setMessagesBasename(String messagesBasename) {
    this.messagesBasename = messagesBasename;
  }

  @Override
  protected ModelAndView handleRequestInternal(HttpServletRequest request,
      HttpServletResponse response) throws Exception {

    try {
      request.setAttribute("hostname", InetAddress.getLocalHost().getHostName());
    } catch (UnknownHostException e) {
      request.setAttribute("hostname", "unknown");
    }

    Properties version = (Properties) getApplicationContext().getBean("version");
    request.setAttribute("version", version.getProperty("probe.version"));

    Object uptimeStart = getServletContext().getAttribute(UptimeListener.START_TIME_KEY);
    if (uptimeStart != null && uptimeStart instanceof Long) {
      long uptimeStartValue = ((Long) uptimeStart);
      long uptime = System.currentTimeMillis() - uptimeStartValue;
      long uptimeDays = uptime / (1000 * 60 * 60 * 24);

      uptime = uptime % (1000 * 60 * 60 * 24);
      long uptimeHours = uptime / (1000 * 60 * 60);

      uptime = uptime % (1000 * 60 * 60);
      long uptimeMins = uptime / (1000 * 60);

      request.setAttribute("uptime_days", uptimeDays);
      request.setAttribute("uptime_hours", uptimeHours);
      request.setAttribute("uptime_mins", uptimeMins);
    }

    //
    // Work out the language of the interface by matching resource files that we have
    // to the request locale.
    //
    String lang = "en";
    for (String fileName : getMessageFileNamesForLocale(request.getLocale())) {
      if (getServletContext().getResource(fileName + ".properties") != null) {
        lang = fileName.substring(messagesBasename.length() + 1);
        break;
      }
    }

    request.setAttribute("lang", lang);

    return super.handleRequestInternal(request, response);
  }

  private List<String> getMessageFileNamesForLocale(Locale locale) {
    return Utils.getNamesForLocale(messagesBasename, locale);
  }
}
