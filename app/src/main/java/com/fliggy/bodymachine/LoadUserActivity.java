package com.fliggy.bodymachine;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.fliggy.bodymachine.view.LoadAgeFragment;
import com.fliggy.bodymachine.view.LoadHeightFragment;
import com.fliggy.bodymachine.view.LoadMaleFragment;
import com.fliggy.bodymachine.view.LoadWeightFragment;
import com.socks.library.KLog;
import java.util.ArrayList;
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;

public class LoadUserActivity extends SupportActivity {

  @BindView(R.id.img_pre) ImageView mImgPre;
  @BindView(R.id.img_next) ImageView mImgNext;
  @BindView(R.id.show_text) TextView mShowText;
  ArrayList<SupportFragment> mSupportFragments = new ArrayList<>();
  @BindView(R.id.txt_m) TextView mTxtM;
  @BindView(R.id.txt_a) TextView mTxtA;
  @BindView(R.id.txt_h) TextView mTxtH;
  @BindView(R.id.txt_w) TextView mTxtW;
  private LoadHeightFragment mLoadHeightFragment;
  private LoadWeightFragment mLoadWeightFragment;
  private LoadAgeFragment mLoadAgeFragment;
  private LoadMaleFragment mLoadMaleFragment;
  private int position = 0;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_load_user);
    ButterKnife.bind(this);
    if (findFragment(LoadWeightFragment.class) == null) {
      mLoadWeightFragment = LoadWeightFragment.newInstance("", "");
      mLoadHeightFragment = LoadHeightFragment.newInstance("", "");
      mLoadAgeFragment = LoadAgeFragment.newInstance("", "");
      mLoadMaleFragment = LoadMaleFragment.newInstance("", "");
      loadMultipleRootFragment(R.id.fl_container, 0, mLoadWeightFragment,
          mLoadHeightFragment,mLoadAgeFragment,mLoadMaleFragment);  // 加载根Fragment
      mSupportFragments.add(mLoadWeightFragment);
      mSupportFragments.add(mLoadHeightFragment);
      mSupportFragments.add(mLoadAgeFragment);
      mSupportFragments.add(mLoadMaleFragment);
    }
  }

  @OnClick({ R.id.img_pre, R.id.img_next }) public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.img_pre:
        KLog.e("img_pre");
        if (position < 1) {
          return;
        }

        position--;
        showHideFragment(mSupportFragments.get(position), mSupportFragments.get(position + 1));
        break;
      case R.id.img_next:
        if (position > mSupportFragments.size() - 2) return;
        position++;
        mShowText.setVisibility(View.GONE);
        showHideFragment(mSupportFragments.get(position), mSupportFragments.get(position - 1));
        break;
    }
    switch (position){
      case 0:
        mTxtW.setText(R.string.WEIGHT);
        mTxtH.setText(R.string.HEIGHT);
        mTxtA.setText(R.string.AGE);
        mTxtM.setText(R.string.MALE);
        mTxtW.setBackgroundResource(R.mipmap.ic_weight_bg);
        mTxtH.setBackgroundResource(R.mipmap.ic__height_bg);
        mTxtA.setBackgroundResource(R.mipmap.ic_age_bg);
        mTxtM.setBackgroundResource(R.mipmap.ic_sex_bg);

        mShowText.setVisibility(View.VISIBLE);
        break;
      case 1:
        mTxtW.setText(R.string.HEIGHT);
        mTxtH.setText(R.string.WEIGHT);
        mTxtA.setText(R.string.AGE);
        mTxtM.setText(R.string.MALE);
        mTxtW.setBackgroundResource(R.mipmap.ic_sex_bg);
        mTxtH.setBackgroundResource(R.mipmap.ic_weight_bg);
        mTxtA.setBackgroundResource(R.mipmap.ic__height_bg);
        mTxtM.setBackgroundResource(R.mipmap.ic_age_bg);
        mShowText.setVisibility(View.GONE);
        break;
      case 2:
        mTxtW.setText(R.string.AGE);
        mTxtH.setText(R.string.WEIGHT);
        mTxtA.setText(R.string.HEIGHT);
        mTxtM.setText(R.string.MALE);
        mTxtW.setBackgroundResource(R.mipmap.ic_age_bg);
        mTxtH.setBackgroundResource(R.mipmap.ic_sex_bg);
        mTxtA.setBackgroundResource(R.mipmap.ic_weight_bg);
        mTxtM.setBackgroundResource(R.mipmap.ic__height_bg);
        mShowText.setVisibility(View.GONE);
        break;
      case 3:
        mTxtW.setText(R.string.MALE);
        mTxtH.setText(R.string.WEIGHT);
        mTxtA.setText(R.string.HEIGHT);
        mTxtM.setText(R.string.AGE);
        mTxtW.setBackgroundResource(R.mipmap.ic__height_bg);
        mTxtH.setBackgroundResource(R.mipmap.ic_age_bg);
        mTxtA.setBackgroundResource(R.mipmap.ic_sex_bg);
        mTxtM.setBackgroundResource(R.mipmap.ic_weight_bg);
        mShowText.setVisibility(View.VISIBLE);
        mShowText.setText("请选择性别");
        break;

    }
  }
}
