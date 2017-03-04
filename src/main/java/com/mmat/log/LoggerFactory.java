package com.mmat.log;

import org.slf4j.Logger;

public class LoggerFactory {
  public static Logger getLogger() {
    return org.slf4j.LoggerFactory.getLogger(getCallerClassName());
  }

  private static String getCallerClassName() {
    StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
    for (int i=1; i<stElements.length; i++) {
      StackTraceElement ste = stElements[i];
      if (!ste.getClassName().equals(LoggerFactory.class.getName())
          && ste.getClassName().indexOf("java.lang.Thread")!=0) {
        return ste.getClassName();
      }
    }
    return null;
  }
}
