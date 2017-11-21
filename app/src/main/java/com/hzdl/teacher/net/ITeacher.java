package com.hzdl.teacher.net;

import com.hzdl.teacher.base.Constant;
import com.hzdl.teacher.bean.LoginBean;
import com.hzdl.teacher.bean.ModifyPswBean;
import com.hzdl.teacher.bean.lesson.CrouseListBean1031;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by wangshuai on 2017/9/8.
 */

public interface ITeacher {

    //登录
    @FormUrlEncoded
    @POST(Constant.URL_LOGIN)
    Call<LoginBean> postLogin(@Field("account") String account, @Field("password") String password);

    //修改密码
    @FormUrlEncoded
    @POST(Constant.URL_MODIFY_PSW)
    Call<ModifyPswBean> postModifyPsw(@Field("account") String account, @Field("oldPassword") String oldPassword,
                                      @Field("newPassword") String newPassword, @Field("confirmPassword") String confirmPassword);

    /**
     * 方法不要加返回值之类的void String。。CAll<String>代表返回值
     * @return
     */
    @GET(Constant.URL_COURSE_LIST)
    Call<CrouseListBean1031> getCrouseList(@Query("sid") String schoolId);




}










