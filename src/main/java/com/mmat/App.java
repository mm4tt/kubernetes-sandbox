package com.mmat;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.mmat.server.ServerModule;
import com.mmat.storage.Storage;

import java.sql.SQLException;

/**
 * Entrypoint to the App.
 */
public class App {
  public static void main(String[] args) throws SQLException {
    Injector injector = Guice.createInjector(new ServerModule());

    Storage storage = injector.getInstance(Storage.class);

    System.out.println(storage.getAndIncrement());
  }
}
