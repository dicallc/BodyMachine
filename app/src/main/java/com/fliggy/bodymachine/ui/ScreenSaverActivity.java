package com.fliggy.bodymachine.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.fliggy.bodymachine.R;
import com.fliggy.bodymachine.ui.video.MyGsyVideo;
import com.fliggy.bodymachine.ui.video.onConnectionFinishLinstener;
import com.fliggy.bodymachine.utils.FileStorageHelper;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import java.io.File;

public class ScreenSaverActivity extends AppCompatActivity implements onConnectionFinishLinstener {

  protected static final String TAG = "ScreenSaverActivity";
  private static PowerManager.WakeLock mWakeLock;
  @BindView(R.id.video_player) MyGsyVideo mVideoPlayer;
  private String mVideoPath;
  private String mVideoFilePath;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_screen_saver);
    ButterKnife.bind(this);
    PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
    mWakeLock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP
        | PowerManager.SCREEN_DIM_WAKE_LOCK
        | PowerManager.ON_AFTER_RELEASE, "SimpleTimer");
    String mAbsolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
    mVideoFilePath = mAbsolutePath + "/" + "BodyMachine";
    mVideoPath = mAbsolutePath + "/" + "BodyMachine/screen.mp4";
    if (!new File(mVideoPath).exists()){
      FileStorageHelper.copyFilesFromRaw(this,R.raw.screen,"screen.mp4", mVideoFilePath);
    }


  }
  @Override protected void onResume() {
    mWakeLock.acquire();
    startVideo();
    super.onResume();
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    GSYVideoManager.releaseAllVideos();
  }

  private void startVideo() {
    //Uri uri = Uri.parse("android.resource://" + getPackageName() + "/raw/" + R.raw.screen);
    mVideoPlayer.setUp("file://"+mVideoPath, true, "");
    //增加title
    //mVideoPlayer.getTitleTextView().setVisibility(View.GONE);
    //设置返回键
    //mVideoPlayer.getBackButton().setVisibility(View.GONE);
    mVideoPlayer.setOnClickLinstener(this);
    mVideoPlayer.setLooping(true);
    mVideoPlayer.startPlayLogic();
  }

  @Override protected void onPause() {
    mWakeLock.release();
    mVideoPlayer.onVideoPause();
    super.onPause();
  }

  @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_MENU) {
      //监控/拦截菜单键
      toMainUI();
    } else if (keyCode == KeyEvent.KEYCODE_HOME) {
      //由于Home键为系统键，此处不能捕获，需要重写onAttachedToWindow()
      toMainUI();
    }
    return super.onKeyDown(keyCode, event);
  }

  private void toMainUI() {
    Intent mIntent=new Intent(this,LoadUserActivity.class);
    startActivity(mIntent);
    finish();
  }

  @Override public boolean onTouchEvent(MotionEvent event) {
    toMainUI();
    return super.onTouchEvent(event);
  }

  //搜索键
  @Override public boolean onSearchRequested() {
    toMainUI();
    return super.onSearchRequested();
  }

  @Override public void onSuccess(int code, Object result) {
    toMainUI();
  }
}
