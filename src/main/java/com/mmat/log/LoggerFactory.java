package com.mmat.log;

import org.slf4j.Logger;
import org.slf4j.bridge.SLF4JBridgeHandler;

public final class LoggerFactory {
  static {
    SLF4JBridgeHandler.removeHandlersForRootLogger();
    SLF4JBridgeHandler.install();
  }

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
