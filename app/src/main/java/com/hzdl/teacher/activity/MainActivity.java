package com.hzdl.teacher.activity;

import android.hardware.usb.UsbDevice;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hzdl.mex.socket.teacher.TeacherClient;
import com.hzdl.teacher.R;
import com.hzdl.teacher.base.BaseMidiActivity;
import com.hzdl.teacher.base.Constant;
import com.hzdl.teacher.net.ITeacher;
import com.hzdl.teacher.utils.Utils;

import java.io.IOException;

import jp.kshoji.driver.midi.device.MidiInputDevice;
import jp.kshoji.driver.midi.device.MidiOutputDevice;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends BaseMidiActivity {


    TextView tv_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initMidi();

        tv_test = (TextView) findViewById(R.id.tv_test);
        tv_test.setText(Utils.getLocalIp(this));
        tv_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent intent=new Intent(MainActivity.this,CourseActivity.class);
//                startActivity(intent);
//
//                  MidiOutputDevice midiOutputDevice = getMidiOutputDevice();
//                  midiOutputDevice.sendMidiNoteOn(0, 0x90, 0x40, 0x7f);

                TeacherClient.getInstance().sendMsgToAll("3|1|1&".getBytes());
                Toast.makeText(MainActivity.this,TeacherClient.getInstance().tRunner.getSocketList().size()+"",0).show();

            }
        });

    }

    int i=0;
    @Override
    protected void handleMsg(Message msg) {
        tv_test.setText(i++ + "");
    }


    private void testRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.URL_TEST_QQ)
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


    @Override
    public void onDeviceAttached(@NonNull UsbDevice usbDevice) {

    }

    @Override
    public void onMidiInputDeviceAttached(@NonNull MidiInputDevice midiInputDevice) {

    }

    @Override
    public void onMidiOutputDeviceAttached(@NonNull MidiOutputDevice midiOutputDevice) {

    }

    @Override
    public void onDeviceDetached(@NonNull UsbDevice usbDevice) {

    }

    @Override
    public void onMidiInputDeviceDetached(@NonNull MidiInputDevice midiInputDevice) {

    }

    @Override
    public void onMidiOutputDeviceDetached(@NonNull MidiOutputDevice midiOutputDevice) {

    }

    @Override
    public void onMidiMiscellaneousFunctionCodes(@NonNull MidiInputDevice sender, int cable, int byte1, int byte2, int byte3) {

    }

    @Override
    public void onMidiCableEvents(@NonNull MidiInputDevice sender, int cable, int byte1, int byte2, int byte3) {

    }

    @Override
    public void onMidiSystemCommonMessage(@NonNull MidiInputDevice sender, int cable, byte[] bytes) {

    }

    @Override
    public void onMidiSystemExclusive(@NonNull MidiInputDevice sender, int cable, byte[] systemExclusive) {

    }

    @Override
    public void onMidiNoteOff(@NonNull MidiInputDevice sender, int cable, int channel, int note, int velocity) {

    }

    @Override
    public void onMidiNoteOn(@NonNull MidiInputDevice sender, int cable, int channel, int note, int velocity) {

    }

    @Override
    public void onMidiPolyphonicAftertouch(@NonNull MidiInputDevice sender, int cable, int channel, int note, int pressure) {

    }

    @Override
    public void onMidiControlChange(@NonNull MidiInputDevice sender, int cable, int channel, int function, int value) {

    }

    @Override
    public void onMidiProgramChange(@NonNull MidiInputDevice sender, int cable, int channel, int program) {

    }

    @Override
    public void onMidiChannelAftertouch(@NonNull MidiInputDevice sender, int cable, int channel, int pressure) {

    }

    @Override
    public void onMidiPitchWheel(@NonNull MidiInputDevice sender, int cable, int channel, int amount) {

    }

    @Override
    public void onMidiSingleByte(@NonNull MidiInputDevice sender, int cable, int byte1) {

    }

    @Override
    public void onMidiTimeCodeQuarterFrame(@NonNull MidiInputDevice sender, int cable, int timing) {

    }

    @Override
    public void onMidiSongSelect(@NonNull MidiInputDevice sender, int cable, int song) {

    }

    @Override
    public void onMidiSongPositionPointer(@NonNull MidiInputDevice sender, int cable, int position) {

    }

    @Override
    public void onMidiTuneRequest(@NonNull MidiInputDevice sender, int cable) {

    }

    @Override
    public void onMidiTimingClock(@NonNull MidiInputDevice sender, int cable) {

    }

    @Override
    public void onMidiStart(@NonNull MidiInputDevice sender, int cable) {

    }

    @Override
    public void onMidiContinue(@NonNull MidiInputDevice sender, int cable) {

    }

    @Override
    public void onMidiStop(@NonNull MidiInputDevice sender, int cable) {

    }

    @Override
    public void onMidiActiveSensing(@NonNull MidiInputDevice sender, int cable) {

    }

    @Override
    public void onMidiReset(@NonNull MidiInputDevice sender, int cable) {

    }
}
