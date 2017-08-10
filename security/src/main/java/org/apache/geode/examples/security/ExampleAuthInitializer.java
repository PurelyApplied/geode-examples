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
      throws AuthenticationFailedException {  }

  @Override
  public Properties getCredentials(Properties securityProps,
                                   DistributedMember server, boolean isPeer)
      throws AuthenticationFailedException {
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
    logger.info("SampleAuthInit: successfully obtained credentials for user "
        + userName);
    return credentials;
  }

  @Override
  public void close() {  }
}
