package com.hzdl.mex.socket.teacher;

import android.util.Log;

import com.hzdl.mex.socket.SocketClient;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangshuai on 2017/9/11.
 */

public class TcpServerRunnable implements Runnable {

    private AbsReceiver receiver;
    private ServerSocket serverSocket;
    private HashMap<String,SocketClient> socketList;


    public TcpServerRunnable(AbsReceiver handler, ServerSocket serverSocket) {
        this.receiver = handler;
        this.serverSocket = serverSocket;
        socketList = new HashMap<>();
    }

    @Override
    public void run() {

        try {
            Log.e("kaka","Teacher Step 2 :  init tcp loop");
            while(true){
                SocketClient socket = new SocketClient(serverSocket.accept());
                socketList.put(socket.mSocket.getInetAddress().getHostAddress(),socket);
                Log.e("kaka","Teacher Step 1 :  receive tcp ip ==" + socket.mSocket.getInetAddress().getHostAddress());

                //开启对话线程
                Thread th = new Thread(new TcpRunnable(receiver,socket));
                th.setName("RecThread___________kaka" + socket.mSocket.getInetAddress().getHostAddress() + " : "+socket.mSocket.getPort());
                th.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("kaka","Teacher Step 2 :  init tcp loop break --------------error-------------");
        }finally{
            try {
                if(serverSocket!=null) {
                    serverSocket.close();
                }
            } catch (Exception e) {
            }
        }

    }

    public void quit(){
        for (Map.Entry<String, SocketClient> entry : socketList.entrySet()) {
            entry.getValue().disconnect();
        }

        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, SocketClient> getSocketList() {
        return socketList;
    }

}
