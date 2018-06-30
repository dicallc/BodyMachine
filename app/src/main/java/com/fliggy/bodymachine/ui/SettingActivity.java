package com.fliggy.bodymachine.ui;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.fliggy.bodymachine.R;
import com.xw.repo.BubbleSeekBar;

public class SettingActivity extends AppCompatActivity {

  @BindView(R.id.img_back) ImageView mImgBack;
  @BindView(R.id.seek_voice) BubbleSeekBar mSeekVoice;
  @BindView(R.id.seek_bright) BubbleSeekBar mSeekBright;
  @BindView(R.id.rl_contain) LinearLayout mRlContain;
  @BindView(R.id.ly_print) LinearLayout mLyPrint;
  @BindView(R.id.ly_calender) LinearLayout mLyCalender;
  @BindView(R.id.ly_weight) LinearLayout mLyWeight;
  @BindView(R.id.txt_voice) TextView mTxtVoice;
  @BindView(R.id.txt_bright) TextView mTxtBright;
  @BindView(R.id.ly_mark_weight) LinearLayout mLyMarkWeight;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_setting);
    ButterKnife.bind(this);
    initView();
  }

  private void initView() {
    int mScreenBrightness = getScreenBrightness();
    mSeekBright.setProgress(mScreenBrightness);
    mTxtBright.setText(mScreenBrightness + "");
    mSeekBright.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
      @Override public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress,
          float progressFloat) {
        mTxtBright.setText(progress + "");
        setScreenBrightness(progress);
      }

      @Override public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress,
          float progressFloat) {

      }

      @Override public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress,
          float progressFloat) {

      }
    });
    final AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
    //获取当前音量
    int currentVolume = am.getStreamVolume(AudioManager.STREAM_MUSIC);
    mTxtVoice.setText(currentVolume + "");
    mSeekVoice.setProgress(currentVolume);
    mSeekVoice.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
      @Override public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress,
          float progressFloat) {
        mTxtVoice.setText(progress + "");
        am.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
      }

      @Override public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress,
          float progressFloat) {

      }

      @Override public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress,
          float progressFloat) {

      }
    });
  }

  @OnClick({ R.id.img_back, R.id.ly_print, R.id.ly_calender, R.id.ly_weight,R.id.ly_mark_weight})
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.img_back:
        finish();
        break;
      case R.id.ly_print:
        break;
      case R.id.ly_calender:
        break;
      case R.id.ly_weight:
        break;
      case R.id.ly_mark_weight:
        Intent mIntent=new Intent(this, MainActivity.class);
        startActivity(mIntent);
        break;
    }
  }

  /**
   * 设置当前屏幕亮度的模式
   * SCREEN_BRIGHTNESS_MODE_AUTOMATIC=1 为自动调节屏幕亮度
   * SCREEN_BRIGHTNESS_MODE_MANUAL=0  为手动调节屏幕亮度
   */
  private void setScreenMode(int paramInt) {
    try {
      Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE,
          paramInt);
    } catch (Exception localException) {
      localException.printStackTrace();
    }
  }

  private void setScreenBrightness(int process) {

    //设置当前窗口的亮度值.这种方法需要权限android.permission.WRITE_EXTERNAL_STORAGE
    WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
    float f = process / 255.0F;
    localLayoutParams.screenBrightness = f;
    getWindow().setAttributes(localLayoutParams);
    //修改系统的亮度值,以至于退出应用程序亮度保持
    saveBrightness(getContentResolver(), process);
  }

  public static void saveBrightness(ContentResolver resolver, int brightness) {
    //改变系统的亮度值
    //这里需要权限android.permission.WRITE_SETTINGS
    //设置为手动调节模式
    Settings.System.putInt(resolver, Settings.System.SCREEN_BRIGHTNESS_MODE,
        Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
    //保存到系统中
    Uri uri = Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS);
    Settings.System.putInt(resolver, Settings.System.SCREEN_BRIGHTNESS, brightness);
    resolver.notifyChange(uri, null);
  }

  /**
   * 获得当前屏幕亮度值  0--255
   */
  private int getScreenBrightness() {
    int screenBrightness = 255;
    try {
      screenBrightness =
          Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
    } catch (Exception localException) {

    }
    return screenBrightness;
  }
}
