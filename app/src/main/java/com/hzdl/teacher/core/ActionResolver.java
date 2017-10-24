package com.hzdl.teacher.core;

/**
 *
 * Created by wangshuai on 2017/9/26.
 *
 *
 */

public class ActionResolver {

    private static ActionResolver instance;
    private ActionResolver (){}
    public static synchronized ActionResolver getInstance() {
        if (instance == null) {
            instance = new ActionResolver();
        }
        return instance;
    }

    public ActionBean resolve(String str){
        ActionBean ab = new ActionBean();
        String[] sA =  str.split("\\|");
        ab.setCodes(sA);
        return ab;
    }




}
