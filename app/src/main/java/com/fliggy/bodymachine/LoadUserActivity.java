package com.fliggy.bodymachine;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.fliggy.bodymachine.view.LoadHeightFragment;
import com.fliggy.bodymachine.view.LoadWeightFragment;
import com.socks.library.KLog;
import me.yokeyword.fragmentation.SupportActivity;

public class LoadUserActivity extends SupportActivity {

  @BindView(R.id.img_pre) ImageView mImgPre;
  @BindView(R.id.img_next) ImageView mImgNext;
  @BindView(R.id.show_text) TextView mShowText;
  private LoadHeightFragment mLoadHeightFragment;
  private LoadWeightFragment mLoadWeightFragment;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_load_user);
    ButterKnife.bind(this);
    if (findFragment(LoadWeightFragment.class) == null) {
      mLoadWeightFragment = LoadWeightFragment.newInstance("", "");
      mLoadHeightFragment = LoadHeightFragment.newInstance("", "");
      loadMultipleRootFragment(R.id.fl_container, 0, mLoadWeightFragment,
          mLoadHeightFragment);  // 加载根Fragment
    }
  }

  @OnClick({ R.id.img_pre, R.id.img_next }) public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.img_pre:
        KLog.e("img_pre");
        showHideFragment(mLoadHeightFragment, mLoadWeightFragment);
        break;
      case R.id.img_next:
        mShowText.setVisibility(View.GONE);
        KLog.e("img_next");
        showHideFragment(mLoadHeightFragment, mLoadWeightFragment);
        break;
    }
  }
}
