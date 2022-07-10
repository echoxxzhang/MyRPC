package com.echoxxzhang.serializer;

import com.echoxxzhang.entity.RpcRequest;
import com.echoxxzhang.entity.RpcResponse;
import com.echoxxzhang.enumeration.SerializerCode;
import com.echoxxzhang.exception.SerializeException;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Slf4j
public class KryoSerializer implements CommonSerializer{
    // 由于kryo 可能存在线程安全问题，因此放在ThreadLocal里，一个线程对应一个Kryo实例
    public static final ThreadLocal<Kryo> kryoThreadLocal = ThreadLocal.withInitial(()->{

        Kryo kryo = new Kryo();
        kryo.register(RpcResponse.class);
        kryo.register(RpcRequest.class);
        kryo.setReferences(true);
        kryo.setRegistrationRequired(false);
        return kryo;
    });

    @Override
    public byte[] serialize(Object obj) {
        try(ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Output output = new Output(byteArrayOutputStream)){

            Kryo kryo = kryoThreadLocal.get();
            // 将对象序列化为byte数组
            kryo.writeObject(output, obj);
            kryoThreadLocal.remove();
            return output.toBytes();

        }catch (Exception e){
            log.error("序列化有错误发生",e);
            throw new SerializeException("序列化时有错误发生");

        }
    }

    @Override
    public Object deserialize(byte[] bytes, Class<?> clazz) {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
             Input input = new Input(byteArrayInputStream)) {
            Kryo kryo = kryoThreadLocal.get();
            // 反序列化
            Object obj = kryo.readObject(input, clazz);
            kryoThreadLocal.remove();
            return obj;
        }catch (Exception e){
            log.error("反序列化有发生错误：",e);
            throw new SerializeException("反序列化时有错误发生");
        }
    }

    @Override
    public int getCode() {
        return SerializerCode.valueOf("KRYO").getCode();
    }
}
