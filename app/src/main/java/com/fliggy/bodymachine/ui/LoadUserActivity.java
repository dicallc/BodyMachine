package com.fliggy.bodymachine.ui;

import android.content.Intent;
import android.os.Bundle;
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
import com.fliggy.bodymachine.utils.MyOnTouchListener;
import com.fliggy.bodymachine.view.LoadAgeFragment;
import com.fliggy.bodymachine.view.LoadHeightFragment;
import com.fliggy.bodymachine.view.LoadMaleFragment;
import com.fliggy.bodymachine.view.LoadResultFragment;
import com.fliggy.bodymachine.view.LoadWeightFragment;
import com.fliggy.bodymachine.view.WaitStandFragment;
import com.socks.library.KLog;
import java.util.ArrayList;
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;

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
  private LoadHeightFragment mLoadHeightFragment;
  private LoadWeightFragment mLoadWeightFragment;
  private LoadAgeFragment mLoadAgeFragment;
  private LoadMaleFragment mLoadMaleFragment;
  private int position = 0;
  private LoadResultFragment mLoadResultFragment;
  private int result_ui_code = 0x110;
  private float x1;
  private float y1;
  private WaitStandFragment mWaitStandFragment;


  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_load_user);
    ButterKnife.bind(this);
    initUI();
    SerialPortHelp.getInstance();
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
      mLoadHeightFragment = LoadHeightFragment.newInstance("", "");
      mLoadAgeFragment = LoadAgeFragment.newInstance("", "");
      mLoadMaleFragment = LoadMaleFragment.newInstance("", "");
      mWaitStandFragment = WaitStandFragment.newInstance("", "");
      mLoadResultFragment = LoadResultFragment.newInstance("", "");
      loadMultipleRootFragment(R.id.fl_container, 0, mLoadWeightFragment, mLoadHeightFragment,
          mLoadAgeFragment, mLoadMaleFragment, mLoadResultFragment,mWaitStandFragment);  // 加载根Fragment
      mSupportFragments.add(mLoadWeightFragment);
      mSupportFragments.add(mLoadHeightFragment);
      mSupportFragments.add(mLoadAgeFragment);
      mSupportFragments.add(mLoadMaleFragment);
      mSupportFragments.add(mWaitStandFragment);
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
    switch (position) {
      case 0:
        mTxtW.setVisibility(View.VISIBLE);
        mTxtH.setVisibility(View.GONE);
        mTxtA.setVisibility(View.GONE);
        mTxtM.setVisibility(View.GONE);
        break;
      case 1:
        mTxtH.setVisibility(View.VISIBLE);
        mTxtW.setVisibility(View.GONE);
        mTxtA.setVisibility(View.GONE);
        mTxtM.setVisibility(View.GONE);
        break;
      case 2:
        mTxtA.setVisibility(View.VISIBLE);
        mTxtW.setVisibility(View.GONE);
        mTxtH.setVisibility(View.GONE);
        mTxtM.setVisibility(View.GONE);
        break;
      case 3:
        mTxtM.setVisibility(View.VISIBLE);
        mTxtW.setVisibility(View.GONE);
        mTxtH.setVisibility(View.GONE);
        mTxtA.setVisibility(View.GONE);
        mTxtLoadData.setVisibility(View.GONE);
        mLyTitlePersonMsg.setVisibility(View.GONE);
        break;
    }
  }

  public void ShowMeasureUI() {
    Intent mIntent = new Intent(this, MeasureActivity.class);
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
    if (requestCode == result_ui_code) {
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
    myTouchListeners.get(position).onTouchEvent(ev);
    return super.dispatchTouchEvent(ev);
  }


}
