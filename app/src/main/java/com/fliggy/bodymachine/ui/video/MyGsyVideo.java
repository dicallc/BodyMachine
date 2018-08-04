package com.fliggy.bodymachine.ui.video;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import com.fliggy.bodymachine.R;
import com.fliggy.bodymachine.ui.LoadUserActivity;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

/**
 * Created by dicallc on 2018/8/4.
 */
public class MyGsyVideo extends StandardGSYVideoPlayer {
  public MyGsyVideo(Context context, Boolean fullFlag) {
    super(context, fullFlag);
  }

  public MyGsyVideo(Context context) {
    super(context);
  }

  public MyGsyVideo(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override protected void init(Context context) {
    super.init(context);
    //setAlphaTo0f(mLockScreen, mBottomContainer, mBottomProgressBar);
  }

  @Override public int getLayoutId() {
    return R.layout.view_video_layout_standard;
  }

  @Override protected void onClickUiToggle() {
    Intent mIntent=new Intent(mContext,LoadUserActivity.class);
    mContext.startActivity(mIntent);
  }

  protected void setAlphaTo0f(View... vs) {
    for (View view : vs) {
      view.setAlpha(0.0f);
    }
  }
}
