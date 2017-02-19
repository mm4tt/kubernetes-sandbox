package com.mmat.client;

import com.mmat.proto.HelloReply;
import com.mmat.proto.HelloRequest;
import com.mmat.proto.SimpleServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.logging.Logger;

/**
 * Implementation of the {@link Client}.
 */
class ClientImpl implements Client {
  private static final Logger logger = Logger.getLogger(ClientImpl.class.getName());

  private final ManagedChannel channel;
  private final SimpleServiceGrpc.SimpleServiceBlockingStub blockingStub;

  /** Construct client connecting to the server at {@code host:port}. */
  ClientImpl(String host, int port) {
    this(ManagedChannelBuilder.forAddress(host, port)
        // Channels are secure by default (via SSL/TLS).
        // Here we disable TLS to avoid needing certificates.
        .usePlaintext(true));
  }

  /** Construct client for accessing server using the existing channel. */
  ClientImpl(ManagedChannelBuilder<?> channelBuilder) {
    channel = channelBuilder.build();
    blockingStub = SimpleServiceGrpc.newBlockingStub(channel);
  }

  @Override
  public String greet(String frontendName) {
    logger.info("Will try to greet " + frontendName + " ...");
    HelloRequest request = HelloRequest.newBuilder()
        .setName(frontendName)
        .build();
    HelloReply response = blockingStub.sayHello(request);
    logger.info("Greeting: " + response.getMessage());
    return response.getMessage();
  }
}
