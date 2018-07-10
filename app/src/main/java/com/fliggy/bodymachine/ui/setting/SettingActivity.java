package com.fliggy.bodymachine.ui.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.fliggy.bodymachine.R;
import me.yokeyword.fragmentation.SupportActivity;

public class SettingActivity extends SupportActivity {

  @BindView(R.id.img_back) ImageView mImgBack;
  @BindView(R.id.fl_contain) FrameLayout mFlContain;
  @BindView(R.id.rl_contain) LinearLayout mRlContain;
  @BindView(R.id.txt_title_left) TextView mTxtTitleLeft;
  private SettingDateFragment mSettingDateFragment;
  private SettingHomeFragment mSettingHomeFragment;
  private SettingWeightFragment mSettingWeightFragment;
  private SettingPrintModelFragment mSettingPrintModelFragment;
  private SettingPrintOffestFragment mSettingPrintOffestFragment;
  private SettingIdFragment mSettingIdFragment;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_setting);
    ButterKnife.bind(this);
    mSettingDateFragment = SettingDateFragment.newInstance("", "");
    mSettingHomeFragment = SettingHomeFragment.newInstance("", "");
    mSettingWeightFragment = SettingWeightFragment.newInstance("", "");
    mSettingPrintModelFragment = SettingPrintModelFragment.newInstance("", "");
    mSettingPrintOffestFragment = SettingPrintOffestFragment.newInstance("", "");
    mSettingIdFragment = SettingIdFragment.newInstance("", "");
    loadMultipleRootFragment(R.id.fl_contain, 0, mSettingHomeFragment, mSettingDateFragment,mSettingWeightFragment,mSettingPrintModelFragment,mSettingPrintOffestFragment,mSettingIdFragment);
  }

  @OnClick({ R.id.img_back }) public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.img_back:
        finish();
        break;
    }
  }

  public void showDateUI() {
    mTxtTitleLeft.setText("DATE");
    mImgBack.setVisibility(View.GONE);
    showHideFragment(mSettingDateFragment, mSettingHomeFragment);
  }
  public void showWeightOffsetUI() {
    mTxtTitleLeft.setText("SCLAE OFFSET");
    mImgBack.setVisibility(View.GONE);
    showHideFragment(mSettingWeightFragment, mSettingHomeFragment);
  }
  public void showOrgin() {
    mTxtTitleLeft.setText("SET");
    mImgBack.setVisibility(View.VISIBLE);
    showHideFragment(mSettingHomeFragment);
  }
  public void showPrintModel() {
    mTxtTitleLeft.setText("PRINT");
    mImgBack.setVisibility(View.GONE);
    showHideFragment(mSettingPrintModelFragment);
  }
  public void showPrintOffet() {
    showHideFragment(mSettingPrintOffestFragment,mSettingPrintModelFragment);
  }
  public void showIdSetting() {
    mTxtTitleLeft.setText("ID");
    mImgBack.setVisibility(View.GONE);
    showHideFragment(mSettingIdFragment,mSettingHomeFragment);
  }
  public void gonePrintOffet() {
    showHideFragment(mSettingPrintModelFragment,mSettingPrintOffestFragment);
  }
}
