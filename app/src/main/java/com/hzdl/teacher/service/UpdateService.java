package com.hzdl.teacher.service;

import android.content.Intent;
import android.os.IBinder;

import com.allenliu.versionchecklib.core.AVersionService;
import com.hzdl.teacher.bean.UpdateInfoBean;
import com.hzdl.teacher.core.ActionDispatcher;
import com.hzdl.teacher.core.ActionProtocol;
import com.hzdl.teacher.downloadcourse.okhttp.util.GsonUtil;
import com.hzdl.teacher.utils.Utils;


public class UpdateService extends AVersionService {


    public UpdateService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onResponses(AVersionService service, String response) {
        try {
            UpdateInfoBean uib = GsonUtil.jsonToObject(response,UpdateInfoBean.class);
            if(uib!=null && Utils.getVersionCode(this)<Integer.parseInt(uib.getDetail().getCode())) {
                service.showVersionDialog("http://video.angelmusic360.com/apk/2017-09-29/app-release10.apk", "发现新版本", "点击升级");
            }else {
                ActionDispatcher.getInstance().dispatch(ActionProtocol.getActionCode(ActionProtocol.CODE_ACTION_UPDATE));
            }

        }catch (Exception e){
            e.printStackTrace();
            service.showVersionDialog("http://video.angelmusic360.com/apk/2017-09-29/app-release10.apk", "发现新版本", "点击升级");
        }
    }

/*    VersionParams.Builder builder = new VersionParams.Builder()
            .setRequestUrl("http://www.baidu.com")
            .setService(UpdateService.class);
    AllenChecker.startVersionCheck(MainActivity.this, builder.build());*/


/*    VersionUpdateConfig.getInstance()//获取配置实例
            .setContext(MainActivity.this)//设置上下文
    .setDownLoadURL("http://video.angelmusic360.com/apk/2017-09-29/app-release10.apk")//设置文件下载链接
    //.setFileSavePath(savePath)//设置文件保存路径（可不设置）
    .setNotificationIconRes(R.mipmap.ic_launcher)//设置通知图标
    .setNotificationSmallIconRes(R.mipmap.ic_launcher)//设置通知小图标
    .setNotificationTitle("版本升级Demo")//设置通知标题
    .startDownLoad();//开始下载*/


}
