package com.fliggy.bodymachine;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.fliggy.bodymachine.ui.LoadUserActivity;

/**
 * Created by dicallc on 2018/4/27 0027.
 */

public class BootBroadcastReceiver extends BroadcastReceiver {
  static final String ACTION = "android.intent.action.BOOT_COMPLETED";
  @Override
  public void onReceive(Context context, Intent intent) {
    if (intent.getAction().equals(ACTION)) {
      Intent mainActivityIntent = new Intent(context, LoadUserActivity.class);  // 要启动的Activity
      mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      context.startActivity(mainActivityIntent);
    }
  }
}
