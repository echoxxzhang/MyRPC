package com.echoxxzhang.registry;

import com.echoxxzhang.enumeration.RpcError;
import com.echoxxzhang.exception.RpcException;
import lombok.extern.slf4j.Slf4j;


import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class DefaultServiceRegistry implements ServiceRegistry{
    // 存储服务关系的注册表，使用currentHashmap来存储
    private static final Map<String, Object> serviceMap = new ConcurrentHashMap<>();
    // 保存当前已注册的对象
    private static final Set<String> registerService = ConcurrentHashMap.newKeySet();

    @Override
    public synchronized <T> void register(T service) {
        String serviceName = service.getClass().getCanonicalName(); // 返回类名
        if (registerService.contains(serviceName)) return;
        registerService.add(serviceName);
        Class<?>[] interfaces = service.getClass().getInterfaces(); // 获取接口
        if (interfaces.length == 0){
            throw new RpcException(RpcError.SERVICE_NOT_IMPLEMENT_ANY_INTERFACE);
        }

        for (Class<?> i : interfaces ){
            serviceMap.put(i.getCanonicalName(), service);
        }
        log.info("向接口：{} 注册服务：{}", interfaces, serviceName);
    }

    // 从Map中获取服务
    @Override
    public Object getService(String serviceName) {
        Object service = serviceMap.get(serviceName);
        if (service == null){
            throw new RpcException(RpcError.SERVICE_NOT_FOUND);
        }
        return service;
    }

}
