package com.hzdl.mex.socket.teacher;

import com.hzdl.mex.socket.SocketClient;
import com.hzdl.mex.socket.SocketParams;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Map;

/**
 * Created by wangshuai on 2017/9/11.
 */

public class TeacherClient {

    public TcpServerRunnable tRunner;
    private ServerSocket sScoket;
    private SendMsgHandler msgHandler = new SendMsgHandler();

    private Thread mThread;

    private static TeacherClient instance=new TeacherClient();
    private TeacherClient(){
    }
    public static TeacherClient getInstance(){
        return instance;
    }


    /**
     * 启动socket服务
     * @param handler
     */
    public void start(AbsReceiver handler){
        try {
            sScoket = new ServerSocket(SocketParams.PORT);
            tRunner = new TcpServerRunnable(handler,sScoket);
            mThread = new Thread(tRunner);
            mThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 向客户端发送消息
     * @param bytes
     */
    public void sendMsgToAll(String msg){
        byte[] bytes = msg.getBytes();
        for (Map.Entry<String, SocketClient> entry : tRunner.getSocketList().entrySet()) {
            msgHandler.sendMsg(entry.getValue(),bytes);
        }
    }

    /**
     * 停止服务
     */
    public void stop(){

    }




}
