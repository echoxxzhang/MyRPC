package com.echoxxzhang;

import com.echoxxzhang.entity.RpcRequest;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

// RPC客户端动态代理
@Slf4j
public class RpcClientProxy implements InvocationHandler {
    private final RpcClient rpcClient;

    public RpcClientProxy(RpcClient rpcClient){
        this.rpcClient = rpcClient;
    }

    // 生成代理对象
    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),
                new Class<?>[]{clazz}, this);
    }

    // 代理方法
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("调用方法: {}#{}", method.getDeclaringClass().getName(), method.getName());

        RpcRequest rpcRequest = new RpcRequest(method.getDeclaringClass().getName(),
                method.getName(), args, method.getParameterTypes());
        return rpcClient.sendRequest(rpcRequest);
    }
}
