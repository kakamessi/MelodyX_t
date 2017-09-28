package com.hzdl.teacher.utils;

import java.security.MessageDigest;

/**
 * Created by wangshuai on 2017/9/28.
 */

public class Md5Util {

    public static String md5(String str) {
        char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

        StringBuilder ret = null;
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] bytes = md.digest(str.getBytes("utf-8"));

            ret = new StringBuilder(bytes.length * 2);
            for (int i = 0; i < bytes.length; i++) {
                ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
                ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
            }
        } catch (Exception e) {
            ret = null;
        }

        if (ret == null) {
            return "";
        }

        return ret.toString();
    }

}
