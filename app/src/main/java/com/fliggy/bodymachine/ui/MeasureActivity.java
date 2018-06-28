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
 * 播放动画 发送数据
 */
public class MeasureActivity extends AppCompatActivity {

  @BindView(R.id.videoView) VideoView mVideoView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_measure);
    ButterKnife.bind(this);
    EventBus.getDefault().register(this);
    setupVideo();
    startMesure();
  }

  private void setupVideo() {
    mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
      @Override
      public void onPrepared(MediaPlayer mp) {
        mVideoView.start();
      }
    });
    mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
      @Override
      public void onCompletion(MediaPlayer mp) {
        stopPlaybackVideo();
      }
    });
    mVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
      @Override
      public boolean onError(MediaPlayer mp, int what, int extra) {
        stopPlaybackVideo();
        return true;
      }
    });

    try {
      Uri uri = Uri.parse("android.resource://" + getPackageName() + "/raw/" + R.raw.m_male);
      mVideoView.setVideoURI(uri);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  private void startMesure() {
      String str = Utils.sendStartCmd();
      SimpleSerialPortUtil.getInstance().sendCmds(str);
  }
  @Override
  protected void onResume() {
    super.onResume();
    if (!mVideoView.isPlaying()) {
      mVideoView.resume();
    }
  }

  @Override
  protected void onPause() {
    super.onPause();
    if (mVideoView.canPause()) {
      mVideoView.pause();
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    EventBus.getDefault().unregister(this);
    stopPlaybackVideo();
  }

  private void stopPlaybackVideo() {
    try {
      mVideoView.stopPlayback();
      finish();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void Event(SerialEvent messageEvent) {
    switch (messageEvent.type) {
      case SerialEvent.LOAD_USER_DATA:
          KLog.e("LOAD_USER_DATA");
          break;
      case SerialEvent.LOAD_USER_DATA_ERRO:
        final String erromsg = messageEvent.content;
          runOnUiThread(new Runnable() {
              @Override
              public void run() {
                  //erromsg="姿势可能错误了";
                  final MaterialDialog mMaterialDialog = initDialog(erromsg);
                  mMaterialDialog.setPositiveButton("OK", new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
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
    final MaterialDialog mMaterialDialog = new MaterialDialog(this).setTitle("友情提示")
        .setMessage(msg);
    return mMaterialDialog;
  }
}
