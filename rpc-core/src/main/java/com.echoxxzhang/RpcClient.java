package com.echoxxzhang;

import com.echoxxzhang.entity.RpcRequest;

// 服务器类型通用接口
public interface RpcClient {
    Object sendRequest(RpcRequest rpcRequest);
}
