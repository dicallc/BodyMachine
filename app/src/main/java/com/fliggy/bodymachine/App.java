package com.fliggy.bodymachine;

import android.app.Application;
import android.content.Context;
import io.realm.Realm;

/**
 * Created by dicallc on 2018/3/5.
 */
public class App extends Application{
  static App context;

  @Override
  public void onCreate() {
    super.onCreate();
    Realm.init(this);
    context = this;
  }
  public static Context getAppContext() {
    return context;
  }
}
