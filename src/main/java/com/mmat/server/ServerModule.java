package com.mmat.server;

import com.google.inject.AbstractModule;
import com.mmat.storage.StorageModule;

/**
 * Guice module for the server app.
 */
public class ServerModule extends AbstractModule {
  @Override
  protected void configure() {
    install(new StorageModule());
  }
}
