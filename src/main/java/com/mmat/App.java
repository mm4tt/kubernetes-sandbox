package com.mmat;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mmat.client.Client;
import com.mmat.client.ClientModule;
import com.mmat.log.LoggerFactory;
import org.slf4j.Logger;

import java.sql.SQLException;

/**
 * Entrypoint to the App.
 */
public class App {
  private static final Logger logger = LoggerFactory.getLogger();

  public static void main(String[] args) throws SQLException {

    Injector injector = Guice.createInjector(new ClientModule());

    Client client = injector.getInstance(Client.class);

    logger.info("Pulp value is {}", client.greet("Pulp"));
  }
}
