package com.hzdl.teacher.core;

/**
 * Created by DELL on 2017/5/17.
 */

public class ActionBean {

    private String[] codes;

    public String[] getCodes() {
        return codes;
    }

    public void setCodes(String[] codes) {
        this.codes = codes;
    }

    public int getCodeByPositon(int p){

        int code = Integer.parseInt(codes[p]);

        return code;
    }

    public String getStringByPositon(int p){
        return codes[p];
    }

}
