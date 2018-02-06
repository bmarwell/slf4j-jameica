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
import static com.jayway.awaitility.Duration.ONE_SECOND;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

import de.willuhn.logging.Level;
import de.willuhn.logging.Logger;
import de.willuhn.logging.Message;
import de.willuhn.logging.targets.Target;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class JameicaLoggerTest {

  private static final org.slf4j.Logger LOG = getLogger(JameicaLoggerTest.class);

  private ListTarget customTarget;

  @Before
  public void setUp() throws Exception {
    this.customTarget = new ListTarget();
    Logger.setLevel(Level.TRACE);
    Logger.addTarget(customTarget);
  }

  @Test
  public void testSlf4jLogger_debug() throws InterruptedException {
    /* When */
    LOG.debug("DebugMessage");
    Logger.flush();

    await().atMost(ONE_SECOND).until(() -> customTarget.getNumberMessages() > 0);

    /* Then */
    assertThat(customTarget.getNumberMessages(), is(1));
    assertThat(customTarget.getNumberMessages(Level.DEBUG), is(1L));
    assertThat(customTarget.getNumberMessages(Level.WARN), is(0L));
  }

  @Test
  public void testSlf4jLogger_warn() throws InterruptedException {
    /* When */
    LOG.warn("DebugMessage");
    Logger.flush();

    await().atMost(ONE_SECOND).until(() -> customTarget.getNumberMessages() > 0);

    /* Then */
    assertThat(customTarget.getNumberMessages(), is(1));
    assertThat(customTarget.getNumberMessages(Level.DEBUG), is(0L));
    assertThat(customTarget.getNumberMessages(Level.WARN), is(1L));
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
