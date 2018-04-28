package com.fliggy.bodymachine.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import com.fliggy.bodymachine.ui.LoadUserActivity;
import com.fliggy.bodymachine.utils.MyOnTouchListener;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by dicallc on 2018/4/27.
 */

public class SwiperFragment extends SupportFragment {
  private float x1;
  private float y1;
  private LoadUserActivity mLoadUserActivity;
  private MyOnTouchListener mMyTouchListener;

  @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mLoadUserActivity = (LoadUserActivity) getActivity();
    //继承了Activity的onTouchEvent方法，直接监听点击事件
    mMyTouchListener = new MyOnTouchListener() {

      @Override public boolean onTouchEvent(MotionEvent event) {
        //继承了Activity的onTouchEvent方法，直接监听点击事件
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
          //当手指按下的时候
          x1 = event.getX();
          y1 = event.getY();
        }
        if(event.getAction() == MotionEvent.ACTION_UP) {
          //当手指离开的时候
          float x2 = event.getX();
          float y2 = event.getY();
          if(y1 - y2 > 50) {
          } else if(y2 - y1 > 50) {
          } else if(x1 - x2 > 50) {

            mLoadUserActivity.toSettingUI();
          } else if(x2 - x1 > 50) {
          }
        }
        return false;
      }
    };
    // 将myTouchListener注册到分发列表
    mLoadUserActivity.registerMyTouchListener(mMyTouchListener);
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    mLoadUserActivity.unRegisterMyTouchListener(mMyTouchListener);
  }
}
