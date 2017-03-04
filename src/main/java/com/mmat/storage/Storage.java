package com.mmat.storage;

import com.google.inject.Inject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Simple storage.
 */
public class Storage {
  private final Connection connection;

  @Inject
  public Storage(Connection connection) {
    this.connection = connection;
  }

  public long getAndIncrement() throws SQLException {
    ResultSet resultSet = connection.createStatement().executeQuery(
        "SELECT value FROM PulpTable WHERE id = 'pulp'");
    resultSet.next();
    long value = resultSet.getLong("value");
    connection.createStatement().executeUpdate(
        String.format("UPDATE PulpTable SET value = %s WHERE id = 'pulp'", value + 1));
    return value;
  }
}
