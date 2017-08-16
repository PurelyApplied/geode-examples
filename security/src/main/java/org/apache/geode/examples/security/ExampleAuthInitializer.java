/*
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The ASF licenses this file to You under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License. You may obtain a
 * copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.apache.geode.examples.security;

import java.util.Properties;

import org.apache.logging.log4j.Logger;

import org.apache.geode.LogWriter;
import org.apache.geode.distributed.DistributedMember;
import org.apache.geode.internal.logging.LogService;
import org.apache.geode.security.AuthInitialize;
import org.apache.geode.security.AuthenticationFailedException;

public class ExampleAuthInitializer implements AuthInitialize {

  private static final Logger logger = LogService.getLogger();

  private static final String USER_NAME = "security-username";
  private static final String PASSWORD = "security-password";

  @Override
  public void init(LogWriter systemLogger, LogWriter securityLogger)
      throws AuthenticationFailedException {}

  @Override
  public Properties getCredentials(Properties securityProps, DistributedMember server,
      boolean isPeer) throws AuthenticationFailedException {
    Properties credentials = new Properties();
    String userName = securityProps.getProperty(USER_NAME);
    if (userName == null) {
      throw new AuthenticationFailedException(
          "SampleAuthInit: user name property [" + USER_NAME + "] not set.");
    }
    credentials.setProperty(USER_NAME, userName);
    String password = securityProps.getProperty(PASSWORD);
    if (password == null) {
      throw new AuthenticationFailedException(
          "SampleAuthInit: password property [" + PASSWORD + "] not set.");
    }
    credentials.setProperty(PASSWORD, password);
    logger.info("SampleAuthInit: successfully obtained credentials for user " + userName);
    return credentials;
  }

  @Override
  public void close() {}
}
