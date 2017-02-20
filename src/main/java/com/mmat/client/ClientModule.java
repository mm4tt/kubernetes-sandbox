package com.mmat.client;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.mmat.server.ServerApp;

/**
 * Guice module for the client.
 */
public class ClientModule extends AbstractModule {
  // TODO: Provide a way to pass these via flags or config file.
  private static String SERVER_HOST = "backend";
  private static int SERVER_PORT = ServerApp.PORT;

  @Override
  protected void configure() {
    bind(Client.class).toInstance(new ClientImpl(SERVER_HOST, SERVER_PORT));
  }
}
