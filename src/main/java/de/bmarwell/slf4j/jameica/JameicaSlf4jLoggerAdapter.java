/*
 *  Copyright 2018 The slf4j-willuhn contributors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package de.bmarwell.slf4j.jameica;

import de.willuhn.logging.Level;
import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MarkerIgnoringBase;
import org.slf4j.helpers.MessageFormatter;
import org.slf4j.spi.LocationAwareLogger;

public class JameicaSlf4jLoggerAdapter extends MarkerIgnoringBase implements Logger, LocationAwareLogger, Serializable {

  private static final String SELF = JameicaSlf4jLoggerAdapter.class.getName();
  private static final String SUPER = MarkerIgnoringBase.class.getName();

  /**
   * Implemented logger
   */
  private final de.willuhn.logging.Logger LOGGER = new de.willuhn.logging.Logger();

  @Override
  public String getName() {
    return LOGGER.getClass().getName();
  }

  @Override
  public boolean isTraceEnabled() {
    return Level.TRACE == de.willuhn.logging.Logger.getLevel();
  }

  @Override
  public void trace(final String msg) {
    if (!isTraceEnabled()) {
      return;
    }

    log(SELF, Level.TRACE, msg, null);
  }

  @Override
  public void trace(final String format, final Object arg) {
    if (!isTraceEnabled()) {
      return;
    }

    final FormattingTuple ft = MessageFormatter.format(format, arg);
    log(SELF, Level.TRACE, ft.getMessage(), ft.getThrowable());
  }

  @Override
  public void trace(final String format, final Object arg1, final Object arg2) {
    if (!isTraceEnabled()) {
      return;
    }

    final FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
    log(SELF, Level.TRACE, ft.getMessage(), ft.getThrowable());
  }

  @Override
  public void trace(final String format, final Object... arguments) {
    if (!isTraceEnabled()) {
      return;
    }

    final FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
    log(SELF, Level.TRACE, ft.getMessage(), ft.getThrowable());
  }

  @Override
  public void trace(final String msg, final Throwable t) {
    if (!isTraceEnabled()) {
      return;
    }

    log(SELF, Level.TRACE, msg, t);
  }

  @Override
  public boolean isDebugEnabled() {
    return isTraceEnabled()
        || Level.DEBUG == de.willuhn.logging.Logger.getLevel();
  }

  @Override
  public void debug(final String msg) {
    if (!isDebugEnabled()) {
      return;
    }

    log(SELF, Level.DEBUG, msg, null);
  }

  @Override
  public void debug(final String format, final Object arg) {
    if (!isDebugEnabled()) {
      return;
    }

    final FormattingTuple ft = MessageFormatter.format(format, arg);
    log(SELF, Level.DEBUG, ft.getMessage(), ft.getThrowable());
  }

  @Override
  public void debug(final String format, final Object arg1, final Object arg2) {
    if (!isDebugEnabled()) {
      return;
    }

    final FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
    log(SELF, Level.DEBUG, ft.getMessage(), ft.getThrowable());
  }

  @Override
  public void debug(final String format, final Object... arguments) {
    if (!isDebugEnabled()) {
      return;
    }

    final FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
    log(SELF, Level.DEBUG, ft.getMessage(), ft.getThrowable());
  }

  @Override
  public void debug(final String msg, final Throwable t) {
    if (!isDebugEnabled()) {
      return;
    }

    log(SELF, Level.DEBUG, msg, null);
  }

  @Override
  public boolean isInfoEnabled() {
    return isDebugEnabled()
        || Level.INFO == de.willuhn.logging.Logger.getLevel();
  }

  @Override
  public void info(final String msg) {
    if (!isInfoEnabled()) {
      return;
    }

    log(SELF, Level.INFO, msg, null);
  }

  @Override
  public void info(final String format, final Object arg) {
    if (!isInfoEnabled()) {
      return;
    }

    final FormattingTuple ft = MessageFormatter.format(format, arg);
    log(SELF, Level.INFO, ft.getMessage(), ft.getThrowable());
  }

  @Override
  public void info(final String format, final Object arg1, final Object arg2) {
    if (!isInfoEnabled()) {
      return;
    }

    final FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
    log(SELF, Level.INFO, ft.getMessage(), ft.getThrowable());
  }

  @Override
  public void info(final String format, final Object... arguments) {
    if (!isInfoEnabled()) {
      return;
    }

    final FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
    log(SELF, Level.INFO, ft.getMessage(), ft.getThrowable());
  }

  @Override
  public void info(final String msg, final Throwable t) {
    if (!isInfoEnabled()) {
      return;
    }

    log(SELF, Level.INFO, msg, t);
  }


  @Override
  public boolean isWarnEnabled() {
    return isInfoEnabled()
        || Level.WARN == de.willuhn.logging.Logger.getLevel();
  }

  @Override
  public void warn(final String msg) {
    if (!isWarnEnabled()) {
      return;
    }

    log(SELF, Level.WARN, msg, null);
  }

  @Override
  public void warn(final String format, final Object arg) {
    if (!isWarnEnabled()) {
      return;
    }

    final FormattingTuple ft = MessageFormatter.format(format, arg);
    log(SELF, Level.WARN, ft.getMessage(), ft.getThrowable());
  }

  @Override
  public void warn(final String format, final Object... arguments) {
    if (!isWarnEnabled()) {
      return;
    }

    final FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
    log(SELF, Level.WARN, ft.getMessage(), ft.getThrowable());
  }

  @Override
  public void warn(final String format, final Object arg1, final Object arg2) {
    if (!isWarnEnabled()) {
      return;
    }

    final FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
    log(SELF, Level.WARN, ft.getMessage(), ft.getThrowable());
  }

  @Override
  public void warn(final String msg, final Throwable t) {
    if (!isWarnEnabled()) {
      return;
    }

    log(SELF, Level.WARN, msg, t);
  }


  @Override
  public boolean isErrorEnabled() {
    return isWarnEnabled()
        || Level.ERROR == de.willuhn.logging.Logger.getLevel();
  }

  @Override
  public void error(final String msg) {
    if (!isErrorEnabled()) {
      return;
    }

    log(SELF, Level.ERROR, msg, null);
  }

  @Override
  public void error(final String format, final Object arg) {
    if (!isErrorEnabled()) {
      return;
    }

    final FormattingTuple ft = MessageFormatter.format(format, arg);
    log(SELF, Level.ERROR, ft.getMessage(), ft.getThrowable());
  }

  @Override
  public void error(final String format, final Object arg1, final Object arg2) {
    if (!isErrorEnabled()) {
      return;
    }

    final FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
    log(SELF, Level.ERROR, ft.getMessage(), ft.getThrowable());
  }

  @Override
  public void error(final String format, final Object... arguments) {
    if (!isErrorEnabled()) {
      return;
    }

    final FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
    log(SELF, Level.ERROR, ft.getMessage(), ft.getThrowable());
  }

  @Override
  public void error(final String msg, final Throwable t) {
    if (!isErrorEnabled()) {
      return;
    }

    log(SELF, Level.ERROR, msg, t);
  }

  private void log(
      final String callerFqcn,
      final Level level,
      final String msg,
      final Throwable throwable) {
    final StackTraceElement[] steArray = new Throwable().getStackTrace();
    int selfIndex = -1;
    for (int i = 0; i < steArray.length; i++) {
      final String className = steArray[i].getClassName();
      if (className.equals(callerFqcn) || className.equals(SUPER)) {
        selfIndex = i;
        break;
      }
    }

    int found = -1;
    for (int i = selfIndex + 1; i < steArray.length; i++) {
      final String className = steArray[i].getClassName();
      if (!(className.equals(callerFqcn) || className.equals(SUPER))) {
        found = i;
        break;
      }
    }

    String className = null;
    String methodName = null;

    if (found != -1) {
      final StackTraceElement ste = steArray[found];
      // setting the class name has the side effect of setting
      // the needToInferCaller variable to false.
      className = ste.getClassName();
      methodName = ste.getMethodName();
    }

    de.willuhn.logging.Logger.write(level, null, className, methodName, msg, throwable);
  }

  @Override
  public void log(
      final Marker marker,
      final String fqcn,
      final int level,
      final String message,
      final Object[] argArray,
      final Throwable t) {
    final Level willuhnLevel = toWilluhnLevel(level);
    log(fqcn, willuhnLevel, message, t);
  }

  private Level toWilluhnLevel(final int level) {
    switch (level) {
      case LocationAwareLogger.TRACE_INT:
        return Level.TRACE;
      case LocationAwareLogger.DEBUG_INT:
        return Level.DEBUG;
      case LocationAwareLogger.INFO_INT:
        return Level.INFO;
      case LocationAwareLogger.WARN_INT:
        return Level.WARN;
      case LocationAwareLogger.ERROR_INT:
        return Level.ERROR;
      default:
        throw new IllegalStateException("Level number [" + level + "] is not recognized.");
    }
  }
}
