package com.echoxxzhang.test;

import com.echoxxzhang.api.HelloService;
import com.echoxxzhang.registry.DefaultServiceRegistry;
import com.echoxxzhang.registry.ServiceRegistry;
import com.echoxxzhang.socket.server.SocketServer;


/**
 * 测试用服务提供方（服务端）
 * @author ziyang
 */
public class SocketTestServer {

    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        ServiceRegistry serviceRegistry = new DefaultServiceRegistry();
        serviceRegistry.register(helloService);
        SocketServer socketServer = new SocketServer(serviceRegistry);
        socketServer.start(9000);
    }

}
