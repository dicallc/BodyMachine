package com.fliggy.bodymachine;

import android.app.Application;
import android.content.Context;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by dicallc on 2018/3/5.
 */
public class App extends Application{
  static App context;

  @Override
  public void onCreate() {
    super.onCreate();
    Realm.init(this);
    RealmConfiguration config = new RealmConfiguration.Builder().name("myrealm.realm").build();
    Realm.setDefaultConfiguration(config);
    context = this;
  }
  public static Context getAppContext() {
    return context;
  }
}
