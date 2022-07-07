# RPC-simple



了解RPC的一些原理后，尝试自己造个轮子，加深了解



## 介绍

> rpc是一种远程过程调用协议。
>
> rpc主要功能：异构分布式项目之间的通信，使消费者只需要知道接口，远程调用方法就像调用本地方法一样。 要使得消费层只通过接口调用远程实现方法，那么其之间的传输数据肯定是：类、方法、参数、返回值，以及一些其它传输的信息。 之间涉及到网络传输通信，肯定要发布服务供客户端请求。客户端要执行未知实现的方法，是通过**动态代理**实现的。 



## 项目架构

![](https://cdn.jsdelivr.net/gh/echoxxzhang/blog_img/img/20220706163713.png)





## 具体过程：

![](https://cdn.jsdelivr.net/gh/echoxxzhang/blog_img/img/20220707000046.png)

<br>



## 项目运行：

1）安装zookeeper：

win版安装：

> https://www.apache.org/dyn/closer.lua/zookeeper/zookeeper-3.5.10/apache-zookeeper-3.5.10-bin.tar.gz



docker版安装：

> docker pull zookeeper:3.5.8
>
> docker run -d --name zookeeper -p 2181:2181 zookeeper:3.5.8