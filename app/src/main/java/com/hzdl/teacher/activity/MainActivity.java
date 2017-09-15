package com.hzdl.teacher.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hzdl.mex.usb.UsbDeviceConnect;
import com.hzdl.mex.usb.UsbDeviceInfo;
import com.hzdl.mex.usb.callback.CallbackInterface;
import com.hzdl.teacher.R;
import com.hzdl.teacher.net.ITeacher;
import com.hzdl.teacher.net.NetConstant;
import com.hzdl.teacher.utils.Utils;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends BaseActivity {


    protected static final String ACTION_USB_PERMISSION = "com.Aries.usbhosttest.USB_PERMISSION";




    TextView tv_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        tv_test = (TextView) findViewById(R.id.tv_test);
        tv_test.setText(Utils.getLocalIp(this));
        tv_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,CourseActivity.class);
                startActivity(intent);
            }
        });

    }







    private void testRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetConstant.URL_TEST_QQ)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ITeacher userBiz = retrofit.create(ITeacher.class);
        Call<ResponseBody> call = userBiz.getTestInfos("125921384","1","1916754934");
        call.enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                try {
                    tv_test.setText(new String(response.body().bytes(),"gbk"));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t)
            {
                tv_test.setText(t.toString());
            }
        });
    }

    private void init() {
        final IntentFilter filter = new IntentFilter();
        filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        filter.addAction(ACTION_USB_PERMISSION);
        registerReceiver(mUsbReceiver, filter);
        setPiano();
    }

    private void setPiano() {
        UsbDeviceConnect.setCallbackInterface(new CallbackInterface() {
            @Override
            public void onReadCallback(String str) {
                Message msg = Message.obtain();
                msg.what = 1;
                msg.obj = str;


            }
            @Override
            public void onSendCallback(boolean isSend) {
            }
            @Override
            public void onConnect(boolean isConnected) {
                if(isConnected){

                }
            }
        });
        UsbDeviceInfo.getUsbDeviceInfo(MainActivity.this).update();
        UsbDeviceInfo.getUsbDeviceInfo(MainActivity.this).connect();
    }

    public void stopConnect() {
        UsbDeviceInfo.getUsbDeviceInfo(MainActivity.this).stopConnect();
    }
    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            //Log.i(TAG, "BroadcastReceiver-->" + action);
            String status = null;
            switch (action) {
                case UsbManager.ACTION_USB_DEVICE_ATTACHED:
                    status = "usb-insert";
                    Toast.makeText(context, "检测到有USB插口接入", Toast.LENGTH_SHORT).show();
                    setPiano();

                    break;
                case UsbManager.ACTION_USB_DEVICE_DETACHED:
                    status = "usb-discrete";
                    Toast.makeText(context, "USB线被拔出", Toast.LENGTH_SHORT).show();
                    UsbDeviceInfo.getUsbDeviceInfo(MainActivity.this).colse();
                    // clear();

                    break;
                case ACTION_USB_PERMISSION:
                    boolean isconnect = false;
                    // 判断用户点击的是取消还是确认
                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        Toast.makeText(context, "连接权限被允许", Toast.LENGTH_SHORT).show();
                        isconnect = UsbDeviceInfo.getUsbDeviceInfo(MainActivity.this).getUsbDeviceConnection();
                    } else {
                        stopConnect();
                    }
                    status = isconnect ? "link-success" : "link-fail";
                    //连上钢琴 开启静音模式

                    break;
            }
            if (status != null) {
                // 发送状态到unity

            }
        }
    };
}
