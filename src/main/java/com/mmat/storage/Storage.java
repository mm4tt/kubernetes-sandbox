package com.mmat.storage;

import com.google.inject.Inject;
import io.crate.action.sql.SQLResponse;
import io.crate.client.CrateClient;

/**
 * Simple storage.
 */
public class Storage {
  private final CrateClient crate;

  @Inject
  public Storage(CrateClient crate) {
    this.crate = crate;
  }

  public long getAndIncrement() {
    SQLResponse response = crate.sql("SELECT value FROM PulpTable WHERE id = 'pulp'").actionGet();
    long value = (long) response.rows()[0][0];
    crate.sql(String.format("UPDATE PulpTable SET value = %s WHERE id = 'pulp'", value + 1)).actionGet();
    return value;
  }
}
