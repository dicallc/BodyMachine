package com.fliggy.bodymachine;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.serialport.utils.SerialDataUtils;
import android.serialport.utils.SerialPortUtil;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private String receiveString;
    private StringBuffer result;
    private MediaPlayer mediaPlayer ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        //手动申请权限,视频音频权限为同一个
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.
                WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);//权限返回码为1
        } else {
            initAudio();
        }

    }

    private void initAudio() {
        try {
            mediaPlayer=MediaPlayer.create(MainActivity.this, R.raw.test);//重新设置要播放的音频
            mediaPlayer.start();//开始播放
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initData() {
        SerialPortUtil  serialPort = SerialPortUtil.getInstance();
        //该方法是读取数据的回调监听，一旦读取到数据，就立马回调
        serialPort.setOnDataReceiveListener(new SerialPortUtil.OnDataReceiveListener() {
            @Override
            public void onDataReceive(byte[] buffer, int size) {
                receiveString = SerialDataUtils.ByteArrToHex(buffer).trim();
                System.out.println("MainActivity2.onDataReceive receiveString= " + receiveString);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        result.append(receiveString + "\r\n");
                    }
                });

            }
        });
    }
    /**
     * 依旧是申请权限
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initAudio();
                } else {
                    Toast.makeText(this, "您禁止了权限，所以无法访问~", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                break;
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }
}
