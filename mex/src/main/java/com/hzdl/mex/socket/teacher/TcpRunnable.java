package com.hzdl.mex.socket.teacher;

import com.hzdl.mex.socket.SocketClient;

import java.net.Socket;

/**
 * Created by wangshuai on 2017/9/11.
 */

public class TcpRunnable implements Runnable {

    private AbsReceiver receiver;
    private SocketClient ss;

    public TcpRunnable(Socket socket) throws Exception {
        ss = new SocketClient(socket);
    }

    public TcpRunnable(AbsReceiver receiver, SocketClient socket) throws Exception{
        this.receiver = receiver;
        this.ss = socket;
    }

    @Override
    public void run() {
        try {
            ss.read(receiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
