package com.echoxxzhang.codec;

import com.echoxxzhang.entity.RpcRequest;
import com.echoxxzhang.enumeration.PackageType;
import com.echoxxzhang.serializer.CommonSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

// 自定义编码拦截器
public class CommonEncoder extends MessageToByteEncoder {
    private static final int MAGIC_NUMBER = 0xCAFEBABE;  // 魔术字
    private final CommonSerializer serializer;

    public CommonEncoder(CommonSerializer serializer){
        this.serializer = serializer;
    }

    // 自定义协议，ByteBuf可以看作是Netty提供的字节数据的容器，使用它会让我们更加方便地处理字节数据。
    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf byteBuf) throws Exception {
        byteBuf.writeInt(MAGIC_NUMBER);
        if(msg instanceof RpcRequest) {
            byteBuf.writeInt(PackageType.REQUEST_PACK.getCode());
        } else {
            byteBuf.writeInt(PackageType.RESPONSE_PACK.getCode());
        }
        byteBuf.writeInt(serializer.getCode());
        byte[] bytes = serializer.serialize(msg);
        byteBuf.writeInt(bytes.length);  // 写入消息对应的字节数组长度（+4）
        byteBuf.writeBytes(bytes);
    }
}
