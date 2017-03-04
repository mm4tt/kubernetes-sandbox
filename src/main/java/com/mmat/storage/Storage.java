package com.mmat.storage;

import com.google.inject.Inject;
import com.mmat.log.LoggerFactory;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Simple storage.
 */
public class Storage {
  private static final Logger logger = LoggerFactory.getLogger();

  private final Connection connection;

  @Inject
  public Storage(Connection connection) {
    this.connection = connection;
  }

  public long getAndIncrement() throws SQLException {
    logger.info("Reading pulp value");
    ResultSet resultSet = connection.createStatement().executeQuery(
        "SELECT value FROM PulpTable WHERE id = 'pulp'");
    resultSet.next();
    long value = resultSet.getLong("value");
    logger.info("Pulp value is {}", value);
    connection.createStatement().executeUpdate(
        String.format("UPDATE PulpTable SET value = %s WHERE id = 'pulp'", value + 1));
    logger.info("Updated pulp value to {}", value + 1);
    return value;
  }
}
