package com.fliggy.bodymachine;

import android.app.Application;
import android.content.Context;

/**
 * Created by dicallc on 2018/3/5.
 */
public class App extends Application{
  static App context;

  @Override
  public void onCreate() {
    super.onCreate();
    context = this;
  }
  public static Context getAppContext() {
    return context;
  }
}