package com.mmat.server;

import com.mmat.proto.HelloReply;
import com.mmat.proto.HelloRequest;
import com.mmat.proto.SimpleServiceGrpc;
import io.grpc.stub.StreamObserver;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Service implementation.
 */
class ServiceImpl extends SimpleServiceGrpc.SimpleServiceImplBase {
  @Override
  public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
    responseObserver.onNext(HelloReply.newBuilder()
        .setMessage(String.format(
            "Hello frontend %s, this is backend %s speaking",
            request.getName(),
            getServerName()))
        .build());
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
