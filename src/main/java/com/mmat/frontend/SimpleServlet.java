package com.mmat.frontend;

import com.google.inject.Inject;
import com.mmat.client.Client;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Simple servlet rendering the frontend page.
 */
public class SimpleServlet extends HttpServlet {
  private final Client backendClient;

  @Inject
  public SimpleServlet(Client backendClient) {
    this.backendClient = backendClient;
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    PrintWriter writer = response.getWriter();

    String backendMessage;
    try {
      backendMessage = backendClient.greet(getHostName());
    } catch (Exception e) {
      backendMessage = "Unable to contact backend.";
    }

    writer.println("<h1>Pulp Frontend</h2>");
    writer.format("<h2>Hello this is %s</h2>", getHostName());
    writer.format("<h2>Backend says: %s</h2>", backendMessage);
  }

  private String getHostName() {
    try {
      return InetAddress.getLocalHost().getHostName();
    } catch (UnknownHostException e) {
      return "unknown host";
    }
  }
}
