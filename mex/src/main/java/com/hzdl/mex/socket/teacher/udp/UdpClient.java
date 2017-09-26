package com.hzdl.mex.socket.teacher.udp;

import android.util.Log;

import com.hzdl.mex.socket.SocketParams;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.charset.Charset;

/**
 * Created by wangshuai on 2017/9/12.
 */

public class UdpClient {

    private DatagramSocket socket = null;
    private SocketAddress socketAddr = null;

    public UdpClient(DatagramSocket socket){
        this.socket = socket;
        init();
    }

    private void init() {
        socketAddr = new InetSocketAddress(SocketParams.UDP_HOST, SocketParams.UDP_PORT);
    }

    public void readSynMsg(){

        try {
            // 2、创建数据报
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            // 3、一直监听端口，接收数据包
            while (true) {
                socket.receive(packet);
                String quest_ip = packet.getAddress().toString().substring(1);
                sendSycMsg();
                Log.e("kaka","Teacher Step 2 :  receive upd ip ==" + quest_ip);
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("kaka","Teacher Step 2 :  init udp loop break --------------error-------------");
        }

    }

    /**
     * 发送五次udp广播数据
     */
    public void sendSycMsg(){
        try {
            // 3、创建数据报。包含要发送的数据、与目标主机地址
            byte[] data = "Hi angle".getBytes(Charset.forName("UTF-8"));
            DatagramPacket packet = new DatagramPacket(data, data.length, socketAddr);
            // 4、发送数据
            for(int i=0; i<5; i++) {
                Thread.sleep(100);
                socket.send(packet);
            }
            Log.e("kaka","-----------udp 5times finish-----------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close(){
        if (null != socket) {
            socket.close();
        }
    }

}
