package com.hzdl.teacher.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.hzdl.mex.socket.SocketParams;
import com.hzdl.mex.socket.teacher.AbsReceiver;
import com.hzdl.mex.socket.teacher.TeacherClient;
import com.hzdl.mex.socket.teacher.udp.UdpClient;
import com.hzdl.teacher.bean.ActionBean;
import com.hzdl.teacher.core.ActionDispatcher;
import com.hzdl.teacher.core.ActionProtocol;
import com.hzdl.teacher.core.ActionResolver;

import java.net.DatagramSocket;


/**
 *
 * TeacherClient.getInstance().sendMsgToAll("3|1|1&".getBytes());
 * ActionDispatcher.getInstance().dispatch(new String(buffer));
 *
 */
public class Mservice extends Service {

    private UdpClient uc;
    private Thread udpThread;
    private DatagramSocket socket;

    @Override
    public void onCreate() {
        super.onCreate();
        ActionDispatcher.getInstance().register(this.getClass().getName(), actionHandler);
        Log.e("kaka","Mservice onCreate");
        initSocket();
        initUdp();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("kaka","stop Mservice");
        ActionDispatcher.getInstance().remove(this.getClass().getName());

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
                ActionDispatcher.getInstance().dispatch(ActionProtocol.getActionCode(ActionProtocol.CODE_ACTION_CONNECTED));
            }

            @Override
            public void receive(byte[] buffer) {

            }
            @Override
            public void disconnect() {
                ActionDispatcher.getInstance().dispatch(ActionProtocol.getActionCode(ActionProtocol.CODE_ACTION_CONNECTED));
            }
        });

    }

    private void destroyUdp(){
        if(udpThread!=null && uc!=null) {
            udpThread.interrupt();
            udpThread = null;
            uc.close();
            uc = null;
        }
    }

    private void initUdp(){

        udpThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new DatagramSocket(SocketParams.TEACHER_UDP_PORT);
                    uc = new UdpClient(socket);
                    uc.readSynMsg();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("kaka","____________________________udp while(true) Exception  stop!!!" + e.getMessage());
                }
            }
        });
        udpThread.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                uc.sendSycMsg();
            }
        }).start();

    }


    private Handler actionHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            handleMsg(msg);
        }
    };

    private void handleMsg(Message msg) {

        ActionBean ab = ActionResolver.getInstance().resolve((String) msg.obj);
        int c1 = Integer.parseInt(ab.getCodes()[0]);
        if (c1 == ActionProtocol.CODE_ACTION_UDP_ON) {
            initUdp();
        }else if(c1 == ActionProtocol.CODE_ACTION_UDP_OFF){
            destroyUdp();
        }

    }

}








