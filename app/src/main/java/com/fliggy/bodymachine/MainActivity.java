package com.fliggy.bodymachine;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.serialport.utils.SerialDataUtils;
import android.serialport.utils.SimpleSerialPortUtil;
import android.serialport.utils.Utils;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.fliggy.bodymachine.base.BasePrintActivity;
import com.fliggy.bodymachine.model.BodyInfoModel;
import com.fliggy.bodymachine.model.MessageEvent;
import com.fliggy.bodymachine.utils.ToastUtils;
import com.fliggy.bodymachine.utils.print.PrintUtil;
import com.socks.library.KLog;
import io.realm.Realm;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends BasePrintActivity {

  @BindView(R.id.textView) TextView mTextView;
  @BindView(R.id.et_height) EditText mEtHeight;
  @BindView(R.id.et_age) EditText mEtAge;
  @BindView(R.id.male_rb) RadioButton mMaleRb;
  @BindView(R.id.famale_rb) RadioButton mFamaleRb;
  @BindView(R.id.sex_rg) RadioGroup mSexRg;
  @BindView(R.id.start) Button mStart;
  @BindView(R.id.start_test) Button mStartTest;
  @BindView(R.id.test) Button mTest;
  @BindView(R.id.spinner1) Spinner mSpinner1;
  @BindView(R.id.start_clear) Button mStartClear;
  @BindView(R.id.start_print) Button mStartPrint;
  @BindView(R.id.txt_cal_version) TextView mTxtCalVersion;
  private StringBuffer result = new StringBuffer();
  private MediaPlayer mediaPlayer;
  private SimpleSerialPortUtil mSerialPort;
  private String mWorkLevel = "00";
  private Realm realm;
  private BodyInfoModel mBodyInfoModel;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    EventBus.getDefault().register(this);
    realm = Realm.getDefaultInstance();
    ArrayAdapter<String> adapter =
        new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
            getResources().getStringArray(R.array.letter));
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    mSpinner1.setAdapter(adapter);
    mSpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mWorkLevel = "0" + position;
      }

      @Override public void onNothingSelected(AdapterView<?> parent) {

      }
    });
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
          if (result.length() < 6) {
            return;
          } else {
            //大于6的话，就可以知道字段长度了
            String mSubstring = result.substring(4, 6);
            int mInt = Integer.parseInt(mSubstring, 16);
            int full_lenght = mInt * 2 + 8;
            if (result.length() < full_lenght) {
              return;
            }
          }
          final String str = result.toString().trim();
          KLog.e("dicallc: " + str);
          final String code = str.substring(2, 4);
          runOnUiThread(new Runnable() {

            @Override public void run() {
              switch (Integer.parseInt(code, 16)) {
                case 1:
                  //模块回应初始化是否成功
                  break;
                case 2:
                  //上行体重数据
                  String wd = Utils.toResultHasPoint(str, 6, 10);
                  mTextView.setText("体重是: " + wd + "kg");
                  break;
                case 3:
                  //上行体脂数据
                  String s = Utils.toShowFinalResult(str);
                  mBodyInfoModel = com.fliggy.bodymachine.utils.Utils.toShowFinalResultModel(str);
                  mTextView.setText(s);
                  break;
                case 4:
                  // 上行测脂报错
                  String rt = Utils.toResult(str, 6, 8);
                  switch (rt) {
                    case "1":
                      mTextView.setText("错误参数非法");
                      break;
                    case "2":
                      mTextView.setText("站姿错误");
                      break;
                    case "3":
                      mTextView.setText("数据溢出");
                      break;
                  }

                  break;
                case 5:
                  //上行锁定体重
                  mTextView.setText("已经稳定了，可以进行下一步");
                  break;
                case 6:
                  //查询是否准备好了
                  String version = Utils.toResult(str, 6, 8);
                  mTxtCalVersion.setText("当前版本是: "+version);
                  break;
                case 7:
                  //用户数据回应是否收到
                  mTextView.setText("用户数据，模块已经收到");
                  break;
                case 9:
                  //清理回复
                  String clear_code = Utils.toResult(str, 6, 8);
                  switch (clear_code) {
                    case "1":
                      mTextView.setText("零点读取完成");
                      break;
                    case "2":
                      mTextView.setText("标定 50kg 完成");
                      break;
                    case "3":
                      mTextView.setText("标定 100kg 完成");
                      break;
                    case "4":
                      mTextView.setText("标定成功");
                      break;
                    case "5":
                      mTextView.setText("标定失败");
                      break;
                  }
                  break;
                //                case 8:
                //                  //测体脂回应
                //
                //                  break;
              }
              result.delete(0, result.length());
            }
          });
          // TODO: 2018/3/9 0009 检验失败怎么办

          //去除识别码，知道这条语句应该干什么

        } catch (Exception mE) {
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
          //initAudio();
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
    if (EventBus.getDefault().isRegistered(this)) {
      EventBus.getDefault().unregister(this);
    }
  }

  @OnClick({ R.id.start, R.id.start_test, R.id.test, R.id.start_clear, R.id.start_print })
  public void onViewClicked(View mView) {
    switch (mView.getId()) {
      case R.id.start:
        startTepOne();
        break;
      case R.id.start_test:
        startTepTwo();
        break;
      case R.id.test:
        TestModubus();
        break;
      case R.id.start_clear:
        StartClear();
        break;
      case R.id.start_print:
        StartPrint();
        break;
    }
  }

  private void StartPrint() {
    //if (null == mBodyInfoModel) {
    //  ToastUtils.showShortToast("还没有得到数据，无法打印");
    //  return;
    //}

    Intent mIntent = new Intent(this, ShowResultActivity.class);
    startActivity(mIntent);
    //buildProgressDialog("正在打印");
    //getPrintPic();
  }

  private void StartClear() {
    String str = Utils.sendClearCmd(true);
    mSerialPort.sendCmds(str);
  }

  private void TestModubus() {
    String str = "CA0106000000000000";
    mSerialPort.sendCmds(str);
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
    String str = Utils.loadUserInfoCmd(height, age, femail, mWorkLevel);
    mSerialPort.sendCmds(str);
  }

  /**
   * 发送可以开始命令
   */
  private void startTepTwo() {
    String str = Utils.sendStartCmd();
    mSerialPort.sendCmds(str);
  }

  //Runnable mRunnable = new Runnable() {
  //  @Override public void run() {
  //    String mImage = viewSaveToImage(mRootView);
  //    MessageEvent mMessageEvent = new MessageEvent(MessageEvent.GET_PIC, mImage);
  //    EventBus.getDefault().post(mMessageEvent);
  //  }
  //};

  @Subscribe(threadMode = ThreadMode.MAIN) public void Event(MessageEvent messageEvent) {
    switch (messageEvent.type) {
      case MessageEvent.GET_PIC:
        PrintUtil printUtil = new PrintUtil(this, messageEvent.content);
        break;
      case MessageEvent.PRINT_Y:
        cancelProgressDialog();
        break;
      case MessageEvent.PRINT_N:
        cancelProgressDialog();
        break;
    }
  }
}
