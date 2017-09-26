package com.hzdl.teacher.core;

import android.os.Handler;
import android.os.Message;

import java.util.HashMap;

/**
 * Created by wangshuai on 2017/9/26.
 */

public class ActionDispatcher {

    private HashMap<String,Handler> mapHandler = new HashMap<>();

    private static ActionDispatcher instance;
    private ActionDispatcher (){}
    public static synchronized ActionDispatcher getInstance() {
        if (instance == null) {
            instance = new ActionDispatcher();
        }
        return instance;
    }

    public void dispatch(String actionType) {

        for (Handler value : mapHandler.values()) {
            sendHandlerMsg(value,actionType);
        }

    }

    private void sendHandlerMsg(Handler hander,String str){
        Message msg = Message.obtain();
        msg.obj = str;
        hander.sendMessage(msg);
    }

    public void register(String tag, Handler handler) {

        mapHandler.put(tag,handler);
    }

    public void remove(String tag) {

        mapHandler.remove(tag);
    }

    public void reset() {

        mapHandler.clear();
    }




}
