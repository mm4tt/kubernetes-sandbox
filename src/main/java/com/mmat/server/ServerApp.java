package com.mmat.server;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.mmat.log.LoggerFactory;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.util.internal.logging.Log4JLoggerFactory;
import org.slf4j.Logger;

import java.io.IOException;

/**
 * ServerApp entrypoint.
 */
public class ServerApp {
  private static final Logger logger = LoggerFactory.getLogger();

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
    Injector injector = Guice.createInjector(new ServerModule());
    try {
      final ServerApp server = injector.getInstance(ServerApp.class);
      server.start().blockUntilShutdown();
    } catch (Throwable t) {
      t.printStackTrace();
    }

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
