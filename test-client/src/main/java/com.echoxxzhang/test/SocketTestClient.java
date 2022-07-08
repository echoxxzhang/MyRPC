package com.echoxxzhang.test;


import com.echoxxzhang.RpcClientProxy;
import com.echoxxzhang.api.HelloObject;
import com.echoxxzhang.api.HelloService;
import com.echoxxzhang.socket.client.SocketClient;

/**
 * 测试用消费者（客户端）
 */
public class SocketTestClient {

    public static void main(String[] args) {
        SocketClient client = new SocketClient("127.0.0.1", 9000);
        RpcClientProxy proxy = new RpcClientProxy(client);
        HelloService helloService = proxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12, "This is a message");
        String res = helloService.hello(object);
        System.out.println(res);
    }

}
