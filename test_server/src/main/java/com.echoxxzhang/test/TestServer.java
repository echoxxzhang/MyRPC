package com.echoxxzhang.test;

import com.echoxxzhang.registry.DefaultServiceRegister;
import com.echoxxzhang.server.RpcServer;

public class TestServer {
    public static void main(String[] args){

        HelloServiceImpl helloService = new HelloServiceImpl();
        DefaultServiceRegister serviceRegister = new DefaultServiceRegister();
        serviceRegister.register(helloService);
        RpcServer rpcServer = new RpcServer(serviceRegister);
        rpcServer.start(9000);
    }
}
