package com.fliggy.bodymachine.view;

import android.media.MediaPlayer;
import com.fliggy.bodymachine.base.SwiperFragment;

/**
 * Created by dicallc on 2018/8/6.
 */
public class BaseAudioFragment extends SwiperFragment{

  private MediaPlayer mediaPlayer;

  protected void PlayAudio(int resid) {
    try {
      if (null == mediaPlayer) mediaPlayer = MediaPlayer.create(getActivity(), resid);//重新设置要播放的音频

      if (!mediaPlayer.isPlaying())
      mediaPlayer.start();//开始播放
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override public void onDestroy() {
    super.onDestroy();
    if (null!=mediaPlayer)
      mediaPlayer.release();
  }
}
