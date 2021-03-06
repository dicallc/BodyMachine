package com.fliggy.bodymachine.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import com.fliggy.bodymachine.utils.Constant;
import com.fliggy.bodymachine.utils.ScreenService;
import com.fliggy.bodymachine.utils.ServiceUtils;
import java.util.Date;
import me.yokeyword.fragmentation.SupportActivity;

public class ScreeActivity extends SupportActivity {

  private Handler mHandler01 = new Handler();
  private Handler mHandler02 = new Handler();

  /* 上一次User有动作的Time Stamp */
  private Date lastUpdateTime;
  /* 计算User有几秒没有动作的 */
  private long timePeriod;

  /* 静止超过N秒将自动进入屏保 */
  private float mHoldStillTime = 60;
  /*标识当前是否进入了屏保*/
  private boolean isRunScreenSaver;

  /*时间间隔*/
  private long intervalScreenSaver = 1000;
  private long intervalKeypadeSaver = 1000;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    boolean mServiceRunning =
        ServiceUtils.isServiceRunning(this, "com.fliggy.bodymachine.utils.ScreenService");
    if (!mServiceRunning){
      Intent mService = new Intent(ScreeActivity.this, ScreenService.class);
      mService.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      startService(mService);
    }

    /* 初始取得User可触碰屏幕的时间 */
    lastUpdateTime = new Date(System.currentTimeMillis());
  }

  /**
   * 计时线程
   */
  private Runnable mTask01 = new Runnable() {
    @Override
    public void run() {
      Date timeNow = new Date(System.currentTimeMillis());
      /* 计算User静止不动作的时间间距 */
      /**当前的系统时间 - 上次触摸屏幕的时间 = 静止不动的时间**/
      timePeriod = (long) timeNow.getTime() - (long) lastUpdateTime.getTime();

      /*将静止时间毫秒换算成秒*/
      float timePeriodSecond = ((float) timePeriod / 1000);

      if(timePeriodSecond > mHoldStillTime){
        if(isRunScreenSaver == false){  //说明没有进入屏保
          /* 启动线程去显示屏保 */
          mHandler02.postAtTime(mTask02, intervalScreenSaver);
          /*显示屏保置为true*/
          isRunScreenSaver = true;
        }else{
          /*屏保正在显示中*/
        }
      }else{
        /*说明静止之间没有超过规定时长*/
        isRunScreenSaver = false;
      }
      /*反复调用自己进行检查*/
      mHandler01.postDelayed(mTask01, intervalKeypadeSaver);
    }
  };

  /**
   * 持续屏保显示线程
   */
  private Runnable mTask02 = new Runnable() {

    @Override
    public void run() {
      // TODO Auto-generated method stub
      if (isRunScreenSaver == true) {  //如果屏保正在显示，就计算不断持续显示
        //              hideOriginalLayout();
        showScreenSaver();
        mHandler02.postDelayed(mTask02, intervalScreenSaver);
      } else {
        mHandler02.removeCallbacks(mTask02);  //如果屏保没有显示则移除线程
      }
    }
  };

  /**
   * 显示屏保
   */
  private void showScreenSaver(){
    Log.d("danxx", "显示屏保------>");
    Intent intent = new Intent(ScreeActivity.this, ScreenSaverActivity.class);
    intent.putExtra(Constant.ISPLAYAUDIO,true);
    startActivity(intent);

  }

  @Override
  public boolean dispatchKeyEvent(KeyEvent event) {
    updateUserActionTime();
    return super.dispatchKeyEvent(event);
  }

  /*用户有操作的时候不断重置静止时间和上次操作的时间*/
  public void updateUserActionTime() {
    Date timeNow = new Date(System.currentTimeMillis());
    timePeriod = timeNow.getTime() - lastUpdateTime.getTime();
    lastUpdateTime.setTime(timeNow.getTime());
  }

  @Override
  protected void onResume() {
    lastUpdateTime = new Date(System.currentTimeMillis());
    /*activity显示的时候启动线程*/
    mHandler01.postAtTime(mTask01, intervalKeypadeSaver);
    super.onResume();
  }

  @Override
  protected void onPause() {
    /*activity不可见的时候取消线程*/
    mHandler01.removeCallbacks(mTask01);
    mHandler02.removeCallbacks(mTask02);
    super.onPause();
  }
}
