package com.echoxxzhang.test;

import com.echoxxzhang.api.HelloObject;
import com.echoxxzhang.api.HelloService;
import com.echoxxzhang.client.RpcClientProxy;

public class TestClient {
    public static void main(String[] args){
        RpcClientProxy proxy = new RpcClientProxy("127.0.0.1", 9000);
        HelloService helloService = proxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12, "This is a message");
        String res = helloService.hello(object);
        System.out.println(res);
    }
}
