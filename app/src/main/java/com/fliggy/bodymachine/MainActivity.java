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
        try {
          String receiveString = SerialDataUtils.ByteArrToHex(buffer).replace(" ", "");
          //如果数据是2位，等待下一次接受，
          result.append(receiveString);
          if (result.length() <6) {
            return;
          }else{
            //大于6的话，就可以知道字段长度了
            String mSubstring = result.substring(4, 6);
            int mInt = Integer.parseInt(mSubstring, 16);
            int full_lenght= mInt*2+8;
            if (result.length()<full_lenght){
              return;
            }
          }
          String str = result.toString().trim();
          KLog.e("dicallc" + str);
          String code = str.substring(2, 4);
          switch (Integer.parseInt(code, 16)){
            case 1:
            //查询命令回应

              break;
            case 2:
            //上行体重数据

              break;
            case 3:
            //上行结果数据

              break;
            case 4:
            //上行清零

              break;
            case 5:
            //上行开始测量命令

              break;
            case 6:

              break;
          }
          //去除识别码，知道这条语句应该干什么

          result.delete(0, result.length());

        }catch (Exception mE){
          KLog.e(mE.getMessage());
        }

      }
    });

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
