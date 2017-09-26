package com.hzdl.teacher.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.hzdl.mex.socket.SocketParams;
import com.hzdl.mex.socket.teacher.AbsReceiver;
import com.hzdl.mex.socket.teacher.TeacherClient;
import com.hzdl.mex.socket.teacher.udp.UdpClient;
import com.hzdl.teacher.core.ActionDispatcher;

import java.net.DatagramSocket;

public class Mservice extends Service {
    
    private DatagramSocket socket;
    private UdpClient uc;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.e("kaka","Mservice onCreate");
        initSocket();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        socket = null;
        uc = null;
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }


    private void initSocket(){
        //服务端开启
        TeacherClient.getInstance().start(new AbsReceiver() {
            @Override
            public void connected() {

            }

            @Override
            public void receive(byte[] buffer) {
                ActionDispatcher.getInstance().dispatch(new String(buffer));
            }

            @Override
            public void disconnect() {

            }
        });
        //udp补偿开启
        try {
            if(socket==null) {
                socket = new DatagramSocket(SocketParams.TEACHER_UDP_PORT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                uc = new UdpClient(socket);
                //教师端上线通知
                uc.sendSycMsg();
                //监听并且下发ip
                uc.readSynMsg();

            }
        }).start();

    }




}








