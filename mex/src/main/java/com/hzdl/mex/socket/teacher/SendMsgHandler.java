package com.hzdl.mex.socket.teacher;

import com.hzdl.mex.socket.SocketClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wangshuai on 2017/9/11.
 */

public class SendMsgHandler {

    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(30);


    public void sendMsg(final SocketClient socket, final byte[] bytes){
        fixedThreadPool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    socket.write(bytes);
                } catch (Exception e) {
                }
            }
        });

    }

    public void quit(){
        fixedThreadPool.shutdownNow();
    }


}
