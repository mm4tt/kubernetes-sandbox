package com.mmat.server;

import com.google.inject.Inject;
import com.mmat.log.LoggerFactory;
import com.mmat.proto.HelloReply;
import com.mmat.proto.HelloRequest;
import com.mmat.proto.SimpleServiceGrpc;
import com.mmat.storage.Storage;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;

/**
 * Service implementation.
 */
class ServiceImpl extends SimpleServiceGrpc.SimpleServiceImplBase {
  private static final Logger logger = LoggerFactory.getLogger();

  private final Storage storage;

  @Inject
  ServiceImpl(Storage storage) {
    this.storage = storage;
  }

  @Override
  public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
    logger.info("Processing request {}", request);
    try {
      responseObserver.onNext(HelloReply.newBuilder()
          .setMessage(String.format(
              "Hello frontend %s, this is backend %s speaking<br /><br />Pulp value is: %s",
              request.getName(),
              getServerName(),
              storage.getAndIncrement()))
          .build());
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    responseObserver.onCompleted();
  }

  private String getServerName() {
    try {
      return InetAddress.getLocalHost().getHostName();
    } catch (UnknownHostException e) {
      return "unknown host";
    }
  }
}
