package com.fliggy.bodymachine.ui;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.VideoView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.fliggy.bodymachine.R;

public class ScreenSaverActivity extends AppCompatActivity {

  protected static final String TAG = "ScreenSaverActivity";
  private static PowerManager.WakeLock mWakeLock;
  @BindView(R.id.video) VideoView mVideoPlayer;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_screen_saver);
    ButterKnife.bind(this);
    PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
    mWakeLock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP
        | PowerManager.SCREEN_DIM_WAKE_LOCK
        | PowerManager.ON_AFTER_RELEASE, "SimpleTimer");
  }

  @Override protected void onResume() {
    mWakeLock.acquire();
    startVideo();
    super.onResume();
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    if (mVideoPlayer != null) {
      mVideoPlayer.suspend();  //将VideoView所占用的资源释放掉
      mVideoPlayer = null;
    }
  }

  private void startVideo() {
    Uri uri = Uri.parse("android.resource://" + getPackageName() + "/raw/" + R.raw.screen);
    mVideoPlayer.setVideoURI(uri);
    mVideoPlayer.requestFocus();
    mVideoPlayer.start();

    mVideoPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
      @Override public void onPrepared(MediaPlayer mp) {
        mp.setLooping(true);
      }
    });
  }

  @Override protected void onPause() {
    mWakeLock.release();
    super.onPause();
  }

  @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_MENU) {
      //监控/拦截菜单键
      toMainUI();
    } else if (keyCode == KeyEvent.KEYCODE_HOME) {
      //由于Home键为系统键，此处不能捕获，需要重写onAttachedToWindow()
      toMainUI();
      //return false;
    }
    return super.onKeyDown(keyCode, event);
  }

  private void toMainUI() {
    Intent mIntent=new Intent(this,LoadUserActivity.class);
    startActivity(mIntent);
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
}
