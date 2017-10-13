package com.hzdl.teacher.bean;

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

}
