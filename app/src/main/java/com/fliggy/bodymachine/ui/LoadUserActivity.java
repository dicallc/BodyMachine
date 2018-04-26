package com.fliggy.bodymachine.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.fliggy.bodymachine.R;
import com.fliggy.bodymachine.view.LoadAgeFragment;
import com.fliggy.bodymachine.view.LoadDataingFragment;
import com.fliggy.bodymachine.view.LoadHeightFragment;
import com.fliggy.bodymachine.view.LoadMaleFragment;
import com.fliggy.bodymachine.view.LoadWeightFragment;
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
  private LoadHeightFragment mLoadHeightFragment;
  private LoadWeightFragment mLoadWeightFragment;
  private LoadAgeFragment mLoadAgeFragment;
  private LoadMaleFragment mLoadMaleFragment;
  private int position = 0;
  private float mPosX;
  private float mPosY;
  private float mCurPosX;
  private float mCurPosY;
  private LoadDataingFragment mLoadDataingFragment;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_load_user);
    ButterKnife.bind(this);
    if (findFragment(LoadWeightFragment.class) == null) {
      mLoadWeightFragment = LoadWeightFragment.newInstance("", "");
      mLoadHeightFragment = LoadHeightFragment.newInstance("", "");
      mLoadAgeFragment = LoadAgeFragment.newInstance("", "");
      mLoadMaleFragment = LoadMaleFragment.newInstance("", "");
      mLoadDataingFragment = LoadDataingFragment.newInstance("", "");
      loadMultipleRootFragment(R.id.fl_container, 0, mLoadWeightFragment, mLoadHeightFragment,
          mLoadAgeFragment, mLoadMaleFragment);  // 加载根Fragment
      mSupportFragments.add(mLoadWeightFragment);
      mSupportFragments.add(mLoadHeightFragment);
      mSupportFragments.add(mLoadAgeFragment);
      mSupportFragments.add(mLoadMaleFragment);
    }
  }

  @Override public boolean onTouchEvent(MotionEvent event) {
    //继承了Activity的onTouchEvent方法，直接监听点击事件
    switch (event.getAction()) {

      case MotionEvent.ACTION_DOWN:
        mPosX = event.getX();
        mPosY = event.getY();
        break;
      case MotionEvent.ACTION_MOVE:
        mCurPosX = event.getX();
        mCurPosY = event.getY();

        break;
      case MotionEvent.ACTION_UP:
        if (mCurPosX - mPosX > 0 && (Math.abs(mCurPosX - mPosX) > 25)) {
          KLog.e("向左滑動");

          //向左滑動
        } else if (mCurPosX - mPosX < 0 && (Math.abs(mCurPosX - mPosX) > 25)) {
          toSettingUI();
        }
        break;
    }
    return super.onTouchEvent(event);
  }

  public void toSettingUI() {
    Intent intent = new Intent();
    intent.setClass(LoadUserActivity.this, SettingActivity.class);
    startActivity(intent);
    //设置切换动画，从右边进入，左边退出
    overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
  }

 public void NextPre(boolean isNext) {
    if (isNext){
      if (position > mSupportFragments.size() - 2) {
        Intent mIntent = new Intent(this, MeasureActivity.class);
        startActivity(mIntent);
        return;
      }
      position++;
      //mShowText.setVisibility(View.GONE);
      showHideFragment(mSupportFragments.get(position), mSupportFragments.get(position - 1));
    }else{
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
        //mShowText.setVisibility(View.VISIBLE);
        break;
      case 1:
        mTxtH.setVisibility(View.VISIBLE);
        mTxtW.setVisibility(View.GONE);
        mTxtA.setVisibility(View.GONE);
        mTxtM.setVisibility(View.GONE);
        //mShowText.setVisibility(View.GONE);
        //mDeviderTop.setVisibility(View.GONE);
        //mDeviderBottom.setVisibility(View.GONE);
        break;
      case 2:
        mTxtA.setVisibility(View.VISIBLE);
        mTxtW.setVisibility(View.GONE);
        mTxtH.setVisibility(View.GONE);
        mTxtM.setVisibility(View.GONE);
        //mShowText.setVisibility(View.GONE);
        break;
      case 3:
        mTxtM.setVisibility(View.VISIBLE);
        mTxtW.setVisibility(View.GONE);
        mTxtH.setVisibility(View.GONE);
        mTxtA.setVisibility(View.GONE);
        //mShowText.setVisibility(View.VISIBLE);
        mTxtLoadData.setVisibility(View.GONE);
        mLyTitlePersonMsg.setVisibility(View.GONE);
        //mShowText.setText("请选择性别");
        break;
    }
  }
}
