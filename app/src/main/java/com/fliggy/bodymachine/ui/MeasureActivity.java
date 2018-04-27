package com.fliggy.bodymachine.ui;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.VideoView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.fliggy.bodymachine.R;

public class MeasureActivity extends AppCompatActivity {

  @BindView(R.id.videoView) VideoView mVideoView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_measure);
    ButterKnife.bind(this);
    finish();
    return;
    //setupVideo();
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
    stopPlaybackVideo();
  }

  private void stopPlaybackVideo() {
    try {
      mVideoView.stopPlayback();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


}
