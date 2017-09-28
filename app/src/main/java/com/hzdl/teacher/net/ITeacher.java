package com.hzdl.teacher.net;

import com.hzdl.teacher.base.Constant;
import com.hzdl.teacher.bean.LoginBean;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by wangshuai on 2017/9/8.
 */

public interface ITeacher {

    //测试数据接口
    @FormUrlEncoded
    @POST(Constant.URL_LOGIN)
    Call<LoginBean> postLogin(@Field("account") String account, @Field("password") String password);







}
