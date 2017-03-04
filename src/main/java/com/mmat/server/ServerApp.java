package com.mmat.server;

import com.google.inject.Guice;
import com.google.inject.Inject;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * ServerApp entrypoint.
 */
public class ServerApp {
  private static final Logger logger = Logger.getLogger(ServerApp.class.getName());

  public static final int PORT = 50001;

  private final ServiceImpl service;

  private Server server;

  @Inject
  public ServerApp(ServiceImpl service) {
    this.service = service;
  }

  /**
   * Launches the server.
   */
  public static void main(String[] args) throws IOException, InterruptedException {
    final ServerApp server = Guice.createInjector().getInstance(ServerApp.class);
    server.start().blockUntilShutdown();
  }

  private ServerApp start() throws IOException {
    server = ServerBuilder.forPort(PORT)
        .addService(service)
        .build().start();
    logger.info("ServerApp started, listening on " + PORT);
    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      // Use stderr here since the logger may have been reset by its JVM shutdown hook.
      System.err.println("*** shutting down gRPC server since JVM is shutting down");
      ServerApp.this.stop();
      System.err.println("*** server shut down");
    }));
    return this;
  }

  private void stop() {
    if (server != null) {
      server.shutdown();
    }
  }

  /**
   * Await termination on the main thread since the grpc library uses daemon threads.
   */
  private void blockUntilShutdown() throws InterruptedException {
    if (server != null) {
      server.awaitTermination();
    }
  }
}
