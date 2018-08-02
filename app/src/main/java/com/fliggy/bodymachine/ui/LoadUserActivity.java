package com.fliggy.bodymachine.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.fliggy.bodymachine.R;
import com.fliggy.bodymachine.ui.setting.SettingActivity;
import com.fliggy.bodymachine.utils.Constant;
import com.fliggy.bodymachine.utils.MyOnTouchListener;
import com.fliggy.bodymachine.view.LoadAgeFragment;
import com.fliggy.bodymachine.view.LoadHeightFragment;
import com.fliggy.bodymachine.view.LoadIdFragment;
import com.fliggy.bodymachine.view.LoadMaleFragment;
import com.fliggy.bodymachine.view.LoadResultFragment;
import com.fliggy.bodymachine.view.LoadWeightFragment;
import com.socks.library.KLog;
import java.util.ArrayList;
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * 首页
 */
public class LoadUserActivity extends SupportActivity {

  ArrayList<SupportFragment> mSupportFragments = new ArrayList<>();
  @BindView(R.id.txt_m) TextView mTxtM;
  @BindView(R.id.txt_a) TextView mTxtA;
  @BindView(R.id.txt_h) TextView mTxtH;
  @BindView(R.id.txt_w) TextView mTxtW;
  @BindView(R.id.rl_contain) RelativeLayout mRlContain;
  @BindView(R.id.fl_container) FrameLayout mFlContainer;
  @BindView(R.id.txt_load_data) TextView mTxtLoadData;
  @BindView(R.id.txt_title_id) TextView mTxtTitleId;
  @BindView(R.id.txt_title_height) TextView mTxtTitleHeight;
  @BindView(R.id.txt_title_age) TextView mTxtTitleAge;
  @BindView(R.id.txt_title_sex) TextView mTxtTitleSex;
  @BindView(R.id.ly_title_person_msg) LinearLayout mLyTitlePersonMsg;
  @BindView(R.id.logo) ImageView mLogo;
  @BindView(R.id.txt_i) TextView mTxtI;
  private LoadHeightFragment mLoadHeightFragment;
  private LoadWeightFragment mLoadWeightFragment;
  private LoadAgeFragment mLoadAgeFragment;
  private LoadMaleFragment mLoadMaleFragment;
  private int position = 0;
  private LoadResultFragment mLoadResultFragment;
  private int result_ui_code = 0x110;
  private float x1;
  private float y1;
  private LoadIdFragment mLoadIdFragment;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_load_user);
    ButterKnife.bind(this);
    initUI();
    //KLog.e("dicallc", "1.0.1 -13.05测试等待5分钟，重启");
    SerialPortHelp.getInstance();
    //手动申请权限,视频音频权限为同一个
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.
        WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(this, new String[] {
          Manifest.permission.WRITE_EXTERNAL_STORAGE
      }, 1);//权限返回码为1
    }
  }

  public TextView getTxtTitleId() {
    return mTxtTitleId;
  }

  public TextView getTxtTitleHeight() {
    return mTxtTitleHeight;
  }

  public TextView getTxtTitleAge() {
    return mTxtTitleAge;
  }

  public TextView getTxtTitleSex() {
    return mTxtTitleSex;
  }

  private void initUI() {
    if (findFragment(LoadWeightFragment.class) == null) {
      mLoadWeightFragment = LoadWeightFragment.newInstance("", "");
      mLoadIdFragment = LoadIdFragment.newInstance("", "");
      mLoadHeightFragment = LoadHeightFragment.newInstance("", "");
      mLoadAgeFragment = LoadAgeFragment.newInstance("", "");
      mLoadMaleFragment = LoadMaleFragment.newInstance("", "");
      mLoadResultFragment = LoadResultFragment.newInstance("", "");
      loadMultipleRootFragment(R.id.fl_container, 0, mLoadWeightFragment, mLoadIdFragment,
          mLoadHeightFragment, mLoadAgeFragment, mLoadMaleFragment, mLoadResultFragment);  // 加载根Fragment
      mSupportFragments.add(mLoadWeightFragment);
      mSupportFragments.add(mLoadIdFragment);
      mSupportFragments.add(mLoadHeightFragment);
      mSupportFragments.add(mLoadAgeFragment);
      mSupportFragments.add(mLoadMaleFragment);
      mSupportFragments.add(mLoadResultFragment);
    }
  }

  public void toSettingUI() {
    Intent intent = new Intent();
    intent.setClass(LoadUserActivity.this, SettingActivity.class);
    startActivity(intent);
    //设置切换动画，从右边进入，左边退出
    overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
  }

  public void toOrgin() {
    mTxtLoadData.setText("");
    showHideFragment(mSupportFragments.get(0), mSupportFragments.get(mSupportFragments.size() - 1));
    position = 0;
  }

  public void NextPre(boolean isNext) {
    if (isNext) {
      position++;
      showHideFragment(mSupportFragments.get(position), mSupportFragments.get(position - 1));
    } else {
      KLog.e("img_pre");
      if (position < 1) {
        return;
      }
      position--;
      showHideFragment(mSupportFragments.get(position), mSupportFragments.get(position + 1));
    }
    switchFunction();
  }
  public void NextPre(boolean isNext,boolean isShowId) {
    if (isNext) {
      position+=2;
      showHideFragment(mSupportFragments.get(position), mSupportFragments.get(position - 2));
    } else {
      KLog.e("img_pre");
      if (position < 1) {
        return;
      }
      position-=2;
      showHideFragment(mSupportFragments.get(position), mSupportFragments.get(position + 2));
    }
    switchFunction();
  }

  private void switchFunction() {
    switch (position) {
      case 0:
        mTxtW.setVisibility(View.VISIBLE);
        mTxtI.setVisibility(View.GONE);
        mTxtH.setVisibility(View.GONE);
        mTxtA.setVisibility(View.GONE);
        mTxtM.setVisibility(View.GONE);
        mTxtLoadData.setVisibility(View.GONE);
        break;
      case 1:
        mTxtW.setVisibility(View.GONE);
        mTxtI.setVisibility(View.VISIBLE);
        mTxtH.setVisibility(View.GONE);
        mTxtA.setVisibility(View.GONE);
        mTxtM.setVisibility(View.GONE);
        mTxtLoadData.setVisibility(View.GONE);
        break;
      case 2:
        mTxtH.setVisibility(View.VISIBLE);
        mTxtW.setVisibility(View.GONE);
        mTxtI.setVisibility(View.GONE);
        mTxtA.setVisibility(View.GONE);
        mTxtM.setVisibility(View.GONE);
        mTxtLoadData.setVisibility(View.GONE);
        break;
      case 3:
        mTxtA.setVisibility(View.VISIBLE);
        mTxtW.setVisibility(View.GONE);
        mTxtI.setVisibility(View.GONE);
        mTxtH.setVisibility(View.GONE);
        mTxtM.setVisibility(View.GONE);
        mTxtLoadData.setVisibility(View.GONE);
        break;
      case 4:
        mTxtM.setVisibility(View.VISIBLE);
        mTxtW.setVisibility(View.GONE);
        mTxtI.setVisibility(View.GONE);
        mTxtH.setVisibility(View.GONE);
        mTxtA.setVisibility(View.GONE);
        mTxtLoadData.setVisibility(View.GONE);
        mLyTitlePersonMsg.setVisibility(View.GONE);
        break;
    }
  }

  public void ShowMeasureUI(String sex) {
    Intent mIntent = new Intent(this, MeasureActivity.class);
    mIntent.putExtra("INTENT_SEX",sex);
    startActivityForResult(mIntent, result_ui_code);
  }

  public void showResultUI() {
    position++;
    showHideFragment(mSupportFragments.get(position), mSupportFragments.get(position - 1));
    mTxtW.setVisibility(View.GONE);
    mTxtH.setVisibility(View.GONE);
    mTxtA.setVisibility(View.GONE);
    mTxtM.setVisibility(View.GONE);
    mLogo.setVisibility(View.GONE);
    mTxtLoadData.setVisibility(View.VISIBLE);
    mTxtLoadData.setText("DETECTION RESULT");
    mLyTitlePersonMsg.setVisibility(View.VISIBLE);
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == Constant.TEST_DATA_ERRO) {

      NextPre(false);
    } else if (requestCode == result_ui_code) {
      showResultUI();
    }
  }

  // 保存MyTouchListener接口的列表
  private ArrayList<MyOnTouchListener> myTouchListeners = new ArrayList<MyOnTouchListener>();

  /**
   * 提供给Fragment通过getActivity()方法来注册自己的触摸事件的方法
   */
  public void registerMyTouchListener(MyOnTouchListener listener) {
    myTouchListeners.add(listener);
  }

  /**
   * 提供给Fragment通过getActivity()方法来取消注册自己的触摸事件的方法
   */
  public void unRegisterMyTouchListener(MyOnTouchListener listener) {
    myTouchListeners.remove(listener);
  }

  /**
   * 分发触摸事件给所有注册了MyTouchListener的接口
   */
  @Override public boolean dispatchTouchEvent(MotionEvent ev) {
    try {
      myTouchListeners.get(position).onTouchEvent(ev);
    } catch (Exception e) {
      KLog.e(e.toString());
    }
    return super.dispatchTouchEvent(ev);
  }

  @Override protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }
}
