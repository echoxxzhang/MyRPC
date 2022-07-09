# MyRPC



为更深了解RPC的核心功能，仿照Dubbo，动手实现一个简易版RPC框架； 



## 介绍

> RPC是一种远程过程调用协议。
>
> RPC主要功能：异构分布式项目之间的通信，使消费者只需要知道接口，远程调用方法就像调用本地方法一样。 要使得消费层只通过接口调用远程实现方法，那么其之间的传输数据肯定是：类、方法、参数、返回值，以及一些其它传输的信息。 之间涉及到网络传输通信，肯定要发布服务供客户端请求。客户端要执行未知实现的方法，是通过**动态代理**实现的。 



## 项目架构

![](https://cdn.jsdelivr.net/gh/echoxxzhang/blog_img/img/20220707000046.png)



- 基于Netty的网络传输
- 实现自定义协议与编解码器，防止粘包

![](https://cdn.jsdelivr.net/gh/echoxxzhang/blog_img/img/20220709162037.png)

- 使用Kryo替代JDK的序列化机制，性能提升近15倍 



<br>



## 项目运行：

优先启动NettyTestServer：

```
public class NettyTestServer {

    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        ServiceRegistry registry = new DefaultServiceRegistry();
        registry.register(helloService);
        NettyServer server = new NettyServer();
        server.start(9999);
    }

}
```

再启动NettyTestClient：

```
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

```

Service端运行截图：

![](https://cdn.jsdelivr.net/gh/echoxxzhang/blog_img/img/20220709161721.png)