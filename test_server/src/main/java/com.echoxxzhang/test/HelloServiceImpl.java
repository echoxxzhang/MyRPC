package com.echoxxzhang.test;


import com.echoxxzhang.api.HelloObject;
import com.echoxxzhang.api.HelloService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HelloServiceImpl implements HelloService {

    @Override
    public String hello(HelloObject object) {
        log.info("接收到：{}", object.getMessage());
        return "这是返回值:" + object.getId();
    }
}
