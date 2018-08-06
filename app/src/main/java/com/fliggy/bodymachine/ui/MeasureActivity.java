package com.fliggy.bodymachine.ui;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.serialport.utils.SimpleSerialPortUtil;
import android.serialport.utils.Utils;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.VideoView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.fliggy.bodymachine.R;
import com.fliggy.bodymachine.model.SerialEvent;
import com.fliggy.bodymachine.utils.Constant;
import com.socks.library.KLog;
import me.drakeet.materialdialog.MaterialDialog;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 同时播放动画和语音 分为站立，测量 发送数据
 */
public class MeasureActivity extends AppCompatActivity {

  @BindView(R.id.videoView) VideoView mVideoView;
  private String mSex;
  private boolean mIsStand;
  private int mStandUrl;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_measure);
    ButterKnife.bind(this);
    EventBus.getDefault().register(this);
    mSex = getIntent().getStringExtra("INTENT_SEX");
    PlayAudio(R.raw.standup);

  }

  private MediaPlayer mediaPlayer;

  protected void PlayAudio(int resid) {
    try {
      if (null == mediaPlayer) mediaPlayer = MediaPlayer.create(this, resid);

      mediaPlayer.setOnCompletionListener(mOnCompletionListener);
      mediaPlayer.setOnPreparedListener(mOnMp3PreparedListener);
      mediaPlayer.start();//开始播放
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  MediaPlayer.OnCompletionListener mOnCompletionListener=new MediaPlayer.OnCompletionListener() {
    @Override public void onCompletion(MediaPlayer mp) {
      stopPlaybackVideo();
    }
  };

  MediaPlayer.OnPreparedListener mOnMp3PreparedListener= new MediaPlayer.OnPreparedListener() {
    @Override public void onPrepared(MediaPlayer mp) {
      setupVideo(true);
    }
  };

  private void setupVideo(boolean isStand) {
    mIsStand=isStand;
    if (isStand){
      mVideoView.setOnCompletionListener(null);
      if (mSex.equals("1")) {
        //男
        mStandUrl = R.raw.stand_man;
      } else {
        mStandUrl = R.raw.stand_woman;
      }
    }else{
      mVideoView.setOnCompletionListener(mOnVideoCompletionListener);
      if (mSex.equals("1")) {
        //男
        mStandUrl = R.raw.meature_man;
      } else {
        mStandUrl = R.raw.meature_woman;
      }
    }

    mVideoView.setOnPreparedListener(mListener);
    try {
      Uri uri = Uri.parse("android.resource://" + getPackageName() + "/raw/" + mStandUrl);
      mVideoView.setVideoURI(uri);
      mVideoView.requestFocus();
      mVideoView.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  MediaPlayer.OnPreparedListener mListener = new MediaPlayer.OnPreparedListener() {
    @Override public void onPrepared(MediaPlayer mp) {
      if (mIsStand)
      mp.setLooping(true);
      else{
        mp.setLooping(false);
      }
    }
  };

  MediaPlayer.OnCompletionListener mOnVideoCompletionListener=new MediaPlayer.OnCompletionListener() {
    @Override public void onCompletion(MediaPlayer mp) {
      finish();
    }
  };

  private void releaseMedea() {
    mVideoView.stopPlayback();
    mediaPlayer.stop();
    mediaPlayer.release();
  }

  private void startMesure() {
    String str = Utils.sendStartCmd();
    SimpleSerialPortUtil.getInstance().sendCmds(str);
  }

  @Override protected void onResume() {
    super.onResume();
    if (!mVideoView.isPlaying()) {
      mVideoView.resume();
    }
  }

  @Override protected void onPause() {
    super.onPause();
    if (mVideoView.canPause()) {
      mVideoView.pause();
    }
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    EventBus.getDefault().unregister(this);
    releaseMedea();
  }

  private void stopPlaybackVideo() {
    try {
      startMesure();
      setupVideo(false);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void Event(SerialEvent messageEvent) {
    switch (messageEvent.type) {
      case SerialEvent.LOAD_USER_DATA:
        KLog.e("LOAD_USER_DATA");
        break;
      case SerialEvent.LOAD_USER_DATA_ERRO:
        final String erromsg = messageEvent.content;
        runOnUiThread(new Runnable() {
          @Override public void run() {
            //erromsg="姿势可能错误了";
            final MaterialDialog mMaterialDialog = initDialog(erromsg);
            mMaterialDialog.setPositiveButton("OK", new View.OnClickListener() {
              @Override public void onClick(View v) {
                mMaterialDialog.dismiss();
                // TODO: 2018/6/28 回到上个界面
                setResult(Constant.TEST_DATA_ERRO);
                finish();
              }
            });
            mMaterialDialog.show();
          }
        });

        break;
    }
  }

  private MaterialDialog initDialog(String msg) {
    final MaterialDialog mMaterialDialog =
        new MaterialDialog(this).setTitle("友情提示").setMessage(msg);
    return mMaterialDialog;
  }
}
