package com.fliggy.bodymachine;

import android.serialport.utils.SerialDataUtils;
import android.serialport.utils.SerialPortUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private String receiveString;
    private StringBuffer result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
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
}
