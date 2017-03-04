package com.mmat;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mmat.log.LoggerFactory;
import com.mmat.server.ServerModule;
import com.mmat.storage.Storage;
import org.slf4j.Logger;

import java.sql.SQLException;

/**
 * Entrypoint to the App.
 */
public class App {
  private static final Logger logger = LoggerFactory.getLogger();

  public static void main(String[] args) throws SQLException {
    Injector injector = Guice.createInjector(new ServerModule());

    Storage storage = injector.getInstance(Storage.class);

    logger.info("Pulp value is {}", storage.getAndIncrement());
  }
}
