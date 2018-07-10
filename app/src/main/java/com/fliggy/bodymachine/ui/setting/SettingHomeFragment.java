package com.fliggy.bodymachine.ui.setting;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.fliggy.bodymachine.R;
import com.fliggy.bodymachine.ui.MainActivity;
import com.xw.repo.BubbleSeekBar;
import me.yokeyword.fragmentation.SupportFragment;

public class SettingHomeFragment extends SupportFragment {
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";
  @BindView(R.id.txt_voice) TextView mTxtVoice;
  @BindView(R.id.seek_voice) BubbleSeekBar mSeekVoice;
  @BindView(R.id.txt_bright) TextView mTxtBright;
  @BindView(R.id.seek_bright) BubbleSeekBar mSeekBright;
  @BindView(R.id.ly_print) LinearLayout mLyPrint;
  @BindView(R.id.ly_calender) LinearLayout mLyCalender;
  @BindView(R.id.ly_weight) LinearLayout mLyWeight;
  @BindView(R.id.ly_mark_weight) LinearLayout mLyMarkWeight;
  Unbinder unbinder;
  private String mParam1;
  private String mParam2;

  public SettingHomeFragment() {
  }

  public static SettingHomeFragment newInstance(String param1, String param2) {
    SettingHomeFragment fragment = new SettingHomeFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    args.putString(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mParam1 = getArguments().getString(ARG_PARAM1);
      mParam2 = getArguments().getString(ARG_PARAM2);
    }
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_setting_home, container, false);
    unbinder = ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
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
    final AudioManager am = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
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
  @OnClick({R.id.ly_print, R.id.ly_calender, R.id.ly_weight,R.id.ly_mark_weight,R.id.ly_id})
  public void onViewClicked(View view) {
  SettingActivity mSettingActivity= (SettingActivity) getActivity();
    switch (view.getId()) {
      case R.id.ly_print:
        mSettingActivity.showPrintModel();
        break;
      case R.id.ly_calender:
        mSettingActivity.showDateUI();
        break;
      case R.id.ly_weight:
        mSettingActivity.showWeightOffsetUI();
        break;
      case R.id.ly_id:
        mSettingActivity.showIdSetting();
        break;
      case R.id.ly_mark_weight:
        Intent mIntent=new Intent(getActivity(), MainActivity.class);
        startActivity(mIntent);
        break;
    }
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }
  /**
   * 设置当前屏幕亮度的模式
   * SCREEN_BRIGHTNESS_MODE_AUTOMATIC=1 为自动调节屏幕亮度
   * SCREEN_BRIGHTNESS_MODE_MANUAL=0  为手动调节屏幕亮度
   */
  private void setScreenMode(int paramInt) {
    try {
      Settings.System.putInt(getActivity().getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE,
          paramInt);
    } catch (Exception localException) {
      localException.printStackTrace();
    }
  }

  private void setScreenBrightness(int process) {

    //设置当前窗口的亮度值.这种方法需要权限android.permission.WRITE_EXTERNAL_STORAGE
    WindowManager.LayoutParams localLayoutParams = getActivity().getWindow().getAttributes();
    float f = process / 255.0F;
    localLayoutParams.screenBrightness = f;
    getActivity().getWindow().setAttributes(localLayoutParams);
    //修改系统的亮度值,以至于退出应用程序亮度保持
    saveBrightness(getActivity().getContentResolver(), process);
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
          Settings.System.getInt(getActivity().getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
    } catch (Exception localException) {

    }
    return screenBrightness;
  }
}
