package com.echoxxzhang.test;

import com.echoxxzhang.RpcClient;
import com.echoxxzhang.RpcClientProxy;
import com.echoxxzhang.api.HelloObject;
import com.echoxxzhang.api.HelloService;
import com.echoxxzhang.netty.client.NettyClient;

public class NettyTestClient {
    public static void main(String[] args){

        RpcClient rpcClient = new NettyClient("127.0.0.1", 9999);
        RpcClientProxy rpcClientProxy = new RpcClientProxy(rpcClient);
        HelloService helloService = rpcClientProxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12, "This is a message");
        String res = helloService.hello(object);
        System.out.println(res);
    }
}
