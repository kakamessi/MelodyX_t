package com.hzdl.mex.socket;

/**
 * Created by wangshuai on 2017/9/11.
 */

public class SocketParams {


    //服务端tcp绑定端口
    public static final int PORT = 20000 + 1;
    //服务端udp发送参数
    public static final String UDP_HOST = "255.255.255.255";
    //服务端udp发送参数
    public static final int UDP_PORT = 8000 + 1;
    //服务端udp监听端口
    public static final int TEACHER_UDP_PORT = 8001 + 1;


    public static final String HOST = "192.168.2.146";

    /**
     * 客户端主动断开连接
     */
    public static final String ACTION_TYPE_OUT = "com.dreamidea.pianodance.bye";


}
