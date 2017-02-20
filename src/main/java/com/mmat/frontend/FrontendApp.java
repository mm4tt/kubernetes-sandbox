package com.mmat.frontend;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.mmat.client.ClientModule;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * Frontend entrypoint.
 */
public class FrontendApp {
  private final SimpleServlet servlet;

  @Inject
  FrontendApp(SimpleServlet servlet) {
    this.servlet = servlet;
  }

  private void start() throws Exception {
    Server server = new Server(8080);

    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
    context.setContextPath("/");
    server.setHandler(context);

    context.addServlet(new ServletHolder(servlet), "/*");

    server.start();
    server.join();
  }

  /**
   * Launches the server.
   */
  public static void main(String[] args) throws Exception {
    final FrontendApp frontend =
        Guice.createInjector(new ClientModule()).getInstance(FrontendApp.class);
    frontend.start();
  }
}
