package com.mmat.storage;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Guice module for app storage.
 */
public class StorageModule extends AbstractModule {
  @Override
  protected void configure() {
  }

  @Provides
  @Singleton
  Connection provideCrateConnection() throws SQLException {
    return DriverManager.getConnection("crate://crate:5432/");
  }
}
