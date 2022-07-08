package com.echoxxzhang.netty.client;

import com.echoxxzhang.codec.CommonDecoder;
import com.echoxxzhang.codec.CommonEncoder;
import com.echoxxzhang.entity.RpcRequest;
import com.echoxxzhang.entity.RpcResponse;
import com.echoxxzhang.serializer.JsonSerializer;
import com.echoxxzhang.RpcClient;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyClient implements RpcClient {
    private String host;
    private int port;
    private static final Bootstrap bootstrap;

    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    static {
        EventLoopGroup group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new CommonDecoder())
                                .addLast(new CommonEncoder(new JsonSerializer()))
                                .addLast(new NettyClientHandler());
                    }
                });
    }

    @Override
    public Object sendRequest(RpcRequest rpcRequest) {
        try {
            ChannelFuture future = bootstrap.connect(host,port).sync();
            log.info("客户端连接到服务器 {}:{}", host, port);
            Channel channel = future.channel();
            if(channel != null) {
                channel.writeAndFlush(rpcRequest).addListener(future1 -> {
                    if(future1.isSuccess()) {
                        log.info(String.format("客户端发送消息: %s", rpcRequest.toString()));
                    } else {
                        log.error("发送消息时有错误发生: ", future1.cause());
                    }
                });
                channel.closeFuture().sync();
                AttributeKey<RpcResponse> key = AttributeKey.valueOf("rpcResponse"); // 通过 AttributeKey 的方式阻塞获得返回结果
                RpcResponse rpcResponse = channel.attr(key).get();
                return rpcResponse.getData();
            }
        }catch (InterruptedException e){
            log.error("发送消息时有错误发生: ", e);
        }
        return null;
    }
}
