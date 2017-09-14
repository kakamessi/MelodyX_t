package com.hzdl.teacher.net;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by wangshuai on 2017/9/8.
 */

public interface ITeacher {

    //测试数据接口
    @GET("fcg-bin/cgi_playlist_xml.fcg")
    Call<ResponseBody> getTestInfos(@Query("uin") String uin, @Query("json") String json, @Query("g_tk") String g_tk);







}
