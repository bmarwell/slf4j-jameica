/*
 *  Copyright 2018 The slf4j-jameica contributors
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

import static com.jayway.awaitility.Awaitility.await;
import static com.jayway.awaitility.Duration.FIVE_SECONDS;
import static com.jayway.awaitility.Duration.ONE_SECOND;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.slf4j.LoggerFactory.getLogger;

import de.willuhn.jameica.system.JameicaException;
import de.willuhn.logging.Level;
import de.willuhn.logging.Logger;
import de.willuhn.logging.Message;
import de.willuhn.logging.targets.Target;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class JameicaLoggerTest {

  private static final org.slf4j.Logger LOG = getLogger(JameicaLoggerTest.class);

  private final ListTarget customTarget = new ListTarget();

  @BeforeClass
  public static void setUpLogger() {
    Logger.setLevel(Level.TRACE);
  }

  @Before
  public void setUp() throws Exception {
    try {
      Logger.removeTarget(customTarget);
    } catch (final Exception e) {
      // just cleaning up.
    }
    Logger.addTarget(customTarget);
  }

  @After
  public void tearDown() throws Exception {
    Logger.flush();
    try {
      Logger.removeTarget(customTarget);
    } catch (final Exception e) {
      // just cleaning up.
    }
  }

  @Test
  public void testSlf4jLogger_debug() throws InterruptedException {
    /* When */
    Logger.setLevel(Level.DEBUG);
    LOG.debug("DebugMessage");
    Logger.flush();

    await().atMost(FIVE_SECONDS).until(() -> customTarget.getNumberMessages() > 0);

    /* Then */
    assertThat(customTarget.getNumberMessages(), is(1));
    assertThat(customTarget.getNumberMessages(Level.DEBUG), is(1L));
    assertThat(customTarget.getNumberMessages(Level.WARN), is(0L));
  }

  @Test
  public void testSlf4jLogger_notDebug() throws InterruptedException {
    /* When */
    Logger.setLevel(Level.WARN);
    LOG.debug("DebugMessage");
    LOG.warn("WarnMessage");
    Logger.flush();

    await().atMost(ONE_SECOND).until(() -> customTarget.getNumberMessages() > 0);

    /* Then */
    assertThat(customTarget.getNumberMessages(), is(1));
    assertThat(customTarget.getNumberMessages(Level.DEBUG), is(0L));
    assertThat(customTarget.getNumberMessages(Level.WARN), is(1L));
  }

  @Test
  public void testSlf4jLogger_warn() throws InterruptedException {
    /* When */
    Logger.setLevel(Level.INFO);
    LOG.warn("WarnMessage");
    Logger.flush();

    await().atMost(ONE_SECOND).until(() -> customTarget.getNumberMessages() > 0);

    /* Then */
    assertThat(customTarget.getNumberMessages(), is(1));
    assertThat(customTarget.getNumberMessages(Level.DEBUG), is(0L));
    assertThat(customTarget.getNumberMessages(Level.WARN), is(1L));

    final Message message = customTarget.getMessages().stream().findFirst().orElseThrow(IllegalStateException::new);

    assertThat(message.getText(), is("WarnMessage"));
  }

  @Test
  public void testSlf4jLogger_warn_stacktrace() throws InterruptedException {
    /* When */
    Logger.setLevel(Level.INFO);
    LOG.warn("WarnMessage with text: [{}]", "replaceMe", new JameicaException("Intended"));
    Logger.flush();

    await().atMost(ONE_SECOND).until(() -> customTarget.getNumberMessages() > 0);

    /* Then */
    assertThat(customTarget.getNumberMessages(), is(1));
    assertThat(customTarget.getNumberMessages(Level.DEBUG), is(0L));
    assertThat(customTarget.getNumberMessages(Level.WARN), is(1L));

    final Message message = customTarget.getMessages().stream().findFirst().orElseThrow(IllegalStateException::new);

    assertThat(message.getText(), containsString("[replaceMe]"));
    assertThat(message.getLoggingClass(), is(this.getClass().getName()));
  }

  @Test
  public void testSlf4jLogger_error_stacktrace() throws InterruptedException {
    Logger.setLevel(Level.INFO);
    /* When */
    LOG.error("ErrorMessage with text: [{}]", "replaceMe", new JameicaException("Intended"));
    Logger.flush();

    await().atMost(FIVE_SECONDS).until(() -> customTarget.getNumberMessages() > 0);

    /* Then */
    assertThat(customTarget.getNumberMessages(), is(1));
    assertThat(customTarget.getNumberMessages(Level.DEBUG), is(0L));
    assertThat(customTarget.getNumberMessages(Level.ERROR), is(1L));

    final Message message = customTarget.getMessages().stream().findFirst().orElseThrow(IllegalStateException::new);

    assertThat(message.getText(), containsString("[replaceMe]"));
    assertThat(message.getLoggingClass(), is(this.getClass().getName()));
  }

  @Test
  public void testSlf4jLogger_error_replaceTwo() throws InterruptedException {
    Logger.setLevel(Level.INFO);
    /* When */
    LOG.error("ErrorMessage with text: [{}] => [{}]", "replaceMe", "MeeToo");
    Logger.flush();

    await().atMost(ONE_SECOND).until(() -> customTarget.getNumberMessages() > 0);

    /* Then */
    assertThat(customTarget.getNumberMessages(), is(1));
    assertThat(customTarget.getNumberMessages(Level.DEBUG), is(0L));
    assertThat(customTarget.getNumberMessages(Level.ERROR), is(1L));

    final Message message = customTarget.getMessages().stream().findFirst().orElseThrow(IllegalStateException::new);

    assertThat(message.getText(), containsString("[replaceMe] => [MeeToo]"));
    assertThat(message.getLoggingClass(), is(this.getClass().getName()));
  }

  @Test
  public void testSlf4jLogger_error_replaceMany() throws InterruptedException {
    Logger.setLevel(Level.INFO);
    /* When */
    LOG.error(
        "ErrorMessage with text: [{}]/[{}]/[{}]/[{}]",
        "1", "2", "3", "4");
    Logger.flush();

    await().atMost(ONE_SECOND).until(() -> customTarget.getNumberMessages() > 0);

    /* Then */
    assertThat(customTarget.getNumberMessages(), is(1));
    assertThat(customTarget.getNumberMessages(Level.DEBUG), is(0L));
    assertThat(customTarget.getNumberMessages(Level.ERROR), is(1L));

    final Message message = customTarget.getMessages().stream().findFirst().orElseThrow(IllegalStateException::new);

    assertThat(message.getText(), containsString("[1]/[2]/[3]/[4]"));
    assertThat(message.getLoggingClass(), is(this.getClass().getName()));
    assertThat(message.getLevel(), is(Level.ERROR));
  }

  @Test
  public void testLogLevelTrace() {
    Logger.setLevel(Level.TRACE);
    assertTrue(LOG.isDebugEnabled());
    assertTrue(LOG.isTraceEnabled());
    assertTrue(LOG.isInfoEnabled());
    assertTrue(LOG.isWarnEnabled());
    assertTrue(LOG.isErrorEnabled());
  }

  @Test
  public void testLogLevelInfo() {
    Logger.setLevel(Level.INFO);
    assertFalse(LOG.isTraceEnabled());
    assertFalse(LOG.isDebugEnabled());

    assertTrue(LOG.isInfoEnabled());
    assertTrue(LOG.isWarnEnabled());
    assertTrue(LOG.isErrorEnabled());
  }

  private static class ListTarget implements Target {

    private final List<Message> message = new ArrayList<>();
    boolean closed = false;

    @Override
    public void write(final Message message) throws Exception {
      if (closed) {
        throw new IllegalStateException("Closed");
      }

      System.out.println("Received message: " + message.toString());
      this.message.add(message);
    }

    @Override
    public void close() throws Exception {
      this.closed = true;
    }

    public List<Message> getMessages() {
      return Collections.unmodifiableList(message);
    }

    public int getNumberMessages() {
      return getMessages().size();
    }

    public long getNumberMessages(final Level loglevel) {
      return getMessages().stream()
          .filter(msg -> loglevel.equals(msg.getLevel()))
          .count();
    }

    public boolean isClosed() {
      return closed;
    }
  }
}
