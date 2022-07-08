package com.echoxxzhang.test;


import com.echoxxzhang.api.HelloService;
import com.echoxxzhang.netty.server.NettyServer;
import com.echoxxzhang.registry.DefaultServiceRegistry;
import com.echoxxzhang.registry.ServiceRegistry;

/**
 * 测试用Netty服务提供者（服务端）
 * @author ziyang
 */
public class NettyTestServer {

    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        ServiceRegistry registry = new DefaultServiceRegistry();
        registry.register(helloService);
        NettyServer server = new NettyServer();
        server.start(9999);
    }

}
