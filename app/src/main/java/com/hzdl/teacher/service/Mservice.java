package com.hzdl.teacher.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.hzdl.mex.socket.SocketParams;
import com.hzdl.mex.socket.teacher.AbsReceiver;
import com.hzdl.mex.socket.teacher.TeacherClient;
import com.hzdl.mex.socket.teacher.udp.UdpClient;
import com.hzdl.teacher.base.Constant;

import java.net.DatagramSocket;

public class Mservice extends Service {
    
    private Handler mHandler = null;
    private DatagramSocket socket;
    private UdpClient uc;

    @Override
    public void onCreate() {
        super.onCreate();

        initSocket();
        initHandler();

    }

    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                String str = msg.obj.toString();
                String[] strs = str.split("\\|");
                String action1 = strs[0];
                String action2 = strs[1];

                switch (action1) {
                    //收到客户端消息
                    case Constant.ACTION_MSG_COMING:

                        break;

                }
            }
        };

    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    private void initSocket(){
        TeacherClient.getInstance().start(new AbsReceiver() {
            @Override
            public void connected() {

            }

            @Override
            public void receive(byte[] buffer) {

            }

            @Override
            public void disconnect() {

            }
        });
        try {
            socket = new DatagramSocket(SocketParams.TEACHER_UDP_PORT);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    uc = new UdpClient(socket);
                    uc.readSynMsg();
                    uc.sendSycMsg();
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}








