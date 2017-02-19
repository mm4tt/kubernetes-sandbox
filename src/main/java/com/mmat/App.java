package com.mmat;

import com.mmat.proto.TestPB;

/**
 * Entrypoint to the App.
 */
public class App {
  public static void main(String[] args) {
    System.out.println("Hello World!");
    TestPB proto = TestPB.newBuilder()
        .setId(1)
        .setValue("value")
        .build();
    System.out.println(proto);
  }
}
