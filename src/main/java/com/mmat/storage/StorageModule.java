package com.mmat.storage;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.crate.client.CrateClient;
import io.crate.shade.org.elasticsearch.common.settings.Settings;

/**
 * Guice module for app storage.
 */
public class StorageModule extends AbstractModule {
  @Override
  protected void configure() {
  }

  @Provides
  @Singleton
  CrateClient provideCrateClient() {
    return new CrateClient(
        Settings.settingsBuilder()
            .put("cluster.name", "my-crate-cluster")
            .build(),
        "crate:4300");
  }
}
