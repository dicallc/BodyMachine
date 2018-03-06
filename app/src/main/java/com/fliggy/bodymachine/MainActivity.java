package com.fliggy.bodymachine;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.serialport.utils.SerialDataUtils;
import android.serialport.utils.SimpleSerialPortUtil;
import android.serialport.utils.Utils;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.fliggy.bodymachine.utils.ToastUtils;
import com.socks.library.KLog;

public class MainActivity extends AppCompatActivity {

  @BindView(R.id.textView) TextView mTextView;
  @BindView(R.id.et_height) EditText mEtHeight;
  @BindView(R.id.et_age) EditText mEtAge;
  @BindView(R.id.male_rb) RadioButton mMaleRb;
  @BindView(R.id.famale_rb) RadioButton mFamaleRb;
  @BindView(R.id.sex_rg) RadioGroup mSexRg;
  @BindView(R.id.start) Button mStart;
  @BindView(R.id.start_test) Button mStartTest;
  private StringBuffer result = new StringBuffer();
  private MediaPlayer mediaPlayer;
  private SimpleSerialPortUtil mSerialPort;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    initData();
    //手动申请权限,视频音频权限为同一个
    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.
        WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(MainActivity.this, new String[] {
          Manifest.permission.WRITE_EXTERNAL_STORAGE
      }, 1);//权限返回码为1
    } else {
      //initAudio();
    }
  }

  private void initAudio() {
    try {
      mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.test);//重新设置要播放的音频
      mediaPlayer.start();//开始播放
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void initData() {
    mSerialPort = SimpleSerialPortUtil.getInstance();
    mSerialPort.setOnDataReceiveListener(new SimpleSerialPortUtil.OnDataReceiveListener() {
      @Override public void onDataReceive(byte[] buffer, int size) {
        String receiveString = SerialDataUtils.ByteArrToHex(buffer).replace(" ", "");
        //如果数据是2位，等待下一次接受，
        result.append(receiveString);
        if (result.length() == 2) {
          return;
        }
        KLog.e("dicallc" + result.toString().trim());
        result.delete(0, result.length());
      }
    });

    //该方法是读取数据的回调监听，一旦读取到数据，就立马回调
    //mSerialPort.setOnDataReceiveListener(new SerialPortUtil.OnDataReceiveListener() {
    //  @Override public void onDataReceive(int mCmd_num, byte[] buffer, boolean size) {
    //    switch (mCmd_num) {
    //      case 2:
    //        char[] tmp = new char[2];
    //        tmp[0] = (char) buffer[0];
    //        tmp[1] = (char) buffer[1];
    //        if (SerialDataUtils.HexToInt(new String(tmp))!=1){
    //          startTepOne();
    //        }else{
    //          startTepTwo();
    //        }
    //
    //        break;
    //    }
    //    receiveString = SerialDataUtils.ByteArrToHex(buffer);
    //    System.out.println("MainActivity2.onDataReceive receiveString= " + receiveString);
    //    runOnUiThread(new Runnable() {
    //      @Override public void run() {
    //        result.append(receiveString + "\r\n");
    //      }
    //    });
    //  }
    //});
  }



  /**
   * 依旧是申请权限
   */
  @Override public void onRequestPermissionsResult(int requestCode, String[] permissions,
      int[] grantResults) {
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

  @Override public void onDestroy() {
    super.onDestroy();
    if (mediaPlayer != null) {
      mediaPlayer.stop();
      mediaPlayer.release();
    }
  }

  @OnClick({R.id.start,R.id.start_test}) public void onViewClicked(View mView) {
    switch (mView.getId()){
      case R.id.start:
        startTepOne();
        break;
      case R.id.start_test:
        startTepTwo();
        break;
    }

  }

  /**
   * 第一步 根据用户所填写内容，发送给机器等待回应，如果正常接收，则进行下一步，不正常就继续发送
   */
  private void startTepOne() {
    String height = mEtHeight.getText().toString().trim();
    String age = mEtAge.getText().toString().trim();
    String femail = "";
    if (mSexRg.getCheckedRadioButtonId() == R.id.male_rb) {
      femail = "1";
    } else {
      femail = "0";
    }
    if (TextUtils.isEmpty(height)) {
      ToastUtils.showShortToast("身高还没填写");
      return;
    }
    if (TextUtils.isEmpty(age)) {
      ToastUtils.showShortToast("年龄还没填写");
      return;
    }
    String runModel = "0";
    String str = Utils.loadUserInfoCmd(height, age, femail, runModel);
    mSerialPort.sendCmds(str);
  }
  /**
   * 发送可以开始命令
   */
  private void startTepTwo() {
    String str = Utils.sendStartCmd();
    mSerialPort.sendCmds(str);
  }

}
