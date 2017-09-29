package com.hzdl.teacher.downloadcourse.okhttp.interceptor;


import com.hzdl.teacher.downloadcourse.okhttp.HttpInfo;

/**
 * 请求结果拦截器
 */
public interface ResultInterceptor {

    HttpInfo intercept(HttpInfo info) throws Exception;

}
