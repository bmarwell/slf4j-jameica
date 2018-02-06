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

import org.slf4j.ILoggerFactory;
import org.slf4j.IMarkerFactory;
import org.slf4j.helpers.BasicMarkerFactory;
import org.slf4j.spi.MDCAdapter;
import org.slf4j.spi.SLF4JServiceProvider;

public class JameicaLoggerBinder implements SLF4JServiceProvider {

  public static String REQUESTED_API_VERSION = "1.8.0"; // !final

  private IMarkerFactory markerFactory;

  @Override
  public void initialize() {
    this.markerFactory = new BasicMarkerFactory();
  }

  @Override
  public ILoggerFactory getLoggerFactory() {
    return new JameicaSlf4jLoggerFactory();
  }

  @Override
  public IMarkerFactory getMarkerFactory() {
    return this.markerFactory;
  }

  @Override
  public MDCAdapter getMDCAdapter() {
    return null;
  }

  @Override
  public String getRequesteApiVersion() {
    return REQUESTED_API_VERSION;
  }

}
